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

import com.liferay.custom.elements.model.CustomElementsPortletDescriptor;
import com.liferay.custom.elements.model.CustomElementsSource;
import com.liferay.custom.elements.service.base.CustomElementsPortletDescriptorLocalServiceBaseImpl;
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
	property = "model.class.name=com.liferay.custom.elements.model.CustomElementsPortletDescriptor",
	service = AopService.class
)
public class CustomElementsPortletDescriptorLocalServiceImpl
	extends CustomElementsPortletDescriptorLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CustomElementsPortletDescriptor addCustomElementsPortletDescriptor(
			String cssURLs, String htmlElementName, boolean instanceable,
			String name, String properties, ServiceContext serviceContext)
		throws PortalException {

		long customElementsPortletDescriptorId =
			counterLocalService.increment();

		CustomElementsPortletDescriptor customElementsPortletDescriptor =
			customElementsPortletDescriptorPersistence.create(
				customElementsPortletDescriptorId);

		customElementsPortletDescriptor.setUuid(serviceContext.getUuid());

		User user = userLocalService.getUser(serviceContext.getUserId());

		customElementsPortletDescriptor.setUserId(user.getUserId());
		customElementsPortletDescriptor.setUserName(user.getFullName());

		_setCustomElementsPortletDescriptorFields(
			customElementsPortletDescriptor, cssURLs, htmlElementName,
			instanceable, name, properties);

		return customElementsPortletDescriptorPersistence.update(
			customElementsPortletDescriptor);
	}

	@Override
	public List<CustomElementsPortletDescriptor> search(
			long companyId, String keywords, int start, int end, Sort sort)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, keywords, start, end, sort);

		Indexer<CustomElementsPortletDescriptor> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(
				CustomElementsPortletDescriptor.class);

		for (int i = 0; i < 10; i++) {
			Hits hits = indexer.search(searchContext);

			List<CustomElementsPortletDescriptor>
				customElementsPortletDescriptors =
					getCustomElementsPortletDescriptors(hits);

			if (customElementsPortletDescriptors != null) {
				return customElementsPortletDescriptors;
			}
		}

		throw new SearchException(
			"Unable to fix the search index after 10 attempts");
	}

	@Override
	public int searchCount(long companyId, String keywords)
		throws SearchException {

		Indexer<CustomElementsSource> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(CustomElementsSource.class);

		SearchContext searchContext = buildSearchContext(
			companyId, keywords, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		return GetterUtil.getInteger(indexer.searchCount(searchContext));
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CustomElementsPortletDescriptor
			updateCustomElementsPortletDescriptor(
				long customElementsSourceId, String cssURLs,
				String htmlElementName, boolean instanceable, String name,
				String properties, ServiceContext serviceContext)
		throws PortalException {

		CustomElementsPortletDescriptor customElementsPortletDescriptor =
			customElementsPortletDescriptorPersistence.findByPrimaryKey(
				customElementsSourceId);

		_setCustomElementsPortletDescriptorFields(
			customElementsPortletDescriptor, cssURLs, htmlElementName,
			instanceable, name, properties);

		return customElementsPortletDescriptorPersistence.update(
			customElementsPortletDescriptor);
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

	protected List<CustomElementsPortletDescriptor>
			getCustomElementsPortletDescriptors(Hits hits)
		throws PortalException {

		List<Document> documents = hits.toList();

		List<CustomElementsPortletDescriptor> customElementsPortletDescriptors =
			new ArrayList<>(documents.size());

		for (Document document : documents) {
			long customElementsPortletDescriptorId = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			CustomElementsPortletDescriptor customElementsPortletDescriptor =
				customElementsPortletDescriptorPersistence.fetchByPrimaryKey(
					customElementsPortletDescriptorId);

			if (customElementsPortletDescriptor == null) {
				customElementsPortletDescriptors = null;

				Indexer<CustomElementsPortletDescriptor> indexer =
					IndexerRegistryUtil.getIndexer(
						CustomElementsPortletDescriptor.class);

				long companyId = GetterUtil.getLong(
					document.get(Field.COMPANY_ID));

				indexer.delete(companyId, document.getUID());
			}
			else {
				customElementsPortletDescriptors.add(
					customElementsPortletDescriptor);
			}
		}

		return customElementsPortletDescriptors;
	}

	private void _setCustomElementsPortletDescriptorFields(
		CustomElementsPortletDescriptor customElementsPortletDescriptor,
		String cssURLs, String htmlElementName, boolean instanceable,
		String name, String properties) {

		customElementsPortletDescriptor.setCSSURLs(cssURLs);
		customElementsPortletDescriptor.setHTMLElementName(htmlElementName);
		customElementsPortletDescriptor.setInstanceable(instanceable);
		customElementsPortletDescriptor.setName(name);
		customElementsPortletDescriptor.setProperties(properties);
	}

}