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

package com.liferay.segments.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link SegmentsEntryRole}.
 * </p>
 *
 * @author Eduardo Garcia
 * @see SegmentsEntryRole
 * @generated
 */
public class SegmentsEntryRoleWrapper
	extends BaseModelWrapper<SegmentsEntryRole>
	implements ModelWrapper<SegmentsEntryRole>, SegmentsEntryRole {

	public SegmentsEntryRoleWrapper(SegmentsEntryRole segmentsEntryRole) {
		super(segmentsEntryRole);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("segmentsEntryRoleId", getSegmentsEntryRoleId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("roleId", getRoleId());
		attributes.put("segmentsEntryId", getSegmentsEntryId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long segmentsEntryRoleId = (Long)attributes.get("segmentsEntryRoleId");

		if (segmentsEntryRoleId != null) {
			setSegmentsEntryRoleId(segmentsEntryRoleId);
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

		Long roleId = (Long)attributes.get("roleId");

		if (roleId != null) {
			setRoleId(roleId);
		}

		Long segmentsEntryId = (Long)attributes.get("segmentsEntryId");

		if (segmentsEntryId != null) {
			setSegmentsEntryId(segmentsEntryId);
		}
	}

	/**
	 * Returns the company ID of this segments entry role.
	 *
	 * @return the company ID of this segments entry role
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this segments entry role.
	 *
	 * @return the create date of this segments entry role
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the modified date of this segments entry role.
	 *
	 * @return the modified date of this segments entry role
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this segments entry role.
	 *
	 * @return the mvcc version of this segments entry role
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this segments entry role.
	 *
	 * @return the primary key of this segments entry role
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the role ID of this segments entry role.
	 *
	 * @return the role ID of this segments entry role
	 */
	@Override
	public long getRoleId() {
		return model.getRoleId();
	}

	/**
	 * Returns the segments entry ID of this segments entry role.
	 *
	 * @return the segments entry ID of this segments entry role
	 */
	@Override
	public long getSegmentsEntryId() {
		return model.getSegmentsEntryId();
	}

	/**
	 * Returns the segments entry role ID of this segments entry role.
	 *
	 * @return the segments entry role ID of this segments entry role
	 */
	@Override
	public long getSegmentsEntryRoleId() {
		return model.getSegmentsEntryRoleId();
	}

	/**
	 * Returns the user ID of this segments entry role.
	 *
	 * @return the user ID of this segments entry role
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this segments entry role.
	 *
	 * @return the user name of this segments entry role
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this segments entry role.
	 *
	 * @return the user uuid of this segments entry role
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a segments entry role model instance should use the <code>SegmentsEntryRole</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this segments entry role.
	 *
	 * @param companyId the company ID of this segments entry role
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this segments entry role.
	 *
	 * @param createDate the create date of this segments entry role
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the modified date of this segments entry role.
	 *
	 * @param modifiedDate the modified date of this segments entry role
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this segments entry role.
	 *
	 * @param mvccVersion the mvcc version of this segments entry role
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this segments entry role.
	 *
	 * @param primaryKey the primary key of this segments entry role
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the role ID of this segments entry role.
	 *
	 * @param roleId the role ID of this segments entry role
	 */
	@Override
	public void setRoleId(long roleId) {
		model.setRoleId(roleId);
	}

	/**
	 * Sets the segments entry ID of this segments entry role.
	 *
	 * @param segmentsEntryId the segments entry ID of this segments entry role
	 */
	@Override
	public void setSegmentsEntryId(long segmentsEntryId) {
		model.setSegmentsEntryId(segmentsEntryId);
	}

	/**
	 * Sets the segments entry role ID of this segments entry role.
	 *
	 * @param segmentsEntryRoleId the segments entry role ID of this segments entry role
	 */
	@Override
	public void setSegmentsEntryRoleId(long segmentsEntryRoleId) {
		model.setSegmentsEntryRoleId(segmentsEntryRoleId);
	}

	/**
	 * Sets the user ID of this segments entry role.
	 *
	 * @param userId the user ID of this segments entry role
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this segments entry role.
	 *
	 * @param userName the user name of this segments entry role
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this segments entry role.
	 *
	 * @param userUuid the user uuid of this segments entry role
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected SegmentsEntryRoleWrapper wrap(
		SegmentsEntryRole segmentsEntryRole) {

		return new SegmentsEntryRoleWrapper(segmentsEntryRole);
	}

}