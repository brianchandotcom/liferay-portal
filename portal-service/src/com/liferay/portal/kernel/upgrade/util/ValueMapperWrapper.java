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

package com.liferay.portal.kernel.upgrade.util;

import java.util.Iterator;

/**
 * @author Brian Wing Shun Chan
 */
public class ValueMapperWrapper implements ValueMapper {

	public ValueMapperWrapper(ValueMapper valueMapper) {
		_valueMapper = valueMapper;
	}

	@Override
	public void appendException(Object exception) {
		_valueMapper.appendException(exception);
	}

	@Override
	public Object getNewValue(Object oldValue) throws Exception {
		return _valueMapper.getNewValue(oldValue);
	}

	public ValueMapper getValueMapper() {
		return _valueMapper;
	}

	@Override
	public Iterator<Object> iterator() throws Exception {
		return _valueMapper.iterator();
	}

	@Override
	public void mapValue(Object oldValue, Object newValue) throws Exception {
		_valueMapper.mapValue(oldValue, newValue);
	}

	@Override
	public int size() throws Exception {
		return _valueMapper.size();
	}

	private ValueMapper _valueMapper;

}