/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.kernel;

import com.liferay.asset.kernel.model.AssetRendererFactory;

/**
 * @author Shuyang Zhou
 */
public interface AssetRendererFactoryCustomizer {

	public <T> AssetRendererFactory<T> customize(
		AssetRendererFactory<T> assetRendererFactory);

}