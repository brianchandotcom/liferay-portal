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

package com.liferay.staging.taglib.servlet.taglib;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.staging.taglib.internal.servlet.ServletContextUtil;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Akos Thurzo
 */
@ProviderType
public class ExportEntityTag extends IncludeTag {

	public void setClassName(String className) {
		_className = className;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	@Override
	protected void cleanUp() {
		_className = StringPool.BLANK;
		_classNameId = 0;
		_classPK = 0;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-staging:export-entity:className", _className);
		request.setAttribute(
			"liferay-staging:export-entity:classNameId", _classNameId);
		request.setAttribute("liferay-staging:export-entity:classPK", _classPK);
	}

	private static final String _PAGE = "/export_entity/page.jsp";

	private String _className = StringPool.BLANK;
	private long _classNameId;
	private long _classPK;

}