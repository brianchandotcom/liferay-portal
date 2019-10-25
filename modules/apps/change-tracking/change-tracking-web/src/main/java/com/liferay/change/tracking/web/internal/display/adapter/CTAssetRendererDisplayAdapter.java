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

package com.liferay.change.tracking.web.internal.display.adapter;

import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.change.tracking.display.CTDisplay;
import com.liferay.portal.kernel.model.BaseModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Samuel Trong Tran
 */
public class CTAssetRendererDisplayAdapter<T extends BaseModel<T>>
	implements CTDisplay<T> {

	public CTAssetRendererDisplayAdapter(
		AssetRendererFactory<T> assetRendererFactory) {

		_assetRendererFactory = assetRendererFactory;

		Class<?> clazz = _assetRendererFactory.getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		try {
			_modelClass = (Class<T>)classLoader.loadClass(
				_assetRendererFactory.getClassName());
		}
		catch (ClassNotFoundException cnfe) {
			throw new RuntimeException(cnfe);
		}
	}

	@Override
	public Class<T> getModelClass() {
		return _modelClass;
	}

	@Override
	public void render(
			HttpServletRequest request, HttpServletResponse response,
			T baseModel)
		throws Exception {

		AssetRenderer<T> assetRenderer = _assetRendererFactory.getAssetRenderer(
			baseModel, AssetRendererFactory.TYPE_LATEST);

		assetRenderer.include(
			request, response, AssetRenderer.TEMPLATE_FULL_CONTENT);
	}

	private final AssetRendererFactory<T> _assetRendererFactory;
	private final Class<T> _modelClass;

}