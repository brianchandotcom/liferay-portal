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
		_modifiedFiles.add(modifiedFile);
	}

	public List<String> getModifiedFiles() {
		return _modifiedFiles;
	}

	public Map<String, Map<String, String>> getProperties() {
		return _properties;
	}

	public void setProperty(String key, String name, String value) {
		Map<String, String> properties = _properties.get(key);

		if (properties == null) {
			properties = new LinkedHashMap<>();

			_properties.put(key, properties);
		}

		properties.put(name, value);
	}

	public String jobKey = "PortalAcceptancePullRequestJob";

	@FunctionalInterface
	public interface Consumer
		extends java.util.function.Consumer<BuildDatabaseArgs> {
	}

	private final List<String> _modifiedFiles = new ArrayList<>();
	private final Map<String, Map<String, String>> _properties =
		new LinkedHashMap<>();

}
