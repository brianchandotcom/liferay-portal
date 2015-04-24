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

package com.liferay.portal.tools.css.builder;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ModelHintsConstants;
import com.liferay.rtl.css.RTLCSSConverter;
import com.liferay.sass.compiler.jni.JniSassCompiler;

import java.io.File;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tools.ant.DirectoryScanner;

/**
 * @author David Truong
 */
public class CSSBuilder {

	public static void main(String[] args) throws Exception {
		new CSSBuilder(args);
	}

	public CSSBuilder(String[] args) throws Exception {
		Map<String, String> arguments = _parseArguments(args);

		_jniSassCompiler = new JniSassCompiler();
		_rtlCSSConverter = new RTLCSSConverter();

		List<String> dirNames = new ArrayList<>();

		String sassDir = arguments.get("sass.dir");

		if (sassDir != null) {
			dirNames.addAll(Arrays.asList(sassDir.split(",")));
		}

		_docrootDirName = arguments.get("sass.docroot.dir");
		_portalCommonDirName = arguments.get("sass.portal.common.dir");

		List<String> fileNames = new ArrayList<>();

		for (String dirName : dirNames) {
			_collectSassFiles(fileNames, dirName, _docrootDirName);
		}

		for (String fileName : fileNames) {
			try {
				_build(fileName);
			}
			catch (Exception e) {
				System.out.println("Could not build " + fileName);
			}
		}
	}

	public void _build(String fileName) throws Exception {
		long start = System.currentTimeMillis();

		String baseURL;

		int pos = fileName.lastIndexOf("/");

		if (pos != -1) {
			baseURL = fileName.substring(0, pos + 1);
		}
		else {
			baseURL = "";
		}

		String ltrContent = _parseFile(baseURL, fileName);

		String rtlContent = "";
		long rtlStart = System.currentTimeMillis();

		if (!_isExcludedPath(fileName)) {
			String rtlCustomFileName = _getRtlCustomFileName(fileName);

			File rtlCustomFile = new File(_docrootDirName, rtlCustomFileName);

			if (rtlCustomFile.exists()) {
				rtlContent = new String(
					Files.readAllBytes(rtlCustomFile.toPath()));
			}
			else {
				rtlContent = _rtlCSSConverter.process(ltrContent);
			}

			rtlContent = _updateRelativeURLs(rtlContent, baseURL);
		}

		long rtlFinished = System.currentTimeMillis() - rtlStart;

		_writeCacheFiles(fileName, ltrContent, rtlContent);

		long finished = System.currentTimeMillis() - start;

		StringBuilder parsedMessage = new StringBuilder(5);

		parsedMessage.append("Parsed ");
		parsedMessage.append(fileName);
		parsedMessage.append(" in ");
		parsedMessage.append(finished);
		parsedMessage.append("ms, rtl conversion took ");
		parsedMessage.append(rtlFinished);
		parsedMessage.append("ms of that time");

		System.out.println(parsedMessage.toString());
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
					"**\\_styled\\**", "**\\_unstyled\\**"
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
			if (path.isEmpty() || path.equals(".") || path.equals("..")) {
				continue;
			}

			sb.append("/");
			sb.append(path);
		}

