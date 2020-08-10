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
import com.liferay.commerce.inventory.type.CommerceInventoryAuditTypeConstants;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.StringBundler;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 */
@Component(
	immediate = true,
	property = "commerce.inventory.audit.type.key=" + CommerceInventoryConstants.AUDIT_TYPE_UPDATE_BOOKED_QUANTITY,
	service = CommerceInventoryAuditType.class
)
public class UpdateBookedQuantityCommerceInventoryAuditTypeImpl
	implements CommerceInventoryAuditType {

	@Override
	public String formatLog(long userId, String context, Locale locale)
		throws Exception {

		JSONObject jsonObject = _jsonFactory.createJSONObject(context);

		User user = _userLocalService.getUserById(userId);

		StringBundler contextSB = new StringBundler(8);

		contextSB.append(LanguageUtil.get(locale, "order"));
		contextSB.append(CharPool.SPACE);
		contextSB.append(
			jsonObject.get(CommerceInventoryAuditTypeConstants.ORDER_ID));
		contextSB.append(CharPool.COMMA);
		contextSB.append(CharPool.SPACE);
		contextSB.append(LanguageUtil.get(locale, "user"));
		contextSB.append(CharPool.SPACE);
		contextSB.append(user.getFullName());

		return contextSB.toString();
	}

	@Override
	public String getLog(Map<String, String> context) {
		JSONObject jsonObject = _jsonFactory.createJSONObject();

		for (Map.Entry<String, String> entry : context.entrySet()) {
			jsonObject.put(entry.getKey(), entry.getValue());
		}

		return jsonObject.toString();
	}

	@Override
	public String getType() {
		return CommerceInventoryConstants.AUDIT_TYPE_BOOKED_QUANTITY;
	}

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private UserLocalService _userLocalService;

}