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

package com.liferay.object.web.internal.object.definitions.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.web.internal.constants.ObjectWebKeys;
import com.liferay.object.web.internal.display.context.util.ObjectRequestHelper;
import com.liferay.portal.kernel.language.LanguageUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 * @author Gabriel Albuquerque
 */
public class ViewObjectFieldsDisplayContext {

	public ViewObjectFieldsDisplayContext(
		HttpServletRequest httpServletRequest) {

		_objectRequestHelper = new ObjectRequestHelper(httpServletRequest);
	}

	public String getAPIURL() {
		return "/o/object-admin/v1.0/object-definitions/" +
			getObjectDefinitionId() + "/object-fields";
	}

	public CreationMenu getCreationMenu() throws Exception {
		CreationMenu creationMenu = new CreationMenu();

		//TODO Check permissions

		creationMenu.addDropdownItem(
			dropdownItem -> {
				dropdownItem.setHref("addObjectField");
				dropdownItem.setLabel(
					LanguageUtil.get(
						_objectRequestHelper.getRequest(), "add-object-field"));
				dropdownItem.setTarget("event");
			});

		return creationMenu;
	}

	public long getObjectDefinitionId() {
		HttpServletRequest httpServletRequest =
			_objectRequestHelper.getRequest();

		ObjectDefinition objectDefinition =
			(ObjectDefinition)httpServletRequest.getAttribute(
				ObjectWebKeys.OBJECT_DEFINITION);

		return objectDefinition.getObjectDefinitionId();
	}

	private final ObjectRequestHelper _objectRequestHelper;

}