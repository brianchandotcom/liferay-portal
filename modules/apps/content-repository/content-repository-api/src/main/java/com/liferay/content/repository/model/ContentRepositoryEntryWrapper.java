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

package com.liferay.content.repository.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link ContentRepositoryEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ContentRepositoryEntry
 * @generated
 */
public class ContentRepositoryEntryWrapper
	extends BaseModelWrapper<ContentRepositoryEntry>
	implements ContentRepositoryEntry, ModelWrapper<ContentRepositoryEntry> {

	public ContentRepositoryEntryWrapper(
		ContentRepositoryEntry contentRepositoryEntry) {

		super(contentRepositoryEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put(
			"contentRepositoryEntryId", getContentRepositoryEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
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

		Long contentRepositoryEntryId = (Long)attributes.get(
			"contentRepositoryEntryId");

		if (contentRepositoryEntryId != null) {
			setContentRepositoryEntryId(contentRepositoryEntryId);
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
	 * Returns the company ID of this content repository entry.
	 *
	 * @return the company ID of this content repository entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the content repository entry ID of this content repository entry.
	 *
	 * @return the content repository entry ID of this content repository entry
	 */
	@Override
	public long getContentRepositoryEntryId() {
		return model.getContentRepositoryEntryId();
	}

	/**
	 * Returns the create date of this content repository entry.
	 *
	 * @return the create date of this content repository entry
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the group ID of this content repository entry.
	 *
	 * @return the group ID of this content repository entry
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this content repository entry.
	 *
	 * @return the modified date of this content repository entry
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this content repository entry.
	 *
	 * @return the mvcc version of this content repository entry
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this content repository entry.
	 *
	 * @return the primary key of this content repository entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this content repository entry.
	 *
	 * @return the user ID of this content repository entry
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user uuid of this content repository entry.
	 *
	 * @return the user uuid of this content repository entry
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this content repository entry.
	 *
	 * @return the uuid of this content repository entry
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a content repository entry model instance should use the <code>ContentRepositoryEntry</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this content repository entry.
	 *
	 * @param companyId the company ID of this content repository entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the content repository entry ID of this content repository entry.
	 *
	 * @param contentRepositoryEntryId the content repository entry ID of this content repository entry
	 */
	@Override
	public void setContentRepositoryEntryId(long contentRepositoryEntryId) {
		model.setContentRepositoryEntryId(contentRepositoryEntryId);
	}

	/**
	 * Sets the create date of this content repository entry.
	 *
	 * @param createDate the create date of this content repository entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the group ID of this content repository entry.
	 *
	 * @param groupId the group ID of this content repository entry
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this content repository entry.
	 *
	 * @param modifiedDate the modified date of this content repository entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this content repository entry.
	 *
	 * @param mvccVersion the mvcc version of this content repository entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this content repository entry.
	 *
	 * @param primaryKey the primary key of this content repository entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this content repository entry.
	 *
	 * @param userId the user ID of this content repository entry
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user uuid of this content repository entry.
	 *
	 * @param userUuid the user uuid of this content repository entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this content repository entry.
	 *
	 * @param uuid the uuid of this content repository entry
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
	protected ContentRepositoryEntryWrapper wrap(
		ContentRepositoryEntry contentRepositoryEntry) {

		return new ContentRepositoryEntryWrapper(contentRepositoryEntry);
	}

}