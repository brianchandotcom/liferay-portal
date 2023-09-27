/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.asset;

import com.liferay.asset.kernel.model.AssetRendererFactory;

import java.util.List;

/**
 * @author Adam Brandizzi
 */
public interface AssetRendererFactoryRegistry {

	public List<AssetRendererFactory<?>> getAssetRendererFactories(
		long companyId);

}