/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.webdav.Resource;
import com.liferay.portal.kernel.webdav.WebDAVException;
import com.liferay.portal.kernel.webdav.WebDAVRequest;
import com.liferay.portal.kernel.webdav.WebDAVStorage;
import com.liferay.portal.kernel.webdav.WebDAVUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class MoveMethodImpl implements Method {

	public int process(WebDAVRequest webDavRequest) throws WebDAVException {
		WebDAVStorage storage = webDavRequest.getWebDAVStorage();
		HttpServletRequest request = webDavRequest.getHttpServletRequest();

		long companyId = webDavRequest.getCompanyId();
		String destination = WebDAVUtil.getDestination(
			request, storage.getRootPath());

		StringBundler sb = new StringBundler();

		if (_log.isInfoEnabled()) {
			sb.append("Destination is ");
			sb.append(destination);
		}

		int status = HttpServletResponse.SC_FORBIDDEN;

		if (!destination.equals(webDavRequest.getPath()) &&
			(WebDAVUtil.getGroupId(companyId, destination) ==
				webDavRequest.getGroupId())) {

			Resource resource = storage.getResource(webDavRequest);

			if (resource == null) {
				status = HttpServletResponse.SC_NOT_FOUND;
			}
			else {
				boolean overwrite = WebDAVUtil.isOverwrite(request);

				if (_log.isInfoEnabled()) {
					sb.append(", overwrite is ");
					sb.append(overwrite);

					_log.info(sb.toString());
				}

				if (resource.isCollection()) {
					status = storage.moveCollectionResource(
						webDavRequest, resource, destination, overwrite);
				}
				else {
					status = storage.moveSimpleResource(
						webDavRequest, resource, destination, overwrite);
				}
			}
		}

		return status;
	}

	private static Log _log = LogFactoryUtil.getLog(MoveMethodImpl.class);

}