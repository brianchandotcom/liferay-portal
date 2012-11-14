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

package com.liferay.portlet.asset.model;

import com.liferay.portal.model.ModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link AssetTagStats}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetTagStats
 * @generated
 */
public class AssetTagStatsWrapper implements AssetTagStats,
	ModelWrapper<AssetTagStats> {
	public AssetTagStatsWrapper(AssetTagStats assetTagStats) {
		_assetTagStats = assetTagStats;
	}

	public Class<?> getModelClass() {
		return AssetTagStats.class;
	}

	public String getModelClassName() {
		return AssetTagStats.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("tagStatsId", getTagStatsId());
		attributes.put("tagId", getTagId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("assetCount", getAssetCount());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		Long tagStatsId = (Long)attributes.get("tagStatsId");

		if (tagStatsId != null) {
			setTagStatsId(tagStatsId);
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
	* Returns the primary key of this asset tag stats.
	*
	* @return the primary key of this asset tag stats
	*/
	public long getPrimaryKey() {
		return _assetTagStats.getPrimaryKey();
	}

	/**
	* Sets the primary key of this asset tag stats.
	*
	* @param primaryKey the primary key of this asset tag stats
	*/
	public void setPrimaryKey(long primaryKey) {
		_assetTagStats.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the tag stats ID of this asset tag stats.
	*
	* @return the tag stats ID of this asset tag stats
	*/
	public long getTagStatsId() {
		return _assetTagStats.getTagStatsId();
	}

	/**
	* Sets the tag stats ID of this asset tag stats.
	*
	* @param tagStatsId the tag stats ID of this asset tag stats
	*/
	public void setTagStatsId(long tagStatsId) {
		_assetTagStats.setTagStatsId(tagStatsId);
	}

	/**
	* Returns the tag ID of this asset tag stats.
	*
	* @return the tag ID of this asset tag stats
	*/
	public long getTagId() {
		return _assetTagStats.getTagId();
	}

	/**
	* Sets the tag ID of this asset tag stats.
	*
	* @param tagId the tag ID of this asset tag stats
	*/
	public void setTagId(long tagId) {
		_assetTagStats.setTagId(tagId);
	}

	/**
	* Returns the fully qualified class name of this asset tag stats.
	*
	* @return the fully qualified class name of this asset tag stats
	*/
	public java.lang.String getClassName() {
		return _assetTagStats.getClassName();
	}

	public void setClassName(java.lang.String className) {
		_assetTagStats.setClassName(className);
	}

	/**
	* Returns the class name ID of this asset tag stats.
	*
	* @return the class name ID of this asset tag stats
	*/
	public long getClassNameId() {
		return _assetTagStats.getClassNameId();
	}

	/**
	* Sets the class name ID of this asset tag stats.
	*
	* @param classNameId the class name ID of this asset tag stats
	*/
	public void setClassNameId(long classNameId) {
		_assetTagStats.setClassNameId(classNameId);
	}

	/**
	* Returns the asset count of this asset tag stats.
	*
	* @return the asset count of this asset tag stats
	*/
	public int getAssetCount() {
		return _assetTagStats.getAssetCount();
	}

	/**
	* Sets the asset count of this asset tag stats.
	*
	* @param assetCount the asset count of this asset tag stats
	*/
	public void setAssetCount(int assetCount) {
		_assetTagStats.setAssetCount(assetCount);
	}

	public boolean isNew() {
		return _assetTagStats.isNew();
	}

	public void setNew(boolean n) {
		_assetTagStats.setNew(n);
	}

	public boolean isCachedModel() {
		return _assetTagStats.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_assetTagStats.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _assetTagStats.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _assetTagStats.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_assetTagStats.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _assetTagStats.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_assetTagStats.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new AssetTagStatsWrapper((AssetTagStats)_assetTagStats.clone());
	}

	public int compareTo(
		com.liferay.portlet.asset.model.AssetTagStats assetTagStats) {
		return _assetTagStats.compareTo(assetTagStats);
	}

	@Override
	public int hashCode() {
		return _assetTagStats.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portlet.asset.model.AssetTagStats> toCacheModel() {
		return _assetTagStats.toCacheModel();
	}

	public com.liferay.portlet.asset.model.AssetTagStats toEscapedModel() {
		return new AssetTagStatsWrapper(_assetTagStats.toEscapedModel());
	}

	public com.liferay.portlet.asset.model.AssetTagStats toUnescapedModel() {
		return new AssetTagStatsWrapper(_assetTagStats.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _assetTagStats.toString();
	}

	public java.lang.String toXmlString() {
		return _assetTagStats.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetTagStats.persist();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public AssetTagStats getWrappedAssetTagStats() {
		return _assetTagStats;
	}

	public AssetTagStats getWrappedModel() {
		return _assetTagStats;
	}

	public void resetOriginalValues() {
		_assetTagStats.resetOriginalValues();
	}

	private AssetTagStats _assetTagStats;
}