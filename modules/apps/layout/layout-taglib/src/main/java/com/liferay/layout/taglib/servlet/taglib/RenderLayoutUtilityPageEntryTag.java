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

import com.liferay.layout.provider.LayoutStructureProvider;
import com.liferay.layout.taglib.internal.servlet.ServletContextUtil;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.utility.page.model.LayoutUtilityPageEntry;
import com.liferay.layout.utility.page.service.LayoutUtilityPageEntryLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.service.SegmentsExperienceLocalServiceUtil;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Víctor Galán
 */
public class RenderLayoutUtilityPageEntryTag extends IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		LayoutUtilityPageEntry layoutUtilityPageEntry =
			_getLayoutUtilityPageEntry();

		if (layoutUtilityPageEntry != null) {
			return SKIP_BODY;
		}

		return EVAL_BODY_INCLUDE;
	}

	public String getType() {
		return _type;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());
	}

	public void setType(String type) {
		_type = type;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_type = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		super.setAttributes(httpServletRequest);

		httpServletRequest.setAttribute(
			"liferay-layout:render-layout-utility-page-entry:layoutStructure",
			_getLayoutStructure());
	}

	private LayoutStructure _getLayoutStructure() {
		LayoutUtilityPageEntry layoutUtilityPageEntry =
			_getLayoutUtilityPageEntry();

		if (layoutUtilityPageEntry == null) {
			return null;
		}

		LayoutStructureProvider layoutStructureProvider =
			ServletContextUtil.getLayoutStructureHelper();

		long defaultSegmentsExperienceId =
			SegmentsExperienceLocalServiceUtil.fetchDefaultSegmentsExperienceId(
				layoutUtilityPageEntry.getPlid());

		return layoutStructureProvider.getLayoutStructure(
			layoutUtilityPageEntry.getPlid(), defaultSegmentsExperienceId);
	}

	private LayoutUtilityPageEntry _getLayoutUtilityPageEntry() {
		HttpServletRequest httpServletRequest = getRequest();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return LayoutUtilityPageEntryLocalServiceUtil.
			fetchDefaultLayoutUtilityPageEntry(
				themeDisplay.getScopeGroupId(), getType());
	}

	private static final String _PAGE =
		"/render_layout_utility_page_entry/page.jsp";

	private String _type;

}