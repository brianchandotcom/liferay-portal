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

import com.liferay.petra.concurrent.ConcurrentReferenceValueHashMap;
import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.memory.FinalizeManager;
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
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
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
					commit(transactionAttribute.isReadOnly());
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

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link #commit(boolean)}
	 */
	@Deprecated
	public static void commit() {
		commit(false);
	}

	public static void commit(boolean readOnly) {
		PortalCacheMap portalCacheMap = _popPortalCacheMap();

		for (UncommittedBuffer uncommittedBuffer : portalCacheMap.values()) {
			uncommittedBuffer.commit(readOnly);
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

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link #put(PortalCache,
	 *             Serializable, Object, int, boolean)}
	 */
	@Deprecated
	public static <K extends Serializable, V> void put(
		PortalCache<K, V> portalCache, K key, V value, int ttl) {

		put(portalCache, key, value, ttl, false);
	}

	public static <K extends Serializable, V> void put(
		PortalCache<K, V> portalCache, K key, V value, int ttl, boolean mvcc) {

		PortalCacheMap portalCacheMap = _peekPortalCacheMap();

		UncommittedBuffer uncommittedBuffer = portalCacheMap.get(portalCache);

		if (uncommittedBuffer == null) {
			if (mvcc) {
				uncommittedBuffer = new UncommittedBuffer(
					(PortalCache<Serializable, Object>)portalCache);
			}
			else {
				uncommittedBuffer = new MarkerUncommittedBuffer(
					(PortalCache<Serializable, Object>)portalCache);
			}

			portalCacheMap.put(portalCache, uncommittedBuffer);
		}

		uncommittedBuffer.put(
			key,
			new ValueEntry(value, ttl, SkipReplicationThreadLocal.isEnabled()));
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #removeAll(PortalCache, boolean)}
	 */
	@Deprecated
	public static <K extends Serializable, V> void removeAll(
		PortalCache<K, V> portalCache) {

		removeAll(portalCache, false);
	}

	public static <K extends Serializable, V> void removeAll(
		PortalCache<K, V> portalCache, boolean mvcc) {

		PortalCacheMap portalCacheMap = _peekPortalCacheMap();

		UncommittedBuffer uncommittedBuffer = portalCacheMap.get(portalCache);

		if (uncommittedBuffer == null) {
			if (mvcc) {
				uncommittedBuffer = new UncommittedBuffer(
					(PortalCache<Serializable, Object>)portalCache);
			}
			else {
				uncommittedBuffer = new MarkerUncommittedBuffer(
					(PortalCache<Serializable, Object>)portalCache);
			}

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
		_backupPortalCacheMapsThreadLocal = new CentralizedThreadLocal<>(
			TransactionalPortalCacheHelper.class.getName() +
				"._backupPortalCacheMapsThreadLocal",
			ArrayList::new, false);
	private static final ThreadLocal<List<PortalCacheMap>>
		_portalCacheMapsThreadLocal = new CentralizedThreadLocal<>(
			TransactionalPortalCacheHelper.class.getName() +
				"._portalCacheMapsThreadLocal",
			ArrayList::new, false);
	private static volatile Boolean _transactionalCacheEnabled;

	private static class MarkerUncommittedBuffer extends UncommittedBuffer {

		@Override
		public void commit(boolean readOnly) {
			if (skipCommit(readOnly)) {
				return;
			}

			_markers.compute(
				_portalCacheName,
				(key, placeHolder) -> {
					if (placeHolder != _marker) {
						commitByRemove = true;
					}

					if (!readOnly || !commitByRemove) {
						doCommit();
					}

					if (readOnly) {
						return placeHolder;
					}

					return null;
				});
		}

		@Override
		public void put(Serializable key, ValueEntry valueEntry) {
			ValueEntry oldValueEntry = super._uncommittedMap.put(
				key, valueEntry);

			if (oldValueEntry != null) {
				oldValueEntry.merge(valueEntry);
			}
		}

		private MarkerUncommittedBuffer(
			PortalCache<Serializable, Object> portalCache) {

			super(portalCache);

			_portalCacheName = portalCache.getPortalCacheName();

			_marker = _markers.computeIfAbsent(
				_portalCacheName, key -> new Object());
		}

		private static final Map<String, Object> _markers =
			new ConcurrentReferenceValueHashMap<>(
				FinalizeManager.WEAK_REFERENCE_FACTORY);

		private final Object _marker;
		private final String _portalCacheName;

	}

	private static class UncommittedBuffer {

		public void commit(boolean readOnly) {
			if (skipCommit(readOnly)) {
				return;
			}

			doCommit();
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

				if (oldValueEntry.isRemove()) {
					valueEntry._removed = true;
				}
			}
		}

		public void removeAll(boolean skipReplicator) {
			_uncommittedMap.clear();

			_removeAll = true;

			if (_skipReplicator) {
				_skipReplicator = skipReplicator;
			}
		}

		protected void doCommit() {
			if (_removeAll) {
				if (_skipReplicator) {
					PortalCacheHelperUtil.removeAllWithoutReplicator(
						_portalCache);
				}
				else {
					_portalCache.removeAll();
				}
			}

			for (Map.Entry<? extends Serializable, ValueEntry> entry :
					_uncommittedMap.entrySet()) {

				ValueEntry valueEntry = entry.getValue();

				if (commitByRemove) {
					valueEntry.commitToByRemove(_portalCache, entry.getKey());
				}
				else {
					valueEntry.commitTo(_portalCache, entry.getKey());
				}
			}
		}

		protected boolean skipCommit(boolean readOnly) {
			if (readOnly) {
				_removeAll = false;

				Collection<ValueEntry> valueEntries = _uncommittedMap.values();

				Iterator<ValueEntry> iterator = valueEntries.iterator();

				while (iterator.hasNext()) {
					ValueEntry valueEntry = iterator.next();

					if (valueEntry.isRemove()) {
						iterator.remove();
					}
				}
			}

			if (!_removeAll && _uncommittedMap.isEmpty()) {
				return true;
			}

			return false;
		}

		protected boolean commitByRemove;

		private UncommittedBuffer(
			PortalCache<Serializable, Object> portalCache) {

			_portalCache = portalCache;
		}

		private final PortalCache<Serializable, Object> _portalCache;
		private boolean _removeAll;
		private boolean _skipReplicator = true;
		private final Map<Serializable, ValueEntry> _uncommittedMap =
			new HashMap<>();

	}

	private static class ValueEntry {

		public void commitTo(
			PortalCache<Serializable, Object> portalCache, Serializable key) {

			boolean remove = isRemove();

			if (remove || _removed) {
				if (_skipReplicator) {
					PortalCacheHelperUtil.removeWithoutReplicator(
						portalCache, key);
				}
				else {
					portalCache.remove(key);
				}
			}

			if (!remove) {
				if (_skipReplicator) {
					PortalCacheHelperUtil.putWithoutReplicator(
						portalCache, key, _value, _ttl);
				}
				else {
					portalCache.put(key, _value, _ttl);
				}
			}
		}

		public void commitToByRemove(
			PortalCache<Serializable, Object> portalCache, Serializable key) {

			if (_skipReplicator) {
				PortalCacheHelperUtil.removeWithoutReplicator(portalCache, key);
			}
			else {
				portalCache.remove(key);
			}
		}

		public boolean isRemove() {
			if (_value == _NULL_HOLDER) {
				return true;
			}

			return false;
		}

		public void merge(ValueEntry valueEntry) {
			if (!_skipReplicator) {
				valueEntry._skipReplicator = false;
			}
		}

		private ValueEntry(Object value, int ttl, boolean skipReplicator) {
			_value = value;
			_ttl = ttl;
			_skipReplicator = skipReplicator;
		}

		private boolean _removed;
		private boolean _skipReplicator;
		private final int _ttl;
		private final Object _value;

	}

}