/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.workspace.internal.client.extension;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.gradle.plugins.workspace.internal.util.StringUtil;
import com.liferay.release.util.ResourceUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.gradle.api.GradleException;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;

/**
 * @author Gregory Amerson
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientExtension {

	public String getClassification() {
		String classification = _clientExtensionProperties.getProperty(
			type + ".classification");

		if (classification != null) {
			return classification;
		}

		throw new GradleException(
			String.format(
				"Client extension %s with type %s is of unknown classification",
				id, type));
	}

	@JsonAnySetter
	public void ignored(String name, Object value) {
		typeSettings.put(name, value);
	}

	@JsonProperty("dxp.lxc.liferay.com.virtualInstanceId")
	public void setVirtualInstanceId(String virtualInstanceId) {
		this.virtualInstanceId = virtualInstanceId;

		if (_logger.isWarnEnabled()) {
			_logger.warn(
				StringUtil.concat(
					"The deprecated property ",
					"\"dxp.lxc.liferay.com.virtualInstanceId\" is set in ",
					"client-extension.yaml. Set the Gradle property ",
					"\"liferay.virtual.instance.id\" instead."));
		}
	}

	public Map<String, Object> toJSONMap(String virtualInstanceId) {
		Map<String, Object> jsonMap = new HashMap<>();

		Map<String, Object> typeSettings = new HashMap<>(this.typeSettings);

		String pid = _clientExtensionProperties.getProperty(type + ".pid");

		if (Objects.equals(type, "instanceSettings")) {
			pid = typeSettings.remove("pid") + ".scoped";
		}

		if (pid == null) {
			return jsonMap;
		}

		if (!StringUtil.isBlank(virtualInstanceId)) {
			if (!StringUtil.isBlank(this.virtualInstanceId) &&
				!Objects.equals(this.virtualInstanceId, virtualInstanceId)) {

				if (_logger.isWarnEnabled()) {
					String message = String.format(
						StringUtil.concat(
							"The client extension property value ",
							"\"dxp.lxc.liferay.com.virtualInstanceId\" ",
							"%s differs from the Gradle property ",
							"\"liferay.virtual.instance.id\" value %s. The ",
							"Gradle property will be used."),
						StringUtil.quote(this.virtualInstanceId),
						StringUtil.quote(virtualInstanceId));

					_logger.warn(message);
				}
			}

			this.virtualInstanceId = virtualInstanceId;
		}

		if (StringUtil.isBlank(this.virtualInstanceId)) {
			this.virtualInstanceId = "default";
		}

		Map<String, Object> configMap = new HashMap<>();

		configMap.put(":configurator:policy", "force");

		String pathSuffix = StringUtil.suffixIfNotBlank(
			projectName, virtualInstanceId);

		configMap.put(
			"baseURL",
			typeSettings.getOrDefault(
				"baseURL", "${portalURL}/o/" + pathSuffix));

		configMap.put("buildTimestamp", System.currentTimeMillis());
		configMap.put("description", description);
		configMap.put(
			"dxp.lxc.liferay.com.virtualInstanceId", this.virtualInstanceId);
		configMap.put("name", name);
		configMap.put("projectId", projectId);
		configMap.put("projectName", projectName);
		configMap.put("properties", _encode(properties));
		configMap.put("sourceCodeURL", sourceCodeURL);
		configMap.put("type", type);
		configMap.put(
			"webContextPath",
			typeSettings.getOrDefault("webContextPath", "/" + pathSuffix));

		if (!pid.contains("CETConfiguration")) {
			configMap.putAll(typeSettings);
		}

		if (type.equals("oAuthApplicationHeadlessServer") ||
			type.equals("oAuthApplicationUserAgent")) {

			configMap.put(
				"homePageURL",
				typeSettings.getOrDefault(
					"homePageURL",
					"$[conf:.serviceScheme]://$[conf:.serviceAddress]"));
		}

		configMap.put("typeSettings", _encode(typeSettings));

		String pidSuffix = StringUtil.suffixIfNotBlank(
			id, StringUtil.FORWARD_SLASH, virtualInstanceId);

		jsonMap.put(pid + "~" + pidSuffix, configMap);

		return jsonMap;
	}

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

	public String virtualInstanceId = "default";

	private List<String> _encode(Map<String, Object> map) {
		Set<Map.Entry<String, Object>> set = map.entrySet();

		Stream<Map.Entry<String, Object>> stream = set.stream();

		return stream.map(
			entry -> {
				Object value = entry.getValue();

				if (value instanceof List) {
					value = StringUtil.join(
						StringUtil.NEW_LINE, (List<?>)value);
				}

				return StringUtil.concat(entry.getKey(), "=", value);
			}
		).collect(
			Collectors.toList()
		);
	}

	private static final Properties _clientExtensionProperties =
		Objects.requireNonNull(
			ResourceUtil.readProperties(
				ResourceUtil.getClassLoaderResolver(
					ClientExtension.class, "client-extension.properties")),
			"Unable to read client-extension.properties file from class path");

	private final Logger _logger = Logging.getLogger(ClientExtension.class);

}