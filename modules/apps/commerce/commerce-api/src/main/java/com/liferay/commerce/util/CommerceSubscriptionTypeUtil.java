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

package com.liferay.commerce.util;

import com.liferay.commerce.model.CommerceSubscriptionEntry;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.portal.kernel.util.UnicodeProperties;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceSubscriptionTypeUtil {

	public static UnicodeProperties getSubscriptionTypeSettingsProperties(
		Object object, boolean payment) {

		if (object == null) {
			return null;
		}

		UnicodeProperties subscriptionTypeSettingsProperties = null;

		if (object instanceof CommerceSubscriptionEntry) {
			CommerceSubscriptionEntry commerceSubscriptionEntry =
				(CommerceSubscriptionEntry)object;

			if (payment) {
				subscriptionTypeSettingsProperties =
					commerceSubscriptionEntry.
						getSubscriptionTypeSettingsProperties();
			}
			else {
				subscriptionTypeSettingsProperties =
					commerceSubscriptionEntry.
						getDeliverySubscriptionTypeSettingsProperties();
			}
		}
		else if (object instanceof CPDefinition) {
			CPDefinition cpDefinition = (CPDefinition)object;

			if (payment) {
				subscriptionTypeSettingsProperties =
					cpDefinition.getSubscriptionTypeSettingsProperties();
			}
			else {
				subscriptionTypeSettingsProperties =
					cpDefinition.
						getDeliverySubscriptionTypeSettingsProperties();
			}
		}
		else if (object instanceof CPInstance) {
			CPInstance cpInstance = (CPInstance)object;

			if (payment) {
				subscriptionTypeSettingsProperties =
					cpInstance.getSubscriptionTypeSettingsProperties();
			}
			else {
				subscriptionTypeSettingsProperties =
					cpInstance.getDeliverySubscriptionTypeSettingsProperties();
			}
		}

		return subscriptionTypeSettingsProperties;
	}

}