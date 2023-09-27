/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.util;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.xml.Element;

/**
 * @author Jürgen Kappler
 */
public interface StagingAssetEntryHelper {

	public void addAssetReference(
		PortletDataContext portletDataContext, ClassedModel classedModel,
		Element stagedElement, AssetEntry assetEntry);

	public AssetEntry fetchAssetEntry(long groupId, String uuid)
		throws PortalException;

	public boolean isAssetEntryApplicable(AssetEntry assetEntry)
		throws PortalException;

}