/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.test.util;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheException;
import com.liferay.portal.kernel.cache.PortalCacheListener;

import java.io.Serializable;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.junit.Assert;

/**
 * @author Tina Tian
 */
public class TestPortalCacheListener<K extends Serializable, V>
	implements PortalCacheListener<K, V> {

	public void assertActionsCount(int count) {
		Assert.assertEquals(_allActions(), count, _actions.size());
	}

	public void assertEvicted(K key, V value, int timeToLive) {
		_assertAction(ActionType.EVICT, key, value, timeToLive);
	}

	public void assertExpired(K key, V value, int timeToLive) {
		_assertAction(ActionType.EXPIRE, key, value, timeToLive);
	}

	public void assertPut(K key, V value) {
		_assertAction(
			ActionType.PUT, key, value, PortalCache.DEFAULT_TIME_TO_LIVE);
	}

	public void assertPut(K key, V value, int timeToLive) {
		_assertAction(ActionType.PUT, key, value, timeToLive);
	}

	public void assertRemoveAll() {
		_assertAction(
			ActionType.REMOVE_ALL, null, null,
			PortalCache.DEFAULT_TIME_TO_LIVE);
	}

	public void assertRemoved(K key, V value) {
		_assertAction(
			ActionType.REMOVE, key, value, PortalCache.DEFAULT_TIME_TO_LIVE);
	}

	public void assertRemoved(K key, V value, int timeToLive) {
		_assertAction(ActionType.REMOVE, key, value, timeToLive);
	}

	public void assertUpdated(K key, V value) {
		_assertAction(
			ActionType.UPDATE, key, value, PortalCache.DEFAULT_TIME_TO_LIVE);
	}

	public void assertUpdated(K key, V value, int timeToLive) {
		_assertAction(ActionType.UPDATE, key, value, timeToLive);
	}

	@Override
	public void dispose() {
	}

	@Override
	public void notifyEntryEvicted(
			PortalCache<K, V> portalCache, K key, V value, int timeToLive)
		throws PortalCacheException {

		_actions.add(new Action(ActionType.EVICT, key, value, timeToLive));
	}

	@Override
	public void notifyEntryExpired(
			PortalCache<K, V> portalCache, K key, V value, int timeToLive)
		throws PortalCacheException {

		_actions.add(new Action(ActionType.EXPIRE, key, value, timeToLive));
	}

	@Override
	public void notifyEntryPut(
			PortalCache<K, V> portalCache, K key, V value, int timeToLive)
		throws PortalCacheException {

		_actions.add(new Action(ActionType.PUT, key, value, timeToLive));
	}

	@Override
	public void notifyEntryRemoved(
			PortalCache<K, V> portalCache, K key, V value, int timeToLive)
		throws PortalCacheException {

		_actions.add(new Action(ActionType.REMOVE, key, value, timeToLive));
	}

	@Override
	public void notifyEntryUpdated(
			PortalCache<K, V> portalCache, K key, V value, int timeToLive)
		throws PortalCacheException {

		_actions.add(new Action(ActionType.UPDATE, key, value, timeToLive));
	}

	@Override
	public void notifyRemoveAll(PortalCache<K, V> portalCache)
		throws PortalCacheException {

		_actions.add(
			new Action(
				ActionType.REMOVE_ALL, null, null,
				PortalCache.DEFAULT_TIME_TO_LIVE));
	}

	public void reset() {
		_actions.clear();
	}

	private String _allActions() {
		StringBundler sb = new StringBundler(2 + (2 * _actions.size()));

		sb.append("All actions{");

		for (Action action : _actions) {
			sb.append(action.toString());
			sb.append(",");
		}

		sb.setStringAt("}", sb.index() - 1);

		return sb.toString();
	}

	private void _assertAction(
		ActionType actionType, K key, V value, int timeToLive) {

		Action action = new Action(actionType, key, value, timeToLive);

		Assert.assertTrue(_allActions(), _actions.contains(action));
	}

	private final List<Action> _actions = new CopyOnWriteArrayList<>();

	private static class Action {

		public Action(
			ActionType actionType, Object key, Object value, int timeToLive) {

			_actionType = actionType;

			if (key == null) {
				key = _NULL_OBJECT;
			}

			if (value == null) {
				value = _NULL_OBJECT;
			}

			_key = key;
			_value = value;
			_timeToLive = timeToLive;
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
				_key.equals(action._key) && _value.equals(action._value) &&
				(_timeToLive == action._timeToLive)) {

				return true;
			}

			return false;
		}

		@SuppressWarnings("unused")
		public ActionType getActionType() {
			return _actionType;
		}

		@SuppressWarnings("unused")
		public Object getKey() {
			return _key;
		}

		@SuppressWarnings("unused")
		public int getTimeToLive() {
			return _timeToLive;
		}

		@SuppressWarnings("unused")
		public Object getValue() {
			return _value;
		}

		@Override
		public int hashCode() {
			int hash = HashUtil.hash(0, _actionType);

			hash = HashUtil.hash(hash, _key);
			hash = HashUtil.hash(hash, _value);

			return HashUtil.hash(hash, _timeToLive);
		}

		@Override
		public String toString() {
			return StringBundler.concat(
				"{actiontype: ", _actionType, ", key: ", _key, ", timetolive: ",
				_timeToLive, ", value: ", _value, "}");
		}

		private static final Object _NULL_OBJECT = new Object();

		private final ActionType _actionType;
		private final Object _key;
		private int _timeToLive;
		private final Object _value;

	}

	private enum ActionType {

		EVICT, EXPIRE, PUT, REMOVE, REMOVE_ALL, UPDATE

	}

}