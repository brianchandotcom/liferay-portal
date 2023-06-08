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

package com.liferay.layout.taglib.servlet.taglib;

import com.liferay.frontend.taglib.clay.servlet.taglib.AlertTag;
import com.liferay.layout.taglib.internal.servlet.ServletContextUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.portletext.RuntimeTag;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

/**
 * @author Eudaldo Alonso
 */
public class LayoutCommonTag extends IncludeTag {

	public boolean getDisplaySessionMessages() {
		return _displaySessionMessages;
	}

	public void setDisplaySessionMessages(boolean displaySessionMessages) {
		_displaySessionMessages = displaySessionMessages;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_displaySessionMessages = false;
		_includeStaticPortlets = false;
		_includeWebServerDisplayNode = false;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected boolean isCleanUpSetAttributes() {
		return super.isCleanUpSetAttributes();
	}

	@Override
	protected int processEndTag() throws Exception {
		HttpServletRequest httpServletRequest = getRequest();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (!themeDisplay.isStatePopUp()) {
			if (!themeDisplay.isStateExclusive() && !themeDisplay.isWidget()) {
				_includeStaticPortlets = true;
			}

			if (_WEB_SERVER_DISPLAY_NODE) {
				_includeWebServerDisplayNode = true;
			}
		}

		if (_includeStaticPortlets) {
			Company company = themeDisplay.getCompany();
			HttpServletResponse httpServletResponse =
				(HttpServletResponse)pageContext.getResponse();

			for (String portletId : _LAYOUT_STATIC_PORTLETS_ALL) {
				if (PortletLocalServiceUtil.hasPortlet(
						company.getCompanyId(), portletId)) {

					RuntimeTag.doTag(
						portletId, pageContext, httpServletRequest,
						httpServletResponse);
				}
			}
		}

		JspWriter jspWriter = pageContext.getOut();

		if (_includeWebServerDisplayNode) {
			jspWriter.write("<div class=\"alert-container cadmin container\">");
			jspWriter.write("<div class=\"alert-notifications ");
			jspWriter.write("alert-notifications-fixed\" ");
			jspWriter.write("id=\"WebServerDisplayNodeContainer\">");

			AlertTag alertTag = new AlertTag();

			alertTag.setDismissible(true);

			StringBundler sb = new StringBundler(6);

			sb.append(LanguageUtil.get(themeDisplay.getLocale(), "node"));
			sb.append(StringPool.COLON);
			sb.append(StringPool.SPACE);
			sb.append(StringUtil.toLowerCase(PortalUtil.getComputerName()));
			sb.append(StringPool.COLON);
			sb.append(PortalUtil.getPortalLocalPort(false));

			alertTag.setTitle(sb.toString());

			jspWriter.write(alertTag.doTagAsString(pageContext));

			jspWriter.write("</div></div>");
		}

		jspWriter.write("<form action=\"#\" aria-hidden=\"true\" ");
		jspWriter.write("class=\"hide\" id=\"hrefFm\" method=\"post\" ");
		jspWriter.write("name=\"hrefFm\"><span></span><button hidden ");
		jspWriter.write("type=\"submit\">");
		jspWriter.write(LanguageUtil.get(httpServletRequest, "hidden"));
		jspWriter.write("</button></form>");

		return EVAL_PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
	}

	private static final String[] _LAYOUT_STATIC_PORTLETS_ALL =
		PropsUtil.getArray(PropsKeys.LAYOUT_STATIC_PORTLETS_ALL);

	private static final String _PAGE = "/layout_common/page.jsp";

	private static final boolean _WEB_SERVER_DISPLAY_NODE =
		GetterUtil.getBoolean(PropsUtil.get(PropsKeys.WEB_SERVER_DISPLAY_NODE));

	private boolean _displaySessionMessages;
	private boolean _includeStaticPortlets;
	private boolean _includeWebServerDisplayNode;

}