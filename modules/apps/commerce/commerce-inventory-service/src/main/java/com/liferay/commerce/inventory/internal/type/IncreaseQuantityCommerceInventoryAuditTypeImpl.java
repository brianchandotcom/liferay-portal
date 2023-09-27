/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.internal.type;

import com.liferay.commerce.inventory.constants.CommerceInventoryConstants;
import com.liferay.commerce.inventory.type.CommerceInventoryAuditType;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;

import java.math.BigDecimal;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "commerce.inventory.audit.type.key=" + CommerceInventoryConstants.AUDIT_TYPE_INCREASE_QUANTITY,
	service = CommerceInventoryAuditType.class
)
public class IncreaseQuantityCommerceInventoryAuditTypeImpl
	implements CommerceInventoryAuditType {

	@Override
	public String formatLog(long userId, String context, Locale locale)
		throws Exception {

		User user = _userLocalService.getUserById(userId);

		return _language.format(
			locale, "x-increased-the-quantity-on-hand", user.getFullName());
	}

	@Override
	public String formatQuantity(BigDecimal quantity, Locale locale) {
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
	private Language _language;

	@Reference
	private UserLocalService _userLocalService;

}