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

package com.liferay.headless.delivery.internal.dto.v1_0.converter;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.AssetLinkLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.headless.delivery.dto.v1_0.MessageBoardMessage;
import com.liferay.headless.delivery.dto.v1_0.converter.DTOConverter;
import com.liferay.headless.delivery.dto.v1_0.converter.DTOConverterContext;
import com.liferay.headless.delivery.internal.dto.v1_0.util.AggregateRatingUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.util.CreatorUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.util.CustomFieldsUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.util.RelatedContentUtil;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.message.boards.service.MBMessageService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.ratings.kernel.service.RatingsStatsLocalService;
import com.liferay.subscription.service.SubscriptionLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 */
@Component(
	property = "asset.entry.class.name=com.liferay.message.boards.model.MBMessage",
	service = {DTOConverter.class, MessageBoardMessageDTOConverter.class}
)
public class MessageBoardMessageDTOConverter implements DTOConverter {

	@Override
	public String getContentType() {
		return MessageBoardMessage.class.getSimpleName();
	}

	@Override
	public MessageBoardMessage toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		MBMessage mbMessage = _mbMessageService.getMessage(
			dtoConverterContext.getResourcePrimKey());

		return new MessageBoardMessage() {
			{
				aggregateRating = AggregateRatingUtil.toAggregateRating(
					_ratingsStatsLocalService.fetchStats(
						MBMessage.class.getName(), mbMessage.getMessageId()));
				anonymous = mbMessage.isAnonymous();
				articleBody = mbMessage.getBody();
				customFields = CustomFieldsUtil.toCustomFields(
					MBMessage.class.getName(), mbMessage.getMessageId(),
					mbMessage.getCompanyId(), dtoConverterContext.getLocale());
				dateCreated = mbMessage.getCreateDate();
				dateModified = mbMessage.getModifiedDate();
				encodingFormat = mbMessage.getFormat();
				headline = mbMessage.getSubject();
				id = mbMessage.getMessageId();
				keywords = ListUtil.toArray(
					_assetTagLocalService.getTags(
						MBMessage.class.getName(), mbMessage.getMessageId()),
					AssetTag.NAME_ACCESSOR);
				numberOfMessageBoardAttachments =
					mbMessage.getAttachmentsFileEntriesCount();
				numberOfMessageBoardMessages =
					_mbMessageLocalService.getChildMessagesCount(
						mbMessage.getMessageId(),
						WorkflowConstants.STATUS_APPROVED);
				relatedContents = RelatedContentUtil.toRelatedContents(
					_assetEntryLocalService, _assetLinkLocalService,
					mbMessage.getModelClassName(), mbMessage.getMessageId(),
					dtoConverterContext.getLocale());
				showAsAnswer = mbMessage.isAnswer();
				siteId = mbMessage.getGroupId();
				subscribed = _subscriptionLocalService.isSubscribed(
					mbMessage.getCompanyId(), dtoConverterContext.getUserId(),
					MBThread.class.getName(), mbMessage.getThreadId());

				setCreator(
					() -> {
						if (mbMessage.isAnonymous()) {
							return null;
						}

						return CreatorUtil.toCreator(
							_portal,
							_userLocalService.getUserById(
								mbMessage.getUserId()));
					});
			}
		};
	}

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private AssetLinkLocalService _assetLinkLocalService;

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private MBMessageLocalService _mbMessageLocalService;

	@Reference
	private MBMessageService _mbMessageService;

	@Reference
	private Portal _portal;

	@Reference
	private RatingsStatsLocalService _ratingsStatsLocalService;

	@Reference
	private SubscriptionLocalService _subscriptionLocalService;

	@Reference
	private UserLocalService _userLocalService;

}