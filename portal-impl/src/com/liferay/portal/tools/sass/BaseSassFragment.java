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
import com.liferay.portal.kernel.util.StringPool;

import java.util.concurrent.Callable;
public abstract class BaseSassFragment
	implements Callable<BaseSassFragment>, SassFragment {

	public BaseSassFragment(String fileName) {
		_fileName = fileName;
	}

	@Override
	public BaseSassFragment call() throws Exception {
		long start = System.currentTimeMillis();

		doCall();

		long end = System.currentTimeMillis();

		_elapsedTime = (end - start);

		return this;
	}

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

	protected abstract void doCall() throws Exception;

	protected abstract String doGetLtrContent() throws Exception;

	protected abstract String doGetRtlContent() throws Exception;

	protected long getElapsedTime() {
		return _elapsedTime;
	}

	protected String getFileName() {
		return _fileName;
	}

	private static Log _log = LogFactoryUtil.getLog(BaseSassFragment.class);

	private long _elapsedTime;
	private String _fileName;
	private String _ltrContent;
	private String _rtlContent;

}