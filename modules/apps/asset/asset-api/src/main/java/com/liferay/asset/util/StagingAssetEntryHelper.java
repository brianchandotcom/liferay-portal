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