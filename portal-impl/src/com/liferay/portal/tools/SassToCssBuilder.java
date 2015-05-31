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

package com.liferay.portal.tools;

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.sass.SassFile;
import com.liferay.portal.tools.sass.SassFileWithMediaQuery;
import com.liferay.portal.tools.sass.SassString;
import com.liferay.portal.util.CSSBuilderUtil;
import com.liferay.portal.util.FastDateFormatFactoryImpl;
import com.liferay.portal.util.FileImpl;
import com.liferay.portal.util.PropsImpl;
import com.liferay.portal.util.PropsValues;
import com.liferay.sass.compiler.SassCompiler;
import com.liferay.sass.compiler.SassCompilerException;
import com.liferay.sass.compiler.jni.internal.JniSassCompiler;
import com.liferay.sass.compiler.ruby.internal.RubySassCompiler;

import java.io.File;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.tools.ant.DirectoryScanner;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Eduardo Lundgren
 * @author Shuyang Zhou
 * @author David Truong
 */
public class SassToCssBuilder {

	public static void main(String[] args) throws Exception {
		Map<String, String> arguments = ArgumentsUtil.parseArguments(args);

		List<String> dirNames = new ArrayList<>();

		String dirName = arguments.get("sass.dir");

		if (Validator.isNotNull(dirName)) {
			dirNames.add(dirName);
		}
		else {
			for (int i = 0;; i++ ) {
				dirName = arguments.get("sass.dir." + i);

				if (Validator.isNotNull(dirName)) {
					dirNames.add(dirName);
				}
				else {
					break;
				}
			}
		}

		String docrootDirName = arguments.get("sass.docroot.dir");
		String portalCommonDirName = arguments.get("sass.portal.common.dir");

		try {
			SassToCssBuilder sassToCssBuilder = new SassToCssBuilder(
				docrootDirName, portalCommonDirName);

			sassToCssBuilder.execute(dirNames);
		}
		catch (Exception e) {
			ArgumentsUtil.processMainException(arguments, e);
		}
	}

	public SassToCssBuilder(String docrootDirName, String portalCommonDirName)
		throws Exception {

		_docrootDirName = docrootDirName;
		_portalCommonDirName = portalCommonDirName;

		_initUtil();

		_initCompiler();
	}

	public void execute(List<String> dirNames) throws Exception {
		List<String> fileNames = new ArrayList<>();

		for (String dirName : dirNames) {
			_collectSassFiles(fileNames, dirName, _docrootDirName);
		}

		for (String fileName : fileNames) {
			_build(fileName);
		}

		for (SassFile sassFile : _sassFileCache.values()) {
			sassFile.writeCacheFiles();

			System.out.println(sassFile);
		}
	}

	private void _addSassString(
			SassFile sassFile, String fileName, String sassContent)
		throws Exception {

		sassContent = sassContent.trim();

		if (sassContent.isEmpty()) {
			return;
		}

		String cssContent = _parseSass(
			fileName, CSSBuilderUtil.parseStaticTokens(sassContent));

		sassFile.addSassFragment(new SassString(fileName, cssContent));
	}

	private SassFile _build(String fileName) throws Exception {
		SassFile sassFile = _sassFileCache.get(fileName);

		if (sassFile != null) {
			return sassFile;
		}

		sassFile = new SassFile(_docrootDirName, fileName);

		SassFile previousSassFile = _sassFileCache.putIfAbsent(
			fileName, sassFile);

		if (previousSassFile != null) {
			sassFile = previousSassFile;
		}
		else {
			_parseSassFile(sassFile);
		}

		return sassFile;
	}

	private void _collectSassFiles(
			List<String> fileNames, String dirName, String docrootDirName)
		throws Exception {

		DirectoryScanner directoryScanner = new DirectoryScanner();

		String basedir = docrootDirName.concat(dirName);

		directoryScanner.setBasedir(basedir);

		directoryScanner.setExcludes(
			new String[] {
				"**\\_diffs\\**", "**\\.sass-cache*\\**",
				"**\\.sass_cache_*\\**", "**\\_sass_cache_*\\**",
				"**\\_styled\\**", "**\\_unstyled\\**", "**\\tmp\\**"
			});
		directoryScanner.setIncludes(new String[] {"**\\*.css"});

		directoryScanner.scan();

		String[] fileNamesArray = directoryScanner.getIncludedFiles();

		if (!_isModified(basedir, fileNamesArray)) {
			return;
		}

		for (String fileName : fileNamesArray) {
			if (fileName.contains("_rtl")) {
				continue;
			}

			fileNames.add(_normalizeFileName(dirName, fileName));
		}
	}

	private String _fixRelativePath(String fileName) {
		String[] paths = StringUtil.split(fileName, CharPool.SLASH);

		StringBundler sb = new StringBundler(paths.length * 2);

		for (String path : paths) {
			if (path.isEmpty() || path.equals(StringPool.PERIOD)) {
				continue;
			}

			if (path.equals(StringPool.DOUBLE_PERIOD) && (sb.length() >= 2)) {
				sb.setIndex(sb.index() - 2);

				continue;
			}

			sb.append(StringPool.SLASH);
			sb.append(path);
		}

		return sb.toString();
	}

	private void _initCompiler() throws Exception {
		try {
			_sassCompiler = new JniSassCompiler();
		}
		catch (Throwable t) {
			_sassCompiler = new RubySassCompiler(
				PropsValues.SCRIPTING_JRUBY_COMPILE_MODE,
				PropsValues.SCRIPTING_JRUBY_COMPILE_THRESHOLD, _TMP_DIR);
		}
	}

