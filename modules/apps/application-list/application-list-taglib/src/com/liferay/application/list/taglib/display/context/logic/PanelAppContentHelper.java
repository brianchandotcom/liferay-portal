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

package com.liferay.application.list.taglib.display.context.logic;

import com.liferay.portal.kernel.template.StringTemplateResource;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.layoutconfiguration.util.RuntimePageUtil;
import com.liferay.portal.model.LayoutTemplateConstants;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.Theme;
import com.liferay.portal.service.LayoutTemplateLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Adolfo Pérez
 */
public class PanelAppContentHelper {

	public PanelAppContentHelper(
		HttpServletRequest request, HttpServletResponse response) {

		_request = request;
		_response = response;
	}

	public boolean isValidPortletSelected() {
		if (getPortlet() == null) {
			return false;
		}

		return true;
	}

	public void writeContent(Writer writer) throws Exception {
		ThemeDisplay themeDisplay = getThemeDisplay();

		String layoutTemplateId = "max";

		if (themeDisplay.isStatePopUp()) {
			layoutTemplateId = "pop_up";
		}

		Theme theme = themeDisplay.getTheme();

		String velocityTemplateId =
			theme.getThemeId() + LayoutTemplateConstants.STANDARD_SEPARATOR +
				layoutTemplateId;

		String content = LayoutTemplateLocalServiceUtil.getContent(
			layoutTemplateId, true, theme.getThemeId());

		if (Validator.isNotNull(velocityTemplateId) &&
			Validator.isNotNull(content)) {

			StringBundler sb = RuntimePageUtil.getProcessedTemplate(
				_request, _response, getPortletId(),
				new StringTemplateResource(velocityTemplateId, content));

			if (sb != null) {
				sb.writeTo(writer);
			}
		}
	}

	protected long getCompanyId() {
		if (_companyId == null) {
			ThemeDisplay themeDisplay = getThemeDisplay();

			_companyId = themeDisplay.getCompanyId();
		}

		return _companyId;
	}

	protected Portlet getPortlet() {
		if ((_portlet == null) && Validator.isNotNull(getPortletId())) {
			_portlet = PortletLocalServiceUtil.getPortletById(
				getCompanyId(), getPortletId());
		}

		return _portlet;
	}

	protected String getPortletId() {
		if (_portletId == null) {
			_portletId = (String)_request.getAttribute(
				"liferay-application-list:application-content:portletId");
		}

		return _portletId;
	}

	private ThemeDisplay getThemeDisplay() {
		return (ThemeDisplay)_request.getAttribute(WebKeys.THEME_DISPLAY);
	}

	private Long _companyId;
	private Portlet _portlet;
	private String _portletId;
	private final HttpServletRequest _request;
	private final HttpServletResponse _response;

}