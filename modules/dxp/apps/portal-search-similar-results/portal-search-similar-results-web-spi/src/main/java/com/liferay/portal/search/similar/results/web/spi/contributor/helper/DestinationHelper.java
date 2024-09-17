/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.similar.results.web.spi.contributor.helper;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;

/**
 * @author André de Oliveira
 */
public interface DestinationHelper {

	/**
	 * @return The {@link AssetEntry} object associated with the current
	 *         destination.
	 */
	public AssetEntry getAssetEntry();

	/**
	 * @return The {@link AssetRenderer} for the {@link AssetEntry} associated
	 *         with the current destination.
	 */
	public AssetRenderer<?> getAssetRenderer();

	/**
	 * @return The view URL of the asset associated with the current
	 *         destination.
	 */
	public String getAssetViewURL();

	/**
	 * @return The fully qualified class name of the {@link AssetEntry}
	 *         associated with the current destination.
	 */
	public String getClassName();

	/**
	 * @return The class primary key of the {@link AssetEntry} associated with
	 *         the current destination.
	 */
	public long getClassPK();

	/**
	 * @param  name The name of the route parameter to retrieve.
	 * @return The value of a route parameter with the specified name.
	 */
	public Object getRouteParameter(String name);

	/**
	 * @return The group ID associated with the current destination.
	 */
	public long getScopeGroupId();

	/**
	 * @return The unique identifier (UID) of the {@link AssetEntry} associated
	 *         with the current destination.
	 */
	public String getUID();

}