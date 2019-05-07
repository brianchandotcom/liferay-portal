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

package com.liferay.portal.layoutconfiguration.util.velocity;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.CustomizedPages;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.servlet.JSPSupportServlet;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.sites.kernel.util.SitesUtil;
import com.liferay.taglib.aui.InputTag;

import java.io.Writer;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

/**
 * @author Raymond Augé
 * @author Oliver Teichmann
 */
public class CustomizationSettingsProcessor implements ColumnProcessor {

	public CustomizationSettingsProcessor(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		JspFactory jspFactory = JspFactory.getDefaultFactory();

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(
				PortalClassLoaderUtil.getClassLoader());

			_pageContext = jspFactory.getPageContext(
				new JSPSupportServlet(httpServletRequest.getServletContext()),
				httpServletRequest, httpServletResponse, null, false, 0, false);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}

		_writer = _pageContext.getOut();

		Layout selLayout = null;

		long selPlid = ParamUtil.getLong(
			httpServletRequest, "selPlid", LayoutConstants.DEFAULT_PLID);

		if (selPlid != LayoutConstants.DEFAULT_PLID) {
			selLayout = LayoutLocalServiceUtil.fetchLayout(selPlid);
		}

		_layoutTypeSettings = selLayout.getTypeSettingsProperties();

		if (!SitesUtil.isLayoutUpdateable(selLayout) ||
			selLayout.isLayoutPrototypeLinkActive()) {

			_customizationEnabled = false;
		}
		else {
			_customizationEnabled = true;
		}
	}

	@Override
	public String processColumn(String columnId) throws Exception {
		return processColumn(columnId, StringPool.BLANK);
	}

	@Override
	public String processColumn(String columnId, String classNames)
		throws Exception {

		String customizableKey = CustomizedPages.namespaceColumnId(columnId);

		boolean customizable = false;

		if (_customizationEnabled) {
			customizable = GetterUtil.getBoolean(
				_layoutTypeSettings.getProperty(
					customizableKey, Boolean.FALSE.toString()));
		}

		_writer.append("<div class=\"");
		_writer.append(classNames);
		_writer.append("\">");

		_writer.append("<h1>");
		_writer.append(columnId);
		_writer.append("</h1>");

		InputTag inputTag = new InputTag();

		inputTag.setDisabled(!_customizationEnabled);
		inputTag.setDynamicAttribute(
			StringPool.BLANK, "labelOff", "not-customizable");
		inputTag.setDynamicAttribute(
			StringPool.BLANK, "labelOn", "customizable");
		inputTag.setLabel(StringPool.BLANK);
		inputTag.setName(
			"TypeSettingsProperties--".concat(
				customizableKey
			).concat(
				"--"
			));
		inputTag.setPageContext(_pageContext);
		inputTag.setType("toggle-switch");
		inputTag.setValue(customizable);

		int result = inputTag.doStartTag();

		if (result == Tag.EVAL_BODY_INCLUDE) {
			inputTag.doEndTag();
		}

		_writer.append("</div>");

		return StringPool.BLANK;
	}

	@Override
	public String processDynamicColumn(String columnId, String classNames)
		throws Exception {

		return StringPool.BLANK;
	}

	@Override
	public String processMax() throws Exception {
		return StringPool.BLANK;
	}

	@Override
	public String processPortlet(String portletId) throws Exception {
		_writer.append("<div class=\"portlet\">");
		_writer.append(portletId);
		_writer.append("</div>");

		return StringPool.BLANK;
	}

	@Override
	public String processPortlet(
			String portletId, Map<String, ?> defaultSettingsMap)
		throws Exception {

		return processPortlet(portletId);
	}

	@Override
	public String processPortlet(
			String portletProviderClassName,
			PortletProvider.Action portletProviderAction)
		throws Exception {

		String portletId = PortletProviderUtil.getPortletId(
			portletProviderClassName, portletProviderAction);

		return processPortlet(portletId);
	}

	private final boolean _customizationEnabled;
	private final UnicodeProperties _layoutTypeSettings;
	private final PageContext _pageContext;
	private final Writer _writer;

}