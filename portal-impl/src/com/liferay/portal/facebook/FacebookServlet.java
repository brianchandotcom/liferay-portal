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

package com.liferay.portal.facebook;

import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.BufferCacheServletResponse;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.servlet.filters.gzip.GZipFilter;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.social.util.FacebookUtil;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 */
public class FacebookServlet extends HttpServlet {

	@Override
	public void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		try {
			String[] facebookData = FacebookUtil.getFacebookData(request);

			if ((facebookData == null) ||
				!PortalUtil.isValidResourceId(facebookData[1])) {

				PortalUtil.sendError(
					HttpServletResponse.SC_NOT_FOUND,
					new NoSuchLayoutException(), request, response);
			}
			else {
				String facebookCanvasPageURL = facebookData[0];
				String redirect = facebookData[1];

				request.setAttribute(
					WebKeys.FACEBOOK_CANVAS_PAGE_URL, facebookCanvasPageURL);
				request.setAttribute(GZipFilter.SKIP_FILTER, Boolean.TRUE);

				ServletContext servletContext = getServletContext();

				RequestDispatcher requestDispatcher =
					servletContext.getRequestDispatcher(redirect);

				BufferCacheServletResponse bufferCacheServletResponse =
					new BufferCacheServletResponse(response);

				requestDispatcher.forward(request, bufferCacheServletResponse);

				String fbml = bufferCacheServletResponse.getString();

				fbml = fixFbml(fbml);

				ServletResponseUtil.write(response, fbml);
			}
		}
		catch (Exception e) {
			_log.error(e, e);

			PortalUtil.sendError(
				HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e, request,
				response);
		}
	}

	protected String fixFbml(String fbml) {
		fbml = StringUtil.replace(
			fbml,
			new String[] {
				"<nobr>", "</nobr>"
			},
			new String[] {
				StringPool.BLANK, StringPool.BLANK
			});

		return fbml;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FacebookServlet.class);

}