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

package com.liferay.layout.page.template.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link LayoutPageTemplateEntryVersion}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateEntryVersion
 * @generated
 */
@ProviderType
public class LayoutPageTemplateEntryVersionWrapper
	extends BaseModelWrapper<LayoutPageTemplateEntryVersion>
	implements LayoutPageTemplateEntryVersion,
			   ModelWrapper<LayoutPageTemplateEntryVersion> {

	public LayoutPageTemplateEntryVersionWrapper(
		LayoutPageTemplateEntryVersion layoutPageTemplateEntryVersion) {

		super(layoutPageTemplateEntryVersion);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put(
			"layoutPageTemplateEntryVersionId",
			getLayoutPageTemplateEntryVersionId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put(
			"layoutPageTemplateEntryId", getLayoutPageTemplateEntryId());
		attributes.put("plid", getPlid());
		attributes.put("version", getVersion());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long layoutPageTemplateEntryVersionId = (Long)attributes.get(
			"layoutPageTemplateEntryVersionId");

		if (layoutPageTemplateEntryVersionId != null) {
			setLayoutPageTemplateEntryVersionId(
				layoutPageTemplateEntryVersionId);
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

		Long layoutPageTemplateEntryId = (Long)attributes.get(
			"layoutPageTemplateEntryId");

		if (layoutPageTemplateEntryId != null) {
			setLayoutPageTemplateEntryId(layoutPageTemplateEntryId);
		}

		Long plid = (Long)attributes.get("plid");

		if (plid != null) {
			setPlid(plid);
		}

		Double version = (Double)attributes.get("version");

		if (version != null) {
			setVersion(version);
		}
	}

	/**
	 * Returns the company ID of this layout page template entry version.
	 *
	 * @return the company ID of this layout page template entry version
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this layout page template entry version.
	 *
	 * @return the create date of this layout page template entry version
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the group ID of this layout page template entry version.
	 *
	 * @return the group ID of this layout page template entry version
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the layout page template entry ID of this layout page template entry version.
	 *
	 * @return the layout page template entry ID of this layout page template entry version
	 */
	@Override
	public long getLayoutPageTemplateEntryId() {
		return model.getLayoutPageTemplateEntryId();
	}

	/**
	 * Returns the layout page template entry version ID of this layout page template entry version.
	 *
	 * @return the layout page template entry version ID of this layout page template entry version
	 */
	@Override
	public long getLayoutPageTemplateEntryVersionId() {
		return model.getLayoutPageTemplateEntryVersionId();
	}

	/**
	 * Returns the modified date of this layout page template entry version.
	 *
	 * @return the modified date of this layout page template entry version
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the plid of this layout page template entry version.
	 *
	 * @return the plid of this layout page template entry version
	 */
	@Override
	public long getPlid() {
		return model.getPlid();
	}

	/**
	 * Returns the primary key of this layout page template entry version.
	 *
	 * @return the primary key of this layout page template entry version
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this layout page template entry version.
	 *
	 * @return the user ID of this layout page template entry version
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this layout page template entry version.
	 *
	 * @return the user name of this layout page template entry version
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this layout page template entry version.
	 *
	 * @return the user uuid of this layout page template entry version
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the version of this layout page template entry version.
	 *
	 * @return the version of this layout page template entry version
	 */
	@Override
	public double getVersion() {
		return model.getVersion();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this layout page template entry version.
	 *
	 * @param companyId the company ID of this layout page template entry version
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this layout page template entry version.
	 *
	 * @param createDate the create date of this layout page template entry version
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the group ID of this layout page template entry version.
	 *
	 * @param groupId the group ID of this layout page template entry version
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the layout page template entry ID of this layout page template entry version.
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID of this layout page template entry version
	 */
	@Override
	public void setLayoutPageTemplateEntryId(long layoutPageTemplateEntryId) {
		model.setLayoutPageTemplateEntryId(layoutPageTemplateEntryId);
	}

	/**
	 * Sets the layout page template entry version ID of this layout page template entry version.
	 *
	 * @param layoutPageTemplateEntryVersionId the layout page template entry version ID of this layout page template entry version
	 */
	@Override
	public void setLayoutPageTemplateEntryVersionId(
		long layoutPageTemplateEntryVersionId) {

		model.setLayoutPageTemplateEntryVersionId(
			layoutPageTemplateEntryVersionId);
	}

	/**
	 * Sets the modified date of this layout page template entry version.
	 *
	 * @param modifiedDate the modified date of this layout page template entry version
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the plid of this layout page template entry version.
	 *
	 * @param plid the plid of this layout page template entry version
	 */
	@Override
	public void setPlid(long plid) {
		model.setPlid(plid);
	}

	/**
	 * Sets the primary key of this layout page template entry version.
	 *
	 * @param primaryKey the primary key of this layout page template entry version
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this layout page template entry version.
	 *
	 * @param userId the user ID of this layout page template entry version
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this layout page template entry version.
	 *
	 * @param userName the user name of this layout page template entry version
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this layout page template entry version.
	 *
	 * @param userUuid the user uuid of this layout page template entry version
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the version of this layout page template entry version.
	 *
	 * @param version the version of this layout page template entry version
	 */
	@Override
	public void setVersion(double version) {
		model.setVersion(version);
	}

	@Override
	protected LayoutPageTemplateEntryVersionWrapper wrap(
		LayoutPageTemplateEntryVersion layoutPageTemplateEntryVersion) {

		return new LayoutPageTemplateEntryVersionWrapper(
			layoutPageTemplateEntryVersion);
	}

}