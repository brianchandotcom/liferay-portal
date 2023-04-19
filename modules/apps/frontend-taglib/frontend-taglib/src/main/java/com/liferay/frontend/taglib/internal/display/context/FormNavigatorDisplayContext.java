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

package com.liferay.frontend.taglib.internal.display.context;

import com.liferay.frontend.taglib.form.navigator.FormNavigatorEntry;
import com.liferay.frontend.taglib.form.navigator.FormNavigatorEntryProvider;
import com.liferay.frontend.taglib.form.navigator.constants.FormNavigatorConstants;
import com.liferay.frontend.taglib.internal.servlet.ServletContextUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class FormNavigatorDisplayContext {

	public FormNavigatorDisplayContext(HttpServletRequest httpServletRequest) {
		_httpServletRequest = httpServletRequest;
	}

	public String getBackURL() {
		return (String)_httpServletRequest.getAttribute(
			"liferay-frontend:form-navigator:backURL");
	}

	public String[] getCategoryKeys() {
		return (String[])_httpServletRequest.getAttribute(
			"liferay-frontend:form-navigator:categoryKeys");
	}

	public String getFieldSetCssClass() {
		return (String)_httpServletRequest.getAttribute(
			"liferay-frontend:form-navigator:fieldSetCssClass");
	}

	public Object getFormModelBean() {
		return _httpServletRequest.getAttribute(
			"liferay-frontend:form-navigator:formModelBean");
	}

	public List<FormNavigatorEntry<Object>> getFormNavigatorEntries() {
		FormNavigatorEntryProvider formNavigatorEntryProvider =
			ServletContextUtil.getFormNavigatorEntryProvider();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return formNavigatorEntryProvider.getFormNavigatorEntries(
			getId(), themeDisplay.getUser(), getFormModelBean());
	}

	public List<FormNavigatorEntry<Object>> getFormNavigatorEntries(
		String categoryKey) {

		FormNavigatorEntryProvider formNavigatorEntryProvider =
			ServletContextUtil.getFormNavigatorEntryProvider();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return formNavigatorEntryProvider.getFormNavigatorEntries(
			getId(), categoryKey, themeDisplay.getUser(), getFormModelBean());
	}

	public String getId() {
		return (String)_httpServletRequest.getAttribute(
			"liferay-frontend:form-navigator:id");
	}

	public String getSectionId(String name) {
		return TextFormatter.format(name, TextFormatter.M);
	}

	public String getTabs1Param() {
		String randomNamespace = PortalUtil.generateRandomKey(
			_httpServletRequest, "taglib_ui_form_navigator_init");

		return randomNamespace + "_tabs1";
	}

	public FormNavigatorConstants.FormNavigatorType getType() {
		return (FormNavigatorConstants.FormNavigatorType)
			_httpServletRequest.getAttribute(
				"liferay-frontend:form-navigator:type");
	}

	public boolean isShowButtons() {
		return GetterUtil.getBoolean(
			(String)_httpServletRequest.getAttribute(
				"liferay-frontend:form-navigator:showButtons"));
	}

	private final HttpServletRequest _httpServletRequest;

}