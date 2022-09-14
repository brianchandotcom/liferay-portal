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

package com.liferay.document.library.kernel.util.comparator;

import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Bruno Farache
 */
public class DLFileVersionVersionComparator
	extends OrderByComparator<DLFileVersion> {

	public DLFileVersionVersionComparator() {
		this(false);
	}

	public DLFileVersionVersionComparator(boolean ascending) {
		_ascending = ascending;

		_versionNumberComparator = new VersionNumberComparator(_ascending);
	}

	@Override
	public int compare(
		DLFileVersion dlFileVersion1, DLFileVersion dlFileVersion2) {

		return _versionNumberComparator.compare(
			dlFileVersion1.getVersion(), dlFileVersion2.getVersion());
	}

	@Override
	public String getOrderBy() {
		if (_ascending) {
			return _ORDER_BY_ASC;
		}

		return _ORDER_BY_DESC;
	}

	@Override
	public String[] getOrderByFields() {
		return _ORDER_BY_FIELDS;
	}

	public boolean isAscending() {
		return _versionNumberComparator.isAscending();
	}

	private static final String _ORDER_BY_ASC = "DLFileVersion.version ASC";

	private static final String _ORDER_BY_DESC = "DLFileVersion.version DESC";

	private static final String[] _ORDER_BY_FIELDS = {"version"};

	private final boolean _ascending;
	private final VersionNumberComparator _versionNumberComparator;

}