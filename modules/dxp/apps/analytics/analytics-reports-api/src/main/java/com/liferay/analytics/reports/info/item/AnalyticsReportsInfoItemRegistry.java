/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.reports.info.item;

import java.util.List;

/**
 * @author David Arques
 */
public interface AnalyticsReportsInfoItemRegistry {

	public AnalyticsReportsInfoItem<?> getAnalyticsReportsInfoItem(String key);

	public List<AnalyticsReportsInfoItem<?>> getAnalyticsReportsInfoItems();

}