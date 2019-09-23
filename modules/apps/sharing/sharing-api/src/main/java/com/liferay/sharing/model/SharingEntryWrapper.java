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

package com.liferay.sharing.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link SharingEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SharingEntry
 * @generated
 */
public class SharingEntryWrapper
	extends BaseModelWrapper<SharingEntry>
	implements ModelWrapper<SharingEntry>, SharingEntry {

	public SharingEntryWrapper(SharingEntry sharingEntry) {
		super(sharingEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("sharingEntryId", getSharingEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("toUserId", getToUserId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("shareable", isShareable());
		attributes.put("actionIds", getActionIds());
		attributes.put("expirationDate", getExpirationDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long sharingEntryId = (Long)attributes.get("sharingEntryId");

		if (sharingEntryId != null) {
			setSharingEntryId(sharingEntryId);
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

		Long toUserId = (Long)attributes.get("toUserId");

		if (toUserId != null) {
			setToUserId(toUserId);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		Boolean shareable = (Boolean)attributes.get("shareable");

		if (shareable != null) {
			setShareable(shareable);
		}

		Long actionIds = (Long)attributes.get("actionIds");

		if (actionIds != null) {
			setActionIds(actionIds);
		}

		Date expirationDate = (Date)attributes.get("expirationDate");

		if (expirationDate != null) {
			setExpirationDate(expirationDate);
		}
	}

	/**
	 * Returns the action IDs of this sharing entry.
	 *
	 * @return the action IDs of this sharing entry
	 */
	@Override
	public long getActionIds() {
		return model.getActionIds();
	}

	/**
	 * Returns the fully qualified class name of this sharing entry.
	 *
	 * @return the fully qualified class name of this sharing entry
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this sharing entry.
	 *
	 * @return the class name ID of this sharing entry
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this sharing entry.
	 *
	 * @return the class pk of this sharing entry
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the company ID of this sharing entry.
	 *
	 * @return the company ID of this sharing entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this sharing entry.
	 *
	 * @return the create date of this sharing entry
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the expiration date of this sharing entry.
	 *
	 * @return the expiration date of this sharing entry
	 */
	@Override
	public Date getExpirationDate() {
		return model.getExpirationDate();
	}

	/**
	 * Returns the group ID of this sharing entry.
	 *
	 * @return the group ID of this sharing entry
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this sharing entry.
	 *
	 * @return the modified date of this sharing entry
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the primary key of this sharing entry.
	 *
	 * @return the primary key of this sharing entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the shareable of this sharing entry.
	 *
	 * @return the shareable of this sharing entry
	 */
	@Override
	public boolean getShareable() {
		return model.getShareable();
	}

	/**
	 * Returns the sharing entry ID of this sharing entry.
	 *
	 * @return the sharing entry ID of this sharing entry
	 */
	@Override
	public long getSharingEntryId() {
		return model.getSharingEntryId();
	}

	/**
	 * Returns the to user ID of this sharing entry.
	 *
	 * @return the to user ID of this sharing entry
	 */
	@Override
	public long getToUserId() {
		return model.getToUserId();
	}

	/**
	 * Returns the to user uuid of this sharing entry.
	 *
	 * @return the to user uuid of this sharing entry
	 */
	@Override
	public String getToUserUuid() {
		return model.getToUserUuid();
	}

	/**
	 * Returns the user ID of this sharing entry.
	 *
	 * @return the user ID of this sharing entry
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this sharing entry.
	 *
	 * @return the user name of this sharing entry
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this sharing entry.
	 *
	 * @return the user uuid of this sharing entry
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this sharing entry.
	 *
	 * @return the uuid of this sharing entry
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns {@code true} if the sharing entry has the sharing entry action.
	 *
	 * @param sharingEntryAction the sharing entry action
	 * @return {@code true} if the sharing entry has the sharing entry action;
	 {@code false} otherwise
	 * @review
	 */
	@Override
	public boolean hasSharingPermission(
		com.liferay.sharing.security.permission.SharingEntryAction
			sharingEntryAction) {

		return model.hasSharingPermission(sharingEntryAction);
	}

	/**
	 * Returns <code>true</code> if this sharing entry is shareable.
	 *
	 * @return <code>true</code> if this sharing entry is shareable; <code>false</code> otherwise
	 */
	@Override
	public boolean isShareable() {
		return model.isShareable();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a sharing entry model instance should use the <code>SharingEntry</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the action IDs of this sharing entry.
	 *
	 * @param actionIds the action IDs of this sharing entry
	 */
	@Override
	public void setActionIds(long actionIds) {
		model.setActionIds(actionIds);
	}

	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	 * Sets the class name ID of this sharing entry.
	 *
	 * @param classNameId the class name ID of this sharing entry
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this sharing entry.
	 *
	 * @param classPK the class pk of this sharing entry
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the company ID of this sharing entry.
	 *
	 * @param companyId the company ID of this sharing entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this sharing entry.
	 *
	 * @param createDate the create date of this sharing entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the expiration date of this sharing entry.
	 *
	 * @param expirationDate the expiration date of this sharing entry
	 */
	@Override
	public void setExpirationDate(Date expirationDate) {
		model.setExpirationDate(expirationDate);
	}

	/**
	 * Sets the group ID of this sharing entry.
	 *
	 * @param groupId the group ID of this sharing entry
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this sharing entry.
	 *
	 * @param modifiedDate the modified date of this sharing entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the primary key of this sharing entry.
	 *
	 * @param primaryKey the primary key of this sharing entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets whether this sharing entry is shareable.
	 *
	 * @param shareable the shareable of this sharing entry
	 */
	@Override
	public void setShareable(boolean shareable) {
		model.setShareable(shareable);
	}

	/**
	 * Sets the sharing entry ID of this sharing entry.
	 *
	 * @param sharingEntryId the sharing entry ID of this sharing entry
	 */
	@Override
	public void setSharingEntryId(long sharingEntryId) {
		model.setSharingEntryId(sharingEntryId);
	}

	/**
	 * Sets the to user ID of this sharing entry.
	 *
	 * @param toUserId the to user ID of this sharing entry
	 */
	@Override
	public void setToUserId(long toUserId) {
		model.setToUserId(toUserId);
	}

	/**
	 * Sets the to user uuid of this sharing entry.
	 *
	 * @param toUserUuid the to user uuid of this sharing entry
	 */
	@Override
	public void setToUserUuid(String toUserUuid) {
		model.setToUserUuid(toUserUuid);
	}

	/**
	 * Sets the user ID of this sharing entry.
	 *
	 * @param userId the user ID of this sharing entry
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this sharing entry.
	 *
	 * @param userName the user name of this sharing entry
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this sharing entry.
	 *
	 * @param userUuid the user uuid of this sharing entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this sharing entry.
	 *
	 * @param uuid the uuid of this sharing entry
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
	protected SharingEntryWrapper wrap(SharingEntry sharingEntry) {
		return new SharingEntryWrapper(sharingEntry);
	}

}