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

package com.liferay.journal.web.internal.util;

import com.liferay.journal.util.JournalChangeTrackingHelper;
import com.liferay.petra.string.StringPool;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Kocsis
 */
@Component(immediate = true, service = {})
public class JournalChangeTrackingHelperUtil {

	public static String getJournalArticleCTCollectionName(
		long companyId, long userId, long classPK) {

		if (_journalChangeTrackingHelper == null) {
			return StringPool.BLANK;
		}

		return _journalChangeTrackingHelper.getJournalArticleCTCollectionName(
			companyId, userId, classPK);
	}

	public static boolean hasActiveCTCollection(long companyId, long userId) {
		if (_journalChangeTrackingHelper == null) {
			return false;
		}

		return _journalChangeTrackingHelper.hasActiveCTCollection(
			companyId, userId);
	}

	public static boolean isJournalArticleInChangeList(
		long companyId, long userId, long classPK) {

		if (_journalChangeTrackingHelper == null) {
			return false;
		}

		return _journalChangeTrackingHelper.isJournalArticleInChangeList(
			companyId, userId, classPK);
	}

	@Reference(unbind = "-")
	public void setJournalChangeTrackingHelper(
		JournalChangeTrackingHelper journalChangeTrackingHelper) {

		_journalChangeTrackingHelper = journalChangeTrackingHelper;
	}

	private static JournalChangeTrackingHelper _journalChangeTrackingHelper;

}