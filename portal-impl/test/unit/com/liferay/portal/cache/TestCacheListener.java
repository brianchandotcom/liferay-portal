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

package com.liferay.portal.cache;

import com.liferay.portal.kernel.cache.CacheListener;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheException;
import com.liferay.portal.kernel.util.HashUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

/**
 * @author Tina Tian
 */
public class TestCacheListener<K extends Serializable, V>
	implements CacheListener<K, V> {

	public void assertActionsNumber(int number) {
		Assert.assertEquals(number, _actions.size());
	}

	public void assertEvicted(K key, V value) {
		_assertAction(ActionType.EVICT, key, value);
	}

	public void assertExpired(K key, V value) {
		_assertAction(ActionType.EXPIRE, key, value);
	}

	public void assertPut(K key, V value) {
		_assertAction(ActionType.PUT, key, value);
	}

	public void assertRemoveAll() {
		_assertAction(ActionType.REMOVE_ALL, null, null);
	}

	public void assertRemoved(K key, V value) {
		_assertAction(ActionType.REMOVE, key, value);
	}

	public void assertUpdated(K key, V value) {
		_assertAction(ActionType.UPDATE, key, value);
	}

	@Override
	public void notifyEntryEvicted(
			PortalCache<K, V> portalCache, K key, V value)
		throws PortalCacheException {

		_actions.add(new Action(ActionType.EVICT, key, value));
	}

	@Override
	public void notifyEntryExpired(
			PortalCache<K, V> portalCache, K key, V value)
		throws PortalCacheException {

		_actions.add(new Action(ActionType.EXPIRE, key, value));
	}

	@Override
	public void notifyEntryPut(PortalCache<K, V> portalCache, K key, V value)
		throws PortalCacheException {

		_actions.add(new Action(ActionType.PUT, key, value));
	}

	@Override
	public void notifyEntryRemoved(
			PortalCache<K, V> portalCache, K key, V value)
		throws PortalCacheException {

		_actions.add(new Action(ActionType.REMOVE, key, value));
	}

	@Override
	public void notifyEntryUpdated(
			PortalCache<K, V> portalCache, K key, V value)
		throws PortalCacheException {

		_actions.add(new Action(ActionType.UPDATE, key, value));
	}

	@Override
	public void notifyRemoveAll(PortalCache<K, V> portalCache)
		throws PortalCacheException {

		_actions.add(new Action(ActionType.REMOVE_ALL, null, null));
	}

	public void reset() {
		_actions.clear();
	}

	private void _assertAction(ActionType actionType, K key, V value) {
		Action action = new Action(actionType, key, value);

		Assert.assertTrue(_actions.contains(action));
	}

	private List<Action> _actions = new ArrayList<Action>();

	private static class Action {

		public Action(ActionType actionType, Object key, Object value) {
			_actionType = actionType;

			if (key == null) {
				key = _NULL_OBJECT;
			}

			if (value == null) {
				value = _NULL_OBJECT;
			}

			_key = key;
			_value = value;
		}

		@Override
		public boolean equals(Object object) {
			if (this == object) {
				return true;
			}

			if (!(object instanceof Action)) {
				return false;
			}

			Action action = (Action)object;

			if (_actionType.equals(action._actionType) &&
				_key.equals(action._key) && _value.equals(action._value)) {

				return true;
			}

			return false;
		}

		public ActionType getActionType() {
			return _actionType;
		}

		public Object getKey() {
			return _key;
		}

		public Object getValue() {
			return _value;
		}

		@Override
		public int hashCode() {
			int hash = HashUtil.hash(0, _actionType);

			hash = HashUtil.hash(hash, _key);

			return HashUtil.hash(hash, _value);
		}

		private static final Object _NULL_OBJECT = new Object();

		private ActionType _actionType;
		private Object _key;
		private Object _value;
	}

	private enum ActionType {

		EVICT, EXPIRE, PUT, REMOVE, REMOVE_ALL, UPDATE

	}

}