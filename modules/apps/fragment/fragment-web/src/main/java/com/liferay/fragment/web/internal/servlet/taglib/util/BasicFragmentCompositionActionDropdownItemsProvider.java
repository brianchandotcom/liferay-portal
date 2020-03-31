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
import com.liferay.fragment.model.FragmentComposition;
import com.liferay.fragment.web.internal.configuration.FragmentPortletConfiguration;
import com.liferay.fragment.web.internal.constants.FragmentWebKeys;
import com.liferay.fragment.web.internal.security.permission.resource.FragmentPermission;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.upload.criterion.UploadItemSelectorCriterion;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadServletRequestConfigurationHelperUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pavel Savinov
 */
public class BasicFragmentCompositionActionDropdownItemsProvider {

	public BasicFragmentCompositionActionDropdownItemsProvider(
		FragmentComposition fragmentComposition, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_fragmentComposition = fragmentComposition;
		_renderResponse = renderResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);

		_fragmentPortletConfiguration =
			(FragmentPortletConfiguration)_httpServletRequest.getAttribute(
				FragmentPortletConfiguration.class.getName());
		_itemSelector = (ItemSelector)_httpServletRequest.getAttribute(
			FragmentWebKeys.ITEM_SELECTOR);
		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getActionDropdownItems() throws Exception {
		boolean hasManageFragmentEntriesPermission =
			FragmentPermission.contains(
				_themeDisplay.getPermissionChecker(),
				_themeDisplay.getScopeGroupId(),
				FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		return DropdownItemListBuilder.add(
			() -> hasManageFragmentEntriesPermission,
			_getRenameFragmentCompositionActionUnsafeConsumer()
		).add(
			() -> hasManageFragmentEntriesPermission,
			_getMoveFragmentCompositionActionUnsafeConsumer()
		).add(
			() -> hasManageFragmentEntriesPermission,
			_getUpdateFragmentCompositionPreviewActionUnsafeConsumer()
		).add(
			() ->
				hasManageFragmentEntriesPermission &&
				(_fragmentComposition.getPreviewFileEntryId() > 0),
			_getDeleteFragmentCompositionPreviewActionUnsafeConsumer()
		).add(
			() -> hasManageFragmentEntriesPermission,
			_getExportFragmentCompositionActionUnsafeConsumer()
		).add(
			() -> hasManageFragmentEntriesPermission,
			_getDeleteFragmentCompositionActionUnsafeConsumer()
		).build();
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getDeleteFragmentCompositionActionUnsafeConsumer() {

		PortletURL deleteFragmentCompositionURL =
			_renderResponse.createActionURL();

		deleteFragmentCompositionURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/fragment/delete_fragment_compositions");
		deleteFragmentCompositionURL.setParameter(
			"redirect", _themeDisplay.getURLCurrent());
		deleteFragmentCompositionURL.setParameter(
			"fragmentCompositionId",
			String.valueOf(_fragmentComposition.getFragmentCompositionId()));

		return dropdownItem -> {
			dropdownItem.putData("action", "deleteFragmentComposition");
			dropdownItem.putData(
				"deleteFragmentCompositionURL",
				deleteFragmentCompositionURL.toString());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "delete"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getDeleteFragmentCompositionPreviewActionUnsafeConsumer() {

		PortletURL deleteFragmentCompositionPreviewURL =
			_renderResponse.createActionURL();

		deleteFragmentCompositionPreviewURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/fragment/delete_fragment_composition_preview");
		deleteFragmentCompositionPreviewURL.setParameter(
			"fragmentCompositionId",
			String.valueOf(_fragmentComposition.getFragmentCompositionId()));

		return dropdownItem -> {
			dropdownItem.putData("action", "deleteFragmentCompositionPreview");
			dropdownItem.putData(
				"deleteFragmentCompositionPreviewURL",
				deleteFragmentCompositionPreviewURL.toString());
			dropdownItem.putData(
				"fragmentCompositionId",
				String.valueOf(
					_fragmentComposition.getFragmentCompositionId()));
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "remove-thumbnail"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getExportFragmentCompositionActionUnsafeConsumer() {

		ResourceURL exportFragmentEntryURL =
			_renderResponse.createResourceURL();

		exportFragmentEntryURL.setParameter(
			"fragmentCompositionId",
			String.valueOf(_fragmentComposition.getFragmentCompositionId()));
		exportFragmentEntryURL.setResourceID(
			"/fragment/export_fragment_compositions_and_fragment_entries");

		return dropdownItem -> {
			dropdownItem.setHref(exportFragmentEntryURL);
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "export"));
		};
	}

	private String _getItemSelectorURL() {
		PortletURL uploadURL = _renderResponse.createActionURL();

		uploadURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/fragment/upload_fragment_composition_preview");

		ItemSelectorCriterion itemSelectorCriterion =
			new UploadItemSelectorCriterion(
				FragmentPortletKeys.FRAGMENT, uploadURL.toString(),
				LanguageUtil.get(_themeDisplay.getLocale(), "fragments"),
				UploadServletRequestConfigurationHelperUtil.getMaxSize(),
				_fragmentPortletConfiguration.thumbnailExtensions());

		itemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new FileEntryItemSelectorReturnType());

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			RequestBackedPortletURLFactoryUtil.create(_httpServletRequest),
			_renderResponse.getNamespace() + "changePreview",
			itemSelectorCriterion);

		itemSelectorURL.setParameter(
			"fragmentCompositionId",
			String.valueOf(_fragmentComposition.getFragmentCompositionId()));

		return itemSelectorURL.toString();
	}

	private UnsafeConsumer<DropdownItem, Exception>
			_getMoveFragmentCompositionActionUnsafeConsumer()
		throws Exception {

		PortletURL selectFragmentCollectionURL =
			_renderResponse.createRenderURL();

		selectFragmentCollectionURL.setParameter(
			"mvcRenderCommandName", "/fragment/select_fragment_collection");
		selectFragmentCollectionURL.setWindowState(LiferayWindowState.POP_UP);

		PortletURL moveFragmentCompositionURL =
			_renderResponse.createActionURL();

		moveFragmentCompositionURL.setParameter(
			ActionRequest.ACTION_NAME, "/fragment/move_fragment_composition");
		moveFragmentCompositionURL.setParameter(
			"redirect", _themeDisplay.getURLCurrent());

		return dropdownItem -> {
			dropdownItem.putData("action", "moveFragmentComposition");
			dropdownItem.putData(
				"fragmentCompositionId",
				String.valueOf(
					_fragmentComposition.getFragmentCompositionId()));
			dropdownItem.putData(
				"moveFragmentCompositionURL",
				moveFragmentCompositionURL.toString());
			dropdownItem.putData(
				"selectFragmentCollectionURL",
				selectFragmentCollectionURL.toString());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "move"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getRenameFragmentCompositionActionUnsafeConsumer() {

		PortletURL renameFragmentCompositionURL =
			_renderResponse.createActionURL();

		renameFragmentCompositionURL.setParameter(
			ActionRequest.ACTION_NAME, "/fragment/rename_fragment_composition");
		renameFragmentCompositionURL.setParameter(
			"fragmentCompositionId",
			String.valueOf(_fragmentComposition.getFragmentCompositionId()));

		return dropdownItem -> {
			dropdownItem.putData("action", "renameFragmentComposition");
			dropdownItem.putData(
				"fragmentCompositionId",
				String.valueOf(
					_fragmentComposition.getFragmentCompositionId()));
			dropdownItem.putData(
				"fragmentCompositionName", _fragmentComposition.getName());
			dropdownItem.putData(
				"renameFragmentCompositionURL",
				renameFragmentCompositionURL.toString());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "rename"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getUpdateFragmentCompositionPreviewActionUnsafeConsumer() {

		return dropdownItem -> {
			dropdownItem.putData("action", "updateFragmentCompositionPreview");
			dropdownItem.putData(
				"fragmentCompositionId",
				String.valueOf(
					_fragmentComposition.getFragmentCompositionId()));
			dropdownItem.putData("itemSelectorURL", _getItemSelectorURL());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "change-thumbnail"));
		};
	}

	private final FragmentComposition _fragmentComposition;
	private final FragmentPortletConfiguration _fragmentPortletConfiguration;
	private final HttpServletRequest _httpServletRequest;
	private final ItemSelector _itemSelector;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;

}