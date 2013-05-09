/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset;

import com.liferay.portlet.asset.model.AssetRendererFactory;

import java.util.List;

/**
 * @author Bruno Farache
 * @author Marcellus Tavares
 */
public interface AssetRendererFactoryRegistry {

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getAssetRendererFactories(
	 *             long)}
	 */
	public List<AssetRendererFactory> getAssetRendererFactories();

	public List<AssetRendererFactory> getAssetRendererFactories(long companyId);

	public AssetRendererFactory getAssetRendererFactoryByClassName(
		String className);

	public AssetRendererFactory getAssetRendererFactoryByType(String type);

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getClassNameIds(
	 *             long)}
	 */
	public long[] getClassNameIds();

	public long[] getClassNameIds(long companyId);

	public void register(AssetRendererFactory assetRendererFactory);

	public void unregister(AssetRendererFactory assetRendererFactory);

}