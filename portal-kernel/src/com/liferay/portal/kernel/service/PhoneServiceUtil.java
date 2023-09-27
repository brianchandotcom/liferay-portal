/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Phone;

import java.util.List;

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
	public static Phone addPhone(
			String className, long classPK, String number, String extension,
			long typeId, boolean primary, ServiceContext serviceContext)
		throws PortalException {

		return getService().addPhone(
			className, classPK, number, extension, typeId, primary,
			serviceContext);
	}

	public static void deletePhone(long phoneId) throws PortalException {
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

	public static Phone getPhone(long phoneId) throws PortalException {
		return getService().getPhone(phoneId);
	}

	public static List<Phone> getPhones(String className, long classPK)
		throws PortalException {

		return getService().getPhones(className, classPK);
	}

	public static Phone updatePhone(
			long phoneId, String number, String extension, long typeId,
			boolean primary)
		throws PortalException {

		return getService().updatePhone(
			phoneId, number, extension, typeId, primary);
	}

	public static PhoneService getService() {
		return _service;
	}

	public static void setService(PhoneService service) {
		_service = service;
	}

	private static volatile PhoneService _service;

}