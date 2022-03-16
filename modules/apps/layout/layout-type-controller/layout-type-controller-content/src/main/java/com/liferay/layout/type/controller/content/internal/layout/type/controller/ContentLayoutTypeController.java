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

package com.liferay.layout.type.controller.content.internal.layout.type.controller;

import com.liferay.layout.content.page.editor.constants.ContentPageEditorWebKeys;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.security.permission.resource.LayoutContentModelResourcePermission;
import com.liferay.layout.type.controller.BaseLayoutTypeControllerImpl;
import com.liferay.petra.io.unsync.UnsyncStringWriter;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypeController;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.permission.LayoutPermission;
import com.liferay.portal.kernel.servlet.PipingServletResponse;
import com.liferay.portal.kernel.servlet.TransferHeadersHelper;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

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
	immediate = true, property = "layout.type=" + LayoutConstants.TYPE_CONTENT,
	service = LayoutTypeController.class
)
public class ContentLayoutTypeController extends BaseLayoutTypeControllerImpl {

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
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, Layout layout) {

		return StringPool.BLANK;
	}

	@Override
	public boolean includeLayoutContent(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Layout layout)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Boolean hasUpdatePermissions = null;

		if (layout.isDraftLayout()) {
			Layout curLayout = _layoutLocalService.fetchLayout(
				layout.getClassPK());

			if (curLayout.isPending()) {
				curLayout = layout;
			}

			hasUpdatePermissions = _hasUpdatePermissions(
				themeDisplay.getPermissionChecker(), curLayout);

			if (!hasUpdatePermissions) {
				throw new PrincipalException.MustHavePermission(
					themeDisplay.getPermissionChecker(), Layout.class.getName(),
					layout.getLayoutId(), ActionKeys.UPDATE);
			}
		}

		String layoutMode = ParamUtil.getString(
			httpServletRequest, "p_l_mode", Constants.VIEW);

		if (layoutMode.equals(Constants.EDIT)) {
			if (hasUpdatePermissions == null) {
				hasUpdatePermissions = _hasUpdatePermissions(
					themeDisplay.getPermissionChecker(), layout);
			}

			if (!hasUpdatePermissions) {
				layoutMode = Constants.VIEW;
			}
		}

		String page = getViewPage();

		if (layoutMode.equals(Constants.EDIT)) {
			page = _EDIT_LAYOUT_PAGE;
		}

		RequestDispatcher requestDispatcher =
			_transferHeadersHelper.getTransferHeadersRequestDispatcher(
				servletContext.getRequestDispatcher(page));

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		ServletResponse servletResponse = createServletResponse(
			httpServletResponse, unsyncStringWriter);

		String contentType = servletResponse.getContentType();

		String includeServletPath = (String)httpServletRequest.getAttribute(
			RequestDispatcher.INCLUDE_SERVLET_PATH);

		try {
			LayoutPageTemplateEntry layoutPageTemplateEntry = null;

			if (layoutMode.equals(Constants.EDIT)) {
				layoutPageTemplateEntry = _fetchLayoutPageTemplateEntry(layout);
			}

			if (layoutPageTemplateEntry != null) {
				httpServletRequest.setAttribute(
					ContentPageEditorWebKeys.CLASS_NAME,
					LayoutPageTemplateEntry.class.getName());

				httpServletRequest.setAttribute(
					ContentPageEditorWebKeys.CLASS_PK,
					layoutPageTemplateEntry.getLayoutPageTemplateEntryId());
			}
			else {
				httpServletRequest.setAttribute(
					ContentPageEditorWebKeys.CLASS_NAME,
					Layout.class.getName());

				httpServletRequest.setAttribute(
					ContentPageEditorWebKeys.CLASS_PK, layout.getPlid());
			}

			addAttributes(httpServletRequest);

			Layout draftLayout = layout.fetchDraftLayout();

			if (layoutMode.equals(Constants.EDIT) && (draftLayout != null)) {
				String layoutFullURL = _portal.getLayoutFullURL(
					draftLayout, themeDisplay);

				HttpServletRequest originalHttpServletRequest =
					_portal.getOriginalServletRequest(httpServletRequest);

				String backURL = originalHttpServletRequest.getParameter(
					"p_l_back_url");

				if (Validator.isNotNull(backURL)) {
					layoutFullURL = _http.addParameter(
						layoutFullURL, "p_l_back_url", backURL);
				}

				layoutFullURL = _http.addParameter(
					layoutFullURL, "p_l_mode", Constants.EDIT);

				long segmentsExperienceId = ParamUtil.getLong(
					httpServletRequest, "segmentsExperienceId", -1);

				if (segmentsExperienceId != -1) {
					layoutFullURL = _http.setParameter(
						layoutFullURL, "segmentsExperienceId",
						segmentsExperienceId);
				}

				httpServletResponse.sendRedirect(layoutFullURL);
			}
			else {
				requestDispatcher.include(httpServletRequest, servletResponse);
			}
		}
		finally {
			removeAttributes(httpServletRequest);

			httpServletRequest.setAttribute(
				RequestDispatcher.INCLUDE_SERVLET_PATH, includeServletPath);
		}

		if (contentType != null) {
			httpServletResponse.setContentType(contentType);
		}

		httpServletRequest.setAttribute(
			WebKeys.LAYOUT_CONTENT, unsyncStringWriter.getStringBundler());

		return false;
	}

	@Override
	public boolean isBrowsable() {
		return true;
	}

	@Override
	public boolean isFirstPageable() {
		return true;
	}

	@Override
	public boolean isFullPageDisplayable() {
		return false;
	}

	@Override
	public boolean isParentable() {
		return true;
	}

	@Override
	public boolean isPrimaryType() {
		return true;
	}

	@Override
	public boolean isSitemapable() {
		return true;
	}

	@Override
	public boolean isURLFriendliable() {
		return true;
	}

	@Override
	protected ServletResponse createServletResponse(
		HttpServletResponse httpServletResponse,
		UnsyncStringWriter unsyncStringWriter) {

		return new PipingServletResponse(
			httpServletResponse, unsyncStringWriter);
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
		target = "(osgi.web.symbolicname=com.liferay.layout.type.controller.content)",
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

		if (layout.isDraftLayout()) {
			Layout publishedLayout = _layoutLocalService.fetchLayout(
				layout.getClassPK());

			return _layoutPageTemplateEntryLocalService.
				fetchLayoutPageTemplateEntryByPlid(publishedLayout.getPlid());
		}

		return null;
	}

	private boolean _hasUpdatePermissions(
		PermissionChecker permissionChecker, Layout layout) {

		try {
			if (_layoutPermission.contains(
					permissionChecker, layout, ActionKeys.UPDATE) ||
				_layoutPermission.contains(
					permissionChecker, layout,
					ActionKeys.UPDATE_LAYOUT_CONTENT) ||
				_modelResourcePermission.contains(
					permissionChecker, layout.getPlid(), ActionKeys.UPDATE)) {

				return true;
			}
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}
		}

		return false;
	}

	private static final String _EDIT_LAYOUT_PAGE =
		"/layout/edit_layout/content.jsp";

	private static final String _URL =
		"${liferay:mainPath}/portal/layout?p_l_id=${liferay:plid}" +
			"&p_v_l_s_g_id=${liferay:pvlsgid}";

	private static final String _VIEW_PAGE = "/layout/view/content.jsp";

	private static final Log _log = LogFactoryUtil.getLog(
		ContentLayoutTypeController.class);

	@Reference
	private Http _http;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Reference
	private LayoutPermission _layoutPermission;

	@Reference
	private LayoutContentModelResourcePermission _modelResourcePermission;

	@Reference
	private Portal _portal;

	@Reference
	private TransferHeadersHelper _transferHeadersHelper;

}