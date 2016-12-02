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

package com.liferay.asset.publisher.test.util;

import com.liferay.asset.kernel.model.AssetEntry;

/**
 * Provides a utility method to convert an asset entry to XML format so it can
 * be saved in the Asset Publisher's portlet preferences.
 *
 * @author Tamas Molnar
 */
public class AssetPublisherTestUtil {

	public static String getAssetEntryXml(AssetEntry assetEntry) {
		StringBuilder sb = new StringBuilder(6);

		sb.append("<?xml version=\"1.0\"?><asset-entry>");
		sb.append("<asset-entry-type>");
		sb.append(assetEntry.getClassName());
		sb.append("</asset-entry-type><asset-entry-uuid>");
		sb.append(assetEntry.getClassUuid());
		sb.append("</asset-entry-uuid></asset-entry>");

		return sb.toString();
	}

}