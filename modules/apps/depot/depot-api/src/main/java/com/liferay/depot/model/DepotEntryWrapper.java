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

package com.liferay.depot.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link DepotEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DepotEntry
 * @generated
 */
public class DepotEntryWrapper
	extends BaseModelWrapper<DepotEntry>
	implements DepotEntry, ModelWrapper<DepotEntry> {

	public DepotEntryWrapper(DepotEntry depotEntry) {
		super(depotEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("depotEntryId", getDepotEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());

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

		Long depotEntryId = (Long)attributes.get("depotEntryId");

		if (depotEntryId != null) {
			setDepotEntryId(depotEntryId);
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
	}

	/**
	 * Returns the company ID of this depot entry.
	 *
	 * @return the company ID of this depot entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this depot entry.
	 *
	 * @return the create date of this depot entry
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the depot entry ID of this depot entry.
	 *
	 * @return the depot entry ID of this depot entry
	 */
	@Override
	public long getDepotEntryId() {
		return model.getDepotEntryId();
	}

	@Override
	public com.liferay.portal.kernel.model.Group getGroup() {
		return model.getGroup();
	}

	/**
	 * Returns the group ID of this depot entry.
	 *
	 * @return the group ID of this depot entry
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this depot entry.
	 *
	 * @return the modified date of this depot entry
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this depot entry.
	 *
	 * @return the mvcc version of this depot entry
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this depot entry.
	 *
	 * @return the primary key of this depot entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this depot entry.
	 *
	 * @return the user ID of this depot entry
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this depot entry.
	 *
	 * @return the user name of this depot entry
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this depot entry.
	 *
	 * @return the user uuid of this depot entry
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this depot entry.
	 *
	 * @return the uuid of this depot entry
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
	 * Sets the company ID of this depot entry.
	 *
	 * @param companyId the company ID of this depot entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this depot entry.
	 *
	 * @param createDate the create date of this depot entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the depot entry ID of this depot entry.
	 *
	 * @param depotEntryId the depot entry ID of this depot entry
	 */
	@Override
	public void setDepotEntryId(long depotEntryId) {
		model.setDepotEntryId(depotEntryId);
	}

	/**
	 * Sets the group ID of this depot entry.
	 *
	 * @param groupId the group ID of this depot entry
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this depot entry.
	 *
	 * @param modifiedDate the modified date of this depot entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this depot entry.
	 *
	 * @param mvccVersion the mvcc version of this depot entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this depot entry.
	 *
	 * @param primaryKey the primary key of this depot entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this depot entry.
	 *
	 * @param userId the user ID of this depot entry
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this depot entry.
	 *
	 * @param userName the user name of this depot entry
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this depot entry.
	 *
	 * @param userUuid the user uuid of this depot entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this depot entry.
	 *
	 * @param uuid the uuid of this depot entry
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
	protected DepotEntryWrapper wrap(DepotEntry depotEntry) {
		return new DepotEntryWrapper(depotEntry);
	}

}