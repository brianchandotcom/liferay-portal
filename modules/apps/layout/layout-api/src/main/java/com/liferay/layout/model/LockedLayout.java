/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.model;

import java.io.Serializable;

import java.util.Date;

/**
 * @author Lourdes Fernández Besada
 */
public class LockedLayout implements Serializable {

	public LockedLayout(
		long classPK, Date lastAutoSaveDate, LockedLayoutType lockedLayoutType,
		String name, long plid, String userName) {

		_classPK = classPK;
		_lastAutoSaveDate = lastAutoSaveDate;
		_lockedLayoutType = lockedLayoutType;
		_name = name;
		_plid = plid;
		_userName = userName;
	}

	public long getClassPK() {
		return _classPK;
	}

	public Date getLastAutoSaveDate() {
		return _lastAutoSaveDate;
	}

	public LockedLayoutType getLockedLayoutType() {
		return _lockedLayoutType;
	}

	public String getName() {
		return _name;
	}

	public long getPlid() {
		return _plid;
	}

	public String getUserName() {
		return _userName;
	}

	private final long _classPK;
	private final Date _lastAutoSaveDate;
	private final LockedLayoutType _lockedLayoutType;
	private final String _name;
	private final long _plid;
	private final String _userName;

}