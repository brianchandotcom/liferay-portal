/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.configuration.web.internal.util.comparator;

import com.liferay.portal.kernel.settings.ArchivedSettings;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Eudaldo Alonso
 */
public class ArchivedSettingsModifiedDateComparator
	extends OrderByComparator<ArchivedSettings> {

	public ArchivedSettingsModifiedDateComparator() {
		this(false);
	}

	public ArchivedSettingsModifiedDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		ArchivedSettings archivedSettings1,
		ArchivedSettings archivedSettings2) {

		int value = DateUtil.compareTo(
			archivedSettings1.getModifiedDate(),
			archivedSettings2.getModifiedDate());

		if (_ascending) {
			return value;
		}

		return -value;
	}

	private final boolean _ascending;

}