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

package com.liferay.portal.webdav.methods;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.webdav.WebDAVException;
import com.liferay.portal.kernel.webdav.WebDAVRequest;
import com.liferay.portal.kernel.webdav.WebDAVStorage;
import com.liferay.portal.kernel.webdav.WebDAVUtil;
import com.liferay.portal.kernel.webdav.methods.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Alexander Chow
 */
public class UnlockMethodImpl implements Method {

	@Override
	public int process(WebDAVRequest webDAVRequest) throws WebDAVException {
		WebDAVStorage storage = webDAVRequest.getWebDAVStorage();

		String token = getToken(webDAVRequest.getHttpServletRequest());

		if (!storage.isSupportsClassTwo()) {
			return HttpServletResponse.SC_METHOD_NOT_ALLOWED;
		}

		if (storage.unlockResource(webDAVRequest, token)) {
			return HttpServletResponse.SC_NO_CONTENT;
		}

		return HttpServletResponse.SC_PRECONDITION_FAILED;
	}

	protected String getToken(HttpServletRequest request) {
		String token = StringPool.BLANK;

		String value = GetterUtil.getString(request.getHeader("Lock-Token"));

		if (_log.isDebugEnabled()) {
			_log.debug("\"Lock-Token\" header is " + value);
		}

		if (value.startsWith("<") && value.endsWith(">")) {
			value = value.substring(1, value.length() - 1);
		}

		int index = value.indexOf(WebDAVUtil.TOKEN_PREFIX);

		if (index >= 0) {
			index += WebDAVUtil.TOKEN_PREFIX.length();

			if (index < value.length()) {
				token = GetterUtil.getString(value.substring(index));
			}
		}

		return token;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UnlockMethodImpl.class);

}