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

package com.liferay.template.web.internal.util;

import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.template.web.internal.security.permissions.resource.DDMTemplatePermission;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Lourdes Fernández Besada
 */
public class DDMTemplateActionDropdownItemsProvider {

	public DDMTemplateActionDropdownItemsProvider(
		DDMTemplate ddmTemplate, HttpServletRequest httpServletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_ddmTemplate = ddmTemplate;
		_httpServletRequest = httpServletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getActionDropdownItems() {
		return DropdownItemListBuilder.add(
			() -> DDMTemplatePermission.containsAddTemplatePermission(
				_themeDisplay.getPermissionChecker(),
				_themeDisplay.getScopeGroupId(), _ddmTemplate.getClassNameId(),
				_ddmTemplate.getResourceClassNameId()),
			_getCopyDDMTemplateActionUnsafeConsumer()
		).add(
			() -> DDMTemplatePermission.contains(
				_themeDisplay.getPermissionChecker(), _ddmTemplate,
				ActionKeys.DELETE),
			_getDeleteDDMTemplateActionUnsafeConsumer()
		).build();
	}

	private UnsafeConsumer<DropdownItem, Exception>
			_getCopyDDMTemplateActionUnsafeConsumer()
		throws Exception {

		return dropdownItem -> {
			dropdownItem.setHref(
				PortletURLBuilder.createRenderURL(
					_liferayPortletResponse
				).build(),
				"redirect", _themeDisplay.getURLCurrent(), "ddmTemplateId",
				_ddmTemplate.getTemplateId(), "mvcPath",
				"/copy_ddm_template.jsp");
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "copy"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
			_getDeleteDDMTemplateActionUnsafeConsumer()
		throws Exception {

		return dropdownItem -> {
			dropdownItem.putData("action", "deleteDDMTemplate");
			dropdownItem.putData(
				"deleteDDMTemplateURL",
				PortletURLBuilder.createActionURL(
					_liferayPortletResponse
				).setActionName(
					"/template/delete_ddm_template"
				).setRedirect(
					_themeDisplay.getURLCurrent()
				).setParameter(
					"ddmTemplateId", _ddmTemplate.getTemplateId()
				).buildString());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "delete"));
		};
	}

	private final DDMTemplate _ddmTemplate;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final ThemeDisplay _themeDisplay;

}