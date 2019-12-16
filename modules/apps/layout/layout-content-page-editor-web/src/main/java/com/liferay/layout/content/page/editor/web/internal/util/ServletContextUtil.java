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

package com.liferay.layout.content.page.editor.web.internal.util;

import com.liferay.info.display.url.provider.InfoEditURLProvider;
import com.liferay.info.display.url.provider.InfoEditURLProviderTracker;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import javax.servlet.http.HttpServletRequest;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = {})
public class ServletContextUtil {

	public static boolean contains(
		PermissionChecker permissionChecker, String className, long classPK,
		String actionId) {

		ModelResourcePermission modelResourcePermission =
			_modelResourcePermissionServiceTrackerMap.getService(className);

		if (modelResourcePermission == null) {
			return false;
		}

		try {
			if (modelResourcePermission.contains(
					permissionChecker, classPK, actionId)) {

				return true;
			}
		}
		catch (PortalException pe) {
			_log.error("An error occurred while checking permissions", pe);
		}

		return false;
	}

	public static String getURLEdit(
			String className, Object object,
			HttpServletRequest httpServletRequest)
		throws Exception {

		InfoEditURLProvider infoEditURLProvider =
			_infoEditURLProviderTracker.getInfoEditURLProvider(className);

		if (infoEditURLProvider == null) {
			return null;
		}

		return infoEditURLProvider.getURL(object, httpServletRequest);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_modelResourcePermissionServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, ModelResourcePermission.class,
				"model.class.name");
	}

	@Reference(unbind = "-")
	protected void setInfoEditURLProviderTracker(
		InfoEditURLProviderTracker infoEditURLProviderTracker) {

		_infoEditURLProviderTracker = infoEditURLProviderTracker;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ServletContextUtil.class);

	private static InfoEditURLProviderTracker _infoEditURLProviderTracker;
	private static ServiceTrackerMap<String, ModelResourcePermission>
		_modelResourcePermissionServiceTrackerMap;

}