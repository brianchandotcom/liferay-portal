/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.webdav.methods;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.webdav.Resource;
import com.liferay.portal.kernel.webdav.WebDAVException;
import com.liferay.portal.kernel.webdav.WebDAVRequest;
import com.liferay.portal.kernel.webdav.WebDAVStorage;
import com.liferay.portal.kernel.webdav.methods.Method;

import jakarta.servlet.http.HttpServletResponse;

import java.io.InputStream;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class GetMethodImpl implements Method {

	@Override
	public int process(WebDAVRequest webDAVRequest) throws WebDAVException {
		InputStream inputStream = null;

		try {
			WebDAVStorage storage = webDAVRequest.getWebDAVStorage();

			Resource resource = storage.getResource(webDAVRequest);

			if (resource == null) {
				return HttpServletResponse.SC_NOT_FOUND;
			}

			try {
				inputStream = resource.getContentAsStream();
			}
			catch (Exception exception) {
				_log.error(exception);
			}

			if (inputStream != null) {
				String contentType = resource.getContentType();
				String fileName = resource.getDisplayName();

				HttpServletResponse httpServletResponse =
					webDAVRequest.getHttpServletResponse();

				try {
					if (_isBrowserExecutableContentType(contentType)) {
						ServletResponseUtil.sendFile(
							webDAVRequest.getHttpServletRequest(),
							httpServletResponse, fileName, inputStream,
							resource.getSize(), contentType,
							HttpHeaders.CONTENT_DISPOSITION_ATTACHMENT);
					}
					else {
						ServletResponseUtil.sendFileWithRangeHeader(
							webDAVRequest.getHttpServletRequest(),
							httpServletResponse, fileName, inputStream,
							resource.getSize(), contentType);
					}
				}
				catch (Exception exception) {
					if (_log.isWarnEnabled()) {
						_log.warn(exception);
					}
				}

				return HttpServletResponse.SC_OK;
			}

			return HttpServletResponse.SC_NOT_FOUND;
		}
		catch (Exception exception) {
			throw new WebDAVException(exception);
		}
	}

	private boolean _isBrowserExecutableContentType(String contentType) {
		if (contentType == null) {
			return false;
		}

		String lowerCaseContentType = StringUtil.toLowerCase(contentType);

		if (lowerCaseContentType.startsWith("application/javascript") ||
			lowerCaseContentType.startsWith("application/xhtml+xml") ||
			lowerCaseContentType.startsWith("image/svg+xml") ||
			lowerCaseContentType.startsWith("text/html") ||
			lowerCaseContentType.startsWith("text/javascript")) {

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(GetMethodImpl.class);

}