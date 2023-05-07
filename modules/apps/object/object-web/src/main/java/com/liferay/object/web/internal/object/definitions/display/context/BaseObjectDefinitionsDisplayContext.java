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
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.object.constants.ObjectWebKeys;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.web.internal.display.context.helper.ObjectRequestHelper;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Feliphe Marinho
 */
public abstract class BaseObjectDefinitionsDisplayContext {

	public String getAPIURL() {
		return "/o/object-admin/v1.0/object-definitions/" +
			getObjectDefinitionId() + getAPIURI();
	}

	public CreationMenu getCreationMenu() throws PortalException {
		CreationMenu creationMenu = new CreationMenu();

		if (!hasUpdateObjectDefinitionPermission()) {
			return creationMenu;
		}

		creationMenu.addDropdownItem(
			getCreationMenuDropdownItemUnsafeConsumer());

		return creationMenu;
	}

	public ObjectDefinition getObjectDefinition() {
		HttpServletRequest httpServletRequest =
			objectRequestHelper.getRequest();

		return (ObjectDefinition)httpServletRequest.getAttribute(
			ObjectWebKeys.OBJECT_DEFINITION);
	}

	public String getObjectDefinitionExternalReferenceCode() {
		ObjectDefinition objectDefinition = getObjectDefinition();

		return objectDefinition.getExternalReferenceCode();
	}

	public long getObjectDefinitionId() {
		ObjectDefinition objectDefinition = getObjectDefinition();

		return objectDefinition.getObjectDefinitionId();
	}

	public PortletURL getPortletURL() throws PortletException {
		return PortletURLUtil.clone(
			PortletURLUtil.getCurrent(
				objectRequestHelper.getLiferayPortletRequest(),
				objectRequestHelper.getLiferayPortletResponse()),
			objectRequestHelper.getLiferayPortletResponse());
	}

	public boolean hasUpdateObjectDefinitionPermission()
		throws PortalException {

		return objectDefinitionModelResourcePermission.contains(
			objectRequestHelper.getPermissionChecker(), getObjectDefinitionId(),
			ActionKeys.UPDATE);
	}

	protected BaseObjectDefinitionsDisplayContext(
		HttpServletRequest httpServletRequest,
		ModelResourcePermission<ObjectDefinition>
			objectDefinitionModelResourcePermission) {

		this.objectDefinitionModelResourcePermission =
			objectDefinitionModelResourcePermission;

		objectRequestHelper = new ObjectRequestHelper(httpServletRequest);
	}

	protected String getAPIURI() {
		return StringPool.BLANK;
	}

	protected UnsafeConsumer<DropdownItem, Exception>
		getCreationMenuDropdownItemUnsafeConsumer() {

		return dropdownItem -> {
		};
	}

	protected final ModelResourcePermission<ObjectDefinition>
		objectDefinitionModelResourcePermission;
	protected final ObjectRequestHelper objectRequestHelper;

}