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

package com.liferay.nested.portlets.web.portlet.util;

import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.util.StringPool;

import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Peter Fellwock
 */
public class NestedPortletUtil {

	public static void defineObjects(
		PortletConfig portletConfig, RenderResponse renderResponse,
		RenderRequest renderRequest) {

		LiferayPortletRequest liferayPortletRequest =
			(LiferayPortletRequest)renderRequest;

		liferayPortletRequest.defineObjects(portletConfig, renderResponse);
	}

	public static String getLayoutTemplateId(PortletPreferences preferences) {
		String layoutTemplateIdDefault = preferences.getValue(
			"nested.portlets.layout.template.default", StringPool.BLANK);

		String layoutTemplateId = preferences.getValue(
			"layoutTemplateId", layoutTemplateIdDefault);

		return layoutTemplateId;
	}

}