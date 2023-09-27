/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.changeset.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link ChangesetEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ChangesetEntry
 * @generated
 */
public class ChangesetEntryWrapper
	extends BaseModelWrapper<ChangesetEntry>
	implements ChangesetEntry, ModelWrapper<ChangesetEntry> {

	public ChangesetEntryWrapper(ChangesetEntry changesetEntry) {
		super(changesetEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("changesetEntryId", getChangesetEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("changesetCollectionId", getChangesetCollectionId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long changesetEntryId = (Long)attributes.get("changesetEntryId");

		if (changesetEntryId != null) {
			setChangesetEntryId(changesetEntryId);
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

		Long changesetCollectionId = (Long)attributes.get(
			"changesetCollectionId");

		if (changesetCollectionId != null) {
			setChangesetCollectionId(changesetCollectionId);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}
	}

	@Override
	public ChangesetEntry cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the changeset collection ID of this changeset entry.
	 *
	 * @return the changeset collection ID of this changeset entry
	 */
	@Override
	public long getChangesetCollectionId() {
		return model.getChangesetCollectionId();
	}

	/**
	 * Returns the changeset entry ID of this changeset entry.
	 *
	 * @return the changeset entry ID of this changeset entry
	 */
	@Override
	public long getChangesetEntryId() {
		return model.getChangesetEntryId();
	}

	/**
	 * Returns the fully qualified class name of this changeset entry.
	 *
	 * @return the fully qualified class name of this changeset entry
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this changeset entry.
	 *
	 * @return the class name ID of this changeset entry
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this changeset entry.
	 *
	 * @return the class pk of this changeset entry
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the company ID of this changeset entry.
	 *
	 * @return the company ID of this changeset entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this changeset entry.
	 *
	 * @return the create date of this changeset entry
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the group ID of this changeset entry.
	 *
	 * @return the group ID of this changeset entry
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this changeset entry.
	 *
	 * @return the modified date of this changeset entry
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the primary key of this changeset entry.
	 *
	 * @return the primary key of this changeset entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this changeset entry.
	 *
	 * @return the user ID of this changeset entry
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this changeset entry.
	 *
	 * @return the user name of this changeset entry
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this changeset entry.
	 *
	 * @return the user uuid of this changeset entry
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
	 * Sets the changeset collection ID of this changeset entry.
	 *
	 * @param changesetCollectionId the changeset collection ID of this changeset entry
	 */
	@Override
	public void setChangesetCollectionId(long changesetCollectionId) {
		model.setChangesetCollectionId(changesetCollectionId);
	}

	/**
	 * Sets the changeset entry ID of this changeset entry.
	 *
	 * @param changesetEntryId the changeset entry ID of this changeset entry
	 */
	@Override
	public void setChangesetEntryId(long changesetEntryId) {
		model.setChangesetEntryId(changesetEntryId);
	}

	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	 * Sets the class name ID of this changeset entry.
	 *
	 * @param classNameId the class name ID of this changeset entry
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this changeset entry.
	 *
	 * @param classPK the class pk of this changeset entry
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the company ID of this changeset entry.
	 *
	 * @param companyId the company ID of this changeset entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this changeset entry.
	 *
	 * @param createDate the create date of this changeset entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the group ID of this changeset entry.
	 *
	 * @param groupId the group ID of this changeset entry
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this changeset entry.
	 *
	 * @param modifiedDate the modified date of this changeset entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the primary key of this changeset entry.
	 *
	 * @param primaryKey the primary key of this changeset entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this changeset entry.
	 *
	 * @param userId the user ID of this changeset entry
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this changeset entry.
	 *
	 * @param userName the user name of this changeset entry
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this changeset entry.
	 *
	 * @param userUuid the user uuid of this changeset entry
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
	protected ChangesetEntryWrapper wrap(ChangesetEntry changesetEntry) {
		return new ChangesetEntryWrapper(changesetEntry);
	}

}