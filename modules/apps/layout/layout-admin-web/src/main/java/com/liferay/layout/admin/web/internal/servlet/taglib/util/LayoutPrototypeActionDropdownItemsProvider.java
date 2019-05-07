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

package com.liferay.layout.admin.web.internal.servlet.taglib.util;

import com.liferay.exportimport.constants.ExportImportPortletKeys;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.service.permission.LayoutPrototypePermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.security.PermissionsURLTag;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
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
		return new DropdownItemList() {
			{
				if (LayoutPrototypePermissionUtil.contains(
						_themeDisplay.getPermissionChecker(),
						_layoutPrototype.getLayoutPrototypeId(),
						ActionKeys.UPDATE)) {

					add(_getEditLayoutPrototypeActionUnsafeConsumer());
					add(_getConfigureLayoutPrototypeActionUnsafeConsumer());
				}

				if (LayoutPrototypePermissionUtil.contains(
						_themeDisplay.getPermissionChecker(),
						_layoutPrototype.getLayoutPrototypeId(),
						ActionKeys.PERMISSIONS)) {

					add(_getPermissionsLayoutPrototypeActionUnsafeConsumer());
				}

				if (GroupPermissionUtil.contains(
						_themeDisplay.getPermissionChecker(),
						_layoutPrototype.getGroup(),
						ActionKeys.EXPORT_IMPORT_LAYOUTS)) {

					add(_getExportLayoutPrototypeActionUnsafeConsumer());
					add(_getImportLayoutPrototypeActionUnsafeConsumer());
				}

				if (LayoutPrototypePermissionUtil.contains(
						_themeDisplay.getPermissionChecker(),
						_layoutPrototype.getLayoutPrototypeId(),
						ActionKeys.DELETE)) {

					add(_getDeleteLayoutPrototypeActionUnsafeConsumer());
				}
			}
		};
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

		PortletURL deleteLayoutPrototypeURL = _renderResponse.createActionURL();

		deleteLayoutPrototypeURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/layout_prototype/delete_layout_prototype");

		deleteLayoutPrototypeURL.setParameter(
			"redirect", _themeDisplay.getURLCurrent());
		deleteLayoutPrototypeURL.setParameter(
			"layoutPrototypeId",
			String.valueOf(_layoutPrototype.getLayoutPrototypeId()));

		return dropdownItem -> {
			dropdownItem.putData("action", "deleteLayoutPrototype");
			dropdownItem.putData(
				"deleteLayoutPrototypeURL",
				deleteLayoutPrototypeURL.toString());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "delete"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
			_getEditLayoutPrototypeActionUnsafeConsumer()
		throws PortalException {

		Group layoutPrototypeGroup = _layoutPrototype.getGroup();

		return dropdownItem -> {
			dropdownItem.setHref(
				layoutPrototypeGroup.getDisplayURL(_themeDisplay, true));
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "edit"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
			_getExportLayoutPrototypeActionUnsafeConsumer()
		throws Exception {

		PortletURL exportLayoutPrototypeURL =
			PortalUtil.getControlPanelPortletURL(
				_httpServletRequest, ExportImportPortletKeys.EXPORT,
				PortletRequest.RENDER_PHASE);

		exportLayoutPrototypeURL.setParameter(
			"mvcRenderCommandName", "exportLayouts");
		exportLayoutPrototypeURL.setParameter(Constants.CMD, Constants.EXPORT);
		exportLayoutPrototypeURL.setParameter(
			"groupId", String.valueOf(_layoutPrototype.getGroupId()));
		exportLayoutPrototypeURL.setParameter(
			"privateLayout", Boolean.TRUE.toString());
		exportLayoutPrototypeURL.setParameter(
			"rootNodeName",
			_layoutPrototype.getName(_themeDisplay.getLocale()));
		exportLayoutPrototypeURL.setParameter(
			"showHeader", Boolean.FALSE.toString());
		exportLayoutPrototypeURL.setWindowState(LiferayWindowState.POP_UP);

		return dropdownItem -> {
			dropdownItem.putData("action", "exportLayoutPrototype");
			dropdownItem.putData(
				"exportLayoutPrototypeURL",
				exportLayoutPrototypeURL.toString());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "export"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
			_getImportLayoutPrototypeActionUnsafeConsumer()
		throws Exception {

		PortletURL importLayoutPrototypeURL =
			PortalUtil.getControlPanelPortletURL(
				_httpServletRequest, ExportImportPortletKeys.IMPORT,
				PortletRequest.RENDER_PHASE);

		importLayoutPrototypeURL.setParameter(
			"mvcRenderCommandName", "importLayouts");
		importLayoutPrototypeURL.setParameter(Constants.CMD, Constants.IMPORT);
		importLayoutPrototypeURL.setParameter(
			"groupId", String.valueOf(_layoutPrototype.getGroupId()));
		importLayoutPrototypeURL.setParameter(
			"privateLayout", Boolean.TRUE.toString());
		importLayoutPrototypeURL.setParameter(
			"rootNodeName",
			_layoutPrototype.getName(_themeDisplay.getLocale()));
		importLayoutPrototypeURL.setParameter(
			"showHeader", Boolean.FALSE.toString());
		importLayoutPrototypeURL.setWindowState(LiferayWindowState.POP_UP);

		return dropdownItem -> {
			dropdownItem.putData("action", "importLayoutPrototype");
			dropdownItem.putData(
				"importLayoutPrototypeURL",
				importLayoutPrototypeURL.toString());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "import"));
		};
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