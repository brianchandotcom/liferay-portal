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
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
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

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setProperties(Map<String, Serializable> properties) {
		this.properties = properties;
	}

	@JsonAnySetter
	public void setProperties(String key, Serializable value) {
		properties.put(key, value);
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

	protected Map<String, Serializable> properties = new HashMap<>();

}