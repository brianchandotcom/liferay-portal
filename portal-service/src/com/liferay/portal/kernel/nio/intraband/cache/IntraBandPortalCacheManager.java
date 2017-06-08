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
import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.io.Serializer;
import com.liferay.portal.kernel.nio.intraband.Datagram;
import com.liferay.portal.kernel.nio.intraband.IntraBand;
import com.liferay.portal.kernel.nio.intraband.RegistrationReference;
import com.liferay.portal.kernel.nio.intraband.SystemDataType;

import java.io.Serializable;

import java.net.URL;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Shuyang Zhou
 */
public class IntraBandPortalCacheManager
		<K extends Serializable, V extends Serializable>
	implements PortalCacheManager<K, V> {

	public static <K extends Serializable, V extends Serializable>
		PortalCacheManager<K, V> getPortalCacheManager() {

		return (PortalCacheManager<K, V>)_portalCacheManager;
	}

	public static void setPortalCacheManager(
		PortalCacheManager<? extends Serializable, ? extends Serializable>
			portalCacheManager) {

		_portalCacheManager = portalCacheManager;
	}

	public IntraBandPortalCacheManager(
		RegistrationReference registrationReference) {

		_intraBand = registrationReference.getIntraBand();
		_registrationReference = registrationReference;

		_portalCaches = new ConcurrentHashMap<String, PortalCache<K, V>>();
	}

	public void clearAll() {
		_portalCaches.clear();
	}

	public PortalCache<K, V> getCache(String name) {
		return getCache(name, false);
	}

	public PortalCache<K, V> getCache(String name, boolean blocking) {
		PortalCache<K, V> portalCache = _portalCaches.get(name);

		if (portalCache == null) {
			portalCache = new IntraBandPortalCache<K, V>(
				name, _registrationReference);

			_portalCaches.put(name, portalCache);
		}

		return portalCache;
	}

	public void reconfigureCaches(URL configurationURL) {
		Serializer serializer = new Serializer();

		serializer.writeInt(PortalCacheActionType.RECONFIGURE.ordinal());
		serializer.writeString(configurationURL.toExternalForm());

		SystemDataType systemDataType = SystemDataType.PORTAL_CACHE;

		_intraBand.sendDatagram(
			_registrationReference,
			Datagram.createRequestDatagram(
				systemDataType.getValue(), serializer.toByteBuffer()));
	}

	public void removeCache(String name) {
		_portalCaches.remove(name);
	}

	private static PortalCacheManager
		<? extends Serializable, ? extends Serializable> _portalCacheManager;

	private final IntraBand _intraBand;
	private final Map<String, PortalCache<K, V>> _portalCaches;
	private final RegistrationReference _registrationReference;

}