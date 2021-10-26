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

package com.liferay.portal.kernel.servlet.taglib.ui;

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alejandro Tardín
 */
public class BreadcrumbEntryContributorUtil {

	public static List<BreadcrumbEntry> contribute(
		List<BreadcrumbEntry> breadcrumbEntries,
		HttpServletRequest httpServletRequest) {

		for (BreadcrumbEntryContributor breadcrumbEntryContributor :
				_breadcrumbEntryContributors) {

			breadcrumbEntries = breadcrumbEntryContributor.getBreadcrumbEntries(
				breadcrumbEntries, httpServletRequest);
		}

		return breadcrumbEntries;
	}

	private static final ServiceTrackerList<BreadcrumbEntryContributor>
		_breadcrumbEntryContributors = ServiceTrackerListFactory.open(
			SystemBundleUtil.getBundleContext(),
			BreadcrumbEntryContributor.class);

}