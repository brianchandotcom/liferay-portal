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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.webdav.Resource;
import com.liferay.portal.kernel.webdav.WebDAVException;
import com.liferay.portal.kernel.webdav.WebDAVRequest;
import com.liferay.portal.kernel.webdav.WebDAVStorage;
import com.liferay.portal.kernel.webdav.WebDAVUtil;
import com.liferay.portal.kernel.webdav.methods.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class CopyMethodImpl implements Method {

	@Override
	public int process(WebDAVRequest webDAVRequest) throws WebDAVException {
		WebDAVStorage storage = webDAVRequest.getWebDAVStorage();
		HttpServletRequest httpServletRequest =
			webDAVRequest.getHttpServletRequest();

		long companyId = webDAVRequest.getCompanyId();
		String destination = WebDAVUtil.getDestination(
			httpServletRequest, storage.getRootPath());

		StringBundler sb = null;

		if (_log.isInfoEnabled()) {
			sb = new StringBundler(6);

			sb.append("Destination is ");
			sb.append(destination);
		}

		if (!destination.equals(webDAVRequest.getPath()) &&
			(WebDAVUtil.getGroupId(companyId, destination) ==
				webDAVRequest.getGroupId())) {

			Resource resource = storage.getResource(webDAVRequest);

			if (resource == null) {
				return HttpServletResponse.SC_NOT_FOUND;
			}

			if (resource.isCollection()) {
				boolean overwrite = WebDAVUtil.isOverwrite(httpServletRequest);
				long depth = WebDAVUtil.getDepth(httpServletRequest);

				if (_log.isInfoEnabled()) {
					sb.append(", overwrite is ");
					sb.append(overwrite);
					sb.append(", depth is ");
					sb.append(depth);

					_log.info(sb.toString());
				}

				return storage.copyCollectionResource(
					webDAVRequest, resource, destination, overwrite, depth);
			}

			boolean overwrite = WebDAVUtil.isOverwrite(httpServletRequest);

			if (_log.isInfoEnabled()) {
				sb.append(", overwrite is ");
				sb.append(overwrite);

				_log.info(sb.toString());
			}

			return storage.copySimpleResource(
				webDAVRequest, resource, destination, overwrite);
		}

		return HttpServletResponse.SC_FORBIDDEN;
	}

	private static final Log _log = LogFactoryUtil.getLog(CopyMethodImpl.class);

}