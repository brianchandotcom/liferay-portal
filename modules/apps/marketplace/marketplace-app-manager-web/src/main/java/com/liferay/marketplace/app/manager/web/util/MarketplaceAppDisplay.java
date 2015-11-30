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

import javax.portlet.MimeResponse;
import javax.portlet.PortletURL;

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

	public App getApp() {
		return _app;
	}

	public String getDescription() {
		return _app.getDescription();
	}

	public String getDisplayURL(MimeResponse mimeResponse) {
		PortletURL portletURL = mimeResponse.createRenderURL();

		if (hasModuleGroups()) {
			portletURL.setParameter("mvcPath", "view_module_groups.jsp");
		}
		else {
			portletURL.setParameter("mvcPath", "view_modules.jsp");
		}

		portletURL.setParameter("app", String.valueOf(_app.getAppId()));

		return portletURL.toString();
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

}