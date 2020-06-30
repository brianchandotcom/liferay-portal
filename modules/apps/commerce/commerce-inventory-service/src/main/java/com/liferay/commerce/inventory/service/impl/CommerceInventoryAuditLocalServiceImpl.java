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

import com.liferay.commerce.inventory.model.CommerceInventoryAudit;
import com.liferay.commerce.inventory.service.base.CommerceInventoryAuditLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;

import java.util.Date;
import java.util.List;

/**
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 */
public class CommerceInventoryAuditLocalServiceImpl
	extends CommerceInventoryAuditLocalServiceBaseImpl {

	@Override
	public CommerceInventoryAudit addCommerceInventoryAudit(
			long userId, String sku, String logType, String logTypeSettings,
			int quantity)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		long commerceInventoryAuditId = counterLocalService.increment();

		CommerceInventoryAudit commerceInventoryAudit =
			commerceInventoryAuditPersistence.create(commerceInventoryAuditId);

		commerceInventoryAudit.setCompanyId(user.getCompanyId());
		commerceInventoryAudit.setUserId(user.getUserId());
		commerceInventoryAudit.setUserName(user.getFullName());
		commerceInventoryAudit.setSku(sku);
		commerceInventoryAudit.setLogType(logType);
		commerceInventoryAudit.setLogTypeSettings(logTypeSettings);
		commerceInventoryAudit.setQuantity(quantity);

		return commerceInventoryAuditPersistence.update(commerceInventoryAudit);
	}

	@Override
	public void checkCommerceInventoryAudit(Date date) {
		commerceInventoryAuditPersistence.removeByLtCreateDate(date);
	}

	@Override
	public List<CommerceInventoryAudit> getCommerceInventoryAudits(
		long companyId, String sku, int start, int end) {

		return commerceInventoryAuditPersistence.findByC_S(
			companyId, sku, start, end);
	}

	@Override
	public int getCommerceInventoryAuditsCount(long companyId, String sku) {
		return commerceInventoryAuditPersistence.countByC_S(companyId, sku);
	}

}