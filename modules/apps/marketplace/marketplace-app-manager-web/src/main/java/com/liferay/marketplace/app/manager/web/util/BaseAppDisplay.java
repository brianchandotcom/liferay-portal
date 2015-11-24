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

import java.util.List;

import org.osgi.framework.Bundle;

/**
 * @author Ryan Park
 */
public abstract class BaseAppDisplay implements AppDisplay {

	@Override
	public int compareTo(AppDisplay appDisplay) {
		if (appDisplay == null) {
			return -1;
		}

		String title = getTitle();

		return title.compareToIgnoreCase(appDisplay.getTitle());
	}

	public int getState() {
		List<Bundle> bundles = getBundles();

		if (bundles.isEmpty()) {
			return Bundle.UNINSTALLED;
		}

		int state = Bundle.ACTIVE;

		for (Bundle bundle : bundles) {
			int bundleState = bundle.getState();

			if (state > bundleState) {
				state = bundleState;
			}
		}

		return state;
	}

}