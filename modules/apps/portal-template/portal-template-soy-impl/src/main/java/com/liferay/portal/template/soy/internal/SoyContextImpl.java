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

import com.liferay.portal.template.soy.constants.SoyTemplateConstants;
import com.liferay.portal.template.soy.data.SoyDataFactory;
import com.liferay.portal.template.soy.internal.data.SoyDataFactoryProvider;
import com.liferay.portal.template.soy.util.SoyContext;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This is the central class to store template arguments. It stores everything
 * as given by the template user, without any transformation to Soy internal
 * types. This is so that values put into this maps are immutables.
 *
 * It can, of course, contain {@link com.liferay.portal.template.soy.util.SoyRawData}
 * values as they are part of the public API and are types known to the user.
 *
 * In order to make use of a {@link SoyContext} in the template engine, it is
 * necessary to process it with a {@link SoyTemplateRecord}, which is the class
 * that really knows how to coerce userland values into soyland values.
 *
 * @author Matthew Tambara
 * @see SoyTemplateRecord
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
	 * @param context initial context values
	 * @param restrictedVariables list of restricted (read-only) variables
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
	public void clearInjectedData() {
		_map.remove(SoyTemplateConstants.INJECTED_DATA);
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
	public Object put(String key, Object value) {
		if (key.equals(SoyTemplateConstants.INJECTED_DATA) &&
			!(value instanceof Map)) {

			throw new IllegalArgumentException("Injected data must be a Map");
		}

		return _map.put(key, value);
	}

	@Override
	public void putAll(Map<? extends String, ?> m) {
		for (Entry<? extends String, ?> entry : m.entrySet()) {
			put(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public void putHTML(String key, String value) {
		SoyDataFactory soyDataFactory =
			SoyDataFactoryProvider.getSoyDataFactory();

		_map.put(key, soyDataFactory.createSoyHTMLData(value));
	}

	@Override
	public void putInjectedData(String key, Object value) {
		Map<String, Object> injectedData = (Map<String, Object>)_map.get(
			SoyTemplateConstants.INJECTED_DATA);

		if (injectedData == null) {
			injectedData = new HashMap<>();

			_map.put(SoyTemplateConstants.INJECTED_DATA, injectedData);
		}

		injectedData.put(key, value);
	}

	@Override
	public Object remove(Object key) {
		return _map.remove(key);
	}

	@Override
	public void removeInjectedData(String key) {
		Map<String, Object> injectedData = (Map<String, Object>)_map.get(
			SoyTemplateConstants.INJECTED_DATA);

		if (injectedData != null) {
			injectedData.remove(key);
		}
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

		Set<Entry<String, Object>> entries = map.entrySet();

		Stream<Entry<String, Object>> stream = entries.stream();

		return stream.filter(
			entry -> entry.getValue() != null
		).filter(
			entry -> !_restrictedVariables.contains(entry.getKey())
		).collect(
			Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)
		);
	}

	private final Map<String, Object> _map;
	private final Set<String> _restrictedVariables;

}