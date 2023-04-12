/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.object.admin.rest.internal.dto.v1_0.converter;

import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.object.admin.rest.dto.v1_0.ObjectField;
import com.liferay.object.admin.rest.dto.v1_0.ObjectFieldSetting;
import com.liferay.object.admin.rest.dto.v1_0.util.ObjectStateFlowUtil;
import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.filter.util.ObjectFilterUtil;
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

				if (FeatureFlagManagerUtil.isEnabled("LPS-146755")) {
					localized = objectField.getLocalized();
				}

				name = objectField.getName();
				objectFieldSettings = TransformUtil.transformToArray(
					objectField.getObjectFieldSettings(),
					objectFieldSetting -> _toObjectFieldSetting(
						objectFieldSetting),
					ObjectFieldSetting.class);
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
			}
		};
	}

	private ObjectFieldSetting _toObjectFieldSetting(
		com.liferay.object.model.ObjectFieldSetting
			serviceBuilderObjectFieldSetting) {

		if ((serviceBuilderObjectFieldSetting == null) ||
			(!FeatureFlagManagerUtil.isEnabled("LPS-163716") &&
			 (serviceBuilderObjectFieldSetting.compareName(
				 ObjectFieldSettingConstants.NAME_DEFAULT_VALUE) ||
			  serviceBuilderObjectFieldSetting.compareName(
				  ObjectFieldSettingConstants.NAME_DEFAULT_VALUE_TYPE)))) {

			return null;
		}

		return new ObjectFieldSetting() {
			{
				name = serviceBuilderObjectFieldSetting.getName();

				setValue(
					() -> {
						if (serviceBuilderObjectFieldSetting.compareName(
								ObjectFieldSettingConstants.NAME_FILTERS)) {

							return ObjectFilterUtil.getObjectFiltersJSONArray(
								serviceBuilderObjectFieldSetting.
									getObjectFilters());
						}
						else if (serviceBuilderObjectFieldSetting.compareName(
									ObjectFieldSettingConstants.
										NAME_STATE_FLOW)) {

							return ObjectStateFlowUtil.toObjectStateFlow(
								_objectStateFlowLocalService.
									fetchObjectStateFlow(
										GetterUtil.getLong(
											serviceBuilderObjectFieldSetting.
												getValue())));
						}

						return serviceBuilderObjectFieldSetting.getValue();
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