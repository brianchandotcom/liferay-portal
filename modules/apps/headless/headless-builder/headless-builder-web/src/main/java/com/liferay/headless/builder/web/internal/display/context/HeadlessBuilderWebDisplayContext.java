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

package com.liferay.headless.builder.web.internal.display.context;

import com.liferay.headless.builder.web.internal.display.context.helper.HeadlessBuilderWebRequestHelper;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Carlos Montenegro
 */
public class HeadlessBuilderWebDisplayContext {

	public HeadlessBuilderWebDisplayContext(
		HttpServletRequest httpServletRequest) {

		_headlessBuilderWebRequestHelper = new HeadlessBuilderWebRequestHelper(
			httpServletRequest);
	}

	public HashMap<String, String> getAPIURLPaths() {
		return HashMapBuilder.put(
			"applications", "/o/headless-builder/applications"
		).put(
			"endpoints", "/o/headless-builder/endpoints"
		).put(
			"filters", "/o/headless-builder/filters"
		).put(
			"properties", "/o/headless-builder/properties"
		).put(
			"schemas", "/o/headless-builder/schemas"
		).put(
			"sorts", "/o/headless-builder/sorts"
		).build();
	}

	public String getPortletId() {
		return _headlessBuilderWebRequestHelper.getPortletId();
	}

	private final HeadlessBuilderWebRequestHelper
		_headlessBuilderWebRequestHelper;

}