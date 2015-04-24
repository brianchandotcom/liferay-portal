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

package com.liferay.portlet.ratings.util.test;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portlet.ratings.model.RatingsEntry;
import com.liferay.portlet.ratings.model.RatingsStats;
import com.liferay.portlet.ratings.service.RatingsEntryLocalServiceUtil;
import com.liferay.portlet.ratings.service.RatingsStatsLocalServiceUtil;

/**
 * @author Daniel Kocsis
 */
public class RatingsTestUtil {

	public static RatingsEntry addEntry(String className, long classPK)
		throws Exception {

		return addEntry(className, classPK, 1.0d, TestPropsValues.getUserId());
	}

	public static RatingsEntry addEntry(
			String className, long classPK, double score, long userId)
		throws Exception {

		return RatingsEntryLocalServiceUtil.updateEntry(
			userId, className, classPK, score,
			ServiceContextTestUtil.getServiceContext());
	}

	public static RatingsStats addStats(String className, long classPK)
		throws Exception {

		return addStats(className, classPK, RandomTestUtil.randomInt());
	}

	public static RatingsStats addStats(
			String className, long classPK, double averageScore)
		throws Exception {

		long statsId = CounterLocalServiceUtil.increment();

		RatingsStats ratingsStats =
			RatingsStatsLocalServiceUtil.createRatingsStats(statsId);

		ratingsStats.setClassName(className);
		ratingsStats.setClassPK(classPK);
		ratingsStats.setTotalEntries(RandomTestUtil.randomInt());
		ratingsStats.setTotalScore(RandomTestUtil.randomInt());
		ratingsStats.setAverageScore(averageScore);

		return RatingsStatsLocalServiceUtil.updateRatingsStats(ratingsStats);
	}

}