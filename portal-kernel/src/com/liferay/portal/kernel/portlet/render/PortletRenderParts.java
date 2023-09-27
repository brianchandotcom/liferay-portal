/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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