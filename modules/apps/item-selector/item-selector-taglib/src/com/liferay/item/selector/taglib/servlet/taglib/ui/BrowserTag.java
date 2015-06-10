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

package com.liferay.item.selector.taglib.servlet.taglib.ui;

import com.liferay.item.selector.taglib.ReturnType;
import com.liferay.item.selector.taglib.util.ServletContextUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.util.IncludeTag;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Roberto Díaz
 */
public class BrowserTag extends IncludeTag {

	public void setDisplayStyle(String displayStyle) {
		_displayStyle = displayStyle;
	}

	public void setDisplayStyleURL(PortletURL displayStyleURL) {
		_displayStyleURL = displayStyleURL;
	}

	public void setIdPrefix(String idPrefix) {
		_idPrefix = idPrefix;
	}

	public void setItemSelectedEventName(String itemSelectedEventName) {
		_itemSelectedEventName = itemSelectedEventName;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	public void setReturnType(ReturnType returnType) {
		_returnType = returnType;
	}

	public void setSearchContainer(SearchContainer<?> searchContainer) {
		_searchContainer = searchContainer;
	}

	public void setTabName(String tabName) {
		_tabName = tabName;
	}

	public void setUploadMessage(String uploadMessage) {
		_uploadMessage = uploadMessage;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_displayStyle = "icon";
		_displayStyleURL = null;
		_idPrefix = null;
		_itemSelectedEventName = null;
		_returnType = null;
		_searchContainer = null;
		_tabName = null;
		_uploadMessage = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	protected String getUploadMessage() {
		if (Validator.isNotNull(_uploadMessage)) {
			return _uploadMessage;
		}

		return LanguageUtil.get(
			request,
			"upload-a-document-by-dropping-it-right-here-or-by-pressing-plus-" +
				"icon");
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-ui:item-selector-browser:displayStyle", _displayStyle);
		request.setAttribute(
			"liferay-ui:item-selector-browser:displayStyleURL",
			_displayStyleURL);
		request.setAttribute(
			"liferay-ui:item-selector-browser:idPrefix", _idPrefix);
		request.setAttribute(
			"liferay-ui:item-selector-browser:itemSelectedEventName",
			_itemSelectedEventName);
		request.setAttribute(
			"liferay-ui:item-selector-browser:returnType", _returnType);
		request.setAttribute(
			"liferay-ui:item-selector-browser:searchContainer",
			_searchContainer);
		request.setAttribute(
			"liferay-ui:item-selector-browser:tabName", _tabName);
		request.setAttribute(
			"liferay-ui:item-selector-browser:uploadMessage",
			getUploadMessage());
	}

	private static final String _PAGE = "/taglib/ui/browser/page.jsp";

	private String _displayStyle;
	private PortletURL _displayStyleURL;
	private String _idPrefix;
	private String _itemSelectedEventName;
	private ReturnType _returnType;
	private SearchContainer<?> _searchContainer;
	private String _tabName;
	private String _uploadMessage;

}