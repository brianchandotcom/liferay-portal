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

package com.liferay.portal.kernel.nio.intraband.cache;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheException;
import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.io.Deserializer;
import com.liferay.portal.kernel.io.Serializer;
import com.liferay.portal.kernel.nio.intraband.BaseAsyncDatagramReceiveHandler;
import com.liferay.portal.kernel.nio.intraband.Datagram;
import com.liferay.portal.kernel.nio.intraband.Intraband;
import com.liferay.portal.kernel.nio.intraband.RegistrationReference;

import java.io.Serializable;

import java.net.URL;

import java.rmi.RemoteException;

/**
 * @author Shuyang Zhou
 */
public class PortalCacheDatagramReceiveHandler
	extends BaseAsyncDatagramReceiveHandler {

	public PortalCacheDatagramReceiveHandler() throws RemoteException {
		_portalCacheManager = (PortalCacheManager<Serializable, Serializable>)
			PortalBeanLocatorUtil.locate(
				_MULTI_VM_PORTAL_CACHE_MANAGER_BEAN_NAME);
	}

	@Override
	protected void doReceive(
			RegistrationReference registrationReference, Datagram datagram)
		throws Exception {

		Deserializer deserializer = new Deserializer(
			datagram.getDataByteBuffer());

		PortalCacheActionType portalCacheActionType =
			PortalCacheActionType.values()[deserializer.readInt()];

		switch (portalCacheActionType) {
			case CLEAR_ALL:
				_portalCacheManager.clearAll();

				break;

			case DESTROY:
				_portalCacheManager.destroy();

				break;

			case GET:
				PortalCache<Serializable, Serializable> portalCache =
					_portalCacheManager.getCache(deserializer.readString());

				Serializable key = deserializer.readObject();

				Serializable value = portalCache.get(key);

				_sendResponse(registrationReference, datagram, value);

				break;

			case PUT:
				portalCache = _portalCacheManager.getCache(
					deserializer.readString());

				key = deserializer.readObject();
				value = deserializer.readObject();

				portalCache.put(key, value);

				break;

			case PUT_QUIET:
				portalCache = _portalCacheManager.getCache(
					deserializer.readString());

				key = deserializer.readObject();
				value = deserializer.readObject();

				portalCache.putQuiet(key, value);

				break;

			case PUT_QUIET_TTL:
				portalCache = _portalCacheManager.getCache(
					deserializer.readString());

				key = deserializer.readObject();
				value = deserializer.readObject();
				int ttl = deserializer.readInt();

				portalCache.putQuiet(key, value, ttl);

				break;

			case PUT_TTL:
				portalCache = _portalCacheManager.getCache(
					deserializer.readString());

				key = deserializer.readObject();
				value = deserializer.readObject();
				ttl = deserializer.readInt();

				portalCache.put(key, value, ttl);

				break;

			case RECONFIGURE:
				_portalCacheManager.reconfigureCaches(
					new URL(deserializer.readString()));

				break;

			case REMOVE:
				portalCache = _portalCacheManager.getCache(
					deserializer.readString());

				key = deserializer.readObject();

				portalCache.remove(key);

				break;

			case REMOVE_ALL:
				portalCache = _portalCacheManager.getCache(
					deserializer.readString());

				portalCache.removeAll();

				break;

			case REMOVE_CACHE:
				_portalCacheManager.removeCache(deserializer.readString());

				break;

			default:

				// This should never happen, for corrupt input, the ordinal
				// indexing should already have caught it. The only reason to
				// have this dead block is to ensure that we never add a new
				// PortalCacheActionType without updating this switch.

				throw new PortalCacheException(
					"Unsupported portal cache action type " +
						portalCacheActionType);
		}
	}

	private void _sendResponse(
		RegistrationReference registrationReference, Datagram datagram,
		Serializable result) {

		Serializer serializer = new Serializer();

		serializer.writeObject(result);

		Intraband intraband = registrationReference.getIntraband();

		intraband.sendDatagram(
			registrationReference,
			Datagram.createResponseDatagram(
				datagram, serializer.toByteBuffer()));
	}

	private static final String _MULTI_VM_PORTAL_CACHE_MANAGER_BEAN_NAME =
		"com.liferay.portal.kernel.cache.MultiVMPortalCacheManager";

	private final PortalCacheManager<Serializable, Serializable>
		_portalCacheManager;

}