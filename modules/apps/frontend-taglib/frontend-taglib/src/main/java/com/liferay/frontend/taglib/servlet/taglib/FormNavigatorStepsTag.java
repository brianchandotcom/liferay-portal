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
import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorCategoryUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorEntryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Eudaldo Alonso
 */
public class FormNavigatorStepsTag extends IncludeTag {

	@Override
	public int doStartTag() {
		return EVAL_BODY_INCLUDE;
	}

	public String getBackURL() {
		return _backURL;
	}

	public Object getFormModelBean() {
		return _formModelBean;
	}

	public String getFormName() {
		return _formName;
	}

	public String getHtmlBottom() {
		return _htmlBottom;
	}

	public String getHtmlTop() {
		return _htmlTop;
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

	public void setFormModelBean(Object formModelBean) {
		_formModelBean = formModelBean;
	}

	public void setFormName(String formName) {
		_formName = formName;
	}

	public void setHtmlBottom(String htmlBottom) {
		_htmlBottom = htmlBottom;
	}

	public void setHtmlTop(String htmlTop) {
		_htmlTop = htmlTop;
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
		_formModelBean = null;
		_formName = "fm";
		_htmlBottom = null;
		_htmlTop = null;
		_id = null;
		_showButtons = true;
	}

	protected String[] getCategoryKeys() {
		return FormNavigatorCategoryUtil.getKeys(_id);
	}

	protected String[] getCategoryLabels() {
		HttpServletRequest httpServletRequest = getRequest();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return FormNavigatorCategoryUtil.getLabels(
			_id, themeDisplay.getLocale());
	}

	protected String[][] getCategorySectionKeys() {
		HttpServletRequest httpServletRequest = getRequest();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		String[] categoryKeys = getCategoryKeys();

		String[][] categorySectionKeys = new String[0][];

		for (String categoryKey : categoryKeys) {
			categorySectionKeys = ArrayUtil.append(
				categorySectionKeys,
				FormNavigatorEntryUtil.getKeys(
					_id, categoryKey, themeDisplay.getUser(), _formModelBean));
		}

		return categorySectionKeys;
	}

	protected String[][] getCategorySectionLabels() {
		HttpServletRequest httpServletRequest = getRequest();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		String[] categoryKeys = getCategoryKeys();

		String[][] categorySectionLabels = new String[0][];

		for (String categoryKey : categoryKeys) {
			categorySectionLabels = ArrayUtil.append(
				categorySectionLabels,
				FormNavigatorEntryUtil.getLabels(
					_id, categoryKey, themeDisplay.getUser(), _formModelBean,
					themeDisplay.getLocale()));
		}

		return categorySectionLabels;
	}

	@Override
	protected String getPage() {
		return "/form_navigator_steps/page.jsp";
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute(
			"liferay-frontend:form-navigator-steps:backURL", _backURL);
		httpServletRequest.setAttribute(
			"liferay-frontend:form-navigator-steps:categoryKeys",
			getCategoryKeys());
		httpServletRequest.setAttribute(
			"liferay-frontend:form-navigator-steps:categoryLabels",
			getCategoryLabels());
		httpServletRequest.setAttribute(
			"liferay-frontend:form-navigator-steps:categorySectionKeys",
			getCategorySectionKeys());
		httpServletRequest.setAttribute(
			"liferay-frontend:form-navigator-steps:categorySectionLabels",
			getCategorySectionLabels());
		httpServletRequest.setAttribute(
			"liferay-frontend:form-navigator-steps:formModelBean",
			_formModelBean);
		httpServletRequest.setAttribute(
			"liferay-frontend:form-navigator-steps:formName", _formName);
		httpServletRequest.setAttribute(
			"liferay-frontend:form-navigator-steps:htmlBottom", _htmlBottom);
		httpServletRequest.setAttribute(
			"liferay-frontend:form-navigator-steps:htmlTop", _htmlTop);
		httpServletRequest.setAttribute(
			"liferay-frontend:form-navigator-steps:id", _id);
		httpServletRequest.setAttribute(
			"liferay-frontend:form-navigator-steps:showButtons",
			String.valueOf(_showButtons));
	}

	private String _backURL;
	private Object _formModelBean;
	private String _formName = "fm";
	private String _htmlBottom;
	private String _htmlTop;
	private String _id;
	private boolean _showButtons = true;

}