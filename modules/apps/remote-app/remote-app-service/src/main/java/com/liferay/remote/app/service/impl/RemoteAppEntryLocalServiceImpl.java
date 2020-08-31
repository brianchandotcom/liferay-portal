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

package com.liferay.remote.app.service.impl;

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
import com.liferay.remote.app.exception.DuplicateRemoteAppEntryURLException;
import com.liferay.remote.app.model.RemoteAppEntry;
import com.liferay.remote.app.service.base.RemoteAppEntryLocalServiceBaseImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.remote.app.model.RemoteAppEntry",
	service = AopService.class
)
public class RemoteAppEntryLocalServiceImpl
	extends RemoteAppEntryLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public RemoteAppEntry addRemoteAppEntry(
			long userId, Map<Locale, String> nameMap, String url,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		long companyId = user.getCompanyId();

		validate(companyId, 0, url);

		long remoteAppEntryId = counterLocalService.increment();

		RemoteAppEntry remoteAppEntry = remoteAppEntryPersistence.create(
			remoteAppEntryId);

		remoteAppEntry.setUuid(serviceContext.getUuid());
		remoteAppEntry.setCompanyId(companyId);
		remoteAppEntry.setUserId(user.getUserId());
		remoteAppEntry.setUserName(user.getFullName());
		remoteAppEntry.setNameMap(nameMap);
		remoteAppEntry.setUrl(url);

		return remoteAppEntryPersistence.update(remoteAppEntry);
	}

	@Override
	public List<RemoteAppEntry> searchRemoteAppEntries(
			long companyId, String keywords, int start, int end, Sort sort)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, keywords, start, end, sort);

		return searchRemoteAppEntries(searchContext);
	}

	@Override
	public int searchRemoteAppEntriesCount(long companyId, String keywords)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, keywords, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		return searchRemoteAppEntriesCount(searchContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public RemoteAppEntry updateRemoteAppEntry(
			long remoteAppEntryId, Map<Locale, String> nameMap, String url,
			ServiceContext serviceContext)
		throws PortalException {

		validate(serviceContext.getCompanyId(), remoteAppEntryId, url);

		RemoteAppEntry remoteAppEntry =
			remoteAppEntryPersistence.findByPrimaryKey(remoteAppEntryId);

		remoteAppEntry.setNameMap(nameMap);
		remoteAppEntry.setUrl(url);

		return remoteAppEntryPersistence.update(remoteAppEntry);
	}

	protected SearchContext buildSearchContext(
		long companyId, String keywords, int start, int end, Sort sort) {

		SearchContext searchContext = new SearchContext();

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

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		return searchContext;
	}

	protected List<RemoteAppEntry> getRemoteAppEntries(Hits hits)
		throws PortalException {

		List<Document> documents = hits.toList();

		List<RemoteAppEntry> remoteAppEntries = new ArrayList<>(
			documents.size());

		for (Document document : documents) {
			long remoteAppEntryId = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			RemoteAppEntry remoteAppEntry =
				remoteAppEntryPersistence.fetchByPrimaryKey(remoteAppEntryId);

			if (remoteAppEntry == null) {
				remoteAppEntries = null;

				Indexer<RemoteAppEntry> indexer =
					IndexerRegistryUtil.getIndexer(RemoteAppEntry.class);

				long companyId = GetterUtil.getLong(
					document.get(Field.COMPANY_ID));

				indexer.delete(companyId, document.getUID());
			}
			else {
				remoteAppEntries.add(remoteAppEntry);
			}
		}

		return remoteAppEntries;
	}

	protected List<RemoteAppEntry> searchRemoteAppEntries(
			SearchContext searchContext)
		throws PortalException {

		Indexer<RemoteAppEntry> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(RemoteAppEntry.class);

		for (int i = 0; i < 10; i++) {
			Hits hits = indexer.search(searchContext);

			List<RemoteAppEntry> remoteAppEntries = getRemoteAppEntries(hits);

			if (remoteAppEntries != null) {
				return remoteAppEntries;
			}
		}

		throw new SearchException(
			"Unable to fix the search index after 10 attempts");
	}

	protected int searchRemoteAppEntriesCount(SearchContext searchContext)
		throws PortalException {

		Indexer<RemoteAppEntry> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(RemoteAppEntry.class);

		return GetterUtil.getInteger(indexer.searchCount(searchContext));
	}

	protected void validate(long companyId, long remoteAppEntryId, String url)
		throws PortalException {

		RemoteAppEntry remoteAppEntry = remoteAppEntryPersistence.fetchByC_U(
			companyId, StringUtil.trim(url));

		if ((remoteAppEntry != null) &&
			(remoteAppEntry.getRemoteAppEntryId() != remoteAppEntryId)) {

			throw new DuplicateRemoteAppEntryURLException(
				"{remoteAppEntryId=" + remoteAppEntryId + "}");
		}
	}

}