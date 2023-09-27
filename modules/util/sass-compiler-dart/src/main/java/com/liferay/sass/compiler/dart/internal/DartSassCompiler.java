/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sass.compiler.dart.internal;

import com.liferay.sass.compiler.SassCompiler;

import de.larsgrefer.sass.embedded.SassCompilerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import java.nio.file.Files;

import java.util.ArrayList;
import java.util.List;

import sass.embedded_protocol.EmbeddedSass;

/**
 * @author David Truong
 */
public class DartSassCompiler implements SassCompiler {

	public DartSassCompiler() {
		this(_PRECISION_DEFAULT);
	}

	public DartSassCompiler(int precision) {
		this(precision, System.getProperty("java.io.tmpdir"));
	}

	public DartSassCompiler(int precision, String tmpDirName) {
		_precision = precision;
		_tmpDirName = tmpDirName;
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

		try (de.larsgrefer.sass.embedded.SassCompiler sassCompiler =
				SassCompilerFactory.bundled()) {

			List<File> loadPaths = new ArrayList<>();

			for (String fileName : includeDirName.split(File.pathSeparator)) {
				File file = new File(fileName);

				loadPaths.add(file.getCanonicalFile());
			}

			File inputFile = new File(inputFileName);

			loadPaths.add(inputFile.getParentFile());

			sassCompiler.setOutputStyle(EmbeddedSass.OutputStyle.EXPANDED);
			sassCompiler.setLoadPaths(loadPaths);
			sassCompiler.setGenerateSourceMaps(generateSourceMap);

			EmbeddedSass.OutboundMessage.CompileResponse.CompileSuccess
				compileSuccess = sassCompiler.compileFile(inputFile);

			if (generateSourceMap) {
				try {
					_write(
						new File(sourceMapFileName),
						compileSuccess.getSourceMap());
				}
				catch (Exception exception) {
					System.out.println("Unable to create source map");
				}
			}

			return compileSuccess.getCss();
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
			throw new DartSassCompilerException(throwable);
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