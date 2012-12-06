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

package com.liferay.portlet.messageboards.trash;

import com.liferay.portal.kernel.trash.BaseTrashRenderer;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.messageboards.model.MBCategory;

import java.util.Locale;

/**
 * @author Eduardo Garcia
 */
public class MBCategoryTrashRenderer extends BaseTrashRenderer {

	public static final String TYPE = "category";

	public MBCategoryTrashRenderer(MBCategory category) {
		_category = category;
	}

	public String getClassName() {
		return MBCategory.class.getName();
	}

	public long getClassPK() {
		return _category.getPrimaryKey();
	}

	@Override
	public String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/common/category.png";
	}

	public String getPortletId() {
		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				MBCategory.class.getName());

		return assetRendererFactory.getPortletId();
	}

	public String getSummary(Locale locale) {
		return HtmlUtil.stripHtml(_category.getDescription());
	}

	public String getTitle(Locale locale) {
		return _category.getName();
	}

	public String getType() {
		return TYPE;
	}

	private MBCategory _category;

}