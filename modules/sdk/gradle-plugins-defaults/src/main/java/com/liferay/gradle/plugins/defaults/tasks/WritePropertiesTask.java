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

package com.liferay.gradle.plugins.defaults.tasks;

import com.liferay.gradle.plugins.defaults.internal.util.FileUtil;
import com.liferay.gradle.plugins.defaults.internal.util.GradleUtil;

import java.io.File;
import java.io.IOException;

import java.util.Map;
import java.util.TreeMap;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.SkipWhenEmpty;
import org.gradle.api.tasks.TaskAction;

/**
 * @author Andrea Di Giorgi
 */
public class WritePropertiesTask extends DefaultTask {

	@Input
	public File getOutputFile() {
		return GradleUtil.toFile(getProject(), _outputFile);
	}

	@Input
	@SkipWhenEmpty
	public Map<String, Object> getProperties() {
		return _properties;
	}

	public WritePropertiesTask properties(Map<String, ?> properties) {
		_properties.putAll(properties);

		return this;
	}

	public WritePropertiesTask property(String key, Object value) {
		_properties.put(key, value);

		return this;
	}

	public void setOutputFile(Object outputFile) {
		_outputFile = outputFile;
	}

	public void setProperties(Map<String, ?> properties) {
		_properties.clear();

		properties(properties);
	}

	@TaskAction
	public void writeProperties() throws IOException {
		FileUtil.writeProperties(getOutputFile(), getProperties());
	}

	private Object _outputFile;
	private final Map<String, Object> _properties = new TreeMap<>();

}