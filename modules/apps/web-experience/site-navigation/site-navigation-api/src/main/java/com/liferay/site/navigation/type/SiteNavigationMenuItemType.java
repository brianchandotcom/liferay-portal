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

package com.liferay.site.navigation.type;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;

import java.net.URL;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Pavel Savinov
 */
public interface SiteNavigationMenuItemType {

	public JSONObject getEditContext(
			HttpServletRequest request, HttpServletResponse response,
			SiteNavigationMenuItem siteNavigationMenuItem)
		throws Exception;

	public default String getIcon() {
		return "magic";
	}

	public String getLabel(Locale locale);

	public default String getModuleName() {
		Bundle bundle = FrameworkUtil.getBundle(getClass());

		URL url = bundle.getEntry("package.json");

		if (url == null) {
			return StringPool.BLANK;
		}

		try {
			String json = StringUtil.read(url.openStream());

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(json);

			return GetterUtil.getString(jsonObject.getString("name"));
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public default List<URL> getResourceURLs() {
		Bundle bundle = FrameworkUtil.getBundle(getClass());

		Enumeration<URL> enumeration = bundle.findEntries(
			"META-INF/resources", "*.es.js", true);

		if (enumeration != null) {
			return Collections.list(enumeration);
		}

		return Collections.emptyList();
	}

	public default String getType() {
		return StringPool.BLANK;
	}

	public JSONObject getViewContext(
			HttpServletRequest request, HttpServletResponse response,
			SiteNavigationMenuItem siteNavigationMenuItem)
		throws Exception;

}