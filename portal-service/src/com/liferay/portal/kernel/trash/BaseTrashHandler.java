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

package com.liferay.portal.kernel.trash;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.AssetRendererFactory;

/**
 * Represents the base class for basic operations with the Trash.
 *
 * <p>
 * The basic operations are:
 * </p>
 *
 * <ul>
 * <li>
 * Deletion of entries
 * </li>
 * <li>
 * Restore of entries
 * </li>
 * </ul>
 *
 * @author Alexander Chow
 * @author Zsolt Berentey
 */
public abstract class BaseTrashHandler implements TrashHandler {

	public void deleteTrashEntry(long classPK)
		throws PortalException, SystemException {

		deleteTrashEntries(new long[] {classPK});
	}

	public TrashRenderer getTrashRenderer(long classPK)
		throws PortalException, SystemException {

		AssetRendererFactory assetRendererFactory = getAssetRendererFactory();

		if (assetRendererFactory != null) {
			AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(
				classPK);

			if (assetRenderer instanceof TrashRenderer) {
				return (TrashRenderer)assetRenderer;
			}
		}

		return null;
	}

	public void restoreTrashEntry(long classPK)
		throws PortalException, SystemException {

		restoreTrashEntries(new long[] {classPK});
	}

	private AssetRendererFactory getAssetRendererFactory() {
		return AssetRendererFactoryRegistryUtil.
				getAssetRendererFactoryByClassName(getClassName());
	}

}