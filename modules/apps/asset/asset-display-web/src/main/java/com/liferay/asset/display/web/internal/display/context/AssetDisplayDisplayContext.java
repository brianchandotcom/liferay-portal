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

package com.liferay.asset.display.web.internal.display.context;

import com.liferay.portal.kernel.util.ParamUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class AssetDisplayDisplayContext {

	public AssetDisplayDisplayContext(HttpServletRequest httpServletRequest) {
		_httpServletRequest = httpServletRequest;
	}

	public int getAbstractLength() {
		if (_abstractLength != null) {
			return _abstractLength;
		}

		_abstractLength = ParamUtil.getInteger(
			_httpServletRequest, "abstractLength");

		return _abstractLength;
	}

	public String getClassName() {
		if (_className != null) {
			return _className;
		}

		_className = ParamUtil.getString(_httpServletRequest, "className");

		return _className;
	}

	public long getClassPK() {
		if (_classPK != null) {
			return _classPK;
		}

		_classPK = ParamUtil.getLong(_httpServletRequest, "classPK");

		return _classPK;
	}

	public String getTemplate() {
		if (_template != null) {
			return _template;
		}

		_template = ParamUtil.getString(_httpServletRequest, "template");

		return _template;
	}

	public String getViewURL() {
		if (_viewURL != null) {
			return _viewURL;
		}

		_viewURL = ParamUtil.getString(_httpServletRequest, "viewURL");

		return _viewURL;
	}

	public boolean isShowComments() {
		if (_showComments != null) {
			return _showComments;
		}

		_showComments = ParamUtil.getBoolean(
			_httpServletRequest, "showComments");

		return _showComments;
	}

	public boolean isShowExtraInfo() {
		if (_showExtraInfo != null) {
			return _showExtraInfo;
		}

		_showExtraInfo = ParamUtil.getBoolean(
			_httpServletRequest, "showExtraInfo");

		return _showExtraInfo;
	}

	public boolean isShowHeader() {
		if (_showHeader != null) {
			return _showHeader;
		}

		_showHeader = ParamUtil.getBoolean(_httpServletRequest, "showHeader");

		return _showHeader;
	}

	private Integer _abstractLength;
	private String _className;
	private Long _classPK;
	private final HttpServletRequest _httpServletRequest;
	private Boolean _showComments;
	private Boolean _showExtraInfo;
	private Boolean _showHeader;
	private String _template;
	private String _viewURL;

}