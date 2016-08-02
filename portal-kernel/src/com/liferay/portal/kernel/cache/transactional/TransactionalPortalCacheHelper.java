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

package com.liferay.portal.kernel.cache.transactional;

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheHelperUtil;
import com.liferay.portal.kernel.cache.SkipReplicationThreadLocal;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionAttribute;
import com.liferay.portal.kernel.transaction.TransactionDefinition;
import com.liferay.portal.kernel.transaction.TransactionLifecycleListener;
import com.liferay.portal.kernel.transaction.TransactionStatus;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InitialThreadLocal;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Shuyang Zhou
 */
public class TransactionalPortalCacheHelper {

	public static final TransactionLifecycleListener
		TRANSACTION_LIFECYCLE_LISTENER = new TransactionLifecycleListener() {

			@Override
			public void committed(
				TransactionAttribute transactionAttribute,
				TransactionStatus transactionStatus) {

				if (!_isTransactionalCacheEnabled()) {
					return;
				}

				Propagation propagation = transactionAttribute.getPropagation();

				if (propagation.value() >=
						TransactionDefinition.PROPAGATION_NOT_SUPPORTED) {

					List<List<PortalCacheMap>> backupPortalCacheMaps =
						_backupPortalCacheMapsThreadLocal.get();

					_portalCacheMapsThreadLocal.set(
						backupPortalCacheMaps.remove(
							backupPortalCacheMaps.size() - 1));
				}
				else if (transactionStatus.isNewTransaction()) {
					commit();
				}
			}

			@Override
			public void created(
				TransactionAttribute transactionAttribute,
				TransactionStatus transactionStatus) {

				if (!_isTransactionalCacheEnabled()) {
					return;
				}

				Propagation propagation = transactionAttribute.getPropagation();

				if (propagation.value() >=
						TransactionDefinition.PROPAGATION_NOT_SUPPORTED) {

					List<List<PortalCacheMap>> backupPortalCacheMaps =
						_backupPortalCacheMapsThreadLocal.get();

					backupPortalCacheMaps.add(
						_portalCacheMapsThreadLocal.get());

					_portalCacheMapsThreadLocal.remove();
				}
				else if (transactionStatus.isNewTransaction()) {
					begin();
				}
			}

			@Override
			public void rollbacked(
				TransactionAttribute transactionAttribute,
				TransactionStatus transactionStatus, Throwable throwable) {

				if (!_isTransactionalCacheEnabled()) {
					return;
				}

				Propagation propagation = transactionAttribute.getPropagation();

				if (propagation.value() >=
						TransactionDefinition.PROPAGATION_NOT_SUPPORTED) {

					List<List<PortalCacheMap>> backupPortalCacheMaps =
						_backupPortalCacheMapsThreadLocal.get();

					_portalCacheMapsThreadLocal.set(
						backupPortalCacheMaps.remove(
							backupPortalCacheMaps.size() - 1));
				}
				else if (transactionStatus.isNewTransaction()) {
					rollback();

					EntityCacheUtil.clearLocalCache();
					FinderCacheUtil.clearLocalCache();
				}
			}

		};

	public static void begin() {
		List<PortalCacheMap> portalCacheMaps =
			_portalCacheMapsThreadLocal.get();

		portalCacheMaps.add(new PortalCacheMap());
	}

	public static void commit() {
		PortalCacheMap portalCacheMap = _popPortalCacheMap();

		for (Map.Entry
				<PortalCache<? extends Serializable, ?>, UncommittedBuffer>
					portalCacheMapEntry : portalCacheMap.entrySet()) {

			PortalCache<Serializable, Object> portalCache =
				(PortalCache<Serializable, Object>)portalCacheMapEntry.getKey();

			UncommittedBuffer uncommittedBuffer =
				portalCacheMapEntry.getValue();

			uncommittedBuffer.commitTo(portalCache);
		}

		portalCacheMap.clear();
	}

	public static <K extends Serializable, V> V get(
		PortalCache<K, V> portalCache, K key) {

		PortalCacheMap portalCacheMap = _peekPortalCacheMap();

		UncommittedBuffer uncommittedBuffer = portalCacheMap.get(portalCache);

		if (uncommittedBuffer == null) {
			return null;
		}

		ValueEntry valueEntry = uncommittedBuffer.get(key);

		if (valueEntry == null) {
			return null;
		}

		return (V)valueEntry._value;
	}

	public static Serializable getNullHolder() {
		return _NULL_HOLDER;
	}

	public static boolean isEnabled() {
		if (!_isTransactionalCacheEnabled()) {
			return false;
		}

		List<PortalCacheMap> portalCacheMaps =
			_portalCacheMapsThreadLocal.get();

		return !portalCacheMaps.isEmpty();
	}

	public static <K extends Serializable, V> void put(
		PortalCache<K, V> portalCache, K key, V value, int ttl) {

		PortalCacheMap portalCacheMap = _peekPortalCacheMap();

		UncommittedBuffer uncommittedBuffer = portalCacheMap.get(portalCache);

		if (uncommittedBuffer == null) {
			uncommittedBuffer = new UncommittedBuffer();

			portalCacheMap.put(portalCache, uncommittedBuffer);
		}

		uncommittedBuffer.put(
			key,
			new ValueEntry(value, ttl, SkipReplicationThreadLocal.isEnabled()));
	}

