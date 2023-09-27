/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.lists.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link DDLRecord}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecord
 * @generated
 */
public class DDLRecordWrapper
	extends BaseModelWrapper<DDLRecord>
	implements DDLRecord, ModelWrapper<DDLRecord> {

	public DDLRecordWrapper(DDLRecord ddlRecord) {
		super(ddlRecord);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("uuid", getUuid());
		attributes.put("recordId", getRecordId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("versionUserId", getVersionUserId());
		attributes.put("versionUserName", getVersionUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("DDMStorageId", getDDMStorageId());
		attributes.put("recordSetId", getRecordSetId());
		attributes.put("recordSetVersion", getRecordSetVersion());
		attributes.put("className", getClassName());
		attributes.put("classPK", getClassPK());
		attributes.put("version", getVersion());
		attributes.put("displayIndex", getDisplayIndex());
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

		Long recordId = (Long)attributes.get("recordId");

		if (recordId != null) {
			setRecordId(recordId);
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

		Long DDMStorageId = (Long)attributes.get("DDMStorageId");

		if (DDMStorageId != null) {
			setDDMStorageId(DDMStorageId);
		}

		Long recordSetId = (Long)attributes.get("recordSetId");

		if (recordSetId != null) {
			setRecordSetId(recordSetId);
		}

		String recordSetVersion = (String)attributes.get("recordSetVersion");

		if (recordSetVersion != null) {
			setRecordSetVersion(recordSetVersion);
		}

		String className = (String)attributes.get("className");

		if (className != null) {
			setClassName(className);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		String version = (String)attributes.get("version");

		if (version != null) {
			setVersion(version);
		}

		Integer displayIndex = (Integer)attributes.get("displayIndex");

		if (displayIndex != null) {
			setDisplayIndex(displayIndex);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	@Override
	public DDLRecord cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the class name of this ddl record.
	 *
	 * @return the class name of this ddl record
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class pk of this ddl record.
	 *
	 * @return the class pk of this ddl record
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the company ID of this ddl record.
	 *
	 * @return the company ID of this ddl record
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this ddl record.
	 *
	 * @return the create date of this ddl record
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this ddl record.
	 *
	 * @return the ct collection ID of this ddl record
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	@Override
	public java.util.List
		<com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue>
				getDDMFormFieldValues(String fieldName)
			throws com.liferay.portal.kernel.exception.PortalException {

		return model.getDDMFormFieldValues(fieldName);
	}

	@Override
	public com.liferay.dynamic.data.mapping.storage.DDMFormValues
			getDDMFormValues()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getDDMFormValues();
	}

	/**
	 * Returns the ddm storage ID of this ddl record.
	 *
	 * @return the ddm storage ID of this ddl record
	 */
	@Override
	public long getDDMStorageId() {
		return model.getDDMStorageId();
	}

	/**
	 * Returns the display index of this ddl record.
	 *
	 * @return the display index of this ddl record
	 */
	@Override
	public int getDisplayIndex() {
		return model.getDisplayIndex();
	}

	@Override
	public Serializable getFieldDataType(String fieldName)
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getFieldDataType(fieldName);
	}

	@Override
	public Serializable getFieldType(String fieldName) throws Exception {
		return model.getFieldType(fieldName);
	}

	/**
	 * Returns the group ID of this ddl record.
	 *
	 * @return the group ID of this ddl record
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the last publish date of this ddl record.
	 *
	 * @return the last publish date of this ddl record
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	@Override
	public DDLRecordVersion getLatestRecordVersion()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getLatestRecordVersion();
	}

	/**
	 * Returns the modified date of this ddl record.
	 *
	 * @return the modified date of this ddl record
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this ddl record.
	 *
	 * @return the mvcc version of this ddl record
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this ddl record.
	 *
	 * @return the primary key of this ddl record
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the record ID of this ddl record.
	 *
	 * @return the record ID of this ddl record
	 */
	@Override
	public long getRecordId() {
		return model.getRecordId();
	}

	@Override
	public DDLRecordSet getRecordSet()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getRecordSet();
	}

	/**
	 * Returns the record set ID of this ddl record.
	 *
	 * @return the record set ID of this ddl record
	 */
	@Override
	public long getRecordSetId() {
		return model.getRecordSetId();
	}

	/**
	 * Returns the record set version of this ddl record.
	 *
	 * @return the record set version of this ddl record
	 */
	@Override
	public String getRecordSetVersion() {
		return model.getRecordSetVersion();
	}

	@Override
	public DDLRecordVersion getRecordVersion()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getRecordVersion();
	}

	@Override
	public DDLRecordVersion getRecordVersion(String version)
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getRecordVersion(version);
	}

	@Override
	public int getStatus()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getStatus();
	}

	/**
	 * Returns the user ID of this ddl record.
	 *
	 * @return the user ID of this ddl record
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this ddl record.
	 *
	 * @return the user name of this ddl record
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this ddl record.
	 *
	 * @return the user uuid of this ddl record
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this ddl record.
	 *
	 * @return the uuid of this ddl record
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns the version of this ddl record.
	 *
	 * @return the version of this ddl record
	 */
	@Override
	public String getVersion() {
		return model.getVersion();
	}

	/**
	 * Returns the version user ID of this ddl record.
	 *
	 * @return the version user ID of this ddl record
	 */
	@Override
	public long getVersionUserId() {
		return model.getVersionUserId();
	}

	/**
	 * Returns the version user name of this ddl record.
	 *
	 * @return the version user name of this ddl record
	 */
	@Override
	public String getVersionUserName() {
		return model.getVersionUserName();
	}

	/**
	 * Returns the version user uuid of this ddl record.
	 *
	 * @return the version user uuid of this ddl record
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
	 * Sets the class name of this ddl record.
	 *
	 * @param className the class name of this ddl record
	 */
	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	 * Sets the class pk of this ddl record.
	 *
	 * @param classPK the class pk of this ddl record
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the company ID of this ddl record.
	 *
	 * @param companyId the company ID of this ddl record
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this ddl record.
	 *
	 * @param createDate the create date of this ddl record
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this ddl record.
	 *
	 * @param ctCollectionId the ct collection ID of this ddl record
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the ddm storage ID of this ddl record.
	 *
	 * @param DDMStorageId the ddm storage ID of this ddl record
	 */
	@Override
	public void setDDMStorageId(long DDMStorageId) {
		model.setDDMStorageId(DDMStorageId);
	}

	/**
	 * Sets the display index of this ddl record.
	 *
	 * @param displayIndex the display index of this ddl record
	 */
	@Override
	public void setDisplayIndex(int displayIndex) {
		model.setDisplayIndex(displayIndex);
	}

	/**
	 * Sets the group ID of this ddl record.
	 *
	 * @param groupId the group ID of this ddl record
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the last publish date of this ddl record.
	 *
	 * @param lastPublishDate the last publish date of this ddl record
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the modified date of this ddl record.
	 *
	 * @param modifiedDate the modified date of this ddl record
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this ddl record.
	 *
	 * @param mvccVersion the mvcc version of this ddl record
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this ddl record.
	 *
	 * @param primaryKey the primary key of this ddl record
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the record ID of this ddl record.
	 *
	 * @param recordId the record ID of this ddl record
	 */
	@Override
	public void setRecordId(long recordId) {
		model.setRecordId(recordId);
	}

	/**
	 * Sets the record set ID of this ddl record.
	 *
	 * @param recordSetId the record set ID of this ddl record
	 */
	@Override
	public void setRecordSetId(long recordSetId) {
		model.setRecordSetId(recordSetId);
	}

	/**
	 * Sets the record set version of this ddl record.
	 *
	 * @param recordSetVersion the record set version of this ddl record
	 */
	@Override
	public void setRecordSetVersion(String recordSetVersion) {
		model.setRecordSetVersion(recordSetVersion);
	}

	/**
	 * Sets the user ID of this ddl record.
	 *
	 * @param userId the user ID of this ddl record
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this ddl record.
	 *
	 * @param userName the user name of this ddl record
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this ddl record.
	 *
	 * @param userUuid the user uuid of this ddl record
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this ddl record.
	 *
	 * @param uuid the uuid of this ddl record
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	/**
	 * Sets the version of this ddl record.
	 *
	 * @param version the version of this ddl record
	 */
	@Override
	public void setVersion(String version) {
		model.setVersion(version);
	}

	/**
	 * Sets the version user ID of this ddl record.
	 *
	 * @param versionUserId the version user ID of this ddl record
	 */
	@Override
	public void setVersionUserId(long versionUserId) {
		model.setVersionUserId(versionUserId);
	}

	/**
	 * Sets the version user name of this ddl record.
	 *
	 * @param versionUserName the version user name of this ddl record
	 */
	@Override
	public void setVersionUserName(String versionUserName) {
		model.setVersionUserName(versionUserName);
	}

	/**
	 * Sets the version user uuid of this ddl record.
	 *
	 * @param versionUserUuid the version user uuid of this ddl record
	 */
	@Override
	public void setVersionUserUuid(String versionUserUuid) {
		model.setVersionUserUuid(versionUserUuid);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	public Map<String, Function<DDLRecord, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<DDLRecord, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected DDLRecordWrapper wrap(DDLRecord ddlRecord) {
		return new DDLRecordWrapper(ddlRecord);
	}

}