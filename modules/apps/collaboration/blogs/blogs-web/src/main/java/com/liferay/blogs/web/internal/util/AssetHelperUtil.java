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

package com.liferay.blogs.web.internal.util;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.util.AssetHelper;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matthew Tambara
 */
@Component(immediate = true)
public class AssetHelperUtil {

	public static Set<String> addLayoutTags(
		HttpServletRequest request, List<AssetTag> tags) {

		return _assetHelper.addLayoutTags(request, tags);
	}

	@Reference(unbind = "-")
	protected void setAssetHelper(AssetHelper assetHelper) {
		_assetHelper = assetHelper;
	}

	private static AssetHelper _assetHelper;

}