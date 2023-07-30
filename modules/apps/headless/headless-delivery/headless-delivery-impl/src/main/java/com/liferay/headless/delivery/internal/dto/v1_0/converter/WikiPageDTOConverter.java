/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.internal.dto.v1_0.converter;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.asset.link.service.AssetLinkLocalService;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.headless.delivery.dto.v1_0.TaxonomyCategoryBrief;
import com.liferay.headless.delivery.dto.v1_0.WikiPage;
import com.liferay.headless.delivery.dto.v1_0.util.CreatorUtil;
import com.liferay.headless.delivery.dto.v1_0.util.CustomFieldsUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.util.AggregateRatingUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.util.RelatedContentUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.util.TaxonomyCategoryBriefUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.ratings.kernel.service.RatingsStatsLocalService;
import com.liferay.subscription.service.SubscriptionLocalService;
import com.liferay.wiki.service.WikiPageService;

import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luis Miguel Barcos
 */
@Component(
	property = "dto.class.name=com.liferay.wiki.model.WikiPage",
	service = DTOConverter.class
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

		return new WikiPage() {
			{
				actions = dtoConverterContext.getActions();
				aggregateRating = AggregateRatingUtil.toAggregateRating(
					_ratingsStatsLocalService.fetchStats(
						com.liferay.wiki.model.WikiPage.class.getName(),
						wikiPage.getResourcePrimKey()));
				content = wikiPage.getContent();
				creator = CreatorUtil.toCreator(
					_portal, dtoConverterContext.getUriInfo(),
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
				id = wikiPage.getResourcePrimKey();
				keywords = ListUtil.toArray(
					_assetTagLocalService.getTags(
						BlogsEntry.class.getName(), wikiPage.getPageId()),
					AssetTag.NAME_ACCESSOR);
				numberOfAttachments = wikiPage.getAttachmentsFileEntriesCount();
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
								dtoConverterContext.getUriInfo(),
								dtoConverterContext.getUser())),
					TaxonomyCategoryBrief.class);
				wikiNodeId = wikiPage.getNodeId();

				setNumberOfWikiPages(
					() -> {
						List<com.liferay.wiki.model.WikiPage> wikiPages =
							wikiPage.getChildPages();

						if (wikiPages != null) {
							return wikiPages.size();
						}

						return 0;
					});
				setParentWikiPageId(
					() -> {
						com.liferay.wiki.model.WikiPage parentWikiPage =
							wikiPage.getParentPage();

						if ((parentWikiPage == null) ||
							(parentWikiPage.getPageId() == 0L)) {

							return null;
						}

						return parentWikiPage.getResourcePrimKey();
					});
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