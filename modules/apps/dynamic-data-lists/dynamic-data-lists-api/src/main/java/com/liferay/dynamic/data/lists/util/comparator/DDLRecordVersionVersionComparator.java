/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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