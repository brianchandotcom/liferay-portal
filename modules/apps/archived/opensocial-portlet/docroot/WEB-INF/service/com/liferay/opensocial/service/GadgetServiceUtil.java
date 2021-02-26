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

package com.liferay.opensocial.service;

import com.liferay.opensocial.model.Gadget;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * Provides the remote service utility for Gadget. This utility wraps
 * <code>com.liferay.opensocial.service.impl.GadgetServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see GadgetService
 * @generated
 */
public class GadgetServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.opensocial.service.impl.GadgetServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static Gadget addGadget(
			long companyId, String url, String portletCategoryNames,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addGadget(
			companyId, url, portletCategoryNames, serviceContext);
	}

	public static void deleteGadget(
			long gadgetId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		getService().deleteGadget(gadgetId, serviceContext);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static void updateGadget(
			long gadgetId, String portletCategoryNames,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		getService().updateGadget(
			gadgetId, portletCategoryNames, serviceContext);
	}

	public static void clearService() {
		_service = null;
	}

	public static GadgetService getService() {
		return _service;
	}

	private static volatile GadgetService _service;

}