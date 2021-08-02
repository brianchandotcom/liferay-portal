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
 * This class is a wrapper for {@link ObjectDefinition}.
 * </p>
 *
 * @author Marco Leo
 * @see ObjectDefinition
 * @generated
 */
public class ObjectDefinitionWrapper
	extends BaseModelWrapper<ObjectDefinition>
	implements ModelWrapper<ObjectDefinition>, ObjectDefinition {

	public ObjectDefinitionWrapper(ObjectDefinition objectDefinition) {
		super(objectDefinition);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("objectDefinitionId", getObjectDefinitionId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("dbTableName", getDBTableName());
		attributes.put("name", getName());
		attributes.put(
			"pkObjectFieldDBColumnName", getPKObjectFieldDBColumnName());
		attributes.put("pkObjectFieldName", getPKObjectFieldName());
		attributes.put("scope", getScope());
		attributes.put("system", isSystem());
		attributes.put("version", getVersion());
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

		Long objectDefinitionId = (Long)attributes.get("objectDefinitionId");

		if (objectDefinitionId != null) {
			setObjectDefinitionId(objectDefinitionId);
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

		String dbTableName = (String)attributes.get("dbTableName");

		if (dbTableName != null) {
			setDBTableName(dbTableName);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String pkObjectFieldDBColumnName = (String)attributes.get(
			"pkObjectFieldDBColumnName");

		if (pkObjectFieldDBColumnName != null) {
			setPKObjectFieldDBColumnName(pkObjectFieldDBColumnName);
		}

		String pkObjectFieldName = (String)attributes.get("pkObjectFieldName");

		if (pkObjectFieldName != null) {
			setPKObjectFieldName(pkObjectFieldName);
		}

		String scope = (String)attributes.get("scope");

		if (scope != null) {
			setScope(scope);
		}

		Boolean system = (Boolean)attributes.get("system");

		if (system != null) {
			setSystem(system);
		}

		Integer version = (Integer)attributes.get("version");

		if (version != null) {
			setVersion(version);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}
	}

	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the company ID of this object definition.
	 *
	 * @return the company ID of this object definition
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this object definition.
	 *
	 * @return the create date of this object definition
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the db table name of this object definition.
	 *
	 * @return the db table name of this object definition
	 */
	@Override
	public String getDBTableName() {
		return model.getDBTableName();
	}

	@Override
	public String getExtensionDBTableName() {
		return model.getExtensionDBTableName();
	}

	/**
	 * Returns the modified date of this object definition.
	 *
	 * @return the modified date of this object definition
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this object definition.
	 *
	 * @return the mvcc version of this object definition
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this object definition.
	 *
	 * @return the name of this object definition
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the object definition ID of this object definition.
	 *
	 * @return the object definition ID of this object definition
	 */
	@Override
	public long getObjectDefinitionId() {
		return model.getObjectDefinitionId();
	}

	/**
	 * Returns the pk object field db column name of this object definition.
	 *
	 * @return the pk object field db column name of this object definition
	 */
	@Override
	public String getPKObjectFieldDBColumnName() {
		return model.getPKObjectFieldDBColumnName();
	}

	/**
	 * Returns the pk object field name of this object definition.
	 *
	 * @return the pk object field name of this object definition
	 */
	@Override
	public String getPKObjectFieldName() {
		return model.getPKObjectFieldName();
	}

	@Override
	public String getPortletId() {
		return model.getPortletId();
	}

	/**
	 * Returns the primary key of this object definition.
	 *
	 * @return the primary key of this object definition
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	@Override
	public String getResourceName() {
		return model.getResourceName();
	}

	@Override
	public String getRESTContextPath() {
		return model.getRESTContextPath();
	}

	/**
	 * Returns the scope of this object definition.
	 *
	 * @return the scope of this object definition
	 */
	@Override
	public String getScope() {
		return model.getScope();
	}

	@Override
	public String getShortName() {
		return model.getShortName();
	}

	/**
	 * Returns the status of this object definition.
	 *
	 * @return the status of this object definition
	 */
	@Override
	public int getStatus() {
		return model.getStatus();
	}

	/**
	 * Returns the system of this object definition.
	 *
	 * @return the system of this object definition
	 */
	@Override
	public boolean getSystem() {
		return model.getSystem();
	}

	/**
	 * Returns the user ID of this object definition.
	 *
	 * @return the user ID of this object definition
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this object definition.
	 *
	 * @return the user name of this object definition
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this object definition.
	 *
	 * @return the user uuid of this object definition
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this object definition.
	 *
	 * @return the uuid of this object definition
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns the version of this object definition.
	 *
	 * @return the version of this object definition
	 */
	@Override
	public int getVersion() {
		return model.getVersion();
	}

	/**
	 * Returns <code>true</code> if this object definition is system.
	 *
	 * @return <code>true</code> if this object definition is system; <code>false</code> otherwise
	 */
	@Override
	public boolean isSystem() {
		return model.isSystem();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this object definition.
	 *
	 * @param companyId the company ID of this object definition
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this object definition.
	 *
	 * @param createDate the create date of this object definition
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the db table name of this object definition.
	 *
	 * @param dbTableName the db table name of this object definition
	 */
	@Override
	public void setDBTableName(String dbTableName) {
		model.setDBTableName(dbTableName);
	}

	/**
	 * Sets the modified date of this object definition.
	 *
	 * @param modifiedDate the modified date of this object definition
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this object definition.
	 *
	 * @param mvccVersion the mvcc version of this object definition
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this object definition.
	 *
	 * @param name the name of this object definition
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the object definition ID of this object definition.
	 *
	 * @param objectDefinitionId the object definition ID of this object definition
	 */
	@Override
	public void setObjectDefinitionId(long objectDefinitionId) {
		model.setObjectDefinitionId(objectDefinitionId);
	}

	/**
	 * Sets the pk object field db column name of this object definition.
	 *
	 * @param pkObjectFieldDBColumnName the pk object field db column name of this object definition
	 */
	@Override
	public void setPKObjectFieldDBColumnName(String pkObjectFieldDBColumnName) {
		model.setPKObjectFieldDBColumnName(pkObjectFieldDBColumnName);
	}

	/**
	 * Sets the pk object field name of this object definition.
	 *
	 * @param pkObjectFieldName the pk object field name of this object definition
	 */
	@Override
	public void setPKObjectFieldName(String pkObjectFieldName) {
		model.setPKObjectFieldName(pkObjectFieldName);
	}

	/**
	 * Sets the primary key of this object definition.
	 *
	 * @param primaryKey the primary key of this object definition
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the scope of this object definition.
	 *
	 * @param scope the scope of this object definition
	 */
	@Override
	public void setScope(String scope) {
		model.setScope(scope);
	}

	/**
	 * Sets the status of this object definition.
	 *
	 * @param status the status of this object definition
	 */
	@Override
	public void setStatus(int status) {
		model.setStatus(status);
	}

	/**
	 * Sets whether this object definition is system.
	 *
	 * @param system the system of this object definition
	 */
	@Override
	public void setSystem(boolean system) {
		model.setSystem(system);
	}

	/**
	 * Sets the user ID of this object definition.
	 *
	 * @param userId the user ID of this object definition
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this object definition.
	 *
	 * @param userName the user name of this object definition
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this object definition.
	 *
	 * @param userUuid the user uuid of this object definition
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this object definition.
	 *
	 * @param uuid the uuid of this object definition
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	/**
	 * Sets the version of this object definition.
	 *
	 * @param version the version of this object definition
	 */
	@Override
	public void setVersion(int version) {
		model.setVersion(version);
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected ObjectDefinitionWrapper wrap(ObjectDefinition objectDefinition) {
		return new ObjectDefinitionWrapper(objectDefinition);
	}

}