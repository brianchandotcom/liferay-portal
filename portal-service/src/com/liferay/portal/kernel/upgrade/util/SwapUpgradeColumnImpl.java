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

package com.liferay.portal.kernel.upgrade.util;

/**
 * @author Brian Wing Shun Chan
 */
public class SwapUpgradeColumnImpl extends BaseUpgradeColumnImpl {

	public SwapUpgradeColumnImpl(
		String name, Integer oldColumnType, ValueMapper valueMapper) {

		super(name, oldColumnType);

		_valueMapper = valueMapper;
	}

	public SwapUpgradeColumnImpl(String name, ValueMapper valueMapper) {
		this(name, null, valueMapper);
	}

	@Override
	public Object getNewValue(Object oldValue) throws Exception {
		return _valueMapper.getNewValue(oldValue);
	}

	private final ValueMapper _valueMapper;

}