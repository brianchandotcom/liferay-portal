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

import com.sun.jna.Memory;
import com.sun.jna.Native;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

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
				sassCompiler.compileFile(fileName, "", ""));
		}
	}

	@Override
	public String compileFile(
			String inputFileName, String includeDirName, String imgDirName)
		throws JniSassCompilerException {

		// NONE((byte)0), DEFAULT((byte)1), MAP((byte)2);

		byte sourceComments = (byte)0;

		String includeDirNames = includeDirName + File.pathSeparator + new File(
			inputFileName).getParent();

		Sass_File_Context sassFileContext = null;

		try {
			sassFileContext = _liferaysassLibrary.sass_make_file_context(
				inputFileName);

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

			_liferaysassLibrary.sass_file_context_set_options(
				sassFileContext, sassOptions);

			_liferaysassLibrary.sass_compile_file_context(sassFileContext);

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
	public String compileString(
			String input, String includeDirName, String imgDirName)
		throws JniSassCompilerException {

		try {
			File tempFile = File.createTempFile("tmp", ".scss");

			tempFile.deleteOnExit();

			write(tempFile, input);

			return compileFile(
				tempFile.getCanonicalPath(), includeDirName, imgDirName);
		}
		catch (Exception e) {
			throw new JniSassCompilerException(e);
		}
	}

	private File getOutputFile(File file) {
		return new File(file.getParentFile(), getOutputFileName(file));
	}

	private String getOutputFileName(File file) {
		String fileName = file.getName();

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

	private Memory toPointer(String input) {
		byte[] data = Native.toByteArray(input);
		Memory pointer = new Memory(data.length + 1);
		pointer.write(0, data, 0, data.length);
		pointer.setByte(data.length, (byte)0);

		return pointer;
	}

	private void write(File file, String string) throws IOException {
		try (Writer writer = new OutputStreamWriter(
				new FileOutputStream(file, false), "UTF-8")) {

			writer.write(string);
		}
	}

	private static final LiferaysassLibrary _liferaysassLibrary =
		LiferaysassLibrary.INSTANCE;

}