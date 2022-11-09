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

package com.liferay.object.internal.field.business.type;

import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.dynamic.data.mapping.form.field.type.constants.ObjectDDMFormFieldTypeConstants;
import com.liferay.object.field.business.type.ObjectFieldBusinessType;
import com.liferay.object.field.setting.util.ObjectFieldSettingUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectEntryLocalServiceUtil;
import com.liferay.object.service.persistence.ObjectDefinitionPersistence;
import com.liferay.object.service.persistence.ObjectRelationshipPersistence;
import com.liferay.object.system.SystemObjectDefinitionMetadata;
import com.liferay.object.system.SystemObjectDefinitionMetadataTracker;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.extension.PropertyDefinition;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcela Cunha
 */
@Component(
	property = "object.field.business.type.key=" + ObjectFieldConstants.BUSINESS_TYPE_RELATIONSHIP,
	service = {
		ObjectFieldBusinessType.class, RelationshipObjectFieldBusinessType.class
	}
)
public class RelationshipObjectFieldBusinessType
	implements ObjectFieldBusinessType {

	@Override
	public String getDBType() {
		return ObjectFieldConstants.DB_TYPE_LONG;
	}

	@Override
	public String getDDMFormFieldTypeName() {
		return ObjectDDMFormFieldTypeConstants.OBJECT_RELATIONSHIP;
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "relationship");
	}

	@Override
	public String getName() {
		return ObjectFieldConstants.BUSINESS_TYPE_RELATIONSHIP;
	}

	@Override
	public PropertyDefinition.PropertyType getPropertyType() {
		return PropertyDefinition.PropertyType.LONG;
	}

	@Override
	public Set<String> getRequiredObjectFieldSettingsNames() {
		return SetUtil.fromArray(
			ObjectFieldSettingConstants.NAME_OBJECT_DEFINITION_1_SHORT_NAME,
			ObjectFieldSettingConstants.
				NAME_OBJECT_RELATIONSHIP_ERC_FIELD_NAME);
	}

	@Override
	public Object getValue(ObjectField objectField, Map<String, Object> values)
		throws PortalException {

		Object value = values.get(objectField.getName());

		if (!Objects.equals(
				objectField.getRelationshipType(),
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY) ||
			(GetterUtil.getLong(value) > 0)) {

			return value;
		}

		String externalReferenceCode = GetterUtil.getString(
			values.get(
				ObjectFieldSettingUtil.getValue(
					ObjectFieldSettingConstants.
						NAME_OBJECT_RELATIONSHIP_ERC_FIELD_NAME,
					objectField)));

		if (Validator.isNull(externalReferenceCode)) {
			return null;
		}

		ObjectRelationship objectRelationship =
			_objectRelationshipPersistence.findByObjectFieldId2(
				objectField.getObjectFieldId());

		ObjectDefinition objectDefinition =
			_objectDefinitionPersistence.findByPrimaryKey(
				objectRelationship.getObjectDefinitionId1());

		if (objectDefinition.isSystem()) {
			SystemObjectDefinitionMetadata systemObjectDefinitionMetadata =
				_systemObjectDefinitionMetadataTracker.
					getSystemObjectDefinitionMetadata(
						objectDefinition.getName());

			BaseModel<?> baseModel =
				systemObjectDefinitionMetadata.
					getBaseModelByExternalReferenceCode(
						externalReferenceCode, objectDefinition.getCompanyId());

			return baseModel.getPrimaryKeyObj();
		}

		// TODO This is a temporary fix
		// We need to do a deeper investigation to understand what is
		// causing the "Lock wait timeout exceeded" error in MySQL when
		// using the _objectEntryPersistence.findByERC_C_ODI directly
		// We also need to avoid using localServiceUtil. We will
		// temporarily use ObjectEntryLocalServiceUtil to avoid
		// circular dependency

		return ObjectEntryLocalServiceUtil.getObjectEntryId(
			externalReferenceCode, objectDefinition.getCompanyId(),
			objectDefinition.getObjectDefinitionId());
	}

	@Override
	public boolean isVisible() {
		return true;
	}

	@Reference
	private Language _language;

	@Reference
	private ObjectDefinitionPersistence _objectDefinitionPersistence;

	@Reference
	private ObjectRelationshipPersistence _objectRelationshipPersistence;

	@Reference
	private SystemObjectDefinitionMetadataTracker
		_systemObjectDefinitionMetadataTracker;

}