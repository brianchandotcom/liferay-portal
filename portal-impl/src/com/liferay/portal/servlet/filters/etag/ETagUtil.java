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

package com.liferay.portal.servlet.filters.etag;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.StringUtil;

import java.nio.ByteBuffer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class ETagUtil {

	public static boolean processETag(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, ByteBuffer byteBuffer) {

		if (httpServletResponse.isCommitted()) {
			return false;
		}

		int hashCode = _hashCode(
			byteBuffer.array(), byteBuffer.position(), byteBuffer.limit());

		String eTag = StringPool.QUOTE.concat(
			StringUtil.toHexString(hashCode)
		).concat(
			StringPool.QUOTE
		);

		httpServletResponse.setHeader(HttpHeaders.ETAG, eTag);

		String ifNoneMatch = httpServletRequest.getHeader(
			HttpHeaders.IF_NONE_MATCH);

		if (eTag.equals(ifNoneMatch)) {
			httpServletResponse.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			httpServletResponse.setContentLength(0);

			return true;
		}

		return false;
	}

	private static int _hashCode(byte[] data, int offset, int length) {
		int hashCode = 0;

		for (int i = 0; i < length; i++) {
			hashCode = 31 * hashCode + data[offset++];
		}

		return hashCode;
	}

}