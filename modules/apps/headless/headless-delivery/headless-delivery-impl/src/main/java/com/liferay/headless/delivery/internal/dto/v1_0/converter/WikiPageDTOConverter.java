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
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.headless.delivery.dto.v1_0.TaxonomyCategoryBrief;
import com.liferay.headless.delivery.dto.v1_0.WikiPage;
import com.liferay.headless.delivery.internal.dto.v1_0.util.AggregateRatingUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.util.CreatorUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.util.CustomFieldsUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.util.RelatedContentUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.util.TaxonomyCategoryBriefUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.osgi.service.component.annotations.Component;

/**
 * @author Luis Miguel Barcos
 */
@Component(
	property = "dto.class.name=com.liferay.wiki.model.WikiPage",
	service = {DTOConverter.class, WikiPageDTOConverter.class}
)
public class WikiPageDTOConverter
	implements DTOConverter<com.liferay.wiki.model.WikiPage, WikiPage> {

	@Override
	public String getContentType() {
		return WikiPage.class.getSimpleName();
	}

	@Override
	public WikiPage toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		return new WikiPage() {
			{
				actions = HashMapBuilder.put(
					"add-page",
					addAction(
						ActionKeys.UPDATE, wikiPage.getResourcePrimKey(),
						"postWikiPageWikiPage", wikiPage.getUserId(),
						WikiPage.class.getName(), wikiPage.getGroupId())
				).put(
					"delete",
					addAction(
						ActionKeys.DELETE, wikiPage.getResourcePrimKey(),
						"deleteWikiPage", wikiPage.getUserId(),
						WikiPage.class.getName(), wikiPage.getGroupId())
				).put(
					"get",
					addAction(
						ActionKeys.VIEW, wikiPage.getResourcePrimKey(),
						"getWikiPage", wikiPage.getUserId(),
						WikiPage.class.getName(), wikiPage.getGroupId())
				).put(
					"replace",
					addAction(
						ActionKeys.UPDATE, wikiPage.getResourcePrimKey(),
						"putWikiPage", wikiPage.getUserId(),
						WikiPage.class.getName(), wikiPage.getGroupId())
				).put(
					"subscribe",
					addAction(
						ActionKeys.SUBSCRIBE, wikiPage.getResourcePrimKey(),
						"putWikiPageSubscribe", wikiPage.getUserId(),
						WikiPage.class.getName(), wikiPage.getGroupId())
				).put(
					"unsubscribe",
					addAction(
						ActionKeys.SUBSCRIBE, wikiPage.getResourcePrimKey(),
						"putWikiPageUnsubscribe", wikiPage.getUserId(),
						WikiPage.class.getName(), wikiPage.getGroupId())
				).build();
				aggregateRating = AggregateRatingUtil.toAggregateRating(
					_ratingsStatsLocalService.fetchStats(
						com.liferay.wiki.model.WikiPage.class.getName(),
						wikiPage.getResourcePrimKey()));
				content = wikiPage.getContent();
				creator = CreatorUtil.toCreator(
					_portal, Optional.of(contextUriInfo),
					_userLocalService.fetchUser(wikiPage.getUserId()));
				customFields = CustomFieldsUtil.toCustomFields(
					contextAcceptLanguage.isAcceptAllLanguages(),
					com.liferay.wiki.model.WikiPage.class.getName(),
					wikiPage.getPageId(), wikiPage.getCompanyId(),
					contextAcceptLanguage.getPreferredLocale());
				dateCreated = wikiPage.getCreateDate();
				dateModified = wikiPage.getModifiedDate();
				description = wikiPage.getSummary();
				encodingFormat = _getEncodingFormat(wikiPage);
				headline = wikiPage.getTitle();
				id = wikiPage.getPageId();
				keywords = ListUtil.toArray(
					_assetTagLocalService.getTags(
						BlogsEntry.class.getName(), wikiPage.getPageId()),
					AssetTag.NAME_ACCESSOR);
				numberOfAttachments = wikiPage.getAttachmentsFileEntriesCount();
				numberOfWikiPages = Optional.ofNullable(
					wikiPage.getChildPages()
				).map(
					List::size
				).orElse(
					0
				);
				relatedContents = RelatedContentUtil.toRelatedContents(
					_assetEntryLocalService, _assetLinkLocalService,
					_dtoConverterRegistry, wikiPage.getModelClassName(),
					wikiPage.getResourcePrimKey(),
					contextAcceptLanguage.getPreferredLocale());
				siteId = wikiPage.getGroupId();
				subscribed = _subscriptionLocalService.isSubscribed(
					wikiPage.getCompanyId(), contextUser.getUserId(),
					com.liferay.wiki.model.WikiPage.class.getName(),
					wikiPage.getResourcePrimKey());
				taxonomyCategoryBriefs = TransformUtil.transformToArray(
					_assetCategoryLocalService.getCategories(
						com.liferay.wiki.model.WikiPage.class.getName(),
						wikiPage.getPageId()),
					assetCategory ->
						TaxonomyCategoryBriefUtil.toTaxonomyCategoryBrief(
							assetCategory,
							new DefaultDTOConverterContext(
								contextAcceptLanguage.isAcceptAllLanguages(),
								Collections.emptyMap(), _dtoConverterRegistry,
								contextHttpServletRequest,
								assetCategory.getCategoryId(),
								contextAcceptLanguage.getPreferredLocale(),
								contextUriInfo, contextUser)),
					TaxonomyCategoryBrief.class);

				setParentWikiPageId(
					() -> {
						com.liferay.wiki.model.WikiPage parentWikiPage =
							wikiPage.getParentPage();

						if ((parentWikiPage == null) ||
							(parentWikiPage.getPageId() == 0L)) {

							return null;
						}

						return parentWikiPage.getPageId();
					});
			}
		};
	}

}