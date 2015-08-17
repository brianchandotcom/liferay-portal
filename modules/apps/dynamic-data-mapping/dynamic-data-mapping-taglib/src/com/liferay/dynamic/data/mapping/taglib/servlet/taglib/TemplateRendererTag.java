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

package com.liferay.dynamic.data.mapping.taglib.servlet.taglib;

import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.taglib.servlet.ServletContextUtil;
import com.liferay.dynamic.data.mapping.taglib.servlet.taglib.base.BaseTemplateRendererTag;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.display.template.PortletDisplayTemplateUtil;
import com.liferay.taglib.FileAvailabilityUtil;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Eduardo Garcia
 */
public class TemplateRendererTag extends BaseTemplateRendererTag {

	@Override
	public int doStartTag() throws JspException {
		try {
			String page = getStartPage();

			setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

			callSetAttributes();

			if (themeResourceExists(page)) {
				doIncludeTheme(page);

				return EVAL_BODY_INCLUDE;
			}

			if (!FileAvailabilityUtil.isAvailable(servletContext, page)) {
				logUnavailablePage(page);
			}

			doInclude(page, true);

			if (_portletDisplayDDMTemplate != null) {
				return SKIP_BODY;
			}

			return EVAL_BODY_INCLUDE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_portletDisplayDDMTemplate = null;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		super.setAttributes(request);

		Map<String, Object> contextObjects = getContextObjects();

		if (contextObjects == null) {
			setNamespacedAttribute(
				request, "contextObjects", new HashMap<String, Object>());
		}

		long displaStyleGroupId = getDisplayStyleGroupId();

		if (displaStyleGroupId == 0) {
			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			displaStyleGroupId = themeDisplay.getScopeGroupId();

			setNamespacedAttribute(
				request, "displayStyleGroupId", displaStyleGroupId);
		}

		_portletDisplayDDMTemplate =
			PortletDisplayTemplateUtil.getPortletDisplayTemplateDDMTemplate(
				displaStyleGroupId, PortalUtil.getClassNameId(getClassName()),
				getDisplayStyle(), true);

		setNamespacedAttribute(
			request, "portletDisplayDDMTemplate", _portletDisplayDDMTemplate);
	}

	private DDMTemplate _portletDisplayDDMTemplate;

}