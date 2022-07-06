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
 * This class is a wrapper for {@link ObjectStateFlow}.
 * </p>
 *
 * @author Marco Leo
 * @see ObjectStateFlow
 * @generated
 */
public class ObjectStateFlowWrapper
	extends BaseModelWrapper<ObjectStateFlow>
	implements ModelWrapper<ObjectStateFlow>, ObjectStateFlow {

	public ObjectStateFlowWrapper(ObjectStateFlow objectStateFlow) {
		super(objectStateFlow);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("objectStateFlowId", getObjectStateFlowId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("objectFieldId", getObjectFieldId());

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

		Long objectStateFlowId = (Long)attributes.get("objectStateFlowId");

		if (objectStateFlowId != null) {
			setObjectStateFlowId(objectStateFlowId);
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

		Long objectFieldId = (Long)attributes.get("objectFieldId");

		if (objectFieldId != null) {
			setObjectFieldId(objectFieldId);
		}
	}

	@Override
	public ObjectStateFlow cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this object state flow.
	 *
	 * @return the company ID of this object state flow
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this object state flow.
	 *
	 * @return the create date of this object state flow
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the modified date of this object state flow.
	 *
	 * @return the modified date of this object state flow
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this object state flow.
	 *
	 * @return the mvcc version of this object state flow
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the object field ID of this object state flow.
	 *
	 * @return the object field ID of this object state flow
	 */
	@Override
	public long getObjectFieldId() {
		return model.getObjectFieldId();
	}

	/**
	 * Returns the object state flow ID of this object state flow.
	 *
	 * @return the object state flow ID of this object state flow
	 */
	@Override
	public long getObjectStateFlowId() {
		return model.getObjectStateFlowId();
	}

	@Override
	public java.util.List<ObjectState> getObjectStates() {
		return model.getObjectStates();
	}

	/**
	 * Returns the primary key of this object state flow.
	 *
	 * @return the primary key of this object state flow
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this object state flow.
	 *
	 * @return the user ID of this object state flow
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this object state flow.
	 *
	 * @return the user name of this object state flow
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this object state flow.
	 *
	 * @return the user uuid of this object state flow
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this object state flow.
	 *
	 * @return the uuid of this object state flow
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
	 * Sets the company ID of this object state flow.
	 *
	 * @param companyId the company ID of this object state flow
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this object state flow.
	 *
	 * @param createDate the create date of this object state flow
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the modified date of this object state flow.
	 *
	 * @param modifiedDate the modified date of this object state flow
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this object state flow.
	 *
	 * @param mvccVersion the mvcc version of this object state flow
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the object field ID of this object state flow.
	 *
	 * @param objectFieldId the object field ID of this object state flow
	 */
	@Override
	public void setObjectFieldId(long objectFieldId) {
		model.setObjectFieldId(objectFieldId);
	}

	/**
	 * Sets the object state flow ID of this object state flow.
	 *
	 * @param objectStateFlowId the object state flow ID of this object state flow
	 */
	@Override
	public void setObjectStateFlowId(long objectStateFlowId) {
		model.setObjectStateFlowId(objectStateFlowId);
	}

	@Override
	public void setObjectStates(java.util.List<ObjectState> objectStates) {
		model.setObjectStates(objectStates);
	}

	/**
	 * Sets the primary key of this object state flow.
	 *
	 * @param primaryKey the primary key of this object state flow
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this object state flow.
	 *
	 * @param userId the user ID of this object state flow
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this object state flow.
	 *
	 * @param userName the user name of this object state flow
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this object state flow.
	 *
	 * @param userUuid the user uuid of this object state flow
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this object state flow.
	 *
	 * @param uuid the uuid of this object state flow
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
	protected ObjectStateFlowWrapper wrap(ObjectStateFlow objectStateFlow) {
		return new ObjectStateFlowWrapper(objectStateFlow);
	}

}