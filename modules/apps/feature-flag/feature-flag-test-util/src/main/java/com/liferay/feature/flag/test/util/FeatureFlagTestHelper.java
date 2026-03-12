/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.feature.flag.test.util;

import com.liferay.portal.feature.flag.FeatureFlagsBagProvider;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManager;
import com.liferay.portal.kernel.feature.flag.constants.FeatureFlagConstants;
import com.liferay.portal.kernel.util.PropsUtil;

import java.util.Properties;

/**
 * @author Drew Brokke
 */
public class FeatureFlagTestHelper {

	public static final String FEATURE_FLAG_KEY_1 = "FAKE-123";

	public static final String FEATURE_FLAG_KEY_2 = "FAKE-456";

	public static final String FEATURE_FLAG_KEY_SYSTEM = "FAKE-000";

	public FeatureFlagTestHelper() throws Exception {
		_featureFlagsBagProvider =
			(FeatureFlagsBagProvider)PortalBeanLocatorUtil.locate(
				FeatureFlagsBagProvider.class.getName());

		_featureFlagManager = (FeatureFlagManager)PortalBeanLocatorUtil.locate(
			FeatureFlagManager.class.getName());

		Properties properties = PropsUtil.getProperties();

		properties.setProperty(
			FeatureFlagConstants.getKey(FEATURE_FLAG_KEY_1),
			Boolean.FALSE.toString());
		properties.setProperty(
			FeatureFlagConstants.getKey(FEATURE_FLAG_KEY_2),
			Boolean.FALSE.toString());
		properties.setProperty(
			FeatureFlagConstants.getKey(FEATURE_FLAG_KEY_SYSTEM),
			Boolean.FALSE.toString());
		properties.setProperty(
			FeatureFlagConstants.getKey(FEATURE_FLAG_KEY_SYSTEM, "system"),
			Boolean.TRUE.toString());

		_featureFlagsBagProvider.clearCache();
	}

	public boolean getFeatureFlagValue(long companyId, String featureFlagKey) {
		return _featureFlagManager.isEnabled(companyId, featureFlagKey);
	}

	public void setFeatureFlagValue(
		long companyId, String featureFlagKey, boolean enabled) {

		_featureFlagsBagProvider.setEnabled(companyId, featureFlagKey, enabled);
	}

	public void tearDown() {
		_featureFlagsBagProvider.clearCache();
	}

	private final FeatureFlagManager _featureFlagManager;
	private final FeatureFlagsBagProvider _featureFlagsBagProvider;

}