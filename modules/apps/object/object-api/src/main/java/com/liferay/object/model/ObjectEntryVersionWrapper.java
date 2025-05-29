/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
 * This class is a wrapper for {@link ObjectEntryVersion}.
 * </p>
 *
 * @author Marco Leo
 * @see ObjectEntryVersion
 * @generated
 */
public class ObjectEntryVersionWrapper
	extends BaseModelWrapper<ObjectEntryVersion>
	implements ModelWrapper<ObjectEntryVersion>, ObjectEntryVersion {

	public ObjectEntryVersionWrapper(ObjectEntryVersion objectEntryVersion) {
		super(objectEntryVersion);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("objectEntryVersionId", getObjectEntryVersionId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("objectDefinitionId", getObjectDefinitionId());
		attributes.put("objectEntryId", getObjectEntryId());
		attributes.put("content", getContent());
		attributes.put("displayDate", getDisplayDate());
		attributes.put("expirationDate", getExpirationDate());
		attributes.put("reviewDate", getReviewDate());
		attributes.put("version", getVersion());
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

		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long objectEntryVersionId = (Long)attributes.get(
			"objectEntryVersionId");

		if (objectEntryVersionId != null) {
			setObjectEntryVersionId(objectEntryVersionId);
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

		Long objectEntryId = (Long)attributes.get("objectEntryId");

		if (objectEntryId != null) {
			setObjectEntryId(objectEntryId);
		}

		String content = (String)attributes.get("content");

		if (content != null) {
			setContent(content);
		}

		Date displayDate = (Date)attributes.get("displayDate");

		if (displayDate != null) {
			setDisplayDate(displayDate);
		}

		Date expirationDate = (Date)attributes.get("expirationDate");

		if (expirationDate != null) {
			setExpirationDate(expirationDate);
		}

		Date reviewDate = (Date)attributes.get("reviewDate");

		if (reviewDate != null) {
			setReviewDate(reviewDate);
		}

		Integer version = (Integer)attributes.get("version");

		if (version != null) {
			setVersion(version);
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
	public ObjectEntryVersion cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this object entry version.
	 *
	 * @return the company ID of this object entry version
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the content of this object entry version.
	 *
	 * @return the content of this object entry version
	 */
	@Override
	public String getContent() {
		return model.getContent();
	}

	/**
	 * Returns the create date of this object entry version.
	 *
	 * @return the create date of this object entry version
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the display date of this object entry version.
	 *
	 * @return the display date of this object entry version
	 */
	@Override
	public Date getDisplayDate() {
		return model.getDisplayDate();
	}

	/**
	 * Returns the expiration date of this object entry version.
	 *
	 * @return the expiration date of this object entry version
	 */
	@Override
	public Date getExpirationDate() {
		return model.getExpirationDate();
	}

	/**
	 * Returns the modified date of this object entry version.
	 *
	 * @return the modified date of this object entry version
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this object entry version.
	 *
	 * @return the mvcc version of this object entry version
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the object definition ID of this object entry version.
	 *
	 * @return the object definition ID of this object entry version
	 */
	@Override
	public long getObjectDefinitionId() {
		return model.getObjectDefinitionId();
	}

	/**
	 * Returns the object entry ID of this object entry version.
	 *
	 * @return the object entry ID of this object entry version
	 */
	@Override
	public long getObjectEntryId() {
		return model.getObjectEntryId();
	}

	/**
	 * Returns the object entry version ID of this object entry version.
	 *
	 * @return the object entry version ID of this object entry version
	 */
	@Override
	public long getObjectEntryVersionId() {
		return model.getObjectEntryVersionId();
	}

	/**
	 * Returns the primary key of this object entry version.
	 *
	 * @return the primary key of this object entry version
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the review date of this object entry version.
	 *
	 * @return the review date of this object entry version
	 */
	@Override
	public Date getReviewDate() {
		return model.getReviewDate();
	}

	/**
	 * Returns the status of this object entry version.
	 *
	 * @return the status of this object entry version
	 */
	@Override
	public int getStatus() {
		return model.getStatus();
	}

	/**
	 * Returns the status by user ID of this object entry version.
	 *
	 * @return the status by user ID of this object entry version
	 */
	@Override
	public long getStatusByUserId() {
		return model.getStatusByUserId();
	}

	/**
	 * Returns the status by user name of this object entry version.
	 *
	 * @return the status by user name of this object entry version
	 */
	@Override
	public String getStatusByUserName() {
		return model.getStatusByUserName();
	}

	/**
	 * Returns the status by user uuid of this object entry version.
	 *
	 * @return the status by user uuid of this object entry version
	 */
	@Override
	public String getStatusByUserUuid() {
		return model.getStatusByUserUuid();
	}

	/**
	 * Returns the status date of this object entry version.
	 *
	 * @return the status date of this object entry version
	 */
	@Override
	public Date getStatusDate() {
		return model.getStatusDate();
	}

	/**
	 * Returns the user ID of this object entry version.
	 *
	 * @return the user ID of this object entry version
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this object entry version.
	 *
	 * @return the user name of this object entry version
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this object entry version.
	 *
	 * @return the user uuid of this object entry version
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this object entry version.
	 *
	 * @return the uuid of this object entry version
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns the version of this object entry version.
	 *
	 * @return the version of this object entry version
	 */
	@Override
	public int getVersion() {
		return model.getVersion();
	}

	/**
	 * Returns <code>true</code> if this object entry version is approved.
	 *
	 * @return <code>true</code> if this object entry version is approved; <code>false</code> otherwise
	 */
	@Override
	public boolean isApproved() {
		return model.isApproved();
	}

	/**
	 * Returns <code>true</code> if this object entry version is denied.
	 *
	 * @return <code>true</code> if this object entry version is denied; <code>false</code> otherwise
	 */
	@Override
	public boolean isDenied() {
		return model.isDenied();
	}

	/**
	 * Returns <code>true</code> if this object entry version is a draft.
	 *
	 * @return <code>true</code> if this object entry version is a draft; <code>false</code> otherwise
	 */
	@Override
	public boolean isDraft() {
		return model.isDraft();
	}

	/**
	 * Returns <code>true</code> if this object entry version is expired.
	 *
	 * @return <code>true</code> if this object entry version is expired; <code>false</code> otherwise
	 */
	@Override
	public boolean isExpired() {
		return model.isExpired();
	}

	/**
	 * Returns <code>true</code> if this object entry version is inactive.
	 *
	 * @return <code>true</code> if this object entry version is inactive; <code>false</code> otherwise
	 */
	@Override
	public boolean isInactive() {
		return model.isInactive();
	}

	/**
	 * Returns <code>true</code> if this object entry version is incomplete.
	 *
	 * @return <code>true</code> if this object entry version is incomplete; <code>false</code> otherwise
	 */
	@Override
	public boolean isIncomplete() {
		return model.isIncomplete();
	}

	/**
	 * Returns <code>true</code> if this object entry version is pending.
	 *
	 * @return <code>true</code> if this object entry version is pending; <code>false</code> otherwise
	 */
	@Override
	public boolean isPending() {
		return model.isPending();
	}

	/**
	 * Returns <code>true</code> if this object entry version is scheduled.
	 *
	 * @return <code>true</code> if this object entry version is scheduled; <code>false</code> otherwise
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
	 * Sets the company ID of this object entry version.
	 *
	 * @param companyId the company ID of this object entry version
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the content of this object entry version.
	 *
	 * @param content the content of this object entry version
	 */
	@Override
	public void setContent(String content) {
		model.setContent(content);
	}

	/**
	 * Sets the create date of this object entry version.
	 *
	 * @param createDate the create date of this object entry version
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the display date of this object entry version.
	 *
	 * @param displayDate the display date of this object entry version
	 */
	@Override
	public void setDisplayDate(Date displayDate) {
		model.setDisplayDate(displayDate);
	}

	/**
	 * Sets the expiration date of this object entry version.
	 *
	 * @param expirationDate the expiration date of this object entry version
	 */
	@Override
	public void setExpirationDate(Date expirationDate) {
		model.setExpirationDate(expirationDate);
	}

	/**
	 * Sets the modified date of this object entry version.
	 *
	 * @param modifiedDate the modified date of this object entry version
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this object entry version.
	 *
	 * @param mvccVersion the mvcc version of this object entry version
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the object definition ID of this object entry version.
	 *
	 * @param objectDefinitionId the object definition ID of this object entry version
	 */
	@Override
	public void setObjectDefinitionId(long objectDefinitionId) {
		model.setObjectDefinitionId(objectDefinitionId);
	}

	/**
	 * Sets the object entry ID of this object entry version.
	 *
	 * @param objectEntryId the object entry ID of this object entry version
	 */
	@Override
	public void setObjectEntryId(long objectEntryId) {
		model.setObjectEntryId(objectEntryId);
	}

	/**
	 * Sets the object entry version ID of this object entry version.
	 *
	 * @param objectEntryVersionId the object entry version ID of this object entry version
	 */
	@Override
	public void setObjectEntryVersionId(long objectEntryVersionId) {
		model.setObjectEntryVersionId(objectEntryVersionId);
	}

	/**
	 * Sets the primary key of this object entry version.
	 *
	 * @param primaryKey the primary key of this object entry version
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the review date of this object entry version.
	 *
	 * @param reviewDate the review date of this object entry version
	 */
	@Override
	public void setReviewDate(Date reviewDate) {
		model.setReviewDate(reviewDate);
	}

	/**
	 * Sets the status of this object entry version.
	 *
	 * @param status the status of this object entry version
	 */
	@Override
	public void setStatus(int status) {
		model.setStatus(status);
	}

	/**
	 * Sets the status by user ID of this object entry version.
	 *
	 * @param statusByUserId the status by user ID of this object entry version
	 */
	@Override
	public void setStatusByUserId(long statusByUserId) {
		model.setStatusByUserId(statusByUserId);
	}

	/**
	 * Sets the status by user name of this object entry version.
	 *
	 * @param statusByUserName the status by user name of this object entry version
	 */
	@Override
	public void setStatusByUserName(String statusByUserName) {
		model.setStatusByUserName(statusByUserName);
	}

	/**
	 * Sets the status by user uuid of this object entry version.
	 *
	 * @param statusByUserUuid the status by user uuid of this object entry version
	 */
	@Override
	public void setStatusByUserUuid(String statusByUserUuid) {
		model.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	 * Sets the status date of this object entry version.
	 *
	 * @param statusDate the status date of this object entry version
	 */
	@Override
	public void setStatusDate(Date statusDate) {
		model.setStatusDate(statusDate);
	}

	/**
	 * Sets the user ID of this object entry version.
	 *
	 * @param userId the user ID of this object entry version
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this object entry version.
	 *
	 * @param userName the user name of this object entry version
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this object entry version.
	 *
	 * @param userUuid the user uuid of this object entry version
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this object entry version.
	 *
	 * @param uuid the uuid of this object entry version
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	/**
	 * Sets the version of this object entry version.
	 *
	 * @param version the version of this object entry version
	 */
	@Override
	public void setVersion(int version) {
		model.setVersion(version);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected ObjectEntryVersionWrapper wrap(
		ObjectEntryVersion objectEntryVersion) {

		return new ObjectEntryVersionWrapper(objectEntryVersion);
	}

}