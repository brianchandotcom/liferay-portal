/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.upgrade.util;

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

		return _oldColumnType;
	}

	@Override
	public Object getOldValue() {
		return _oldValue;
	}

	@Override
	public boolean isApplicable(String name) {
		if (_name.equals(name)) {
			return true;
		}

		return false;
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