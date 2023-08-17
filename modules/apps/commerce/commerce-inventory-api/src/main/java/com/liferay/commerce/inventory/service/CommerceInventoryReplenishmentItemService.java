/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.service;

import com.liferay.commerce.inventory.model.CommerceInventoryReplenishmentItem;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

import java.math.BigDecimal;

import java.util.Date;
import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for CommerceInventoryReplenishmentItem. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Luca Pellizzon
 * @see CommerceInventoryReplenishmentItemServiceUtil
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface CommerceInventoryReplenishmentItemService extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.commerce.inventory.service.impl.CommerceInventoryReplenishmentItemServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the commerce inventory replenishment item remote service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link CommerceInventoryReplenishmentItemServiceUtil} if injection and service tracking are not available.
	 */
	public CommerceInventoryReplenishmentItem
			addCommerceInventoryReplenishmentItem(
				String externalReferenceCode, long commerceInventoryWarehouseId,
				Date availabilityDate, BigDecimal quantity, String sku,
				String unitOfMeasureKey)
		throws PortalException;

	public void deleteCommerceInventoryReplenishmentItem(
			long commerceInventoryReplenishmentItemId)
		throws PortalException;

	public void deleteCommerceInventoryReplenishmentItems(
			long companyId, String sku, String unitOfMeasureKey)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceInventoryReplenishmentItem
			fetchCommerceInventoryReplenishmentItemByExternalReferenceCode(
				String externalReferenceCode, long companyId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceInventoryReplenishmentItem
			getCommerceInventoryReplenishmentItem(
				long commerceInventoryReplenishmentItemId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceInventoryReplenishmentItem>
			getCommerceInventoryReplenishmentItemsByCommerceInventoryWarehouseId(
				long commerceInventoryWarehouseId, int start, int end)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceInventoryReplenishmentItem>
			getCommerceInventoryReplenishmentItemsByCompanyIdSkuAndUnitOfMeasureKey(
				long companyId, String sku, String unitOfMeasureKey, int start,
				int end)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BigDecimal getCommerceInventoryReplenishmentItemsCount(
			long commerceInventoryWarehouseId, String sku,
			String unitOfMeasureKey)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int
			getCommerceInventoryReplenishmentItemsCountByCommerceInventoryWarehouseId(
				long commerceInventoryWarehouseId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int
			getCommerceInventoryReplenishmentItemsCountByCompanyIdSkuAndUnitOfMeasureKey(
				long companyId, String sku, String unitOfMeasureKey)
		throws PortalException;

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	public CommerceInventoryReplenishmentItem
			updateCommerceInventoryReplenishmentItem(
				String externalReferenceCode,
				long commerceInventoryReplenishmentItemId,
				Date availabilityDate, BigDecimal quantity, long mvccVersion)
		throws PortalException;

}