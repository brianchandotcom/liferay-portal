/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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