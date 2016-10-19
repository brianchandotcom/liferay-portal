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

package com.liferay.blogs.internal.service;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.model.BlogsStatsUser;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.blogs.service.BlogsStatsUserLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.ratings.kernel.model.RatingsEntry;
import com.liferay.ratings.kernel.service.RatingsEntryLocalService;
import com.liferay.ratings.kernel.service.RatingsEntryLocalServiceWrapper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(immediate = true, service = ServiceWrapper.class)
public class BlogsRatingsEntryLocalServiceWrapper
	extends RatingsEntryLocalServiceWrapper {

	public BlogsRatingsEntryLocalServiceWrapper() {
		super(null);
	}

	public BlogsRatingsEntryLocalServiceWrapper(
		RatingsEntryLocalService ratingsEntryLocalService) {

		super(ratingsEntryLocalService);
	}

	public RatingsEntry updateEntry(
			long userId, String className, long classPK, double score,
			ServiceContext serviceContext)
		throws PortalException {

		if (className.equals(BlogsEntry.class.getName())) {
			BlogsEntry blogsEntry = _blogsEntryLocalService.getEntry(classPK);

			BlogsStatsUser blogsStatsUser =
				_blogsStatsUserLocalService.getStatsUser(
					blogsEntry.getGroupId(), blogsEntry.getUserId());

			int ratingsTotalEntries = blogsStatsUser.getRatingsTotalEntries();
			double ratingsTotalScore = blogsStatsUser.getRatingsTotalScore();

			RatingsEntry ratingsEntry = _ratingsEntryLocalService.fetchEntry(
				userId, className, classPK);

			if (ratingsEntry == null) {
				ratingsTotalEntries++;
				ratingsTotalScore += score;
			}
			else {
				ratingsTotalScore =
					ratingsTotalScore - ratingsEntry.getScore() + score;
			}

			double ratingsAverageScore =
				ratingsTotalScore / ratingsTotalEntries;

			_blogsStatsUserLocalService.updateStatsUser(
				blogsEntry.getGroupId(), blogsEntry.getUserId(),
				ratingsTotalEntries, ratingsTotalScore, ratingsAverageScore);
		}

		return super.updateEntry(
			userId, className, classPK, score, serviceContext);
	}

	@Reference(unbind = "-")
	protected void setBlogsEntryLocalService(
		BlogsEntryLocalService blogsEntryLocalService) {

		_blogsEntryLocalService = blogsEntryLocalService;
	}

	@Reference(unbind = "-")
	protected void setBlogsStatsUserLocalService(
		BlogsStatsUserLocalService blogsStatsUserLocalService) {

		_blogsStatsUserLocalService = blogsStatsUserLocalService;
	}

	@Reference(unbind = "-")
	protected void setRatingsEntryLocalService(
		RatingsEntryLocalService ratingsEntryLocalService) {

		_ratingsEntryLocalService = ratingsEntryLocalService;
	}

	private BlogsEntryLocalService _blogsEntryLocalService;
	private BlogsStatsUserLocalService _blogsStatsUserLocalService;
	private RatingsEntryLocalService _ratingsEntryLocalService;

}