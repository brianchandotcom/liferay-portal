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

import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.exception.CPDefinitionOptionValueRelCPInstanceException;
import com.liferay.commerce.product.exception.CPDefinitionOptionValueRelKeyException;
import com.liferay.commerce.product.exception.CPDefinitionOptionValueRelPriceException;
import com.liferay.commerce.product.exception.CPDefinitionOptionValueRelQuantityException;
import com.liferay.commerce.product.exception.NoSuchCPDefinitionOptionValueRelException;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPInstanceOptionValueRel;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.model.CPOptionValue;
import com.liferay.commerce.product.service.base.CPDefinitionOptionValueRelLocalServiceBaseImpl;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
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
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.util.CustomAttributesUtil;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * @author Marco Leo
 * @author Igor Beslic
 */
public class CPDefinitionOptionValueRelLocalServiceImpl
	extends CPDefinitionOptionValueRelLocalServiceBaseImpl {

	@Override
	public CPDefinitionOptionValueRel addCPDefinitionOptionValueRel(
			long cpDefinitionOptionRelId, CPOptionValue cpOptionValue,
			ServiceContext serviceContext)
		throws PortalException {

		return cpDefinitionOptionValueRelLocalService.
			addCPDefinitionOptionValueRel(
				cpDefinitionOptionRelId, cpOptionValue.getNameMap(),
				cpOptionValue.getPriority(), cpOptionValue.getKey(),
				serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CPDefinitionOptionValueRel addCPDefinitionOptionValueRel(
			long cpDefinitionOptionRelId, Map<Locale, String> nameMap,
			double priority, String key, ServiceContext serviceContext)
		throws PortalException {

		// Commerce product definition option value rel

		User user = userLocalService.getUser(serviceContext.getUserId());

		key = FriendlyURLNormalizerUtil.normalize(key);

		validate(0, cpDefinitionOptionRelId, key);

		long cpDefinitionOptionValueRelId = counterLocalService.increment();

		CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
			cpDefinitionOptionValueRelPersistence.create(
				cpDefinitionOptionValueRelId);

		CPDefinitionOptionRel cpDefinitionOptionRel =
			cpDefinitionOptionRelLocalService.getCPDefinitionOptionRel(
				cpDefinitionOptionRelId);

		if (cpDefinitionLocalService.isVersionable(
				cpDefinitionOptionRel.getCPDefinitionId(),
				serviceContext.getRequest())) {

			CPDefinition newCPDefinition =
				cpDefinitionLocalService.copyCPDefinition(
					cpDefinitionOptionRel.getCPDefinitionId());

			cpDefinitionOptionRel = cpDefinitionOptionRelPersistence.findByC_C(
				newCPDefinition.getCPDefinitionId(),
				cpDefinitionOptionRel.getCPOptionId());

			cpDefinitionOptionRelId =
				cpDefinitionOptionRel.getCPDefinitionOptionRelId();
		}

		cpDefinitionOptionValueRel.setGroupId(
			cpDefinitionOptionRel.getGroupId());
		cpDefinitionOptionValueRel.setCompanyId(user.getCompanyId());
		cpDefinitionOptionValueRel.setUserId(user.getUserId());
		cpDefinitionOptionValueRel.setUserName(user.getFullName());
		cpDefinitionOptionValueRel.setCPDefinitionOptionRelId(
			cpDefinitionOptionRelId);
		cpDefinitionOptionValueRel.setNameMap(nameMap);
		cpDefinitionOptionValueRel.setPriority(priority);
		cpDefinitionOptionValueRel.setKey(key);
		cpDefinitionOptionValueRel.setExpandoBridgeAttributes(serviceContext);

		if (cpDefinitionOptionRel.isPriceTypeStatic()) {
			cpDefinitionOptionValueRel.setPrice(BigDecimal.ZERO);
		}

		_validateLinkedCPDefinitionOptionValueRel(cpDefinitionOptionValueRel);
		_validatePriceableCPDefinitionOptionValue(
			cpDefinitionOptionValueRel, cpDefinitionOptionRel.getPriceType());

		cpDefinitionOptionValueRel =
			cpDefinitionOptionValueRelPersistence.update(
				cpDefinitionOptionValueRel);

		// Commerce product definition

		reindexCPDefinition(cpDefinitionOptionRel);

		return cpDefinitionOptionValueRel;
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CPDefinitionOptionValueRel deleteCPDefinitionOptionValueRel(
			CPDefinitionOptionValueRel cpDefinitionOptionValueRel)
		throws PortalException {

		CPDefinitionOptionRel cpDefinitionOptionRel =
			cpDefinitionOptionValueRel.getCPDefinitionOptionRel();

		if (cpDefinitionLocalService.isVersionable(
				cpDefinitionOptionRel.getCPDefinitionId())) {

			CPDefinition newCPDefinition =
				cpDefinitionLocalService.copyCPDefinition(
					cpDefinitionOptionRel.getCPDefinitionId());

			cpDefinitionOptionRel = cpDefinitionOptionRelPersistence.findByC_C(
				newCPDefinition.getCPDefinitionId(),
				cpDefinitionOptionRel.getCPOptionId());

			cpDefinitionOptionValueRel =
				cpDefinitionOptionValueRelPersistence.findByC_K(
					cpDefinitionOptionRel.getCPDefinitionOptionRelId(),
					cpDefinitionOptionValueRel.getKey());
		}

		// Commerce product definition option value rel

		cpDefinitionOptionValueRelPersistence.remove(
			cpDefinitionOptionValueRel);

		// Expando

		expandoRowLocalService.deleteRows(
			cpDefinitionOptionValueRel.getCPDefinitionOptionValueRelId());

		cpInstanceLocalService.inactivateCPDefinitionOptionValueRelCPInstances(
			PrincipalThreadLocal.getUserId(),
			cpDefinitionOptionRel.getCPDefinitionId(),
			cpDefinitionOptionValueRel.getCPDefinitionOptionValueRelId());

		// Commerce product definition

		reindexCPDefinition(cpDefinitionOptionRel);

		return cpDefinitionOptionValueRel;
	}

	@Override
	public CPDefinitionOptionValueRel deleteCPDefinitionOptionValueRel(
			long cpDefinitionOptionValueRelId)
		throws PortalException {

		CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
			cpDefinitionOptionValueRelPersistence.findByPrimaryKey(
				cpDefinitionOptionValueRelId);

		return cpDefinitionOptionValueRelLocalService.
			deleteCPDefinitionOptionValueRel(cpDefinitionOptionValueRel);
	}

	@Override
	public void deleteCPDefinitionOptionValueRels(long cpDefinitionOptionRelId)
		throws PortalException {

		List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels =
			cpDefinitionOptionValueRelLocalService.
				getCPDefinitionOptionValueRels(
					cpDefinitionOptionRelId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS);

		for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel :
				cpDefinitionOptionValueRels) {

			cpDefinitionOptionValueRelLocalService.
				deleteCPDefinitionOptionValueRel(cpDefinitionOptionValueRel);
		}
	}

	@Override
	public CPDefinitionOptionValueRel fetchCPDefinitionOptionValueRel(
		long cpDefinitionOptionRelId, String key) {

		return cpDefinitionOptionValueRelPersistence.fetchByC_K(
			cpDefinitionOptionRelId, key);
	}

	@Override
	public CPDefinitionOptionValueRel
		fetchPreselectedCPDefinitionOptionValueRel(
			long cpDefinitionOptionRelId) {

		List<CPDefinitionOptionValueRel>
			preselectedCPDefinitionOptionValueRels =
				cpDefinitionOptionValueRelPersistence.findByCDORI_P(
					cpDefinitionOptionRelId, true);

		if (preselectedCPDefinitionOptionValueRels.isEmpty()) {
			return null;
		}

		return preselectedCPDefinitionOptionValueRels.get(0);
	}

	@Override
	public List<CPDefinitionOptionValueRel> filterByCPInstanceOptionValueRels(
		List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels,
		List<CPInstanceOptionValueRel> cpInstanceOptionValueRels) {

		List<CPDefinitionOptionValueRel> filteredCPDefinitionOptionValueRels =
			new ArrayList<>();

		for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel :
				cpDefinitionOptionValueRels) {

			for (CPInstanceOptionValueRel cpInstanceOptionValueRel :
					cpInstanceOptionValueRels) {

				long cpDefinitionOptionValueRelId1 =
					cpDefinitionOptionValueRel.
						getCPDefinitionOptionValueRelId();
				long cpDefinitionOptionValueRelId2 =
					cpInstanceOptionValueRel.getCPDefinitionOptionValueRelId();

				if (cpDefinitionOptionValueRelId1 ==
						cpDefinitionOptionValueRelId2) {

					filteredCPDefinitionOptionValueRels.add(
						cpDefinitionOptionValueRel);

					break;
				}
			}
		}

		return filteredCPDefinitionOptionValueRels;
	}

	@Override
	public List<CPDefinitionOptionValueRel> getCPDefinitionOptionValueRels(
		long cpDefinitionOptionRelId) {

		return cpDefinitionOptionValueRelPersistence.
			findByCPDefinitionOptionRelId(cpDefinitionOptionRelId);
	}

	@Override
	public List<CPDefinitionOptionValueRel> getCPDefinitionOptionValueRels(
		long cpDefinitionOptionRelId, int start, int end) {

		return cpDefinitionOptionValueRelPersistence.
			findByCPDefinitionOptionRelId(cpDefinitionOptionRelId, start, end);
	}

	@Override
	public List<CPDefinitionOptionValueRel> getCPDefinitionOptionValueRels(
		long cpDefinitionOptionRelId, int start, int end,
		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator) {

		return cpDefinitionOptionValueRelPersistence.
			findByCPDefinitionOptionRelId(
				cpDefinitionOptionRelId, start, end, orderByComparator);
	}

	@Override
	public List<CPDefinitionOptionValueRel> getCPDefinitionOptionValueRels(
			long[] cpDefinitionOptionValueRelsId)
		throws PortalException {

		if ((cpDefinitionOptionValueRelsId == null) ||
			(cpDefinitionOptionValueRelsId.length == 0)) {

			return Collections.emptyList();
		}

		DynamicQuery dynamicQuery = dynamicQuery();

		Property property = PropertyFactoryUtil.forName(
			"CPDefinitionOptionValueRelId");

		Criterion criterion = property.in(cpDefinitionOptionValueRelsId);

		dynamicQuery.add(criterion);

		dynamicQuery.addOrder(OrderFactoryUtil.asc("priority"));

		return cpDefinitionOptionValueRelPersistence.findWithDynamicQuery(
			dynamicQuery);
	}

	@Override
	public List<CPDefinitionOptionValueRel> getCPDefinitionOptionValueRels(
		String key, int start, int end) {

		return cpDefinitionOptionValueRelPersistence.findByKey(key, start, end);
	}

	@Override
	public int getCPDefinitionOptionValueRelsCount(
		long cpDefinitionOptionRelId) {

		return cpDefinitionOptionValueRelPersistence.
			countByCPDefinitionOptionRelId(cpDefinitionOptionRelId);
	}

	@Override
	public CPDefinitionOptionValueRel getCPInstanceCPDefinitionOptionValueRel(
			long cpDefinitionOptionRelId, long cpInstanceId)
		throws PortalException {

		List<CPInstanceOptionValueRel> cpInstanceCPInstanceOptionValueRels =
			cpInstanceOptionValueRelLocalService.
				getCPInstanceCPInstanceOptionValueRels(
					cpDefinitionOptionRelId, cpInstanceId);

		for (CPInstanceOptionValueRel cpInstanceCPInstanceOptionValueRel :
				cpInstanceCPInstanceOptionValueRels) {

			if (cpDefinitionOptionRelId !=
					cpInstanceCPInstanceOptionValueRel.
						getCPDefinitionOptionRelId()) {

				continue;
			}

			return cpDefinitionOptionValueRelPersistence.findByPrimaryKey(
				cpInstanceCPInstanceOptionValueRel.
					getCPDefinitionOptionValueRelId());
		}

		throw new NoSuchCPDefinitionOptionValueRelException(
			String.format(
				"Unable to find option value with CP definition option ID %d " +
					"assigned to CP instance ID %d",
				cpDefinitionOptionRelId, cpInstanceId));
	}

	@Override
	public boolean hasCPDefinitionOptionValueRels(
		long cpDefinitionOptionRelId) {

		int count =
			cpDefinitionOptionValueRelPersistence.
				countByCPDefinitionOptionRelId(cpDefinitionOptionRelId);

		if (count > 0) {
			return true;
		}

		return false;
	}

	@Override
	public boolean hasPreselectedCPDefinitionOptionValueRel(
		long cpDefinitionOptionRelId) {

		int count = cpDefinitionOptionValueRelPersistence.countByCDORI_P(
			cpDefinitionOptionRelId, true);

		if (count == 0) {
			return false;
		}

		return true;
	}

	@Override
	public void importCPDefinitionOptionRels(
			long cpDefinitionOptionRelId, ServiceContext serviceContext)
		throws PortalException {

		CPDefinitionOptionRel cpDefinitionOptionRel =
			cpDefinitionOptionRelLocalService.getCPDefinitionOptionRel(
				cpDefinitionOptionRelId);

		CPOption cpOption = cpOptionLocalService.fetchCPOption(
			cpDefinitionOptionRel.getCPOptionId());

		if (cpOption == null) {
			return;
		}

		List<CPOptionValue> cpOptionValues =
			cpOptionValueLocalService.getCPOptionValues(
				cpOption.getCPOptionId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Map<String, Serializable> expandoBridgeAttributes =
			serviceContext.getExpandoBridgeAttributes();

		try {
			_addCPDefinitionOptionValueRel(
				cpDefinitionOptionRelId, cpOptionValues, serviceContext);
		}
		finally {
			serviceContext.setExpandoBridgeAttributes(expandoBridgeAttributes);
		}
	}

	@Override
	public CPDefinitionOptionValueRel resetCPInstanceCPDefinitionOptionValueRel(
			long cpDefinitionOptionValueRelId)
		throws PortalException {

		CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
			cpDefinitionOptionValueRelLocalService.
				getCPDefinitionOptionValueRel(cpDefinitionOptionValueRelId);

		cpDefinitionOptionValueRel.setCPInstanceUuid(null);
		cpDefinitionOptionValueRel.setCProductId(0);
		cpDefinitionOptionValueRel.setQuantity(0);

		return cpDefinitionOptionValueRelLocalService.
			updateCPDefinitionOptionValueRel(cpDefinitionOptionValueRel);
	}

	@Override
	public void resetCPInstanceCPDefinitionOptionValueRels(
		String cpInstanceUuid) {

		List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels =
			cpDefinitionOptionValueRelPersistence.findByCPInstanceUuid(
				cpInstanceUuid);

		for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel :
				cpDefinitionOptionValueRels) {

			cpDefinitionOptionValueRel.setCPInstanceUuid(null);
			cpDefinitionOptionValueRel.setCProductId(0);
			cpDefinitionOptionValueRel.setQuantity(0);
			cpDefinitionOptionValueRel.setPrice(BigDecimal.ZERO);

			cpDefinitionOptionValueRelPersistence.update(
				cpDefinitionOptionValueRel);
		}
	}

	@Override
	public Hits search(SearchContext searchContext) {
		try {
			Indexer<CPDefinitionOptionValueRel> indexer =
				IndexerRegistryUtil.nullSafeGetIndexer(
					CPDefinitionOptionValueRel.class);

			return indexer.search(searchContext);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
	}

	@Override
	public BaseModelSearchResult<CPDefinitionOptionValueRel>
			searchCPDefinitionOptionValueRels(
				long companyId, long groupId, long cpDefinitionOptionRelId,
				String keywords, int start, int end, Sort sort)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, groupId, cpDefinitionOptionRelId, keywords, start, end,
			sort);

		return searchCPOptions(searchContext);
	}

	/**
	 * @param      cpDefinitionOptionValueRelId
	 * @param      nameMap
	 * @param      priority
	 * @param      key
	 * @param      cpInstanceId
	 * @param      quantity
	 * @param      price
	 * @param      serviceContext
	 * @return
	 *
	 * @throws     PortalException
	 * @deprecated As of Athanasius (7.3.x), use {@link
	 *             #updateCPDefinitionOptionValueRel(long, Map, double, String,
	 *             long, int, boolean, BigDecimal, ServiceContext)}
	 */
	@Deprecated
	@Override
	public CPDefinitionOptionValueRel updateCPDefinitionOptionValueRel(
			long cpDefinitionOptionValueRelId, Map<Locale, String> nameMap,
			double priority, String key, long cpInstanceId, int quantity,
			BigDecimal price, ServiceContext serviceContext)
		throws PortalException {

		return cpDefinitionOptionValueRelLocalService.
			updateCPDefinitionOptionValueRel(
				cpDefinitionOptionValueRelId, nameMap, priority, key,
				cpInstanceId, quantity, false, price, serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CPDefinitionOptionValueRel updateCPDefinitionOptionValueRel(
			long cpDefinitionOptionValueRelId, Map<Locale, String> nameMap,
			double priority, String key, long cpInstanceId, int quantity,
			boolean preselected, BigDecimal price,
			ServiceContext serviceContext)
		throws PortalException {

		// Commerce product definition option value rel

		CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
			cpDefinitionOptionValueRelPersistence.findByPrimaryKey(
				cpDefinitionOptionValueRelId);

		key = FriendlyURLNormalizerUtil.normalize(key);

		validate(
			cpDefinitionOptionValueRel.getCPDefinitionOptionValueRelId(),
			cpDefinitionOptionValueRel.getCPDefinitionOptionRelId(), key);

		CPDefinitionOptionRel cpDefinitionOptionRel =
			cpDefinitionOptionValueRel.getCPDefinitionOptionRel();

		if (cpDefinitionLocalService.isVersionable(
				cpDefinitionOptionRel.getCPDefinitionId(),
				serviceContext.getRequest())) {

			CPDefinition newCPDefinition =
				cpDefinitionLocalService.copyCPDefinition(
					cpDefinitionOptionRel.getCPDefinitionId());

			cpDefinitionOptionRel = cpDefinitionOptionRelPersistence.findByC_C(
				newCPDefinition.getCPDefinitionId(),
				cpDefinitionOptionRel.getCPOptionId());

			cpDefinitionOptionValueRel =
				cpDefinitionOptionValueRelPersistence.findByC_K(
					cpDefinitionOptionRel.getCPDefinitionOptionRelId(),
					cpDefinitionOptionValueRel.getKey());
		}

		cpDefinitionOptionValueRel.setNameMap(nameMap);
		cpDefinitionOptionValueRel.setPriority(priority);
		cpDefinitionOptionValueRel.setKey(key);
		cpDefinitionOptionValueRel.setExpandoBridgeAttributes(serviceContext);

		_updateCPDefinitionOptionValueRelCPInstance(
			cpDefinitionOptionValueRel, cpInstanceId);

		if (cpDefinitionOptionRel.isPriceTypeStatic()) {
			cpDefinitionOptionValueRel.setPrice(price);
		}

		cpDefinitionOptionValueRel.setQuantity(quantity);

		_validateLinkedCPDefinitionOptionValueRel(cpDefinitionOptionValueRel);
		_validatePriceableCPDefinitionOptionValue(
			cpDefinitionOptionValueRel, cpDefinitionOptionRel.getPriceType());

		cpDefinitionOptionValueRel =
			cpDefinitionOptionValueRelPersistence.update(
				cpDefinitionOptionValueRel);

		if (preselected) {
			cpDefinitionOptionValueRel =
				updateCPDefinitionOptionValueRelPreselected(
					cpDefinitionOptionValueRel.
						getCPDefinitionOptionValueRelId(),
					preselected);
		}

		// Commerce product definition

		reindexCPDefinition(cpDefinitionOptionRel);

		return cpDefinitionOptionValueRel;
	}

	/**
	 * @param      cpDefinitionOptionValueRelId
	 * @param      nameMap
	 * @param      priority
	 * @param      key
	 * @param      serviceContext
	 * @return
	 *
	 * @throws     PortalException
	 * @deprecated As of Athanasius (7.3.x), use {@link
	 *             #updateCPDefinitionOptionValueRel(long, Map, double, String,
	 *             long, int, boolean, BigDecimal, ServiceContext)}
	 */
	@Deprecated
	@Override
	public CPDefinitionOptionValueRel updateCPDefinitionOptionValueRel(
			long cpDefinitionOptionValueRelId, Map<Locale, String> nameMap,
			double priority, String key, ServiceContext serviceContext)
		throws PortalException {

		return cpDefinitionOptionValueRelLocalService.
			updateCPDefinitionOptionValueRel(
				cpDefinitionOptionValueRelId, nameMap, priority, key, 0, 0,
				false, null, serviceContext);
	}

	@Override
	public CPDefinitionOptionValueRel
		updateCPDefinitionOptionValueRelPreselected(
			long cpDefinitionOptionValueRelId, boolean preselected) {

		CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
			cpDefinitionOptionValueRelPersistence.fetchByPrimaryKey(
				cpDefinitionOptionValueRelId);

		if (!preselected) {
			cpDefinitionOptionValueRel.setPreselected(false);

			return cpDefinitionOptionValueRelPersistence.update(
				cpDefinitionOptionValueRel);
		}

		CPDefinitionOptionValueRel curPreselectedCPDefinitionOptionValueRel =
			fetchPreselectedCPDefinitionOptionValueRel(
				cpDefinitionOptionValueRel.getCPDefinitionOptionRelId());

		if (curPreselectedCPDefinitionOptionValueRel != null) {
			curPreselectedCPDefinitionOptionValueRel.setPreselected(false);

			cpDefinitionOptionValueRelPersistence.update(
				curPreselectedCPDefinitionOptionValueRel);
		}

		cpDefinitionOptionValueRel.setPreselected(true);

		return cpDefinitionOptionValueRelPersistence.update(
			cpDefinitionOptionValueRel);
	}

	protected SearchContext buildSearchContext(
		long companyId, long groupId, long cpDefinitionOptionRelId,
		String keywords, int start, int end, Sort sort) {

		SearchContext searchContext = new SearchContext();

		Map<String, Serializable> attributes =
			HashMapBuilder.<String, Serializable>put(
				_FIELD_KEY, keywords
			).put(
				Field.CONTENT, keywords
			).put(
				Field.ENTRY_CLASS_PK, keywords
			).put(
				Field.NAME, keywords
			).put(
				"CPDefinitionOptionRelId", cpDefinitionOptionRelId
			).put(
				"params",
				LinkedHashMapBuilder.<String, Object>put(
					"keywords", keywords
				).build()
			).build();

		searchContext.setAttributes(attributes);

		searchContext.setCompanyId(companyId);
		searchContext.setEnd(end);
		searchContext.setGroupIds(new long[] {groupId});

		if (Validator.isNotNull(keywords)) {
			searchContext.setKeywords(keywords);
		}

		if (sort != null) {
			searchContext.setSorts(sort);
		}

		searchContext.setStart(start);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		return searchContext;
	}

	protected List<CPDefinitionOptionValueRel> getCPDefinitionOptionValueRels(
			Hits hits)
		throws PortalException {

		List<Document> documents = hits.toList();

		List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels =
			new ArrayList<>(documents.size());

		for (Document document : documents) {
			long cpDefinitionOptionValueRelId = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
				fetchCPDefinitionOptionValueRel(cpDefinitionOptionValueRelId);

			if (cpDefinitionOptionValueRel == null) {
				cpDefinitionOptionValueRels = null;

				Indexer<CPDefinitionOptionValueRel> indexer =
					IndexerRegistryUtil.getIndexer(
						CPDefinitionOptionValueRel.class);

				long companyId = GetterUtil.getLong(
					document.get(Field.COMPANY_ID));

				indexer.delete(companyId, document.getUID());
			}
			else if (cpDefinitionOptionValueRels != null) {
				cpDefinitionOptionValueRels.add(cpDefinitionOptionValueRel);
			}
		}

		return cpDefinitionOptionValueRels;
	}

	protected void reindexCPDefinition(
			CPDefinitionOptionRel cpDefinitionOptionRel)
		throws PortalException {

		Indexer<CPDefinition> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			CPDefinition.class);

		indexer.reindex(
			CPDefinition.class.getName(),
			cpDefinitionOptionRel.getCPDefinitionId());
	}

	protected BaseModelSearchResult<CPDefinitionOptionValueRel> searchCPOptions(
			SearchContext searchContext)
		throws PortalException {

		Indexer<CPDefinitionOptionValueRel> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(
				CPDefinitionOptionValueRel.class);

		for (int i = 0; i < 10; i++) {
			Hits hits = indexer.search(searchContext, _SELECTED_FIELD_NAMES);

			List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels =
				getCPDefinitionOptionValueRels(hits);

			if (cpDefinitionOptionValueRels != null) {
				return new BaseModelSearchResult<>(
					cpDefinitionOptionValueRels, hits.getLength());
			}
		}

		throw new SearchException(
			"Unable to fix the search index after 10 attempts");
	}

	protected void validate(
			long cpDefinitionOptionValueRelId, long cpDefinitionOptionRelId,
			String key)
		throws PortalException {

		CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
			cpDefinitionOptionValueRelPersistence.fetchByC_K(
				cpDefinitionOptionRelId, key);

		if ((cpDefinitionOptionValueRel != null) &&
			(cpDefinitionOptionValueRel.getCPDefinitionOptionValueRelId() !=
				cpDefinitionOptionValueRelId)) {

			throw new CPDefinitionOptionValueRelKeyException();
		}
	}

	private void _addCPDefinitionOptionValueRel(
			long cpDefinitionOptionRelId, List<CPOptionValue> cpOptionValues,
			ServiceContext serviceContext)
		throws PortalException {

		for (CPOptionValue cpOptionValue : cpOptionValues) {
			if (_hasCustomAttributes(cpOptionValue)) {
				ExpandoBridge expandoBridge = cpOptionValue.getExpandoBridge();

				serviceContext.setExpandoBridgeAttributes(
					expandoBridge.getAttributes());
			}
			else {
				serviceContext.setExpandoBridgeAttributes(
					Collections.emptyMap());
			}

			cpDefinitionOptionValueRelLocalService.
				addCPDefinitionOptionValueRel(
					cpDefinitionOptionRelId, cpOptionValue, serviceContext);
		}
	}

	private boolean _hasCustomAttributes(CPOptionValue cpOptionValue)
		throws PortalException {

		try {
			return CustomAttributesUtil.hasCustomAttributes(
				cpOptionValue.getCompanyId(), CPOptionValue.class.getName(),
				cpOptionValue.getCPOptionValueId(), null);
		}
		catch (Exception exception) {
			throw new PortalException(exception);
		}
	}

	private void _updateCPDefinitionOptionValueRelCPInstance(
			CPDefinitionOptionValueRel cpDefinitionOptionValueRel,
			long cpInstanceId)
		throws PortalException {

		if (cpInstanceId <= 0) {
			cpDefinitionOptionValueRel.setCPInstanceUuid(null);
			cpDefinitionOptionValueRel.setCProductId(0);

			return;
		}

		CPInstance cpInstance = cpInstanceLocalService.getCPInstance(
			cpInstanceId);

		cpDefinitionOptionValueRel.setCPInstanceUuid(
			cpInstance.getCPInstanceUuid());

		CPDefinition cpDefinition = cpInstance.getCPDefinition();

		cpDefinitionOptionValueRel.setCProductId(cpDefinition.getCProductId());
	}

	private void _validateLinkedCPDefinitionOptionValueRel(
			CPDefinitionOptionValueRel cpDefinitionOptionValueRel)
		throws PortalException {

		if (Validator.isNull(cpDefinitionOptionValueRel.getCPInstanceUuid()) ||
			(cpDefinitionOptionValueRel.getCProductId() == 0)) {

			return;
		}

		List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels =
			cpDefinitionOptionValueRelPersistence.findByCPDefinitionOptionRelId(
				cpDefinitionOptionValueRel.getCPDefinitionOptionRelId());

		for (CPDefinitionOptionValueRel curCPDefinitionOptionValueRel :
				cpDefinitionOptionValueRels) {

			if (cpDefinitionOptionValueRel.getCPDefinitionOptionValueRelId() ==
					curCPDefinitionOptionValueRel.
						getCPDefinitionOptionValueRelId()) {

				continue;
			}

			if (Objects.equals(
					cpDefinitionOptionValueRel.getCPInstanceUuid(),
					curCPDefinitionOptionValueRel.getCPInstanceUuid()) &&
				(cpDefinitionOptionValueRel.getCProductId() ==
					curCPDefinitionOptionValueRel.getCProductId()) &&
				(cpDefinitionOptionValueRel.getQuantity() ==
					curCPDefinitionOptionValueRel.getQuantity())) {

				throw new CPDefinitionOptionValueRelQuantityException();
			}
		}
	}

	private void _validatePriceableCPDefinitionOptionValue(
			CPDefinitionOptionValueRel cpDefinitionOptionValueRel,
			String priceType)
		throws PortalException {

		if (cpDefinitionOptionValueRel.isNew()) {
			return;
		}

		if (Validator.isNull(priceType)) {
			if (Validator.isNotNull(
					cpDefinitionOptionValueRel.getCPInstanceUuid()) ||
				(cpDefinitionOptionValueRel.getPrice() != null) ||
				(cpDefinitionOptionValueRel.getCProductId() != 0) ||
				(cpDefinitionOptionValueRel.getQuantity() != 0)) {

				throw new CPDefinitionOptionValueRelCPInstanceException();
			}

			return;
		}

		if (Objects.equals(
				priceType, CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC) &&
			(cpDefinitionOptionValueRel.getPrice() == null)) {

			throw new CPDefinitionOptionValueRelPriceException();
		}

		CPInstance cpInstance = cpInstanceLocalService.fetchCProductInstance(
			cpDefinitionOptionValueRel.getCProductId(),
			cpDefinitionOptionValueRel.getCPInstanceUuid());

		if (((cpInstance == null) ||
			 (cpDefinitionOptionValueRel.getPrice() != null)) &&
			Objects.equals(
				priceType, CPConstants.PRODUCT_OPTION_PRICE_TYPE_DYNAMIC)) {

			throw new CPDefinitionOptionValueRelCPInstanceException();
		}

		if (cpInstance == null) {
			return;
		}

		if (cpDefinitionOptionRelLocalService.
				hasCPDefinitionRequiredCPDefinitionOptionRels(
					cpInstance.getCPDefinitionId()) ||
			(cpInstance.getCPSubscriptionInfo() != null)) {

			throw new CPDefinitionOptionValueRelCPInstanceException();
		}

		if (cpDefinitionOptionValueRel.getQuantity() <= 0) {
			throw new CPDefinitionOptionValueRelQuantityException();
		}

		if (!cpInstance.isApproved()) {
			throw new CPDefinitionOptionValueRelCPInstanceException();
		}
	}

	private static final String _FIELD_KEY = "key";

	private static final String[] _SELECTED_FIELD_NAMES = {
		Field.ENTRY_CLASS_PK, Field.COMPANY_ID, Field.GROUP_ID, Field.UID
	};

}