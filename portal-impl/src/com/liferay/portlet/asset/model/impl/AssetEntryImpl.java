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

package com.liferay.portlet.asset.model.impl;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetTagLocalServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.view.count.ViewCountServiceUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Juan Fernández
 */
public class AssetEntryImpl extends AssetEntryBaseImpl {

	@Override
	public AssetRenderer<?> getAssetRenderer() {
		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				getClassName());

		try {
			return assetRendererFactory.getAssetRenderer(getClassPK());
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to get asset renderer", e);
			}
		}

		return null;
	}

	@Override
	public AssetRendererFactory<?> getAssetRendererFactory() {
		return AssetRendererFactoryRegistryUtil.
			getAssetRendererFactoryByClassName(getClassName());
	}

	@Override
	public List<AssetCategory> getCategories() {
		return AssetCategoryLocalServiceUtil.getEntryCategories(getEntryId());
	}

	@Override
	public long[] getCategoryIds() {
		return ListUtil.toLongArray(
			getCategories(), AssetCategory.CATEGORY_ID_ACCESSOR);
	}

	@Override
	public String[] getTagNames() {
		return ListUtil.toArray(getTags(), AssetTag.NAME_ACCESSOR);
	}

	@Override
	public List<AssetTag> getTags() {
		return AssetTagLocalServiceUtil.getEntryTags(getEntryId());
	}

	public int getViewCount() {
		return (int)ViewCountServiceUtil.getViewCount(
			getCompanyId(), getClassNameId(), getClassPK());
	}

	private static final Log _log = LogFactoryUtil.getLog(AssetEntryImpl.class);

}