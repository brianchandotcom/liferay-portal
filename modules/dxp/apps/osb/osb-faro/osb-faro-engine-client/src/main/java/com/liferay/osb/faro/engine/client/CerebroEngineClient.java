/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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