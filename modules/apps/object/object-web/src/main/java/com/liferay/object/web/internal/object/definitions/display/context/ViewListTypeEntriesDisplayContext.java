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
import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.object.web.internal.display.context.util.ObjectRequestHelper;
import com.liferay.portal.kernel.language.LanguageUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Gabriel Albuquerque
 */
public class ViewListTypeEntriesDisplayContext {

	public ViewListTypeEntriesDisplayContext(
		HttpServletRequest httpServletRequest) {

		_objectRequestHelper = new ObjectRequestHelper(httpServletRequest);
	}

	public String getAPIURL() {
		return "/o/headless-admin-list-type/v1.0/list-type-definitions/" +
			_getListTypeDefinitionId() + "/list-type-entries";
	}

	public CreationMenu getCreationMenu() {
		CreationMenu creationMenu = new CreationMenu();

		creationMenu.addDropdownItem(
			dropdownItem -> {
				dropdownItem.setHref("addListTypeEntry");
				dropdownItem.setLabel(
					LanguageUtil.get(
						_objectRequestHelper.getRequest(), "add-item"));
				dropdownItem.setTarget("event");
			});

		return creationMenu;
	}

	private long _getListTypeDefinitionId() {
		HttpServletRequest httpServletRequest =
			_objectRequestHelper.getRequest();

		ListTypeDefinition listTypeDefinition =
			(ListTypeDefinition)httpServletRequest.getAttribute(
				"LIST_TYPE_DEFINITION");

		return listTypeDefinition.getListTypeDefinitionId();
	}

	private final ObjectRequestHelper _objectRequestHelper;

}