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

import com.liferay.commerce.product.exception.CPInstanceDisplayDateException;
import com.liferay.commerce.product.exception.CPInstanceExpirationDateException;
import com.liferay.commerce.product.exception.NoSuchSkuContributorCPDefinitionOptionRelException;
import com.liferay.commerce.product.internal.util.SKUCombinationsIterator;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.base.CPInstanceLocalServiceBaseImpl;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
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
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Marco Leo
 */
public class CPInstanceLocalServiceImpl extends CPInstanceLocalServiceBaseImpl {

	public static final String[] SELECTED_FIELD_NAMES =
		{Field.ENTRY_CLASS_PK, Field.COMPANY_ID, Field.GROUP_ID, Field.UID};

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CPInstance addCPInstance(
			long cpDefinitionId, String sku, String ddmContent,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		// Commerce product instance

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		Date displayDate = null;
		Date expirationDate = null;
		Date now = new Date();

		displayDate = PortalUtil.getDate(
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, user.getTimeZone(),
			CPInstanceDisplayDateException.class);

		if (!neverExpire) {
			expirationDate = PortalUtil.getDate(
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, user.getTimeZone(),
				CPInstanceExpirationDateException.class);
		}

		long cpInstanceId = counterLocalService.increment();

		CPInstance cpInstance = cpInstancePersistence.create(cpInstanceId);

		cpInstance.setUuid(serviceContext.getUuid());
		cpInstance.setGroupId(groupId);
		cpInstance.setCompanyId(user.getCompanyId());
		cpInstance.setUserId(user.getUserId());
		cpInstance.setUserName(user.getFullName());
		cpInstance.setCPDefinitionId(cpDefinitionId);
		cpInstance.setSku(sku);
		cpInstance.setDDMContent(ddmContent);
		cpInstance.setDisplayDate(displayDate);
		cpInstance.setExpirationDate(expirationDate);

		if ((expirationDate == null) || expirationDate.after(now)) {
			cpInstance.setStatus(WorkflowConstants.STATUS_DRAFT);
		}
		else {
			cpInstance.setStatus(WorkflowConstants.STATUS_EXPIRED);
		}

		cpInstance.setStatusByUserId(user.getUserId());
		cpInstance.setStatusDate(serviceContext.getModifiedDate(now));
		cpInstance.setExpandoBridgeAttributes(serviceContext);

		cpInstancePersistence.update(cpInstance);

		// Workflow

		return startWorkflowInstance(
			user.getUserId(), cpInstance, serviceContext);
	}

