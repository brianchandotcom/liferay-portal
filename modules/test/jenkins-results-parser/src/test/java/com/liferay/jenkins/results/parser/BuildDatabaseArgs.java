/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Calum Ragan
 */
public class BuildDatabaseArgs {

	public static Consumer withJobKey(String jobKey) {
		return buildDatabaseArgs -> buildDatabaseArgs.jobKey = jobKey;
	}

	public static Consumer withModifiedFiles(String... modifiedFiles) {
		return buildDatabaseArgs ->
			buildDatabaseArgs.modifiedFiles = modifiedFiles;
	}

	public static Consumer withProperty(String key, String name, String value) {
		return buildDatabaseArgs -> {
			Map<String, String> properties = buildDatabaseArgs.properties.get(
				key);

			if (properties == null) {
				properties = new LinkedHashMap<>();

				buildDatabaseArgs.properties.put(key, properties);
			}

			properties.put(name, value);
		};
	}

	public String jobKey = "PortalAcceptancePullRequestJob";
	public String[] modifiedFiles = {};
	public Map<String, Map<String, String>> properties = new LinkedHashMap<>();

	@FunctionalInterface
	public interface Consumer
		extends java.util.function.Consumer<BuildDatabaseArgs> {
	}

}