/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.exception.SystemException;

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

	public Integer getNewColumnType(Integer defaultType) {
		return defaultType;
	}

	public String getName() {
		return _name;
	}

	public Object getNewValue() {
		return _newValue;
	}

	public Integer getOldColumnType(Integer defaultType) {
		if (_oldColumnType == null) {
			return defaultType;
		}
		else {
			return _oldColumnType;
		}
	}

	public Object getOldValue() {
		return _oldValue;
	}

	public long increment() throws SystemException {
		DB db = DBFactoryUtil.getDB();

		return db.increment();
	}

	public boolean isApplicable(String name) {
		if (_name.equals(name)) {
			return true;
		}
		else {
			return false;
		}
	}

	public void setNewValue(Object newValue) {
		_newValue = newValue;
	}

	public void setOldValue(Object oldValue) {
		_oldValue = oldValue;
	}

	private String _name;
	private Object _newValue;
	private Integer _oldColumnType;
	private Object _oldValue;

}