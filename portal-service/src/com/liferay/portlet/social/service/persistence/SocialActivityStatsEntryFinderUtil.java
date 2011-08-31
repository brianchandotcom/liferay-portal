/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.social.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @author Brian Wing Shun Chan
 */
public class SocialActivityStatsEntryFinderUtil {
	public static int countTopUsersByCounters(long groupId,
		java.lang.String[] rankingCounters)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().countTopUsersByCounters(groupId, rankingCounters);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivityStatsEntry> findStatsByNameAndPeriod(
		long groupId, java.lang.String statName, int statPeriodStart,
		int statPeriodEnd)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .findStatsByNameAndPeriod(groupId, statName,
			statPeriodStart, statPeriodEnd);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivityStatsEntry> findStatsDistributionByNameAndPeriod(
		long groupId, java.lang.String statName, int statPeriodStart,
		int statPeriodEnd)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .findStatsDistributionByNameAndPeriod(groupId, statName,
			statPeriodStart, statPeriodEnd);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivityStatsEntry> findTagsByCounter(
		long groupId, java.lang.String statName, int statPeriodStart,
		int statPeriodEnd)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .findTagsByCounter(groupId, statName, statPeriodStart,
			statPeriodEnd);
	}

	public static java.util.List<java.lang.Long> findTopUsersByCounters(
		long groupId, java.lang.String[] rankingCounters, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .findTopUsersByCounters(groupId, rankingCounters, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivityStatsEntry> findUserStatsByCounters(
		long groupId, java.util.List<java.lang.Long> userIds,
		java.lang.String[] selectedCounters, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .findUserStatsByCounters(groupId, userIds, selectedCounters,
			start, end);
	}

	public static SocialActivityStatsEntryFinder getFinder() {
		if (_finder == null) {
			_finder = (SocialActivityStatsEntryFinder)PortalBeanLocatorUtil.locate(SocialActivityStatsEntryFinder.class.getName());

			ReferenceRegistry.registerReference(SocialActivityStatsEntryFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(SocialActivityStatsEntryFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(SocialActivityStatsEntryFinderUtil.class,
			"_finder");
	}

	private static SocialActivityStatsEntryFinder _finder;
}