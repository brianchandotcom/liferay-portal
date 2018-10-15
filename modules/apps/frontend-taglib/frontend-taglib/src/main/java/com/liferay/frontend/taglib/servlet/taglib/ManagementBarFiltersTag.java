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

import com.liferay.frontend.taglib.internal.servlet.ServletContextUtil;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTag;

/**
 * @author Eudaldo Alonso
 */
public class ManagementBarFiltersTag extends IncludeTag implements BodyTag {

	@Override
	public int doEndTag() {
		BodyContent bodyContent = getBodyContent();

		if (bodyContent != null) {
			ManagementBarTag managementBarTag =
				(ManagementBarTag)findAncestorWithClass(
					this, ManagementBarTag.class);

			managementBarTag.setFilters(bodyContent.getString());
		}

		return EVAL_PAGE;
	}

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		return super.doStartTag();
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected int processStartTag() throws Exception {
		return EVAL_BODY_BUFFERED;
	}

	private static final String _ATTRIBUTE_NAMESPACE =
		"liferay-frontend:management-bar-filters:";

	private static final String _PAGE = "/management_bar_filters/page.jsp";

}