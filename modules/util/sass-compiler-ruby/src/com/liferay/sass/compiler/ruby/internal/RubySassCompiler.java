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

package com.liferay.sass.compiler.ruby.internal;

import com.liferay.sass.compiler.SassCompiler;

import java.io.InputStream;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.jruby.RubyInstanceConfig;
import org.jruby.embed.LocalContextScope;
import org.jruby.embed.ScriptingContainer;
import org.jruby.embed.internal.LocalContextProvider;

/**
 * @author David Truong
 */
public class RubySassCompiler implements AutoCloseable, SassCompiler {

	public RubySassCompiler() throws Exception {
		this(COMPILE_MODE_JIT, COMPILE_DEFAULT_THRESHOLD,
			System.getProperty("java.io.tmpdir"));
	}

	public RubySassCompiler(
			String compileMode, int compilerThreshold, String tmpDir)
		throws Exception {

		_tmpDir = tmpDir;

		_scriptingContainer = new ScriptingContainer(
			LocalContextScope.THREADSAFE);

		LocalContextProvider localContextProvider =
			_scriptingContainer.getProvider();

		RubyInstanceConfig rubyInstanceConfig =
			localContextProvider.getRubyInstanceConfig();

		if (COMPILE_MODE_FORCE.equals(compileMode)) {
			rubyInstanceConfig.setCompileMode(
				RubyInstanceConfig.CompileMode.FORCE);
		}
		else if (COMPILE_MODE_JIT.equals(compileMode)) {
			rubyInstanceConfig.setCompileMode(
				RubyInstanceConfig.CompileMode.JIT);
		}

		List<String> loadPaths = new ArrayList<>();

		loadPaths.add("META-INF/jruby.home/lib/ruby/site_ruby/1.8");
		loadPaths.add("META-INF/jruby.home/lib/ruby/site_ruby/shared");
		loadPaths.add("META-INF/jruby.home/lib/ruby/1.8");
		loadPaths.add("gems/chunky_png-1.3.4/lib");
		loadPaths.add("gems/compass-1.0.1/lib");
		loadPaths.add("gems/compass-core-1.0.3/lib");
		loadPaths.add("gems/compass-import-once-1.0.5/lib");
		loadPaths.add("gems/ffi-1.9.6-java/lib");
		loadPaths.add("gems/multi_json-1.10.1/lib");
		loadPaths.add("gems/rb-fsevent-0.9.4/lib");
		loadPaths.add("gems/rb-inotify-0.9.5/lib");
		loadPaths.add("gems/sass-3.4.13/lib");

		rubyInstanceConfig.setLoadPaths(loadPaths);

		rubyInstanceConfig.setJitThreshold(compilerThreshold);

		String rubyScript = null;

		Class<?> clazz = getClass();

		try (InputStream inputStream =
				clazz.getResourceAsStream("dependencies/main.rb")) {

			Scanner scanner = new Scanner(inputStream, "UTF-8");

			scanner.useDelimiter("\\A");

			rubyScript = scanner.next();
		}

		_scriptObject = _scriptingContainer.runScriptlet(rubyScript);
	}

	@Override
	public void close() throws Exception {
		_scriptingContainer.terminate();
	}

	@Override
	public String compileFile(
			String inputFileName, String includeDirName, String imgDirName)
		throws RubySassCompilerException {

		try {
			Path path = Paths.get(inputFileName);

			String input = new String(Files.readAllBytes(path));

			return compileString(input, includeDirName, imgDirName);
		}
		catch (Exception e) {
			throw new RubySassCompilerException(
				"Unable to parse " + inputFileName);
		}
	}

	@Override
	public String compileString(
			String input, String includeDirName, String imgDirName)
		throws RubySassCompilerException {

		try {
			return _scriptingContainer.callMethod(
				_scriptObject, "process",
				new Object[] {input, includeDirName, _tmpDir, false},
				String.class);
		}
		catch (Exception e) {
			throw new RubySassCompilerException(e);
		}
	}

	public static final int COMPILE_DEFAULT_THRESHOLD = 5;

	public static final String COMPILE_MODE_FORCE = "force";

	public static final String COMPILE_MODE_JIT = "jit";

	private final ScriptingContainer _scriptingContainer;
	private final Object _scriptObject;
	private final String _tmpDir;

}