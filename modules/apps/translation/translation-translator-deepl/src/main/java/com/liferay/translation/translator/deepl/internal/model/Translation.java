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
public class Translation {

	public Translation(String detectedSourceLanguageId, String text) {
		_detectedSourceLanguageId = detectedSourceLanguageId;
		_text = text;
	}

	public String getDetectedSourceLanguageId() {
		return _detectedSourceLanguageId;
	}

	public String getText() {
		return _text;
	}

	private final String _detectedSourceLanguageId;
	private final String _text;

}