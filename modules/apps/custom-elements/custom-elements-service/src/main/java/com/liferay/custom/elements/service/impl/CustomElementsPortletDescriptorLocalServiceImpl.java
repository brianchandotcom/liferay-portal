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

import com.liferay.custom.elements.exception.NoSuchSourceException;
import com.liferay.custom.elements.internal.portlet.CustomElementsPortlet;
import com.liferay.custom.elements.model.CustomElementsPortletDescriptor;
import com.liferay.custom.elements.model.CustomElementsSource;
import com.liferay.custom.elements.service.CustomElementsSourceLocalService;
import com.liferay.custom.elements.service.base.CustomElementsPortletDescriptorLocalServiceBaseImpl;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.cluster.Clusterable;
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
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.Portlet;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

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

		customElementsPortletDescriptor.setCSSURLs(cssURLs);
		customElementsPortletDescriptor.setHTMLElementName(htmlElementName);
		customElementsPortletDescriptor.setInstanceable(instanceable);
		customElementsPortletDescriptor.setName(name);
		customElementsPortletDescriptor.setProperties(properties);

		customElementsPortletDescriptor =
			customElementsPortletDescriptorPersistence.update(
				customElementsPortletDescriptor);

		customElementsPortletDescriptorLocalService.
			deployCustomElementsPortletDescriptor(
				customElementsPortletDescriptor);

		return customElementsPortletDescriptor;
	}

	@Override
	public CustomElementsPortletDescriptor
			deleteCustomElementsPortletDescriptor(
				CustomElementsPortletDescriptor customElementsPortletDescriptor)
		throws PortalException {

		customElementsPortletDescriptorPersistence.remove(
			customElementsPortletDescriptor);

		customElementsPortletDescriptorLocalService.
			undeployCustomElementsPortletDescriptor(
				customElementsPortletDescriptor);

		return customElementsPortletDescriptor;
	}

	@Override
	public CustomElementsPortletDescriptor
			deleteCustomElementsPortletDescriptor(
				long customElementsPortletDescriptorId)
		throws PortalException {

		CustomElementsPortletDescriptor customElementsPortletDescriptor =
			customElementsPortletDescriptorPersistence.findByPrimaryKey(
				customElementsPortletDescriptorId);

		return deleteCustomElementsPortletDescriptor(
			customElementsPortletDescriptor);
	}

	@Clusterable
	@Override
	public void deployCustomElementsPortletDescriptor(
		CustomElementsPortletDescriptor customElementsPortletDescriptor) {

		undeployCustomElementsPortletDescriptor(
			customElementsPortletDescriptor);

		String cssURLs = customElementsPortletDescriptor.getCSSURLs();

		_serviceRegistrations.put(
			customElementsPortletDescriptor.
				getCustomElementsPortletDescriptorId(),
			_bundleContext.registerService(
				Portlet.class,
				new CustomElementsPortlet(customElementsPortletDescriptor),
				HashMapDictionaryBuilder.<String, Object>put(
					"com.liferay.portlet.company",
					customElementsPortletDescriptor.getCompanyId()
				).put(
					"com.liferay.portlet.display-category", "category.sample"
				).put(
					"com.liferay.portlet.header-portal-css",
					cssURLs.split(StringPool.NEW_LINE)
				).put(
					"com.liferay.portlet.header-portal-javascript",
					_getCustomElementsSourceURLs(
						customElementsPortletDescriptor)
				).put(
					"com.liferay.portlet.instanceable",
					customElementsPortletDescriptor.isInstanceable()
				).put(
					"javax.portlet.display-name",
					customElementsPortletDescriptor.getName()
				).put(
					"javax.portlet.name",
					StringBundler.concat(
						"com_liferay_custom_elements_web_internal_portlet_",
						"CustomElementsPortlet_",
						customElementsPortletDescriptor.
							getCustomElementsPortletDescriptorId())
				).put(
					"javax.portlet.security-role-ref", "power-user,user"
				).build()));
	}

	@Override
	public List<CustomElementsPortletDescriptor> search(
			long companyId, String keywords, int start, int end, Sort sort)
		throws PortalException {

		SearchContext searchContext = _buildSearchContext(
			companyId, keywords, start, end, sort);

		Indexer<CustomElementsPortletDescriptor> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(
				CustomElementsPortletDescriptor.class);

		for (int i = 0; i < 10; i++) {
			Hits hits = indexer.search(searchContext);

			List<CustomElementsPortletDescriptor>
				customElementsPortletDescriptors =
					_getCustomElementsPortletDescriptors(hits);

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

		SearchContext searchContext = _buildSearchContext(
			companyId, keywords, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		return GetterUtil.getInteger(indexer.searchCount(searchContext));
	}

	@Override
	public void setAopProxy(Object aopProxy) {
		super.setAopProxy(aopProxy);

		List<CustomElementsPortletDescriptor> customElementsPortletDescriptors =
			customElementsPortletDescriptorLocalService.
				getCustomElementsPortletDescriptors(
					QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (CustomElementsPortletDescriptor customElementsPortletDescriptor :
				customElementsPortletDescriptors) {

			deployCustomElementsPortletDescriptor(
				customElementsPortletDescriptor);
		}
	}

	@Clusterable
	@Override
	public void undeployCustomElementsPortletDescriptor(
		CustomElementsPortletDescriptor customElementsPortletDescriptor) {

		ServiceRegistration<Portlet> serviceRegistration =
			_serviceRegistrations.remove(
				customElementsPortletDescriptor.
					getCustomElementsPortletDescriptorId());

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
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

		customElementsPortletDescriptor.setCSSURLs(cssURLs);
		customElementsPortletDescriptor.setHTMLElementName(htmlElementName);
		customElementsPortletDescriptor.setInstanceable(instanceable);
		customElementsPortletDescriptor.setName(name);
		customElementsPortletDescriptor.setProperties(properties);

		customElementsPortletDescriptor =
			customElementsPortletDescriptorPersistence.update(
				customElementsPortletDescriptor);

		customElementsPortletDescriptorLocalService.
			deployCustomElementsPortletDescriptor(
				customElementsPortletDescriptor);

		return customElementsPortletDescriptor;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
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

	private List<CustomElementsPortletDescriptor>
			_getCustomElementsPortletDescriptors(Hits hits)
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

	private String[] _getCustomElementsSourceURLs(
		CustomElementsPortletDescriptor customElementsPortletDescriptor) {

		try {
			CustomElementsSource customElementsSource =
				_customElementsSourceLocalService.getCustomElementsSource(
					customElementsPortletDescriptor.getHTMLElementName());

			String urls = customElementsSource.getURLs();

			return urls.split(StringPool.NEW_LINE);
		}
		catch (NoSuchSourceException noSuchSourceException) {
			return StringPool.EMPTY_ARRAY;
		}
	}

	private BundleContext _bundleContext;

	@Reference
	private CustomElementsSourceLocalService _customElementsSourceLocalService;

	private final Map<Long, ServiceRegistration<Portlet>>
		_serviceRegistrations = new ConcurrentHashMap<>();

}