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

package com.liferay.portal.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the remote service utility for Phone. This utility wraps
 * {@link com.liferay.portal.service.impl.PhoneServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see PhoneService
 * @see com.liferay.portal.service.base.PhoneServiceBaseImpl
 * @see com.liferay.portal.service.impl.PhoneServiceImpl
 * @generated
 */
@ProviderType
public class PhoneServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.PhoneServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* @deprecated As of 6.2.0, replaced by {@link #addPhone(String, long,
	String, String, int, boolean, ServiceContext)}
	*/
	@Deprecated
	public static com.liferay.portal.model.Phone addPhone(
		java.lang.String className, long classPK, java.lang.String number,
		java.lang.String extension, long typeId, boolean primary)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addPhone(className, classPK, number, extension, typeId,
			primary);
	}

	public static com.liferay.portal.model.Phone addPhone(
		java.lang.String className, long classPK, java.lang.String number,
		java.lang.String extension, long typeId, boolean primary,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addPhone(className, classPK, number, extension, typeId,
			primary, serviceContext);
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
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.model.Phone getPhone(long phoneId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPhone(phoneId);
	}

	public static java.util.List<com.liferay.portal.model.Phone> getPhones(
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPhones(className, classPK);
	}

	public static com.liferay.portal.model.Phone updatePhone(long phoneId,
		java.lang.String number, java.lang.String extension, long typeId,
		boolean primary)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updatePhone(phoneId, number, extension, typeId, primary);
	}

	public static PhoneService getService() {
		if (_service == null) {
			_service = (PhoneService)PortalBeanLocatorUtil.locate(PhoneService.class.getName());

			ReferenceRegistry.registerReference(PhoneServiceUtil.class,
				"_service");
		}

		return _service;
	}

	private static PhoneService _service;
}