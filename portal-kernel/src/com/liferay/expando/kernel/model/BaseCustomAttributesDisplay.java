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

package com.liferay.expando.kernel.model;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.theme.ThemeDisplay;

/**
 * @author Jorge Ferrer
 */
public abstract class BaseCustomAttributesDisplay
	implements CustomAttributesDisplay {

	public long getClassNameId() {
		return _classNameId;
	}

	@Override
	public String getIconCssClass() {
		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				getClassName());

		if (assetRendererFactory != null) {
			return assetRendererFactory.getIconCssClass();
		}

		return StringPool.BLANK;
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public String getIconPath(ThemeDisplay themeDisplay) {
		return StringPool.BLANK;
	}

	@Override
	public String getPortletId() {
		return _portletId;
	}

	@Override
	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	@Override
	public void setPortletId(String portletId) {
		_portletId = portletId;
	}

	private long _classNameId;
	private String _portletId;

}