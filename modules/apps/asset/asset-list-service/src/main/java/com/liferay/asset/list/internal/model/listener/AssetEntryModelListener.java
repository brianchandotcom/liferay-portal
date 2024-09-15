/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.list.internal.model.listener;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.list.service.AssetListEntryAssetEntryRelLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(service = ModelListener.class)
public class AssetEntryModelListener extends BaseModelListener<AssetEntry> {

	@Override
	public void onAfterRemove(AssetEntry assetEntry)
		throws ModelListenerException {

		try {
			_assetListEntryAssetEntryRelLocalService.
				deleteAssetListEntryAssetEntryRelByAssetEntryId(
					assetEntry.getEntryId());
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			throw new ModelListenerException(portalException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetEntryModelListener.class);

	@Reference
	private AssetListEntryAssetEntryRelLocalService
		_assetListEntryAssetEntryRelLocalService;

}