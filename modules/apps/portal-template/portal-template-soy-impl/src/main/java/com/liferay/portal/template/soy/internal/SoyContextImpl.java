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
import com.liferay.portal.template.soy.utils.SoyContext;

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
 * It can, of course, contain {@link com.liferay.portal.template.soy.utils.SoyRawData}
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
		this(Collections.emptyMap(), false);
	}

	/**
	 * Create a context with initial values.
	 *
	 * @param context initial context values
	 * @param wrap whether to wrap the given context or make a copy of it
	 * @review
	 */
	public SoyContextImpl(Map<String, Object> context, boolean wrap) {
		if (wrap) {
			_map = context;
		}
		else {
			_map = new HashMap<>(context);
		}

		if (getInjectedData() == null) {
			put(SoyTemplateConstants.INJECTED_DATA, new HashMap<>());
		}
	}

	@Override
	public void clear() {
		_map.clear();
	}

	@Override
	public void clearInjectedData() {
		Map<String, Object> injectedData = getInjectedData();

		injectedData.clear();
	}

	@Override
	public boolean containsKey(Object key) {
		return _map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return _map.containsValue(value);
	}

	@Override
	public Set<Entry<String, Object>> entrySet() {
		return _map.entrySet();
	}

	@Override
	public Object get(Object key) {
		return _map.get(key);
	}

	public Map<String, Object> getInjectedData() {
		return (Map<String, Object>)_map.get(
			SoyTemplateConstants.INJECTED_DATA);
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
		return _map.put(key, value);
	}

	@Override
	public void putAll(Map<? extends String, ?> m) {
		_map.putAll(m);
	}

	@Override
	public void putHTML(String key, String value) {
		SoyDataFactory soyDataFactory =
			SoyDataFactoryProvider.getSoyDataFactory();

		_map.put(key, soyDataFactory.createSoyHTMLData(value));
	}

	@Override
	public void putInjectedData(String key, Object value) {
		Map<String, Object> injectedData = getInjectedData();

		injectedData.put(key, value);
	}

	@Override
	public Object remove(Object key) {
		return _map.remove(key);
	}

	@Override
	public void removeInjectedData(String key) {
		Map<String, Object> injectedData = getInjectedData();

		injectedData.remove(key);
	}

	@Override
	public int size() {
		return _map.size();
	}

	@Override
	public Collection<Object> values() {
		return _map.values();
	}

	private final Map<String, Object> _map;

}