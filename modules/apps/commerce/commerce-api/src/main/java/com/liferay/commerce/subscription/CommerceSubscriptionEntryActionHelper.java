/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.subscription;

/**
 * @author Alessio Antonio Rendina
 */
public interface CommerceSubscriptionEntryActionHelper {

	public void activateCommerceSubscriptionEntry(
			long commerceSubscriptionEntryId)
		throws Exception;

	public void cancelCommerceSubscriptionEntry(
			long commerceSubscriptionEntryId)
		throws Exception;

	public void suspendCommerceSubscriptionEntry(
			long commerceSubscriptionEntryId)
		throws Exception;

}