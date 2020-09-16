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

package com.liferay.commerce.product.service.impl;

import com.liferay.commerce.product.constants.CommerceCatalogConstants;
import com.liferay.commerce.product.exception.CommerceCatalogProductsException;
import com.liferay.commerce.product.exception.CommerceCatalogSystemException;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.base.CommerceCatalogLocalServiceBaseImpl;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
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
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alec Sloan
 * @author Alessio Antonio Rendina
 */
public class CommerceCatalogLocalServiceImpl
	extends CommerceCatalogLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceCatalog addCommerceCatalog(
			String name, String commerceCurrencyCode,
			String catalogDefaultLanguageId, boolean system,
			String externalReferenceCode, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());

		if (Validator.isBlank(externalReferenceCode)) {
			externalReferenceCode = null;
		}

		long commerceCatalogId = counterLocalService.increment();

		CommerceCatalog commerceCatalog = commerceCatalogPersistence.create(
			commerceCatalogId);

		commerceCatalog.setCompanyId(user.getCompanyId());
		commerceCatalog.setUserId(user.getUserId());
		commerceCatalog.setUserName(user.getFullName());

		commerceCatalog.setName(name);
		commerceCatalog.setCommerceCurrencyCode(commerceCurrencyCode);
		commerceCatalog.setCatalogDefaultLanguageId(catalogDefaultLanguageId);
		commerceCatalog.setSystem(system);
		commerceCatalog.setExternalReferenceCode(externalReferenceCode);

		// Group

		groupLocalService.addGroup(
			user.getUserId(), GroupConstants.DEFAULT_PARENT_GROUP_ID,
			CommerceCatalog.class.getName(), commerceCatalogId,
			GroupConstants.DEFAULT_LIVE_GROUP_ID, getLocalizationMap(name),
			null, GroupConstants.TYPE_SITE_PRIVATE, false,
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION, null, false, true,
			null);

		commerceCatalog = commerceCatalogPersistence.update(commerceCatalog);

		// Resources

		resourceLocalService.addModelResources(commerceCatalog, serviceContext);

		return commerceCatalog;
	}

	@Override
	public CommerceCatalog addCommerceCatalog(
			String name, String commerceCurrencyCode,
			String catalogDefaultLanguageId, String externalReferenceCode,
			ServiceContext serviceContext)
		throws PortalException {

		return commerceCatalogLocalService.addCommerceCatalog(
			name, commerceCurrencyCode, catalogDefaultLanguageId, false,
			externalReferenceCode, serviceContext);
	}

	@Override
	public CommerceCatalog addDefaultCommerceCatalog(long companyId)
		throws PortalException {

		Company company = companyLocalService.getCompany(companyId);

		User defaultUser = company.getDefaultUser();

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(company.getCompanyId());
		serviceContext.setUserId(defaultUser.getUserId());
		serviceContext.setUuid(PortalUUIDUtil.generate());

		return commerceCatalogLocalService.addCommerceCatalog(
			CommerceCatalogConstants.MASTER_COMMERCE_CATALOG,
			CommerceCatalogConstants.MASTER_COMMERCE_DEFAULT_CURRENCY,
			defaultUser.getLanguageId(), true, null, serviceContext);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommerceCatalog deleteCommerceCatalog(
			CommerceCatalog commerceCatalog)
		throws PortalException {

		validate(commerceCatalog);

		long groupId = commerceCatalog.getGroupId();

		// Commerce catalog

		commerceCatalogPersistence.remove(commerceCatalog);

		// Group

		groupLocalService.deleteGroup(groupId);

		// Resources

		resourceLocalService.deleteResource(
			commerceCatalog, ResourceConstants.SCOPE_INDIVIDUAL);

		return commerceCatalog;
	}

	@Override
	public CommerceCatalog deleteCommerceCatalog(long commerceCatalogId)
		throws PortalException {

		CommerceCatalog commerceCatalog =
			commerceCatalogPersistence.findByPrimaryKey(commerceCatalogId);

		return commerceCatalogLocalService.deleteCommerceCatalog(
			commerceCatalog);
	}

	@Override
	public void deleteCommerceCatalogs(long companyId) throws PortalException {
		List<CommerceCatalog> commerceCatalogs =
			commerceCatalogPersistence.findByCompanyId(companyId);

		for (CommerceCatalog commerceCatalog : commerceCatalogs) {
			commerceCatalogPersistence.remove(commerceCatalog);
		}
	}

	@Override
	public CommerceCatalog fetchByExternalReferenceCode(
		long companyId, String externalReferenceCode) {

		if (Validator.isBlank(externalReferenceCode)) {
			return null;
		}

		return commerceCatalogPersistence.fetchByC_ERC(
			companyId, externalReferenceCode);
	}

	@Override
	public CommerceCatalog fetchCommerceCatalogByGroupId(long groupId) {
		Group group = groupLocalService.fetchGroup(groupId);

		if ((group != null) &&
			(group.getClassNameId() == classNameLocalService.getClassNameId(
				CommerceCatalog.class))) {

			return fetchCommerceCatalog(group.getClassPK());
		}

		return null;
	}

	@Override
	public Group getCommerceCatalogGroup(long commerceCatalogId)
		throws PortalException {

		CommerceCatalog commerceCatalog =
			commerceCatalogLocalService.getCommerceCatalog(commerceCatalogId);

		long classNameId = classNameLocalService.getClassNameId(
			CommerceCatalog.class.getName());

		Group group = groupLocalService.fetchGroup(
			commerceCatalog.getCompanyId(), classNameId, commerceCatalogId);

		if (group != null) {
			return group;
		}

		throw new PortalException();
	}

	@Override
	public List<CommerceCatalog> getCommerceCatalogs(
		long companyId, boolean system) {

		return commerceCatalogPersistence.findByC_S(companyId, system);
	}

	@Override
	public List<CommerceCatalog> searchCommerceCatalogs(long companyId)
		throws PortalException {

		return searchCommerceCatalogs(
			companyId, StringPool.BLANK, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	@Override
	public List<CommerceCatalog> searchCommerceCatalogs(
			long companyId, String keywords, int start, int end, Sort sort)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, start, end, sort);

		searchContext.setKeywords(keywords);

		return searchCommerceCatalogs(searchContext);
	}

	@Override
	public int searchCommerceCatalogsCount(long companyId, String keywords)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		searchContext.setKeywords(keywords);

		return searchCommerceCatalogsCount(searchContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceCatalog updateCommerceCatalog(
			long commerceCatalogId, String name, String commerceCurrencyCode,
			String catalogDefaultLanguageId)
		throws PortalException {

		CommerceCatalog commerceCatalog =
			commerceCatalogPersistence.findByPrimaryKey(commerceCatalogId);

		commerceCatalog.setName(name);
		commerceCatalog.setCommerceCurrencyCode(commerceCurrencyCode);
		commerceCatalog.setCatalogDefaultLanguageId(catalogDefaultLanguageId);

		return commerceCatalogPersistence.update(commerceCatalog);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceCatalog updateCommerceCatalogExternalReferenceCode(
			long commerceCatalogId, String externalReferenceCode)
		throws PortalException {

		CommerceCatalog commerceCatalog =
			commerceCatalogPersistence.findByPrimaryKey(commerceCatalogId);

		commerceCatalog.setExternalReferenceCode(externalReferenceCode);

		return commerceCatalogPersistence.update(commerceCatalog);
	}

	protected SearchContext buildSearchContext(
		long companyId, int start, int end, Sort sort) {

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(companyId);
		searchContext.setEnd(end);
		searchContext.setStart(start);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		if (sort != null) {
			searchContext.setSorts(sort);
		}

		return searchContext;
	}

	protected List<CommerceCatalog> getCommerceCatalogs(Hits hits)
		throws PortalException {

		List<Document> documents = hits.toList();

		List<CommerceCatalog> commerceCatalogs = new ArrayList<>(
			documents.size());

		for (Document document : documents) {
			long commerceCatalogId = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			CommerceCatalog commerceCatalog = fetchCommerceCatalog(
				commerceCatalogId);

			if (commerceCatalog == null) {
				commerceCatalogs = null;

				Indexer<CommerceCatalog> indexer =
					IndexerRegistryUtil.getIndexer(CommerceCatalog.class);

				long companyId = GetterUtil.getLong(
					document.get(Field.COMPANY_ID));

				indexer.delete(companyId, document.getUID());
			}
			else if (commerceCatalogs != null) {
				commerceCatalogs.add(commerceCatalog);
			}
		}

		return commerceCatalogs;
	}

	protected List<CommerceCatalog> searchCommerceCatalogs(
			SearchContext searchContext)
		throws PortalException {

		Indexer<CommerceCatalog> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(CommerceCatalog.class);

		for (int i = 0; i < 10; i++) {
			Hits hits = indexer.search(searchContext, _SELECTED_FIELD_NAMES);

			List<CommerceCatalog> commerceCatalogs = getCommerceCatalogs(hits);

			if (commerceCatalogs != null) {
				return commerceCatalogs;
			}
		}

		throw new SearchException(
			"Unable to fix the search index after 10 attempts");
	}

	protected int searchCommerceCatalogsCount(SearchContext searchContext)
		throws PortalException {

		Indexer<CommerceCatalog> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(CommerceCatalog.class);

		return GetterUtil.getInteger(indexer.searchCount(searchContext));
	}

	protected void validate(CommerceCatalog commerceCatalog)
		throws PortalException {

		if (commerceCatalog.isSystem()) {
			throw new CommerceCatalogSystemException();
		}

		int cpDefinitionsCount = cpDefinitionLocalService.getCPDefinitionsCount(
			commerceCatalog.getGroupId(), WorkflowConstants.STATUS_ANY);

		if (cpDefinitionsCount > 0) {
			throw new CommerceCatalogProductsException();
		}
	}

	private static final String[] _SELECTED_FIELD_NAMES = {
		Field.ENTRY_CLASS_PK, Field.COMPANY_ID
	};

}