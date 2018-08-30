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

package com.liferay.asset.auto.tagger;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * Facade to the auto-tagging framework.
 *
 * @author Alejandro Tardín
 */
public interface AssetAutoTagger {

	/**
	 * Automatically applies tags to an asset entry by running every {@link
	 * AssetAutoTagProvider} implementation that can handle the asset entry's
	 * associated model and merging all the resulting tags.
	 *
	 * <p>
	 * Only assets with an associated {@link
	 * com.liferay.asset.kernel.model.AssetRenderer} are supported.
	 * </p>
	 *
	 * @param  assetEntry the asset entry to tag
	 * @throws PortalException if a portal exception occurred
	 */
	public void tag(AssetEntry assetEntry) throws PortalException;

	/**
	 * Removes the tags that have been automatically applied to the asset entry.
	 * Any tags added externally (e.g., via a human or the asset APIs) are not
	 * removed.
	 *
	 * <p>
	 * This method provides a way to rollback {@link #tag(AssetEntry)}.
	 * </p>
	 *
	 * @param  assetEntry the asset entry to untag
	 * @throws PortalException if a portal exception occurred
	 */
	public void untag(AssetEntry assetEntry) throws PortalException;

}