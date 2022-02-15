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
 * Provides a wrapper for {@link ObjectFieldSettingService}.
 *
 * @author Marco Leo
 * @see ObjectFieldSettingService
 * @generated
 */
public class ObjectFieldSettingServiceWrapper
	implements ObjectFieldSettingService,
			   ServiceWrapper<ObjectFieldSettingService> {

	public ObjectFieldSettingServiceWrapper() {
		this(null);
	}

	public ObjectFieldSettingServiceWrapper(
		ObjectFieldSettingService objectFieldSettingService) {

		_objectFieldSettingService = objectFieldSettingService;
	}

	@Override
	public com.liferay.object.model.ObjectFieldSetting addObjectFieldSetting(
			long objectFieldId, String name, boolean required, String value)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectFieldSettingService.addObjectFieldSetting(
			objectFieldId, name, required, value);
	}

	@Override
	public com.liferay.object.model.ObjectFieldSetting deleteObjectFieldSetting(
			long objectFieldSettingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectFieldSettingService.deleteObjectFieldSetting(
			objectFieldSettingId);
	}

	@Override
	public com.liferay.object.model.ObjectFieldSetting getObjectFieldSetting(
			long objectFieldSettingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectFieldSettingService.getObjectFieldSetting(
			objectFieldSettingId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _objectFieldSettingService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.object.model.ObjectFieldSetting updateObjectFieldSetting(
			long objectFieldSettingId, String value)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectFieldSettingService.updateObjectFieldSetting(
			objectFieldSettingId, value);
	}

	@Override
	public ObjectFieldSettingService getWrappedService() {
		return _objectFieldSettingService;
	}

	@Override
	public void setWrappedService(
		ObjectFieldSettingService objectFieldSettingService) {

		_objectFieldSettingService = objectFieldSettingService;
	}

	private ObjectFieldSettingService _objectFieldSettingService;

}