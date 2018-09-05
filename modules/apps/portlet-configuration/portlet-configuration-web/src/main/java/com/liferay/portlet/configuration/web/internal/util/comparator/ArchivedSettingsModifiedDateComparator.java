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