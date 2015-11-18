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

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.Bundle;

/**
 * @author Ryan Park
 */
public class AppDisplay implements Comparable<AppDisplay> {

	public static final String APP_NAME_UNCATEGORIZED = "Uncategorized";

	public AppDisplay() {
		_name = APP_NAME_UNCATEGORIZED;
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

	public int getState() {
		if (_bundles.isEmpty()) {
			return Bundle.UNINSTALLED;
		}

		int state = Bundle.ACTIVE;

		for (Bundle bundle : _bundles) {
			int bundleState = bundle.getState();

			if (state > bundleState) {
				state = bundleState;
			}
		}

		return state;
	}

	private final List<Bundle> _bundles = new ArrayList<>();
	private final String _name;

}