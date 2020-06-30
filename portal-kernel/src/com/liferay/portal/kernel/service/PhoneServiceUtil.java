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

package com.liferay.portal.kernel.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * Provides the remote service utility for Phone. This utility wraps
 * <code>com.liferay.portal.service.impl.PhoneServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see PhoneService
 * @generated
 */
public class PhoneServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.service.impl.PhoneServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portal.kernel.model.Phone addPhone(
			String className, long classPK, String number, String extension,
			long typeId, boolean primary, ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addPhone(
			className, classPK, number, extension, typeId, primary,
			serviceContext);
	}

	public static void deletePhone(long phoneId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deletePhone(phoneId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.Phone getPhone(long phoneId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPhone(phoneId);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Phone>
			getPhones(String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPhones(className, classPK);
	}

	public static com.liferay.portal.kernel.model.Phone updatePhone(
			long phoneId, String number, String extension, long typeId,
			boolean primary)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updatePhone(
			phoneId, number, extension, typeId, primary);
	}

	public static PhoneService getService() {
		if (_service == null) {
			_service = (PhoneService)PortalBeanLocatorUtil.locate(
				PhoneService.class.getName());
		}

		return _service;
	}

	private static PhoneService _service;

}