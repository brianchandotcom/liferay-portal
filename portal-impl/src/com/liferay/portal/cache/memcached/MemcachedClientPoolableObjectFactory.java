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

import org.apache.commons.pool.PoolableObjectFactory;

/**
 * @author Michael C. Han
 */
public class MemcachedClientPoolableObjectFactory
	implements PoolableObjectFactory {

	@Override
	public void activateObject(Object obj) throws Exception {
	}

	@Override
	public void destroyObject(Object obj) {
		MemcachedClientIF memcachedClient = (MemcachedClientIF)obj;

		memcachedClient.shutdown();
	}

	@Override
	public Object makeObject() throws Exception {
		return new MemcachedClient(_connectionFactory, _inetSocketAddresses);
	}

	@Override
	public void passivateObject(Object obj) throws Exception {
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

	@Override
	public boolean validateObject(Object obj) {
		return true;
	}

	private static final int _DEFAULT_MEMCACHED_PORT = 11211;

	private ConnectionFactory _connectionFactory;
	private List<InetSocketAddress> _inetSocketAddresses =
		new ArrayList<InetSocketAddress>();

}