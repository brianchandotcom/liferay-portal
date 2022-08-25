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
public class Translation {

	@JsonProperty("detected_source_language")
	public String getDetectedSourceLanguage() {
		return _detectedSourceLanguage;
	}

	@JsonProperty("text")
	public String getText() {
		return _text;
	}

	public void setDetectedSourceLanguage(String detectedSourceLanguage) {
		_detectedSourceLanguage = detectedSourceLanguage;
	}

	public void setText(String text) {
		_text = text;
	}

	private String _detectedSourceLanguage;
	private String _text;

}