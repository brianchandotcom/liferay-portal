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

package com.liferay.custom.elements.service.impl;

import com.liferay.custom.elements.model.CustomElementsSource;
import com.liferay.custom.elements.service.base.CustomElementsSourceLocalServiceBaseImpl;
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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.custom.elements.model.CustomElementsSource",
	service = AopService.class
)
public class CustomElementsSourceLocalServiceImpl
	extends CustomElementsSourceLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CustomElementsSource addCustomElementsSource(
			long userId, String htmlElementName, String name, String urls,
			ServiceContext serviceContext)
		throws PortalException {

		long customElementsSourceId = counterLocalService.increment();

		CustomElementsSource customElementsSource =
			customElementsSourcePersistence.create(customElementsSourceId);

		customElementsSource.setUuid(serviceContext.getUuid());

		User user = userLocalService.getUser(userId);

		customElementsSource.setUserId(user.getUserId());
		customElementsSource.setUserName(user.getFullName());

		customElementsSource.setHTMLElementName(htmlElementName);
		customElementsSource.setName(name);
		customElementsSource.setURLs(urls);

		return customElementsSourcePersistence.update(customElementsSource);
	}

	@Override
	public List<CustomElementsSource> getCustomElementsSources(long companyId) {
		return customElementsSourcePersistence.findByCompanyId(companyId);
	}

	@Override
	public List<CustomElementsSource> search(
			long companyId, String keywords, int start, int end, Sort sort)
		throws PortalException {

		SearchContext searchContext = _buildSearchContext(
			companyId, keywords, start, end, sort);

		Indexer<CustomElementsSource> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(CustomElementsSource.class);

		for (int i = 0; i < 10; i++) {
			Hits hits = indexer.search(searchContext);

			List<CustomElementsSource> customElementsSources =
				_getCustomElementsSources(hits);

			if (customElementsSources != null) {
				return customElementsSources;
			}
		}

		throw new SearchException(
			"Unable to fix the search index after 10 attempts");
	}

	@Override
	public int searchCount(long companyId, String keywords)
		throws PortalException {

		Indexer<CustomElementsSource> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(CustomElementsSource.class);

		SearchContext searchContext = _buildSearchContext(
			companyId, keywords, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		return GetterUtil.getInteger(indexer.searchCount(searchContext));
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CustomElementsSource updateCustomElementsSource(
			long customElementsSourceId, String htmlElementName, String name,
			String urls, ServiceContext serviceContext)
		throws PortalException {

		CustomElementsSource customElementsSource =
			customElementsSourcePersistence.findByPrimaryKey(
				customElementsSourceId);

		customElementsSource.setHTMLElementName(htmlElementName);
		customElementsSource.setName(name);
		customElementsSource.setURLs(urls);

		return customElementsSourcePersistence.update(customElementsSource);
	}

	private SearchContext _buildSearchContext(
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

	private List<CustomElementsSource> _getCustomElementsSources(Hits hits)
		throws PortalException {

		List<Document> documents = hits.toList();

		List<CustomElementsSource> customElementsSources = new ArrayList<>(
			documents.size());

		for (Document document : documents) {
			long customElementsSourceId = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			CustomElementsSource customElementsSource =
				customElementsSourcePersistence.fetchByPrimaryKey(
					customElementsSourceId);

			if (customElementsSource == null) {
				customElementsSources = null;

				Indexer<CustomElementsSource> indexer =
					IndexerRegistryUtil.getIndexer(CustomElementsSource.class);

				long companyId = GetterUtil.getLong(
					document.get(Field.COMPANY_ID));

				indexer.delete(companyId, document.getUID());
			}
			else {
				customElementsSources.add(customElementsSource);
			}
		}

		return customElementsSources;
	}

}