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

import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * Provides the remote service utility for ObjectFieldSetting. This utility wraps
 * <code>com.liferay.object.service.impl.ObjectFieldSettingServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see ObjectFieldSettingService
 * @generated
 */
public class ObjectFieldSettingServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.object.service.impl.ObjectFieldSettingServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static ObjectFieldSetting addObjectFieldSetting(
			long objectFieldId, String name, boolean required, String value)
		throws PortalException {

		return getService().addObjectFieldSetting(
			objectFieldId, name, required, value);
	}

	public static ObjectFieldSetting deleteObjectFieldSetting(
			long objectFieldSettingId)
		throws PortalException {

		return getService().deleteObjectFieldSetting(objectFieldSettingId);
	}

	public static ObjectFieldSetting getObjectFieldSetting(
			long objectFieldSettingId)
		throws PortalException {

		return getService().getObjectFieldSetting(objectFieldSettingId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static ObjectFieldSetting updateObjectFieldSetting(
			long objectFieldSettingId, String value)
		throws PortalException {

		return getService().updateObjectFieldSetting(
			objectFieldSettingId, value);
	}

	public static ObjectFieldSettingService getService() {
		return _service;
	}

	private static volatile ObjectFieldSettingService _service;

}