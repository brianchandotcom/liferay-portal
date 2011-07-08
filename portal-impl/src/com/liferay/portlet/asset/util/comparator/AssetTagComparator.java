/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.util.comparator;

import com.liferay.portlet.asset.model.AssetTag;

import java.util.Comparator;

/**
 * @author Juan Fernández
 */
public class AssetTagComparator implements Comparator<AssetTag> {

	public int compare(AssetTag assetTag1, AssetTag assetTag2) {
		return assetTag1.getName().compareTo(assetTag2.getName());
	}

}