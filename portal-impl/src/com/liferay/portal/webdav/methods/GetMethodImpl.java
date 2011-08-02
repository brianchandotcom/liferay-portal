/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class GetMethodImpl implements Method {

	public int process(WebDAVRequest webDavRequest) throws WebDAVException {
		InputStream is = null;

		try {
			WebDAVStorage storage = webDavRequest.getWebDAVStorage();
			HttpServletResponse response =
				webDavRequest.getHttpServletResponse();

			Resource resource = storage.getResource(webDavRequest);

			if (resource != null) {
				try {
					is = resource.getContentAsStream();
				}
				catch (Exception e) {
					if (_log.isErrorEnabled()) {
						_log.error(e.getMessage());
					}
				}
			}

			int status = HttpServletResponse.SC_NOT_FOUND;

			if (is != null) {
				try {
					response.setContentType(resource.getContentType());

					ServletResponseUtil.write(response, is);
				}
				catch (Exception e) {
					if (_log.isWarnEnabled()) {
						_log.warn(e);
					}
				}

				status = HttpServletResponse.SC_OK;
			}

			return status;
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(GetMethodImpl.class);

}