	public static <K extends Serializable, V> void removeAll(
		PortalCache<K, V> portalCache) {

		PortalCacheMap portalCacheMap = _peekPortalCacheMap();

		UncommittedBuffer uncommittedBuffer = portalCacheMap.get(portalCache);

		if (uncommittedBuffer == null) {
			uncommittedBuffer = new UncommittedBuffer();

			portalCacheMap.put(portalCache, uncommittedBuffer);
		}

		uncommittedBuffer.removeAll(SkipReplicationThreadLocal.isEnabled());
	}

	public static void rollback() {
		PortalCacheMap portalCacheMap = _popPortalCacheMap();

		portalCacheMap.clear();
	}

	protected static class PortalCacheMap
		extends HashMap
			<PortalCache<? extends Serializable, ?>, UncommittedBuffer> {
	}

	private static boolean _isTransactionalCacheEnabled() {
		if (_transactionalCacheEnabled == null) {
			_transactionalCacheEnabled = GetterUtil.getBoolean(
				PropsUtil.get(PropsKeys.TRANSACTIONAL_CACHE_ENABLED));
		}

		return _transactionalCacheEnabled;
	}

	private static PortalCacheMap _peekPortalCacheMap() {
		List<PortalCacheMap> portalCacheMaps =
			_portalCacheMapsThreadLocal.get();

		return portalCacheMaps.get(portalCacheMaps.size() - 1);
	}

	private static PortalCacheMap _popPortalCacheMap() {
		List<PortalCacheMap> portalCacheMaps =
			_portalCacheMapsThreadLocal.get();

		return portalCacheMaps.remove(portalCacheMaps.size() - 1);
	}

	private static final Serializable _NULL_HOLDER = "NULL_HOLDER";

	private static final ValueEntry _NULL_HOLDER_VALUE_ENTRY = new ValueEntry(
		_NULL_HOLDER, PortalCache.DEFAULT_TIME_TO_LIVE, false);

	private static final ThreadLocal<List<List<PortalCacheMap>>>
		_backupPortalCacheMapsThreadLocal =
			new InitialThreadLocal<List<List<PortalCacheMap>>>(
				TransactionalPortalCacheHelper.class.getName() +
					"._backupPortalCacheMapsThreadLocal",
				new ArrayList<List<PortalCacheMap>>());
	private static final ThreadLocal<List<PortalCacheMap>>
		_portalCacheMapsThreadLocal =
			new InitialThreadLocal<List<PortalCacheMap>>(
				TransactionalPortalCacheHelper.class.getName() +
					"._portalCacheMapsThreadLocal",
				new ArrayList<PortalCacheMap>());
	private static volatile Boolean _transactionalCacheEnabled;

	private static class UncommittedBuffer {

		public void commitTo(PortalCache<Serializable, Object> portalCache) {
			if (_removeAll) {
				if (_skipReplicator) {
					PortalCacheHelperUtil.removeAllWithoutReplicator(
						portalCache);
				}
				else {
					portalCache.removeAll();
				}
			}

			for (Map.Entry<? extends Serializable, ValueEntry> entry :
					_uncommittedMap.entrySet()) {

				ValueEntry valueEntry = entry.getValue();

				valueEntry.commitTo(portalCache, entry.getKey());
			}
		}

		public ValueEntry get(Serializable key) {
			ValueEntry valueEntry = _uncommittedMap.get(key);

			if ((valueEntry == null) && _removeAll) {
				valueEntry = _NULL_HOLDER_VALUE_ENTRY;
			}

			return valueEntry;
		}

		public void put(Serializable key, ValueEntry valueEntry) {
			ValueEntry oldValueEntry = _uncommittedMap.put(key, valueEntry);

			if (oldValueEntry != null) {
				oldValueEntry.merge(valueEntry);
			}
		}

		public void removeAll(boolean skipReplicator) {
			_uncommittedMap.clear();

			_removeAll = true;

			if (_skipReplicator) {
				_skipReplicator = skipReplicator;
			}
		}

		private boolean _removeAll;
		private boolean _skipReplicator = true;
		private final Map<Serializable, ValueEntry> _uncommittedMap =
			new HashMap<>();

	}

	private static class ValueEntry {

		public ValueEntry(Object value, int ttl, boolean skipReplicator) {
			_value = value;
			_ttl = ttl;
			_skipReplicator = skipReplicator;
		}

		public void commitTo(
			PortalCache<Serializable, Object> portalCache, Serializable key) {

			if (_value == _NULL_HOLDER) {
				if (_skipReplicator) {
					PortalCacheHelperUtil.removeWithoutReplicator(
						portalCache, key);
				}
				else {
					portalCache.remove(key);
				}
			}
			else {
				if (_skipReplicator) {
					PortalCacheHelperUtil.putWithoutReplicator(
						portalCache, key, _value, _ttl);
				}
				else {
					portalCache.put(key, _value, _ttl);
				}
			}
		}

		public void merge(ValueEntry valueEntry) {
			if (!_skipReplicator) {
				valueEntry._skipReplicator = false;
			}
		}

		private boolean _skipReplicator;
		private final int _ttl;
		private final Object _value;

	}

}