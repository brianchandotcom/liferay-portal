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
 */
public class JniSassCompiler implements SassCompiler {

	public static void main(String[] args) {
		try {
			new JniSassCompiler(args);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public JniSassCompiler() {
	}

	public JniSassCompiler(String[] fileNames) throws Exception {
		final JniSassCompiler sassCompiler = new JniSassCompiler();

		for (String fileName : fileNames) {
			File file = new File(fileName);

			if (!isValidFile(file)) {
				continue;
			}

			write(
				getOutputFile(file),
				sassCompiler.compileFile(fileName, "", false));
		}
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
			String includeDirNames =
				includeDirName + File.pathSeparator + new File(
					inputFileName).getParent();

			if ((sourceMapFileName == null) || sourceMapFileName.equals("")) {
				sourceMapFileName = getOutputFileName(inputFileName) + ".map";
			}

			sassFileContext = _createSassFileContext(
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

					String sourcemapOutput =
						_liferaysassLibrary.sass_context_get_source_map_string(
							sassContext);

					write(sourceMapFile, sourcemapOutput);
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
		boolean generateSourceMap) throws JniSassCompilerException {

		return compileString(
			input, inputFileName, includeDirName, generateSourceMap, "");
	}

	@Override
	public String compileString(
			String input, String inputFileName, String includeDirName,
			boolean generateSourceMap, String sourceMapFileName)
		throws JniSassCompilerException {

		try {
			File parentFile = new File(inputFileName).getParentFile();
			File tempFile = null;
			boolean modifySourceMap = false;

			if ((inputFileName == null) || inputFileName.equals("")) {
				tempFile = new File(_TMP_DIR, "tmp.scss");

				if (generateSourceMap) {
					System.out.println("Source maps require a fileName");

					generateSourceMap = false;
				}
			}
			else {
				modifySourceMap = true;

				tempFile = new File(parentFile.getCanonicalPath(), "tmp.scss");

				if ((sourceMapFileName == null) ||
					sourceMapFileName.equals("")) {

					String outputFileName = getOutputFileName(
						inputFileName.substring(
							inputFileName.lastIndexOf("/")));

					sourceMapFileName =
						parentFile.getPath() + outputFileName + ".map";
				}
			}

			tempFile.deleteOnExit();

			write(tempFile, input);

			String output = compileFile(
				tempFile.getCanonicalPath(), includeDirName, generateSourceMap,
				sourceMapFileName);

			if (modifySourceMap) {
				String fileName = inputFileName.substring(
					inputFileName.lastIndexOf("/") + 1);
				File sourceMapFile = new File(sourceMapFileName);
				String sourceMapContent = new String(
					Files.readAllBytes(sourceMapFile.toPath()));

				sourceMapContent = sourceMapContent.replaceAll(
					"tmp\\.scss", fileName);
				sourceMapContent = sourceMapContent.replaceAll(
					"tmp\\.css", getOutputFileName(fileName));

				write(sourceMapFile, sourceMapContent);
			}

			return output;
		}
		catch (Throwable t) {
			throw new JniSassCompilerException(t);
		}
	}

	private Sass_File_Context _createSassFileContext(
		String inputFileName, String includeDirNames, boolean generateSourceMap,
		String sourceMapFileName) {

		Sass_File_Context sassFileContext =
			_liferaysassLibrary.sass_make_file_context(inputFileName);

		// NONE((byte)0), DEFAULT((byte)1), MAP((byte)2);

		byte sourceComments = (byte)0;

		Sass_Options sassOptions = _liferaysassLibrary.sass_make_options();

		_liferaysassLibrary.sass_option_set_include_path(
			sassOptions, includeDirNames);
		_liferaysassLibrary.sass_option_set_input_path(
			sassOptions, inputFileName);
		_liferaysassLibrary.sass_option_set_output_path(sassOptions, "");
		_liferaysassLibrary.sass_option_set_output_style(
			sassOptions, Sass_Output_Style.SASS_STYLE_NESTED);
		_liferaysassLibrary.sass_option_set_source_comments(
			sassOptions, sourceComments);

		if (generateSourceMap) {
			_liferaysassLibrary.sass_option_set_source_map_file(
				sassOptions, sourceMapFileName);
			_liferaysassLibrary.sass_option_set_source_map_contents(
				sassOptions, (byte) 0);
			_liferaysassLibrary.sass_option_set_source_map_embed(
				sassOptions, (byte) 0);
			_liferaysassLibrary.sass_option_set_omit_source_map_url(
				sassOptions, (byte) 0);
		}

		_liferaysassLibrary.sass_file_context_set_options(
			sassFileContext, sassOptions);

		_liferaysassLibrary.sass_compile_file_context(sassFileContext);

		return sassFileContext;
	}

	private File getOutputFile(File file) {
		return new File(file.getParentFile(), getOutputFileName(file));
	}

	private String getOutputFileName(File file) {
		return getOutputFileName(file.getName());
	}

	private String getOutputFileName(String fileName) {
		return fileName.replaceAll("scss$", "css");
	}

	private boolean isValidFile(File file) {
		if (file == null) {
			return false;
		}

		if (!file.exists()) {
			return false;
		}

		String fileName = file.getName();

		return fileName.endsWith(".scss");
	}

	private void write(File file, String string) throws IOException {
		if (!file.exists()) {
			file.getParentFile().mkdirs();

			file.createNewFile();
		}

		try (Writer writer = new OutputStreamWriter(
				new FileOutputStream(file, false), "UTF-8")) {

			writer.write(string);
		}
	}

	private static final String _TMP_DIR = System.getProperty("java.io.tmpdir");

	private static final LiferaysassLibrary _liferaysassLibrary =
		LiferaysassLibrary.INSTANCE;

}