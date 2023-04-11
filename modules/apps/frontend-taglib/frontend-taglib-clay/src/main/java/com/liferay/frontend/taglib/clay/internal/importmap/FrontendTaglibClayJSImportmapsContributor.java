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

package com.liferay.frontend.taglib.clay.internal.importmap;

import com.liferay.frontend.js.importmaps.extender.JSImportmapsContributor;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 */
@Component(service = JSImportmapsContributor.class)
public class FrontendTaglibClayJSImportmapsContributor
	implements JSImportmapsContributor {

	@Override
	public JSONObject getImportmapsJSONObject() {
		return _importmapsJSONObject;
	}

	@Activate
	protected void activate() {
		_importmapsJSONObject = _jsonFactory.createJSONObject();

		String contextPath = _servletContext.getContextPath();

		for (String moduleName : _MODULE_NAMES) {
			_importmapsJSONObject.put(
				moduleName,
				StringBundler.concat(
					contextPath, "/__liferay__/exports/",
					moduleName.replaceAll("\\/", "\\$"), ".js"));
		}
	}

	private static final String[] _MODULE_NAMES = {
		"@clayui/alert", "@clayui/autocomplete", "@clayui/badge",
		"@clayui/breadcrumb", "@clayui/button", "@clayui/card",
		"@clayui/charts", "@clayui/color-picker", "@clayui/core", "@clayui/css",
		"@clayui/data-provider", "@clayui/date-picker", "@clayui/drop-down",
		"@clayui/empty-state", "@clayui/form", "@clayui/icon", "@clayui/label",
		"@clayui/layout", "@clayui/link", "@clayui/list",
		"@clayui/loading-indicator", "@clayui/localized-input",
		"@clayui/management-toolbar", "@clayui/modal", "@clayui/multi-select",
		"@clayui/multi-step-nav", "@clayui/nav", "@clayui/navigation-bar",
		"@clayui/pagination", "@clayui/pagination-bar", "@clayui/panel",
		"@clayui/popover", "@clayui/progress-bar", "@clayui/shared",
		"@clayui/slider", "@clayui/sticker", "@clayui/table", "@clayui/tabs",
		"@clayui/time-picker", "@clayui/toolbar", "@clayui/tooltip",
		"@clayui/upper-toolbar"
	};

	private JSONObject _importmapsJSONObject;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.frontend.taglib.clay)",
		unbind = "-"
	)
	private ServletContext _servletContext;

}