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

package com.liferay.commerce.discount.service.impl;

import com.liferay.commerce.discount.exception.CommerceDiscountDisplayDateException;
import com.liferay.commerce.discount.exception.CommerceDiscountExpirationDateException;
import com.liferay.commerce.discount.exception.CommerceDiscountTargetException;
import com.liferay.commerce.discount.exception.CommerceDiscountTitleException;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.service.base.CommerceDiscountLocalServiceBaseImpl;
import com.liferay.commerce.discount.target.CommerceDiscountTarget;
import com.liferay.commerce.discount.target.CommerceDiscountTargetRegistry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
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
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CommerceDiscountLocalServiceImpl
	extends CommerceDiscountLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceDiscount addCommerceDiscount(
			String title, String target, boolean useCouponCode,
			String couponCode, boolean usePercentage,
			BigDecimal maximumDiscountAmount, BigDecimal level1,
			BigDecimal level2, BigDecimal level3, String limitationType,
			int limitationTimes, boolean cumulative, boolean active,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		// Commerce discount

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		validate(title, target, limitationType);

		Date now = new Date();

		Date displayDate = PortalUtil.getDate(
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, user.getTimeZone(),
			CommerceDiscountDisplayDateException.class);

		Date expirationDate = null;

		if (!neverExpire) {
			expirationDate = PortalUtil.getDate(
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, user.getTimeZone(),
				CommerceDiscountExpirationDateException.class);
		}

		long commerceDiscountId = counterLocalService.increment();

		CommerceDiscount commerceDiscount = commerceDiscountPersistence.create(
			commerceDiscountId);

		commerceDiscount.setUuid(serviceContext.getUuid());
		commerceDiscount.setGroupId(groupId);
		commerceDiscount.setCompanyId(user.getCompanyId());
		commerceDiscount.setUserId(user.getUserId());
		commerceDiscount.setUserName(user.getFullName());
		commerceDiscount.setTitle(title);
		commerceDiscount.setTarget(target);
		commerceDiscount.setUseCouponCode(useCouponCode);
		commerceDiscount.setCouponCode(couponCode);
		commerceDiscount.setUsePercentage(usePercentage);
		commerceDiscount.setMaximumDiscountAmount(maximumDiscountAmount);
		commerceDiscount.setLevel1(level1);
		commerceDiscount.setLevel2(level2);
		commerceDiscount.setLevel3(level3);
		commerceDiscount.setLimitationType(limitationType);
		commerceDiscount.setLimitationTimes(limitationTimes);
		commerceDiscount.setCumulative(cumulative);
		commerceDiscount.setActive(active);
		commerceDiscount.setDisplayDate(displayDate);
		commerceDiscount.setExpirationDate(expirationDate);

		if ((expirationDate == null) || expirationDate.after(now)) {
			commerceDiscount.setStatus(WorkflowConstants.STATUS_DRAFT);
		}
		else {
			commerceDiscount.setStatus(WorkflowConstants.STATUS_EXPIRED);
		}

		commerceDiscount.setStatusByUserId(user.getUserId());
		commerceDiscount.setStatusDate(serviceContext.getModifiedDate(now));
		commerceDiscount.setExpandoBridgeAttributes(serviceContext);

		commerceDiscountPersistence.update(commerceDiscount);

		// Resources

		resourceLocalService.addModelResources(
			commerceDiscount, serviceContext);

		// Workflow

		return startWorkflowInstance(
			user.getUserId(), commerceDiscount, serviceContext);
	}

	@Override
	public void checkCommerceDiscounts() throws PortalException {
		checkCommerceDiscountsByDisplayDate();
		checkCommerceDiscountsByExpirationDate();
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommerceDiscount deleteCommerceDiscount(
			CommerceDiscount commerceDiscount)
		throws PortalException {

		// Commerce discount rels

		commerceDiscountRelLocalService.deleteCommerceDiscountRels(
			commerceDiscount.getCommerceDiscountId());

		// Commerce discount rules

		commerceDiscountRuleLocalService.deleteCommerceDiscountRules(
			commerceDiscount.getCommerceDiscountId());

		// Commerce discount user segment rels

		commerceDiscountUserSegmentRelLocalService.
			deleteCommerceDiscountUserSegmentRelsByCommerceDiscountId(
				commerceDiscount.getCommerceDiscountId());

		// Commerce discount

		commerceDiscountPersistence.remove(commerceDiscount);

		// Resources

		resourceLocalService.deleteResource(
			commerceDiscount.getCompanyId(), CommerceDiscount.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			commerceDiscount.getCommerceDiscountId());

		// Expando

		expandoRowLocalService.deleteRows(
			commerceDiscount.getCommerceDiscountId());

		// Workflow

		workflowInstanceLinkLocalService.deleteWorkflowInstanceLinks(
			commerceDiscount.getCompanyId(), commerceDiscount.getGroupId(),
			CommerceDiscount.class.getName(),
			commerceDiscount.getCommerceDiscountId());

		return commerceDiscount;
	}

	@Override
	public CommerceDiscount deleteCommerceDiscount(long commerceDiscountId)
		throws PortalException {

		CommerceDiscount commerceDiscount =
			commerceDiscountPersistence.findByPrimaryKey(commerceDiscountId);

		return commerceDiscountLocalService.deleteCommerceDiscount(
			commerceDiscount);
	}

	@Override
	public void deleteCommerceDiscounts(long groupId) throws PortalException {
		List<CommerceDiscount> commerceDiscounts =
			commerceDiscountPersistence.findByGroupId(groupId);

		for (CommerceDiscount commerceDiscount : commerceDiscounts) {
			commerceDiscountLocalService.deleteCommerceDiscount(
				commerceDiscount);
		}
	}

	@Override
	public List<CommerceDiscount> getCommerceDiscounts(
		long groupId, int start, int end,
		OrderByComparator<CommerceDiscount> orderByComparator) {

		return commerceDiscountPersistence.findByGroupId(
			groupId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceDiscountsCount(long groupId) {
		return commerceDiscountPersistence.countByGroupId(groupId);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceDiscount incrementCommerceDiscountNumberOfUse(
			long commerceDiscountId)
		throws PortalException {

		CommerceDiscount commerceDiscount =
			commerceDiscountPersistence.findByPrimaryKey(commerceDiscountId);

		commerceDiscount.setNumberOfUse(commerceDiscount.getNumberOfUse() + 1);

		commerceDiscountPersistence.update(commerceDiscount);

		return commerceDiscount;
	}

	@Override
	public BaseModelSearchResult<CommerceDiscount> searchCommerceDiscounts(
			long companyId, long groupId, String keywords, int status,
			int start, int end, Sort sort)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, groupId, keywords, status, start, end, sort);

		return searchCommerceDiscounts(searchContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceDiscount updateCommerceDiscount(
			long commerceDiscountId, String title, String target,
			boolean useCouponCode, String couponCode, boolean usePercentage,
			BigDecimal maximumDiscountAmount, BigDecimal level1,
			BigDecimal level2, BigDecimal level3, String limitationType,
			int limitationTimes, boolean cumulative, boolean active,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		CommerceDiscount commerceDiscount =
			commerceDiscountPersistence.findByPrimaryKey(commerceDiscountId);

		validate(title, target, limitationType);

		Date now = new Date();

		Date displayDate = PortalUtil.getDate(
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, user.getTimeZone(),
			CommerceDiscountDisplayDateException.class);

		Date expirationDate = null;

		if (!neverExpire) {
			expirationDate = PortalUtil.getDate(
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, user.getTimeZone(),
				CommerceDiscountExpirationDateException.class);
		}

		commerceDiscount.setTitle(title);
		commerceDiscount.setTarget(target);
		commerceDiscount.setUseCouponCode(useCouponCode);
		commerceDiscount.setCouponCode(couponCode);
		commerceDiscount.setUsePercentage(usePercentage);
		commerceDiscount.setMaximumDiscountAmount(maximumDiscountAmount);
		commerceDiscount.setLevel1(level1);
		commerceDiscount.setLevel2(level2);
		commerceDiscount.setLevel3(level3);
		commerceDiscount.setLimitationType(limitationType);
		commerceDiscount.setLimitationTimes(limitationTimes);
		commerceDiscount.setCumulative(cumulative);
		commerceDiscount.setActive(active);
		commerceDiscount.setDisplayDate(displayDate);
		commerceDiscount.setExpirationDate(expirationDate);

		if ((expirationDate == null) || expirationDate.after(now)) {
			commerceDiscount.setStatus(WorkflowConstants.STATUS_DRAFT);
		}
		else {
			commerceDiscount.setStatus(WorkflowConstants.STATUS_EXPIRED);
		}

		commerceDiscount.setStatusByUserId(user.getUserId());
		commerceDiscount.setStatusDate(serviceContext.getModifiedDate(now));
		commerceDiscount.setExpandoBridgeAttributes(serviceContext);

		commerceDiscountPersistence.update(commerceDiscount);

		return startWorkflowInstance(
			user.getUserId(), commerceDiscount, serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceDiscount updateStatus(
			long userId, long commerceDiscountId, int status,
			ServiceContext serviceContext,
			Map<String, Serializable> workflowContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);
		Date now = new Date();

		CommerceDiscount commerceDiscount =
			commerceDiscountPersistence.findByPrimaryKey(commerceDiscountId);

		if ((status == WorkflowConstants.STATUS_APPROVED) &&
			(commerceDiscount.getDisplayDate() != null) &&
			now.before(commerceDiscount.getDisplayDate())) {

			status = WorkflowConstants.STATUS_SCHEDULED;
		}

		Date modifiedDate = serviceContext.getModifiedDate(now);

		commerceDiscount.setModifiedDate(modifiedDate);

		if (status == WorkflowConstants.STATUS_APPROVED) {
			Date expirationDate = commerceDiscount.getExpirationDate();

			if ((expirationDate != null) && expirationDate.before(now)) {
				commerceDiscount.setExpirationDate(null);
			}
		}

		if (status == WorkflowConstants.STATUS_EXPIRED) {
			commerceDiscount.setExpirationDate(now);
		}

		commerceDiscount.setStatus(status);
		commerceDiscount.setStatusByUserId(user.getUserId());
		commerceDiscount.setStatusByUserName(user.getFullName());
		commerceDiscount.setStatusDate(modifiedDate);

		commerceDiscountPersistence.update(commerceDiscount);

		return commerceDiscount;
	}

	protected SearchContext buildSearchContext(
		long companyId, long groupId, String keywords, int status, int start,
		int end, Sort sort) {

		SearchContext searchContext = new SearchContext();

		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		params.put("keywords", keywords);

		Map<String, Serializable> attributes = new HashMap<>();

		attributes.put(Field.ENTRY_CLASS_PK, keywords);
		attributes.put(Field.TITLE, keywords);
		attributes.put(Field.STATUS, status);
		attributes.put("params", params);

		searchContext.setAttributes(attributes);

		searchContext.setCompanyId(companyId);
		searchContext.setStart(start);
		searchContext.setEnd(end);
		searchContext.setGroupIds(new long[] {groupId});

		if (Validator.isNotNull(keywords)) {
			searchContext.setKeywords(keywords);
		}

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		if (sort != null) {
			searchContext.setSorts(sort);
		}

		return searchContext;
	}

	protected void checkCommerceDiscountsByDisplayDate()
		throws PortalException {

		List<CommerceDiscount> commerceDiscounts =
			commerceDiscountPersistence.findByLtD_S(
				new Date(), WorkflowConstants.STATUS_SCHEDULED);

		for (CommerceDiscount commerceDiscount : commerceDiscounts) {
			long userId = PortalUtil.getValidUserId(
				commerceDiscount.getCompanyId(), commerceDiscount.getUserId());

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setCommand(Constants.UPDATE);
			serviceContext.setScopeGroupId(commerceDiscount.getGroupId());

			commerceDiscountLocalService.updateStatus(
				userId, commerceDiscount.getCommerceDiscountId(),
				WorkflowConstants.STATUS_APPROVED, serviceContext,
				new HashMap<String, Serializable>());
		}
	}

	protected void checkCommerceDiscountsByExpirationDate()
		throws PortalException {

		List<CommerceDiscount> commerceDiscounts =
			commerceDiscountPersistence.findByLtE_S(
				new Date(), WorkflowConstants.STATUS_APPROVED);

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Expiring " + commerceDiscounts.size() + " commerce discounts");
		}

		if ((commerceDiscounts != null) && !commerceDiscounts.isEmpty()) {
			for (CommerceDiscount commerceDiscount : commerceDiscounts) {
				long userId = PortalUtil.getValidUserId(
					commerceDiscount.getCompanyId(),
					commerceDiscount.getUserId());

				ServiceContext serviceContext = new ServiceContext();

				serviceContext.setCommand(Constants.UPDATE);
				serviceContext.setScopeGroupId(commerceDiscount.getGroupId());

				commerceDiscountLocalService.updateStatus(
					userId, commerceDiscount.getCommerceDiscountId(),
					WorkflowConstants.STATUS_EXPIRED, serviceContext,
					new HashMap<String, Serializable>());
			}
		}
	}

	protected List<CommerceDiscount> getCommerceDiscounts(Hits hits)
		throws PortalException {

		List<Document> documents = hits.toList();

		List<CommerceDiscount> commerceDiscounts = new ArrayList<>(
			documents.size());

		for (Document document : documents) {
			long commerceDiscountId = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			CommerceDiscount commerceDiscount = fetchCommerceDiscount(
				commerceDiscountId);

			if (commerceDiscount == null) {
				commerceDiscounts = null;

				Indexer<CommerceDiscount> indexer =
					IndexerRegistryUtil.getIndexer(CommerceDiscount.class);

				long companyId = GetterUtil.getLong(
					document.get(Field.COMPANY_ID));

				indexer.delete(companyId, document.getUID());
			}
			else if (commerceDiscounts != null) {
				commerceDiscounts.add(commerceDiscount);
			}
		}

		return commerceDiscounts;
	}

	protected BaseModelSearchResult<CommerceDiscount> searchCommerceDiscounts(
			SearchContext searchContext)
		throws PortalException {

		Indexer<CommerceDiscount> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(CommerceDiscount.class);

		for (int i = 0; i < 10; i++) {
			Hits hits = indexer.search(searchContext, _SELECTED_FIELD_NAMES);

			List<CommerceDiscount> commerceDiscounts = getCommerceDiscounts(
				hits);

			if (commerceDiscounts != null) {
				return new BaseModelSearchResult<>(
					commerceDiscounts, hits.getLength());
			}
		}

		throw new SearchException(
			"Unable to fix the search index after 10 attempts");
	}

	protected CommerceDiscount startWorkflowInstance(
			long userId, CommerceDiscount commerceDiscount,
			ServiceContext serviceContext)
		throws PortalException {

		Map<String, Serializable> workflowContext = new HashMap<>();

		return WorkflowHandlerRegistryUtil.startWorkflowInstance(
			commerceDiscount.getCompanyId(), commerceDiscount.getGroupId(),
			userId, CommerceDiscount.class.getName(),
			commerceDiscount.getCommerceDiscountId(), commerceDiscount,
			serviceContext, workflowContext);
	}

	protected void validate(String title, String target, String limitationType)
		throws PortalException {

		if (Validator.isNull(title)) {
			throw new CommerceDiscountTitleException();
		}

		CommerceDiscountTarget commerceDiscountTarget =
			_commerceDiscountTargetRegistry.getCommerceDiscountTarget(target);

		if (commerceDiscountTarget == null) {
			throw new CommerceDiscountTargetException();
		}

		if (Validator.isNull(limitationType)) {
			throw new CommerceDiscountTargetException();
		}
	}

	private static final String[] _SELECTED_FIELD_NAMES =
		{Field.ENTRY_CLASS_PK, Field.COMPANY_ID, Field.GROUP_ID, Field.UID};

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceDiscountLocalServiceImpl.class);

	@ServiceReference(type = CommerceDiscountTargetRegistry.class)
	private CommerceDiscountTargetRegistry _commerceDiscountTargetRegistry;

}