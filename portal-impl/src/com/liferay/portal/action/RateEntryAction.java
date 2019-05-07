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

package com.liferay.portal.action;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.struts.JSONAction;
import com.liferay.ratings.kernel.model.RatingsStats;
import com.liferay.ratings.kernel.service.RatingsEntryServiceUtil;
import com.liferay.ratings.kernel.service.RatingsStatsLocalServiceUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 */
public class RateEntryAction extends JSONAction {

	@Override
	public String getJSON(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		String className = getClassName(httpServletRequest);
		long classPK = getClassPK(httpServletRequest);
		double score = ParamUtil.getDouble(httpServletRequest, "score");

		if (score == -1) {
			RatingsEntryServiceUtil.deleteEntry(className, classPK);
		}
		else {
			RatingsEntryServiceUtil.updateEntry(className, classPK, score);
		}

		RatingsStats stats = RatingsStatsLocalServiceUtil.fetchStats(
			className, classPK);

		double averageScore = 0.0;
		int totalEntries = 0;
		double totalScore = 0.0;

		if (stats != null) {
			averageScore = stats.getAverageScore();
			totalEntries = stats.getTotalEntries();
			totalScore = stats.getTotalScore();
		}

		JSONObject jsonObject = JSONUtil.put(
			"averageScore", averageScore
		).put(
			"score", score
		).put(
			"totalEntries", totalEntries
		).put(
			"totalScore", totalScore
		);

		return jsonObject.toString();
	}

	protected String getClassName(HttpServletRequest httpServletRequest) {
		return ParamUtil.getString(httpServletRequest, "className");
	}

	protected long getClassPK(HttpServletRequest httpServletRequest) {
		return ParamUtil.getLong(httpServletRequest, "classPK");
	}

}