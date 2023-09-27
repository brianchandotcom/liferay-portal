/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.lists.util.comparator;

import com.liferay.document.library.kernel.util.comparator.VersionNumberComparator;
import com.liferay.dynamic.data.lists.model.DDLRecordSetVersion;

import java.util.Comparator;

/**
 * @author Leonardo Barros
 */
public class DDLRecordSetVersionVersionComparator
	implements Comparator<DDLRecordSetVersion> {

	public DDLRecordSetVersionVersionComparator() {
		this(false);
	}

	public DDLRecordSetVersionVersionComparator(boolean ascending) {
		_versionNumberComparator = new VersionNumberComparator(ascending);
	}

	@Override
	public int compare(
		DDLRecordSetVersion recordSetVersion1,
		DDLRecordSetVersion recordSetVersion2) {

		return _versionNumberComparator.compare(
			recordSetVersion1.getVersion(), recordSetVersion2.getVersion());
	}

	public boolean isAscending() {
		return _versionNumberComparator.isAscending();
	}

	private final VersionNumberComparator _versionNumberComparator;

}