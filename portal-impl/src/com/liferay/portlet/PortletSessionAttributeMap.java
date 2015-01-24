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
package com.liferay.portlet;

import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.servlet.http.HttpSession;

/**
 * @author Minhchau Dang
 */
public class PortletSessionAttributeMap implements Map<String, Object> {
	public PortletSessionAttributeMap(
		HttpSession httpSession, String attributeNamespace) {

		_httpSession = httpSession;
		_attributeNamespace = attributeNamespace;
		_attributeNamespaceLength = attributeNamespace.length();
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsKey(Object key) {
		Iterator<String> iterator = new PortletSessionAttributeNameIterator();

		while (iterator.hasNext()) {
			String attributeName = iterator.next();

			if (attributeName.equals(key)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean containsValue(Object value) {
		Iterator<String> iterator = new PortletSessionAttributeNameIterator();

		while (iterator.hasNext()) {
			String attributeName = iterator.next();

			Object attributeValue = get(attributeName);

			if (attributeValue.equals(value)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Set<Entry<String, Object>> entrySet() {
		Map<String, Object> map = new LinkedHashMap<>();

		Iterator<String> iterator = new PortletSessionAttributeNameIterator();

		while (iterator.hasNext()) {
			String attributeName = iterator.next();

			Object attributeValue = get(attributeName);

			map.put(attributeName, attributeValue);
		}

		return map.entrySet();
	}

	@Override
	public Object get(Object key) {
		if (key == null) {
			return null;
		}

		String attributeName = null;

		if (Validator.isNull(_attributeNamespace)) {
			attributeName = key.toString();
		}
		else {
			attributeName = _attributeNamespace + key.toString();
		}

		return _httpSession.getAttribute(attributeName);
	}

	@Override
	public boolean isEmpty() {
		Iterator<String> iterator = new PortletSessionAttributeNameIterator();

		return !iterator.hasNext();
	}

	@Override
	public Set<String> keySet() {
		Set<String> keySet = new LinkedHashSet<>();

		Iterator<String> iterator = new PortletSessionAttributeNameIterator();

		while (iterator.hasNext()) {
			keySet.add(iterator.next());
		}

		return keySet;
	}

	@Override
	public Object put(String key, Object value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void putAll(Map<? extends String, ?> map) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object remove(Object key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int size() {
		int size = 0;

		Iterator<String> iterator = new PortletSessionAttributeNameIterator();

		while (iterator.hasNext()) {
			iterator.next();

			size++;
		}

		return size;
	}

	@Override
	public Collection<Object> values() {
		List<Object> attributeValues = new ArrayList<>();

		Iterator<String> iterator = new PortletSessionAttributeNameIterator();

		while (iterator.hasNext()) {
			String attributeName = iterator.next();

			Object attributeValue = get(attributeName);

			attributeValues.add(attributeValue);
		}

		return attributeValues;
	}

	protected class PortletSessionAttributeNameIterator
		implements Iterator<String> {

		public PortletSessionAttributeNameIterator() {
			_attributeNames = _httpSession.getAttributeNames();

			advanceToNext();
		}

		@Override
		public boolean hasNext() {
			if (_next != null) {
				return true;
			}

			return false;
		}

		@Override
		public String next() {
			if (_next == null) {
				throw new NoSuchElementException();
			}

			String next = _next;

			advanceToNext();

			return next;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

		protected void advanceToNext() {
			while (_attributeNames.hasMoreElements()) {
				String attributeName = _attributeNames.nextElement();

				if (_attributeNamespaceLength == 0) {
					_next = attributeName;

					return;
				}

				if ((attributeName.length() > _attributeNamespaceLength) &&
					attributeName.startsWith(_attributeNamespace)) {

					_next = attributeName.substring(_attributeNamespaceLength);

					return;
				}
			}

			_next = null;
		}

		private final Enumeration<String> _attributeNames;
		private String _next;

	}

	private final String _attributeNamespace;
	private final int _attributeNamespaceLength;
	private final HttpSession _httpSession;

}