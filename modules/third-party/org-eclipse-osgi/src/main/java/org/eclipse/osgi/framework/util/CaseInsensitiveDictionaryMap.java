/*******************************************************************************
 * Copyright (c) 2017 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.osgi.framework.util;

import static java.util.Objects.requireNonNull;

import java.util.*;
import org.eclipse.osgi.internal.messages.Msg;
import org.eclipse.osgi.util.NLS;

/**
 * CaseInsensitiveDictionaryMap classes. This class implements Dictionary and Map with
 * the following behavior:
 * <ul>
 * <li>String keys are case-preserved,
 * but the lookup operations are case-insensitive.</li>
 * <li>Keys and values must not be null.</li>
 * </ul>
 * @since 3.13
 */
public class CaseInsensitiveDictionaryMap<K, V> extends Dictionary<K, V> implements Map<K, V> {
	final Map<Object, V> map;

	/**
	 * Create an empty CaseInsensitiveDictionaryMap.
	 */
	public CaseInsensitiveDictionaryMap() {
		map = new HashMap<>();
	}

	/**
	 * Create an empty CaseInsensitiveDictionaryMap.
	 *
	 * @param initialCapacity The initial capacity.
	 */
	public CaseInsensitiveDictionaryMap(int initialCapacity) {
		map = new HashMap<>(initialCapacity);
	}

	/**
	 * Create a CaseInsensitiveDictionaryMap dictionary from a Dictionary.
	 *
	 * @param dictionary The initial dictionary for this CaseInsensitiveDictionaryMap object.
	 * @throws IllegalArgumentException If a case-variants of a key are
	 * in the dictionary parameter.
	 */
	public CaseInsensitiveDictionaryMap(Dictionary<? extends K, ? extends V> dictionary) {
		this(initialCapacity(dictionary.size()));
		/* initialize the keys and values */
		Enumeration<? extends K> keys = dictionary.keys();
		while (keys.hasMoreElements()) {
			K key = keys.nextElement();
			// ignore null keys
			if (key != null) {
				V value = dictionary.get(key);
				// ignore null values
				if (value != null) {
					if (put(key, value) != null) {
						throw new IllegalArgumentException(NLS.bind(Msg.HEADER_DUPLICATE_KEY_EXCEPTION, key));
					}
				}
			}
		}
	}

	/**
	 * Create a CaseInsensitiveDictionaryMap dictionary from a Map.
	 *
	 * @param map The initial map for this CaseInsensitiveDictionaryMap object.
	 * @throws IllegalArgumentException If a case-variants of a key are
	 * in the map parameter.
	 */
	public CaseInsensitiveDictionaryMap(Map<? extends K, ? extends V> map) {
		this(initialCapacity(map.size()));
		/* initialize the keys and values */
		for (Entry<? extends K, ? extends V> e : map.entrySet()) {
			K key = e.getKey();
			// ignore null keys
			if (key != null) {
				V value = e.getValue();
				// ignore null values
				if (value != null) {
					if (put(key, value) != null) {
						throw new IllegalArgumentException(NLS.bind(Msg.HEADER_DUPLICATE_KEY_EXCEPTION, key));
					}
				}
			}
		}
	}

