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

package com.liferay.info.test.util.model;

import com.liferay.info.field.InfoField;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lourdes Fernández Besada
 */
public class MockObject {

	public MockObject(long classPK) {
		_classPK = classPK;
	}

	public void addInfoField(InfoField infoField, Object value) {
		_infoFieldsMap.put(infoField, value);
	}

	public long getClassPK() {
		return _classPK;
	}

	public Map<InfoField<?>, Object> getInfoFieldsMap() {
		return _infoFieldsMap;
	}

	private final long _classPK;
	private final Map<InfoField<?>, Object> _infoFieldsMap = new HashMap<>();

}