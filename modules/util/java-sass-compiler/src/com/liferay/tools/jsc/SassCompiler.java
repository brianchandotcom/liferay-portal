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

package com.liferay.tools.jsc;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.tools.jsc.libsass.SassLibrary;
import com.liferay.tools.jsc.libsass.SassLibrary.Sass_Context;
import com.liferay.tools.jsc.libsass.SassLibrary.Sass_File_Context;
import com.liferay.tools.jsc.libsass.SassLibrary.Sass_Options;

import com.sun.jna.Pointer;

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

	public SassCompiler(String[] args) throws Exception {
		final SassCompiler compiler = new SassCompiler();

		for (String arg : args) {
			final File file = new File(arg);

			if (isValidFile(file)) {
				final String output = compiler.compileFile(arg, "", "");

				final File outputFile = getOutputFile(file);
				write(outputFile, output);
			}
		}
	}

	public String compileFile(
			String inputFile, String includePath, String imgPath)
		throws SassCompilationException {

		// NESTED 0 EXPANDED 1 COMPACT 2 COMPRESSED 3 FORMATTED 4

		final int outputstyle = 1;

		// NONE((byte)0), DEFAULT((byte)1), MAP((byte)2);

		final byte sourceComments = (byte) 0;

		final String includePaths =
			includePath + File.pathSeparator + new File(inputFile).getParent();

		Sass_File_Context sassFileContext = null;

		try {
			final Sass_Options opt = _libsass.sass_make_options();
			_libsass.sass_option_set_input_path(opt, inputFile);
			_libsass.sass_option_set_output_path(opt, "");
			_libsass.sass_option_set_image_path(opt, imgPath);
			_libsass.sass_option_set_output_style(opt, outputstyle);
			_libsass.sass_option_set_source_comments(opt, sourceComments);
			_libsass.sass_option_set_include_path(opt, includePaths);

			sassFileContext = _libsass.sass_make_file_context(inputFile);
			_libsass.sass_file_context_set_options(sassFileContext, opt);

			_libsass.sass_compile_file_context(sassFileContext);

			final Sass_Context context = _libsass.sass_file_context_get_context(
				sassFileContext);
			final int errorStatus = _libsass.sass_context_get_error_status(
				context);

			if (errorStatus != 0) {
				Pointer errorMsg = _libsass.sass_context_get_error_message(
					context);
				throw new SassCompilationException(errorMsg.getString(0));
			}

			final Pointer outputString =
				_libsass.sass_context_get_output_string(context);

			if ((outputString == null) || (outputString.getString(0) == null)) {
				throw new SassCompilationException("libsass returned null");
			}

			return outputString.getString(0);
		}
		finally {
			try {
				if (sassFileContext != null) {
					_libsass.sass_delete_file_context(sassFileContext);
				}
			}
			catch (Throwable t) {
				throw new SassCompilationException(t);
			}
		}
	}

	private File getOutputFile(File file) {
		return new File(file.getParentFile(), getOutputFileName(file));
	}

	private String getOutputFileName(File file) {
		return file.getName().replaceAll("scss$", "css");
	}

	private boolean isValidFile(File file) {
		return file != null && file.exists() &&
			file.getName().endsWith(".scss");
	}

	private void write(File outputFile, String output) throws IOException {
		try (Writer writer = new OutputStreamWriter(
			new FileOutputStream(outputFile, false), StringPool.UTF8)) {

			writer.write(output);
		}
	}

	private static final SassLibrary _libsass = SassLibrary.INSTANCE;

}