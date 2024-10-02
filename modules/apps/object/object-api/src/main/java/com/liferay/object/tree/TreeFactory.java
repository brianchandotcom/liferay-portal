/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.tree;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.service.persistence.ObjectDefinitionPersistence;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author Feliphe Marinho
 */
public class TreeFactory {

	public TreeFactory(
		ObjectDefinitionLocalService objectDefinitionLocalService,
		ObjectEntryLocalService objectEntryLocalService,
		ObjectRelationshipLocalService objectRelationshipLocalService) {

		_objectDefinitionLocalService = objectDefinitionLocalService;
		_objectEntryLocalService = objectEntryLocalService;
		_objectRelationshipLocalService = objectRelationshipLocalService;
	}

	public TreeFactory(
		ObjectDefinitionLocalService objectDefinitionLocalService,
		ObjectRelationshipLocalService objectRelationshipLocalService) {

		_objectDefinitionLocalService = objectDefinitionLocalService;
		_objectRelationshipLocalService = objectRelationshipLocalService;
	}

	public TreeFactory(
		ObjectDefinitionPersistence objectDefinitionPersistence,
		ObjectEntryLocalService objectEntryLocalService,
		ObjectRelationshipLocalService objectRelationshipLocalService) {

		_objectDefinitionPersistence = objectDefinitionPersistence;
		_objectEntryLocalService = objectEntryLocalService;
		_objectRelationshipLocalService = objectRelationshipLocalService;
	}

	public TreeFactory(
		ObjectDefinitionPersistence objectDefinitionPersistence,
		ObjectRelationshipLocalService objectRelationshipLocalService) {

		_objectDefinitionPersistence = objectDefinitionPersistence;
		_objectRelationshipLocalService = objectRelationshipLocalService;
	}

	public Tree createObjectDefinitionTree(long objectDefinitionId)
		throws PortalException {

		ObjectDefinition rootObjectDefinition = _getObjectDefinition(
			objectDefinitionId);

		return _create(
			objectDefinitionId,
			node -> TransformUtil.transform(
				_objectRelationshipLocalService.getObjectRelationships(
					node.getPrimaryKey(), true),
				objectRelationship -> {
					ObjectDefinition objectDefinition2 = _getObjectDefinition(
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

	public Tree createObjectEntryTree(long objectEntryId)
		throws PortalException {

		UnsafeFunction<Node, List<Node>, PortalException> unsafeFunction =
			node -> {
				ObjectEntry parentObjectEntry =
					_objectEntryLocalService.fetchObjectEntry(
						node.getPrimaryKey());

				List<Node> childrenNodes = new ArrayList<>();

				for (ObjectRelationship objectRelationship :
						_objectRelationshipLocalService.getObjectRelationships(
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

	private ObjectDefinition _getObjectDefinition(long objectDefinitionId)
		throws PortalException {

		if (_objectDefinitionPersistence == null) {
			return _objectDefinitionLocalService.getObjectDefinition(
				objectDefinitionId);
		}

		return _objectDefinitionPersistence.findByPrimaryKey(
			objectDefinitionId);
	}

	private ObjectDefinitionLocalService _objectDefinitionLocalService;
	private ObjectDefinitionPersistence _objectDefinitionPersistence;
	private ObjectEntryLocalService _objectEntryLocalService;
	private final ObjectRelationshipLocalService
		_objectRelationshipLocalService;

}