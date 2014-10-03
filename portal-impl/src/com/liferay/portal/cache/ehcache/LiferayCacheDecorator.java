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

package com.liferay.portal.cache.ehcache;

import com.liferay.portal.kernel.cache.AggregatedCacheListener;

import java.io.Serializable;

import java.util.Collection;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.constructs.EhcacheDecoratorAdapter;

/**
 * @author Tina Tian
 */
public class LiferayCacheDecorator extends EhcacheDecoratorAdapter {

	public LiferayCacheDecorator(Ehcache underlyingCache) {
		super(underlyingCache);
	}

	@Override
	public boolean equals(Object object) {
		return underlyingCache.equals(object);
	}

	public Ehcache getUnderlyingCache() {
		return underlyingCache;
	}

	@Override
	public int hashCode() {
		return underlyingCache.hashCode();
	}

	@Override
	public void put(Element element, boolean doNotNotifyCacheReplicators)
		throws CacheException, IllegalArgumentException, IllegalStateException {

		boolean remoteInvoke =
			AggregatedCacheListener.getRemoteInvokeThreadLocal();

		AggregatedCacheListener.setRemoteInvokeThreadLocal(
			doNotNotifyCacheReplicators);

		try {
			super.put(element, doNotNotifyCacheReplicators);
		}
		finally {
			AggregatedCacheListener.setRemoteInvokeThreadLocal(remoteInvoke);
		}
	}

	@Override
	public Element putIfAbsent(
			final Element element, final boolean doNotNotifyCacheReplicators)
		throws NullPointerException {

		boolean remoteInvoke =
			AggregatedCacheListener.getRemoteInvokeThreadLocal();

		AggregatedCacheListener.setRemoteInvokeThreadLocal(
			doNotNotifyCacheReplicators);

		try {
			return super.putIfAbsent(element, doNotNotifyCacheReplicators);
		}
		finally {
			AggregatedCacheListener.setRemoteInvokeThreadLocal(remoteInvoke);
		}
	}

	@Override
	public boolean remove(Object key, boolean doNotNotifyCacheReplicators)
		throws IllegalStateException {

		boolean remoteInvoke =
			AggregatedCacheListener.getRemoteInvokeThreadLocal();

		AggregatedCacheListener.setRemoteInvokeThreadLocal(
			doNotNotifyCacheReplicators);

		try {
			return super.remove(key, doNotNotifyCacheReplicators);
		}
		finally {
			AggregatedCacheListener.setRemoteInvokeThreadLocal(remoteInvoke);
		}
	}

	@Override
	public boolean remove(Serializable key, boolean doNotNotifyCacheReplicators)
		throws IllegalStateException {

		return remove((Object)key, doNotNotifyCacheReplicators);
	}

	@Override
	public void removeAll(boolean doNotNotifyCacheReplicators)
		throws CacheException, IllegalStateException {

		boolean remoteInvoke =
			AggregatedCacheListener.getRemoteInvokeThreadLocal();

		AggregatedCacheListener.setRemoteInvokeThreadLocal(
			doNotNotifyCacheReplicators);

		try {
			super.removeAll(doNotNotifyCacheReplicators);
		}
		finally {
			AggregatedCacheListener.setRemoteInvokeThreadLocal(remoteInvoke);
		}
	}

	@Override
	public void removeAll(
			Collection<?> keys, boolean doNotNotifyCacheReplicators)
		throws IllegalStateException {

		boolean remoteInvoke =
			AggregatedCacheListener.getRemoteInvokeThreadLocal();

		AggregatedCacheListener.setRemoteInvokeThreadLocal(
			doNotNotifyCacheReplicators);

		try {
			super.removeAll(keys, doNotNotifyCacheReplicators);
		}
		finally {
			AggregatedCacheListener.setRemoteInvokeThreadLocal(remoteInvoke);
		}
	}

}