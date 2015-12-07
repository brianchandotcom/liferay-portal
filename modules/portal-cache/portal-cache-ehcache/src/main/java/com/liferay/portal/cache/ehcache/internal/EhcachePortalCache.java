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

package com.liferay.portal.cache.ehcache.internal;

import com.liferay.portal.cache.BasePortalCache;
import com.liferay.portal.cache.ehcache.EhcacheWrapper;
import com.liferay.portal.cache.ehcache.internal.event.PortalCacheCacheEventListener;
import com.liferay.portal.kernel.cache.PortalCacheManager;

import java.io.Serializable;

import java.util.List;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.NotificationScope;
import net.sf.ehcache.event.RegisteredEventListeners;

/**
 * @author Brian Wing Shun Chan
 * @author Edward Han
 * @author Shuyang Zhou
 */
public class EhcachePortalCache<K extends Serializable, V>
	extends BasePortalCache<K, V> implements EhcacheWrapper {

	public EhcachePortalCache(
		PortalCacheManager<K, V> portalCacheManager, Ehcache ehcache) {

		super(portalCacheManager);

		this.ehcache = ehcache;

		RegisteredEventListeners registeredEventListeners =
			ehcache.getCacheEventNotificationService();

		registeredEventListeners.registerListener(
			new PortalCacheCacheEventListener<>(
				aggregatedPortalCacheListener, this),
			NotificationScope.ALL);
	}

	@Override
	public Ehcache getEhcache() {
		return ehcache;
	}

	@Override
	public List<K> getKeys() {
		return ehcache.getKeys();
	}

	@Override
	public String getPortalCacheName() {
		return ehcache.getName();
	}

	@Override
	public void removeAll() {
		ehcache.removeAll();
	}

	@Override
	protected V doGet(K key) {
		Element element = ehcache.get(key);

		if (element == null) {
			return null;
		}

		return (V)element.getObjectValue();
	}

	@Override
	protected void doPut(K key, V value, int timeToLive) {
		Element element = new Element(key, value);

		if (timeToLive != DEFAULT_TIME_TO_LIVE) {
			element.setTimeToLive(timeToLive);
		}

		ehcache.put(element);
	}

	@Override
	protected V doPutIfAbsent(K key, V value, int timeToLive) {
		Element element = new Element(key, value);

		if (timeToLive != DEFAULT_TIME_TO_LIVE) {
			element.setTimeToLive(timeToLive);
		}

		Element oldElement = ehcache.putIfAbsent(element);

		if (oldElement == null) {
			return null;
		}

		return (V)oldElement.getObjectValue();
	}

	@Override
	protected void doRemove(K key) {
		ehcache.remove(key);
	}

	@Override
	protected boolean doRemove(K key, V value) {
		Element element = new Element(key, value);

		return ehcache.removeElement(element);
	}

	@Override
	protected V doReplace(K key, V value, int timeToLive) {
		Element element = new Element(key, value);

		if (timeToLive != DEFAULT_TIME_TO_LIVE) {
			element.setTimeToLive(timeToLive);
		}

		Element oldElement = ehcache.replace(element);

		if (oldElement == null) {
			return null;
		}

		return (V)oldElement.getObjectValue();
	}

	@Override
	protected boolean doReplace(K key, V oldValue, V newValue, int timeToLive) {
		Element oldElement = new Element(key, oldValue);

		Element newElement = new Element(key, newValue);

		if (timeToLive != DEFAULT_TIME_TO_LIVE) {
			newElement.setTimeToLive(timeToLive);
		}

		return ehcache.replace(oldElement, newElement);
	}

	protected void reconfigEhcache(Ehcache ehcache) {
		this.ehcache = ehcache;
	}

	protected volatile Ehcache ehcache;

}