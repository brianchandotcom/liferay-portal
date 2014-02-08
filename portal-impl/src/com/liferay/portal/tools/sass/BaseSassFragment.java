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
public abstract class BaseSassFragment implements SassFragment {

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

	protected abstract String doGetLtrContent() throws Exception;

	protected abstract String doGetRtlContent() throws Exception;

	private static Log _log = LogFactoryUtil.getLog(BaseSassFragment.class);

	private String _ltrContent;
	private String _rtlContent;

}