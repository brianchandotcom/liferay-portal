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

import com.liferay.portal.kernel.util.StringPool;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.Version;

/**
 * @author Ryan Park
 */
public class SimpleModuleGroupDisplay implements ModuleGroupDisplay {

	public SimpleModuleGroupDisplay() {
		_title = MODULE_GROUP_TITLE_UNCATEGORIZED;
		_description = StringPool.BLANK;
		_version = null;
	}

	public SimpleModuleGroupDisplay(
		String title, String description, Version version) {

		_title = title;
		_description = description;
		_version = version;
	}

	public void addBundle(Bundle bundle) {
		_bundles.add(bundle);
	}

	@Override
	public int compareTo(ModuleGroupDisplay moduleGroupDisplay) {
		if (moduleGroupDisplay == null) {
			return -1;
		}

		String title = getTitle();

		return title.compareToIgnoreCase(moduleGroupDisplay.getTitle());
	}

	public List<Bundle> getBundles() {
		return _bundles;
	}

	public String getDescription() {
		return _description;
	}

	public int getState() {
		List<Bundle> bundles = getBundles();

		if (bundles.isEmpty()) {
			return Bundle.UNINSTALLED;
		}

		int state = Bundle.ACTIVE;

		for (Bundle bundle : bundles) {
			if (BundleUtil.isFragment(bundle)) {
				continue;
			}

			int bundleState = bundle.getState();

			if (state > bundleState) {
				state = bundleState;
			}
		}

		return state;
	}

	public String getTitle() {
		return _title;
	}

	public String getVersion() {
		return _version.toString();
	}

	private final List<Bundle> _bundles = new ArrayList<>();
	private final String _description;
	private final String _title;
	private final Version _version;

}