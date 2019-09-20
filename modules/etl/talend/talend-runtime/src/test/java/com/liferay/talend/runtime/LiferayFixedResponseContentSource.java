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

package com.liferay.talend.runtime;

import com.liferay.talend.BaseTest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.json.JsonObject;

/**
 * @author Igor Beslic
 */
public class LiferayFixedResponseContentSource extends LiferaySource {

	public LiferayFixedResponseContentSource() {
		_jsonObject = null;
	}

	public LiferayFixedResponseContentSource(JsonObject jsonObject) {
		_jsonObject = jsonObject;
	}

	@Override
	public JsonObject doGetRequest(String resourceURL) {
		if (_jsonObject != null) {
			return _jsonObject;
		}

		return _baseTest.readObject(_getResponseContentFileName(resourceURL));
	}

	private String _getResponseContentFileName(String resourceURL) {
		Matcher matcher = _resourceURLPattern.matcher(resourceURL);

		if (!matcher.matches()) {
			throw new UnsupportedOperationException(
				"Unable to extract file name from given resource URL");
		}

		String fileName = matcher.group(3);
		String fileNumber = matcher.group(4);

		StringBuilder sb = new StringBuilder();

		sb.append(fileName.replace("-", "_"));
		sb.append("_");
		sb.append(fileNumber.substring(fileNumber.indexOf("=") + 1));
		sb.append(".json");

		return sb.toString();
	}

	private final BaseTest _baseTest = new BaseTest();
	private final JsonObject _jsonObject;
	private Pattern _resourceURLPattern = Pattern.compile(
		"https?://.+(:\\d+)?/o/.+/v\\d+(.\\d+)*/([^/\\s]+)/.+\\?(page=\\d+)" +
			"&.+");

}