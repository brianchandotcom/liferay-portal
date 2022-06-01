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

package com.liferay.gradle.plugins.workspace.internal.client.extension;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.petra.string.StringBundler;

import java.io.StringWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Gregory Amerson
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientExtension {

	@JsonAnySetter
	public void ignored(String name, Object value) {
		_typeSettings.add(StringBundler.concat(name, "=", value));
	}

	public String toJSON() throws Exception {
		Map<String, Object> config = new HashMap<>();

		config.put("__timestamp", "${tstamp}");
		config.put("baseURL", _BASE_URL_PREFIX + projectName);
		config.put("description", description);
		config.put("name", name);
		config.put("sourceCodeUrl", sourceCodeUrl);
		config.put("type", type);
		config.put("typeSettings", _typeSettings);

		Map<String, Object> jsonMap = new HashMap<>();

		jsonMap.put(_CLIENT_EXTENSION_FACTORY_PREFIX + id, config);

		ObjectMapper objectMapper = new ObjectMapper();

		StringWriter stringWriter = new StringWriter();

		objectMapper.writeValue(stringWriter, jsonMap);

		return stringWriter.toString();
	}

	public String description;
	public String id;
	public String name;
	public String projectName;
	public String sourceCodeUrl;
	public String type;

	private static final String _BASE_URL_PREFIX = "${baseURL}/o/";

	private static final String _CLIENT_EXTENSION_FACTORY_PREFIX =
		"com.liferay.client.extension.type.configuration.CETConfiguration~";

	private final List<String> _typeSettings = new ArrayList<>();

}