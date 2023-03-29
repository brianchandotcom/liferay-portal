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

package com.liferay.osb.faro.web.internal.model.preferences;

/**
 * @author Matthew Kong
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class IndividualSegmentPreferences {

	public DistributionCardTabsPreferences
		getDistributionCardTabsPreferences() {

		return _distributionCardTabsPreferences;
	}

	public void setDistributionCardTabsPreferences(
		DistributionCardTabsPreferences distributionCardTabsPreferences) {

		_distributionCardTabsPreferences = distributionCardTabsPreferences;
	}

	private DistributionCardTabsPreferences _distributionCardTabsPreferences =
		new DistributionCardTabsPreferences();

}