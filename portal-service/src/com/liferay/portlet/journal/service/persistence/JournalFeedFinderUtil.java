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

package com.liferay.portlet.journal.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @generated
 */
@ProviderType
public class JournalFeedFinderUtil {
	public static int countByKeywords(long companyId, long groupId,
		java.lang.String keywords) {
		return getFinder().countByKeywords(companyId, groupId, keywords);
	}

	public static int countByC_G_F_N_D(long companyId, long groupId,
		java.lang.String feedId, java.lang.String name,
		java.lang.String description, boolean andOperator) {
		return getFinder()
				   .countByC_G_F_N_D(companyId, groupId, feedId, name,
			description, andOperator);
	}

	public static int countByC_G_F_N_D(long companyId, long groupId,
		java.lang.String[] feedIds, java.lang.String[] names,
		java.lang.String[] descriptions, boolean andOperator) {
		return getFinder()
				   .countByC_G_F_N_D(companyId, groupId, feedIds, names,
			descriptions, andOperator);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalFeed> findByKeywords(
		long companyId, long groupId, java.lang.String keywords, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.journal.model.JournalFeed> obc) {
		return getFinder()
				   .findByKeywords(companyId, groupId, keywords, start, end, obc);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalFeed> findByC_G_F_N_D(
		long companyId, long groupId, java.lang.String feedId,
		java.lang.String name, java.lang.String description,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.journal.model.JournalFeed> obc) {
		return getFinder()
				   .findByC_G_F_N_D(companyId, groupId, feedId, name,
			description, andOperator, start, end, obc);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalFeed> findByC_G_F_N_D(
		long companyId, long groupId, java.lang.String[] feedIds,
		java.lang.String[] names, java.lang.String[] descriptions,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.journal.model.JournalFeed> obc) {
		return getFinder()
				   .findByC_G_F_N_D(companyId, groupId, feedIds, names,
			descriptions, andOperator, start, end, obc);
	}

	public static JournalFeedFinder getFinder() {
		if (_finder == null) {
			_finder = (JournalFeedFinder)PortalBeanLocatorUtil.locate(JournalFeedFinder.class.getName());

			ReferenceRegistry.registerReference(JournalFeedFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(JournalFeedFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(JournalFeedFinderUtil.class,
			"_finder");
	}

	private static JournalFeedFinder _finder;
}