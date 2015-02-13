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

package com.liferay.sass.compiler;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.sass.compiler.libsass.SassLibrary;
import com.liferay.sass.compiler.libsass.SassLibrary.Sass_Context;
import com.liferay.sass.compiler.libsass.SassLibrary.Sass_Data_Context;
import com.liferay.sass.compiler.libsass.SassLibrary.Sass_File_Context;
import com.liferay.sass.compiler.libsass.SassLibrary.Sass_Options;
import com.liferay.sass.compiler.libsass.SassLibrary.Sass_Output_Style;

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
public class SassCompiler {

	public static void main(String[] args) {
		try {
			new SassCompiler(args);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SassCompiler() {
	}

	public SassCompiler(String[] fileNames) throws Exception {
		final SassCompiler sassCompiler = new SassCompiler();

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

	public String compileFile(
			String inputFile, String includePath, String imgPath)
		throws SassCompilerException {

		// NONE((byte)0), DEFAULT((byte)1), MAP((byte)2);

		byte sourceComments = (byte)0;

		String includePaths =
			includePath + File.pathSeparator + new File(inputFile).getParent();

		Sass_File_Context sassFileContext = null;

		try {
			sassFileContext = _sassLibrary.sass_make_file_context(inputFile);

			Sass_Options sassOptions = _sassLibrary.sass_make_options();

			_sassLibrary.sass_option_set_image_path(sassOptions, imgPath);
			_sassLibrary.sass_option_set_include_path(
				sassOptions, includePaths);
			_sassLibrary.sass_option_set_input_path(sassOptions, inputFile);
			_sassLibrary.sass_option_set_output_path(sassOptions, "");
			_sassLibrary.sass_option_set_output_style(
				sassOptions, Sass_Output_Style.SASS_STYLE_COMPACT);
			_sassLibrary.sass_option_set_source_comments(
				sassOptions, sourceComments);

			_sassLibrary.sass_file_context_set_options(
				sassFileContext, sassOptions);

			_sassLibrary.sass_compile_file_context(sassFileContext);

			Sass_Context sassContext =
				_sassLibrary.sass_file_context_get_context(sassFileContext);

			int errorStatus = _sassLibrary.sass_context_get_error_status(
				sassContext);

			if (errorStatus != 0) {
				String errorMessage =
					_sassLibrary.sass_context_get_error_message(sassContext);

				throw new SassCompilerException(errorMessage);
			}

			String output = _sassLibrary.sass_context_get_output_string(
				sassContext);

			if (output == null) {
				throw new SassCompilerException("Null output");
			}

			return output;
		}
		finally {
			try {
				if (sassFileContext != null) {
					_sassLibrary.sass_delete_file_context(sassFileContext);
				}
			}
			catch (Throwable t) {
				throw new SassCompilerException(t);
			}
		}
	}

	@SuppressWarnings("deprecation")
	public String compileString(
			String input, String includePath, String imgPath)
		throws SassCompilerException {

		// NONE((byte)0), DEFAULT((byte)1), MAP((byte)2);

		byte sourceComments = (byte)0;

		Sass_Data_Context sassDataContext = null;

		try {
			Memory pointer = toPointer(input);

			sassDataContext = _sassLibrary.sass_make_data_context(pointer);

			Sass_Options sassOptions = _sassLibrary.sass_make_options();

			_sassLibrary.sass_option_set_image_path(sassOptions, imgPath);
			_sassLibrary.sass_option_set_include_path(sassOptions, includePath);
			_sassLibrary.sass_option_set_output_style(
				sassOptions, Sass_Output_Style.SASS_STYLE_COMPACT);
			_sassLibrary.sass_option_set_source_comments(
				sassOptions, sourceComments);

			_sassLibrary.sass_data_context_set_options(
				sassDataContext, sassOptions);

			_sassLibrary.sass_compile_data_context(sassDataContext);

			Sass_Context sassContext =
				_sassLibrary.sass_data_context_get_context(sassDataContext);

			int errorStatus = _sassLibrary.sass_context_get_error_status(
				sassContext);

			if (errorStatus != 0) {
				String errorMessage =
					_sassLibrary.sass_context_get_error_message(sassContext);

				throw new SassCompilerException(errorMessage);
			}

			String output = _sassLibrary.sass_context_get_output_string(
				sassContext);

			if (output == null) {
				throw new SassCompilerException("Null output");
			}

			return output;
		}
		finally {
			try {
				if (sassDataContext != null) {
					_sassLibrary.sass_delete_data_context(sassDataContext);
				}
			}
			catch (Throwable t) {
				throw new SassCompilerException(t);
			}
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
				new FileOutputStream(file, false), StringPool.UTF8)) {

			writer.write(string);
		}
	}

	private static final SassLibrary _sassLibrary = SassLibrary.INSTANCE;

}