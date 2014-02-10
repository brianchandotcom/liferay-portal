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

package com.liferay.portal.tools.sass;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.SassToCssBuilder;
import com.liferay.portal.util.AggregateUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author Minhchau Dang
 */
public class SassFile implements Callable<SassFile>, SassFragment {

	public SassFile(String docrootDirName, String fileName) {
		_docrootDirName = docrootDirName;
		_fileName = fileName;

		int pos = fileName.lastIndexOf(CharPool.SLASH);

		if (pos != -1) {
			_baseDir = fileName.substring(0, pos + 1);
		}
		else {
			_baseDir = StringPool.BLANK;
		}
	}

	@Override
	public SassFile call() throws Exception {
		long start = System.currentTimeMillis();

		File file = new File(_docrootDirName, _fileName);

		if (!file.exists()) {
			return this;
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

				if (importFileName.length() > 0) {
					if (importFileName.charAt(0) != CharPool.SLASH) {
						importFileName = _baseDir.concat(importFileName);

						importFileName = _fixRelativePath(importFileName);
					}

					SassFile importFile = SassExecutorUtil.execute(
						_docrootDirName, importFileName);

					if (Validator.isNotNull(mediaQuery)) {
						_fragments.add(
							new SassFileWithMediaQuery(importFile, mediaQuery));
					}
					else {
						_fragments.add(importFile);
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

		_addSassString(_fileName, sb.toString());

		String rtlCustomFileName = SassToCssBuilder.getRtlCustomFileName(
			_fileName);

		File rtlCustomFile = new File(_docrootDirName, rtlCustomFileName);

		if (rtlCustomFile.exists()) {
			_addSassString(rtlCustomFileName, FileUtil.read(rtlCustomFile));
		}

		_elapsedTime = System.currentTimeMillis() - start;

		return this;
	}

	@Override
	public String getLtrContent() throws Exception {
		if (_ltrContent != null) {
			return _ltrContent;
		}

		_ltrContent = doGetLtrContent();

		if (_ltrContent == null) {
			_ltrContent = StringPool.BLANK;
		}

		return _ltrContent;
	}

	@Override
	public String getRtlContent() throws Exception {
		if (_rtlContent != null) {
			return _rtlContent;
		}

		_rtlContent = doGetRtlContent();

		if (_rtlContent == null) {
			_rtlContent = StringPool.BLANK;
		}

		return _rtlContent;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append("Parsed ");
		sb.append(_fileName);
		sb.append(" in ");
		sb.append(_elapsedTime);
		sb.append("ms");

		return sb.toString();
	}

	public void writeCacheFiles() throws Exception {
		File ltrFile = new File(_docrootDirName, _fileName);
		String ltrCacheFileName = SassToCssBuilder.getCacheFileName(
			_fileName, StringPool.BLANK);
		File ltrCacheFile = new File(_docrootDirName, ltrCacheFileName);

		FileUtil.write(ltrCacheFile, getLtrContent());
		ltrCacheFile.setLastModified(ltrFile.lastModified());

		String rtlFileName = SassToCssBuilder.getRtlCustomFileName(_fileName);
		File rtlFile = new File(_docrootDirName, rtlFileName);
		String rtlCacheFileName = SassToCssBuilder.getCacheFileName(
			rtlFileName, StringPool.BLANK);
		File rtlCacheFile = new File(_docrootDirName, rtlCacheFileName);

		FileUtil.write(rtlCacheFile, getRtlContent());

		if (rtlFile.exists()) {
			rtlCacheFile.setLastModified(rtlFile.lastModified());
		}
		else {
			rtlCacheFile.setLastModified(ltrFile.lastModified());
		}
	}

	protected String doGetLtrContent() throws Exception {
		StringBundler sb = new StringBundler();

		for (SassFragment fragment : _fragments) {
			String ltrContent = fragment.getLtrContent();

			if (fragment instanceof SassFile) {
				SassFile file = (SassFile)fragment;

				String baseURL = _BASE_URL.concat(file._baseDir);

				ltrContent = AggregateUtil.updateRelativeURLs(
					ltrContent, baseURL);
			}

			sb.append(ltrContent);
		}

		return sb.toString();
	}

	protected String doGetRtlContent() throws Exception {
		StringBundler sb = new StringBundler();

		for (SassFragment fragment : _fragments) {
			String rtlContent = fragment.getRtlContent();

			if (fragment instanceof SassFile) {
				SassFile file = (SassFile)fragment;

				String baseURL = _BASE_URL.concat(file._baseDir);

				rtlContent = AggregateUtil.updateRelativeURLs(
					rtlContent, baseURL);
			}

			sb.append(rtlContent);
		}

		return sb.toString();
	}

	private void _addSassString(String fileName, String sassContent)
		throws Exception {

		sassContent = sassContent.trim();

		if (sassContent.isEmpty()) {
			return;
		}

		_fragments.add(new SassString(fileName, sassContent));
	}

	private String _fixRelativePath(String fileName) {
		int x = fileName.indexOf("/./");

		while (x != -1) {
			fileName = fileName.substring(0, x) + fileName.substring(x + 2);

			x = fileName.indexOf("/./");
		}

		int y = fileName.indexOf("/../");

		while (y != -1) {
			x = fileName.lastIndexOf(CharPool.SLASH, y - 1);

			if (x != -1) {
				fileName = fileName.substring(0, x) + fileName.substring(y + 3);
			}

			y = fileName.indexOf("/../");
		}

		return fileName;
	}

	private static final String _BASE_URL = "@base_url@";

	private static final String _CSS_COMMENT_BEGIN = "/*";

	private static final String _CSS_COMMENT_END = "*/";

	private static final String _CSS_IMPORT_BEGIN = "@import url(";

	private static final String _CSS_IMPORT_END = ");";

	private static Log _log = LogFactoryUtil.getLog(SassFile.class);

	private String _baseDir;
	private String _docrootDirName;
	private long _elapsedTime;
	private String _fileName;
	private List<SassFragment> _fragments = new ArrayList<SassFragment>();
	private String _ltrContent;
	private String _rtlContent;

}