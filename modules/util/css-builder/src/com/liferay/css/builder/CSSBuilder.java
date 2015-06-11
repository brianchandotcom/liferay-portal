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

import com.liferay.css.builder.sass.SassFile;
import com.liferay.css.builder.sass.SassFileWithMediaQuery;
import com.liferay.css.builder.sass.SassString;
import com.liferay.portal.tools.ArgumentsUtil;
import com.liferay.portal.tools.CSSBuilderUtil;
import com.liferay.sass.compiler.SassCompiler;
import com.liferay.sass.compiler.SassCompilerException;
import com.liferay.sass.compiler.jni.internal.JniSassCompiler;
import com.liferay.sass.compiler.ruby.internal.RubySassCompiler;

import java.io.File;

import java.nio.file.Files;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.lang.StringUtils;
import org.apache.tools.ant.DirectoryScanner;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Eduardo Lundgren
 * @author Shuyang Zhou
 * @author David Truong
 */
public class CSSBuilder {

	public static void main(String[] args) throws Exception {
		Map<String, String> arguments = ArgumentsUtil.parseArguments(args);

		List<String> dirNames = new ArrayList<>();

		String dirName = arguments.get("sass.dir");

		if ((dirName != null) && !dirName.equals("")) {
			dirNames.add(dirName);
		}
		else {
			for (int i = 0;; i++ ) {
				dirName = arguments.get("sass.dir." + i);

				if ((dirName != null) && !dirName.equals("")) {
					dirNames.add(dirName);
				}
				else {
					break;
				}
			}
		}

		String docrootDirName = arguments.get("sass.docroot.dir");
		String portalCommonDirName = arguments.get("sass.portal.common.dir");
		String sassCompilerClassName = arguments.get(
			"sass.compiler.class.name");

		try {
			CSSBuilder CSSBuilder = new CSSBuilder(
				docrootDirName, portalCommonDirName, sassCompilerClassName);

			CSSBuilder.execute(dirNames);
		}
		catch (Exception e) {
			ArgumentsUtil.processMainException(arguments, e);
		}
	}

	public CSSBuilder(
			String docrootDirName, String portalCommonDirName,
			String sassCompilerClassName)
		throws Exception {

		_docrootDirName = docrootDirName;
		_portalCommonDirName = portalCommonDirName;

		_initSassCompiler(sassCompilerClassName);
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
		String[] paths = fileName.split("/");

		StringBuilder sb = new StringBuilder(paths.length * 2);

		for (String path : paths) {
			if (path.isEmpty() || path.equals(".")) {
				continue;
			}

			if (path.equals("..") && (sb.length() >= 2)) {
				continue;
			}

			sb.append("/");
			sb.append(path);
		}

		return sb.toString();
	}

	private void _initSassCompiler(String sassCompilerClassName)
		throws Exception {

		if ((sassCompilerClassName == null) ||
			sassCompilerClassName.equals("jni")) {

			try {
				_sassCompiler = new JniSassCompiler();
			}
			catch (Throwable t) {
				System.out.println(
					"Unable to load native compiler, falling back to Ruby");

				_sassCompiler = new RubySassCompiler("jit", 5, _TMP_DIR);
			}
		}
		else {
			try {
				_sassCompiler = new RubySassCompiler("jit", 5, _TMP_DIR);
			}
			catch (Exception e) {
				System.out.println(
					"Unable to load Ruby compiler, falling back to native");

				_sassCompiler = new JniSassCompiler();
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
			File cacheFile = CSSBuilderUtil.getCacheFile(fileName);

			if (file.lastModified() != cacheFile.lastModified()) {
				return true;
			}
		}

		return false;
	}

	private String _normalizeFileName(String dirName, String fileName) {
		return StringUtils.replaceEach(
			dirName + "/" + fileName, new String[] {"\\", "//"},
			new String[] {"/", "/"}
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

		String content = _read(file);

		int pos = 0;

		StringBuilder sb = new StringBuilder();

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

				String mediaQuery = "";

				int mediaQueryImportX =
					content.indexOf(')', importX + _CSS_IMPORT_BEGIN.length());
				int mediaQueryImportY = content.indexOf(
					';', importX + _CSS_IMPORT_BEGIN.length());

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
					if (importFileName.charAt(0) != '/') {
						importFileName = _fixRelativePath(
							sassFile.getBaseDir().concat(importFileName));
					}

					SassFile importSassFile = _build(importFileName);

					if ((mediaQuery != null) && !mediaQuery.equals("")) {
						sassFile.addSassFragment(
							new SassFileWithMediaQuery(
								importSassFile, mediaQuery));
					}
					else {
						sassFile.addSassFragment(importSassFile);
					}
				}

				// LEP-7540

				if ((mediaQuery != null) && !mediaQuery.equals("")) {
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
			_addSassString(sassFile, rtlCustomFileName, _read(rtlCustomFile));
		}

		sassFile.setElapsedTime(System.currentTimeMillis() - start);
	}

	private String _read(File file) throws Exception {
		return new String(Files.readAllBytes(file.toPath()));
	}

	private static final String _CSS_COMMENT_BEGIN = "/*";

	private static final String _CSS_COMMENT_END = "*/";

	private static final String _CSS_IMPORT_BEGIN = "@import url(";

	private static final String _CSS_IMPORT_END = ");";

	private static final String _TMP_DIR = System.getProperty("java.io.tmpdir");

	private final String _docrootDirName;
	private final String _portalCommonDirName;
	private SassCompiler _sassCompiler;
	private final ConcurrentMap<String, SassFile> _sassFileCache =
		new ConcurrentHashMap<>();

}