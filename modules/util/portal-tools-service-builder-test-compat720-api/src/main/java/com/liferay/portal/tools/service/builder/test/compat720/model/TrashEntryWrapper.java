/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat720.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link TrashEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see TrashEntry
 * @generated
 */
public class TrashEntryWrapper
	extends BaseModelWrapper<TrashEntry>
	implements ModelWrapper<TrashEntry>, TrashEntry {

	public TrashEntryWrapper(TrashEntry trashEntry) {
		super(trashEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("trashEntryId", getTrashEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long trashEntryId = (Long)attributes.get("trashEntryId");

		if (trashEntryId != null) {
			setTrashEntryId(trashEntryId);
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

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}
	}

	/**
	 * Returns the company ID of this trash entry.
	 *
	 * @return the company ID of this trash entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this trash entry.
	 *
	 * @return the create date of this trash entry
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the group ID of this trash entry.
	 *
	 * @return the group ID of this trash entry
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this trash entry.
	 *
	 * @return the modified date of this trash entry
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the name of this trash entry.
	 *
	 * @return the name of this trash entry
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this trash entry.
	 *
	 * @return the primary key of this trash entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the status of this trash entry.
	 *
	 * @return the status of this trash entry
	 */
	@Override
	public int getStatus() {
		return model.getStatus();
	}

	/**
	 * Returns the trash entry created when this trash entry was moved to the Recycle Bin. The trash entry may belong to one of the ancestors of this trash entry.
	 *
	 * @return the trash entry created when this trash entry was moved to the Recycle Bin
	 */
	@Override
	public com.liferay.trash.kernel.model.TrashEntry getTrashEntry()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getTrashEntry();
	}

	/**
	 * Returns the class primary key of the trash entry for this trash entry.
	 *
	 * @return the class primary key of the trash entry for this trash entry
	 */
	@Override
	public long getTrashEntryClassPK() {
		return model.getTrashEntryClassPK();
	}

	/**
	 * Returns the trash entry ID of this trash entry.
	 *
	 * @return the trash entry ID of this trash entry
	 */
	@Override
	public long getTrashEntryId() {
		return model.getTrashEntryId();
	}

	/**
	 * Returns the trash handler for this trash entry.
	 *
	 * @return the trash handler for this trash entry
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public com.liferay.portal.kernel.trash.TrashHandler getTrashHandler() {
		return model.getTrashHandler();
	}

	/**
	 * Returns the user ID of this trash entry.
	 *
	 * @return the user ID of this trash entry
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this trash entry.
	 *
	 * @return the user name of this trash entry
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this trash entry.
	 *
	 * @return the user uuid of this trash entry
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns <code>true</code> if this trash entry is in the Recycle Bin.
	 *
	 * @return <code>true</code> if this trash entry is in the Recycle Bin; <code>false</code> otherwise
	 */
	@Override
	public boolean isInTrash() {
		return model.isInTrash();
	}

	/**
	 * Returns <code>true</code> if the parent of this trash entry is in the Recycle Bin.
	 *
	 * @return <code>true</code> if the parent of this trash entry is in the Recycle Bin; <code>false</code> otherwise
	 */
	@Override
	public boolean isInTrashContainer() {
		return model.isInTrashContainer();
	}

	@Override
	public boolean isInTrashExplicitly() {
		return model.isInTrashExplicitly();
	}

	@Override
	public boolean isInTrashImplicitly() {
		return model.isInTrashImplicitly();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this trash entry.
	 *
	 * @param companyId the company ID of this trash entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this trash entry.
	 *
	 * @param createDate the create date of this trash entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the group ID of this trash entry.
	 *
	 * @param groupId the group ID of this trash entry
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this trash entry.
	 *
	 * @param modifiedDate the modified date of this trash entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the name of this trash entry.
	 *
	 * @param name the name of this trash entry
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this trash entry.
	 *
	 * @param primaryKey the primary key of this trash entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the trash entry ID of this trash entry.
	 *
	 * @param trashEntryId the trash entry ID of this trash entry
	 */
	@Override
	public void setTrashEntryId(long trashEntryId) {
		model.setTrashEntryId(trashEntryId);
	}

	/**
	 * Sets the user ID of this trash entry.
	 *
	 * @param userId the user ID of this trash entry
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this trash entry.
	 *
	 * @param userName the user name of this trash entry
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this trash entry.
	 *
	 * @param userUuid the user uuid of this trash entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected TrashEntryWrapper wrap(TrashEntry trashEntry) {
		return new TrashEntryWrapper(trashEntry);
	}

}
// SB-Hash:-1985782566