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

package com.liferay.object.service;

import com.liferay.object.model.ObjectField;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;
import java.util.Map;

/**
 * Provides the remote service utility for ObjectField. This utility wraps
 * <code>com.liferay.object.service.impl.ObjectFieldServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see ObjectFieldService
 * @generated
 */
public class ObjectFieldServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.object.service.impl.ObjectFieldServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static ObjectField addCustomObjectField(
			long listTypeDefinitionId, long objectDefinitionId,
			String businessType, String dbType, String defaultValue,
			boolean indexed, boolean indexedAsKeyword, String indexedLanguageId,
			Map<java.util.Locale, String> labelMap, String name,
			boolean required, boolean state,
			List<com.liferay.object.model.ObjectFieldSetting>
				objectFieldSettings)
		throws PortalException {

		return getService().addCustomObjectField(
			listTypeDefinitionId, objectDefinitionId, businessType, dbType,
			defaultValue, indexed, indexedAsKeyword, indexedLanguageId,
			labelMap, name, required, state, objectFieldSettings);
	}

	public static ObjectField deleteObjectField(long objectFieldId)
		throws Exception {

		return getService().deleteObjectField(objectFieldId);
	}

	public static ObjectField getObjectField(long objectFieldId)
		throws PortalException {

		return getService().getObjectField(objectFieldId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static ObjectField updateObjectField(
			long objectFieldId, String externalReferenceCode,
			long listTypeDefinitionId, String businessType, String dbType,
			String defaultValue, boolean indexed, boolean indexedAsKeyword,
			String indexedLanguageId, Map<java.util.Locale, String> labelMap,
			String name, boolean required, boolean state,
			List<com.liferay.object.model.ObjectFieldSetting>
				objectFieldSettings,
			com.liferay.object.model.ObjectStateFlow objectStateFlow)
		throws PortalException {

		return getService().updateObjectField(
			objectFieldId, externalReferenceCode, listTypeDefinitionId,
			businessType, dbType, defaultValue, indexed, indexedAsKeyword,
			indexedLanguageId, labelMap, name, required, state,
			objectFieldSettings, objectStateFlow);
	}

	public static ObjectFieldService getService() {
		return _service;
	}

	private static volatile ObjectFieldService _service;

}