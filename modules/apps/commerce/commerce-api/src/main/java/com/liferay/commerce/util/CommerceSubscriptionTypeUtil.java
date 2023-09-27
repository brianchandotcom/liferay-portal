/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.util;

import com.liferay.commerce.model.CommerceSubscriptionEntry;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.portal.kernel.util.UnicodeProperties;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceSubscriptionTypeUtil {

	public static UnicodeProperties
		getSubscriptionTypeSettingsUnicodeProperties(
			Object object, boolean payment) {

		if (object == null) {
			return null;
		}

		UnicodeProperties subscriptionTypeSettingsUnicodeProperties = null;

		if (object instanceof CommerceSubscriptionEntry) {
			CommerceSubscriptionEntry commerceSubscriptionEntry =
				(CommerceSubscriptionEntry)object;

			if (payment) {
				subscriptionTypeSettingsUnicodeProperties =
					commerceSubscriptionEntry.
						getSubscriptionTypeSettingsUnicodeProperties();
			}
			else {
				subscriptionTypeSettingsUnicodeProperties =
					commerceSubscriptionEntry.
						getDeliverySubscriptionTypeSettingsUnicodeProperties();
			}
		}
		else if (object instanceof CPDefinition) {
			CPDefinition cpDefinition = (CPDefinition)object;

			if (payment) {
				subscriptionTypeSettingsUnicodeProperties =
					cpDefinition.getSubscriptionTypeSettingsUnicodeProperties();
			}
			else {
				subscriptionTypeSettingsUnicodeProperties =
					cpDefinition.
						getDeliverySubscriptionTypeSettingsUnicodeProperties();
			}
		}
		else if (object instanceof CPInstance) {
			CPInstance cpInstance = (CPInstance)object;

			if (payment) {
				subscriptionTypeSettingsUnicodeProperties =
					cpInstance.getSubscriptionTypeSettingsUnicodeProperties();
			}
			else {
				subscriptionTypeSettingsUnicodeProperties =
					cpInstance.
						getDeliverySubscriptionTypeSettingsUnicodeProperties();
			}
		}

		return subscriptionTypeSettingsUnicodeProperties;
	}

}