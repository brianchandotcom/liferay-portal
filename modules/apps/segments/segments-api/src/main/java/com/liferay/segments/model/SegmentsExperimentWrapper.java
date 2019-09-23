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

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link SegmentsExperiment}.
 * </p>
 *
 * @author Eduardo Garcia
 * @see SegmentsExperiment
 * @generated
 */
public class SegmentsExperimentWrapper
	extends BaseModelWrapper<SegmentsExperiment>
	implements ModelWrapper<SegmentsExperiment>, SegmentsExperiment {

	public SegmentsExperimentWrapper(SegmentsExperiment segmentsExperiment) {
		super(segmentsExperiment);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("segmentsExperimentId", getSegmentsExperimentId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("segmentsEntryId", getSegmentsEntryId());
		attributes.put("segmentsExperienceId", getSegmentsExperienceId());
		attributes.put("segmentsExperimentKey", getSegmentsExperimentKey());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("typeSettings", getTypeSettings());
		attributes.put("status", getStatus());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long segmentsExperimentId = (Long)attributes.get(
			"segmentsExperimentId");

		if (segmentsExperimentId != null) {
			setSegmentsExperimentId(segmentsExperimentId);
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

		Long segmentsEntryId = (Long)attributes.get("segmentsEntryId");

		if (segmentsEntryId != null) {
			setSegmentsEntryId(segmentsEntryId);
		}

		Long segmentsExperienceId = (Long)attributes.get(
			"segmentsExperienceId");

		if (segmentsExperienceId != null) {
			setSegmentsExperienceId(segmentsExperienceId);
		}

		String segmentsExperimentKey = (String)attributes.get(
			"segmentsExperimentKey");

		if (segmentsExperimentKey != null) {
			setSegmentsExperimentKey(segmentsExperimentKey);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String typeSettings = (String)attributes.get("typeSettings");

		if (typeSettings != null) {
			setTypeSettings(typeSettings);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}
	}

	/**
	 * Returns the fully qualified class name of this segments experiment.
	 *
	 * @return the fully qualified class name of this segments experiment
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this segments experiment.
	 *
	 * @return the class name ID of this segments experiment
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this segments experiment.
	 *
	 * @return the class pk of this segments experiment
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the company ID of this segments experiment.
	 *
	 * @return the company ID of this segments experiment
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	@Override
	public double getConfidenceLevel() {
		return model.getConfidenceLevel();
	}

	/**
	 * Returns the create date of this segments experiment.
	 *
	 * @return the create date of this segments experiment
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the description of this segments experiment.
	 *
	 * @return the description of this segments experiment
	 */
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	@Override
	public String getGoal() {
		return model.getGoal();
	}

	@Override
	public String getGoalTarget() {
		return model.getGoalTarget();
	}

	/**
	 * Returns the group ID of this segments experiment.
	 *
	 * @return the group ID of this segments experiment
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this segments experiment.
	 *
	 * @return the modified date of this segments experiment
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this segments experiment.
	 *
	 * @return the mvcc version of this segments experiment
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this segments experiment.
	 *
	 * @return the name of this segments experiment
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this segments experiment.
	 *
	 * @return the primary key of this segments experiment
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the segments entry ID of this segments experiment.
	 *
	 * @return the segments entry ID of this segments experiment
	 */
	@Override
	public long getSegmentsEntryId() {
		return model.getSegmentsEntryId();
	}

	@Override
	public String getSegmentsEntryName(java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getSegmentsEntryName(locale);
	}

	/**
	 * Returns the segments experience ID of this segments experiment.
	 *
	 * @return the segments experience ID of this segments experiment
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
	 * Returns the segments experiment ID of this segments experiment.
	 *
	 * @return the segments experiment ID of this segments experiment
	 */
	@Override
	public long getSegmentsExperimentId() {
		return model.getSegmentsExperimentId();
	}

	/**
	 * Returns the segments experiment key of this segments experiment.
	 *
	 * @return the segments experiment key of this segments experiment
	 */
	@Override
	public String getSegmentsExperimentKey() {
		return model.getSegmentsExperimentKey();
	}

	@Override
	public java.util.List<SegmentsExperimentRel> getSegmentsExperimentRels() {
		return model.getSegmentsExperimentRels();
	}

	/**
	 * Returns the status of this segments experiment.
	 *
	 * @return the status of this segments experiment
	 */
	@Override
	public int getStatus() {
		return model.getStatus();
	}

	/**
	 * Returns the type settings of this segments experiment.
	 *
	 * @return the type settings of this segments experiment
	 */
	@Override
	public String getTypeSettings() {
		return model.getTypeSettings();
	}

	@Override
	public com.liferay.portal.kernel.util.UnicodeProperties
		getTypeSettingsProperties() {

		return model.getTypeSettingsProperties();
	}

	/**
	 * Returns the user ID of this segments experiment.
	 *
	 * @return the user ID of this segments experiment
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this segments experiment.
	 *
	 * @return the user name of this segments experiment
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this segments experiment.
	 *
	 * @return the user uuid of this segments experiment
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this segments experiment.
	 *
	 * @return the uuid of this segments experiment
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	@Override
	public long getWinnerSegmentsExperienceId() {
		return model.getWinnerSegmentsExperienceId();
	}

	@Override
	public String getWinnerSegmentsExperienceKey() {
		return model.getWinnerSegmentsExperienceKey();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a segments experiment model instance should use the <code>SegmentsExperiment</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	 * Sets the class name ID of this segments experiment.
	 *
	 * @param classNameId the class name ID of this segments experiment
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this segments experiment.
	 *
	 * @param classPK the class pk of this segments experiment
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the company ID of this segments experiment.
	 *
	 * @param companyId the company ID of this segments experiment
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this segments experiment.
	 *
	 * @param createDate the create date of this segments experiment
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the description of this segments experiment.
	 *
	 * @param description the description of this segments experiment
	 */
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	 * Sets the group ID of this segments experiment.
	 *
	 * @param groupId the group ID of this segments experiment
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this segments experiment.
	 *
	 * @param modifiedDate the modified date of this segments experiment
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this segments experiment.
	 *
	 * @param mvccVersion the mvcc version of this segments experiment
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this segments experiment.
	 *
	 * @param name the name of this segments experiment
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this segments experiment.
	 *
	 * @param primaryKey the primary key of this segments experiment
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the segments entry ID of this segments experiment.
	 *
	 * @param segmentsEntryId the segments entry ID of this segments experiment
	 */
	@Override
	public void setSegmentsEntryId(long segmentsEntryId) {
		model.setSegmentsEntryId(segmentsEntryId);
	}

	/**
	 * Sets the segments experience ID of this segments experiment.
	 *
	 * @param segmentsExperienceId the segments experience ID of this segments experiment
	 */
	@Override
	public void setSegmentsExperienceId(long segmentsExperienceId) {
		model.setSegmentsExperienceId(segmentsExperienceId);
	}

	/**
	 * Sets the segments experiment ID of this segments experiment.
	 *
	 * @param segmentsExperimentId the segments experiment ID of this segments experiment
	 */
	@Override
	public void setSegmentsExperimentId(long segmentsExperimentId) {
		model.setSegmentsExperimentId(segmentsExperimentId);
	}

	/**
	 * Sets the segments experiment key of this segments experiment.
	 *
	 * @param segmentsExperimentKey the segments experiment key of this segments experiment
	 */
	@Override
	public void setSegmentsExperimentKey(String segmentsExperimentKey) {
		model.setSegmentsExperimentKey(segmentsExperimentKey);
	}

	/**
	 * Sets the status of this segments experiment.
	 *
	 * @param status the status of this segments experiment
	 */
	@Override
	public void setStatus(int status) {
		model.setStatus(status);
	}

	/**
	 * Sets the type settings of this segments experiment.
	 *
	 * @param typeSettings the type settings of this segments experiment
	 */
	@Override
	public void setTypeSettings(String typeSettings) {
		model.setTypeSettings(typeSettings);
	}

	/**
	 * Sets the user ID of this segments experiment.
	 *
	 * @param userId the user ID of this segments experiment
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this segments experiment.
	 *
	 * @param userName the user name of this segments experiment
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this segments experiment.
	 *
	 * @param userUuid the user uuid of this segments experiment
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this segments experiment.
	 *
	 * @param uuid the uuid of this segments experiment
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected SegmentsExperimentWrapper wrap(
		SegmentsExperiment segmentsExperiment) {

		return new SegmentsExperimentWrapper(segmentsExperiment);
	}

}