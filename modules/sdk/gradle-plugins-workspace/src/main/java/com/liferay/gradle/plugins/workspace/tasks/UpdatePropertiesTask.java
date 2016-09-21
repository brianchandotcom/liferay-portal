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

package com.liferay.gradle.plugins.workspace.tasks;

import com.liferay.gradle.plugins.workspace.internal.util.GradleUtil;
import com.liferay.gradle.util.FileUtil;

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
			String key = entry.getKey();
			String value = GradleUtil.toString(entry.getValue());

			properties.setProperty(key, value);
		}

		try (OutputStream outputStream = Files.newOutputStream(
				propertiesFile.toPath())) {

			properties.store(outputStream, null);
		}
	}

	private final Map<String, Object> _properties = new HashMap<>();
	private Object _propertiesFile;

}