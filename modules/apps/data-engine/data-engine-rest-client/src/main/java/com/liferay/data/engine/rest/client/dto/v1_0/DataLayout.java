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
public class DataLayout {

	public Long getDataDefinitionId() {
		return dataDefinitionId;
	}

	public void setDataDefinitionId(Long dataDefinitionId) {
		this.dataDefinitionId = dataDefinitionId;
	}

	public void setDataDefinitionId(
		UnsafeSupplier<Long, Exception> dataDefinitionIdUnsafeSupplier) {

		try {
			dataDefinitionId = dataDefinitionIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long dataDefinitionId;

	public DataLayoutPage[] getDataLayoutPages() {
		return dataLayoutPages;
	}

	public void setDataLayoutPages(DataLayoutPage[] dataLayoutPages) {
		this.dataLayoutPages = dataLayoutPages;
	}

	public void setDataLayoutPages(
		UnsafeSupplier<DataLayoutPage[], Exception>
			dataLayoutPagesUnsafeSupplier) {

		try {
			dataLayoutPages = dataLayoutPagesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected DataLayoutPage[] dataLayoutPages;

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

	public String getDefaultLanguageId() {
		return defaultLanguageId;
	}

	public void setDefaultLanguageId(String defaultLanguageId) {
		this.defaultLanguageId = defaultLanguageId;
	}

	public void setDefaultLanguageId(
		UnsafeSupplier<String, Exception> defaultLanguageIdUnsafeSupplier) {

		try {
			defaultLanguageId = defaultLanguageIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String defaultLanguageId;

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

	public String getPaginationMode() {
		return paginationMode;
	}

	public void setPaginationMode(String paginationMode) {
		this.paginationMode = paginationMode;
	}

	public void setPaginationMode(
		UnsafeSupplier<String, Exception> paginationModeUnsafeSupplier) {

		try {
			paginationMode = paginationModeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String paginationMode;

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

		if (!(object instanceof DataLayout)) {
			return false;
		}

		DataLayout dataLayout = (DataLayout)object;

		return Objects.equals(toString(), dataLayout.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"dataDefinitionId\": ");

		if (dataDefinitionId == null) {
			sb.append("null");
		}
		else {
			sb.append(dataDefinitionId);
		}

		sb.append(", ");

		sb.append("\"dataLayoutPages\": ");

		if (dataLayoutPages == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < dataLayoutPages.length; i++) {
				sb.append(dataLayoutPages[i]);

				if ((i + 1) < dataLayoutPages.length) {
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

		sb.append("\"defaultLanguageId\": ");

		if (defaultLanguageId == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");
			sb.append(defaultLanguageId);
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

		sb.append("\"paginationMode\": ");

		if (paginationMode == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");
			sb.append(paginationMode);
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