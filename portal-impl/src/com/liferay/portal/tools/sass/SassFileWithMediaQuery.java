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
import com.liferay.portal.kernel.util.StringBundler;

/**
 * @author Minhchau Dang
 */
public class SassFileWithMediaQuery extends BaseSassFragment {

	public SassFileWithMediaQuery(SassFile file, String mediaQuery) {
		_file = file;
		_mediaQuery = mediaQuery;
	}

	@Override
	protected String doGetLtrContent() throws Exception {
		StringBundler sb = new StringBundler();

		sb.append(_CSS_MEDIA_QUERY);
		sb.append(CharPool.SPACE);
		sb.append(_mediaQuery);
		sb.append(CharPool.OPEN_CURLY_BRACE);
		sb.append(_file.getLtrContent());
		sb.append(CharPool.CLOSE_CURLY_BRACE);

		return sb.toString();
	}

	@Override
	protected String doGetRtlContent() throws Exception {
		StringBundler sb = new StringBundler();

		sb.append(_CSS_MEDIA_QUERY);
		sb.append(CharPool.SPACE);
		sb.append(_mediaQuery);
		sb.append(CharPool.OPEN_CURLY_BRACE);
		sb.append(_file.getRtlContent());
		sb.append(CharPool.CLOSE_CURLY_BRACE);

		return sb.toString();
	}

	private static final String _CSS_MEDIA_QUERY = "@media";

	private static Log _log = LogFactoryUtil.getLog(
		SassFileWithMediaQuery.class);

	private SassFile _file;
	private String _mediaQuery;

}