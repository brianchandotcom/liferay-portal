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

package com.liferay.object.rest.internal.extension.v1_0;

import com.liferay.object.field.business.type.ObjectFieldBusinessType;
import com.liferay.object.field.business.type.ObjectFieldBusinessTypeTracker;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.vulcan.dto.converter.DTOMapper;
import com.liferay.portal.vulcan.extension.ExtensionProvider;
import com.liferay.portal.vulcan.extension.PropertyDefinition;

import java.io.Serializable;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Correa
 * @author Javier de Arcos
 */
@Component(immediate = true, service = ExtensionProvider.class)
public class ObjectEntryExtensionProvider implements ExtensionProvider {

	@Override
	public Map<String, Serializable> getExtendedProperties(
		long companyId, String className, Object entity) {

		try {
			return _objectEntryLocalService.
				getExtensionDynamicObjectDefinitionTableValues(
					_getObjectDefinition(companyId, className),
					_getPrimaryKey(entity));
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

		ObjectDefinition objectDefinition = _getObjectDefinition(
			companyId, className);

		for (ObjectField objectField :
				_objectFieldLocalService.getObjectFields(
					objectDefinition.getObjectDefinitionId())) {

			if (objectField.isSystem()) {
				continue;
			}

			ObjectFieldBusinessType objectFieldBusinessType =
				_objectFieldBusinessTypeTracker.getObjectFieldBusinessType(
					objectField.getBusinessType());

			extendedPropertyDefinitions.put(
				objectField.getName(),
				new PropertyDefinition(
					null, objectField.getName(),
					objectFieldBusinessType.getPropertyType(),
					objectField.isRequired()));
		}

		return extendedPropertyDefinitions;
	}

	@Override
	public Collection<String> getFilteredPropertyNames(
		long companyId, Object entity) {

		return Collections.emptyList();
	}

	@Override
	public boolean isApplicableExtension(long companyId, String className) {
		ObjectDefinition objectDefinition = _getObjectDefinition(
			companyId, className);

		if ((objectDefinition != null) && objectDefinition.isSystem()) {
			return true;
		}

		return false;
	}

	@Override
	public void setExtendedProperties(
		long companyId, long userId, String className, Object entity,
		Map<String, Serializable> extendedProperties) {

		try {
			_objectEntryLocalService.
				addOrUpdateExtensionDynamicObjectDefinitionTableValues(
					userId, _getObjectDefinition(companyId, className),
					_getPrimaryKey(entity), extendedProperties,
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

	private ObjectDefinition _getObjectDefinition(
		long companyId, String className) {

		String internalDTOClassName = _dtoMapper.getInternalDTOClassNameFrom(
			className);

		if (internalDTOClassName == null) {
			return null;
		}

		try {
			return _objectDefinitionLocalService.
				fetchObjectDefinitionByClassName(
					companyId, internalDTOClassName);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			return null;
		}
	}

	private long _getPrimaryKey(Object entity) throws PortalException {
		JSONObject jsonObject = _jsonFactory.createJSONObject(
			_jsonFactory.looseSerializeDeep(entity));

		return jsonObject.getLong("id");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectEntryExtensionProvider.class);

	@Reference
	private DTOMapper _dtoMapper;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private ObjectFieldBusinessTypeTracker _objectFieldBusinessTypeTracker;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

}