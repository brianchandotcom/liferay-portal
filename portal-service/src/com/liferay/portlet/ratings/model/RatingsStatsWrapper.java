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

package com.liferay.portlet.ratings.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link RatingsStats}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RatingsStats
 * @generated
 */
@ProviderType
public class RatingsStatsWrapper implements RatingsStats,
	ModelWrapper<RatingsStats> {
	public RatingsStatsWrapper(RatingsStats ratingsStats) {
		_ratingsStats = ratingsStats;
	}

	@Override
	public Class<?> getModelClass() {
		return RatingsStats.class;
	}

	@Override
	public String getModelClassName() {
		return RatingsStats.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("statsId", getStatsId());
		attributes.put("companyId", getCompanyId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("totalEntries", getTotalEntries());
		attributes.put("totalScore", getTotalScore());
		attributes.put("averageScore", getAverageScore());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long statsId = (Long)attributes.get("statsId");

		if (statsId != null) {
			setStatsId(statsId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		Integer totalEntries = (Integer)attributes.get("totalEntries");

		if (totalEntries != null) {
			setTotalEntries(totalEntries);
		}

		Double totalScore = (Double)attributes.get("totalScore");

		if (totalScore != null) {
			setTotalScore(totalScore);
		}

		Double averageScore = (Double)attributes.get("averageScore");

		if (averageScore != null) {
			setAverageScore(averageScore);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new RatingsStatsWrapper((RatingsStats)_ratingsStats.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portlet.ratings.model.RatingsStats ratingsStats) {
		return _ratingsStats.compareTo(ratingsStats);
	}

	/**
	* Returns the average score of this ratings stats.
	*
	* @return the average score of this ratings stats
	*/
	@Override
	public double getAverageScore() {
		return _ratingsStats.getAverageScore();
	}

	/**
	* Returns the fully qualified class name of this ratings stats.
	*
	* @return the fully qualified class name of this ratings stats
	*/
	@Override
	public java.lang.String getClassName() {
		return _ratingsStats.getClassName();
	}

	/**
	* Returns the class name ID of this ratings stats.
	*
	* @return the class name ID of this ratings stats
	*/
	@Override
	public long getClassNameId() {
		return _ratingsStats.getClassNameId();
	}

	/**
	* Returns the class p k of this ratings stats.
	*
	* @return the class p k of this ratings stats
	*/
	@Override
	public long getClassPK() {
		return _ratingsStats.getClassPK();
	}

	/**
	* Returns the company ID of this ratings stats.
	*
	* @return the company ID of this ratings stats
	*/
	@Override
	public long getCompanyId() {
		return _ratingsStats.getCompanyId();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _ratingsStats.getExpandoBridge();
	}

	/**
	* Returns the primary key of this ratings stats.
	*
	* @return the primary key of this ratings stats
	*/
	@Override
	public long getPrimaryKey() {
		return _ratingsStats.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _ratingsStats.getPrimaryKeyObj();
	}

	/**
	* Returns the stats ID of this ratings stats.
	*
	* @return the stats ID of this ratings stats
	*/
	@Override
	public long getStatsId() {
		return _ratingsStats.getStatsId();
	}

	/**
	* Returns the total entries of this ratings stats.
	*
	* @return the total entries of this ratings stats
	*/
	@Override
	public int getTotalEntries() {
		return _ratingsStats.getTotalEntries();
	}

	/**
	* Returns the total score of this ratings stats.
	*
	* @return the total score of this ratings stats
	*/
	@Override
	public double getTotalScore() {
		return _ratingsStats.getTotalScore();
	}

	@Override
	public int hashCode() {
		return _ratingsStats.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _ratingsStats.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _ratingsStats.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _ratingsStats.isNew();
	}

	@Override
	public void persist() {
		_ratingsStats.persist();
	}

	/**
	* Sets the average score of this ratings stats.
	*
	* @param averageScore the average score of this ratings stats
	*/
	@Override
	public void setAverageScore(double averageScore) {
		_ratingsStats.setAverageScore(averageScore);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_ratingsStats.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(java.lang.String className) {
		_ratingsStats.setClassName(className);
	}

	/**
	* Sets the class name ID of this ratings stats.
	*
	* @param classNameId the class name ID of this ratings stats
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_ratingsStats.setClassNameId(classNameId);
	}

	/**
	* Sets the class p k of this ratings stats.
	*
	* @param classPK the class p k of this ratings stats
	*/
	@Override
	public void setClassPK(long classPK) {
		_ratingsStats.setClassPK(classPK);
	}

	/**
	* Sets the company ID of this ratings stats.
	*
	* @param companyId the company ID of this ratings stats
	*/
	@Override
	public void setCompanyId(long companyId) {
		_ratingsStats.setCompanyId(companyId);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_ratingsStats.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_ratingsStats.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_ratingsStats.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public void setNew(boolean n) {
		_ratingsStats.setNew(n);
	}

	/**
	* Sets the primary key of this ratings stats.
	*
	* @param primaryKey the primary key of this ratings stats
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_ratingsStats.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_ratingsStats.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the stats ID of this ratings stats.
	*
	* @param statsId the stats ID of this ratings stats
	*/
	@Override
	public void setStatsId(long statsId) {
		_ratingsStats.setStatsId(statsId);
	}

	/**
	* Sets the total entries of this ratings stats.
	*
	* @param totalEntries the total entries of this ratings stats
	*/
	@Override
	public void setTotalEntries(int totalEntries) {
		_ratingsStats.setTotalEntries(totalEntries);
	}

	/**
	* Sets the total score of this ratings stats.
	*
	* @param totalScore the total score of this ratings stats
	*/
	@Override
	public void setTotalScore(double totalScore) {
		_ratingsStats.setTotalScore(totalScore);
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.ratings.model.RatingsStats> toCacheModel() {
		return _ratingsStats.toCacheModel();
	}

	@Override
	public com.liferay.portlet.ratings.model.RatingsStats toEscapedModel() {
		return new RatingsStatsWrapper(_ratingsStats.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _ratingsStats.toString();
	}

	@Override
	public com.liferay.portlet.ratings.model.RatingsStats toUnescapedModel() {
		return new RatingsStatsWrapper(_ratingsStats.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _ratingsStats.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof RatingsStatsWrapper)) {
			return false;
		}

		RatingsStatsWrapper ratingsStatsWrapper = (RatingsStatsWrapper)obj;

		if (Validator.equals(_ratingsStats, ratingsStatsWrapper._ratingsStats)) {
			return true;
		}

		return false;
	}

	@Override
	public RatingsStats getWrappedModel() {
		return _ratingsStats;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _ratingsStats.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _ratingsStats.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_ratingsStats.resetOriginalValues();
	}

	private final RatingsStats _ratingsStats;
}