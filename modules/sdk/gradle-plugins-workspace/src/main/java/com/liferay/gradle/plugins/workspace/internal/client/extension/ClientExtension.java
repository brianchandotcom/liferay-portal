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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Gregory Amerson
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientExtension {

	@JsonAnySetter
	public void ignored(String name, Object value) {
		_typeSettings.put(name, value);
	}

	public Map<String, Object> toJSONMap() throws Exception {
		Map<String, Object> config = new HashMap<>();

		config.put("baseURL", _BASE_URL_PREFIX + projectName);
		config.put("description", description);
		config.put("dxp.lxc.liferay.com.virtualInstanceId", "default");
		config.put("name", name);
		config.put("sourceCodeURL", sourceCodeURL);
		config.put("type", type);

		Properties clientExtensionTypePids = _getClientExtensionTypePids();

		String pid = clientExtensionTypePids.getProperty(type);

		if ((pid != null) &&
			(type.equals(_NOTIFICATION_TYPE_TYPE) ||
			 type.equals(_OAUTH_HEADLESS_SERVER_TYPE) ||
			 type.equals(_OAUTH_USER_AGENT_TYPE) ||
			 type.equals(_WORKFLOW_ACTION_TYPE)) &&
			(_typeSettings.get("homePageURL") == null)) {

			_typeSettings.put(
				"homePageURL",
				"https://$[conf:ext.lxc.liferay.com.mainDomain]");
		}

		Set<Map.Entry<String, Object>> set = _typeSettings.entrySet();

		Stream<Map.Entry<String, Object>> stream = set.stream();

		List<String> typeSettings = stream.peek(
			entry -> {
				if (!pid.contains("CETConfiguration")) {
					config.put(entry.getKey(), entry.getValue());
				}
			}
		).map(
			entry -> {
				Object value = entry.getValue();

				if (value instanceof List) {
					value = StringUtil.merge(
						(List<?>)value, StringPool.NEW_LINE);
				}

				return StringBundler.concat(entry.getKey(), "=", value);
			}
		).collect(
			Collectors.toList()
		);

		config.put("typeSettings", typeSettings);

		Map<String, Object> jsonMap = new HashMap<>();

		jsonMap.put(pid + "~" + id, config);

		return jsonMap;
	}

	public String description = "";
	public String id;
	public String name = "";
	public String projectName;
	public String sourceCodeURL = "";
	public String type;

	private Properties _getClientExtensionTypePids() throws Exception {
		Properties properties = new Properties();

		properties.load(
			ClientExtension.class.getResourceAsStream(
				"ClientExtensionTypePids.properties"));

		return properties;
	}

	private static final String _BASE_URL_PREFIX = "${portalURL}/o/";

	private static final String _NOTIFICATION_TYPE_TYPE = "notificationType";

	private static final String _OAUTH_HEADLESS_SERVER_TYPE =
		"oauthApplicationHeadlessServer";

	private static final String _OAUTH_USER_AGENT_TYPE =
		"oauthApplicationUserAgent";

	private static final String _WORKFLOW_ACTION_TYPE = "workflowAction";

	private final Map<String, Object> _typeSettings = new HashMap<>();

}