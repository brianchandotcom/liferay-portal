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

package com.liferay.layout.seo.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link SiteSEOEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SiteSEOEntry
 * @generated
 */
public class SiteSEOEntryWrapper
	extends BaseModelWrapper<SiteSEOEntry>
	implements ModelWrapper<SiteSEOEntry>, SiteSEOEntry {

	public SiteSEOEntryWrapper(SiteSEOEntry siteSEOEntry) {
		super(siteSEOEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("siteSEOEntryId", getSiteSEOEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put(
			"openGraphImageFileEntryId", getOpenGraphImageFileEntryId());
		attributes.put("openGraphSiteEnabled", isOpenGraphSiteEnabled());

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

		Long siteSEOEntryId = (Long)attributes.get("siteSEOEntryId");

		if (siteSEOEntryId != null) {
			setSiteSEOEntryId(siteSEOEntryId);
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

		Long openGraphImageFileEntryId = (Long)attributes.get(
			"openGraphImageFileEntryId");

		if (openGraphImageFileEntryId != null) {
			setOpenGraphImageFileEntryId(openGraphImageFileEntryId);
		}

		Boolean openGraphSiteEnabled = (Boolean)attributes.get(
			"openGraphSiteEnabled");

		if (openGraphSiteEnabled != null) {
			setOpenGraphSiteEnabled(openGraphSiteEnabled);
		}
	}

	/**
	 * Returns the company ID of this site seo entry.
	 *
	 * @return the company ID of this site seo entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this site seo entry.
	 *
	 * @return the create date of this site seo entry
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the group ID of this site seo entry.
	 *
	 * @return the group ID of this site seo entry
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this site seo entry.
	 *
	 * @return the modified date of this site seo entry
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this site seo entry.
	 *
	 * @return the mvcc version of this site seo entry
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the open graph image file entry ID of this site seo entry.
	 *
	 * @return the open graph image file entry ID of this site seo entry
	 */
	@Override
	public long getOpenGraphImageFileEntryId() {
		return model.getOpenGraphImageFileEntryId();
	}

	/**
	 * Returns the open graph site enabled of this site seo entry.
	 *
	 * @return the open graph site enabled of this site seo entry
	 */
	@Override
	public boolean getOpenGraphSiteEnabled() {
		return model.getOpenGraphSiteEnabled();
	}

	/**
	 * Returns the primary key of this site seo entry.
	 *
	 * @return the primary key of this site seo entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the site seo entry ID of this site seo entry.
	 *
	 * @return the site seo entry ID of this site seo entry
	 */
	@Override
	public long getSiteSEOEntryId() {
		return model.getSiteSEOEntryId();
	}

	/**
	 * Returns the user ID of this site seo entry.
	 *
	 * @return the user ID of this site seo entry
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this site seo entry.
	 *
	 * @return the user name of this site seo entry
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this site seo entry.
	 *
	 * @return the user uuid of this site seo entry
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this site seo entry.
	 *
	 * @return the uuid of this site seo entry
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns <code>true</code> if this site seo entry is open graph site enabled.
	 *
	 * @return <code>true</code> if this site seo entry is open graph site enabled; <code>false</code> otherwise
	 */
	@Override
	public boolean isOpenGraphSiteEnabled() {
		return model.isOpenGraphSiteEnabled();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a site seo entry model instance should use the <code>SiteSEOEntry</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this site seo entry.
	 *
	 * @param companyId the company ID of this site seo entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this site seo entry.
	 *
	 * @param createDate the create date of this site seo entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the group ID of this site seo entry.
	 *
	 * @param groupId the group ID of this site seo entry
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this site seo entry.
	 *
	 * @param modifiedDate the modified date of this site seo entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this site seo entry.
	 *
	 * @param mvccVersion the mvcc version of this site seo entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the open graph image file entry ID of this site seo entry.
	 *
	 * @param openGraphImageFileEntryId the open graph image file entry ID of this site seo entry
	 */
	@Override
	public void setOpenGraphImageFileEntryId(long openGraphImageFileEntryId) {
		model.setOpenGraphImageFileEntryId(openGraphImageFileEntryId);
	}

	/**
	 * Sets whether this site seo entry is open graph site enabled.
	 *
	 * @param openGraphSiteEnabled the open graph site enabled of this site seo entry
	 */
	@Override
	public void setOpenGraphSiteEnabled(boolean openGraphSiteEnabled) {
		model.setOpenGraphSiteEnabled(openGraphSiteEnabled);
	}

	/**
	 * Sets the primary key of this site seo entry.
	 *
	 * @param primaryKey the primary key of this site seo entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the site seo entry ID of this site seo entry.
	 *
	 * @param siteSEOEntryId the site seo entry ID of this site seo entry
	 */
	@Override
	public void setSiteSEOEntryId(long siteSEOEntryId) {
		model.setSiteSEOEntryId(siteSEOEntryId);
	}

	/**
	 * Sets the user ID of this site seo entry.
	 *
	 * @param userId the user ID of this site seo entry
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this site seo entry.
	 *
	 * @param userName the user name of this site seo entry
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this site seo entry.
	 *
	 * @param userUuid the user uuid of this site seo entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this site seo entry.
	 *
	 * @param uuid the uuid of this site seo entry
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
	protected SiteSEOEntryWrapper wrap(SiteSEOEntry siteSEOEntry) {
		return new SiteSEOEntryWrapper(siteSEOEntry);
	}

}