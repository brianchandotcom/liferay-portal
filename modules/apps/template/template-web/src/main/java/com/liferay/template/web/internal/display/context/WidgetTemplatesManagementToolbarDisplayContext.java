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

package com.liferay.template.web.internal.display.context;

import com.liferay.dynamic.data.mapping.constants.DDMTemplateConstants;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class WidgetTemplatesManagementToolbarDisplayContext
	extends BaseTemplateManagementToolbarDisplayContext {

	public WidgetTemplatesManagementToolbarDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		TemplateDisplayContext templateDisplayContext) {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			templateDisplayContext);
	}

	@Override
	public CreationMenu getCreationMenu() {
		if (!templateDisplayContext.isAddDDMTemplateEnabled()) {
			return null;
		}

		List<Long> addAllowedClassNameIds = _getAddAllowedClassNameIds();

		if (addAllowedClassNameIds.isEmpty()) {
			return null;
		}

		CreationMenu creationMenu = new CreationMenu();

		PortletURL addDDMTemplateURL = PortletURLBuilder.createRenderURL(
			liferayPortletResponse
		).setMVCPath(
			"/edit_ddm_template.jsp"
		).setRedirect(
			themeDisplay.getURLCurrent()
		).setTabs1(
			templateDisplayContext.getTabs1()
		).setParameter(
			"groupId", themeDisplay.getScopeGroupId()
		).setParameter(
			"type", DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY
		).buildPortletURL();

		for (long addAllowedClassNameId : addAllowedClassNameIds) {
			addDDMTemplateURL.setParameter(
				"classNameId", String.valueOf(addAllowedClassNameId));
			addDDMTemplateURL.setParameter("classPK", "0");
			addDDMTemplateURL.setParameter(
				"resourceClassNameId",
				String.valueOf(
					templateDisplayContext.getResourceClassNameId()));

			creationMenu.addPrimaryDropdownItem(
				dropdownItem -> {
					dropdownItem.setHref(addDDMTemplateURL);
					dropdownItem.setLabel(
						LanguageUtil.get(
							httpServletRequest,
							templateDisplayContext.getTemplateTypeLabel(
								addAllowedClassNameId)));
				});
		}

		return creationMenu;
	}

	private List<Long> _getAddAllowedClassNameIds() {
		List<Long> addAllowedClassNameIds = new ArrayList<>();

		for (long classNameId : templateDisplayContext.getClassNameIds()) {
			if (containsAddPortletDisplayTemplatePermission(classNameId)) {
				addAllowedClassNameIds.add(classNameId);
			}
		}

		return addAllowedClassNameIds;
	}

}