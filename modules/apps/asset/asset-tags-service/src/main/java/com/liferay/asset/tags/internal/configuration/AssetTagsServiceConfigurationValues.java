/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.tags.internal.configuration;

import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Máté Thurzó
 */
public class AssetTagsServiceConfigurationValues {

	public static final boolean STAGING_MERGE_TAGS_BY_NAME =
		GetterUtil.getBoolean(
			AssetTagsServiceConfigurationUtil.get(
				AssetTagsServiceConfigurationKeys.STAGING_MERGE_TAGS_BY_NAME));

}