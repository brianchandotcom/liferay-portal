/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bulk.rest.client.dto.v1_0;

import com.liferay.bulk.rest.client.function.UnsafeSupplier;
import com.liferay.bulk.rest.client.serdes.v1_0.DeleteObjectAssetVersionBulkSelectionActionSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Alejandro Tardín
 * @generated
 */
@Generated("")
public class DeleteObjectAssetVersionBulkSelectionAction
	extends BulkAction implements Cloneable, Serializable {

	public static DeleteObjectAssetVersionBulkSelectionAction toDTO(
		String json) {

		return DeleteObjectAssetVersionBulkSelectionActionSerDes.toDTO(json);
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public void setClassName(
		UnsafeSupplier<String, Exception> classNameUnsafeSupplier) {

		try {
			className = classNameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String className;

	public Long getClassPK() {
		return classPK;
	}

	public void setClassPK(Long classPK) {
		this.classPK = classPK;
	}

	public void setClassPK(
		UnsafeSupplier<Long, Exception> classPKUnsafeSupplier) {

		try {
			classPK = classPKUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long classPK;

	public Integer[] getVersions() {
		return versions;
	}

	public void setVersions(Integer[] versions) {
		this.versions = versions;
	}

	public void setVersions(
		UnsafeSupplier<Integer[], Exception> versionsUnsafeSupplier) {

		try {
			versions = versionsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer[] versions;

	@Override
	public DeleteObjectAssetVersionBulkSelectionAction clone()
		throws CloneNotSupportedException {

		return (DeleteObjectAssetVersionBulkSelectionAction)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DeleteObjectAssetVersionBulkSelectionAction)) {
			return false;
		}

		DeleteObjectAssetVersionBulkSelectionAction
			deleteObjectAssetVersionBulkSelectionAction =
				(DeleteObjectAssetVersionBulkSelectionAction)object;

		return Objects.equals(
			toString(), deleteObjectAssetVersionBulkSelectionAction.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return DeleteObjectAssetVersionBulkSelectionActionSerDes.toJSON(this);
	}

}