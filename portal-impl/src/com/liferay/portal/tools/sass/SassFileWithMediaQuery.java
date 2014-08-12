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

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

/**
 * @author Minhchau Dang
 * @author Shuyang Zhou
 */
public class SassFileWithMediaQuery implements SassFragment {

	public SassFileWithMediaQuery(SassFile sassfile, String mediaQuery) {
		_sassFile = sassfile;
		_mediaQuery = mediaQuery;
	}

	@Override
	public String getLtrContent() {
		StringBundler sb = new StringBundler(6);

		sb.append(_CSS_MEDIA_QUERY);
		sb.append(StringPool.SPACE);
		sb.append(_mediaQuery);
		sb.append(StringPool.OPEN_CURLY_BRACE);
		sb.append(_sassFile.getLtrContent());
		sb.append(StringPool.CLOSE_CURLY_BRACE);

		return sb.toString();
	}

	@Override
	public String getRtlContent() {
		StringBundler sb = new StringBundler(6);

		sb.append(_CSS_MEDIA_QUERY);
		sb.append(StringPool.SPACE);
		sb.append(_mediaQuery);
		sb.append(StringPool.OPEN_CURLY_BRACE);
		sb.append(_sassFile.getRtlContent());
		sb.append(StringPool.CLOSE_CURLY_BRACE);

		return sb.toString();
	}

	private static final String _CSS_MEDIA_QUERY = "@media";

	private final String _mediaQuery;
	private final SassFile _sassFile;

}