/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.tree;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.service.persistence.ObjectDefinitionPersistence;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Pedro Leite
 */
public class ObjectDefinitionTreeFactory extends BaseTreeFactory {

	public ObjectDefinitionTreeFactory(
		ObjectDefinitionLocalService objectDefinitionLocalService,
		ObjectRelationshipLocalService objectRelationshipLocalService) {

		super(objectRelationshipLocalService);

		_objectDefinitionLocalService = objectDefinitionLocalService;
	}

	public ObjectDefinitionTreeFactory(
		ObjectDefinitionPersistence objectDefinitionPersistence,
		ObjectRelationshipLocalService objectRelationshipLocalService) {

		super(objectRelationshipLocalService);

		_objectDefinitionPersistence = objectDefinitionPersistence;
	}

	public Tree create(boolean excludeDifferentStatus, long objectDefinitionId)
		throws PortalException {

		ObjectDefinition rootObjectDefinition = _getObjectDefinition(
			objectDefinitionId);

		return apply(
			objectDefinitionId,
			node -> TransformUtil.transform(
				objectRelationshipLocalService.getObjectRelationships(
					node.getPrimaryKey(), true),
				objectRelationship -> {
					ObjectDefinition objectDefinition2 = _getObjectDefinition(
						objectRelationship.getObjectDefinitionId2());

					if ((excludeDifferentStatus &&
						 (rootObjectDefinition.getStatus() !=
							 objectDefinition2.getStatus())) ||
						(rootObjectDefinition.getObjectDefinitionId() !=
							objectDefinition2.getRootObjectDefinitionId())) {

						return null;
					}

					return new Node(
						new Edge(objectRelationship.getObjectRelationshipId()),
						node, objectRelationship.getObjectDefinitionId2());
				}));
	}

	public Tree create(long objectDefinitionId) throws PortalException {
		return create(true, objectDefinitionId);
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

}