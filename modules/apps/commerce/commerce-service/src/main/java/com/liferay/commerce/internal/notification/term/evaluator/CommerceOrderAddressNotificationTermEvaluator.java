/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.notification.term.evaluator;

import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.notification.term.evaluator.NotificationTermEvaluator;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Map;

/**
 * @author Balázs Breier
 */
public class CommerceOrderAddressNotificationTermEvaluator
	implements NotificationTermEvaluator {

	public CommerceOrderAddressNotificationTermEvaluator(
		CommerceOrderLocalService commerceOrderLocalService,
		ObjectDefinition objectDefinition) {

		_commerceOrderLocalService = commerceOrderLocalService;
		_objectDefinition = objectDefinition;
	}

	@Override
	public String evaluate(Context context, Object object, String termName)
		throws PortalException {

		if (!(object instanceof Map) ||
			!StringUtil.equalsIgnoreCase(
				"CommerceOrder", _objectDefinition.getShortName())) {

			return termName;
		}

		Map<String, Object> termValues = (Map<String, Object>)object;

		return _getAddress(termName, termValues);
	}

	private String _getAddress(String termName, Map<String, Object> termValues)
		throws PortalException {

		String billingAddressPrefix = "[%COMMERCEORDER_BILLING_ADDRESS_";
		String shippingAddressPrefix = "[%COMMERCEORDER_SHIPPING_ADDRESS_";

		if (!termName.startsWith(billingAddressPrefix) &&
			!termName.startsWith(shippingAddressPrefix)) {

			return termName;
		}

		CommerceOrder commerceOrder =
			_commerceOrderLocalService.getCommerceOrder(
				GetterUtil.getLong(termValues.get("id")));

		if ((commerceOrder.getBillingAddress() != null) &&
			termName.startsWith(billingAddressPrefix)) {

			return _getTermValue(
				StringUtil.removeSubstring(termName, billingAddressPrefix),
				commerceOrder.getBillingAddress());
		}
		else if ((commerceOrder.getShippingAddress() != null) &&
				 termName.startsWith(shippingAddressPrefix)) {

			return _getTermValue(
				StringUtil.removeSubstring(termName, shippingAddressPrefix),
				commerceOrder.getShippingAddress());
		}

		return termName;
	}

	private String _getTermValue(
			String partialTermName, CommerceAddress commerceAddress)
		throws PortalException {

		if (partialTermName.equals("CITY%]")) {
			return commerceAddress.getCity();
		}
		else if (partialTermName.equals("COUNTRY%]")) {
			Country country = commerceAddress.getCountry();

			if (country == null) {
				return null;
			}

			return country.getTitle();
		}
		else if (partialTermName.equals("NAME%]")) {
			return commerceAddress.getName();
		}
		else if (partialTermName.equals("PHONE_NUMBER%]")) {
			return commerceAddress.getPhoneNumber();
		}
		else if (partialTermName.equals("REGION%]")) {
			Region region = commerceAddress.getRegion();

			if (region == null) {
				return null;
			}

			return region.getTitle();
		}
		else if (partialTermName.equals("STREET1%]")) {
			return commerceAddress.getStreet1();
		}
		else if (partialTermName.equals("STREET2%]")) {
			return commerceAddress.getStreet2();
		}
		else if (partialTermName.equals("STREET3%]")) {
			return commerceAddress.getStreet3();
		}
		else if (partialTermName.equals("ZIP%]")) {
			return commerceAddress.getZip();
		}

		return null;
	}

	private final CommerceOrderLocalService _commerceOrderLocalService;
	private final ObjectDefinition _objectDefinition;

}