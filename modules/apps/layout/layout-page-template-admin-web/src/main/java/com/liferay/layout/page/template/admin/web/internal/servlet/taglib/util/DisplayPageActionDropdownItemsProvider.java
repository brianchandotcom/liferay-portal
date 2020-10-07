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

import com.liferay.asset.display.page.service.AssetDisplayPageEntryServiceUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.upload.criterion.UploadItemSelectorCriterion;
import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.layout.page.template.admin.constants.LayoutPageTemplateAdminPortletKeys;
import com.liferay.layout.page.template.admin.web.internal.configuration.FFDisplayPageAdminWebConfigurationUtil;
import com.liferay.layout.page.template.admin.web.internal.configuration.LayoutPageTemplateAdminWebConfiguration;
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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.taglib.security.PermissionsURLTag;

import java.util.List;
import java.util.Objects;

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
public class DisplayPageActionDropdownItemsProvider {

	public DisplayPageActionDropdownItemsProvider(
		LayoutPageTemplateEntry layoutPageTemplateEntry,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_layoutPageTemplateEntry = layoutPageTemplateEntry;
		_renderResponse = renderResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);

		_itemSelector = (ItemSelector)_httpServletRequest.getAttribute(
			LayoutPageTemplateAdminWebKeys.ITEM_SELECTOR);
		_layoutPageTemplateAdminWebConfiguration =
			(LayoutPageTemplateAdminWebConfiguration)
				_httpServletRequest.getAttribute(
					LayoutPageTemplateAdminWebConfiguration.class.getName());
		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_draftLayout = LayoutLocalServiceUtil.fetchDraftLayout(
			layoutPageTemplateEntry.getPlid());
	}

	public List<DropdownItem> getActionDropdownItems() throws Exception {
		boolean hasUpdatePermission =
			LayoutPageTemplateEntryPermission.contains(
				_themeDisplay.getPermissionChecker(), _layoutPageTemplateEntry,
				ActionKeys.UPDATE);

		return DropdownItemListBuilder.add(
			() -> hasUpdatePermission, _getEditDisplayPageActionUnsafeConsumer()
		).add(
			() -> hasUpdatePermission,
			_getUpdateLayoutPageTemplateEntryPreviewActionUnsafeConsumer()
		).add(
			() ->
				hasUpdatePermission &&
				(_layoutPageTemplateEntry.getPreviewFileEntryId() > 0),
			_getDeleteLayoutPageTemplateEntryPreviewActionUnsafeConsumer()
		).add(
			() -> hasUpdatePermission,
			_getConfigureDisplayPageActionUnsafeConsumer()
		).add(
			() -> hasUpdatePermission,
			_getRenameDisplayPageActionUnsafeConsumer()
		).add(
			() ->
				(_layoutPageTemplateEntry.getLayoutPageTemplateEntryId() > 0) &&
				(_layoutPageTemplateEntry.getLayoutPrototypeId() == 0),
			_getExportDisplayPageActionUnsafeConsumer()
		).add(
			() -> LayoutPageTemplateEntryPermission.contains(
				_themeDisplay.getPermissionChecker(), _layoutPageTemplateEntry,
				ActionKeys.PERMISSIONS),
			_getPermissionsDisplayPageActionUnsafeConsumer()
		).add(
			() ->
				_layoutPageTemplateEntry.isApproved() &&
				Objects.equals(
					_layoutPageTemplateEntry.getType(),
					LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE) &&
				(_layoutPageTemplateEntry.getClassNameId() > 0) &&
				hasUpdatePermission,
			_getMarkAsDefaultDisplayPageActionUnsafeConsumer()
		).add(
			() -> hasUpdatePermission && _isShowDiscardDraftAction(),
			_getDiscardDraftActionUnsafeConsumer()
		).add(
			() -> {
				int count =
					AssetDisplayPageEntryServiceUtil.
						getAssetDisplayPageEntriesCount(
							_layoutPageTemplateEntry.getClassNameId(),
							_layoutPageTemplateEntry.getClassTypeId(),
							_layoutPageTemplateEntry.
								getLayoutPageTemplateEntryId(),
							_layoutPageTemplateEntry.isDefaultTemplate());

				return FFDisplayPageAdminWebConfigurationUtil.
					viewUsagesEnabled() &&
					   (count > 0);
			},
			_getViewUsagesDisplayPageActionUnsafeConsumer()
		).add(
			() -> LayoutPageTemplateEntryPermission.contains(
				_themeDisplay.getPermissionChecker(), _layoutPageTemplateEntry,
				ActionKeys.DELETE),
			_getDeleteLayoutPrototypeActionUnsafeConsumer()
		).build();
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getConfigureDisplayPageActionUnsafeConsumer() {

		PortletURL editPageURL = PortalUtil.getControlPanelPortletURL(
			_httpServletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
			PortletRequest.RENDER_PHASE);

		return dropdownItem -> {
			dropdownItem.setHref(
				editPageURL, "mvcRenderCommandName", "/layout/edit_layout",
				"redirect", _themeDisplay.getURLCurrent(), "backURL",
				_themeDisplay.getURLCurrent(), "portletResource",
				LayoutPageTemplateAdminPortletKeys.LAYOUT_PAGE_TEMPLATES,
				"selPlid", _layoutPageTemplateEntry.getPlid());

			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "configure"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getDeleteLayoutPageTemplateEntryPreviewActionUnsafeConsumer() {

		PortletURL deleteLayoutPageTemplateEntryPreviewURL =
			_renderResponse.createActionURL();

		deleteLayoutPageTemplateEntryPreviewURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/layout_page_template/delete_layout_page_template_entry_preview");

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

	private UnsafeConsumer<DropdownItem, Exception>
		_getDeleteLayoutPrototypeActionUnsafeConsumer() {

		PortletURL deleteDisplayPageURL = _renderResponse.createActionURL();

		deleteDisplayPageURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/layout_page_template/delete_layout_page_template_entry");

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
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "delete"));
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
		_getEditDisplayPageActionUnsafeConsumer() {

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
		_getExportDisplayPageActionUnsafeConsumer() {

		ResourceURL exportDisplayPageURL = _renderResponse.createResourceURL();

		exportDisplayPageURL.setParameter(
			"layoutPageTemplateEntryId",
			String.valueOf(
				_layoutPageTemplateEntry.getLayoutPageTemplateEntryId()));
		exportDisplayPageURL.setResourceID(
			"/layout_page_template/export_display_page");

		return dropdownItem -> {
			dropdownItem.setDisabled(_layoutPageTemplateEntry.isDraft());
			dropdownItem.setHref(exportDisplayPageURL);
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
				LanguageUtil.get(_themeDisplay.getLocale(), "page-template"),
				UploadServletRequestConfigurationHelperUtil.getMaxSize(),
				_layoutPageTemplateAdminWebConfiguration.thumbnailExtensions());

		itemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new FileEntryItemSelectorReturnType());

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			RequestBackedPortletURLFactoryUtil.create(_httpServletRequest),
			_renderResponse.getNamespace() + "changePreview",
			itemSelectorCriterion);

		return itemSelectorURL.toString();
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getMarkAsDefaultDisplayPageActionUnsafeConsumer() {

		PortletURL markAsDefaultDisplayPageURL =
			_renderResponse.createActionURL();

		markAsDefaultDisplayPageURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/layout_page_template/edit_layout_page_template_settings");

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

			if (defaultLayoutPageTemplateEntry != null) {
				long defaultLayoutPageTemplateEntryId =
					defaultLayoutPageTemplateEntry.
						getLayoutPageTemplateEntryId();
				long layoutPageTemplateEntryId =
					_layoutPageTemplateEntry.getLayoutPageTemplateEntryId();

				if (defaultLayoutPageTemplateEntryId !=
						layoutPageTemplateEntryId) {

					message = LanguageUtil.format(
						_httpServletRequest,
						"do-you-want-to-replace-x-for-x-as-the-default-" +
							"display-page-template",
						new String[] {
							_layoutPageTemplateEntry.getName(),
							defaultLayoutPageTemplateEntry.getName()
						});
				}
			}

			if (Validator.isNull(message) &&
				_layoutPageTemplateEntry.isDefaultTemplate()) {

				message = LanguageUtil.get(
					_httpServletRequest, "unmark-default-confirmation");
			}

			dropdownItem.putData("message", message);

			String label = "mark-as-default";

			if (_layoutPageTemplateEntry.isDefaultTemplate()) {
				label = "unmark-as-default";
			}

			dropdownItem.setLabel(LanguageUtil.get(_httpServletRequest, label));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
			_getPermissionsDisplayPageActionUnsafeConsumer()
		throws Exception {

		String permissionsDisplayPageURL = PermissionsURLTag.doTag(
			StringPool.BLANK, LayoutPageTemplateEntry.class.getName(),
			_layoutPageTemplateEntry.getName(), null,
			String.valueOf(
				_layoutPageTemplateEntry.getLayoutPageTemplateEntryId()),
			LiferayWindowState.POP_UP.toString(), null, _httpServletRequest);

		return dropdownItem -> {
			dropdownItem.putData("action", "permissionsDisplayPage");
			dropdownItem.putData(
				"permissionsDisplayPageURL", permissionsDisplayPageURL);
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "permissions"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getRenameDisplayPageActionUnsafeConsumer() {

		PortletURL updateDisplayPageURL = _renderResponse.createActionURL();

		updateDisplayPageURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/layout_page_template/update_layout_page_template_entry");

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
				"layoutPageTemplateEntryId",
				String.valueOf(
					_layoutPageTemplateEntry.getLayoutPageTemplateEntryId()));
			dropdownItem.putData(
				"layoutPageTemplateEntryName",
				_layoutPageTemplateEntry.getName());
			dropdownItem.putData(
				"updateDisplayPageURL", updateDisplayPageURL.toString());
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

	private UnsafeConsumer<DropdownItem, Exception>
		_getViewUsagesDisplayPageActionUnsafeConsumer() {

		return dropdownItem -> {
			dropdownItem.setHref(
				_renderResponse.createRenderURL(), "mvcRenderCommandName",
				"/layout_page_template/view_display_page_usages", "classNameId",
				String.valueOf(_layoutPageTemplateEntry.getClassNameId()),
				"classTypeId",
				String.valueOf(_layoutPageTemplateEntry.getClassTypeId()),
				"defaultTemplate",
				String.valueOf(_layoutPageTemplateEntry.isDefaultTemplate()),
				"layoutPageTemplateEntryId",
				String.valueOf(
					_layoutPageTemplateEntry.getLayoutPageTemplateEntryId()),
				"redirect", _themeDisplay.getURLCurrent());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "view-usages"));
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
	private final LayoutPageTemplateAdminWebConfiguration
		_layoutPageTemplateAdminWebConfiguration;
	private final LayoutPageTemplateEntry _layoutPageTemplateEntry;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;

}