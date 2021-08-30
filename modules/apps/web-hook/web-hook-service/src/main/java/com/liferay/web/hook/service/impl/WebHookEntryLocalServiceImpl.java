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

package com.liferay.web.hook.service.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.web.hook.exception.DuplicateWebHookEntryException;
import com.liferay.web.hook.model.WebHookEntry;
import com.liferay.web.hook.service.base.WebHookEntryLocalServiceBaseImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eduardo García
 */
@Component(
	property = "model.class.name=com.liferay.web.hook.model.WebHookEntry",
	service = AopService.class
)
public class WebHookEntryLocalServiceImpl
	extends WebHookEntryLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public WebHookEntry addWebHookEntry(
			long userId, Map<Locale, String> nameMap, String destination,
			String url, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		long companyId = user.getCompanyId();

		validate(companyId, 0, destination, url);

		long webHookEntryId = counterLocalService.increment();

		WebHookEntry webHookEntry = webHookEntryPersistence.create(
			webHookEntryId);

		webHookEntry.setUuid(serviceContext.getUuid());
		webHookEntry.setCompanyId(companyId);
		webHookEntry.setUserId(user.getUserId());
		webHookEntry.setUserName(user.getFullName());
		webHookEntry.setNameMap(nameMap);
		webHookEntry.setDestination(destination);
		webHookEntry.setUrl(url);

		return webHookEntryPersistence.update(webHookEntry);
	}

	@Override
	public List<WebHookEntry> search(
			long companyId, String keywords, int start, int end, Sort sort)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, keywords, start, end, sort);

		return search(searchContext);
	}

	@Override
	public int searchCount(long companyId, String keywords)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, keywords, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		return searchCount(searchContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public WebHookEntry updateWebHookEntry(
			long webHookEntryId, Map<Locale, String> nameMap,
			String destination, String url, ServiceContext serviceContext)
		throws PortalException {

		validate(
			serviceContext.getCompanyId(), webHookEntryId, destination, url);

		WebHookEntry webHookEntry = webHookEntryPersistence.findByPrimaryKey(
			webHookEntryId);

		webHookEntry.setNameMap(nameMap);
		webHookEntry.setDestination(destination);
		webHookEntry.setUrl(url);

		return webHookEntryPersistence.update(webHookEntry);
	}

	protected SearchContext buildSearchContext(
		long companyId, String keywords, int start, int end, Sort sort) {

		SearchContext searchContext = new SearchContext();

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		searchContext.setAttributes(
			HashMapBuilder.<String, Serializable>put(
				Field.NAME, keywords
			).put(
				Field.URL, keywords
			).build());
		searchContext.setCompanyId(companyId);
		searchContext.setEnd(end);
		searchContext.setKeywords(keywords);

		if (sort != null) {
			searchContext.setSorts(sort);
		}

		searchContext.setStart(start);

		return searchContext;
	}

	protected List<WebHookEntry> getWebHookEntries(Hits hits)
		throws PortalException {

		List<Document> documents = hits.toList();

		List<WebHookEntry> webHookEntries = new ArrayList<>(documents.size());

		for (Document document : documents) {
			long webHookEntryId = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			WebHookEntry webHookEntry =
				webHookEntryPersistence.fetchByPrimaryKey(webHookEntryId);

			if (webHookEntry == null) {
				webHookEntries = null;

				Indexer<WebHookEntry> indexer = IndexerRegistryUtil.getIndexer(
					WebHookEntry.class);

				long companyId = GetterUtil.getLong(
					document.get(Field.COMPANY_ID));

				indexer.delete(companyId, document.getUID());
			}
			else {
				webHookEntries.add(webHookEntry);
			}
		}

		return webHookEntries;
	}

	protected List<WebHookEntry> search(SearchContext searchContext)
		throws PortalException {

		Indexer<WebHookEntry> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			WebHookEntry.class);

		for (int i = 0; i < 10; i++) {
			Hits hits = indexer.search(searchContext);

			List<WebHookEntry> webHookEntries = getWebHookEntries(hits);

			if (webHookEntries != null) {
				return webHookEntries;
			}
		}

		throw new SearchException(
			"Unable to fix the search index after 10 attempts");
	}

	protected int searchCount(SearchContext searchContext)
		throws PortalException {

		Indexer<WebHookEntry> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			WebHookEntry.class);

		return GetterUtil.getInteger(indexer.searchCount(searchContext));
	}

	protected void validate(
			long companyId, long webHookEntryId, String destination, String url)
		throws PortalException {

		WebHookEntry webHookEntry = webHookEntryPersistence.fetchByC_D_U(
			companyId, StringUtil.trim(destination), StringUtil.trim(url));

		if ((webHookEntry != null) &&
			(webHookEntry.getWebHookEntryId() != webHookEntryId)) {

			throw new DuplicateWebHookEntryException(
				StringBundler.concat(
					"Duplicate destination ", destination, " and url ", url));
		}
	}

}