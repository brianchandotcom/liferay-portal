/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.test.util;

import com.liferay.object.definition.tree.Edge;
import com.liferay.object.definition.tree.Node;
import com.liferay.object.definition.tree.Tree;
import com.liferay.object.definition.tree.TreeFactory;
import com.liferay.object.definition.tree.constants.TreeConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.util.TestPropsValues;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Feliphe Marinho
 */
public class TreeTestUtil {

	public static void assertTree(
			Map<String, String[]> expectedMap, Tree actualTree,
			ObjectDefinitionLocalService objectDefinitionLocalService)
		throws PortalException {

		Map<String, String[]> actualMap = new LinkedHashMap<>();

		Iterator<Node> iterator = actualTree.iterator();

		while (iterator.hasNext()) {
			Node node = iterator.next();

			actualMap.put(
				_getShortName(node, objectDefinitionLocalService),
				TransformUtil.transformToArray(
					node.getChildNodes(),
					childNode -> _getShortName(
						childNode, objectDefinitionLocalService),
					String.class));
		}

		AssertUtils.assertEquals(expectedMap, actualMap);
	}

	public static void bind(
			ObjectDefinitionLocalService objectDefinitionLocalService,
			List<ObjectRelationship> objectRelationships)
		throws PortalException {

		objectDefinitionLocalService.bindObjectDefinitions(
			TransformUtil.transformToLongArray(
				objectRelationships,
				ObjectRelationship::getObjectRelationshipId));
	}

	public static Tree createTree(
			ObjectDefinitionLocalService objectDefinitionLocalService,
			ObjectRelationshipLocalService objectRelationshipLocalService,
			TreeFactory treeFactory)
		throws PortalException {

		ObjectDefinition objectDefinitionA =
			ObjectDefinitionTestUtil.addCustomObjectDefinition(
				"A", objectDefinitionLocalService);

		ObjectDefinition objectDefinitionAA =
			ObjectDefinitionTestUtil.addCustomObjectDefinition(
				"AA", objectDefinitionLocalService);

		bind(
			objectDefinitionLocalService,
			Arrays.asList(
				ObjectRelationshipTestUtil.addObjectRelationship(
					objectRelationshipLocalService, objectDefinitionA,
					objectDefinitionAA),
				ObjectRelationshipTestUtil.addObjectRelationship(
					objectRelationshipLocalService, objectDefinitionAA,
					ObjectDefinitionTestUtil.addCustomObjectDefinition(
						"AAA", objectDefinitionLocalService)),
				ObjectRelationshipTestUtil.addObjectRelationship(
					objectRelationshipLocalService, objectDefinitionAA,
					ObjectDefinitionTestUtil.addCustomObjectDefinition(
						"AAB", objectDefinitionLocalService)),
				ObjectRelationshipTestUtil.addObjectRelationship(
					objectRelationshipLocalService, objectDefinitionA,
					ObjectDefinitionTestUtil.addCustomObjectDefinition(
						"AB", objectDefinitionLocalService))));

		return treeFactory.create(objectDefinitionA.getObjectDefinitionId());
	}

	public static void deleteObjectDefinitionHierarchy(
			ObjectDefinitionLocalService objectDefinitionLocalService)
		throws Exception {

		for (String objectDefinitionName :
				new String[] {"C_A", "C_AA", "C_AAA", "C_AAB", "C_AB"}) {

			ObjectDefinition objectDefinition =
				objectDefinitionLocalService.fetchObjectDefinition(
					TestPropsValues.getCompanyId(), objectDefinitionName);

			if (objectDefinition == null) {
				continue;
			}

			if (objectDefinition.getRootObjectDefinitionId() != 0) {
				unbind(objectDefinitionLocalService, objectDefinitionName);
			}

			objectDefinitionLocalService.deleteObjectDefinition(
				objectDefinition.getObjectDefinitionId());
		}
	}

	public static ObjectRelationship getEdgeObjectRelationship(
			ObjectDefinition objectDefinition,
			ObjectRelationshipLocalService objectRelationshipLocalService,
			Tree tree)
		throws PortalException {

		Node node = tree.getNode(objectDefinition.getObjectDefinitionId());

		Edge edge = node.getEdge();

		return objectRelationshipLocalService.getObjectRelationship(
			edge.getObjectRelationshipId());
	}

	public static void unbind(
			ObjectDefinitionLocalService objectDefinitionLocalService,
			String objectDefinitionName)
		throws PortalException {

		ObjectDefinition objectDefinition =
			objectDefinitionLocalService.fetchObjectDefinition(
				TestPropsValues.getCompanyId(), objectDefinitionName);

		objectDefinitionLocalService.unbindObjectDefinition(
			objectDefinition.getObjectDefinitionId());
	}

	public static void unsafeForEach(
			ObjectDefinitionLocalService objectDefinitionLocalService,
			Tree tree,
			UnsafeConsumer<ObjectDefinition, Exception> unsafeConsumer)
		throws Exception {

		unsafeForEach(
			TreeConstants.ITERATOR_TYPE_BREADTH_FIRST,
			objectDefinitionLocalService, tree, unsafeConsumer);
	}

	public static void unsafeForEach(
			String iteratorType,
			ObjectDefinitionLocalService objectDefinitionLocalService,
			Tree tree,
			UnsafeConsumer<ObjectDefinition, Exception> unsafeConsumer)
		throws Exception {

		Iterator<Node> iterator = tree.iterator(iteratorType);

		while (iterator.hasNext()) {
			Node node = iterator.next();

			unsafeConsumer.accept(
				objectDefinitionLocalService.getObjectDefinition(
					node.getObjectDefinitionId()));
		}
	}

	private static String _getShortName(
			Node node,
			ObjectDefinitionLocalService objectDefinitionLocalService)
		throws PortalException {

		ObjectDefinition objectDefinition =
			objectDefinitionLocalService.getObjectDefinition(
				node.getObjectDefinitionId());

		return objectDefinition.getShortName();
	}

}