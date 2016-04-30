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

import org.bridj.Platform;
import org.bridj.Pointer;

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
		Platform.addEmbeddedLibraryResourceRoot("/");

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

		Pointer<Sass_File_Context> sassFileContextPointer = null;

		try {
			File inputFile = new File(inputFileName);

			String includeDirNames =
				includeDirName + File.pathSeparator + inputFile.getParent();

			if ((sourceMapFileName == null) || sourceMapFileName.equals("")) {
				sourceMapFileName = getOutputFileName(inputFileName) + ".map";
			}

			sassFileContextPointer = createSassFileContext(
				inputFileName, includeDirNames, generateSourceMap,
				sourceMapFileName);

			Pointer<Sass_Context> sassContextPointer =
				LiferaysassLibrary.sassFileContextGetContext(
					sassFileContextPointer);

			int errorStatus = LiferaysassLibrary.sassContextGetErrorStatus(
				sassContextPointer);

			if (errorStatus != 0) {
				Pointer<Byte> errorMessagePointer =
					LiferaysassLibrary.sassContextGetErrorMessage(
						sassContextPointer);

				throw new JniSassCompilerException(
					errorMessagePointer.getCString());
			}

			Pointer<Byte> outputPointer =
				LiferaysassLibrary.sassContextGetOutputString(
					sassContextPointer);

			if (generateSourceMap) {
				try {
					File sourceMapFile = new File(sourceMapFileName);

					Pointer<Byte> sourceMapOutputPointer =
						LiferaysassLibrary.sassContextGetSourceMapString(
							sassContextPointer);

					write(sourceMapFile, sourceMapOutputPointer.getCString());
				}
				catch (Exception e) {
					System.out.println("Unable to create source map");
				}
			}

			if (outputPointer == null) {
				throw new JniSassCompilerException("Null output");
			}

			return outputPointer.getCString();
		}
		finally {
			try {
				if (sassFileContextPointer != null) {
					LiferaysassLibrary.sassDeleteFileContext(
						sassFileContextPointer);
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

	protected Pointer<Sass_File_Context> createSassFileContext(
		String inputFileName, String includeDirNames, boolean generateSourceMap,
		String sourceMapFileName) {

		Pointer<Sass_File_Context> sassFileContextPointer =
			LiferaysassLibrary.sassMakeFileContext(toPointer(inputFileName));

		Pointer<Sass_Options> sassOptionsPointer =
			LiferaysassLibrary.sassMakeOptions();

		LiferaysassLibrary.sassOptionSetIncludePath(
			sassOptionsPointer, toPointer(includeDirNames));
		LiferaysassLibrary.sassOptionSetInputPath(
			sassOptionsPointer, toPointer(inputFileName));
		LiferaysassLibrary.sassOptionSetOutputPath(
			sassOptionsPointer, toPointer(""));
		LiferaysassLibrary.sassOptionSetOutputStyle(
			sassOptionsPointer, Sass_Output_Style.SASS_STYLE_NESTED);
		LiferaysassLibrary.sassOptionSetPrecision(
			sassOptionsPointer, _precision);
		LiferaysassLibrary.sassOptionSetSourceComments(
			sassOptionsPointer, false);

		if (generateSourceMap) {
			LiferaysassLibrary.sassOptionSetSourceMapContents(
				sassOptionsPointer, false);
			LiferaysassLibrary.sassOptionSetSourceMapEmbed(
				sassOptionsPointer, false);
			LiferaysassLibrary.sassOptionSetSourceMapFile(
				sassOptionsPointer, toPointer(sourceMapFileName));
			LiferaysassLibrary.sassOptionSetOmitSourceMapUrl(
				sassOptionsPointer, false);
		}

		LiferaysassLibrary.sassFileContextSetOptions(
			sassFileContextPointer, sassOptionsPointer);

		LiferaysassLibrary.sassCompileFileContext(sassFileContextPointer);

		return sassFileContextPointer;
	}

	protected String getOutputFileName(String fileName) {
		return fileName.replaceAll("scss$", "css");
	}

	protected Pointer<Byte> toPointer(String s) {
		return Pointer.pointerToCString(s);
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

	private final int _precision;
	private final String _tmpDirName;

}