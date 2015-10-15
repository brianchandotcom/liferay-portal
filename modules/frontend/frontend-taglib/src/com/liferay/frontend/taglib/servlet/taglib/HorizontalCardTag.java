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

import com.liferay.frontend.taglib.servlet.ServletContextUtil;
import com.liferay.portal.kernel.dao.search.ResultRow;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.IncludeTag;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Carlos Lancha
 */
public class HorizontalCardTag extends IncludeTag {

	@Override
	public int doStartTag() {
		return EVAL_BODY_INCLUDE;
	}

	public void setActionJsp(String actionJsp) {
		_actionJsp = actionJsp;
	}

	public void setActionJspServletContext(
		ServletContext actionJspServletContext) {

		_actionJspServletContext = actionJspServletContext;
	}

	public void setCheckboxChecked(boolean checkboxChecked) {
		_checkboxChecked = checkboxChecked;
	}

	public void setCheckboxCSSClass(String checkboxCSSClass) {
		_checkboxCSSClass = checkboxCSSClass;
	}

	public void setCheckboxData(Map<String, Object> checkboxData) {
		_checkboxData = checkboxData;
	}

	public void setCheckboxDisabled(boolean checkboxDisabled) {
		_checkboxDisabled = checkboxDisabled;
	}

	public void setCheckboxId(String checkboxId) {
		_checkboxId = checkboxId;
	}

	public void setCheckboxName(String checkboxName) {
		_checkboxName = checkboxName;
	}

	public void setCheckboxValue(String checkboxValue) {
		_checkboxValue = checkboxValue;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setData(Map<String, Object> data) {
		_data = data;
	}

	public void setIcon(String icon) {
		_icon = icon;
	}

	public void setImageCSSClass(String imageCSSClass) {
		_imageCSSClass = imageCSSClass;
	}

	public void setImageUrl(String imageUrl) {
		_imageUrl = imageUrl;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	public void setResultRow(ResultRow resultRow) {
		_resultRow = resultRow;
	}

	public void setRowChecker(RowChecker rowChecker) {
		_rowChecker = rowChecker;
	}

	public void setShowCheckbox(boolean showCheckbox) {
		_showCheckbox = showCheckbox;
	}

	public void setText(String text) {
		_text = text;
	}

	public void setUrl(String url) {
		_url = url;
	}

	@Override
	protected void cleanUp() {
		_actionJsp = null;
		_actionJspServletContext = null;
		_checkboxChecked = null;
		_checkboxCSSClass = null;
		_checkboxData = null;
		_checkboxDisabled = null;
		_checkboxId = null;
		_checkboxName = null;
		_checkboxValue = null;
		_cssClass = null;
		_data = null;
		_icon = null;
		_imageUrl = null;
		_imageCSSClass = null;
		_resultRow = null;
		_rowChecker = null;
		_showCheckbox = false;
		_text = null;
		_url = null;
	}

	protected ServletContext getActionJspServletContext() {
		if (_actionJspServletContext != null) {
			return _actionJspServletContext;
		}

		return servletContext;
	}

	@Override
	protected String getPage() {
		return "/horizontal_card/page.jsp";
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute("liferay-frontend:horizontal-card:actionJsp", _actionJsp);
		request.setAttribute(
			"liferay-frontend:horizontal-card:actionJspServletContext",
			getActionJspServletContext());
		request.setAttribute(
			"liferay-frontend:horizontal-card:checkboxChecked",
			String.valueOf(_checkboxChecked));
		request.setAttribute(
			"liferay-frontend:horizontal-card:checkboxCSSClass", _checkboxCSSClass);
		request.setAttribute(
			"liferay-frontend:horizontal-card:checkboxData", _checkboxData);
		request.setAttribute(
			"liferay-frontend:horizontal-card:checkboxDisabled",
			String.valueOf(_checkboxDisabled));
		request.setAttribute("liferay-frontend:horizontal-card:checkboxId", _checkboxId);
		request.setAttribute(
			"liferay-frontend:horizontal-card:checkboxName", _checkboxName);
		request.setAttribute(
			"liferay-frontend:horizontal-card:checkboxValue", _checkboxValue);
		request.setAttribute("liferay-frontend:horizontal-card:cssClass", _cssClass);
		request.setAttribute("liferay-frontend:horizontal-card:data", _data);
		request.setAttribute("liferay-frontend:horizontal-card:icon", _icon);
		request.setAttribute(
			"liferay-frontend:horizontal-card:imageCSSClass", _imageCSSClass);
		request.setAttribute("liferay-frontend:horizontal-card:imageUrl", _imageUrl);
		request.setAttribute("liferay-frontend:horizontal-card:resultRow", _resultRow);
		request.setAttribute("liferay-frontend:horizontal-card:rowChecker", _rowChecker);

		if (_rowChecker != null) {
			_showCheckbox = true;
		}

		request.setAttribute(
			"liferay-frontend:horizontal-card:showCheckbox", _showCheckbox);

		request.setAttribute("liferay-frontend:horizontal-card:text", _text);
		request.setAttribute("liferay-frontend:horizontal-card:url", _url);

		request.setAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW, _resultRow);
	}

	private String _actionJsp;
	private ServletContext _actionJspServletContext;
	private Boolean _checkboxChecked;
	private String _checkboxCSSClass;
	private Map<String, Object> _checkboxData;
	private Boolean _checkboxDisabled;
	private String _checkboxId;
	private String _checkboxName;
	private String _checkboxValue;
	private String _cssClass;
	private Map<String, Object> _data;
	private String _icon;
	private String _imageCSSClass;
	private String _imageUrl;
	private ResultRow _resultRow;
	private RowChecker _rowChecker;
	private boolean _showCheckbox;
	private String _text;
	private String _url;


}