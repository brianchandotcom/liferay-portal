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

package com.liferay.fragment.web.internal.servlet.taglib.util;

import com.liferay.fragment.constants.FragmentActionKeys;
import com.liferay.fragment.constants.FragmentPortletKeys;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.web.internal.configuration.FragmentPortletConfiguration;
import com.liferay.fragment.web.internal.constants.FragmentWebKeys;
import com.liferay.fragment.web.internal.security.permission.resource.FragmentPermission;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.upload.criterion.UploadItemSelectorCriterion;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadServletRequestConfigurationHelperUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class FragmentEntryActionDropdownItemsProvider {

	public FragmentEntryActionDropdownItemsProvider(
		FragmentEntry fragmentEntry, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_fragmentEntry = fragmentEntry;
		_renderResponse = renderResponse;

		_request = PortalUtil.getHttpServletRequest(renderRequest);

		_fragmentPortletConfiguration =
			(FragmentPortletConfiguration)_request.getAttribute(
				FragmentPortletConfiguration.class.getName());
		_itemSelector = (ItemSelector)_request.getAttribute(
			FragmentWebKeys.ITEM_SELECTOR);
		_themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getActionDropdownItems() throws Exception {
		return new DropdownItemList() {
			{
				if (FragmentPermission.contains(
						_themeDisplay.getPermissionChecker(),
						_themeDisplay.getScopeGroupId(),
						FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES)) {

					add(_getEditFragmentEntryActionConsumer());
					add(_getRenameFragmentEntryActionConsumer());
					add(_getMoveFragmentEntryActionConsumer());
					add(_getUpdateFragmentEntryPreviewActionConsumer());
				}

				add(_getExportFragmentEntryActionConsumer());

				if (_fragmentEntry.getUsageCount() > 0) {
					add(_getViewFragmentEntryUsagesActionConsumer());
				}

				if (FragmentPermission.contains(
						_themeDisplay.getPermissionChecker(),
						_themeDisplay.getScopeGroupId(),
						FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES)) {

					add(_getDeleteFragmentEntryActionConsumer());
				}
			}
		};
	}

	private Consumer<DropdownItem> _getDeleteFragmentEntryActionConsumer() {
		PortletURL deleteFragmentEntryURL = _renderResponse.createActionURL();

		deleteFragmentEntryURL.setParameter(
			ActionRequest.ACTION_NAME, "/fragment/delete_fragment_entries");

		deleteFragmentEntryURL.setParameter(
			"redirect", _themeDisplay.getURLCurrent());
		deleteFragmentEntryURL.setParameter(
			"fragmentEntryId",
			String.valueOf(_fragmentEntry.getFragmentEntryId()));

		return dropdownItem -> {
			dropdownItem.putData("action", "deleteFragmentEntry");
			dropdownItem.putData(
				"deleteFragmentEntryURL", deleteFragmentEntryURL.toString());
			dropdownItem.setLabel(LanguageUtil.get(_request, "delete"));
		};
	}

	private Consumer<DropdownItem> _getEditFragmentEntryActionConsumer() {
		return dropdownItem -> {
			dropdownItem.setHref(
				_renderResponse.createRenderURL(), "mvcRenderCommandName",
				"/fragment/edit_fragment_entry", "redirect",
				_themeDisplay.getURLCurrent(), "fragmentCollectionId",
				_fragmentEntry.getFragmentCollectionId(), "fragmentEntryId",
				_fragmentEntry.getFragmentEntryId());
			dropdownItem.setLabel(LanguageUtil.get(_request, "edit"));
		};
	}

	private Consumer<DropdownItem> _getExportFragmentEntryActionConsumer() {
		ResourceURL exportFragmentEntryURL =
			_renderResponse.createResourceURL();

		exportFragmentEntryURL.setParameter(
			"fragmentEntryId",
			String.valueOf(_fragmentEntry.getFragmentEntryId()));
		exportFragmentEntryURL.setResourceID(
			"/fragment/export_fragment_entries");

		return dropdownItem -> {
			dropdownItem.setHref(exportFragmentEntryURL);
			dropdownItem.setLabel(LanguageUtil.get(_request, "export"));
		};
	}

	private String _getItemSelectorURL() {
		PortletURL uploadURL = _renderResponse.createActionURL();

		uploadURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/fragment/upload_fragment_entry_preview");

		ItemSelectorCriterion uploadItemSelectorCriterion =
			new UploadItemSelectorCriterion(
				FragmentPortletKeys.FRAGMENT, uploadURL.toString(),
				LanguageUtil.get(_themeDisplay.getLocale(), "fragments"),
				UploadServletRequestConfigurationHelperUtil.getMaxSize(),
				_fragmentPortletConfiguration.thumbnailExtensions());

		List<ItemSelectorReturnType> uploadDesiredItemSelectorReturnTypes =
			new ArrayList<>();

		uploadDesiredItemSelectorReturnTypes.add(
			new FileEntryItemSelectorReturnType());

		uploadItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			uploadDesiredItemSelectorReturnTypes);

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			RequestBackedPortletURLFactoryUtil.create(_request),
			_renderResponse.getNamespace() + "changePreview",
			uploadItemSelectorCriterion);

		itemSelectorURL.setParameter(
			"fragmentEntryId",
			String.valueOf(_fragmentEntry.getFragmentEntryId()));

		return itemSelectorURL.toString();
	}

	private Consumer<DropdownItem> _getMoveFragmentEntryActionConsumer()
		throws Exception {

		PortletURL selectFragmentCollectionURL =
			_renderResponse.createRenderURL();

		selectFragmentCollectionURL.setParameter(
			"mvcRenderCommandName", "/fragment/select_fragment_collection");

		selectFragmentCollectionURL.setWindowState(LiferayWindowState.POP_UP);

		return dropdownItem -> {
			dropdownItem.putData("action", "moveFragmentEntry");
			dropdownItem.putData(
				"fragmentEntryId",
				String.valueOf(_fragmentEntry.getFragmentEntryId()));
			dropdownItem.putData(
				"selectFragmentCollectionURL",
				selectFragmentCollectionURL.toString());
			dropdownItem.setLabel(LanguageUtil.get(_request, "move"));
		};
	}

	private Consumer<DropdownItem> _getRenameFragmentEntryActionConsumer() {
		PortletURL updateFragmentEntryURL = _renderResponse.createActionURL();

		updateFragmentEntryURL.setParameter(
			ActionRequest.ACTION_NAME, "/fragment/update_fragment_entry");

		updateFragmentEntryURL.setParameter(
			"fragmentCollectionId",
			String.valueOf(_fragmentEntry.getFragmentCollectionId()));
		updateFragmentEntryURL.setParameter(
			"fragmentEntryId",
			String.valueOf(_fragmentEntry.getFragmentEntryId()));

		return dropdownItem -> {
			dropdownItem.putData("action", "renameFragmentEntry");
			dropdownItem.putData(
				"updateFragmentEntryURL", updateFragmentEntryURL.toString());
			dropdownItem.putData(
				"fragmentEntryId",
				String.valueOf(_fragmentEntry.getFragmentEntryId()));
			dropdownItem.putData("fragmentEntryName", _fragmentEntry.getName());
			dropdownItem.setLabel(LanguageUtil.get(_request, "rename"));
		};
	}

	private Consumer<DropdownItem>
		_getUpdateFragmentEntryPreviewActionConsumer() {

		return dropdownItem -> {
			dropdownItem.putData("action", "updateFragmentEntryPreview");
			dropdownItem.putData(
				"fragmentEntryId",
				String.valueOf(_fragmentEntry.getFragmentEntryId()));
			dropdownItem.putData("itemSelectorURL", _getItemSelectorURL());
			dropdownItem.setLabel(
				LanguageUtil.get(_request, "change-thumbnail"));
		};
	}

	private Consumer<DropdownItem> _getViewFragmentEntryUsagesActionConsumer() {
		return dropdownItem -> {
			dropdownItem.setHref(
				_renderResponse.createRenderURL(), "mvcRenderCommandName",
				"/fragment/view_fragment_entry_usages", "redirect",
				_themeDisplay.getURLCurrent(), "fragmentCollectionId",
				_fragmentEntry.getFragmentCollectionId(), "fragmentEntryId",
				_fragmentEntry.getFragmentEntryId());
			dropdownItem.setLabel(LanguageUtil.get(_request, "view-usages"));
		};
	}

	private final FragmentEntry _fragmentEntry;
	private final FragmentPortletConfiguration _fragmentPortletConfiguration;
	private final ItemSelector _itemSelector;
	private final RenderResponse _renderResponse;
	private final HttpServletRequest _request;
	private final ThemeDisplay _themeDisplay;

}