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
 */
public class WebDAVStorageWrapper implements WebDAVStorage {

	public WebDAVStorageWrapper(WebDAVStorage webDAVStorage) {
		_webDAVStorage = webDAVStorage;
	}

	@Override
	public int copyCollectionResource(
			WebDAVRequest webDAVRequest, Resource resource, String destination,
			boolean overwrite, long depth)
		throws WebDAVException {

		return _webDAVStorage.copyCollectionResource(
			webDAVRequest, resource, destination, overwrite, depth);
	}

	@Override
	public int copySimpleResource(
			WebDAVRequest webDAVRequest, Resource resource, String destination,
			boolean overwrite)
		throws WebDAVException {

		return _webDAVStorage.copySimpleResource(
			webDAVRequest, resource, destination, overwrite);
	}

	@Override
	public int deleteResource(WebDAVRequest webDAVRequest)
		throws WebDAVException {

		return _webDAVStorage.deleteResource(webDAVRequest);
	}

	@Override
	public MethodFactory getMethodFactory() {
		return _webDAVStorage.getMethodFactory();
	}

	@Override
	public Resource getResource(WebDAVRequest webDAVRequest)
		throws WebDAVException {

		return _webDAVStorage.getResource(webDAVRequest);
	}

	@Override
	public List<Resource> getResources(WebDAVRequest webDAVRequest)
		throws WebDAVException {

		return _webDAVStorage.getResources(webDAVRequest);
	}

	@Override
	public String getRootPath() {
		return _webDAVStorage.getRootPath();
	}

	@Override
	public String getToken() {
		return _webDAVStorage.getToken();
	}

	public WebDAVStorage getWrappedWebDAVStorage() {
		return _webDAVStorage;
	}

	@Override
	public boolean isAvailable(WebDAVRequest webDAVRequest)
		throws WebDAVException {

		return _webDAVStorage.isAvailable(webDAVRequest);
	}

	@Override
	public boolean isSupportsClassTwo() {
		return _webDAVStorage.isSupportsClassTwo();
	}

	@Override
	public Status lockResource(
			WebDAVRequest webDAVRequest, String owner, long timeout)
		throws WebDAVException {

		return _webDAVStorage.lockResource(webDAVRequest, owner, timeout);
	}

	@Override
	public Status makeCollection(WebDAVRequest webDAVRequest)
		throws WebDAVException {

		return _webDAVStorage.makeCollection(webDAVRequest);
	}

	@Override
	public int moveCollectionResource(
			WebDAVRequest webDAVRequest, Resource resource, String destination,
			boolean overwrite)
		throws WebDAVException {

		return _webDAVStorage.moveCollectionResource(
			webDAVRequest, resource, destination, overwrite);
	}

	@Override
	public int moveSimpleResource(
			WebDAVRequest webDAVRequest, Resource resource, String destination,
			boolean overwrite)
		throws WebDAVException {

		return _webDAVStorage.moveSimpleResource(
			webDAVRequest, resource, destination, overwrite);
	}

	@Override
	public int putResource(WebDAVRequest webDAVRequest) throws WebDAVException {
		return _webDAVStorage.putResource(webDAVRequest);
	}

	@Override
	public Lock refreshResourceLock(
			WebDAVRequest webDAVRequest, String uuid, long timeout)
		throws WebDAVException {

		return _webDAVStorage.refreshResourceLock(webDAVRequest, uuid, timeout);
	}

	@Override
	public void setRootPath(String rootPath) {
		_webDAVStorage.setRootPath(rootPath);
	}

	@Override
	public void setToken(String token) {
		_webDAVStorage.setToken(token);
	}

	@Override
	public boolean unlockResource(WebDAVRequest webDAVRequest, String token)
		throws WebDAVException {

		return _webDAVStorage.unlockResource(webDAVRequest, token);
	}

	private final WebDAVStorage _webDAVStorage;

}