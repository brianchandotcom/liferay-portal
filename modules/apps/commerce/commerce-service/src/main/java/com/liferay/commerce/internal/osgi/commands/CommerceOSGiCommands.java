/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.osgi.commands;

import com.liferay.commerce.service.CommerceSubscriptionEntryLocalService;
import com.liferay.commerce.subscription.CommerceSubscriptionEntryHelper;
import com.liferay.osgi.util.osgi.commands.OSGiCommands;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = {
		"osgi.command.function=checkSubscriptionEntriesStatus",
		"osgi.command.scope=commerce"
	},
	service = OSGiCommands.class
)
public class CommerceOSGiCommands implements OSGiCommands {

	public void renewSubscriptionEntries() throws Exception {
		_commerceSubscriptionEntryHelper.checkSubscriptionEntriesStatus(
			_commerceSubscriptionEntryLocalService.
				getActiveCommerceSubscriptionEntries());
	}

	@Reference
	private CommerceSubscriptionEntryHelper _commerceSubscriptionEntryHelper;

	@Reference
	private CommerceSubscriptionEntryLocalService
		_commerceSubscriptionEntryLocalService;

}