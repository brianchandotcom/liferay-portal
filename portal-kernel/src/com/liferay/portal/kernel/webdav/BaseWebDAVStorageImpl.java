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

package com.liferay.portal.kernel.webdav;

import com.liferay.portal.kernel.lock.Lock;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.webdav.methods.MethodFactory;
import com.liferay.portal.kernel.webdav.methods.MethodFactoryRegistryUtil;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 * @author Fabio Pezzutto
 */
public abstract class BaseWebDAVStorageImpl implements WebDAVStorage {

	@Override
	public int copyCollectionResource(
			WebDAVRequest webDAVRequest, Resource resource, String destination,
			boolean overwrite, long depth)
		throws WebDAVException {

		return HttpServletResponse.SC_FORBIDDEN;
	}

	@Override
	public int copySimpleResource(
			WebDAVRequest webDAVRequest, Resource resource, String destination,
			boolean overwrite)
		throws WebDAVException {

		return HttpServletResponse.SC_FORBIDDEN;
	}

	@Override
	public int deleteResource(WebDAVRequest webDAVRequest)
		throws WebDAVException {

		return HttpServletResponse.SC_FORBIDDEN;
	}

	@Override
	public MethodFactory getMethodFactory() {
		return MethodFactoryRegistryUtil.getDefaultMethodFactory();
	}

	@Override
	public String getRootPath() {
		return _rootPath;
	}

	@Override
	public String getToken() {
		return _token;
	}

	@Override
	public boolean isAvailable(WebDAVRequest webDAVRequest)
		throws WebDAVException {

		if (getResource(webDAVRequest) == null) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isSupportsClassTwo() {
		return false;
	}

	@Override
	public Status lockResource(
			WebDAVRequest webDAVRequest, String owner, long timeout)
		throws WebDAVException {

		return null;
	}

	@Override
	public Status makeCollection(WebDAVRequest webDAVRequest)
		throws WebDAVException {

		return new Status(HttpServletResponse.SC_FORBIDDEN);
	}

	@Override
	public int moveCollectionResource(
			WebDAVRequest webDAVRequest, Resource resource, String destination,
			boolean overwrite)
		throws WebDAVException {

		return HttpServletResponse.SC_FORBIDDEN;
	}

	@Override
	public int moveSimpleResource(
			WebDAVRequest webDAVRequest, Resource resource, String destination,
			boolean overwrite)
		throws WebDAVException {

		return HttpServletResponse.SC_FORBIDDEN;
	}

	@Override
	public int putResource(WebDAVRequest webDAVRequest) throws WebDAVException {
		return HttpServletResponse.SC_FORBIDDEN;
	}

	@Override
	public Lock refreshResourceLock(
			WebDAVRequest webDAVRequest, String uuid, long timeout)
		throws WebDAVException {

		return null;
	}

	@Override
	public void setRootPath(String rootPath) {
		_rootPath = rootPath;
	}

	@Override
	public void setToken(String token) {
		_token = token;
	}

	@Override
	public boolean unlockResource(WebDAVRequest webDAVRequest, String token)
		throws WebDAVException {

		return false;
	}

	protected long getPlid(long groupId) {
		return LayoutLocalServiceUtil.getDefaultPlid(groupId);
	}

	protected boolean isAddGroupPermissions(long groupId) throws Exception {
		Group group = GroupLocalServiceUtil.getGroup(groupId);

		if (!group.isUser()) {
			return true;
		}

		return false;
	}

	private String _rootPath;
	private String _token;

}