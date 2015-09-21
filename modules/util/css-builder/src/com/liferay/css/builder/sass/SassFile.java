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

package com.liferay.css.builder.sass;

import com.liferay.css.builder.CSSBuilder;
import com.liferay.css.builder.CSSBuilderUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.AggregateUtil;

import java.io.File;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Minhchau Dang
 * @author Shuyang Zhou
 */
public class SassFile implements SassFragment {

	public SassFile(
		CSSBuilder cssBuilder, String docrootDirName, String fileName) {

		_cssBuilder = cssBuilder;
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

	public void addSassFragment(SassFragment sassFragment) {
		_sassFragments.add(sassFragment);
	}

	public String getBaseDir() {
		return _baseDir;
	}

	public String getFileName() {
		return _fileName;
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

	public void setElapsedTime(long elapsedTime) {
		_elapsedTime = elapsedTime;
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

		_write(ltrCacheFile, getLtrContent());

		File ltrFile = new File(_docrootDirName, _fileName);

		ltrCacheFile.setLastModified(ltrFile.lastModified());

		String rtlFileName = CSSBuilderUtil.getRtlCustomFileName(_fileName);

		if (_cssBuilder.isRtlExcludedPath(_fileName)) {
			return;
		}

		File rtlCacheFile = new File(
			_docrootDirName,
			CSSBuilderUtil.getCacheFileName(rtlFileName, StringPool.BLANK));

		_write(rtlCacheFile, getRtlContent());

		rtlCacheFile.setLastModified(ltrFile.lastModified());
	}

	private void _write(File file, String content) throws Exception {
		File parentFile = file.getParentFile();

		if (!parentFile.exists()) {
			parentFile.mkdirs();
		}

		Path path = Paths.get(file.toURI());

		Files.write(path, content.getBytes(StringPool.UTF8));
	}

	private static final String _BASE_URL = "@base_url@";

	private final String _baseDir;
	private final CSSBuilder _cssBuilder;
	private final String _docrootDirName;
	private long _elapsedTime;
	private final String _fileName;
	private String _ltrContent;
	private String _rtlContent;
	private final List<SassFragment> _sassFragments = new ArrayList<>();

}