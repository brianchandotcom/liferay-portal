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

		String pid = _clientExtensionFactoryPids.get(type);

		if ((pid != null) &&
			(pid.equals(_OAUTH_HEADLESS_SERVER_PID) ||
			 pid.equals(_OAUTH_USER_AGENT_PID)) &&
			(_typeSettings.get("homePageURL") == null)) {

			_typeSettings.put(
				"homePageURL",
				"https://$[conf:ext.lxc.liferay.com.mainDomain]");
		}

		Set<Map.Entry<String, Object>> entrySet = _typeSettings.entrySet();

		Stream<Map.Entry<String, Object>> stream = entrySet.stream();

		List<String> typeSettings = stream.peek(
			entry -> {
				if (!pid.equals(_CET_CONFIGURATION_PID)) {
					config.put(entry.getKey(), entry.getValue());
				}
			}
		).map(
			entry -> {
				Object value = entry.getValue();

				if (value instanceof List) {
					List<?> listValue = (List<?>)value;

					value = StringUtil.merge(listValue, StringPool.NEW_LINE);
				}

				return StringBundler.concat(entry.getKey(), "=", value);
			}
		).collect(
			Collectors.toList()
		);

		config.put("typeSettings", typeSettings);

		Map<String, Object> jsonMap = new HashMap<>();

		jsonMap.put(_clientExtensionFactoryPids.get(type) + "~" + id, config);

		return jsonMap;
	}

	public String description = "";
	public String id;
	public String name = "";
	public String projectName;
	public String sourceCodeURL = "";
	public String type;

	private static final String _BASE_URL_PREFIX = "${portalURL}/o/";

	private static final String _CET_CONFIGURATION_PID =
		"com.liferay.client.extension.type.configuration.CETConfiguration";

	private static final String _FUNCTION_OBJECT_ACTION_PID =
		ClientExtension._OBJECT_INTERNAL_PREFIX +
			"FunctionObjectActionExecutorImplConfiguration";

	private static final String _OAUTH_HEADLESS_SERVER_PID =
		ClientExtension._OAUTH_PROVIDER_PREFIX + "HeadlessServerConfiguration";

	private static final String _OAUTH_PROVIDER_PREFIX =
		"com.liferay.oauth2.provider.configuration.OAuth2ProviderApplication";

	private static final String _OAUTH_USER_AGENT_PID =
		_OAUTH_PROVIDER_PREFIX + "UserAgentConfiguration";

	private static final String _OBJECT_INTERNAL_PREFIX =
		"com.liferay.object.internal.configuration.";

	private static final Map<String, String> _clientExtensionFactoryPids =
		new HashMap<>();
	{
		_clientExtensionFactoryPids.put(
			"customElement", _CET_CONFIGURATION_PID);
		_clientExtensionFactoryPids.put("globalCSS", _CET_CONFIGURATION_PID);
		_clientExtensionFactoryPids.put("globalJS", _CET_CONFIGURATION_PID);
		_clientExtensionFactoryPids.put("iframe", _CET_CONFIGURATION_PID);
		_clientExtensionFactoryPids.put(
			"oauthApplicationHeadlessServer", _OAUTH_HEADLESS_SERVER_PID);
		_clientExtensionFactoryPids.put(
			"oauthApplicationUserAgent", _OAUTH_USER_AGENT_PID);
		_clientExtensionFactoryPids.put(
			"objectAction", _FUNCTION_OBJECT_ACTION_PID);
		_clientExtensionFactoryPids.put("themeCSS", _CET_CONFIGURATION_PID);
		_clientExtensionFactoryPids.put("themeFavicon", _CET_CONFIGURATION_PID);
		_clientExtensionFactoryPids.put("themeJS", _CET_CONFIGURATION_PID);
	}

	private final Map<String, Object> _typeSettings = new HashMap<>();

}