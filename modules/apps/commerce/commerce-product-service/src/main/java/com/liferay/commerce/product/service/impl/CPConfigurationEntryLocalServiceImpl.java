/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service.impl;

import com.liferay.commerce.product.model.CPConfigurationEntry;
import com.liferay.commerce.product.service.base.CPConfigurationEntryLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.UserLocalService;

import java.math.BigDecimal;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = "model.class.name=com.liferay.commerce.product.model.CPConfigurationEntry",
	service = AopService.class
)
public class CPConfigurationEntryLocalServiceImpl
	extends CPConfigurationEntryLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CPConfigurationEntry addCPConfigurationEntry(
			String externalReferenceCode, long userId, long groupId,
			long classNameId, long classPK, long cpConfigurationListId,
			String allowedOrderQuantities, boolean backOrders,
			long commerceAvailabilityEstimateId,
			String cpDefinitionInventoryEngine, boolean displayAvailability,
			boolean displayStockQuantity, String lowStockActivity,
			BigDecimal maxOrderQuantity, BigDecimal minOrderQuantity,
			BigDecimal minStockQuantity, BigDecimal multipleOrderQuantity)
		throws PortalException {

		User user = _userLocalService.getUser(userId);

		CPConfigurationEntry cpConfigurationEntry =
			cpConfigurationEntryPersistence.create(
				counterLocalService.increment());

		cpConfigurationEntry.setExternalReferenceCode(externalReferenceCode);
		cpConfigurationEntry.setGroupId(groupId);
		cpConfigurationEntry.setCompanyId(user.getCompanyId());
		cpConfigurationEntry.setUserId(user.getUserId());
		cpConfigurationEntry.setUserName(user.getFullName());
		cpConfigurationEntry.setClassNameId(classNameId);
		cpConfigurationEntry.setClassPK(classPK);
		cpConfigurationEntry.setCPConfigurationListId(cpConfigurationListId);
		cpConfigurationEntry.setAllowedOrderQuantities(allowedOrderQuantities);
		cpConfigurationEntry.setBackOrders(backOrders);
		cpConfigurationEntry.setCommerceAvailabilityEstimateId(
			commerceAvailabilityEstimateId);
		cpConfigurationEntry.setCPDefinitionInventoryEngine(
			cpDefinitionInventoryEngine);
		cpConfigurationEntry.setDisplayAvailability(displayAvailability);
		cpConfigurationEntry.setDisplayStockQuantity(displayStockQuantity);
		cpConfigurationEntry.setLowStockActivity(lowStockActivity);
		cpConfigurationEntry.setMaxOrderQuantity(maxOrderQuantity);
		cpConfigurationEntry.setMinOrderQuantity(minOrderQuantity);
		cpConfigurationEntry.setMinStockQuantity(minStockQuantity);
		cpConfigurationEntry.setMultipleOrderQuantity(multipleOrderQuantity);

		return cpConfigurationEntryPersistence.update(cpConfigurationEntry);
	}

	@Override
	public void deleteCPConfigurationEntries(long cpConfigurationListId) {
		List<CPConfigurationEntry> cpConfigurationEntries =
			cpConfigurationEntryLocalService.getCPConfigurationEntries(
				cpConfigurationListId);

		for (CPConfigurationEntry cpConfigurationEntry :
				cpConfigurationEntries) {

			cpConfigurationEntryLocalService.deleteCPConfigurationEntry(
				cpConfigurationEntry);
		}
	}

	@Override
	public CPConfigurationEntry fetchCPConfigurationEntry(
		long classNameId, long classPK, long cpConfigurationListId) {

		return cpConfigurationEntryPersistence.fetchByC_C_C(
			classNameId, classPK, cpConfigurationListId);
	}

	@Override
	public List<CPConfigurationEntry> getCPConfigurationEntries(
		long cpConfigurationListId) {

		return cpConfigurationEntryPersistence.findByCPConfigurationListId(
			cpConfigurationListId);
	}

	@Override
	public CPConfigurationEntry getCPConfigurationEntry(
			long classNameId, long classPK, long cpConfigurationListId)
		throws PortalException {

		return cpConfigurationEntryPersistence.findByC_C_C(
			classNameId, classPK, cpConfigurationListId);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CPConfigurationEntry updateCPConfigurationEntry(
			String externalReferenceCode, long cpConfigurationEntryId,
			String allowedOrderQuantities, boolean backOrders,
			long commerceAvailabilityEstimateId,
			String cpDefinitionInventoryEngine, boolean displayAvailability,
			boolean displayStockQuantity, String lowStockActivity,
			BigDecimal maxOrderQuantity, BigDecimal minOrderQuantity,
			BigDecimal minStockQuantity, BigDecimal multipleOrderQuantity)
		throws PortalException {

		CPConfigurationEntry cpConfigurationEntry =
			cpConfigurationEntryPersistence.findByPrimaryKey(
				cpConfigurationEntryId);

		cpConfigurationEntry.setExternalReferenceCode(externalReferenceCode);
		cpConfigurationEntry.setAllowedOrderQuantities(allowedOrderQuantities);
		cpConfigurationEntry.setBackOrders(backOrders);
		cpConfigurationEntry.setCommerceAvailabilityEstimateId(
			commerceAvailabilityEstimateId);
		cpConfigurationEntry.setCPDefinitionInventoryEngine(
			cpDefinitionInventoryEngine);
		cpConfigurationEntry.setDisplayAvailability(displayAvailability);
		cpConfigurationEntry.setDisplayStockQuantity(displayStockQuantity);
		cpConfigurationEntry.setLowStockActivity(lowStockActivity);
		cpConfigurationEntry.setMaxOrderQuantity(maxOrderQuantity);
		cpConfigurationEntry.setMinOrderQuantity(minOrderQuantity);
		cpConfigurationEntry.setMinStockQuantity(minStockQuantity);
		cpConfigurationEntry.setMultipleOrderQuantity(multipleOrderQuantity);

		return cpConfigurationEntryPersistence.update(cpConfigurationEntry);
	}

	@Reference
	private UserLocalService _userLocalService;

}