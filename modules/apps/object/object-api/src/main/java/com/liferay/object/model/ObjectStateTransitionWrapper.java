/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link ObjectStateTransition}.
 * </p>
 *
 * @author Marco Leo
 * @see ObjectStateTransition
 * @generated
 */
public class ObjectStateTransitionWrapper
	extends BaseModelWrapper<ObjectStateTransition>
	implements ModelWrapper<ObjectStateTransition>, ObjectStateTransition {

	public ObjectStateTransitionWrapper(
		ObjectStateTransition objectStateTransition) {

		super(objectStateTransition);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("objectStateTransitionId", getObjectStateTransitionId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("objectStateFlowId", getObjectStateFlowId());
		attributes.put("sourceObjectStateId", getSourceObjectStateId());
		attributes.put("targetObjectStateId", getTargetObjectStateId());

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

		Long objectStateTransitionId = (Long)attributes.get(
			"objectStateTransitionId");

		if (objectStateTransitionId != null) {
			setObjectStateTransitionId(objectStateTransitionId);
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

		Long objectStateFlowId = (Long)attributes.get("objectStateFlowId");

		if (objectStateFlowId != null) {
			setObjectStateFlowId(objectStateFlowId);
		}

		Long sourceObjectStateId = (Long)attributes.get("sourceObjectStateId");

		if (sourceObjectStateId != null) {
			setSourceObjectStateId(sourceObjectStateId);
		}

		Long targetObjectStateId = (Long)attributes.get("targetObjectStateId");

		if (targetObjectStateId != null) {
			setTargetObjectStateId(targetObjectStateId);
		}
	}

	@Override
	public ObjectStateTransition cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this object state transition.
	 *
	 * @return the company ID of this object state transition
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this object state transition.
	 *
	 * @return the create date of this object state transition
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the modified date of this object state transition.
	 *
	 * @return the modified date of this object state transition
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this object state transition.
	 *
	 * @return the mvcc version of this object state transition
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the object state flow ID of this object state transition.
	 *
	 * @return the object state flow ID of this object state transition
	 */
	@Override
	public long getObjectStateFlowId() {
		return model.getObjectStateFlowId();
	}

	/**
	 * Returns the object state transition ID of this object state transition.
	 *
	 * @return the object state transition ID of this object state transition
	 */
	@Override
	public long getObjectStateTransitionId() {
		return model.getObjectStateTransitionId();
	}

	/**
	 * Returns the primary key of this object state transition.
	 *
	 * @return the primary key of this object state transition
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the source object state ID of this object state transition.
	 *
	 * @return the source object state ID of this object state transition
	 */
	@Override
	public long getSourceObjectStateId() {
		return model.getSourceObjectStateId();
	}

	/**
	 * Returns the target object state ID of this object state transition.
	 *
	 * @return the target object state ID of this object state transition
	 */
	@Override
	public long getTargetObjectStateId() {
		return model.getTargetObjectStateId();
	}

	@Override
	public long getTargetObjectStateListTypeEntryId() {
		return model.getTargetObjectStateListTypeEntryId();
	}

	/**
	 * Returns the user ID of this object state transition.
	 *
	 * @return the user ID of this object state transition
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this object state transition.
	 *
	 * @return the user name of this object state transition
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this object state transition.
	 *
	 * @return the user uuid of this object state transition
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this object state transition.
	 *
	 * @return the uuid of this object state transition
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this object state transition.
	 *
	 * @param companyId the company ID of this object state transition
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this object state transition.
	 *
	 * @param createDate the create date of this object state transition
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the modified date of this object state transition.
	 *
	 * @param modifiedDate the modified date of this object state transition
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this object state transition.
	 *
	 * @param mvccVersion the mvcc version of this object state transition
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the object state flow ID of this object state transition.
	 *
	 * @param objectStateFlowId the object state flow ID of this object state transition
	 */
	@Override
	public void setObjectStateFlowId(long objectStateFlowId) {
		model.setObjectStateFlowId(objectStateFlowId);
	}

	/**
	 * Sets the object state transition ID of this object state transition.
	 *
	 * @param objectStateTransitionId the object state transition ID of this object state transition
	 */
	@Override
	public void setObjectStateTransitionId(long objectStateTransitionId) {
		model.setObjectStateTransitionId(objectStateTransitionId);
	}

	/**
	 * Sets the primary key of this object state transition.
	 *
	 * @param primaryKey the primary key of this object state transition
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the source object state ID of this object state transition.
	 *
	 * @param sourceObjectStateId the source object state ID of this object state transition
	 */
	@Override
	public void setSourceObjectStateId(long sourceObjectStateId) {
		model.setSourceObjectStateId(sourceObjectStateId);
	}

	/**
	 * Sets the target object state ID of this object state transition.
	 *
	 * @param targetObjectStateId the target object state ID of this object state transition
	 */
	@Override
	public void setTargetObjectStateId(long targetObjectStateId) {
		model.setTargetObjectStateId(targetObjectStateId);
	}

	@Override
	public void setTargetObjectStateListTypeEntryId(
		long targetObjectStateListTypeEntryId) {

		model.setTargetObjectStateListTypeEntryId(
			targetObjectStateListTypeEntryId);
	}

	/**
	 * Sets the user ID of this object state transition.
	 *
	 * @param userId the user ID of this object state transition
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this object state transition.
	 *
	 * @param userName the user name of this object state transition
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this object state transition.
	 *
	 * @param userUuid the user uuid of this object state transition
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this object state transition.
	 *
	 * @param uuid the uuid of this object state transition
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected ObjectStateTransitionWrapper wrap(
		ObjectStateTransition objectStateTransition) {

		return new ObjectStateTransitionWrapper(objectStateTransition);
	}

}