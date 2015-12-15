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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;

import javax.portlet.MimeResponse;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.framework.Version;

/**
 * @author Ryan Park
 */
public class SimpleAppDisplay extends BaseAppDisplay {

	public SimpleAppDisplay() {
		_title = APP_TITLE_UNCATEGORIZED;
		_description = StringPool.BLANK;
		_version = null;
	}

	public SimpleAppDisplay(String title, String description, Version version) {
		if (Validator.isNull(title)) {
			_title = APP_TITLE_UNCATEGORIZED;
		}
		else {
			_title = title;
		}

		_description = description;
		_version = version;
	}

	public String getDescription() {
		return _description;
	}

	public String getDisplayURL(MimeResponse mimeResponse) {
		PortletURL portletURL = mimeResponse.createRenderURL();

		if (hasModuleGroups()) {
			portletURL.setParameter("mvcPath", "/view_module_groups.jsp");
		}
		else {
			portletURL.setParameter("mvcPath", "/view_modules.jsp");
		}

		portletURL.setParameter("app", _title);

		return portletURL.toString();
	}

	public String getIconURL(HttpServletRequest request) {
		return PortalUtil.getPathContext(request) + "/images/icons.svg#apps";
	}

	public String getTitle() {
		return _title;
	}

	public String getVersion() {
		return _version.toString();
	}

	private final String _description;
	private final String _title;
	private final Version _version;

}