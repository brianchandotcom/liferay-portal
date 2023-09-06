/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.admin.rest.internal.dto.v1_0.converter;

import com.liferay.object.admin.rest.dto.v1_0.ObjectRelationship;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = "dto.class.name=com.liferay.object.model.ObjectRelationship",
	service = DTOConverter.class
)
public class ObjectRelationshipDTOConverter
	implements DTOConverter
		<com.liferay.object.model.ObjectRelationship, ObjectRelationship> {

	@Override
	public String getContentType() {
		return ObjectRelationship.class.getSimpleName();
	}

	@Override
	public ObjectRelationship toDTO(
			DTOConverterContext dtoConverterContext,
			com.liferay.object.model.ObjectRelationship
				serviceBuilderObjectRelationship)
		throws Exception {

		if (serviceBuilderObjectRelationship == null) {
			return null;
		}

		ObjectDefinition objectDefinition1 =
			_objectDefinitionLocalService.getObjectDefinition(
				serviceBuilderObjectRelationship.getObjectDefinitionId1());

		ObjectDefinition objectDefinition2 =
			_objectDefinitionLocalService.getObjectDefinition(
				serviceBuilderObjectRelationship.getObjectDefinitionId2());

		return new ObjectRelationship() {
			{
				actions = dtoConverterContext.getActions();
				deletionType = ObjectRelationship.DeletionType.create(
					serviceBuilderObjectRelationship.getDeletionType());
				id = serviceBuilderObjectRelationship.getObjectRelationshipId();
				label = LocalizedMapUtil.getLanguageIdMap(
					serviceBuilderObjectRelationship.getLabelMap());
				name = serviceBuilderObjectRelationship.getName();
				objectDefinitionExternalReferenceCode1 =
					objectDefinition1.getExternalReferenceCode();
				objectDefinitionExternalReferenceCode2 =
					objectDefinition2.getExternalReferenceCode();
				objectDefinitionId1 =
					serviceBuilderObjectRelationship.getObjectDefinitionId1();
				objectDefinitionId2 =
					serviceBuilderObjectRelationship.getObjectDefinitionId2();
				objectDefinitionName2 = objectDefinition2.getShortName();
				parameterObjectFieldId =
					serviceBuilderObjectRelationship.
						getParameterObjectFieldId();
				reverse = serviceBuilderObjectRelationship.isReverse();
				type = ObjectRelationship.Type.create(
					serviceBuilderObjectRelationship.getType());

				setEdge(
					() -> {
						if (!FeatureFlagManagerUtil.isEnabled("LPS-187142")) {
							return null;
						}

						return serviceBuilderObjectRelationship.isEdge();
					});
				setObjectDefinitionModifiable2(
					() -> {
						if (!FeatureFlagManagerUtil.isEnabled("LPS-167253")) {
							return null;
						}

						return objectDefinition2.isModifiable();
					});
				setObjectDefinitionSystem2(
					() -> {
						if (!FeatureFlagManagerUtil.isEnabled("LPS-167253")) {
							return null;
						}

						return objectDefinition2.isSystem();
					});
				setParameterObjectFieldName(
					() -> {
						if (Validator.isNull(
								serviceBuilderObjectRelationship.
									getParameterObjectFieldId())) {

							return StringPool.BLANK;
						}

						ObjectField objectField =
							_objectFieldLocalService.getObjectField(
								serviceBuilderObjectRelationship.
									getParameterObjectFieldId());

						return objectField.getName();
					});
			}
		};
	}

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

}