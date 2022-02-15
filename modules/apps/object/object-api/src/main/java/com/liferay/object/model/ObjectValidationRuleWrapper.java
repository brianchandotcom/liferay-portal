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
 * This class is a wrapper for {@link ObjectValidationRule}.
 * </p>
 *
 * @author Marco Leo
 * @see ObjectValidationRule
 * @generated
 */
public class ObjectValidationRuleWrapper
	extends BaseModelWrapper<ObjectValidationRule>
	implements ModelWrapper<ObjectValidationRule>, ObjectValidationRule {

	public ObjectValidationRuleWrapper(
		ObjectValidationRule objectValidationRule) {

		super(objectValidationRule);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("objectValidationRuleId", getObjectValidationRuleId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("objectDefinitionId", getObjectDefinitionId());
		attributes.put("active", isActive());
		attributes.put("errorLabel", getErrorLabel());
		attributes.put("engine", getEngine());
		attributes.put("script", getScript());

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

		Long objectValidationRuleId = (Long)attributes.get(
			"objectValidationRuleId");

		if (objectValidationRuleId != null) {
			setObjectValidationRuleId(objectValidationRuleId);
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

		String errorLabel = (String)attributes.get("errorLabel");

		if (errorLabel != null) {
			setErrorLabel(errorLabel);
		}

		String engine = (String)attributes.get("engine");

		if (engine != null) {
			setEngine(engine);
		}

		String script = (String)attributes.get("script");

		if (script != null) {
			setScript(script);
		}
	}

	@Override
	public ObjectValidationRule cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the active of this object validation rule.
	 *
	 * @return the active of this object validation rule
	 */
	@Override
	public boolean getActive() {
		return model.getActive();
	}

	/**
	 * Returns the company ID of this object validation rule.
	 *
	 * @return the company ID of this object validation rule
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this object validation rule.
	 *
	 * @return the create date of this object validation rule
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the engine of this object validation rule.
	 *
	 * @return the engine of this object validation rule
	 */
	@Override
	public String getEngine() {
		return model.getEngine();
	}

	/**
	 * Returns the error label of this object validation rule.
	 *
	 * @return the error label of this object validation rule
	 */
	@Override
	public String getErrorLabel() {
		return model.getErrorLabel();
	}

	/**
	 * Returns the modified date of this object validation rule.
	 *
	 * @return the modified date of this object validation rule
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this object validation rule.
	 *
	 * @return the mvcc version of this object validation rule
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the object definition ID of this object validation rule.
	 *
	 * @return the object definition ID of this object validation rule
	 */
	@Override
	public long getObjectDefinitionId() {
		return model.getObjectDefinitionId();
	}

	/**
	 * Returns the object validation rule ID of this object validation rule.
	 *
	 * @return the object validation rule ID of this object validation rule
	 */
	@Override
	public long getObjectValidationRuleId() {
		return model.getObjectValidationRuleId();
	}

	/**
	 * Returns the primary key of this object validation rule.
	 *
	 * @return the primary key of this object validation rule
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the script of this object validation rule.
	 *
	 * @return the script of this object validation rule
	 */
	@Override
	public String getScript() {
		return model.getScript();
	}

	/**
	 * Returns the user ID of this object validation rule.
	 *
	 * @return the user ID of this object validation rule
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this object validation rule.
	 *
	 * @return the user name of this object validation rule
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this object validation rule.
	 *
	 * @return the user uuid of this object validation rule
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this object validation rule.
	 *
	 * @return the uuid of this object validation rule
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns <code>true</code> if this object validation rule is active.
	 *
	 * @return <code>true</code> if this object validation rule is active; <code>false</code> otherwise
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
	 * Sets whether this object validation rule is active.
	 *
	 * @param active the active of this object validation rule
	 */
	@Override
	public void setActive(boolean active) {
		model.setActive(active);
	}

	/**
	 * Sets the company ID of this object validation rule.
	 *
	 * @param companyId the company ID of this object validation rule
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this object validation rule.
	 *
	 * @param createDate the create date of this object validation rule
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the engine of this object validation rule.
	 *
	 * @param engine the engine of this object validation rule
	 */
	@Override
	public void setEngine(String engine) {
		model.setEngine(engine);
	}

	/**
	 * Sets the error label of this object validation rule.
	 *
	 * @param errorLabel the error label of this object validation rule
	 */
	@Override
	public void setErrorLabel(String errorLabel) {
		model.setErrorLabel(errorLabel);
	}

	/**
	 * Sets the modified date of this object validation rule.
	 *
	 * @param modifiedDate the modified date of this object validation rule
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this object validation rule.
	 *
	 * @param mvccVersion the mvcc version of this object validation rule
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the object definition ID of this object validation rule.
	 *
	 * @param objectDefinitionId the object definition ID of this object validation rule
	 */
	@Override
	public void setObjectDefinitionId(long objectDefinitionId) {
		model.setObjectDefinitionId(objectDefinitionId);
	}

	/**
	 * Sets the object validation rule ID of this object validation rule.
	 *
	 * @param objectValidationRuleId the object validation rule ID of this object validation rule
	 */
	@Override
	public void setObjectValidationRuleId(long objectValidationRuleId) {
		model.setObjectValidationRuleId(objectValidationRuleId);
	}

	/**
	 * Sets the primary key of this object validation rule.
	 *
	 * @param primaryKey the primary key of this object validation rule
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the script of this object validation rule.
	 *
	 * @param script the script of this object validation rule
	 */
	@Override
	public void setScript(String script) {
		model.setScript(script);
	}

	/**
	 * Sets the user ID of this object validation rule.
	 *
	 * @param userId the user ID of this object validation rule
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this object validation rule.
	 *
	 * @param userName the user name of this object validation rule
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this object validation rule.
	 *
	 * @param userUuid the user uuid of this object validation rule
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this object validation rule.
	 *
	 * @param uuid the uuid of this object validation rule
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
	protected ObjectValidationRuleWrapper wrap(
		ObjectValidationRule objectValidationRule) {

		return new ObjectValidationRuleWrapper(objectValidationRule);
	}

}