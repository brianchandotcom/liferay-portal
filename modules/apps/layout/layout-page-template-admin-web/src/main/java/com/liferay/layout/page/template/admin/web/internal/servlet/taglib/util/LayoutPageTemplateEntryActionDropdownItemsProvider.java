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
import com.liferay.layout.page.template.admin.web.internal.configuration.LayoutPageTemplateAdminWebConfiguration;
import com.liferay.layout.page.template.admin.web.internal.constants.LayoutPageTemplateAdminWebKeys;
import com.liferay.layout.page.template.admin.web.internal.security.permission.resource.LayoutPageTemplateEntryPermission;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutPrototypeLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutPrototypeServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadServletRequestConfigurationHelperUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.PortalUtil;
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
public class LayoutPageTemplateEntryActionDropdownItemsProvider {

	public LayoutPageTemplateEntryActionDropdownItemsProvider(
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
			() -> hasUpdatePermission,
			_getEditLayoutPageTemplateEntryActionUnsafeConsumer()
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
			_getRenameLayoutPageTemplateEntryActionUnsafeConsumer()
		).add(
			() ->
				hasUpdatePermission &&
				(_layoutPageTemplateEntry.getLayoutPrototypeId() > 0),
			_getConfigureLayoutPrototypeActionUnsafeConsumer()
		).add(
			() ->
				hasUpdatePermission &&
				(_layoutPageTemplateEntry.getLayoutPrototypeId() <= 0),
			_getConfigureLayoutPageTemplateEntryActionUnsafeConsumer()
		).add(
			() -> _layoutPageTemplateEntry.getLayoutPrototypeId() == 0,
			_getExportLayoutPageTemplateEntryActionUnsafeConsumer()
		).add(
			() -> LayoutPageTemplateEntryPermission.contains(
				_themeDisplay.getPermissionChecker(), _layoutPageTemplateEntry,
				ActionKeys.PERMISSIONS),
			_getPermissionsLayoutPageTemplateEntryActionUnsafeConsumer()
		).add(
			() -> hasUpdatePermission && _isShowDiscardDraftAction(),
			_getDiscardDraftActionUnsafeConsumer()
		).add(
			() -> LayoutPageTemplateEntryPermission.contains(
				_themeDisplay.getPermissionChecker(), _layoutPageTemplateEntry,
				ActionKeys.DELETE),
			_getDeleteLayoutPageTemplateEntryActionUnsafeConsumer()
		).build();
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getConfigureLayoutPageTemplateEntryActionUnsafeConsumer() {

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
		_getConfigureLayoutPrototypeActionUnsafeConsumer() {

		PortletURL configureLayoutPrototypeURL =
			_renderResponse.createRenderURL();

		configureLayoutPrototypeURL.setParameter(
			"mvcPath", "/edit_layout_prototype.jsp");
		configureLayoutPrototypeURL.setParameter(
			"redirect", _themeDisplay.getURLCurrent());
		configureLayoutPrototypeURL.setParameter(
			"layoutPrototypeId",
			String.valueOf(_layoutPageTemplateEntry.getLayoutPrototypeId()));

		return dropdownItem -> {
			dropdownItem.setHref(configureLayoutPrototypeURL);
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
			"/layout_page_template/delete_layout_page_template_entry");
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
			_getEditLayoutPageTemplateEntryActionUnsafeConsumer()
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
				String layoutFullURL = layoutPrototypeGroup.getDisplayURL(
					_themeDisplay, true);

				layoutFullURL = HttpUtil.setParameter(
					layoutFullURL, "p_l_back_url",
					_themeDisplay.getURLCurrent());

				dropdownItem.setHref(layoutFullURL);

				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "edit"));
			};
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
		_getExportLayoutPageTemplateEntryActionUnsafeConsumer() {

		ResourceURL exportLayoutPageTemplateURL =
			_renderResponse.createResourceURL();

		exportLayoutPageTemplateURL.setParameter(
			"layoutPageTemplateCollectionId",
			String.valueOf(
				_layoutPageTemplateEntry.getLayoutPageTemplateCollectionId()));
		exportLayoutPageTemplateURL.setParameter(
			"layoutPageTemplateEntryId",
			String.valueOf(
				_layoutPageTemplateEntry.getLayoutPageTemplateEntryId()));
		exportLayoutPageTemplateURL.setResourceID(
			"/layout_page_template/export_layout_page_template_entry");

		return dropdownItem -> {
			dropdownItem.setDisabled(_layoutPageTemplateEntry.isDraft());
			dropdownItem.setHref(exportLayoutPageTemplateURL);
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
		throws Exception {

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
			"/layout_page_template/update_layout_page_template_entry");
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