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

import com.liferay.portal.kernel.flash.FlashMagicBytesUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.Range;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.webdav.Resource;
import com.liferay.portal.kernel.webdav.WebDAVException;
import com.liferay.portal.kernel.webdav.WebDAVRequest;
import com.liferay.portal.kernel.webdav.WebDAVStorage;
import com.liferay.portal.kernel.webdav.methods.Method;

import java.io.IOException;
import java.io.InputStream;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class GetMethodImpl implements Method {

	@Override
	public int process(WebDAVRequest webDAVRequest) throws WebDAVException {
		InputStream is = null;

		try {
			WebDAVStorage storage = webDAVRequest.getWebDAVStorage();
			HttpServletRequest request = webDAVRequest.getHttpServletRequest();
			HttpServletResponse response =
				webDAVRequest.getHttpServletResponse();

			Resource resource = storage.getResource(webDAVRequest);

			if (resource == null) {
				return HttpServletResponse.SC_NOT_FOUND;
			}

			try {
				is = resource.getContentAsStream();
			}
			catch (Exception e) {
				if (_log.isErrorEnabled()) {
					_log.error(e.getMessage());
				}
			}

			if (is != null) {
				String fileName = resource.getDisplayName();

				FlashMagicBytesUtil.Result flashMagicBytesUtilResult =
					FlashMagicBytesUtil.check(is);

				if (flashMagicBytesUtilResult.isFlash()) {
					fileName = FileUtil.stripExtension(fileName) + ".swf";
				}

				is = flashMagicBytesUtilResult.getInputStream();

				try {
					sendFileWithRangeHeader(
						request, response, fileName, is, resource.getSize(),
						resource.getContentType());
				}
				catch (Exception e) {
					if (_log.isWarnEnabled()) {
						_log.warn(e);
					}
				}

				return HttpServletResponse.SC_OK;
			}

			return HttpServletResponse.SC_NOT_FOUND;
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	protected void sendFileWithRangeHeader(
			HttpServletRequest request, HttpServletResponse response,
			String fileName, InputStream inputStream, long contentLength,
			String contentType)
		throws IOException {

		if (_log.isDebugEnabled()) {
			_log.debug("Accepting ranges for the file " + fileName);
		}

		response.setHeader(
			HttpHeaders.ACCEPT_RANGES, HttpHeaders.ACCEPT_RANGES_BYTES_VALUE);

		List<Range> ranges = null;

		try {
			ranges = ServletResponseUtil.getRanges(
				request, response, contentLength);
		}
		catch (IOException ioe) {
			if (_log.isErrorEnabled()) {
				_log.error(ioe);
			}

			response.setHeader(
				HttpHeaders.CONTENT_RANGE, "bytes */" + contentLength);

			response.sendError(
				HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);

			return;
		}

		if ((ranges == null) || ranges.isEmpty()) {
			ServletResponseUtil.sendFile(
				request, response, fileName, inputStream, contentLength,
				contentType);
		}
		else {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Request has range header " +
						request.getHeader(HttpHeaders.RANGE));
			}

			ServletResponseUtil.write(
				request, response, fileName, ranges, inputStream, contentLength,
				contentType);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(GetMethodImpl.class);

}