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

import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.webdav.Status;
import com.liferay.portal.kernel.webdav.WebDAVException;
import com.liferay.portal.kernel.webdav.WebDAVRequest;
import com.liferay.portal.kernel.webdav.WebDAVStorage;
import com.liferay.portal.util.PortalUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class MkcolMethodImpl implements Method {

	@Override
	public int process(WebDAVRequest webDAVRequest) throws WebDAVException {
		WebDAVStorage storage = webDAVRequest.getWebDAVStorage();
		HttpServletRequest request = webDAVRequest.getHttpServletRequest();
		HttpServletResponse response = webDAVRequest.getHttpServletResponse();
		long groupId = webDAVRequest.getGroupId();

		if (groupId != 0) {
			Status status = storage.makeCollection(webDAVRequest);

			if (Validator.isNotNull(status.getObject())) {
				response.setHeader(
					HttpHeaders.LOCATION,
					PortalUtil.getPortalURL(request) +
						webDAVRequest.getRootPath() + StringPool.SLASH +
							status.getObject());
			}

			return status.getCode();
		}

		return HttpServletResponse.SC_FORBIDDEN;
	}

}