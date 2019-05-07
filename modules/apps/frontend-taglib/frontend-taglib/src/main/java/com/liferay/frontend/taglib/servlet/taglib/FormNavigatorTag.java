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

package com.liferay.frontend.taglib.servlet.taglib;

import com.liferay.frontend.taglib.internal.servlet.ServletContextUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorCategoryUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorEntry;
import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorEntryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.IncludeTag;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Eudaldo Alonso
 */
public class FormNavigatorTag extends IncludeTag {

	@Override
	public int doStartTag() {
		return EVAL_BODY_INCLUDE;
	}

	public String getBackURL() {
		return _backURL;
	}

	public String getFieldSetCssClass() {
		return _fieldSetCssClass;
	}

	public Object getFormModelBean() {
		return _formModelBean;
	}

	public String getId() {
		return _id;
	}

	public boolean isShowButtons() {
		return _showButtons;
	}

	public void setBackURL(String backURL) {
		_backURL = backURL;
	}

	public void setFieldSetCssClass(String fieldSetCssClass) {
		_fieldSetCssClass = fieldSetCssClass;
	}

	public void setFormModelBean(Object formModelBean) {
		_formModelBean = formModelBean;
	}

	public void setId(String id) {
		_id = id;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	public void setShowButtons(boolean showButtons) {
		_showButtons = showButtons;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_backURL = null;
		_fieldSetCssClass = null;
		_formModelBean = null;
		_id = null;
		_showButtons = true;
	}

	@Override
	protected String getPage() {
		return "/form_navigator/page.jsp";
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute(
			"liferay-frontend:form-navigator:backURL", _getBackURL());
		httpServletRequest.setAttribute(
			"liferay-frontend:form-navigator:categoryKeys", _getCategoryKeys());
		httpServletRequest.setAttribute(
			"liferay-frontend:form-navigator:fieldSetCssClass",
			_fieldSetCssClass);
		httpServletRequest.setAttribute(
			"liferay-frontend:form-navigator:formModelBean", _formModelBean);
		httpServletRequest.setAttribute(
			"liferay-frontend:form-navigator:id", _id);
		httpServletRequest.setAttribute(
			"liferay-frontend:form-navigator:showButtons",
			String.valueOf(_showButtons));
	}

	private String _getBackURL() {
		String backURL = _backURL;

		if (Validator.isNull(backURL)) {
			backURL = ParamUtil.getString(request, "redirect");
		}

		PortletResponse portletResponse = (PortletResponse)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE);

		LiferayPortletResponse liferayPortletResponse =
			PortalUtil.getLiferayPortletResponse(portletResponse);

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		if (Validator.isNull(backURL)) {
			backURL = portletURL.toString();
		}

		return backURL;
	}

	private String[] _getCategoryKeys() {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		List<String> categoryKeys = new ArrayList<>();

		for (String categoryKey : FormNavigatorCategoryUtil.getKeys(_id)) {
			List<FormNavigatorEntry<Object>> formNavigatorEntries =
				FormNavigatorEntryUtil.getFormNavigatorEntries(
					_id, categoryKey, themeDisplay.getUser(), _formModelBean);

			if (ListUtil.isNotEmpty(formNavigatorEntries)) {
				categoryKeys.add(categoryKey);
			}
		}

		return ArrayUtil.toStringArray(categoryKeys);
	}

	private String _backURL;
	private String _fieldSetCssClass;
	private Object _formModelBean;
	private String _id;
	private boolean _showButtons = true;

}