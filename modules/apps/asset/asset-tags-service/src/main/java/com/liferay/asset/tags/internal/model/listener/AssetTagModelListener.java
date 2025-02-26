/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.tags.internal.model.listener;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagGroupRelLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gislayne Vitorino
 */
@Component(service = ModelListener.class)
public class AssetTagModelListener extends BaseModelListener<AssetTag> {

	@Override
	public void onAfterRemove(AssetTag assetTag) throws ModelListenerException {
		_assetTagGroupRelLocalService.deleteAssetTagGroupRelsByTagId(
			assetTag.getTagId());
	}

	@Reference
	private AssetTagGroupRelLocalService _assetTagGroupRelLocalService;

}