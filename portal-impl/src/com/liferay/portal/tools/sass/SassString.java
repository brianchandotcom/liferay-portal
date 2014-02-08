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
import com.liferay.portal.servlet.filters.dynamiccss.RTLCSSUtil;
import com.liferay.portal.tools.SassToCssBuilder;

import java.util.concurrent.Callable;
public class SassString extends BaseSassFragment implements Callable<Void> {

	public SassString(
		SassExecutor sassExecutor, String fileName, String sassContent) {

		_sassExecutor = sassExecutor;
		_fileName = fileName;
		_sassContent = sassContent;
	}

	@Override
	public Void call() throws Exception {
		_sassContent = SassToCssBuilder.parseStaticTokens(_sassContent);

		String cssContent = _sassExecutor.parse(_fileName, _sassContent);

		if (_fileName.contains("_rtl")) {
			_ltrContent = StringPool.BLANK;
			_rtlContent = cssContent;
		}
		else {
			_ltrContent = cssContent;
			_rtlContent = RTLCSSUtil.getRtlCss(cssContent);
		}

		return null;
	}

	@Override
	protected String doGetLtrContent() throws Exception {
		return _ltrContent;
	}

	@Override
	protected String doGetRtlContent() throws Exception {
		return _rtlContent;
	}

	private static Log _log = LogFactoryUtil.getLog(SassString.class);

	private String _fileName;
	private String _ltrContent;
	private String _rtlContent;
	private String _sassContent;
	private SassExecutor _sassExecutor;

}