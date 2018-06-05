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

package com.liferay.portal.kernel.portlet;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.HeaderRequest;
import javax.portlet.HeaderResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.filter.FilterChain;
import javax.portlet.filter.HeaderFilterChain;

/**
 * @author Michael Young
 * @author Neil Griffin
 */
public class PortletFilterUtil {

	public static void doFilter(
			PortletRequest portletRequest, PortletResponse portletResponse,
			String lifecycle, FilterChain filterChain)
		throws IOException, PortletException {

		if (lifecycle.equals(PortletRequest.ACTION_PHASE)) {
			ActionRequest actionRequest = (ActionRequest)portletRequest;
			ActionResponse actionResponse = (ActionResponse)portletResponse;

			filterChain.doFilter(actionRequest, actionResponse);
		}
		else if (lifecycle.equals(PortletRequest.EVENT_PHASE)) {
			EventRequest eventRequest = (EventRequest)portletRequest;
			EventResponse eventResponse = (EventResponse)portletResponse;

			filterChain.doFilter(eventRequest, eventResponse);
		}
		else if (lifecycle.equals(PortletRequest.HEADER_PHASE)) {
			HeaderRequest headerRequest = (HeaderRequest)portletRequest;
			HeaderResponse headerResponse = (HeaderResponse)portletResponse;

			HeaderFilterChain headerFilterChain =
				(HeaderFilterChain)filterChain;

			headerFilterChain.doFilter(headerRequest, headerResponse);
		}
		else if (lifecycle.equals(PortletRequest.RENDER_PHASE)) {
			RenderRequest renderRequest = (RenderRequest)portletRequest;
			RenderResponse renderResponse = (RenderResponse)portletResponse;

			filterChain.doFilter(renderRequest, renderResponse);
		}
		else if (lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {
			ResourceRequest resourceRequest = (ResourceRequest)portletRequest;
			ResourceResponse resourceResponse =
				(ResourceResponse)portletResponse;

			filterChain.doFilter(resourceRequest, resourceResponse);
		}
	}

}