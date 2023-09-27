/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.css.builder;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

import com.liferay.css.builder.internal.util.CSSBuilderUtil;
import com.liferay.css.builder.internal.util.FileUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.rtl.css.RTLCSSConverter;
import com.liferay.sass.compiler.SassCompiler;
import com.liferay.sass.compiler.dart.internal.DartSassCompiler;

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
		catch (ParameterException parameterException) {
			System.err.println(parameterException.getMessage());

			_printHelp(jCommander);

			System.exit(1);
		}
	}

	public CSSBuilder(CSSBuilderArgs cssBuilderArgs) throws Exception {
		_cssBuilderArgs = cssBuilderArgs;

		List<File> importPaths = cssBuilderArgs.getImportPaths();

		List<String> excludes = cssBuilderArgs.getExcludes();

		_excludes = excludes.toArray(new String[0]);

		_importPath = Files.createTempDirectory("portalCssImportPath");

		if ((importPaths != null) && !importPaths.isEmpty()) {
			StringBundler sb = new StringBundler();

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
			cssBuilderArgs.getRtlExcludedPathRegexps();

		_rtlExcludedPathPatterns = new Pattern[rtlExcludedPathRegexps.size()];

		for (int i = 0; i < rtlExcludedPathRegexps.size(); i++) {
			_rtlExcludedPathPatterns[i] = Pattern.compile(
				rtlExcludedPathRegexps.get(i));
		}

		_initSassCompiler(cssBuilderArgs.getSassCompilerClassName());
	}

	@Override
	public void close() throws Exception {
		FileUtil.deltree(_importPath);

		_sassCompiler.close();
	}

	public void execute() throws Exception {
		File baseDir = _cssBuilderArgs.getBaseDir();

		if (!baseDir.exists()) {
			throw new IOException("Directory " + baseDir + " does not exist");
		}

		List<String> fileNames = new ArrayList<>();

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
				StringBundler.concat(
					"Parsed ", fileName, " in ",
					System.currentTimeMillis() - startTime, "ms"));
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
			long oldestSassModifiedTime = _getModifiedTime(
				basedir, scssFiles, Comparator.naturalOrder());

			long newestFragmentModifiedTime = _getModifiedTime(
				basedir, _getScssFragments(basedir), Comparator.reverseOrder());

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

	private long _getModifiedTime(
		String baseDir, String[] fileNames, Comparator<Long> comparator) {

		List<Long> lastModifiedTimes = TransformUtil.transformToList(
			fileNames,
			fileName -> FileUtil.getLastModifiedTime(
				Paths.get(baseDir, fileName)));

		if (lastModifiedTimes.isEmpty()) {
			return Long.MIN_VALUE;
		}

		lastModifiedTimes.sort(comparator);

		return lastModifiedTimes.get(0);
	}

	private String _getRtlCss(String fileName, String css) {
		String rtlCss = css;

		try {
			if (_rtlCSSConverter == null) {
				_rtlCSSConverter = new RTLCSSConverter();
			}

			rtlCss = _rtlCSSConverter.process(rtlCss);
		}
		catch (Exception exception) {
			System.out.println(
				StringBundler.concat(
					"Unable to generate RTL version for ", fileName, ", ",
					exception.getMessage()));
		}

		return rtlCss;
	}

	private String[] _getScssFiles(String baseDir) throws Exception {
		String[] includes = {"**/*.scss"};

		String[] excludes = Arrays.copyOf(_excludes, _excludes.length + 1);

		excludes[excludes.length - 1] = "**/_*.scss";

		return FileUtil.getFilesFromDirectory(baseDir, includes, excludes);
	}

	private String[] _getScssFragments(String baseDir) throws Exception {
		return FileUtil.getFilesFromDirectory(
			baseDir, new String[] {"**/_*.scss"}, _excludes);
	}

	private void _initSassCompiler(String sassCompilerClassName)
		throws Exception {

		int precision = _cssBuilderArgs.getPrecision();

		if ((sassCompilerClassName == null) ||
			sassCompilerClassName.isEmpty() ||
			sassCompilerClassName.equals("dart")) {

			System.out.println("Using Dart Sass compiler");
		}
		else if (sassCompilerClassName.equals("jni") ||
				 sassCompilerClassName.equals("jni32") ||
				 sassCompilerClassName.equals("ruby")) {

			System.out.println(
				"Using Dart Sass compiler because other sass compilers are " +
					"no longer supported");
		}

		try {
			_sassCompiler = new DartSassCompiler(precision);
		}
		catch (Throwable throwable) {
			System.out.println("Unable to load sass compiler");

			throw throwable;
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

	private String _parseSass(String fileName) throws Exception {
		File sassFile = new File(_cssBuilderArgs.getBaseDir(), fileName);

		String filePath = String.valueOf(sassFile.toPath());

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

	private File _unzipImport(File importFile) throws Exception {
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