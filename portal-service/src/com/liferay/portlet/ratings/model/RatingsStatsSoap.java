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

package com.liferay.portlet.ratings.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class RatingsStatsSoap implements Serializable {
	public static RatingsStatsSoap toSoapModel(RatingsStats model) {
		RatingsStatsSoap soapModel = new RatingsStatsSoap();

		soapModel.setStatsId(model.getStatsId());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setTotalEntries(model.getTotalEntries());
		soapModel.setTotalScore(model.getTotalScore());
		soapModel.setAverageScore(model.getAverageScore());

		return soapModel;
	}

	public static RatingsStatsSoap[] toSoapModels(RatingsStats[] models) {
		RatingsStatsSoap[] soapModels = new RatingsStatsSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static RatingsStatsSoap[][] toSoapModels(RatingsStats[][] models) {
		RatingsStatsSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new RatingsStatsSoap[models.length][models[0].length];
		}
		else {
			soapModels = new RatingsStatsSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static RatingsStatsSoap[] toSoapModels(List<RatingsStats> models) {
		List<RatingsStatsSoap> soapModels = new ArrayList<RatingsStatsSoap>(models.size());

		for (RatingsStats model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new RatingsStatsSoap[soapModels.size()]);
	}

	public RatingsStatsSoap() {
	}

	public long getPrimaryKey() {
		return _statsId;
	}

	public void setPrimaryKey(long pk) {
		setStatsId(pk);
	}

	public long getStatsId() {
		return _statsId;
	}

	public void setStatsId(long statsId) {
		_statsId = statsId;
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public int getTotalEntries() {
		return _totalEntries;
	}

	public void setTotalEntries(int totalEntries) {
		_totalEntries = totalEntries;
	}

	public double getTotalScore() {
		return _totalScore;
	}

	public void setTotalScore(double totalScore) {
		_totalScore = totalScore;
	}

	public double getAverageScore() {
		return _averageScore;
	}

	public void setAverageScore(double averageScore) {
		_averageScore = averageScore;
	}

	private long _statsId;
	private long _classNameId;
	private long _classPK;
	private int _totalEntries;
	private double _totalScore;
	private double _averageScore;
}