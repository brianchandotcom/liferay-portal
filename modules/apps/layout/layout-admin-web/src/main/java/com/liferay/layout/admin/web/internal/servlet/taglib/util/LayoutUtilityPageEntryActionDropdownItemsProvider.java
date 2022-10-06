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
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.layout.utility.page.model.LayoutUtilityPageEntry;
import com.liferay.layout.utility.page.service.LayoutUtilityPageEntryLocalServiceUtil;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Bárbara Cabrera
 */
public class LayoutUtilityPageEntryActionDropdownItemsProvider {

	public LayoutUtilityPageEntryActionDropdownItemsProvider(
		LayoutUtilityPageEntry layoutUtilityPageEntry,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_layoutUtilityPageEntry = layoutUtilityPageEntry;
		_renderResponse = renderResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);
		_themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_draftLayout = LayoutLocalServiceUtil.fetchDraftLayout(
			layoutUtilityPageEntry.getPlid());
	}

	public List<DropdownItem> getActionDropdownItems() {
		return DropdownItemListBuilder.addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						_getEditDisplayPageActionUnsafeConsumer()
					).build());
				dropdownGroupItem.setSeparator(true);
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						_getMarkAsDefaultLayoutUtilityPageEntryActionUnsafeConsumer()
					).add(
						_getRenameUtilityPageActionUnsafeConsumer()
					).build());
				dropdownGroupItem.setSeparator(true);
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						_getDeleteLayoutUtilityPageActionUnsafeConsumer()
					).build());

				dropdownGroupItem.setSeparator(true);
			}
		).build();
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getDeleteLayoutUtilityPageActionUnsafeConsumer() {

		return dropdownItem -> {
			dropdownItem.putData("action", "deleteLayoutUtilityPage");

			String key = "are-you-sure-you-want-to-delete-this";

			if (_layoutUtilityPageEntry.isDefaultLayoutUtilityPageEntry()) {
				key =
					"are-you-sure-you-want-to-delete-the-default-utility-page";
			}

			dropdownItem.putData(
				"deleteLayoutUtilityPageMessage",
				LanguageUtil.get(_httpServletRequest, key));
			dropdownItem.putData(
				"deleteLayoutUtilityPageURL",
				PortletURLBuilder.createActionURL(
					_renderResponse
				).setActionName(
					"/layout_admin/delete_layout_utility_page_entry"
				).setRedirect(
					_themeDisplay.getURLCurrent()
				).setParameter(
					"layoutUtilityPageEntryId",
					_layoutUtilityPageEntry.getLayoutUtilityPageEntryId()
				).buildString());
			dropdownItem.setIcon("trash");
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "delete"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getEditDisplayPageActionUnsafeConsumer() {

		return dropdownItem -> {
			String layoutFullURL = PortalUtil.getLayoutFullURL(
				_draftLayout, _themeDisplay);

			layoutFullURL = HttpComponentsUtil.setParameter(
				layoutFullURL, "p_l_back_url", _themeDisplay.getURLCurrent());
			layoutFullURL = HttpComponentsUtil.setParameter(
				layoutFullURL, "p_l_mode", Constants.EDIT);

			dropdownItem.setHref(layoutFullURL);

			dropdownItem.setIcon("pencil");
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "edit"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getMarkAsDefaultLayoutUtilityPageEntryActionUnsafeConsumer() {

		return dropdownItem -> {
			dropdownItem.putData(
				"action", "markAsDefaultLayoutUtilityPageEntry");
			dropdownItem.putData(
				"markAsDefaultLayoutUtilityPageEntryURL",
				PortletURLBuilder.createActionURL(
					_renderResponse
				).setActionName(
					"/layout_admin/mark_as_default_layout_utility_page_entry"
				).setRedirect(
					_themeDisplay.getURLCurrent()
				).setParameter(
					"isDefaultLayoutUtilityPageEntry",
					!_layoutUtilityPageEntry.isDefaultLayoutUtilityPageEntry()
				).setParameter(
					"layoutUtilityPageEntryId",
					_layoutUtilityPageEntry.getLayoutUtilityPageEntryId()
				).buildString());

			String message = StringPool.BLANK;

			LayoutUtilityPageEntry defaultLayoutUtilityPageEntry =
				LayoutUtilityPageEntryLocalServiceUtil.
					fetchDefaultLayoutUtilityPageEntry(
						_layoutUtilityPageEntry.getGroupId(),
						_layoutUtilityPageEntry.getType());

			if (defaultLayoutUtilityPageEntry != null) {
				long defaultLayoutUtilityPageEntryId =
					defaultLayoutUtilityPageEntry.getLayoutUtilityPageEntryId();
				long layoutUtilityPageEntryId =
					_layoutUtilityPageEntry.getLayoutUtilityPageEntryId();

				if (defaultLayoutUtilityPageEntryId !=
						layoutUtilityPageEntryId) {

					message = LanguageUtil.format(
						_httpServletRequest,
						"do-you-want-to-replace-x-for-x-as-the-default-" +
							"utility-page-entry",
						new String[] {
							_layoutUtilityPageEntry.getName(),
							defaultLayoutUtilityPageEntry.getName()
						});
				}
			}

			if (Validator.isNull(message) &&
				_layoutUtilityPageEntry.isDefaultLayoutUtilityPageEntry()) {

				message = LanguageUtil.get(
					_httpServletRequest, "unmark-default-confirmation");
			}

			dropdownItem.putData("message", message);

			String label = "mark-as-default";

			if (_layoutUtilityPageEntry.isDefaultLayoutUtilityPageEntry()) {
				label = "unmark-as-default";
			}

			dropdownItem.setLabel(LanguageUtil.get(_httpServletRequest, label));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getRenameUtilityPageActionUnsafeConsumer() {

		return dropdownItem -> {
			dropdownItem.putData("action", "renameLayoutUtilityPageEntry");
			dropdownItem.putData(
				"layoutUtilityPageEntryId",
				String.valueOf(
					_layoutUtilityPageEntry.getLayoutUtilityPageEntryId()));
			dropdownItem.putData(
				"layoutUtilityPageEntryName",
				_layoutUtilityPageEntry.getName());
			dropdownItem.putData(
				"updateLayoutUtilityPageEntryURL",
				PortletURLBuilder.createActionURL(
					_renderResponse
				).setActionName(
					"/layout_admin/update_layout_utility_page_entry"
				).setRedirect(
					_themeDisplay.getURLCurrent()
				).setParameter(
					"layoutUtilityPageCollectionId",
					_layoutUtilityPageEntry.getCtCollectionId()
				).setParameter(
					"layoutUtilityPageEntryId",
					_layoutUtilityPageEntry.getLayoutUtilityPageEntryId()
				).buildString());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "rename"));
		};
	}

	private final Layout _draftLayout;
	private final HttpServletRequest _httpServletRequest;
	private final LayoutUtilityPageEntry _layoutUtilityPageEntry;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;

}