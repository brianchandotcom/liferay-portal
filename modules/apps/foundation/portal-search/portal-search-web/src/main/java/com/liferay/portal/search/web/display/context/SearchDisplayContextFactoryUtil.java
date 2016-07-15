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

package com.liferay.portal.search.web.display.context;

import com.liferay.osgi.util.ServiceTrackerFactory;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Tina Tian
 */
public class SearchDisplayContextFactoryUtil {

	public static SearchDisplayContext create(
			RenderRequest renderRequest, RenderResponse renderResponse,
			PortletPreferences portletPreferences)
		throws Exception {

		SearchDisplayContextFactory searchDisplayContextFactory =
			_serviceTracker.getService();

		return searchDisplayContextFactory.create(
			renderRequest, renderResponse, portletPreferences);
	}

	private static final ServiceTracker
		<SearchDisplayContextFactory, SearchDisplayContextFactory>
			_serviceTracker = ServiceTrackerFactory.open(
				SearchDisplayContextFactory.class);

}