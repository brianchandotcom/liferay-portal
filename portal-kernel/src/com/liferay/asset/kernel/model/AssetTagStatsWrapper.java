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

package com.liferay.asset.kernel.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link AssetTagStats}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetTagStats
 * @deprecated As of Judson (7.1.x), replaced by {@link
 com.liferay.asset.tag.stats.model.impl.AssetTagStatsImpl}
 * @generated
 */
@Deprecated
public class AssetTagStatsWrapper
	extends BaseModelWrapper<AssetTagStats>
	implements AssetTagStats, ModelWrapper<AssetTagStats> {

	public AssetTagStatsWrapper(AssetTagStats assetTagStats) {
		super(assetTagStats);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("tagStatsId", getTagStatsId());
		attributes.put("companyId", getCompanyId());
		attributes.put("tagId", getTagId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("assetCount", getAssetCount());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long tagStatsId = (Long)attributes.get("tagStatsId");

		if (tagStatsId != null) {
			setTagStatsId(tagStatsId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long tagId = (Long)attributes.get("tagId");

		if (tagId != null) {
			setTagId(tagId);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Integer assetCount = (Integer)attributes.get("assetCount");

		if (assetCount != null) {
			setAssetCount(assetCount);
		}
	}

	/**
	 * Returns the asset count of this asset tag stats.
	 *
	 * @return the asset count of this asset tag stats
	 */
	@Override
	public int getAssetCount() {
		return model.getAssetCount();
	}

	/**
	 * Returns the fully qualified class name of this asset tag stats.
	 *
	 * @return the fully qualified class name of this asset tag stats
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this asset tag stats.
	 *
	 * @return the class name ID of this asset tag stats
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the company ID of this asset tag stats.
	 *
	 * @return the company ID of this asset tag stats
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the primary key of this asset tag stats.
	 *
	 * @return the primary key of this asset tag stats
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the tag ID of this asset tag stats.
	 *
	 * @return the tag ID of this asset tag stats
	 */
	@Override
	public long getTagId() {
		return model.getTagId();
	}

	/**
	 * Returns the tag stats ID of this asset tag stats.
	 *
	 * @return the tag stats ID of this asset tag stats
	 */
	@Override
	public long getTagStatsId() {
		return model.getTagStatsId();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the asset count of this asset tag stats.
	 *
	 * @param assetCount the asset count of this asset tag stats
	 */
	@Override
	public void setAssetCount(int assetCount) {
		model.setAssetCount(assetCount);
	}

	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	 * Sets the class name ID of this asset tag stats.
	 *
	 * @param classNameId the class name ID of this asset tag stats
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the company ID of this asset tag stats.
	 *
	 * @param companyId the company ID of this asset tag stats
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the primary key of this asset tag stats.
	 *
	 * @param primaryKey the primary key of this asset tag stats
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the tag ID of this asset tag stats.
	 *
	 * @param tagId the tag ID of this asset tag stats
	 */
	@Override
	public void setTagId(long tagId) {
		model.setTagId(tagId);
	}

	/**
	 * Sets the tag stats ID of this asset tag stats.
	 *
	 * @param tagStatsId the tag stats ID of this asset tag stats
	 */
	@Override
	public void setTagStatsId(long tagStatsId) {
		model.setTagStatsId(tagStatsId);
	}

	@Override
	protected AssetTagStatsWrapper wrap(AssetTagStats assetTagStats) {
		return new AssetTagStatsWrapper(assetTagStats);
	}

}