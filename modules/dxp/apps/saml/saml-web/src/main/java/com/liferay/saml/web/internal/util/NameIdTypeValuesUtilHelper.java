/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.saml.web.internal.util;

import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.saml.util.NameIdTypeValues;

import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Michael C. Han
 */
public class NameIdTypeValuesUtilHelper {

	public static NameIdTypeValues getNameIdTypeValues() {
		return _serviceTracker.getService();
	}

	private static final ServiceTracker<NameIdTypeValues, NameIdTypeValues>
		_serviceTracker = ServiceTrackerFactory.open(NameIdTypeValues.class);

}