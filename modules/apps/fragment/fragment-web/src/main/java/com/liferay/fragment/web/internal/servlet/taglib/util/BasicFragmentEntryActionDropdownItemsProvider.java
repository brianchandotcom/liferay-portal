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
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadServletRequestConfigurationHelperUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

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

		return DropdownItemListBuilder.addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						() ->
							hasManageFragmentEntriesPermission &&
							(_fragmentEntry.getType() !=
								FragmentConstants.TYPE_REACT),
						_getEditFragmentEntryActionUnsafeConsumer()
					).build());
				dropdownGroupItem.setSeparator(true);
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						() ->
							hasManageFragmentEntriesPermission &&
							!_fragmentEntry.isReadOnly(),
						_getUpdateFragmentEntryPreviewActionUnsafeConsumer()
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
							(_fragmentEntry.isDraft() ||
							 (_fragmentEntry.fetchDraftFragmentEntry() !=
								 null)) &&
							(_fragmentEntry.getType() !=
								FragmentConstants.TYPE_REACT),
						_getDeleteDraftFragmentEntryActionUnsafeConsumer()
					).add(
						() ->
							hasManageFragmentEntriesPermission &&
							!_fragmentEntry.isReadOnly(),
						_getRenameFragmentEntryActionUnsafeConsumer()
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
							(_fragmentEntry.getGroupId() !=
								_themeDisplay.getCompanyGroupId()) &&
							(_fragmentEntry.getUsageCount() > 0),
						_getViewFragmentEntryUsagesActionUnsafeConsumer()
					).build());
				dropdownGroupItem.setSeparator(true);
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						() ->
							hasManageFragmentEntriesPermission &&
							!_fragmentEntry.isReadOnly() &&
							(_fragmentEntry.getType() !=
								FragmentConstants.TYPE_REACT),
						_getExportFragmentEntryActionUnsafeConsumer()
					).add(
						() -> hasManageFragmentEntriesPermission,
						_getCopyFragmentEntryActionUnsafeConsumer()
					).add(
						() ->
							hasManageFragmentEntriesPermission &&
							!_fragmentEntry.isReadOnly(),
						_getMoveFragmentEntryActionUnsafeConsumer()
					).build());
				dropdownGroupItem.setSeparator(true);
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						() ->
							hasManageFragmentEntriesPermission &&
							!_fragmentEntry.isReadOnly(),
						_getDeleteFragmentEntryActionUnsafeConsumer()
					).build());
				dropdownGroupItem.setSeparator(true);
			}
		).build();
	}

	private UnsafeConsumer<DropdownItem, Exception>
			_getCopyFragmentEntryActionUnsafeConsumer()
		throws Exception {

		return dropdownItem -> {
			dropdownItem.putData("action", "copyFragmentEntry");
			dropdownItem.putData(
				"copyFragmentEntryURL",
				PortletURLBuilder.createActionURL(
					_renderResponse
				).setActionName(
					"/fragment/copy_fragment_entry"
				).setRedirect(
					_themeDisplay.getURLCurrent()
				).buildString());
			dropdownItem.putData(
				"fragmentCollectionId",
				String.valueOf(_fragmentEntry.getFragmentCollectionId()));
			dropdownItem.putData(
				"fragmentEntryId",
				String.valueOf(_fragmentEntry.getFragmentEntryId()));
			dropdownItem.putData(
				"selectFragmentCollectionURL",
				PortletURLBuilder.createRenderURL(
					_renderResponse
				).setMVCRenderCommandName(
					"/fragment/select_fragment_collection"
				).setWindowState(
					LiferayWindowState.POP_UP
				).buildString());
			dropdownItem.setIcon("copy");
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "make-a-copy"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getDeleteDraftFragmentEntryActionUnsafeConsumer() {

		return dropdownItem -> {
			dropdownItem.putData("action", "deleteDraftFragmentEntry");
			dropdownItem.putData(
				"deleteDraftFragmentEntryURL",
				PortletURLBuilder.createActionURL(
					_renderResponse
				).setActionName(
					"/fragment/delete_draft_fragment_entries"
				).setRedirect(
					_themeDisplay.getURLCurrent()
				).setParameter(
					"fragmentEntryId", _fragmentEntry.getFragmentEntryId()
				).buildString());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "discard-draft"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getDeleteFragmentEntryActionUnsafeConsumer() {

		return dropdownItem -> {
			dropdownItem.putData("action", "deleteFragmentEntry");
			dropdownItem.putData(
				"deleteFragmentEntryURL",
				PortletURLBuilder.createActionURL(
					_renderResponse
				).setActionName(
					"/fragment/delete_fragment_entries"
				).setRedirect(
					_themeDisplay.getURLCurrent()
				).setParameter(
					"fragmentEntryId", _fragmentEntry.getFragmentEntryId()
				).buildString());
			dropdownItem.setIcon("trash");
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "delete"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getDeleteFragmentEntryPreviewActionUnsafeConsumer() {

		return dropdownItem -> {
			dropdownItem.putData("action", "deleteFragmentEntryPreview");
			dropdownItem.putData(
				"deleteFragmentEntryPreviewURL",
				PortletURLBuilder.createActionURL(
					_renderResponse
				).setActionName(
					"/fragment/delete_fragment_entry_preview"
				).setParameter(
					"fragmentEntryId", _fragmentEntry.getFragmentEntryId()
				).buildString());
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
			dropdownItem.setIcon("pencil");
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
			dropdownItem.setIcon("upload");
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "export"));
		};
	}

	private String _getItemSelectorURL() {
		ItemSelectorCriterion itemSelectorCriterion =
			new UploadItemSelectorCriterion(
				FragmentPortletKeys.FRAGMENT,
				PortletURLBuilder.createActionURL(
					_renderResponse
				).setActionName(
					"/fragment/upload_fragment_entry_preview"
				).buildString(),
				LanguageUtil.get(_themeDisplay.getLocale(), "fragments"),
				UploadServletRequestConfigurationHelperUtil.getMaxSize(),
				_fragmentPortletConfiguration.thumbnailExtensions());

		itemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new FileEntryItemSelectorReturnType());

		return PortletURLBuilder.create(
			_itemSelector.getItemSelectorURL(
				RequestBackedPortletURLFactoryUtil.create(_httpServletRequest),
				_renderResponse.getNamespace() + "changePreview",
				itemSelectorCriterion)
		).setParameter(
			"fragmentEntryId", _fragmentEntry.getFragmentEntryId()
		).buildString();
	}

	private UnsafeConsumer<DropdownItem, Exception>
			_getMoveFragmentEntryActionUnsafeConsumer()
		throws Exception {

		return dropdownItem -> {
			dropdownItem.putData("action", "moveFragmentEntry");
			dropdownItem.putData(
				"fragmentEntryId",
				String.valueOf(_fragmentEntry.getFragmentEntryId()));
			dropdownItem.putData(
				"moveFragmentEntryURL",
				PortletURLBuilder.createActionURL(
					_renderResponse
				).setActionName(
					"/fragment/move_fragment_compositions_and_fragment_entries"
				).setRedirect(
					_themeDisplay.getURLCurrent()
				).buildString());
			dropdownItem.putData(
				"selectFragmentCollectionURL",
				PortletURLBuilder.createRenderURL(
					_renderResponse
				).setMVCRenderCommandName(
					"/fragment/select_fragment_collection"
				).setWindowState(
					LiferayWindowState.POP_UP
				).buildString());
			dropdownItem.setIcon("move-folder");
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "move"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getRenameFragmentEntryActionUnsafeConsumer() {

		return dropdownItem -> {
			dropdownItem.putData("action", "renameFragmentEntry");
			dropdownItem.putData(
				"fragmentEntryId",
				String.valueOf(_fragmentEntry.getFragmentEntryId()));
			dropdownItem.putData("fragmentEntryName", _fragmentEntry.getName());
			dropdownItem.putData(
				"updateFragmentEntryURL",
				PortletURLBuilder.createActionURL(
					_renderResponse
				).setActionName(
					"/fragment/update_fragment_entry"
				).setParameter(
					"fragmentCollectionId",
					_fragmentEntry.getFragmentCollectionId()
				).setParameter(
					"fragmentEntryId", _fragmentEntry.getFragmentEntryId()
				).buildString());
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
			dropdownItem.setIcon("change");
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
			dropdownItem.setIcon("list-ul");
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