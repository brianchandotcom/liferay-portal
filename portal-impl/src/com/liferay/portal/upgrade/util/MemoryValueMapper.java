/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.util;

import com.liferay.portal.kernel.upgrade.StagnantRowException;
import com.liferay.portal.kernel.upgrade.util.ValueMapper;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Alexander Chow
 * @author Brian Wing Shun Chan
 */
public class MemoryValueMapper implements ValueMapper {

	public MemoryValueMapper() {
		this(new LinkedHashSet<Object>());
	}

	public MemoryValueMapper(Set<Object> exceptions) {
		_map = new LinkedHashMap<Object, Object>();
		_exceptions = exceptions;
	}

	@Override
	public void appendException(Object exception) {
		_exceptions.add(exception);
	}

	public Map<Object, Object> getMap() {
		return _map;
	}

	@Override
	public Object getNewValue(Object oldValue) throws Exception {
		Object value = _map.get(oldValue);

		if (value == null) {
			if (_exceptions.contains(oldValue)) {
				value = oldValue;
			}
			else {
				throw new StagnantRowException(String.valueOf(oldValue));
			}
		}

		return value;
	}

	@Override
	public Iterator<Object> iterator() throws Exception {
		return _map.keySet().iterator();
	}

	@Override
	public void mapValue(Object oldValue, Object newValue) throws Exception {
		_map.put(oldValue, newValue);
	}

	@Override
	public int size() throws Exception {
		return _map.size();
	}

	private Set<Object> _exceptions;
	private Map<Object, Object> _map;

}