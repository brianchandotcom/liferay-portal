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

package com.liferay.journal.web.internal.servlet.taglib.util;

import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.journal.web.internal.security.permission.resource.DDMTemplatePermission;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.security.PermissionsURLTag;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class JournalDDMTemplateActionDropdownItemsProvider {

	public JournalDDMTemplateActionDropdownItemsProvider(
		DDMTemplate ddmTemplate, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_ddmTemplate = ddmTemplate;
		_renderResponse = renderResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);
		_themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getActionDropdownItems() throws Exception {
		return new DropdownItemList() {
			{
				if (DDMTemplatePermission.contains(
						_themeDisplay.getPermissionChecker(), _ddmTemplate,
						ActionKeys.UPDATE)) {

					add(_getEditDDMTemplateActionUnsafeConsumer());
				}

				if (DDMTemplatePermission.contains(
						_themeDisplay.getPermissionChecker(), _ddmTemplate,
						ActionKeys.PERMISSIONS)) {

					add(_getPermissionsDDMTemplateActionUnsafeConsumer());
				}

				Group scopeGroup = _themeDisplay.getScopeGroup();

				if ((!scopeGroup.hasLocalOrRemoteStagingGroup() ||
					 scopeGroup.isStagingGroup()) &&
					DDMTemplatePermission.containsAddTemplatePermission(
						_themeDisplay.getPermissionChecker(),
						_themeDisplay.getScopeGroupId(),
						_ddmTemplate.getClassNameId(),
						_ddmTemplate.getResourceClassNameId())) {

					add(_getCopyDDMTemplateActionUnsafeConsumer());
				}

				if (DDMTemplatePermission.contains(
						_themeDisplay.getPermissionChecker(), _ddmTemplate,
						ActionKeys.DELETE)) {

					add(_getDeleteDDMTemplateActionUnsafeConsumer());
				}
			}
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getCopyDDMTemplateActionUnsafeConsumer() {

		return dropdownItem -> {
			dropdownItem.setHref(
				_renderResponse.createRenderURL(), "mvcPath",
				"/copy_ddm_template.jsp", "redirect",
				_themeDisplay.getURLCurrent(), "ddmTemplateId",
				_ddmTemplate.getTemplateId());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "copy"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getDeleteDDMTemplateActionUnsafeConsumer() {

		PortletURL deleteDDMTemplateURL = _renderResponse.createActionURL();

		deleteDDMTemplateURL.setParameter(
			ActionRequest.ACTION_NAME, "/journal/delete_ddm_template");
		deleteDDMTemplateURL.setParameter("mvcPath", "/view_ddm_templates.jsp");
		deleteDDMTemplateURL.setParameter(
			"redirect", _themeDisplay.getURLCurrent());
		deleteDDMTemplateURL.setParameter(
			"ddmTemplateId", String.valueOf(_ddmTemplate.getTemplateId()));

		return dropdownItem -> {
			dropdownItem.putData("action", "deleteDDMTemplate");
			dropdownItem.putData(
				"deleteDDMTemplateURL", deleteDDMTemplateURL.toString());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "delete"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getEditDDMTemplateActionUnsafeConsumer() {

		return dropdownItem -> {
			dropdownItem.setHref(
				_renderResponse.createRenderURL(), "mvcPath",
				"/edit_ddm_template.jsp", "redirect",
				_themeDisplay.getURLCurrent(), "ddmTemplateId",
				_ddmTemplate.getTemplateId());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "edit"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
			_getPermissionsDDMTemplateActionUnsafeConsumer()
		throws Exception {

		String permissionsDDMTemplateURL = PermissionsURLTag.doTag(
			StringPool.BLANK,
			DDMTemplatePermission.getTemplateModelResourceName(
				_ddmTemplate.getResourceClassNameId()),
			_ddmTemplate.getName(_themeDisplay.getLocale()), null,
			String.valueOf(_ddmTemplate.getTemplateId()),
			LiferayWindowState.POP_UP.toString(), null, _httpServletRequest);

		return dropdownItem -> {
			dropdownItem.putData("action", "permissionsDDMTemplate");
			dropdownItem.putData(
				"permissionsDDMTemplateURL", permissionsDDMTemplateURL);
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "permissions"));
		};
	}

	private final DDMTemplate _ddmTemplate;
	private final HttpServletRequest _httpServletRequest;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;

}