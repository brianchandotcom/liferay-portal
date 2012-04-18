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

package com.liferay.portal.velocity;

import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.util.StringPool;

import org.apache.velocity.runtime.resource.util.StringResource;
import org.apache.velocity.runtime.resource.util.StringResourceRepository;

/**
 * @author Raymond Augé
 */
public class StringResourceRepositoryImpl implements StringResourceRepository {

	public String getEncoding() {
		return _encoding;
	}

	public StringResource getStringResource(String key) {
		Object resource = _portalCache.get(key);

		if ((resource != null) &&
			(resource instanceof SerializableStringResource)) {

			SerializableStringResource serializableStringResource =
				(SerializableStringResource)resource;

			return serializableStringResource.toStringResource();
		}

		return null;
	}

	public void putStringResource(String key, String body) {
		_portalCache.put(
			key, new SerializableStringResource(body, getEncoding()));
	}

	public void putStringResource(String key, String body, String encoding) {
		_portalCache.put(key, new SerializableStringResource(body, encoding));
	}

	public void removeAll() {
		_portalCache.removeAll();
	}

	public void removeStringResource(String key) {
		_portalCache.remove(key);
	}

	public void setEncoding(String encoding) {
		_encoding = encoding;
	}

	private static final String _CACHE_NAME =
		StringResourceRepository.class.getName();

	private static PortalCache _portalCache = MultiVMPoolUtil.getCache(
		_CACHE_NAME);

	private String _encoding = StringPool.UTF8;

}