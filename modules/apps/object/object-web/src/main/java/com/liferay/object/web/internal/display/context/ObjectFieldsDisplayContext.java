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

package com.liferay.object.web.internal.display.context;

import com.liferay.frontend.taglib.clay.data.set.servlet.taglib.util.ClayDataSetActionDropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.web.internal.constants.ObjectWebKeys;
import com.liferay.object.web.internal.display.context.util.ObjectRequestHelper;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 */
public class ObjectFieldsDisplayContext {

	public ObjectFieldsDisplayContext(HttpServletRequest httpServletRequest) {
		_objectRequestHelper = new ObjectRequestHelper(httpServletRequest);
	}

	public String getAPIURL() {
		return "/o/object-admin/v1.0/object-definitions/" +
			getObjectDefinitionId() + "/fields";
	}

	public List<ClayDataSetActionDropdownItem>
			getClayDataSetActionDropdownItems()
		throws PortalException, PortletException {

		List<ClayDataSetActionDropdownItem> clayDataSetActionDropdownItems =
			new ArrayList<>();

		clayDataSetActionDropdownItems.add(
			new ClayDataSetActionDropdownItem(
				PortletURLBuilder.create(
					getPortletURL()
				).setMVCRenderCommandName(
					"/object_definitions_admin/edit_object_field"
				).setParameter(
					"objectFieldId", "{id}"
				).buildString(),
				"view", "view",
				LanguageUtil.get(_objectRequestHelper.getRequest(), "view"),
				"get", null, "sidePanel"));

		clayDataSetActionDropdownItems.add(
			new ClayDataSetActionDropdownItem(
				getAPIURL() + "/{id}", "trash", "delete",
				LanguageUtil.get(_objectRequestHelper.getRequest(), "delete"),
				"delete", "delete", "async"));

		return clayDataSetActionDropdownItems;
	}

	public CreationMenu getCreationMenu() throws Exception {
		CreationMenu creationMenu = new CreationMenu();

		//TODO Check permissions
		creationMenu.addDropdownItem(
			dropdownItem -> {
				dropdownItem.setHref("eventName");
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

	public PortletURL getPortletURL() throws PortletException {
		return PortletURLUtil.clone(
			PortletURLUtil.getCurrent(
				_objectRequestHelper.getLiferayPortletRequest(),
				_objectRequestHelper.getLiferayPortletResponse()),
			_objectRequestHelper.getLiferayPortletResponse());
	}

	private final ObjectRequestHelper _objectRequestHelper;

}