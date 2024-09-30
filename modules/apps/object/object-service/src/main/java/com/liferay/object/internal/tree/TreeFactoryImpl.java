/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.tree;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.tree.Edge;
import com.liferay.object.tree.Node;
import com.liferay.object.tree.Tree;
import com.liferay.object.tree.TreeFactory;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
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
	public Tree createObjectDefinitionTree(
			long objectDefinitionId,
			UnsafeFunction<Long, ObjectDefinition, PortalException>
				objectDefinitionLookupUnsafeFunction)
		throws PortalException {

		ObjectRelationshipLocalService objectRelationshipLocalService =
			_objectRelationshipLocalServiceSnapshot.get();

		ObjectDefinition rootObjectDefinition =
			objectDefinitionLookupUnsafeFunction.apply(objectDefinitionId);

		return _create(
			objectDefinitionId,
			node -> TransformUtil.transform(
				objectRelationshipLocalService.getObjectRelationships(
					node.getPrimaryKey(), true),
				objectRelationship -> {
					ObjectDefinition objectDefinition2 =
						objectDefinitionLookupUnsafeFunction.apply(
							objectRelationship.getObjectDefinitionId2());

					if (rootObjectDefinition.isApproved() !=
							objectDefinition2.isApproved()) {

						return null;
					}

					return new Node(
						new Edge(objectRelationship.getObjectRelationshipId()),
						node, objectRelationship.getObjectDefinitionId2());
				}));
	}

	@Override
	public Tree createObjectEntryTree(long objectEntryId)
		throws PortalException {

		ObjectRelationshipLocalService objectRelationshipLocalService =
			_objectRelationshipLocalServiceSnapshot.get();

		UnsafeFunction<Node, List<Node>, PortalException> unsafeFunction =
			node -> {
				ObjectEntry parentObjectEntry =
					_objectEntryLocalService.fetchObjectEntry(
						node.getPrimaryKey());

				List<Node> childrenNodes = new ArrayList<>();

				for (ObjectRelationship objectRelationship :
						objectRelationshipLocalService.getObjectRelationships(
							parentObjectEntry.getObjectDefinitionId(), true)) {

					childrenNodes.addAll(
						TransformUtil.transform(
							_objectEntryLocalService.getOneToManyObjectEntries(
								parentObjectEntry.getGroupId(),
								objectRelationship.getObjectRelationshipId(),
								parentObjectEntry.getPrimaryKey(), true, null,
								QueryUtil.ALL_POS, QueryUtil.ALL_POS),
							objectEntry -> new Node(
								new Edge(
									objectRelationship.
										getObjectRelationshipId()),
								node, objectEntry.getObjectEntryId())));
				}

				return childrenNodes;
			};

		return _create(objectEntryId, unsafeFunction);
	}

	private Tree _create(
			long primaryKey,
			UnsafeFunction<Node, List<Node>, PortalException> unsafeFunction)
		throws PortalException {

		Node rootNode = new Node(null, null, primaryKey);

		Queue<Node> queue = new LinkedList<>();

		queue.add(rootNode);

		while (!queue.isEmpty()) {
			Node node = queue.poll();

			List<Node> nodes = unsafeFunction.apply(node);

			if (ListUtil.isNotEmpty(nodes)) {
				node.setChildNodes(nodes);

				queue.addAll(nodes);
			}
		}

		return new Tree(rootNode);
	}

	private static final Snapshot<ObjectRelationshipLocalService>
		_objectRelationshipLocalServiceSnapshot = new Snapshot<>(
			TreeFactoryImpl.class, ObjectRelationshipLocalService.class, null,
			true);

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

}