/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ratings.test.util;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.ratings.kernel.model.RatingsEntry;
import com.liferay.ratings.kernel.model.RatingsStats;
import com.liferay.ratings.kernel.service.RatingsEntryLocalServiceUtil;
import com.liferay.ratings.kernel.service.RatingsStatsLocalServiceUtil;

/**
 * @author Daniel Kocsis
 */
public class RatingsTestUtil {

	public static RatingsEntry addEntry(String className, long classPK)
		throws Exception {

		return addEntry(className, classPK, 1.0D, TestPropsValues.getUserId());
	}

	public static RatingsEntry addEntry(
			String className, long classPK, double score, long userId)
		throws Exception {

		return RatingsEntryLocalServiceUtil.updateEntry(
			userId, className, classPK, score,
			ServiceContextTestUtil.getServiceContext());
	}

	public static RatingsStats addStats(String className, long classPK) {
		return addStats(className, classPK, RandomTestUtil.randomInt());
	}

	public static RatingsStats addStats(
		String className, long classPK, double averageScore) {

		RatingsStats ratingsStats =
			RatingsStatsLocalServiceUtil.createRatingsStats(
				CounterLocalServiceUtil.increment());

		ratingsStats.setClassName(className);
		ratingsStats.setClassPK(classPK);
		ratingsStats.setTotalEntries(RandomTestUtil.randomInt());
		ratingsStats.setTotalScore(RandomTestUtil.randomInt());
		ratingsStats.setAverageScore(averageScore);

		return RatingsStatsLocalServiceUtil.updateRatingsStats(ratingsStats);
	}

}