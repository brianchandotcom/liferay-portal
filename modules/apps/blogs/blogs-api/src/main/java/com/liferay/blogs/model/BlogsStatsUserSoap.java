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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class BlogsStatsUserSoap implements Serializable {

	public static BlogsStatsUserSoap toSoapModel(BlogsStatsUser model) {
		BlogsStatsUserSoap soapModel = new BlogsStatsUserSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setStatsUserId(model.getStatsUserId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setEntryCount(model.getEntryCount());
		soapModel.setLastPostDate(model.getLastPostDate());
		soapModel.setRatingsTotalEntries(model.getRatingsTotalEntries());
		soapModel.setRatingsTotalScore(model.getRatingsTotalScore());
		soapModel.setRatingsAverageScore(model.getRatingsAverageScore());

		return soapModel;
	}

	public static BlogsStatsUserSoap[] toSoapModels(BlogsStatsUser[] models) {
		BlogsStatsUserSoap[] soapModels = new BlogsStatsUserSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static BlogsStatsUserSoap[][] toSoapModels(
		BlogsStatsUser[][] models) {

		BlogsStatsUserSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new BlogsStatsUserSoap[models.length][models[0].length];
		}
		else {
			soapModels = new BlogsStatsUserSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static BlogsStatsUserSoap[] toSoapModels(
		List<BlogsStatsUser> models) {

		List<BlogsStatsUserSoap> soapModels = new ArrayList<BlogsStatsUserSoap>(
			models.size());

		for (BlogsStatsUser model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new BlogsStatsUserSoap[soapModels.size()]);
	}

	public BlogsStatsUserSoap() {
	}

	public long getPrimaryKey() {
		return _statsUserId;
	}

	public void setPrimaryKey(long pk) {
		setStatsUserId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getStatsUserId() {
		return _statsUserId;
	}

	public void setStatsUserId(long statsUserId) {
		_statsUserId = statsUserId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public int getEntryCount() {
		return _entryCount;
	}

	public void setEntryCount(int entryCount) {
		_entryCount = entryCount;
	}

	public Date getLastPostDate() {
		return _lastPostDate;
	}

	public void setLastPostDate(Date lastPostDate) {
		_lastPostDate = lastPostDate;
	}

	public int getRatingsTotalEntries() {
		return _ratingsTotalEntries;
	}

	public void setRatingsTotalEntries(int ratingsTotalEntries) {
		_ratingsTotalEntries = ratingsTotalEntries;
	}

	public double getRatingsTotalScore() {
		return _ratingsTotalScore;
	}

	public void setRatingsTotalScore(double ratingsTotalScore) {
		_ratingsTotalScore = ratingsTotalScore;
	}

	public double getRatingsAverageScore() {
		return _ratingsAverageScore;
	}

	public void setRatingsAverageScore(double ratingsAverageScore) {
		_ratingsAverageScore = ratingsAverageScore;
	}

	private long _mvccVersion;
	private long _statsUserId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private int _entryCount;
	private Date _lastPostDate;
	private int _ratingsTotalEntries;
	private double _ratingsTotalScore;
	private double _ratingsAverageScore;

}