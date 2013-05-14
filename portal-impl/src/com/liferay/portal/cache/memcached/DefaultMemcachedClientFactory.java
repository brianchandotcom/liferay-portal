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

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.net.InetSocketAddress;

import java.util.ArrayList;
import java.util.List;

import net.spy.memcached.ConnectionFactory;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.MemcachedClientIF;

/**
 * @author Michael C. Han
 */
public class DefaultMemcachedClientFactory implements MemcachedClientFactory {

	@Override
	public void clear() {
	}

	@Override
	public void close() {
	}

	@Override
	public MemcachedClientIF getMemcachedClient() throws Exception {
		return new MemcachedClient(_connectionFactory, _inetSocketAddresses);
	}

	@Override
	public int getNumActive() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getNumIdle() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void invalidateMemcachedClient(MemcachedClientIF memcachedClient) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void returnMemcachedObject(MemcachedClientIF memcachedClient) {
		throw new UnsupportedOperationException();
	}

	public void setAddresses(List<String> addresses) {
		for (String address : addresses) {
			String[] hostAndPort = StringUtil.split(address, CharPool.COLON);

			String hostName = hostAndPort[0];

			int port = _DEFAULT_MEMCACHED_PORT;

			if (hostAndPort.length == 2) {
				port = GetterUtil.getInteger(hostAndPort[1]);
			}

			InetSocketAddress inetSocketAddress = new InetSocketAddress(
				hostName, port);

			_inetSocketAddresses.add(inetSocketAddress);
		}
	}

	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		_connectionFactory = connectionFactory;
	}

	private static final int _DEFAULT_MEMCACHED_PORT = 11211;

	private ConnectionFactory _connectionFactory;
	private List<InetSocketAddress> _inetSocketAddresses =
		new ArrayList<InetSocketAddress>();

}