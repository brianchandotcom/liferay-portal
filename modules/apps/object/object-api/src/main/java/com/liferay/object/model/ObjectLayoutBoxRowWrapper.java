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
 * This class is a wrapper for {@link ObjectLayoutBoxRow}.
 * </p>
 *
 * @author Marco Leo
 * @see ObjectLayoutBoxRow
 * @generated
 */
public class ObjectLayoutBoxRowWrapper
	extends BaseModelWrapper<ObjectLayoutBoxRow>
	implements ModelWrapper<ObjectLayoutBoxRow>, ObjectLayoutBoxRow {

	public ObjectLayoutBoxRowWrapper(ObjectLayoutBoxRow objectLayoutBoxRow) {
		super(objectLayoutBoxRow);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("objectLayoutBoxRowId", getObjectLayoutBoxRowId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("objectLayoutBoxId", getObjectLayoutBoxId());
		attributes.put("order", getOrder());

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

		Long objectLayoutBoxRowId = (Long)attributes.get(
			"objectLayoutBoxRowId");

		if (objectLayoutBoxRowId != null) {
			setObjectLayoutBoxRowId(objectLayoutBoxRowId);
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

		Long objectLayoutBoxId = (Long)attributes.get("objectLayoutBoxId");

		if (objectLayoutBoxId != null) {
			setObjectLayoutBoxId(objectLayoutBoxId);
		}

		Integer order = (Integer)attributes.get("order");

		if (order != null) {
			setOrder(order);
		}
	}

	@Override
	public ObjectLayoutBoxRow cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this object layout box row.
	 *
	 * @return the company ID of this object layout box row
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this object layout box row.
	 *
	 * @return the create date of this object layout box row
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the modified date of this object layout box row.
	 *
	 * @return the modified date of this object layout box row
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this object layout box row.
	 *
	 * @return the mvcc version of this object layout box row
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the object layout box ID of this object layout box row.
	 *
	 * @return the object layout box ID of this object layout box row
	 */
	@Override
	public long getObjectLayoutBoxId() {
		return model.getObjectLayoutBoxId();
	}

	/**
	 * Returns the object layout box row ID of this object layout box row.
	 *
	 * @return the object layout box row ID of this object layout box row
	 */
	@Override
	public long getObjectLayoutBoxRowId() {
		return model.getObjectLayoutBoxRowId();
	}

	/**
	 * Returns the order of this object layout box row.
	 *
	 * @return the order of this object layout box row
	 */
	@Override
	public int getOrder() {
		return model.getOrder();
	}

	/**
	 * Returns the primary key of this object layout box row.
	 *
	 * @return the primary key of this object layout box row
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this object layout box row.
	 *
	 * @return the user ID of this object layout box row
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this object layout box row.
	 *
	 * @return the user name of this object layout box row
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this object layout box row.
	 *
	 * @return the user uuid of this object layout box row
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this object layout box row.
	 *
	 * @return the uuid of this object layout box row
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
	 * Sets the company ID of this object layout box row.
	 *
	 * @param companyId the company ID of this object layout box row
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this object layout box row.
	 *
	 * @param createDate the create date of this object layout box row
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the modified date of this object layout box row.
	 *
	 * @param modifiedDate the modified date of this object layout box row
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this object layout box row.
	 *
	 * @param mvccVersion the mvcc version of this object layout box row
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the object layout box ID of this object layout box row.
	 *
	 * @param objectLayoutBoxId the object layout box ID of this object layout box row
	 */
	@Override
	public void setObjectLayoutBoxId(long objectLayoutBoxId) {
		model.setObjectLayoutBoxId(objectLayoutBoxId);
	}

	/**
	 * Sets the object layout box row ID of this object layout box row.
	 *
	 * @param objectLayoutBoxRowId the object layout box row ID of this object layout box row
	 */
	@Override
	public void setObjectLayoutBoxRowId(long objectLayoutBoxRowId) {
		model.setObjectLayoutBoxRowId(objectLayoutBoxRowId);
	}

	/**
	 * Sets the order of this object layout box row.
	 *
	 * @param order the order of this object layout box row
	 */
	@Override
	public void setOrder(int order) {
		model.setOrder(order);
	}

	/**
	 * Sets the primary key of this object layout box row.
	 *
	 * @param primaryKey the primary key of this object layout box row
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this object layout box row.
	 *
	 * @param userId the user ID of this object layout box row
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this object layout box row.
	 *
	 * @param userName the user name of this object layout box row
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this object layout box row.
	 *
	 * @param userUuid the user uuid of this object layout box row
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this object layout box row.
	 *
	 * @param uuid the uuid of this object layout box row
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
	protected ObjectLayoutBoxRowWrapper wrap(
		ObjectLayoutBoxRow objectLayoutBoxRow) {

		return new ObjectLayoutBoxRowWrapper(objectLayoutBoxRow);
	}

}