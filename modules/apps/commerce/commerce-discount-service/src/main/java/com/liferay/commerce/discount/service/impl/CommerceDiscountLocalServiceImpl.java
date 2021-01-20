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

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.commerce.discount.constants.CommerceDiscountConstants;
import com.liferay.commerce.discount.exception.CommerceDiscountCouponCodeException;
import com.liferay.commerce.discount.exception.CommerceDiscountDisplayDateException;
import com.liferay.commerce.discount.exception.CommerceDiscountExpirationDateException;
import com.liferay.commerce.discount.exception.CommerceDiscountLimitationTypeException;
import com.liferay.commerce.discount.exception.CommerceDiscountTargetException;
import com.liferay.commerce.discount.exception.CommerceDiscountTitleException;
import com.liferay.commerce.discount.exception.DuplicateCommerceDiscountException;
import com.liferay.commerce.discount.exception.NoSuchDiscountException;
import com.liferay.commerce.discount.internal.search.CommerceDiscountIndexer;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.service.base.CommerceDiscountLocalServiceBaseImpl;
import com.liferay.commerce.discount.target.CommerceDiscountTarget;
import com.liferay.commerce.discount.target.CommerceDiscountTargetRegistry;
import com.liferay.commerce.discount.util.comparator.CommerceDiscountCreateDateComparator;
import com.liferay.commerce.pricing.service.CommercePricingClassLocalService;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.petra.string.StringPool;
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
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CommerceDiscountLocalServiceImpl
	extends CommerceDiscountLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceDiscount addCommerceDiscount(
			long userId, String title, String target, boolean useCouponCode,
			String couponCode, boolean usePercentage,
			BigDecimal maximumDiscountAmount, BigDecimal level1,
			BigDecimal level2, BigDecimal level3, BigDecimal level4,
			String limitationType, int limitationTimes, boolean active,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		return commerceDiscountLocalService.addCommerceDiscount(
			userId, title, target, useCouponCode, couponCode, usePercentage,
			maximumDiscountAmount, StringPool.BLANK, level1, level2, level3,
			level4, limitationType, limitationTimes, true, active,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceDiscount addCommerceDiscount(
			long userId, String title, String target, boolean useCouponCode,
			String couponCode, boolean usePercentage,
			BigDecimal maximumDiscountAmount, String level, BigDecimal level1,
			BigDecimal level2, BigDecimal level3, BigDecimal level4,
			String limitationType, int limitationTimes,
			boolean rulesConjunction, boolean active, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		return addCommerceDiscount(
			null, userId, title, target, useCouponCode, couponCode,
			usePercentage, maximumDiscountAmount, level, level1, level2, level3,
			level4, limitationType, limitationTimes, rulesConjunction, active,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #addCommerceDiscount(String, long, String, String, boolean,
	 *             String, boolean, BigDecimal, String, BigDecimal, BigDecimal,
	 *             BigDecimal, BigDecimal, String, int, boolean, boolean, int,
	 *             int, int, int, int, int, int, int, int, int, boolean,
	 *             ServiceContext)}
	 */
	@Deprecated
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceDiscount addCommerceDiscount(
			long userId, String title, String target, boolean useCouponCode,
			String couponCode, boolean usePercentage,
			BigDecimal maximumDiscountAmount, String level, BigDecimal level1,
			BigDecimal level2, BigDecimal level3, BigDecimal level4,
			String limitationType, int limitationTimes,
			boolean rulesConjunction, boolean active, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			String externalReferenceCode, boolean neverExpire,
			ServiceContext serviceContext)
		throws PortalException {

		return addCommerceDiscount(
			externalReferenceCode, userId, title, target, useCouponCode,
			couponCode, usePercentage, maximumDiscountAmount, level, level1,
			level2, level3, level4, limitationType, limitationTimes,
			rulesConjunction, active, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #addCommerceDiscount(String, long, String, String, boolean, String,
	 *             boolean, BigDecimal, String, BigDecimal, BigDecimal,
	 *             BigDecimal, BigDecimal, BigDecimal, String, int, int,
	 *             boolean, boolean, int, int, int, int, int, int, int, int,
	 *             int, int, boolean, ServiceContext)}
	 */
	@Deprecated
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceDiscount addCommerceDiscount(
			long userId, String title, String target, boolean useCouponCode,
			String couponCode, boolean usePercentage,
			BigDecimal maximumDiscountAmount, String level, BigDecimal level1,
			BigDecimal level2, BigDecimal level3, BigDecimal level4,
			String limitationType, int limitationTimes,
			int limitationTimesPerAccount, boolean rulesConjunction,
			boolean active, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, String externalReferenceCode,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		return addCommerceDiscount(
			externalReferenceCode, userId, title, target, useCouponCode,
			couponCode, usePercentage, maximumDiscountAmount, level, level1,
			level2, level3, level4, limitationType, limitationTimes,
			limitationTimesPerAccount, rulesConjunction, active,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceDiscount addCommerceDiscount(
			String externalReferenceCode, long userId, String title,
			String target, boolean useCouponCode, String couponCode,
			boolean usePercentage, BigDecimal maximumDiscountAmount,
			String level, BigDecimal level1, BigDecimal level2,
			BigDecimal level3, BigDecimal level4, String limitationType,
			int limitationTimes, boolean rulesConjunction, boolean active,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		return addCommerceDiscount(
			externalReferenceCode, userId, title, target, useCouponCode,
			couponCode, usePercentage, maximumDiscountAmount, level, level1,
			level2, level3, level4, limitationType, limitationTimes, 0,
			rulesConjunction, active, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceDiscount addCommerceDiscount(
			String externalReferenceCode, long userId, String title,
			String target, boolean useCouponCode, String couponCode,
			boolean usePercentage, BigDecimal maximumDiscountAmount,
			String level, BigDecimal level1, BigDecimal level2,
			BigDecimal level3, BigDecimal level4, String limitationType,
			int limitationTimes, int limitationTimesPerAccount,
			boolean rulesConjunction, boolean active, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		if (Validator.isBlank(externalReferenceCode)) {
			externalReferenceCode = null;
		}

		validateExternalReferenceCode(
			externalReferenceCode, serviceContext.getCompanyId());

		// Commerce discount

		User user = userLocalService.getUser(userId);

		validate(
			serviceContext.getCompanyId(), 0, title, target, useCouponCode,
			couponCode, limitationType);

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

		commerceDiscount.setExternalReferenceCode(externalReferenceCode);
		commerceDiscount.setCompanyId(user.getCompanyId());
		commerceDiscount.setUserId(user.getUserId());
		commerceDiscount.setUserName(user.getFullName());
		commerceDiscount.setTitle(title);
		commerceDiscount.setTarget(target);
		commerceDiscount.setUseCouponCode(useCouponCode);
		commerceDiscount.setCouponCode(couponCode);
		commerceDiscount.setUsePercentage(usePercentage);
		commerceDiscount.setMaximumDiscountAmount(maximumDiscountAmount);
		commerceDiscount.setLevel(level);

		if (!level.isEmpty()) {
			if (level.equals(CommerceDiscountConstants.LEVEL_L1)) {
				commerceDiscount.setLevel1(level1);
			}
			else if (level.equals(CommerceDiscountConstants.LEVEL_L2)) {
				commerceDiscount.setLevel2(level1);
			}
			else if (level.equals(CommerceDiscountConstants.LEVEL_L3)) {
				commerceDiscount.setLevel3(level1);
			}
			else if (level.equals(CommerceDiscountConstants.LEVEL_L4)) {
				commerceDiscount.setLevel4(level1);
			}
		}
		else {
			commerceDiscount.setLevel1(level1);
			commerceDiscount.setLevel2(level2);
			commerceDiscount.setLevel3(level3);
			commerceDiscount.setLevel4(level4);
		}

		commerceDiscount.setLimitationType(limitationType);
		commerceDiscount.setLimitationTimes(limitationTimes);
		commerceDiscount.setLimitationTimesPerAccount(
			limitationTimesPerAccount);
		commerceDiscount.setRulesConjunction(rulesConjunction);
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

		commerceDiscount = commerceDiscountPersistence.update(commerceDiscount);

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

		// Commerce discount usage entries

		commerceDiscountUsageEntryLocalService.
			deleteCommerceUsageEntryByDiscountId(
				commerceDiscount.getCommerceDiscountId());

		// Commerce discount rels

		commerceDiscountRelLocalService.deleteCommerceDiscountRels(
			commerceDiscount.getCommerceDiscountId());

		// Commerce discount rules

		commerceDiscountRuleLocalService.deleteCommerceDiscountRules(
			commerceDiscount.getCommerceDiscountId());

		// Commerce discount account groups rels

		commerceDiscountCommerceAccountGroupRelLocalService.
			deleteCommerceDiscountCommerceAccountGroupRelsByCommerceDiscountId(
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
			commerceDiscount.getCompanyId(), 0L,
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
	public void deleteCommerceDiscounts(long companyId) throws PortalException {
		List<CommerceDiscount> commerceDiscounts =
			commerceDiscountPersistence.findByCompanyId(companyId);

		for (CommerceDiscount commerceDiscount : commerceDiscounts) {
			commerceDiscountLocalService.deleteCommerceDiscount(
				commerceDiscount);
		}
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #fetchByExternalReferenceCode(String, long)}
	 */
	@Deprecated
	@Override
	public CommerceDiscount fetchByExternalReferenceCode(
		long companyId, String externalReferenceCode) {

		if (Validator.isBlank(externalReferenceCode)) {
			return null;
		}

		return commerceDiscountPersistence.fetchByC_ERC(
			companyId, externalReferenceCode);
	}

	@Override
	public CommerceDiscount fetchByExternalReferenceCode(
		String externalReferenceCode, long companyId) {

		if (Validator.isBlank(externalReferenceCode)) {
			return null;
		}

		return commerceDiscountPersistence.fetchByC_ERC(
			companyId, externalReferenceCode);
	}

	@Override
	public List<CommerceDiscount> getAccountAndChannelCommerceDiscounts(
		long commerceAccountId, long commerceChannelId, long cpDefinitionId) {

		return commerceDiscountFinder.findByA_C_C_C_Product(
			commerceAccountId, commerceChannelId, cpDefinitionId,
			_getAssetCategoryIds(cpDefinitionId),
			_commercePricingClassLocalService.
				getCommercePricingClassByCPDefinition(cpDefinitionId));
	}

	@Override
	public List<CommerceDiscount> getAccountAndChannelCommerceDiscounts(
		long commerceAccountId, long commerceChannelId,
		String commerceDiscountTargetType) {

		return commerceDiscountFinder.findByA_C_C_C_Order(
			commerceAccountId, commerceChannelId, commerceDiscountTargetType);
	}

	@Override
	public List<CommerceDiscount> getAccountCommerceDiscounts(
		long commerceAccountId, long cpDefinitionId) {

		return commerceDiscountFinder.findByA_C_C_Product(
			commerceAccountId, cpDefinitionId,
			_getAssetCategoryIds(cpDefinitionId),
			_commercePricingClassLocalService.
				getCommercePricingClassByCPDefinition(cpDefinitionId));
	}

	@Override
	public List<CommerceDiscount> getAccountCommerceDiscounts(
		long commerceAccountId, String commerceDiscountTargetType) {

		return commerceDiscountFinder.findByA_C_C_Order(
			commerceAccountId, commerceDiscountTargetType);
	}

	@Override
	public List<CommerceDiscount> getAccountGroupAndChannelCommerceDiscount(
		long[] commerceAccountGroupIds, long commerceChannelId,
		long cpDefinitionId) {

		return commerceDiscountFinder.findByAG_C_C_C_Product(
			commerceAccountGroupIds, commerceChannelId, cpDefinitionId,
			_getAssetCategoryIds(cpDefinitionId),
			_commercePricingClassLocalService.
				getCommercePricingClassByCPDefinition(cpDefinitionId));
	}

	@Override
	public List<CommerceDiscount> getAccountGroupAndChannelCommerceDiscount(
		long[] commerceAccountGroupIds, long commerceChannelId,
		String commerceDiscountTargetType) {

		return commerceDiscountFinder.findByAG_C_C_C_Order(
			commerceAccountGroupIds, commerceChannelId,
			commerceDiscountTargetType);
	}

	@Override
	public List<CommerceDiscount> getAccountGroupCommerceDiscount(
		long[] commerceAccountGroupIds, long cpDefinitionId) {

		return commerceDiscountFinder.findByAG_C_C_Product(
			commerceAccountGroupIds, cpDefinitionId,
			_getAssetCategoryIds(cpDefinitionId),
			_commercePricingClassLocalService.
				getCommercePricingClassByCPDefinition(cpDefinitionId));
	}

	@Override
	public List<CommerceDiscount> getAccountGroupCommerceDiscount(
		long[] commerceAccountGroupIds, String commerceDiscountTargetType) {

		return commerceDiscountFinder.findByAG_C_C_Order(
			commerceAccountGroupIds, commerceDiscountTargetType);
	}

	@Override
	public CommerceDiscount getActiveCommerceDiscount(
			long companyId, String couponCode, boolean active)
		throws PortalException {

		return commerceDiscountPersistence.findByC_C_A(
			companyId, couponCode, active);
	}

	@Override
	public int getActiveCommerceDiscountsCount(
		long companyId, String couponCode, boolean active) {

		return commerceDiscountPersistence.countByC_C_A(
			companyId, couponCode, active);
	}

	@Override
	public List<CommerceDiscount> getChannelCommerceDiscounts(
		long commerceChannelId, long cpDefinitionId) {

		return commerceDiscountFinder.findByC_C_C_Product(
			commerceChannelId, cpDefinitionId,
			_getAssetCategoryIds(cpDefinitionId),
			_commercePricingClassLocalService.
				getCommercePricingClassByCPDefinition(cpDefinitionId));
	}

	@Override
	public List<CommerceDiscount> getChannelCommerceDiscounts(
		long commerceChannelId, String commerceDiscountTargetType) {

		return commerceDiscountFinder.findByC_C_C_Order(
			commerceChannelId, commerceDiscountTargetType);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public List<CommerceDiscount> getCommerceDiscounts(
		long companyId, String couponCode) {

		return commerceDiscountPersistence.findByC_C(companyId, couponCode);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public int getCommerceDiscountsCount(long companyId, String couponCode) {
		return commerceDiscountPersistence.countByC_C(companyId, couponCode);
	}

	@Override
	public int getCommerceDiscountsCountByPricingClassId(
		long commercePricingClassId, String title) {

		return commerceDiscountFinder.countByCommercePricingClassId(
			commercePricingClassId, title);
	}

	@Override
	public List<CommerceDiscount> getPriceListCommerceDiscounts(
		long[] commerceDiscountIds, long cpDefinitionId) {

		return commerceDiscountFinder.findPriceListDiscountProduct(
			commerceDiscountIds, cpDefinitionId,
			_getAssetCategoryIds(cpDefinitionId),
			_commercePricingClassLocalService.
				getCommercePricingClassByCPDefinition(cpDefinitionId));
	}

	@Override
	public List<CommerceDiscount> getUnqualifiedCommerceDiscounts(
		long companyId, long cpDefinitionId) {

		return commerceDiscountFinder.findByUnqualifiedProduct(
			companyId, cpDefinitionId, _getAssetCategoryIds(cpDefinitionId),
			_commercePricingClassLocalService.
				getCommercePricingClassByCPDefinition(cpDefinitionId));
	}

	@Override
	public List<CommerceDiscount> getUnqualifiedCommerceDiscounts(
		long companyId, String commerceDiscountTargetType) {

		return commerceDiscountFinder.findByUnqualifiedOrder(
			companyId, commerceDiscountTargetType);
	}

	@Override
	public int getValidCommerceDiscountsCount(
		long commerceAccountId, long[] commerceAccountGroupIds,
		long commerceChannelId, long commerceDiscountId) {

		return commerceDiscountFinder.countByValidCommerceDiscount(
			commerceAccountId, commerceAccountGroupIds, commerceChannelId,
			commerceDiscountId);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceDiscount incrementCommerceDiscountNumberOfUse(
			long commerceDiscountId)
		throws PortalException {

		CommerceDiscount commerceDiscount =
			commerceDiscountPersistence.findByPrimaryKey(commerceDiscountId);

		commerceDiscount.setNumberOfUse(commerceDiscount.getNumberOfUse() + 1);

		return commerceDiscountPersistence.update(commerceDiscount);
	}

	@Override
	public List<CommerceDiscount> searchByCommercePricingClassId(
		long commercePricingClassId, String title, int start, int end) {

		return commerceDiscountFinder.findByCommercePricingClassId(
			commercePricingClassId, title, start, end);
	}

	@Override
	public BaseModelSearchResult<CommerceDiscount> searchCommerceDiscounts(
			long companyId, long[] groupIds, String keywords, int status,
			int start, int end, Sort sort)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, groupIds, keywords, status, start, end, sort);

		return searchCommerceDiscounts(searchContext);
	}

	@Override
	public BaseModelSearchResult<CommerceDiscount> searchCommerceDiscounts(
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

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceDiscount updateCommerceDiscount(
			long commerceDiscountId, String title, String target,
			boolean useCouponCode, String couponCode, boolean usePercentage,
			BigDecimal maximumDiscountAmount, BigDecimal level1,
			BigDecimal level2, BigDecimal level3, BigDecimal level4,
			String limitationType, int limitationTimes, boolean active,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		return updateCommerceDiscount(
			commerceDiscountId, title, target, useCouponCode, couponCode,
			usePercentage, maximumDiscountAmount, StringPool.BLANK, level1,
			level2, level3, level4, limitationType, limitationTimes, 0, true,
			active, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceDiscount updateCommerceDiscount(
			long commerceDiscountId, String title, String target,
			boolean useCouponCode, String couponCode, boolean usePercentage,
			BigDecimal maximumDiscountAmount, String level, BigDecimal level1,
			BigDecimal level2, BigDecimal level3, BigDecimal level4,
			String limitationType, int limitationTimes,
			boolean rulesConjunction, boolean active, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());

		CommerceDiscount commerceDiscount =
			commerceDiscountPersistence.findByPrimaryKey(commerceDiscountId);

		validate(
			serviceContext.getCompanyId(), commerceDiscountId, title, target,
			useCouponCode, couponCode, limitationType);

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
		commerceDiscount.setLevel(level);
		commerceDiscount.setLevel1(level1);
		commerceDiscount.setLevel2(level2);
		commerceDiscount.setLevel3(level3);
		commerceDiscount.setLevel4(level4);
		commerceDiscount.setLimitationType(limitationType);
		commerceDiscount.setLimitationTimes(limitationTimes);
		commerceDiscount.setRulesConjunction(rulesConjunction);
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

		commerceDiscount = commerceDiscountPersistence.update(commerceDiscount);

		return startWorkflowInstance(
			user.getUserId(), commerceDiscount, serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceDiscount updateCommerceDiscount(
			long commerceDiscountId, String title, String target,
			boolean useCouponCode, String couponCode, boolean usePercentage,
			BigDecimal maximumDiscountAmount, String level, BigDecimal level1,
			BigDecimal level2, BigDecimal level3, BigDecimal level4,
			String limitationType, int limitationTimes,
			int limitationTimesPerAccount, boolean rulesConjunction,
			boolean active, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());

		CommerceDiscount commerceDiscount =
			commerceDiscountPersistence.findByPrimaryKey(commerceDiscountId);

		validate(
			serviceContext.getCompanyId(), commerceDiscountId, title, target,
			useCouponCode, couponCode, limitationType);

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
		commerceDiscount.setLevel(level);
		commerceDiscount.setLevel1(level1);
		commerceDiscount.setLevel2(level2);
		commerceDiscount.setLevel3(level3);
		commerceDiscount.setLevel4(level4);
		commerceDiscount.setLimitationType(limitationType);
		commerceDiscount.setLimitationTimes(limitationTimes);
		commerceDiscount.setLimitationTimesPerAccount(
			limitationTimesPerAccount);
		commerceDiscount.setRulesConjunction(rulesConjunction);
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

		commerceDiscount = commerceDiscountPersistence.update(commerceDiscount);

		return startWorkflowInstance(
			user.getUserId(), commerceDiscount, serviceContext);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #updateCommerceDiscountExternalReferenceCode(String, long)}
	 */
	@Deprecated
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceDiscount updateCommerceDiscountExternalReferenceCode(
			long commerceDiscountId, String externalReferenceCode)
		throws PortalException {

		return updateCommerceDiscountExternalReferenceCode(
			externalReferenceCode, commerceDiscountId);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceDiscount updateCommerceDiscountExternalReferenceCode(
			String externalReferenceCode, long commerceDiscountId)
		throws PortalException {

		CommerceDiscount commerceDiscount =
			commerceDiscountLocalService.getCommerceDiscount(
				commerceDiscountId);

		commerceDiscount.setExternalReferenceCode(externalReferenceCode);

		return commerceDiscountLocalService.updateCommerceDiscount(
			commerceDiscount);
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

			commerceDiscount.setActive(false);

			status = WorkflowConstants.STATUS_SCHEDULED;
		}

		if (status == WorkflowConstants.STATUS_APPROVED) {
			Date expirationDate = commerceDiscount.getExpirationDate();

			if ((expirationDate != null) && expirationDate.before(now)) {
				commerceDiscount.setExpirationDate(null);
			}

			commerceDiscount.setActive(true);
		}

		if (status == WorkflowConstants.STATUS_EXPIRED) {
			commerceDiscount.setActive(false);
			commerceDiscount.setExpirationDate(now);
		}

		commerceDiscount.setStatus(status);
		commerceDiscount.setStatusByUserId(user.getUserId());
		commerceDiscount.setStatusByUserName(user.getFullName());
		commerceDiscount.setStatusDate(serviceContext.getModifiedDate(now));

		return commerceDiscountPersistence.update(commerceDiscount);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #upsertCommerceDiscount(String, long, long, String, String,
	 *             boolean, String, boolean, BigDecimal, BigDecimal,
	 *             BigDecimal, BigDecimal, BigDecimal, String, int, boolean,
	 *             int, int, int, int, int, int, int, int, int, int, boolean,
	 *             ServiceContext)}
	 */
	@Deprecated
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceDiscount upsertCommerceDiscount(
			long userId, long commerceDiscountId, String title, String target,
			boolean useCouponCode, String couponCode, boolean usePercentage,
			BigDecimal maximumDiscountAmount, BigDecimal level1,
			BigDecimal level2, BigDecimal level3, BigDecimal level4,
			String limitationType, int limitationTimes, boolean active,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			String externalReferenceCode, boolean neverExpire,
			ServiceContext serviceContext)
		throws PortalException {

		return upsertCommerceDiscount(
			externalReferenceCode, userId, commerceDiscountId, title, target,
			useCouponCode, couponCode, usePercentage, maximumDiscountAmount,
			StringPool.BLANK, level1, level2, level3, level4, limitationType,
			limitationTimes, 0, true, active, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #upsertCommerceDiscount(String, long, long, String, String,
	 *             boolean, String, boolean, BigDecimal, String, BigDecimal,
	 *             BigDecimal, BigDecimal, BigDecimal, String, int, boolean,
	 *             boolean, int, int, int, int, int, int, int, int, int, int,
	 *             boolean, ServiceContext)}
	 */
	@Deprecated
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceDiscount upsertCommerceDiscount(
			long userId, long commerceDiscountId, String title, String target,
			boolean useCouponCode, String couponCode, boolean usePercentage,
			BigDecimal maximumDiscountAmount, String level, BigDecimal level1,
			BigDecimal level2, BigDecimal level3, BigDecimal level4,
			String limitationType, int limitationTimes,
			boolean rulesConjunction, boolean active, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			String externalReferenceCode, boolean neverExpire,
			ServiceContext serviceContext)
		throws PortalException {

		return upsertCommerceDiscount(
			externalReferenceCode, userId, commerceDiscountId, title, target,
			useCouponCode, couponCode, usePercentage, maximumDiscountAmount,
			level, level1, level2, level3, level4, limitationType,
			limitationTimes, rulesConjunction, active, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #upsertCommerceDiscount(String, long, long, String, String,
	 *             boolean, String, boolean, BigDecimal, String, BigDecimal,
	 *             BigDecimal, BigDecimal, BigDecimal, String, int, int,
	 *             boolean, boolean, int, int, int, int, int, int, int, int,
	 *             int, int, boolean, ServiceContext)}
	 */
	@Deprecated
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceDiscount upsertCommerceDiscount(
			long userId, long commerceDiscountId, String title, String target,
			boolean useCouponCode, String couponCode, boolean usePercentage,
			BigDecimal maximumDiscountAmount, String level, BigDecimal level1,
			BigDecimal level2, BigDecimal level3, BigDecimal level4,
			String limitationType, int limitationTimes,
			int limitationTimesPerAccount, boolean rulesConjunction,
			boolean active, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, String externalReferenceCode,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		return upsertCommerceDiscount(
			externalReferenceCode, userId, commerceDiscountId, title, target,
			useCouponCode, couponCode, usePercentage, maximumDiscountAmount,
			level, level1, level2, level3, level4, limitationType,
			limitationTimes, limitationTimesPerAccount, rulesConjunction,
			active, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceDiscount upsertCommerceDiscount(
			String externalReferenceCode, long userId, long commerceDiscountId,
			String title, String target, boolean useCouponCode,
			String couponCode, boolean usePercentage,
			BigDecimal maximumDiscountAmount, BigDecimal level1,
			BigDecimal level2, BigDecimal level3, BigDecimal level4,
			String limitationType, int limitationTimes, boolean active,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		return upsertCommerceDiscount(
			externalReferenceCode, userId, commerceDiscountId, title, target,
			useCouponCode, couponCode, usePercentage, maximumDiscountAmount,
			StringPool.BLANK, level1, level2, level3, level4, limitationType,
			limitationTimes, 0, true, active, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceDiscount upsertCommerceDiscount(
			String externalReferenceCode, long userId, long commerceDiscountId,
			String title, String target, boolean useCouponCode,
			String couponCode, boolean usePercentage,
			BigDecimal maximumDiscountAmount, String level, BigDecimal level1,
			BigDecimal level2, BigDecimal level3, BigDecimal level4,
			String limitationType, int limitationTimes,
			boolean rulesConjunction, boolean active, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		// Update

		if (commerceDiscountId > 0) {
			try {
				return commerceDiscountLocalService.updateCommerceDiscount(
					commerceDiscountId, title, target, useCouponCode,
					couponCode, usePercentage, maximumDiscountAmount, level,
					level1, level2, level3, level4, limitationType,
					limitationTimes, rulesConjunction, active, displayDateMonth,
					displayDateDay, displayDateYear, displayDateHour,
					displayDateMinute, expirationDateMonth, expirationDateDay,
					expirationDateYear, expirationDateHour,
					expirationDateMinute, neverExpire, serviceContext);
			}
			catch (NoSuchDiscountException noSuchDiscountException) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Unable to find discount with ID: " +
							commerceDiscountId,
						noSuchDiscountException);
				}
			}
		}

		if (!Validator.isBlank(externalReferenceCode)) {
			CommerceDiscount commerceDiscount =
				commerceDiscountPersistence.fetchByC_ERC(
					serviceContext.getCompanyId(), externalReferenceCode);

			if (commerceDiscount != null) {
				return commerceDiscountLocalService.updateCommerceDiscount(
					commerceDiscountId, title, target, useCouponCode,
					couponCode, usePercentage, maximumDiscountAmount, level,
					level1, level2, level3, level4, limitationType,
					limitationTimes, rulesConjunction, active, displayDateMonth,
					displayDateDay, displayDateYear, displayDateHour,
					displayDateMinute, expirationDateMonth, expirationDateDay,
					expirationDateYear, expirationDateHour,
					expirationDateMinute, neverExpire, serviceContext);
			}
		}

		// Add

		return commerceDiscountLocalService.addCommerceDiscount(
			externalReferenceCode, userId, title, target, useCouponCode,
			couponCode, usePercentage, maximumDiscountAmount, level, level1,
			level2, level3, level4, limitationType, limitationTimes,
			rulesConjunction, active, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceDiscount upsertCommerceDiscount(
			String externalReferenceCode, long userId, long commerceDiscountId,
			String title, String target, boolean useCouponCode,
			String couponCode, boolean usePercentage,
			BigDecimal maximumDiscountAmount, String level, BigDecimal level1,
			BigDecimal level2, BigDecimal level3, BigDecimal level4,
			String limitationType, int limitationTimes,
			int limitationTimesPerAccount, boolean rulesConjunction,
			boolean active, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire,
			ServiceContext serviceContext)
		throws PortalException {

		// Update

		if (commerceDiscountId > 0) {
			try {
				return commerceDiscountLocalService.updateCommerceDiscount(
					commerceDiscountId, title, target, useCouponCode,
					couponCode, usePercentage, maximumDiscountAmount, level,
					level1, level2, level3, level4, limitationType,
					limitationTimes, limitationTimesPerAccount,
					rulesConjunction, active, displayDateMonth, displayDateDay,
					displayDateYear, displayDateHour, displayDateMinute,
					expirationDateMonth, expirationDateDay, expirationDateYear,
					expirationDateHour, expirationDateMinute, neverExpire,
					serviceContext);
			}
			catch (NoSuchDiscountException noSuchDiscountException) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Unable to find discount with ID: " +
							commerceDiscountId,
						noSuchDiscountException);
				}
			}
		}

		if (!Validator.isBlank(externalReferenceCode)) {
			CommerceDiscount commerceDiscount =
				commerceDiscountPersistence.fetchByC_ERC(
					serviceContext.getCompanyId(), externalReferenceCode);

			if (commerceDiscount != null) {
				return commerceDiscountLocalService.updateCommerceDiscount(
					commerceDiscountId, title, target, useCouponCode,
					couponCode, usePercentage, maximumDiscountAmount, level,
					level1, level2, level3, level4, limitationType,
					limitationTimes, limitationTimesPerAccount,
					rulesConjunction, active, displayDateMonth, displayDateDay,
					displayDateYear, displayDateHour, displayDateMinute,
					expirationDateMonth, expirationDateDay, expirationDateYear,
					expirationDateHour, expirationDateMinute, neverExpire,
					serviceContext);
			}
		}

		// Add

		return commerceDiscountLocalService.addCommerceDiscount(
			externalReferenceCode, userId, title, target, useCouponCode,
			couponCode, usePercentage, maximumDiscountAmount, level, level1,
			level2, level3, level4, limitationType, limitationTimes,
			limitationTimesPerAccount, rulesConjunction, active,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	protected SearchContext buildSearchContext(
		long companyId, long[] groupIds, String keywords, int status, int start,
		int end, Sort sort) {

		SearchContext searchContext = new SearchContext();

		searchContext.setAttributes(
			HashMapBuilder.<String, Serializable>put(
				CommerceDiscountIndexer.FIELD_GROUP_IDS, groupIds
			).put(
				Field.ENTRY_CLASS_PK, keywords
			).put(
				Field.STATUS, status
			).put(
				Field.TITLE, keywords
			).put(
				"params",
				LinkedHashMapBuilder.<String, Object>put(
					"keywords", keywords
				).build()
			).put(
				"skipCommerceAccountGroupValidation", true
			).build());

		searchContext.setCompanyId(companyId);
		searchContext.setEnd(end);

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

	protected CommerceDiscount startWorkflowInstance(
			long userId, CommerceDiscount commerceDiscount,
			ServiceContext serviceContext)
		throws PortalException {

		Map<String, Serializable> workflowContext = new HashMap<>();

		return WorkflowHandlerRegistryUtil.startWorkflowInstance(
			commerceDiscount.getCompanyId(), 0L, userId,
			CommerceDiscount.class.getName(),
			commerceDiscount.getCommerceDiscountId(), commerceDiscount,
			serviceContext, workflowContext);
	}

	protected void validate(
			long companyId, long commerceDiscountId, String title,
			String target, boolean useCouponCode, String couponCode,
			String limitationType)
		throws PortalException {

		if (Validator.isNull(title)) {
			throw new CommerceDiscountTitleException();
		}

		CommerceDiscountTarget commerceDiscountTarget =
			_commerceDiscountTargetRegistry.getCommerceDiscountTarget(target);

		if (commerceDiscountTarget == null) {
			throw new CommerceDiscountTargetException();
		}

		if (useCouponCode) {
			if (Validator.isNull(couponCode)) {
				throw new CommerceDiscountCouponCodeException();
			}

			CommerceDiscount commerceDiscount =
				commerceDiscountPersistence.fetchByC_C_First(
					companyId, couponCode,
					new CommerceDiscountCreateDateComparator(true));

			if (((commerceDiscountId <= 0) && (commerceDiscount != null)) ||
				((commerceDiscount != null) &&
				 (commerceDiscountId !=
					 commerceDiscount.getCommerceDiscountId()))) {

				throw new DuplicateCommerceDiscountException();
			}
		}

		if (Validator.isNull(limitationType)) {
			throw new CommerceDiscountLimitationTypeException();
		}
	}

	protected void validateExternalReferenceCode(
			String externalReferenceCode, long companyId)
		throws PortalException {

		if (Validator.isNull(externalReferenceCode)) {
			return;
		}

		CommerceDiscount commerceDiscount =
			commerceDiscountPersistence.fetchByC_ERC(
				companyId, externalReferenceCode);

		if (commerceDiscount != null) {
			throw new DuplicateCommerceDiscountException(
				"There is another commerce discount with external reference " +
					"code " + externalReferenceCode);
		}
	}

	private long[] _getAssetCategoryIds(long cpDefinitionId) {
		try {
			AssetEntry assetEntry = _assetEntryLocalService.getEntry(
				CPDefinition.class.getName(), cpDefinitionId);

			Set<AssetCategory> assetCategories = new HashSet<>();

			for (AssetCategory assetCategory : assetEntry.getCategories()) {
				assetCategories.add(assetCategory);
				assetCategories.addAll(assetCategory.getAncestors());
			}

			Stream<AssetCategory> stream = assetCategories.stream();

			LongStream longStream = stream.mapToLong(
				AssetCategory::getCategoryId);

			return longStream.toArray();
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}

		return new long[0];
	}

	private static final String[] _SELECTED_FIELD_NAMES = {
		Field.ENTRY_CLASS_PK, Field.COMPANY_ID, Field.UID
	};

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceDiscountLocalServiceImpl.class);

	@ServiceReference(type = AssetEntryLocalService.class)
	private AssetEntryLocalService _assetEntryLocalService;

	@ServiceReference(type = CommerceDiscountTargetRegistry.class)
	private CommerceDiscountTargetRegistry _commerceDiscountTargetRegistry;

	@ServiceReference(type = CommercePricingClassLocalService.class)
	private CommercePricingClassLocalService _commercePricingClassLocalService;

}