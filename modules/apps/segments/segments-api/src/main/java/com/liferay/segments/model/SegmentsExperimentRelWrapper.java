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

package com.liferay.segments.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link SegmentsExperimentRel}.
 * </p>
 *
 * @author Eduardo Garcia
 * @see SegmentsExperimentRel
 * @generated
 */
public class SegmentsExperimentRelWrapper
	extends BaseModelWrapper<SegmentsExperimentRel>
	implements ModelWrapper<SegmentsExperimentRel>, SegmentsExperimentRel {

	public SegmentsExperimentRelWrapper(
		SegmentsExperimentRel segmentsExperimentRel) {

		super(segmentsExperimentRel);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("segmentsExperimentRelId", getSegmentsExperimentRelId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("segmentsExperimentId", getSegmentsExperimentId());
		attributes.put("segmentsExperienceId", getSegmentsExperienceId());
		attributes.put("split", getSplit());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long segmentsExperimentRelId = (Long)attributes.get(
			"segmentsExperimentRelId");

		if (segmentsExperimentRelId != null) {
			setSegmentsExperimentRelId(segmentsExperimentRelId);
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

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Long segmentsExperimentId = (Long)attributes.get(
			"segmentsExperimentId");

		if (segmentsExperimentId != null) {
			setSegmentsExperimentId(segmentsExperimentId);
		}

		Long segmentsExperienceId = (Long)attributes.get(
			"segmentsExperienceId");

		if (segmentsExperienceId != null) {
			setSegmentsExperienceId(segmentsExperienceId);
		}

		Double split = (Double)attributes.get("split");

		if (split != null) {
			setSplit(split);
		}
	}

	/**
	 * Returns the company ID of this segments experiment rel.
	 *
	 * @return the company ID of this segments experiment rel
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this segments experiment rel.
	 *
	 * @return the create date of this segments experiment rel
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the group ID of this segments experiment rel.
	 *
	 * @return the group ID of this segments experiment rel
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this segments experiment rel.
	 *
	 * @return the modified date of this segments experiment rel
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this segments experiment rel.
	 *
	 * @return the mvcc version of this segments experiment rel
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	@Override
	public String getName(java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getName(locale);
	}

	/**
	 * Returns the primary key of this segments experiment rel.
	 *
	 * @return the primary key of this segments experiment rel
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the segments experience ID of this segments experiment rel.
	 *
	 * @return the segments experience ID of this segments experiment rel
	 */
	@Override
	public long getSegmentsExperienceId() {
		return model.getSegmentsExperienceId();
	}

	@Override
	public String getSegmentsExperienceKey() {
		return model.getSegmentsExperienceKey();
	}

	/**
	 * Returns the segments experiment ID of this segments experiment rel.
	 *
	 * @return the segments experiment ID of this segments experiment rel
	 */
	@Override
	public long getSegmentsExperimentId() {
		return model.getSegmentsExperimentId();
	}

	@Override
	public String getSegmentsExperimentKey()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getSegmentsExperimentKey();
	}

	/**
	 * Returns the segments experiment rel ID of this segments experiment rel.
	 *
	 * @return the segments experiment rel ID of this segments experiment rel
	 */
	@Override
	public long getSegmentsExperimentRelId() {
		return model.getSegmentsExperimentRelId();
	}

	/**
	 * Returns the split of this segments experiment rel.
	 *
	 * @return the split of this segments experiment rel
	 */
	@Override
	public double getSplit() {
		return model.getSplit();
	}

	/**
	 * Returns the user ID of this segments experiment rel.
	 *
	 * @return the user ID of this segments experiment rel
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this segments experiment rel.
	 *
	 * @return the user name of this segments experiment rel
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this segments experiment rel.
	 *
	 * @return the user uuid of this segments experiment rel
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	@Override
	public boolean isActive()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.isActive();
	}

	@Override
	public boolean isControl()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.isControl();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a segments experiment rel model instance should use the <code>SegmentsExperimentRel</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this segments experiment rel.
	 *
	 * @param companyId the company ID of this segments experiment rel
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this segments experiment rel.
	 *
	 * @param createDate the create date of this segments experiment rel
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the group ID of this segments experiment rel.
	 *
	 * @param groupId the group ID of this segments experiment rel
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this segments experiment rel.
	 *
	 * @param modifiedDate the modified date of this segments experiment rel
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this segments experiment rel.
	 *
	 * @param mvccVersion the mvcc version of this segments experiment rel
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this segments experiment rel.
	 *
	 * @param primaryKey the primary key of this segments experiment rel
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the segments experience ID of this segments experiment rel.
	 *
	 * @param segmentsExperienceId the segments experience ID of this segments experiment rel
	 */
	@Override
	public void setSegmentsExperienceId(long segmentsExperienceId) {
		model.setSegmentsExperienceId(segmentsExperienceId);
	}

	/**
	 * Sets the segments experiment ID of this segments experiment rel.
	 *
	 * @param segmentsExperimentId the segments experiment ID of this segments experiment rel
	 */
	@Override
	public void setSegmentsExperimentId(long segmentsExperimentId) {
		model.setSegmentsExperimentId(segmentsExperimentId);
	}

	/**
	 * Sets the segments experiment rel ID of this segments experiment rel.
	 *
	 * @param segmentsExperimentRelId the segments experiment rel ID of this segments experiment rel
	 */
	@Override
	public void setSegmentsExperimentRelId(long segmentsExperimentRelId) {
		model.setSegmentsExperimentRelId(segmentsExperimentRelId);
	}

	/**
	 * Sets the split of this segments experiment rel.
	 *
	 * @param split the split of this segments experiment rel
	 */
	@Override
	public void setSplit(double split) {
		model.setSplit(split);
	}

	/**
	 * Sets the user ID of this segments experiment rel.
	 *
	 * @param userId the user ID of this segments experiment rel
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this segments experiment rel.
	 *
	 * @param userName the user name of this segments experiment rel
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this segments experiment rel.
	 *
	 * @param userUuid the user uuid of this segments experiment rel
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected SegmentsExperimentRelWrapper wrap(
		SegmentsExperimentRel segmentsExperimentRel) {

		return new SegmentsExperimentRelWrapper(segmentsExperimentRel);
	}

}