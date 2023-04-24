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

package com.liferay.frontend.taglib.clay.internal.js.importmaps.extender;

import com.liferay.frontend.js.importmaps.extender.JSImportMapsContributor;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 */
@Component(service = JSImportMapsContributor.class)
public class FrontendTaglibClayJSImportMapsContributor
	implements JSImportMapsContributor {

	@Override
	public JSONObject getImportMapsJSONObject() {
		return _importMapsJSONObject;
	}

	@Activate
	protected void activate() throws IOException, JSONException {
		_importMapsJSONObject = _jsonFactory.createJSONObject();

		JSONObject packageJSONObject = _getPackageJSONObject();

		JSONObject dependenciesJSONObject = packageJSONObject.getJSONObject(
			"dependencies");

		for (String moduleName : dependenciesJSONObject.keySet()) {
			if (!moduleName.startsWith("@clayui/")) {
				continue;
			}

			_importMapsJSONObject.put(
				moduleName,
				StringBundler.concat(
					_servletContext.getContextPath(), "/__liferay__/exports/",
					moduleName.replaceAll("\\/", "\\$"), ".js"));
		}
	}

	private JSONObject _getPackageJSONObject()
		throws IOException, JSONException {

		try (InputStream inputStream =
				FrontendTaglibClayJSImportMapsContributor.class.
					getResourceAsStream("dependencies/package.json")) {

			return _jsonFactory.createJSONObject(StringUtil.read(inputStream));
		}
	}

	private JSONObject _importMapsJSONObject;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.frontend.taglib.clay)",
		unbind = "-"
	)
	private ServletContext _servletContext;

}