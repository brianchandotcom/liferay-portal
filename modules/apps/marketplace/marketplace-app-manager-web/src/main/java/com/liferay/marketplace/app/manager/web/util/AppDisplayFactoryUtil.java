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

package com.liferay.marketplace.app.manager.web.util;

import com.liferay.marketplace.app.manager.web.constants.BundleConstants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.framework.Bundle;

/**
 * @author Ryan Park
 */
public class AppDisplayFactoryUtil {

	public static AppDisplay getAppDisplay(
		List<Bundle> bundles, String appName) {

		AppDisplay appDisplay = new AppDisplay(appName);

		if (appName.equals(AppDisplay.APP_NAME_UNCATEGORIZED)) {
			appName = null;
		}

		for (Bundle bundle : bundles) {
			Dictionary<String, String> headers = bundle.getHeaders();

			String curAppName = headers.get(
				BundleConstants.LIFERAY_RELENG_APP_NAME);

			if (Validator.isNotNull(appName) && !appName.equals(curAppName)) {
				continue;
			}
			else if (curAppName != null) {
				continue;
			}

			appDisplay.addBundle(bundle);
		}

		return appDisplay;
	}

	public static List<AppDisplay> getAppDisplays(
		List<Bundle> bundles, String category, int state) {

		Map<String, AppDisplay> appDisplaysMap = new HashMap<>();

		for (Bundle bundle : bundles) {
			Dictionary<String, String> headers = bundle.getHeaders();

			if (Validator.isNotNull(category)) {
				String curCategory = headers.get(
					BundleConstants.LIFERAY_RELENG_CATEGORY);

				if (!category.equals(curCategory)) {
					continue;
				}
			}

			if ((state > 0) && (state != bundle.getState())) {
				continue;
			}

			String appName = headers.get(
				BundleConstants.LIFERAY_RELENG_APP_NAME);

			if (appName == null) {
				appName = AppDisplay.APP_NAME_UNCATEGORIZED;
			}

			AppDisplay appDisplay = appDisplaysMap.get(appName);

			if (appDisplay == null) {
				appDisplay = new AppDisplay(appName);

				appDisplaysMap.put(appName, appDisplay);
			}

			appDisplay.addBundle(bundle);
		}

		return ListUtil.fromMapValues(appDisplaysMap);
	}

}