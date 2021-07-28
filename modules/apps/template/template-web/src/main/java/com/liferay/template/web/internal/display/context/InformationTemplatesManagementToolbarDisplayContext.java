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

import com.liferay.dynamic.data.mapping.constants.DDMActionKeys;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.template.constants.TemplatePortletKeys;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class InformationTemplatesManagementToolbarDisplayContext
	extends BaseTemplateManagementToolbarDisplayContext {

	public InformationTemplatesManagementToolbarDisplayContext(
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
		if (!containsAddPortletDisplayTemplatePermission(
				TemplatePortletKeys.TEMPLATE, DDMActionKeys.ADD_TEMPLATE)) {

			return null;
		}

		return CreationMenuBuilder.addDropdownItem(
			dropdownItem -> {
				dropdownItem.putData("action", "addInformationTemplate");
				dropdownItem.setLabel(
					LanguageUtil.get(themeDisplay.getLocale(), "add"));
			}
		).build();
	}

}