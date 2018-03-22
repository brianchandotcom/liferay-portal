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

import com.liferay.commerce.product.constants.CPActionKeys;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.base.CPDefinitionServiceBaseImpl;
import com.liferay.commerce.product.service.permission.CPDefinitionPermission;
import com.liferay.commerce.product.service.permission.CPPermission;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.Serializable;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CPDefinitionServiceImpl extends CPDefinitionServiceBaseImpl {

	@Override
	public CPDefinition addCPDefinition(
			Map<Locale, String> titleMap,
			Map<Locale, String> shortDescriptionMap,
			Map<Locale, String> descriptionMap, Map<Locale, String> urlTitleMap,
			Map<Locale, String> metaTitleMap,
			Map<Locale, String> metaKeywordsMap,
			Map<Locale, String> metaDescriptionMap, String productTypeName,
			boolean ignoreSKUCombinations, boolean shippable,
			boolean freeShipping, boolean shipSeparately,
			double shippingExtraPrice, double width, double height,
			double depth, double weight, String ddmStructureKey,
			boolean published, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire,
			ServiceContext serviceContext)
		throws PortalException {

		CPPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			CPActionKeys.ADD_COMMERCE_PRODUCT_DEFINITION);

		return cpDefinitionLocalService.addCPDefinition(
			titleMap, shortDescriptionMap, descriptionMap, urlTitleMap,
			metaTitleMap, metaKeywordsMap, metaDescriptionMap, productTypeName,
			ignoreSKUCombinations, shippable, freeShipping, shipSeparately,
			shippingExtraPrice, width, height, depth, weight, ddmStructureKey,
			published, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, serviceContext);
	}

	@Override
	public CPDefinition addCPDefinition(
			Map<Locale, String> titleMap,
			Map<Locale, String> shortDescriptionMap,
			Map<Locale, String> descriptionMap, Map<Locale, String> urlTitleMap,
			Map<Locale, String> metaTitleMap,
			Map<Locale, String> metaKeywordsMap,
			Map<Locale, String> metaDescriptionMap, String productTypeName,
			boolean ignoreSKUCombinations, String ddmStructureKey,
			boolean published, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire,
			ServiceContext serviceContext)
		throws PortalException {

		CPPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			CPActionKeys.ADD_COMMERCE_PRODUCT_DEFINITION);

		return cpDefinitionLocalService.addCPDefinition(
			titleMap, shortDescriptionMap, descriptionMap, urlTitleMap,
			metaTitleMap, metaKeywordsMap, metaDescriptionMap, productTypeName,
			ignoreSKUCombinations, ddmStructureKey, published, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	@Override
	public void deleteAssetCategoryCPDefinition(
			long cpDefinitionId, long categoryId)
		throws PortalException {

		CPDefinitionPermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.UPDATE);

		cpDefinitionLocalService.deleteAssetCategoryCPDefinition(
			cpDefinitionId, categoryId);
	}

	@Override
	public CPDefinition deleteCPDefinition(long cpDefinitionId)
		throws PortalException {

		CPDefinitionPermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.DELETE);

		return cpDefinitionLocalService.deleteCPDefinition(cpDefinitionId);
	}

	@Override
	public CPDefinition fetchCPDefinition(long cpDefinitionId)
		throws PortalException {

		CPDefinition cpDefinition = cpDefinitionLocalService.fetchCPDefinition(
			cpDefinitionId);

		if (cpDefinition != null) {
			CPDefinitionPermission.check(
				getPermissionChecker(), cpDefinition, ActionKeys.VIEW);
		}

		return cpDefinition;
	}

	@Override
	public CPDefinition getCPDefinition(long cpDefinitionId)
		throws PortalException {

		CPDefinitionPermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.VIEW);

		return cpDefinitionLocalService.getCPDefinition(cpDefinitionId);
	}

	@Override
	public List<CPDefinition> getCPDefinitions(
			long groupId, String productTypeName, String languageId, int status,
			int start, int end,
			OrderByComparator<CPDefinition> orderByComparator)
		throws PortalException {

		QueryDefinition<CPDefinition> queryDefinition = new QueryDefinition<>(
			status, start, end, orderByComparator);

		if (status == WorkflowConstants.STATUS_ANY) {
			queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);
		}

		return cpDefinitionFinder.filterFindByG_P_S(
			groupId, productTypeName, languageId, queryDefinition);
	}

	@Override
	public List<CPDefinition> getCPDefinitionsByCategoryId(
		long categoryId, int start, int end) {

		return cpDefinitionLocalService.getCPDefinitionsByCategoryId(
			categoryId, start, end);
	}

	@Override
	public int getCPDefinitionsCount(
		long groupId, String productTypeName, String languageId, int status) {

		QueryDefinition<CPDefinition> queryDefinition = new QueryDefinition<>(
			status);

		if (status == WorkflowConstants.STATUS_ANY) {
			queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);
		}

		return cpDefinitionFinder.filterCountByG_P_S(
			groupId, productTypeName, languageId, queryDefinition);
	}

	@Override
	public int getCPDefinitionsCountByCategoryId(long categoryId) {
		return cpDefinitionLocalService.getCPDefinitionsCountByCategoryId(
			categoryId);
	}

	@Override
	public CPAttachmentFileEntry getDefaultImage(long cpDefinitionId)
		throws PortalException {

		CPDefinitionPermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.VIEW);

		return cpDefinitionLocalService.getDefaultImage(cpDefinitionId);
	}

	@Override
	public String getLayoutUuid(long cpDefinitionId) throws PortalException {
		CPDefinitionPermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.VIEW);

		return cpDefinitionLocalService.getLayoutUuid(cpDefinitionId);
	}

	@Override
	public String getUrlTitleMapAsXML(long cpDefinitionId)
		throws PortalException {

		CPDefinitionPermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.VIEW);

		return cpDefinitionLocalService.getUrlTitleMapAsXML(cpDefinitionId);
	}

	@Override
	public CPDefinition moveCPDefinitionToTrash(long cpDefinitionId)
		throws PortalException {

		CPDefinitionPermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.DELETE);

		return cpDefinitionLocalService.moveCPDefinitionToTrash(
			getUserId(), cpDefinitionId);
	}

	@Override
	public void restoreCPDefinitionFromTrash(long cpDefinitionId)
		throws PortalException {

		CPDefinitionPermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.DELETE);

		cpDefinitionLocalService.restoreCPDefinitionFromTrash(
			getUserId(), cpDefinitionId);
	}

	@Override
	public Hits search(SearchContext searchContext) {
		return cpDefinitionLocalService.search(searchContext);
	}

	@Override
	public BaseModelSearchResult<CPDefinition> searchCPDefinitions(
			long companyId, long groupId, String keywords, int status,
			int start, int end, Sort sort)
		throws PortalException {

		return cpDefinitionLocalService.searchCPDefinitions(
			companyId, groupId, keywords, status, start, end, sort);
	}

	@Override
	public CPDefinition updateCPDefinition(
			long cpDefinitionId, Map<Locale, String> titleMap,
			Map<Locale, String> shortDescriptionMap,
			Map<Locale, String> descriptionMap, Map<Locale, String> urlTitleMap,
			Map<Locale, String> metaTitleMap,
			Map<Locale, String> metaKeywordsMap,
			Map<Locale, String> metaDescriptionMap,
			boolean ignoreSKUCombinations, boolean shippable,
			boolean freeShipping, boolean shipSeparately,
			double shippingExtraPrice, double width, double height,
			double depth, double weight, String ddmStructureKey,
			boolean published, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire,
			ServiceContext serviceContext)
		throws PortalException {

		CPDefinitionPermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.UPDATE);

		return cpDefinitionLocalService.updateCPDefinition(
			cpDefinitionId, titleMap, shortDescriptionMap, descriptionMap,
			urlTitleMap, metaTitleMap, metaKeywordsMap, metaDescriptionMap,
			ignoreSKUCombinations, shippable, freeShipping, shipSeparately,
			shippingExtraPrice, width, height, depth, weight, ddmStructureKey,
			published, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, serviceContext);
	}

	@Override
	public CPDefinition updateCPDefinition(
			long cpDefinitionId, Map<Locale, String> titleMap,
			Map<Locale, String> shortDescriptionMap,
			Map<Locale, String> descriptionMap, Map<Locale, String> urlTitleMap,
			Map<Locale, String> metaTitleMap,
			Map<Locale, String> metaKeywordsMap,
			Map<Locale, String> metaDescriptionMap,
			boolean ignoreSKUCombinations, String ddmStructureKey,
			boolean published, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire,
			ServiceContext serviceContext)
		throws PortalException {

		CPDefinitionPermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.UPDATE);

		return cpDefinitionLocalService.updateCPDefinition(
			cpDefinitionId, titleMap, shortDescriptionMap, descriptionMap,
			urlTitleMap, metaTitleMap, metaKeywordsMap, metaDescriptionMap,
			ignoreSKUCombinations, ddmStructureKey, published, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	@Override
	public CPDefinition updateCPDefinitionCategorization(
			long cpDefinitionId, ServiceContext serviceContext)
		throws PortalException {

		CPDefinitionPermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.UPDATE);

		return cpDefinitionLocalService.updateCPDefinitionCategorization(
			cpDefinitionId, serviceContext);
	}

	@Override
	public CPDefinition updateCPDefinitionIgnoreSKUCombinations(
			long cpDefinitionId, boolean ignoreSKUCombinations,
			ServiceContext serviceContext)
		throws PortalException {

		CPDefinitionPermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.UPDATE);

		return cpDefinitionLocalService.updateCPDefinitionIgnoreSKUCombinations(
			cpDefinitionId, ignoreSKUCombinations, serviceContext);
	}

	@Override
	public void updateCPDisplayLayout(
			long cpDefinitionId, String layoutUuid,
			ServiceContext serviceContext)
		throws PortalException {

		CPDefinitionPermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.UPDATE);

		cpDisplayLayoutLocalService.addCPDisplayLayout(
			CPDefinition.class, cpDefinitionId, layoutUuid, serviceContext);
	}

	@Override
	public CPDefinition updateShippingInfo(
			long cpDefinitionId, boolean shippable, boolean freeShipping,
			boolean shipSeparately, double shippingExtraPrice, double width,
			double height, double depth, double weight,
			ServiceContext serviceContext)
		throws PortalException {

		CPDefinitionPermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.UPDATE);

		return cpDefinitionLocalService.updateShippingInfo(
			cpDefinitionId, shippable, freeShipping, shipSeparately,
			shippingExtraPrice, width, height, depth, weight, serviceContext);
	}

	@Override
	public CPDefinition updateStatus(
			long userId, long cpDefinitionId, int status,
			ServiceContext serviceContext,
			Map<String, Serializable> workflowContext)
		throws PortalException {

		CPDefinitionPermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.UPDATE);

		return cpDefinitionLocalService.updateStatus(
			userId, cpDefinitionId, status, serviceContext, workflowContext);
	}

}