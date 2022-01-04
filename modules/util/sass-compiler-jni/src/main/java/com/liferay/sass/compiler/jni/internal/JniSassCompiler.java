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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import java.lang.reflect.Field;

import java.nio.file.Files;

import java.util.Iterator;
import java.util.Set;

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

		_cleanTempDir = Boolean.getBoolean("sass.compiler.jni.clean.temp.dir");
	}

	@Override
	public void close() throws IOException {
		if (!_cleanTempDir) {
			return;
		}

		try {
			Field field = Platform.class.getDeclaredField(
				"temporaryExtractedLibraryCanonicalFiles");

			field.setAccessible(true);

			Set<File> temporaryExtractedLibraryCanonicalFiles =
				(Set<File>)field.get(null);

			Iterator<File> iterator =
				temporaryExtractedLibraryCanonicalFiles.iterator();

			while (iterator.hasNext()) {
				File file = iterator.next();

				if (file.isFile() && file.delete()) {
					iterator.remove();
				}
			}

			field = Platform.class.getDeclaredField(
				"extractedLibrariesTempDir");

			field.setAccessible(true);

			File extractedLibrariesTempDir = (File)field.get(null);

			iterator = temporaryExtractedLibraryCanonicalFiles.iterator();

			while (iterator.hasNext()) {
				File file = iterator.next();

				if (!file.equals(extractedLibrariesTempDir) && file.delete()) {
					iterator.remove();
				}
			}
		}
		catch (Exception exception) {
			throw new IOException(
				"Unable to clean up BridJ's temporary directory", exception);
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

		Pointer<LiferaysassLibrary.Sass_File_Context> sassFileContextPointer =
			null;

		try {
			File inputFile = new File(inputFileName);

			String includeDirNames =
				includeDirName + File.pathSeparator + inputFile.getParent();

			if ((sourceMapFileName == null) || sourceMapFileName.equals("")) {
				sourceMapFileName = _getOutputFileName(inputFileName) + ".map";
			}

			sassFileContextPointer = _createSassFileContext(
				inputFileName, includeDirNames, generateSourceMap,
				sourceMapFileName);

			Pointer<LiferaysassLibrary.Sass_Context> sassContextPointer =
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

					_write(sourceMapFile, sourceMapOutputPointer.getCString());
				}
				catch (Exception exception) {
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
			catch (Throwable throwable) {
				throw new JniSassCompilerException(throwable);
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
			throw new JniSassCompilerException(throwable);
		}
	}

	private Pointer<LiferaysassLibrary.Sass_File_Context>
		_createSassFileContext(
			String inputFileName, String includeDirNames,
			boolean generateSourceMap, String sourceMapFileName) {

		Pointer<LiferaysassLibrary.Sass_File_Context> sassFileContextPointer =
			LiferaysassLibrary.sassMakeFileContext(_toPointer(inputFileName));

		Pointer<LiferaysassLibrary.Sass_Options> sassOptionsPointer =
			LiferaysassLibrary.sassMakeOptions();

		LiferaysassLibrary.sassOptionSetIncludePath(
			sassOptionsPointer, _toPointer(includeDirNames));
		LiferaysassLibrary.sassOptionSetInputPath(
			sassOptionsPointer, _toPointer(inputFileName));
		LiferaysassLibrary.sassOptionSetOutputPath(
			sassOptionsPointer, _toPointer(""));
		LiferaysassLibrary.sassOptionSetOutputStyle(
			sassOptionsPointer,
			LiferaysassLibrary.Sass_Output_Style.SASS_STYLE_NESTED);
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
				sassOptionsPointer, _toPointer(sourceMapFileName));
			LiferaysassLibrary.sassOptionSetOmitSourceMapUrl(
				sassOptionsPointer, false);
		}

		LiferaysassLibrary.sassFileContextSetOptions(
			sassFileContextPointer, sassOptionsPointer);

		LiferaysassLibrary.sassCompileFileContext(sassFileContextPointer);

		return sassFileContextPointer;
	}

	private String _getOutputFileName(String fileName) {
		return fileName.replaceAll("scss$", "css");
	}

	private Pointer<Byte> _toPointer(String s) {
		return Pointer.pointerToCString(s);
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

	private final boolean _cleanTempDir;
	private final int _precision;
	private final String _tmpDirName;

}