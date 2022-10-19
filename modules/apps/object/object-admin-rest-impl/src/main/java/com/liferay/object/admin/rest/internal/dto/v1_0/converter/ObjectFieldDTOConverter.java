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
import com.liferay.object.admin.rest.internal.dto.v1_0.util.ObjectFieldSettingUtil;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Feliphe Marinho
 */
@Component(
	property = "dto.class.name=com.liferay.object.model.ObjectField",
	service = {DTOConverter.class, ObjectFieldDTOConverter.class}
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
				defaultValue = objectField.getDefaultValue();
				externalReferenceCode = objectField.getExternalReferenceCode();
				id = objectField.getObjectFieldId();
				indexed = objectField.getIndexed();
				indexedAsKeyword = objectField.getIndexedAsKeyword();
				indexedLanguageId = objectField.getIndexedLanguageId();
				label = LocalizedMapUtil.getLanguageIdMap(
					objectField.getLabelMap());

				if (!GetterUtil.getBoolean(
						PropsUtil.get("feature.flag.LPS-164278"))) {

					listTypeDefinitionId =
						objectField.getListTypeDefinitionId();
				}

				name = objectField.getName();
				objectFieldSettings = TransformUtil.transformToArray(
					objectField.getObjectFieldSettings(),
					objectFieldSetting ->
						ObjectFieldSettingUtil.toObjectFieldSetting(
							objectField.getBusinessType(), objectFieldSetting),
					ObjectFieldSetting.class);
				relationshipType = ObjectField.RelationshipType.create(
					objectField.getRelationshipType());
				required = objectField.isRequired();
				state = objectField.isState();
				system = objectField.getSystem();
				type = ObjectField.Type.create(objectField.getDBType());

				if (GetterUtil.getBoolean(
						PropsUtil.get("feature.flag.LPS-164278"))) {

					setListTypeDefinitionExternalReferenceCode(
						() -> {
							if (objectField.getListTypeDefinitionId() == 0) {
								return StringPool.BLANK;
							}

							ListTypeDefinition listTypeDefinition =
								_listTypeDefinitionLocalService.
									fetchListTypeDefinition(
										objectField.getListTypeDefinitionId());

							return listTypeDefinition.
								getExternalReferenceCode();
						});
				}
			}
		};
	}

	@Reference
	private ListTypeDefinitionLocalService _listTypeDefinitionLocalService;

}