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

package com.liferay.portal.instances.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for PortalInstances. This utility wraps
 * {@link com.liferay.portal.instances.service.impl.PortalInstancesLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Michael C. Han
 * @see PortalInstancesLocalService
 * @see com.liferay.portal.instances.service.base.PortalInstancesLocalServiceBaseImpl
 * @see com.liferay.portal.instances.service.impl.PortalInstancesLocalServiceImpl
 * @generated
 */
@ProviderType
public class PortalInstancesLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.instances.service.impl.PortalInstancesLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static void addCompanyId(long companyId) {
		getService().addCompanyId(companyId);
	}

	public static long getCompanyId(
		javax.servlet.http.HttpServletRequest request) {
		return getService().getCompanyId(request);
	}

	public static long[] getCompanyIds() {
		return getService().getCompanyIds();
	}

	public static long[] getCompanyIdsBySQL() throws java.sql.SQLException {
		return getService().getCompanyIdsBySQL();
	}

	public static long getDefaultCompanyId() {
		return getService().getDefaultCompanyId();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static java.lang.String[] getWebIds() {
		return getService().getWebIds();
	}

	public static void initializePortalInstance(
		javax.servlet.ServletContext servletContext, java.lang.String webId) {
		getService().initializePortalInstance(servletContext, webId);
	}

	public static boolean isAutoLoginIgnoreHost(java.lang.String host) {
		return getService().isAutoLoginIgnoreHost(host);
	}

	public static boolean isAutoLoginIgnorePath(java.lang.String path) {
		return getService().isAutoLoginIgnorePath(path);
	}

	public static boolean isCompanyActive(long companyId) {
		return getService().isCompanyActive(companyId);
	}

	public static boolean isVirtualHostsIgnoreHost(java.lang.String host) {
		return getService().isVirtualHostsIgnoreHost(host);
	}

	public static boolean isVirtualHostsIgnorePath(java.lang.String path) {
		return getService().isVirtualHostsIgnorePath(path);
	}

	public static void reload(javax.servlet.ServletContext servletContext) {
		getService().reload(servletContext);
	}

	public static void removeCompany(long companyId) {
		getService().removeCompany(companyId);
	}

	public static void synchronizePortalInstances() {
		getService().synchronizePortalInstances();
	}

	public static PortalInstancesLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<PortalInstancesLocalService, PortalInstancesLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(PortalInstancesLocalService.class);

		ServiceTracker<PortalInstancesLocalService, PortalInstancesLocalService> serviceTracker =
			new ServiceTracker<PortalInstancesLocalService, PortalInstancesLocalService>(bundle.getBundleContext(),
				PortalInstancesLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}