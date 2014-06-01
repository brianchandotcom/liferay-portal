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

package com.liferay.portal.kernel.settings;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @author Iván Zaera
 */
public class MemorySettings extends BaseModifiableSettings {

	public MemorySettings() {
	}

	public MemorySettings(Settings parentSettings) {
		super(parentSettings);
	}

	@Override
	public Collection<String> getModifiedKeys() {
		return new HashSet<String>(_map.keySet());
	}

	@Override
	public void reset(String key) {
		_map.remove(key);
	}

	@Override
	public ModifiableSettings setValue(String key, String value) {
		_map.put(key, new String[] { value });

		return this;
	}

	@Override
	public ModifiableSettings setValues(String key, String[] values) {
		_map.put(key, values);

		return this;
	}

	@Override
	public void store() {
	}

	@Override
	protected String doGetValue(String key) {
		String[] values = doGetValues(key);

		if (values == null) {
			return null;
		}

		return values[0];
	}

	@Override
	protected String[] doGetValues(String key) {
		return _map.get(key);
	}

	private Map<String, String[]> _map = new HashMap<String, String[]>();

}