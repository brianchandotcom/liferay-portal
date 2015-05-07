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

package com.liferay.portal.kernel.servlet.taglib.ui;

import com.liferay.portal.kernel.util.HashUtil;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Julio Camarero
 */
public abstract class BaseAssetAddonEntry implements AssetAddonEntry {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AssetAddonEntry)) {
			return false;
		}

		AssetAddonEntry assetAddonEntry = (AssetAddonEntry)obj;

		String key = assetAddonEntry.getKey();

		if (getKey() == key) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public String getIcon() {
		return _DEFAUTL_ICON;
	}

	@Override
	public String getKey() {
		return getClass().getSimpleName();
	}

	@Override
	public Double getWeight() {
		return 10.0;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, getKey());
	}

	@Override
	public void include(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	private static final String _DEFAUTL_ICON = "circle-blank";

}