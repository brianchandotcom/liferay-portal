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

package com.liferay.portlet.journal.webdav;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.webdav.BaseResourceImpl;
import com.liferay.portal.kernel.webdav.BaseWebDAVStorageImpl;
import com.liferay.portal.kernel.webdav.Resource;
import com.liferay.portal.kernel.webdav.WebDAVException;
import com.liferay.portal.kernel.webdav.WebDAVRequest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class JournalWebDAVStorageImpl extends BaseWebDAVStorageImpl {

	@Override
	public int deleteResource(WebDAVRequest webDavRequest)
		throws WebDAVException {

		try {
			Resource resource = getResource(webDavRequest);

			if (resource == null) {
				return HttpServletResponse.SC_NOT_FOUND;
			}

			return HttpServletResponse.SC_FORBIDDEN;
		}
		catch (PortalException pe) {
			return HttpServletResponse.SC_FORBIDDEN;
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	public Resource getResource(WebDAVRequest webDavRequest)
		throws WebDAVException {

		try {
			String[] pathArray = webDavRequest.getPathArray();

			if (pathArray.length == 2) {
				String path = getRootPath() + webDavRequest.getPath();

				return new BaseResourceImpl(path, StringPool.BLANK, getToken());
			}
			else if (pathArray.length == 3) {
				String type = pathArray[2];

				return toResource(webDavRequest, type, false);
			}

			return null;
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	public List<Resource> getResources(WebDAVRequest webDavRequest)
		throws WebDAVException {

		return new ArrayList<Resource>();
	}

	@Override
	public int putResource(WebDAVRequest webDavRequest) throws WebDAVException {
		try {
			Resource resource = getResource(webDavRequest);

			if (resource == null) {
				return HttpServletResponse.SC_NOT_FOUND;
			}

			return HttpServletResponse.SC_FORBIDDEN;
		}
		catch (PortalException pe) {
			return HttpServletResponse.SC_FORBIDDEN;
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	protected Resource toResource(
		WebDAVRequest webDavRequest, String type, boolean appendPath) {

		String parentPath = getRootPath() + webDavRequest.getPath();

		String name = StringPool.BLANK;

		if (appendPath) {
			name = type;
		}

		Resource resource = new BaseResourceImpl(parentPath, name, type);

		resource.setModel(type);

		return resource;
	}

}