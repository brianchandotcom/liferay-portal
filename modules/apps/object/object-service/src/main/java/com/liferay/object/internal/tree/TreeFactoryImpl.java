/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.tree;

import com.liferay.object.tree.Edge;
import com.liferay.object.tree.Node;
import com.liferay.object.tree.Tree;
import com.liferay.object.tree.TreeFactory;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Feliphe Marinho
 */
@Component(service = TreeFactory.class)
public class TreeFactoryImpl implements TreeFactory {

	@Override
	public Tree create(long objectDefinitionId) throws PortalException {
		return create(objectDefinitionId, true);
	}

	@Override
	public Tree create(long primaryKey, boolean objectDefinitionNode)
		throws PortalException {

		Node rootNode = new Node(null, primaryKey, null);

		Queue<Node> queue = new LinkedList<>();

		queue.add(rootNode);

		while (!queue.isEmpty()) {
			Node node = queue.poll();

			List<Node> nodes = _getChildrenNodes(node, objectDefinitionNode);

			if (ListUtil.isNotEmpty(nodes)) {
				node.setChildNodes(nodes);

				queue.addAll(nodes);
			}
		}

		return new Tree(rootNode);
	}

	private List<Node> _getChildrenNodes(
			Node node, boolean objectDefinitionNode)
		throws PortalException {

		if (objectDefinitionNode) {
			return TransformUtil.transform(
				_objectRelationshipLocalService.getObjectRelationships(
					node.getPrimaryKey(), true),
				objectRelationship -> new Node(
					new Edge(objectRelationship.getObjectRelationshipId()),
					objectRelationship.getObjectDefinitionId2(), node));
		}

		ObjectEntry parentObjectEntry =
			_objectEntryLocalService.fetchObjectEntry(node.getPrimaryKey());

		if (parentObjectEntry == null) {
			return Collections.emptyList();
		}

		List<Node> childrenNodes = new ArrayList<>();

		for (ObjectRelationship objectRelationship :
				_objectRelationshipLocalService.getObjectRelationships(
					parentObjectEntry.getObjectDefinitionId(), true)) {

			for (ObjectEntry objectEntry :
					_objectEntryLocalService.getOneToManyObjectEntries(
						parentObjectEntry.getGroupId(),
						objectRelationship.getObjectRelationshipId(),
						parentObjectEntry.getPrimaryKey(), true, null,
						QueryUtil.ALL_POS, QueryUtil.ALL_POS)) {

				childrenNodes.add(
					new Node(
						new Edge(objectRelationship.getObjectRelationshipId()),
						objectEntry.getObjectEntryId(), node));
			}
		}

		return childrenNodes;
	}

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

}