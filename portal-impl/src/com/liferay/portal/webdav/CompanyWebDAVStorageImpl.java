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

package com.liferay.portal.webdav;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.webdav.BaseResourceImpl;
import com.liferay.portal.kernel.webdav.BaseWebDAVStorageImpl;
import com.liferay.portal.kernel.webdav.Resource;
import com.liferay.portal.kernel.webdav.WebDAVException;
import com.liferay.portal.kernel.webdav.WebDAVRequest;
import com.liferay.portal.kernel.webdav.WebDAVUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Chow
 */
public class CompanyWebDAVStorageImpl extends BaseWebDAVStorageImpl {

	@Override
	public Resource getResource(WebDAVRequest webDAVRequest) {
		String path = getRootPath() + webDAVRequest.getPath();

		return new BaseResourceImpl(path, StringPool.BLANK, StringPool.BLANK);
	}

	@Override
	public List<Resource> getResources(WebDAVRequest webDAVRequest)
		throws WebDAVException {

		try {
			return getResources(webDAVRequest.getUserId());
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	protected List<Resource> getResources(long userId) throws Exception {
		User user = UserLocalServiceUtil.getUserById(userId);

		List<Group> groups = WebDAVUtil.getGroups(user);

		List<Resource> resources = new ArrayList<>(groups.size());

		for (Group group : groups) {
			String parentPath = getRootPath();

			String name = group.getFriendlyURL();

			name = name.substring(1);

			resources.add(new BaseResourceImpl(parentPath, name, name));
		}

		return resources;
	}

}