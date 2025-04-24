/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.asset;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Bryan Engler
 */
@ProviderType
public interface AssetSubtypeIdentifierBuilder {

	public AssetSubtypeIdentifier build();

	public AssetSubtypeIdentifierBuilder searchableAssetType(
		String searchableAssetType);

}