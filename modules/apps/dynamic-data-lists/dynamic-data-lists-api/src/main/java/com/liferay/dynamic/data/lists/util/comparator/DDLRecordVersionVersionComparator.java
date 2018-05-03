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

package com.liferay.dynamic.data.lists.util.comparator;

import com.liferay.document.library.kernel.util.comparator.VersionNumberComparator;
import com.liferay.dynamic.data.lists.model.DDLRecordVersion;

import java.util.Comparator;

/**
 * Used to order a list of record versions by version number. The order can be
 * ascending or descending and is defined by the value specified in the class
 * constructor.
 *
 * @author Marcellus Tavares
 */
public class DDLRecordVersionVersionComparator
	implements Comparator<DDLRecordVersion> {

	public DDLRecordVersionVersionComparator() {
		this(false);
	}

	public DDLRecordVersionVersionComparator(boolean ascending) {
		_versionNumberComparator = new VersionNumberComparator(ascending);
	}

	@Override
	public int compare(
		DDLRecordVersion recordVersion1, DDLRecordVersion recordVersion2) {

		return _versionNumberComparator.compare(
			recordVersion1.getVersion(), recordVersion2.getVersion());
	}

	public boolean isAscending() {
		return _versionNumberComparator.isAscending();
	}

	private final VersionNumberComparator _versionNumberComparator;

}