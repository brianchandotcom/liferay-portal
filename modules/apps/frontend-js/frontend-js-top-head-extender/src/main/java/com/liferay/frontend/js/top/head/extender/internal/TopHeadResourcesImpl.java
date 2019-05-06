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
		String servletContextPath, Collection<String> jsResourcePaths,
		Collection<String> authenticatedJsResourcePaths) {

		_servletContextPath = servletContextPath;
		_jsResourcePaths = jsResourcePaths;
		_authenticatedJsResourcePaths = authenticatedJsResourcePaths;
	}

	@Override
	public Collection<String> getAuthenticatedJsResourcePaths() {
		return _authenticatedJsResourcePaths;
	}

	@Override
	public Collection<String> getJsResourcePaths() {
		return _jsResourcePaths;
	}

	@Override
	public String getServletContextPath() {
		return _servletContextPath;
	}

	private final Collection<String> _authenticatedJsResourcePaths;
	private final Collection<String> _jsResourcePaths;
	private final String _servletContextPath;

}