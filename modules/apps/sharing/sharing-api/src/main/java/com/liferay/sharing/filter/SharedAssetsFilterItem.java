/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharing.filter;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;

import java.util.Locale;

/**
 * Filters the shared items provided by the Shared With Me portlet.
 * Implementations of this interface must be registered as OSGi components using
 * the service {@code SharedAssetsFilterItem}. The {@code navigation.item.order}
 * property defines the order in which the filter appears in the user interface.
 *
 * @author Sergio González
 */
public interface SharedAssetsFilterItem {

	/**
	 * Returns the name of the class that filters the sharing entries.
	 *
	 * @return the class name
	 */
	public String getClassName();

	/**
	 * Returns the icon displayed in the user interface.
	 *
	 * @return the icon
	 */
	public default String getIcon() {
		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				getClassName());

		if (assetRendererFactory != null) {
			return assetRendererFactory.getIconCssClass();
		}

		return null;
	}

	/**
	 * Returns the label displayed in the user interface.
	 *
	 * @return the label
	 */
	public default String getLabel(Locale locale) {
		return ResourceActionsUtil.getModelResource(locale, getClassName());
	}

}