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
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.base.CPInstanceServiceBaseImpl;
import com.liferay.commerce.product.service.permission.CPDefinitionPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.List;
import java.util.Map;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CPInstanceServiceImpl extends CPInstanceServiceBaseImpl {

	@Override
	public CPInstance addCPInstance(
			long cpDefinitionId, String sku, String gtin,
			String manufacturerPartNumber, boolean purchasable,
			String ddmContent, boolean published, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		CPDefinitionPermission.check(
			getPermissionChecker(), cpDefinitionId,
			CPActionKeys.ADD_COMMERCE_PRODUCT_INSTANCE);

		return cpInstanceLocalService.addCPInstance(
			cpDefinitionId, sku, gtin, manufacturerPartNumber, purchasable,
			ddmContent, published, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	@Override
	public CPInstance addCPInstance(
			long cpDefinitionId, String sku, String gtin,
			String manufacturerPartNumber, boolean purchasable,
			String ddmContent, double width, double height, double depth,
			double weight, BigDecimal cost, BigDecimal price,
			BigDecimal promoPrice, boolean published, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		CPDefinitionPermission.check(
			getPermissionChecker(), cpDefinitionId,
			CPActionKeys.ADD_COMMERCE_PRODUCT_INSTANCE);

		return cpInstanceLocalService.addCPInstance(
			cpDefinitionId, sku, gtin, manufacturerPartNumber, purchasable,
			ddmContent, width, height, depth, weight, cost, price, promoPrice,
			published, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, serviceContext);
	}

	@Override
	public void buildCPInstances(
			long cpDefinitionId, ServiceContext serviceContext)
		throws PortalException {

		CPDefinitionPermission.check(
			getPermissionChecker(), cpDefinitionId,
			CPActionKeys.ADD_COMMERCE_PRODUCT_INSTANCE);

		cpInstanceLocalService.buildCPInstances(cpDefinitionId, serviceContext);
	}

	@Override
	public CPInstance deleteCPInstance(CPInstance cpInstance)
		throws PortalException {

		CPDefinitionPermission.checkCPInstance(
			getPermissionChecker(), cpInstance,
			CPActionKeys.DELETE_COMMERCE_PRODUCT_INSTANCE);

		return cpInstanceLocalService.deleteCPInstance(cpInstance);
	}

	@Override
	public CPInstance deleteCPInstance(long cpInstanceId)
		throws PortalException {

		CPDefinitionPermission.checkCPInstance(
			getPermissionChecker(), cpInstanceId,
			CPActionKeys.DELETE_COMMERCE_PRODUCT_INSTANCE);

		return cpInstanceLocalService.deleteCPInstance(cpInstanceId);
	}

	@Override
	public CPInstance fetchCPInstance(long cpInstanceId)
		throws PortalException {

		CPInstance cpInstance = cpInstanceLocalService.fetchCPInstance(
			cpInstanceId);

		if (cpInstance != null) {
			CPDefinitionPermission.checkCPInstance(
				getPermissionChecker(), cpInstance, ActionKeys.VIEW);
		}

		return cpInstance;
	}

	@Override
	public List<CPInstance> getCPDefinitionInstances(
			long cpDefinitionId, int start, int end)
		throws PortalException {

		CPDefinitionPermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.VIEW);

		return cpInstanceLocalService.getCPDefinitionInstances(
			cpDefinitionId, start, end);
	}

	@Override
	public List<CPInstance> getCPDefinitionInstances(
			long cpDefinitionId, int status, int start, int end,
			OrderByComparator<CPInstance> orderByComparator)
		throws PortalException {

		CPDefinitionPermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.VIEW);

		return cpInstanceLocalService.getCPDefinitionInstances(
			cpDefinitionId, status, start, end, orderByComparator);
	}

	@Override
	public int getCPDefinitionInstancesCount(long cpDefinitionId, int status)
		throws PortalException {

		CPDefinitionPermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.VIEW);

		return cpInstanceLocalService.getCPDefinitionInstancesCount(
			cpDefinitionId, status);
	}

	@Override
	public CPInstance getCPInstance(long cpInstanceId) throws PortalException {
		CPDefinitionPermission.checkCPInstance(
			getPermissionChecker(), cpInstanceId, ActionKeys.VIEW);

		return cpInstanceLocalService.getCPInstance(cpInstanceId);
	}

	@Override
	public List<CPInstance> getCPInstances(
			long groupId, int status, int start, int end,
			OrderByComparator<CPInstance> orderByComparator)
		throws PortalException {

		return cpInstanceLocalService.getCPInstances(
			groupId, status, start, end, orderByComparator);
	}

	@Override
	public int getCPInstancesCount(long groupId, int status)
		throws PortalException {

		return cpInstanceLocalService.getCPInstancesCount(groupId, status);
	}

	@Override
	public Hits search(SearchContext searchContext) {
		return cpInstanceLocalService.search(searchContext);
	}

	@Override
	public BaseModelSearchResult<CPInstance> searchCPDefinitionInstances(
			long companyId, long groupId, long cpDefinitionId, String keywords,
			int status, int start, int end, Sort sort)
		throws PortalException {

		return cpInstanceLocalService.searchCPDefinitionInstances(
			companyId, groupId, cpDefinitionId, keywords, status, start, end,
			sort);
	}

	@Override
	public BaseModelSearchResult<CPInstance> searchCPInstances(
			long companyId, long groupId, String keywords, int status,
			int start, int end, Sort sort)
		throws PortalException {

		return cpInstanceLocalService.searchCPInstances(
			companyId, groupId, keywords, status, start, end, sort);
	}

	@Override
	public BaseModelSearchResult<CPInstance> searchCPInstances(
			SearchContext searchContext)
		throws PortalException {

		return cpInstanceLocalService.searchCPInstances(searchContext);
	}

	@Override
	public CPInstance updateCPInstance(
			long cpInstanceId, String sku, String gtin,
			String manufacturerPartNumber, boolean purchasable,
			boolean published, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire,
			ServiceContext serviceContext)
		throws PortalException {

		CPDefinitionPermission.checkCPInstance(
			getPermissionChecker(), cpInstanceId,
			CPActionKeys.UPDATE_COMMERCE_PRODUCT_INSTANCE);

		return cpInstanceLocalService.updateCPInstance(
			cpInstanceId, sku, gtin, manufacturerPartNumber, purchasable,
			published, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, serviceContext);
	}

	@Override
	public CPInstance updateCPInstance(
			long cpInstanceId, String sku, String gtin,
			String manufacturerPartNumber, boolean purchasable, double width,
			double height, double depth, double weight, BigDecimal cost,
			BigDecimal price, BigDecimal promoPrice, boolean published,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		CPDefinitionPermission.checkCPInstance(
			getPermissionChecker(), cpInstanceId,
			CPActionKeys.UPDATE_COMMERCE_PRODUCT_INSTANCE);

		return cpInstanceLocalService.updateCPInstance(
			cpInstanceId, sku, gtin, manufacturerPartNumber, purchasable, width,
			height, depth, weight, cost, price, promoPrice, published,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	@Override
	public CPInstance updatePricingInfo(
			long cpInstanceId, BigDecimal cost, BigDecimal price,
			BigDecimal promoPrice, ServiceContext serviceContext)
		throws PortalException {

		CPDefinitionPermission.checkCPInstance(
			getPermissionChecker(), cpInstanceId,
			CPActionKeys.UPDATE_COMMERCE_PRODUCT_INSTANCE);

		return cpInstanceLocalService.updatePricingInfo(
			cpInstanceId, cost, price, promoPrice, serviceContext);
	}

	@Override
	public CPInstance updateShippingInfo(
			long cpInstanceId, double width, double height, double depth,
			double weight, ServiceContext serviceContext)
		throws PortalException {

		CPDefinitionPermission.checkCPInstance(
			getPermissionChecker(), cpInstanceId,
			CPActionKeys.UPDATE_COMMERCE_PRODUCT_INSTANCE);

		return cpInstanceLocalService.updateShippingInfo(
			cpInstanceId, width, height, depth, weight, serviceContext);
	}

	@Override
	public CPInstance updateStatus(
			long userId, long cpInstanceId, int status,
			ServiceContext serviceContext,
			Map<String, Serializable> workflowContext)
		throws PortalException {

		CPDefinitionPermission.checkCPInstance(
			getPermissionChecker(), cpInstanceId,
			CPActionKeys.UPDATE_COMMERCE_PRODUCT_INSTANCE);

		return cpInstanceLocalService.updateStatus(
			userId, cpInstanceId, status, serviceContext, workflowContext);
	}

}