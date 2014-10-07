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
	public void put(Element element, boolean doNotNotifyCacheReplicators) {
		boolean remoteInvoke = AggregatedCacheListener.isRemoteInvoke();

		AggregatedCacheListener.setRemoteInvoke(doNotNotifyCacheReplicators);

		try {
			super.put(element, doNotNotifyCacheReplicators);
		}
		finally {
			AggregatedCacheListener.setRemoteInvoke(remoteInvoke);
		}
	}

	@Override
	public Element putIfAbsent(
		Element element, boolean doNotNotifyCacheReplicators) {

		boolean remoteInvoke = AggregatedCacheListener.isRemoteInvoke();

		AggregatedCacheListener.setRemoteInvoke(doNotNotifyCacheReplicators);

		try {
			return super.putIfAbsent(element, doNotNotifyCacheReplicators);
		}
		finally {
			AggregatedCacheListener.setRemoteInvoke(remoteInvoke);
		}
	}

	@Override
	public boolean remove(Object key, boolean doNotNotifyCacheReplicators) {
		boolean remoteInvoke = AggregatedCacheListener.isRemoteInvoke();

		AggregatedCacheListener.setRemoteInvoke(doNotNotifyCacheReplicators);

		try {
			return super.remove(key, doNotNotifyCacheReplicators);
		}
		finally {
			AggregatedCacheListener.setRemoteInvoke(remoteInvoke);
		}
	}

	@Override
	public boolean remove(
		Serializable key, boolean doNotNotifyCacheReplicators) {

		return remove((Object)key, doNotNotifyCacheReplicators);
	}

	@Override
	public void removeAll(boolean doNotNotifyCacheReplicators) {
		boolean remoteInvoke = AggregatedCacheListener.isRemoteInvoke();

		AggregatedCacheListener.setRemoteInvoke(doNotNotifyCacheReplicators);

		try {
			super.removeAll(doNotNotifyCacheReplicators);
		}
		finally {
			AggregatedCacheListener.setRemoteInvoke(remoteInvoke);
		}
	}

	@Override
	public void removeAll(
		Collection<?> keys, boolean doNotNotifyCacheReplicators) {

		boolean remoteInvoke = AggregatedCacheListener.isRemoteInvoke();

		AggregatedCacheListener.setRemoteInvoke(doNotNotifyCacheReplicators);

		try {
			super.removeAll(keys, doNotNotifyCacheReplicators);
		}
		finally {
			AggregatedCacheListener.setRemoteInvoke(remoteInvoke);
		}
	}

}