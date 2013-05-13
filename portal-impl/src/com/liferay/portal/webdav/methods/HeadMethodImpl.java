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

import com.liferay.portal.kernel.webdav.Resource;
import com.liferay.portal.kernel.webdav.WebDAVException;
import com.liferay.portal.kernel.webdav.WebDAVRequest;
import com.liferay.portal.kernel.webdav.WebDAVStorage;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class HeadMethodImpl implements Method {

	@Override
	public int process(WebDAVRequest webDAVRequest) throws WebDAVException {
		try {
			WebDAVStorage storage = webDAVRequest.getWebDAVStorage();
			HttpServletResponse response =
				webDAVRequest.getHttpServletResponse();

			Resource resource = storage.getResource(webDAVRequest);

			if (resource == null) {
				return HttpServletResponse.SC_NOT_FOUND;
			}

			if (!resource.isCollection()) {
				response.setContentLength((int)resource.getSize());
			}

			return HttpServletResponse.SC_OK;
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

}