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

package com.liferay.portlet;

import com.liferay.portal.kernel.portlet.Route;
import com.liferay.portal.kernel.portlet.Router;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Connor McKay
 * @author Brian Wing Shun Chan
 */
public class RouterImpl implements Router {

	@Override
	public Route addRoute(String pattern) {
		Route route = new RouteImpl(pattern);

		_routes.add(route);

		return route;
	}

	@Override
	public String parametersToUrl(Map<String, String> parameters) {
		for (Route route : _routes) {
			String url = route.parametersToUrl(parameters);

			if (url != null) {
				return url;
			}
		}

		return null;
	}

	@Override
	public boolean urlToParameters(String url, Map<String, String> parameters) {
		for (Route route : _routes) {
			if (route.urlToParameters(url, parameters)) {
				return true;
			}
		}

		return false;
	}

	private final List<Route> _routes = new ArrayList<Route>();

}