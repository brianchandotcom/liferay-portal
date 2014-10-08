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

package com.liferay.portal.kernel.concurrent;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.util.SetUtil;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import java.util.AbstractMap.SimpleEntry;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.junit.After;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class ConcurrentMapperHashMapTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@After
	public void tearDown() {
		_eventQueue.clear();
	}

	@Test
	public void testClear() {
		Assert.assertTrue(_concurrentMap.isEmpty());
		Assert.assertNull(_concurrentMap.put(_testKey, _testValue));

		_assertEventQueue(Event.MAP_KEY, Event.MAP_VALUE);

		Assert.assertFalse(_concurrentMap.isEmpty());

		_concurrentMap.clear();

		Assert.assertTrue(_concurrentMap.isEmpty());
	}

	@Test
	public void testContainsKey() {
		try {
			_concurrentMap.containsKey(null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Key is null", npe.getMessage());
		}

		Assert.assertFalse(_concurrentMap.containsKey(_testKey));

		_assertEventQueue(Event.MAP_KEY_FOR_QUERY);

		Assert.assertNull(_concurrentMap.put(_testKey, _testValue));

		_assertEventQueue(Event.MAP_KEY, Event.MAP_VALUE);

		Assert.assertTrue(_concurrentMap.containsKey(_testKey));

		_assertEventQueue(Event.MAP_KEY_FOR_QUERY);
	}

	@Test
	public void testContainsValue() {
		try {
			_concurrentMap.containsValue(null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Value is null", npe.getMessage());
		}

		Assert.assertFalse(_concurrentMap.containsValue(_testValue));

		_assertEventQueue(Event.MAP_VALUE_FOR_QUERY);

		Assert.assertNull(_concurrentMap.put(_testKey, _testValue));

		_assertEventQueue(Event.MAP_KEY, Event.MAP_VALUE);

		Assert.assertTrue(_concurrentMap.containsValue(_testValue));

		_assertEventQueue(Event.MAP_VALUE_FOR_QUERY);
	}

	@Test
	public void testEntrySet() {
		Set<Map.Entry<Key, Value>> entrySet = _concurrentMap.entrySet();

		Assert.assertSame(entrySet, _concurrentMap.entrySet());
		Assert.assertTrue(entrySet.isEmpty());
		Assert.assertFalse(entrySet.contains(new Object()));
		Assert.assertFalse(entrySet.remove(new Object()));
		Assert.assertNull(_concurrentMap.put(_testKey, _testValue));

		_assertEventQueue(Event.MAP_KEY, Event.MAP_VALUE);

		Assert.assertEquals(1, entrySet.size());
		Assert.assertTrue(
			entrySet.contains(
				new SimpleEntry<Key, Value>(_testKey, _testValue)));

		_assertEventQueue(Event.MAP_KEY_FOR_QUERY, Event.UNMAP_VALUE_FOR_QUERY);

		Assert.assertFalse(
			entrySet.contains(
				new SimpleEntry<Key, Value>(_testKey, new Value(""))));

		_assertEventQueue(Event.MAP_KEY_FOR_QUERY, Event.UNMAP_VALUE_FOR_QUERY);

		Assert.assertFalse(
			entrySet.remove(
				new SimpleEntry<Key, Value>(_testKey, new Value(""))));

		_assertEventQueue(Event.MAP_KEY_FOR_QUERY, Event.MAP_VALUE_FOR_QUERY);

		Assert.assertTrue(
			entrySet.remove(new SimpleEntry<Key, Value>(_testKey, _testValue)));

		_assertEventQueue(
			Event.MAP_KEY_FOR_QUERY, Event.MAP_VALUE_FOR_QUERY,
			Event.UNMAP_VALUE);

		Assert.assertNull(_concurrentMap.put(_testKey, _testValue));

		_assertEventQueue(Event.MAP_KEY, Event.MAP_VALUE);

		Iterator<Map.Entry<Key, Value>> iterator = entrySet.iterator();

		Assert.assertTrue(iterator.hasNext());

		Map.Entry<Key, Value> entry = iterator.next();

		Assert.assertEquals(entry, entry);
		Assert.assertNotEquals(entry, new Object());
		Assert.assertNotEquals(
			entry, new SimpleEntry<Key, Value>(new Key("someKey"), _testValue));

		_assertEventQueue(Event.UNMAP_KEY_FOR_QUERY);

		Assert.assertNotEquals(
			entry, new SimpleEntry<Key, Value>(_testKey, new Value("")));

		_assertEventQueue(
			Event.UNMAP_KEY_FOR_QUERY, Event.UNMAP_VALUE_FOR_QUERY);

		Assert.assertEquals(
			entry, new SimpleEntry<Key, Value>(_testKey, _testValue));

		_assertEventQueue(
			Event.UNMAP_KEY_FOR_QUERY, Event.UNMAP_VALUE_FOR_QUERY);

		Assert.assertEquals(
			_testKey.hashCode() ^ _testValue.hashCode(), entry.hashCode());
		Assert.assertFalse(iterator.hasNext());
		Assert.assertSame(_testKey, entry.getKey());

		_assertEventQueue(Event.UNMAP_KEY_FOR_QUERY);

		Assert.assertSame(_testValue, entry.getValue());

		_assertEventQueue(Event.UNMAP_VALUE_FOR_QUERY);

		Assert.assertTrue(entrySet.contains(entry));

		_assertEventQueue(
			Event.UNMAP_KEY_FOR_QUERY, Event.MAP_KEY_FOR_QUERY,
			Event.UNMAP_VALUE_FOR_QUERY, Event.UNMAP_VALUE_FOR_QUERY);

		entry.setValue(_testValue2);

		_assertEventQueue(
			Event.UNMAP_KEY_FOR_QUERY, Event.MAP_VALUE_FOR_QUERY,
			Event.UNMAP_VALUE_FOR_QUERY, Event.MAP_KEY, Event.MAP_VALUE,
			Event.UNMAP_KEY, Event.UNMAP_VALUE);

		Assert.assertSame(_testValue2, _concurrentMap.get(_testKey));

		_assertEventQueue(Event.MAP_KEY_FOR_QUERY, Event.UNMAP_VALUE_FOR_QUERY);

		iterator.remove();

		Assert.assertTrue(entrySet.isEmpty());
		Assert.assertFalse(entrySet.contains(entry));

		_assertEventQueue(Event.UNMAP_KEY_FOR_QUERY, Event.MAP_KEY_FOR_QUERY);

		Assert.assertNull(_concurrentMap.put(_testKey, _testValue));

		_assertEventQueue(Event.MAP_KEY, Event.MAP_VALUE);

		Assert.assertEquals(1, entrySet.size());

		entrySet.clear();

		Assert.assertTrue(entrySet.isEmpty());
	}

	@Test
	public void testGet() {
		try {
			_concurrentMap.get(null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Key is null", npe.getMessage());
		}

		Assert.assertNull(_concurrentMap.get(_testKey));

		_assertEventQueue(Event.MAP_KEY_FOR_QUERY);

		Assert.assertNull(_concurrentMap.put(_testKey, _testValue));

		_assertEventQueue(Event.MAP_KEY, Event.MAP_VALUE);

		Assert.assertSame(_testValue, _concurrentMap.get(_testKey));

		_assertEventQueue(Event.MAP_KEY_FOR_QUERY, Event.UNMAP_VALUE_FOR_QUERY);
	}

	@Test
	public void testKeySet() {
		Set<Key> keySet = _concurrentMap.keySet();

		Assert.assertSame(keySet, _concurrentMap.keySet());
		Assert.assertTrue(keySet.isEmpty());
		Assert.assertFalse(keySet.contains(_testKey));

		_assertEventQueue(Event.MAP_KEY_FOR_QUERY);

		Assert.assertFalse(keySet.remove(_testKey));

		_assertEventQueue(Event.MAP_KEY_FOR_QUERY);

		Assert.assertNull(_concurrentMap.put(_testKey, _testValue));

		_assertEventQueue(Event.MAP_KEY, Event.MAP_VALUE);

		Assert.assertEquals(1, keySet.size());
		Assert.assertTrue(keySet.contains(_testKey));

		_assertEventQueue(Event.MAP_KEY_FOR_QUERY);

		Assert.assertTrue(keySet.remove(_testKey));

		_assertEventQueue(Event.MAP_KEY_FOR_QUERY, Event.UNMAP_VALUE);

		Assert.assertNull(_concurrentMap.put(_testKey, _testValue));

		_assertEventQueue(Event.MAP_KEY, Event.MAP_VALUE);

		Assert.assertEquals(1, keySet.size());
		Assert.assertEquals(SetUtil.fromArray(new Key[]{_testKey}), keySet);

		_assertEventQueue(Event.UNMAP_KEY_FOR_QUERY);

		Iterator<Key> iterator = keySet.iterator();

		Assert.assertTrue(iterator.hasNext());

		Key key = iterator.next();

		_assertEventQueue(Event.UNMAP_KEY_FOR_QUERY);

		Assert.assertEquals(_testKey, key);
		Assert.assertFalse(iterator.hasNext());
		Assert.assertTrue(keySet.contains(key));

		_assertEventQueue(Event.MAP_KEY_FOR_QUERY);

		iterator.remove();

		Assert.assertTrue(keySet.isEmpty());
		Assert.assertFalse(keySet.contains(key));

		_assertEventQueue(Event.MAP_KEY_FOR_QUERY);

		Assert.assertNull(_concurrentMap.put(_testKey, _testValue));

		_assertEventQueue(Event.MAP_KEY, Event.MAP_VALUE);

		Assert.assertEquals(1, keySet.size());

		keySet.clear();

		Assert.assertTrue(_concurrentMap.isEmpty());
	}

	@Test
	public void testPut() {
		try {
			_concurrentMap.put(null, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Key is null", npe.getMessage());
		}

		try {
			_concurrentMap.put(_testKey, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Value is null", npe.getMessage());
		}

		Assert.assertNull(_concurrentMap.put(_testKey, _testValue));

		_assertEventQueue(Event.MAP_KEY, Event.MAP_VALUE);

		Assert.assertSame(_testValue, _concurrentMap.put(_testKey, _testValue));

		_assertEventQueue(
			Event.MAP_KEY, Event.MAP_VALUE, Event.UNMAP_KEY, Event.UNMAP_VALUE);

		Assert.assertSame(
			_testValue, _concurrentMap.put(_testKey, _testValue2));

		_assertEventQueue(
			Event.MAP_KEY, Event.MAP_VALUE, Event.UNMAP_KEY, Event.UNMAP_VALUE);

		Assert.assertSame(
			_testValue2, _concurrentMap.put(_testKey, _testValue2));

		_assertEventQueue(
			Event.MAP_KEY, Event.MAP_VALUE, Event.UNMAP_KEY, Event.UNMAP_VALUE);

		Assert.assertSame(_testValue2, _concurrentMap.get(_testKey));

		_assertEventQueue(Event.MAP_KEY_FOR_QUERY, Event.UNMAP_VALUE_FOR_QUERY);
	}

	@Test
	public void testPutAll() {
		Map<Key, Value> map = _createDataMap();

		_concurrentMap.putAll(map);

		Assert.assertEquals(map, _concurrentMap);
	}

	@Test
	public void testPutIfAbsent() {
		try {
			_concurrentMap.putIfAbsent(null, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Key is null", npe.getMessage());
		}

		try {
			_concurrentMap.putIfAbsent(_testKey, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Value is null", npe.getMessage());
		}

		Assert.assertNull(_concurrentMap.putIfAbsent(_testKey, _testValue));

		_assertEventQueue(Event.MAP_KEY, Event.MAP_VALUE);

		Assert.assertSame(
			_testValue, _concurrentMap.putIfAbsent(_testKey, _testValue));

		_assertEventQueue(
			Event.MAP_KEY, Event.MAP_VALUE, Event.UNMAP_KEY, Event.UNMAP_VALUE,
			Event.UNMAP_VALUE_FOR_QUERY);

		Assert.assertSame(
			_testValue, _concurrentMap.putIfAbsent(_testKey, _testValue2));

		_assertEventQueue(
			Event.MAP_KEY, Event.MAP_VALUE, Event.UNMAP_KEY, Event.UNMAP_VALUE,
			Event.UNMAP_VALUE_FOR_QUERY);

		Assert.assertSame(
			_testValue, _concurrentMap.putIfAbsent(_testKey, _testValue2));

		_assertEventQueue(
			Event.MAP_KEY, Event.MAP_VALUE, Event.UNMAP_KEY, Event.UNMAP_VALUE,
			Event.UNMAP_VALUE_FOR_QUERY);

		Assert.assertSame(_testValue, _concurrentMap.get(_testKey));

		_assertEventQueue(Event.MAP_KEY_FOR_QUERY, Event.UNMAP_VALUE_FOR_QUERY);
	}

	@Test
	public void testRemove() {
		try {
			_concurrentMap.remove(null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Key is null", npe.getMessage());
		}

		Assert.assertNull(_concurrentMap.remove(_testKey));

		_assertEventQueue(Event.MAP_KEY_FOR_QUERY);

		Assert.assertNull(_concurrentMap.put(_testKey, _testValue));

		_assertEventQueue(Event.MAP_KEY, Event.MAP_VALUE);

		Assert.assertSame(_testValue, _concurrentMap.remove(_testKey));

		_assertEventQueue(Event.MAP_KEY_FOR_QUERY, Event.UNMAP_VALUE);

		Assert.assertNull(_concurrentMap.remove(_testKey));

		_assertEventQueue(Event.MAP_KEY_FOR_QUERY);

		Assert.assertNull(_concurrentMap.get(_testKey));

		_assertEventQueue(Event.MAP_KEY_FOR_QUERY);
	}

	@Test
	public void testRemoveWithValue() {
		try {
			_concurrentMap.remove(null, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Key is null", npe.getMessage());
		}

		try {
			_concurrentMap.remove(_testKey, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Value is null", npe.getMessage());
		}

		Assert.assertFalse(_concurrentMap.remove(_testKey, _testValue));

		_assertEventQueue(Event.MAP_KEY_FOR_QUERY, Event.MAP_VALUE_FOR_QUERY);

		Assert.assertNull(_concurrentMap.put(_testKey, _testValue));

		_assertEventQueue(Event.MAP_KEY, Event.MAP_VALUE);

		Assert.assertFalse(_concurrentMap.remove(_testKey, new Value("")));

		_assertEventQueue(Event.MAP_KEY_FOR_QUERY, Event.MAP_VALUE_FOR_QUERY);

		Assert.assertTrue(_concurrentMap.remove(_testKey, _testValue));

		_assertEventQueue(
			Event.MAP_KEY_FOR_QUERY, Event.MAP_VALUE_FOR_QUERY,
			Event.UNMAP_VALUE);

		Assert.assertFalse(_concurrentMap.remove(_testKey, _testValue));

		_assertEventQueue(Event.MAP_KEY_FOR_QUERY, Event.MAP_VALUE_FOR_QUERY);

		Assert.assertNull(_concurrentMap.get(_testKey));

		_assertEventQueue(Event.MAP_KEY_FOR_QUERY);
	}

	@Test
	public void testRemoveWithValueConcurrentModification() {
		ConcurrentMap<Key, Value> concurrentMap =
			new ConcurrentTypeReferenceHashMap(
				new ConcurrentHashMap<KeyReference, ValueReference>() {

					@Override
					public ValueReference get(Object key) {
						KeyReference keyReference = (KeyReference)key;

						ValueReference valueReference = super.get(keyReference);

						if (_testKey.equals(keyReference._key)) {
							put(
								new KeyReference(_testKey),
								new ValueReference(_testValue2));
						}

						return valueReference;
					}

				});

		Assert.assertNull(concurrentMap.put(_testKey, _testValue));

		_assertEventQueue(Event.MAP_KEY, Event.MAP_VALUE);

		Assert.assertFalse(concurrentMap.remove(_testKey, _testValue));

		_assertEventQueue(Event.MAP_KEY_FOR_QUERY, Event.MAP_VALUE_FOR_QUERY);
	}

	@Test
	public void testReplace() {
		try {
			_concurrentMap.replace(null, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Key is null", npe.getMessage());
		}

		try {
			_concurrentMap.replace(_testKey, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Value is null", npe.getMessage());
		}

		Assert.assertNull(_concurrentMap.replace(_testKey, _testValue));

		_assertEventQueue(
			Event.MAP_VALUE, Event.MAP_KEY_FOR_QUERY, Event.UNMAP_VALUE);

		Assert.assertNull(_concurrentMap.put(_testKey, _testValue));

		_assertEventQueue(Event.MAP_KEY, Event.MAP_VALUE);

		Assert.assertSame(
			_testValue, _concurrentMap.replace(_testKey, _testValue2));

		_assertEventQueue(
			Event.MAP_VALUE, Event.MAP_KEY_FOR_QUERY, Event.UNMAP_VALUE);

		Assert.assertSame(_testValue2, _concurrentMap.get(_testKey));

		_assertEventQueue(Event.MAP_KEY_FOR_QUERY, Event.UNMAP_VALUE_FOR_QUERY);
	}

	@Test
	public void testReplaceWithValue() {
		try {
			_concurrentMap.replace(null, null, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Key is null", npe.getMessage());
		}

		try {
			_concurrentMap.replace(_testKey, null, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Old value is null", npe.getMessage());
		}

		try {
			_concurrentMap.replace(_testKey, _testValue, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("New value is null", npe.getMessage());
		}

		Assert.assertFalse(
			_concurrentMap.replace(_testKey, _testValue, _testValue2));

		_assertEventQueue(
			Event.MAP_KEY_FOR_QUERY, Event.MAP_VALUE, Event.UNMAP_VALUE);

		Assert.assertNull(_concurrentMap.put(_testKey, _testValue));

		_assertEventQueue(Event.MAP_KEY, Event.MAP_VALUE);

		Assert.assertTrue(
			_concurrentMap.replace(_testKey, _testValue, _testValue2));

		_assertEventQueue(
			Event.MAP_KEY_FOR_QUERY, Event.MAP_VALUE,
			Event.UNMAP_VALUE_FOR_QUERY, Event.UNMAP_VALUE);

		Assert.assertSame(_testValue2, _concurrentMap.get(_testKey));

		_assertEventQueue(Event.MAP_KEY_FOR_QUERY, Event.UNMAP_VALUE_FOR_QUERY);

		Assert.assertFalse(
			_concurrentMap.replace(_testKey, _testValue, _testValue2));

		_assertEventQueue(
			Event.MAP_KEY_FOR_QUERY, Event.MAP_VALUE,
			Event.UNMAP_VALUE_FOR_QUERY, Event.UNMAP_VALUE);
	}

	@Test
	public void testReplaceWithValueConcurrentModification() {
		ConcurrentMap<Key, Value> concurrentMap =
			new ConcurrentTypeReferenceHashMap(
				new ConcurrentHashMap<KeyReference, ValueReference>() {

					@Override
					public ValueReference get(Object key) {
						KeyReference keyReference = (KeyReference)key;

						ValueReference valueReference = super.get(keyReference);

						if (_testKey.equals(keyReference._key)) {
							put(
								new KeyReference(_testKey),
								new ValueReference(_testValue2));
						}

						return valueReference;
					}

				});

		Assert.assertNull(concurrentMap.put(_testKey, _testValue));

		_assertEventQueue(Event.MAP_KEY, Event.MAP_VALUE);

		Assert.assertFalse(
			concurrentMap.replace(_testKey, _testValue, _testValue2));

		_assertEventQueue(
			Event.MAP_KEY_FOR_QUERY, Event.MAP_VALUE,
			Event.UNMAP_VALUE_FOR_QUERY, Event.UNMAP_VALUE);
	}

	@Test
	public void testSerialization() throws Exception {
		Map<Key, Value> map = _createDataMap();

		_concurrentMap.putAll(map);

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(
				unsyncByteArrayOutputStream)) {

			objectOutputStream.writeObject(_concurrentMap);
		}

		ObjectInputStream objectInputStream = new ObjectInputStream(
			new UnsyncByteArrayInputStream(
				unsyncByteArrayOutputStream.unsafeGetByteArray(), 0,
				unsyncByteArrayOutputStream.size()));

		Assert.assertEquals(_concurrentMap, objectInputStream.readObject());
	}

	@Test
	public void testValues() {
		Collection<Value> values = _concurrentMap.values();

		Assert.assertSame(values, _concurrentMap.values());
		Assert.assertTrue(values.isEmpty());
		Assert.assertFalse(values.contains(new Value("")));

		_assertEventQueue(Event.MAP_VALUE_FOR_QUERY);

		Assert.assertFalse(values.remove(new Value("")));
		Assert.assertNull(_concurrentMap.put(_testKey, _testValue));

		_assertEventQueue(Event.MAP_KEY, Event.MAP_VALUE);

		Assert.assertEquals(1, values.size());
		Assert.assertTrue(values.contains(_testValue));

		_assertEventQueue(Event.MAP_VALUE_FOR_QUERY);

		Assert.assertFalse(values.contains(_testValue2));

		_assertEventQueue(Event.MAP_VALUE_FOR_QUERY);

		Assert.assertFalse(values.remove(_testValue2));

		_assertEventQueue(Event.UNMAP_VALUE_FOR_QUERY);

		Assert.assertTrue(values.remove(_testValue));

		_assertEventQueue(Event.UNMAP_VALUE_FOR_QUERY);

		Assert.assertNull(_concurrentMap.put(_testKey, _testValue));

		_assertEventQueue(Event.MAP_KEY, Event.MAP_VALUE);

		Iterator<Value> iterator = values.iterator();

		Assert.assertTrue(iterator.hasNext());

		Value value = iterator.next();

		_assertEventQueue(Event.UNMAP_VALUE_FOR_QUERY);

		Assert.assertSame(_testValue, value);
		Assert.assertFalse(iterator.hasNext());
		Assert.assertTrue(values.contains(value));

		_assertEventQueue(Event.MAP_VALUE_FOR_QUERY);

		iterator.remove();

		Assert.assertTrue(values.isEmpty());
		Assert.assertFalse(values.contains(value));

		_assertEventQueue(Event.MAP_VALUE_FOR_QUERY);

		Assert.assertNull(_concurrentMap.put(_testKey, _testValue));

		_assertEventQueue(Event.MAP_KEY, Event.MAP_VALUE);

		Assert.assertEquals(1, values.size());

		values.clear();

		Assert.assertTrue(_concurrentMap.isEmpty());
	}

	public static class Key implements Serializable {

		public Key(String id) {
			if (id == null) {
				throw new NullPointerException("Id is null");
			}

			_id = id;
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof Key)) {
				return false;
			}

			Key key = (Key)obj;

			return _id.equals(key._id);
		}

		@Override
		public int hashCode() {
			return _id.hashCode();
		}

		private static final long serialVersionUID = 1L;

		private final String _id;

	}

	public static class KeyReference implements Serializable {

		public KeyReference(Key key) {
			if (key == null) {
				throw new NullPointerException("Type is null");
			}

			_key = key;
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof KeyReference)) {
				return false;
			}

			KeyReference keyReference = (KeyReference)obj;

			return _key.equals(keyReference._key);
		}

		@Override
		public int hashCode() {
			return _key.hashCode();
		}

		private static final long serialVersionUID = 1L;

		private final Key _key;

	}

	public static class Value implements Serializable {

		public Value(String valueId) {
			if (valueId == null) {
				throw new NullPointerException("Value id is null");
			}

			_valueId = valueId;
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof Value)) {
				return false;
			}

			Value value = (Value)obj;

			return _valueId.equals(value._valueId);
		}

		@Override
		public int hashCode() {
			return _valueId.hashCode();
		}

		private static final long serialVersionUID = 1L;

		private final String _valueId;

	}

	public static class ValueReference implements Serializable {

		public ValueReference(Value value) {
			if (value == null) {
				throw new NullPointerException("Value is null");
			}

			_value = value;
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof ValueReference)) {
				return false;
			}

			ValueReference valueReference = (ValueReference)obj;

			return _value.equals(valueReference._value);
		}

		@Override
		public int hashCode() {
			return _value.hashCode();
		}

		private static final long serialVersionUID = 1L;

		private final Value _value;

	}

	protected Map<Key, Value> _createDataMap() {
		Map<Key, Value> map = new HashMap<Key, Value>();

		map.put(_testKey, _testValue);
		map.put(new Key("testKey2"), _testValue2);
		map.put(new Key("testKey3"), new Value("testValue3"));

		return map;
	}

	private void _assertEventQueue(Event... events) {
		Assert.assertArrayEquals(
			_eventQueue.toArray(new Event[_eventQueue.size()]), events);

		_eventQueue.clear();
	}

	private static final Queue<Event> _eventQueue =  new LinkedList<Event>();

	private final ConcurrentMap<Key, Value> _concurrentMap =
		new ConcurrentTypeReferenceHashMap();
	private final Key _testKey = new Key("testKey");
	private final Value _testValue = new Value("testValue");
	private final Value _testValue2 = new Value("testValue2");

	private static class ConcurrentTypeReferenceHashMap
		extends
			ConcurrentMapperHashMap<Key, KeyReference, Value, ValueReference> {

		public ConcurrentTypeReferenceHashMap() {
			super(new ConcurrentHashMap<KeyReference, ValueReference>());
		}

		public ConcurrentTypeReferenceHashMap(
			ConcurrentMap<KeyReference, ValueReference> innerConcurrentMap) {

			super(innerConcurrentMap);
		}

		@Override
		public KeyReference mapKey(Key key) {
			_eventQueue.offer(Event.MAP_KEY);

			return new KeyReference(key);
		}

		@Override
		public KeyReference mapKeyForQuery(Key key) {
			_eventQueue.offer(Event.MAP_KEY_FOR_QUERY);

			return new KeyReference(key);
		}

		@Override
		public ValueReference mapValue(Key key, Value value) {
			_eventQueue.offer(Event.MAP_VALUE);

			return new ValueReference(value);
		}

		@Override
		public ValueReference mapValueForQuery(Value value) {
			_eventQueue.offer(Event.MAP_VALUE_FOR_QUERY);

			return new ValueReference(value);
		}

		@Override
		public Key unmapKey(KeyReference keyReference) {
			_eventQueue.offer(Event.UNMAP_KEY);

			return keyReference._key;
		}

		@Override
		public Key unmapKeyForQuery(KeyReference keyReference) {
			_eventQueue.offer(Event.UNMAP_KEY_FOR_QUERY);

			return keyReference._key;
		}

		@Override
		public Value unmapValue(ValueReference valueReference) {
			_eventQueue.offer(Event.UNMAP_VALUE);

			return valueReference._value;
		}

		@Override
		public Value unmapValueForQuery(ValueReference valueReference) {
			_eventQueue.offer(Event.UNMAP_VALUE_FOR_QUERY);

			return valueReference._value;
		}

	}

	private enum Event {

		MAP_KEY, MAP_KEY_FOR_QUERY, MAP_VALUE, MAP_VALUE_FOR_QUERY, UNMAP_KEY,
		UNMAP_KEY_FOR_QUERY, UNMAP_VALUE, UNMAP_VALUE_FOR_QUERY

	}

}