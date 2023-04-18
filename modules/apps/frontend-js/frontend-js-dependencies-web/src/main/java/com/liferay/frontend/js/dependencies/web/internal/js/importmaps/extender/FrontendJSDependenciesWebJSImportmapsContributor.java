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

package com.liferay.frontend.js.dependencies.web.internal.js.importmaps.extender;

import com.liferay.frontend.js.importmaps.extender.JSImportmapsContributor;
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
public class FrontendJSDependenciesWebJSImportmapsContributor
	implements JSImportmapsContributor {

	@Override
	public JSONObject getImportmapsJSONObject() {
		return _importmapsJSONObject;
	}

	@Activate
	protected void activate() {
		_importmapsJSONObject = _jsonFactory.createJSONObject();

		_importmapsJSONObject.put(
			"@liferay/frontend-js-api",
			_servletContext.getContextPath() +
				"/__liferay__/exports/@liferay$js-api.js"
		).put(
			"@liferay/frontend-js-api/data-set",
			_servletContext.getContextPath() +
				"/__liferay__/exports/@liferay$js-api$data-set.js"
		);
	}

	private JSONObject _importmapsJSONObject;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.frontend.js.dependencies.web)",
		unbind = "-"
	)
	private ServletContext _servletContext;

}