/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.blogs.model;

import com.liferay.portal.model.ModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link BlogsStatsUser}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BlogsStatsUser
 * @generated
 */
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
	* Sets the primary key of this blogs stats user.
	*
	* @param primaryKey the primary key of this blogs stats user
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_blogsStatsUser.setPrimaryKey(primaryKey);
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
	* Sets the stats user ID of this blogs stats user.
	*
	* @param statsUserId the stats user ID of this blogs stats user
	*/
	@Override
	public void setStatsUserId(long statsUserId) {
		_blogsStatsUser.setStatsUserId(statsUserId);
	}

	/**
	* Returns the stats user uuid of this blogs stats user.
	*
	* @return the stats user uuid of this blogs stats user
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getStatsUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsStatsUser.getStatsUserUuid();
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
	* Returns the group ID of this blogs stats user.
	*
	* @return the group ID of this blogs stats user
	*/
	@Override
	public long getGroupId() {
		return _blogsStatsUser.getGroupId();
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
	* Returns the company ID of this blogs stats user.
	*
	* @return the company ID of this blogs stats user
	*/
	@Override
	public long getCompanyId() {
		return _blogsStatsUser.getCompanyId();
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
	* Returns the user ID of this blogs stats user.
	*
	* @return the user ID of this blogs stats user
	*/
	@Override
	public long getUserId() {
		return _blogsStatsUser.getUserId();
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
	* Returns the user uuid of this blogs stats user.
	*
	* @return the user uuid of this blogs stats user
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsStatsUser.getUserUuid();
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
	* Sets the entry count of this blogs stats user.
	*
	* @param entryCount the entry count of this blogs stats user
	*/
	@Override
	public void setEntryCount(int entryCount) {
		_blogsStatsUser.setEntryCount(entryCount);
	}

	/**
	* Returns the last post date of this blogs stats user.
	*
	* @return the last post date of this blogs stats user
	*/
	@Override
	public java.util.Date getLastPostDate() {
		return _blogsStatsUser.getLastPostDate();
	}

	/**
	* Sets the last post date of this blogs stats user.
	*
	* @param lastPostDate the last post date of this blogs stats user
	*/
	@Override
	public void setLastPostDate(java.util.Date lastPostDate) {
		_blogsStatsUser.setLastPostDate(lastPostDate);
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
	* Returns the ratings total score of this blogs stats user.
	*
	* @return the ratings total score of this blogs stats user
	*/
	@Override
	public double getRatingsTotalScore() {
		return _blogsStatsUser.getRatingsTotalScore();
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
	* Returns the ratings average score of this blogs stats user.
	*
	* @return the ratings average score of this blogs stats user
	*/
	@Override
	public double getRatingsAverageScore() {
		return _blogsStatsUser.getRatingsAverageScore();
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

	@Override
	public boolean isNew() {
		return _blogsStatsUser.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_blogsStatsUser.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _blogsStatsUser.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_blogsStatsUser.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _blogsStatsUser.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _blogsStatsUser.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_blogsStatsUser.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _blogsStatsUser.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_blogsStatsUser.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_blogsStatsUser.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_blogsStatsUser.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new BlogsStatsUserWrapper((BlogsStatsUser)_blogsStatsUser.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portlet.blogs.model.BlogsStatsUser blogsStatsUser) {
		return _blogsStatsUser.compareTo(blogsStatsUser);
	}

	@Override
	public int hashCode() {
		return _blogsStatsUser.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.blogs.model.BlogsStatsUser> toCacheModel() {
		return _blogsStatsUser.toCacheModel();
	}

	@Override
	public com.liferay.portlet.blogs.model.BlogsStatsUser toEscapedModel() {
		return new BlogsStatsUserWrapper(_blogsStatsUser.toEscapedModel());
	}

	@Override
	public com.liferay.portlet.blogs.model.BlogsStatsUser toUnescapedModel() {
		return new BlogsStatsUserWrapper(_blogsStatsUser.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _blogsStatsUser.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _blogsStatsUser.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_blogsStatsUser.persist();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public BlogsStatsUser getWrappedBlogsStatsUser() {
		return _blogsStatsUser;
	}

	@Override
	public BlogsStatsUser getWrappedModel() {
		return _blogsStatsUser;
	}

	@Override
	public void resetOriginalValues() {
		_blogsStatsUser.resetOriginalValues();
	}

	private BlogsStatsUser _blogsStatsUser;
}