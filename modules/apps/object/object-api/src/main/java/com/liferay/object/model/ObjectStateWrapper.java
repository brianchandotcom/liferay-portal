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

package com.liferay.object.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link ObjectState}.
 * </p>
 *
 * @author Marco Leo
 * @see ObjectState
 * @generated
 */
public class ObjectStateWrapper
	extends BaseModelWrapper<ObjectState>
	implements ModelWrapper<ObjectState>, ObjectState {

	public ObjectStateWrapper(ObjectState objectState) {
		super(objectState);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("objectStateId", getObjectStateId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("listTypeEntryId", getListTypeEntryId());
		attributes.put("objectStateFlowId", getObjectStateFlowId());

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

		Long objectStateId = (Long)attributes.get("objectStateId");

		if (objectStateId != null) {
			setObjectStateId(objectStateId);
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

		Long listTypeEntryId = (Long)attributes.get("listTypeEntryId");

		if (listTypeEntryId != null) {
			setListTypeEntryId(listTypeEntryId);
		}

		Long objectStateFlowId = (Long)attributes.get("objectStateFlowId");

		if (objectStateFlowId != null) {
			setObjectStateFlowId(objectStateFlowId);
		}
	}

	@Override
	public ObjectState cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this object state.
	 *
	 * @return the company ID of this object state
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this object state.
	 *
	 * @return the create date of this object state
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the list type entry ID of this object state.
	 *
	 * @return the list type entry ID of this object state
	 */
	@Override
	public long getListTypeEntryId() {
		return model.getListTypeEntryId();
	}

	/**
	 * Returns the modified date of this object state.
	 *
	 * @return the modified date of this object state
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this object state.
	 *
	 * @return the mvcc version of this object state
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the object state flow ID of this object state.
	 *
	 * @return the object state flow ID of this object state
	 */
	@Override
	public long getObjectStateFlowId() {
		return model.getObjectStateFlowId();
	}

	/**
	 * Returns the object state ID of this object state.
	 *
	 * @return the object state ID of this object state
	 */
	@Override
	public long getObjectStateId() {
		return model.getObjectStateId();
	}

	@Override
	public java.util.List<ObjectStateTransition> getObjectStateTransitions() {
		return model.getObjectStateTransitions();
	}

	/**
	 * Returns the primary key of this object state.
	 *
	 * @return the primary key of this object state
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this object state.
	 *
	 * @return the user ID of this object state
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this object state.
	 *
	 * @return the user name of this object state
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this object state.
	 *
	 * @return the user uuid of this object state
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this object state.
	 *
	 * @return the uuid of this object state
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
	 * Sets the company ID of this object state.
	 *
	 * @param companyId the company ID of this object state
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this object state.
	 *
	 * @param createDate the create date of this object state
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the list type entry ID of this object state.
	 *
	 * @param listTypeEntryId the list type entry ID of this object state
	 */
	@Override
	public void setListTypeEntryId(long listTypeEntryId) {
		model.setListTypeEntryId(listTypeEntryId);
	}

	/**
	 * Sets the modified date of this object state.
	 *
	 * @param modifiedDate the modified date of this object state
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this object state.
	 *
	 * @param mvccVersion the mvcc version of this object state
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the object state flow ID of this object state.
	 *
	 * @param objectStateFlowId the object state flow ID of this object state
	 */
	@Override
	public void setObjectStateFlowId(long objectStateFlowId) {
		model.setObjectStateFlowId(objectStateFlowId);
	}

	/**
	 * Sets the object state ID of this object state.
	 *
	 * @param objectStateId the object state ID of this object state
	 */
	@Override
	public void setObjectStateId(long objectStateId) {
		model.setObjectStateId(objectStateId);
	}

	@Override
	public void setObjectStateTransitions(
		java.util.List<ObjectStateTransition> objectStateTransitions) {

		model.setObjectStateTransitions(objectStateTransitions);
	}

	/**
	 * Sets the primary key of this object state.
	 *
	 * @param primaryKey the primary key of this object state
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this object state.
	 *
	 * @param userId the user ID of this object state
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this object state.
	 *
	 * @param userName the user name of this object state
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this object state.
	 *
	 * @param userUuid the user uuid of this object state
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this object state.
	 *
	 * @param uuid the uuid of this object state
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
	protected ObjectStateWrapper wrap(ObjectState objectState) {
		return new ObjectStateWrapper(objectState);
	}

}