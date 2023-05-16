/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.defaults.task;

import com.liferay.gradle.plugins.defaults.internal.util.FileUtil;
import com.liferay.gradle.plugins.defaults.internal.util.GradleUtil;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.TaskAction;

/**
 * @author Andrea Di Giorgi
 */
public class WritePropertiesTask extends DefaultTask {

	@Internal
	public File getOutputFile() {
		return GradleUtil.toFile(getProject(), _outputFile);
	}

	@Input
	@Optional
	public Map<String, Object> getProperties() {
		Map<String, Object> properties = new TreeMap<>();

		for (KeyValuePair keyValuePair : _keyValuePairs) {
			String key = GradleUtil.toString(keyValuePair.key);

			properties.put(key, keyValuePair.value);
		}

		return properties;
	}

	public WritePropertiesTask properties(Map<String, ?> properties) {
		for (Map.Entry<String, ?> entry : properties.entrySet()) {
			_keyValuePairs.add(
				new KeyValuePair(entry.getKey(), entry.getValue()));
		}

		return this;
	}

	public WritePropertiesTask property(Object key, Object value) {
		_keyValuePairs.add(new KeyValuePair(key, value));

		return this;
	}

	public WritePropertiesTask property(String key, Object value) {
		return property((Object)key, value);
	}

	public void setOutputFile(Object outputFile) {
		_outputFile = outputFile;
	}

	public void setProperties(Map<String, ?> properties) {
		_keyValuePairs.clear();

		properties(properties);
	}

	@TaskAction
	public void writeProperties() throws IOException {
		FileUtil.writeProperties(getOutputFile(), getProperties());
	}

	private final List<KeyValuePair> _keyValuePairs = new ArrayList<>();
	private Object _outputFile;

	private static class KeyValuePair {

		public KeyValuePair(Object key, Object value) {
			this.key = key;
			this.value = value;
		}

		public final Object key;
		public final Object value;

	}

}