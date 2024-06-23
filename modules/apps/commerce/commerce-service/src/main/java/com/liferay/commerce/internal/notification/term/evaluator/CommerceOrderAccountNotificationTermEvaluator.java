/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.notification.term.evaluator;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.notification.term.evaluator.NotificationTermEvaluator;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Map;

/**
 * @author Matyas Wollner
 */
public class CommerceOrderAccountNotificationTermEvaluator
	implements NotificationTermEvaluator {

	public CommerceOrderAccountNotificationTermEvaluator(
		CommerceOrderLocalService commerceOrderLocalService,
		ObjectDefinition objectDefinition) {

		_commerceOrderLocalService = commerceOrderLocalService;
		_objectDefinition = objectDefinition;
	}

	@Override
	public String evaluate(Context context, Object object, String termName)
		throws PortalException {

		if (!(object instanceof Map) ||
			!StringUtil.equals(termName, "[%COMMERCEORDER_ACCOUNT_NAME%]") ||
			!StringUtil.equalsIgnoreCase(
				"CommerceOrder", _objectDefinition.getShortName())) {

			return termName;
		}

		Map<String, Object> termValues = (Map<String, Object>)object;

		CommerceOrder commerceOrder =
			_commerceOrderLocalService.getCommerceOrder(
				GetterUtil.getLong(termValues.get("id")));

		return commerceOrder.getCommerceAccountName();
	}

	private final CommerceOrderLocalService _commerceOrderLocalService;
	private final ObjectDefinition _objectDefinition;

}