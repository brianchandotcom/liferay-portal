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

import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.servlet.JSPSupportServlet;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.CustomizedPages;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portlet.sites.util.SitesUtil;
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
		HttpServletRequest request, HttpServletResponse response) {

		JspFactory jspFactory = JspFactory.getDefaultFactory();

		_pageContext = jspFactory.getPageContext(
			new JSPSupportServlet(request.getServletContext()), request,
			response, null, false, 0, false);

		_writer = _pageContext.getOut();

		Layout selLayout = null;

		long selPlid = ParamUtil.getLong(
			request, "selPlid", LayoutConstants.DEFAULT_PLID);

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
					customizableKey, String.valueOf(false)));
		}

		_writer.append("<div class=\"");
		_writer.append(classNames);
		_writer.append("\">");

		_writer.append("<h1>");
		_writer.append(columnId);
		_writer.append("</h1>");

		InputTag inputTag = new InputTag();

		inputTag.setDisabled(!_customizationEnabled);
		inputTag.setLabel("customizable");
		inputTag.setName(
			"TypeSettingsProperties--".concat(customizableKey).concat("--"));
		inputTag.setPageContext(_pageContext);
		inputTag.setType("checkbox");
		inputTag.setValue(customizable);

		int result = inputTag.doStartTag();

		if (result == Tag.EVAL_BODY_INCLUDE) {
			inputTag.doEndTag();
		}

		_writer.append("</div>");

		return StringPool.BLANK;
	}

	@Override
	public String processMax() throws Exception {
		return StringPool.BLANK;
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #processMax()}
	 */
	@Deprecated
	@Override
	public String processMax(String classNames) throws Exception {
		return processMax();
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