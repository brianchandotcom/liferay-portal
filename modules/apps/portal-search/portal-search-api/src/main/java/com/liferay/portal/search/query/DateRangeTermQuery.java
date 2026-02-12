/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.query;

import java.util.TimeZone;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 */
@ProviderType
public abstract class DateRangeTermQuery extends RangeTermQuery {

	public DateRangeTermQuery(
		String field, boolean includesLower, boolean includesUpper,
		Object lowerBound, Object upperBound) {

		super(field, includesLower, includesUpper, lowerBound, upperBound);
	}

	public abstract String getDateFormat();

	public abstract TimeZone getTimeZone();

	public abstract void setDateFormat(String dateFormat);

	public abstract void setTimeZone(TimeZone timeZone);

}