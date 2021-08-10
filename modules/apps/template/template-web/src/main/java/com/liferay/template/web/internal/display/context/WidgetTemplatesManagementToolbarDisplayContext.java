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
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateHandlerRegistryUtil;
import com.liferay.portal.kernel.template.comparator.TemplateHandlerComparator;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.ArrayList;
import java.util.Collections;
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
		WidgetTemplatesTemplateDisplayContext
			widgetTemplatesTemplateDisplayContext) {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			widgetTemplatesTemplateDisplayContext.getTemplateSearchContainer());

		_widgetTemplatesTemplateDisplayContext =
			widgetTemplatesTemplateDisplayContext;
	}

	@Override
	public CreationMenu getCreationMenu() {
		if (!_widgetTemplatesTemplateDisplayContext.isAddDDMTemplateEnabled()) {
			return null;
		}

		List<TemplateHandler> addAllowedTemplateHandlers =
			_getAddAllowedTemplateHandlers();

		if (addAllowedTemplateHandlers.isEmpty()) {
			return null;
		}

		CreationMenu creationMenu = new CreationMenu();

		PortletURL addDDMTemplateURL = PortletURLBuilder.createRenderURL(
			liferayPortletResponse
		).setMVCRenderCommandName(
			"/template/edit_ddm_template"
		).setRedirect(
			themeDisplay.getURLCurrent()
		).setTabs1(
			_widgetTemplatesTemplateDisplayContext.getTabs1()
		).setParameter(
			"groupId", themeDisplay.getScopeGroupId()
		).setParameter(
			"type", DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY
		).buildPortletURL();

		for (TemplateHandler addAllowedTemplateHandler :
				addAllowedTemplateHandlers) {

			addDDMTemplateURL.setParameter(
				"classNameId",
				String.valueOf(
					PortalUtil.getClassNameId(
						addAllowedTemplateHandler.getClassName())));
			addDDMTemplateURL.setParameter("classPK", "0");
			addDDMTemplateURL.setParameter(
				"resourceClassNameId",
				String.valueOf(
					_widgetTemplatesTemplateDisplayContext.
						getResourceClassNameId()));

			creationMenu.addPrimaryDropdownItem(
				dropdownItem -> {
					dropdownItem.setHref(addDDMTemplateURL);
					dropdownItem.setLabel(
						LanguageUtil.get(
							httpServletRequest,
							addAllowedTemplateHandler.getName(
								themeDisplay.getLocale())));
				});
		}

		return creationMenu;
	}

	private List<TemplateHandler> _getAddAllowedTemplateHandlers() {
		List<TemplateHandler> addAllowedTemplateHandlers = new ArrayList<>();

		for (long classNameId :
				_widgetTemplatesTemplateDisplayContext.getClassNameIds()) {

			TemplateHandler templateHandler =
				TemplateHandlerRegistryUtil.getTemplateHandler(classNameId);

			if (containsAddPortletDisplayTemplatePermission(
					templateHandler.getResourceName(),
					ActionKeys.ADD_PORTLET_DISPLAY_TEMPLATE)) {

				addAllowedTemplateHandlers.add(templateHandler);
			}
		}

		Collections.sort(
			addAllowedTemplateHandlers,
			new TemplateHandlerComparator(themeDisplay.getLocale()));

		return addAllowedTemplateHandlers;
	}

	private final WidgetTemplatesTemplateDisplayContext
		_widgetTemplatesTemplateDisplayContext;

}