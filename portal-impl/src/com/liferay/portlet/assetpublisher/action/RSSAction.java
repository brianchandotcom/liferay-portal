/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.assetpublisher.action;

import com.liferay.portlet.rss.RSSRenderer;
import com.liferay.portlet.rss.action.DefaultRSSAction;

import javax.portlet.PortletPreferences;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

/**
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 */
public class RSSAction extends DefaultRSSAction {

	@Override
	protected byte[] getRSS(
			ResourceRequest portletRequest, ResourceResponse portletResponse)
		throws Exception {

		PortletPreferences portletPreferences = portletRequest.getPreferences();

		String selectionStyle = portletPreferences.getValue(
			"selectionStyle", "dynamic");

		if (!selectionStyle.equals("dynamic")) {
			return new byte[0];
		}

		return super.getRSS(portletRequest, portletResponse);
	}

	@Override
	protected RSSRenderer getRSSRenderer(
			ResourceRequest portletRequest, ResourceResponse portletResponse)
		throws Exception {

		return new AssetRSSRenderer(portletRequest, portletResponse);
	}

}