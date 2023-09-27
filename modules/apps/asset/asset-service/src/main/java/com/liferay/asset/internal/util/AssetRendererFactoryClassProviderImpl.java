/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.internal.util;

import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.util.AssetRendererFactoryClassProvider;
import com.liferay.asset.util.AssetRendererFactoryWrapper;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component(service = AssetRendererFactoryClassProvider.class)
public class AssetRendererFactoryClassProviderImpl
	implements AssetRendererFactoryClassProvider {

	@Override
	public Class<? extends AssetRendererFactory<?>> getClass(
		AssetRendererFactory<?> assetRendererFactory) {

		if (assetRendererFactory instanceof AssetRendererFactoryWrapper<?>) {
			AssetRendererFactoryWrapper<?> assetRendererFactoryWrapper =
				(AssetRendererFactoryWrapper<?>)assetRendererFactory;

			return (Class<? extends AssetRendererFactory<?>>)
				assetRendererFactoryWrapper.getWrappedClass();
		}

		return (Class<? extends AssetRendererFactory<?>>)
			assetRendererFactory.getClass();
	}

}