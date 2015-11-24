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

import com.liferay.marketplace.model.App;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.Bundle;

/**
 * @author Ryan Park
 */
public class MarketplaceAppDisplay extends BaseAppDisplay {

	public MarketplaceAppDisplay() {
		_app = null;
	}

	public MarketplaceAppDisplay(App app) {
		_app = app;
	}

	public void addBundle(Bundle bundle) {
		_bundles.add(bundle);
	}

	public App getApp() {
		return _app;
	}

	public List<Bundle> getBundles() {
		return _bundles;
	}

	public String getDescription() {
		return _app.getDescription();
	}

	public String getIconURL() {
		return _app.getIconURL();
	}

	public String getTitle() {
		return _app.getTitle();
	}

	public String getVersion() {
		return _app.getVersion();
	}

	private final App _app;
	private final List<Bundle> _bundles = new ArrayList<>();

}