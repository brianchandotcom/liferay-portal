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

package com.liferay.portal.upgrade.v5_0_0.util;

import com.liferay.portal.kernel.upgrade.util.BaseUpgradeColumnImpl;
import com.liferay.portal.kernel.upgrade.util.UpgradeColumn;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Alexander Chow
 */
public class IGFolderNameColumnImpl extends BaseUpgradeColumnImpl {

	public IGFolderNameColumnImpl(
		UpgradeColumn groupIdColumn, UpgradeColumn parentFolderIdColumn) {

		super("name", null);

		_groupIdColumn = groupIdColumn;
		_parentFolderIdColumn = parentFolderIdColumn;
	}

	public Set<String> getDistintNames() {
		return _distinctNames;
	}

	public Object getNewValue(Object oldValue) throws Exception {
		String newName = (String)oldValue;

		while (_distinctNames.contains(_getKey(newName))) {
			_counter++;

			newName = newName + StringPool.SPACE + _counter;
		}

		_distinctNames.add(_getKey(newName));

		return newName;
	}

	private String _getKey(String name) {
		StringBundler sb = new StringBundler(5);

		sb.append(_groupIdColumn.getOldValue());
		sb.append(StringPool.UNDERLINE);
		sb.append(_parentFolderIdColumn.getOldValue());
		sb.append(StringPool.UNDERLINE);
		sb.append(name);

		return sb.toString();
	}

	private int _counter = 0;
	private Set<String> _distinctNames = new HashSet<String>();
	private UpgradeColumn _groupIdColumn;
	private UpgradeColumn _parentFolderIdColumn;

}