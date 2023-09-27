/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.web.internal.util;

import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.saml.util.NameIdTypeValues;

import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Michael C. Han
 */
public class NameIdTypeValuesUtil {

	public static NameIdTypeValues getNameIdTypeValues() {
		return _serviceTracker.getService();
	}

	private static final ServiceTracker<NameIdTypeValues, NameIdTypeValues>
		_serviceTracker = ServiceTrackerFactory.open(
			FrameworkUtil.getBundle(NameIdTypeValuesUtil.class),
			NameIdTypeValues.class);

}