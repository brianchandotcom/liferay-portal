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

package com.liferay.sass.compiler.ruby;

import java.io.File;
import java.io.IOException;

import java.util.Arrays;
import java.util.List;

import jodd.io.ZipUtil;

import org.jruby.RubyInstanceConfig;
import org.jruby.RubyInstanceConfig.CompileMode;
import org.jruby.embed.LocalContextScope;
import org.jruby.embed.ScriptingContainer;
import org.jruby.embed.internal.LocalContextProvider;

/**
 * @author David Truong
 */
public class JRubyScriptingContainer {

	public JRubyScriptingContainer() {
		this(_COMPILE_MODE_JIT, _COMPILE_DEFAULT_THRESHOLD);
	}

	public JRubyScriptingContainer(
		String jrubyCompileMode, int jrubyCompilerThreshold) {

		initRubyGems();

		_scriptingContainer = new ScriptingContainer(
			LocalContextScope.THREADSAFE);

		LocalContextProvider localContextProvider =
			_scriptingContainer.getProvider();

		RubyInstanceConfig rubyInstanceConfig =
			localContextProvider.getRubyInstanceConfig();

		if (_COMPILE_MODE_FORCE.equals(jrubyCompileMode)) {
			rubyInstanceConfig.setCompileMode(CompileMode.FORCE);
		}
		else if (_COMPILE_MODE_JIT.equals(jrubyCompileMode)) {
			rubyInstanceConfig.setCompileMode(CompileMode.JIT);
		}

		rubyInstanceConfig.setLoadPaths(_loadPaths);
		rubyInstanceConfig.setJitThreshold(jrubyCompilerThreshold);
	}

	public ScriptingContainer getScriptingContainer() {
		return _scriptingContainer;
	}

	private void initRubyGems() {
		File rubyGemsJarFile = new File("lib/com.liferay.ruby.gems.jar");

		if (!rubyGemsJarFile.exists()) {
			System.err.println(rubyGemsJarFile + " does not exist");

			return;
		}

		File rubyDir = new File(_RUBY_LIB_PATH);

		if (rubyDir.lastModified() < rubyGemsJarFile.lastModified()) {
			if (rubyDir.exists()) {
				rubyDir.delete();
			}

			rubyDir.mkdirs();

			try {
				ZipUtil.unzip(rubyGemsJarFile, rubyDir);

				rubyDir.setLastModified(rubyGemsJarFile.lastModified());
			}
			catch (IOException ioe) {
				System.err.println(
					"Unable to unzip " + rubyGemsJarFile + " to " + rubyDir);

				ioe.printStackTrace();
			}
		}
	}

	private static final int _COMPILE_DEFAULT_THRESHOLD = 5;

	private static final String _COMPILE_MODE_FORCE = "force";

	private static final String _COMPILE_MODE_JIT = "jit";

	private static final String _LANGUAGE = "ruby";

	private static final String _RUBY_LIB_PATH =
		System.getProperty("java.io.tmpdir") + "/liferay/ruby";

	private final List<String> _loadPaths = Arrays.asList(
		_RUBY_LIB_PATH + "/gems/chunky_png-1.3.4/lib",
		_RUBY_LIB_PATH + "/gems/compass-1.0.1/lib",
		_RUBY_LIB_PATH + "/gems/compass-core-1.0.3/lib",
		_RUBY_LIB_PATH + "/gems/compass-import-once-1.0.5/lib",
		_RUBY_LIB_PATH + "/gems/ffi-1.9.6-java/lib",
		_RUBY_LIB_PATH + "/gems/multi_json-1.10.1/lib",
		_RUBY_LIB_PATH + "/gems/rb-fsevent-0.9.4/lib",
		_RUBY_LIB_PATH + "/gems/rb-inotify-0.9.5/lib",
		_RUBY_LIB_PATH + "/gems/sass-3.4.13/lib");
	private final ScriptingContainer _scriptingContainer;

}