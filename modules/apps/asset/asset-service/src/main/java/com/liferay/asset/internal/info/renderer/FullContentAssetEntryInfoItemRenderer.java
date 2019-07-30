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

package com.liferay.asset.internal.info.renderer;

import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.info.item.renderer.InfoItemRenderer;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jorge Ferrer
 */
@Component(service = InfoItemRenderer.class)
public class FullContentAssetEntryInfoItemRenderer
	extends BaseAssetEntryInfoItemRenderer {

	@Override
	protected String getTemplate() {
		return AssetRenderer.TEMPLATE_FULL_CONTENT;
	}

}