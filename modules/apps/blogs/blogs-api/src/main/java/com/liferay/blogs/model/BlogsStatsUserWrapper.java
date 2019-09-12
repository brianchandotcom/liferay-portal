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

package com.liferay.blogs.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

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
public class BlogsStatsUserWrapper
	extends BaseModelWrapper<BlogsStatsUser>
	implements BlogsStatsUser, ModelWrapper<BlogsStatsUser> {

	public BlogsStatsUserWrapper(BlogsStatsUser blogsStatsUser) {
		super(blogsStatsUser);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
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
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

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
	 * Returns the company ID of this blogs stats user.
	 *
	 * @return the company ID of this blogs stats user
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the entry count of this blogs stats user.
	 *
	 * @return the entry count of this blogs stats user
	 */
	@Override
	public int getEntryCount() {
		return model.getEntryCount();
	}

	/**
	 * Returns the group ID of this blogs stats user.
	 *
	 * @return the group ID of this blogs stats user
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the last post date of this blogs stats user.
	 *
	 * @return the last post date of this blogs stats user
	 */
	@Override
	public Date getLastPostDate() {
		return model.getLastPostDate();
	}

	/**
	 * Returns the mvcc version of this blogs stats user.
	 *
	 * @return the mvcc version of this blogs stats user
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this blogs stats user.
	 *
	 * @return the primary key of this blogs stats user
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the ratings average score of this blogs stats user.
	 *
	 * @return the ratings average score of this blogs stats user
	 */
	@Override
	public double getRatingsAverageScore() {
		return model.getRatingsAverageScore();
	}

	/**
	 * Returns the ratings total entries of this blogs stats user.
	 *
	 * @return the ratings total entries of this blogs stats user
	 */
	@Override
	public int getRatingsTotalEntries() {
		return model.getRatingsTotalEntries();
	}

	/**
	 * Returns the ratings total score of this blogs stats user.
	 *
	 * @return the ratings total score of this blogs stats user
	 */
	@Override
	public double getRatingsTotalScore() {
		return model.getRatingsTotalScore();
	}

	/**
	 * Returns the stats user ID of this blogs stats user.
	 *
	 * @return the stats user ID of this blogs stats user
	 */
	@Override
	public long getStatsUserId() {
		return model.getStatsUserId();
	}

	/**
	 * Returns the stats user uuid of this blogs stats user.
	 *
	 * @return the stats user uuid of this blogs stats user
	 */
	@Override
	public String getStatsUserUuid() {
		return model.getStatsUserUuid();
	}

	/**
	 * Returns the user ID of this blogs stats user.
	 *
	 * @return the user ID of this blogs stats user
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user uuid of this blogs stats user.
	 *
	 * @return the user uuid of this blogs stats user
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a blogs stats user model instance should use the <code>BlogsStatsUser</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this blogs stats user.
	 *
	 * @param companyId the company ID of this blogs stats user
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the entry count of this blogs stats user.
	 *
	 * @param entryCount the entry count of this blogs stats user
	 */
	@Override
	public void setEntryCount(int entryCount) {
		model.setEntryCount(entryCount);
	}

	/**
	 * Sets the group ID of this blogs stats user.
	 *
	 * @param groupId the group ID of this blogs stats user
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the last post date of this blogs stats user.
	 *
	 * @param lastPostDate the last post date of this blogs stats user
	 */
	@Override
	public void setLastPostDate(Date lastPostDate) {
		model.setLastPostDate(lastPostDate);
	}

	/**
	 * Sets the mvcc version of this blogs stats user.
	 *
	 * @param mvccVersion the mvcc version of this blogs stats user
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this blogs stats user.
	 *
	 * @param primaryKey the primary key of this blogs stats user
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the ratings average score of this blogs stats user.
	 *
	 * @param ratingsAverageScore the ratings average score of this blogs stats user
	 */
	@Override
	public void setRatingsAverageScore(double ratingsAverageScore) {
		model.setRatingsAverageScore(ratingsAverageScore);
	}

	/**
	 * Sets the ratings total entries of this blogs stats user.
	 *
	 * @param ratingsTotalEntries the ratings total entries of this blogs stats user
	 */
	@Override
	public void setRatingsTotalEntries(int ratingsTotalEntries) {
		model.setRatingsTotalEntries(ratingsTotalEntries);
	}

	/**
	 * Sets the ratings total score of this blogs stats user.
	 *
	 * @param ratingsTotalScore the ratings total score of this blogs stats user
	 */
	@Override
	public void setRatingsTotalScore(double ratingsTotalScore) {
		model.setRatingsTotalScore(ratingsTotalScore);
	}

	/**
	 * Sets the stats user ID of this blogs stats user.
	 *
	 * @param statsUserId the stats user ID of this blogs stats user
	 */
	@Override
	public void setStatsUserId(long statsUserId) {
		model.setStatsUserId(statsUserId);
	}

	/**
	 * Sets the stats user uuid of this blogs stats user.
	 *
	 * @param statsUserUuid the stats user uuid of this blogs stats user
	 */
	@Override
	public void setStatsUserUuid(String statsUserUuid) {
		model.setStatsUserUuid(statsUserUuid);
	}

	/**
	 * Sets the user ID of this blogs stats user.
	 *
	 * @param userId the user ID of this blogs stats user
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user uuid of this blogs stats user.
	 *
	 * @param userUuid the user uuid of this blogs stats user
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected BlogsStatsUserWrapper wrap(BlogsStatsUser blogsStatsUser) {
		return new BlogsStatsUserWrapper(blogsStatsUser);
	}

}