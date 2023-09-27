/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.trash.model;

import java.io.Serializable;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class TrashEntryList implements Serializable {

	public int getCount() {
		return _count;
	}

	public List<TrashEntry> getOriginalTrashEntries() {
		return _originalTrashEntries;
	}

	public boolean isApproximate() {
		return _approximate;
	}

	public void setApproximate(boolean approximate) {
		_approximate = approximate;
	}

	public void setCount(int count) {
		_count = count;
	}

	public void setOriginalTrashEntries(List<TrashEntry> originalTrashEntries) {
		_originalTrashEntries = originalTrashEntries;
	}

	private boolean _approximate;
	private int _count;
	private transient List<TrashEntry> _originalTrashEntries;

}