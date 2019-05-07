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
import com.liferay.dynamic.data.mapping.taglib.internal.servlet.ServletContextUtil;
import com.liferay.dynamic.data.mapping.taglib.internal.util.PortletDisplayTemplateUtil;
import com.liferay.dynamic.data.mapping.taglib.servlet.taglib.base.BaseTemplateSelectorTag;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Juan Fernández
 */
public class TemplateSelectorTag extends BaseTemplateSelectorTag {

	@Override
	public String getDisplayStyle() {
		DDMTemplate portletDisplayDDMTemplate = getPortletDisplayDDMTemplate();

		if (portletDisplayDDMTemplate != null) {
			return PortletDisplayTemplateUtil.getDisplayStyle(
				portletDisplayDDMTemplate.getTemplateKey());
		}

		if (Validator.isNull(super.getDisplayStyle())) {
			return super.getDefaultDisplayStyle();
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
			displayStyle = super.getDefaultDisplayStyle();
		}

		return PortletDisplayTemplateUtil.getPortletDisplayTemplateDDMTemplate(
			getDisplayStyleGroupId(), PortalUtil.getClassNameId(getClassName()),
			displayStyle, true);
	}

	protected ResourceBundle getResourceBundle() {
		Locale locale = PortalUtil.getLocale(request);

		Class<?> clazz = getClass();

		return ResourceBundleUtil.getBundle(
			"content.Language", locale, clazz.getClassLoader());
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		super.setAttributes(httpServletRequest);

		setNamespacedAttribute(
			httpServletRequest, "classNameId",
			String.valueOf(PortalUtil.getClassNameId(getClassName())));
		setNamespacedAttribute(
			httpServletRequest, "portletDisplayDDMTemplate",
			getPortletDisplayDDMTemplate());
		setNamespacedAttribute(
			httpServletRequest, "resourceBundle", getResourceBundle());
	}

}