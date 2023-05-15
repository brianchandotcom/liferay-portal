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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Website;

import java.util.List;

/**
 * Provides the remote service utility for Website. This utility wraps
 * <code>com.liferay.portal.service.impl.WebsiteServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see WebsiteService
 * @generated
 */
public class WebsiteServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.service.impl.WebsiteServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static Website addWebsite(
			String className, long classPK, String url, long typeId,
			boolean primary, ServiceContext serviceContext)
		throws PortalException {

		return getService().addWebsite(
			className, classPK, url, typeId, primary, serviceContext);
	}

	public static void deleteWebsite(long websiteId) throws PortalException {
		getService().deleteWebsite(websiteId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static Website getWebsite(long websiteId) throws PortalException {
		return getService().getWebsite(websiteId);
	}

	public static List<Website> getWebsites(String className, long classPK)
		throws PortalException {

		return getService().getWebsites(className, classPK);
	}

	public static Website updateWebsite(
			long websiteId, String url, long typeId, boolean primary)
		throws PortalException {

		return getService().updateWebsite(websiteId, url, typeId, primary);
	}

	public static WebsiteService getService() {
		return _service;
	}

	public static void setService(WebsiteService service) {
		_service = service;
	}

	private static volatile WebsiteService _service;

}