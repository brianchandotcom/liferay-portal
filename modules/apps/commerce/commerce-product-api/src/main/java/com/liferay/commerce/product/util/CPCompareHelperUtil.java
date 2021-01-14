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

package com.liferay.commerce.product.util;

import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Alessio Antonio Rendina
 * @author Alec Sloan
 */
public class CPCompareHelperUtil {

	public static List<Long> getCPDefinitionIds(
			long groupId, long commerceAccountId,
			String cpDefinitionIdsCookieValue)
		throws PortalException {

		CPCompareHelper cpCompareHelper = _serviceTracker.getService();

		return cpCompareHelper.getCPDefinitionIds(
			groupId, commerceAccountId, cpDefinitionIdsCookieValue);
	}

	public static String getCPDefinitionIdsCookieKey(
		long commerceChannelGroupId) {

		CPCompareHelper cpCompareHelper = _serviceTracker.getService();

		return cpCompareHelper.getCPDefinitionIdsCookieKey(
			commerceChannelGroupId);
	}

	private static final ServiceTracker<?, CPCompareHelper> _serviceTracker =
		ServiceTrackerFactory.open(
			FrameworkUtil.getBundle(CPCompareHelperUtil.class),
			CPCompareHelper.class);

}