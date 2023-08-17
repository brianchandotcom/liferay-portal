/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.service;

import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

import java.math.BigDecimal;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for CPDefinitionInventory. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CPDefinitionInventoryServiceUtil
 * @generated
 */
@AccessControlled
@CTAware
@JSONWebService
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface CPDefinitionInventoryService extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.commerce.service.impl.CPDefinitionInventoryServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the cp definition inventory remote service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link CPDefinitionInventoryServiceUtil} if injection and service tracking are not available.
	 */
	public CPDefinitionInventory addCPDefinitionInventory(
			long cpDefinitionId, String cpDefinitionInventoryEngine,
			String lowStockActivity, boolean displayAvailability,
			boolean displayStockQuantity, BigDecimal minStockQuantity,
			boolean backOrders, BigDecimal minOrderQuantity,
			BigDecimal maxOrderQuantity, String allowedOrderQuantities,
			BigDecimal multipleOrderQuantity)
		throws PortalException;

	public void deleteCPDefinitionInventory(long cpDefinitionInventoryId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPDefinitionInventory fetchCPDefinitionInventoryByCPDefinitionId(
			long cpDefinitionId)
		throws PortalException;

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	public CPDefinitionInventory updateCPDefinitionInventory(
			long cpDefinitionInventoryId, String cpDefinitionInventoryEngine,
			String lowStockActivity, boolean displayAvailability,
			boolean displayStockQuantity, BigDecimal minStockQuantity,
			boolean backOrders, BigDecimal minOrderQuantity,
			BigDecimal maxOrderQuantity, String allowedOrderQuantities,
			BigDecimal multipleOrderQuantity)
		throws PortalException;

}