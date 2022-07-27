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

package com.liferay.portal.kernel.portlet.render;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Iván Zaera Avellón
 */
public class PortletRenderParts {

	public PortletRenderParts(
		Collection<String> footerCssPaths,
		Collection<String> footerJavaScriptPaths,
		Collection<String> headerCssPaths,
		Collection<String> headerJavaScriptPaths, String portletHTML,
		boolean refresh) {

		_footerCssPaths = new ArrayList<>(footerCssPaths);
		_footerJavaScriptPaths = new ArrayList<>(footerJavaScriptPaths);
		_headerCssPaths = new ArrayList<>(headerCssPaths);
		_headerJavaScriptPaths = new ArrayList<>(headerJavaScriptPaths);
		_portletHTML = portletHTML;
		_refresh = refresh;
	}

	public Collection<String> getFooterCssPaths() {
		return _footerCssPaths;
	}

	public Collection<String> getFooterJavaScriptPaths() {
		return _footerJavaScriptPaths;
	}

	public Collection<String> getHeaderCssPaths() {
		return _headerCssPaths;
	}

	public Collection<String> getHeaderJavaScriptPaths() {
		return _headerJavaScriptPaths;
	}

	public String getPortletHTML() {
		return _portletHTML;
	}

	public boolean isRefresh() {
		return _refresh;
	}

	private final Collection<String> _footerCssPaths;
	private final Collection<String> _footerJavaScriptPaths;
	private final Collection<String> _headerCssPaths;
	private final Collection<String> _headerJavaScriptPaths;
	private final String _portletHTML;
	private final boolean _refresh;

}