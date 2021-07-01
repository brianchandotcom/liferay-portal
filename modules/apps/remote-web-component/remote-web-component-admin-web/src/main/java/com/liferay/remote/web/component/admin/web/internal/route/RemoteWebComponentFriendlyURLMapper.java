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

package com.liferay.remote.web.component.admin.web.internal.route;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.portlet.DefaultFriendlyURLMapper;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.Route;
import com.liferay.portal.kernel.portlet.Router;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.remote.web.component.admin.web.internal.configuration.RemoteWebComponentConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.WindowState;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Raymond Augé
 */
@Component(
	configurationPid = "com.liferay.remote.web.component.admin.web.internal.configuration.RemoteWebComponentConfiguration",
	factory = "remote.web.component.friendly.url.mapper",
	service = FriendlyURLMapper.class
)
public class RemoteWebComponentFriendlyURLMapper
	extends DefaultFriendlyURLMapper {

	@Override
	public String buildPath(LiferayPortletURL liferayPortletURL) {
		Map<String, String> routeParameters = new HashMap<>();

		buildRouteParameters(liferayPortletURL, routeParameters);

		String friendlyURLPath = router.parametersToUrl(routeParameters);

		if (friendlyURLPath == null) {
			return null;
		}

		addParametersIncludedInPath(liferayPortletURL, routeParameters);

		StringBuilder sb = new StringBuilder(StringPool.SLASH);

		sb.append(getMapping());
		sb.append(friendlyURLPath);

		return sb.toString();
	}

	@Override
	public String getMapping() {
		return _mapping;
	}

	@Override
	public void setRouter(Router router) {

		// make sure we don't get reset

	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_remoteWebComponentConfiguration = ConfigurableUtil.createConfigurable(
			RemoteWebComponentConfiguration.class, properties);

		if (Validator.isNotNull(
				_remoteWebComponentConfiguration.portletAlias())) {

			_mapping = _remoteWebComponentConfiguration.portletAlias();
		}
		else {
			_mapping = _remoteWebComponentConfiguration.elementName();
		}

		boolean instanceable = _remoteWebComponentConfiguration.instanceable();

		Router router = new RouterImpl();

		List<Route> list = router.getRoutes();

		// Render URL with any state and a path

		String baseRoute =
			(instanceable ? "/{instanceId}" : "") + "/s/{p_p_state}/{%path:.*}";

		if (list.stream(
			).map(
				Route::getPattern
			).noneMatch(
				baseRoute::equals
			)) {

			Route route = router.addRoute(baseRoute);

			route.addImplicitParameter("p_p_lifecycle", "0");
		}

		// Render URL with any state

		baseRoute = (instanceable ? "/{instanceId}" : "") + "/s/{p_p_state}";

		if (list.stream(
			).map(
				Route::getPattern
			).noneMatch(
				baseRoute::equals
			)) {

			Route route = router.addRoute(baseRoute);

			route.addImplicitParameter("p_p_lifecycle", "0");
		}

		// Simple render URL with normal state and path

		baseRoute = instanceable ? "/{instanceId}" : "/{%path:.*}";

		if (list.stream(
			).map(
				Route::getPattern
			).noneMatch(
				baseRoute::equals
			)) {

			Route route = router.addRoute(baseRoute);

			route.addImplicitParameter("p_p_lifecycle", "0");
			route.addImplicitParameter(
				"p_p_state", WindowState.NORMAL.toString());
		}

		// Simple render URL with normal state

		baseRoute = instanceable ? "/{instanceId}" : "";

		if (list.stream(
			).map(
				Route::getPattern
			).noneMatch(
				baseRoute::equals
			)) {

			Route route = router.addRoute(baseRoute);

			route.addImplicitParameter("p_p_lifecycle", "0");
			route.addImplicitParameter(
				"p_p_state", WindowState.NORMAL.toString());
		}

		super.router = router;
	}

	private volatile String _mapping;
	private RemoteWebComponentConfiguration _remoteWebComponentConfiguration;

}