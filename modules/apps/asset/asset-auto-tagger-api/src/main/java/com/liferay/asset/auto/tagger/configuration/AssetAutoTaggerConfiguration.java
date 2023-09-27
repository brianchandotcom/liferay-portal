/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.auto.tagger.configuration;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Alejandro Tardín
 */
@ProviderType
public interface AssetAutoTaggerConfiguration {

	public int getMaximumNumberOfTagsPerAsset();

	public boolean isAvailable();

	public boolean isEnabled();

	public boolean isUpdateAutoTags();

}