	@Override
	public void buildCPInstances(
			long cpDefinitionId, ServiceContext serviceContext)
		throws PortalException {

		CPDefinition cpDefinition = cpDefinitionLocalService.getCPDefinition(
			cpDefinitionId);

		boolean neverExpire = false;

		if (cpDefinition.getExpirationDate() == null) {
			neverExpire = true;
		}

		Map<CPDefinitionOptionRel, CPDefinitionOptionValueRel[]>
			combinationGeneratorMap = new HashMap<>();

		List<CPDefinitionOptionRel> cpDefinitionOptionRels =
			cpDefinitionOptionRelLocalService.
				getSkuContributorCPDefinitionOptionRels(cpDefinitionId);

		if (cpDefinitionOptionRels.isEmpty()) {
			throw new NoSuchSkuContributorCPDefinitionOptionRelException();
		}

		for (CPDefinitionOptionRel cpDefinitionOptionRel :
				cpDefinitionOptionRels) {

			List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels =
				cpDefinitionOptionRel.getCPDefinitionOptionValueRels();

			CPDefinitionOptionValueRel[] cpDefinitionOptionValueRelArray =
				new CPDefinitionOptionValueRel[
					cpDefinitionOptionValueRels.size()];

			cpDefinitionOptionValueRelArray =
				cpDefinitionOptionValueRels.toArray(
					cpDefinitionOptionValueRelArray);

			combinationGeneratorMap.put(
				cpDefinitionOptionRel, cpDefinitionOptionValueRelArray);
		}

		SKUCombinationsIterator iterator = new SKUCombinationsIterator(
			combinationGeneratorMap);

		while (iterator.hasNext()) {
			CPDefinitionOptionValueRel[]
				cpDefinitionOptionValueRels = iterator.next();

			JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

			StringBuilder sku = new StringBuilder(cpDefinition.getBaseSKU());

			for (CPDefinitionOptionValueRel
					cpDefinitionOptionValueRel :
						cpDefinitionOptionValueRels) {

				sku.append(
					StringUtil.toUpperCase(
						cpDefinitionOptionValueRel.getTitle(
							serviceContext.getLanguageId())));

				JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

				jsonObject.put(
					"cpDefinitionOptionRelId",
					cpDefinitionOptionValueRel.getCPDefinitionOptionRelId());
				jsonObject.put(
					"cpDefinitionOptionValueRelId",
					cpDefinitionOptionValueRel.
						getCPDefinitionOptionValueRelId());

				jsonArray.put(jsonObject);
			}

			addCPInstance(
				cpDefinitionId, sku.toString(), jsonArray.toString(),
				cpDefinition.getDisplayDate(), cpDefinition.getExpirationDate(),
				neverExpire, serviceContext);
		}
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CPInstance deleteCPInstance(CPInstance cpInstance)
		throws PortalException {

		// Commerce product instance

		cpInstancePersistence.remove(cpInstance);

		// Expando

		expandoRowLocalService.deleteRows(cpInstance.getCPInstanceId());

		// Workflow

		workflowInstanceLinkLocalService.deleteWorkflowInstanceLinks(
			cpInstance.getCompanyId(), cpInstance.getGroupId(),
			CPInstance.class.getName(), cpInstance.getCPInstanceId());

		return cpInstance;
	}

	@Override
	public CPInstance deleteCPInstance(long cpInstanceId)
		throws PortalException {

		CPInstance cpInstance = cpInstancePersistence.findByPrimaryKey(
			cpInstanceId);

		return cpInstanceLocalService.deleteCPInstance(cpInstance);
	}

	@Override
	public void deleteCPInstances(long cpDefinitionId) throws PortalException {
		List<CPInstance> cpInstances = cpInstanceLocalService.getCPInstances(
			cpDefinitionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (CPInstance cpInstance : cpInstances) {
			cpInstanceLocalService.deleteCPInstance(cpInstance);
		}
	}

	@Override
	public List<CPInstance> getCPInstances(
		long cpDefinitionId, int start, int end) {

		return cpInstancePersistence.findByCPDefinitionId(
			cpDefinitionId, start, end);
	}

	@Override
	public List<CPInstance> getCPInstances(
		long cpDefinitionId, int start, int end,
		OrderByComparator<CPInstance> orderByComparator) {

		return cpInstancePersistence.findByCPDefinitionId(
			cpDefinitionId, start, end, orderByComparator);
	}

	@Override
	public int getCPInstancesCount(long cpDefinitionId) {
		return cpInstancePersistence.countByCPDefinitionId(cpDefinitionId);
	}

	@Override
	public String[] getSKUs(long cpDefinitionId) {
		List<CPInstance> cpInstances = getCPInstances(
			cpDefinitionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		String[] skusArray = new String[cpInstances.size()];

		List<String> skus = new ArrayList<>();

		for (CPInstance cpInstance : cpInstances) {
			skus.add(cpInstance.getSku());
		}

		skusArray = skus.toArray(skusArray);

		return skusArray;
	}

	@Override
	public Hits search(SearchContext searchContext) {
		try {
			Indexer<CPInstance> indexer =
				IndexerRegistryUtil.nullSafeGetIndexer(CPInstance.class);

			return indexer.search(searchContext);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@Override
	public BaseModelSearchResult<CPInstance> searchCPOptions(
			long companyId, long groupId, long cpDefinitionId, String keywords,
			int start, int end, Sort sort)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, groupId, cpDefinitionId, keywords, start, end, sort);

		return searchCPInstances(searchContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CPInstance updateCPInstance(
			long cpInstanceId, String sku, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		// Commerce product instance

		User user = userLocalService.getUser(serviceContext.getUserId());
		CPInstance cpInstance = cpInstancePersistence.findByPrimaryKey(
			cpInstanceId);

		Date displayDate = null;
		Date expirationDate = null;
		Date now = new Date();

		displayDate = PortalUtil.getDate(
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, user.getTimeZone(),
			CPInstanceDisplayDateException.class);

		if (!neverExpire) {
			expirationDate = PortalUtil.getDate(
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, user.getTimeZone(),
				CPInstanceExpirationDateException.class);
		}

		cpInstance.setSku(sku);
		cpInstance.setDisplayDate(displayDate);
		cpInstance.setExpirationDate(expirationDate);

		if ((expirationDate == null) || expirationDate.after(now)) {
			cpInstance.setStatus(WorkflowConstants.STATUS_DRAFT);
		}
		else {
			cpInstance.setStatus(WorkflowConstants.STATUS_EXPIRED);
		}

		cpInstance.setStatusByUserId(user.getUserId());
		cpInstance.setStatusDate(serviceContext.getModifiedDate(now));
		cpInstance.setExpandoBridgeAttributes(serviceContext);

		cpInstancePersistence.update(cpInstance);

		// Workflow

		return startWorkflowInstance(
			user.getUserId(), cpInstance, serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CPInstance updateStatus(
			long userId, long cpInstanceId, int status,
			ServiceContext serviceContext,
			Map<String, Serializable> workflowContext)
		throws PortalException {

		// Commerce product instance

		User user = userLocalService.getUser(userId);
		Date now = new Date();

		CPInstance cpInstance = cpInstancePersistence.findByPrimaryKey(
			cpInstanceId);

		if ((status == WorkflowConstants.STATUS_APPROVED) &&
			(cpInstance.getDisplayDate() != null) &&
			now.before(cpInstance.getDisplayDate())) {

			status = WorkflowConstants.STATUS_SCHEDULED;
		}

		Date modifiedDate = serviceContext.getModifiedDate(now);

		cpInstance.setModifiedDate(modifiedDate);

		if (status == WorkflowConstants.STATUS_APPROVED) {
			Date expirationDate = cpInstance.getExpirationDate();

			if ((expirationDate != null) && expirationDate.before(now)) {
				cpInstance.setExpirationDate(null);
			}
		}

		if (status == WorkflowConstants.STATUS_EXPIRED) {
			cpInstance.setExpirationDate(now);
		}

		cpInstance.setStatus(status);
		cpInstance.setStatusByUserId(user.getUserId());
		cpInstance.setStatusByUserName(user.getFullName());
		cpInstance.setStatusDate(modifiedDate);

		cpInstancePersistence.update(cpInstance);

		return cpInstance;
	}

	protected CPInstance addCPInstance(
			long cpDefinitionId, String sku, String ddmContent,
			Date displayDate, Date expirationDate, boolean neverExpire,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());

		Calendar displayDateCalendar = CalendarFactoryUtil.getCalendar(
			displayDate.getTime(), user.getTimeZone());

		int displayDateMonth = displayDateCalendar.get(Calendar.MONTH);
		int displayDateDay = displayDateCalendar.get(Calendar.DAY_OF_MONTH);
		int displayDateYear = displayDateCalendar.get(Calendar.YEAR);
		int displayDateHour = displayDateCalendar.get(Calendar.HOUR);
		int displayDateMinute = displayDateCalendar.get(Calendar.MINUTE);

		int expirationDateMonth = 0;
		int expirationDateDay = 0;
		int expirationDateYear = 0;
		int expirationDateHour = 0;
		int expirationDateMinute = 0;

		if (!neverExpire) {
			Calendar expirationDateCalendar = CalendarFactoryUtil.getCalendar(
				expirationDate.getTime(), user.getTimeZone());

			expirationDateMonth = expirationDateCalendar.get(Calendar.MONTH);
			expirationDateDay = expirationDateCalendar.get(
				Calendar.DAY_OF_MONTH);
			expirationDateYear = expirationDateCalendar.get(Calendar.YEAR);
			expirationDateHour = expirationDateCalendar.get(Calendar.HOUR);
			expirationDateMinute = expirationDateCalendar.get(Calendar.MINUTE);
		}

		return cpInstanceLocalService.addCPInstance(
			cpDefinitionId, sku, ddmContent, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	protected SearchContext buildSearchContext(
		long companyId, long groupId, long cpDefinitionId, String keywords,
		int start, int end, Sort sort) {

		SearchContext searchContext = new SearchContext();

		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		params.put("keywords", keywords);

		Map<String, Serializable> attributes = new HashMap<>();

		attributes.put(Field.CONTENT, keywords);
		attributes.put("cpDefinitionId", cpDefinitionId);
		attributes.put("params", params);

		searchContext.setAttributes(attributes);

		searchContext.setCompanyId(companyId);
		searchContext.setStart(start);
		searchContext.setEnd(end);
		searchContext.setGroupIds(new long[] {groupId});

		if (Validator.isNotNull(keywords)) {
			searchContext.setKeywords(keywords);
		}

		QueryConfig queryConfig = new QueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		searchContext.setQueryConfig(queryConfig);

		if (sort != null) {
			searchContext.setSorts(sort);
		}

		return searchContext;
	}

	protected BaseModelSearchResult<CPInstance> searchCPInstances(
			SearchContext searchContext)
		throws PortalException {

		Indexer<CPInstance> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			CPInstance.class);

		List<CPInstance> cpInstances = new ArrayList<>();

		for (int i = 0; i < 10; i++) {
			Hits hits = indexer.search(searchContext, SELECTED_FIELD_NAMES);

			Document[] documents = hits.getDocs();

			for (Document document : documents) {
				long classPK = GetterUtil.getLong(
					document.get(Field.ENTRY_CLASS_PK));

				CPInstance cpInstance = getCPInstance(classPK);

				cpInstances.add(cpInstance);
			}

			if (cpInstances != null) {
				return new BaseModelSearchResult<>(
					cpInstances, hits.getLength());
			}
		}

		throw new SearchException(
			"Unable to fix the search index after 10 attempts");
	}

	protected CPInstance startWorkflowInstance(
			long userId, CPInstance cpInstance, ServiceContext serviceContext)
		throws PortalException {

		Map<String, Serializable> workflowContext = new HashMap<>();

		return WorkflowHandlerRegistryUtil.startWorkflowInstance(
			cpInstance.getCompanyId(), cpInstance.getGroupId(), userId,
			CPInstance.class.getName(), cpInstance.getCPInstanceId(),
			cpInstance, serviceContext, workflowContext);
	}

}