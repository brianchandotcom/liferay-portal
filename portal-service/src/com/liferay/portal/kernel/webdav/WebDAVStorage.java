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
import com.liferay.portal.kernel.webdav.methods.MethodFactory;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public interface WebDAVStorage {

	public int copyCollectionResource(
			WebDAVRequest webDAVRequest, Resource resource, String destination,
			boolean overwrite, long depth)
		throws WebDAVException;

	public int copySimpleResource(
			WebDAVRequest webDAVRequest, Resource resource, String destination,
			boolean overwrite)
		throws WebDAVException;

	public int deleteResource(WebDAVRequest webDAVRequest)
		throws WebDAVException;

	public MethodFactory getMethodFactory();

	public Resource getResource(WebDAVRequest webDAVRequest)
		throws WebDAVException;

	public List<Resource> getResources(WebDAVRequest webDAVRequest)
		throws WebDAVException;

	public String getRootPath();

	public String getToken();

	public boolean isAvailable(WebDAVRequest webDAVRequest)
		throws WebDAVException;

	public boolean isSupportsClassTwo();

	public Status lockResource(
			WebDAVRequest webDAVRequest, String owner, long timeout)
		throws WebDAVException;

	public Status makeCollection(WebDAVRequest webDAVRequest)
		throws WebDAVException;

	public int moveCollectionResource(
			WebDAVRequest webDAVRequest, Resource resource, String destination,
			boolean overwrite)
		throws WebDAVException;

	public int moveSimpleResource(
			WebDAVRequest webDAVRequest, Resource resource, String destination,
			boolean overwrite)
		throws WebDAVException;

	public int putResource(WebDAVRequest webDAVRequest) throws WebDAVException;

	public Lock refreshResourceLock(
			WebDAVRequest webDAVRequest, String uuid, long timeout)
		throws WebDAVException;

	public void setRootPath(String rootPath);

	public void setToken(String token);

	public boolean unlockResource(WebDAVRequest webDAVRequest, String token)
		throws WebDAVException;

}