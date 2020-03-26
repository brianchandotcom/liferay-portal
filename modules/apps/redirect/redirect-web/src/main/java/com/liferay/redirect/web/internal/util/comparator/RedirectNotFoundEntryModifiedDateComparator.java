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

import com.liferay.redirect.model.RedirectNotFoundEntry;

import java.util.Date;

/**
 * @author Alejandro Tardín
 */
public class RedirectNotFoundEntryModifiedDateComparator
	extends BaseRedirectNotFoundEntryDateComparator {

	public RedirectNotFoundEntryModifiedDateComparator(boolean ascending) {
		super(ascending);
	}

	@Override
	protected String getFieldName() {
		return "modifiedDate";
	}

	@Override
	protected Date getFieldValue(RedirectNotFoundEntry redirectNotFoundEntry) {
		return redirectNotFoundEntry.getModifiedDate();
	}

}