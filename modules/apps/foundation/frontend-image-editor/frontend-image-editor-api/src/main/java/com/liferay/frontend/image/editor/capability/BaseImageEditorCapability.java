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

package com.liferay.frontend.image.editor.capability;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Bruno Basto
 */
public abstract class BaseImageEditorCapability
	implements ImageEditorCapability {

	public BaseImageEditorCapability() {
		initModuleName();

		initResourceUrls();
	}

	@Override
	public String getModuleName() {
		return _moduleName;
	}

	@Override
	public List<URL> getResourceURLs() {
		return _resourceUrls;
	}

	public void prepareContext(
		Map<String, Object> context, HttpServletRequest request) {
	}

	protected Bundle getBundle() {
		return FrameworkUtil.getBundle(getClass());
	}

	protected void initModuleName() {
		URL url = getBundle().getEntry("package.json");

		if (url == null) {
			_moduleName = StringPool.BLANK;

			return;
		}

		try {
			String json = StringUtil.read(url.openStream());

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(json);

			_moduleName = GetterUtil.getString(
				jsonObject.getString("name"), StringPool.BLANK);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected void initResourceUrls() {
		_resourceUrls = new ArrayList<>();

		Enumeration<URL> urls = getBundle().findEntries(
			"META-INF/resources", _ES_JS_FILE_EXTENSION, true);

		if (urls != null) {
			_resourceUrls.addAll(Collections.list(urls));
		}
	}

	private static final String _ES_JS_FILE_EXTENSION = "*.es.js";

	private String _moduleName;
	private List<URL> _resourceUrls;

}