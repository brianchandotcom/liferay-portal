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

import com.liferay.portal.model.adapter.builder.ModelAdapterBuilder;
import com.liferay.portlet.asset.adapter.StagedAssetTag;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.model.adapter.impl.StagedAssetTagImpl;

/**
 * @author Daniel Kocsis
 */
public class StagedAssetTagModelAdapterBuilder
	implements ModelAdapterBuilder<AssetTag, StagedAssetTag> {

	@Override
	public StagedAssetTag build(AssetTag assetTag) {
		return new StagedAssetTagImpl(assetTag);
	}

}