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

import com.liferay.css.builder.internal.util.FileUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.rtl.css.RTLCSSConverter;
import com.liferay.sass.compiler.SassCompiler;
import com.liferay.sass.compiler.SassCompilerException;
import com.liferay.sass.compiler.jni.internal.JniSassCompiler;
import com.liferay.sass.compiler.ruby.internal.RubySassCompiler;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import java.util.ArrayList;
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

		File portalCommonDir = _cssBuilderArgs.getPortalCommonPath();

		if (portalCommonDir.isFile()) {
			portalCommonDir = _unzipPortalCommon(portalCommonDir);

			_cleanPortalCommonDir = true;
		}
		else {
			_cleanPortalCommonDir = false;
		}

		_portalCommonDirName = portalCommonDir.getCanonicalPath();

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
		if (_cleanPortalCommonDir) {
			FileUtil.deltree(Paths.get(_portalCommonDirName));
		}

		_sassCompiler.close();
	}

	public void execute() throws Exception {
		List<String> fileNames = new ArrayList<>();

		File docrootDir = _cssBuilderArgs.getDocrootDir();

		for (String dirName : _cssBuilderArgs.getDirNames()) {
			List<String> sassFileNames = _collectSassFiles(dirName, docrootDir);

			fileNames.addAll(sassFileNames);
		}

		for (String fileName : fileNames) {
			long startTime = System.currentTimeMillis();

			_parseSassFile(fileName);

			System.out.println(
				StringBundler.concat(
					"Parsed ", fileName, " in ",
					String.valueOf(System.currentTimeMillis() - startTime),
					"ms"));
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

	private static void _printHelp(JCommander jCommander) throws Exception {
		jCommander.usage();
	}

	private List<String> _collectSassFiles(String dirName, File docrootDir)
		throws Exception {

		List<String> fileNames = new ArrayList<>();

		String basedir = new File(docrootDir, dirName).toString();

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
		Stream<String> stream = Stream.of(fileNames);

		return stream.map(
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
		Stream<String> stream = Stream.of(fileNames);

		return stream.map(
			fileName -> Paths.get(baseDir, fileName)
		).map(
			FileUtil::getLastModifiedTime
		).min(
			Comparator.naturalOrder()
		).orElse(
			Long.MIN_VALUE
		);
	}

	private String _getRtlCss(String fileName, String css) throws Exception {
		String rtlCss = css;

		try {
			if (_rtlCSSConverter == null) {
				_rtlCSSConverter = new RTLCSSConverter();
			}

			rtlCss = _rtlCSSConverter.process(rtlCss);
		}
		catch (Exception e) {
			System.out.println(
				StringBundler.concat(
					"Unable to generate RTL version for ", fileName,
					StringPool.COMMA_AND_SPACE, e.getMessage()));
		}

		return rtlCss;
	}

	private String[] _getScssFiles(String baseDir) {
		String[] fragments = {"**\\_*.scss"};
		String[] includes = {"**\\*.scss"};

		Stream<String[]> stream = Stream.of(fragments, _EXCLUDES);

		String[] excludes = stream.flatMap(
			Stream::of
		).toArray(
			String[]::new
		);

		return FileUtil.getFilesFromDirectory(baseDir, includes, excludes);
	}

	private String[] _getScssFragments(String baseDir) {
		String[] includes = {"**\\\\_*.scss"};

		return FileUtil.getFilesFromDirectory(baseDir, includes, _EXCLUDES);
	}

	private void _initSassCompiler(String sassCompilerClassName)
		throws Exception {

		int precision = _cssBuilderArgs.getPrecision();

		if (Validator.isNull(sassCompilerClassName) ||
			sassCompilerClassName.equals("jni")) {

			try {
				System.setProperty("jna.nosys", Boolean.TRUE.toString());

				_sassCompiler = new JniSassCompiler(precision);

				System.out.println("Using native Sass compiler");
			}
			catch (Throwable t) {
				System.out.println(
					"Unable to load native compiler, falling back to Ruby");

				_sassCompiler = new RubySassCompiler(precision);
			}
		}
		else {
			try {
				_sassCompiler = new RubySassCompiler(precision);

				System.out.println("Using Ruby Sass compiler");
			}
			catch (Exception e) {
				System.out.println(
					"Unable to load Ruby compiler, falling back to native");

				System.setProperty("jna.nosys", Boolean.TRUE.toString());

				_sassCompiler = new JniSassCompiler(precision);
			}
		}
	}

	private boolean _isModified(String dirName, String[] fileNames)
		throws Exception {

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
		fileName = StringUtil.replace(
			dirName + StringPool.SLASH + fileName,
			new String[] {StringPool.BACK_SLASH, StringPool.DOUBLE_SLASH},
			new String[] {StringPool.SLASH, StringPool.SLASH});

		return fileName;
	}

	private String _parseSass(String fileName) throws SassCompilerException {
		File sassFile = new File(_cssBuilderArgs.getDocrootDir(), fileName);

		String filePath = sassFile.toPath().toString();

		String cssBasePath = filePath;

		int pos = filePath.lastIndexOf("/css/");

		if (pos >= 0) {
			cssBasePath = filePath.substring(0, pos + 4);
		}
		else {
			pos = filePath.lastIndexOf("/resources/");

			if (pos >= 0) {
				cssBasePath = filePath.substring(0, pos + 10);
			}
		}

		String css = _sassCompiler.compileFile(
			filePath, _portalCommonDirName + File.pathSeparator + cssBasePath,
			_cssBuilderArgs.isGenerateSourceMap(), filePath + ".map");

		return CSSBuilderUtil.parseStaticTokens(css);
	}

	private void _parseSassFile(String fileName) throws Exception {
		File file = new File(_cssBuilderArgs.getDocrootDir(), fileName);

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
			_cssBuilderArgs.getDocrootDir(), rtlCustomFileName);

		if (rtlCustomFile.exists()) {
			rtlContent += _parseSass(rtlCustomFileName);
		}

		_writeOutputFile(fileName, rtlContent, true);
	}

	private File _unzipPortalCommon(File portalCommonFile) throws IOException {
		Path portalCommonCssDirPath = Files.createTempDirectory(
			"portalCommonCss");

		try (ZipFile zipFile = new ZipFile(portalCommonFile)) {
			Enumeration<? extends ZipEntry> enumeration = zipFile.entries();

			while (enumeration.hasMoreElements()) {
				ZipEntry zipEntry = enumeration.nextElement();

				String name = zipEntry.getName();

				if (name.endsWith("/") ||
					!name.startsWith("META-INF/resources/")) {

					continue;
				}

				name = name.substring(19);

				Path path = portalCommonCssDirPath.resolve(name);

				Files.createDirectories(path.getParent());

				Files.copy(
					zipFile.getInputStream(zipEntry), path,
					StandardCopyOption.REPLACE_EXISTING);
			}
		}

		return portalCommonCssDirPath.toFile();
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
			outputFileDirName = StringPool.BLANK;
		}

		if (rtl) {
			String rtlFileName = CSSBuilderUtil.getRtlCustomFileName(fileName);

			outputFileName = CSSBuilderUtil.getOutputFileName(
				rtlFileName, outputFileDirName, StringPool.BLANK);
		}
		else {
			outputFileName = CSSBuilderUtil.getOutputFileName(
				fileName, outputFileDirName, StringPool.BLANK);
		}

		File outputFile;

		if (absoluteOutputDir) {
			outputFile = new File(
				_cssBuilderArgs.getOutputDirName(), outputFileName);
		}
		else {
			outputFile = new File(
				_cssBuilderArgs.getDocrootDir(), outputFileName);
		}

		FileUtil.write(outputFile, content);

		File file = new File(_cssBuilderArgs.getDocrootDir(), fileName);

		outputFile.setLastModified(file.lastModified());
	}

	private static final String[] _EXCLUDES = {
		"**\\_diffs\\**", "**\\.sass-cache*\\**", "**\\.sass_cache_*\\**",
		"**\\_sass_cache_*\\**", "**\\_styled\\**", "**\\_unstyled\\**",
		"**\\css\\aui\\**", "**\\tmp\\**"
	};

	private static RTLCSSConverter _rtlCSSConverter;

	private final boolean _cleanPortalCommonDir;
	private final CSSBuilderArgs _cssBuilderArgs;
	private final String _portalCommonDirName;
	private final Pattern[] _rtlExcludedPathPatterns;
	private SassCompiler _sassCompiler;

}