/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.lists.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link DDLRecordVersion}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordVersion
 * @generated
 */
public class DDLRecordVersionWrapper
	extends BaseModelWrapper<DDLRecordVersion>
	implements DDLRecordVersion, ModelWrapper<DDLRecordVersion> {

	public DDLRecordVersionWrapper(DDLRecordVersion ddlRecordVersion) {
		super(ddlRecordVersion);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("recordVersionId", getRecordVersionId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("DDMStorageId", getDDMStorageId());
		attributes.put("recordSetId", getRecordSetId());
		attributes.put("recordSetVersion", getRecordSetVersion());
		attributes.put("recordId", getRecordId());
		attributes.put("version", getVersion());
		attributes.put("displayIndex", getDisplayIndex());
		attributes.put("status", getStatus());
		attributes.put("statusByUserId", getStatusByUserId());
		attributes.put("statusByUserName", getStatusByUserName());
		attributes.put("statusDate", getStatusDate());

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

		Long recordVersionId = (Long)attributes.get("recordVersionId");

		if (recordVersionId != null) {
			setRecordVersionId(recordVersionId);
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

		Long recordId = (Long)attributes.get("recordId");

		if (recordId != null) {
			setRecordId(recordId);
		}

		String version = (String)attributes.get("version");

		if (version != null) {
			setVersion(version);
		}

		Integer displayIndex = (Integer)attributes.get("displayIndex");

		if (displayIndex != null) {
			setDisplayIndex(displayIndex);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}

		Long statusByUserId = (Long)attributes.get("statusByUserId");

		if (statusByUserId != null) {
			setStatusByUserId(statusByUserId);
		}

		String statusByUserName = (String)attributes.get("statusByUserName");

		if (statusByUserName != null) {
			setStatusByUserName(statusByUserName);
		}

		Date statusDate = (Date)attributes.get("statusDate");

		if (statusDate != null) {
			setStatusDate(statusDate);
		}
	}

	@Override
	public DDLRecordVersion cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this ddl record version.
	 *
	 * @return the company ID of this ddl record version
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this ddl record version.
	 *
	 * @return the create date of this ddl record version
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this ddl record version.
	 *
	 * @return the ct collection ID of this ddl record version
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	@Override
	public com.liferay.dynamic.data.mapping.storage.DDMFormValues
			getDDMFormValues()
		throws com.liferay.dynamic.data.mapping.exception.StorageException {

		return model.getDDMFormValues();
	}

	/**
	 * Returns the ddm storage ID of this ddl record version.
	 *
	 * @return the ddm storage ID of this ddl record version
	 */
	@Override
	public long getDDMStorageId() {
		return model.getDDMStorageId();
	}

	/**
	 * Returns the display index of this ddl record version.
	 *
	 * @return the display index of this ddl record version
	 */
	@Override
	public int getDisplayIndex() {
		return model.getDisplayIndex();
	}

	/**
	 * Returns the group ID of this ddl record version.
	 *
	 * @return the group ID of this ddl record version
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the mvcc version of this ddl record version.
	 *
	 * @return the mvcc version of this ddl record version
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this ddl record version.
	 *
	 * @return the primary key of this ddl record version
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	@Override
	public DDLRecord getRecord()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getRecord();
	}

	/**
	 * Returns the record ID of this ddl record version.
	 *
	 * @return the record ID of this ddl record version
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
	 * Returns the record set ID of this ddl record version.
	 *
	 * @return the record set ID of this ddl record version
	 */
	@Override
	public long getRecordSetId() {
		return model.getRecordSetId();
	}

	/**
	 * Returns the record set version of this ddl record version.
	 *
	 * @return the record set version of this ddl record version
	 */
	@Override
	public String getRecordSetVersion() {
		return model.getRecordSetVersion();
	}

	/**
	 * Returns the record version ID of this ddl record version.
	 *
	 * @return the record version ID of this ddl record version
	 */
	@Override
	public long getRecordVersionId() {
		return model.getRecordVersionId();
	}

	/**
	 * Returns the status of this ddl record version.
	 *
	 * @return the status of this ddl record version
	 */
	@Override
	public int getStatus() {
		return model.getStatus();
	}

	/**
	 * Returns the status by user ID of this ddl record version.
	 *
	 * @return the status by user ID of this ddl record version
	 */
	@Override
	public long getStatusByUserId() {
		return model.getStatusByUserId();
	}

	/**
	 * Returns the status by user name of this ddl record version.
	 *
	 * @return the status by user name of this ddl record version
	 */
	@Override
	public String getStatusByUserName() {
		return model.getStatusByUserName();
	}

	/**
	 * Returns the status by user uuid of this ddl record version.
	 *
	 * @return the status by user uuid of this ddl record version
	 */
	@Override
	public String getStatusByUserUuid() {
		return model.getStatusByUserUuid();
	}

	/**
	 * Returns the status date of this ddl record version.
	 *
	 * @return the status date of this ddl record version
	 */
	@Override
	public Date getStatusDate() {
		return model.getStatusDate();
	}

	/**
	 * Returns the user ID of this ddl record version.
	 *
	 * @return the user ID of this ddl record version
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this ddl record version.
	 *
	 * @return the user name of this ddl record version
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this ddl record version.
	 *
	 * @return the user uuid of this ddl record version
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the version of this ddl record version.
	 *
	 * @return the version of this ddl record version
	 */
	@Override
	public String getVersion() {
		return model.getVersion();
	}

	/**
	 * Returns <code>true</code> if this ddl record version is approved.
	 *
	 * @return <code>true</code> if this ddl record version is approved; <code>false</code> otherwise
	 */
	@Override
	public boolean isApproved() {
		return model.isApproved();
	}

	/**
	 * Returns <code>true</code> if this ddl record version is denied.
	 *
	 * @return <code>true</code> if this ddl record version is denied; <code>false</code> otherwise
	 */
	@Override
	public boolean isDenied() {
		return model.isDenied();
	}

	/**
	 * Returns <code>true</code> if this ddl record version is a draft.
	 *
	 * @return <code>true</code> if this ddl record version is a draft; <code>false</code> otherwise
	 */
	@Override
	public boolean isDraft() {
		return model.isDraft();
	}

	/**
	 * Returns <code>true</code> if this ddl record version is expired.
	 *
	 * @return <code>true</code> if this ddl record version is expired; <code>false</code> otherwise
	 */
	@Override
	public boolean isExpired() {
		return model.isExpired();
	}

	/**
	 * Returns <code>true</code> if this ddl record version is inactive.
	 *
	 * @return <code>true</code> if this ddl record version is inactive; <code>false</code> otherwise
	 */
	@Override
	public boolean isInactive() {
		return model.isInactive();
	}

	/**
	 * Returns <code>true</code> if this ddl record version is incomplete.
	 *
	 * @return <code>true</code> if this ddl record version is incomplete; <code>false</code> otherwise
	 */
	@Override
	public boolean isIncomplete() {
		return model.isIncomplete();
	}

	/**
	 * Returns <code>true</code> if this ddl record version is pending.
	 *
	 * @return <code>true</code> if this ddl record version is pending; <code>false</code> otherwise
	 */
	@Override
	public boolean isPending() {
		return model.isPending();
	}

	/**
	 * Returns <code>true</code> if this ddl record version is scheduled.
	 *
	 * @return <code>true</code> if this ddl record version is scheduled; <code>false</code> otherwise
	 */
	@Override
	public boolean isScheduled() {
		return model.isScheduled();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this ddl record version.
	 *
	 * @param companyId the company ID of this ddl record version
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this ddl record version.
	 *
	 * @param createDate the create date of this ddl record version
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this ddl record version.
	 *
	 * @param ctCollectionId the ct collection ID of this ddl record version
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the ddm storage ID of this ddl record version.
	 *
	 * @param DDMStorageId the ddm storage ID of this ddl record version
	 */
	@Override
	public void setDDMStorageId(long DDMStorageId) {
		model.setDDMStorageId(DDMStorageId);
	}

	/**
	 * Sets the display index of this ddl record version.
	 *
	 * @param displayIndex the display index of this ddl record version
	 */
	@Override
	public void setDisplayIndex(int displayIndex) {
		model.setDisplayIndex(displayIndex);
	}

	/**
	 * Sets the group ID of this ddl record version.
	 *
	 * @param groupId the group ID of this ddl record version
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the mvcc version of this ddl record version.
	 *
	 * @param mvccVersion the mvcc version of this ddl record version
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this ddl record version.
	 *
	 * @param primaryKey the primary key of this ddl record version
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the record ID of this ddl record version.
	 *
	 * @param recordId the record ID of this ddl record version
	 */
	@Override
	public void setRecordId(long recordId) {
		model.setRecordId(recordId);
	}

	/**
	 * Sets the record set ID of this ddl record version.
	 *
	 * @param recordSetId the record set ID of this ddl record version
	 */
	@Override
	public void setRecordSetId(long recordSetId) {
		model.setRecordSetId(recordSetId);
	}

	/**
	 * Sets the record set version of this ddl record version.
	 *
	 * @param recordSetVersion the record set version of this ddl record version
	 */
	@Override
	public void setRecordSetVersion(String recordSetVersion) {
		model.setRecordSetVersion(recordSetVersion);
	}

	/**
	 * Sets the record version ID of this ddl record version.
	 *
	 * @param recordVersionId the record version ID of this ddl record version
	 */
	@Override
	public void setRecordVersionId(long recordVersionId) {
		model.setRecordVersionId(recordVersionId);
	}

	/**
	 * Sets the status of this ddl record version.
	 *
	 * @param status the status of this ddl record version
	 */
	@Override
	public void setStatus(int status) {
		model.setStatus(status);
	}

	/**
	 * Sets the status by user ID of this ddl record version.
	 *
	 * @param statusByUserId the status by user ID of this ddl record version
	 */
	@Override
	public void setStatusByUserId(long statusByUserId) {
		model.setStatusByUserId(statusByUserId);
	}

	/**
	 * Sets the status by user name of this ddl record version.
	 *
	 * @param statusByUserName the status by user name of this ddl record version
	 */
	@Override
	public void setStatusByUserName(String statusByUserName) {
		model.setStatusByUserName(statusByUserName);
	}

	/**
	 * Sets the status by user uuid of this ddl record version.
	 *
	 * @param statusByUserUuid the status by user uuid of this ddl record version
	 */
	@Override
	public void setStatusByUserUuid(String statusByUserUuid) {
		model.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	 * Sets the status date of this ddl record version.
	 *
	 * @param statusDate the status date of this ddl record version
	 */
	@Override
	public void setStatusDate(Date statusDate) {
		model.setStatusDate(statusDate);
	}

	/**
	 * Sets the user ID of this ddl record version.
	 *
	 * @param userId the user ID of this ddl record version
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this ddl record version.
	 *
	 * @param userName the user name of this ddl record version
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this ddl record version.
	 *
	 * @param userUuid the user uuid of this ddl record version
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the version of this ddl record version.
	 *
	 * @param version the version of this ddl record version
	 */
	@Override
	public void setVersion(String version) {
		model.setVersion(version);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	public Map<String, Function<DDLRecordVersion, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<DDLRecordVersion, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	protected DDLRecordVersionWrapper wrap(DDLRecordVersion ddlRecordVersion) {
		return new DDLRecordVersionWrapper(ddlRecordVersion);
	}

}