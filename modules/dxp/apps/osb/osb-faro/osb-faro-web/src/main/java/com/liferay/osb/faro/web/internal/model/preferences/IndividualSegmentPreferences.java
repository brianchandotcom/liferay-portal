/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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