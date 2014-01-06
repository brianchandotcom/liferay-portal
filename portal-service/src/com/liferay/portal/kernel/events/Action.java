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

package com.liferay.portal.kernel.events;

import com.liferay.portal.util.PortalUtil;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class Action implements LifecycleAction {

	@Override
	public final void processEvent(LifecycleEvent lifecycleEvent)
		throws ActionException {

		run(lifecycleEvent.getRequest(), lifecycleEvent.getResponse());
	}

	public abstract void run(
			HttpServletRequest request, HttpServletResponse response)
		throws ActionException;

	public void run(RenderRequest renderRequest, RenderResponse renderResponse)
		throws ActionException {

		try {
			HttpServletRequest request = PortalUtil.getHttpServletRequest(
				renderRequest);
			HttpServletResponse response = PortalUtil.getHttpServletResponse(
				renderResponse);

			run(request, response);
		}
		catch (ActionException ae) {
			throw ae;
		}
		catch (Exception e) {
			throw new ActionException(e);
		}
	}

}