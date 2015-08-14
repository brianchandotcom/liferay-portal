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
import com.liferay.dynamic.data.mapping.taglib.servlet.taglib.base.BaseTemplateSelectorTag;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.display.template.PortletDisplayTemplate;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Juan Fernández
 */
public class TemplateSelectorTag extends BaseTemplateSelectorTag {

	@Override
	public String getDefaultDisplayStyle() {
		String defaultDisplayStyle = super.getDefaultDisplayStyle();

		if (defaultDisplayStyle != null) {
			return defaultDisplayStyle;
		}

		return StringPool.BLANK;
	}

	@Override
	public String getDisplayStyle() {
		DDMTemplate portletDisplayDDMTemplate = getPortletDisplayDDMTemplate();

		if (portletDisplayDDMTemplate != null) {
			return getPortletDisplayTemplate().getDisplayStyle(
				portletDisplayDDMTemplate.getTemplateKey());
		}

		if (Validator.isNull(super.getDisplayStyle())) {
			return getDefaultDisplayStyle();
		}

		return super.getDisplayStyle();
	}

	@Override
	public long getDisplayStyleGroupId() {
		long displayStyleGroupId = super.getDisplayStyleGroupId();

		if (displayStyleGroupId > 0) {
			return displayStyleGroupId;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		return themeDisplay.getScopeGroupId();
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());
	}

	protected DDMTemplate getPortletDisplayDDMTemplate() {
		String displayStyle = super.getDisplayStyle();

		if (Validator.isNull(displayStyle)) {
			displayStyle = getDefaultDisplayStyle();
		}

		return getPortletDisplayTemplate().getPortletDisplayTemplateDDMTemplate(
			getDisplayStyleGroupId(), PortalUtil.getClassNameId(getClassName()),
			displayStyle, true);
	}

	protected PortletDisplayTemplate getPortletDisplayTemplate() {
		Registry registry = RegistryUtil.getRegistry();

		return registry.getService(PortletDisplayTemplate.class);
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		super.setAttributes(request);

		setNamespacedAttribute(
			request, "classNameId",
			String.valueOf(PortalUtil.getClassNameId(getClassName())));
		setNamespacedAttribute(
			request, "portletDisplayDDMTemplate",
			getPortletDisplayDDMTemplate());
	}

}