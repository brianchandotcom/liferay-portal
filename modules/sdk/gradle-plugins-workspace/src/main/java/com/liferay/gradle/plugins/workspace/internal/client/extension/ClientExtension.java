/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.workspace.internal.client.extension;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;

import java.util.Collections;
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
		typeSettings.put(name, value);
	}

	public Map<String, Object> toJSONMap(String pid) {
		Map<String, Object> jsonMap = new HashMap<>();

		Map<String, Object> configMap = new HashMap<>();

		configMap.put(
			"baseURL",
			typeSettings.getOrDefault(
				"baseURL", "${portalURL}/o/" + projectName));
		configMap.put("description", description);
		configMap.put(
			"dxp.lxc.liferay.com.virtualInstanceId", virtualInstanceId);
		configMap.put("name", name);
		configMap.put("projectId", projectId);
		configMap.put("projectName", projectName);
		configMap.put("properties", _encode(properties));
		configMap.put("sourceCodeURL", sourceCodeURL);
		configMap.put("type", type);
		configMap.put(
			"webContextPath",
			typeSettings.getOrDefault("webContextPath", "/" + projectName));

		Set<Map.Entry<String, Object>> set = typeSettings.entrySet();

		set.forEach(
			entry -> {
				if (!pid.contains("CETConfiguration")) {
					configMap.put(entry.getKey(), entry.getValue());
				}
			});

		if (type.equals("oAuthApplicationHeadlessServer") ||
			type.equals("oAuthApplicationUserAgent")) {

			configMap.put(
				"homePageURL",
				typeSettings.getOrDefault(
					"homePageURL",
					"$[conf:.serviceScheme]://$[conf:.serviceAddress]"));
		}

		configMap.put("typeSettings", _encode(typeSettings));

		jsonMap.put(pid + "~" + id, configMap);

		return jsonMap;
	}

	public String classification = "static";
	public String description = "";
	public String id;
	public String name = "";
	public String projectId;
	public String projectName;
	public Map<String, Object> properties = Collections.emptyMap();
	public String sourceCodeURL = "";
	public String type;

	@JsonIgnore
	public Map<String, Object> typeSettings = new HashMap<>();

	@JsonProperty("dxp.lxc.liferay.com.virtualInstanceId")
	public String virtualInstanceId = "default";

	private List<String> _encode(Map<String, Object> map) {
		Set<Map.Entry<String, Object>> set = map.entrySet();

		Stream<Map.Entry<String, Object>> stream = set.stream();

		return stream.map(
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
	}

}