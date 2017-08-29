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

package com.liferay.frontend.js.top.head.extender.internal;

import com.liferay.frontend.js.top.head.extender.TopHeadResources;

import java.util.Collection;

/**
 * @author Iván Zaera Avellón
 */
public class TopHeadResourcesImpl implements TopHeadResources {

	public TopHeadResourcesImpl(
		Collection<String> bareboneJsResourcePaths,
		Collection<String> jsResourcePaths) {

		_bareboneJsResourcePaths = bareboneJsResourcePaths;
		_jsResourcePaths = jsResourcePaths;
	}

	@Override
	public Collection<String> getBareboneJsResourcePaths() {
		return _bareboneJsResourcePaths;
	}

	@Override
	public Collection<String> getJsResourcePaths() {
		return _jsResourcePaths;
	}

	@Override
	public String getServletContextPath() {
		return _servletContextPath;
	}

	public void setServletContextPath(String servletContextPath) {
		_servletContextPath = servletContextPath;
	}

	private final Collection<String> _bareboneJsResourcePaths;
	private final Collection<String> _jsResourcePaths;
	private String _servletContextPath;

}