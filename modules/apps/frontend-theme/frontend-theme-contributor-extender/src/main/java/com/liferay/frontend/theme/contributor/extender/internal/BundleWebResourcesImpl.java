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

package com.liferay.frontend.theme.contributor.extender.internal;

import com.liferay.frontend.theme.contributor.extender.BundleWebResources;

import java.util.Collection;

/**
 * @author Carlos Sierra Andrés
 */
public class BundleWebResourcesImpl implements BundleWebResources {

	public BundleWebResourcesImpl(
		String servletContextPath, Collection<String> cssResourcePaths,
		Collection<String> jsResourcePaths) {

		_servletContextPath = servletContextPath;
		_cssResourcePaths = cssResourcePaths;
		_jsResourcePaths = jsResourcePaths;
	}

	@Override
	public Collection<String> getCssResourcePaths() {
		return _cssResourcePaths;
	}

	@Override
	public Collection<String> getJsResourcePaths() {
		return _jsResourcePaths;
	}

	@Override
	public String getServletContextPath() {
		return _servletContextPath;
	}

	private final Collection<String> _cssResourcePaths;
	private final Collection<String> _jsResourcePaths;
	private final String _servletContextPath;

}