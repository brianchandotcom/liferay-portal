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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.cluster.Clusterable;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
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
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.remote.app.constants.RemoteAppConstants;
import com.liferay.remote.app.deployer.RemoteAppEntryDeployer;
import com.liferay.remote.app.exception.DuplicateRemoteAppEntryExternalReferenceCodeException;
import com.liferay.remote.app.exception.RemoteAppEntryCustomElementCSSURLsException;
import com.liferay.remote.app.exception.RemoteAppEntryCustomElementHTMLElementNameException;
import com.liferay.remote.app.exception.RemoteAppEntryCustomElementURLsException;
import com.liferay.remote.app.exception.RemoteAppEntryFriendlyURLMappingException;
import com.liferay.remote.app.exception.RemoteAppEntryIFrameURLException;
import com.liferay.remote.app.model.RemoteAppEntry;
import com.liferay.remote.app.service.base.RemoteAppEntryLocalServiceBaseImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
			String externalReferenceCode, long userId,
			String customElementCSSURLs, String customElementHTMLElementName,
			String customElementURLs, boolean customElementUseESM,
			String description, String friendlyURLMapping, boolean instanceable,
			Map<Locale, String> nameMap, String portletCategoryName,
			String properties, String sourceCodeURL)
		throws PortalException {

		long remoteAppEntryId = counterLocalService.increment();

		if (Validator.isBlank(externalReferenceCode)) {
			externalReferenceCode = String.valueOf(remoteAppEntryId);
		}

		User user = _userLocalService.getUser(userId);

		_validateExternalReferenceCode(
			user.getCompanyId(), externalReferenceCode);

		customElementCSSURLs = StringUtil.trim(customElementCSSURLs);
		customElementHTMLElementName = StringUtil.trim(
			customElementHTMLElementName);
		customElementURLs = StringUtil.trim(customElementURLs);

		_validateCustomElement(
			customElementCSSURLs, customElementHTMLElementName,
			customElementURLs);

		_validateFriendlyURLMapping(friendlyURLMapping);

		RemoteAppEntry remoteAppEntry = remoteAppEntryPersistence.create(
			remoteAppEntryId);

		remoteAppEntry.setExternalReferenceCode(externalReferenceCode);
		remoteAppEntry.setCompanyId(user.getCompanyId());
		remoteAppEntry.setUserId(user.getUserId());
		remoteAppEntry.setUserName(user.getFullName());
		remoteAppEntry.setCustomElementCSSURLs(customElementCSSURLs);
		remoteAppEntry.setCustomElementHTMLElementName(
			customElementHTMLElementName);
		remoteAppEntry.setCustomElementURLs(customElementURLs);
		remoteAppEntry.setCustomElementUseESM(customElementUseESM);
		remoteAppEntry.setDescription(description);
		remoteAppEntry.setFriendlyURLMapping(friendlyURLMapping);
		remoteAppEntry.setInstanceable(instanceable);
		remoteAppEntry.setNameMap(nameMap);
		remoteAppEntry.setPortletCategoryName(portletCategoryName);
		remoteAppEntry.setProperties(properties);
		remoteAppEntry.setSourceCodeURL(sourceCodeURL);
		remoteAppEntry.setType(RemoteAppConstants.TYPE_CUSTOM_ELEMENT);
		remoteAppEntry.setStatus(WorkflowConstants.STATUS_DRAFT);
		remoteAppEntry.setStatusByUserId(userId);
		remoteAppEntry.setStatusDate(new Date());

		remoteAppEntry = remoteAppEntryPersistence.update(remoteAppEntry);

		_addResources(remoteAppEntry);

		return _startWorkflowInstance(userId, remoteAppEntry);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public RemoteAppEntry addIFrameRemoteAppEntry(
			long userId, String description, String friendlyURLMapping,
			String iFrameURL, boolean instanceable, Map<Locale, String> nameMap,
			String portletCategoryName, String properties, String sourceCodeURL)
		throws PortalException {

		_validateFriendlyURLMapping(friendlyURLMapping);

		iFrameURL = StringUtil.trim(iFrameURL);

		_validateIFrameURL(iFrameURL);

		RemoteAppEntry remoteAppEntry = remoteAppEntryPersistence.create(
			counterLocalService.increment());

		User user = _userLocalService.getUser(userId);

		remoteAppEntry.setCompanyId(user.getCompanyId());
		remoteAppEntry.setUserId(user.getUserId());
		remoteAppEntry.setUserName(user.getFullName());

		remoteAppEntry.setDescription(description);
		remoteAppEntry.setFriendlyURLMapping(friendlyURLMapping);
		remoteAppEntry.setIFrameURL(iFrameURL);
		remoteAppEntry.setInstanceable(instanceable);
		remoteAppEntry.setNameMap(nameMap);
		remoteAppEntry.setPortletCategoryName(portletCategoryName);
		remoteAppEntry.setProperties(properties);
		remoteAppEntry.setSourceCodeURL(sourceCodeURL);
		remoteAppEntry.setType(RemoteAppConstants.TYPE_IFRAME);
		remoteAppEntry.setStatus(WorkflowConstants.STATUS_DRAFT);
		remoteAppEntry.setStatusByUserId(userId);
		remoteAppEntry.setStatusDate(new Date());

		remoteAppEntry = remoteAppEntryPersistence.update(remoteAppEntry);

		_addResources(remoteAppEntry);

		return _startWorkflowInstance(userId, remoteAppEntry);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public RemoteAppEntry addOrUpdateCustomElementRemoteAppEntry(
			String externalReferenceCode, long userId,
			String customElementCSSURLs, String customElementHTMLElementName,
			String customElementURLs, boolean customElementUseESM,
			String description, String friendlyURLMapping, boolean instanceable,
			Map<Locale, String> nameMap, String portletCategoryName,
			String properties, String sourceCodeURL)
		throws PortalException {

		User user = _userLocalService.getUser(userId);

		RemoteAppEntry remoteAppEntry =
			remoteAppEntryLocalService.
				fetchRemoteAppEntryByExternalReferenceCode(
					user.getCompanyId(), externalReferenceCode);

		if (remoteAppEntry != null) {
			return remoteAppEntryLocalService.updateCustomElementRemoteAppEntry(
				userId, remoteAppEntry.getRemoteAppEntryId(),
				customElementCSSURLs, customElementHTMLElementName,
				customElementURLs, customElementUseESM, description,
				friendlyURLMapping, nameMap, portletCategoryName, properties,
				sourceCodeURL);
		}

		return addCustomElementRemoteAppEntry(
			externalReferenceCode, userId, customElementCSSURLs,
			customElementHTMLElementName, customElementURLs,
			customElementUseESM, description, friendlyURLMapping, instanceable,
			nameMap, portletCategoryName, properties, sourceCodeURL);
	}

	@Override
	public RemoteAppEntry deleteRemoteAppEntry(long remoteAppEntryId)
		throws PortalException {

		RemoteAppEntry remoteAppEntry =
			remoteAppEntryPersistence.findByPrimaryKey(remoteAppEntryId);

		return deleteRemoteAppEntry(remoteAppEntry);
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public RemoteAppEntry deleteRemoteAppEntry(RemoteAppEntry remoteAppEntry)
		throws PortalException {

		remoteAppEntryPersistence.remove(remoteAppEntry);

		_resourceLocalService.deleteResource(
			remoteAppEntry.getCompanyId(), RemoteAppEntry.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			remoteAppEntry.getRemoteAppEntryId());

		remoteAppEntryLocalService.undeployRemoteAppEntry(remoteAppEntry);

		return remoteAppEntry;
	}

	@Clusterable
	@Override
	public void deployRemoteAppEntry(RemoteAppEntry remoteAppEntry) {
		undeployRemoteAppEntry(remoteAppEntry);

		_serviceRegistrationsMaps.put(
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
		List<ServiceRegistration<?>> serviceRegistrations =
			_serviceRegistrationsMaps.remove(
				remoteAppEntry.getRemoteAppEntryId());

		if (serviceRegistrations != null) {
			for (ServiceRegistration<?> serviceRegistration :
					serviceRegistrations) {

				serviceRegistration.unregister();
			}
		}
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public RemoteAppEntry updateCustomElementRemoteAppEntry(
			long userId, long remoteAppEntryId, String customElementCSSURLs,
			String customElementHTMLElementName, String customElementURLs,
			boolean customElementUseESM, String description,
			String friendlyURLMapping, Map<Locale, String> nameMap,
			String portletCategoryName, String properties, String sourceCodeURL)
		throws PortalException {

		customElementCSSURLs = StringUtil.trim(customElementCSSURLs);
		customElementHTMLElementName = StringUtil.trim(
			customElementHTMLElementName);
		customElementURLs = StringUtil.trim(customElementURLs);

		_validateCustomElement(
			customElementCSSURLs, customElementHTMLElementName,
			customElementURLs);

		_validateFriendlyURLMapping(friendlyURLMapping);

		RemoteAppEntry remoteAppEntry =
			remoteAppEntryPersistence.findByPrimaryKey(remoteAppEntryId);

		remoteAppEntryLocalService.undeployRemoteAppEntry(remoteAppEntry);

		remoteAppEntry.setCustomElementCSSURLs(customElementCSSURLs);
		remoteAppEntry.setCustomElementHTMLElementName(
			customElementHTMLElementName);
		remoteAppEntry.setCustomElementURLs(customElementURLs);
		remoteAppEntry.setCustomElementUseESM(customElementUseESM);
		remoteAppEntry.setDescription(description);
		remoteAppEntry.setFriendlyURLMapping(friendlyURLMapping);
		remoteAppEntry.setNameMap(nameMap);
		remoteAppEntry.setPortletCategoryName(portletCategoryName);
		remoteAppEntry.setProperties(properties);
		remoteAppEntry.setSourceCodeURL(sourceCodeURL);
		remoteAppEntry.setStatus(WorkflowConstants.STATUS_DRAFT);
		remoteAppEntry.setStatusByUserId(userId);
		remoteAppEntry.setStatusDate(new Date());

		remoteAppEntry = remoteAppEntryPersistence.update(remoteAppEntry);

		return _startWorkflowInstance(userId, remoteAppEntry);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public RemoteAppEntry updateIFrameRemoteAppEntry(
			long userId, long remoteAppEntryId, String description,
			String friendlyURLMapping, String iFrameURL,
			Map<Locale, String> nameMap, String portletCategoryName,
			String properties, String sourceCodeURL)
		throws PortalException {

		_validateFriendlyURLMapping(friendlyURLMapping);

		iFrameURL = StringUtil.trim(iFrameURL);

		_validateIFrameURL(iFrameURL);

		RemoteAppEntry remoteAppEntry =
			remoteAppEntryPersistence.findByPrimaryKey(remoteAppEntryId);

		remoteAppEntryLocalService.undeployRemoteAppEntry(remoteAppEntry);

		remoteAppEntry.setDescription(description);
		remoteAppEntry.setFriendlyURLMapping(friendlyURLMapping);
		remoteAppEntry.setIFrameURL(iFrameURL);
		remoteAppEntry.setNameMap(nameMap);
		remoteAppEntry.setPortletCategoryName(portletCategoryName);
		remoteAppEntry.setProperties(properties);
		remoteAppEntry.setSourceCodeURL(sourceCodeURL);
		remoteAppEntry.setStatus(WorkflowConstants.STATUS_DRAFT);
		remoteAppEntry.setStatusByUserId(userId);
		remoteAppEntry.setStatusDate(new Date());

		remoteAppEntry = remoteAppEntryPersistence.update(remoteAppEntry);

		return _startWorkflowInstance(userId, remoteAppEntry);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public RemoteAppEntry updateStatus(
			long userId, long remoteAppEntryId, int status)
		throws PortalException {

		RemoteAppEntry remoteAppEntry =
			remoteAppEntryPersistence.findByPrimaryKey(remoteAppEntryId);

		if (status == remoteAppEntry.getStatus()) {
			return remoteAppEntry;
		}

		if (status == WorkflowConstants.STATUS_APPROVED) {
			remoteAppEntryLocalService.deployRemoteAppEntry(remoteAppEntry);
		}
		else if (remoteAppEntry.getStatus() ==
					WorkflowConstants.STATUS_APPROVED) {

			remoteAppEntryLocalService.undeployRemoteAppEntry(remoteAppEntry);
		}

		User user = _userLocalService.getUser(userId);

		remoteAppEntry.setStatus(status);
		remoteAppEntry.setStatusByUserId(user.getUserId());
		remoteAppEntry.setStatusByUserName(user.getFullName());
		remoteAppEntry.setStatusDate(new Date());

		return remoteAppEntryPersistence.update(remoteAppEntry);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	private void _addResources(RemoteAppEntry remoteAppEntry)
		throws PortalException {

		_resourceLocalService.addResources(
			remoteAppEntry.getCompanyId(), 0, remoteAppEntry.getUserId(),
			RemoteAppEntry.class.getName(),
			remoteAppEntry.getRemoteAppEntryId(), false, true, true);
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

	private RemoteAppEntry _startWorkflowInstance(
			long userId, RemoteAppEntry remoteAppEntry)
		throws PortalException {

		Company company = _companyLocalService.getCompany(
			remoteAppEntry.getCompanyId());

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		return WorkflowHandlerRegistryUtil.startWorkflowInstance(
			remoteAppEntry.getCompanyId(), company.getGroupId(), userId,
			RemoteAppEntry.class.getName(),
			remoteAppEntry.getRemoteAppEntryId(), remoteAppEntry,
			serviceContext,
			Collections.singletonMap(
				WorkflowConstants.CONTEXT_URL,
				Optional.ofNullable(
					remoteAppEntry.getCustomElementURLs()
				).orElse(
					remoteAppEntry.getIFrameURL()
				)));
	}

	private void _validateCustomElement(
			String customElementCSSURLs, String customElementHTMLElementName,
			String customElementURLs)
		throws PortalException {

		if (Validator.isNotNull(customElementCSSURLs)) {
			for (String customElementCSSURL :
					customElementCSSURLs.split(StringPool.NEW_LINE)) {

				if (!Validator.isUrl(customElementCSSURL, true)) {
					throw new RemoteAppEntryCustomElementCSSURLsException(
						"Invalid custom element CSS URL " +
							customElementCSSURL);
				}
			}
		}

		if (Validator.isNull(customElementHTMLElementName)) {
			throw new RemoteAppEntryCustomElementHTMLElementNameException(
				"Custom element HTML element name is null");
		}

		char[] customElementHTMLElementNameCharArray =
			customElementHTMLElementName.toCharArray();

		if (!Validator.isChar(customElementHTMLElementNameCharArray[0]) ||
			!Character.isLowerCase(customElementHTMLElementNameCharArray[0])) {

			throw new RemoteAppEntryCustomElementHTMLElementNameException(
				"Custom element HTML element name must start with a " +
					"lowercase letter");
		}

		boolean containsDash = false;

		for (char c : customElementHTMLElementNameCharArray) {
			if (c == CharPool.DASH) {
				containsDash = true;
			}

			if ((Validator.isChar(c) && Character.isLowerCase(c)) ||
				Validator.isNumber(String.valueOf(c)) || (c == CharPool.DASH) ||
				(c == CharPool.PERIOD) || (c == CharPool.UNDERLINE)) {
			}
			else {
				throw new RemoteAppEntryCustomElementHTMLElementNameException(
					"Custom element HTML element name contains an invalid " +
						"character");
			}
		}

		if (!containsDash) {
			throw new RemoteAppEntryCustomElementHTMLElementNameException(
				"Custom element HTML element name must contain at least one " +
					"hyphen");
		}

		if (_reservedCustomElementHTMLElementNames.contains(
				customElementHTMLElementName)) {

			throw new RemoteAppEntryCustomElementHTMLElementNameException(
				"Reserved custom element HTML element name " +
					customElementHTMLElementName);
		}

		if (Validator.isNull(customElementURLs)) {
			throw new RemoteAppEntryCustomElementURLsException(
				"Invalid custom element URLs " + customElementURLs);
		}

		for (String customElementURL :
				customElementURLs.split(StringPool.NEW_LINE)) {

			if (!Validator.isUrl(customElementURL, true)) {
				throw new RemoteAppEntryCustomElementURLsException(
					"Invalid custom element URL " + customElementURL);
			}
		}
	}

	private void _validateExternalReferenceCode(
			long companyId, String externalReferenceCode)
		throws DuplicateRemoteAppEntryExternalReferenceCodeException {

		if (Validator.isNull(externalReferenceCode)) {
			return;
		}

		RemoteAppEntry remoteAppEntry =
			remoteAppEntryLocalService.
				fetchRemoteAppEntryByExternalReferenceCode(
					companyId, externalReferenceCode);

		if (remoteAppEntry != null) {
			throw new DuplicateRemoteAppEntryExternalReferenceCodeException();
		}
	}

	private void _validateFriendlyURLMapping(String friendlyURLMapping)
		throws PortalException {

		Matcher matcher = _friendlyURLMappingPattern.matcher(
			friendlyURLMapping);

		if (!matcher.matches()) {
			throw new RemoteAppEntryFriendlyURLMappingException(
				"Invalid friendly URL mapping " + friendlyURLMapping);
		}
	}

	private void _validateIFrameURL(String iFrameURL) throws PortalException {
		if (!Validator.isUrl(iFrameURL)) {
			throw new RemoteAppEntryIFrameURLException(
				"Invalid IFrame URL " + iFrameURL);
		}
	}

	private static final Pattern _friendlyURLMappingPattern = Pattern.compile(
		"[A-Za-z0-9-_]*");

	private BundleContext _bundleContext;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private RemoteAppEntryDeployer _remoteAppEntryDeployer;

	private final Set<String> _reservedCustomElementHTMLElementNames =
		SetUtil.fromArray(
			"annotation-xml", "color-profile", "font-face", "font-face-format",
			"font-face-name", "font-face-src", "font-face-uri",
			"missing-glyph");

	@Reference
	private ResourceLocalService _resourceLocalService;

	private final Map<Long, List<ServiceRegistration<?>>>
		_serviceRegistrationsMaps = new ConcurrentHashMap<>();

	@Reference
	private UserLocalService _userLocalService;

}