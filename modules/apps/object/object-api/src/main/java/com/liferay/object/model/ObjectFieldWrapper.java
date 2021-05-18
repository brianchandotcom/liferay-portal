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
 * This class is a wrapper for {@link ObjectField}.
 * </p>
 *
 * @author Marco Leo
 * @see ObjectField
 * @generated
 */
public class ObjectFieldWrapper
	extends BaseModelWrapper<ObjectField>
	implements ModelWrapper<ObjectField>, ObjectField {

	public ObjectFieldWrapper(ObjectField objectField) {
		super(objectField);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("objectFieldId", getObjectFieldId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("objectDefinitionId", getObjectDefinitionId());
		attributes.put("indexed", isIndexed());
		attributes.put("indexedAsKeyword", isIndexedAsKeyword());
		attributes.put("indexedLanguageId", getIndexedLanguageId());
		attributes.put("name", getName());
		attributes.put("type", getType());

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

		Long objectFieldId = (Long)attributes.get("objectFieldId");

		if (objectFieldId != null) {
			setObjectFieldId(objectFieldId);
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

		Boolean indexed = (Boolean)attributes.get("indexed");

		if (indexed != null) {
			setIndexed(indexed);
		}

		Boolean indexedAsKeyword = (Boolean)attributes.get("indexedAsKeyword");

		if (indexedAsKeyword != null) {
			setIndexedAsKeyword(indexedAsKeyword);
		}

		String indexedLanguageId = (String)attributes.get("indexedLanguageId");

		if (indexedLanguageId != null) {
			setIndexedLanguageId(indexedLanguageId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
		}
	}

	/**
	 * Returns the company ID of this object field.
	 *
	 * @return the company ID of this object field
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this object field.
	 *
	 * @return the create date of this object field
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	@Override
	public String getDBColumnName() {
		return model.getDBColumnName();
	}

	/**
	 * Returns the indexed of this object field.
	 *
	 * @return the indexed of this object field
	 */
	@Override
	public boolean getIndexed() {
		return model.getIndexed();
	}

	/**
	 * Returns the indexed as keyword of this object field.
	 *
	 * @return the indexed as keyword of this object field
	 */
	@Override
	public boolean getIndexedAsKeyword() {
		return model.getIndexedAsKeyword();
	}

	/**
	 * Returns the indexed language ID of this object field.
	 *
	 * @return the indexed language ID of this object field
	 */
	@Override
	public String getIndexedLanguageId() {
		return model.getIndexedLanguageId();
	}

	/**
	 * Returns the modified date of this object field.
	 *
	 * @return the modified date of this object field
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this object field.
	 *
	 * @return the mvcc version of this object field
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this object field.
	 *
	 * @return the name of this object field
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the object definition ID of this object field.
	 *
	 * @return the object definition ID of this object field
	 */
	@Override
	public long getObjectDefinitionId() {
		return model.getObjectDefinitionId();
	}

	/**
	 * Returns the object field ID of this object field.
	 *
	 * @return the object field ID of this object field
	 */
	@Override
	public long getObjectFieldId() {
		return model.getObjectFieldId();
	}

	/**
	 * Returns the primary key of this object field.
	 *
	 * @return the primary key of this object field
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the type of this object field.
	 *
	 * @return the type of this object field
	 */
	@Override
	public String getType() {
		return model.getType();
	}

	/**
	 * Returns the user ID of this object field.
	 *
	 * @return the user ID of this object field
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this object field.
	 *
	 * @return the user name of this object field
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this object field.
	 *
	 * @return the user uuid of this object field
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this object field.
	 *
	 * @return the uuid of this object field
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns <code>true</code> if this object field is indexed.
	 *
	 * @return <code>true</code> if this object field is indexed; <code>false</code> otherwise
	 */
	@Override
	public boolean isIndexed() {
		return model.isIndexed();
	}

	/**
	 * Returns <code>true</code> if this object field is indexed as keyword.
	 *
	 * @return <code>true</code> if this object field is indexed as keyword; <code>false</code> otherwise
	 */
	@Override
	public boolean isIndexedAsKeyword() {
		return model.isIndexedAsKeyword();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this object field.
	 *
	 * @param companyId the company ID of this object field
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this object field.
	 *
	 * @param createDate the create date of this object field
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets whether this object field is indexed.
	 *
	 * @param indexed the indexed of this object field
	 */
	@Override
	public void setIndexed(boolean indexed) {
		model.setIndexed(indexed);
	}

	/**
	 * Sets whether this object field is indexed as keyword.
	 *
	 * @param indexedAsKeyword the indexed as keyword of this object field
	 */
	@Override
	public void setIndexedAsKeyword(boolean indexedAsKeyword) {
		model.setIndexedAsKeyword(indexedAsKeyword);
	}

	/**
	 * Sets the indexed language ID of this object field.
	 *
	 * @param indexedLanguageId the indexed language ID of this object field
	 */
	@Override
	public void setIndexedLanguageId(String indexedLanguageId) {
		model.setIndexedLanguageId(indexedLanguageId);
	}

	/**
	 * Sets the modified date of this object field.
	 *
	 * @param modifiedDate the modified date of this object field
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this object field.
	 *
	 * @param mvccVersion the mvcc version of this object field
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this object field.
	 *
	 * @param name the name of this object field
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the object definition ID of this object field.
	 *
	 * @param objectDefinitionId the object definition ID of this object field
	 */
	@Override
	public void setObjectDefinitionId(long objectDefinitionId) {
		model.setObjectDefinitionId(objectDefinitionId);
	}

	/**
	 * Sets the object field ID of this object field.
	 *
	 * @param objectFieldId the object field ID of this object field
	 */
	@Override
	public void setObjectFieldId(long objectFieldId) {
		model.setObjectFieldId(objectFieldId);
	}

	/**
	 * Sets the primary key of this object field.
	 *
	 * @param primaryKey the primary key of this object field
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the type of this object field.
	 *
	 * @param type the type of this object field
	 */
	@Override
	public void setType(String type) {
		model.setType(type);
	}

	/**
	 * Sets the user ID of this object field.
	 *
	 * @param userId the user ID of this object field
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this object field.
	 *
	 * @param userName the user name of this object field
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this object field.
	 *
	 * @param userUuid the user uuid of this object field
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this object field.
	 *
	 * @param uuid the uuid of this object field
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
	protected ObjectFieldWrapper wrap(ObjectField objectField) {
		return new ObjectFieldWrapper(objectField);
	}

}