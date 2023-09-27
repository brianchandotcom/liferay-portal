/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.subscription;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceSubscriptionEntry;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Alec Sloan
 */
public class CommerceSubscriptionEntryHelperUtil {

	public static void checkCommerceSubscriptions(CommerceOrder commerceOrder)
		throws PortalException {

		CommerceSubscriptionEntryHelper commerceSubscriptionEntryHelper =
			_serviceTracker.getService();

		commerceSubscriptionEntryHelper.checkCommerceSubscriptions(
			commerceOrder);
	}

	public static void checkSubscriptionEntriesStatus(
			List<CommerceSubscriptionEntry> commerceSubscriptionEntries)
		throws Exception {

		CommerceSubscriptionEntryHelper commerceSubscriptionEntryHelper =
			_serviceTracker.getService();

		commerceSubscriptionEntryHelper.checkSubscriptionEntriesStatus(
			commerceSubscriptionEntries);
	}

	public static void checkSubscriptionStatus(
			CommerceSubscriptionEntry commerceSubscriptionEntry)
		throws Exception {

		CommerceSubscriptionEntryHelper commerceSubscriptionEntryHelper =
			_serviceTracker.getService();

		commerceSubscriptionEntryHelper.checkSubscriptionStatus(
			commerceSubscriptionEntry);
	}

	private static final ServiceTracker<?, CommerceSubscriptionEntryHelper>
		_serviceTracker = ServiceTrackerFactory.open(
			FrameworkUtil.getBundle(CommerceSubscriptionEntry.class),
			CommerceSubscriptionEntryHelper.class);

}