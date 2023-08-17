/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.service;

import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * Provides the remote service utility for CPDefinitionInventory. This utility wraps
 * <code>com.liferay.commerce.service.impl.CPDefinitionInventoryServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CPDefinitionInventoryService
 * @generated
 */
public class CPDefinitionInventoryServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.service.impl.CPDefinitionInventoryServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CPDefinitionInventory addCPDefinitionInventory(
			long cpDefinitionId, String cpDefinitionInventoryEngine,
			String lowStockActivity, boolean displayAvailability,
			boolean displayStockQuantity, java.math.BigDecimal minStockQuantity,
			boolean backOrders, java.math.BigDecimal minOrderQuantity,
			java.math.BigDecimal maxOrderQuantity,
			String allowedOrderQuantities,
			java.math.BigDecimal multipleOrderQuantity)
		throws PortalException {

		return getService().addCPDefinitionInventory(
			cpDefinitionId, cpDefinitionInventoryEngine, lowStockActivity,
			displayAvailability, displayStockQuantity, minStockQuantity,
			backOrders, minOrderQuantity, maxOrderQuantity,
			allowedOrderQuantities, multipleOrderQuantity);
	}

	public static void deleteCPDefinitionInventory(long cpDefinitionInventoryId)
		throws PortalException {

		getService().deleteCPDefinitionInventory(cpDefinitionInventoryId);
	}

	public static CPDefinitionInventory
			fetchCPDefinitionInventoryByCPDefinitionId(long cpDefinitionId)
		throws PortalException {

		return getService().fetchCPDefinitionInventoryByCPDefinitionId(
			cpDefinitionId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CPDefinitionInventory updateCPDefinitionInventory(
			long cpDefinitionInventoryId, String cpDefinitionInventoryEngine,
			String lowStockActivity, boolean displayAvailability,
			boolean displayStockQuantity, java.math.BigDecimal minStockQuantity,
			boolean backOrders, java.math.BigDecimal minOrderQuantity,
			java.math.BigDecimal maxOrderQuantity,
			String allowedOrderQuantities,
			java.math.BigDecimal multipleOrderQuantity)
		throws PortalException {

		return getService().updateCPDefinitionInventory(
			cpDefinitionInventoryId, cpDefinitionInventoryEngine,
			lowStockActivity, displayAvailability, displayStockQuantity,
			minStockQuantity, backOrders, minOrderQuantity, maxOrderQuantity,
			allowedOrderQuantities, multipleOrderQuantity);
	}

	public static CPDefinitionInventoryService getService() {
		return _service;
	}

	public static void setService(CPDefinitionInventoryService service) {
		_service = service;
	}

	private static volatile CPDefinitionInventoryService _service;

}