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

package com.liferay.portal.webdav;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.webdav.BaseResourceImpl;
import com.liferay.portal.kernel.webdav.BaseWebDAVStorageImpl;
import com.liferay.portal.kernel.webdav.Resource;
import com.liferay.portal.kernel.webdav.WebDAVException;
import com.liferay.portal.kernel.webdav.WebDAVRequest;
import com.liferay.portal.kernel.webdav.WebDAVUtil;
import com.liferay.portal.model.Group;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Chow
 */
public class GroupWebDAVStorageImpl extends BaseWebDAVStorageImpl {

	@Override
	public Resource getResource(WebDAVRequest webDAVRequest)
		throws WebDAVException {

		verifyGroup(webDAVRequest);

		String path = getRootPath() + webDAVRequest.getPath();

		return new BaseResourceImpl(path, StringPool.BLANK, StringPool.BLANK);
	}

	@Override
	public List<Resource> getResources(WebDAVRequest webDAVRequest)
		throws WebDAVException {

		verifyGroup(webDAVRequest);

		List<Resource> resources = new ArrayList<Resource>();

		String path = getRootPath() + webDAVRequest.getPath();

		for (String token : WebDAVUtil.getStorageTokens()) {
			resources.add(new BaseResourceImpl(path, token, token));
		}

		return resources;
	}

	protected void verifyGroup(WebDAVRequest webDAVRequest)
		throws WebDAVException {

		String path = webDAVRequest.getPath();

		try {
			long userId = webDAVRequest.getUserId();

			List<Group> groups = WebDAVUtil.getGroups(userId);

			for (Group group : groups) {
				if (path.equals(group.getFriendlyURL())) {
					return;
				}
			}
		}
		catch (Exception e) {
		}

		throw new WebDAVException(
			"Invalid group for given credentials " +
				webDAVRequest.getRootPath() + path);
	}

}