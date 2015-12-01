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
public abstract class BaseAppDisplay implements AppDisplay {

	public void addBundle(Bundle bundle) {
		_moduleGroupDisplays = null;

		_bundles.add(bundle);
	}

	@Override
	public int compareTo(AppDisplay appDisplay) {
		if (appDisplay == null) {
			return -1;
		}

		String title = getTitle();

		return title.compareToIgnoreCase(appDisplay.getTitle());
	}

	public List<Bundle> getBundles() {
		return _bundles;
	}

	public List<ModuleGroupDisplay> getModuleGroupDisplays() {
		if (_moduleGroupDisplays == null) {
			_moduleGroupDisplays =
				ModuleGroupDisplayFactoryUtil.getModuleGroupDisplays(_bundles);
		}

		return _moduleGroupDisplays;
	}

	public int getState() {
		List<Bundle> _bundles = getBundles();

		if (_bundles.isEmpty()) {
			return Bundle.UNINSTALLED;
		}

		int state = Bundle.ACTIVE;

		for (Bundle bundle : _bundles) {
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

	public boolean hasModuleGroups() {
		List<ModuleGroupDisplay> moduleGroupDisplays = getModuleGroupDisplays();

		if (moduleGroupDisplays.isEmpty()) {
			return false;
		}
		else if (moduleGroupDisplays.size() > 1) {
			return true;
		}

		ModuleGroupDisplay moduleGroupDisplay = moduleGroupDisplays.get(0);

		String title = moduleGroupDisplay.getTitle();

		if (title.equals(ModuleGroupDisplay.MODULE_GROUP_TITLE_UNCATEGORIZED)) {
			return false;
		}
		else {
			return true;
		}
	}

	private List<Bundle> _bundles = new ArrayList<>();
	private List<ModuleGroupDisplay> _moduleGroupDisplays;

}