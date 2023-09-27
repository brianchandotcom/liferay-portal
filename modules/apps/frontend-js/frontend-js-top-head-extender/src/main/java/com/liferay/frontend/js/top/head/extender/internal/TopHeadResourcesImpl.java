/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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