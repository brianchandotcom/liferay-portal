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

package com.liferay.blogs.kernel.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link BlogsStatsUser}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BlogsStatsUser
 * @deprecated As of 7.0.0, replaced by {@link
com.liferay.blogs.model.impl.BlogsStatsUserImpl}
 * @generated
 */
@Deprecated
@ProviderType
public class BlogsStatsUserWrapper implements BlogsStatsUser,
	ModelWrapper<BlogsStatsUser> {
	public BlogsStatsUserWrapper(BlogsStatsUser blogsStatsUser) {
		_blogsStatsUser = blogsStatsUser;
	}

	@Override
	public Class<?> getModelClass() {
		return BlogsStatsUser.class;
	}

	@Override
	public String getModelClassName() {
		return BlogsStatsUser.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("statsUserId", getStatsUserId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("entryCount", getEntryCount());
		attributes.put("lastPostDate", getLastPostDate());
		attributes.put("ratingsTotalEntries", getRatingsTotalEntries());
		attributes.put("ratingsTotalScore", getRatingsTotalScore());
		attributes.put("ratingsAverageScore", getRatingsAverageScore());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long statsUserId = (Long)attributes.get("statsUserId");

		if (statsUserId != null) {
			setStatsUserId(statsUserId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Integer entryCount = (Integer)attributes.get("entryCount");

		if (entryCount != null) {
			setEntryCount(entryCount);
		}

		Date lastPostDate = (Date)attributes.get("lastPostDate");

		if (lastPostDate != null) {
			setLastPostDate(lastPostDate);
		}

		Integer ratingsTotalEntries = (Integer)attributes.get(
				"ratingsTotalEntries");

		if (ratingsTotalEntries != null) {
			setRatingsTotalEntries(ratingsTotalEntries);
		}

		Double ratingsTotalScore = (Double)attributes.get("ratingsTotalScore");

		if (ratingsTotalScore != null) {
			setRatingsTotalScore(ratingsTotalScore);
		}

		Double ratingsAverageScore = (Double)attributes.get(
				"ratingsAverageScore");

		if (ratingsAverageScore != null) {
			setRatingsAverageScore(ratingsAverageScore);
		}
	}

	@Override
	public BlogsStatsUser toEscapedModel() {
		return new BlogsStatsUserWrapper(_blogsStatsUser.toEscapedModel());
	}

	@Override
	public BlogsStatsUser toUnescapedModel() {
		return new BlogsStatsUserWrapper(_blogsStatsUser.toUnescapedModel());
	}

	@Override
	public boolean isCachedModel() {
		return _blogsStatsUser.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _blogsStatsUser.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _blogsStatsUser.isNew();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _blogsStatsUser.getExpandoBridge();
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<BlogsStatsUser> toCacheModel() {
		return _blogsStatsUser.toCacheModel();
	}

	/**
	* Returns the ratings average score of this blogs stats user.
	*
	* @return the ratings average score of this blogs stats user
	*/
	@Override
	public double getRatingsAverageScore() {
		return _blogsStatsUser.getRatingsAverageScore();
	}

	/**
	* Returns the ratings total score of this blogs stats user.
	*
	* @return the ratings total score of this blogs stats user
	*/
	@Override
	public double getRatingsTotalScore() {
		return _blogsStatsUser.getRatingsTotalScore();
	}

	@Override
	public int compareTo(BlogsStatsUser blogsStatsUser) {
		return _blogsStatsUser.compareTo(blogsStatsUser);
	}

	/**
	* Returns the entry count of this blogs stats user.
	*
	* @return the entry count of this blogs stats user
	*/
	@Override
	public int getEntryCount() {
		return _blogsStatsUser.getEntryCount();
	}

	/**
	* Returns the ratings total entries of this blogs stats user.
	*
	* @return the ratings total entries of this blogs stats user
	*/
	@Override
	public int getRatingsTotalEntries() {
		return _blogsStatsUser.getRatingsTotalEntries();
	}

	@Override
	public int hashCode() {
		return _blogsStatsUser.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _blogsStatsUser.getPrimaryKeyObj();
	}

	@Override
	public java.lang.Object clone() {
		return new BlogsStatsUserWrapper((BlogsStatsUser)_blogsStatsUser.clone());
	}

	/**
	* Returns the stats user uuid of this blogs stats user.
	*
	* @return the stats user uuid of this blogs stats user
	*/
	@Override
	public java.lang.String getStatsUserUuid() {
		return _blogsStatsUser.getStatsUserUuid();
	}

	/**
	* Returns the user uuid of this blogs stats user.
	*
	* @return the user uuid of this blogs stats user
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _blogsStatsUser.getUserUuid();
	}

	@Override
	public java.lang.String toString() {
		return _blogsStatsUser.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _blogsStatsUser.toXmlString();
	}

	/**
	* Returns the last post date of this blogs stats user.
	*
	* @return the last post date of this blogs stats user
	*/
	@Override
	public Date getLastPostDate() {
		return _blogsStatsUser.getLastPostDate();
	}

	/**
	* Returns the company ID of this blogs stats user.
	*
	* @return the company ID of this blogs stats user
	*/
	@Override
	public long getCompanyId() {
		return _blogsStatsUser.getCompanyId();
	}

	/**
	* Returns the group ID of this blogs stats user.
	*
	* @return the group ID of this blogs stats user
	*/
	@Override
	public long getGroupId() {
		return _blogsStatsUser.getGroupId();
	}

	/**
	* Returns the primary key of this blogs stats user.
	*
	* @return the primary key of this blogs stats user
	*/
	@Override
	public long getPrimaryKey() {
		return _blogsStatsUser.getPrimaryKey();
	}

	/**
	* Returns the stats user ID of this blogs stats user.
	*
	* @return the stats user ID of this blogs stats user
	*/
	@Override
	public long getStatsUserId() {
		return _blogsStatsUser.getStatsUserId();
	}

	/**
	* Returns the user ID of this blogs stats user.
	*
	* @return the user ID of this blogs stats user
	*/
	@Override
	public long getUserId() {
		return _blogsStatsUser.getUserId();
	}

	@Override
	public void persist() {
		_blogsStatsUser.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_blogsStatsUser.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this blogs stats user.
	*
	* @param companyId the company ID of this blogs stats user
	*/
	@Override
	public void setCompanyId(long companyId) {
		_blogsStatsUser.setCompanyId(companyId);
	}

	/**
	* Sets the entry count of this blogs stats user.
	*
	* @param entryCount the entry count of this blogs stats user
	*/
	@Override
	public void setEntryCount(int entryCount) {
		_blogsStatsUser.setEntryCount(entryCount);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_blogsStatsUser.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_blogsStatsUser.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_blogsStatsUser.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this blogs stats user.
	*
	* @param groupId the group ID of this blogs stats user
	*/
	@Override
	public void setGroupId(long groupId) {
		_blogsStatsUser.setGroupId(groupId);
	}

	/**
	* Sets the last post date of this blogs stats user.
	*
	* @param lastPostDate the last post date of this blogs stats user
	*/
	@Override
	public void setLastPostDate(Date lastPostDate) {
		_blogsStatsUser.setLastPostDate(lastPostDate);
	}

	@Override
	public void setNew(boolean n) {
		_blogsStatsUser.setNew(n);
	}

	/**
	* Sets the primary key of this blogs stats user.
	*
	* @param primaryKey the primary key of this blogs stats user
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_blogsStatsUser.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_blogsStatsUser.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the ratings average score of this blogs stats user.
	*
	* @param ratingsAverageScore the ratings average score of this blogs stats user
	*/
	@Override
	public void setRatingsAverageScore(double ratingsAverageScore) {
		_blogsStatsUser.setRatingsAverageScore(ratingsAverageScore);
	}

	/**
	* Sets the ratings total entries of this blogs stats user.
	*
	* @param ratingsTotalEntries the ratings total entries of this blogs stats user
	*/
	@Override
	public void setRatingsTotalEntries(int ratingsTotalEntries) {
		_blogsStatsUser.setRatingsTotalEntries(ratingsTotalEntries);
	}

	/**
	* Sets the ratings total score of this blogs stats user.
	*
	* @param ratingsTotalScore the ratings total score of this blogs stats user
	*/
	@Override
	public void setRatingsTotalScore(double ratingsTotalScore) {
		_blogsStatsUser.setRatingsTotalScore(ratingsTotalScore);
	}

	/**
	* Sets the stats user ID of this blogs stats user.
	*
	* @param statsUserId the stats user ID of this blogs stats user
	*/
	@Override
	public void setStatsUserId(long statsUserId) {
		_blogsStatsUser.setStatsUserId(statsUserId);
	}

	/**
	* Sets the stats user uuid of this blogs stats user.
	*
	* @param statsUserUuid the stats user uuid of this blogs stats user
	*/
	@Override
	public void setStatsUserUuid(java.lang.String statsUserUuid) {
		_blogsStatsUser.setStatsUserUuid(statsUserUuid);
	}

	/**
	* Sets the user ID of this blogs stats user.
	*
	* @param userId the user ID of this blogs stats user
	*/
	@Override
	public void setUserId(long userId) {
		_blogsStatsUser.setUserId(userId);
	}

	/**
	* Sets the user uuid of this blogs stats user.
	*
	* @param userUuid the user uuid of this blogs stats user
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_blogsStatsUser.setUserUuid(userUuid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof BlogsStatsUserWrapper)) {
			return false;
		}

		BlogsStatsUserWrapper blogsStatsUserWrapper = (BlogsStatsUserWrapper)obj;

		if (Objects.equals(_blogsStatsUser,
					blogsStatsUserWrapper._blogsStatsUser)) {
			return true;
		}

		return false;
	}

	@Override
	public BlogsStatsUser getWrappedModel() {
		return _blogsStatsUser;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _blogsStatsUser.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _blogsStatsUser.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_blogsStatsUser.resetOriginalValues();
	}

	private final BlogsStatsUser _blogsStatsUser;
}