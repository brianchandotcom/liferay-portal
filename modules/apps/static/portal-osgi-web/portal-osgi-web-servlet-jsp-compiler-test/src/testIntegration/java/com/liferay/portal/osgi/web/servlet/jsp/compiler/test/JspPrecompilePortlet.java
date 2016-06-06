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

package com.liferay.portal.osgi.web.servlet.jsp.compiler.test;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Matthew Tambara
 */
public class JspPrecompilePortlet extends MVCPortlet {

	public static final String PORTLET_NAME = StringUtil.replace(
		JspPrecompilePortlet.class.getName(), CharPool.PERIOD,
		CharPool.UNDERLINE);

	public static String getJSPParameterName() {
		StringBundler sb = new StringBundler(4);

		sb.append(StringPool.UNDERLINE);
		sb.append(PORTLET_NAME);
		sb.append(StringPool.UNDERLINE);
		sb.append(_JSP_PARAMETER);

		return sb.toString();
	}

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		String jsp = renderRequest.getParameter(_JSP_PARAMETER);

		if (jsp == null) {
			throw new IllegalArgumentException(
				_JSP_PARAMETER + " query must not be null");
		}

		PortletContext portletContext = getPortletContext();

		PortletRequestDispatcher portletRequestDispatcher =
			portletContext.getRequestDispatcher(jsp);

		portletRequestDispatcher.include(renderRequest, renderResponse);
	}

	private static final String _JSP_PARAMETER = "jsp";

}