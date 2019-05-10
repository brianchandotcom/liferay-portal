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

package com.liferay.portal.kernel.service.persistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @deprecated
 * @generated
 */
@Deprecated
@ProviderType
public interface ResourceTypePermissionFinder {

	public java.util.List
		<com.liferay.portal.kernel.model.ResourceTypePermission>
			findByEitherScopeC_G_N(long companyId, long groupId, String name);

	public java.util.List
		<com.liferay.portal.kernel.model.ResourceTypePermission>
			findByGroupScopeC_N_R(long companyId, String name, long roleId);

}