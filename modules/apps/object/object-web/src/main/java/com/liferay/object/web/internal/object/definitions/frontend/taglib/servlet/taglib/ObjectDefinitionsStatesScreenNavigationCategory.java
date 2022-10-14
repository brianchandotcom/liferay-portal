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

package com.liferay.object.web.internal.object.definitions.frontend.taglib.servlet.taglib;

import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.list.type.service.ListTypeDefinitionService;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.web.internal.object.definitions.constants.ObjectDefinitionsScreenNavigationEntryConstants;
import com.liferay.object.web.internal.object.definitions.display.context.ObjectDefinitionsStateManagerDisplayContext;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Montenegro
 */
@Component(
	property = {
		"screen.navigation.category.order:Integer=70",
		"screen.navigation.entry.order:Integer=10"
	},
	service = {ScreenNavigationCategory.class, ScreenNavigationEntry.class}
)
public class ObjectDefinitionsStatesScreenNavigationCategory
	extends BaseObjectDefinitionsScreenNavigationEntry
	implements ScreenNavigationCategory {

	@Override
	public String getCategoryKey() {
		return ObjectDefinitionsScreenNavigationEntryConstants.
			CATEGORY_KEY_STATE_MANAGER;
	}

	@Override
	public String getEntryKey() {
		return ObjectDefinitionsScreenNavigationEntryConstants.
			ENTRY_KEY_STATE_MANAGER;
	}

	@Override
	public String getJspPath() {
		return "/object_definitions/object_definition/states.jsp";
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "state-manager");
	}

	@Override
	public boolean isVisible(User user, ObjectDefinition objectDefinition) {
		return !objectDefinition.isSystem();
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		httpServletRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
			new ObjectDefinitionsStateManagerDisplayContext(
				httpServletRequest, _listTypeDefinitionService,
				_objectDefinitionModelResourcePermission));

		super.render(httpServletRequest, httpServletResponse);
	}

	@Reference
	private Language _language;

	@Reference
	private ListTypeDefinitionService _listTypeDefinitionService;

	@Reference(
		target = "(model.class.name=com.liferay.object.model.ObjectDefinition)"
	)
	private ModelResourcePermission<ObjectDefinition>
		_objectDefinitionModelResourcePermission;

}