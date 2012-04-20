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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Juan Fernández
 */
public class AssetEntryImpl extends AssetEntryBaseImpl {

	public AssetEntryImpl() {
	}

	public AssetRenderer getAssetRenderer() throws SystemException {
		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				getClassName());

		try {
			return assetRendererFactory.getAssetRenderer(getClassPK());
		}
		catch (PortalException pe) {
			_log.warn("Error obtaining asset renderer", pe);
		}

		return null;
	}

	public AssetRendererFactory getAssetRendererFactory() {
		return
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				getClassName());
	}

	public List<AssetCategory> getCategories() throws SystemException {
		return AssetCategoryLocalServiceUtil.getEntryCategories(getEntryId());
	}

	public long[] getCategoryIds() throws SystemException {
		return StringUtil.split(
			ListUtil.toString(
				getCategories(), AssetCategory.CATEGORY_ID_ACCESSOR), 0L);
	}

	public String[] getTagNames() throws SystemException {
		return StringUtil.split(
			ListUtil.toString(getTags(), AssetTag.NAME_ACCESSOR));
	}

	public List<AssetTag> getTags() throws SystemException {
		return AssetTagLocalServiceUtil.getEntryTags(getEntryId());
	}

	private static Log _log = LogFactoryUtil.getLog(AssetEntryImpl.class);

}