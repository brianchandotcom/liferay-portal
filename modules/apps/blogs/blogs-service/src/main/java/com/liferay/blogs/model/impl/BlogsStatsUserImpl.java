/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.model.impl;

import com.liferay.blogs.model.BlogsStatsUser;

import java.util.Date;

/**
 * @author Cheryl Tang
 */
public class BlogsStatsUserImpl implements BlogsStatsUser {

	public BlogsStatsUserImpl(
		long entryCount, long groupId, Date lastPostDate,
		long ratingsTotalEntries, double ratingsAverageScore,
		double ratingsTotalScore, long statsUserId) {

		_entryCount = entryCount;
		_groupId = groupId;
		_lastPostDate = lastPostDate;
		_ratingsTotalEntries = ratingsTotalEntries;
		_ratingsAverageScore = ratingsAverageScore;
		_ratingsTotalScore = ratingsTotalScore;
		_statsUserId = statsUserId;
	}

	@Override
	public long getEntryCount() {
		return _entryCount;
	}

	@Override
	public long getGroupId() {
		return _groupId;
	}

	@Override
	public Date getLastPostDate() {
		return _lastPostDate;
	}

	@Override
	public double getRatingsAverageScore() {
		return _ratingsAverageScore;
	}

	@Override
	public long getRatingsTotalEntries() {
		return _ratingsTotalEntries;
	}

	@Override
	public double getRatingsTotalScore() {
		return _ratingsTotalScore;
	}

	@Override
	public long getStatsUserId() {
		return _statsUserId;
	}

	private final long _entryCount;
	private final long _groupId;
	private final Date _lastPostDate;
	private final double _ratingsAverageScore;
	private final long _ratingsTotalEntries;
	private final double _ratingsTotalScore;
	private final long _statsUserId;

}