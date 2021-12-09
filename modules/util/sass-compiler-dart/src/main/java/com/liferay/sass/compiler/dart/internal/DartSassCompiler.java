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

package com.liferay.sass.compiler.dart.internal;

import com.liferay.sass.compiler.SassCompiler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Cleydyr Albuquerque
 */
public class DartSassCompiler implements SassCompiler {

	public DartSassCompiler() {
		this(System.getProperty("java.io.tmpdir"));
	}

	public DartSassCompiler(String tmpDirName) {
		_tmpDirName = tmpDirName;
	}

	@Override
	public void close() {
	}

	@Override
	public String compileFile(String inputFileName, String includeDirName)
		throws DartSassCompilerException {

		return compileFile(inputFileName, includeDirName, false, "");
	}

	@Override
	public String compileFile(
			String inputFileName, String includeDirName,
			boolean generateSourceMap)
		throws DartSassCompilerException {

		return compileFile(
			inputFileName, includeDirName, generateSourceMap, "");
	}

	@Override
	public String compileFile(
			String inputFileName, String includeDirName,
			boolean generateSourceMap, String sourceMapFileName)
		throws DartSassCompilerException {

		try {
			List<String> includeDirPaths = new ArrayList<>();

			if ((inputFileName != null) && !inputFileName.equals("")) {
				File inputFile = new File(inputFileName);

				includeDirPaths.add(inputFile.getParent());
			}

			if ((includeDirName != null) && !includeDirName.equals("")) {
				for (String includeDirPath :
						includeDirName.split(File.pathSeparator)) {

					includeDirPaths.add(includeDirPath);
				}
			}

			String outputFileName = _getOutputFileName(inputFileName);

			if ((sourceMapFileName == null) || sourceMapFileName.equals("")) {
				sourceMapFileName = outputFileName + ".map";
			}

			List<String> commands = new ArrayList<>();

			commands.add("sass");

			for (String includeDirPath : includeDirPaths) {
				commands.add("--load-path");

				commands.add(includeDirPath);
			}

			if (!generateSourceMap) {
				commands.add("--no-source-map");
			}

			commands.add(inputFileName);

			commands.add(outputFileName);

			ProcessBuilder processBuilder = new ProcessBuilder(commands);

			Process process = processBuilder.start();

			process.waitFor();

			File outputFile = new File(outputFileName);

			FileInputStream fileInputStream = new FileInputStream(outputFile);

			return _inputStreamToString(fileInputStream);
		}
		catch (Exception exception) {
			throw new DartSassCompilerException(exception);
		}
	}

	@Override
	public String compileString(String input, String includeDirName)
		throws DartSassCompilerException {

		return compileString(input, "", includeDirName, false);
	}

	@Override
	public String compileString(
			String input, String inputFileName, String includeDirName,
			boolean generateSourceMap)
		throws DartSassCompilerException {

		return compileString(
			input, inputFileName, includeDirName, generateSourceMap, "");
	}

	@Override
	public String compileString(
			String input, String inputFileName, String includeDirName,
			boolean generateSourceMap, String sourceMapFileName)
		throws DartSassCompilerException {

		try {
			if ((inputFileName == null) || inputFileName.equals("")) {
				inputFileName = _tmpDirName + File.separator + "tmp.scss";

				if (generateSourceMap) {
					System.out.println("Source maps require a valid fileName");

					generateSourceMap = false;
				}

				int index = inputFileName.lastIndexOf(File.separatorChar);

				if ((index == -1) && (File.separatorChar != '/')) {
					index = inputFileName.lastIndexOf('/');
				}

				index += 1;

				String path = inputFileName.substring(0, index);

				File tempFile = new File(path, "tmp.scss");

				tempFile.deleteOnExit();

				_write(tempFile, input);
			}

			return compileFile(
				inputFileName, includeDirName, generateSourceMap,
				sourceMapFileName);
		}
		catch (Exception exception) {
			throw new DartSassCompilerException(exception);
		}
	}

	private String _getOutputFileName(String fileName) {
		return fileName.replaceAll("scss$", "css");
	}

	private String _inputStreamToString(InputStream inputStream) {
		InputStreamReader inputStreamReader = new InputStreamReader(
			inputStream, StandardCharsets.UTF_8);

		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

		Stream<String> lines = bufferedReader.lines();

		return lines.collect(Collectors.joining("\n"));
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

	private String _tmpDirName;

}