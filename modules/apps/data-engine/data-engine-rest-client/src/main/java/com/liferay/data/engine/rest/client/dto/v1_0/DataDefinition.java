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

import java.util.Date;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Jeyvison Nascimento
 * @generated
 */
@Generated("")
public class DataDefinition {

	public DataDefinitionField[] getDataDefinitionFields() {
		return dataDefinitionFields;
	}

	public void setDataDefinitionFields(
		DataDefinitionField[] dataDefinitionFields) {

		this.dataDefinitionFields = dataDefinitionFields;
	}

	public void setDataDefinitionFields(
		UnsafeSupplier<DataDefinitionField[], Exception>
			dataDefinitionFieldsUnsafeSupplier) {

		try {
			dataDefinitionFields = dataDefinitionFieldsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected DataDefinitionField[] dataDefinitionFields;

	public DataDefinitionRule[] getDataDefinitionRules() {
		return dataDefinitionRules;
	}

	public void setDataDefinitionRules(
		DataDefinitionRule[] dataDefinitionRules) {

		this.dataDefinitionRules = dataDefinitionRules;
	}

	public void setDataDefinitionRules(
		UnsafeSupplier<DataDefinitionRule[], Exception>
			dataDefinitionRulesUnsafeSupplier) {

		try {
			dataDefinitionRules = dataDefinitionRulesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected DataDefinitionRule[] dataDefinitionRules;

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public void setDateCreated(
		UnsafeSupplier<Date, Exception> dateCreatedUnsafeSupplier) {

		try {
			dateCreated = dateCreatedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Date dateCreated;

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	public void setDateModified(
		UnsafeSupplier<Date, Exception> dateModifiedUnsafeSupplier) {

		try {
			dateModified = dateModifiedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Date dateModified;

	public LocalizedValue[] getDescription() {
		return description;
	}

	public void setDescription(LocalizedValue[] description) {
		this.description = description;
	}

	public void setDescription(
		UnsafeSupplier<LocalizedValue[], Exception> descriptionUnsafeSupplier) {

		try {
			description = descriptionUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected LocalizedValue[] description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setId(UnsafeSupplier<Long, Exception> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long id;

	public LocalizedValue[] getName() {
		return name;
	}

	public void setName(LocalizedValue[] name) {
		this.name = name;
	}

	public void setName(
		UnsafeSupplier<LocalizedValue[], Exception> nameUnsafeSupplier) {

		try {
			name = nameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected LocalizedValue[] name;

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public void setSiteId(
		UnsafeSupplier<Long, Exception> siteIdUnsafeSupplier) {

		try {
			siteId = siteIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long siteId;

	public String getStorageType() {
		return storageType;
	}

	public void setStorageType(String storageType) {
		this.storageType = storageType;
	}

	public void setStorageType(
		UnsafeSupplier<String, Exception> storageTypeUnsafeSupplier) {

		try {
			storageType = storageTypeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String storageType;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setUserId(
		UnsafeSupplier<Long, Exception> userIdUnsafeSupplier) {

		try {
			userId = userIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long userId;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DataDefinition)) {
			return false;
		}

		DataDefinition dataDefinition = (DataDefinition)object;

		return Objects.equals(toString(), dataDefinition.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"dataDefinitionFields\": ");

		if (dataDefinitionFields == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < dataDefinitionFields.length; i++) {
				sb.append(dataDefinitionFields[i]);

				if ((i + 1) < dataDefinitionFields.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"dataDefinitionRules\": ");

		if (dataDefinitionRules == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < dataDefinitionRules.length; i++) {
				sb.append(dataDefinitionRules[i]);

				if ((i + 1) < dataDefinitionRules.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"dateCreated\": ");

		if (dateCreated == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");
			sb.append(dateCreated);
			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"dateModified\": ");

		if (dateModified == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");
			sb.append(dateModified);
			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"description\": ");

		if (description == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < description.length; i++) {
				sb.append(description[i]);

				if ((i + 1) < description.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"id\": ");

		if (id == null) {
			sb.append("null");
		}
		else {
			sb.append(id);
		}

		sb.append(", ");

		sb.append("\"name\": ");

		if (name == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < name.length; i++) {
				sb.append(name[i]);

				if ((i + 1) < name.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"siteId\": ");

		if (siteId == null) {
			sb.append("null");
		}
		else {
			sb.append(siteId);
		}

		sb.append(", ");

		sb.append("\"storageType\": ");

		if (storageType == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");
			sb.append(storageType);
			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"userId\": ");

		if (userId == null) {
			sb.append("null");
		}
		else {
			sb.append(userId);
		}

		sb.append("}");

		return sb.toString();
	}

}