/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.qualifier.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CommerceQualifierEntry}.
 * </p>
 *
 * @author Riccardo Alberti
 * @see CommerceQualifierEntry
 * @generated
 */
public class CommerceQualifierEntryWrapper
	extends BaseModelWrapper<CommerceQualifierEntry>
	implements CommerceQualifierEntry, ModelWrapper<CommerceQualifierEntry> {

	public CommerceQualifierEntryWrapper(
		CommerceQualifierEntry commerceQualifierEntry) {

		super(commerceQualifierEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put(
			"commerceQualifierEntryId", getCommerceQualifierEntryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("sourceClassNameId", getSourceClassNameId());
		attributes.put("sourceClassPK", getSourceClassPK());
		attributes.put(
			"sourceCommerceQualifierMetadataKey",
			getSourceCommerceQualifierMetadataKey());
		attributes.put("targetClassNameId", getTargetClassNameId());
		attributes.put("targetClassPK", getTargetClassPK());
		attributes.put(
			"targetCommerceQualifierMetadataKey",
			getTargetCommerceQualifierMetadataKey());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long commerceQualifierEntryId = (Long)attributes.get(
			"commerceQualifierEntryId");

		if (commerceQualifierEntryId != null) {
			setCommerceQualifierEntryId(commerceQualifierEntryId);
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

		Long sourceClassNameId = (Long)attributes.get("sourceClassNameId");

		if (sourceClassNameId != null) {
			setSourceClassNameId(sourceClassNameId);
		}

		Long sourceClassPK = (Long)attributes.get("sourceClassPK");

		if (sourceClassPK != null) {
			setSourceClassPK(sourceClassPK);
		}

		String sourceCommerceQualifierMetadataKey = (String)attributes.get(
			"sourceCommerceQualifierMetadataKey");

		if (sourceCommerceQualifierMetadataKey != null) {
			setSourceCommerceQualifierMetadataKey(
				sourceCommerceQualifierMetadataKey);
		}

		Long targetClassNameId = (Long)attributes.get("targetClassNameId");

		if (targetClassNameId != null) {
			setTargetClassNameId(targetClassNameId);
		}

		Long targetClassPK = (Long)attributes.get("targetClassPK");

		if (targetClassPK != null) {
			setTargetClassPK(targetClassPK);
		}

		String targetCommerceQualifierMetadataKey = (String)attributes.get(
			"targetCommerceQualifierMetadataKey");

		if (targetCommerceQualifierMetadataKey != null) {
			setTargetCommerceQualifierMetadataKey(
				targetCommerceQualifierMetadataKey);
		}
	}

	@Override
	public CommerceQualifierEntry cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the commerce qualifier entry ID of this commerce qualifier entry.
	 *
	 * @return the commerce qualifier entry ID of this commerce qualifier entry
	 */
	@Override
	public long getCommerceQualifierEntryId() {
		return model.getCommerceQualifierEntryId();
	}

	/**
	 * Returns the company ID of this commerce qualifier entry.
	 *
	 * @return the company ID of this commerce qualifier entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce qualifier entry.
	 *
	 * @return the create date of this commerce qualifier entry
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the modified date of this commerce qualifier entry.
	 *
	 * @return the modified date of this commerce qualifier entry
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this commerce qualifier entry.
	 *
	 * @return the mvcc version of this commerce qualifier entry
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this commerce qualifier entry.
	 *
	 * @return the primary key of this commerce qualifier entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the source class name ID of this commerce qualifier entry.
	 *
	 * @return the source class name ID of this commerce qualifier entry
	 */
	@Override
	public long getSourceClassNameId() {
		return model.getSourceClassNameId();
	}

	/**
	 * Returns the source class pk of this commerce qualifier entry.
	 *
	 * @return the source class pk of this commerce qualifier entry
	 */
	@Override
	public long getSourceClassPK() {
		return model.getSourceClassPK();
	}

	/**
	 * Returns the source commerce qualifier metadata key of this commerce qualifier entry.
	 *
	 * @return the source commerce qualifier metadata key of this commerce qualifier entry
	 */
	@Override
	public String getSourceCommerceQualifierMetadataKey() {
		return model.getSourceCommerceQualifierMetadataKey();
	}

	/**
	 * Returns the target class name ID of this commerce qualifier entry.
	 *
	 * @return the target class name ID of this commerce qualifier entry
	 */
	@Override
	public long getTargetClassNameId() {
		return model.getTargetClassNameId();
	}

	/**
	 * Returns the target class pk of this commerce qualifier entry.
	 *
	 * @return the target class pk of this commerce qualifier entry
	 */
	@Override
	public long getTargetClassPK() {
		return model.getTargetClassPK();
	}

	/**
	 * Returns the target commerce qualifier metadata key of this commerce qualifier entry.
	 *
	 * @return the target commerce qualifier metadata key of this commerce qualifier entry
	 */
	@Override
	public String getTargetCommerceQualifierMetadataKey() {
		return model.getTargetCommerceQualifierMetadataKey();
	}

	/**
	 * Returns the user ID of this commerce qualifier entry.
	 *
	 * @return the user ID of this commerce qualifier entry
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this commerce qualifier entry.
	 *
	 * @return the user name of this commerce qualifier entry
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce qualifier entry.
	 *
	 * @return the user uuid of this commerce qualifier entry
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the commerce qualifier entry ID of this commerce qualifier entry.
	 *
	 * @param commerceQualifierEntryId the commerce qualifier entry ID of this commerce qualifier entry
	 */
	@Override
	public void setCommerceQualifierEntryId(long commerceQualifierEntryId) {
		model.setCommerceQualifierEntryId(commerceQualifierEntryId);
	}

	/**
	 * Sets the company ID of this commerce qualifier entry.
	 *
	 * @param companyId the company ID of this commerce qualifier entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce qualifier entry.
	 *
	 * @param createDate the create date of this commerce qualifier entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the modified date of this commerce qualifier entry.
	 *
	 * @param modifiedDate the modified date of this commerce qualifier entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this commerce qualifier entry.
	 *
	 * @param mvccVersion the mvcc version of this commerce qualifier entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this commerce qualifier entry.
	 *
	 * @param primaryKey the primary key of this commerce qualifier entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the source class name ID of this commerce qualifier entry.
	 *
	 * @param sourceClassNameId the source class name ID of this commerce qualifier entry
	 */
	@Override
	public void setSourceClassNameId(long sourceClassNameId) {
		model.setSourceClassNameId(sourceClassNameId);
	}

	/**
	 * Sets the source class pk of this commerce qualifier entry.
	 *
	 * @param sourceClassPK the source class pk of this commerce qualifier entry
	 */
	@Override
	public void setSourceClassPK(long sourceClassPK) {
		model.setSourceClassPK(sourceClassPK);
	}

	/**
	 * Sets the source commerce qualifier metadata key of this commerce qualifier entry.
	 *
	 * @param sourceCommerceQualifierMetadataKey the source commerce qualifier metadata key of this commerce qualifier entry
	 */
	@Override
	public void setSourceCommerceQualifierMetadataKey(
		String sourceCommerceQualifierMetadataKey) {

		model.setSourceCommerceQualifierMetadataKey(
			sourceCommerceQualifierMetadataKey);
	}

	/**
	 * Sets the target class name ID of this commerce qualifier entry.
	 *
	 * @param targetClassNameId the target class name ID of this commerce qualifier entry
	 */
	@Override
	public void setTargetClassNameId(long targetClassNameId) {
		model.setTargetClassNameId(targetClassNameId);
	}

	/**
	 * Sets the target class pk of this commerce qualifier entry.
	 *
	 * @param targetClassPK the target class pk of this commerce qualifier entry
	 */
	@Override
	public void setTargetClassPK(long targetClassPK) {
		model.setTargetClassPK(targetClassPK);
	}

	/**
	 * Sets the target commerce qualifier metadata key of this commerce qualifier entry.
	 *
	 * @param targetCommerceQualifierMetadataKey the target commerce qualifier metadata key of this commerce qualifier entry
	 */
	@Override
	public void setTargetCommerceQualifierMetadataKey(
		String targetCommerceQualifierMetadataKey) {

		model.setTargetCommerceQualifierMetadataKey(
			targetCommerceQualifierMetadataKey);
	}

	/**
	 * Sets the user ID of this commerce qualifier entry.
	 *
	 * @param userId the user ID of this commerce qualifier entry
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce qualifier entry.
	 *
	 * @param userName the user name of this commerce qualifier entry
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce qualifier entry.
	 *
	 * @param userUuid the user uuid of this commerce qualifier entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected CommerceQualifierEntryWrapper wrap(
		CommerceQualifierEntry commerceQualifierEntry) {

		return new CommerceQualifierEntryWrapper(commerceQualifierEntry);
	}

}