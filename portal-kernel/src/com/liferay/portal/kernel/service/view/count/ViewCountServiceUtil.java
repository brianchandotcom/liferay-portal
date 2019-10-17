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

package com.liferay.portal.kernel.service.view.count;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

/**
 * @author Preston Crary
 */
public class ViewCountServiceUtil {

	public static long getViewCount(
		long companyId, long classNameId, long classPK) {

		return _viewCountService.getViewCount(companyId, classNameId, classPK);
	}

	public static void incrementViewCount(
		long companyId, long classNameId, long classPK) {

		_viewCountService.incrementViewCount(companyId, classNameId, classPK);
	}

	public static void incrementViewCount(
		long companyId, long classNameId, long classPK, int increment) {

		_viewCountService.incrementViewCount(
			companyId, classNameId, classPK, increment);
	}

	public static void removeViewCount(
			long companyId, long classNameId, long classPK)
		throws PortalException {

		_viewCountService.removeViewCount(companyId, classNameId, classPK);
	}

	private static volatile ViewCountService _viewCountService =
		ServiceProxyFactory.newServiceTrackedInstance(
			ViewCountService.class, ViewCountServiceUtil.class,
			"_viewCountService", true);

}