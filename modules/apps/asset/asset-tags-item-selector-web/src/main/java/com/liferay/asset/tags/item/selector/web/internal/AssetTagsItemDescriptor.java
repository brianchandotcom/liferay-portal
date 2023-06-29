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

package com.liferay.asset.tags.item.selector.web.internal;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.tags.item.selector.web.internal.display.context.AssetTagsDisplayContext;
import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.HtmlUtil;

import java.util.Locale;

/**
 * @author Stefan Tanasie
 */
public class AssetTagsItemDescriptor
	implements ItemSelectorViewDescriptor.ItemDescriptor {

	public AssetTagsItemDescriptor(
		AssetTag assetTag, AssetTagsDisplayContext assetTagsDisplayContext) {

		_assetTag = assetTag;
		_assetTagsDisplayContext = assetTagsDisplayContext;
	}

	@Override
	public String getIcon() {
		return null;
	}

	@Override
	public String getImageURL() {
		return null;
	}

	@Override
	public String getPayload() {
		if (_assetTagsDisplayContext.isMultiple()) {
			return _assetTag.getName();
		}

		return JSONUtil.put(
			"tagId", String.valueOf(_assetTag.getTagId())
		).put(
			"tagName", _assetTag.getName()
		).toString();
	}

	@Override
	public String getSubtitle(Locale locale) {
		return StringPool.BLANK;
	}

	@Override
	public String getTitle(Locale locale) {
		return HtmlUtil.escape(_assetTag.getName());
	}

	private final AssetTag _assetTag;
	private final AssetTagsDisplayContext _assetTagsDisplayContext;

}