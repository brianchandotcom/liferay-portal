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

package com.liferay.portal.template.soy.internal;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.portal.template.soy.constants.SoyTemplateConstants;
import com.liferay.portal.template.soy.data.SoyDataFactory;
import com.liferay.portal.template.soy.internal.data.SoyDataFactoryProvider;
import com.liferay.portal.template.soy.util.SoyContext;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * This is the central class to store template arguments. It stores everything
 * as given by the template user, without any transformation to Soy internal
 * types. This is so that values put into this maps are immutables.
 *
 * It can, of course, contain {@link
 * com.liferay.portal.template.soy.util.SoyRawData}
 * values as they are part of the public API and are types known to the user.
 *
 * In order to make use of a {@link SoyContext} in the template engine, it is
 * necessary to process it with a {@link SoyTemplateRecord}, which is the class
 * that really knows how to coerce userland values into soyland values.
 *
 * @author Matthew Tambara
 * @see    SoyTemplateRecord
 * @review
 */
public class SoyContextImpl implements SoyContext {

	public SoyContextImpl() {
		this(Collections.emptyMap(), Collections.emptySet());
	}

	public SoyContextImpl(Map<String, Object> context) {
		this(context, Collections.emptySet());
	}

	/**
	 * Create a context with initial values.
	 *
	 * @param  context initial context values
	 * @param  restrictedVariables list of restricted (read-only) variables
	 * @review
	 */
	public SoyContextImpl(
		Map<String, Object> context, Set<String> restrictedVariables) {

		_map = new HashMap<>(context);
		_restrictedVariables = restrictedVariables;
	}

	@Override
	public void clear() {
		_map.clear();
	}

	@Override
	public SoyContext clearInjectedData() {
		_map.remove(SoyTemplateConstants.INJECTED_DATA);

		return this;
	}

	@Override
	public boolean containsKey(Object key) {
		return _map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return _map.containsValue(value);
	}

	public SoyTemplateRecord createInjectedSoyTemplateRecord() {
		Map<String, Object> injectedData = (Map<String, Object>)_map.get(
			SoyTemplateConstants.INJECTED_DATA);

		if (injectedData == null) {
			injectedData = Collections.emptyMap();
		}

		return new SoyTemplateRecord(_filterRestrictedVariables(injectedData));
	}

	public SoyTemplateRecord createSoyTemplateRecord() {
		return new SoyTemplateRecord(_filterRestrictedVariables(_map));
	}

	@Override
	public Set<Entry<String, Object>> entrySet() {
		return _map.entrySet();
	}

	@Override
	public Object get(Object key) {
		return _map.get(key);
	}

	public Object getInjectedData(String key) {
		Map<String, Object> injectedData = (Map<String, Object>)_map.get(
			SoyTemplateConstants.INJECTED_DATA);

		if (injectedData == null) {
			return null;
		}

		return injectedData.get(key);
	}

	@Override
	public boolean isEmpty() {
		return _map.isEmpty();
	}

	@Override
	public Set<String> keySet() {
		return _map.keySet();
	}

	@Override
	public SoyContext put(String key, Object value) {
		if (key.equals(SoyTemplateConstants.INJECTED_DATA) &&
			!(value instanceof Map)) {

			throw new IllegalArgumentException("Injected data must be a Map");
		}

		_map.put(key, value);

		return this;
	}

	@Override
	public SoyContext put(
		String key, UnsafeSupplier<?, Exception> unsafeSupplier) {

		Object value = null;

		if (unsafeSupplier != null) {
			try {
				value = unsafeSupplier.get();
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		put(key, value);

		return this;
	}

	@Override
	public void putAll(Map<? extends String, ?> m) {
		for (Entry<? extends String, ?> entry : m.entrySet()) {
			put(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public SoyContext putHTML(String key, String value) {
		SoyDataFactory soyDataFactory =
			SoyDataFactoryProvider.getSoyDataFactory();

		_map.put(key, soyDataFactory.createSoyRawData(value));

		return this;
	}

	@Override
	public SoyContext putInjectedData(String key, Object value) {
		Map<String, Object> injectedData = (Map<String, Object>)_map.get(
			SoyTemplateConstants.INJECTED_DATA);

		if (injectedData == null) {
			injectedData = new HashMap<>();

			_map.put(SoyTemplateConstants.INJECTED_DATA, injectedData);
		}

		injectedData.put(key, value);

		return this;
	}

	@Override
	public Object remove(Object key) {
		return _map.remove(key);
	}

	@Override
	public SoyContext removeInjectedData(String key) {
		Map<String, Object> injectedData = (Map<String, Object>)_map.get(
			SoyTemplateConstants.INJECTED_DATA);

		if (injectedData != null) {
			injectedData.remove(key);
		}

		return this;
	}

	@Override
	public int size() {
		return _map.size();
	}

	@Override
	public Collection<Object> values() {
		return _map.values();
	}

	private Map<String, Object> _filterRestrictedVariables(
		Map<String, Object> map) {

		Map<String, Object> filteredMap = new HashMap<>();

		for (Entry<String, Object> entry : map.entrySet()) {
			Object value = entry.getValue();

			if (value == null) {
				continue;
			}

			String key = entry.getKey();

			if (!_restrictedVariables.contains(key)) {
				filteredMap.put(key, value);
			}
		}

		return filteredMap;
	}

	private final Map<String, Object> _map;
	private final Set<String> _restrictedVariables;

}