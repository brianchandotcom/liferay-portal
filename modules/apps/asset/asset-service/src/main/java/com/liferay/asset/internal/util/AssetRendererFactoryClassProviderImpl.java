/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
	public Class<? extends AssetRendererFactory> getClass(
		AssetRendererFactory assetRendererFactory) {

		if (assetRendererFactory instanceof AssetRendererFactoryWrapper) {
			AssetRendererFactoryWrapper assetRendererFactoryWrapper =
				(AssetRendererFactoryWrapper)assetRendererFactory;

			return assetRendererFactoryWrapper.getWrappedClass();
		}

		return assetRendererFactory.getClass();
	}

}