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

package com.liferay.layout.type.controller.display.page.internal.controller;

import com.liferay.fragment.constants.FragmentActionKeys;
import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.renderer.FragmentRendererController;
import com.liferay.fragment.renderer.FragmentRendererTracker;
import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;
import com.liferay.item.selector.ItemSelector;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorWebKeys;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypeController;
import com.liferay.portal.kernel.model.impl.BaseLayoutTypeControllerImpl;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.servlet.TransferHeadersHelperUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.servlet.PipingServletResponse;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Juergen Kappler
 */
@Component(
	immediate = true,
	property = "layout.type=" + LayoutConstants.TYPE_ASSET_DISPLAY,
	service = LayoutTypeController.class
)
public class DisplayPageLayoutTypeController
	extends BaseLayoutTypeControllerImpl {

	@Override
	public String getType() {
		return LayoutConstants.TYPE_PORTLET;
	}

	@Override
	public String getURL() {
		return _URL;
	}

	@Override
	public String includeEditContent(
		HttpServletRequest request, HttpServletResponse response,
		Layout layout) {

		return StringPool.BLANK;
	}

	@Override
	public boolean includeLayoutContent(
			HttpServletRequest request, HttpServletResponse response,
			Layout layout)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		String layoutMode = ParamUtil.getString(
			request, "p_l_mode", Constants.VIEW);

		if (layoutMode.equals(Constants.EDIT) &&
			!LayoutPermissionUtil.contains(
				themeDisplay.getPermissionChecker(), layout,
				ActionKeys.UPDATE)) {

			layoutMode = Constants.VIEW;
		}

		if (layoutMode.equals(Constants.EDIT)) {
			request.setAttribute(
				ContentPageEditorWebKeys.
					FRAGMENT_COLLECTION_CONTRIBUTOR_TRACKER,
				_fragmentCollectionContributorTracker);

			request.setAttribute(
				ContentPageEditorWebKeys.ITEM_SELECTOR, _itemSelector);
		}

		request.setAttribute(
			FragmentActionKeys.FRAGMENT_RENDERER_CONTROLLER,
			_fragmentRendererController);

		String page = getViewPage();

		if (layoutMode.equals(Constants.EDIT)) {
			page = _EDIT_PAGE;
		}

		RequestDispatcher requestDispatcher =
			TransferHeadersHelperUtil.getTransferHeadersRequestDispatcher(
				servletContext.getRequestDispatcher(page));

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		ServletResponse servletResponse = createServletResponse(
			response, unsyncStringWriter);

		String contentType = servletResponse.getContentType();

		String includeServletPath = (String)request.getAttribute(
			RequestDispatcher.INCLUDE_SERVLET_PATH);

		try {
			LayoutPageTemplateEntry layoutPageTemplateEntry =
				_fetchLayoutPageTemplateEntry(layout);

			if (layoutPageTemplateEntry != null) {
				request.setAttribute(
					ContentPageEditorWebKeys.CLASS_NAME,
					LayoutPageTemplateEntry.class.getName());

				request.setAttribute(
					ContentPageEditorWebKeys.CLASS_PK,
					layoutPageTemplateEntry.getLayoutPageTemplateEntryId());
			}

			request.setAttribute(
				FragmentActionKeys.FRAGMENT_RENDERER_TRACKER,
				_fragmentRendererTracker);
			request.setAttribute(
				InfoDisplayWebKeys.INFO_DISPLAY_CONTRIBUTOR_TRACKER,
				_infoDisplayContributorTracker);

			addAttributes(request);

			requestDispatcher.include(request, servletResponse);
		}
		finally {
			removeAttributes(request);

			request.setAttribute(
				RequestDispatcher.INCLUDE_SERVLET_PATH, includeServletPath);
		}

		if (contentType != null) {
			response.setContentType(contentType);
		}

		request.setAttribute(
			WebKeys.LAYOUT_CONTENT, unsyncStringWriter.getStringBundler());

		return false;
	}

	@Override
	public boolean isFirstPageable() {
		return true;
	}

	@Override
	public boolean isFullPageDisplayable() {
		return true;
	}

	@Override
	public boolean isInstanceable() {
		return false;
	}

	@Override
	public boolean isParentable() {
		return false;
	}

	@Override
	public boolean isSitemapable() {
		return false;
	}

	@Override
	public boolean isURLFriendliable() {
		return true;
	}

	@Override
	protected ServletResponse createServletResponse(
		HttpServletResponse response, UnsyncStringWriter unsyncStringWriter) {

		return new PipingServletResponse(response, unsyncStringWriter);
	}

	@Override
	protected String getEditPage() {
		return null;
	}

	@Override
	protected String getViewPage() {
		return _VIEW_PAGE;
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.layout.type.controller.display.page)",
		unbind = "-"
	)
	protected void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	private LayoutPageTemplateEntry _fetchLayoutPageTemplateEntry(
		Layout layout) {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.
				fetchLayoutPageTemplateEntryByPlid(layout.getPlid());

		if (layoutPageTemplateEntry != null) {
			return layoutPageTemplateEntry;
		}

		if (layout.getClassNameId() == _portal.getClassNameId(Layout.class)) {
			Layout publishedLayout = _layoutLocalService.fetchLayout(
				layout.getClassPK());

			return _layoutPageTemplateEntryLocalService.
				fetchLayoutPageTemplateEntryByPlid(publishedLayout.getPlid());
		}

		return null;
	}

	private static final String _EDIT_PAGE = "/layout/edit/display_page.jsp";

	private static final String _URL =
		"${liferay:mainPath}/portal/layout?p_l_id=${liferay:plid}" +
			"&p_v_l_s_g_id=${liferay:pvlsgid}";

	private static final String _VIEW_PAGE = "/layout/view/display_page.jsp";

	@Reference
	private FragmentCollectionContributorTracker
		_fragmentCollectionContributorTracker;

	@Reference
	private FragmentRendererController _fragmentRendererController;

	@Reference
	private FragmentRendererTracker _fragmentRendererTracker;

	@Reference
	private InfoDisplayContributorTracker _infoDisplayContributorTracker;

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Reference
	private Portal _portal;

}