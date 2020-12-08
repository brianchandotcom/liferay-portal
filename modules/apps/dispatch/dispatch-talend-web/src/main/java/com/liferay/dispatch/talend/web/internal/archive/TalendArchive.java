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

package com.liferay.dispatch.talend.web.internal.archive;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Igor Beslic
 */
public class TalendArchive {

	public String getClasspath() {
		return _classpath;
	}

	public List<String> getClasspathEntries() {
		return _classpathEntries;
	}

	public String getContextName() {
		return _contextName;
	}

	public String getJobDirectory() {
		return _jobDirectory;
	}

	public String getJobJARPath() {
		return _jobJARPath;
	}

	public String getJobMainClassFQN() {
		return _jobMainClassFQN;
	}

	public List<String> getJVMOptions() {
		return _jvmOptions;
	}

	public static class Builder {

		public TalendArchive build() {
			return new TalendArchive(this);
		}

		public Builder classpathEntry(String classpathEntry) {
			_classpathEntries.add(_stripJobDirectory(classpathEntry));

			return this;
		}

		public Builder contextName(String contextName) {
			_contextName = contextName;

			return this;
		}

		public Builder jobDirectory(String jobDirectory) {
			_jobDirectory = jobDirectory;

			_stripJobDirectory();

			return this;
		}

		public Builder jobJARPath(String jobJARPath) {
			_jobJARPath = _stripJobDirectory(jobJARPath);

			return this;
		}

		public Builder jobMainClassFQN(String jobMainClassFQN) {
			_jobMainClassFQN = jobMainClassFQN;

			return this;
		}

		public Builder jvmOption(String jvmOption) {
			_jvmOptions.add(jvmOption);

			return this;
		}

		private String _buildClasspath() {
			String parentDirectory = StringPool.PERIOD;

			if (Validator.isNotNull(_jobDirectory)) {
				parentDirectory = _jobDirectory;
			}

			StringBundler sb = new StringBundler(
				(_classpathEntries.size() * 3) + 4);

			sb.append(parentDirectory);
			sb.append(File.pathSeparatorChar);

			for (String classpathEntry : _classpathEntries) {
				sb.append(parentDirectory);
				sb.append(classpathEntry);
				sb.append(File.pathSeparatorChar);
			}

			sb.append(parentDirectory);
			sb.append(_jobJARPath);

			return sb.toString();
		}

		private void _stripJobDirectory() {
			List<String> classpathEntries = new ArrayList<>();

			for (String classpathEntry : _classpathEntries) {
				classpathEntries.add(_stripJobDirectory(classpathEntry));
			}

			if (classpathEntries.isEmpty()) {
				return;
			}

			_classpathEntries = classpathEntries;

			if (Validator.isNotNull(_jobJARPath)) {
				_jobJARPath = _stripJobDirectory(_jobJARPath);
			}
		}

		private String _stripJobDirectory(String directory) {
			if (Validator.isNull(_jobDirectory) ||
				!directory.startsWith(_jobDirectory)) {

				return directory;
			}

			return directory.substring(_jobDirectory.length());
		}

		private List<String> _classpathEntries = new ArrayList<>();
		private String _contextName;
		private String _jobDirectory;
		private String _jobJARPath;
		private String _jobMainClassFQN;
		private List<String> _jvmOptions = new ArrayList<>();

	}

	private TalendArchive(Builder builder) {
		_classpath = builder._buildClasspath();
		_classpathEntries = builder._classpathEntries;
		_contextName = builder._contextName;
		_jobDirectory = builder._jobDirectory;
		_jobJARPath = StringPool.PERIOD + builder._jobJARPath;
		_jobMainClassFQN = builder._jobMainClassFQN;
		_jvmOptions = builder._jvmOptions;
	}

	private final String _classpath;
	private final List<String> _classpathEntries;
	private final String _contextName;
	private final String _jobDirectory;
	private final String _jobJARPath;
	private final String _jobMainClassFQN;
	private final List<String> _jvmOptions;

}