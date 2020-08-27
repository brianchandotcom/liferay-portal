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