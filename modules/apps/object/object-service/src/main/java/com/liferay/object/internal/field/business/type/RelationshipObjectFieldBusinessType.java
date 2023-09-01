/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.field.business.type;

import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.dynamic.data.mapping.form.field.type.constants.ObjectDDMFormFieldTypeConstants;
import com.liferay.object.field.business.type.ObjectFieldBusinessType;
import com.liferay.object.field.setting.util.ObjectFieldSettingUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.system.SystemObjectDefinitionManager;
import com.liferay.object.system.SystemObjectDefinitionManagerRegistry;
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
	service = ObjectFieldBusinessType.class
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
	public Set<String> getRequiredObjectFieldSettingsNames(
		ObjectField objectField) {

		return SetUtil.fromArray(
			ObjectFieldSettingConstants.NAME_OBJECT_DEFINITION_1_SHORT_NAME,
			ObjectFieldSettingConstants.
				NAME_OBJECT_RELATIONSHIP_ERC_OBJECT_FIELD_NAME);
	}

	@Override
	public Object getValue(
			ObjectField objectField, long userId, Map<String, Object> values)
		throws PortalException {

		if (!Objects.equals(
				objectField.getRelationshipType(),
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY) ||
			values.containsKey(objectField.getName())) {

			return values.get(objectField.getName());
		}

		String objectRelationshipERCObjectFieldName =
			ObjectFieldSettingUtil.getValue(
				ObjectFieldSettingConstants.
					NAME_OBJECT_RELATIONSHIP_ERC_OBJECT_FIELD_NAME,
				objectField);

		if (!values.containsKey(objectRelationshipERCObjectFieldName)) {
			return null;
		}

		String externalReferenceCode = GetterUtil.getString(
			values.get(objectRelationshipERCObjectFieldName));

		if (Validator.isNull(externalReferenceCode)) {
			return 0;
		}

		ObjectRelationship objectRelationship =
			_objectRelationshipLocalService.
				fetchObjectRelationshipByObjectFieldId2(
					objectField.getObjectFieldId());

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				objectRelationship.getObjectDefinitionId1());

		if (objectDefinition.isUnmodifiableSystemObject()) {
			SystemObjectDefinitionManager systemObjectDefinitionManager =
				_systemObjectDefinitionManagerRegistry.
					getSystemObjectDefinitionManager(
						objectDefinition.getName());

			BaseModel<?> baseModel =
				systemObjectDefinitionManager.
					getBaseModelByExternalReferenceCode(
						externalReferenceCode, objectDefinition.getCompanyId());

			return baseModel.getPrimaryKeyObj();
		}

		ObjectEntry objectEntry = _objectEntryLocalService.getObjectEntry(
			externalReferenceCode, objectDefinition.getObjectDefinitionId());

		return objectEntry.getObjectEntryId();
	}

	@Reference
	private Language _language;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	@Reference
	private SystemObjectDefinitionManagerRegistry
		_systemObjectDefinitionManagerRegistry;

}