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

package com.liferay.headless.admin.taxonomy.internal.dto.v1_0.converter;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.headless.admin.taxonomy.dto.v1_0.Keyword;
import com.liferay.headless.admin.taxonomy.internal.dto.v1_0.util.CreatorUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.util.GroupUtil;
import com.liferay.subscription.service.SubscriptionLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 * @author Víctor Galán
 */
@Component(
	property = "dto.class.name=com.liferay.asset.kernel.model.AssetTag",
	service = {DTOConverter.class, KeywordDTOConverter.class}
)
public class KeywordDTOConverter implements DTOConverter<AssetTag, Keyword> {

	@Override
	public String getContentType() {
		return Keyword.class.getSimpleName();
	}

	@Override
	public Keyword toDTO(
		DTOConverterContext dtoConverterContext, AssetTag assetTag) {

		Group group = _groupLocalService.fetchGroup(assetTag.getGroupId());

		return new Keyword() {
			{
				actions = dtoConverterContext.getActions();
				assetLibraryKey = GroupUtil.getAssetLibraryKey(group);
				dateCreated = assetTag.getCreateDate();
				dateModified = assetTag.getModifiedDate();
				id = assetTag.getTagId();
				name = assetTag.getName();
				siteId = GroupUtil.getSiteId(group);
				subscribed = _subscriptionLocalService.isSubscribed(
					assetTag.getCompanyId(), dtoConverterContext.getUserId(),
					AssetTag.class.getName(), assetTag.getTagId());

				setCreator(
					() -> {
						if (assetTag.getUserId() != 0) {
							return CreatorUtil.toCreator(
								_portal,
								_userLocalService.fetchUser(
									assetTag.getUserId()));
						}

						return null;
					});
				setKeywordUsageCount(
					() -> {
						Hits hits = _assetEntryLocalService.search(
							assetTag.getCompanyId(),
							new long[] {assetTag.getGroupId()},
							assetTag.getUserId(), null, 0, null, null, null,
							null, assetTag.getName(), true,
							new int[] {
								WorkflowConstants.STATUS_APPROVED,
								WorkflowConstants.STATUS_PENDING,
								WorkflowConstants.STATUS_SCHEDULED
							},
							false, 0, 1);

						return hits.getLength();
					});
			}
		};
	}

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private SubscriptionLocalService _subscriptionLocalService;

	@Reference
	private UserLocalService _userLocalService;

}