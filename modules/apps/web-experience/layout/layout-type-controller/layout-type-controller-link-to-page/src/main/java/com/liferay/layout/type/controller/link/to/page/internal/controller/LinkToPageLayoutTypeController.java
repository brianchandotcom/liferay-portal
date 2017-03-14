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

package com.liferay.layout.type.controller.link.to.page.internal.controller;

import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypeController;
import com.liferay.portal.kernel.model.impl.BaseLayoutTypeControllerImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.taglib.servlet.PipingServletResponse;

import javax.servlet.ServletContext;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true,
	property = {"layout.type=" + LayoutConstants.TYPE_LINK_TO_LAYOUT},
	service = LayoutTypeController.class
)
public class LinkToPageLayoutTypeController
	extends BaseLayoutTypeControllerImpl {

	@Override
	public String getType() {
		return LayoutConstants.TYPE_LINK_TO_LAYOUT;
	}

	@Override
	public String getURL() {
		Filter filter = new Filter(getType());

		return GetterUtil.getString(
			PropsUtil.get(PropsKeys.LAYOUT_URL, filter), _URL);
	}

	@Override
	public String includeEditContent(
			HttpServletRequest request, HttpServletResponse response,
			Layout layout)
		throws Exception {

		request.setAttribute(WebKeys.SEL_LAYOUT, layout);

		return super.includeEditContent(request, response, layout);
	}

	@Override
	public boolean isBrowsable() {
		Filter filter = new Filter(getType());

		return GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.LAYOUT_BROWSABLE, filter), true);
	}

	@Override
	public boolean isFirstPageable() {
		Filter filter = new Filter(getType());

		return GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.LAYOUT_FIRST_PAGEABLE, filter));
	}

	@Override
	public boolean isFullPageDisplayable() {
		Filter filter = new Filter(getType());

		return GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.FULL_PAGE_DISPLAYABLE, filter));
	}

	@Override
	public boolean isParentable() {
		Filter filter = new Filter(getType());

		return GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.LAYOUT_PARENTABLE, filter));
	}

	@Override
	public boolean isSitemapable() {
		Filter filter = new Filter(getType());

		return GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.LAYOUT_SITEMAPABLE, filter));
	}

	@Override
	public boolean isURLFriendliable() {
		Filter filter = new Filter(getType());

		return GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.LAYOUT_URL_FRIENDLIABLE, filter), true);
	}

	@Override
	protected ServletResponse createServletResponse(
		HttpServletResponse response, UnsyncStringWriter unsyncStringWriter) {

		return new PipingServletResponse(response, unsyncStringWriter);
	}

	@Override
	protected String getEditPage() {
		return _EDIT_PAGE;
	}

	@Override
	protected String getViewPage() {
		Filter filter = new Filter(getType());

		return GetterUtil.getString(
			PropsUtil.get(PropsKeys.LAYOUT_VIEW_PAGE, filter));
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.layout.type.controller.link.to.page)",
		unbind = "-"
	)
	protected void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	private static final String _EDIT_PAGE = "/layout/edit/link_to_layout.jsp";

	private static final String _URL =
		"${liferay:mainPath}/portal/layout?p_v_l_s_g_id=${liferay:pvlsgid}&" +
			"groupId=${liferay:groupId}&privateLayout=${privateLayout}&" +
				"layoutId=${linkToLayoutId}";

}