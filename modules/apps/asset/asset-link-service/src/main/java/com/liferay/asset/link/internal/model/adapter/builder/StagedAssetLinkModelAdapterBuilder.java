/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.link.internal.model.adapter.builder;

import com.liferay.asset.link.internal.model.adapter.iml.StagedAssetLinkImpl;
import com.liferay.asset.link.model.AssetLink;
import com.liferay.asset.link.model.adapter.StagedAssetLink;
import com.liferay.portal.kernel.model.adapter.builder.ModelAdapterBuilder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Máté Thurzó
 */
@Component(service = ModelAdapterBuilder.class)
public class StagedAssetLinkModelAdapterBuilder
	implements ModelAdapterBuilder<AssetLink, StagedAssetLink> {

	@Override
	public StagedAssetLink build(AssetLink assetLink) {
		return new StagedAssetLinkImpl(assetLink);
	}

}