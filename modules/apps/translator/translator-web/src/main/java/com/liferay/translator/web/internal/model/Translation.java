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

package com.liferay.translator.web.internal.model;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringPool;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

/**
 * @author Brian Wing Shun Chan
 */
public class Translation implements Serializable {

	public Translation(
		String fromLanguageId, String toLanguageId, String fromText) {

		_fromLanguageId = fromLanguageId;
		_toLanguageId = toLanguageId;

		setFromText(fromText);
	}

	public Translation(
		String fromLanguageId, String toLanguageId, String fromText,
		String toText) {

		_fromLanguageId = fromLanguageId;
		_toLanguageId = toLanguageId;

		setFromText(fromText);
		setToText(toText);
	}

	public String getFromLanguageId() {
		return _fromLanguageId;
	}

	public String getFromText() {
		return _fromText;
	}

	public String getToLanguageId() {
		return _toLanguageId;
	}

	public String getToText() {
		return _toText;
	}

	public void setFromText(String fromText) {
		try {
			_fromText = new String(fromText.getBytes(), StringPool.UTF8);
		}
		catch (UnsupportedEncodingException uee) {
			ReflectionUtil.throwException(uee);
		}
	}

	public void setToText(String toText) {
		try {
			_toText = new String(toText.getBytes(), StringPool.UTF8);
		}
		catch (UnsupportedEncodingException uee) {
			ReflectionUtil.throwException(uee);
		}
	}

	private final String _fromLanguageId;
	private String _fromText;
	private final String _toLanguageId;
	private String _toText;

}