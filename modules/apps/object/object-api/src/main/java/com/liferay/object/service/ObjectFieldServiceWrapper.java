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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ObjectFieldService}.
 *
 * @author Marco Leo
 * @see ObjectFieldService
 * @generated
 */
public class ObjectFieldServiceWrapper
	implements ObjectFieldService, ServiceWrapper<ObjectFieldService> {

	public ObjectFieldServiceWrapper() {
		this(null);
	}

	public ObjectFieldServiceWrapper(ObjectFieldService objectFieldService) {
		_objectFieldService = objectFieldService;
	}

	@Override
	public com.liferay.object.model.ObjectField addCustomObjectField(
			long listTypeDefinitionId, long objectDefinitionId,
			String businessType, String dbType, boolean indexed,
			boolean indexedAsKeyword, String indexedLanguageId,
			java.util.Map<java.util.Locale, String> labelMap, String name,
			boolean required)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectFieldService.addCustomObjectField(
			listTypeDefinitionId, objectDefinitionId, businessType, dbType,
			indexed, indexedAsKeyword, indexedLanguageId, labelMap, name,
			required);
	}

	@Override
	public com.liferay.object.model.ObjectField deleteObjectField(
			long objectFieldId)
		throws Exception {

		return _objectFieldService.deleteObjectField(objectFieldId);
	}

	@Override
	public com.liferay.object.model.ObjectField getObjectField(
			long objectFieldId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectFieldService.getObjectField(objectFieldId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _objectFieldService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.object.model.ObjectField updateCustomObjectField(
			long objectFieldId, long listTypeDefinitionId, String businessType,
			String dbType, boolean indexed, boolean indexedAsKeyword,
			String indexedLanguageId,
			java.util.Map<java.util.Locale, String> labelMap, String name,
			boolean required)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectFieldService.updateCustomObjectField(
			objectFieldId, listTypeDefinitionId, businessType, dbType, indexed,
			indexedAsKeyword, indexedLanguageId, labelMap, name, required);
	}

	@Override
	public ObjectFieldService getWrappedService() {
		return _objectFieldService;
	}

	@Override
	public void setWrappedService(ObjectFieldService objectFieldService) {
		_objectFieldService = objectFieldService;
	}

	private ObjectFieldService _objectFieldService;

}