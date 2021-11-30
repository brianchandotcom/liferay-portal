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

package com.liferay.object.web.internal.list.type.display.context;

import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.object.web.internal.constants.ObjectWebKeys;
import com.liferay.object.web.internal.display.context.helper.ObjectRequestHelper;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Gabriel Albuquerque
 */
public class ViewListTypeEntriesDisplayContext {

	public ViewListTypeEntriesDisplayContext(
		HttpServletRequest httpServletRequest,
		ModelResourcePermission<ListTypeDefinition>
			listTypeDefinitionModelResourcePermission) {

		_listTypeDefinitionModelResourcePermission =
			listTypeDefinitionModelResourcePermission;

		_objectRequestHelper = new ObjectRequestHelper(httpServletRequest);
	}

	public String getAPIURL() {
		return "/o/headless-admin-list-type/v1.0/list-type-definitions/" +
			_getListTypeDefinitionId() + "/list-type-entries";
	}

	public CreationMenu getCreationMenu() throws PortalException {
		CreationMenu creationMenu = new CreationMenu();

		if (!hasUpdateListTypeDefinitionPermission()) {
			return creationMenu;
		}

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

	public List<FDSActionDropdownItem> getFDSActionDropdownItems()
		throws Exception {

		return Arrays.asList(
			new FDSActionDropdownItem(
				PortletURLBuilder.create(
					PortletURLUtil.clone(
						PortletURLUtil.getCurrent(
							_objectRequestHelper.getLiferayPortletRequest(),
							_objectRequestHelper.getLiferayPortletResponse()),
						_objectRequestHelper.getLiferayPortletResponse())
				).setMVCRenderCommandName(
					"/list_type_definitions/edit_list_type_entry"
				).setParameter(
					"listTypeEntryId", "{id}"
				).setWindowState(
					LiferayWindowState.POP_UP
				).buildString(),
				"view", "view",
				LanguageUtil.get(_objectRequestHelper.getRequest(), "view"),
				"get", null, "modal"),
			new FDSActionDropdownItem(
				"/o/headless-admin-list-type/v1.0/list-type-entries/{id}",
				"trash", "delete",
				LanguageUtil.get(_objectRequestHelper.getRequest(), "delete"),
				"delete", "delete", "async"));
	}

	public boolean hasUpdateListTypeDefinitionPermission()
		throws PortalException {

		return _listTypeDefinitionModelResourcePermission.contains(
			_objectRequestHelper.getPermissionChecker(),
			_getListTypeDefinitionId(), ActionKeys.UPDATE);
	}

	private long _getListTypeDefinitionId() {
		HttpServletRequest httpServletRequest =
			_objectRequestHelper.getRequest();

		ListTypeDefinition listTypeDefinition =
			(ListTypeDefinition)httpServletRequest.getAttribute(
				ObjectWebKeys.LIST_TYPE_DEFINITION);

		return listTypeDefinition.getListTypeDefinitionId();
	}

	private final ModelResourcePermission<ListTypeDefinition>
		_listTypeDefinitionModelResourcePermission;
	private final ObjectRequestHelper _objectRequestHelper;

}