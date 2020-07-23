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

import com.liferay.commerce.product.catalog.CPCatalogEntry;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Alessio Antonio Rendina
 * @author Alec Sloan
 */
public class CPCompareHelperUtil {

	public static void addCompareProduct(
			long groupId, long commerceAccountId, long cpDefinitionId,
			HttpSession httpSession)
		throws PortalException {

		CPCompareHelper cpCompareHelper = _serviceTracker.getService();

		cpCompareHelper.addCompareProduct(
			groupId, commerceAccountId, cpDefinitionId, httpSession);
	}

	public static List<CPCatalogEntry> getCPCatalogEntries(
			long groupId, long commerceAccountId, HttpSession httpSession)
		throws PortalException {

		CPCompareHelper cpCompareHelper = _serviceTracker.getService();

		return cpCompareHelper.getCPCatalogEntries(
			groupId, commerceAccountId, httpSession);
	}

	public static List<Long> getCPDefinitionIds(
			long groupId, long commerceAccountId, HttpSession httpSession)
		throws PortalException {

		CPCompareHelper cpCompareHelper = _serviceTracker.getService();

		return cpCompareHelper.getCPDefinitionIds(
			groupId, commerceAccountId, httpSession);
	}

	public static void removeCompareProduct(
			long groupId, long commerceAccountId, long cpDefinitionId,
			HttpSession httpSession)
		throws PortalException {

		CPCompareHelper cpCompareHelper = _serviceTracker.getService();

		cpCompareHelper.removeCompareProduct(
			groupId, commerceAccountId, cpDefinitionId, httpSession);
	}

	public static void setCPDefinitionIds(
		long groupId, List<Long> cpDefinitionIds, HttpSession httpSession) {

		CPCompareHelper cpCompareHelper = _serviceTracker.getService();

		cpCompareHelper.setCPDefinitionIds(
			groupId, cpDefinitionIds, httpSession);
	}

	private static final ServiceTracker<?, CPCompareHelper> _serviceTracker =
		ServiceTrackerFactory.open(
			FrameworkUtil.getBundle(CPCompareHelperUtil.class),
			CPCompareHelper.class);

}