/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.filter;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author André de Oliveira
 */
@ProviderType
public interface DateRangeFilter extends Filter {

	public String getFieldName();

	public String getFormat();

	public String getFrom();

	public String getTimeZoneId();

	public String getTo();

	public boolean isIncludeLower();

	public boolean isIncludeUpper();

}