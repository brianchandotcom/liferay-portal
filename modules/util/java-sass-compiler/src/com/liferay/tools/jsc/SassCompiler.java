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

import com.sun.jna.Pointer;

import java.io.File;

import sass.SassLibrary;
import sass.SassLibrary.Sass_Context;
import sass.SassLibrary.Sass_File_Context;
import sass.SassLibrary.Sass_Options;

/**
 * @author Gregory Amerson
 */
public class SassCompiler {

	public String compileFile(
			String inputFile, String includePath, String imgPath)
		throws SassCompilationException {

		Sass_File_Context sassFileContext = null;

		try {
			sassFileContext = _libsass.sass_make_file_context(inputFile);

			Sass_Options opt = _libsass.sass_make_options();
			_libsass.sass_option_set_output_path(opt, "");
			_libsass.sass_option_set_image_path(opt, imgPath);

			int outputstyle = 1; // NESTED 0 EXPANDED 1 COMPACT 2 COMPRESSED 3 FORMATTED 4
			_libsass.sass_option_set_output_style(opt, outputstyle);

			byte sourceComments = (byte)0; // NONE((byte)0), DEFAULT((byte)1), MAP((byte)2);
			_libsass.sass_option_set_source_comments(opt, sourceComments);
//			sassFileContext.source_map_string = str("");

			String includePaths = includePath +
				File.pathSeparator + new File(inputFile).getParent();

			_libsass.sass_option_set_include_path(opt, includePaths);

			_libsass.sass_file_context_set_options(sassFileContext, opt);

			_libsass.sass_compile_file_context(sassFileContext);

			Sass_Context context = _libsass.sass_file_context_get_context(sassFileContext);

			int errorStatus = _libsass.sass_context_get_error_status(context);

			if (errorStatus != 0) {
				Pointer errorMsg = _libsass.sass_context_get_error_message(context);
				throw new SassCompilationException( errorMsg.getString(0));
			}

			Pointer outputString = _libsass.sass_context_get_output_string(context);

			if (outputString == null || outputString.getString(0) == null) {

				throw new SassCompilationException("libsass returned null");
			}

			return outputString.getString(0);
		} finally {
			try {
				if (sassFileContext != null) {
					_libsass.sass_delete_file_context(sassFileContext);
				}
			} catch (Throwable t) {
				throw new SassCompilationException(t);
			}
		}
	}

	private static final SassLibrary _libsass = SassLibrary.INSTANCE;

}