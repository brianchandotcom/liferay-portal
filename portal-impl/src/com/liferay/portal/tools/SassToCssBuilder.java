/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncPrintWriter;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.UnsyncPrintWriterPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelHintsConstants;
import com.liferay.portal.scripting.ruby.RubyExecutor;
import com.liferay.portal.servlet.filters.aggregate.AggregateFilter;
import com.liferay.portal.servlet.filters.aggregate.FileAggregateContext;
import com.liferay.portal.util.FastDateFormatFactoryImpl;
import com.liferay.portal.util.FileImpl;
import com.liferay.portal.util.PortalImpl;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsImpl;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tools.ant.DirectoryScanner;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class SassToCssBuilder {

	public static File getCacheFile(String fileName) {
		fileName = StringUtil.replace(
			fileName, StringPool.BACK_SLASH, StringPool.SLASH);

		int pos = fileName.lastIndexOf(StringPool.SLASH);

		String cacheFileName =
			fileName.substring(0, pos + 1) + ".sass-cache/" +
				fileName.substring(pos + 1);

		return new File(cacheFileName);
	}

	public static void main(String[] args) {
		Map<String, String> arguments = ArgumentsUtil.parseArguments(args);

		List<String> dirNames = new ArrayList<String>();

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

		try {
			new SassToCssBuilder(dirNames);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String parseStaticTokens(String content) {
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

	public SassToCssBuilder(List<String> dirNames) throws Exception {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		_initUtil(classLoader);

		_rubyScript = StringUtil.read(
			classLoader,
			"com/liferay/portal/servlet/filters/dynamiccss/main.rb");

		_tempDir = SystemProperties.get(SystemProperties.TMP_DIR);

		for (String dirName : dirNames) {

			// Create a new Ruby executor as a workaround for a bug with Ruby
			// that breaks "ant build-css" when it parses too many CSS files

			_rubyExecutor = new RubyExecutor();

			_parseSassDirectory(dirName);
		}
	}

	private String _getContent(File file) throws Exception {
		String content = FileUtil.read(file);

		content = AggregateFilter.aggregateCss(
			new FileAggregateContext(file), content);

		return parseStaticTokens(content);
	}

	private String _getCssThemePath(String fileName) {
		int pos = fileName.lastIndexOf("/css/");

		return fileName.substring(0, pos + 4);
	}

	private List<String> _getFilesURLsWhereFileIsImported(
		String fileURLToFind, List<String> fileURLsWhereSearching)
			throws Exception {

		List<String> filesWhereImported = new ArrayList<String>();

		for (String fileURL : fileURLsWhereSearching) {
			File file = new File (fileURL);

			String content = FileUtil.read(file);

			if (_isImportedFile(fileURLToFind, content, file.getParent())) {
				filesWhereImported.add(fileURL);
			}
		}

		return filesWhereImported;
	}

	private String[] _getFileURLsToParse(
		String CSSFilesDirName, String[] CSSFileNames) throws Exception {

		List<String> modifiedFileURLs = new ArrayList<String>();
		List<String> unModifiedFileURLs = new ArrayList<String>();

		for (String fileName : CSSFileNames) {
			String fileURL = StringUtil.replace(
				CSSFilesDirName + StringPool.SLASH + fileName,
				StringPool.BACK_SLASH, StringPool.SLASH);

			long lastModifiedFile = (new File(fileURL)).lastModified();
			long lastModifiedCacheFile = getCacheFile(fileURL).lastModified();

			if (lastModifiedFile == lastModifiedCacheFile) {
				unModifiedFileURLs.add(fileURL);
			}
			else {
				modifiedFileURLs.add(fileURL);
			}
		}

		if ((modifiedFileURLs.size() > 0) && (unModifiedFileURLs.size() > 0)) {
			List<String> indirectlyModifiedFiles =
				_getIndirectlyModifiedFileURLs(
					modifiedFileURLs, unModifiedFileURLs);

			modifiedFileURLs.addAll(indirectlyModifiedFiles);
		}

		return modifiedFileURLs.toArray(new String[modifiedFileURLs.size()]);
	}

	private List<String> _getIndirectlyModifiedFileURLs(
		List<String> modifiedFileURLs, List<String> unmodifiedFileURLs)
			throws Exception {

		List<String> indirectlyModifiedFiles = new ArrayList<String>();

		for (String modifiedFileURL : modifiedFileURLs) {
			List<String> filesWhereFileIsImported =
				_getFilesURLsWhereFileIsImported(
					modifiedFileURL, unmodifiedFileURLs);

			indirectlyModifiedFiles.addAll(filesWhereFileIsImported);

			unmodifiedFileURLs.removeAll(filesWhereFileIsImported);

			if (unmodifiedFileURLs.size() == 0) {
				break;
			}
		}

		if ((indirectlyModifiedFiles.size() > 0) &&
			(unmodifiedFileURLs.size() > 0)) {

			indirectlyModifiedFiles.addAll(
				_getIndirectlyModifiedFileURLs(
					indirectlyModifiedFiles, unmodifiedFileURLs));
		}

		return indirectlyModifiedFiles;
	}

	private void _initUtil(ClassLoader classLoader) {
		FastDateFormatFactoryUtil fastDateFormatFactoryUtil =
			new FastDateFormatFactoryUtil();

		fastDateFormatFactoryUtil.setFastDateFormatFactory(
			new FastDateFormatFactoryImpl());

		FileUtil fileUtil = new FileUtil();

		fileUtil.setFile(new FileImpl());

		PortalClassLoaderUtil.setClassLoader(classLoader);

		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(new PortalImpl());

		PropsUtil.setProps(new PropsImpl());
	}

	private boolean _isImportedFile(
		String fileURLtoFind, String contentWhereSearching, String contentDir)
			throws IOException {

		int pos = 0;

		while (true) {
			int commentX = contentWhereSearching.indexOf(
				_CSS_COMMENT_BEGIN, pos);
			int commentY = contentWhereSearching.indexOf(
				_CSS_COMMENT_END, commentX + _CSS_COMMENT_BEGIN.length());

			int importX = contentWhereSearching.indexOf(_CSS_IMPORT_BEGIN, pos);
			int importY = contentWhereSearching.indexOf(
				StringPool.CLOSE_PARENTHESIS,
				importX + _CSS_IMPORT_BEGIN.length());

			if ((importX == -1) || (importY == -1)) {
				break;
			}
			else if ((commentX != -1) && (commentY != -1) &&
					 (commentX < importX) && (commentY > importX)) {

				commentY += _CSS_COMMENT_END.length();

				pos = commentY;
			}
			else {
				String importedFileName = null;

				importedFileName = contentWhereSearching.substring(
					importX + _CSS_IMPORT_BEGIN.length(), importY).trim();

				String importedFileURL = StringUtil.replace(
					contentDir + StringPool.SLASH + importedFileName,
					StringPool.BACK_SLASH, StringPool.SLASH);

				File importedFile = new File(importedFileURL);

				String canonicalPath = importedFile.getCanonicalPath().replace(
					StringPool.BACK_SLASH, StringPool.SLASH);

				if (canonicalPath.equals(fileURLtoFind)) {
					return true;
				}

				pos = importY + StringPool.CLOSE_PARENTHESIS.length();
			}
		}

		return false;
	}

	private void _parseSassDirectory(String dirName) throws Exception {
		DirectoryScanner directoryScanner = new DirectoryScanner();

		directoryScanner.setBasedir(dirName);
		directoryScanner.setExcludes(
			new String[] {
				"**\\_diffs\\**", "**\\.sass-cache*\\**",
				"**\\.sass_cache_*\\**", "**\\_sass_cache_*\\**",
				"**\\_styled\\**", "**\\_unstyled\\**"
			});
		directoryScanner.setIncludes(new String[] {"**\\*.css"});

		directoryScanner.scan();

		String[] fileNames = directoryScanner.getIncludedFiles();

		String[] fileURLsToParse = _getFileURLsToParse(dirName, fileNames);

		for (String fileURL : fileURLsToParse) {
			try {
				long start = System.currentTimeMillis();

				_parseSassFile(fileURL);

				long end = System.currentTimeMillis();

				System.out.println(
					"Parsed " + fileURL + " in " + (end - start) + " ms");
			}
			catch (Exception e) {
				System.out.println("Unable to parse " + fileURL);

				e.printStackTrace();
			}
		}
	}

	private void _parseSassFile(String fileName) throws Exception {
		File file = new File(fileName);
		File cacheFile = getCacheFile(fileName);

		Map<String, Object> inputObjects = new HashMap<String, Object>();

		inputObjects.put("content", _getContent(file));
		inputObjects.put("cssRealPath", fileName);
		inputObjects.put("cssThemePath", _getCssThemePath(fileName));
		inputObjects.put("sassCachePath", _tempDir);

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		UnsyncPrintWriter unsyncPrintWriter = UnsyncPrintWriterPool.borrow(
			unsyncByteArrayOutputStream);

		inputObjects.put("out", unsyncPrintWriter);

		_rubyExecutor.eval(null, inputObjects, null, _rubyScript);

		unsyncPrintWriter.flush();

		String parsedContent = unsyncByteArrayOutputStream.toString();

		FileUtil.write(cacheFile, parsedContent);

		cacheFile.setLastModified(file.lastModified());
	}

	private static final String _CSS_COMMENT_BEGIN = "/*";

	private static final String _CSS_COMMENT_END = "*/";

	private static final String _CSS_IMPORT_BEGIN = "@import url(";

	private RubyExecutor _rubyExecutor;
	private String _rubyScript;
	private String _tempDir;

}