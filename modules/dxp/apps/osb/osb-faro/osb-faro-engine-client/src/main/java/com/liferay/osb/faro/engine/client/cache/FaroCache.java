/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.engine.client.cache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

/**
 * @author Shinn Lok
 */
public class FaroCache implements Cache {

	public FaroCache() {
		_store = new LinkedHashMap<Object, ValueWrapper>() {
		};
	}

	@Override
	public synchronized void clear() {
		_store.clear();
	}

	@Override
	public synchronized void evict(Object key) {
		_store.remove(key);
	}

	@Override
	public synchronized ValueWrapper get(Object key) {
		return _store.get(key);
	}

	@Override
	public <T> T get(Object key, Callable<T> callable) {
		return null;
	}

	@Override
	public <T> T get(Object key, Class<T> type) {
		return null;
	}

	@Override
	public String getName() {
		Class<? extends FaroCache> clazz = getClass();

		return clazz.getName();
	}

	@Override
	public Map<Object, ValueWrapper> getNativeCache() {
		return _store;
	}

	@Override
	public synchronized void put(Object key, Object value) {
		_store.put(key, new SimpleValueWrapper(value));
	}

	@Override
	public synchronized ValueWrapper putIfAbsent(Object key, Object value) {
		return _store.putIfAbsent(key, new SimpleValueWrapper(value));
	}

	private final LinkedHashMap<Object, ValueWrapper> _store;

}