/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.changeset.taglib.servlet.taglib;

import com.liferay.exportimport.changeset.taglib.internal.servlet.ServletContextUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Akos Thurzo
 */
public class ExportEntityTag extends IncludeTag {

	public String getClassName() {
		return _className;
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public String getUuid() {
		return _uuid;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_className = StringPool.BLANK;
		_classNameId = 0;
		_groupId = 0;
		_uuid = StringPool.BLANK;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute(
			"liferay-export-import-changeset:export-entity:className",
			_className);
		httpServletRequest.setAttribute(
			"liferay-export-import-changeset:export-entity:classNameId",
			_classNameId);
		httpServletRequest.setAttribute(
			"liferay-export-import-changeset:export-entity:groupId", _groupId);
		httpServletRequest.setAttribute(
			"liferay-export-import-changeset:export-entity:uuid", _uuid);
	}

	private static final String _PAGE = "/export_entity/page.jsp";

	private String _className = StringPool.BLANK;
	private long _classNameId;
	private long _groupId;
	private String _uuid = StringPool.BLANK;

}