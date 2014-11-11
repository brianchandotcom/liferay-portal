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

package com.liferay.portal.service.persistence;

import aQute.bnd.annotation.ProviderType;

/**
 * @generated
 */
@ProviderType
public interface ResourcePermissionFinder {
	public int countByR_S(long roleId, int[] scopes);

	public int countByC_N_S_P_R_A(long companyId, java.lang.String name,
		int scope, java.lang.String primKey, long[] roleIds, long actionId);

	public java.util.List<com.liferay.portal.model.ResourcePermission> findByResource(
		long companyId, long groupId, java.lang.String name,
		java.lang.String primKey);

	public java.util.Map<java.io.Serializable, com.liferay.portal.model.ResourcePermission> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	public java.util.List<com.liferay.portal.model.ResourcePermission> findByR_S(
		long roleId, int[] scopes, int start, int end);
}