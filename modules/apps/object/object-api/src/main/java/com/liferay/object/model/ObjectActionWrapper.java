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
 * This class is a wrapper for {@link ObjectAction}.
 * </p>
 *
 * @author Marco Leo
 * @see ObjectAction
 * @generated
 */
public class ObjectActionWrapper
	extends BaseModelWrapper<ObjectAction>
	implements ModelWrapper<ObjectAction>, ObjectAction {

	public ObjectActionWrapper(ObjectAction objectAction) {
		super(objectAction);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("objectActionId", getObjectActionId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("objectDefinitionId", getObjectDefinitionId());
		attributes.put("active", isActive());
		attributes.put("conditionExpression", getConditionExpression());
		attributes.put("description", getDescription());
		attributes.put("name", getName());
		attributes.put("objectActionExecutorKey", getObjectActionExecutorKey());
		attributes.put("objectActionTriggerKey", getObjectActionTriggerKey());
		attributes.put("parameters", getParameters());
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

		Long objectActionId = (Long)attributes.get("objectActionId");

		if (objectActionId != null) {
			setObjectActionId(objectActionId);
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

		Long objectDefinitionId = (Long)attributes.get("objectDefinitionId");

		if (objectDefinitionId != null) {
			setObjectDefinitionId(objectDefinitionId);
		}

		Boolean active = (Boolean)attributes.get("active");

		if (active != null) {
			setActive(active);
		}

		String conditionExpression = (String)attributes.get(
			"conditionExpression");

		if (conditionExpression != null) {
			setConditionExpression(conditionExpression);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String objectActionExecutorKey = (String)attributes.get(
			"objectActionExecutorKey");

		if (objectActionExecutorKey != null) {
			setObjectActionExecutorKey(objectActionExecutorKey);
		}

		String objectActionTriggerKey = (String)attributes.get(
			"objectActionTriggerKey");

		if (objectActionTriggerKey != null) {
			setObjectActionTriggerKey(objectActionTriggerKey);
		}

		String parameters = (String)attributes.get("parameters");

		if (parameters != null) {
			setParameters(parameters);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}
	}

	@Override
	public ObjectAction cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the active of this object action.
	 *
	 * @return the active of this object action
	 */
	@Override
	public boolean getActive() {
		return model.getActive();
	}

	/**
	 * Returns the company ID of this object action.
	 *
	 * @return the company ID of this object action
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the condition expression of this object action.
	 *
	 * @return the condition expression of this object action
	 */
	@Override
	public String getConditionExpression() {
		return model.getConditionExpression();
	}

	/**
	 * Returns the create date of this object action.
	 *
	 * @return the create date of this object action
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the description of this object action.
	 *
	 * @return the description of this object action
	 */
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	 * Returns the modified date of this object action.
	 *
	 * @return the modified date of this object action
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this object action.
	 *
	 * @return the mvcc version of this object action
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this object action.
	 *
	 * @return the name of this object action
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the object action executor key of this object action.
	 *
	 * @return the object action executor key of this object action
	 */
	@Override
	public String getObjectActionExecutorKey() {
		return model.getObjectActionExecutorKey();
	}

	/**
	 * Returns the object action ID of this object action.
	 *
	 * @return the object action ID of this object action
	 */
	@Override
	public long getObjectActionId() {
		return model.getObjectActionId();
	}

	/**
	 * Returns the object action trigger key of this object action.
	 *
	 * @return the object action trigger key of this object action
	 */
	@Override
	public String getObjectActionTriggerKey() {
		return model.getObjectActionTriggerKey();
	}

	/**
	 * Returns the object definition ID of this object action.
	 *
	 * @return the object definition ID of this object action
	 */
	@Override
	public long getObjectDefinitionId() {
		return model.getObjectDefinitionId();
	}

	/**
	 * Returns the parameters of this object action.
	 *
	 * @return the parameters of this object action
	 */
	@Override
	public String getParameters() {
		return model.getParameters();
	}

	@Override
	public com.liferay.portal.kernel.util.UnicodeProperties
		getParametersUnicodeProperties() {

		return model.getParametersUnicodeProperties();
	}

	/**
	 * Returns the primary key of this object action.
	 *
	 * @return the primary key of this object action
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the status of this object action.
	 *
	 * @return the status of this object action
	 */
	@Override
	public int getStatus() {
		return model.getStatus();
	}

	/**
	 * Returns the user ID of this object action.
	 *
	 * @return the user ID of this object action
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this object action.
	 *
	 * @return the user name of this object action
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this object action.
	 *
	 * @return the user uuid of this object action
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this object action.
	 *
	 * @return the uuid of this object action
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns <code>true</code> if this object action is active.
	 *
	 * @return <code>true</code> if this object action is active; <code>false</code> otherwise
	 */
	@Override
	public boolean isActive() {
		return model.isActive();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets whether this object action is active.
	 *
	 * @param active the active of this object action
	 */
	@Override
	public void setActive(boolean active) {
		model.setActive(active);
	}

	/**
	 * Sets the company ID of this object action.
	 *
	 * @param companyId the company ID of this object action
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the condition expression of this object action.
	 *
	 * @param conditionExpression the condition expression of this object action
	 */
	@Override
	public void setConditionExpression(String conditionExpression) {
		model.setConditionExpression(conditionExpression);
	}

	/**
	 * Sets the create date of this object action.
	 *
	 * @param createDate the create date of this object action
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the description of this object action.
	 *
	 * @param description the description of this object action
	 */
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	 * Sets the modified date of this object action.
	 *
	 * @param modifiedDate the modified date of this object action
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this object action.
	 *
	 * @param mvccVersion the mvcc version of this object action
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this object action.
	 *
	 * @param name the name of this object action
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the object action executor key of this object action.
	 *
	 * @param objectActionExecutorKey the object action executor key of this object action
	 */
	@Override
	public void setObjectActionExecutorKey(String objectActionExecutorKey) {
		model.setObjectActionExecutorKey(objectActionExecutorKey);
	}

	/**
	 * Sets the object action ID of this object action.
	 *
	 * @param objectActionId the object action ID of this object action
	 */
	@Override
	public void setObjectActionId(long objectActionId) {
		model.setObjectActionId(objectActionId);
	}

	/**
	 * Sets the object action trigger key of this object action.
	 *
	 * @param objectActionTriggerKey the object action trigger key of this object action
	 */
	@Override
	public void setObjectActionTriggerKey(String objectActionTriggerKey) {
		model.setObjectActionTriggerKey(objectActionTriggerKey);
	}

	/**
	 * Sets the object definition ID of this object action.
	 *
	 * @param objectDefinitionId the object definition ID of this object action
	 */
	@Override
	public void setObjectDefinitionId(long objectDefinitionId) {
		model.setObjectDefinitionId(objectDefinitionId);
	}

	/**
	 * Sets the parameters of this object action.
	 *
	 * @param parameters the parameters of this object action
	 */
	@Override
	public void setParameters(String parameters) {
		model.setParameters(parameters);
	}

	/**
	 * Sets the primary key of this object action.
	 *
	 * @param primaryKey the primary key of this object action
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the status of this object action.
	 *
	 * @param status the status of this object action
	 */
	@Override
	public void setStatus(int status) {
		model.setStatus(status);
	}

	/**
	 * Sets the user ID of this object action.
	 *
	 * @param userId the user ID of this object action
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this object action.
	 *
	 * @param userName the user name of this object action
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this object action.
	 *
	 * @param userUuid the user uuid of this object action
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this object action.
	 *
	 * @param uuid the uuid of this object action
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
	protected ObjectActionWrapper wrap(ObjectAction objectAction) {
		return new ObjectActionWrapper(objectAction);
	}

}