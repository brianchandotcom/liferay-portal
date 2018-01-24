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

package com.liferay.user.associated.data.util;

import java.util.List;
import java.util.Locale;

/**
 * @author William Newbury
 */
public class UADEntitySetComposite {

	public UADEntitySetComposite(
		long userId, String bundleId,
		List<UADEntityTypeComposite> uadEntityTypeComposites) {

		_userId = userId;
		_bundleId = bundleId;
		_uadEntityTypeComposites = uadEntityTypeComposites;
	}

	public String getBundleId() {
		return _bundleId;
	}

	public int getCount() {
		int count = 0;

		for (UADEntityTypeComposite uadEntityTypeComposite :
				_uadEntityTypeComposites) {

			count += uadEntityTypeComposite.getCount();
		}

		return count;
	}

	public String getStatusLabel(Locale locale) {
		if (getCount() == 0) {
			return "complete";
		}

		return "incomplete";
	}

	public List<UADEntityTypeComposite> getUADEntityTypeComposites() {
		return _uadEntityTypeComposites;
	}

	public long getUserId() {
		return _userId;
	}

	private final String _bundleId;
	private final List<UADEntityTypeComposite> _uadEntityTypeComposites;
	private final long _userId;

}