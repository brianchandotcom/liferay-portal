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

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheException;
import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.io.Deserializer;
import com.liferay.portal.kernel.io.Serializer;
import com.liferay.portal.kernel.nio.intraband.BaseAsyncDatagramReceiveHandler;
import com.liferay.portal.kernel.nio.intraband.Datagram;
import com.liferay.portal.kernel.nio.intraband.IntraBand;
import com.liferay.portal.kernel.nio.intraband.RegistrationReference;

import java.io.Serializable;

import java.net.URL;

import java.util.Collection;

/**
 * @author Shuyang Zhou
 */
public class PortalCacheDatagramReceiveHandler
	extends BaseAsyncDatagramReceiveHandler {

	protected void doReceive(
			RegistrationReference registrationReference, Datagram datagram)
		throws Exception {

		Deserializer deserializer = new Deserializer(
			datagram.getDataByteBuffer());

		PortalCacheActionType actionType =
			PortalCacheActionType.values()[deserializer.readInt()];

		PortalCacheManager<Serializable, Serializable> portalCacheManager =
			IntraBandPortalCacheManager.getPortalCacheManager();

		if (actionType == PortalCacheActionType.RECONFIGURE) {
			portalCacheManager.reconfigureCaches(
				new URL(deserializer.readString()));

			return;
		}

		PortalCache<Serializable, Serializable> portalCache =
			portalCacheManager.getCache(deserializer.readString());

		switch (actionType) {
			case DESTROY :
				portalCache.destroy();

				break;
			case GET_BULK :
				Collection<Serializable> keys =
					(Collection<Serializable>)deserializer.readObject();

				Collection<Serializable> values = portalCache.get(keys);

				_sendResponse(
					registrationReference, datagram, (Serializable)values);

				break;
			case GET :
				Serializable key = deserializer.readObject();

				Serializable value = portalCache.get(key);

				_sendResponse(registrationReference, datagram, value);

				break;
			case PUT :
				key = deserializer.readObject();
				value = deserializer.readObject();

				portalCache.put(key, value);

				break;
			case PUT_TTL :
				key = deserializer.readObject();
				value = deserializer.readObject();
				int ttl = deserializer.readInt();

				portalCache.put(key, value, ttl);

				break;
			case REMOVE :
				key = deserializer.readObject();

				portalCache.remove(key);

				break;
			case REMOVE_ALL :
				portalCache.removeAll();

				break;
			default :

				// This should never happen, for corrupt input, the ordinal
				// indexing should already caught it. The only reason to have
				// this dead block is to prevent adding new type to
				// PortalCacheActionType in the future, but forget to update
				// this switch.

				throw new PortalCacheException(
					"Unsupported portal cache action type " + actionType);
		}
	}

	private void _sendResponse(
		RegistrationReference registrationReference, Datagram datagram,
		Serializable result) {

		Serializer serializer = new Serializer();

		serializer.writeObject(result);

		IntraBand intraBand = registrationReference.getIntraBand();

		intraBand.sendDatagram(
			registrationReference,
			Datagram.createResponseDatagram(
				datagram, serializer.toByteBuffer()));
	}

}