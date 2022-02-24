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

package com.liferay.frontend.js.react.web.internal.importmap;

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
@Component(immediate = true, service = JSImportmapsContributor.class)
public class FrontendJSReactWebJSImportmapsContributor
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
					contextPath, "/__liferay__/exports/", moduleName, ".js"));
		}
	}

	private static final String[] _MODULE_NAMES = {
		"classnames", "formik", "prop-types", "react", "react-dnd",
		"react-dnd-html5-backend", "react-dom"
	};

	private JSONObject _importmapsJSONObject;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.frontend.js.react.web)",
		unbind = "-"
	)
	private ServletContext _servletContext;

}