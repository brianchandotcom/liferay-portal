/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.model.impl;

import com.liferay.portal.kernel.json.JSON;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.model.AssetTagDisplay;

import java.util.List;

/**
 * @author Igor Spasic
 */
public class AssetTagDisplayImpl implements AssetTagDisplay {

	public AssetTagDisplayImpl(int page, List<AssetTag> tags, int total) {
		_page = page;
		_tags = tags;
		_total = total;
	}

	public int getPage() {
		return _page;
	}

	public List<AssetTag> getTags() {
		return _tags;
	}

	public int getTotal() {
		return _total;
	}

	private int _page;
	@JSON
	private List<AssetTag> _tags;
	private int _total;

}