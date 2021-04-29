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

package com.liferay.object.dto;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

import java.util.Date;
import java.util.Map;

import javax.validation.Valid;

/**
 * @author Javier de Arcos
 */
@JsonFilter("Liferay.Vulcan")
public class ObjectEntry {

	public static ObjectEntry toDTO(String json) {
		return ObjectMapperUtil.readValue(ObjectEntry.class, json);
	}

	@Schema(description = "The entry's creator.")
	public Creator getCreator() {
		return creator;
	}

	@Schema(description = "The entry's creation date.")
	public Date getDateCreated() {
		return dateCreated;
	}

	@Schema(description = "The last time any field of the entry was changed.")
	public Date getDateModified() {
		return dateModified;
	}

	@Schema(description = "The entry's ID.")
	public long getId() {
		return id;
	}

	@JsonAnyGetter
	public Map<String, Serializable> getProperties() {
		return properties;
	}

	public void setCreator(Creator creator) {
		this.creator = creator;
	}

	@JsonIgnore
	public void setCreator(
		UnsafeSupplier<Creator, Exception> creatorUnsafeSupplier) {

		try {
			creator = creatorUnsafeSupplier.get();
		}
		catch (RuntimeException runtimeException) {
			throw runtimeException;
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	@JsonIgnore
	public void setDateCreated(
		UnsafeSupplier<Date, Exception> dateCreatedUnsafeSupplier) {

		try {
			dateCreated = dateCreatedUnsafeSupplier.get();
		}
		catch (RuntimeException runtimeException) {
			throw runtimeException;
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	@JsonIgnore
	public void setDateModified(
		UnsafeSupplier<Date, Exception> dateModifiedUnsafeSupplier) {

		try {
			dateCreated = dateModifiedUnsafeSupplier.get();
		}
		catch (RuntimeException runtimeException) {
			throw runtimeException;
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	public void setId(long id) {
		this.id = id;
	}

	@JsonIgnore
	public void setId(UnsafeSupplier<Long, Exception> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
		}
		catch (RuntimeException runtimeException) {
			throw runtimeException;
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	public void setProperties(Map<String, Serializable> properties) {
		this.properties = properties;
	}

	@JsonIgnore
	public void setProperties(
		UnsafeSupplier<Map<String, Serializable>, Exception>
			propertiesUnsafeSupplier) {

		try {
			properties = propertiesUnsafeSupplier.get();
		}
		catch (RuntimeException runtimeException) {
			throw runtimeException;
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	@Valid
	protected Creator creator;

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Date dateCreated;

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Date dateModified;

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected long id;

	protected Map<String, Serializable> properties;

}