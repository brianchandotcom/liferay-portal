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

package com.liferay.ratings.kernel.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

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
public class RatingsStatsWrapper
	extends BaseModelWrapper<RatingsStats>
	implements ModelWrapper<RatingsStats>, RatingsStats {

	public RatingsStatsWrapper(RatingsStats ratingsStats) {
		super(ratingsStats);
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

	/**
	 * Returns the average score of this ratings stats.
	 *
	 * @return the average score of this ratings stats
	 */
	@Override
	public double getAverageScore() {
		return model.getAverageScore();
	}

	/**
	 * Returns the fully qualified class name of this ratings stats.
	 *
	 * @return the fully qualified class name of this ratings stats
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this ratings stats.
	 *
	 * @return the class name ID of this ratings stats
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this ratings stats.
	 *
	 * @return the class pk of this ratings stats
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the company ID of this ratings stats.
	 *
	 * @return the company ID of this ratings stats
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the primary key of this ratings stats.
	 *
	 * @return the primary key of this ratings stats
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the stats ID of this ratings stats.
	 *
	 * @return the stats ID of this ratings stats
	 */
	@Override
	public long getStatsId() {
		return model.getStatsId();
	}

	/**
	 * Returns the total entries of this ratings stats.
	 *
	 * @return the total entries of this ratings stats
	 */
	@Override
	public int getTotalEntries() {
		return model.getTotalEntries();
	}

	/**
	 * Returns the total score of this ratings stats.
	 *
	 * @return the total score of this ratings stats
	 */
	@Override
	public double getTotalScore() {
		return model.getTotalScore();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a ratings stats model instance should use the <code>RatingsStats</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the average score of this ratings stats.
	 *
	 * @param averageScore the average score of this ratings stats
	 */
	@Override
	public void setAverageScore(double averageScore) {
		model.setAverageScore(averageScore);
	}

	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	 * Sets the class name ID of this ratings stats.
	 *
	 * @param classNameId the class name ID of this ratings stats
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this ratings stats.
	 *
	 * @param classPK the class pk of this ratings stats
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the company ID of this ratings stats.
	 *
	 * @param companyId the company ID of this ratings stats
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the primary key of this ratings stats.
	 *
	 * @param primaryKey the primary key of this ratings stats
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the stats ID of this ratings stats.
	 *
	 * @param statsId the stats ID of this ratings stats
	 */
	@Override
	public void setStatsId(long statsId) {
		model.setStatsId(statsId);
	}

	/**
	 * Sets the total entries of this ratings stats.
	 *
	 * @param totalEntries the total entries of this ratings stats
	 */
	@Override
	public void setTotalEntries(int totalEntries) {
		model.setTotalEntries(totalEntries);
	}

	/**
	 * Sets the total score of this ratings stats.
	 *
	 * @param totalScore the total score of this ratings stats
	 */
	@Override
	public void setTotalScore(double totalScore) {
		model.setTotalScore(totalScore);
	}

	@Override
	protected RatingsStatsWrapper wrap(RatingsStats ratingsStats) {
		return new RatingsStatsWrapper(ratingsStats);
	}

}