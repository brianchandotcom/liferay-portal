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

package com.liferay.redirect.web.internal.util.comparator;

import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.redirect.model.RedirectEntry;

import java.util.Date;

/**
 * @author Alejandro Tardín
 */
public abstract class BaseRedirectEntryDateComparator
	extends BaseRedirectEntryComparator<Date> {

	public BaseRedirectEntryDateComparator(boolean ascending) {
		super(ascending);
	}

	@Override
	public int compare(
		RedirectEntry redirectEntry1, RedirectEntry redirectEntry2) {

		int value = DateUtil.compareTo(
			redirectEntry2.getCreateDate(), redirectEntry2.getCreateDate());

		if (isAscending()) {
			return value;
		}

		return -value;
	}

}