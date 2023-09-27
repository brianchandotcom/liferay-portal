/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharing.security.permission.resource;

import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionLogic;

import java.util.function.Consumer;

/**
 * @author Alejandro Tardín
 */
public interface SharingModelResourcePermissionConfigurator {

	public <T extends GroupedModel> void configure(
		ModelResourcePermission<T> modelResourcePermission,
		Consumer<ModelResourcePermissionLogic<T>> consumer);

}