		return sb.toString();
	}

	private File _getCacheFile(String fileName) {
		return _getCacheFile(fileName, "");
	}

	private File _getCacheFile(String fileName, String suffix) {
		return new File(_getCacheFileName(fileName, suffix));
	}

	private String _getCacheFileName(String fileName, String suffix) {
		String cacheFileName = StringUtil.replace(fileName, "\\", "/");

		int x = cacheFileName.lastIndexOf("/");
		int y = cacheFileName.lastIndexOf(".");

		return cacheFileName.substring(0, x + 1) + ".sass-cache/" +
			cacheFileName.substring(x + 1, y) + suffix +
			cacheFileName.substring(y);
	}

	private String _getRtlCustomFileName(String fileName) {
		int pos = fileName.lastIndexOf(".");

		return fileName.substring(0, pos) + "_rtl" + fileName.substring(pos);
	}

	private boolean _isExcludedPath(String filePath) {
		for (Pattern pattern : _patterns) {
			Matcher matcher = pattern.matcher(filePath);

			if (matcher.matches()) {
				return true;
			}
		}

		return false;
	}

	private boolean _isModified(String dirName, String[] fileNames)
		throws Exception {

		for (String fileName : fileNames) {
			if (fileName.contains("_rtl")) {
				continue;
			}

			fileName = _normalizeFileName(dirName, fileName);

			File file = new File(fileName);
			File cacheFile = _getCacheFile(fileName);

			if (file.lastModified() != cacheFile.lastModified()) {
				return true;
			}
		}

		return false;
	}

	private String _normalizeFileName(String dirName, String fileName) {
		return StringUtil.replace(
			dirName + "/" + fileName, new String[] {"\\", "//"},
			new String[] {"/", "/"}
		);
	}

	private Map<String, String> _parseArguments(String[] args) {
		Map<String, String> arguments = new HashMap<>();

		for (String arg : args) {
			int pos = arg.indexOf('=');

			if (pos <= 0) {
				throw new IllegalArgumentException("Bad argument " + arg);
			}

			String key = arg.substring(0, pos).trim();
			String value = arg.substring(pos + 1).trim();

			arguments.put(key, value);
		}

		return arguments;
	}

	private String _parseFile(String baseURL, String fileName)
		throws Exception {

		if (_fileCache.containsKey(fileName)) {
			return _fileCache.get(fileName);
		}

		File file = new File(_docrootDirName, fileName);

		if (!file.exists()) {
			return "";
		}

		String content = new String(Files.readAllBytes(file.toPath()));

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

				int mediaQueryImportX = content.indexOf(
					")", importX + _CSS_IMPORT_BEGIN.length());
				int mediaQueryImportY = content.indexOf(
					";", importX + _CSS_IMPORT_BEGIN.length());

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
							baseURL.concat(importFileName));
					}

					String importParsedContent = _parseFile(
						baseURL, importFileName);

					if ((mediaQuery != null) && !mediaQuery.equals("")) {
						sb.append(_CSS_MEDIA_QUERY);
						sb.append(" ");
						sb.append(mediaQuery);
						sb.append("{");
						sb.append(importParsedContent);
						sb.append("}");
					}
					else {
						sb.append(importParsedContent);
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

		String css = _updateRelativeURLs(
			_parseSass(fileName, _parseStaticTokens(sb.toString())), baseURL);

		_fileCache.put(fileName, css);

		return css;
	}

	private String _parseSass(String fileName, String content) {
		String filePath = _docrootDirName.concat(fileName);

		String cssThemePath = filePath;

		int pos = filePath.lastIndexOf("/css/");

		if (pos >= 0) {
			cssThemePath = filePath.substring(0, pos + 4);
		}

		try {
			return _jniSassCompiler.compileString(
				content,
				_portalCommonDirName + File.pathSeparator + cssThemePath, "");
		}
		catch (Exception e) {
			e.printStackTrace();

			System.out.println("Unable to parse " + fileName);
		}

		return content;
	}

	private String _parseStaticTokens(String content) {
		return StringUtil.replace(
				content,
				new String[] {
					"@model_hints_constants_text_display_height@",
					"@model_hints_constants_text_display_width@",
					"@model_hints_constants_textarea_display_height@",
					"@model_hints_constants_textarea_display_width@"
				},
				new String[] {
					ModelHintsConstants.TEXT_DISPLAY_HEIGHT,
					ModelHintsConstants.TEXT_DISPLAY_WIDTH,
					ModelHintsConstants.TEXTAREA_DISPLAY_HEIGHT,
					ModelHintsConstants.TEXTAREA_DISPLAY_WIDTH
				});
	}

	private String _updateRelativeURLs(String content, String baseURL) {
		content = StringUtil.replace(
			content, _CSS_PATH_TYPES, _CSS_PATH_PLACEHOLDERS);

		content = StringUtil.replace(
				content,
				new String[] {
					"[$RELATIVE_1$]", "[$RELATIVE_2$]", "[$RELATIVE_3$]"
				},
				new String[] {
					"url('" + baseURL, "url(\"" + baseURL, "url(" + baseURL
				});

		content = StringUtil.replace(
			content, _CSS_PATH_PLACEHOLDERS, _CSS_PATH_TYPES);

		return content;
	}

	private void _writeCacheFiles(
			String fileName, String ltrContent, String rtlContent)
		throws Exception {

		File ltrCacheFile = new File(
			_docrootDirName,
			_getCacheFileName(fileName, ""));

		_writeFile(ltrCacheFile, ltrContent);

		File ltrFile = new File(_docrootDirName, fileName);

		ltrCacheFile.setLastModified(ltrFile.lastModified());

		String rtlFileName = _getRtlCustomFileName(fileName);

		if (_isExcludedPath(fileName)) {
			return;
		}

		File rtlCacheFile = new File(
			_docrootDirName,
			_getCacheFileName(rtlFileName, ""));

		_writeFile(rtlCacheFile, rtlContent);

		rtlCacheFile.setLastModified(ltrFile.lastModified());
	}

	private void _writeFile(File file, String content) throws Exception {
		File parentFile = file.getParentFile();

		if (!parentFile.exists()) {
			parentFile.mkdirs();
		}

		Path path = Paths.get(file.toURI());
		Files.write(path, content.getBytes()); //creates, overwrites
	}

	private static final String _CSS_COMMENT_BEGIN = "/*";

	private static final String _CSS_COMMENT_END = "*/";

	private static final String _CSS_IMPORT_BEGIN = "@import url(";

	private static final String _CSS_IMPORT_END = ");";

	private static final String _CSS_MEDIA_QUERY = "@media";

	private final String[] _CSS_PATH_PLACEHOLDERS = new String[] {
		"[$EMPTY_1$]", "[$EMPTY_2$]", "[$EMPTY_3$]", "[$TOKEN_1$]",
		"[$TOKEN_2$]", "[$TOKEN_3$]", "[$ABSOLUTE_1$]", "[$ABSOLUTE_2$]",
		"[$ABSOLUTE_3$]", "[$ABSOLUTE_4$]", "[$ABSOLUTE_5$]", "[$ABSOLUTE_6$]",
		"[$ABSOLUTE_7$]", "[$ABSOLUTE_8$]", "[$ABSOLUTE_9$]", "[$ABSOLUTE_10$]",
		"[$ABSOLUTE_11$]", "[$ABSOLUTE_12$]", "[$RELATIVE_1$]",
		"[$RELATIVE_2$]", "[$RELATIVE_3$]"
	};

	private final String[] _CSS_PATH_TYPES = new String[] {
		"url('')", "url(\"\")", "url()", "url('@",
		"url(\"@", "url(@", "url('http://", "url(\"http://", "url(http://",
		"url('https://", "url(\"https://", "url(https://", "url('/", "url(\"/",
		"url(/", "url('data:", "url(\"data:", "url(data:", "url('", "url(\"",
		"url("
	};

	private final String _docrootDirName;
	private final Map<String, String> _fileCache = new HashMap<>();
	private final JniSassCompiler _jniSassCompiler;
	private final Pattern[] _patterns = {Pattern.compile(".*\\/ckeditor\\/.*")};
	private final String _portalCommonDirName;
	private final RTLCSSConverter _rtlCSSConverter;

}