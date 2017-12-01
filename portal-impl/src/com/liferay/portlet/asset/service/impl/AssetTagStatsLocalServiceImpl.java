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

package com.liferay.portlet.asset.service.impl;

import com.liferay.asset.kernel.model.AssetTagStats;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portlet.asset.service.base.AssetTagStatsLocalServiceBaseImpl;

import java.util.List;

/**
 * Provides the local service for accessing, adding, deleting, and updating
 * asset tag statistics.
 *
 * @author Jorge Ferrer
 * @deprecated As of 7.0.0, replaced by {@link
 *             com.liferay.asset.tag.stats.service.impl.AssetTagStatsLocalServiceImpl}
 */
@Deprecated
public class AssetTagStatsLocalServiceImpl
	extends AssetTagStatsLocalServiceBaseImpl {

	/**
	 * Adds an asset tag statistics instance.
	 *
	 * @param  tagId the primary key of the tag
	 * @param  classNameId the asset entry's class name ID
	 * @return the asset tag statistics instance
	 */
	@Override
	public AssetTagStats addTagStats(long tagId, long classNameId) {
		throw new UnsupportedOperationException(
			"This class is deprecate and replaced by " +
				"com.liferay.asset.tag.stats.service.impl." +
					"AssetTagStatsLocalServiceImpl");
	}

	/**
	 * Deletes the asset tag statistics instance.
	 *
	 * @param tagStats the asset tag statistics instance
	 */
	@Override
	public void deleteTagStats(AssetTagStats tagStats) {
		throw new UnsupportedOperationException(
			"This class is deprecate and replaced by " +
				"com.liferay.asset.tag.stats.service.impl." +
					"AssetTagStatsLocalServiceImpl");
	}

	/**
	 * Deletes the asset tag statistics instance matching the tag statistics ID.
	 *
	 * @param tagStatsId the primary key of the asset tag statistics instance
	 */
	@Override
	public void deleteTagStats(long tagStatsId) throws PortalException {
		throw new UnsupportedOperationException(
			"This class is deprecate and replaced by " +
				"com.liferay.asset.tag.stats.service.impl." +
					"AssetTagStatsLocalServiceImpl");
	}

	/**
	 * Deletes all asset tag statistics instances associated with the asset
	 * entry matching the class name ID.
	 *
	 * @param classNameId the asset entry's class name ID
	 */
	@Override
	public void deleteTagStatsByClassNameId(long classNameId) {
		throw new UnsupportedOperationException(
			"This class is deprecate and replaced by " +
				"com.liferay.asset.tag.stats.service.impl." +
					"AssetTagStatsLocalServiceImpl");
	}

	/**
	 * Deletes all asset tag statistics instances associated with the tag.
	 *
	 * @param tagId the primary key of the tag
	 */
	@Override
	public void deleteTagStatsByTagId(long tagId) {
		throw new UnsupportedOperationException(
			"This class is deprecate and replaced by " +
				"com.liferay.asset.tag.stats.service.impl." +
					"AssetTagStatsLocalServiceImpl");
	}

	/**
	 * Returns a range of all the asset tag statistics instances associated with
	 * the asset entry matching the class name ID.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  classNameId the asset entry's class name ID
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @return the range of asset tag statistics associated with the asset entry
	 *         matching the class name ID
	 */
	@Override
	public List<AssetTagStats> getTagStats(
		long classNameId, int start, int end) {

		throw new UnsupportedOperationException(
			"This class is deprecate and replaced by " +
				"com.liferay.asset.tag.stats.service.impl." +
					"AssetTagStatsLocalServiceImpl");
	}

	/**
	 * Returns the asset tag statistics instance with the tag and asset entry
	 * matching the class name ID
	 *
	 * @param  tagId the primary key of the tag
	 * @param  classNameId the asset entry's class name ID
	 * @return Returns the asset tag statistics instance with the tag and asset
	 *         entry  matching the class name ID
	 */
	@Override
	public AssetTagStats getTagStats(long tagId, long classNameId) {
		throw new UnsupportedOperationException(
			"This class is deprecate and replaced by " +
				"com.liferay.asset.tag.stats.service.impl." +
					"AssetTagStatsLocalServiceImpl");
	}

	/**
	 * Updates the asset tag statistics instance.
	 *
	 * @param  tagId the primary key of the tag
	 * @param  classNameId the asset entry's class name ID
	 * @return the updated asset tag statistics instance
	 */
	@Override
	public AssetTagStats updateTagStats(long tagId, long classNameId)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecate and replaced by " +
				"com.liferay.asset.tag.stats.service.impl." +
					"AssetTagStatsLocalServiceImpl");
	}

}