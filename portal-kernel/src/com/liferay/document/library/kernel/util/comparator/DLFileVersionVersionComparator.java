/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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