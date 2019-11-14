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
 * This class is a wrapper for {@link LayoutSEOSite}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSEOSite
 * @generated
 */
public class LayoutSEOSiteWrapper
	extends BaseModelWrapper<LayoutSEOSite>
	implements LayoutSEOSite, ModelWrapper<LayoutSEOSite> {

	public LayoutSEOSiteWrapper(LayoutSEOSite layoutSEOSite) {
		super(layoutSEOSite);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("layoutSEOSiteId", getLayoutSEOSiteId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("openGraphEnabled", isOpenGraphEnabled());
		attributes.put(
			"openGraphImageFileEntryId", getOpenGraphImageFileEntryId());

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

		Long layoutSEOSiteId = (Long)attributes.get("layoutSEOSiteId");

		if (layoutSEOSiteId != null) {
			setLayoutSEOSiteId(layoutSEOSiteId);
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

		Boolean openGraphEnabled = (Boolean)attributes.get("openGraphEnabled");

		if (openGraphEnabled != null) {
			setOpenGraphEnabled(openGraphEnabled);
		}

		Long openGraphImageFileEntryId = (Long)attributes.get(
			"openGraphImageFileEntryId");

		if (openGraphImageFileEntryId != null) {
			setOpenGraphImageFileEntryId(openGraphImageFileEntryId);
		}
	}

	/**
	 * Returns the company ID of this layout seo site.
	 *
	 * @return the company ID of this layout seo site
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this layout seo site.
	 *
	 * @return the create date of this layout seo site
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the group ID of this layout seo site.
	 *
	 * @return the group ID of this layout seo site
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the layout seo site ID of this layout seo site.
	 *
	 * @return the layout seo site ID of this layout seo site
	 */
	@Override
	public long getLayoutSEOSiteId() {
		return model.getLayoutSEOSiteId();
	}

	/**
	 * Returns the modified date of this layout seo site.
	 *
	 * @return the modified date of this layout seo site
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this layout seo site.
	 *
	 * @return the mvcc version of this layout seo site
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the open graph enabled of this layout seo site.
	 *
	 * @return the open graph enabled of this layout seo site
	 */
	@Override
	public boolean getOpenGraphEnabled() {
		return model.getOpenGraphEnabled();
	}

	/**
	 * Returns the open graph image file entry ID of this layout seo site.
	 *
	 * @return the open graph image file entry ID of this layout seo site
	 */
	@Override
	public long getOpenGraphImageFileEntryId() {
		return model.getOpenGraphImageFileEntryId();
	}

	/**
	 * Returns the primary key of this layout seo site.
	 *
	 * @return the primary key of this layout seo site
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this layout seo site.
	 *
	 * @return the user ID of this layout seo site
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this layout seo site.
	 *
	 * @return the user name of this layout seo site
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this layout seo site.
	 *
	 * @return the user uuid of this layout seo site
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this layout seo site.
	 *
	 * @return the uuid of this layout seo site
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns <code>true</code> if this layout seo site is open graph enabled.
	 *
	 * @return <code>true</code> if this layout seo site is open graph enabled; <code>false</code> otherwise
	 */
	@Override
	public boolean isOpenGraphEnabled() {
		return model.isOpenGraphEnabled();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a layout seo site model instance should use the <code>LayoutSEOSite</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this layout seo site.
	 *
	 * @param companyId the company ID of this layout seo site
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this layout seo site.
	 *
	 * @param createDate the create date of this layout seo site
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the group ID of this layout seo site.
	 *
	 * @param groupId the group ID of this layout seo site
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the layout seo site ID of this layout seo site.
	 *
	 * @param layoutSEOSiteId the layout seo site ID of this layout seo site
	 */
	@Override
	public void setLayoutSEOSiteId(long layoutSEOSiteId) {
		model.setLayoutSEOSiteId(layoutSEOSiteId);
	}

	/**
	 * Sets the modified date of this layout seo site.
	 *
	 * @param modifiedDate the modified date of this layout seo site
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this layout seo site.
	 *
	 * @param mvccVersion the mvcc version of this layout seo site
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets whether this layout seo site is open graph enabled.
	 *
	 * @param openGraphEnabled the open graph enabled of this layout seo site
	 */
	@Override
	public void setOpenGraphEnabled(boolean openGraphEnabled) {
		model.setOpenGraphEnabled(openGraphEnabled);
	}

	/**
	 * Sets the open graph image file entry ID of this layout seo site.
	 *
	 * @param openGraphImageFileEntryId the open graph image file entry ID of this layout seo site
	 */
	@Override
	public void setOpenGraphImageFileEntryId(long openGraphImageFileEntryId) {
		model.setOpenGraphImageFileEntryId(openGraphImageFileEntryId);
	}

	/**
	 * Sets the primary key of this layout seo site.
	 *
	 * @param primaryKey the primary key of this layout seo site
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this layout seo site.
	 *
	 * @param userId the user ID of this layout seo site
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this layout seo site.
	 *
	 * @param userName the user name of this layout seo site
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this layout seo site.
	 *
	 * @param userUuid the user uuid of this layout seo site
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this layout seo site.
	 *
	 * @param uuid the uuid of this layout seo site
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
	protected LayoutSEOSiteWrapper wrap(LayoutSEOSite layoutSEOSite) {
		return new LayoutSEOSiteWrapper(layoutSEOSite);
	}

}