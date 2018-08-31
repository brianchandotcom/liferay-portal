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

import com.liferay.portal.kernel.util.MappingEnumeration;
import com.liferay.portal.kernel.util.SetUtil;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

/**
 * @author Minhchau Dang
 * @author Shuyang Zhou
 */
public class PortletSessionAttributeMap extends AbstractMap<String, Object> {

	public PortletSessionAttributeMap(HttpSession session) {
		this(session, null);
	}

	public PortletSessionAttributeMap(HttpSession session, String scopePrefix) {
		this.session = session;
		this.scopePrefix = scopePrefix;
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsKey(Object key) {
		if (key == null) {
			return false;
		}

		key = encodeKey(String.valueOf(key));

		Enumeration<String> enumeration = getAttributeNames(false);

		while (enumeration.hasMoreElements()) {
			String attributeName = enumeration.nextElement();

			if (attributeName.equals(key)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean containsValue(Object value) {
		Enumeration<String> enumeration = getAttributeNames(false);

		while (enumeration.hasMoreElements()) {
			Object attributeValue = session.getAttribute(
				enumeration.nextElement());

			if (attributeValue.equals(value)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Set<Entry<String, Object>> entrySet() {
		Map<String, Object> map = new HashMap<>();

		Enumeration<String> enumeration = getAttributeNames(true);

		while (enumeration.hasMoreElements()) {
			String attributeName = enumeration.nextElement();

			map.put(attributeName, get(attributeName));
		}

		return Collections.unmodifiableSet(map.entrySet());
	}

	@Override
	public Object get(Object key) {
		if (key == null) {
			return null;
		}

		return session.getAttribute(encodeKey(String.valueOf(key)));
	}

	@Override
	public boolean isEmpty() {
		Enumeration<String> enumeration = getAttributeNames(false);

		return !enumeration.hasMoreElements();
	}

	@Override
	public Set<String> keySet() {
		return Collections.unmodifiableSet(
			SetUtil.fromEnumeration(getAttributeNames(true)));
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

		Enumeration<String> enumeration = getAttributeNames(false);

		while (enumeration.hasMoreElements()) {
			enumeration.nextElement();

			size++;
		}

		return size;
	}

	@Override
	public Collection<Object> values() {
		List<Object> attributeValues = new ArrayList<>();

		Enumeration<String> enumeration = getAttributeNames(false);

		while (enumeration.hasMoreElements()) {
			attributeValues.add(
				session.getAttribute(enumeration.nextElement()));
		}

		return attributeValues;
	}

	protected String encodeKey(String key) {
		if (scopePrefix == null) {
			return key;
		}

		return scopePrefix.concat(key);
	}

	protected Enumeration<String> getAttributeNames(boolean removePrefix) {
		Enumeration<String> enumeration = session.getAttributeNames();

		if (scopePrefix == null) {
			return enumeration;
		}

		return new MappingEnumeration<>(
			enumeration, new AttributeNameMapper(scopePrefix, removePrefix));
	}

	protected final String scopePrefix;
	protected final HttpSession session;

	protected static class AttributeNameMapper
		implements MappingEnumeration.Mapper<String, String> {

		@Override
		public String map(String attributeName) {
			if (attributeName.startsWith(_attributeNamespace)) {
				if (_removePrefix) {
					return attributeName.substring(
						_attributeNamespace.length());
				}

				return attributeName;
			}

			return null;
		}

		protected AttributeNameMapper(
			String attributeNamespace, boolean removePrefix) {

			_attributeNamespace = attributeNamespace;
			_removePrefix = removePrefix;
		}

		private final String _attributeNamespace;
		private final boolean _removePrefix;

	}

}