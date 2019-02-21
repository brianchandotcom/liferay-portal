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

import com.liferay.data.engine.model.DEDataRecord;
import com.liferay.data.engine.model.DEDataRecordCollection;

/**
 * @author Leonardo Barros
 */
public class DEDataRecordCollectionRequestBuilder {

	public static DEDataRecordCollectionDeleteRequest.Builder deleteBuilder() {
		return new DEDataRecordCollectionDeleteRequest.Builder();
	}

	public static DEDataRecordCollectionDeleteModelPermissionsRequest.Builder
		deleteModelPermissionsBuilder(
			long companyId, long scopedGroupId, long deDataRecordCollectionId,
			String[] actionIds, String[] roleNames) {

		return new DEDataRecordCollectionDeleteModelPermissionsRequest.Builder(
			companyId, scopedGroupId, deDataRecordCollectionId, actionIds,
			roleNames);
	}

	public static DEDataRecordCollectionDeletePermissionsRequest.Builder
		deletePermissionsBuilder(
			long companyId, long scopedGroupId, String[] roleNames) {

		return new DEDataRecordCollectionDeletePermissionsRequest.Builder(
			companyId, scopedGroupId, roleNames);
	}

	public static DEDataRecordCollectionDeleteRecordRequest.Builder
		deleteRecordBuilder(long deDataRecordId) {

		return new DEDataRecordCollectionDeleteRecordRequest.Builder(
			deDataRecordId);
	}

	public static DEDataRecordCollectionGetRequest.Builder getBuilder() {
		return new DEDataRecordCollectionGetRequest.Builder();
	}

	public static DEDataRecordCollectionGetRecordRequest.Builder
		getRecordBuilder(long deDataRecordId) {

		return new DEDataRecordCollectionGetRecordRequest.Builder(
			deDataRecordId);
	}

	public static DEDataRecordCollectionListRequest.Builder listBuilder() {
		return new DEDataRecordCollectionListRequest.Builder();
	}

	public static DEDataRecordCollectionListRecordRequest.Builder
		listRecordBuilder(long deDataRecordCollectionId) {

		return new DEDataRecordCollectionListRecordRequest.Builder(
			deDataRecordCollectionId);
	}

	public static DEDataRecordCollectionSaveRequest.Builder saveBuilder(
		DEDataRecordCollection deDataRecordCollection) {

		return new DEDataRecordCollectionSaveRequest.Builder(
			deDataRecordCollection);
	}

	public static DEDataRecordCollectionSaveModelPermissionsRequest.Builder
		saveModelPermissionsBuilder(
			long companyId, long groupId, long scopedUserId, long scopedGroupId,
			long deDataRecordCollectionId, String[] roleNames) {

		return new DEDataRecordCollectionSaveModelPermissionsRequest.Builder(
			companyId, groupId, scopedUserId, scopedGroupId,
			deDataRecordCollectionId, roleNames);
	}

	public static DEDataRecordCollectionSavePermissionsRequest.Builder
		savePermissionsBuilder(
			long companyId, long scopedGroupId, String[] roleNames) {

		return new DEDataRecordCollectionSavePermissionsRequest.Builder(
			companyId, scopedGroupId, roleNames);
	}

	public static DEDataRecordCollectionSaveRecordRequest.Builder
		saveRecordBuilder(DEDataRecord deDataRecord) {

		return new DEDataRecordCollectionSaveRecordRequest.Builder(
			deDataRecord);
	}

	public static DEDataRecordCollectionSearchRequest.Builder searchBuilder() {
		return new DEDataRecordCollectionSearchRequest.Builder();
	}

}