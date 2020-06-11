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

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.upload.criterion.UploadItemSelectorCriterion;
import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.layout.page.template.admin.constants.LayoutPageTemplateAdminPortletKeys;
import com.liferay.layout.page.template.admin.web.internal.constants.LayoutPageTemplateAdminWebKeys;
import com.liferay.layout.page.template.admin.web.internal.security.permission.resource.LayoutPageTemplateEntryPermission;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryServiceUtil;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadServletRequestConfigurationHelperUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.taglib.security.PermissionsURLTag;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class MasterLayoutActionDropdownItemsProvider {

	public MasterLayoutActionDropdownItemsProvider(
		LayoutPageTemplateEntry layoutPageTemplateEntry,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_layoutPageTemplateEntry = layoutPageTemplateEntry;
		_renderResponse = renderResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);
		_itemSelector = (ItemSelector)renderRequest.getAttribute(
			LayoutPageTemplateAdminWebKeys.ITEM_SELECTOR);
		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_draftLayout = LayoutLocalServiceUtil.fetchDraftLayout(
			layoutPageTemplateEntry.getPlid());
	}

	public List<DropdownItem> getActionDropdownItems() throws Exception {
		LayoutPageTemplateEntry defaultLayoutPageTemplateEntry =
			LayoutPageTemplateEntryServiceUtil.
				fetchDefaultLayoutPageTemplateEntry(
					_themeDisplay.getScopeGroupId(),
					LayoutPageTemplateEntryTypeConstants.TYPE_MASTER_LAYOUT,
					WorkflowConstants.STATUS_APPROVED);
		boolean hasUpdatePermission =
			LayoutPageTemplateEntryPermission.contains(
				_themeDisplay.getPermissionChecker(), _layoutPageTemplateEntry,
				ActionKeys.UPDATE);
		long layoutPageTemplateEntryId =
			_layoutPageTemplateEntry.getLayoutPageTemplateEntryId();

		return DropdownItemListBuilder.add(
			() -> (layoutPageTemplateEntryId > 0) && hasUpdatePermission,
			_getEditMasterLayoutActionUnsafeConsumer()
		).add(
			() -> (layoutPageTemplateEntryId > 0) && hasUpdatePermission,
			_getUpdateMasterLayoutPreviewActionUnsafeConsumer()
		).add(
			() ->
				(layoutPageTemplateEntryId > 0) && hasUpdatePermission &&
				(_layoutPageTemplateEntry.getPreviewFileEntryId() > 0),
			_getDeleteMasterLayoutPreviewActionUnsafeConsumer()
		).add(
			() -> (layoutPageTemplateEntryId > 0) && hasUpdatePermission,
			_getRenameMasterLayoutActionUnsafeConsumer()
		).add(
			() -> (layoutPageTemplateEntryId > 0) && hasUpdatePermission,
			_getCopyMasterLayoutActionUnsafeConsumer()
		).add(
			() ->
				(layoutPageTemplateEntryId > 0) &&
				(_layoutPageTemplateEntry.getLayoutPrototypeId() == 0),
			_getExportMasterLayoutActionUnsafeConsumer()
		).add(
			() ->
				(layoutPageTemplateEntryId > 0) &&
				LayoutPageTemplateEntryPermission.contains(
					_themeDisplay.getPermissionChecker(),
					_layoutPageTemplateEntry, ActionKeys.PERMISSIONS),
			_getPermissionsMasterLayoutActionUnsafeConsumer()
		).add(
			() ->
				(layoutPageTemplateEntryId > 0) &&
				_layoutPageTemplateEntry.isApproved() &&
				!_layoutPageTemplateEntry.isDefaultTemplate() &&
				hasUpdatePermission,
			_getMarkAsDefaultMasterLayoutActionUnsafeConsumer()
		).add(
			() ->
				(layoutPageTemplateEntryId > 0) &&
				LayoutPageTemplateEntryPermission.contains(
					_themeDisplay.getPermissionChecker(),
					_layoutPageTemplateEntry, ActionKeys.DELETE),
			_getDeleteMasterLayoutActionUnsafeConsumer()
		).add(
			() ->
				(layoutPageTemplateEntryId > 0) && hasUpdatePermission &&
				_isShowDiscardDraftAction(),
			_getDiscardDraftActionUnsafeConsumer()
		).add(
			() ->
				(layoutPageTemplateEntryId <= 0) &&
				(defaultLayoutPageTemplateEntry != null),
			_getMarkAsDefaulBlanktMasterLayoutActionUnsafeConsumer(
				defaultLayoutPageTemplateEntry)
		).build();
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getCopyMasterLayoutActionUnsafeConsumer() {

		PortletURL copyMasterLayoutURL = _renderResponse.createActionURL();

		copyMasterLayoutURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/layout_page_template/copy_layout_page_template_entry");
		copyMasterLayoutURL.setParameter(
			"redirect", _themeDisplay.getURLCurrent());
		copyMasterLayoutURL.setParameter(
			"layoutPageTemplateCollectionId",
			String.valueOf(
				_layoutPageTemplateEntry.getLayoutPageTemplateCollectionId()));
		copyMasterLayoutURL.setParameter(
			"layoutPageTemplateEntryId",
			String.valueOf(
				_layoutPageTemplateEntry.getLayoutPageTemplateEntryId()));

		return dropdownItem -> {
			dropdownItem.putData("action", "copyMasterLayout");
			dropdownItem.putData(
				"copyMasterLayoutURL", copyMasterLayoutURL.toString());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "make-a-copy"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getDeleteMasterLayoutActionUnsafeConsumer() {

		PortletURL deleteMasterLayoutURL = _renderResponse.createActionURL();

		deleteMasterLayoutURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/layout_page_template/delete_master_layout");

		deleteMasterLayoutURL.setParameter(
			"redirect", _themeDisplay.getURLCurrent());
		deleteMasterLayoutURL.setParameter(
			"layoutPageTemplateEntryId",
			String.valueOf(
				_layoutPageTemplateEntry.getLayoutPageTemplateEntryId()));

		return dropdownItem -> {
			dropdownItem.putData("action", "deleteMasterLayout");
			dropdownItem.putData(
				"deleteMasterLayoutURL", deleteMasterLayoutURL.toString());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "delete"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getDeleteMasterLayoutPreviewActionUnsafeConsumer() {

		PortletURL deleteMasterLayoutPreviewURL =
			_renderResponse.createActionURL();

		deleteMasterLayoutPreviewURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/layout_page_template/delete_layout_page_template_entry_preview");

		deleteMasterLayoutPreviewURL.setParameter(
			"redirect", _themeDisplay.getURLCurrent());
		deleteMasterLayoutPreviewURL.setParameter(
			"layoutPageTemplateEntryId",
			String.valueOf(
				_layoutPageTemplateEntry.getLayoutPageTemplateEntryId()));

		return dropdownItem -> {
			dropdownItem.putData("action", "deleteMasterLayoutPreview");
			dropdownItem.putData(
				"deleteMasterLayoutPreviewURL",
				deleteMasterLayoutPreviewURL.toString());
			dropdownItem.putData(
				"layoutPageTemplateEntryId",
				String.valueOf(
					_layoutPageTemplateEntry.getLayoutPageTemplateEntryId()));
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "remove-thumbnail"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getDiscardDraftActionUnsafeConsumer() {

		if (_draftLayout == null) {
			return null;
		}

		PortletURL discardDraftURL = PortletURLFactoryUtil.create(
			_httpServletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
			PortletRequest.ACTION_PHASE);

		discardDraftURL.setParameter(
			ActionRequest.ACTION_NAME, "/layout/discard_draft_layout");
		discardDraftURL.setParameter("redirect", _themeDisplay.getURLCurrent());
		discardDraftURL.setParameter(
			"selPlid", String.valueOf(_draftLayout.getPlid()));

		return dropdownItem -> {
			dropdownItem.putData("action", "discardDraft");
			dropdownItem.putData("discardDraftURL", discardDraftURL.toString());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "discard-draft"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getEditMasterLayoutActionUnsafeConsumer() {

		if (_draftLayout == null) {
			return null;
		}

		return dropdownItem -> {
			String layoutFullURL = PortalUtil.getLayoutFullURL(
				_draftLayout, _themeDisplay);

			layoutFullURL = HttpUtil.setParameter(
				layoutFullURL, "p_l_back_url", _themeDisplay.getURLCurrent());
			layoutFullURL = HttpUtil.setParameter(
				layoutFullURL, "p_l_mode", Constants.EDIT);

			dropdownItem.setHref(layoutFullURL);

			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "edit"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getExportMasterLayoutActionUnsafeConsumer() {

		ResourceURL exportMasterLayoutURL = _renderResponse.createResourceURL();

		exportMasterLayoutURL.setParameter(
			"layoutPageTemplateEntryId",
			String.valueOf(
				_layoutPageTemplateEntry.getLayoutPageTemplateEntryId()));
		exportMasterLayoutURL.setResourceID(
			"/layout_page_template/export_master_layout");

		return dropdownItem -> {
			dropdownItem.setDisabled(_layoutPageTemplateEntry.isDraft());
			dropdownItem.setHref(exportMasterLayoutURL);
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "export"));
		};
	}

	private String _getItemSelectorURL() {
		PortletURL uploadURL = _renderResponse.createActionURL();

		uploadURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/layout_page_template/upload_layout_page_template_entry_preview");
		uploadURL.setParameter(
			"layoutPageTemplateEntryId",
			String.valueOf(
				_layoutPageTemplateEntry.getLayoutPageTemplateEntryId()));

		ItemSelectorCriterion itemSelectorCriterion =
			new UploadItemSelectorCriterion(
				LayoutPageTemplateAdminPortletKeys.LAYOUT_PAGE_TEMPLATES,
				uploadURL.toString(),
				LanguageUtil.get(_themeDisplay.getLocale(), "master-page"),
				UploadServletRequestConfigurationHelperUtil.getMaxSize());

		itemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new FileEntryItemSelectorReturnType());

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			RequestBackedPortletURLFactoryUtil.create(_httpServletRequest),
			_renderResponse.getNamespace() + "changePreview",
			itemSelectorCriterion);

		return itemSelectorURL.toString();
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getMarkAsDefaulBlanktMasterLayoutActionUnsafeConsumer(
			LayoutPageTemplateEntry defaultLayoutPageTemplateEntry) {

		if (defaultLayoutPageTemplateEntry == null) {
			return null;
		}

		PortletURL markAsDefaultMasterLayoutURL =
			_renderResponse.createActionURL();

		markAsDefaultMasterLayoutURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/layout_page_template/edit_layout_page_template_settings");
		markAsDefaultMasterLayoutURL.setParameter(
			"redirect", _themeDisplay.getURLCurrent());
		markAsDefaultMasterLayoutURL.setParameter(
			"layoutPageTemplateEntryId",
			String.valueOf(
				defaultLayoutPageTemplateEntry.getLayoutPageTemplateEntryId()));
		markAsDefaultMasterLayoutURL.setParameter(
			"defaultTemplate", Boolean.FALSE.toString());

		return dropdownItem -> {
			dropdownItem.putData("action", "markAsDefaultMasterLayout");
			dropdownItem.putData(
				"markAsDefaultMasterLayoutURL",
				markAsDefaultMasterLayoutURL.toString());
			dropdownItem.putData(
				"message",
				LanguageUtil.format(
					_httpServletRequest,
					"do-you-want-to-replace-x-for-x-as-the-default-master-" +
						"page-for-widget-pages",
					new String[] {
						defaultLayoutPageTemplateEntry.getName(),
						_layoutPageTemplateEntry.getName()
					}));
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "mark-as-default"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getMarkAsDefaultMasterLayoutActionUnsafeConsumer() {

		PortletURL markAsDefaultMasterLayoutURL =
			_renderResponse.createActionURL();

		markAsDefaultMasterLayoutURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/layout_page_template/edit_layout_page_template_settings");

		markAsDefaultMasterLayoutURL.setParameter(
			"redirect", _themeDisplay.getURLCurrent());
		markAsDefaultMasterLayoutURL.setParameter(
			"layoutPageTemplateEntryId",
			String.valueOf(
				_layoutPageTemplateEntry.getLayoutPageTemplateEntryId()));
		markAsDefaultMasterLayoutURL.setParameter(
			"defaultTemplate", Boolean.TRUE.toString());

		return dropdownItem -> {
			dropdownItem.putData("action", "markAsDefaultMasterLayout");
			dropdownItem.putData(
				"markAsDefaultMasterLayoutURL",
				markAsDefaultMasterLayoutURL.toString());

			String name = "Blank";

			LayoutPageTemplateEntry defaultLayoutPageTemplateEntry =
				LayoutPageTemplateEntryServiceUtil.
					fetchDefaultLayoutPageTemplateEntry(
						_layoutPageTemplateEntry.getGroupId(),
						LayoutPageTemplateEntryTypeConstants.TYPE_MASTER_LAYOUT,
						WorkflowConstants.STATUS_APPROVED);

			if (defaultLayoutPageTemplateEntry != null) {
				name = defaultLayoutPageTemplateEntry.getName();
			}

			dropdownItem.putData(
				"message",
				LanguageUtil.format(
					_httpServletRequest,
					"do-you-want-to-replace-x-for-x-as-the-default-master-" +
						"page-for-widget-pages",
					new String[] {name, _layoutPageTemplateEntry.getName()}));

			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "mark-as-default"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
			_getPermissionsMasterLayoutActionUnsafeConsumer()
		throws Exception {

		String permissionsMasterLayoutURL = PermissionsURLTag.doTag(
			StringPool.BLANK, LayoutPageTemplateEntry.class.getName(),
			_layoutPageTemplateEntry.getName(), null,
			String.valueOf(
				_layoutPageTemplateEntry.getLayoutPageTemplateEntryId()),
			LiferayWindowState.POP_UP.toString(), null, _httpServletRequest);

		return dropdownItem -> {
			dropdownItem.putData("action", "permissionsMasterLayout");
			dropdownItem.putData(
				"permissionsMasterLayoutURL", permissionsMasterLayoutURL);
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "permissions"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getRenameMasterLayoutActionUnsafeConsumer() {

		PortletURL updateMasterLayoutURL = _renderResponse.createActionURL();

		updateMasterLayoutURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/layout_page_template/update_layout_page_template_entry");

		updateMasterLayoutURL.setParameter(
			"redirect", _themeDisplay.getURLCurrent());
		updateMasterLayoutURL.setParameter(
			"layoutPageTemplateCollectionId",
			String.valueOf(
				_layoutPageTemplateEntry.getLayoutPageTemplateCollectionId()));
		updateMasterLayoutURL.setParameter(
			"layoutPageTemplateEntryId",
			String.valueOf(
				_layoutPageTemplateEntry.getLayoutPageTemplateEntryId()));

		return dropdownItem -> {
			dropdownItem.putData("action", "renameMasterLayout");
			dropdownItem.putData(
				"layoutPageTemplateEntryId",
				String.valueOf(
					_layoutPageTemplateEntry.getLayoutPageTemplateEntryId()));
			dropdownItem.putData(
				"layoutPageTemplateEntryName",
				_layoutPageTemplateEntry.getName());
			dropdownItem.putData(
				"updateMasterLayoutURL", updateMasterLayoutURL.toString());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "rename"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getUpdateMasterLayoutPreviewActionUnsafeConsumer() {

		return dropdownItem -> {
			dropdownItem.putData("action", "updateMasterLayoutPreview");
			dropdownItem.putData("itemSelectorURL", _getItemSelectorURL());
			dropdownItem.putData(
				"layoutPageTemplateEntryId",
				String.valueOf(
					_layoutPageTemplateEntry.getLayoutPageTemplateEntryId()));
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "change-thumbnail"));
		};
	}

	private boolean _isShowDiscardDraftAction() {
		if (_draftLayout == null) {
			return false;
		}

		if (_draftLayout.getStatus() == WorkflowConstants.STATUS_DRAFT) {
			return true;
		}

		return false;
	}

	private final Layout _draftLayout;
	private final HttpServletRequest _httpServletRequest;
	private final ItemSelector _itemSelector;
	private final LayoutPageTemplateEntry _layoutPageTemplateEntry;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;

}