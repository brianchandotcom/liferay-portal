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

/**
 * @author Yasuyuki Takeo
 */
public class SupportedLanguage {

	public SupportedLanguage(
		String language, String name, Boolean supportsFormality) {

		_language = language;
		_name = name;
		_supportsFormality = supportsFormality;
	}

	public String getLanguage() {
		return _language;
	}

	public String getName() {
		return _name;
	}

	public Boolean getSupportsFormality() {
		return _supportsFormality;
	}

	private final String _language;
	private final String _name;
	private final Boolean _supportsFormality;

}