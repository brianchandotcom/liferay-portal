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

package com.liferay.portal.kernel.nio.intraband.cache;

import com.liferay.portal.kernel.cache.CacheListener;
import com.liferay.portal.kernel.cache.CacheListenerScope;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.io.Deserializer;
import com.liferay.portal.kernel.io.Serializer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.nio.intraband.Datagram;
import com.liferay.portal.kernel.nio.intraband.IntraBand;
import com.liferay.portal.kernel.nio.intraband.RegistrationReference;
import com.liferay.portal.kernel.nio.intraband.SystemDataType;

import java.io.Serializable;

import java.nio.ByteBuffer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Shuyang Zhou
 */
public class IntraBandPortalCache
		<K extends Serializable, V extends Serializable>
	implements PortalCache<K, V>, Serializable {

	public IntraBandPortalCache(
		String name, RegistrationReference registrationReference) {

		_name = name;
		_registrationReference = registrationReference;
		_intraBand = _registrationReference.getIntraBand();
	}

	public void destroy() {
		Serializer serializer = _createSerializer(
			PortalCacheActionType.DESTROY);

		_asyncSend(serializer);
	}

	public Collection<V> get(Collection<K> keys) {
		Serializer serializer = _createSerializer(
			PortalCacheActionType.GET_BULK);

		serializer.writeObject((Serializable)keys);

		try {
			return (Collection<V>)_syncSend(serializer);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Bulk get failure, coverting to cache miss", e);
			}

			List<V> values = new ArrayList<V>(keys.size());

			for (int i = 0; i < keys.size(); i++) {
				values.add(null);
			}

			return values;
		}
	}

	public V get(K key) {
		Serializer serializer = _createSerializer(PortalCacheActionType.GET);

		serializer.writeObject(key);

		try {
			return _syncSend(serializer);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Get failure, coverting to cache miss", e);
			}

			return null;
		}
	}

	public String getName() {
		return _name;
	}

	public void put(K key, V value) {
		Serializer serializer = _createSerializer(PortalCacheActionType.PUT);

		serializer.writeObject(key);
		serializer.writeObject(value);

		_asyncSend(serializer);
	}

	public void put(K key, V value, int timeToLive) {
		Serializer serializer = _createSerializer(
			PortalCacheActionType.PUT_TTL);

		serializer.writeObject(key);
		serializer.writeObject(value);
		serializer.writeInt(timeToLive);

		_asyncSend(serializer);
	}

	public void registerCacheListener(CacheListener<K, V> cacheListener) {
	}

	public void registerCacheListener(
		CacheListener<K, V> cacheListener,
		CacheListenerScope cacheListenerScope) {
	}

	public void remove(K key) {
		Serializer serializer = _createSerializer(PortalCacheActionType.REMOVE);

		serializer.writeObject(key);

		_asyncSend(serializer);
	}

	public void removeAll() {
		Serializer serializer = _createSerializer(
			PortalCacheActionType.REMOVE_ALL);

		_asyncSend(serializer);
	}

	public void unregisterCacheListener(CacheListener<K, V> cacheListener) {
	}

	public void unregisterCacheListeners() {
	}

	private void _asyncSend(Serializer serializer) {
		ByteBuffer byteBuffer = serializer.toByteBuffer();

		Datagram datagram = Datagram.createRequestDatagram(
			SystemDataType.PORTAL_CACHE.getValue(), byteBuffer);

		_intraBand.sendDatagram(_registrationReference, datagram);
	}

	private Serializer _createSerializer(PortalCacheActionType actionType) {
		Serializer serializer = new Serializer();

		serializer.writeInt(actionType.ordinal());
		serializer.writeString(_name);

		return serializer;
	}

	private <V extends Serializable> V _syncSend(Serializer serializer)
		throws Exception {

		ByteBuffer byteBuffer = serializer.toByteBuffer();

		Datagram requestDatagram = Datagram.createRequestDatagram(
			SystemDataType.PORTAL_CACHE.getValue(), byteBuffer);

		Datagram responseDatagram = _intraBand.sendSyncDatagram(
			_registrationReference, requestDatagram);

		Deserializer deserializer = new Deserializer(
			responseDatagram.getData());

		return deserializer.readObject();
	}

	private static Log _log = LogFactoryUtil.getLog(IntraBandPortalCache.class);

	private final IntraBand _intraBand;
	private final String _name;
	private final RegistrationReference _registrationReference;

}