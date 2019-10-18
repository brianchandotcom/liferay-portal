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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import java.nio.file.Files;

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
		this(_PRECISION_DEFAULT);
	}

	public RubySassCompiler(int precision) throws Exception {
		this(
			_COMPILE_MODE_JIT, _COMPILE_THRESHOLD_DEFAULT, precision,
			System.getProperty("java.io.tmpdir"));
	}

	public RubySassCompiler(
			String compileMode, int compilerThreshold, int precision,
			String tmpDirName)
		throws Exception {

		_precision = precision;
		_tmpDirName = tmpDirName;

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

		List<String> loadPaths = new ArrayList<>();

		loadPaths.add("META-INF/jruby.home/lib/ruby/stdlib");
		loadPaths.add("gems/ffi-1.11.1-java/lib");
		loadPaths.add("gems/rb-fsevent-0.10.3/lib");
		loadPaths.add("gems/rb-inotify-0.10.0/lib");
		loadPaths.add("gems/sass-3.5.5/lib");
		loadPaths.add("gems/sass-listen-4.0.0/lib");

		rubyInstanceConfig.setLoadPaths(loadPaths);

		rubyInstanceConfig.setJitThreshold(compilerThreshold);

		String rubyScript = null;

		Class<?> clazz = getClass();

		try (InputStream inputStream = clazz.getResourceAsStream(
				"dependencies/main.rb")) {

			Scanner scanner = new Scanner(inputStream, "UTF-8");

			scanner.useDelimiter("\\A");

			rubyScript = scanner.next();
		}

		_scriptObject = _scriptingContainer.runScriptlet(rubyScript);
	}

	@Override
	public void close() {
		_scriptingContainer.terminate();
	}

	@Override
	public String compileFile(String inputFileName, String includeDirName)
		throws RubySassCompilerException {

		return compileFile(inputFileName, includeDirName, false, "");
	}

	@Override
	public String compileFile(
			String inputFileName, String includeDirName,
			boolean generateSourceMap)
		throws RubySassCompilerException {

		return compileFile(
			inputFileName, includeDirName, generateSourceMap, "");
	}

	@Override
	public String compileFile(
			String inputFileName, String includeDirName,
			boolean generateSourceMap, String sourceMapFileName)
		throws RubySassCompilerException {

		try {
			File inputFile = new File(inputFileName);

			String includeDirNames =
				includeDirName + File.pathSeparator + inputFile.getParent();

			String outputFileName = _getOutputFileName(inputFileName);

			if ((sourceMapFileName == null) || sourceMapFileName.equals("")) {
				sourceMapFileName = outputFileName + ".map";
			}

			String[] results = _scriptingContainer.callMethod(
				_scriptObject, "process",
				new Object[] {
					inputFileName, includeDirNames, _tmpDirName, false,
					outputFileName, _precision, generateSourceMap,
					sourceMapFileName
				},
				String[].class);

			if (generateSourceMap) {
				try {
					_write(new File(sourceMapFileName), results[1]);
				}
				catch (Exception e) {
					System.out.println("Unable to create source map");
				}
			}

			return results[0];
		}
		catch (Exception e) {
			throw new RubySassCompilerException(
				"Unable to parse " + inputFileName, e);
		}
	}

	@Override
	public String compileString(String input, String includeDirName)
		throws RubySassCompilerException {

		return compileString(input, "", includeDirName, false);
	}

	@Override
	public String compileString(
			String input, String inputFileName, String includeDirName,
			boolean generateSourceMap)
		throws RubySassCompilerException {

		return compileString(
			input, inputFileName, includeDirName, generateSourceMap, "");
	}

	@Override
	public String compileString(
			String input, String inputFileName, String includeDirName,
			boolean generateSourceMap, String sourceMapFileName)
		throws RubySassCompilerException {

		try {
			if ((inputFileName == null) || inputFileName.equals("")) {
				inputFileName = _tmpDirName + File.separator + "tmp.scss";

				if (generateSourceMap) {
					System.out.println("Source maps require a valid fileName");

					generateSourceMap = false;
				}
			}

			int index = inputFileName.lastIndexOf(File.separatorChar);

			if ((index == -1) && (File.separatorChar != '/')) {
				index = inputFileName.lastIndexOf('/');
			}

			index += 1;

			String path = inputFileName.substring(0, index);

			String fileName = inputFileName.substring(index);

			String outputFileName = _getOutputFileName(fileName);

			if ((sourceMapFileName == null) || sourceMapFileName.equals("")) {
				sourceMapFileName = path + outputFileName + ".map";
			}

			File tempFile = new File(path, "tmp.scss");

			tempFile.deleteOnExit();

			_write(tempFile, input);

			String output = compileFile(
				tempFile.getCanonicalPath(), includeDirName, generateSourceMap,
				sourceMapFileName);

			if (generateSourceMap) {
				File sourceMapFile = new File(sourceMapFileName);

				String sourceMapContent = new String(
					Files.readAllBytes(sourceMapFile.toPath()));

				sourceMapContent = sourceMapContent.replaceAll(
					"tmp\\.scss", fileName);
				sourceMapContent = sourceMapContent.replaceAll(
					"tmp\\.css", outputFileName);

				_write(sourceMapFile, sourceMapContent);
			}

			return output;
		}
		catch (Exception e) {
			throw new RubySassCompilerException(e);
		}
	}

	private String _getOutputFileName(String fileName) {
		return fileName.replaceAll("scss$", "css");
	}

	private void _write(File file, String string) throws IOException {
		if (!file.exists()) {
			File parentFile = file.getParentFile();

			parentFile.mkdirs();

			file.createNewFile();
		}

		try (Writer writer = new OutputStreamWriter(
				new FileOutputStream(file, false), "UTF-8")) {

			writer.write(string);
		}
	}

	private static final String _COMPILE_MODE_FORCE = "force";

	private static final String _COMPILE_MODE_JIT = "jit";

	private static final int _COMPILE_THRESHOLD_DEFAULT = 5;

	private static final int _PRECISION_DEFAULT = 5;

	private final int _precision;
	private final ScriptingContainer _scriptingContainer;
	private final Object _scriptObject;
	private final String _tmpDirName;

}