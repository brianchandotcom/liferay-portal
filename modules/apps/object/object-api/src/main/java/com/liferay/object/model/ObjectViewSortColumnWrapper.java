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
 * This class is a wrapper for {@link ObjectViewSortColumn}.
 * </p>
 *
 * @author Marco Leo
 * @see ObjectViewSortColumn
 * @generated
 */
public class ObjectViewSortColumnWrapper
	extends BaseModelWrapper<ObjectViewSortColumn>
	implements ModelWrapper<ObjectViewSortColumn>, ObjectViewSortColumn {

	public ObjectViewSortColumnWrapper(
		ObjectViewSortColumn objectViewSortColumn) {

		super(objectViewSortColumn);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("objectViewSortColumnId", getObjectViewSortColumnId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("objectViewId", getObjectViewId());
		attributes.put("objectFieldName", getObjectFieldName());
		attributes.put("priority", getPriority());
		attributes.put("sortOrder", getSortOrder());

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

		Long objectViewSortColumnId = (Long)attributes.get(
			"objectViewSortColumnId");

		if (objectViewSortColumnId != null) {
			setObjectViewSortColumnId(objectViewSortColumnId);
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

		Long objectViewId = (Long)attributes.get("objectViewId");

		if (objectViewId != null) {
			setObjectViewId(objectViewId);
		}

		String objectFieldName = (String)attributes.get("objectFieldName");

		if (objectFieldName != null) {
			setObjectFieldName(objectFieldName);
		}

		Integer priority = (Integer)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}

		String sortOrder = (String)attributes.get("sortOrder");

		if (sortOrder != null) {
			setSortOrder(sortOrder);
		}
	}

	@Override
	public ObjectViewSortColumn cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this object view sort column.
	 *
	 * @return the company ID of this object view sort column
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this object view sort column.
	 *
	 * @return the create date of this object view sort column
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the modified date of this object view sort column.
	 *
	 * @return the modified date of this object view sort column
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this object view sort column.
	 *
	 * @return the mvcc version of this object view sort column
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the object field name of this object view sort column.
	 *
	 * @return the object field name of this object view sort column
	 */
	@Override
	public String getObjectFieldName() {
		return model.getObjectFieldName();
	}

	/**
	 * Returns the object view ID of this object view sort column.
	 *
	 * @return the object view ID of this object view sort column
	 */
	@Override
	public long getObjectViewId() {
		return model.getObjectViewId();
	}

	/**
	 * Returns the object view sort column ID of this object view sort column.
	 *
	 * @return the object view sort column ID of this object view sort column
	 */
	@Override
	public long getObjectViewSortColumnId() {
		return model.getObjectViewSortColumnId();
	}

	/**
	 * Returns the primary key of this object view sort column.
	 *
	 * @return the primary key of this object view sort column
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the priority of this object view sort column.
	 *
	 * @return the priority of this object view sort column
	 */
	@Override
	public int getPriority() {
		return model.getPriority();
	}

	/**
	 * Returns the sort order of this object view sort column.
	 *
	 * @return the sort order of this object view sort column
	 */
	@Override
	public String getSortOrder() {
		return model.getSortOrder();
	}

	/**
	 * Returns the user ID of this object view sort column.
	 *
	 * @return the user ID of this object view sort column
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this object view sort column.
	 *
	 * @return the user name of this object view sort column
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this object view sort column.
	 *
	 * @return the user uuid of this object view sort column
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this object view sort column.
	 *
	 * @return the uuid of this object view sort column
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Sets the company ID of this object view sort column.
	 *
	 * @param companyId the company ID of this object view sort column
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this object view sort column.
	 *
	 * @param createDate the create date of this object view sort column
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the modified date of this object view sort column.
	 *
	 * @param modifiedDate the modified date of this object view sort column
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this object view sort column.
	 *
	 * @param mvccVersion the mvcc version of this object view sort column
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the object field name of this object view sort column.
	 *
	 * @param objectFieldName the object field name of this object view sort column
	 */
	@Override
	public void setObjectFieldName(String objectFieldName) {
		model.setObjectFieldName(objectFieldName);
	}

	/**
	 * Sets the object view ID of this object view sort column.
	 *
	 * @param objectViewId the object view ID of this object view sort column
	 */
	@Override
	public void setObjectViewId(long objectViewId) {
		model.setObjectViewId(objectViewId);
	}

	/**
	 * Sets the object view sort column ID of this object view sort column.
	 *
	 * @param objectViewSortColumnId the object view sort column ID of this object view sort column
	 */
	@Override
	public void setObjectViewSortColumnId(long objectViewSortColumnId) {
		model.setObjectViewSortColumnId(objectViewSortColumnId);
	}

	/**
	 * Sets the primary key of this object view sort column.
	 *
	 * @param primaryKey the primary key of this object view sort column
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the priority of this object view sort column.
	 *
	 * @param priority the priority of this object view sort column
	 */
	@Override
	public void setPriority(int priority) {
		model.setPriority(priority);
	}

	/**
	 * Sets the sort order of this object view sort column.
	 *
	 * @param sortOrder the sort order of this object view sort column
	 */
	@Override
	public void setSortOrder(String sortOrder) {
		model.setSortOrder(sortOrder);
	}

	/**
	 * Sets the user ID of this object view sort column.
	 *
	 * @param userId the user ID of this object view sort column
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this object view sort column.
	 *
	 * @param userName the user name of this object view sort column
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this object view sort column.
	 *
	 * @param userUuid the user uuid of this object view sort column
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this object view sort column.
	 *
	 * @param uuid the uuid of this object view sort column
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
	protected ObjectViewSortColumnWrapper wrap(
		ObjectViewSortColumn objectViewSortColumn) {

		return new ObjectViewSortColumnWrapper(objectViewSortColumn);
	}

}