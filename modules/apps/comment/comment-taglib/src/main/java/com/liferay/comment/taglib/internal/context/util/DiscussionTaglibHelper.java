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

package com.liferay.comment.taglib.internal.context.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public class DiscussionTaglibHelper {

	public DiscussionTaglibHelper(HttpServletRequest httpServletRequest) {
		_httpServletRequest = httpServletRequest;
	}

	public String getClassName() {
		if (_className == null) {
			_className = (String)_httpServletRequest.getAttribute(
				"liferay-comment:discussion:className");
		}

		return _className;
	}

	public long getClassPK() {
		if (_classPK == null) {
			_classPK = GetterUtil.getLong(
				_httpServletRequest.getAttribute(
					"liferay-comment:discussion:classPK"));
		}

		return _classPK;
	}

	public String getFormAction() {
		if (_formAction == null) {
			_formAction = (String)_httpServletRequest.getAttribute(
				"liferay-comment:discussion:formAction");
		}

		return _formAction;
	}

	public String getFormName() {
		if (_formName == null) {
			_formName = (String)_httpServletRequest.getAttribute(
				"liferay-comment:discussion:formName");
		}

		return _formName;
	}

	public String getPaginationURL() {
		if (_paginationURL == null) {
			_paginationURL = (String)_httpServletRequest.getAttribute(
				"liferay-comment:discussion:paginationURL");
		}

		return _paginationURL;
	}

	public String getRedirect() {
		if (_redirect == null) {
			_redirect = (String)_httpServletRequest.getAttribute(
				"liferay-comment:discussion:redirect");
		}

		return _redirect;
	}

	public String getSubscriptionClassName() {
		return _CLASS_NAME + StringPool.UNDERLINE + getClassName();
	}

	public long getUserId() {
		if (_userId == null) {
			_userId = GetterUtil.getLong(
				_httpServletRequest.getAttribute(
					"liferay-comment:discussion:userId"));
		}

		return _userId;
	}

	public boolean isAssetEntryVisible() {
		if (_assetEntryVisible == null) {
			_assetEntryVisible = GetterUtil.getBoolean(
				_httpServletRequest.getAttribute(
					"liferay-comment:discussion:assetEntryVisible"));
		}

		return _assetEntryVisible;
	}

	public boolean isHideControls() {
		if (_hideControls == null) {
			_hideControls = GetterUtil.getBoolean(
				_httpServletRequest.getAttribute(
					"liferay-comment:discussion:hideControls"));
		}

		return _hideControls;
	}

	public boolean isRatingsEnabled() {
		if (_ratingsEnabled == null) {
			_ratingsEnabled = GetterUtil.getBoolean(
				_httpServletRequest.getAttribute(
					"liferay-comment:discussion:ratingsEnabled"));
		}

		return _ratingsEnabled;
	}

	private static final String _CLASS_NAME =
		"com.liferay.message.boards.model.MBDiscussion";

	private Boolean _assetEntryVisible;
	private String _className;
	private Long _classPK;
	private String _formAction;
	private String _formName;
	private Boolean _hideControls;
	private final HttpServletRequest _httpServletRequest;
	private String _paginationURL;
	private Boolean _ratingsEnabled;
	private String _redirect;
	private Long _userId;

}