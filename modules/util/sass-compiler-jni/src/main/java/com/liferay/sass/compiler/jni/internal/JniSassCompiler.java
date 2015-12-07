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

package com.liferay.sass.compiler.jni.internal;

import com.liferay.sass.compiler.SassCompiler;
import com.liferay.sass.compiler.jni.internal.libsass.LiferaysassLibrary;
import com.liferay.sass.compiler.jni.internal.libsass.LiferaysassLibrary.Sass_Context;
import com.liferay.sass.compiler.jni.internal.libsass.LiferaysassLibrary.Sass_File_Context;
import com.liferay.sass.compiler.jni.internal.libsass.LiferaysassLibrary.Sass_Options;
import com.liferay.sass.compiler.jni.internal.libsass.LiferaysassLibrary.Sass_Output_Style;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import java.nio.file.Files;

/**
 * @author Gregory Amerson
 * @author David Truong
 */
public class JniSassCompiler implements SassCompiler {

	public JniSassCompiler() {
		this(_PRECISION_DEFAULT);
	}

	public JniSassCompiler(int precision) {
		this(precision, System.getProperty("java.io.tmpdir"));
	}

	public JniSassCompiler(int precision, String tmpDirName) {
		_precision = precision;
		_tmpDirName = tmpDirName;
	}

	@Override
	public String compileFile(String inputFileName, String includeDirName)
		throws JniSassCompilerException {

		return compileFile(inputFileName, includeDirName, false, "");
	}

	@Override
	public String compileFile(
			String inputFileName, String includeDirName,
			boolean generateSourceMap)
		throws JniSassCompilerException {

		return compileFile(
			inputFileName, includeDirName, generateSourceMap, "");
	}

	@Override
	public String compileFile(
			String inputFileName, String includeDirName,
			boolean generateSourceMap, String sourceMapFileName)
		throws JniSassCompilerException {

		Sass_File_Context sassFileContext = null;

		try {
			File inputFile = new File(inputFileName);

			String includeDirNames =
				includeDirName + File.pathSeparator + inputFile.getParent();

			if ((sourceMapFileName == null) || sourceMapFileName.equals("")) {
				sourceMapFileName = getOutputFileName(inputFileName) + ".map";
			}

			sassFileContext = createSassFileContext(
				inputFileName, includeDirNames, generateSourceMap,
				sourceMapFileName);

			Sass_Context sassContext =
				_liferaysassLibrary.sass_file_context_get_context(
					sassFileContext);

			int errorStatus = _liferaysassLibrary.sass_context_get_error_status(
				sassContext);

			if (errorStatus != 0) {
				String errorMessage =
					_liferaysassLibrary.sass_context_get_error_message(
						sassContext);

				throw new JniSassCompilerException(errorMessage);
			}

			String output = _liferaysassLibrary.sass_context_get_output_string(
				sassContext);

			if (generateSourceMap) {
				try {
					File sourceMapFile = new File(sourceMapFileName);

					String sourceMapOutput =
						_liferaysassLibrary.sass_context_get_source_map_string(
							sassContext);

					write(sourceMapFile, sourceMapOutput);
				}
				catch (Exception e) {
					System.out.println("Unable to create source map");
				}
			}

			if (output == null) {
				throw new JniSassCompilerException("Null output");
			}

			return output;
		}
		finally {
			try {
				if (sassFileContext != null) {
					_liferaysassLibrary.sass_delete_file_context(
						sassFileContext);
				}
			}
			catch (Throwable t) {
				throw new JniSassCompilerException(t);
			}
		}
	}

	@Override
	public String compileString(String input, String includeDirName)
		throws JniSassCompilerException {

		return compileString(input, "", includeDirName, false);
	}

	@Override
	public String compileString(
			String input, String inputFileName, String includeDirName,
			boolean generateSourceMap)
		throws JniSassCompilerException {

		return compileString(
			input, inputFileName, includeDirName, generateSourceMap, "");
	}

	@Override
	public String compileString(
			String input, String inputFileName, String includeDirName,
			boolean generateSourceMap, String sourceMapFileName)
		throws JniSassCompilerException {

		try {
			if ((inputFileName == null) || inputFileName.equals("")) {
				inputFileName = _tmpDirName + "tmp.scss";

				if (generateSourceMap) {
					System.out.println("Source maps require a valid file name");

					generateSourceMap = false;
				}
			}

			int index = inputFileName.lastIndexOf("/") + 1;

			String dirName = inputFileName.substring(0, index);
			String fileName = inputFileName.substring(index);

			String outputFileName = getOutputFileName(fileName);

			if ((sourceMapFileName == null) || sourceMapFileName.equals("")) {
				sourceMapFileName = dirName + outputFileName + ".map";
			}

			File tempFile = new File(dirName, "tmp.scss");

			tempFile.deleteOnExit();

			write(tempFile, input);

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

				write(sourceMapFile, sourceMapContent);
			}

			return output;
		}
		catch (Throwable t) {
			throw new JniSassCompilerException(t);
		}
	}

	protected Sass_File_Context createSassFileContext(
		String inputFileName, String includeDirNames, boolean generateSourceMap,
		String sourceMapFileName) {

		Sass_File_Context sassFileContext =
			_liferaysassLibrary.sass_make_file_context(inputFileName);

		Sass_Options sassOptions = _liferaysassLibrary.sass_make_options();

		_liferaysassLibrary.sass_option_set_include_path(
			sassOptions, includeDirNames);
		_liferaysassLibrary.sass_option_set_input_path(
			sassOptions, inputFileName);
		_liferaysassLibrary.sass_option_set_output_path(sassOptions, "");
		_liferaysassLibrary.sass_option_set_output_style(
			sassOptions, Sass_Output_Style.SASS_STYLE_NESTED);
		_liferaysassLibrary.sass_option_set_precision(sassOptions, _precision);
		_liferaysassLibrary.sass_option_set_source_comments(
			sassOptions, (byte)0);

		if (generateSourceMap) {
			_liferaysassLibrary.sass_option_set_source_map_contents(
				sassOptions, (byte)0);
			_liferaysassLibrary.sass_option_set_source_map_embed(
				sassOptions, (byte)0);
			_liferaysassLibrary.sass_option_set_source_map_file(
				sassOptions, sourceMapFileName);
			_liferaysassLibrary.sass_option_set_omit_source_map_url(
				sassOptions, (byte)0);
		}

		_liferaysassLibrary.sass_file_context_set_options(
			sassFileContext, sassOptions);

		_liferaysassLibrary.sass_compile_file_context(sassFileContext);

		return sassFileContext;
	}

	protected String getOutputFileName(String fileName) {
		return fileName.replaceAll("scss$", "css");
	}

	protected void write(File file, String string) throws IOException {
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

	private static final LiferaysassLibrary _liferaysassLibrary =
		LiferaysassLibrary.INSTANCE;

	private final int _precision;
	private final String _tmpDirName;

}