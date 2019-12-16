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

package com.liferay.data.engine.rest.client.dto.v1_0;

import com.liferay.data.engine.rest.client.function.UnsafeSupplier;
import com.liferay.data.engine.rest.client.serdes.v1_0.DataRecordCollectionPermissionSerDes;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Jeyvison Nascimento
 * @generated
 */
@Generated("")
public class DataRecordCollectionPermission {

	public Boolean getAddDataRecord() {
		return addDataRecord;
	}

	public void setAddDataRecord(Boolean addDataRecord) {
		this.addDataRecord = addDataRecord;
	}

	public void setAddDataRecord(
		UnsafeSupplier<Boolean, Exception> addDataRecordUnsafeSupplier) {

		try {
			addDataRecord = addDataRecordUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean addDataRecord;

	public Boolean getAddDataRecordCollection() {
		return addDataRecordCollection;
	}

	public void setAddDataRecordCollection(Boolean addDataRecordCollection) {
		this.addDataRecordCollection = addDataRecordCollection;
	}

	public void setAddDataRecordCollection(
		UnsafeSupplier<Boolean, Exception>
			addDataRecordCollectionUnsafeSupplier) {

		try {
			addDataRecordCollection =
				addDataRecordCollectionUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean addDataRecordCollection;

	public Boolean getDefinePermissions() {
		return definePermissions;
	}

	public void setDefinePermissions(Boolean definePermissions) {
		this.definePermissions = definePermissions;
	}

	public void setDefinePermissions(
		UnsafeSupplier<Boolean, Exception> definePermissionsUnsafeSupplier) {

		try {
			definePermissions = definePermissionsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean definePermissions;

	public Boolean getDelete() {
		return delete;
	}

	public void setDelete(Boolean delete) {
		this.delete = delete;
	}

	public void setDelete(
		UnsafeSupplier<Boolean, Exception> deleteUnsafeSupplier) {

		try {
			delete = deleteUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean delete;

	public Boolean getDeleteDataRecord() {
		return deleteDataRecord;
	}

	public void setDeleteDataRecord(Boolean deleteDataRecord) {
		this.deleteDataRecord = deleteDataRecord;
	}

	public void setDeleteDataRecord(
		UnsafeSupplier<Boolean, Exception> deleteDataRecordUnsafeSupplier) {

		try {
			deleteDataRecord = deleteDataRecordUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean deleteDataRecord;

	public Boolean getExportDataRecord() {
		return exportDataRecord;
	}

	public void setExportDataRecord(Boolean exportDataRecord) {
		this.exportDataRecord = exportDataRecord;
	}

	public void setExportDataRecord(
		UnsafeSupplier<Boolean, Exception> exportDataRecordUnsafeSupplier) {

		try {
			exportDataRecord = exportDataRecordUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean exportDataRecord;

	public String[] getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String[] roleNames) {
		this.roleNames = roleNames;
	}

	public void setRoleNames(
		UnsafeSupplier<String[], Exception> roleNamesUnsafeSupplier) {

		try {
			roleNames = roleNamesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String[] roleNames;

	public Boolean getUpdate() {
		return update;
	}

	public void setUpdate(Boolean update) {
		this.update = update;
	}

	public void setUpdate(
		UnsafeSupplier<Boolean, Exception> updateUnsafeSupplier) {

		try {
			update = updateUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean update;

	public Boolean getUpdateDataRecord() {
		return updateDataRecord;
	}

	public void setUpdateDataRecord(Boolean updateDataRecord) {
		this.updateDataRecord = updateDataRecord;
	}

	public void setUpdateDataRecord(
		UnsafeSupplier<Boolean, Exception> updateDataRecordUnsafeSupplier) {

		try {
			updateDataRecord = updateDataRecordUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean updateDataRecord;

	public Boolean getView() {
		return view;
	}

	public void setView(Boolean view) {
		this.view = view;
	}

	public void setView(UnsafeSupplier<Boolean, Exception> viewUnsafeSupplier) {
		try {
			view = viewUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean view;

	public Boolean getViewDataRecord() {
		return viewDataRecord;
	}

	public void setViewDataRecord(Boolean viewDataRecord) {
		this.viewDataRecord = viewDataRecord;
	}

	public void setViewDataRecord(
		UnsafeSupplier<Boolean, Exception> viewDataRecordUnsafeSupplier) {

		try {
			viewDataRecord = viewDataRecordUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean viewDataRecord;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DataRecordCollectionPermission)) {
			return false;
		}

		DataRecordCollectionPermission dataRecordCollectionPermission =
			(DataRecordCollectionPermission)object;

		return Objects.equals(
			toString(), dataRecordCollectionPermission.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return DataRecordCollectionPermissionSerDes.toJSON(this);
	}

}