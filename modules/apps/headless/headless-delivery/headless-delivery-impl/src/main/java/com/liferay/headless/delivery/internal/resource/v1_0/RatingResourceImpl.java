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

package com.liferay.headless.collaboration.internal.resource.v1_0;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.headless.collaboration.dto.v1_0.Rating;
import com.liferay.headless.collaboration.internal.dto.v1_0.util.RatingUtil;
import com.liferay.headless.collaboration.resource.v1_0.RatingResource;
import com.liferay.headless.common.spi.resource.SPIRatingResource;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.MBThreadLocalService;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.ratings.kernel.service.RatingsEntryLocalService;

import javax.ws.rs.core.Context;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/rating.properties",
	scope = ServiceScope.PROTOTYPE, service = RatingResource.class
)
public class RatingResourceImpl extends BaseRatingResourceImpl {

	@Override
	public void deleteRating(Long ratingId) throws Exception {
		SPIRatingResource<Rating> spiRatingResource = _getSPIRatingResource();

		spiRatingResource.deleteRating(ratingId);
	}

	@Override
	public Page<Rating> getBlogPostingRatingsPage(Long blogPostingId)
		throws Exception {

		SPIRatingResource<Rating> spiRatingResource = _getSPIRatingResource();

		return spiRatingResource.getRatingsPage(
			BlogsEntry.class.getName(), blogPostingId);
	}

	@Override
	public Page<Rating> getKnowledgeBaseArticleRatingsPage(
			Long knowledgeBaseArticleId)
		throws Exception {

		SPIRatingResource<Rating> spiRatingResource = _getSPIRatingResource();

		return spiRatingResource.getRatingsPage(
			KBArticle.class.getName(), knowledgeBaseArticleId);
	}

	@Override
	public Page<Rating> getMessageBoardMessageRatingsPage(
			Long messageBoardMessageId)
		throws Exception {

		SPIRatingResource<Rating> spiRatingResource = _getSPIRatingResource();

		return spiRatingResource.getRatingsPage(
			MBMessage.class.getName(), messageBoardMessageId);
	}

	@Override
	public Page<Rating> getMessageBoardThreadRatingsPage(
			Long messageBoardThreadId)
		throws Exception {

		SPIRatingResource<Rating> spiRatingResource = _getSPIRatingResource();

		MBThread mbThread = _mbThreadLocalService.getMBThread(
			messageBoardThreadId);

		return spiRatingResource.getRatingsPage(
			MBMessage.class.getName(), mbThread.getRootMessageId());
	}

	@Override
	public Rating getRating(Long ratingId) throws Exception {
		SPIRatingResource<Rating> spiRatingResource = _getSPIRatingResource();

		return spiRatingResource.getRating(ratingId);
	}

	@Override
	public Rating postBlogPostingRating(Long blogPostingId, Rating rating)
		throws Exception {

		SPIRatingResource<Rating> spiRatingResource = _getSPIRatingResource();

		return spiRatingResource.postRating(
			BlogsEntry.class.getName(), blogPostingId,
			GetterUtil.getDouble(rating.getRatingValue()));
	}

	@Override
	public Rating postKnowledgeBaseArticleRating(
			Long knowledgeBaseArticleId, Rating rating)
		throws Exception {

		SPIRatingResource<Rating> spiRatingResource = _getSPIRatingResource();

		return spiRatingResource.postRating(
			KBArticle.class.getName(), knowledgeBaseArticleId,
			GetterUtil.getDouble(rating.getRatingValue()));
	}

	@Override
	public Rating postMessageBoardMessageRating(
			Long messageBoardMessageId, Rating rating)
		throws Exception {

		SPIRatingResource<Rating> spiRatingResource = _getSPIRatingResource();

		return spiRatingResource.postRating(
			MBMessage.class.getName(), messageBoardMessageId,
			GetterUtil.getDouble(rating.getRatingValue()));
	}

	@Override
	public Rating postMessageBoardThreadRating(
			Long messageBoardThreadId, Rating rating)
		throws Exception {

		SPIRatingResource<Rating> spiRatingResource = _getSPIRatingResource();

		MBThread mbThread = _mbThreadLocalService.getThread(
			messageBoardThreadId);

		return spiRatingResource.postRating(
			MBMessage.class.getName(), mbThread.getRootMessageId(),
			GetterUtil.getDouble(rating.getRatingValue()));
	}

	@Override
	public Rating putRating(Long ratingId, Rating rating) throws Exception {
		SPIRatingResource<Rating> spiRatingResource = _getSPIRatingResource();

		return spiRatingResource.putRating(
			ratingId, GetterUtil.getDouble(rating.getRatingValue()));
	}

	private SPIRatingResource<Rating> _getSPIRatingResource() {
		return new SPIRatingResource<>(
			_ratingsEntryLocalService,
			ratingsEntry -> RatingUtil.toRating(
				_portal, ratingsEntry, _userLocalService),
			_user);
	}

	@Reference
	private MBThreadLocalService _mbThreadLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private RatingsEntryLocalService _ratingsEntryLocalService;

	@Context
	private User _user;

	@Reference
	private UserLocalService _userLocalService;

}