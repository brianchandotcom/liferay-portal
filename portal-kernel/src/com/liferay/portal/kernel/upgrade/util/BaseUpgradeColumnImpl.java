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

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class BaseUpgradeColumnImpl implements UpgradeColumn {

	public BaseUpgradeColumnImpl(String name) {
		this(name, null);
	}

	public BaseUpgradeColumnImpl(String name, Integer oldColumnType) {
		_name = name;
		_oldColumnType = oldColumnType;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public Integer getNewColumnType(Integer defaultType) {
		return defaultType;
	}

	@Override
	public Object getNewValue() {
		return _newValue;
	}

	@Override
	public Integer getOldColumnType(Integer defaultType) {
		if (_oldColumnType == null) {
			return defaultType;
		}
		else {
			return _oldColumnType;
		}
	}

	@Override
	public Object getOldValue() {
		return _oldValue;
	}

	@Override
	public long increment() {
		DB db = DBManagerUtil.getDB();

		return db.increment();
	}

	@Override
	public boolean isApplicable(String name) {
		if (_name.equals(name)) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public void setNewValue(Object newValue) {
		_newValue = newValue;
	}

	@Override
	public void setOldValue(Object oldValue) {
		_oldValue = oldValue;
	}

	private final String _name;
	private Object _newValue;
	private Integer _oldColumnType;
	private Object _oldValue;

}