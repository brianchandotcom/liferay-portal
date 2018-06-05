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

package com.liferay.commerce.product.rule.test.util;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyLocalServiceUtil;
import com.liferay.commerce.product.constants.CPRuleConstants;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPRule;
import com.liferay.commerce.product.service.CPDefinitionLocalServiceUtil;
import com.liferay.commerce.product.service.CPRuleAssetCategoryRelLocalServiceUtil;
import com.liferay.commerce.product.service.CPRuleLocalServiceUtil;
import com.liferay.commerce.product.service.CPRuleUserSegmentRelLocalServiceUtil;
import com.liferay.commerce.product.util.CPRulesThreadLocal;
import com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry;
import com.liferay.commerce.user.segment.service.CommerceUserSegmentEntryLocalServiceUtil;
import com.liferay.commerce.user.segment.test.util.CommerceUserSegmentTestUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;

import java.util.List;

/**
 * @author Luca Pellizzon
 */
public class CPRuleTestUtil {

	public static AssetCategory addAssetCategoryToCPRule(CPRule cpRule)
		throws Exception {

		long groupId = cpRule.getGroupId();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		AssetVocabulary assetVocabulary =
			AssetVocabularyLocalServiceUtil.addVocabulary(
				serviceContext.getUserId(), groupId,
				RandomTestUtil.randomString(), serviceContext);

		AssetCategory assetCategory = AssetCategoryLocalServiceUtil.addCategory(
			serviceContext.getUserId(), groupId, RandomTestUtil.randomString(),
			assetVocabulary.getVocabularyId(), serviceContext);

		CPRuleAssetCategoryRelLocalServiceUtil.addCPRuleAssetCategoryRel(
			cpRule.getCPRuleId(), assetCategory.getCategoryId(),
			serviceContext);

		return assetCategory;
	}

	public static CPRule addCategoryCPRule(long groupId) throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		return CPRuleLocalServiceUtil.addCPRule(
			RandomTestUtil.randomString(), true,
			CPRuleConstants.TYPE_ASSET_CATEGORY, serviceContext);
	}

	public static void addCategoryToCPDefinition(
			CPDefinition cPDefinition, long categoryId)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(cPDefinition.getGroupId());

		serviceContext.setAssetCategoryIds(new long[] {categoryId});

		CPDefinitionLocalServiceUtil.updateCPDefinitionCategorization(
			cPDefinition.getCPDefinitionId(), serviceContext);
	}

	public static CPRule addUserSegmentCPRule(long groupId, long userId)
		throws Exception {

		CPRule cpRule = addCategoryCPRule(groupId);

		CommerceUserSegmentEntry commerceUserSegmentEntry =
			CommerceUserSegmentTestUtil.addUserCommerceUserSegmentEntry(
				groupId, userId);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		CPRuleUserSegmentRelLocalServiceUtil.addCPRuleUserSegmentRel(
			cpRule.getCPRuleId(),
			commerceUserSegmentEntry.getCommerceUserSegmentEntryId(),
			serviceContext);

		CommerceUserSegmentEntry defaultCommerceUserSegmentEntry =
			CommerceUserSegmentEntryLocalServiceUtil.
				getCommerceUserSegmentEntry(groupId, "user");

		CPRuleUserSegmentRelLocalServiceUtil.addCPRuleUserSegmentRel(
			cpRule.getCPRuleId(),
			defaultCommerceUserSegmentEntry.getCommerceUserSegmentEntryId(),
			serviceContext);

		return cpRule;
	}

	public static void setCPRulesThreadLocal(
			long groupId, long organizationId, long userId)
		throws PortalException {

		long[] commerceUserSegmentEntryIds =
			CommerceUserSegmentEntryLocalServiceUtil.
				getCommerceUserSegmentEntryIds(groupId, organizationId, userId);

		List<CPRule> cpRules = CPRuleLocalServiceUtil.getCPRules(
			groupId, commerceUserSegmentEntryIds);

		CPRulesThreadLocal.setCPRules(cpRules);
	}

}