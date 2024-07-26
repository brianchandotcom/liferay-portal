/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.friendly.url.util.comparator;

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

	public static FriendlyURLEntryLocalizationComparator getInstance(
		boolean ascending) {

		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
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

	private FriendlyURLEntryLocalizationComparator(boolean ascending) {
		_ascending = ascending;
	}

	private static final FriendlyURLEntryLocalizationComparator
		_INSTANCE_ASCENDING = new FriendlyURLEntryLocalizationComparator(true);

	private static final FriendlyURLEntryLocalizationComparator
		_INSTANCE_DESCENDING = new FriendlyURLEntryLocalizationComparator(
			false);

	private final boolean _ascending;

}