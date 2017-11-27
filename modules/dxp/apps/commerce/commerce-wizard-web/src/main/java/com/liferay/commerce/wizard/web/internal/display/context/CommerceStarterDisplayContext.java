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

package com.liferay.commerce.wizard.web.internal.display.context;

import com.liferay.commerce.product.util.CommerceStarter;
import com.liferay.commerce.product.util.CommerceStarterRegistry;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceStarterDisplayContext {

	public CommerceStarterDisplayContext(
		CommerceStarterRegistry commerceStarterRegistry,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		_commerceStarterRegistry = commerceStarterRegistry;
		_httpServletRequest = httpServletRequest;
		_httpServletResponse = httpServletResponse;
	}

	public CommerceStarter getCommerceStarter(String key) {
		return _commerceStarterRegistry.getCommerceStarter(key);
	}

	public List<CommerceStarter> getCommerStarters() {
		return _commerceStarterRegistry.getCommerceStarters(
			true, _httpServletRequest);
	}

	public void renderPreview(CommerceStarter commerceStarter)
		throws Exception {

		commerceStarter.renderPreview(
			_httpServletRequest, _httpServletResponse);
	}

	private final CommerceStarterRegistry _commerceStarterRegistry;
	private final HttpServletRequest _httpServletRequest;
	private final HttpServletResponse _httpServletResponse;

}