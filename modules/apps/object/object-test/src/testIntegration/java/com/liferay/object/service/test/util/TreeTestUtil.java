/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.test.util;

import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.definition.tree.Edge;
import com.liferay.object.definition.tree.Node;
import com.liferay.object.definition.tree.Tree;
import com.liferay.object.definition.tree.TreeFactory;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

/**
 * @author Feliphe Marinho
 */
public class TreeTestUtil {

	public static ObjectDefinition bindObjectDefinition(
			ObjectDefinition objectDefinition,
			ObjectDefinitionLocalService objectDefinitionLocalService,
			ObjectRelationshipLocalService objectRelationshipLocalService,
			ObjectDefinition parentObjectDefinition,
			ObjectDefinition rootObjectDefinition)
		throws PortalException {

		ObjectRelationship objectRelationship =
			objectRelationshipLocalService.addObjectRelationship(
				TestPropsValues.getUserId(),
				parentObjectDefinition.getObjectDefinitionId(),
				objectDefinition.getObjectDefinitionId(), 0,
				ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				StringUtil.randomId(),
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		objectRelationshipLocalService.updateObjectRelationship(
			objectRelationship.getObjectRelationshipId(), 0,
			ObjectRelationshipConstants.DELETION_TYPE_CASCADE, true,
			objectRelationship.getLabelMap());

		return objectDefinitionLocalService.updateRootObjectDefinitionId(
			objectDefinition.getObjectDefinitionId(),
			rootObjectDefinition.getObjectDefinitionId());
	}

	public static Tree createTree(
			ObjectDefinitionLocalService objectDefinitionLocalService,
			ObjectRelationshipLocalService objectRelationshipLocalService,
			TreeFactory treeFactory)
		throws PortalException {

		ObjectDefinition objectDefinitionA =
			ObjectDefinitionTestUtil.addObjectDefinition(
				"A", objectDefinitionLocalService);

		objectDefinitionLocalService.updateRootObjectDefinitionId(
			objectDefinitionA.getObjectDefinitionId(),
			objectDefinitionA.getObjectDefinitionId());

		ObjectDefinition objectDefinitionAA =
			ObjectDefinitionTestUtil.addObjectDefinition(
				"AA", objectDefinitionLocalService);

		bindObjectDefinition(
			objectDefinitionAA, objectDefinitionLocalService,
			objectRelationshipLocalService, objectDefinitionA,
			objectDefinitionA);
		bindObjectDefinition(
			ObjectDefinitionTestUtil.addObjectDefinition(
				"AAA", objectDefinitionLocalService),
			objectDefinitionLocalService, objectRelationshipLocalService,
			objectDefinitionAA, objectDefinitionA);
		bindObjectDefinition(
			ObjectDefinitionTestUtil.addObjectDefinition(
				"AAB", objectDefinitionLocalService),
			objectDefinitionLocalService, objectRelationshipLocalService,
			objectDefinitionAA, objectDefinitionA);

		bindObjectDefinition(
			ObjectDefinitionTestUtil.addObjectDefinition(
				"AB", objectDefinitionLocalService),
			objectDefinitionLocalService, objectRelationshipLocalService,
			objectDefinitionA, objectDefinitionA);

		return treeFactory.create(objectDefinitionA.getObjectDefinitionId());
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

}