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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.GetterUtil;
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

		initResourceURLs();
	}

	@Override
	public String getModuleName() {
		return _moduleName;
	}

	@Override
	public List<URL> getResourceURLs() {
		return _resourceURLs;
	}

	@Override
	public void prepareContext(
		Map<String, Object> context, HttpServletRequest httpServletRequest) {
	}

	protected Bundle getBundle() {
		return FrameworkUtil.getBundle(getClass());
	}

	protected void initModuleName() {
		Bundle bundle = getBundle();

		URL url = bundle.getEntry("package.json");

		if (url == null) {
			_moduleName = StringPool.BLANK;

			return;
		}

		try {
			String json = StringUtil.read(url.openStream());

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(json);

			_moduleName =
				GetterUtil.getString(jsonObject.getString("name")) + "@" +
					GetterUtil.getString(jsonObject.getString("version"));
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected void initResourceURLs() {
		_resourceURLs = new ArrayList<>();

		Bundle bundle = getBundle();

		Enumeration<URL> enumeration = bundle.findEntries(
			"META-INF/resources", "*.es.js", true);

		if (enumeration != null) {
			_resourceURLs.addAll(Collections.list(enumeration));
		}
	}

	private String _moduleName;
	private List<URL> _resourceURLs;

}