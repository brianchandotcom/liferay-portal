/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.object.client.dto.v1_0;

import com.liferay.headless.object.client.function.UnsafeSupplier;
import com.liferay.headless.object.client.serdes.v1_0.ObjectEntryCMSBulkActionSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Alicia García
 * @generated
 */
@Generated("")
public class ObjectEntryCMSBulkAction implements Cloneable, Serializable {

	public static ObjectEntryCMSBulkAction toDTO(String json) {
		return ObjectEntryCMSBulkActionSerDes.toDTO(json);
	}

	public Long[] getIds() {
		return ids;
	}

	public void setIds(Long[] ids) {
		this.ids = ids;
	}

	public void setIds(UnsafeSupplier<Long[], Exception> idsUnsafeSupplier) {
		try {
			ids = idsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long[] ids;

	public String[] getKeywords() {
		return keywords;
	}

	public void setKeywords(String[] keywords) {
		this.keywords = keywords;
	}

	public void setKeywords(
		UnsafeSupplier<String[], Exception> keywordsUnsafeSupplier) {

		try {
			keywords = keywordsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String[] keywords;

	public Long getObjectEntryFolderId() {
		return objectEntryFolderId;
	}

	public void setObjectEntryFolderId(Long objectEntryFolderId) {
		this.objectEntryFolderId = objectEntryFolderId;
	}

	public void setObjectEntryFolderId(
		UnsafeSupplier<Long, Exception> objectEntryFolderIdUnsafeSupplier) {

		try {
			objectEntryFolderId = objectEntryFolderIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long objectEntryFolderId;

	public com.liferay.headless.object.client.permission.Permission[]
		getPermissions() {

		return permissions;
	}

	public void setPermissions(
		com.liferay.headless.object.client.permission.Permission[]
			permissions) {

		this.permissions = permissions;
	}

	public void setPermissions(
		UnsafeSupplier
			<com.liferay.headless.object.client.permission.Permission[],
			 Exception> permissionsUnsafeSupplier) {

		try {
			permissions = permissionsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected com.liferay.headless.object.client.permission.Permission[]
		permissions;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setStatus(
		UnsafeSupplier<Integer, Exception> statusUnsafeSupplier) {

		try {
			status = statusUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer status;

	public Long[] getTaxonomyCategoryIds() {
		return taxonomyCategoryIds;
	}

	public void setTaxonomyCategoryIds(Long[] taxonomyCategoryIds) {
		this.taxonomyCategoryIds = taxonomyCategoryIds;
	}

	public void setTaxonomyCategoryIds(
		UnsafeSupplier<Long[], Exception> taxonomyCategoryIdsUnsafeSupplier) {

		try {
			taxonomyCategoryIds = taxonomyCategoryIdsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long[] taxonomyCategoryIds;

	@Override
	public ObjectEntryCMSBulkAction clone() throws CloneNotSupportedException {
		return (ObjectEntryCMSBulkAction)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ObjectEntryCMSBulkAction)) {
			return false;
		}

		ObjectEntryCMSBulkAction objectEntryCMSBulkAction =
			(ObjectEntryCMSBulkAction)object;

		return Objects.equals(toString(), objectEntryCMSBulkAction.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return ObjectEntryCMSBulkActionSerDes.toJSON(this);
	}

}