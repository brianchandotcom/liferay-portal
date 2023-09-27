/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.reports.info.item.provider;

import com.liferay.info.item.InfoItemReference;

/**
 * @author Cristina González
 */
public interface AnalyticsReportsInfoItemObjectProvider<T> {

	public T getAnalyticsReportsInfoItemObject(
		InfoItemReference infoItemReference);

	public String getClassName();

}