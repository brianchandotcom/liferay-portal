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

import com.liferay.commerce.product.exception.NoSuchCPDefinitionException;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.base.CPInstanceServiceBaseImpl;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.math.BigDecimal;

import java.util.List;
import java.util.Map;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CPInstanceServiceImpl extends CPInstanceServiceBaseImpl {

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	@Override
	public CPInstance addCPInstance(
			long cpDefinitionId, long groupId, String sku, String gtin,
			String manufacturerPartNumber, boolean purchasable,
			Map<Long, List<Long>>
				cpDefinitionOptionRelIdCPDefinitionOptionValueRelIds,
			boolean published, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire,
			ServiceContext serviceContext)
		throws PortalException {

		return cpInstanceService.addCPInstance(
			cpDefinitionId, groupId, sku, gtin, manufacturerPartNumber,
			purchasable, cpDefinitionOptionRelIdCPDefinitionOptionValueRelIds,
			published, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, null, false, null, 0, 0, 0, 0,
			serviceContext);
	}

	@Override
	public CPInstance addCPInstance(
			long cpDefinitionId, long groupId, String sku, String gtin,
			String manufacturerPartNumber, boolean purchasable,
			Map<Long, List<Long>>
				cpDefinitionOptionRelIdCPDefinitionOptionValueRelIds,
			boolean published, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire, String unspsc,
			boolean discontinued, String replacementCPInstanceUuid,
			long replacementCProductId, int discontinuedDateMonth,
			int discontinuedDateDay, int discontinuedDateYear,
			ServiceContext serviceContext)
		throws PortalException {

		_checkCommerceCatalogByCPDefinitionId(
			cpDefinitionId, ActionKeys.UPDATE);

		CPDefinition cpDefinition = cpDefinitionLocalService.getCPDefinition(
			cpDefinitionId);

		return cpInstanceLocalService.addCPInstance(
			StringPool.BLANK, cpDefinitionId, groupId, sku, gtin,
			manufacturerPartNumber, purchasable,
			cpDefinitionOptionRelIdCPDefinitionOptionValueRelIds,
			cpDefinition.getWidth(), cpDefinition.getHeight(),
			cpDefinition.getDepth(), cpDefinition.getWeight(), BigDecimal.ZERO,
			BigDecimal.ZERO, BigDecimal.ZERO, published, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire, false, false,
			1, StringPool.BLANK, null, 0, false, 0, StringPool.BLANK, null, 0,
			unspsc, discontinued, replacementCPInstanceUuid,
			replacementCProductId, discontinuedDateMonth, discontinuedDateDay,
			discontinuedDateYear, serviceContext);
	}

	@Override
	public CPInstance addOrUpdateCPInstance(
			String externalReferenceCode, long cpDefinitionId, long groupId,
			String sku, String gtin, String manufacturerPartNumber,
			boolean purchasable, String json, double width, double height,
			double depth, double weight, BigDecimal price,
			BigDecimal promoPrice, BigDecimal cost, boolean published,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, String unspsc, boolean discontinued,
			String replacementCPInstanceUuid, long replacementCProductId,
			int discontinuedDateMonth, int discontinuedDateDay,
			int discontinuedDateYear, ServiceContext serviceContext)
		throws PortalException {

		_checkCommerceCatalog(groupId, ActionKeys.UPDATE);

		return cpInstanceLocalService.addOrUpdateCPInstance(
			externalReferenceCode, cpDefinitionId, groupId, sku, gtin,
			manufacturerPartNumber, purchasable, json, width, height, depth,
			weight, price, promoPrice, cost, published, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire, unspsc,
			discontinued, replacementCPInstanceUuid, replacementCProductId,
			discontinuedDateMonth, discontinuedDateDay, discontinuedDateYear,
			serviceContext);
	}

	@Override
	public void buildCPInstances(
			long cpDefinitionId, ServiceContext serviceContext)
		throws PortalException {

		_checkCommerceCatalogByCPDefinitionId(
			cpDefinitionId, ActionKeys.UPDATE);

		cpInstanceLocalService.buildCPInstances(cpDefinitionId, serviceContext);
	}

	@Override
	public void deleteCPInstance(long cpInstanceId) throws PortalException {
		CPInstance cpInstance = cpInstanceService.getCPInstance(cpInstanceId);

		_checkCommerceCatalogByCPDefinitionId(
			cpInstance.getCPDefinitionId(), ActionKeys.UPDATE);

		cpInstanceLocalService.deleteCPInstance(cpInstance);
	}

	@Override
	public CPInstance fetchByExternalReferenceCode(
			String externalReferenceCode, long companyId)
		throws PortalException {

		CPInstance cpInstance =
			cpInstanceLocalService.fetchByExternalReferenceCode(
				externalReferenceCode, companyId);

		if (cpInstance != null) {
			_checkCommerceCatalogByCPDefinitionId(
				cpInstance.getCPDefinitionId(), ActionKeys.VIEW);
		}

		return cpInstance;
	}

	@Override
	public CPInstance fetchCPInstance(long cpInstanceId)
		throws PortalException {

		CPInstance cpInstance = cpInstanceLocalService.fetchCPInstance(
			cpInstanceId);

		if (cpInstance != null) {
			_checkCommerceCatalogByCPDefinitionId(
				cpInstance.getCPDefinitionId(), ActionKeys.VIEW);
		}

		return cpInstance;
	}

	@Override
	public CPInstance fetchCProductInstance(
			long cProductId, String cpInstanceUuid)
		throws PortalException {

		CProduct cProduct = cProductLocalService.fetchCProduct(cProductId);

		if (cProduct == null) {
			return null;
		}

		_checkCommerceCatalogByCPDefinitionId(
			cProduct.getPublishedCPDefinitionId(), ActionKeys.VIEW);

		return cpInstanceLocalService.fetchCProductInstance(
			cProductId, cpInstanceUuid);
	}

	@Override
	public List<CPInstance> getCPDefinitionInstances(
			long cpDefinitionId, int status, int start, int end,
			OrderByComparator<CPInstance> orderByComparator)
		throws PortalException {

		_checkCommerceCatalogByCPDefinitionId(cpDefinitionId, ActionKeys.VIEW);

		return cpInstanceLocalService.getCPDefinitionInstances(
			cpDefinitionId, status, start, end, orderByComparator);
	}

	@Override
	public int getCPDefinitionInstancesCount(long cpDefinitionId, int status)
		throws PortalException {

		_checkCommerceCatalogByCPDefinitionId(cpDefinitionId, ActionKeys.VIEW);

		return cpInstanceLocalService.getCPDefinitionInstancesCount(
			cpDefinitionId, status);
	}

	@Override
	public CPInstance getCPInstance(long cpInstanceId) throws PortalException {
		CPInstance cpInstance = cpInstanceLocalService.getCPInstance(
			cpInstanceId);

		_checkCommerceCatalogByCPDefinitionId(
			cpInstance.getCPDefinitionId(), ActionKeys.VIEW);

		return cpInstance;
	}

	@Override
	public List<CPInstance> getCPInstances(
			long groupId, int status, int start, int end,
			OrderByComparator<CPInstance> orderByComparator)
		throws PortalException {

		_checkCommerceCatalog(groupId, ActionKeys.VIEW);

		return cpInstanceLocalService.getCPInstances(
			groupId, status, start, end, orderByComparator);
	}

	@Override
	public int getCPInstancesCount(long groupId, int status)
		throws PortalException {

		_checkCommerceCatalog(groupId, ActionKeys.VIEW);

		return cpInstanceLocalService.getCPInstancesCount(groupId, status);
	}

	@Override
	public CPInstance getCProductInstance(
			long cProductId, String cpInstanceUuid)
		throws PortalException {

		CPInstance cpInstance = cpInstanceLocalService.getCProductInstance(
			cProductId, cpInstanceUuid);

		_checkCommerceCatalogByCPDefinitionId(
			cpInstance.getCPDefinitionId(), ActionKeys.VIEW);

		return cpInstance;
	}

	@Override
	public BaseModelSearchResult<CPInstance> searchCPDefinitionInstances(
			long companyId, long cpDefinitionId, String keywords, int status,
			int start, int end, Sort sort)
		throws PortalException {

		if (cpDefinitionId > 0) {
			_checkCommerceCatalogByCPDefinitionId(
				cpDefinitionId, ActionKeys.VIEW);
		}

		return cpInstanceLocalService.searchCPDefinitionInstances(
			companyId, cpDefinitionId, keywords, status, start, end, sort);
	}

	@Override
	public BaseModelSearchResult<CPInstance> searchCPDefinitionInstances(
			long companyId, long cpDefinitionId, String keywords, int status,
			Sort sort)
		throws PortalException {

		if (cpDefinitionId > 0) {
			_checkCommerceCatalogByCPDefinitionId(
				cpDefinitionId, ActionKeys.VIEW);
		}

		return cpInstanceLocalService.searchCPDefinitionInstances(
			companyId, cpDefinitionId, keywords, status, sort);
	}

	@Override
	public BaseModelSearchResult<CPInstance> searchCPInstances(
			long companyId, long groupId, String keywords, int status,
			int start, int end, Sort sort)
		throws PortalException {

		_checkCommerceCatalog(groupId, ActionKeys.VIEW);

		return cpInstanceLocalService.searchCPInstances(
			companyId, new long[] {groupId}, keywords, status, start, end,
			sort);
	}

	@Override
	public BaseModelSearchResult<CPInstance> searchCPInstances(
			long companyId, String keywords, int status, int start, int end,
			Sort sort)
		throws PortalException {

		List<CommerceCatalog> commerceCatalogs =
			commerceCatalogService.getCommerceCatalogs(
				companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Stream<CommerceCatalog> stream = commerceCatalogs.stream();

		LongStream longStream = stream.mapToLong(CommerceCatalog::getGroupId);

		long[] groupIds = longStream.toArray();

		return cpInstanceLocalService.searchCPInstances(
			companyId, groupIds, keywords, status, start, end, sort);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
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

		return cpInstanceService.updateCPInstance(
			cpInstanceId, sku, gtin, manufacturerPartNumber, purchasable,
			published, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, null, serviceContext);
	}

	@Override
	public CPInstance updateCPInstance(
			long cpInstanceId, String sku, String gtin,
			String manufacturerPartNumber, boolean purchasable,
			boolean published, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire, String unspsc,
			boolean discontinued, String replacementCPInstanceUuid,
			long replacementCProductId, int discontinuedDateMonth,
			int discontinuedDateDay, int discontinuedDateYear,
			ServiceContext serviceContext)
		throws PortalException {

		CPInstance cpInstance = cpInstanceLocalService.getCPInstance(
			cpInstanceId);

		_checkCommerceCatalogByCPDefinitionId(
			cpInstance.getCPDefinitionId(), ActionKeys.UPDATE);

		return cpInstanceLocalService.updateCPInstance(
			cpInstanceId, sku, gtin, manufacturerPartNumber, purchasable,
			cpInstance.getWidth(), cpInstance.getHeight(),
			cpInstance.getDepth(), cpInstance.getWeight(),
			cpInstance.getPrice(), cpInstance.getPromoPrice(),
			cpInstance.getCost(), published, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire, unspsc,
			discontinued, replacementCPInstanceUuid, replacementCProductId,
			discontinuedDateMonth, discontinuedDateDay, discontinuedDateYear,
			serviceContext);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	@Override
	public CPInstance updateCPInstance(
			long cpInstanceId, String sku, String gtin,
			String manufacturerPartNumber, boolean purchasable,
			boolean published, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire, String unspsc,
			ServiceContext serviceContext)
		throws PortalException {

		return cpInstanceService.updateCPInstance(
			cpInstanceId, sku, gtin, manufacturerPartNumber, purchasable,
			published, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, unspsc, false, null, 0, 0, 0, 0,
			serviceContext);
	}

	@Override
	public CPInstance updatePricingInfo(
			long cpInstanceId, BigDecimal price, BigDecimal promoPrice,
			BigDecimal cost, ServiceContext serviceContext)
		throws PortalException {

		CPInstance cpInstance = cpInstanceLocalService.getCPInstance(
			cpInstanceId);

		_checkCommerceCatalogByCPDefinitionId(
			cpInstance.getCPDefinitionId(), ActionKeys.UPDATE);

		return cpInstanceLocalService.updatePricingInfo(
			cpInstanceId, price, promoPrice, cost, serviceContext);
	}

	@Override
	public CPInstance updateShippingInfo(
			long cpInstanceId, double width, double height, double depth,
			double weight, ServiceContext serviceContext)
		throws PortalException {

		CPInstance cpInstance = cpInstanceLocalService.getCPInstance(
			cpInstanceId);

		_checkCommerceCatalogByCPDefinitionId(
			cpInstance.getCPDefinitionId(), ActionKeys.UPDATE);

		return cpInstanceLocalService.updateShippingInfo(
			cpInstanceId, width, height, depth, weight, serviceContext);
	}

	@Override
	public CPInstance updateSubscriptionInfo(
			long cpInstanceId, boolean overrideSubscriptionInfo,
			boolean subscriptionEnabled, int subscriptionLength,
			String subscriptionType,
			UnicodeProperties subscriptionTypeSettingsUnicodeProperties,
			long maxSubscriptionCycles, boolean deliverySubscriptionEnabled,
			int deliverySubscriptionLength, String deliverySubscriptionType,
			UnicodeProperties deliverySubscriptionTypeSettingsUnicodeProperties,
			long deliveryMaxSubscriptionCycles)
		throws PortalException {

		CPInstance cpInstance = cpInstanceLocalService.getCPInstance(
			cpInstanceId);

		_checkCommerceCatalogByCPDefinitionId(
			cpInstance.getCPDefinitionId(), ActionKeys.UPDATE);

		return cpInstanceLocalService.updateSubscriptionInfo(
			cpInstanceId, overrideSubscriptionInfo, subscriptionEnabled,
			subscriptionLength, subscriptionType,
			subscriptionTypeSettingsUnicodeProperties, maxSubscriptionCycles,
			deliverySubscriptionEnabled, deliverySubscriptionLength,
			deliverySubscriptionType,
			deliverySubscriptionTypeSettingsUnicodeProperties,
			deliveryMaxSubscriptionCycles);
	}

	private void _checkCommerceCatalog(long groupId, String actionId)
		throws PortalException {

		CommerceCatalog commerceCatalog =
			commerceCatalogLocalService.fetchCommerceCatalogByGroupId(groupId);

		if (commerceCatalog == null) {
			throw new PrincipalException();
		}

		_commerceCatalogModelResourcePermission.check(
			getPermissionChecker(), commerceCatalog, actionId);
	}

	private void _checkCommerceCatalogByCPDefinitionId(
			long cpDefinitionId, String actionId)
		throws PortalException {

		CPDefinition cpDefinition = cpDefinitionLocalService.fetchCPDefinition(
			cpDefinitionId);

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException();
		}

		CommerceCatalog commerceCatalog =
			commerceCatalogLocalService.fetchCommerceCatalogByGroupId(
				cpDefinition.getGroupId());

		if (commerceCatalog == null) {
			throw new PrincipalException();
		}

		_commerceCatalogModelResourcePermission.check(
			getPermissionChecker(), commerceCatalog, actionId);
	}

	private static volatile ModelResourcePermission<CommerceCatalog>
		_commerceCatalogModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CPInstanceServiceImpl.class,
				"_commerceCatalogModelResourcePermission",
				CommerceCatalog.class);

}