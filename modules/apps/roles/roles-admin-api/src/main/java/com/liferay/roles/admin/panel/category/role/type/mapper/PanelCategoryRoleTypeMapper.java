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

package com.liferay.roles.admin.panel.category.role.type.mapper;

/**
 * Defines a mapping between role types and a panel category. When implemented,
 * the returned role types will be able to define permissions for apps under the
 * returned panel category. This will be visible in the Define Permissions view
 * in the Roles Admin portlet.
 *
 * @author Drew Brokke
 * @review
 */
public interface PanelCategoryRoleTypeMapper {

	public String getPanelCategoryKey();

	public int[] getRoleTypes();

}