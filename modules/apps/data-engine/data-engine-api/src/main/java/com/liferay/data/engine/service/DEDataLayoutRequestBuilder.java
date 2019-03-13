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

package com.liferay.data.engine.service;

import com.liferay.data.engine.model.DEDataLayout;

/**
 * @author Jeyvison Nascimento
 */
public class DEDataLayoutRequestBuilder {

	public static DEDataLayoutCountRequest.Builder countBuilder() {
		return new DEDataLayoutCountRequest.Builder();
	}

	public static DEDataLayoutDeleteRequest.Builder deleteBuilder() {
		return new DEDataLayoutDeleteRequest.Builder();
	}

	public static DEDataLayoutDeleteModelPermissionsRequest.Builder
		deleteModelPermissionsBuilder(
			long companyId, long scopedGroupId, long deDataLayoutId,
			boolean revokeDelete, boolean revokeUpdate, boolean revokeView,
			String[] roleNames) {

		return new DEDataLayoutDeleteModelPermissionsRequest.Builder(
			companyId, scopedGroupId, deDataLayoutId, revokeDelete,
			revokeUpdate, revokeView, roleNames);
	}

	public static DEDataLayoutDeletePermissionsRequest.Builder
		deletePermissionsBuilder(
			long companyId, long scopedGroupId, boolean revokeAddDataLayout,
			boolean revokeDefinePermissions, String[] roleNames) {

		return new DEDataLayoutDeletePermissionsRequest.Builder(
			companyId, scopedGroupId, revokeAddDataLayout,
			revokeDefinePermissions, roleNames);
	}

	public static DEDataLayoutGetRequest.Builder getBuilder() {
		return new DEDataLayoutGetRequest.Builder();
	}

	public static DEDataLayoutListRequest.Builder listBuilder() {
		return new DEDataLayoutListRequest.Builder();
	}

	public static DEDataLayoutSaveRequest.Builder saveBuilder(
		DEDataLayout deDataLayout) {

		return new DEDataLayoutSaveRequest.Builder(deDataLayout);
	}

	public static DEDataLayoutSaveModelPermissionsRequest.Builder
		saveModelPermissionsBuilder(
			long companyId, long groupId, long scopedUserId, long scopedGroupId,
			long deDataLayoutId, String[] roleNames) {

		return new DEDataLayoutSaveModelPermissionsRequest.Builder(
			companyId, groupId, scopedUserId, scopedGroupId, deDataLayoutId,
			roleNames);
	}

	public static DEDataLayoutSavePermissionsRequest.Builder
		savePermissionsBuilder(
			long companyId, long scopedGroupId, String[] roleNames) {

		return new DEDataLayoutSavePermissionsRequest.Builder(
			companyId, scopedGroupId, roleNames);
	}

}