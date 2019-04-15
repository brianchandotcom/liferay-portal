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

package com.liferay.portal.kernel.security.permission.resource.definition;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionLogic;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import java.util.function.Consumer;

/**
 * @author Preston Crary
 */
public interface ModelResourcePermissionDefinition<T extends GroupedModel> {

	public T getModel(long primaryKey) throws PortalException;

	public Class<T> getModelClass();

	public PortletResourcePermission getPortletResourcePermission();

	public long getPrimaryKey(T t);

	public default String mapActionId(String actionId) {
		return actionId;
	}

	public void registerModelResourcePermissionLogics(
		ModelResourcePermission<T> modelResourcePermission,
		Consumer<ModelResourcePermissionLogic<T>>
			modelResourcePermissionLogicConsumer);

}