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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * @author Gregory Amerson
 */
public class jsc {

	public static void main(String[] args) {

		try {
			new jsc(args);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public jsc(String[] args)
		throws Exception {

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
}
