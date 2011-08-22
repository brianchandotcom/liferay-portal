/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.model.AssetTagStats;
import com.liferay.portlet.asset.service.base.AssetTagStatsLocalServiceBaseImpl;

import java.util.List;

/**
 * @author Jorge Ferrer
 */
public class AssetTagStatsLocalServiceImpl
	extends AssetTagStatsLocalServiceBaseImpl {

	public AssetTagStats addTagStats(long tagId, long classNameId)
		throws SystemException {

		long tagStatsId = counterLocalService.increment();

		AssetTagStats tagStats = assetTagStatsPersistence.create(tagStatsId);

		tagStats.setTagId(tagId);
		tagStats.setClassNameId(classNameId);

		try {
			assetTagStatsPersistence.update(tagStats, false);
		}
		catch (SystemException se) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Add failed, fetch {tagId=" + tagId + ", classNameId=" +
						classNameId + "}");
			}

			tagStats = assetTagStatsPersistence.fetchByT_C(
				tagId, classNameId, false);

			if (tagStats == null) {
				throw se;
			}
		}

		return tagStats;
	}

	public void deleteTagStats(AssetTagStats tagStats)
		throws SystemException {

		assetTagStatsPersistence.remove(tagStats);
	}

	public void deleteTagStats(long tagStatsId)
		throws PortalException, SystemException {

		AssetTagStats tagStats = assetTagStatsPersistence.findByPrimaryKey(
			tagStatsId);

		deleteTagStats(tagStats);
	}

	public void deleteTagStatsByClassNameId(long classNameId)
		throws SystemException {

		List<AssetTagStats> tagStatsList =
			assetTagStatsPersistence.findByClassNameId(classNameId);

		for (AssetTagStats tagStats : tagStatsList) {
			deleteTagStats(tagStats);
		}
	}

	public void deleteTagStatsByTagId(long tagId)
		throws SystemException {

		List<AssetTagStats> tagStatsList = assetTagStatsPersistence.findByTagId(
			tagId);

		for (AssetTagStats tagStats : tagStatsList) {
			deleteTagStats(tagStats);
		}
	}

	public List<AssetTagStats> getTagStats(long classNameId, int start, int end)
		throws SystemException {

		return assetTagStatsPersistence.findByClassNameId(
			classNameId, start, end);
	}

	public AssetTagStats getTagStats(long tagId, long classNameId)
		throws SystemException {

		AssetTagStats tagStats = assetTagStatsPersistence.fetchByT_C(
			tagId, classNameId);

		if (tagStats == null) {
			tagStats = assetTagStatsLocalService.addTagStats(
				tagId, classNameId);
		}

		return tagStats;
	}

	public AssetTagStats updateTagStats(long tagId, long classNameId)
		throws PortalException, SystemException {

		AssetTag tag = assetTagPersistence.findByPrimaryKey(tagId);

		int assetCount = assetTagFinder.countByG_C_N(
			tag.getGroupId(), classNameId, tag.getName());

		AssetTagStats tagStats = getTagStats(tagId, classNameId);

		tagStats.setAssetCount(assetCount);

		assetTagStatsPersistence.update(tagStats, false);

		return tagStats;
	}

	private static Log _log = LogFactoryUtil.getLog(
		AssetTagStatsLocalServiceImpl.class);

}