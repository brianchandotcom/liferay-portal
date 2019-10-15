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

package com.liferay.css.builder;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

import com.liferay.css.builder.internal.util.CSSBuilderUtil;
import com.liferay.css.builder.internal.util.FileUtil;
import com.liferay.rtl.css.RTLCSSConverter;
import com.liferay.sass.compiler.SassCompiler;
import com.liferay.sass.compiler.SassCompilerException;
import com.liferay.sass.compiler.jni.internal.JniSassCompiler;
import com.liferay.sass.compiler.jsass.internal.JSassCompiler;
import com.liferay.sass.compiler.ruby.internal.RubySassCompiler;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Eduardo Lundgren
 * @author Shuyang Zhou
 * @author David Truong
 * @author Christopher Bryan Boyd
 */
public class CSSBuilder implements AutoCloseable {

	public static void main(String[] args) throws Exception {
		CSSBuilderArgs cssBuilderArgs = new CSSBuilderArgs();

		JCommander jCommander = new JCommander(cssBuilderArgs);

		try {
			File jarFile = FileUtil.getJarFile();

			if (jarFile.isFile()) {
				jCommander.setProgramName("java -jar " + jarFile.getName());
			}
			else {
				jCommander.setProgramName(CSSBuilder.class.getName());
			}

			jCommander.parse(args);

			if (cssBuilderArgs.isHelp()) {
				_printHelp(jCommander);
			}
			else {
				try (CSSBuilder cssBuilder = new CSSBuilder(cssBuilderArgs)) {
					cssBuilder.execute();
				}
			}
		}
		catch (ParameterException pe) {
			System.err.println(pe.getMessage());

			_printHelp(jCommander);

			System.exit(1);
		}
	}

	public CSSBuilder(CSSBuilderArgs cssBuilderArgs) throws Exception {
		_cssBuilderArgs = cssBuilderArgs;

		List<File> importPaths = _cssBuilderArgs.getImportPaths();

		List<String> excludes = _cssBuilderArgs.getExcludes();

		_excludes = excludes.toArray(new String[0]);

		_importPath = Files.createTempDirectory("portalCssImportPath");

		if ((importPaths != null) && !importPaths.isEmpty()) {
			StringBuilder sb = new StringBuilder();

			for (File importPath : importPaths) {
				if (importPath.isFile()) {
					importPath = _unzipImport(importPath);
				}

				sb.append(importPath);
				sb.append(File.pathSeparator);
			}

			_importPathsString = sb.toString();
		}
		else {
			_importPathsString = null;
		}

		List<String> rtlExcludedPathRegexps =
			_cssBuilderArgs.getRtlExcludedPathRegexps();

		_rtlExcludedPathPatterns = new Pattern[rtlExcludedPathRegexps.size()];

		for (int i = 0; i < rtlExcludedPathRegexps.size(); i++) {
			_rtlExcludedPathPatterns[i] = Pattern.compile(
				rtlExcludedPathRegexps.get(i));
		}

		_initSassCompiler(_cssBuilderArgs.getSassCompilerClassName());
	}

	@Override
	public void close() throws Exception {
		FileUtil.deltree(_importPath);

		_sassCompiler.close();
	}

	public void execute() throws Exception {
		List<String> fileNames = new ArrayList<>();

		File baseDir = _cssBuilderArgs.getBaseDir();

		if (!baseDir.exists()) {
			throw new IOException("Directory " + baseDir + " does not exist");
		}

		for (String dirName : _cssBuilderArgs.getDirNames()) {
			List<String> sassFileNames = _collectSassFiles(dirName, baseDir);

			fileNames.addAll(sassFileNames);
		}

		if (fileNames.isEmpty()) {
			System.out.println("There are no files to compile");

			return;
		}

		for (String fileName : fileNames) {
			long startTime = System.currentTimeMillis();

			_parseSassFile(fileName);

			System.out.println(
				"Parsed " + fileName + " in " +
					String.valueOf(System.currentTimeMillis() - startTime) +
						"ms");
		}
	}

	public boolean isRtlExcludedPath(String filePath) {
		for (Pattern pattern : _rtlExcludedPathPatterns) {
			Matcher matcher = pattern.matcher(filePath);

			if (matcher.matches()) {
				return true;
			}
		}

		return false;
	}

	private static void _printHelp(JCommander jCommander) {
		jCommander.usage();
	}

