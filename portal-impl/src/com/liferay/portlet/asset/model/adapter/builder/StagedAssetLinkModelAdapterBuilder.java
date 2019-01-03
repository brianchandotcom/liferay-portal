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

package com.liferay.portlet.asset.model.adapter.builder;

import com.liferay.asset.kernel.model.AssetLink;
import com.liferay.asset.kernel.model.adapter.StagedAssetLink;
import com.liferay.portal.kernel.model.adapter.builder.ModelAdapterBuilder;
import com.liferay.portlet.asset.model.adapter.impl.StagedAssetLinkImpl;

/**
 * @author Máté Thurzó
 */
public class StagedAssetLinkModelAdapterBuilder
	implements ModelAdapterBuilder<AssetLink, StagedAssetLink> {

	@Override
	public StagedAssetLink build(AssetLink assetLink) {
		return new StagedAssetLinkImpl(assetLink);
	}

}