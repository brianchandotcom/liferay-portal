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

package com.liferay.sass.compiler.jsass.internal;

import com.liferay.sass.compiler.SassCompiler;

import io.bit3.jsass.Compiler;
import io.bit3.jsass.Options;
import io.bit3.jsass.Output;
import io.bit3.jsass.OutputStyle;
import io.bit3.jsass.context.FileContext;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import java.nio.file.Files;

import java.util.ArrayList;
import java.util.List;

/**
 * @author David Truong
 */
public class JSassCompiler implements SassCompiler {

	public JSassCompiler() {
		this(_PRECISION_DEFAULT);
	}

	public JSassCompiler(int precision) {
		this(precision, System.getProperty("java.io.tmpdir"));
	}

	public JSassCompiler(int precision, String tmpDirName) {
		_precision = precision;
		_tmpDirName = tmpDirName;
	}

	@Override
	public String compileFile(String inputFileName, String includeDirName)
		throws JSassCompilerException {

		return compileFile(inputFileName, includeDirName, false, "");
	}

	@Override
	public String compileFile(
			String inputFileName, String includeDirName,
			boolean generateSourceMap)
		throws JSassCompilerException {

		return compileFile(
			inputFileName, includeDirName, generateSourceMap, "");
	}

	@Override
	public String compileFile(
			String inputFileName, String includeDirName,
			boolean generateSourceMap, String sourceMapFileName)
		throws JSassCompilerException {

		try {
			Options options = new Options();

			List<File> includeDirNames = new ArrayList<>();

			for (String fileName : includeDirName.split(File.pathSeparator)) {
				File file = new File(fileName);

				includeDirNames.add(file.getCanonicalFile());
			}

			File inputFile = new File(inputFileName);

			includeDirNames.add(inputFile.getParentFile());

			options.setIncludePaths(includeDirNames);
			options.setOutputStyle(OutputStyle.NESTED);
			options.setPrecision(_precision);
			options.setSourceComments(false);

			if (generateSourceMap) {
				if ((sourceMapFileName == null) ||
					sourceMapFileName.equals("")) {

					sourceMapFileName =
						_getOutputFileName(inputFileName) + ".map";
				}

				File sourceMapFile = new File(sourceMapFileName);

				options.setSourceMapContents(false);
				options.setSourceMapEmbed(false);
				options.setSourceMapFile(sourceMapFile.toURI());
				options.setOmitSourceMapUrl(false);
			}

			Compiler compiler = new Compiler();

			Output output = compiler.compile(
				new FileContext(inputFile.toURI(), null, options));

			if (output == null) {
				throw new JSassCompilerException("Null output");
			}

			if (generateSourceMap) {
				try {
					_write(new File(sourceMapFileName), output.getSourceMap());
				}
				catch (Exception exception) {
					System.out.println("Unable to create source map");
				}
			}

			return output.getCss();
		}
		catch (Exception exception) {
			throw new JSassCompilerException(exception);
		}
	}

	@Override
	public String compileString(String input, String includeDirName)
		throws JSassCompilerException {

		return compileString(input, "", includeDirName, false);
	}

	@Override
	public String compileString(
			String input, String inputFileName, String includeDirName,
			boolean generateSourceMap)
		throws JSassCompilerException {

		return compileString(
			input, inputFileName, includeDirName, generateSourceMap, "");
	}

	@Override
	public String compileString(
			String input, String inputFileName, String includeDirName,
			boolean generateSourceMap, String sourceMapFileName)
		throws JSassCompilerException {

		try {
			if ((inputFileName == null) || inputFileName.equals("")) {
				inputFileName = _tmpDirName + File.separator + "tmp.scss";

				if (generateSourceMap) {
					System.out.println("Source maps require a valid file name");

					generateSourceMap = false;
				}
			}

			int index = inputFileName.lastIndexOf(File.separatorChar);

			if ((index == -1) && (File.separatorChar != '/')) {
				index = inputFileName.lastIndexOf('/');
			}

			index += 1;

			String dirName = inputFileName.substring(0, index);

			String fileName = inputFileName.substring(index);

			String outputFileName = _getOutputFileName(fileName);

			if ((sourceMapFileName == null) || sourceMapFileName.equals("")) {
				sourceMapFileName = dirName + outputFileName + ".map";
			}

			File tempFile = new File(dirName, "tmp.scss");

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
		catch (Throwable throwable) {
			throw new JSassCompilerException(throwable);
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

	private static final int _PRECISION_DEFAULT = 5;

	private final int _precision;
	private final String _tmpDirName;

}