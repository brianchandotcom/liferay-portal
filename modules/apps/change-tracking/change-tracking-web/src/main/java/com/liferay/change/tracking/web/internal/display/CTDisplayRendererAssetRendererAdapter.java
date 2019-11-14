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

package com.liferay.change.tracking.web.internal.display;

import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.change.tracking.display.CTDisplayRenderer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.change.tracking.CTModel;

import java.util.Locale;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Samuel Trong Tran
 */
public class CTDisplayRendererAssetRendererAdapter<T extends CTModel<T>>
	implements CTDisplayRenderer<T> {

	public CTDisplayRendererAssetRendererAdapter(
		AssetRendererFactory<T> assetRendererFactory, Class<T> modelClass) {

		_assetRendererFactory = assetRendererFactory;
		_modelClass = modelClass;
	}

	@Override
	public String getEditURL(HttpServletRequest httpServletRequest, T ctModel)
		throws Exception {

		AssetRenderer<T> assetRenderer = _getAssetRenderer(ctModel);

		PortletURL portletURL = assetRenderer.getURLEdit(httpServletRequest);

		if (portletURL == null) {
			return null;
		}

		return portletURL.toString();
	}

	@Override
	public Class<T> getModelClass() {
		return _modelClass;
	}

	@Override
	public String getTypeName(Locale locale) {
		return _assetRendererFactory.getTypeName(locale);
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, T ctModel)
		throws Exception {

		AssetRenderer<T> assetRenderer = _getAssetRenderer(ctModel);

		assetRenderer.include(
			httpServletRequest, httpServletResponse,
			AssetRenderer.TEMPLATE_FULL_CONTENT);
	}

	private AssetRenderer<T> _getAssetRenderer(T baseModel)
		throws PortalException {

		AssetRenderer<T> assetRenderer = _assetRendererFactory.getAssetRenderer(
			baseModel.getPrimaryKey(), AssetRendererFactory.TYPE_LATEST);

		if (assetRenderer == null) {
			assetRenderer = _assetRendererFactory.getAssetRenderer(
				baseModel, AssetRendererFactory.TYPE_LATEST);
		}

		return assetRenderer;
	}

	private final AssetRendererFactory<T> _assetRendererFactory;
	private final Class<T> _modelClass;

}