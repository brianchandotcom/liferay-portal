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
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.product.navigation.control.menu.BaseJSPProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.ProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.constants.ProductNavigationControlMenuCategoryKeys;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsEntryLocalService;
import com.liferay.segments.service.SegmentsExperienceLocalService;
import com.liferay.segments.web.internal.constants.SegmentsWebKeys;
import com.liferay.sites.kernel.util.SitesUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
public class ExperienceSelectorProductNavigationControlMenuEntry
	extends BaseJSPProductNavigationControlMenuEntry
	implements ProductNavigationControlMenuEntry {

	@Override
	public String getIconJspPath() {
		return "/experience_selector.jsp";
	}

	@Override
	public boolean includeIcon(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		Locale locale = (Locale)httpServletRequest.getAttribute(WebKeys.LOCALE);

		List<HashMap<String, Object>> segmentsExperiences =
			_getSegmentsExperiences(httpServletRequest, locale);

		if (segmentsExperiences.isEmpty()) {
			return false;
		}

		httpServletRequest.setAttribute(
			SegmentsWebKeys.LAYOUT_SEGMENTS_EXPERIENCES, segmentsExperiences);

		httpServletRequest.setAttribute(
			SegmentsWebKeys.LAYOUT_SELECTED_SEGMENTS_EXPERIENCE,
			_getSelectedSegmentsExperience(httpServletRequest, locale));

		return super.includeIcon(httpServletRequest, httpServletResponse);
	}

	@Override
	public boolean isShow(HttpServletRequest httpServletRequest) {
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

		String className = (String)httpServletRequest.getAttribute(
			ContentPageEditorWebKeys.CLASS_NAME);

		if (Objects.equals(
				className, LayoutPageTemplateEntry.class.getName())) {

			return false;
		}

		Layout layout = themeDisplay.getLayout();

		if (!layout.isTypeContent() || !SitesUtil.isLayoutUpdateable(layout)) {
			return false;
		}

		try {
			if (layout.isSystem() && layout.isTypeContent()) {
				layout = _layoutLocalService.getLayout(layout.getClassPK());
			}

			if (_layoutPermission.contains(
					themeDisplay.getPermissionChecker(), layout,
					ActionKeys.UPDATE) ||
				_layoutPermission.contains(
					themeDisplay.getPermissionChecker(), layout,
					ActionKeys.UPDATE_LAYOUT_CONTENT) ||
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
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.segments.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	private HashMap<String, Object> _getDefaultSegmentsExperience(
		HttpServletRequest httpServletRequest, Locale locale) {

		return HashMapBuilder.<String, Object>put(
			"active", true
		).put(
			"segmentsEntryName",
			SegmentsEntryConstants.getDefaultSegmentsEntryName(locale)
		).put(
			"segmentsExperienceName",
			SegmentsExperienceConstants.getDefaultSegmentsExperienceName(locale)
		).put(
			"url",
			_http.removeParameter(
				_portal.getCurrentURL(httpServletRequest),
				"p_l_segments_experience_id")
		).build();
	}

	private HashMap<String, Object> _getSegmentsExperience(
		HttpServletRequest httpServletRequest, Locale locale,
		SegmentsExperience segmentsExperience) {

		return HashMapBuilder.<String, Object>put(
			"active", segmentsExperience.isActive()
		).put(
			"segmentsEntryName",
			() -> {
				SegmentsEntry segmentsEntry =
					_segmentsEntryLocalService.fetchSegmentsEntry(
						segmentsExperience.getSegmentsEntryId());

				if (segmentsEntry != null) {
					return segmentsEntry.getName(locale);
				}

				return SegmentsEntryConstants.getDefaultSegmentsEntryName(
					locale);
			}
		).put(
			"segmentsExperienceName", segmentsExperience.getName(locale)
		).put(
			"url",
			_http.setParameter(
				_portal.getCurrentURL(httpServletRequest),
				"p_l_segments_experience_id",
				segmentsExperience.getSegmentsExperienceId())
		).build();
	}

	private List<HashMap<String, Object>> _getSegmentsExperiences(
		HttpServletRequest httpServletRequest, Locale locale) {

		List<HashMap<String, Object>> segmentsExperiencesDropdownItems =
			new ArrayList<>();

		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			List<SegmentsExperience> segmentsExperiences =
				_segmentsExperienceLocalService.getSegmentsExperiences(
					themeDisplay.getScopeGroupId(),
					_portal.getClassNameId(Layout.class.getName()),
					themeDisplay.getPlid(), true);

			if (segmentsExperiences.isEmpty()) {
				return segmentsExperiencesDropdownItems;
			}

			segmentsExperiencesDropdownItems.add(
				_getDefaultSegmentsExperience(httpServletRequest, locale));

			for (SegmentsExperience segmentsExperience : segmentsExperiences) {
				segmentsExperiencesDropdownItems.add(
					_getSegmentsExperience(
						httpServletRequest, locale, segmentsExperience));
			}
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		return segmentsExperiencesDropdownItems;
	}

	private HashMap<String, Object> _getSelectedSegmentsExperience(
		HttpServletRequest httpServletRequest, Locale locale) {

		long segmentsExperienceId = ParamUtil.getLong(
			httpServletRequest, "p_l_segments_experience_id",
			SegmentsExperienceConstants.ID_DEFAULT);

		SegmentsExperience segmentsExperience =
			_segmentsExperienceLocalService.fetchSegmentsExperience(
				segmentsExperienceId);

		if (segmentsExperience == null) {
			return _getDefaultSegmentsExperience(httpServletRequest, locale);
		}

		return _getSegmentsExperience(
			httpServletRequest, locale, segmentsExperience);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ExperienceSelectorProductNavigationControlMenuEntry.class);

	@Reference
	private Http _http;

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

}