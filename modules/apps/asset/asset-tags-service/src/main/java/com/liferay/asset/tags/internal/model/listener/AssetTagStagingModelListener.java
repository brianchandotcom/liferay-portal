/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.tags.internal.model.listener;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.staging.model.listener.StagingModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Akos Thurzo
 */
@Component(service = ModelListener.class)
public class AssetTagStagingModelListener extends BaseModelListener<AssetTag> {

	@Override
	public void onAfterCreate(AssetTag assetTag) throws ModelListenerException {
		_stagingModelListener.onAfterCreate(assetTag);
	}

	@Override
	public void onAfterRemove(AssetTag assetTag) throws ModelListenerException {
		_stagingModelListener.onAfterRemove(assetTag);
	}

	@Override
	public void onAfterUpdate(AssetTag originalAssetTag, AssetTag assetTag)
		throws ModelListenerException {

		_stagingModelListener.onAfterUpdate(assetTag);
	}

	@Reference
	private StagingModelListener<AssetTag> _stagingModelListener;

}