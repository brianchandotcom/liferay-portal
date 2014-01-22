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

import com.liferay.portal.kernel.cache.CacheListener;
import com.liferay.portal.kernel.cache.CacheListenerScope;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.io.Deserializer;
import com.liferay.portal.kernel.io.Serializer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.nio.intraband.Datagram;
import com.liferay.portal.kernel.nio.intraband.Intraband;
import com.liferay.portal.kernel.nio.intraband.RegistrationReference;
import com.liferay.portal.kernel.nio.intraband.SystemDataType;

import java.io.Serializable;

import java.nio.ByteBuffer;

/**
 * @author Shuyang Zhou
 */
public class IntrabandPortalCache
		<K extends Serializable, V extends Serializable>
	implements PortalCache<K, V>, Serializable {

	public IntrabandPortalCache(
		String name, RegistrationReference registrationReference) {

		_name = name;
		_registrationReference = registrationReference;

		_intraband = registrationReference.getIntraband();

		SystemDataType systemDataType = SystemDataType.PORTAL_CACHE;

		_portalCacheType = systemDataType.getValue();
	}

	@Override
	public V get(K key) {
		Serializer serializer = _createSerializer(PortalCacheActionType.GET);

		serializer.writeObject(key);

		try {
			return _syncSend(serializer.toByteBuffer());
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to get, coverting to cache miss", e);
			}

			return null;
		}
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public void put(K key, V value) {
		Serializer serializer = _createSerializer(PortalCacheActionType.PUT);

		serializer.writeObject(key);
		serializer.writeObject(value);

		_intraband.sendDatagram(
			_registrationReference,
			Datagram.createRequestDatagram(
				_portalCacheType, serializer.toByteBuffer()));
	}

	@Override
	public void put(K key, V value, int timeToLive) {
		Serializer serializer = _createSerializer(
			PortalCacheActionType.PUT_TTL);

		serializer.writeObject(key);
		serializer.writeObject(value);
		serializer.writeInt(timeToLive);

		_intraband.sendDatagram(
			_registrationReference,
			Datagram.createRequestDatagram(
				_portalCacheType, serializer.toByteBuffer()));
	}

	@Override
	public void putQuiet(K key, V value) {
		Serializer serializer = _createSerializer(
			PortalCacheActionType.PUT_QUIET);

		serializer.writeObject(key);
		serializer.writeObject(value);

		_intraband.sendDatagram(
			_registrationReference,
			Datagram.createRequestDatagram(
				_portalCacheType, serializer.toByteBuffer()));
	}

	@Override
	public void putQuiet(K key, V value, int timeToLive) {
		Serializer serializer = _createSerializer(
			PortalCacheActionType.PUT_QUIET_TTL);

		serializer.writeObject(key);
		serializer.writeObject(value);
		serializer.writeInt(timeToLive);

		_intraband.sendDatagram(
			_registrationReference,
			Datagram.createRequestDatagram(
				_portalCacheType, serializer.toByteBuffer()));
	}

	@Override
	public void registerCacheListener(CacheListener<K, V> cacheListener) {
	}

	@Override
	public void registerCacheListener(
		CacheListener<K, V> cacheListener,
		CacheListenerScope cacheListenerScope) {
	}

	@Override
	public void remove(K key) {
		Serializer serializer = _createSerializer(PortalCacheActionType.REMOVE);

		serializer.writeObject(key);

		_intraband.sendDatagram(
			_registrationReference,
			Datagram.createRequestDatagram(
				_portalCacheType, serializer.toByteBuffer()));
	}

	@Override
	public void removeAll() {
		Serializer serializer = _createSerializer(
			PortalCacheActionType.REMOVE_ALL);

		_intraband.sendDatagram(
			_registrationReference,
			Datagram.createRequestDatagram(
				_portalCacheType, serializer.toByteBuffer()));
	}

	@Override
	public void unregisterCacheListener(CacheListener<K, V> cacheListener) {
	}

	@Override
	public void unregisterCacheListeners() {
	}

	private Serializer _createSerializer(
		PortalCacheActionType portalCacheActionType) {

		Serializer serializer = new Serializer();

		serializer.writeInt(portalCacheActionType.ordinal());
		serializer.writeString(_name);

		return serializer;
	}

	private <T extends Serializable> T _syncSend(ByteBuffer byteBuffer)
		throws Exception {

		Datagram responseDatagram = _intraband.sendSyncDatagram(
			_registrationReference,
			Datagram.createRequestDatagram(_portalCacheType, byteBuffer));

		Deserializer deserializer = new Deserializer(
			responseDatagram.getDataByteBuffer());

		return deserializer.readObject();
	}

	private static Log _log = LogFactoryUtil.getLog(IntrabandPortalCache.class);

	private final Intraband _intraband;
	private final String _name;
	private final byte _portalCacheType;
	private final RegistrationReference _registrationReference;

}