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

package com.liferay.portlet.osgiadmin.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the o s gi remote service. This utility wraps {@link com.liferay.portlet.osgiadmin.service.impl.OSGiServiceImpl} and is the primary access point for service operations in application layer code running on a remote server.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see OSGiService
 * @see com.liferay.portlet.osgiadmin.service.base.OSGiServiceBaseImpl
 * @see com.liferay.portlet.osgiadmin.service.impl.OSGiServiceImpl
 * @generated
 */
public class OSGiServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.osgiadmin.service.impl.OSGiServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static java.lang.Object addBundle(java.lang.String location)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().addBundle(location);
	}

	public static java.lang.Object addBundle(java.lang.String location,
		java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().addBundle(location, inputStream);
	}

	public static java.lang.Object getBundleContext() {
		return getService().getBundleContext();
	}

	public static java.lang.Object getFramework() {
		return getService().getFramework();
	}

	public static java.lang.String getState(long bundleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getState(bundleId);
	}

	public static void startBundle(long bundleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().startBundle(bundleId);
	}

	public static void startBundle(long bundleId, int options)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().startBundle(bundleId, options);
	}

	public static void stopBundle(long bundleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().stopBundle(bundleId);
	}

	public static void stopBundle(long bundleId, int options)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().stopBundle(bundleId, options);
	}

	public static void uninstallBundle(long bundleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().uninstallBundle(bundleId);
	}

	public static void updateBundle(long bundleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().updateBundle(bundleId);
	}

	public static void updateBundle(long bundleId,
		java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().updateBundle(bundleId, inputStream);
	}

	public static OSGiService getService() {
		if (_service == null) {
			_service = (OSGiService)PortalBeanLocatorUtil.locate(OSGiService.class.getName());

			ReferenceRegistry.registerReference(OSGiServiceUtil.class,
				"_service");
			MethodCache.remove(OSGiService.class);
		}

		return _service;
	}

	public void setService(OSGiService service) {
		MethodCache.remove(OSGiService.class);

		_service = service;

		ReferenceRegistry.registerReference(OSGiServiceUtil.class, "_service");
		MethodCache.remove(OSGiService.class);
	}

	private static OSGiService _service;
}