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

import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.object.web.internal.constants.ObjectWebKeys;
import com.liferay.object.web.internal.display.context.helper.ObjectRequestHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

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