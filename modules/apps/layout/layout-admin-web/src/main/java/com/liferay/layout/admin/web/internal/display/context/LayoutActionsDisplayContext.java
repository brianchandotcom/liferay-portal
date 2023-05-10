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

package com.liferay.layout.admin.web.internal.display.context;

import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.layout.admin.web.internal.security.permission.resource.LayoutPageTemplatePermission;
import com.liferay.layout.page.template.constants.LayoutPageTemplateActionKeys;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalServiceUtil;
import com.liferay.layout.utility.page.model.LayoutUtilityPageEntry;
import com.liferay.layout.utility.page.service.LayoutUtilityPageEntryLocalServiceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.manager.SegmentsExperienceManager;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsExperienceLocalService;
import com.liferay.taglib.security.PermissionsURLTag;

import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class LayoutActionsDisplayContext {

	public LayoutActionsDisplayContext(
		HttpServletRequest httpServletRequest,
		SegmentsExperienceLocalService segmentsExperienceLocalService) {

		_httpServletRequest = httpServletRequest;
		_segmentsExperienceLocalService = segmentsExperienceLocalService;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getDropdownItems() {
		Layout layout = _getLayout();

		return DropdownItemListBuilder.addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						() -> _isShowConfigureAction(layout),
						dropdownItem -> {
							dropdownItem.setHref(
								_getConfigureLayoutURL(layout));
							dropdownItem.setIcon("cog");
							dropdownItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest, "configure"));

							dropdownItem.setTarget("_blank");
						}
					).add(
						dropdownItem -> {
							String previewLayoutURL = _getPreviewLayoutURL(
								layout);

							dropdownItem.setData(
								HashMapBuilder.<String, Object>put(
									"page-editor-layout-preview-base-url",
									previewLayoutURL
								).build());
							dropdownItem.setHref(previewLayoutURL);

							dropdownItem.setIcon("shortcut");
							dropdownItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest,
									"preview-in-a-new-tab"));
							dropdownItem.setTarget("_blank");
						}
					).add(
						() -> _isShowConvertToPageTemplateAction(layout),
						dropdownItem -> {
							dropdownItem.putData(
								"action", "convertToPageTemplate");

							dropdownItem.setIcon("page-template");
							dropdownItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest,
									"convert-to-page-template"));
						}
					).add(
						() ->
							_isContentLayout(layout) &&
							_isShowPermissionsAction(layout),
						dropdownItem -> {
							dropdownItem.putData("action", "permissionLayout");
							dropdownItem.putData(
								"permissionLayoutURL",
								_getPermissionsURL(layout));
							dropdownItem.setIcon("password-policies");
							dropdownItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest, "permissions"));
						}
					).build());
				dropdownGroupItem.setSeparator(true);
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						() ->
							_isContentLayout(layout) &&
							_isShowDeleteAction(layout),
						dropdownItem -> {
							dropdownItem.putData("action", "deleteLayout");
							dropdownItem.putData(
								"deleteLayoutURL", _getDeleteLayoutURL(layout));

							String messageKey =
								"are-you-sure-you-want-to-delete-the-page-x.-" +
									"it-will-be-removed-immediately";

							if (layout.hasChildren() &&
								layout.hasScopeGroup()) {

								messageKey = StringBundler.concat(
									"are-you-sure-you-want-to-delete-the-page-",
									"x.-this-page-serves-as-a-scope-for-",
									"content-and-also-contains-child-pages");
							}
							else if (layout.hasChildren()) {
								messageKey = StringBundler.concat(
									"are-you-sure-you-want-to-delete-the-page-",
									"x.-this-page-contains-child-pages-that-",
									"will-also-be-removed");
							}
							else if (layout.hasScopeGroup()) {
								messageKey = StringBundler.concat(
									"are-you-sure-you-want-to-delete-the-page-",
									"x.-this-page-serves-as-a-scope-for-",
									"content");
							}

							dropdownItem.putData(
								"message",
								LanguageUtil.format(
									_httpServletRequest, messageKey,
									HtmlUtil.escape(
										layout.getName(
											_themeDisplay.getLocale()))));

							dropdownItem.setIcon("trash");
							dropdownItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest, "delete"));
						}
					).build());
				dropdownGroupItem.setSeparator(true);
			}
		).build();
	}

	private String _getConfigureLayoutURL(Layout layout) {
		String currentURL = PortalUtil.getCurrentURL(_httpServletRequest);

		return PortletURLBuilder.create(
			PortalUtil.getControlPanelPortletURL(
				_httpServletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
				PortletRequest.RENDER_PHASE)
		).setMVCRenderCommandName(
			"/layout_admin/edit_layout"
		).setRedirect(
			currentURL
		).setBackURL(
			currentURL
		).setParameter(
			"groupId", layout.getGroupId()
		).setParameter(
			"privateLayout", layout.isPrivateLayout()
		).setParameter(
			"selPlid", layout.getPlid()
		).buildPortletURL(
		).toString();
	}

	private String _getDeleteLayoutURL(Layout layout) {
		return PortletURLBuilder.create(
			PortalUtil.getControlPanelPortletURL(
				_httpServletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
				PortletRequest.ACTION_PHASE)
		).setActionName(
			"/layout_admin/delete_layout"
		).setRedirect(
			PortalUtil.getControlPanelPortletURL(
				_httpServletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
				PortletRequest.RENDER_PHASE)
		).setParameter(
			"selPlid", layout.getPlid()
		).buildString();
	}

	private Layout _getLayout() {
		Layout layout = _themeDisplay.getLayout();

		if (layout.isDraftLayout()) {
			layout = LayoutLocalServiceUtil.fetchLayout(layout.getClassPK());
		}

		return layout;
	}

	private String _getPermissionsURL(Layout layout) throws Exception {
		return PermissionsURLTag.doTag(
			StringPool.BLANK, Layout.class.getName(),
			HtmlUtil.escape(layout.getName(_themeDisplay.getLocale())), null,
			String.valueOf(layout.getPlid()),
			LiferayWindowState.POP_UP.toString(), null,
			_themeDisplay.getRequest());
	}

	private String _getPreviewLayoutURL(Layout layout) {
		Layout draftLayout = layout;

		if (!layout.isDraftLayout()) {
			draftLayout = layout.fetchDraftLayout();
		}

		String pagePreviewURL = HttpComponentsUtil.addParameters(
			_themeDisplay.getPortalURL() + _themeDisplay.getPathMain() +
				"/portal/get_page_preview",
			"p_l_mode", Constants.PREVIEW, "p_p_state",
			WindowState.UNDEFINED.toString(), "segmentsExperienceId",
			_getSegmentsExperienceId(draftLayout), "selPlid",
			draftLayout.getPlid());

		if (Validator.isNotNull(_themeDisplay.getDoAsUserId())) {
			pagePreviewURL = PortalUtil.addPreservedParameters(
				_themeDisplay, pagePreviewURL, false, true);
		}

		return pagePreviewURL;
	}

	private long _getSegmentsExperienceId(Layout layout) {
		UnicodeProperties unicodeProperties =
			layout.getTypeSettingsProperties();

		// LPS-131416

		long segmentsExperienceId = ParamUtil.getLong(
			PortalUtil.getOriginalServletRequest(_httpServletRequest),
			"segmentsExperienceId",
			GetterUtil.getLong(
				unicodeProperties.getProperty("segmentsExperienceId"), -1));

		if (segmentsExperienceId != -1) {
			SegmentsExperience segmentsExperience =
				_segmentsExperienceLocalService.fetchSegmentsExperience(
					segmentsExperienceId);

			if (segmentsExperience != null) {
				return segmentsExperience.getSegmentsExperienceId();
			}

			segmentsExperienceId = -1L;
		}

		if (segmentsExperienceId == -1) {
			SegmentsExperienceManager segmentsExperienceManager =
				new SegmentsExperienceManager(_segmentsExperienceLocalService);

			segmentsExperienceId =
				segmentsExperienceManager.getSegmentsExperienceId(
					_httpServletRequest);
		}

		return segmentsExperienceId;
	}

	private boolean _isContentLayout(Layout layout) {
		if (_contentLayout != null) {
			return _contentLayout;
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			LayoutPageTemplateEntryLocalServiceUtil.
				fetchLayoutPageTemplateEntryByPlid(layout.getPlid());

		if (layoutPageTemplateEntry == null) {
			layoutPageTemplateEntry =
				LayoutPageTemplateEntryLocalServiceUtil.
					fetchLayoutPageTemplateEntryByPlid(layout.getClassPK());
		}

		if (layoutPageTemplateEntry == null) {
			LayoutUtilityPageEntry layoutUtilityPageEntry =
				LayoutUtilityPageEntryLocalServiceUtil.
					fetchLayoutUtilityPageEntryByPlid(layout.getPlid());

			if (layoutUtilityPageEntry != null) {
				_contentLayout = false;
			}
			else {
				_contentLayout = true;
			}
		}
		else {
			_contentLayout = false;
		}

		return _contentLayout;
	}

	private boolean _isShowConfigureAction(Layout layout)
		throws PortalException {

		return LayoutPermissionUtil.containsLayoutUpdatePermission(
			_themeDisplay.getPermissionChecker(), layout);
	}

	private boolean _isShowConvertToPageTemplateAction(Layout layout)
		throws PortalException {

		if (_isContentLayout(layout) &&
			LayoutPageTemplatePermission.contains(
				_themeDisplay.getPermissionChecker(), layout.getGroupId(),
				LayoutPageTemplateActionKeys.
					ADD_LAYOUT_PAGE_TEMPLATE_COLLECTION) &&
			LayoutPageTemplatePermission.contains(
				_themeDisplay.getPermissionChecker(), layout.getGroupId(),
				LayoutPageTemplateActionKeys.ADD_LAYOUT_PAGE_TEMPLATE_ENTRY)) {

			return true;
		}

		return false;
	}

	private boolean _isShowDeleteAction(Layout layout) throws PortalException {
		if (StagingUtil.isIncomplete(layout) ||
			!LayoutPermissionUtil.contains(
				_themeDisplay.getPermissionChecker(), layout,
				ActionKeys.DELETE)) {

			return false;
		}

		Group group = layout.getGroup();

		int layoutsCount = LayoutLocalServiceUtil.getLayoutsCount(
			group, false, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

		if (group.isGuest() && !layout.isPrivateLayout() &&
			layout.isRootLayout() && (layoutsCount == 1)) {

			return false;
		}

		return true;
	}

	private boolean _isShowPermissionsAction(Layout layout)
		throws PortalException {

		if (StagingUtil.isIncomplete(layout)) {
			return false;
		}

		Group group = layout.getGroup();

		if (group.isLayoutPrototype()) {
			return false;
		}

		return LayoutPermissionUtil.contains(
			_themeDisplay.getPermissionChecker(), layout,
			ActionKeys.PERMISSIONS);
	}

	private Boolean _contentLayout;
	private final HttpServletRequest _httpServletRequest;
	private final SegmentsExperienceLocalService
		_segmentsExperienceLocalService;
	private final ThemeDisplay _themeDisplay;

}