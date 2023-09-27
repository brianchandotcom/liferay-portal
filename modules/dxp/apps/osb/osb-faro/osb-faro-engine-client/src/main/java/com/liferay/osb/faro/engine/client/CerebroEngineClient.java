/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client;

import com.liferay.osb.faro.model.FaroProject;

import java.util.Date;

/**
 * @author Marcellus Tavares
 */
public interface CerebroEngineClient {

	public long getPageViews(
			FaroProject faroProject, Date fromDate, Date toDate)
		throws Exception;

	public String getSiteMetrics(
			String channelId, FaroProject faroProject, String interval,
			int rangeKey)
		throws Exception;

	public boolean isCustomEventsLimitReached(FaroProject faroProject)
		throws Exception;

	public void updateTimeZone(FaroProject faroProject) throws Exception;

}