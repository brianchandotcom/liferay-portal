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

package com.liferay.data.engine.rest.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Jeyvison Nascimento
 * @generated
 */
@Generated("")
@GraphQLName("DataRecordCollectionPermission")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "DataRecordCollectionPermission")
public class DataRecordCollectionPermission {

	@Schema
	public Boolean getAddDataRecord() {
		return addDataRecord;
	}

	public void setAddDataRecord(Boolean addDataRecord) {
		this.addDataRecord = addDataRecord;
	}

	@JsonIgnore
	public void setAddDataRecord(
		UnsafeSupplier<Boolean, Exception> addDataRecordUnsafeSupplier) {

		try {
			addDataRecord = addDataRecordUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean addDataRecord;

	@Schema
	public Boolean getAddDataRecordCollection() {
		return addDataRecordCollection;
	}

	public void setAddDataRecordCollection(Boolean addDataRecordCollection) {
		this.addDataRecordCollection = addDataRecordCollection;
	}

	@JsonIgnore
	public void setAddDataRecordCollection(
		UnsafeSupplier<Boolean, Exception>
			addDataRecordCollectionUnsafeSupplier) {

		try {
			addDataRecordCollection =
				addDataRecordCollectionUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean addDataRecordCollection;

	@Schema
	public Boolean getDefinePermissions() {
		return definePermissions;
	}

	public void setDefinePermissions(Boolean definePermissions) {
		this.definePermissions = definePermissions;
	}

	@JsonIgnore
	public void setDefinePermissions(
		UnsafeSupplier<Boolean, Exception> definePermissionsUnsafeSupplier) {

		try {
			definePermissions = definePermissionsUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean definePermissions;

	@Schema
	public Boolean getDelete() {
		return delete;
	}

	public void setDelete(Boolean delete) {
		this.delete = delete;
	}

	@JsonIgnore
	public void setDelete(
		UnsafeSupplier<Boolean, Exception> deleteUnsafeSupplier) {

		try {
			delete = deleteUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean delete;

	@Schema
	public Boolean getDeleteDataRecord() {
		return deleteDataRecord;
	}

	public void setDeleteDataRecord(Boolean deleteDataRecord) {
		this.deleteDataRecord = deleteDataRecord;
	}

	@JsonIgnore
	public void setDeleteDataRecord(
		UnsafeSupplier<Boolean, Exception> deleteDataRecordUnsafeSupplier) {

		try {
			deleteDataRecord = deleteDataRecordUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean deleteDataRecord;

	@Schema
	public Boolean getExportDataRecord() {
		return exportDataRecord;
	}

	public void setExportDataRecord(Boolean exportDataRecord) {
		this.exportDataRecord = exportDataRecord;
	}

	@JsonIgnore
	public void setExportDataRecord(
		UnsafeSupplier<Boolean, Exception> exportDataRecordUnsafeSupplier) {

		try {
			exportDataRecord = exportDataRecordUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean exportDataRecord;

	@Schema
	public String[] getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String[] roleNames) {
		this.roleNames = roleNames;
	}

	@JsonIgnore
	public void setRoleNames(
		UnsafeSupplier<String[], Exception> roleNamesUnsafeSupplier) {

		try {
			roleNames = roleNamesUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String[] roleNames;

	@Schema
	public Boolean getUpdate() {
		return update;
	}

	public void setUpdate(Boolean update) {
		this.update = update;
	}

	@JsonIgnore
	public void setUpdate(
		UnsafeSupplier<Boolean, Exception> updateUnsafeSupplier) {

		try {
			update = updateUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean update;

	@Schema
	public Boolean getUpdateDataRecord() {
		return updateDataRecord;
	}

	public void setUpdateDataRecord(Boolean updateDataRecord) {
		this.updateDataRecord = updateDataRecord;
	}

	@JsonIgnore
	public void setUpdateDataRecord(
		UnsafeSupplier<Boolean, Exception> updateDataRecordUnsafeSupplier) {

		try {
			updateDataRecord = updateDataRecordUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean updateDataRecord;

	@Schema
	public Boolean getView() {
		return view;
	}

	public void setView(Boolean view) {
		this.view = view;
	}

	@JsonIgnore
	public void setView(UnsafeSupplier<Boolean, Exception> viewUnsafeSupplier) {
		try {
			view = viewUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean view;

	@Schema
	public Boolean getViewDataRecord() {
		return viewDataRecord;
	}

	public void setViewDataRecord(Boolean viewDataRecord) {
		this.viewDataRecord = viewDataRecord;
	}

	@JsonIgnore
	public void setViewDataRecord(
		UnsafeSupplier<Boolean, Exception> viewDataRecordUnsafeSupplier) {

		try {
			viewDataRecord = viewDataRecordUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
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
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (addDataRecord != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"addDataRecord\": ");

			sb.append(addDataRecord);
		}

		if (addDataRecordCollection != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"addDataRecordCollection\": ");

			sb.append(addDataRecordCollection);
		}

		if (definePermissions != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"definePermissions\": ");

			sb.append(definePermissions);
		}

		if (delete != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"delete\": ");

			sb.append(delete);
		}

		if (deleteDataRecord != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"deleteDataRecord\": ");

			sb.append(deleteDataRecord);
		}

		if (exportDataRecord != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"exportDataRecord\": ");

			sb.append(exportDataRecord);
		}

		if (roleNames != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"roleNames\": ");

			sb.append("[");

			for (int i = 0; i < roleNames.length; i++) {
				sb.append("\"");

				sb.append(_escape(roleNames[i]));

				sb.append("\"");

				if ((i + 1) < roleNames.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (update != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"update\": ");

			sb.append(update);
		}

		if (updateDataRecord != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"updateDataRecord\": ");

			sb.append(updateDataRecord);
		}

		if (view != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"view\": ");

			sb.append(view);
		}

		if (viewDataRecord != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"viewDataRecord\": ");

			sb.append(viewDataRecord);
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		defaultValue = "com.liferay.data.engine.rest.dto.v1_0.DataRecordCollectionPermission",
		name = "x-class-name"
	)
	public String xClassName;

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		return string.replaceAll("\"", "\\\\\"");
	}

	private static String _toJSON(Map<String, ?> map) {
		StringBuilder sb = new StringBuilder("{");

		@SuppressWarnings("unchecked")
		Set set = map.entrySet();

		@SuppressWarnings("unchecked")
		Iterator<Map.Entry<String, ?>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, ?> entry = iterator.next();

			sb.append("\"");
			sb.append(entry.getKey());
			sb.append("\":");
			sb.append("\"");
			sb.append(entry.getValue());
			sb.append("\"");

			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}