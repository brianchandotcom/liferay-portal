/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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