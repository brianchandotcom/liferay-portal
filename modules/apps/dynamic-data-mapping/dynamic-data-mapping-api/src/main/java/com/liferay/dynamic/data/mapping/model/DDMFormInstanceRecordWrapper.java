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

package com.liferay.dynamic.data.mapping.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link DDMFormInstanceRecord}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMFormInstanceRecord
 * @generated
 */
public class DDMFormInstanceRecordWrapper
	extends BaseModelWrapper<DDMFormInstanceRecord>
	implements DDMFormInstanceRecord, ModelWrapper<DDMFormInstanceRecord> {

	public DDMFormInstanceRecordWrapper(
		DDMFormInstanceRecord ddmFormInstanceRecord) {

		super(ddmFormInstanceRecord);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("uuid", getUuid());
		attributes.put("formInstanceRecordId", getFormInstanceRecordId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("versionUserId", getVersionUserId());
		attributes.put("versionUserName", getVersionUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("formInstanceId", getFormInstanceId());
		attributes.put("formInstanceVersion", getFormInstanceVersion());
		attributes.put("storageId", getStorageId());
		attributes.put("version", getVersion());
		attributes.put("ipAddress", getIpAddress());
		attributes.put("lastPublishDate", getLastPublishDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long ctCollectionId = (Long)attributes.get("ctCollectionId");

		if (ctCollectionId != null) {
			setCtCollectionId(ctCollectionId);
		}

		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long formInstanceRecordId = (Long)attributes.get(
			"formInstanceRecordId");

		if (formInstanceRecordId != null) {
			setFormInstanceRecordId(formInstanceRecordId);
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

		Long versionUserId = (Long)attributes.get("versionUserId");

		if (versionUserId != null) {
			setVersionUserId(versionUserId);
		}

		String versionUserName = (String)attributes.get("versionUserName");

		if (versionUserName != null) {
			setVersionUserName(versionUserName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Long formInstanceId = (Long)attributes.get("formInstanceId");

		if (formInstanceId != null) {
			setFormInstanceId(formInstanceId);
		}

		String formInstanceVersion = (String)attributes.get(
			"formInstanceVersion");

		if (formInstanceVersion != null) {
			setFormInstanceVersion(formInstanceVersion);
		}

		Long storageId = (Long)attributes.get("storageId");

		if (storageId != null) {
			setStorageId(storageId);
		}

		String version = (String)attributes.get("version");

		if (version != null) {
			setVersion(version);
		}

		String ipAddress = (String)attributes.get("ipAddress");

		if (ipAddress != null) {
			setIpAddress(ipAddress);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	/**
	 * Returns the company ID of this ddm form instance record.
	 *
	 * @return the company ID of this ddm form instance record
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this ddm form instance record.
	 *
	 * @return the create date of this ddm form instance record
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this ddm form instance record.
	 *
	 * @return the ct collection ID of this ddm form instance record
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	@Override
	public com.liferay.dynamic.data.mapping.storage.DDMFormValues
			getDDMFormValues()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getDDMFormValues();
	}

	@Override
	public DDMFormInstance getFormInstance()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getFormInstance();
	}

	/**
	 * Returns the form instance ID of this ddm form instance record.
	 *
	 * @return the form instance ID of this ddm form instance record
	 */
	@Override
	public long getFormInstanceId() {
		return model.getFormInstanceId();
	}

	/**
	 * Returns the form instance record ID of this ddm form instance record.
	 *
	 * @return the form instance record ID of this ddm form instance record
	 */
	@Override
	public long getFormInstanceRecordId() {
		return model.getFormInstanceRecordId();
	}

	@Override
	public DDMFormInstanceRecordVersion getFormInstanceRecordVersion()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getFormInstanceRecordVersion();
	}

	@Override
	public DDMFormInstanceRecordVersion getFormInstanceRecordVersion(
			String version)
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getFormInstanceRecordVersion(version);
	}

	/**
	 * Returns the form instance version of this ddm form instance record.
	 *
	 * @return the form instance version of this ddm form instance record
	 */
	@Override
	public String getFormInstanceVersion() {
		return model.getFormInstanceVersion();
	}

	/**
	 * Returns the group ID of this ddm form instance record.
	 *
	 * @return the group ID of this ddm form instance record
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the ip address of this ddm form instance record.
	 *
	 * @return the ip address of this ddm form instance record
	 */
	@Override
	public String getIpAddress() {
		return model.getIpAddress();
	}

	/**
	 * Returns the last publish date of this ddm form instance record.
	 *
	 * @return the last publish date of this ddm form instance record
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	@Override
	public DDMFormInstanceRecordVersion getLatestFormInstanceRecordVersion()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getLatestFormInstanceRecordVersion();
	}

	/**
	 * Returns the modified date of this ddm form instance record.
	 *
	 * @return the modified date of this ddm form instance record
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this ddm form instance record.
	 *
	 * @return the mvcc version of this ddm form instance record
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this ddm form instance record.
	 *
	 * @return the primary key of this ddm form instance record
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	@Override
	public int getStatus()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getStatus();
	}

	/**
	 * Returns the storage ID of this ddm form instance record.
	 *
	 * @return the storage ID of this ddm form instance record
	 */
	@Override
	public long getStorageId() {
		return model.getStorageId();
	}

	@Override
	public String getStorageType()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getStorageType();
	}

	/**
	 * Returns the user ID of this ddm form instance record.
	 *
	 * @return the user ID of this ddm form instance record
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this ddm form instance record.
	 *
	 * @return the user name of this ddm form instance record
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this ddm form instance record.
	 *
	 * @return the user uuid of this ddm form instance record
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this ddm form instance record.
	 *
	 * @return the uuid of this ddm form instance record
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns the version of this ddm form instance record.
	 *
	 * @return the version of this ddm form instance record
	 */
	@Override
	public String getVersion() {
		return model.getVersion();
	}

	/**
	 * Returns the version user ID of this ddm form instance record.
	 *
	 * @return the version user ID of this ddm form instance record
	 */
	@Override
	public long getVersionUserId() {
		return model.getVersionUserId();
	}

	/**
	 * Returns the version user name of this ddm form instance record.
	 *
	 * @return the version user name of this ddm form instance record
	 */
	@Override
	public String getVersionUserName() {
		return model.getVersionUserName();
	}

	/**
	 * Returns the version user uuid of this ddm form instance record.
	 *
	 * @return the version user uuid of this ddm form instance record
	 */
	@Override
	public String getVersionUserUuid() {
		return model.getVersionUserUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this ddm form instance record.
	 *
	 * @param companyId the company ID of this ddm form instance record
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this ddm form instance record.
	 *
	 * @param createDate the create date of this ddm form instance record
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this ddm form instance record.
	 *
	 * @param ctCollectionId the ct collection ID of this ddm form instance record
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the form instance ID of this ddm form instance record.
	 *
	 * @param formInstanceId the form instance ID of this ddm form instance record
	 */
	@Override
	public void setFormInstanceId(long formInstanceId) {
		model.setFormInstanceId(formInstanceId);
	}

	/**
	 * Sets the form instance record ID of this ddm form instance record.
	 *
	 * @param formInstanceRecordId the form instance record ID of this ddm form instance record
	 */
	@Override
	public void setFormInstanceRecordId(long formInstanceRecordId) {
		model.setFormInstanceRecordId(formInstanceRecordId);
	}

	/**
	 * Sets the form instance version of this ddm form instance record.
	 *
	 * @param formInstanceVersion the form instance version of this ddm form instance record
	 */
	@Override
	public void setFormInstanceVersion(String formInstanceVersion) {
		model.setFormInstanceVersion(formInstanceVersion);
	}

	/**
	 * Sets the group ID of this ddm form instance record.
	 *
	 * @param groupId the group ID of this ddm form instance record
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the ip address of this ddm form instance record.
	 *
	 * @param ipAddress the ip address of this ddm form instance record
	 */
	@Override
	public void setIpAddress(String ipAddress) {
		model.setIpAddress(ipAddress);
	}

	/**
	 * Sets the last publish date of this ddm form instance record.
	 *
	 * @param lastPublishDate the last publish date of this ddm form instance record
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the modified date of this ddm form instance record.
	 *
	 * @param modifiedDate the modified date of this ddm form instance record
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this ddm form instance record.
	 *
	 * @param mvccVersion the mvcc version of this ddm form instance record
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this ddm form instance record.
	 *
	 * @param primaryKey the primary key of this ddm form instance record
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the storage ID of this ddm form instance record.
	 *
	 * @param storageId the storage ID of this ddm form instance record
	 */
	@Override
	public void setStorageId(long storageId) {
		model.setStorageId(storageId);
	}

	/**
	 * Sets the user ID of this ddm form instance record.
	 *
	 * @param userId the user ID of this ddm form instance record
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this ddm form instance record.
	 *
	 * @param userName the user name of this ddm form instance record
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this ddm form instance record.
	 *
	 * @param userUuid the user uuid of this ddm form instance record
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this ddm form instance record.
	 *
	 * @param uuid the uuid of this ddm form instance record
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	/**
	 * Sets the version of this ddm form instance record.
	 *
	 * @param version the version of this ddm form instance record
	 */
	@Override
	public void setVersion(String version) {
		model.setVersion(version);
	}

	/**
	 * Sets the version user ID of this ddm form instance record.
	 *
	 * @param versionUserId the version user ID of this ddm form instance record
	 */
	@Override
	public void setVersionUserId(long versionUserId) {
		model.setVersionUserId(versionUserId);
	}

	/**
	 * Sets the version user name of this ddm form instance record.
	 *
	 * @param versionUserName the version user name of this ddm form instance record
	 */
	@Override
	public void setVersionUserName(String versionUserName) {
		model.setVersionUserName(versionUserName);
	}

	/**
	 * Sets the version user uuid of this ddm form instance record.
	 *
	 * @param versionUserUuid the version user uuid of this ddm form instance record
	 */
	@Override
	public void setVersionUserUuid(String versionUserUuid) {
		model.setVersionUserUuid(versionUserUuid);
	}

	@Override
	public Map<String, Function<DDMFormInstanceRecord, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<DDMFormInstanceRecord, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected DDMFormInstanceRecordWrapper wrap(
		DDMFormInstanceRecord ddmFormInstanceRecord) {

		return new DDMFormInstanceRecordWrapper(ddmFormInstanceRecord);
	}

}