	/**
	 * Compute the initial capacity of a map for the specified number of entries
	 * based upon the load factor of 0.75f.
	 *
	 * @param size The desired number of entries.
	 * @return The initial capacity of a map.
	 */
	protected static int initialCapacity(int size) {
		return Math.max((int) (size / 0.75f) + 1, 16);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Enumeration<K> keys() {
		return Collections.enumeration(keySet());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Enumeration<V> elements() {
		return Collections.enumeration(values());
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * If the key is a String, the key is located in a case-insensitive manner.
	 */
	@Override
	public V get(Object key) {
		return map.get(keyWrap(key));
	}

	/**
	 * Returns the specified key or, if the key is a String, returns 
	 * a case-insensitive wrapping of the key.
	 *
	 * @param key
	 * @return The specified key or a case-insensitive wrapping of the key.
	 */
	private Object keyWrap(Object key) {
		if (key instanceof String) {
			return new CaseInsensitiveKey((String) key);
		}
		return key;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int size() {
		return map.size();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * The key and value must be non-null.
	 * <p>
	 * If the key is a String, any case-variant will be replaced.
	 */
	@Override
	public V put(K key, V value) {
		requireNonNull(value);
		if (key instanceof String) {
			Object wrappedKey = keyWrap(((String) key));
			V previous = map.remove(wrappedKey); // remove so we put key into map
			map.put(wrappedKey, value);
			return previous;
		}
		return map.put(requireNonNull(key), value);
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * If the key is a String, the key is removed in a case-insensitive manner.
	 */
	@Override
	public V remove(Object key) {
		return map.remove(keyWrap(key));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return map.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear() {
		map.clear();
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * If the key is a String, the key is located in a case-insensitive manner.
	 */
	@Override
	public boolean containsKey(Object key) {
		return map.containsKey(keyWrap(key));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	private transient Set<Entry<K, V>> entrySet = null;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<Entry<K, V>> entrySet() {
		Set<Entry<K, V>> es = entrySet;
		if (es == null) {
			return entrySet = new EntrySet();
		}
		return es;
	}

	private transient Set<K> keySet = null;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<K> keySet() {
		Set<K> ks = keySet;
		if (ks == null) {
			return keySet = new KeySet();
		}
		return ks;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<V> values() {
		return map.values();
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * If the specified map has case-variants of a String key, only the last case-variant
	 * found while iterating over the entrySet will be present in this object.
	 */
	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		for (Entry<? extends K, ? extends V> e : m.entrySet()) {
			put(e.getKey(), e.getValue());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return map.hashCode();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		return map.equals(obj);
	}

	/**
	 * Return an unmodifiable map wrapping this CaseInsensitiveDictionaryMap.
	 *
	 * @return An unmodifiable map wrapping this CaseInsensitiveDictionaryMap.
	 */
	public Map<K, V> asUnmodifiableMap() {
		return Collections.unmodifiableMap(this);
	}

	/**
	 * Return an unmodifiable dictionary wrapping this CaseInsensitiveDictionaryMap.
	 *
	 * @return An unmodifiable dictionary wrapping this CaseInsensitiveDictionaryMap.
	 */
	public Dictionary<K, V> asUnmodifiableDictionary() {
		return unmodifiableDictionary(this);
	}

	/**
	 * Return an unmodifiable dictionary wrapping the specified dictionary.
	 *
	 * @return An unmodifiable dictionary wrapping the specified dictionary.
	 */
	public static <K, V> Dictionary<K, V> unmodifiableDictionary(Dictionary<? extends K, ? extends V> d) {
		return new UnmodifiableDictionary<>(d);
	}

	private static final class UnmodifiableDictionary<K, V> extends Dictionary<K, V> {
		private final Dictionary<? extends K, ? extends V> d;

		UnmodifiableDictionary(Dictionary<? extends K, ? extends V> d) {
			this.d = requireNonNull(d);
		}

		@Override
		public int size() {
			return d.size();
		}

		@Override
		public boolean isEmpty() {
			return d.isEmpty();
		}

		@SuppressWarnings("unchecked")
		@Override
		public Enumeration<K> keys() {
			return (Enumeration<K>) d.keys();
		}

		@SuppressWarnings("unchecked")
		@Override
		public Enumeration<V> elements() {
			return (Enumeration<V>) d.elements();
		}

		@Override
		public V get(Object key) {
			return d.get(key);
		}

		@Override
		public V put(K key, V value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public V remove(Object key) {
			throw new UnsupportedOperationException();
		}

		@Override
		public String toString() {
			return d.toString();
		}

		@Override
		public int hashCode() {
			return d.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			return d.equals(obj);
		}
	}

	private static final class CaseInsensitiveKey {
		final String key;
		private transient int hashCode;

		CaseInsensitiveKey(String key) {
			this.key = key;
		}

		@Override
		public int hashCode() {
			int h = hashCode;
			if (h != 0) {
				return h;
			}
			h = 1;
			for (int i = 0; i < key.length(); i++) {
				char c = key.charAt(i);

				if (c > 127) {
					h = 31 * h + Character.toLowerCase(Character.toUpperCase(c));
				}
				else {
					h = 31 * h + c;

					if ((c >= 'A') && (c <= 'Z')) {
						h += 32;
					}
				}
			}
			return hashCode = h;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj instanceof CaseInsensitiveKey) {
				return key.equalsIgnoreCase(((CaseInsensitiveKey) obj).key);
			}
			return false;
		}

		@Override
		public String toString() {
			return key;
		}
	}

	private final class KeySet extends AbstractSet<K> {
		KeySet() {
		}

		@Override
		public int size() {
			return CaseInsensitiveDictionaryMap.this.size();
		}

		@Override
		public boolean isEmpty() {
			return CaseInsensitiveDictionaryMap.this.isEmpty();
		}

		@Override
		public boolean contains(Object o) {
			return CaseInsensitiveDictionaryMap.this.containsKey(o);
		}

		@Override
		public Iterator<K> iterator() {
			return new KeyIterator<>(map.keySet());
		}

		@Override
		public boolean remove(Object o) {
			return CaseInsensitiveDictionaryMap.this.remove(o) != null;
		}

		@Override
		public void clear() {
			CaseInsensitiveDictionaryMap.this.clear();
		}
	}

	private static final class KeyIterator<K> implements Iterator<K> {
		private final Iterator<Object> i;

		KeyIterator(Collection<Object> c) {
			this.i = c.iterator();
		}

		@Override
		public boolean hasNext() {
			return i.hasNext();
		}

		@SuppressWarnings("unchecked")
		@Override
		public K next() {
			Object k = i.next();
			if (k instanceof CaseInsensitiveKey) {
				k = ((CaseInsensitiveKey) k).key;
			}
			return (K) k;
		}

		@Override
		public void remove() {
			i.remove();
		}
	}

	private final class EntrySet extends AbstractSet<Entry<K, V>> {
		EntrySet() {
		}

		@Override
		public int size() {
			return CaseInsensitiveDictionaryMap.this.size();
		}

		@Override
		public boolean isEmpty() {
			return CaseInsensitiveDictionaryMap.this.isEmpty();
		}

		@Override
		public Iterator<Entry<K, V>> iterator() {
			return new EntryIterator<>(map.entrySet());
		}

		@Override
		public void clear() {
			CaseInsensitiveDictionaryMap.this.clear();
		}
	}

	private static final class EntryIterator<K, V> implements Iterator<Entry<K, V>> {
		private final Iterator<Entry<Object, V>> i;

		EntryIterator(Collection<Entry<Object, V>> c) {
			this.i = c.iterator();
		}

		@Override
		public boolean hasNext() {
			return i.hasNext();
		}

		@Override
		public Entry<K, V> next() {
			return new CaseInsentiveEntry<>(i.next());
		}

		@Override
		public void remove() {
			i.remove();
		}
	}

	private static final class CaseInsentiveEntry<K, V> implements Entry<K, V> {
		private final Entry<Object, V> entry;

		CaseInsentiveEntry(Entry<Object, V> entry) {
			this.entry = entry;
		}

		@SuppressWarnings("unchecked")
		@Override
		public K getKey() {
			Object k = entry.getKey();
			if (k instanceof CaseInsensitiveKey) {
				k = ((CaseInsensitiveKey) k).key;
			}
			return (K) k;
		}

		@Override
		public V getValue() {
			return entry.getValue();
		}

		@Override
		public V setValue(V value) {
			return entry.setValue(requireNonNull(value));
		}

		@Override
		public int hashCode() {
			return Objects.hashCode(entry.getKey()) ^ Objects.hashCode(entry.getValue());
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Entry) {
				Entry<?, ?> other = (Entry<?, ?>) obj;
				Object k1 = entry.getKey();
				@SuppressWarnings("unchecked")
				Object k2 = (other instanceof CaseInsentiveEntry) ? ((CaseInsentiveEntry<K, V>) other).entry.getKey() : other.getKey();
				return Objects.equals(k1, k2) && Objects.equals(entry.getValue(), other.getValue());
			}
			return false;
		}

		@Override
		public String toString() {
			return entry.toString();
		}
	}
}
/* @generated */