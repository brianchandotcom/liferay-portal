/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.link.internal.model.listener;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.link.service.AssetLinkLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(service = ModelListener.class)
public class AssetEntryModelListener extends BaseModelListener<AssetEntry> {

	@Override
	public void onBeforeRemove(AssetEntry assetEntry)
		throws ModelListenerException {

		_assetLinkLocalService.deleteLinks(assetEntry.getEntryId());
	}

	@Reference
	private AssetLinkLocalService _assetLinkLocalService;

}