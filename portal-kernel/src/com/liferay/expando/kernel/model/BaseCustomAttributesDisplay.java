/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.expando.kernel.model;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.petra.string.StringPool;

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