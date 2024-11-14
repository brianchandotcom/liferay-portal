/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.portal.vulcan.dto.action.DTOActionProvider;
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
	service = DTOConverter.class
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
				setActions(
					() -> _dtoActionProvider.getActions(
						assetTag.getGroupId(), assetTag.getTagId(),
						dtoConverterContext.getUriInfo(),
						dtoConverterContext.getUserId()));
				setAssetLibraryKey(() -> GroupUtil.getAssetLibraryKey(group));
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
				setDateCreated(assetTag::getCreateDate);
				setDateModified(assetTag::getModifiedDate);
				setExternalReferenceCode(assetTag::getExternalReferenceCode);
				setId(assetTag::getTagId);
				setKeywordUsageCount(
					() -> {
						Hits hits = _assetEntryLocalService.search(
							assetTag.getCompanyId(),
							new long[] {assetTag.getGroupId()},
							assetTag.getUserId(), null, -1, null, null, null,
							null, assetTag.getName(), true,
							new int[] {
								WorkflowConstants.STATUS_APPROVED,
								WorkflowConstants.STATUS_PENDING,
								WorkflowConstants.STATUS_SCHEDULED
							},
							false, 0, 1);

						return hits.getLength();
					});
				setName(assetTag::getName);
				setSiteId(() -> GroupUtil.getSiteId(group));
				setSubscribed(
					() -> _subscriptionLocalService.isSubscribed(
						assetTag.getCompanyId(),
						dtoConverterContext.getUserId(),
						AssetTag.class.getName(), assetTag.getTagId()));
			}
		};
	}

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference(
		target = "(dto.class.name=com.liferay.headless.admin.taxonomy.dto.v1_0.Keyword)"
	)
	private DTOActionProvider _dtoActionProvider;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private SubscriptionLocalService _subscriptionLocalService;

	@Reference
	private UserLocalService _userLocalService;

}