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

package com.liferay.wiki.web;

import com.liferay.portal.kernel.portlet.DefaultFriendlyURLMapper;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.wiki.util.WikiServiceUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Shinn Lok
 * @author Levente Hudák
 */
public class WikiFriendlyURLMapper extends DefaultFriendlyURLMapper {

	@Override
	public String buildPath(LiferayPortletURL liferayPortletURL) {
		Map<String, String> routeParameters = new HashMap<String, String>();

		buildRouteParameters(liferayPortletURL, routeParameters);

		addParameter(routeParameters, "nodeName", true);
		addParameter(routeParameters, "title", true);

		String friendlyURLPath = router.parametersToUrl(routeParameters);

		if (Validator.isNull(friendlyURLPath)) {
			return null;
		}

		addParametersIncludedInPath(liferayPortletURL, routeParameters);

		friendlyURLPath = StringPool.SLASH.concat(getMapping()).concat(
			friendlyURLPath);

		return friendlyURLPath;
	}

	protected void addParameter(
		Map<String, String> routeParameters, String name, boolean escape) {

		if (!routeParameters.containsKey(name)) {
			return;
		}

		String value = routeParameters.get(name);

		if (escape) {
			value = WikiServiceUtil.escapeName(value);
		}
		else {
			value = WikiServiceUtil.unescapeName(value);
		}

		routeParameters.put(name, value);
	}

	@Override
	protected void populateParams(
		Map<String, String[]> parameterMap, String namespace,
		Map<String, String> routeParameters) {

		addParameter(routeParameters, "nodeName", false);
		addParameter(routeParameters, "title", false);

		super.populateParams(parameterMap, namespace, routeParameters);
	}

}