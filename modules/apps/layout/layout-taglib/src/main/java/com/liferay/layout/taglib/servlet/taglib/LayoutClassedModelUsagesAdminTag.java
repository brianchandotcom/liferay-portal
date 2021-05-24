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

package com.liferay.layout.taglib.servlet.taglib;

import com.liferay.fragment.constants.FragmentActionKeys;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorWebKeys;
import com.liferay.layout.taglib.internal.servlet.ServletContextUtil;
import com.liferay.layout.taglib.internal.servlet.taglib.util.LayoutClassedModelUsagesTaglibUtil;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Eudaldo Alonso
 */
public class LayoutClassedModelUsagesAdminTag extends IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		LayoutClassedModelUsagesTaglibUtil.recordLayoutClassedModelUsage(
			getClassName(), getClassPK());

		HttpServletRequest httpServletRequest = getRequest();

		httpServletRequest.setAttribute(
			ContentPageEditorWebKeys.FRAGMENT_COLLECTION_CONTRIBUTOR_TRACKER,
			ServletContextUtil.getFragmentCollectionContributorTracker());
		httpServletRequest.setAttribute(
			FragmentActionKeys.FRAGMENT_RENDERER_TRACKER,
			ServletContextUtil.getFragmentRendererTracker());

		return super.doStartTag();
	}

	public String getClassName() {
		return _className;
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_className = null;
		_classPK = 0;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute(
			"liferay-layout:layout-classed-model-usages-admin:className",
			_className);
		httpServletRequest.setAttribute(
			"liferay-layout:layout-classed-model-usages-admin:classPK",
			String.valueOf(_classPK));
	}

	private static final String _PAGE =
		"/layout_classed_model_usages_admin/page.jsp";

	private String _className;
	private long _classPK;

}