	private List<String> _collectSassFiles(String dirName, File baseDir)
		throws Exception {

		List<String> fileNames = new ArrayList<>();

		String basedir = String.valueOf(new File(baseDir, dirName));

		String[] scssFiles = _getScssFiles(basedir);

		if (!_isModified(basedir, scssFiles)) {
			long oldestSassModifiedTime = _getOldestModifiedTime(
				basedir, scssFiles);

			String[] scssFragments = _getScssFragments(basedir);

			long newestFragmentModifiedTime = _getNewestModifiedTime(
				basedir, scssFragments);

			if (oldestSassModifiedTime > newestFragmentModifiedTime) {
				return fileNames;
			}
		}

		for (String fileName : scssFiles) {
			if (fileName.contains("_rtl")) {
				continue;
			}

			fileNames.add(_normalizeFileName(dirName, fileName));
		}

		return fileNames;
	}

	private long _getNewestModifiedTime(String baseDir, String[] fileNames) {
		return Stream.of(
			fileNames
		).map(
			fileName -> Paths.get(baseDir, fileName)
		).map(
			FileUtil::getLastModifiedTime
		).max(
			Comparator.naturalOrder()
		).orElse(
			Long.MIN_VALUE
		);
	}

	private long _getOldestModifiedTime(String baseDir, String[] fileNames) {
		return Stream.of(
			fileNames
		).map(
			fileName -> Paths.get(baseDir, fileName)
		).map(
			FileUtil::getLastModifiedTime
		).min(
			Comparator.naturalOrder()
		).orElse(
			Long.MIN_VALUE
		);
	}

	private String _getRtlCss(String fileName, String css) {
		String rtlCss = css;

		try {
			if (_rtlCSSConverter == null) {
				_rtlCSSConverter = new RTLCSSConverter();
			}

			rtlCss = _rtlCSSConverter.process(rtlCss);
		}
		catch (Exception e) {
			System.out.println(
				"Unable to generate RTL version for " + fileName + ", " +
					e.getMessage());
		}

		return rtlCss;
	}

	private String[] _getScssFiles(String baseDir) throws IOException {
		String[] includes = {"**/*.scss"};

		String[] excludes = Arrays.copyOf(_excludes, _excludes.length + 1);

		excludes[excludes.length - 1] = "**/_*.scss";

		return FileUtil.getFilesFromDirectory(baseDir, includes, excludes);
	}

	private String[] _getScssFragments(String baseDir) throws IOException {
		String[] includes = {"**/_*.scss"};

		return FileUtil.getFilesFromDirectory(baseDir, includes, _excludes);
	}

	private void _initSassCompiler(String sassCompilerClassName)
		throws Exception {

		int precision = _cssBuilderArgs.getPrecision();

		if ((sassCompilerClassName == null) ||
			sassCompilerClassName.isEmpty() ||
			sassCompilerClassName.equals("jni")) {

			try {
				_sassCompiler = new JSassCompiler(precision);

				System.out.println("Using native Sass compiler");
			}
			catch (Throwable t) {
				System.out.println(
					"Unable to load native compiler, falling back to Ruby");

				_sassCompiler = new RubySassCompiler(precision);
			}
		}
		else if (sassCompilerClassName.equals("jni32")) {
			try {
				System.setProperty("jna.nosys", Boolean.TRUE.toString());

				_sassCompiler = new JniSassCompiler(precision);

				System.out.println("Using native 32-bit Sass compiler");
			}
			catch (Throwable t) {
				System.out.println(
					"Unable to load native compiler, falling back to Ruby");

				_sassCompiler = new RubySassCompiler(precision);
			}
		}
		else if (sassCompilerClassName.equals("ruby")) {
			try {
				_sassCompiler = new RubySassCompiler(precision);

				System.out.println("Using Ruby Sass compiler");
			}
			catch (Exception e) {
				System.out.println(
					"Unable to load Ruby compiler, falling back to native");

				_sassCompiler = new JSassCompiler(precision);
			}
		}
	}

	private boolean _isModified(String dirName, String[] fileNames) {
		for (String fileName : fileNames) {
			if (fileName.contains("_rtl")) {
				continue;
			}

			fileName = _normalizeFileName(dirName, fileName);

			File file = new File(fileName);
			File cacheFile = CSSBuilderUtil.getOutputFile(
				fileName, _cssBuilderArgs.getOutputDirName());

			if (file.lastModified() != cacheFile.lastModified()) {
				return true;
			}
		}

		return false;
	}

