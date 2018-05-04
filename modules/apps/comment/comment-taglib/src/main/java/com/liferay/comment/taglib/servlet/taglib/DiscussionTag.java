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

package com.liferay.comment.taglib.servlet.taglib;

import com.liferay.comment.taglib.internal.servlet.ServletContextUtil;
import com.liferay.portal.kernel.comment.Discussion;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Charles May
 */
public class DiscussionTag extends IncludeTag {

	public void setAssetEntryVisible(boolean assetEntryVisible) {
		_assetEntryVisible = assetEntryVisible;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public void setDiscussion(Discussion discussion) {
		_discussion = discussion;
	}

	public void setFormAction(String formAction) {
		_formAction = formAction;
	}

	public void setFormName(String formName) {
		_formName = formName;
	}

	public void setHideControls(boolean hideControls) {
		_hideControls = hideControls;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());
	}

	public void setRatingsEnabled(boolean ratingsEnabled) {
		_ratingsEnabled = ratingsEnabled;
	}

	public void setRedirect(String redirect) {
		_redirect = redirect;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_assetEntryVisible = true;
		_className = null;
		_classPK = 0;
		_discussion = null;
		_formAction = null;
		_formName = "fm";
		_hideControls = false;
		_ratingsEnabled = true;
		_redirect = null;
		_userId = 0;
	}

	protected String getFormAction(HttpServletRequest request) {
		if (_formAction != null) {
			return _formAction;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		return themeDisplay.getPathMain() + "/portal/comment/discussion/edit";
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	protected String getPaginationURL(HttpServletRequest request) {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		String portletId = portletDisplay.getId();

		return StringBundler.concat(
			themeDisplay.getPathMain(),
			"/portal/comment/discussion/get_comments?p_p_isolated=1&",
			"portletId=", portletId);
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-comment:discussion:assetEntryVisible",
			String.valueOf(_assetEntryVisible));
		request.setAttribute(
			"liferay-comment:discussion:className", _className);
		request.setAttribute(
			"liferay-comment:discussion:classPK", String.valueOf(_classPK));

		if (_discussion != null) {
			request.setAttribute(
				"liferay-comment:discussion:discussion", _discussion);
		}

		request.setAttribute(
			"liferay-comment:discussion:formAction", getFormAction(request));
		request.setAttribute("liferay-comment:discussion:formName", _formName);
		request.setAttribute(
			"liferay-comment:discussion:hideControls",
			String.valueOf(_hideControls));
		request.setAttribute(
			"liferay-comment:discussion:paginationURL",
			getPaginationURL(request));
		request.setAttribute(
			"liferay-comment:discussion:ratingsEnabled",
			String.valueOf(_ratingsEnabled));
		request.setAttribute("liferay-comment:discussion:redirect", _redirect);
		request.setAttribute(
			"liferay-comment:discussion:userId", String.valueOf(_userId));
	}

	private static final String _PAGE = "/discussion/page.jsp";

	private boolean _assetEntryVisible = true;
	private String _className;
	private long _classPK;
	private Discussion _discussion;
	private String _formAction;
	private String _formName = "fm";
	private boolean _hideControls;
	private boolean _ratingsEnabled = true;
	private String _redirect;
	private long _userId;

}