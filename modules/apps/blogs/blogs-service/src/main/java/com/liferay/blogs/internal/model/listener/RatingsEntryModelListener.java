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

package com.liferay.blogs.internal.model.listener;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.model.BlogsStatsUser;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.blogs.service.BlogsStatsUserLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portlet.ratings.model.impl.RatingsEntryModelImpl;
import com.liferay.ratings.kernel.model.RatingsEntry;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(immediate = true, service = ModelListener.class)
public class RatingsEntryModelListener extends BaseModelListener<RatingsEntry> {

	@Override
	public void onBeforeCreate(RatingsEntry ratingsEntry)
		throws ModelListenerException {

		_updateBlogsStatsUser(ratingsEntry);
	}

	@Override
	public void onBeforeRemove(RatingsEntry ratingsEntry)
		throws ModelListenerException {

		String className = ratingsEntry.getClassName();

		if (!className.equals(BlogsEntry.class.getName())) {
			return;
		}

		try {
			BlogsEntry blogsEntry = _blogsEntryLocalService.getEntry(
				ratingsEntry.getClassPK());

			BlogsStatsUser blogsStatsUser =
				_blogsStatsUserLocalService.fetchStatsUser(
					blogsEntry.getGroupId(), blogsEntry.getUserId());

			if (blogsStatsUser != null) {
				double ratingsAverageScore = 0;
				double ratingsTotalScore = 0;

				int ratingsTotalEntries =
					blogsStatsUser.getRatingsTotalEntries() - 1;

				if (ratingsTotalEntries > 0) {
					ratingsTotalScore =
						blogsStatsUser.getRatingsTotalScore() -
							ratingsEntry.getScore();

					ratingsAverageScore =
						ratingsTotalScore / ratingsTotalEntries;
				}

				_blogsStatsUserLocalService.updateStatsUser(
					blogsEntry.getGroupId(), blogsEntry.getUserId(),
					ratingsTotalEntries, ratingsTotalScore,
					ratingsAverageScore);
			}
		}
		catch (PortalException pe) {
			throw new ModelListenerException(pe);
		}
	}

	@Override
	public void onBeforeUpdate(RatingsEntry ratingsEntry)
		throws ModelListenerException {

		_updateBlogsStatsUser(ratingsEntry);
	}

	private void _updateBlogsStatsUser(RatingsEntry ratingsEntry)
		throws ModelListenerException {

		String className = ratingsEntry.getClassName();

		if (!className.equals(BlogsEntry.class.getName())) {
			return;
		}

		try {
			BlogsEntry blogsEntry = _blogsEntryLocalService.getEntry(
				ratingsEntry.getClassPK());

			BlogsStatsUser blogsStatsUser =
				_blogsStatsUserLocalService.getStatsUser(
					blogsEntry.getGroupId(), blogsEntry.getUserId());

			int ratingsTotalEntries = blogsStatsUser.getRatingsTotalEntries();
			double ratingsTotalScore = blogsStatsUser.getRatingsTotalScore();

			RatingsEntryModelImpl ratingsEntryModelImpl =
				(RatingsEntryModelImpl)ratingsEntry;

			double originalScore = ratingsEntryModelImpl.getOriginalScore();

			ratingsTotalScore += ratingsEntry.getScore() - originalScore;

			if (ratingsEntry.isNew()) {
				ratingsTotalEntries++;
			}

			double ratingsAverageScore =
				ratingsTotalScore / ratingsTotalEntries;

			_blogsStatsUserLocalService.updateStatsUser(
				blogsEntry.getGroupId(), blogsEntry.getUserId(),
				ratingsTotalEntries, ratingsTotalScore, ratingsAverageScore);
		}
		catch (PortalException pe) {
			throw new ModelListenerException(pe);
		}
	}

	@Reference
	private BlogsEntryLocalService _blogsEntryLocalService;

	@Reference
	private BlogsStatsUserLocalService _blogsStatsUserLocalService;

}