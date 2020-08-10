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

import com.liferay.headless.common.spi.service.context.ServiceContextUtil;
import com.liferay.headless.delivery.dto.v1_0.WikiNode;
import com.liferay.headless.delivery.internal.dto.v1_0.util.CreatorUtil;
import com.liferay.headless.delivery.internal.odata.entity.v1_0.WikiNodeEntityModel;
import com.liferay.headless.delivery.resource.v1_0.WikiNodeResource;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.SearchUtil;
import com.liferay.subscription.service.SubscriptionLocalService;
import com.liferay.wiki.service.WikiNodeService;
import com.liferay.wiki.service.WikiPageService;

import java.util.Optional;

import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/wiki-node.properties",
	scope = ServiceScope.PROTOTYPE, service = WikiNodeResource.class
)
public class WikiNodeResourceImpl
	extends BaseWikiNodeResourceImpl implements EntityModelResource {

	@Override
	public void deleteWikiNode(Long wikiNodeId) throws Exception {
		_wikiNodeService.deleteNode(wikiNodeId);
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return _entityModel;
	}

	@Override
	public Page<WikiNode> getSiteWikiNodesPage(
			Long siteId, String search, Aggregation aggregation, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			HashMapBuilder.put(
				"create",
				addAction(
					"ADD_NODE", "postSiteWikiNode", "com.liferay.wiki", siteId)
			).build(),
			booleanQuery -> {
				BooleanFilter booleanFilter =
					booleanQuery.getPreBooleanFilter();

				booleanFilter.add(
					new TermFilter(Field.GROUP_ID, String.valueOf(siteId)),
					BooleanClauseOccur.MUST);
			},
			filter, com.liferay.wiki.model.WikiNode.class, search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.addVulcanAggregation(aggregation);
				searchContext.setCompanyId(contextCompany.getCompanyId());
			},
			sorts,
			document -> _toWikiNode(
				_wikiNodeService.getNode(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))));
	}

	@Override
	public WikiNode getWikiNode(Long wikiNodeId) throws Exception {
		return _toWikiNode(_wikiNodeService.getNode(wikiNodeId));
	}

	@Override
	public WikiNode postSiteWikiNode(Long siteId, WikiNode wikiNode)
		throws Exception {

		return _toWikiNode(
			_wikiNodeService.addNode(
				wikiNode.getName(), wikiNode.getDescription(),
				ServiceContextUtil.createServiceContext(siteId, null)));
	}

	@Override
	public WikiNode putWikiNode(Long wikiNodeId, WikiNode wikiNode)
		throws Exception {

		com.liferay.wiki.model.WikiNode serviceBuilderWikiNode =
			_wikiNodeService.getNode(wikiNodeId);

		return _toWikiNode(
			_wikiNodeService.updateNode(
				wikiNodeId, wikiNode.getName(), wikiNode.getDescription(),
				ServiceContextUtil.createServiceContext(
					serviceBuilderWikiNode.getGroupId(),
					wikiNode.getViewableByAsString())));
	}

	@Override
	public void putWikiNodeSubscribe(Long wikiNodeId) throws Exception {
		_wikiNodeService.subscribeNode(wikiNodeId);
	}

	@Override
	public void putWikiNodeUnsubscribe(Long wikiNodeId) throws Exception {
		_wikiNodeService.unsubscribeNode(wikiNodeId);
	}

	private WikiNode _toWikiNode(com.liferay.wiki.model.WikiNode wikiNode)
		throws Exception {

		return new WikiNode() {
			{
				actions = HashMapBuilder.put(
					"delete", addAction("DELETE", wikiNode, "deleteWikiNode")
				).put(
					"get", addAction("VIEW", wikiNode, "getWikiNode")
				).put(
					"replace", addAction("UPDATE", wikiNode, "putWikiNode")
				).put(
					"subscribe",
					addAction("SUBSCRIBE", wikiNode, "putWikiNodeSubscribe")
				).put(
					"unsubscribe",
					addAction("SUBSCRIBE", wikiNode, "putWikiNodeUnsubscribe")
				).build();
				creator = CreatorUtil.toCreator(
					_portal, Optional.of(contextUriInfo),
					_userLocalService.fetchUser(wikiNode.getUserId()));
				dateCreated = wikiNode.getCreateDate();
				dateModified = wikiNode.getModifiedDate();
				description = wikiNode.getDescription();
				id = wikiNode.getNodeId();
				name = wikiNode.getName();
				numberOfWikiPages = _wikiPageService.getPagesCount(
					wikiNode.getGroupId(), wikiNode.getNodeId(), true);
				siteId = wikiNode.getGroupId();
				subscribed = _subscriptionLocalService.isSubscribed(
					wikiNode.getCompanyId(), contextUser.getUserId(),
					com.liferay.wiki.model.WikiNode.class.getName(),
					wikiNode.getNodeId());
			}
		};
	}

	private static final EntityModel _entityModel = new WikiNodeEntityModel();

	@Reference
	private Portal _portal;

	@Reference
	private SubscriptionLocalService _subscriptionLocalService;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private WikiNodeService _wikiNodeService;

	@Reference
	private WikiPageService _wikiPageService;

}