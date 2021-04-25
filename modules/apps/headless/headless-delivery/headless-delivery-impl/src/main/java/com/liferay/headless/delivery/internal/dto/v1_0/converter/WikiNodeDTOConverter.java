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

import com.liferay.headless.delivery.dto.v1_0.WikiNode;
import com.liferay.headless.delivery.dto.v1_0.util.CreatorUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.subscription.service.SubscriptionLocalService;
import com.liferay.wiki.service.WikiPageService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luis Miguel Barcos
 */
@Component(
	property = "dto.class.name=com.liferay.wiki.model.WikiNode",
	service = {DTOConverter.class, WikiNodeDTOConverter.class}
)
public class WikiNodeDTOConverter
	implements DTOConverter<com.liferay.wiki.model.WikiNode, WikiNode> {

	@Override
	public String getContentType() {
		return WikiNode.class.getSimpleName();
	}

	@Override
	public WikiNode toDTO(
			DTOConverterContext dtoConverterContext,
			com.liferay.wiki.model.WikiNode wikiNode)
		throws Exception {

		return new WikiNode() {
			{
				actions = dtoConverterContext.getActions();
				creator = CreatorUtil.toCreator(
					_portal, dtoConverterContext.getUriInfoOptional(),
					_userLocalService.fetchUser(wikiNode.getUserId()));
				dateCreated = wikiNode.getCreateDate();
				dateModified = wikiNode.getModifiedDate();
				description = wikiNode.getDescription();
				externalReferenceCode = wikiNode.getExternalReferenceCode();
				id = wikiNode.getNodeId();
				name = wikiNode.getName();
				numberOfWikiPages = _wikiPageService.getPagesCount(
					wikiNode.getGroupId(), wikiNode.getNodeId(), true);
				siteId = wikiNode.getGroupId();
				subscribed = _subscriptionLocalService.isSubscribed(
					wikiNode.getCompanyId(), dtoConverterContext.getUserId(),
					com.liferay.wiki.model.WikiNode.class.getName(),
					wikiNode.getNodeId());
			}
		};
	}

	@Reference
	private Portal _portal;

	@Reference
	private SubscriptionLocalService _subscriptionLocalService;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private WikiPageService _wikiPageService;

}