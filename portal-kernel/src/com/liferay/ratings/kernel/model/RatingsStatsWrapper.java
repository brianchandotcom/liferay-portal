/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ratings.kernel.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

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

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("statsId", getStatsId());
		attributes.put("companyId", getCompanyId());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("totalEntries", getTotalEntries());
		attributes.put("totalScore", getTotalScore());
		attributes.put("averageScore", getAverageScore());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long ctCollectionId = (Long)attributes.get("ctCollectionId");

		if (ctCollectionId != null) {
			setCtCollectionId(ctCollectionId);
		}

		Long statsId = (Long)attributes.get("statsId");

		if (statsId != null) {
			setStatsId(statsId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
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
	public RatingsStats cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
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
	 * Returns the create date of this ratings stats.
	 *
	 * @return the create date of this ratings stats
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this ratings stats.
	 *
	 * @return the ct collection ID of this ratings stats
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the modified date of this ratings stats.
	 *
	 * @return the modified date of this ratings stats
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this ratings stats.
	 *
	 * @return the mvcc version of this ratings stats
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
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
	 * Sets the create date of this ratings stats.
	 *
	 * @param createDate the create date of this ratings stats
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this ratings stats.
	 *
	 * @param ctCollectionId the ct collection ID of this ratings stats
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the modified date of this ratings stats.
	 *
	 * @param modifiedDate the modified date of this ratings stats
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this ratings stats.
	 *
	 * @param mvccVersion the mvcc version of this ratings stats
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
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
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	public Map<String, Function<RatingsStats, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<RatingsStats, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	protected RatingsStatsWrapper wrap(RatingsStats ratingsStats) {
		return new RatingsStatsWrapper(ratingsStats);
	}

}