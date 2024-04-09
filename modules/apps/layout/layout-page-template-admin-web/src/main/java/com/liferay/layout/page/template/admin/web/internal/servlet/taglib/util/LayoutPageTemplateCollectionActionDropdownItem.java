/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.admin.web.internal.servlet.taglib.util;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.layout.page.template.admin.web.internal.constants.LayoutPageTemplateAdminWebKeys;
import com.liferay.layout.page.template.admin.web.internal.security.permission.resource.LayoutPageTemplateCollectionPermission;
import com.liferay.layout.page.template.constants.LayoutPageTemplateCollectionTypeConstants;
import com.liferay.layout.page.template.item.selector.criterion.LayoutPageTemplateCollectionTreeNodeItemSelectorCriterion;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.portlet.url.builder.ResourceURLBuilder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.security.PermissionsURLTag;

import java.util.List;

import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Víctor Galán
 */
public class LayoutPageTemplateCollectionActionDropdownItem {

	public LayoutPageTemplateCollectionActionDropdownItem(
		HttpServletRequest httpServletRequest, RenderResponse renderResponse) {

		_httpServletRequest = httpServletRequest;
		_renderResponse = renderResponse;

		_itemSelector = (ItemSelector)httpServletRequest.getAttribute(
			LayoutPageTemplateAdminWebKeys.ITEM_SELECTOR);
		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getActionDropdownItems(
		LayoutPageTemplateCollection layoutPageTemplateCollection,
		String tabs1) {

		return DropdownItemListBuilder.addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						() ->
							(layoutPageTemplateCollection.getType() ==
								LayoutPageTemplateCollectionTypeConstants.
									BASIC) &&
							LayoutPageTemplateCollectionPermission.contains(
								_themeDisplay.getPermissionChecker(),
								layoutPageTemplateCollection,
								ActionKeys.UPDATE),
						dropdownItem -> {
							dropdownItem.setHref(
								_getEditLayoutPageTemplateCollectionURL(
									layoutPageTemplateCollection, tabs1));
							dropdownItem.setIcon("pencil");
							dropdownItem.setLabel(
								LanguageUtil.get(_httpServletRequest, "edit"));
						}
					).build());
				dropdownGroupItem.setSeparator(true);
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						() ->
							(layoutPageTemplateCollection.getType() ==
								LayoutPageTemplateCollectionTypeConstants.
									DISPLAY_PAGE) &&
							LayoutPageTemplateCollectionPermission.contains(
								_themeDisplay.getPermissionChecker(),
								layoutPageTemplateCollection,
								ActionKeys.UPDATE),
						dropdownItem -> {
							dropdownItem.putData(
								"action", "updateLayoutPageTemplateCollection");
							dropdownItem.putData(
								"dialogTitle",
								_getRenameDialogTitle(
									layoutPageTemplateCollection));
							dropdownItem.putData(
								"layoutPageTemplateCollectionDescription",
								layoutPageTemplateCollection.getDescription());
							dropdownItem.putData(
								"layoutPageTemplateCollectionName",
								layoutPageTemplateCollection.getName());
							dropdownItem.putData(
								"updateLayoutPageTemplateCollectionURL",
								_getUpdateLayoutPageTemplateCollectionURL(
									layoutPageTemplateCollection, tabs1));
							dropdownItem.setIcon("pencil");
							dropdownItem.setLabel(
								LanguageUtil.get(_httpServletRequest, "edit"));
						}
					).add(
						() ->
							(layoutPageTemplateCollection.getType() ==
								LayoutPageTemplateCollectionTypeConstants.
									DISPLAY_PAGE) &&
							LayoutPageTemplateCollectionPermission.contains(
								_themeDisplay.getPermissionChecker(),
								layoutPageTemplateCollection,
								ActionKeys.UPDATE),
						dropdownItem -> {
							dropdownItem.setHref(
								_getCopyLayoutPageTemplateCollectionURL(
									layoutPageTemplateCollection));
							dropdownItem.setIcon("copy");
							dropdownItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest, "make-a-copy"));
						}
					).add(
						() ->
							(layoutPageTemplateCollection.getType() ==
								LayoutPageTemplateCollectionTypeConstants.
									DISPLAY_PAGE) &&
							LayoutPageTemplateCollectionPermission.contains(
								_themeDisplay.getPermissionChecker(),
								layoutPageTemplateCollection, ActionKeys.VIEW),
						dropdownItem -> {
							dropdownItem.setHref(
								_getExportLayoutPageTemplateCollectionURL(
									layoutPageTemplateCollection));
							dropdownItem.setIcon("export");
							dropdownItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest, "export"));
						}
					).add(
						() ->
							(layoutPageTemplateCollection.getType() ==
								LayoutPageTemplateCollectionTypeConstants.
									DISPLAY_PAGE) &&
							LayoutPageTemplateCollectionPermission.contains(
								_themeDisplay.getPermissionChecker(),
								layoutPageTemplateCollection,
								ActionKeys.UPDATE),
						dropdownItem -> {
							dropdownItem.putData(
								"action", "moveLayoutPageTemplateCollection");
							dropdownItem.putData(
								"itemSelectorURL",
								_getItemSelectorURL(
									layoutPageTemplateCollection));
							dropdownItem.putData(
								"layoutPageTemplateCollectionId",
								String.valueOf(
									layoutPageTemplateCollection.
										getLayoutPageTemplateCollectionId()));
							dropdownItem.putData(
								"layoutPageTemplateCollectionName",
								layoutPageTemplateCollection.getName());
							dropdownItem.setIcon("move-folder");
							dropdownItem.setLabel(
								LanguageUtil.get(_httpServletRequest, "move"));
						}
					).build());
				dropdownGroupItem.setSeparator(true);
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						() -> LayoutPageTemplateCollectionPermission.contains(
							_themeDisplay.getPermissionChecker(),
							layoutPageTemplateCollection,
							ActionKeys.PERMISSIONS),
						dropdownItem -> {
							dropdownItem.putData(
								"action",
								"permissionsLayoutPageTemplateCollection");
							dropdownItem.putData(
								"permissionsLayoutPageTemplateCollectionURL",
								_getPermissionsLayoutPageTemplateCollectionURL(
									layoutPageTemplateCollection));
							dropdownItem.setIcon("password-policies");
							dropdownItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest, "permissions"));
						}
					).build());
				dropdownGroupItem.setSeparator(true);
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						() -> LayoutPageTemplateCollectionPermission.contains(
							_themeDisplay.getPermissionChecker(),
							layoutPageTemplateCollection, ActionKeys.DELETE),
						dropdownItem -> {
							dropdownItem.putData(
								"action", "deleteLayoutPageTemplateCollection");
							dropdownItem.putData(
								"deleteLayoutPageTemplateCollectionURL",
								_getDeleteLayoutPageTemplateCollectionURL(
									layoutPageTemplateCollection, tabs1));
							dropdownItem.putData(
								"dialogTitle",
								_getDeleteDialogTitle(
									layoutPageTemplateCollection));
							dropdownItem.setIcon("trash");
							dropdownItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest, "delete"));
						}
					).build());
				dropdownGroupItem.setSeparator(true);
			}
		).build();
	}

	private String _getCopyLayoutPageTemplateCollectionURL(
		LayoutPageTemplateCollection layoutPageTemplateCollection) {

		return PortletURLBuilder.createActionURL(
			_renderResponse
		).setActionName(
			"/layout_page_template_admin/copy_layout_page_template_entries_" +
				"and_layout_page_template_collections"
		).setRedirect(
			_themeDisplay.getURLCurrent()
		).setParameter(
			"copyPermissions", true
		).setParameter(
			"layoutPageTemplateCollectionsIds",
			layoutPageTemplateCollection.getLayoutPageTemplateCollectionId()
		).setParameter(
			"layoutParentPageTemplateCollectionId",
			layoutPageTemplateCollection.
				getParentLayoutPageTemplateCollectionId()
		).buildString();
	}

	private String _getDeleteDialogTitle(
		LayoutPageTemplateCollection layoutPageTemplateCollection) {

		if (layoutPageTemplateCollection.getType() ==
				LayoutPageTemplateCollectionTypeConstants.BASIC) {

			return LanguageUtil.get(_httpServletRequest, "page-template-set");
		}

		return LanguageUtil.get(_httpServletRequest, "folder");
	}

	private String _getDeleteLayoutPageTemplateCollectionURL(
		LayoutPageTemplateCollection layoutPageTemplateCollection,
		String tabs1) {

		return PortletURLBuilder.createActionURL(
			_renderResponse
		).setActionName(
			"/layout_page_template_admin/delete_layout_page_template_collection"
		).setRedirect(
			PortletURLBuilder.createRenderURL(
				_renderResponse
			).setTabs1(
				tabs1
			).setParameter(
				"layoutPageTemplateCollectionId",
				layoutPageTemplateCollection.
					getParentLayoutPageTemplateCollectionId()
			).buildString()
		).setParameter(
			"layoutPageTemplateCollectionId",
			layoutPageTemplateCollection.getLayoutPageTemplateCollectionId()
		).buildString();
	}

	private String _getEditLayoutPageTemplateCollectionURL(
		LayoutPageTemplateCollection layoutPageTemplateCollection,
		String tabs1) {

		return PortletURLBuilder.createRenderURL(
			_renderResponse
		).setMVCRenderCommandName(
			"/layout_page_template_admin/edit_layout_page_template_collection"
		).setRedirect(
			_themeDisplay.getURLCurrent()
		).setTabs1(
			tabs1
		).setParameter(
			"layoutPageTemplateCollectionId",
			layoutPageTemplateCollection.getLayoutPageTemplateCollectionId()
		).buildString();
	}

	private String _getExportLayoutPageTemplateCollectionURL(
		LayoutPageTemplateCollection layoutPageTemplateCollection) {

		return ResourceURLBuilder.createResourceURL(
			_renderResponse
		).setParameter(
			"layoutPageTemplateCollectionsIds",
			layoutPageTemplateCollection.getLayoutPageTemplateCollectionId()
		).setResourceID(
			"/layout_page_template_admin/export_layout_page_template_" +
				"entries_and_layout_page_template_collections"
		).buildString();
	}

	private String _getItemSelectorURL(
		LayoutPageTemplateCollection layoutPageTemplateCollection) {

		LayoutPageTemplateCollectionTreeNodeItemSelectorCriterion
			layoutPageTemplateCollectionTreeNodeItemSelectorCriterion =
				new LayoutPageTemplateCollectionTreeNodeItemSelectorCriterion();

		layoutPageTemplateCollectionTreeNodeItemSelectorCriterion.
			setDesiredItemSelectorReturnTypes(new UUIDItemSelectorReturnType());
		layoutPageTemplateCollectionTreeNodeItemSelectorCriterion.
			setLayoutPageTemplateCollectionIds(
				new long[] {
					layoutPageTemplateCollection.
						getLayoutPageTemplateCollectionId()
				});

		return PortletURLBuilder.create(
			_itemSelector.getItemSelectorURL(
				RequestBackedPortletURLFactoryUtil.create(_httpServletRequest),
				"selectFolder",
				layoutPageTemplateCollectionTreeNodeItemSelectorCriterion)
		).buildString();
	}

	private String _getPermissionsLayoutPageTemplateCollectionURL(
			LayoutPageTemplateCollection layoutPageTemplateCollection)
		throws Exception {

		return PermissionsURLTag.doTag(
			StringPool.BLANK, LayoutPageTemplateCollection.class.getName(),
			layoutPageTemplateCollection.getName(), null,
			String.valueOf(
				layoutPageTemplateCollection.
					getLayoutPageTemplateCollectionId()),
			LiferayWindowState.POP_UP.toString(), null, _httpServletRequest);
	}

	private String _getRenameDialogTitle(
		LayoutPageTemplateCollection layoutPageTemplateCollection) {

		if (layoutPageTemplateCollection.getType() ==
				LayoutPageTemplateCollectionTypeConstants.BASIC) {

			return LanguageUtil.get(
				_httpServletRequest, "rename-page-template-set");
		}

		return LanguageUtil.get(_httpServletRequest, "edit-folder");
	}

	private String _getUpdateLayoutPageTemplateCollectionURL(
		LayoutPageTemplateCollection layoutPageTemplateCollection,
		String tabs1) {

		return PortletURLBuilder.createActionURL(
			_renderResponse
		).setActionName(
			"/layout_page_template_admin/update_layout_page_template_collection"
		).setRedirect(
			PortletURLBuilder.createRenderURL(
				_renderResponse
			).setTabs1(
				tabs1
			).buildString()
		).setParameter(
			"layoutPageTemplateCollectionId",
			layoutPageTemplateCollection.getLayoutPageTemplateCollectionId()
		).buildString();
	}

	private final HttpServletRequest _httpServletRequest;
	private final ItemSelector _itemSelector;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;

}