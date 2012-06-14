/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.translator.model;

import com.liferay.portal.kernel.util.StringPool;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

/**
 * @author Brian Wing Shun Chan
 */
public class Translation implements Serializable {

	public Translation(
		String fromLanguage, String toLanguage, String fromText) {

		_fromLanguage = fromLanguage;
		_toLanguage = toLanguage;
		setFromText(fromText);
	}

	public Translation(
		String fromLanguage, String toLanguage, String fromText,
		String toText) {

		_fromLanguage = fromLanguage;
		_toLanguage = toLanguage;
		setFromText(fromText);
		setToText(toText);
	}

	public String getFromLanguage() {
		return _fromLanguage;
	}

	public String getFromText() {
		return _fromText;
	}

	public String getToLanguage() {
		return _toLanguage;
	}

	public String getToText() {
		return _toText;
	}

	public void setFromText(String fromText) {
		try {
			_fromText = new String(fromText.getBytes(), StringPool.UTF8);
		}
		catch (UnsupportedEncodingException uee) {
		}
	}

	public void setToText(String toText) {
		try {
			_toText = new String(toText.getBytes(), StringPool.UTF8);
		}
		catch (UnsupportedEncodingException uee) {
		}
	}

	private String _fromLanguage;
	private String _fromText;
	private String _toLanguage;
	private String _toText;

}