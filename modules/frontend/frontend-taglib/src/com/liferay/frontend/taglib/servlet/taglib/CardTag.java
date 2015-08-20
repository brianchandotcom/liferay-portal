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
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.search.ResultRow;
import com.liferay.taglib.util.IncludeTag;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Carlos Lancha
 */
public class CardTag extends IncludeTag {

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

	public void setFooter(String footer) {
		_footer = footer;
	}

	public void setHeader(String header) {
		_header = header;
	}

	public void setHorizontal(boolean horizontal) {
		_horizontal = horizontal;
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

	public void setShowCheckbox(boolean showCheckbox) {
		_showCheckbox = showCheckbox;
	}

	public void setSmallImageCSSClass(String smallImageCSSClass) {
		_smallImageCSSClass = smallImageCSSClass;
	}

	public void setSmallImageUrl(String smallImageUrl) {
		_smallImageUrl = smallImageUrl;
	}

	public void setSubtitle(String subtitle) {
		_subtitle = subtitle;
	}

	public void setTitle(String title) {
		_title = title;
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
		_checkboxDisabled = null;
		_checkboxId = null;
		_checkboxName = null;
		_checkboxValue = null;
		_cssClass = null;
		_data = null;
		_footer = null;
		_header = null;
		_horizontal = false;
		_imageUrl = null;
		_imageCSSClass = null;
		_showCheckbox = true;
		_smallImageCSSClass = null;
		_smallImageUrl = null;
		_subtitle = null;
		_title = null;
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
		if (_horizontal) {
			return "/card/horizontal.jsp";
		}

		return "/card/vertical.jsp";
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute("liferay-frontend:card:actionJsp", _actionJsp);
		request.setAttribute(
			"liferay-frontend:card:actionJspServletContext",
			getActionJspServletContext());

		boolean checkboxChecked = false;

		ResultRow resultRow = (ResultRow)request.getAttribute(
			WebKeys.SEARCH_CONTAINER_RESULT_ROW);
		RowChecker rowChecker = (RowChecker)request.getAttribute(
			WebKeys.SEARCH_CONTAINER_RESULT_ROW_CHECKER);

		if ((_checkboxChecked == null) && (resultRow != null) &&
			(rowChecker != null)) {

			checkboxChecked = rowChecker.isChecked(resultRow.getObject());
		}
		else {
			checkboxChecked = _checkboxChecked.booleanValue();
		}

		request.setAttribute(
			"liferay-frontend:card:checkboxChecked",
			String.valueOf(checkboxChecked));

		String checkboxCssClass = _checkboxCSSClass;

		if (Validator.isNull(checkboxCssClass) && (rowChecker != null)) {
			checkboxCssClass = rowChecker.getCssClass();
		}

		request.setAttribute(
			"liferay-frontend:card:checkboxCSSClass", checkboxCssClass);

		boolean checkboxDisabled = false;

		if ((_checkboxDisabled == null) && (resultRow != null) &&
			(rowChecker != null)) {

			checkboxDisabled = rowChecker.isDisabled(resultRow.getObject());
		}
		else {
			checkboxDisabled = _checkboxChecked.booleanValue();
		}

		request.setAttribute(
			"liferay-frontend:card:checkboxDisabled",
			String.valueOf(checkboxDisabled));

		String checkboxId = _checkboxId;

		if (Validator.isNull(checkboxId) && (resultRow != null) &&
			(rowChecker != null)) {

			checkboxId = rowChecker.getRowIds() + resultRow.getPrimaryKey();
		}

		request.setAttribute("liferay-frontend:card:checkboxId", checkboxId);

		String checkboxName = _checkboxName;

		if (Validator.isNull(checkboxName) && (rowChecker != null)) {
			checkboxName = rowChecker.getRowIds();
		}

		request.setAttribute(
			"liferay-frontend:card:checkboxName", checkboxName);

		String checkboxValue = _checkboxValue;

		if (Validator.isNull(checkboxValue) && (resultRow != null)) {
			checkboxValue = resultRow.getPrimaryKey();
		}

		request.setAttribute(
			"liferay-frontend:card:checkboxValue", checkboxValue);

		request.setAttribute("liferay-frontend:card:cssClass", _cssClass);
		request.setAttribute("liferay-frontend:card:data", _data);
		request.setAttribute("liferay-frontend:card:footer", _footer);
		request.setAttribute("liferay-frontend:card:header", _header);
		request.setAttribute(
			"liferay-frontend:card:imageCSSClass", _imageCSSClass);
		request.setAttribute("liferay-frontend:card:imageUrl", _imageUrl);
		request.setAttribute(
			"liferay-frontend:card:showCheckbox", _showCheckbox);
		request.setAttribute(
			"liferay-frontend:card:smallImageCSSClass", _smallImageCSSClass);
		request.setAttribute(
			"liferay-frontend:card:smallImageUrl", _smallImageUrl);
		request.setAttribute("liferay-frontend:card:subtitle", _subtitle);
		request.setAttribute("liferay-frontend:card:title", _title);
		request.setAttribute("liferay-frontend:card:url", _url);
	}

	private String _actionJsp;
	private ServletContext _actionJspServletContext;
	private Boolean _checkboxChecked;
	private String _checkboxCSSClass;
	private Boolean _checkboxDisabled;
	private String _checkboxId;
	private String _checkboxName;
	private String _checkboxValue;
	private String _cssClass;
	private Map<String, Object> _data;
	private String _footer;
	private String _header;
	private boolean _horizontal = false;
	private String _imageCSSClass;
	private String _imageUrl;
	private boolean _showCheckbox;
	private String _smallImageCSSClass;
	private String _smallImageUrl;
	private String _subtitle;
	private String _title;
	private String _url;

}