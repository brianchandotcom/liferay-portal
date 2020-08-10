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

import com.liferay.headless.delivery.dto.v1_0.MessageBoardSection;
import com.liferay.headless.delivery.internal.dto.v1_0.util.CreatorUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.util.CustomFieldsUtil;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.service.MBCategoryService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.subscription.service.SubscriptionLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Javier Gamarra
 */
@Component(
	property = "dto.class.name=com.liferay.message.boards.model.MBCategory",
	service = {DTOConverter.class, MessageBoardSectionDTOConverter.class}
)
public class MessageBoardSectionDTOConverter
	implements DTOConverter<MBCategory, MessageBoardSection> {

	@Override
	public String getContentType() {
		return MessageBoardSection.class.getSimpleName();
	}

	@Override
	public MessageBoardSection toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		MBCategory mbCategory = _mbCategoryService.getCategory(
			(Long)dtoConverterContext.getId());

		return new MessageBoardSection() {
			{
				actions = dtoConverterContext.getActions();
				creator = CreatorUtil.toCreator(
					_portal, dtoConverterContext.getUriInfoOptional(),
					_userLocalService.fetchUser(mbCategory.getUserId()));
				customFields = CustomFieldsUtil.toCustomFields(
					dtoConverterContext.isAcceptAllLanguages(),
					MBCategory.class.getName(), mbCategory.getCategoryId(),
					mbCategory.getCompanyId(), dtoConverterContext.getLocale());
				dateCreated = mbCategory.getCreateDate();
				dateModified = mbCategory.getModifiedDate();
				description = mbCategory.getDescription();
				id = mbCategory.getCategoryId();
				numberOfMessageBoardSections =
					_mbCategoryService.getCategoriesCount(
						mbCategory.getGroupId(), mbCategory.getCategoryId(),
						WorkflowConstants.STATUS_APPROVED);
				numberOfMessageBoardThreads = mbCategory.getThreadCount();
				siteId = mbCategory.getGroupId();
				subscribed = _subscriptionLocalService.isSubscribed(
					mbCategory.getCompanyId(), dtoConverterContext.getUserId(),
					MBCategory.class.getName(), mbCategory.getCategoryId());
				title = mbCategory.getName();

				setParentMessageBoardSectionId(
					() -> {
						if (mbCategory.getParentCategoryId() == 0L) {
							return null;
						}

						return mbCategory.getParentCategoryId();
					});
			}
		};
	}

	@Reference
	private MBCategoryService _mbCategoryService;

	@Reference
	private Portal _portal;

	@Reference
	private SubscriptionLocalService _subscriptionLocalService;

	@Reference
	private UserLocalService _userLocalService;

}