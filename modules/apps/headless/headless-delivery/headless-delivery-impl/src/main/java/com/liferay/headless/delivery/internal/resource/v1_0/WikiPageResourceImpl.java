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

package com.liferay.headless.delivery.internal.resource.v1_0;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.AssetLinkLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.headless.common.spi.service.context.ServiceContextUtil;
import com.liferay.headless.delivery.dto.v1_0.TaxonomyCategory;
import com.liferay.headless.delivery.dto.v1_0.WikiPage;
import com.liferay.headless.delivery.internal.dto.v1_0.util.AggregateRatingUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.util.CreatorUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.util.CustomFieldsUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.util.EntityFieldsUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.util.RelatedContentUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.util.TaxonomyCategoryUtil;
import com.liferay.headless.delivery.internal.odata.entity.v1_0.WikiPageEntityModel;
import com.liferay.headless.delivery.resource.v1_0.WikiPageResource;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.SearchUtil;
import com.liferay.portal.vulcan.util.TransformUtil;
import com.liferay.ratings.kernel.service.RatingsStatsLocalService;
import com.liferay.subscription.service.SubscriptionLocalService;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPageConstants;
import com.liferay.wiki.service.WikiNodeService;
import com.liferay.wiki.service.WikiPageLocalService;
import com.liferay.wiki.service.WikiPageService;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/wiki-page.properties",
	scope = ServiceScope.PROTOTYPE, service = WikiPageResource.class
)
public class WikiPageResourceImpl
	extends BaseWikiPageResourceImpl implements EntityModelResource {

	@Override
	public void deleteWikiPage(Long wikiPageId) throws Exception {
		com.liferay.wiki.model.WikiPage wikiPage =
			_wikiPageLocalService.getPageByPageId(wikiPageId);

		_wikiPageService.deletePage(wikiPage.getNodeId(), wikiPage.getTitle());
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return new WikiPageEntityModel(
			EntityFieldsUtil.getEntityFields(
				_portal.getClassNameId(
					com.liferay.wiki.model.WikiPage.class.getName()),
				contextCompany.getCompanyId(), _expandoColumnLocalService,
				_expandoTableLocalService));
	}

	@Override
	public Page<WikiPage> getWikiNodeWikiPagesPage(
			Long wikiNodeId, String search, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		WikiNode wikiNode = _wikiNodeService.getNode(wikiNodeId);

		return SearchUtil.search(
			_getWikiNodeListActions(
				wikiNode.getPrimaryKey(), wikiNode.getGroupId()),
			booleanQuery -> {
				BooleanFilter booleanFilter =
					booleanQuery.getPreBooleanFilter();

				booleanFilter.add(
					new TermFilter(Field.NODE_ID, String.valueOf(wikiNodeId)),
					BooleanClauseOccur.MUST);
			},
			filter, com.liferay.wiki.model.WikiPage.class, search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> searchContext.setCompanyId(
				contextCompany.getCompanyId()),
			sorts,
			document -> _toWikiPage(
				_wikiPageService.getPage(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))));
	}

	@Override
	public WikiPage getWikiPage(Long wikiPageId) throws Exception {
		com.liferay.wiki.model.WikiPage wikiPage =
			_wikiPageLocalService.getPageByPageId(wikiPageId);

		_wikiPageModelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(), wikiPage,
			ActionKeys.VIEW);

		return _toWikiPage(wikiPage);
	}

	@Override
	public Page<WikiPage> getWikiPageWikiPagesPage(Long parentWikiPageId)
		throws Exception {

		com.liferay.wiki.model.WikiPage wikiPage =
			_wikiPageLocalService.getPageByPageId(parentWikiPageId);

		_wikiPageModelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(), wikiPage,
			ActionKeys.VIEW);

		return Page.of(
			(Map)_getWikiPageListActions(wikiPage),
			transform(
				_wikiPageService.getChildren(
					wikiPage.getGroupId(), wikiPage.getNodeId(), true,
					wikiPage.getTitle()),
				this::_toWikiPage));
	}

	@Override
	public WikiPage postWikiNodeWikiPage(Long wikiNodeId, WikiPage wikiPage)
		throws Exception {

		WikiNode wikiNode = _wikiNodeService.getNode(wikiNodeId);

		ServiceContext serviceContext = ServiceContextUtil.createServiceContext(
			wikiPage.getTaxonomyCategoryIds(), wikiPage.getKeywords(),
			_getExpandoBridgeAttributes(wikiPage), wikiNode.getGroupId(),
			wikiPage.getViewableByAsString());

		serviceContext.setCommand("add");

		return _toWikiPage(
			_wikiPageService.addPage(
				wikiNodeId, wikiPage.getHeadline(), wikiPage.getContent(),
				wikiPage.getHeadline(), true, wikiPage.getEncodingFormat(),
				null, null, serviceContext));
	}

	@Override
	public WikiPage postWikiPageWikiPage(
			Long parentWikiPageId, WikiPage wikiPage)
		throws Exception {

		com.liferay.wiki.model.WikiPage parentWikiPage =
			_wikiPageLocalService.getPageByPageId(parentWikiPageId);

		_wikiNodeModelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(),
			parentWikiPage.getNodeId(), ActionKeys.ADD_PAGE);

		ServiceContext serviceContext = ServiceContextUtil.createServiceContext(
			wikiPage.getTaxonomyCategoryIds(), wikiPage.getKeywords(),
			_getExpandoBridgeAttributes(wikiPage), parentWikiPage.getGroupId(),
			wikiPage.getViewableByAsString());

		serviceContext.setCommand("add");

		return _toWikiPage(
			_wikiPageLocalService.addPage(
				contextUser.getUserId(), parentWikiPage.getNodeId(),
				wikiPage.getHeadline(), WikiPageConstants.VERSION_DEFAULT,
				wikiPage.getContent(), wikiPage.getHeadline(), false,
				wikiPage.getEncodingFormat(), false, parentWikiPage.getTitle(),
				null, serviceContext));
	}

	@Override
	public WikiPage putWikiPage(Long wikiPageId, WikiPage wikiPage)
		throws Exception {

		com.liferay.wiki.model.WikiPage serviceBuilderWikiPage =
			_wikiPageLocalService.getPageByPageId(wikiPageId);

		_wikiPageModelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(),
			serviceBuilderWikiPage, ActionKeys.UPDATE);

		ServiceContext serviceContext = ServiceContextUtil.createServiceContext(
			wikiPage.getTaxonomyCategoryIds(), wikiPage.getKeywords(),
			_getExpandoBridgeAttributes(wikiPage),
			serviceBuilderWikiPage.getGroupId(),
			wikiPage.getViewableByAsString());

		serviceContext.setCommand("update");

		return _toWikiPage(
			_wikiPageService.updatePage(
				serviceBuilderWikiPage.getNodeId(), wikiPage.getHeadline(),
				serviceBuilderWikiPage.getVersion(), wikiPage.getContent(),
				wikiPage.getDescription(), true, wikiPage.getEncodingFormat(),
				serviceBuilderWikiPage.getParentTitle(),
				serviceBuilderWikiPage.getRedirectTitle(), serviceContext));
	}

	@Override
	public void putWikiPageSubscribe(Long wikiPageId) throws Exception {
		com.liferay.wiki.model.WikiPage wikiPage =
			_wikiPageLocalService.getPageByPageId(wikiPageId);

		_wikiPageService.subscribePage(
			wikiPage.getNodeId(), wikiPage.getTitle());
	}

	@Override
	public void putWikiPageUnsubscribe(Long wikiPageId) throws Exception {
		com.liferay.wiki.model.WikiPage wikiPage =
			_wikiPageLocalService.getPageByPageId(wikiPageId);

		_wikiPageService.unsubscribePage(
			wikiPage.getNodeId(), wikiPage.getTitle());
	}

	private Map<String, Map<String, String>> _getActions(
		com.liferay.wiki.model.WikiPage wikiPage) {

		return HashMapBuilder.<String, Map<String, String>>put(
			"add-page",
			addAction(
				"UPDATE", wikiPage.getResourcePrimKey(), "postWikiPageWikiPage",
				"com.liferay.wiki.model.WikiPage", wikiPage.getGroupId())
		).put(
			"delete",
			addAction(
				"DELETE", wikiPage.getResourcePrimKey(), "deleteWikiPage",
				"com.liferay.wiki.model.WikiPage", wikiPage.getGroupId())
		).put(
			"get",
			addAction(
				"VIEW", wikiPage.getResourcePrimKey(), "getWikiPage",
				"com.liferay.wiki.model.WikiPage", wikiPage.getGroupId())
		).put(
			"replace",
			addAction(
				"UPDATE", wikiPage.getResourcePrimKey(), "putWikiPage",
				"com.liferay.wiki.model.WikiPage", wikiPage.getGroupId())
		).put(
			"subscribe",
			addAction(
				"SUBSCRIBE", wikiPage.getResourcePrimKey(),
				"putWikiPageSubscribe", "com.liferay.wiki.model.WikiPage",
				wikiPage.getGroupId())
		).put(
			"unsubscribe",
			addAction(
				"SUBSCRIBE", wikiPage.getResourcePrimKey(),
				"putWikiPageUnsubscribe", "com.liferay.wiki.model.WikiPage",
				wikiPage.getGroupId())
		).build();
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

	private Map<String, Serializable> _getExpandoBridgeAttributes(
		WikiPage wikiPage) {

		return CustomFieldsUtil.toMap(
			com.liferay.wiki.model.WikiPage.class.getName(),
			contextCompany.getCompanyId(), wikiPage.getCustomFields(),
			contextAcceptLanguage.getPreferredLocale());
	}

	private Map<String, Map<String, String>> _getWikiNodeListActions(
		Long wikiNodeId, Long groupId) {

		return HashMapBuilder.<String, Map<String, String>>put(
			"add-page",
			addAction(
				"ADD_PAGE", wikiNodeId, "postWikiNodeWikiPage",
				"com.liferay.wiki.model.WikiNode", groupId)
		).put(
			"get",
			addAction(
				"VIEW", wikiNodeId, "getWikiNodeWikiPagesPage",
				"com.liferay.wiki.model.WikiNode", groupId)
		).build();
	}

	private Map<String, Map<String, String>> _getWikiPageListActions(
		com.liferay.wiki.model.WikiPage wikiPage) {

		return HashMapBuilder.<String, Map<String, String>>put(
			"add-page",
			addAction(
				"UPDATE", wikiPage.getResourcePrimKey(), "postWikiPageWikiPage",
				"com.liferay.wiki.model.WikiPage", wikiPage.getGroupId())
		).put(
			"get",
			addAction(
				"VIEW", wikiPage.getResourcePrimKey(),
				"getWikiPageWikiPagesPage", "com.liferay.wiki.model.WikiPage",
				wikiPage.getGroupId())
		).build();
	}

	private WikiPage _toWikiPage(com.liferay.wiki.model.WikiPage wikiPage)
		throws Exception {

		return new WikiPage() {
			{
				actions = (Map)_getActions(wikiPage);
				aggregateRating = AggregateRatingUtil.toAggregateRating(
					_ratingsStatsLocalService.fetchStats(
						com.liferay.wiki.model.WikiPage.class.getName(),
						wikiPage.getResourcePrimKey()));
				content = wikiPage.getContent();
				creator = CreatorUtil.toCreator(
					_portal, _userLocalService.getUser(wikiPage.getUserId()));
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
				taxonomyCategories = TransformUtil.transformToArray(
					_assetCategoryLocalService.getCategories(
						com.liferay.wiki.model.WikiPage.class.getName(),
						wikiPage.getPageId()),
					assetCategory -> TaxonomyCategoryUtil.toTaxonomyCategory(
						contextAcceptLanguage.isAcceptAllLanguages(),
						assetCategory,
						contextAcceptLanguage.getPreferredLocale()),
					TaxonomyCategory.class);

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
	private ExpandoColumnLocalService _expandoColumnLocalService;

	@Reference
	private ExpandoTableLocalService _expandoTableLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private RatingsStatsLocalService _ratingsStatsLocalService;

	@Reference
	private SubscriptionLocalService _subscriptionLocalService;

	@Reference
	private UserLocalService _userLocalService;

	@Reference(target = "(model.class.name=com.liferay.wiki.model.WikiNode)")
	private ModelResourcePermission<WikiNode> _wikiNodeModelResourcePermission;

	@Reference
	private WikiNodeService _wikiNodeService;

	@Reference
	private WikiPageLocalService _wikiPageLocalService;

	@Reference(target = "(model.class.name=com.liferay.wiki.model.WikiPage)")
	private ModelResourcePermission<com.liferay.wiki.model.WikiPage>
		_wikiPageModelResourcePermission;

	@Reference
	private WikiPageService _wikiPageService;

}