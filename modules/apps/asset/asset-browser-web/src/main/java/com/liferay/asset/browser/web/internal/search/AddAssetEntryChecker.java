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

package com.liferay.asset.browser.web.internal.search;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;

import javax.portlet.RenderResponse;

/**
 * @author Jürgen Kappler
 */
public class AddAssetEntryChecker extends EmptyOnClickRowChecker {

	public AddAssetEntryChecker(
		RenderResponse renderResponse, long assetEntryId) {

		super(renderResponse);

		_assetEntryId = assetEntryId;
	}

	@Override
	public boolean isDisabled(Object obj) {
		AssetEntry assetEntry = (AssetEntry)obj;

		if (assetEntry.getEntryId() == _assetEntryId) {
			return true;
		}

		return super.isDisabled(obj);
	}

	private final long _assetEntryId;

}