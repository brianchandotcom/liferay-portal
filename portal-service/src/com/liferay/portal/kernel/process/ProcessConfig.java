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

package com.liferay.portal.kernel.process;

import java.io.File;
import java.io.Serializable;

import java.util.Collections;
import java.util.List;

/**
 * @author Shuyang Zhou
 */
public class ProcessConfig implements Serializable {

	public List<String> getArguments() {
		return _arguments;
	}

	public String getBootstrapClassPath() {
		return sanitizePathSeparatorChar(_bootstrapClassPath);
	}

	public String getJavaExecutable() {
		return _javaExecutable;
	}

	public ClassLoader getReactClassLoader() {
		return _reactClassLoader;
	}

	public String getRuntimeClassPath() {
		return sanitizePathSeparatorChar(_runtimeClassPath);
	}

	public static class Builder {

		public ProcessConfig build() {
			return new ProcessConfig(this);
		}

		public Builder setArguments(List<String> arguments) {
			_arguments = arguments;

			return this;
		}

		public Builder setBootstrapClassPath(String bootstrapClassPath) {
			_bootstrapClassPath = bootstrapClassPath;

			return this;
		}

		public Builder setJavaExecutable(String javaExecutable) {
			_javaExecutable = javaExecutable;

			return this;
		}

		public Builder setReactClassLoader(ClassLoader reactClassLoader) {
			_reactClassLoader = reactClassLoader;

			return this;
		}

		public Builder setRuntimeClassPath(String runtimeClassPath) {
			_runtimeClassPath = runtimeClassPath;

			return this;
		}

		private List<String> _arguments = Collections.emptyList();
		private String _bootstrapClassPath = System.getProperty(
			"java.class.path");
		private String _javaExecutable = "java";
		private ClassLoader _reactClassLoader =
			ProcessConfig.class.getClassLoader();
		private String _runtimeClassPath = _bootstrapClassPath;

	}

	protected String sanitizePathSeparatorChar(String path) {
		if ((path != null) && (_pathSeparatorChar != File.pathSeparatorChar)) {
			return path.replace(_pathSeparatorChar, File.pathSeparatorChar);
		}

		return path;
	}

	private ProcessConfig(Builder builder) {
		_arguments = builder._arguments;
		_bootstrapClassPath = builder._bootstrapClassPath;
		_javaExecutable = builder._javaExecutable;
		_reactClassLoader = builder._reactClassLoader;
		_runtimeClassPath = builder._runtimeClassPath;
	}

	private static final long serialVersionUID = 1L;

	private final List<String> _arguments;
	private final String _bootstrapClassPath;
	private final String _javaExecutable;
	private final char _pathSeparatorChar = File.pathSeparatorChar;
	private final transient ClassLoader _reactClassLoader;
	private final String _runtimeClassPath;

}