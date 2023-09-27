/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.tags.compiler.web.internal.configuration;

import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Jürgen Kappler
 */
public class AssetTagsCompilerWebConfigurationValues {

	public static final boolean ENABLED = GetterUtil.getBoolean(
		AssetTagsCompilerWebConfigurationUtil.get("enabled"));

}