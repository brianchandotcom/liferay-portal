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
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.upload.criterion.UploadItemSelectorCriterion;
import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.layout.admin.web.internal.configuration.LayoutUtilityPageThumbnailConfiguration;
import com.liferay.layout.utility.page.model.LayoutUtilityPageEntry;
import com.liferay.layout.utility.page.service.LayoutUtilityPageEntryLocalServiceUtil;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadServletRequestConfigurationHelperUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.taglib.security.PermissionsURLTag;

import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceURL;

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

		_draftLayout = LayoutLocalServiceUtil.fetchDraftLayout(
			layoutUtilityPageEntry.getPlid());
		_httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);
		_itemSelector = (ItemSelector)renderRequest.getAttribute(
			ItemSelector.class.getName());
		_layout = LayoutLocalServiceUtil.fetchLayout(
			layoutUtilityPageEntry.getPlid());
		_layoutUtilityPageThumbnailConfiguration =
			(LayoutUtilityPageThumbnailConfiguration)renderRequest.getAttribute(
				LayoutUtilityPageThumbnailConfiguration.class.getName());
		_themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getActionDropdownItems() {
		return DropdownItemListBuilder.addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						_getEditLayoutUtilityPageEntryActionUnsafeConsumer()
					).add(
						_getViewLayoutUtilityPageEntryActionUnsafeConsumer()
					).build());
				dropdownGroupItem.setSeparator(true);
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						_getMarkAsDefaultLayoutUtilityPageEntryActionUnsafeConsumer()
					).add(
						_getRenameLayoutUtilityPageEntryActionUnsafeConsumer()
					).add(
						_getUpdateLayoutUtilityPageEntryPreviewActionUnsafeConsumer()
					).build());
				dropdownGroupItem.setSeparator(true);
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						_getExportLayoutUtilityPageEntryActionUnsafeConsumer()
					).build());
				dropdownGroupItem.setSeparator(true);
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						_getCopyLayoutUtilityPageEntryActionUnsafeConsumer()
					).build());
				dropdownGroupItem.setSeparator(true);
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						_getPermissionsLayoutUtilityPageEntryActionUnsafeConsumer()
					).build());
				dropdownGroupItem.setSeparator(true);
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						_getDeleteLayoutUtilityPageEntryActionUnsafeConsumer()
					).build());
				dropdownGroupItem.setSeparator(true);
			}
		).build();
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getCopyLayoutUtilityPageEntryActionUnsafeConsumer() {

		return dropdownItem -> {
			dropdownItem.putData("action", "copyLayoutUtilityPageEntry");
			dropdownItem.putData(
				"copyLayoutUtilityPageEntryURL",
				PortletURLBuilder.createActionURL(
					_renderResponse
				).setActionName(
					"/layout_admin/copy_layout_utility_page_entry"
				).setRedirect(
					_themeDisplay.getURLCurrent()
				).setParameter(
					"layoutUtilityPageEntryId",
					_layoutUtilityPageEntry.getLayoutUtilityPageEntryId()
				).buildString());
			dropdownItem.setIcon("copy");
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "make-a-copy"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getDeleteLayoutUtilityPageEntryActionUnsafeConsumer() {

		return dropdownItem -> {
			dropdownItem.putData("action", "deleteLayoutUtilityPageEntry");

			String key = "are-you-sure-you-want-to-delete-this";

			if (_layoutUtilityPageEntry.isDefaultLayoutUtilityPageEntry()) {
				key =
					"are-you-sure-you-want-to-delete-the-default-utility-page";
			}

			dropdownItem.putData(
				"deleteLayoutUtilityPageEntryMessage",
				LanguageUtil.get(_httpServletRequest, key));
			dropdownItem.putData(
				"deleteLayoutUtilityPageEntryURL",
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
		_getEditLayoutUtilityPageEntryActionUnsafeConsumer() {

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
		_getExportLayoutUtilityPageEntryActionUnsafeConsumer() {

		ResourceURL exportLayoutUtilityPageEntryURL =
			_renderResponse.createResourceURL();

		exportLayoutUtilityPageEntryURL.setParameter(
			"layoutUtilityPageEntryId",
			String.valueOf(
				_layoutUtilityPageEntry.getLayoutUtilityPageEntryId()));
		exportLayoutUtilityPageEntryURL.setResourceID(
			"/layout_admin/export_layout_utility_page_entries");

		return dropdownItem -> {
			dropdownItem.setDisabled(
				_draftLayout.getStatus() == WorkflowConstants.STATUS_DRAFT);
			dropdownItem.setHref(exportLayoutUtilityPageEntryURL);
			dropdownItem.setIcon("upload");
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "export"));
		};
	}

	private String _getItemSelectorURL() {
		ItemSelectorCriterion itemSelectorCriterion =
			UploadItemSelectorCriterion.builder(
			).desiredItemSelectorReturnTypes(
				new FileEntryItemSelectorReturnType()
			).extensions(
				_layoutUtilityPageThumbnailConfiguration.thumbnailExtensions()
			).maxFileSize(
				UploadServletRequestConfigurationHelperUtil.getMaxSize()
			).portletId(
				LayoutAdminPortletKeys.GROUP_PAGES
			).repositoryName(
				LanguageUtil.get(_themeDisplay.getLocale(), "utility-pages")
			).url(
				PortletURLBuilder.createActionURL(
					_renderResponse
				).setActionName(
					"/layout_admin/upload_layout_utility_page_entry_preview"
				).setParameter(
					"layoutUtilityPageEntryId",
					_layoutUtilityPageEntry.getLayoutUtilityPageEntryId()
				).buildString()
			).build();

		return String.valueOf(
			_itemSelector.getItemSelectorURL(
				RequestBackedPortletURLFactoryUtil.create(_httpServletRequest),
				_renderResponse.getNamespace() + "changePreview",
				itemSelectorCriterion));
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
					"/layout_admin/update_default_layout_utility_page_entry"
				).setRedirect(
					_themeDisplay.getURLCurrent()
				).setParameter(
					"isDefaultLayoutUtilityPageEntry",
					!_layoutUtilityPageEntry.isDefaultLayoutUtilityPageEntry()
				).setParameter(
					"layoutUtilityPageEntryId",
					_layoutUtilityPageEntry.getLayoutUtilityPageEntryId()
				).buildString());
			dropdownItem.setDisabled(!_layout.isPublished());

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
							"utility-page",
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
			_getPermissionsLayoutUtilityPageEntryActionUnsafeConsumer()
		throws Exception {

		String permissionsLayoutUtilityPageEntryURL = PermissionsURLTag.doTag(
			StringPool.BLANK, LayoutUtilityPageEntry.class.getName(),
			_layoutUtilityPageEntry.getName(), null,
			String.valueOf(
				_layoutUtilityPageEntry.getLayoutUtilityPageEntryId()),
			LiferayWindowState.POP_UP.toString(), null, _httpServletRequest);

		return dropdownItem -> {
			dropdownItem.putData("action", "permissionsLayoutUtilityPageEntry");
			dropdownItem.putData(
				"permissionsLayoutUtilityPageEntryURL",
				permissionsLayoutUtilityPageEntryURL);
			dropdownItem.setIcon("password-policies");
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "permissions"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getRenameLayoutUtilityPageEntryActionUnsafeConsumer() {

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
					"layoutUtilityPageEntryId",
					_layoutUtilityPageEntry.getLayoutUtilityPageEntryId()
				).buildString());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "rename"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getUpdateLayoutUtilityPageEntryPreviewActionUnsafeConsumer() {

		return dropdownItem -> {
			dropdownItem.putData(
				"action", "updateLayoutUtilityPageEntryPreview");
			dropdownItem.putData("itemSelectorURL", _getItemSelectorURL());
			dropdownItem.putData(
				"layoutUtilityPageEntryId",
				String.valueOf(
					_layoutUtilityPageEntry.getLayoutUtilityPageEntryId()));
			dropdownItem.setIcon("change");
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "change-thumbnail"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getViewLayoutUtilityPageEntryActionUnsafeConsumer() {

		return dropdownItem -> {
			dropdownItem.setHref(
				HttpComponentsUtil.setParameter(
					PortalUtil.getLayoutFullURL(_draftLayout, _themeDisplay),
					"p_l_back_url", _themeDisplay.getURLCurrent()));
			dropdownItem.setIcon("shortcut");
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "preview"));
		};
	}

	private final Layout _draftLayout;
	private final HttpServletRequest _httpServletRequest;
	private final ItemSelector _itemSelector;
	private final Layout _layout;
	private final LayoutUtilityPageEntry _layoutUtilityPageEntry;
	private final LayoutUtilityPageThumbnailConfiguration
		_layoutUtilityPageThumbnailConfiguration;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;

}