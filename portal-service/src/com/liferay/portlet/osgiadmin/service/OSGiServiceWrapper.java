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

/**
 * <p>
 * This class is a wrapper for {@link OSGiService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       OSGiService
 * @generated
 */
public class OSGiServiceWrapper implements OSGiService {
	public OSGiServiceWrapper(OSGiService osGiService) {
		_osGiService = osGiService;
	}

	public java.lang.Object addBundle(java.lang.String location)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _osGiService.addBundle(location);
	}

	public java.lang.Object addBundle(java.lang.String location,
		java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _osGiService.addBundle(location, inputStream);
	}

	public java.lang.Object getBundleContext() {
		return _osGiService.getBundleContext();
	}

	public java.lang.Object getFramework() {
		return _osGiService.getFramework();
	}

	public java.lang.String getState(long bundleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _osGiService.getState(bundleId);
	}

	public void setBundleStartLevel(long bundleId, int startLevel)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_osGiService.setBundleStartLevel(bundleId, startLevel);
	}

	public void startBundle(long bundleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_osGiService.startBundle(bundleId);
	}

	public void startBundle(long bundleId, int options)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_osGiService.startBundle(bundleId, options);
	}

	public void stopBundle(long bundleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_osGiService.stopBundle(bundleId);
	}

	public void stopBundle(long bundleId, int options)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_osGiService.stopBundle(bundleId, options);
	}

	public void uninstallBundle(long bundleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_osGiService.uninstallBundle(bundleId);
	}

	public void updateBundle(long bundleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_osGiService.updateBundle(bundleId);
	}

	public void updateBundle(long bundleId, java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_osGiService.updateBundle(bundleId, inputStream);
	}

	public OSGiService getWrappedOSGiService() {
		return _osGiService;
	}

	public void setWrappedOSGiService(OSGiService osGiService) {
		_osGiService = osGiService;
	}

	private OSGiService _osGiService;
}