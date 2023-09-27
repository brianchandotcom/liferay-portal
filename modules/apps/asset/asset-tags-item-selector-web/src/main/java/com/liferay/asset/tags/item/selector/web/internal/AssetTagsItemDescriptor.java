/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.tags.item.selector.web.internal;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONUtil;

import java.util.Locale;

/**
 * @author Stefan Tanasie
 */
public class AssetTagsItemDescriptor
	implements ItemSelectorViewDescriptor.ItemDescriptor {

	public AssetTagsItemDescriptor(AssetTag assetTag) {
		_assetTag = assetTag;
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
		return _assetTag.getName();
	}

	private final AssetTag _assetTag;

}