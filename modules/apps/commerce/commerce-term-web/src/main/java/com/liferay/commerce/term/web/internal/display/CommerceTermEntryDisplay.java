/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.term.web.internal.display;

import com.liferay.commerce.term.model.CommerceTermEntry;
import com.liferay.commerce.term.service.CommerceTermEntryLocalServiceUtil;
import com.liferay.petra.string.StringPool;

/**
 * @author Andrea Sbarra
 */
public class CommerceTermEntryDisplay {

	public static CommerceTermEntryDisplay of(
		CommerceTermEntry commerceTermEntry) {

		if (commerceTermEntry != null) {
			return new CommerceTermEntryDisplay(commerceTermEntry);
		}

		return _EMPTY_INSTANCE;
	}

	public static CommerceTermEntryDisplay of(long commerceTermEntryId) {
		return of(
			CommerceTermEntryLocalServiceUtil.fetchCommerceTermEntry(
				commerceTermEntryId));
	}

	public long getCommerceTermEntryId() {
		return _commerceTermEntryId;
	}

	public String getName() {
		return _name;
	}

	private CommerceTermEntryDisplay() {
		_commerceTermEntryId = 0;
		_name = StringPool.BLANK;
	}

	private CommerceTermEntryDisplay(CommerceTermEntry commerceTermEntry) {
		_commerceTermEntryId = commerceTermEntry.getCommerceTermEntryId();
		_name = commerceTermEntry.getName();
	}

	private static final CommerceTermEntryDisplay _EMPTY_INSTANCE =
		new CommerceTermEntryDisplay();

	private final long _commerceTermEntryId;
	private final String _name;

}