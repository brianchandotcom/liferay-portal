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

package com.liferay.portal.settings.authentication.ldap.web.internal.portlet.action;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderConstants;
import com.liferay.portal.kernel.util.PortalUtil;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;

/**
 * @author Tomas Polesovsky
 */
public abstract class BasePortalSettingsMVCRenderCommand
	implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher(getJspPath());

		try {
			requestDispatcher.include(
				PortalUtil.getHttpServletRequest(renderRequest),
				PortalUtil.getHttpServletResponse(renderResponse));
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to include JSP " + getJspPath(), e);
			}

			throw new PortletException(
				"Unable to include JSP " + getJspPath(), e);
		}

		return MVCRenderConstants.MVC_PATH_VALUE_SKIP_DISPATCH;
	}

	protected abstract String getJspPath();

	protected volatile ServletContext servletContext;

	private static final Log _log = LogFactoryUtil.getLog(
		BasePortalSettingsMVCRenderCommand.class);

}