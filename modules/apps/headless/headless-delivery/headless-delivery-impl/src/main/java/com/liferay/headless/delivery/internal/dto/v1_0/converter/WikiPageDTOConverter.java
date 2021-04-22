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
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.AssetLinkLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.headless.delivery.dto.v1_0.TaxonomyCategoryBrief;
import com.liferay.headless.delivery.dto.v1_0.WikiPage;
import com.liferay.headless.delivery.dto.v1_0.util.CreatorUtil;
import com.liferay.headless.delivery.dto.v1_0.util.CustomFieldsUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.util.AggregateRatingUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.util.RelatedContentUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.util.TaxonomyCategoryBriefUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.util.TransformUtil;
import com.liferay.ratings.kernel.service.RatingsStatsLocalService;
import com.liferay.subscription.service.SubscriptionLocalService;
import com.liferay.wiki.service.WikiPageService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

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

		com.liferay.wiki.model.WikiPage wikiPage = _wikiPageService.getPage(
			(Long)dtoConverterContext.getId());

		Optional<UriInfo> uriInfoOptional =
			dtoConverterContext.getUriInfoOptional();

		return new WikiPage() {
			{
				actions = dtoConverterContext.getActions();
				aggregateRating = AggregateRatingUtil.toAggregateRating(
					_ratingsStatsLocalService.fetchStats(
						com.liferay.wiki.model.WikiPage.class.getName(),
						wikiPage.getResourcePrimKey()));
				content = wikiPage.getContent();
				creator = CreatorUtil.toCreator(
					_portal, dtoConverterContext.getUriInfoOptional(),
					_userLocalService.fetchUser(wikiPage.getUserId()));
				customFields = CustomFieldsUtil.toCustomFields(
					dtoConverterContext.isAcceptAllLanguages(),
					com.liferay.wiki.model.WikiPage.class.getName(),
					wikiPage.getPageId(), wikiPage.getCompanyId(),
					dtoConverterContext.getLocale());
				dateCreated = wikiPage.getCreateDate();
				dateModified = wikiPage.getModifiedDate();
				description = wikiPage.getSummary();
				encodingFormat = _getEncodingFormat(wikiPage);
				externalReferenceCode = wikiPage.getExternalReferenceCode();
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
					dtoConverterContext.getLocale());
				siteId = wikiPage.getGroupId();
				subscribed = _subscriptionLocalService.isSubscribed(
					wikiPage.getCompanyId(), dtoConverterContext.getUserId(),
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
								dtoConverterContext.isAcceptAllLanguages(),
								Collections.emptyMap(), _dtoConverterRegistry,
								dtoConverterContext.getHttpServletRequest(),
								assetCategory.getCategoryId(),
								dtoConverterContext.getLocale(),
								uriInfoOptional.orElse(null),
								dtoConverterContext.getUser())),
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
				wikiNodeId = wikiPage.getNodeId();
			}
		};
	}

	private String _getEncodingFormat(
		com.liferay.wiki.model.WikiPage wikiPage) {

		String format = wikiPage.getFormat();

		if (format.equals("creole")) {
			return "text/x-wiki";
		}
		else if (format.equals("html")) {
			return "text/html";
		}
		else if (format.equals("plain_text")) {
			return "text/plain";
		}

		return format;
	}

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private AssetLinkLocalService _assetLinkLocalService;

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private Portal _portal;

	@Reference
	private RatingsStatsLocalService _ratingsStatsLocalService;

	@Reference
	private SubscriptionLocalService _subscriptionLocalService;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private WikiPageService _wikiPageService;

}