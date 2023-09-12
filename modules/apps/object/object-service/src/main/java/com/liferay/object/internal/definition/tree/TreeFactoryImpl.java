/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.definition.tree;

import com.liferay.object.definition.tree.Edge;
import com.liferay.object.definition.tree.Node;
import com.liferay.object.definition.tree.Tree;
import com.liferay.object.definition.tree.TreeFactory;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ListUtil;

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
	public Tree create(long rootObjectDefinitionId) throws PortalException {
		Node rootNode = new Node(null, rootObjectDefinitionId, null);

		Queue<Node> queue = new LinkedList<>();

		queue.add(rootNode);

		while (!queue.isEmpty()) {
			Node node = queue.poll();

			List<Node> nodes = _getChildrenNodes(node);

			if (ListUtil.isNotEmpty(nodes)) {
				node.setChildNodes(nodes);

				queue.addAll(nodes);
			}
		}

		return new Tree(rootNode);
	}

	private List<Node> _getChildrenNodes(Node node) {
		return TransformUtil.transform(
			_objectRelationshipLocalService.getObjectRelationships(
				node.getObjectDefinitionId(), true),
			objectRelationship -> new Node(
				new Edge(objectRelationship.getObjectRelationshipId()),
				objectRelationship.getObjectDefinitionId2(), node));
	}

	@Reference
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

}