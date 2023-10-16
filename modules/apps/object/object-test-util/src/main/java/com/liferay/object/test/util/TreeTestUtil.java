/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.test.util;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.tree.Edge;
import com.liferay.object.tree.Node;
import com.liferay.object.tree.Tree;
import com.liferay.object.tree.TreeFactory;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.io.Serializable;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

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

	public static Tree createObjectDefinitionTree(
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

		return treeFactory.createObjectDefinitionTree(
			objectDefinitionA.getObjectDefinitionId());
	}

	public static Tree createObjectEntryTree(
			ObjectEntryLocalService objectEntryLocalService,
			ObjectFieldLocalService objectFieldLocalService,
			long rootObjectDefinitionId,
			ObjectRelationshipLocalService objectRelationshipLocalService,
			TreeFactory treeFactory, int treeId)
		throws PortalException {

		Tree objectDefinitionTree = treeFactory.createObjectDefinitionTree(
			rootObjectDefinitionId);

		Iterator<Node> iterator = objectDefinitionTree.iterator();

		Node rootNode = iterator.next();

		Queue<String> externalReferenceCodes = new ArrayDeque<>(
			Arrays.asList("A", "AA", "AB", "AAA", "AAB"));

		ObjectEntry rootObjectEntry = objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), 0, rootNode.getPrimaryKey(),
			HashMapBuilder.<String, Serializable>put(
				"externalReferenceCode", externalReferenceCodes.poll() + treeId
			).build(),
			ServiceContextTestUtil.getServiceContext());

		Map<Long, Long> objectEntryIds = HashMapBuilder.put(
			rootNode.getPrimaryKey(), rootObjectEntry.getObjectEntryId()
		).build();

		while (iterator.hasNext()) {
			Node node = iterator.next();

			ObjectEntry objectEntry = objectEntryLocalService.addObjectEntry(
				TestPropsValues.getUserId(), 0, node.getPrimaryKey(),
				HashMapBuilder.<String, Serializable>put(
					"externalReferenceCode",
					externalReferenceCodes.poll() + treeId
				).put(
					() -> {
						ObjectRelationship objectRelationship =
							objectRelationshipLocalService.
								getObjectRelationship(
									node.getEdge(
									).getObjectRelationshipId());

						ObjectField objectField =
							objectFieldLocalService.getObjectField(
								objectRelationship.getObjectFieldId2());

						return objectField.getName();
					},
					() -> {
						Node parentNode = node.getParentNode();

						return objectEntryIds.get(parentNode.getPrimaryKey());
					}
				).build(),
				ServiceContextTestUtil.getServiceContext());

			objectEntryIds.put(
				node.getPrimaryKey(), objectEntry.getObjectEntryId());
		}

		return treeFactory.createObjectEntryTree(
			rootObjectEntry.getObjectEntryId());
	}

	public static void deleteObjectDefinitionHierarchy(
			ObjectDefinitionLocalService objectDefinitionLocalService,
			String[] objectDefinitionNames)
		throws Exception {

		for (String objectDefinitionName : objectDefinitionNames) {
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

	public static void forEachNodeObjectDefinition(
			Iterator<Node> iterator,
			ObjectDefinitionLocalService objectDefinitionLocalService,
			UnsafeConsumer<ObjectDefinition, Exception> unsafeConsumer)
		throws Exception {

		while (iterator.hasNext()) {
			Node node = iterator.next();

			unsafeConsumer.accept(
				objectDefinitionLocalService.getObjectDefinition(
					node.getPrimaryKey()));
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

	private static String _getShortName(
			Node node,
			ObjectDefinitionLocalService objectDefinitionLocalService)
		throws PortalException {

		ObjectDefinition objectDefinition =
			objectDefinitionLocalService.getObjectDefinition(
				node.getPrimaryKey());

		return objectDefinition.getShortName();
	}

}