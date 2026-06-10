/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Calum Ragan
 */
public class BuildDatabaseArgs {

	public void addModifiedFile(String modifiedFile) {
		modifiedFiles.add(modifiedFile);
	}

	public void setProperty(String key, String name, String value) {
		Map<String, String> values = properties.get(key);

		if (values == null) {
			values = new LinkedHashMap<>();

			properties.put(key, values);
		}

		values.put(name, value);
	}

	public String jobKey = "PortalAcceptancePullRequestJob";
	public List<String> modifiedFiles = new ArrayList<>();
	public Map<String, Map<String, String>> properties = new LinkedHashMap<>();

	@FunctionalInterface
	public interface Consumer
		extends java.util.function.Consumer<BuildDatabaseArgs> {
	}

}