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

import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.list.type.service.ListTypeDefinitionService;
import com.liferay.object.field.business.type.ObjectFieldBusinessTypeRegistry;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldSettingLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.web.internal.object.definitions.constants.ObjectDefinitionsScreenNavigationEntryConstants;
import com.liferay.object.web.internal.object.definitions.display.context.ObjectDefinitionsFieldsDisplayContext;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Renan Vasconcelos
 */
@Component(
	property = "screen.navigation.entry.order:Integer=10",
	service = ScreenNavigationEntry.class
)
public class FieldsObjectDefinitionsScreenNavigationEntry
	extends BaseObjectDefinitionsScreenNavigationEntry {

	@Override
	public String getCategoryKey() {
		return ObjectDefinitionsScreenNavigationEntryConstants.
			CATEGORY_KEY_FIELDS;
	}

	@Override
	public String getJspPath() {
		return "/object_definitions/object_definition/fields.jsp";
	}

	@Override
	public boolean isVisible(User user, ObjectDefinition context) {
		return super.isVisible(user, context);
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		httpServletRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
			new ObjectDefinitionsFieldsDisplayContext(
				httpServletRequest, _listTypeDefinitionService,
				_objectDefinitionLocalService,
				_objectDefinitionModelResourcePermission,
				_objectFieldBusinessTypeRegistry,
				_objectFieldSettingLocalService,
				_objectRelationshipLocalService));

		super.render(httpServletRequest, httpServletResponse);
	}

	@Reference
	private ListTypeDefinitionService _listTypeDefinitionService;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.object.model.ObjectDefinition)"
	)
	private ModelResourcePermission<ObjectDefinition>
		_objectDefinitionModelResourcePermission;

	@Reference
	private ObjectFieldBusinessTypeRegistry _objectFieldBusinessTypeRegistry;

	@Reference
	private ObjectFieldSettingLocalService _objectFieldSettingLocalService;

	@Reference
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

}