	private String _normalizeFileName(String dirName, String fileName) {
		fileName = dirName + "/" + fileName;

		fileName = fileName.replace('\\', '/');
		fileName = fileName.replace("//", "/");

		return fileName;
	}

	private String _parseSass(String fileName) throws SassCompilerException {
		File sassFile = new File(_cssBuilderArgs.getBaseDir(), fileName);

		Path path = sassFile.toPath();

		String filePath = path.toString();

		String cssBasePath = filePath;

		String cssSegment = "css" + File.separator;

		int pos = filePath.lastIndexOf(File.separator + cssSegment);

		if (pos >= 0) {
			cssBasePath = filePath.substring(0, pos + cssSegment.length());
		}
		else {
			String resourcesSegment = "resources" + File.separator;

			pos = filePath.lastIndexOf(File.separator + resourcesSegment);

			if (pos >= 0) {
				cssBasePath = filePath.substring(
					0, pos + resourcesSegment.length());
			}
		}

		return _sassCompiler.compileFile(
			filePath, _importPathsString + File.pathSeparator + cssBasePath,
			_cssBuilderArgs.isGenerateSourceMap(), filePath + ".map");
	}

	private void _parseSassFile(String fileName) throws Exception {
		File file = new File(_cssBuilderArgs.getBaseDir(), fileName);

		if (!file.exists()) {
			return;
		}

		String ltrContent = _parseSass(fileName);

		_writeOutputFile(fileName, ltrContent, false);

		if (isRtlExcludedPath(fileName)) {
			return;
		}

		String rtlContent = _getRtlCss(fileName, ltrContent);

		String rtlCustomFileName = CSSBuilderUtil.getRtlCustomFileName(
			fileName);

		File rtlCustomFile = new File(
			_cssBuilderArgs.getBaseDir(), rtlCustomFileName);

		if (rtlCustomFile.exists()) {
			rtlContent += _parseSass(rtlCustomFileName);
		}

		_writeOutputFile(fileName, rtlContent, true);
	}

	private File _unzipImport(File importFile) throws IOException {
		Path outputPath = _importPath.resolve(importFile.getName());

		try (ZipFile zipFile = new ZipFile(importFile)) {
			Enumeration<? extends ZipEntry> enumeration = zipFile.entries();

			while (enumeration.hasMoreElements()) {
				ZipEntry zipEntry = enumeration.nextElement();

				String name = zipEntry.getName();

				if (name.endsWith("/") ||
					!name.startsWith("META-INF/resources/")) {

					continue;
				}

				name = name.substring(19);

				Path path = outputPath.resolve(name);

				Files.createDirectories(path.getParent());

				Files.copy(
					zipFile.getInputStream(zipEntry), path,
					StandardCopyOption.REPLACE_EXISTING);
			}
		}

		return outputPath.toFile();
	}

	private void _writeOutputFile(String fileName, String content, boolean rtl)
		throws Exception {

		if (_cssBuilderArgs.isAppendCssImportTimestamps()) {
			content = CSSBuilderUtil.parseCSSImports(content);
		}

		String outputFileName;

		boolean absoluteOutputDir = false;
		String outputFileDirName = _cssBuilderArgs.getOutputDirName();

		if (FileUtil.isAbsolute(outputFileDirName)) {
			absoluteOutputDir = true;
			outputFileDirName = "";
		}

		if (rtl) {
			String rtlFileName = CSSBuilderUtil.getRtlCustomFileName(fileName);

			outputFileName = CSSBuilderUtil.getOutputFileName(
				rtlFileName, outputFileDirName, "");
		}
		else {
			outputFileName = CSSBuilderUtil.getOutputFileName(
				fileName, outputFileDirName, "");
		}

		File outputFile;

		if (absoluteOutputDir) {
			outputFile = new File(
				_cssBuilderArgs.getOutputDirName(), outputFileName);
		}
		else {
			outputFile = new File(_cssBuilderArgs.getBaseDir(), outputFileName);
		}

		FileUtil.write(outputFile, content);

		File file = new File(_cssBuilderArgs.getBaseDir(), fileName);

		outputFile.setLastModified(file.lastModified());
	}

	private static RTLCSSConverter _rtlCSSConverter;

	private final CSSBuilderArgs _cssBuilderArgs;
	private final String[] _excludes;
	private final Path _importPath;
	private final String _importPathsString;
	private final Pattern[] _rtlExcludedPathPatterns;
	private SassCompiler _sassCompiler;

}