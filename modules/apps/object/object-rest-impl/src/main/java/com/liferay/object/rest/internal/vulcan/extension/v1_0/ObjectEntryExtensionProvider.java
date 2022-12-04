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

package com.liferay.object.rest.internal.vulcan.extension.v1_0;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFactory;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.field.business.type.ObjectFieldBusinessType;
import com.liferay.object.field.business.type.ObjectFieldBusinessTypeRegistry;
import com.liferay.object.field.setting.util.ObjectFieldSettingUtil;
import com.liferay.object.field.util.ObjectFieldFormulaEvaluatorUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.rest.internal.util.ObjectEntryValuesUtil;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectFieldSettingLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.vulcan.extension.ExtensionProvider;
import com.liferay.portal.vulcan.extension.PropertyDefinition;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Correa
 * @author Javier de Arcos
 */
@Component(service = ExtensionProvider.class)
public class ObjectEntryExtensionProvider extends BaseObjectExtensionProvider {

	@Override
	public Map<String, Serializable> getExtendedProperties(
		long companyId, String className, Object entity) {

		try {
			ObjectDefinition objectDefinition = getObjectDefinition(
				companyId, className);

			Map<String, Serializable> values =
				_objectEntryLocalService.
					getExtensionDynamicObjectDefinitionTableValues(
						objectDefinition, getPrimaryKey(entity));

			JSONObject jsonObject = jsonFactory.createJSONObject(
				jsonFactory.looseSerializeDeep(entity));

			Map<String, Serializable> variables =
				ObjectFieldFormulaEvaluatorUtil.evaluate(
					_ddmExpressionFactory,
					_objectFieldLocalService.getObjectFields(
						objectDefinition.getObjectDefinitionId()),
					_objectFieldSettingLocalService, _userLocalService,
					new HashMap<>(JSONUtil.toStringMap(jsonObject)));

			for (ObjectField objectField :
					_objectFieldLocalService.getObjectFields(
						objectDefinition.getObjectDefinitionId(), false)) {

				if (objectField.compareBusinessType(
						ObjectFieldConstants.BUSINESS_TYPE_FORMULA)) {

					values.put(
						objectField.getName(),
						variables.get(objectField.getName()));
				}
				else if (Objects.equals(
							objectField.getRelationshipType(),
							ObjectRelationshipConstants.TYPE_ONE_TO_MANY)) {

					values.remove(objectField.getName());
				}
			}

			return values;
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			return Collections.emptyMap();
		}
	}

	@Override
	public Map<String, PropertyDefinition> getExtendedPropertyDefinitions(
		long companyId, String className) {

		Map<String, PropertyDefinition> extendedPropertyDefinitions =
			new HashMap<>();

		ObjectDefinition objectDefinition = getObjectDefinition(
			companyId, className);

		for (ObjectField objectField :
				_objectFieldLocalService.getObjectFields(
					objectDefinition.getObjectDefinitionId(), false)) {

			ObjectFieldBusinessType objectFieldBusinessType =
				_objectFieldBusinessTypeRegistry.getObjectFieldBusinessType(
					objectField.getBusinessType());

			extendedPropertyDefinitions.put(
				objectField.getName(),
				new PropertyDefinition(
					null, objectField.getName(),
					objectFieldBusinessType.getPropertyType(),
					objectField.isRequired()));

			if (Objects.equals(
					objectField.getRelationshipType(),
					ObjectRelationshipConstants.TYPE_ONE_TO_MANY)) {

				String objectRelationshipERCObjectFieldName =
					ObjectFieldSettingUtil.getValue(
						ObjectFieldSettingConstants.
							NAME_OBJECT_RELATIONSHIP_ERC_OBJECT_FIELD_NAME,
						objectField);

				extendedPropertyDefinitions.put(
					objectRelationshipERCObjectFieldName,
					new PropertyDefinition(
						null, objectRelationshipERCObjectFieldName,
						PropertyDefinition.PropertyType.TEXT,
						objectField.isRequired()));
			}
		}

		return extendedPropertyDefinitions;
	}

	@Override
	public void setExtendedProperties(
		long companyId, long userId, String className, Object entity,
		Map<String, Serializable> extendedProperties) {

		try {
			ObjectDefinition objectDefinition = getObjectDefinition(
				companyId, className);

			for (ObjectField objectField :
					_objectFieldLocalService.getObjectFields(
						objectDefinition.getObjectDefinitionId(), false)) {

				Object value = ObjectEntryValuesUtil.getValue(
					objectDefinitionLocalService, _objectEntryLocalService,
					objectField, _objectFieldBusinessTypeRegistry, userId,
					new HashMap<>(extendedProperties));

				if (value == null) {
					continue;
				}

				extendedProperties.put(
					objectField.getName(), (Serializable)value);
			}

			_objectEntryLocalService.
				addOrUpdateExtensionDynamicObjectDefinitionTableValues(
					userId, objectDefinition, getPrimaryKey(entity),
					extendedProperties,
					new ServiceContext() {
						{
							setCompanyId(companyId);
							setUserId(userId);
						}
					});
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectEntryExtensionProvider.class);

	@Reference
	private DDMExpressionFactory _ddmExpressionFactory;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private ObjectFieldBusinessTypeRegistry _objectFieldBusinessTypeRegistry;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private ObjectFieldSettingLocalService _objectFieldSettingLocalService;

	@Reference
	private UserLocalService _userLocalService;

}