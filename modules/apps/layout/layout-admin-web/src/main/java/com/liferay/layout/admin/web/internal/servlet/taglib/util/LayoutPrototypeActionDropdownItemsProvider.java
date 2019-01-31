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
import java.util.function.Consumer;

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

		_request = PortalUtil.getHttpServletRequest(renderRequest);

		_themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getActionDropdownItems() throws Exception {
		return new DropdownItemList() {
			{
				if (LayoutPrototypePermissionUtil.contains(
						_themeDisplay.getPermissionChecker(),
						_layoutPrototype.getLayoutPrototypeId(),
						ActionKeys.UPDATE)) {

					add(_getEditLayoutPrototypeActionConsumer());
					add(_getConfigureLayoutPrototypeActionConsumer());
				}

				if (LayoutPrototypePermissionUtil.contains(
						_themeDisplay.getPermissionChecker(),
						_layoutPrototype.getLayoutPrototypeId(),
						ActionKeys.PERMISSIONS)) {

					add(_getPermissionsLayoutPrototypeActionConsumer());
				}

				if (GroupPermissionUtil.contains(
						_themeDisplay.getPermissionChecker(),
						_layoutPrototype.getGroup(),
						ActionKeys.EXPORT_IMPORT_LAYOUTS)) {

					add(_getExportLayoutPrototypeActionConsumer());
					add(_getImportLayoutPrototypeActionConsumer());
				}

				if (LayoutPrototypePermissionUtil.contains(
						_themeDisplay.getPermissionChecker(),
						_layoutPrototype.getLayoutPrototypeId(),
						ActionKeys.DELETE)) {

					add(_getDeleteLayoutPrototypeActionConsumer());
				}
			}
		};
	}

	private Consumer<DropdownItem>
		_getConfigureLayoutPrototypeActionConsumer() {

		return dropdownItem -> {
			dropdownItem.setHref(
				_renderResponse.createRenderURL(), "mvcPath",
				"/edit_layout_prototype.jsp", "layoutPrototypeId",
				_layoutPrototype.getLayoutPrototypeId());
			dropdownItem.setLabel(LanguageUtil.get(_request, "configure"));
		};
	}

	private Consumer<DropdownItem> _getDeleteLayoutPrototypeActionConsumer() {
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
			dropdownItem.setLabel(LanguageUtil.get(_request, "delete"));
		};
	}

	private Consumer<DropdownItem> _getEditLayoutPrototypeActionConsumer()
		throws PortalException {

		Group layoutPrototypeGroup = _layoutPrototype.getGroup();

		return dropdownItem -> {
			dropdownItem.setHref(
				layoutPrototypeGroup.getDisplayURL(_themeDisplay, true));
			dropdownItem.setLabel(LanguageUtil.get(_request, "edit"));
		};
	}

	private Consumer<DropdownItem> _getExportLayoutPrototypeActionConsumer()
		throws Exception {

		PortletURL exportLayoutPrototypeURL =
			PortalUtil.getControlPanelPortletURL(
				_request, ExportImportPortletKeys.EXPORT,
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
			dropdownItem.setLabel(LanguageUtil.get(_request, "export"));
		};
	}

	private Consumer<DropdownItem> _getImportLayoutPrototypeActionConsumer()
		throws Exception {

		PortletURL importLayoutPrototypeURL =
			PortalUtil.getControlPanelPortletURL(
				_request, ExportImportPortletKeys.IMPORT,
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
			dropdownItem.setLabel(LanguageUtil.get(_request, "import"));
		};
	}

	private Consumer<DropdownItem>
			_getPermissionsLayoutPrototypeActionConsumer()
		throws Exception {

		String permissionsLayoutPrototypeURL = PermissionsURLTag.doTag(
			StringPool.BLANK, LayoutPrototype.class.getName(),
			_layoutPrototype.getName(_themeDisplay.getLocale()), null,
			String.valueOf(_layoutPrototype.getLayoutPrototypeId()),
			LiferayWindowState.POP_UP.toString(), null, _request);

		return dropdownItem -> {
			dropdownItem.putData("action", "permissionsLayoutPrototype");
			dropdownItem.putData(
				"permissionsLayoutPrototypeURL", permissionsLayoutPrototypeURL);
			dropdownItem.setLabel(LanguageUtil.get(_request, "permissions"));
		};
	}

	private final LayoutPrototype _layoutPrototype;
	private final RenderResponse _renderResponse;
	private final HttpServletRequest _request;
	private final ThemeDisplay _themeDisplay;

}