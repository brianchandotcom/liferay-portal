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

package com.liferay.journal.service.persistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public interface JournalFeedFinder {

	public int countByKeywords(long companyId, long groupId, String keywords);

	public int countByC_G_F_N_D(
		long companyId, long groupId, String feedId, String name,
		String description, boolean andOperator);

	public int countByC_G_F_N_D(
		long companyId, long groupId, String[] feedIds, String[] names,
		String[] descriptions, boolean andOperator);

	public java.util.List<com.liferay.journal.model.JournalFeed> findByKeywords(
		long companyId, long groupId, String keywords, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<com.liferay.journal.model.JournalFeed> obc);

	public java.util.List<com.liferay.journal.model.JournalFeed>
		findByC_G_F_N_D(
			long companyId, long groupId, String feedId, String name,
			String description, boolean andOperator, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.journal.model.JournalFeed> obc);

	public java.util.List<com.liferay.journal.model.JournalFeed>
		findByC_G_F_N_D(
			long companyId, long groupId, String[] feedIds, String[] names,
			String[] descriptions, boolean andOperator, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.journal.model.JournalFeed> obc);

}