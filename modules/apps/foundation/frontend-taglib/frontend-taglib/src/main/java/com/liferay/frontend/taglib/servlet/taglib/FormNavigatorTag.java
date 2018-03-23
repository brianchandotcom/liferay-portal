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

import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorCategoryUtil;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class FormNavigatorTag extends IncludeTag {

	@Override
	public int doStartTag() {
		return EVAL_BODY_INCLUDE;
	}

	public void setBackURL(String backURL) {
		_backURL = backURL;
	}

	public void setFormModelBean(Object formModelBean) {
		_formModelBean = formModelBean;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setShowButtons(boolean showButtons) {
		_showButtons = showButtons;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_backURL = null;
		_formModelBean = null;
		_id = null;
		_showButtons = true;
	}

	protected String[] getCategoryKeys() {
		return FormNavigatorCategoryUtil.getKeys(_id);
	}

	@Override
	protected String getPage() {
		return "/form_navigator/page.jsp";
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-frontend:form-navigator:backURL", _backURL);
		request.setAttribute(
			"liferay-frontend:form-navigator:categoryKeys", getCategoryKeys());
		request.setAttribute(
			"liferay-frontend:form-navigator:formModelBean", _formModelBean);
		request.setAttribute("liferay-frontend:form-navigator:id", _id);
		request.setAttribute(
			"liferay-frontend:form-navigator:showButtons",
			String.valueOf(_showButtons));
	}

	private String _backURL;
	private Object _formModelBean;
	private String _id;
	private boolean _showButtons = true;

}