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

package com.liferay.portal.webdav.methods;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.webdav.Resource;
import com.liferay.portal.kernel.webdav.WebDAVException;
import com.liferay.portal.kernel.webdav.WebDAVRequest;
import com.liferay.portal.kernel.webdav.WebDAVStorage;

import java.io.InputStream;

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
				try {
					ServletResponseUtil.sendFile(
						request, response, resource.getDisplayName(), is,
						resource.getSize(), resource.getContentType());
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

	private static Log _log = LogFactoryUtil.getLog(GetMethodImpl.class);

}