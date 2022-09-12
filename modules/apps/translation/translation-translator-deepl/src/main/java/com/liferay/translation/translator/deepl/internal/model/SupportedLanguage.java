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

package com.liferay.translation.translator.deepl.internal.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Yasuyuki Takeo
 */
public class SupportedLanguage {

	@JsonProperty("language")
	public String getLanguage() {
		return _language;
	}

	@JsonProperty("name")
	public String getName() {
		return _name;
	}

	@JsonProperty("supports_formality")
	public Boolean getSupportsFormality() {
		return _supportsFormality;
	}

	public void setLanguage(String language) {
		_language = language;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setSupportsFormality(Boolean supportsFormality) {
		_supportsFormality = supportsFormality;
	}

	private String _language;
	private String _name;
	private Boolean _supportsFormality;

}