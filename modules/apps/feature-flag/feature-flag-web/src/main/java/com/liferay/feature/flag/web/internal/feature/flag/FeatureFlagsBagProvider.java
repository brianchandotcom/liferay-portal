/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.feature.flag.web.internal.feature.flag;

import java.util.function.Function;

/**
 * @author Drew Brokke
 */
public interface FeatureFlagsBagProvider {

	public void clearCache();

	public FeatureFlagsBag getOrCreateFeatureFlagsBag(long companyId);

	public void setEnabled(long companyId, String key, boolean enabled);

	public <T> T withFeatureFlagsBag(
		long companyId, Function<FeatureFlagsBag, T> function);

}