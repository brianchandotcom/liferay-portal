/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the d l sync local service. This utility wraps {@link com.liferay.portlet.documentlibrary.service.impl.DLSyncLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLSyncLocalService
 * @see com.liferay.portlet.documentlibrary.service.base.DLSyncLocalServiceBaseImpl
 * @see com.liferay.portlet.documentlibrary.service.impl.DLSyncLocalServiceImpl
 * @generated
 */
public class DLSyncLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.documentlibrary.service.impl.DLSyncLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static DLSyncLocalService getService() {
		if (_service == null) {
			_service = (DLSyncLocalService)PortalBeanLocatorUtil.locate(DLSyncLocalService.class.getName());

			ReferenceRegistry.registerReference(DLSyncLocalServiceUtil.class,
				"_service");
			MethodCache.remove(DLSyncLocalService.class);
		}

		return _service;
	}

	public void setService(DLSyncLocalService service) {
		MethodCache.remove(DLSyncLocalService.class);

		_service = service;

		ReferenceRegistry.registerReference(DLSyncLocalServiceUtil.class,
			"_service");
		MethodCache.remove(DLSyncLocalService.class);
	}

	private static DLSyncLocalService _service;
}