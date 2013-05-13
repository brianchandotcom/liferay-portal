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

package com.liferay.portal.cache.memcached;

import net.spy.memcached.MemcachedClientIF;

import org.apache.commons.pool.ObjectPool;

/**
 * @author Michael C. Han
 */
public class PooledMemcachedClientFactory implements MemcachedClientFactory {

	@Override
	public void clear() throws Exception {
		_memcachedClientPool.clear();
	}

	@Override
	public void close() throws Exception {
		_memcachedClientPool.close();
	}

	public void destroy() {
		try {
			close();
		}
		catch (Exception e) {
		}
	}

	@Override
	public MemcachedClientIF getMemcachedClient() throws Exception {
		return (MemcachedClientIF)_memcachedClientPool.borrowObject();
	}

	@Override
	public int getNumActive() {
		return _memcachedClientPool.getNumActive();
	}

	@Override
	public int getNumIdle() {
		return _memcachedClientPool.getNumIdle();
	}

	@Override
	public void invalidateMemcachedClient(MemcachedClientIF memcachedClient)
		throws Exception {

		_memcachedClientPool.invalidateObject(memcachedClient);
	}

	@Override
	public void returnMemcachedObject(MemcachedClientIF memcachedClient)
		throws Exception {

		_memcachedClientPool.returnObject(memcachedClient);
	}

	public void setMemcachedClientPool(ObjectPool memcachedClientPool) {
		_memcachedClientPool = memcachedClientPool;
	}

	private ObjectPool _memcachedClientPool;

}