/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.web.internal.asset.model;

import com.liferay.asset.kernel.AssetRendererFactoryCustomizer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.depot.web.internal.application.controller.DepotApplicationController;
import com.liferay.portal.kernel.service.GroupLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(service = AssetRendererFactoryCustomizer.class)
public class DepotAssetRendererFactoryCustomizer
	implements AssetRendererFactoryCustomizer {

	@Override
	public <T> AssetRendererFactory<T> customize(
		AssetRendererFactory<T> assetRendererFactory) {

		return new DepotAssetRendererFactoryWrapper<>(
			assetRendererFactory, _depotApplicationController,
			_depotEntryLocalService, _groupLocalService);
	}

	@Reference
	private DepotApplicationController _depotApplicationController;

	@Reference
	private DepotEntryLocalService _depotEntryLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

}