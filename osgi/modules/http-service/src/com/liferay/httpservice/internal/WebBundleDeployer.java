/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.httpservice.internal;

import com.liferay.httpservice.internal.servlet.WebExtenderServlet;

import org.osgi.framework.Bundle;

/**
 * @author Raymond Augé
 * @author Miguel Pastor
 */
public class WebBundleDeployer {

	public WebBundleDeployer(WebExtenderServlet webExtenderServlet)
		throws Exception {

		_webExtenderServlet = webExtenderServlet;
	}

	public void close() {
	}

	public void doStart(Bundle bundle, String servletContextName) {
	}

	public void doStop(Bundle bundle, String servletContextName) {
	}

	private WebExtenderServlet _webExtenderServlet;

}