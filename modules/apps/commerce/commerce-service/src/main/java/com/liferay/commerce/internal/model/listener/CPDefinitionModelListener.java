/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.model.listener;

import com.liferay.commerce.product.model.CPConfigurationEntry;
import com.liferay.commerce.product.model.CPConfigurationList;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPConfigurationEntryLocalService;
import com.liferay.commerce.product.service.CPConfigurationListLocalService;
import com.liferay.commerce.service.CPDefinitionInventoryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.ClassNameLocalService;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alec Sloan
 */
@Component(service = ModelListener.class)
public class CPDefinitionModelListener extends BaseModelListener<CPDefinition> {

	@Override
	public void onAfterCreate(CPDefinition cpDefinition) {
		try {
			CPConfigurationList cpConfigurationList =
				_cpConfigurationListLocalService.getMasterCPConfigurationList(
					cpDefinition.getGroupId());

			CPConfigurationEntry templateCPConfigurationEntry =
				_cpConfigurationEntryLocalService.getCPConfigurationEntry(
					_classNameLocalService.getClassNameId(
						CPConfigurationList.class),
					cpConfigurationList.getCPConfigurationListId(),
					cpConfigurationList.getCPConfigurationListId());

			_cpConfigurationEntryLocalService.addCPConfigurationEntry(
				null, cpDefinition.getUserId(), cpDefinition.getGroupId(),
				_classNameLocalService.getClassNameId(CPDefinition.class),
				cpDefinition.getCPDefinitionId(),
				cpConfigurationList.getCPConfigurationListId(),
				templateCPConfigurationEntry.getCPTaxCategoryId(),
				templateCPConfigurationEntry.getAllowedOrderQuantities(),
				templateCPConfigurationEntry.isBackOrders(),
				templateCPConfigurationEntry.
					getCommerceAvailabilityEstimateId(),
				templateCPConfigurationEntry.getCPDefinitionInventoryEngine(),
				templateCPConfigurationEntry.getDepth(),
				templateCPConfigurationEntry.isDisplayAvailability(),
				templateCPConfigurationEntry.isDisplayStockQuantity(),
				templateCPConfigurationEntry.isFreeShipping(),
				templateCPConfigurationEntry.getHeight(),
				templateCPConfigurationEntry.getLowStockActivity(),
				templateCPConfigurationEntry.getMaxOrderQuantity(),
				templateCPConfigurationEntry.getMinOrderQuantity(),
				templateCPConfigurationEntry.getMinStockQuantity(),
				templateCPConfigurationEntry.getMultipleOrderQuantity(),
				templateCPConfigurationEntry.isPurchasable(),
				templateCPConfigurationEntry.isShippable() &&
				!Objects.equals(cpDefinition.getProductTypeName(), "virtual"),
				templateCPConfigurationEntry.getShippingExtraPrice(),
				templateCPConfigurationEntry.isShipSeparately(),
				templateCPConfigurationEntry.isTaxExempt(),
				templateCPConfigurationEntry.isVisible(),
				templateCPConfigurationEntry.getWeight(),
				templateCPConfigurationEntry.getWidth());

			_cpDefinitionInventoryLocalService.addCPDefinitionInventory(
				cpDefinition.getUserId(), cpDefinition.getCPDefinitionId(),
				templateCPConfigurationEntry.getCPDefinitionInventoryEngine(),
				templateCPConfigurationEntry.getLowStockActivity(),
				templateCPConfigurationEntry.isDisplayAvailability(),
				templateCPConfigurationEntry.isDisplayStockQuantity(),
				templateCPConfigurationEntry.getMinStockQuantity(),
				templateCPConfigurationEntry.isBackOrders(),
				templateCPConfigurationEntry.getMinOrderQuantity(),
				templateCPConfigurationEntry.getMaxOrderQuantity(),
				templateCPConfigurationEntry.getAllowedOrderQuantities(),
				templateCPConfigurationEntry.getMultipleOrderQuantity());
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPDefinitionModelListener.class);

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CPConfigurationEntryLocalService _cpConfigurationEntryLocalService;

	@Reference
	private CPConfigurationListLocalService _cpConfigurationListLocalService;

	@Reference
	private CPDefinitionInventoryLocalService
		_cpDefinitionInventoryLocalService;

}