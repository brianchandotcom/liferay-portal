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

package com.liferay.exportimport.changeset.taglib.servlet.taglib;

import com.liferay.exportimport.changeset.Changeset;
import com.liferay.exportimport.changeset.ChangesetManager;
import com.liferay.exportimport.changeset.ChangesetManagerUtil;
import com.liferay.exportimport.changeset.taglib.internal.servlet.ServletContextUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Máté Thurzó
 */
public class PublishChangesetTag extends IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		Object themeDisplayObj = pageContext.getAttribute("themeDisplay");

		ThemeDisplay themeDisplay = null;

		if ((themeDisplayObj != null) &&
			(themeDisplayObj instanceof ThemeDisplay)) {

			themeDisplay = (ThemeDisplay)themeDisplayObj;
		}

		long groupId = _groupId;

		if ((groupId <= 0) && (themeDisplay == null)) {
			Object groupIdObj = pageContext.getAttribute("groupId");

			groupId = GetterUtil.getLong(groupIdObj);
		}
		else if (groupId <= 0) {
			groupId = themeDisplay.getScopeGroupId();
		}

		_changesetUuid = _changeset.getUuid();
		_groupId = groupId;

		ChangesetManager changesetManager =
			ChangesetManagerUtil.getChangesetManager();

		changesetManager.addChangeset(_changeset);

		return EVAL_BODY_INCLUDE;
	}

	public Changeset getChangeset() {
		return _changeset;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setChangeset(Changeset changeset) {
		_changeset = changeset;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_changeset = null;
		_changesetUuid = StringPool.BLANK;
		_groupId = 0;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute(
			"liferay-export-import-changeset:publish-changeset:changesetUuid",
			_changesetUuid);
		httpServletRequest.setAttribute(
			"liferay-export-import-changeset:publish-changeset:groupId",
			_groupId);
	}

	private static final String _PAGE = "/publish_changeset/page.jsp";

	private Changeset _changeset;
	private String _changesetUuid = StringPool.BLANK;
	private long _groupId;

}