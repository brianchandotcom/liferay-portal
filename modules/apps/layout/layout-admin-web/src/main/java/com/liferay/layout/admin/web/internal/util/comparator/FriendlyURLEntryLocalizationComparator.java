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

package com.liferay.layout.admin.web.internal.util.comparator;

import com.liferay.friendly.url.model.FriendlyURLEntryLocalization;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Alejandro Tardín
 */
public class FriendlyURLEntryLocalizationComparator
	extends OrderByComparator<FriendlyURLEntryLocalization> {

	public static final String ORDER_BY_ASC =
		"FriendlyURLEntryLocalization.friendlyURLEntryLocalizationId ASC";

	public static final String ORDER_BY_DESC =
		"FriendlyURLEntryLocalization.friendlyURLEntryLocalizationId DESC";

	public static final String[] ORDER_BY_FIELDS = {
		"friendlyURLEntryLocalizationId"
	};

	public FriendlyURLEntryLocalizationComparator() {
		this(false);
	}

	public FriendlyURLEntryLocalizationComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		FriendlyURLEntryLocalization friendlyURLEntryLocalization1,
		FriendlyURLEntryLocalization friendlyURLEntryLocalization2) {

		int value = Long.compare(
			friendlyURLEntryLocalization1.getFriendlyURLEntryLocalizationId(),
			friendlyURLEntryLocalization2.getFriendlyURLEntryLocalizationId());

		if (_ascending) {
			return -value;
		}

		return value;
	}

	@Override
	public String getOrderBy() {
		if (_ascending) {
			return ORDER_BY_ASC;
		}

		return ORDER_BY_DESC;
	}

	@Override
	public String[] getOrderByFields() {
		return ORDER_BY_FIELDS;
	}

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	private final boolean _ascending;

}