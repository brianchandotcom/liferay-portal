/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.servlet.filters.etag;

import com.liferay.petra.string.StringBundler;
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

		String eTag = StringBundler.concat(
			StringPool.QUOTE, StringUtil.toHexString(hashCode),
			StringPool.QUOTE);

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
			hashCode = (31 * hashCode) + data[offset++];
		}

		return hashCode;
	}

}