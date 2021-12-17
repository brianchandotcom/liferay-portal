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

package com.liferay.commerce.inventory.service.impl;

import com.liferay.commerce.inventory.constants.CommerceInventoryConstants;
import com.liferay.commerce.inventory.exception.MVCCException;
import com.liferay.commerce.inventory.exception.NoSuchInventoryBookedQuantityException;
import com.liferay.commerce.inventory.model.CommerceInventoryBookedQuantity;
import com.liferay.commerce.inventory.service.base.CommerceInventoryBookedQuantityLocalServiceBaseImpl;
import com.liferay.commerce.inventory.type.CommerceInventoryAuditType;
import com.liferay.commerce.inventory.type.CommerceInventoryAuditTypeRegistry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 */
public class CommerceInventoryBookedQuantityLocalServiceImpl
	extends CommerceInventoryBookedQuantityLocalServiceBaseImpl {

	@Override
	public CommerceInventoryBookedQuantity addCommerceBookedQuantity(
			long userId, String sku, int quantity, Date expirationDate,
			Map<String, String> context)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		long commerceInventoryBookedQuantityId =
			counterLocalService.increment();

		CommerceInventoryBookedQuantity commerceInventoryBookedQuantity =
			commerceInventoryBookedQuantityPersistence.create(
				commerceInventoryBookedQuantityId);

		commerceInventoryBookedQuantity.setCompanyId(user.getCompanyId());
		commerceInventoryBookedQuantity.setUserId(user.getUserId());
		commerceInventoryBookedQuantity.setUserName(user.getFullName());
		commerceInventoryBookedQuantity.setSku(sku);
		commerceInventoryBookedQuantity.setQuantity(quantity);
		commerceInventoryBookedQuantity.setExpirationDate(expirationDate);

		CommerceInventoryAuditType commerceInventoryAuditType =
			_commerceInventoryAuditTypeRegistry.getCommerceInventoryAuditType(
				CommerceInventoryConstants.AUDIT_TYPE_BOOKED_QUANTITY);

		commerceInventoryAuditLocalService.addCommerceInventoryAudit(
			userId, sku, commerceInventoryAuditType.getType(),
			commerceInventoryAuditType.getLog(context), quantity);

		return commerceInventoryBookedQuantityPersistence.update(
			commerceInventoryBookedQuantity);
	}

	@Override
	public void checkCommerceInventoryBookedQuantities() {
		commerceInventoryBookedQuantityPersistence.removeByLtExpirationDate(
			new Date());
	}

	@Override
	public CommerceInventoryBookedQuantity consumeCommerceBookedQuantity(
			long commerceBookedQuantityId, int quantity)
		throws NoSuchInventoryBookedQuantityException {

		CommerceInventoryBookedQuantity commerceInventoryBookedQuantity =
			commerceInventoryBookedQuantityPersistence.findByPrimaryKey(
				commerceBookedQuantityId);

		if (quantity < commerceInventoryBookedQuantity.getQuantity()) {
			int newQuantity =
				commerceInventoryBookedQuantity.getQuantity() - quantity;

			commerceInventoryBookedQuantity.setQuantity(newQuantity);

			return commerceInventoryBookedQuantityPersistence.update(
				commerceInventoryBookedQuantity);
		}

		return commerceInventoryBookedQuantityPersistence.remove(
			commerceBookedQuantityId);
	}

	@Override
	public int getCommerceBookedQuantity(long companyId, String sku) {
		List<CommerceInventoryBookedQuantity>
			commerceInventoryBookedQuantities =
				commerceInventoryBookedQuantityPersistence.findByC_S(
					companyId, sku);

		int resultQuantity = 0;

		for (CommerceInventoryBookedQuantity commerceInventoryBookedQuantity :
				commerceInventoryBookedQuantities) {

			resultQuantity += commerceInventoryBookedQuantity.getQuantity();
		}

		return resultQuantity;
	}

	@Override
	public List<CommerceInventoryBookedQuantity>
		getCommerceInventoryBookedQuantities(
			long companyId, String sku, int start, int end) {

		return commerceInventoryBookedQuantityPersistence.findByC_S(
			companyId, sku, start, end);
	}

	@Override
	public int getCommerceInventoryBookedQuantitiesCount(
		long companyId, String sku) {

		return commerceInventoryBookedQuantityPersistence.countByC_S(
			companyId, sku);
	}

	@Override
	public CommerceInventoryBookedQuantity resetCommerceBookedQuantity(
			long commerceBookedQuantityId, long userId, String sku,
			int quantity, Date expirationDate, Map<String, String> context)
		throws PortalException {

		CommerceInventoryBookedQuantity commerceBookedQuantity =
			commerceInventoryBookedQuantityPersistence.fetchByPrimaryKey(
				commerceBookedQuantityId);

		if (commerceBookedQuantity == null) {
			User user = userLocalService.getUser(userId);

			commerceBookedQuantity =
				commerceInventoryBookedQuantityPersistence.create(
					commerceBookedQuantityId);

			commerceBookedQuantity.setCompanyId(user.getCompanyId());
			commerceBookedQuantity.setUserId(userId);
			commerceBookedQuantity.setUserName(user.getFullName());
			commerceBookedQuantity.setSku(sku);
			commerceBookedQuantity.setExpirationDate(expirationDate);
		}

		commerceBookedQuantity.setQuantity(quantity);

		CommerceInventoryAuditType commerceInventoryAuditType =
			_commerceInventoryAuditTypeRegistry.getCommerceInventoryAuditType(
				CommerceInventoryConstants.AUDIT_TYPE_RESTORE_QUANTITY);

		commerceInventoryAuditLocalService.addCommerceInventoryAudit(
			userId, sku, commerceInventoryAuditType.getType(),
			commerceInventoryAuditType.getLog(context), quantity);

		return commerceInventoryBookedQuantityPersistence.update(
			commerceBookedQuantity);
	}

	@Override
	public CommerceInventoryBookedQuantity
			restockCommerceInventoryBookedQuantity(
				long userId, long commerceInventoryBookedQuantityId,
				Map<String, String> context)
		throws PortalException {

		CommerceInventoryBookedQuantity commerceInventoryBookedQuantity =
			commerceInventoryBookedQuantityPersistence.remove(
				commerceInventoryBookedQuantityId);

		CommerceInventoryAuditType commerceInventoryAuditType =
			_commerceInventoryAuditTypeRegistry.getCommerceInventoryAuditType(
				CommerceInventoryConstants.AUDIT_TYPE_RESTOCK_QUANTITY);

		commerceInventoryAuditLocalService.addCommerceInventoryAudit(
			userId, commerceInventoryBookedQuantity.getSku(),
			commerceInventoryAuditType.getType(),
			commerceInventoryAuditType.getLog(context),
			commerceInventoryBookedQuantity.getQuantity());

		return commerceInventoryBookedQuantity;
	}

	@Override
	public CommerceInventoryBookedQuantity
			updateCommerceInventoryBookedQuantity(
				long userId, long commerceInventoryBookedQuantityId,
				int quantity, Map<String, String> context, long mvccVersion)
		throws PortalException {

		CommerceInventoryBookedQuantity commerceInventoryBookedQuantity =
			commerceInventoryBookedQuantityLocalService.
				getCommerceInventoryBookedQuantity(
					commerceInventoryBookedQuantityId);

		if (commerceInventoryBookedQuantity.getMvccVersion() != mvccVersion) {
			throw new MVCCException();
		}

		commerceInventoryBookedQuantity.setQuantity(quantity);

		CommerceInventoryAuditType commerceInventoryAuditType =
			_commerceInventoryAuditTypeRegistry.getCommerceInventoryAuditType(
				CommerceInventoryConstants.AUDIT_TYPE_UPDATE_BOOKED_QUANTITY);

		commerceInventoryAuditLocalService.addCommerceInventoryAudit(
			userId, commerceInventoryBookedQuantity.getSku(),
			commerceInventoryAuditType.getType(),
			commerceInventoryAuditType.getLog(context), quantity);

		return commerceInventoryBookedQuantityLocalService.
			updateCommerceInventoryBookedQuantity(
				commerceInventoryBookedQuantity);
	}

	@ServiceReference(type = CommerceInventoryAuditTypeRegistry.class)
	private CommerceInventoryAuditTypeRegistry
		_commerceInventoryAuditTypeRegistry;

}