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

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.framework.Bundle;

/**
 * @author Ryan Park
 */
public class AppDisplay implements Comparable<AppDisplay> {

	public static AppDisplay getAppDisplay(
		List<Bundle> bundles, String appName) {

		AppDisplay appDisplay = new AppDisplay(appName);

		if (appName.equals(_APP_NAME_UNCATEGORIZED)) {
			appName = null;
		}

		for (Bundle bundle : bundles) {
			Dictionary<String, String> headers = bundle.getHeaders();

			String curAppName = headers.get("Liferay-Releng-App-Name");

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
				String curCategory = headers.get("Liferay-Releng-Category");

				if (!category.equals(curCategory)) {
					continue;
				}
			}

			if ((state > 0) && (state != bundle.getState())) {
				continue;
			}

			String appName = headers.get("Liferay-Releng-App-Name");

			if (appName == null) {
				appName = _APP_NAME_UNCATEGORIZED;
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

	public AppDisplay() {
		_name = _APP_NAME_UNCATEGORIZED;
	}

	public AppDisplay(String name) {
		_name = name;
	}

	public void addBundle(Bundle bundle) {
		_bundles.add(bundle);
	}

	@Override
	public int compareTo(AppDisplay appDisplay) {
		if (appDisplay == null) {
			return -1;
		}

		return _name.compareToIgnoreCase(appDisplay.getName());
	}

	public String getName() {
		return _name;
	}

	private static final String _APP_NAME_UNCATEGORIZED = "Uncategorized";

	private final List<Bundle> _bundles = new ArrayList<>();
	private final String _name;

}