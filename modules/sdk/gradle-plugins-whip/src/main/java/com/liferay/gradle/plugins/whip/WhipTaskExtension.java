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

package com.liferay.gradle.plugins.whip;

import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.GradleUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gradle.api.Task;
import org.gradle.util.GUtil;

/**
 * @author Andrea Di Giorgi
 */
public class WhipTaskExtension {

	public WhipTaskExtension(Task task) {
		_task = task;
	}

	public WhipTaskExtension excludes(Iterable<Object> excludes) {
		GUtil.addToCollection(_excludes, excludes);

		return this;
	}

	public WhipTaskExtension excludes(Object ... excludes) {
		return excludes(Arrays.asList(excludes));
	}

	public File getDataFile() {
		return GradleUtil.toFile(_task.getProject(), _dataFile);
	}

	public List<String> getExcludes() {
		return GradleUtil.toStringList(_excludes);
	}

	public List<String> getIncludes() {
		return GradleUtil.toStringList(_includes);
	}

	public File getWhipJarFile() {
		return GradleUtil.toFile(_task.getProject(), _whipJarFile);
	}

	public WhipTaskExtension includes(Iterable<Object> includes) {
		GUtil.addToCollection(_includes, includes);

		return this;
	}

	public WhipTaskExtension includes(Object ... includes) {
		return includes(Arrays.asList(includes));
	}

	public boolean isEnabled() {
		return _enabled;
	}

	public boolean isInstrumentDump() {
		return _instrumentDump;
	}

	public void setDataFile(Object dataFile) {
		_dataFile = dataFile;
	}

	public void setEnabled(boolean enabled) {
		_enabled = enabled;
	}

	public void setExcludes(Iterable<Object> excludes) {
		_excludes.clear();

		excludes(excludes);
	}

	public void setIncludes(Iterable<Object> includes) {
		_includes.clear();

		includes(includes);
	}

	public void setInstrumentDump(boolean instrumentDump) {
		_instrumentDump = instrumentDump;
	}

	public void setWhipJarFile(Object whipJarFile) {
		_whipJarFile = whipJarFile;
	}

	protected Iterable<Object> getAsJvmArgs() {
		List<Object> jvmArgs = new ArrayList<>(1);

		jvmArgs.add(getWhipAgent());

		return jvmArgs;
	}

	protected Map<String, Object> getAsSystemProperties() {
		Map<String, Object> systemProperties = new HashMap<>();

		systemProperties.put("whip.agent", getWhipAgent());
		systemProperties.put(
			"whip.datafile", FileUtil.getAbsolutePath(getDataFile()));
		systemProperties.put("whip.instrument.dump", isInstrumentDump());

		return systemProperties;
	}

	protected String getWhipAgent() {
		StringBuilder sb = new StringBuilder();

		sb.append("-javaagent:");
		sb.append(FileUtil.getAbsolutePath(getWhipJarFile()));
		sb.append('=');

		List<String> includes = getIncludes();

		for (String include : includes) {
			sb.append(include);
			sb.append(',');
		}

		if (includes.size() > 0) {
			sb.setLength(sb.length() - 1);
		}

		sb.append(';');

		List<String> excludes = getExcludes();

		for (String exclude : excludes) {
			sb.append(exclude);
			sb.append(',');
		}

		if (excludes.size() > 0) {
			sb.setLength(sb.length() - 1);
		}

		return sb.toString();
	}

	private Object _dataFile;
	private boolean _enabled = true;
	private final List<Object> _excludes = new ArrayList<>();
	private final List<Object> _includes = new ArrayList<>();
	private boolean _instrumentDump;
	private final Task _task;
	private Object _whipJarFile;

}