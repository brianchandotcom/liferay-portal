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
import com.liferay.remote.app.exception.DuplicateRemoteCustomElementEntryURLException;
import com.liferay.remote.app.model.RemoteCustomElementEntry;
import com.liferay.remote.app.service.base.RemoteCustomElementEntryLocalServiceBaseImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * The implementation of the remote custom element entry local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.liferay.remote.app.service.RemoteCustomElementEntryLocalService</code> interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RemoteCustomElementEntryLocalServiceBaseImpl
 */
@Component(
	property = "model.class.name=com.liferay.remote.app.model.RemoteCustomElementEntry",
	service = AopService.class
)
public class RemoteCustomElementEntryLocalServiceImpl
	extends RemoteCustomElementEntryLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public RemoteCustomElementEntry addRemoteCustomElementEntry(
			long userId, Map<Locale, String> nameMap, String tagName,
			String url, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		long companyId = user.getCompanyId();

		validate(companyId, 0, url);

		long remoteCustomElementEntryId = counterLocalService.increment();

		RemoteCustomElementEntry remoteCustomElementEntry =
			remoteCustomElementEntryPersistence.create(
				remoteCustomElementEntryId);

		remoteCustomElementEntry.setUuid(serviceContext.getUuid());
		remoteCustomElementEntry.setCompanyId(companyId);
		remoteCustomElementEntry.setUserId(user.getUserId());
		remoteCustomElementEntry.setUserName(user.getFullName());
		remoteCustomElementEntry.setNameMap(nameMap);
		remoteCustomElementEntry.setTagName(tagName);
		remoteCustomElementEntry.setUrl(url);

		return remoteCustomElementEntryPersistence.update(
			remoteCustomElementEntry);
	}

	@Override
	public List<RemoteCustomElementEntry> searchRemoteCustomElementEntries(
			long companyId, String keywords, int start, int end, Sort sort)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, keywords, start, end, sort);

		return searchRemoteCustomElementEntries(searchContext);
	}

	@Override
	public int searchRemoteCustomElementEntriesCount(
			long companyId, String keywords)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, keywords, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		return searchRemoteCustomElementEntriesCount(searchContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public RemoteCustomElementEntry updateRemoteCustomElementEntry(
			long remoteCustomElementEntryId, Map<Locale, String> nameMap,
			String tagName, String url, ServiceContext serviceContext)
		throws PortalException {

		validate(
			serviceContext.getCompanyId(), remoteCustomElementEntryId, url);

		RemoteCustomElementEntry remoteCustomElementEntry =
			remoteCustomElementEntryPersistence.findByPrimaryKey(
				remoteCustomElementEntryId);

		remoteCustomElementEntry.setNameMap(nameMap);
		remoteCustomElementEntry.setTagName(tagName);
		remoteCustomElementEntry.setUrl(url);

		return remoteCustomElementEntryPersistence.update(
			remoteCustomElementEntry);
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

	protected List<RemoteCustomElementEntry> getRemoteCustomElementEntries(
			Hits hits)
		throws PortalException {

		List<Document> documents = hits.toList();

		List<RemoteCustomElementEntry> remoteCustomElementEntries =
			new ArrayList<>(documents.size());

		for (Document document : documents) {
			long remoteCustomElementEntryId = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			RemoteCustomElementEntry remoteCustomElementEntry =
				remoteCustomElementEntryPersistence.fetchByPrimaryKey(
					remoteCustomElementEntryId);

			if (remoteCustomElementEntry == null) {
				remoteCustomElementEntries = null;

				Indexer<RemoteCustomElementEntry> indexer =
					IndexerRegistryUtil.getIndexer(
						RemoteCustomElementEntry.class);

				long companyId = GetterUtil.getLong(
					document.get(Field.COMPANY_ID));

				indexer.delete(companyId, document.getUID());
			}
			else {
				remoteCustomElementEntries.add(remoteCustomElementEntry);
			}
		}

		return remoteCustomElementEntries;
	}

	protected List<RemoteCustomElementEntry> searchRemoteCustomElementEntries(
			SearchContext searchContext)
		throws PortalException {

		Indexer<RemoteCustomElementEntry> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(
				RemoteCustomElementEntry.class);

		for (int i = 0; i < 10; i++) {
			Hits hits = indexer.search(searchContext);

			List<RemoteCustomElementEntry> remoteCustomElementEntries =
				getRemoteCustomElementEntries(hits);

			if (remoteCustomElementEntries != null) {
				return remoteCustomElementEntries;
			}
		}

		throw new SearchException(
			"Unable to fix the search index after 10 attempts");
	}

	protected int searchRemoteCustomElementEntriesCount(
			SearchContext searchContext)
		throws PortalException {

		Indexer<RemoteCustomElementEntry> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(
				RemoteCustomElementEntry.class);

		return GetterUtil.getInteger(indexer.searchCount(searchContext));
	}

	protected void validate(
			long companyId, long remoteCustomElementEntryId, String url)
		throws PortalException {

		RemoteCustomElementEntry remoteCustomElementEntry =
			remoteCustomElementEntryPersistence.fetchByC_U(
				companyId, StringUtil.trim(url));

		if ((remoteCustomElementEntry != null) &&
			(remoteCustomElementEntry.getRemoteCustomElementEntryId() !=
				remoteCustomElementEntryId)) {

			throw new DuplicateRemoteCustomElementEntryURLException(
				"{remoteCustomElementEntryId=" + remoteCustomElementEntryId +
					"}");
		}
	}

}