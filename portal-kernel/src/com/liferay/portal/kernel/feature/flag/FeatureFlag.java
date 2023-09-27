/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.feature.flag;

import java.util.Locale;

/**
 * @author Drew Brokke
 */
public interface FeatureFlag {

	public String[] getDependencyKeys();

	public String getDescription(Locale locale);

	public FeatureFlagType getFeatureFlagType();

	public String getKey();

	public String getTitle(Locale locale);

	public boolean isEnabled();

}