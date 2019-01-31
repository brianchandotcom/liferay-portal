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

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.layout.admin.web.internal.security.permission.resource.LayoutPageTemplateEntryPermission;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutPrototypeLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.security.PermissionsURLTag;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class DisplayPageActionDropdownItemsProvider {

	public DisplayPageActionDropdownItemsProvider(
		LayoutPageTemplateEntry layoutPageTemplateEntry,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_layoutPageTemplateEntry = layoutPageTemplateEntry;
		_renderResponse = renderResponse;

		_request = PortalUtil.getHttpServletRequest(renderRequest);

		_themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getActionDropdownItems() throws Exception {
		return new DropdownItemList() {
			{
				if (LayoutPageTemplateEntryPermission.contains(
						_themeDisplay.getPermissionChecker(),
						_layoutPageTemplateEntry, ActionKeys.UPDATE)) {

					add(_getEditDisplayPageActionConsumer());
					add(_getConfigureDisplayPageActionConsumer());
					add(_getRenameDisplayPageActionConsumer());
				}

				if (LayoutPageTemplateEntryPermission.contains(
						_themeDisplay.getPermissionChecker(),
						_layoutPageTemplateEntry, ActionKeys.PERMISSIONS)) {

					add(_getPermissionsDisplayPageActionConsumer());
				}

				if (_layoutPageTemplateEntry.isApproved() &&
					Objects.equals(
						_layoutPageTemplateEntry.getType(),
						LayoutPageTemplateEntryTypeConstants.
							TYPE_DISPLAY_PAGE) &&
					(_layoutPageTemplateEntry.getClassNameId() > 0) &&
					LayoutPageTemplateEntryPermission.contains(
						_themeDisplay.getPermissionChecker(),
						_layoutPageTemplateEntry, ActionKeys.UPDATE)) {

					add(_getMarkAsDefaultDisplayPageActionConsumer());
				}

				if (LayoutPageTemplateEntryPermission.contains(
						_themeDisplay.getPermissionChecker(),
						_layoutPageTemplateEntry, ActionKeys.DELETE)) {

					add(_getDeleteLayoutPrototypeActionConsumer());
				}
			}
		};
	}

	private Consumer<DropdownItem> _getConfigureDisplayPageActionConsumer() {
		return dropdownItem -> {
			dropdownItem.setHref(
				_renderResponse.createRenderURL(), "mvcRenderCommandName",
				"/layout/edit_layout", "redirect",
				_themeDisplay.getURLCurrent(), "backURL",
				_themeDisplay.getURLCurrent(), "selPlid",
				_layoutPageTemplateEntry.getPlid());
			dropdownItem.setLabel(LanguageUtil.get(_request, "configure"));
		};
	}

	private Consumer<DropdownItem> _getDeleteLayoutPrototypeActionConsumer() {
		PortletURL deleteDisplayPageURL = _renderResponse.createActionURL();

		deleteDisplayPageURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/layout/delete_layout_page_template_entry");

		deleteDisplayPageURL.setParameter(
			"redirect", _themeDisplay.getURLCurrent());
		deleteDisplayPageURL.setParameter(
			"layoutPageTemplateEntryId",
			String.valueOf(
				_layoutPageTemplateEntry.getLayoutPageTemplateEntryId()));

		return dropdownItem -> {
			dropdownItem.putData("action", "deleteDisplayPage");
			dropdownItem.putData(
				"deleteDisplayPageURL", deleteDisplayPageURL.toString());
			dropdownItem.setLabel(LanguageUtil.get(_request, "delete"));
		};
	}

	private Consumer<DropdownItem> _getEditDisplayPageActionConsumer()
		throws Exception {

		if (Objects.equals(
				_layoutPageTemplateEntry.getType(),
				LayoutPageTemplateEntryTypeConstants.TYPE_WIDGET_PAGE)) {

			LayoutPrototype layoutPrototype =
				LayoutPrototypeLocalServiceUtil.fetchLayoutPrototype(
					_layoutPageTemplateEntry.getLayoutPrototypeId());

			if (layoutPrototype == null) {
				return null;
			}

			Group layoutPrototypeGroup = layoutPrototype.getGroup();

			return dropdownItem -> {
				dropdownItem.setHref(
					layoutPrototypeGroup.getDisplayURL(_themeDisplay, true));
				dropdownItem.setLabel(LanguageUtil.get(_request, "edit"));
			};
		}

		Layout layout = LayoutLocalServiceUtil.fetchLayout(
			_layoutPageTemplateEntry.getPlid());

		String layoutFullURL = PortalUtil.getLayoutFullURL(
			layout, _themeDisplay);

		return dropdownItem -> {
			dropdownItem.setHref(
				HttpUtil.setParameter(
					layoutFullURL, "p_l_mode", Constants.EDIT));
			dropdownItem.setLabel(LanguageUtil.get(_request, "edit"));
		};
	}

	private Consumer<DropdownItem>
		_getMarkAsDefaultDisplayPageActionConsumer() {

		PortletURL markAsDefaultDisplayPageURL =
			_renderResponse.createActionURL();

		markAsDefaultDisplayPageURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/layout/edit_layout_page_template_settings");

		markAsDefaultDisplayPageURL.setParameter(
			"redirect", _themeDisplay.getURLCurrent());
		markAsDefaultDisplayPageURL.setParameter(
			"layoutPageTemplateEntryId",
			String.valueOf(
				_layoutPageTemplateEntry.getLayoutPageTemplateEntryId()));
		markAsDefaultDisplayPageURL.setParameter(
			"defaultTemplate",
			String.valueOf(!_layoutPageTemplateEntry.isDefaultTemplate()));

		return dropdownItem -> {
			dropdownItem.putData("action", "markAsDefaultDisplayPage");
			dropdownItem.putData(
				"markAsDefaultDisplayPageURL",
				markAsDefaultDisplayPageURL.toString());

			String message = StringPool.BLANK;

			LayoutPageTemplateEntry defaultLayoutPageTemplateEntry =
				LayoutPageTemplateEntryServiceUtil.
					fetchDefaultLayoutPageTemplateEntry(
						_layoutPageTemplateEntry.getGroupId(),
						_layoutPageTemplateEntry.getClassNameId(),
						_layoutPageTemplateEntry.getClassTypeId());

			if ((defaultLayoutPageTemplateEntry != null) &&
				(defaultLayoutPageTemplateEntry.
					getLayoutPageTemplateEntryId() !=
						_layoutPageTemplateEntry.
							getLayoutPageTemplateEntryId())) {

				message = LanguageUtil.format(
					_request,
					"do-you-want-to-replace-x-for-x-as-the-default-display-" +
						"page",
					new String[] {
						_layoutPageTemplateEntry.getName(),
						defaultLayoutPageTemplateEntry.getName()
					});
			}
			else if (_layoutPageTemplateEntry.isDefaultTemplate()) {
				message = LanguageUtil.get(
					_request, "unmark-default-confirmation");
			}

			dropdownItem.putData("message", message);

			String label = "mark-as-default";

			if (_layoutPageTemplateEntry.isDefaultTemplate()) {
				label = "unmark-as-default";
			}

			dropdownItem.setLabel(LanguageUtil.get(_request, label));
		};
	}

	private Consumer<DropdownItem> _getPermissionsDisplayPageActionConsumer()
		throws Exception {

		String permissionsDisplayPageURL = PermissionsURLTag.doTag(
			StringPool.BLANK, LayoutPageTemplateEntry.class.getName(),
			_layoutPageTemplateEntry.getName(), null,
			String.valueOf(
				_layoutPageTemplateEntry.getLayoutPageTemplateEntryId()),
			LiferayWindowState.POP_UP.toString(), null, _request);

		return dropdownItem -> {
			dropdownItem.putData("action", "permissionsDisplayPage");
			dropdownItem.putData(
				"permissionsDisplayPageURL", permissionsDisplayPageURL);
			dropdownItem.setLabel(LanguageUtil.get(_request, "permissions"));
		};
	}

	private Consumer<DropdownItem> _getRenameDisplayPageActionConsumer() {
		PortletURL updateDisplayPageURL = _renderResponse.createActionURL();

		updateDisplayPageURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/layout/update_layout_page_template_entry");

		updateDisplayPageURL.setParameter(
			"redirect", _themeDisplay.getURLCurrent());
		updateDisplayPageURL.setParameter(
			"layoutPageTemplateCollectionId",
			String.valueOf(
				_layoutPageTemplateEntry.getLayoutPageTemplateCollectionId()));
		updateDisplayPageURL.setParameter(
			"layoutPageTemplateEntryId",
			String.valueOf(
				_layoutPageTemplateEntry.getLayoutPageTemplateEntryId()));

		return dropdownItem -> {
			dropdownItem.putData("action", "renameDisplayPage");
			dropdownItem.putData(
				"updateDisplayPageURL", updateDisplayPageURL.toString());
			dropdownItem.putData(
				"layoutPageTemplateEntryId",
				String.valueOf(
					_layoutPageTemplateEntry.getLayoutPageTemplateEntryId()));
			dropdownItem.putData(
				"layoutPageTemplateEntryName",
				_layoutPageTemplateEntry.getName());
			dropdownItem.setLabel(LanguageUtil.get(_request, "rename"));
		};
	}

	private final LayoutPageTemplateEntry _layoutPageTemplateEntry;
	private final RenderResponse _renderResponse;
	private final HttpServletRequest _request;
	private final ThemeDisplay _themeDisplay;

}