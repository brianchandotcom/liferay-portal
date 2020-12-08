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

	public static class Builder {

		public TalendArchive build() {
			return new TalendArchive(this);
		}

		public Builder classpathEntry(String classpathEntry) {
			_classpathEntries.add(classpathEntry);

			return this;
		}

		public Builder contextName(String contextName) {
			_contextName = contextName;

			return this;
		}

		public Builder jobDirectory(String jobDirectory) {
			_jobDirectory = jobDirectory;

			return this;
		}

		public Builder jobJARPath(String jobJARPath) {
			_jobJARPath = jobJARPath;

			return this;
		}

		public Builder jobMainClassFQN(String jobMainClassFQN) {
			_jobMainClassFQN = jobMainClassFQN;

			return this;
		}

		private String _buildClasspath() {
			StringBundler sb = new StringBundler(
				(_classpathEntries.size() * 2) + 1);

			for (String classpathEntry : _classpathEntries) {
				sb.append(classpathEntry);
				sb.append(File.pathSeparatorChar);
			}

			sb.append(_jobJARPath);

			return sb.toString();
		}

		private List<String> _classpathEntries = new ArrayList<>();
		private String _contextName;
		private String _jobDirectory;
		private String _jobJARPath;
		private String _jobMainClassFQN;

	}

	private TalendArchive(Builder builder) {
		_classpath = builder._buildClasspath();
		_classpathEntries = builder._classpathEntries;
		_contextName = builder._contextName;
		_jobDirectory = builder._jobDirectory;
		_jobJARPath = builder._jobJARPath;
		_jobMainClassFQN = builder._jobMainClassFQN;
	}

	private final String _classpath;
	private final List<String> _classpathEntries;
	private final String _contextName;
	private final String _jobDirectory;
	private final String _jobJARPath;
	private final String _jobMainClassFQN;

}