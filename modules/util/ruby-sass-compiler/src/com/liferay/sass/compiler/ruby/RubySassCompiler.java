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

import java.io.InputStream;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

import org.jruby.RubyArray;
import org.jruby.RubyException;
import org.jruby.RubyInstanceConfig;
import org.jruby.embed.LocalContextScope;
import org.jruby.embed.ScriptingContainer;
import org.jruby.embed.internal.LocalContextProvider;
import org.jruby.exceptions.RaiseException;
import org.jruby.runtime.builtin.IRubyObject;

/**
 * @author David Truong
 */
public class RubySassCompiler {

	public RubySassCompiler() throws Exception {
		this("", "", _COMPILE_MODE_JIT, _COMPILE_DEFAULT_THRESHOLD);
	}

	public RubySassCompiler(String docrootPath, String includePath)
		throws Exception {

		this(
			docrootPath, includePath, _COMPILE_MODE_JIT,
			_COMPILE_DEFAULT_THRESHOLD);
	}

	public RubySassCompiler(
			String docrootPath, String includePath, String compileMode,
			int compilerThreshold)
		throws Exception {

		_loadPaths = new ArrayList<>();

		_loadPaths.add("META-INF/jruby.home/lib/ruby/site_ruby/1.8");
		_loadPaths.add("META-INF/jruby.home/lib/ruby/site_ruby/shared");
		_loadPaths.add("META-INF/jruby.home/lib/ruby/1.8");
		_loadPaths.add("gems/chunky_png-1.3.4/lib");
		_loadPaths.add("gems/compass-1.0.1/lib");
		_loadPaths.add("gems/compass-core-1.0.3/lib");
		_loadPaths.add("gems/compass-import-once-1.0.5/lib");
		_loadPaths.add("gems/ffi-1.9.6-java/lib");
		_loadPaths.add("gems/multi_json-1.10.1/lib");
		_loadPaths.add("gems/rb-fsevent-0.9.4/lib");
		_loadPaths.add("gems/rb-inotify-0.9.5/lib");
		_loadPaths.add("gems/sass-3.4.13/lib");

		_scriptingContainer = new ScriptingContainer(
			LocalContextScope.THREADSAFE);

		LocalContextProvider localContextProvider =
			_scriptingContainer.getProvider();

		RubyInstanceConfig rubyInstanceConfig =
			localContextProvider.getRubyInstanceConfig();

		if (_COMPILE_MODE_FORCE.equals(compileMode)) {
			rubyInstanceConfig.setCompileMode(
				RubyInstanceConfig.CompileMode.FORCE);
		}
		else if (_COMPILE_MODE_JIT.equals(compileMode)) {
			rubyInstanceConfig.setCompileMode(
				RubyInstanceConfig.CompileMode.JIT);
		}

		rubyInstanceConfig.setLoadPaths(_loadPaths);
		rubyInstanceConfig.setJitThreshold(compilerThreshold);

		ClassLoader classLoader = getClass().getClassLoader();

		InputStream is = classLoader.getResourceAsStream(_SCRIPT_PATH);

		String rubyScript = IOUtils.toString(is);

		_scriptObject = _scriptingContainer.runScriptlet(rubyScript);

		_docrootPath = docrootPath;
		_includePath = includePath;
	}

	public String compileFile(String fileName)
		throws RubySassCompilerException {

		try {
			Path path = Paths.get(_docrootPath.concat(fileName));

			String input = new String(Files.readAllBytes(path));

			return compileString(input, fileName);
		}
		catch (Exception e) {
			System.err.println("Unable to parse " + fileName);
		}

		return null;
	}

	public String compileString(String input, String fileName)
		throws RubySassCompilerException {

		String filePath = _docrootPath.concat(fileName);

		String cssThemePath = filePath;

		int pos = filePath.lastIndexOf("/css/");

		if (pos >= 0) {
			cssThemePath = filePath.substring(0, pos + 4);
		}

		try {
			Object[] args = new Object[] {
				input, _includePath, filePath, cssThemePath, _TMP_DIR, false
			};

			return _scriptingContainer.callMethod(
				_scriptObject, "process", args, String.class);
		}
		catch (Exception e) {
			RaiseException raiseException = (RaiseException)e;

			RubyException rubyException = raiseException.getException();

			System.err.println(
				String.valueOf(rubyException.message.toJava(String.class)));

			IRubyObject iRubyObject = rubyException.getBacktrace();

			RubyArray rubyArray = (RubyArray)iRubyObject.toJava(
				RubyArray.class);

			for (int i = 0; i < rubyArray.size(); i++) {
				Object object = rubyArray.get(i);

				System.err.println(String.valueOf(object));
			}
		}

		return null;
	}

	private static final int _COMPILE_DEFAULT_THRESHOLD = 5;

	private static final String _COMPILE_MODE_FORCE = "force";

	private static final String _COMPILE_MODE_JIT = "jit";

	private static final String _RUBY_LIB_PATH =
		System.getProperty("java.io.tmpdir") + "/liferay/ruby";

	private static final String _SCRIPT_PATH =
		"com/liferay/sass/compiler/ruby/dependencies/main.rb";

	private static final String _TMP_DIR = System.getProperty("java.io.tmpdir");

	private final String _docrootPath;
	private final String _includePath;
	private final List<String> _loadPaths;
	private final ScriptingContainer _scriptingContainer;
	private final Object _scriptObject;

}