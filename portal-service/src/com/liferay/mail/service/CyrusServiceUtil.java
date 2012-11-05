/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.mail.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @author Alexander Chow
 */
public class CyrusServiceUtil {

	public static void addUser(
			long userId, String emailAddress, String password)
		throws SystemException {

		getService().addUser(userId, emailAddress, password);
	}

	public static void deleteEmailAddress(long companyId, long userId)
		throws SystemException {

		getService().deleteEmailAddress(companyId, userId);
	}

	public static void deleteUser(long userId) throws SystemException {
		getService().deleteUser(userId);
	}

	public static CyrusService getService() {
		if (_service == null) {
			_service = (CyrusService)PortalBeanLocatorUtil.locate(
				CyrusService.class.getName());

			ReferenceRegistry.registerReference(
				CyrusServiceUtil.class, "_service");
		}

		return _service;
	}

	public static void updateEmailAddress(
			long companyId, long userId, String emailAddress)
		throws SystemException {

		getService().updateEmailAddress(companyId, userId, emailAddress);
	}

	public static void updatePassword(
			long companyId, long userId, String password)
		throws SystemException {

		getService().updatePassword(companyId, userId, password);
	}

	public void setService(CyrusService service) {
		_service = service;

		ReferenceRegistry.registerReference(CyrusServiceUtil.class, "_service");
	}

	private static CyrusService _service;

}