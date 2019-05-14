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
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.upload.criterion.UploadItemSelectorCriterion;
import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.layout.admin.web.internal.configuration.LayoutAdminWebConfiguration;
import com.liferay.layout.admin.web.internal.constants.LayoutAdminWebKeys;
import com.liferay.layout.admin.web.internal.security.permission.resource.LayoutPageTemplateEntryPermission;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.LayoutPrototypeServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadServletRequestConfigurationHelperUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.security.PermissionsURLTag;

import java.util.List;
import java.util.Objects;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class LayoutPageTemplateEntryActionDropdownItemsProvider {

	public LayoutPageTemplateEntryActionDropdownItemsProvider(
		LayoutPageTemplateEntry layoutPageTemplateEntry,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_layoutPageTemplateEntry = layoutPageTemplateEntry;
		_renderResponse = renderResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);

		_itemSelector = (ItemSelector)_httpServletRequest.getAttribute(
			LayoutAdminWebKeys.ITEM_SELECTOR);
		_layoutAdminWebConfiguration =
			(LayoutAdminWebConfiguration)_httpServletRequest.getAttribute(
				LayoutAdminWebConfiguration.class.getName());
		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getActionDropdownItems() throws Exception {
		return new DropdownItemList() {
			{
				if (LayoutPageTemplateEntryPermission.contains(
						_themeDisplay.getPermissionChecker(),
						_layoutPageTemplateEntry, ActionKeys.UPDATE)) {

					add(
						_getUpdateLayoutPageTemplateEntryPreviewActionUnsafeConsumer());

					if (_layoutPageTemplateEntry.getPreviewFileEntryId() > 0) {
						add(
							_getDeleteLayoutPageTemplateEntryPreviewActionUnsafeConsumer());
					}

					add(
						_getRenameLayoutPageTemplateEntryActionUnsafeConsumer());

					add(
						_getConfigureLayoutPageTemplateEntryActionUnsafeConsumer());
				}

				if (LayoutPageTemplateEntryPermission.contains(
						_themeDisplay.getPermissionChecker(),
						_layoutPageTemplateEntry, ActionKeys.PERMISSIONS)) {

					add(
						_getPermissionsLayoutPageTemplateEntryActionUnsafeConsumer());
				}

				if (LayoutPageTemplateEntryPermission.contains(
						_themeDisplay.getPermissionChecker(),
						_layoutPageTemplateEntry, ActionKeys.DELETE)) {

					add(
						_getDeleteLayoutPageTemplateEntryActionUnsafeConsumer());
				}
			}
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getConfigureLayoutPageTemplateEntryActionUnsafeConsumer() {

		return dropdownItem -> {
			dropdownItem.setHref(
				_renderResponse.createRenderURL(), "mvcRenderCommandName",
				"/layout/edit_layout", "redirect",
				_themeDisplay.getURLCurrent(), "backURL",
				_themeDisplay.getURLCurrent(), "selPlid",
				_layoutPageTemplateEntry.getPlid());

			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "configure"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getDeleteLayoutPageTemplateEntryActionUnsafeConsumer() {

		PortletURL deleteLayoutPageTemplateEntryURL =
			_renderResponse.createActionURL();

		deleteLayoutPageTemplateEntryURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/layout/delete_layout_page_template_entry");
		deleteLayoutPageTemplateEntryURL.setParameter(
			"redirect", _themeDisplay.getURLCurrent());
		deleteLayoutPageTemplateEntryURL.setParameter(
			"layoutPageTemplateEntryId",
			String.valueOf(
				_layoutPageTemplateEntry.getLayoutPageTemplateEntryId()));

		return dropdownItem -> {
			dropdownItem.putData("action", "deleteLayoutPageTemplateEntry");
			dropdownItem.putData(
				"deleteLayoutPageTemplateEntryURL",
				deleteLayoutPageTemplateEntryURL.toString());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "delete"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getDeleteLayoutPageTemplateEntryPreviewActionUnsafeConsumer() {

		PortletURL deleteLayoutPageTemplateEntryPreviewURL =
			_renderResponse.createActionURL();

		deleteLayoutPageTemplateEntryPreviewURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/layout/delete_layout_page_template_entry_preview");

		deleteLayoutPageTemplateEntryPreviewURL.setParameter(
			"redirect", _themeDisplay.getURLCurrent());
		deleteLayoutPageTemplateEntryPreviewURL.setParameter(
			"layoutPageTemplateEntryId",
			String.valueOf(
				_layoutPageTemplateEntry.getLayoutPageTemplateEntryId()));

		return dropdownItem -> {
			dropdownItem.putData(
				"action", "deleteLayoutPageTemplateEntryPreview");
			dropdownItem.putData(
				"deleteLayoutPageTemplateEntryPreviewURL",
				deleteLayoutPageTemplateEntryPreviewURL.toString());
			dropdownItem.putData(
				"layoutPageTemplateEntryId",
				String.valueOf(
					_layoutPageTemplateEntry.getLayoutPageTemplateEntryId()));
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "remove-thumbnail"));
		};
	}

	private String _getItemSelectorURL() {
		PortletURL uploadURL = _renderResponse.createActionURL();

		uploadURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/layout/upload_layout_page_template_entry_preview");
		uploadURL.setParameter(
			"layoutPageTemplateEntryId",
			String.valueOf(
				_layoutPageTemplateEntry.getLayoutPageTemplateEntryId()));

		ItemSelectorCriterion itemSelectorCriterion =
			new UploadItemSelectorCriterion(
				LayoutAdminPortletKeys.GROUP_PAGES, uploadURL.toString(),
				LanguageUtil.get(_themeDisplay.getLocale(), "page-template"),
				UploadServletRequestConfigurationHelperUtil.getMaxSize(),
				_layoutAdminWebConfiguration.thumbnailExtensions());

		itemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new FileEntryItemSelectorReturnType());

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			RequestBackedPortletURLFactoryUtil.create(_httpServletRequest),
			_renderResponse.getNamespace() + "changePreview",
			itemSelectorCriterion);

		return itemSelectorURL.toString();
	}

	private UnsafeConsumer<DropdownItem, Exception>
			_getPermissionsLayoutPageTemplateEntryActionUnsafeConsumer()
		throws Exception {

		String permissionsLayoutPageTemplateEntryURL = PermissionsURLTag.doTag(
			StringPool.BLANK, LayoutPageTemplateEntry.class.getName(),
			_layoutPageTemplateEntry.getName(), null,
			String.valueOf(
				_layoutPageTemplateEntry.getLayoutPageTemplateEntryId()),
			LiferayWindowState.POP_UP.toString(), null, _httpServletRequest);

		return dropdownItem -> {
			dropdownItem.putData(
				"action", "permissionsLayoutPageTemplateEntry");
			dropdownItem.putData(
				"permissionsLayoutPageTemplateEntryURL",
				permissionsLayoutPageTemplateEntryURL);
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "permissions"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
			_getRenameLayoutPageTemplateEntryActionUnsafeConsumer()
		throws PortalException {

		if (Objects.equals(
				_layoutPageTemplateEntry.getType(),
				LayoutPageTemplateEntryTypeConstants.TYPE_WIDGET_PAGE)) {

			LayoutPrototype layoutPrototype =
				LayoutPrototypeServiceUtil.fetchLayoutPrototype(
					_layoutPageTemplateEntry.getLayoutPrototypeId());

			return dropdownItem -> {
				dropdownItem.putData("action", "renameLayoutPageTemplateEntry");
				dropdownItem.putData("idFieldName", "layoutPrototypeId");
				dropdownItem.putData(
					"idFieldValue",
					String.valueOf(layoutPrototype.getLayoutPrototypeId()));
				dropdownItem.putData(
					"layoutPageTemplateEntryName",
					_layoutPageTemplateEntry.getName());
				dropdownItem.putData(
					"updateLayoutPageTemplateEntryURL",
					_getUpdateLayoutPrototypeURL(layoutPrototype));
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "rename"));
			};
		}

		return dropdownItem -> {
			dropdownItem.putData("action", "renameLayoutPageTemplateEntry");
			dropdownItem.putData("idFieldName", "layoutPageTemplateEntryId");
			dropdownItem.putData(
				"idFieldValue",
				String.valueOf(
					_layoutPageTemplateEntry.getLayoutPageTemplateEntryId()));
			dropdownItem.putData(
				"layoutPageTemplateEntryName",
				_layoutPageTemplateEntry.getName());
			dropdownItem.putData(
				"updateLayoutPageTemplateEntryURL",
				_getUpdateLayoutPageTemplateEntryURL());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "rename"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getUpdateLayoutPageTemplateEntryPreviewActionUnsafeConsumer() {

		return dropdownItem -> {
			dropdownItem.putData(
				"action", "updateLayoutPageTemplateEntryPreview");
			dropdownItem.putData("itemSelectorURL", _getItemSelectorURL());
			dropdownItem.putData(
				"layoutPageTemplateEntryId",
				String.valueOf(
					_layoutPageTemplateEntry.getLayoutPageTemplateEntryId()));
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "change-thumbnail"));
		};
	}

	private String _getUpdateLayoutPageTemplateEntryURL() {
		PortletURL updateLayoutPageTemplateEntryURL =
			_renderResponse.createActionURL();

		updateLayoutPageTemplateEntryURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/layout/update_layout_page_template_entry");
		updateLayoutPageTemplateEntryURL.setParameter(
			"redirect", _themeDisplay.getURLCurrent());
		updateLayoutPageTemplateEntryURL.setParameter(
			"layoutPageTemplateCollectionId",
			String.valueOf(
				_layoutPageTemplateEntry.getLayoutPageTemplateCollectionId()));
		updateLayoutPageTemplateEntryURL.setParameter(
			"layoutPageTemplateEntryId",
			String.valueOf(
				_layoutPageTemplateEntry.getLayoutPageTemplateEntryId()));

		return updateLayoutPageTemplateEntryURL.toString();
	}

	private String _getUpdateLayoutPrototypeURL(
		LayoutPrototype layoutPrototype) {

		PortletURL updateLayoutPrototypeURL = _renderResponse.createActionURL();

		updateLayoutPrototypeURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/layout_prototype/update_layout_prototype");
		updateLayoutPrototypeURL.setParameter(
			"redirect", _themeDisplay.getURLCurrent());
		updateLayoutPrototypeURL.setParameter(
			"layoutPrototypeId",
			String.valueOf(layoutPrototype.getLayoutPrototypeId()));

		return updateLayoutPrototypeURL.toString();
	}

	private final HttpServletRequest _httpServletRequest;
	private final ItemSelector _itemSelector;
	private final LayoutAdminWebConfiguration _layoutAdminWebConfiguration;
	private final LayoutPageTemplateEntry _layoutPageTemplateEntry;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;

}