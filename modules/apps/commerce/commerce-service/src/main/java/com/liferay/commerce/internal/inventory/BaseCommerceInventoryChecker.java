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

package com.liferay.commerce.internal.inventory;

import com.liferay.commerce.inventory.CPDefinitionInventoryEngine;
import com.liferay.commerce.inventory.CommerceInventoryChecker;
import com.liferay.commerce.inventory.engine.CommerceInventoryEngine;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Igor Beslic
 */
public abstract class BaseCommerceInventoryChecker<T>
	implements CommerceInventoryChecker<T> {

	protected boolean isAvailable(CPInstance cpInstance, int quantity) {
		if (cpInstance == null) {
			return false;
		}

		if (isBackOrderAllowed(cpInstance) ||
			commerceInventoryEngine.hasStockQuantity(
				cpInstance.getCompanyId(), cpInstance.getSku(), quantity)) {

			return true;
		}

		return false;
	}

	protected boolean isBackOrderAllowed(CPInstance cpInstance) {
		try {
			if (cpDefinitionInventoryEngine.isBackOrderAllowed(cpInstance)) {
				return true;
			}
		}
		catch (PortalException portalException) {
			_log.error(
				"Unable to check is back order allowed", portalException);
		}

		return false;
	}

	@Reference
	protected CommerceInventoryEngine commerceInventoryEngine;

	@Reference
	protected CPDefinitionInventoryEngine cpDefinitionInventoryEngine;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseCommerceInventoryChecker.class);

}