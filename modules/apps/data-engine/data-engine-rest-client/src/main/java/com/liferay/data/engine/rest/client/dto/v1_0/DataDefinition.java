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
import com.liferay.data.engine.rest.client.serdes.v1_0.DataDefinitionSerDes;

import java.util.Date;
import java.util.Map;
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

	public String getDataDefinitionKey() {
		return dataDefinitionKey;
	}

	public void setDataDefinitionKey(String dataDefinitionKey) {
		this.dataDefinitionKey = dataDefinitionKey;
	}

	public void setDataDefinitionKey(
		UnsafeSupplier<String, Exception> dataDefinitionKeyUnsafeSupplier) {

		try {
			dataDefinitionKey = dataDefinitionKeyUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String dataDefinitionKey;

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

	public Map<String, Object> getDescription() {
		return description;
	}

	public void setDescription(Map<String, Object> description) {
		this.description = description;
	}

	public void setDescription(
		UnsafeSupplier<Map<String, Object>, Exception>
			descriptionUnsafeSupplier) {

		try {
			description = descriptionUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, Object> description;

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

	public Map<String, Object> getName() {
		return name;
	}

	public void setName(Map<String, Object> name) {
		this.name = name;
	}

	public void setName(
		UnsafeSupplier<Map<String, Object>, Exception> nameUnsafeSupplier) {

		try {
			name = nameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, Object> name;

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
		return DataDefinitionSerDes.toJSON(this);
	}

}