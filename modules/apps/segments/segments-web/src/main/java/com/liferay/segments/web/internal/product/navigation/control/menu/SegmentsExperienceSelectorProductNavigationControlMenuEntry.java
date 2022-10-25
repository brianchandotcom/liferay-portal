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

package com.liferay.segments.web.internal.product.navigation.control.menu;

import com.liferay.layout.content.page.editor.constants.ContentPageEditorWebKeys;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.security.permission.resource.LayoutContentModelResourcePermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypeController;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.permission.LayoutPermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.product.navigation.control.menu.BaseJSPProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.ProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.constants.ProductNavigationControlMenuCategoryKeys;
import com.liferay.segments.manager.SegmentsExperienceManager;
import com.liferay.segments.service.SegmentsEntryLocalService;
import com.liferay.segments.service.SegmentsExperienceLocalService;
import com.liferay.segments.service.SegmentsExperimentLocalService;
import com.liferay.segments.service.SegmentsExperimentRelLocalService;
import com.liferay.segments.web.internal.display.context.SegmentsExperienceSelectorDisplayContext;
import com.liferay.sites.kernel.util.Sites;

import java.io.IOException;

import java.util.Objects;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pablo Molina
 */
@Component(
	immediate = true,
	property = {
		"product.navigation.control.menu.category.key=" + ProductNavigationControlMenuCategoryKeys.TOOLS,
		"product.navigation.control.menu.entry.order:Integer=110"
	},
	service = ProductNavigationControlMenuEntry.class
)
public class SegmentsExperienceSelectorProductNavigationControlMenuEntry
	extends BaseJSPProductNavigationControlMenuEntry
	implements ProductNavigationControlMenuEntry {

	@Override
	public String getIconJspPath() {
		return "/segments_experience_selector.jsp";
	}

	@Override
	public boolean isShow(HttpServletRequest httpServletRequest)
		throws PortalException {

		String mode = ParamUtil.getString(
			httpServletRequest, "p_l_mode", Constants.VIEW);

		if (Objects.equals(mode, Constants.EDIT)) {
			return false;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		LayoutTypePortlet layoutTypePortlet =
			themeDisplay.getLayoutTypePortlet();

		LayoutTypeController layoutTypeController =
			layoutTypePortlet.getLayoutTypeController();

		if (layoutTypeController.isFullPageDisplayable()) {
			return false;
		}

		Layout layout = themeDisplay.getLayout();

		if (!layout.isTypeContent() || !_sites.isLayoutUpdateable(layout)) {
			return false;
		}

		long segmentsExperiencesCount =
			_segmentsExperienceLocalService.getSegmentsExperiencesCount(
				themeDisplay.getScopeGroupId(),
				_portal.getClassNameId(Layout.class.getName()),
				themeDisplay.getPlid(), true);

		if (segmentsExperiencesCount <= 1) {
			return false;
		}

		String className = (String)httpServletRequest.getAttribute(
			ContentPageEditorWebKeys.CLASS_NAME);

		if (Objects.equals(
				className, LayoutPageTemplateEntry.class.getName())) {

			return false;
		}

		try {
			if (layout.isSystem() && layout.isTypeContent()) {
				layout = _layoutLocalService.getLayout(layout.getClassPK());
			}

			if (_layoutPermission.containsLayoutUpdatePermission(
					themeDisplay.getPermissionChecker(), layout) ||
				_modelResourcePermission.contains(
					themeDisplay.getPermissionChecker(), layout.getPlid(),
					ActionKeys.UPDATE)) {

				return true;
			}
		}
		catch (PortalException portalException) {
			_log.error(portalException);

			return false;
		}

		return false;
	}

	@Override
	protected ServletContext getServletContext() {
		return _servletContext;
	}

	@Override
	protected boolean include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String jspPath)
		throws IOException {

		httpServletRequest.setAttribute(
			SegmentsExperienceSelectorDisplayContext.class.getName(),
			new SegmentsExperienceSelectorDisplayContext(
				httpServletRequest, _jsonFactory, _language, _portal,
				_segmentsEntryLocalService,
				new SegmentsExperienceManager(_segmentsExperienceLocalService),
				_segmentsExperienceLocalService,
				_segmentsExperimentLocalService,
				_segmentsExperimentRelLocalService));

		return super.include(httpServletRequest, httpServletResponse, jspPath);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SegmentsExperienceSelectorProductNavigationControlMenuEntry.class);

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPermission _layoutPermission;

	@Reference
	private LayoutContentModelResourcePermission _modelResourcePermission;

	@Reference
	private Portal _portal;

	@Reference
	private SegmentsEntryLocalService _segmentsEntryLocalService;

	@Reference
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	@Reference
	private SegmentsExperimentLocalService _segmentsExperimentLocalService;

	@Reference
	private SegmentsExperimentRelLocalService
		_segmentsExperimentRelLocalService;

	@Reference(target = "(osgi.web.symbolicname=com.liferay.segments.web)")
	private ServletContext _servletContext;

	@Reference
	private Sites _sites;

}