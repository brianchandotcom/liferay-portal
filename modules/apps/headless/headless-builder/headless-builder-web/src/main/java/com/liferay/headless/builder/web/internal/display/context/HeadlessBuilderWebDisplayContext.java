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

	public String getAPIURL() {
		return "/o/headless/v1.0/placeholder";
	}

	public String getPortletId() {
		return _headlessBuilderWebRequestHelper.getPortletId();
	}

	private final HeadlessBuilderWebRequestHelper
		_headlessBuilderWebRequestHelper;

}