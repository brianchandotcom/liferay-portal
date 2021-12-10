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

package com.liferay.portal.vulcan.list.type;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;

import java.util.Map;

/**
 * @author Javier de Arcos
 */
public class ListEntry {

	public String getKey() {
		return key;
	}

	public String getName() {
		return name;
	}

	public Map<String, String> getName_i18n() {
		return name_i18n;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setName_i18n(Map<String, String> name_i18n) {
		this.name_i18n = name_i18n;
	}

	@GraphQLField(
		description = "List entry's key. Independent from localization"
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String key;

	@GraphQLField(description = "Localized list entry's name")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String name;

	@GraphQLField(description = "The localized list entry's names.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Map<String, String> name_i18n;

}