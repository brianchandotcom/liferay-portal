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

package com.liferay.layout.type.controller.content.internal.product.navigation.control.menu;

import com.liferay.layout.content.page.editor.constants.ContentPageEditorWebKeys;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.security.permission.resource.LayoutContentModelResourcePermission;
import com.liferay.layout.util.LayoutCopyHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypeController;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.permission.LayoutPermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.product.navigation.control.menu.BaseProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.ProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.constants.ProductNavigationControlMenuCategoryKeys;
import com.liferay.sites.kernel.util.SitesUtil;
import com.liferay.staging.StagingGroupHelper;

import java.util.Collections;
import java.util.Locale;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {
		"product.navigation.control.menu.category.key=" + ProductNavigationControlMenuCategoryKeys.USER,
		"product.navigation.control.menu.entry.order:Integer=50"
	},
	service = ProductNavigationControlMenuEntry.class
)
public class EditLayoutModeProductNavigationControlMenuEntry
	extends BaseProductNavigationControlMenuEntry
	implements ProductNavigationControlMenuEntry {

	@Override
	public String getIcon(HttpServletRequest httpServletRequest) {
		return "pencil";
	}

	@Override
	public String getIconCssClass(HttpServletRequest httpServletRequest) {
		return "icon-monospaced";
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "edit");
	}

	@Override
	public String getURL(HttpServletRequest httpServletRequest) {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		try {
			String redirect = themeDisplay.getURLCurrent();

			Layout layout = themeDisplay.getLayout();

			long publishedLayoutPlid = layout.getPlid();

			if (layout.isDraftLayout()) {
				publishedLayoutPlid = layout.getClassPK();

				redirect = _portal.getLayoutFullURL(
					_layoutLocalService.getLayout(publishedLayoutPlid),
					themeDisplay);
			}
			else {
				Layout draftLayout = layout.fetchDraftLayout();

				if (draftLayout == null) {
					UnicodeProperties unicodeProperties =
						layout.getTypeSettingsProperties();

					unicodeProperties.put("published", "true");

					ServiceContext serviceContext =
						ServiceContextFactory.getInstance(httpServletRequest);

					draftLayout = _layoutLocalService.addLayout(
						layout.getUserId(), layout.getGroupId(),
						layout.isPrivateLayout(), layout.getParentLayoutId(),
						_portal.getClassNameId(Layout.class), layout.getPlid(),
						layout.getNameMap(), layout.getTitleMap(),
						layout.getDescriptionMap(), layout.getKeywordsMap(),
						layout.getRobotsMap(), layout.getType(),
						unicodeProperties.toString(), true, true,
						Collections.emptyMap(), layout.getMasterLayoutPlid(),
						serviceContext);

					draftLayout = _layoutCopyHelper.copyLayout(
						layout, draftLayout);

					_layoutLocalService.updateStatus(
						draftLayout.getUserId(), draftLayout.getPlid(),
						WorkflowConstants.STATUS_APPROVED, serviceContext);
				}

				redirect = _portal.getLayoutFullURL(draftLayout, themeDisplay);
			}

			redirect = _http.setParameter(
				redirect, "p_l_back_url",
				_portal.getLayoutFullURL(
					themeDisplay.getLayout(), themeDisplay));

			return _http.setParameter(redirect, "p_l_mode", Constants.EDIT);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}

		return StringPool.BLANK;
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

		long scopeGroupId = themeDisplay.getScopeGroupId();

		if (_stagingGroupHelper.isLocalLiveGroup(scopeGroupId) ||
			_stagingGroupHelper.isRemoteLiveGroup(scopeGroupId)) {

			return false;
		}

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

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditLayoutModeProductNavigationControlMenuEntry.class);

	@Reference
	private Http _http;

	@Reference
	private LayoutCopyHelper _layoutCopyHelper;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPermission _layoutPermission;

	@Reference
	private LayoutContentModelResourcePermission _modelResourcePermission;

	@Reference
	private Portal _portal;

	@Reference
	private StagingGroupHelper _stagingGroupHelper;

}