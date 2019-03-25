/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.asset.internal.info.renderer;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.info.renderer.InfoItemRenderer;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jorge Ferrer
 */
@Component(service = InfoItemRenderer.class)
public class AssetEntryInfoItemRenderer
	implements InfoItemRenderer<AssetEntry> {

	@Override
	public void render(
		AssetEntry assetEntry, HttpServletRequest request,
		HttpServletResponse response) {

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				assetEntry.getClassName());

		request.setAttribute(
			WebKeys.ASSET_RENDERER_FACTORY, assetRendererFactory);

		try {
			AssetRenderer<?> assetRenderer =
				assetRendererFactory.getAssetRenderer(assetEntry.getClassPK());

			assetRenderer.include(request, response, "full-content");
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}