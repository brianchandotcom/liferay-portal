/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.opener.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link DLOpenerFileEntryReference}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLOpenerFileEntryReference
 * @generated
 */
public class DLOpenerFileEntryReferenceWrapper
	extends BaseModelWrapper<DLOpenerFileEntryReference>
	implements DLOpenerFileEntryReference,
			   ModelWrapper<DLOpenerFileEntryReference> {

	public DLOpenerFileEntryReferenceWrapper(
		DLOpenerFileEntryReference dlOpenerFileEntryReference) {

		super(dlOpenerFileEntryReference);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put(
			"dlOpenerFileEntryReferenceId", getDlOpenerFileEntryReferenceId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("referenceKey", getReferenceKey());
		attributes.put("referenceType", getReferenceType());
		attributes.put("fileEntryId", getFileEntryId());
		attributes.put("type", getType());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long dlOpenerFileEntryReferenceId = (Long)attributes.get(
			"dlOpenerFileEntryReferenceId");

		if (dlOpenerFileEntryReferenceId != null) {
			setDlOpenerFileEntryReferenceId(dlOpenerFileEntryReferenceId);
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

		String referenceKey = (String)attributes.get("referenceKey");

		if (referenceKey != null) {
			setReferenceKey(referenceKey);
		}

		String referenceType = (String)attributes.get("referenceType");

		if (referenceType != null) {
			setReferenceType(referenceType);
		}

		Long fileEntryId = (Long)attributes.get("fileEntryId");

		if (fileEntryId != null) {
			setFileEntryId(fileEntryId);
		}

		Integer type = (Integer)attributes.get("type");

		if (type != null) {
			setType(type);
		}
	}

	@Override
	public DLOpenerFileEntryReference cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this dl opener file entry reference.
	 *
	 * @return the company ID of this dl opener file entry reference
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this dl opener file entry reference.
	 *
	 * @return the create date of this dl opener file entry reference
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the dl opener file entry reference ID of this dl opener file entry reference.
	 *
	 * @return the dl opener file entry reference ID of this dl opener file entry reference
	 */
	@Override
	public long getDlOpenerFileEntryReferenceId() {
		return model.getDlOpenerFileEntryReferenceId();
	}

	/**
	 * Returns the file entry ID of this dl opener file entry reference.
	 *
	 * @return the file entry ID of this dl opener file entry reference
	 */
	@Override
	public long getFileEntryId() {
		return model.getFileEntryId();
	}

	/**
	 * Returns the group ID of this dl opener file entry reference.
	 *
	 * @return the group ID of this dl opener file entry reference
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this dl opener file entry reference.
	 *
	 * @return the modified date of this dl opener file entry reference
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the primary key of this dl opener file entry reference.
	 *
	 * @return the primary key of this dl opener file entry reference
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the reference key of this dl opener file entry reference.
	 *
	 * @return the reference key of this dl opener file entry reference
	 */
	@Override
	public String getReferenceKey() {
		return model.getReferenceKey();
	}

	/**
	 * Returns the reference type of this dl opener file entry reference.
	 *
	 * @return the reference type of this dl opener file entry reference
	 */
	@Override
	public String getReferenceType() {
		return model.getReferenceType();
	}

	/**
	 * Returns the type of this dl opener file entry reference.
	 *
	 * @return the type of this dl opener file entry reference
	 */
	@Override
	public int getType() {
		return model.getType();
	}

	/**
	 * Returns the user ID of this dl opener file entry reference.
	 *
	 * @return the user ID of this dl opener file entry reference
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this dl opener file entry reference.
	 *
	 * @return the user name of this dl opener file entry reference
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this dl opener file entry reference.
	 *
	 * @return the user uuid of this dl opener file entry reference
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
	 * Sets the company ID of this dl opener file entry reference.
	 *
	 * @param companyId the company ID of this dl opener file entry reference
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this dl opener file entry reference.
	 *
	 * @param createDate the create date of this dl opener file entry reference
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the dl opener file entry reference ID of this dl opener file entry reference.
	 *
	 * @param dlOpenerFileEntryReferenceId the dl opener file entry reference ID of this dl opener file entry reference
	 */
	@Override
	public void setDlOpenerFileEntryReferenceId(
		long dlOpenerFileEntryReferenceId) {

		model.setDlOpenerFileEntryReferenceId(dlOpenerFileEntryReferenceId);
	}

	/**
	 * Sets the file entry ID of this dl opener file entry reference.
	 *
	 * @param fileEntryId the file entry ID of this dl opener file entry reference
	 */
	@Override
	public void setFileEntryId(long fileEntryId) {
		model.setFileEntryId(fileEntryId);
	}

	/**
	 * Sets the group ID of this dl opener file entry reference.
	 *
	 * @param groupId the group ID of this dl opener file entry reference
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this dl opener file entry reference.
	 *
	 * @param modifiedDate the modified date of this dl opener file entry reference
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the primary key of this dl opener file entry reference.
	 *
	 * @param primaryKey the primary key of this dl opener file entry reference
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the reference key of this dl opener file entry reference.
	 *
	 * @param referenceKey the reference key of this dl opener file entry reference
	 */
	@Override
	public void setReferenceKey(String referenceKey) {
		model.setReferenceKey(referenceKey);
	}

	/**
	 * Sets the reference type of this dl opener file entry reference.
	 *
	 * @param referenceType the reference type of this dl opener file entry reference
	 */
	@Override
	public void setReferenceType(String referenceType) {
		model.setReferenceType(referenceType);
	}

	/**
	 * Sets the type of this dl opener file entry reference.
	 *
	 * @param type the type of this dl opener file entry reference
	 */
	@Override
	public void setType(int type) {
		model.setType(type);
	}

	/**
	 * Sets the user ID of this dl opener file entry reference.
	 *
	 * @param userId the user ID of this dl opener file entry reference
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this dl opener file entry reference.
	 *
	 * @param userName the user name of this dl opener file entry reference
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this dl opener file entry reference.
	 *
	 * @param userUuid the user uuid of this dl opener file entry reference
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
	protected DLOpenerFileEntryReferenceWrapper wrap(
		DLOpenerFileEntryReference dlOpenerFileEntryReference) {

		return new DLOpenerFileEntryReferenceWrapper(
			dlOpenerFileEntryReference);
	}

}