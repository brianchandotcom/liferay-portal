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
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.constants.FragmentPortletKeys;
import com.liferay.fragment.model.FragmentEntry;
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
 * @author Eudaldo Alonso
 */
public class BasicFragmentEntryActionDropdownItemsProvider {

	public BasicFragmentEntryActionDropdownItemsProvider(
		FragmentEntry fragmentEntry, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_fragmentEntry = fragmentEntry;
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
			() ->
				hasManageFragmentEntriesPermission &&
				(_fragmentEntry.getType() != FragmentConstants.TYPE_REACT),
			_getEditFragmentEntryActionUnsafeConsumer()
		).add(
			() ->
				hasManageFragmentEntriesPermission &&
				!_fragmentEntry.isReadOnly(),
			_getRenameFragmentEntryActionUnsafeConsumer()
		).add(
			() ->
				hasManageFragmentEntriesPermission &&
				!_fragmentEntry.isReadOnly(),
			_getMoveFragmentEntryActionUnsafeConsumer()
		).add(
			() -> hasManageFragmentEntriesPermission,
			_getCopyFragmentEntryActionUnsafeConsumer()
		).add(
			() ->
				hasManageFragmentEntriesPermission &&
				!_fragmentEntry.isReadOnly(),
			_getUpdateFragmentEntryPreviewActionUnsafeConsumer()
		).add(
			() ->
				hasManageFragmentEntriesPermission &&
				!_fragmentEntry.isReadOnly() &&
				(_fragmentEntry.getGroupId() ==
					_themeDisplay.getCompanyGroupId()) &&
				(_fragmentEntry.getGlobalUsageCount() > 0),
			_getViewGroupFragmentEntryUsagesActionUnsafeConsumer()
		).add(
			() ->
				hasManageFragmentEntriesPermission &&
				!_fragmentEntry.isReadOnly() &&
				(_fragmentEntry.getPreviewFileEntryId() > 0),
			_getDeleteFragmentEntryPreviewActionUnsafeConsumer()
		).add(
			() ->
				hasManageFragmentEntriesPermission &&
				!_fragmentEntry.isReadOnly() &&
				(_fragmentEntry.getType() != FragmentConstants.TYPE_REACT),
			_getExportFragmentEntryActionUnsafeConsumer()
		).add(
			() ->
				hasManageFragmentEntriesPermission &&
				!_fragmentEntry.isReadOnly() &&
				(_fragmentEntry.getGroupId() !=
					_themeDisplay.getCompanyGroupId()) &&
				(_fragmentEntry.getUsageCount() > 0),
			_getViewFragmentEntryUsagesActionUnsafeConsumer()
		).add(
			() ->
				hasManageFragmentEntriesPermission &&
				!_fragmentEntry.isReadOnly(),
			_getDeleteFragmentEntryActionUnsafeConsumer()
		).add(
			() ->
				hasManageFragmentEntriesPermission &&
				!_fragmentEntry.isReadOnly() &&
				(_fragmentEntry.isDraft() ||
				 (_fragmentEntry.fetchDraftFragmentEntry() != null)) &&
				(_fragmentEntry.getType() != FragmentConstants.TYPE_REACT),
			_getDeleteDraftFragmentEntryActionUnsafeConsumer()
		).build();
	}

	private UnsafeConsumer<DropdownItem, Exception>
			_getCopyFragmentEntryActionUnsafeConsumer()
		throws Exception {

		PortletURL selectFragmentCollectionURL =
			_renderResponse.createRenderURL();

		selectFragmentCollectionURL.setParameter(
			"mvcRenderCommandName", "/fragment/select_fragment_collection");

		selectFragmentCollectionURL.setWindowState(LiferayWindowState.POP_UP);

		PortletURL copyFragmentEntryURL = _renderResponse.createActionURL();

		copyFragmentEntryURL.setParameter(
			ActionRequest.ACTION_NAME, "/fragment/copy_fragment_entry");
		copyFragmentEntryURL.setParameter(
			"redirect", _themeDisplay.getURLCurrent());

		return dropdownItem -> {
			dropdownItem.putData("action", "copyFragmentEntry");
			dropdownItem.putData(
				"copyFragmentEntryURL", copyFragmentEntryURL.toString());
			dropdownItem.putData(
				"fragmentCollectionId",
				String.valueOf(_fragmentEntry.getFragmentCollectionId()));
			dropdownItem.putData(
				"fragmentEntryId",
				String.valueOf(_fragmentEntry.getFragmentEntryId()));
			dropdownItem.putData(
				"selectFragmentCollectionURL",
				selectFragmentCollectionURL.toString());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "make-a-copy"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getDeleteDraftFragmentEntryActionUnsafeConsumer() {

		PortletURL deleteDraftFragmentEntryURL =
			_renderResponse.createActionURL();

		deleteDraftFragmentEntryURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/fragment/delete_draft_fragment_entries");
		deleteDraftFragmentEntryURL.setParameter(
			"redirect", _themeDisplay.getURLCurrent());
		deleteDraftFragmentEntryURL.setParameter(
			"fragmentEntryId",
			String.valueOf(_fragmentEntry.getFragmentEntryId()));

		return dropdownItem -> {
			dropdownItem.putData("action", "deleteDraftFragmentEntry");
			dropdownItem.putData(
				"deleteDraftFragmentEntryURL",
				deleteDraftFragmentEntryURL.toString());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "discard-draft"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getDeleteFragmentEntryActionUnsafeConsumer() {

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
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "delete"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getDeleteFragmentEntryPreviewActionUnsafeConsumer() {

		PortletURL deleteFragmentEntryPreviewURL =
			_renderResponse.createActionURL();

		deleteFragmentEntryPreviewURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/fragment/delete_fragment_entry_preview");
		deleteFragmentEntryPreviewURL.setParameter(
			"fragmentEntryId",
			String.valueOf(_fragmentEntry.getFragmentEntryId()));

		return dropdownItem -> {
			dropdownItem.putData("action", "deleteFragmentEntryPreview");
			dropdownItem.putData(
				"deleteFragmentEntryPreviewURL",
				deleteFragmentEntryPreviewURL.toString());
			dropdownItem.putData(
				"fragmentEntryId",
				String.valueOf(_fragmentEntry.getFragmentEntryId()));
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "remove-thumbnail"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getEditFragmentEntryActionUnsafeConsumer() {

		FragmentEntry fragmentEntry = null;

		if (_fragmentEntry.isDraft()) {
			fragmentEntry = _fragmentEntry;
		}
		else {
			fragmentEntry = _fragmentEntry.fetchDraftFragmentEntry();
		}

		if (fragmentEntry == null) {
			fragmentEntry = _fragmentEntry;
		}

		FragmentEntry editFragmentEntry = fragmentEntry;

		return dropdownItem -> {
			dropdownItem.setHref(
				_renderResponse.createRenderURL(), "mvcRenderCommandName",
				"/fragment/edit_fragment_entry", "redirect",
				_themeDisplay.getURLCurrent(), "fragmentCollectionId",
				editFragmentEntry.getFragmentCollectionId(), "fragmentEntryId",
				editFragmentEntry.getFragmentEntryId());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "edit"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getExportFragmentEntryActionUnsafeConsumer() {

		ResourceURL exportFragmentEntryURL =
			_renderResponse.createResourceURL();

		exportFragmentEntryURL.setParameter(
			"fragmentEntryId",
			String.valueOf(_fragmentEntry.getFragmentEntryId()));
		exportFragmentEntryURL.setResourceID(
			"/fragment/export_fragment_compositions_and_fragment_entries");

		return dropdownItem -> {
			dropdownItem.setDisabled(_fragmentEntry.isDraft());
			dropdownItem.setHref(exportFragmentEntryURL);
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "export"));
		};
	}

	private String _getItemSelectorURL() {
		PortletURL uploadURL = _renderResponse.createActionURL();

		uploadURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/fragment/upload_fragment_entry_preview");

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
			"fragmentEntryId",
			String.valueOf(_fragmentEntry.getFragmentEntryId()));

		return itemSelectorURL.toString();
	}

	private UnsafeConsumer<DropdownItem, Exception>
			_getMoveFragmentEntryActionUnsafeConsumer()
		throws Exception {

		PortletURL selectFragmentCollectionURL =
			_renderResponse.createRenderURL();

		selectFragmentCollectionURL.setParameter(
			"mvcRenderCommandName", "/fragment/select_fragment_collection");

		selectFragmentCollectionURL.setWindowState(LiferayWindowState.POP_UP);

		PortletURL moveFragmentEntryURL = _renderResponse.createActionURL();

		moveFragmentEntryURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/fragment/move_fragment_compositions_and_fragment_entries");
		moveFragmentEntryURL.setParameter(
			"redirect", _themeDisplay.getURLCurrent());

		return dropdownItem -> {
			dropdownItem.putData("action", "moveFragmentEntry");
			dropdownItem.putData(
				"fragmentEntryId",
				String.valueOf(_fragmentEntry.getFragmentEntryId()));
			dropdownItem.putData(
				"moveFragmentEntryURL", moveFragmentEntryURL.toString());
			dropdownItem.putData(
				"selectFragmentCollectionURL",
				selectFragmentCollectionURL.toString());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "move"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getRenameFragmentEntryActionUnsafeConsumer() {

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
				"fragmentEntryId",
				String.valueOf(_fragmentEntry.getFragmentEntryId()));
			dropdownItem.putData("fragmentEntryName", _fragmentEntry.getName());
			dropdownItem.putData(
				"updateFragmentEntryURL", updateFragmentEntryURL.toString());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "rename"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getUpdateFragmentEntryPreviewActionUnsafeConsumer() {

		return dropdownItem -> {
			dropdownItem.putData("action", "updateFragmentEntryPreview");
			dropdownItem.putData(
				"fragmentEntryId",
				String.valueOf(_fragmentEntry.getFragmentEntryId()));
			dropdownItem.putData("itemSelectorURL", _getItemSelectorURL());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "change-thumbnail"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getViewFragmentEntryUsagesActionUnsafeConsumer() {

		return dropdownItem -> {
			dropdownItem.setHref(
				_renderResponse.createRenderURL(), "mvcRenderCommandName",
				"/fragment/view_fragment_entry_usages", "redirect",
				_themeDisplay.getURLCurrent(), "fragmentCollectionId",
				_fragmentEntry.getFragmentCollectionId(), "fragmentEntryId",
				_fragmentEntry.getFragmentEntryId());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "view-usages"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getViewGroupFragmentEntryUsagesActionUnsafeConsumer() {

		return dropdownItem -> {
			dropdownItem.setHref(
				_renderResponse.createRenderURL(), "mvcRenderCommandName",
				"/fragment/view_group_fragment_entry_usages", "redirect",
				_themeDisplay.getURLCurrent(), "fragmentCollectionId",
				_fragmentEntry.getFragmentCollectionId(), "fragmentEntryId",
				_fragmentEntry.getFragmentEntryId());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "view-site-usages"));
		};
	}

	private final FragmentEntry _fragmentEntry;
	private final FragmentPortletConfiguration _fragmentPortletConfiguration;
	private final HttpServletRequest _httpServletRequest;
	private final ItemSelector _itemSelector;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;

}