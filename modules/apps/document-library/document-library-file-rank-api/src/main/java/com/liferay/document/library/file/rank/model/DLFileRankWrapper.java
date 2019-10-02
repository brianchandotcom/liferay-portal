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

package com.liferay.document.library.file.rank.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link DLFileRank}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLFileRank
 * @generated
 */
public class DLFileRankWrapper
	extends BaseModelWrapper<DLFileRank>
	implements DLFileRank, ModelWrapper<DLFileRank> {

	public DLFileRankWrapper(DLFileRank dlFileRank) {
		super(dlFileRank);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("fileRankId", getFileRankId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("createDate", getCreateDate());
		attributes.put("fileEntryId", getFileEntryId());
		attributes.put("active", isActive());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long fileRankId = (Long)attributes.get("fileRankId");

		if (fileRankId != null) {
			setFileRankId(fileRankId);
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

		Long fileEntryId = (Long)attributes.get("fileEntryId");

		if (fileEntryId != null) {
			setFileEntryId(fileEntryId);
		}

		Boolean active = (Boolean)attributes.get("active");

		if (active != null) {
			setActive(active);
		}
	}

	/**
	 * Returns the active of this document library file rank.
	 *
	 * @return the active of this document library file rank
	 */
	@Override
	public boolean getActive() {
		return model.getActive();
	}

	/**
	 * Returns the company ID of this document library file rank.
	 *
	 * @return the company ID of this document library file rank
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this document library file rank.
	 *
	 * @return the create date of this document library file rank
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the file entry ID of this document library file rank.
	 *
	 * @return the file entry ID of this document library file rank
	 */
	@Override
	public long getFileEntryId() {
		return model.getFileEntryId();
	}

	/**
	 * Returns the file rank ID of this document library file rank.
	 *
	 * @return the file rank ID of this document library file rank
	 */
	@Override
	public long getFileRankId() {
		return model.getFileRankId();
	}

	/**
	 * Returns the group ID of this document library file rank.
	 *
	 * @return the group ID of this document library file rank
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the mvcc version of this document library file rank.
	 *
	 * @return the mvcc version of this document library file rank
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this document library file rank.
	 *
	 * @return the primary key of this document library file rank
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this document library file rank.
	 *
	 * @return the user ID of this document library file rank
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user uuid of this document library file rank.
	 *
	 * @return the user uuid of this document library file rank
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns <code>true</code> if this document library file rank is active.
	 *
	 * @return <code>true</code> if this document library file rank is active; <code>false</code> otherwise
	 */
	@Override
	public boolean isActive() {
		return model.isActive();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a document library file rank model instance should use the <code>DLFileRank</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets whether this document library file rank is active.
	 *
	 * @param active the active of this document library file rank
	 */
	@Override
	public void setActive(boolean active) {
		model.setActive(active);
	}

	/**
	 * Sets the company ID of this document library file rank.
	 *
	 * @param companyId the company ID of this document library file rank
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this document library file rank.
	 *
	 * @param createDate the create date of this document library file rank
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the file entry ID of this document library file rank.
	 *
	 * @param fileEntryId the file entry ID of this document library file rank
	 */
	@Override
	public void setFileEntryId(long fileEntryId) {
		model.setFileEntryId(fileEntryId);
	}

	/**
	 * Sets the file rank ID of this document library file rank.
	 *
	 * @param fileRankId the file rank ID of this document library file rank
	 */
	@Override
	public void setFileRankId(long fileRankId) {
		model.setFileRankId(fileRankId);
	}

	/**
	 * Sets the group ID of this document library file rank.
	 *
	 * @param groupId the group ID of this document library file rank
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the mvcc version of this document library file rank.
	 *
	 * @param mvccVersion the mvcc version of this document library file rank
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this document library file rank.
	 *
	 * @param primaryKey the primary key of this document library file rank
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this document library file rank.
	 *
	 * @param userId the user ID of this document library file rank
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user uuid of this document library file rank.
	 *
	 * @param userUuid the user uuid of this document library file rank
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected DLFileRankWrapper wrap(DLFileRank dlFileRank) {
		return new DLFileRankWrapper(dlFileRank);
	}

}