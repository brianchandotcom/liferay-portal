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

package com.liferay.layout.page.template.admin.web.internal.servlet.taglib.util;

import com.liferay.exportimport.constants.ExportImportPortletKeys;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.service.permission.LayoutPrototypePermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.security.PermissionsURLTag;

import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class LayoutPrototypeActionDropdownItemsProvider {

	public LayoutPrototypeActionDropdownItemsProvider(
		LayoutPrototype layoutPrototype, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_layoutPrototype = layoutPrototype;
		_renderResponse = renderResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getActionDropdownItems() throws Exception {
		boolean hasExportImportLayoutsPermission = GroupPermissionUtil.contains(
			_themeDisplay.getPermissionChecker(), _layoutPrototype.getGroup(),
			ActionKeys.EXPORT_IMPORT_LAYOUTS);
		boolean hasUpdatePermission = LayoutPrototypePermissionUtil.contains(
			_themeDisplay.getPermissionChecker(),
			_layoutPrototype.getLayoutPrototypeId(), ActionKeys.UPDATE);

		return DropdownItemListBuilder.add(
			() -> hasUpdatePermission,
			_getEditLayoutPrototypeActionUnsafeConsumer()
		).add(
			() -> hasUpdatePermission,
			_getConfigureLayoutPrototypeActionUnsafeConsumer()
		).add(
			() -> LayoutPrototypePermissionUtil.contains(
				_themeDisplay.getPermissionChecker(),
				_layoutPrototype.getLayoutPrototypeId(),
				ActionKeys.PERMISSIONS),
			_getPermissionsLayoutPrototypeActionUnsafeConsumer()
		).add(
			() -> hasExportImportLayoutsPermission,
			_getExportLayoutPrototypeActionUnsafeConsumer()
		).add(
			() -> hasExportImportLayoutsPermission,
			_getImportLayoutPrototypeActionUnsafeConsumer()
		).add(
			() -> LayoutPrototypePermissionUtil.contains(
				_themeDisplay.getPermissionChecker(),
				_layoutPrototype.getLayoutPrototypeId(), ActionKeys.DELETE),
			_getDeleteLayoutPrototypeActionUnsafeConsumer()
		).build();
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getConfigureLayoutPrototypeActionUnsafeConsumer() {

		return dropdownItem -> {
			dropdownItem.setHref(
				_renderResponse.createRenderURL(), "mvcPath",
				"/edit_layout_prototype.jsp", "layoutPrototypeId",
				_layoutPrototype.getLayoutPrototypeId());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "configure"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getDeleteLayoutPrototypeActionUnsafeConsumer() {

		return dropdownItem -> {
			dropdownItem.putData("action", "deleteLayoutPrototype");
			dropdownItem.putData(
				"deleteLayoutPrototypeURL",
				PortletURLBuilder.createActionURL(
					_renderResponse
				).setActionName(
					"/layout_page_template_admin/delete_layout_prototype"
				).setRedirect(
					_themeDisplay.getURLCurrent()
				).setParameter(
					"layoutPrototypeId", _layoutPrototype.getLayoutPrototypeId()
				).buildString());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "delete"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
			_getEditLayoutPrototypeActionUnsafeConsumer()
		throws Exception {

		Group layoutPrototypeGroup = _layoutPrototype.getGroup();

		return dropdownItem -> {
			dropdownItem.setHref(
				_getLayoutPrototypeGroupHref(layoutPrototypeGroup));
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "edit"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
			_getExportLayoutPrototypeActionUnsafeConsumer()
		throws Exception {

		return dropdownItem -> {
			dropdownItem.putData("action", "exportLayoutPrototype");
			dropdownItem.putData(
				"exportLayoutPrototypeURL",
				PortletURLBuilder.create(
					PortalUtil.getControlPanelPortletURL(
						_httpServletRequest, ExportImportPortletKeys.EXPORT,
						PortletRequest.RENDER_PHASE)
				).setMVCRenderCommandName(
					"/export_import/export_layouts"
				).setParameter(
					Constants.CMD, Constants.EXPORT
				).setParameter(
					"groupId", _layoutPrototype.getGroupId()
				).setParameter(
					"privateLayout", Boolean.TRUE.toString()
				).setParameter(
					"rootNodeName",
					_layoutPrototype.getName(_themeDisplay.getLocale())
				).setParameter(
					"showHeader", Boolean.FALSE.toString()
				).setWindowState(
					LiferayWindowState.POP_UP
				).buildString());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "export"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
			_getImportLayoutPrototypeActionUnsafeConsumer()
		throws Exception {

		return dropdownItem -> {
			dropdownItem.putData("action", "importLayoutPrototype");
			dropdownItem.putData(
				"importLayoutPrototypeURL",
				PortletURLBuilder.create(
					PortalUtil.getControlPanelPortletURL(
						_httpServletRequest, ExportImportPortletKeys.IMPORT,
						PortletRequest.RENDER_PHASE)
				).setMVCRenderCommandName(
					"/export_import/import_layouts"
				).setParameter(
					Constants.CMD, Constants.IMPORT
				).setParameter(
					"groupId", _layoutPrototype.getGroupId()
				).setParameter(
					"privateLayout", Boolean.TRUE.toString()
				).setParameter(
					"rootNodeName",
					_layoutPrototype.getName(_themeDisplay.getLocale())
				).setParameter(
					"showHeader", Boolean.FALSE.toString()
				).setWindowState(
					LiferayWindowState.POP_UP
				).buildString());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "import"));
		};
	}

	private String _getLayoutPrototypeGroupHref(Group layoutPrototypeGroup) {
		String layoutFullURL = layoutPrototypeGroup.getDisplayURL(
			_themeDisplay, true);

		return HttpUtil.setParameter(
			layoutFullURL, "p_l_back_url", _themeDisplay.getURLCurrent());
	}

	private UnsafeConsumer<DropdownItem, Exception>
			_getPermissionsLayoutPrototypeActionUnsafeConsumer()
		throws Exception {

		String permissionsLayoutPrototypeURL = PermissionsURLTag.doTag(
			StringPool.BLANK, LayoutPrototype.class.getName(),
			_layoutPrototype.getName(_themeDisplay.getLocale()), null,
			String.valueOf(_layoutPrototype.getLayoutPrototypeId()),
			LiferayWindowState.POP_UP.toString(), null, _httpServletRequest);

		return dropdownItem -> {
			dropdownItem.putData("action", "permissionsLayoutPrototype");
			dropdownItem.putData(
				"permissionsLayoutPrototypeURL", permissionsLayoutPrototypeURL);
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "permissions"));
		};
	}

	private final HttpServletRequest _httpServletRequest;
	private final LayoutPrototype _layoutPrototype;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;

}