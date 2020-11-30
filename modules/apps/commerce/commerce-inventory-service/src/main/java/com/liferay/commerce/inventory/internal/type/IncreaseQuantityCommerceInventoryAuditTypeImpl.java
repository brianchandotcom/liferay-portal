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

package com.liferay.commerce.inventory.internal.type;

import com.liferay.commerce.inventory.constants.CommerceInventoryConstants;
import com.liferay.commerce.inventory.type.CommerceInventoryAuditType;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = "commerce.inventory.audit.type.key=" + CommerceInventoryConstants.AUDIT_TYPE_INCREASE_QUANTITY,
	service = CommerceInventoryAuditType.class
)
public class IncreaseQuantityCommerceInventoryAuditTypeImpl
	implements CommerceInventoryAuditType {

	@Override
	public String formatLog(long userId, String context, Locale locale)
		throws Exception {

		User user = _userLocalService.getUserById(userId);

		return LanguageUtil.format(
			locale, "x-increased-the-quantity-on-hand", user.getFullName());
	}

	@Override
	public String formatQuantity(int quantity, Locale locale) {
		return StringPool.PLUS + StringPool.SPACE + quantity;
	}

	@Override
	public String getLog(Map<String, String> context) {
		return getType();
	}

	@Override
	public String getType() {
		return CommerceInventoryConstants.AUDIT_TYPE_INCREASE_QUANTITY;
	}

	@Reference
	private UserLocalService _userLocalService;

}