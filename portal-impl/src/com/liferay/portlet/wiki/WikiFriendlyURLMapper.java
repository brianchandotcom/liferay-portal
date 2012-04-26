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

package com.liferay.portlet.wiki;

import com.liferay.portal.kernel.portlet.DefaultFriendlyURLMapper;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Shinn Lok
 */
public class WikiFriendlyURLMapper extends DefaultFriendlyURLMapper {

	@Override
	public String buildPath(LiferayPortletURL liferayPortletURL) {
		Map<String, String> routeParameters = new HashMap<String, String>();

		buildRouteParameters(liferayPortletURL, routeParameters);

		if (routeParameters.containsKey("title")) {
			String title = routeParameters.get("title");

			title = title.replaceAll(StringPool.SLASH, ".sl.");
			title = title.replaceAll("\\?", ".qm.");
			title = title.replaceAll("\\+", ".ps.");
			
			routeParameters.put("title", title);
		}

		String friendlyURLPath = router.parametersToUrl(routeParameters);

		if (Validator.isNull(friendlyURLPath)) {
			return null;
		}

		addParametersIncludedInPath(liferayPortletURL, routeParameters);

		friendlyURLPath = StringPool.SLASH.concat(getMapping()).concat(
			friendlyURLPath);

		return friendlyURLPath;
	}

	@Override
	protected void populateParams(Map<String, String[]> parameterMap,
			String namespace, Map<String, String> routeParameters) {

		if (routeParameters.containsKey("title")) {
			String title = routeParameters.get("title");

			title = title.replaceAll(".sl.", StringPool.SLASH);
			title = title.replaceAll(".qm.", StringPool.QUESTION);
			title = title.replaceAll(".ps.", StringPool.PLUS);
			
			routeParameters.put("title", title);
		}
		
		super.populateParams(parameterMap, namespace, routeParameters);

	}
	
}