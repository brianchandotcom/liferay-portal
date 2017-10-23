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

package com.liferay.asset.publisher.web.internal.util;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.util.AssetHelper;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.search.Hits;

import java.util.List;

import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matthew Tambara
 */
@Component(immediate = true)
public class AssetHelperUtil {

	public static String getAddURLPopUp(
		long groupId, long plid, PortletURL addPortletURL,
		boolean addDisplayPageParameter, Layout layout) {

		return _assetHelper.getAddURLPopUp(
			groupId, plid, addPortletURL, addDisplayPageParameter, layout);
	}

	public static List<AssetEntry> getAssetEntries(Hits hits) {
		return _assetHelper.getAssetEntries(hits);
	}

	public static String getAssetKeywords(String className, long classPK) {
		return _assetHelper.getAssetKeywords(className, classPK);
	}

	@Reference(unbind = "-")
	protected void setAssetHelper(AssetHelper assetHelper) {
		_assetHelper = assetHelper;
	}

	private static AssetHelper _assetHelper;

}