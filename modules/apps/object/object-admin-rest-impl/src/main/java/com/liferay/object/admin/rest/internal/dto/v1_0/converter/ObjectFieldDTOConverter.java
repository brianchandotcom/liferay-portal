/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.admin.rest.internal.dto.v1_0.converter;

import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.object.admin.rest.dto.v1_0.ObjectField;
import com.liferay.object.admin.rest.dto.v1_0.ObjectFieldSetting;
import com.liferay.object.admin.rest.dto.v1_0.util.ObjectFieldSettingUtil;
import com.liferay.object.admin.rest.dto.v1_0.util.ObjectStateFlowUtil;
import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.service.ObjectFieldSettingLocalService;
import com.liferay.object.service.ObjectStateFlowLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Feliphe Marinho
 */
@Component(
	property = "dto.class.name=com.liferay.object.model.ObjectField",
	service = DTOConverter.class
)
public class ObjectFieldDTOConverter
	implements DTOConverter<com.liferay.object.model.ObjectField, ObjectField> {

	@Override
	public String getContentType() {
		return ObjectField.class.getSimpleName();
	}

	@Override
	public ObjectField toDTO(
			DTOConverterContext dtoConverterContext,
			com.liferay.object.model.ObjectField objectField)
		throws Exception {

		if (objectField == null) {
			return null;
		}

		return new ObjectField() {
			{
				actions = dtoConverterContext.getActions();
				businessType = ObjectField.BusinessType.create(
					objectField.getBusinessType());
				DBType = ObjectField.DBType.create(objectField.getDBType());
				defaultValue =
					com.liferay.object.field.setting.util.
						ObjectFieldSettingUtil.getDefaultValueAsString(
							null, objectField.getObjectFieldId(),
							_objectFieldSettingLocalService, null);
				externalReferenceCode = objectField.getExternalReferenceCode();
				id = objectField.getObjectFieldId();
				indexed = objectField.getIndexed();
				indexedAsKeyword = objectField.getIndexedAsKeyword();
				indexedLanguageId = objectField.getIndexedLanguageId();
				label = LocalizedMapUtil.getLanguageIdMap(
					objectField.getLabelMap());
				listTypeDefinitionId = objectField.getListTypeDefinitionId();
				name = objectField.getName();
				objectFieldSettings = TransformUtil.transformToArray(
					objectField.getObjectFieldSettings(),
					objectFieldSetting -> _toObjectFieldSetting(
						objectFieldSetting),
					ObjectFieldSetting.class);
				readOnly = ObjectField.ReadOnly.create(
					objectField.getReadOnly());
				readOnlyConditionExpression =
					objectField.getReadOnlyConditionExpression();
				relationshipType = ObjectField.RelationshipType.create(
					objectField.getRelationshipType());
				required = objectField.isRequired();
				state = objectField.isState();
				system = objectField.getSystem();
				type = ObjectField.Type.create(objectField.getDBType());

				setListTypeDefinitionExternalReferenceCode(
					() -> {
						if (objectField.getListTypeDefinitionId() == 0) {
							return null;
						}

						ListTypeDefinition listTypeDefinition =
							_listTypeDefinitionLocalService.
								fetchListTypeDefinition(
									objectField.getListTypeDefinitionId());

						return listTypeDefinition.getExternalReferenceCode();
					});
				setLocalized(
					() -> {
						if (!FeatureFlagManagerUtil.isEnabled("LPS-172017")) {
							return null;
						}

						return objectField.getLocalized();
					});
			}
		};
	}

	private ObjectFieldSetting _toObjectFieldSetting(
		com.liferay.object.model.ObjectFieldSetting
			serviceBuilderObjectFieldSetting) {

		if (serviceBuilderObjectFieldSetting == null) {
			return null;
		}

		return new ObjectFieldSetting() {
			{
				name = serviceBuilderObjectFieldSetting.getName();

				setValue(
					() -> {
						if (serviceBuilderObjectFieldSetting.compareName(
								ObjectFieldSettingConstants.NAME_STATE_FLOW)) {

							return ObjectStateFlowUtil.toObjectStateFlow(
								_objectStateFlowLocalService.
									fetchObjectStateFlow(
										GetterUtil.getLong(
											serviceBuilderObjectFieldSetting.
												getValue())));
						}

						return ObjectFieldSettingUtil.getValue(
							serviceBuilderObjectFieldSetting);
					});
			}
		};
	}

	@Reference
	private ListTypeDefinitionLocalService _listTypeDefinitionLocalService;

	@Reference
	private ObjectFieldSettingLocalService _objectFieldSettingLocalService;

	@Reference
	private ObjectStateFlowLocalService _objectStateFlowLocalService;

}