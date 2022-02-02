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