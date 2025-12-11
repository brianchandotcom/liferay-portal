/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.model.listener;

import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.ModelListener;

import org.osgi.service.component.annotations.Component;

/**
 * @author Carolina Barbosa
 */
@Component(service = ModelListener.class)
public class AssetListEntryModelListener
	extends BaseModelListener<AssetListEntry> {

	@Override
	public void onBeforeUpdate(
			AssetListEntry originalAssetListEntry,
			AssetListEntry assetListEntry)
		throws ModelListenerException {

		if (!ExportImportThreadLocal.isImportInProcess()) {
			return;
		}

		assetListEntry.setAssetEntryType(
			updateObjectDefinitionReferences(
				assetListEntry.getAssetEntryType()));
	}

}