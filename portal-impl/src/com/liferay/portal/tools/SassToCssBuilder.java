/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.servlet.filters.dynamiccss.RTLCSSUtil;
import com.liferay.portal.util.FastDateFormatFactoryImpl;
import com.liferay.portal.util.FileImpl;
import com.liferay.portal.util.PortalImpl;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsImpl;

import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tools.ant.DirectoryScanner;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Eduardo Lundgren
 */
public class SassToCssBuilder {

	public static File getCacheFile(String fileName) {
		return getCacheFile(fileName, StringPool.BLANK);
	}

	public static File getCacheFile(String fileName, String suffix) {
		return new File(getCacheFileName(fileName, suffix));
	}

	public static String getCacheFileName(String fileName, String suffix) {
		String cacheFileName = StringUtil.replace(
			fileName, StringPool.BACK_SLASH, StringPool.SLASH);

		int x = cacheFileName.lastIndexOf(StringPool.SLASH);
		int y = cacheFileName.lastIndexOf(StringPool.PERIOD);

		return cacheFileName.substring(0, x + 1) + ".sass-cache/" +
			cacheFileName.substring(x + 1, y) + suffix +
				cacheFileName.substring(y);
	}

	public static String getContent(String docrootDirName, String fileName)
		throws Exception {

		File file = new File(docrootDirName.concat(fileName));

		String content = FileUtil.read(file);

		content = AggregateFilter.aggregateCss(
			new FileAggregateContext(docrootDirName, fileName), content);

		return parseStaticTokens(content);
	}

	public static String getRtlCustomFileName(String fileName) {
		int pos = fileName.lastIndexOf(StringPool.PERIOD);

		return fileName.substring(0, pos) + "_rtl" + fileName.substring(pos);
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

		String docrootDirName = arguments.get("sass.docroot.dir");
		String portalCommonDirName = arguments.get("sass.portal.common.dir");

		try {
			new SassToCssBuilder(dirNames, docrootDirName, portalCommonDirName);
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

	public SassToCssBuilder(
			List<String> dirNames, String docrootDirName,
			String portalCommonDirName)
		throws Exception {

		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		_initUtil(classLoader);

		_rubyScript = StringUtil.read(
			classLoader,
			"com/liferay/portal/servlet/filters/dynamiccss" +
				"/dependencies/main.rb");

		_tempDir = SystemProperties.get(SystemProperties.TMP_DIR);

		for (String dirName : dirNames) {

			// Create a new Ruby executor as a workaround for a bug with Ruby
			// that breaks "ant build-css" when it parses too many CSS files

			_rubyExecutor = new RubyExecutor();

			_rubyExecutor.setExecuteInSeparateThread(false);

			_parseSassDirectory(dirName, docrootDirName, portalCommonDirName);
		}
	}

	private void _cacheSass(
			String docrootDirName, String portalCommonDirName, String fileName)
		throws Exception {

		if (fileName.contains("_rtl")) {
			return;
		}

		File cacheFile = getCacheFile(docrootDirName.concat(fileName));

		String parsedContent = _parseSassFile(
			docrootDirName, portalCommonDirName, fileName);

		FileUtil.write(cacheFile, parsedContent);

		File file = new File(docrootDirName.concat(fileName));

		long lastModified = file.lastModified();

		cacheFile.setLastModified(lastModified);

		// Generate RTL cache

		File rtlCacheFile = getCacheFile(
			docrootDirName.concat(fileName), "_rtl");

		String rtlCss = RTLCSSUtil.getRtlCss(fileName, parsedContent);

		// Append custom CSS for RTL

		String rtlCustomFileName = getRtlCustomFileName(fileName);
		String rtlCustomFilePath = docrootDirName.concat(rtlCustomFileName);

		if (FileUtil.exists(rtlCustomFilePath)) {
			File rtlCustomFile = new File(rtlCustomFilePath);

			lastModified = rtlCustomFile.lastModified();

			String rtlCustomCss = _parseSassFile(
				docrootDirName, portalCommonDirName, rtlCustomFileName);

			rtlCss += rtlCustomCss;
		}

		FileUtil.write(rtlCacheFile, rtlCss);

		rtlCacheFile.setLastModified(lastModified);
	}

	private String _getCssThemePath(String fileName) {
		int pos = fileName.lastIndexOf("/css/");

		return fileName.substring(0, pos + 4);
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

		RTLCSSUtil.init();
	}

	private boolean _isModified(String dirName, String[] fileNames)
		throws Exception {

		for (String fileName : fileNames) {
			fileName = _normalizeFileName(dirName, fileName);

			File file = new File(fileName);
			File cacheFile = getCacheFile(fileName);

			if (file.lastModified() != cacheFile.lastModified()) {
				return true;
			}
		}

		return false;
	}

	private String _normalizeFileName(String dirName, String fileName) {
		return StringUtil.replace(
			dirName + StringPool.SLASH + fileName,
			new String[] {
				StringPool.BACK_SLASH, StringPool.DOUBLE_SLASH
			},
			new String[] {
				StringPool.SLASH, StringPool.SLASH
			}
		);
	}

	private void _parseSassDirectory(
			String dirName, String docrootDirName, String portalCommonDirName)
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

		String[] fileNames = directoryScanner.getIncludedFiles();

		if (!_isModified(basedir, fileNames)) {
			return;
		}

		for (String fileName : fileNames) {
			fileName = _normalizeFileName(dirName, fileName);

			try {
				long start = System.currentTimeMillis();

				_cacheSass(docrootDirName, portalCommonDirName, fileName);

				long end = System.currentTimeMillis();

				System.out.println(
					"Parsed " + docrootDirName + fileName + " in " +
						(end - start) + " ms");
			}
			catch (Exception e) {
				System.out.println("Unable to parse " + fileName);

				e.printStackTrace();
			}
		}
	}

	private String _parseSassFile(
			String docrootDirName, String portalCommonDirName, String fileName)
		throws Exception {

		String filePath = docrootDirName.concat(fileName);

		Map<String, Object> inputObjects = new HashMap<String, Object>();

		inputObjects.put("commonSassPath", portalCommonDirName);
		inputObjects.put("content", getContent(docrootDirName, fileName));
		inputObjects.put("cssRealPath", filePath);
		inputObjects.put("cssThemePath", _getCssThemePath(filePath));
		inputObjects.put("sassCachePath", _tempDir);

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		UnsyncPrintWriter unsyncPrintWriter = UnsyncPrintWriterPool.borrow(
			unsyncByteArrayOutputStream);

		inputObjects.put("out", unsyncPrintWriter);

		_rubyExecutor.eval(null, inputObjects, null, _rubyScript);

		unsyncPrintWriter.flush();

		return unsyncByteArrayOutputStream.toString();
	}

	private RubyExecutor _rubyExecutor;
	private String _rubyScript;
	private String _tempDir;

}