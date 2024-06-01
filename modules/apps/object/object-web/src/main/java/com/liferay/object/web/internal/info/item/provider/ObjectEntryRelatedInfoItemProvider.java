/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.info.item.provider;

import com.liferay.info.item.provider.RelatedInfoItemProvider;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.petra.function.transform.TransformUtil;

import java.util.List;
import java.util.Objects;

/**
 * @author Eudaldo Alonso
 */
public class ObjectEntryRelatedInfoItemProvider
	implements RelatedInfoItemProvider<ObjectEntry> {

	public ObjectEntryRelatedInfoItemProvider(
		ObjectDefinition objectDefinition,
		ObjectDefinitionLocalService objectDefinitionLocalService,
		ObjectRelationshipLocalService objectRelationshipLocalService) {

		_objectDefinition = objectDefinition;
		_objectDefinitionLocalService = objectDefinitionLocalService;
		_objectRelationshipLocalService = objectRelationshipLocalService;
	}

	@Override
	public List<String> getRelatedItemClassNames() {
		return TransformUtil.transform(
			_objectRelationshipLocalService.getObjectRelationships(
				_objectDefinition.getObjectDefinitionId(),
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY),
			objectRelationship -> {
				if (!objectRelationship.isSelf() &&
					Objects.equals(
						_objectDefinition.getObjectDefinitionId(),
						objectRelationship.getObjectDefinitionId1())) {

					return null;
				}

				ObjectDefinition parentObjectDefinition =
					_objectDefinitionLocalService.fetchObjectDefinition(
						objectRelationship.getObjectDefinitionId1());

				return parentObjectDefinition.getClassName();
			});
	}

	private final ObjectDefinition _objectDefinition;
	private final ObjectDefinitionLocalService _objectDefinitionLocalService;
	private final ObjectRelationshipLocalService
		_objectRelationshipLocalService;

}