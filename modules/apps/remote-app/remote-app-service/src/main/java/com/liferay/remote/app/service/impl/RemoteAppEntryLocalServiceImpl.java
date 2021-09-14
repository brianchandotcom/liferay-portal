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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.remote.app.constants.RemoteAppConstants;
import com.liferay.remote.app.deployer.RemoteAppEntryDeployer;
import com.liferay.remote.app.exception.DuplicateRemoteAppEntryException;
import com.liferay.remote.app.exception.InvalidRemoteAppEntryTypeException;
import com.liferay.remote.app.model.RemoteAppEntry;
import com.liferay.remote.app.service.base.RemoteAppEntryLocalServiceBaseImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
	property = "model.class.name=com.liferay.remote.app.model.RemoteAppEntry",
	service = AopService.class
)
public class RemoteAppEntryLocalServiceImpl
	extends RemoteAppEntryLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public RemoteAppEntry addCustomElementRemoteAppEntry(
			long userId, Map<Locale, String> nameMap,
			String customElementCSSURLs, String customElementHTMLElementName,
			String customElementURLs, ServiceContext serviceContext)
		throws PortalException {

		customElementCSSURLs = StringUtil.trim(customElementCSSURLs);
		customElementHTMLElementName = StringUtil.trim(
			customElementHTMLElementName);
		customElementURLs = StringUtil.trim(customElementURLs);

		long remoteAppEntryId = counterLocalService.increment();

		RemoteAppEntry remoteAppEntry = remoteAppEntryPersistence.create(
			remoteAppEntryId);

		User user = userLocalService.getUser(userId);

		remoteAppEntry.setUuid(serviceContext.getUuid());
		remoteAppEntry.setCompanyId(user.getCompanyId());
		remoteAppEntry.setUserId(user.getUserId());
		remoteAppEntry.setUserName(user.getFullName());
		remoteAppEntry.setNameMap(nameMap);
		remoteAppEntry.setCustomElementCSSURLs(customElementCSSURLs);
		remoteAppEntry.setCustomElementHTMLElementName(
			customElementHTMLElementName);
		remoteAppEntry.setCustomElementURLs(customElementURLs);
		remoteAppEntry.setType(RemoteAppConstants.TYPE_CUSTOM_ELEMENT);

		_validateCustomElementRemoteAppEntry(remoteAppEntry);

		remoteAppEntry = remoteAppEntryPersistence.update(remoteAppEntry);

		remoteAppEntryLocalService.deployRemoteAppEntry(remoteAppEntry);

		return remoteAppEntry;
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public RemoteAppEntry addIframeRemoteAppEntry(
			long userId, Map<Locale, String> nameMap, String iframeURL,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);
		iframeURL = StringUtil.trim(iframeURL);

		long remoteAppEntryId = counterLocalService.increment();

		RemoteAppEntry remoteAppEntry = remoteAppEntryPersistence.create(
			remoteAppEntryId);

		remoteAppEntry.setUuid(serviceContext.getUuid());
		remoteAppEntry.setCompanyId(user.getCompanyId());
		remoteAppEntry.setUserId(user.getUserId());
		remoteAppEntry.setUserName(user.getFullName());
		remoteAppEntry.setNameMap(nameMap);
		remoteAppEntry.setIframeURL(iframeURL);
		remoteAppEntry.setType(RemoteAppConstants.TYPE_IFRAME);

		_validateIframeRemoteAppEntry(remoteAppEntry);

		remoteAppEntry = remoteAppEntryPersistence.update(remoteAppEntry);

		remoteAppEntryLocalService.deployRemoteAppEntry(remoteAppEntry);

		return remoteAppEntry;
	}

	@Override
	public RemoteAppEntry deleteRemoteAppEntry(long remoteAppEntryId)
		throws PortalException {

		RemoteAppEntry remoteAppEntry =
			remoteAppEntryPersistence.findByPrimaryKey(remoteAppEntryId);

		return deleteRemoteAppEntry(remoteAppEntry);
	}

	@Override
	public RemoteAppEntry deleteRemoteAppEntry(RemoteAppEntry remoteAppEntry)
		throws PortalException {

		remoteAppEntryPersistence.remove(remoteAppEntry);

		remoteAppEntryLocalService.undeployRemoteAppEntry(remoteAppEntry);

		return remoteAppEntry;
	}

	@Clusterable
	@Override
	public void deployRemoteAppEntry(RemoteAppEntry remoteAppEntry) {
		undeployRemoteAppEntry(remoteAppEntry);

		_serviceRegistrations.put(
			remoteAppEntry.getRemoteAppEntryId(),
			_remoteAppEntryDeployer.deploy(remoteAppEntry));
	}

	@Override
	public List<RemoteAppEntry> search(
			long companyId, String keywords, int start, int end, Sort sort)
		throws PortalException {

		SearchContext searchContext = _buildSearchContext(
			companyId, keywords, start, end, sort);

		Indexer<RemoteAppEntry> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(RemoteAppEntry.class);

		for (int i = 0; i < 10; i++) {
			Hits hits = indexer.search(searchContext);

			List<RemoteAppEntry> remoteAppEntries = _getRemoteAppEntries(hits);

			if (remoteAppEntries != null) {
				return remoteAppEntries;
			}
		}

		throw new SearchException(
			"Unable to fix the search index after 10 attempts");
	}

	@Override
	public int searchCount(long companyId, String keywords)
		throws PortalException {

		Indexer<RemoteAppEntry> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(RemoteAppEntry.class);

		SearchContext searchContext = _buildSearchContext(
			companyId, keywords, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		return GetterUtil.getInteger(indexer.searchCount(searchContext));
	}

	@Override
	public void setAopProxy(Object aopProxy) {
		super.setAopProxy(aopProxy);

		List<RemoteAppEntry> remoteAppEntries =
			remoteAppEntryLocalService.getRemoteAppEntries(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (RemoteAppEntry remoteAppEntry : remoteAppEntries) {
			deployRemoteAppEntry(remoteAppEntry);
		}
	}

	@Clusterable
	@Override
	public void undeployRemoteAppEntry(RemoteAppEntry remoteAppEntry) {
		ServiceRegistration<Portlet> serviceRegistration =
			_serviceRegistrations.remove(remoteAppEntry.getRemoteAppEntryId());

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public RemoteAppEntry updateCustomElementRemoteAppEntry(
			long remoteAppEntryId, Map<Locale, String> nameMap,
			String customElementCSSURLs, String customElementHTMLElementName,
			String customElementURLs)
		throws PortalException {

		customElementCSSURLs = StringUtil.trim(customElementCSSURLs);
		customElementHTMLElementName = StringUtil.trim(
			customElementHTMLElementName);
		customElementURLs = StringUtil.trim(customElementURLs);

		RemoteAppEntry remoteAppEntry =
			remoteAppEntryPersistence.findByPrimaryKey(remoteAppEntryId);

		remoteAppEntry.setNameMap(nameMap);
		remoteAppEntry.setCustomElementCSSURLs(customElementCSSURLs);
		remoteAppEntry.setCustomElementHTMLElementName(
			customElementHTMLElementName);
		remoteAppEntry.setCustomElementURLs(customElementURLs);

		_validateCustomElementRemoteAppEntry(remoteAppEntry);

		remoteAppEntry = remoteAppEntryPersistence.update(remoteAppEntry);

		remoteAppEntryLocalService.deployRemoteAppEntry(remoteAppEntry);

		return remoteAppEntry;
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public RemoteAppEntry updateIframeRemoteAppEntry(
			long remoteAppEntryId, Map<Locale, String> nameMap,
			String iframeURL)
		throws PortalException {

		iframeURL = StringUtil.trim(iframeURL);

		RemoteAppEntry remoteAppEntry =
			remoteAppEntryPersistence.findByPrimaryKey(remoteAppEntryId);

		remoteAppEntry.setNameMap(nameMap);
		remoteAppEntry.setIframeURL(iframeURL);

		_validateIframeRemoteAppEntry(remoteAppEntry);

		remoteAppEntry = remoteAppEntryPersistence.update(remoteAppEntry);

		remoteAppEntryLocalService.deployRemoteAppEntry(remoteAppEntry);

		return remoteAppEntry;
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

	private List<RemoteAppEntry> _getRemoteAppEntries(Hits hits)
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

	private void _validateCustomElementRemoteAppEntry(
			RemoteAppEntry remoteAppEntry)
		throws InvalidRemoteAppEntryTypeException {

		String type = remoteAppEntry.getType();

		if (!type.equals(RemoteAppConstants.TYPE_CUSTOM_ELEMENT)) {
			throw new InvalidRemoteAppEntryTypeException(type);
		}
	}

	private void _validateIframeRemoteAppEntry(RemoteAppEntry remoteAppEntry)
		throws PortalException {

		RemoteAppEntry remoteAppEntry2 = remoteAppEntryPersistence.fetchByC_IU(
			remoteAppEntry.getCompanyId(), remoteAppEntry.getIframeURL());

		if ((remoteAppEntry2 != null) &&
			(remoteAppEntry2.getRemoteAppEntryId() !=
				remoteAppEntry.getRemoteAppEntryId())) {

			throw new DuplicateRemoteAppEntryException(
				"Duplicate iframe URL " + remoteAppEntry.getIframeURL());
		}
	}

	private BundleContext _bundleContext;

	@Reference
	private RemoteAppEntryDeployer _remoteAppEntryDeployer;

	private final Map<Long, ServiceRegistration<Portlet>>
		_serviceRegistrations = new ConcurrentHashMap<>();

}