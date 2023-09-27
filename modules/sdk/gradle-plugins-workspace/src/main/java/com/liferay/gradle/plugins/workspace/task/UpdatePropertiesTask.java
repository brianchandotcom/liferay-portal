/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.workspace.task;

import com.liferay.gradle.plugins.workspace.internal.util.FileUtil;
import com.liferay.gradle.plugins.workspace.internal.util.GradleUtil;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import java.nio.file.Files;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

/**
 * @author Andrea Di Giorgi
 */
public class UpdatePropertiesTask extends DefaultTask {

	@Input
	public Map<String, Object> getProperties() {
		return _properties;
	}

	@Input
	public File getPropertiesFile() {
		return GradleUtil.toFile(getProject(), _propertiesFile);
	}

	public UpdatePropertiesTask properties(Map<String, Object> properties) {
		_properties.putAll(properties);

		return this;
	}

	public UpdatePropertiesTask property(String key, Object value) {
		return properties(Collections.singletonMap(key, value));
	}

	public void setProperties(Map<String, Object> properties) {
		_properties.clear();

		properties(properties);
	}

	public void setPropertiesFile(Object propertiesFile) {
		_propertiesFile = propertiesFile;
	}

	@TaskAction
	public void updateProperties() throws IOException {
		File propertiesFile = getPropertiesFile();

		Properties properties = FileUtil.readProperties(propertiesFile);

		for (Map.Entry<String, Object> entry : _properties.entrySet()) {
			String value = GradleUtil.toString(entry.getValue());

			properties.setProperty(entry.getKey(), value);
		}

		try (OutputStream outputStream = Files.newOutputStream(
				propertiesFile.toPath())) {

			properties.store(outputStream, null);
		}
	}

	private final Map<String, Object> _properties = new HashMap<>();
	private Object _propertiesFile;

}