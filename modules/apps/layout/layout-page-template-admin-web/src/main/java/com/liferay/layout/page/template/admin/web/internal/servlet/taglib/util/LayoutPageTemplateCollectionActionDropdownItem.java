/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.admin.web.internal.servlet.taglib.util;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.layout.page.template.admin.web.internal.security.permission.resource.LayoutPageTemplateCollectionPermission;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
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
	}

	public List<DropdownItem> getActionDropdownItems(
		LayoutPageTemplateCollection layoutPageTemplateCollection,
		String tabs1) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return DropdownItemListBuilder.addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						() -> LayoutPageTemplateCollectionPermission.contains(
							themeDisplay.getPermissionChecker(),
							layoutPageTemplateCollection, ActionKeys.UPDATE),
						dropdownItem -> {
							dropdownItem.setHref(
								PortletURLBuilder.createRenderURL(
									_renderResponse
								).setMVCRenderCommandName(
									"/layout_page_template_admin" +
										"/edit_layout_page_template_collection"
								).setRedirect(
									themeDisplay.getURLCurrent()
								).setTabs1(
									tabs1
								).setParameter(
									"layoutPageTemplateCollectionId",
									layoutPageTemplateCollection.
										getLayoutPageTemplateCollectionId()
								).buildString());
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
						() -> LayoutPageTemplateCollectionPermission.contains(
							themeDisplay.getPermissionChecker(),
							layoutPageTemplateCollection,
							ActionKeys.PERMISSIONS),
						dropdownItem -> {
							dropdownItem.putData(
								"action",
								"permissionsLayoutPageTemplateCollection");
							dropdownItem.putData(
								"permissionsLayoutPageTemplateCollectionURL",
								PermissionsURLTag.doTag(
									StringPool.BLANK,
									LayoutPageTemplateCollection.class.
										getName(),
									layoutPageTemplateCollection.getName(),
									null,
									String.valueOf(
										layoutPageTemplateCollection.
											getLayoutPageTemplateCollectionId()),
									LiferayWindowState.POP_UP.toString(), null,
									_httpServletRequest));
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
							themeDisplay.getPermissionChecker(),
							layoutPageTemplateCollection, ActionKeys.DELETE),
						dropdownItem -> {
							dropdownItem.putData(
								"action", "deleteLayoutPageTemplateCollection");
							dropdownItem.putData(
								"deleteLayoutPageTemplateCollectionURL",
								PortletURLBuilder.createActionURL(
									_renderResponse
								).setActionName(
									"/layout_page_template_admin/delete_" +
										"layout_page_template_collection"
								).setRedirect(
									PortletURLBuilder.createRenderURL(
										_renderResponse
									).setTabs1(
										tabs1
									).buildString()
								).setParameter(
									"layoutPageTemplateCollectionId",
									layoutPageTemplateCollection.
										getLayoutPageTemplateCollectionId()
								).buildString());
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

	private final HttpServletRequest _httpServletRequest;
	private final RenderResponse _renderResponse;

}