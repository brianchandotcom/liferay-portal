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

package com.liferay.portal.comment.display.context.util;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public class DiscussionTaglibHelper {

	public DiscussionTaglibHelper(HttpServletRequest request) {
		_request = request;
	}

	public String getClassName() {
		if (_className == null) {
			_className = _getAttribute("className");
		}

		return _className;
	}

	public long getClassPK() {
		if (_classPK == null) {
			_classPK = GetterUtil.getLong(_getAttribute("classPK"));
		}

		return _classPK;
	}

	public String getFormAction() {
		if (_formAction == null) {
			_formAction = _getAttribute("formAction");
		}

		return _formAction;
	}

	public String getFormName() {
		if (_formName == null) {
			_formName = _getAttribute("formName");
		}

		return _formName;
	}

	public String getPaginationURL() {
		if (_paginationURL == null) {
			_paginationURL = _getAttribute("paginationURL");
		}

		return _paginationURL;
	}

	public String getRedirect() {
		if (_redirect == null) {
			_redirect = _getAttribute("redirect");
		}

		return _redirect;
	}

	public long getUserId() {
		if (_userId == null) {
			_userId = GetterUtil.getLong(_getAttribute("userId"));
		}

		return _userId;
	}

	public boolean isAssetEntryVisible() {
		if (_assetEntryVisible == null) {
			_assetEntryVisible = GetterUtil.getBoolean(
				_getAttribute("assetEntryVisible"));
		}

		return _assetEntryVisible;
	}

	public boolean isHideControls() {
		if (_hideControls == null) {
			_hideControls = GetterUtil.getBoolean(
				_getAttribute("hideControls"));
		}

		return _hideControls;
	}

	public boolean isRatingsEnabled() {
		if (_ratingsEnabled == null) {
			_ratingsEnabled = GetterUtil.getBoolean(
				_getAttribute("ratingsEnabled"));
		}

		return _ratingsEnabled;
	}

	protected HttpServletRequest getRequest() {
		return _request;
	}

	private String _getAttribute(String name) {
		HttpServletRequest request = getRequest();

		String value = (String)request.getAttribute(_LEGACY_PREFIX + name);

		if (Validator.isNotNull(value)) {
			return value;
		}

		return (String)request.getAttribute(_PREFIX + name);
	}

	private static final String _LEGACY_PREFIX = "liferay-ui:discussion:";

	private static final String _PREFIX = "liferay-comment:discussion:";

	private Boolean _assetEntryVisible;
	private String _className;
	private Long _classPK;
	private String _formAction;
	private String _formName;
	private Boolean _hideControls;
	private String _paginationURL;
	private Boolean _ratingsEnabled;
	private String _redirect;
	private final HttpServletRequest _request;
	private Long _userId;

}