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

import com.liferay.portal.kernel.upgrade.util.BaseUpgradeColumnImpl;
import com.liferay.portal.kernel.upgrade.util.ValueMapper;
import com.liferay.portal.kernel.upgrade.util.ValueMapperFactoryUtil;

import java.sql.Types;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class PKUpgradeColumnImpl extends BaseUpgradeColumnImpl {

	public PKUpgradeColumnImpl(String name, boolean trackValues) {
		this(name, null, trackValues);
	}

	public PKUpgradeColumnImpl(
		String name, Integer oldColumnType, boolean trackValues) {

		super(name, oldColumnType);

		_newColumnType = new Integer(Types.BIGINT);
		_trackValues = trackValues;

		if (_trackValues) {
			_valueMapper = ValueMapperFactoryUtil.getValueMapper();
		}
	}

	@Override
	public Integer getNewColumnType(Integer defaultType) {
		return _newColumnType;
	}

	@Override
	public Object getNewValue(Object oldValue) throws Exception {
		Long newValue = new Long(increment());

		if (_trackValues) {
			_valueMapper.mapValue(oldValue, newValue);
		}

		return newValue;
	}

	public ValueMapper getValueMapper() {
		return _valueMapper;
	}

	public boolean isTrackValues() {
		return _trackValues;
	}

	private Integer _newColumnType;
	private boolean _trackValues;
	private ValueMapper _valueMapper;

}