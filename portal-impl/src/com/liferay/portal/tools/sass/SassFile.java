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

package com.liferay.portal.tools.sass;

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.servlet.filters.dynamiccss.RTLCSSUtil;
import com.liferay.portal.tools.SassToCssBuilder;
import com.liferay.portal.util.AggregateUtil;
import com.liferay.portal.util.CSSBuilderUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author Minhchau Dang
 * @author Shuyang Zhou
 */
public class SassFile implements SassFragment {

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

	public void build() throws Exception {
		long start = System.currentTimeMillis();

		File file = new File(_docrootDirName, _fileName);

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
							_baseDir.concat(importFileName));
					}

					SassFile importSassFile = SassToCssBuilder.execute(
						_docrootDirName, importFileName);

					if (Validator.isNotNull(mediaQuery)) {
						_sassFragments.add(
							new SassFileWithMediaQuery(
								importSassFile, mediaQuery));
					}
					else {
						_sassFragments.add(importSassFile);
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

		String rtlCustomFileName = CSSBuilderUtil.getRtlCustomFileName(
			_fileName);

		File rtlCustomFile = new File(_docrootDirName, rtlCustomFileName);

		if (rtlCustomFile.exists()) {
			_addSassString(rtlCustomFileName, FileUtil.read(rtlCustomFile));
		}

		_elapsedTime = System.currentTimeMillis() - start;

		return;
	}

	@Override
	public String getLtrContent() {
		if (_ltrContent != null) {
			return _ltrContent;
		}

		StringBundler sb = new StringBundler(_sassFragments.size());

		for (SassFragment sassFragment : _sassFragments) {
			String ltrContent = sassFragment.getLtrContent();

			if (sassFragment instanceof SassFile) {
				SassFile sassFile = (SassFile)sassFragment;

				String baseURL = _BASE_URL.concat(sassFile._baseDir);

				ltrContent = AggregateUtil.updateRelativeURLs(
					ltrContent, baseURL);
			}

			sb.append(ltrContent);
		}

		_ltrContent = sb.toString();

		return _ltrContent;
	}

	@Override
	public String getRtlContent() {
		if (_rtlContent != null) {
			return _rtlContent;
		}

		StringBundler sb = new StringBundler(_sassFragments.size());

		for (SassFragment sassFragment : _sassFragments) {
			String rtlContent = sassFragment.getRtlContent();

			if (sassFragment instanceof SassFile) {
				SassFile sassFile = (SassFile)sassFragment;

				String baseURL = _BASE_URL.concat(sassFile._baseDir);

				rtlContent = AggregateUtil.updateRelativeURLs(
					rtlContent, baseURL);
			}

			sb.append(rtlContent);
		}

		_rtlContent = sb.toString();

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
		File ltrCacheFile = new File(
			_docrootDirName,
			CSSBuilderUtil.getCacheFileName(_fileName, StringPool.BLANK));

		FileUtil.write(ltrCacheFile, getLtrContent());

		File ltrFile = new File(_docrootDirName, _fileName);

		ltrCacheFile.setLastModified(ltrFile.lastModified());

		String rtlFileName = CSSBuilderUtil.getRtlCustomFileName(_fileName);

		if (RTLCSSUtil.isExcludedPath(_fileName)) {
			return;
		}

		File rtlCacheFile = new File(
			_docrootDirName,
			CSSBuilderUtil.getCacheFileName(rtlFileName, StringPool.BLANK));

		FileUtil.write(rtlCacheFile, getRtlContent());

		rtlCacheFile.setLastModified(ltrFile.lastModified());
	}

	private void _addSassString(String fileName, String sassContent)
		throws Exception {

		sassContent = sassContent.trim();

		if (sassContent.isEmpty()) {
			return;
		}

		_sassFragments.add(new SassString(fileName, sassContent));
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

	private static final String _BASE_URL = "@base_url@";

	private static final String _CSS_COMMENT_BEGIN = "/*";

	private static final String _CSS_COMMENT_END = "*/";

	private static final String _CSS_IMPORT_BEGIN = "@import url(";

	private static final String _CSS_IMPORT_END = ");";

	private final String _baseDir;
	private final String _docrootDirName;
	private long _elapsedTime;
	private final String _fileName;
	private String _ltrContent;
	private String _rtlContent;
	private final List<SassFragment> _sassFragments = new ArrayList<>();

}