	private void _initUtil() {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		FastDateFormatFactoryUtil fastDateFormatFactoryUtil =
			new FastDateFormatFactoryUtil();

		fastDateFormatFactoryUtil.setFastDateFormatFactory(
			new FastDateFormatFactoryImpl());

		FileUtil fileUtil = new FileUtil();

		fileUtil.setFile(new FileImpl());

		PortalClassLoaderUtil.setClassLoader(classLoader);

		PropsUtil.setProps(new PropsImpl());
	}

	private boolean _isModified(String dirName, String[] fileNames)
		throws Exception {

		for (String fileName : fileNames) {
			if (fileName.contains("_rtl")) {
				continue;
			}

			fileName = _normalizeFileName(dirName, fileName);

			File file = new File(fileName);
			File cacheFile = CSSBuilderUtil.getCacheFile(fileName);

			if (file.lastModified() != cacheFile.lastModified()) {
				return true;
			}
		}

		return false;
	}

	private String _normalizeFileName(String dirName, String fileName) {
		return StringUtil.replace(
			dirName + StringPool.SLASH + fileName,
			new String[] {StringPool.BACK_SLASH, StringPool.DOUBLE_SLASH},
			new String[] {StringPool.SLASH, StringPool.SLASH}
		);
	}

	private String _parseSass(String fileName, String content)
		throws SassCompilerException {

		String filePath = _docrootDirName.concat(fileName);

		String cssThemePath = filePath;

		int pos = filePath.lastIndexOf("/css/");

		if (pos >= 0) {
			cssThemePath = filePath.substring(0, pos + 4);
		}

		return _sassCompiler.compileString(
			content, _portalCommonDirName + File.pathSeparator + cssThemePath,
			"");
	}

	private void _parseSassFile(SassFile sassFile) throws Exception {
		String fileName = sassFile.getFileName();

		long start = System.currentTimeMillis();

		File file = new File(_docrootDirName, fileName);

		if (!file.exists()) {
			return;
		}

		String content = FileUtil.read(file);

		int pos = 0;

		StringBundler sb = new StringBundler();

		while (true) {
			int commentX = content.indexOf(_CSS_COMMENT_BEGIN, pos);
			int commentY = content.indexOf(
				_CSS_COMMENT_END, commentX + _CSS_COMMENT_BEGIN.length());

			int importX = content.indexOf(_CSS_IMPORT_BEGIN, pos);
			int importY = content.indexOf(
				_CSS_IMPORT_END, importX + _CSS_IMPORT_BEGIN.length());

			if ((importX == -1) || (importY == -1)) {
				sb.append(content.substring(pos));

				break;
			}
			else if ((commentX != -1) && (commentY != -1) &&
					 (commentX < importX) && (commentY > importX)) {

				commentY += _CSS_COMMENT_END.length();

				sb.append(content.substring(pos, commentY));

				pos = commentY;
			}
			else {
				sb.append(content.substring(pos, importX));

				String mediaQuery = StringPool.BLANK;

				int mediaQueryImportX = content.indexOf(
					CharPool.CLOSE_PARENTHESIS,
					importX + _CSS_IMPORT_BEGIN.length());
				int mediaQueryImportY = content.indexOf(
					CharPool.SEMICOLON, importX + _CSS_IMPORT_BEGIN.length());

				String importFileName = null;

				if (importY != mediaQueryImportX) {
					mediaQuery = content.substring(
						mediaQueryImportX + 1, mediaQueryImportY);

					importFileName = content.substring(
						importX + _CSS_IMPORT_BEGIN.length(),
						mediaQueryImportX);
				}
				else {
					importFileName = content.substring(
						importX + _CSS_IMPORT_BEGIN.length(), importY);
				}

				if (!importFileName.isEmpty()) {
					if (importFileName.charAt(0) != CharPool.SLASH) {
						importFileName = _fixRelativePath(
							sassFile.getBaseDir().concat(importFileName));
					}

					SassFile importSassFile = _build(importFileName);

					if (Validator.isNotNull(mediaQuery)) {
						sassFile.addSassFragment(
							new SassFileWithMediaQuery(
								importSassFile, mediaQuery));
					}
					else {
						sassFile.addSassFragment(importSassFile);
					}
				}

				// LEP-7540

				if (Validator.isNotNull(mediaQuery)) {
					pos = mediaQueryImportY + 1;
				}
				else {
					pos = importY + _CSS_IMPORT_END.length();
				}
			}
		}

		_addSassString(sassFile, fileName, sb.toString());

		String rtlCustomFileName = CSSBuilderUtil.getRtlCustomFileName(
			fileName);

		File rtlCustomFile = new File(_docrootDirName, rtlCustomFileName);

		if (rtlCustomFile.exists()) {
			_addSassString(
				sassFile, rtlCustomFileName, FileUtil.read(rtlCustomFile));
		}

		sassFile.setElapsedTime(System.currentTimeMillis() - start);
	}

	private static final String _CSS_COMMENT_BEGIN = "/*";

	private static final String _CSS_COMMENT_END = "*/";

	private static final String _CSS_IMPORT_BEGIN = "@import url(";

	private static final String _CSS_IMPORT_END = ");";

	private static final String _TMP_DIR = SystemProperties.get(
		SystemProperties.TMP_DIR);

	private final String _docrootDirName;
	private final String _portalCommonDirName;
	private SassCompiler _sassCompiler;
	private final ConcurrentMap<String, SassFile> _sassFileCache =
		new ConcurrentHashMap<>();

}