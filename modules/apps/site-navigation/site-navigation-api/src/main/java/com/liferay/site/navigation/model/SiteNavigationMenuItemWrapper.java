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

package com.liferay.site.navigation.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link SiteNavigationMenuItem}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SiteNavigationMenuItem
 * @generated
 */
public class SiteNavigationMenuItemWrapper
	extends BaseModelWrapper<SiteNavigationMenuItem>
	implements ModelWrapper<SiteNavigationMenuItem>, SiteNavigationMenuItem {

	public SiteNavigationMenuItemWrapper(
		SiteNavigationMenuItem siteNavigationMenuItem) {

		super(siteNavigationMenuItem);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put(
			"siteNavigationMenuItemId", getSiteNavigationMenuItemId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("siteNavigationMenuId", getSiteNavigationMenuId());
		attributes.put(
			"parentSiteNavigationMenuItemId",
			getParentSiteNavigationMenuItemId());
		attributes.put("name", getName());
		attributes.put("type", getType());
		attributes.put("typeSettings", getTypeSettings());
		attributes.put("order", getOrder());
		attributes.put("lastPublishDate", getLastPublishDate());

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

		Long siteNavigationMenuItemId = (Long)attributes.get(
			"siteNavigationMenuItemId");

		if (siteNavigationMenuItemId != null) {
			setSiteNavigationMenuItemId(siteNavigationMenuItemId);
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

		Long siteNavigationMenuId = (Long)attributes.get(
			"siteNavigationMenuId");

		if (siteNavigationMenuId != null) {
			setSiteNavigationMenuId(siteNavigationMenuId);
		}

		Long parentSiteNavigationMenuItemId = (Long)attributes.get(
			"parentSiteNavigationMenuItemId");

		if (parentSiteNavigationMenuItemId != null) {
			setParentSiteNavigationMenuItemId(parentSiteNavigationMenuItemId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		String typeSettings = (String)attributes.get("typeSettings");

		if (typeSettings != null) {
			setTypeSettings(typeSettings);
		}

		Integer order = (Integer)attributes.get("order");

		if (order != null) {
			setOrder(order);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	/**
	 * Returns the company ID of this site navigation menu item.
	 *
	 * @return the company ID of this site navigation menu item
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this site navigation menu item.
	 *
	 * @return the create date of this site navigation menu item
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the group ID of this site navigation menu item.
	 *
	 * @return the group ID of this site navigation menu item
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the last publish date of this site navigation menu item.
	 *
	 * @return the last publish date of this site navigation menu item
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	 * Returns the modified date of this site navigation menu item.
	 *
	 * @return the modified date of this site navigation menu item
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this site navigation menu item.
	 *
	 * @return the mvcc version of this site navigation menu item
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this site navigation menu item.
	 *
	 * @return the name of this site navigation menu item
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the order of this site navigation menu item.
	 *
	 * @return the order of this site navigation menu item
	 */
	@Override
	public int getOrder() {
		return model.getOrder();
	}

	/**
	 * Returns the parent site navigation menu item ID of this site navigation menu item.
	 *
	 * @return the parent site navigation menu item ID of this site navigation menu item
	 */
	@Override
	public long getParentSiteNavigationMenuItemId() {
		return model.getParentSiteNavigationMenuItemId();
	}

	/**
	 * Returns the primary key of this site navigation menu item.
	 *
	 * @return the primary key of this site navigation menu item
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the site navigation menu ID of this site navigation menu item.
	 *
	 * @return the site navigation menu ID of this site navigation menu item
	 */
	@Override
	public long getSiteNavigationMenuId() {
		return model.getSiteNavigationMenuId();
	}

	/**
	 * Returns the site navigation menu item ID of this site navigation menu item.
	 *
	 * @return the site navigation menu item ID of this site navigation menu item
	 */
	@Override
	public long getSiteNavigationMenuItemId() {
		return model.getSiteNavigationMenuItemId();
	}

	/**
	 * Returns the type of this site navigation menu item.
	 *
	 * @return the type of this site navigation menu item
	 */
	@Override
	public String getType() {
		return model.getType();
	}

	/**
	 * Returns the type settings of this site navigation menu item.
	 *
	 * @return the type settings of this site navigation menu item
	 */
	@Override
	public String getTypeSettings() {
		return model.getTypeSettings();
	}

	/**
	 * Returns the user ID of this site navigation menu item.
	 *
	 * @return the user ID of this site navigation menu item
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this site navigation menu item.
	 *
	 * @return the user name of this site navigation menu item
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this site navigation menu item.
	 *
	 * @return the user uuid of this site navigation menu item
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this site navigation menu item.
	 *
	 * @return the uuid of this site navigation menu item
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a site navigation menu item model instance should use the <code>SiteNavigationMenuItem</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this site navigation menu item.
	 *
	 * @param companyId the company ID of this site navigation menu item
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this site navigation menu item.
	 *
	 * @param createDate the create date of this site navigation menu item
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the group ID of this site navigation menu item.
	 *
	 * @param groupId the group ID of this site navigation menu item
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the last publish date of this site navigation menu item.
	 *
	 * @param lastPublishDate the last publish date of this site navigation menu item
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the modified date of this site navigation menu item.
	 *
	 * @param modifiedDate the modified date of this site navigation menu item
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this site navigation menu item.
	 *
	 * @param mvccVersion the mvcc version of this site navigation menu item
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this site navigation menu item.
	 *
	 * @param name the name of this site navigation menu item
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the order of this site navigation menu item.
	 *
	 * @param order the order of this site navigation menu item
	 */
	@Override
	public void setOrder(int order) {
		model.setOrder(order);
	}

	/**
	 * Sets the parent site navigation menu item ID of this site navigation menu item.
	 *
	 * @param parentSiteNavigationMenuItemId the parent site navigation menu item ID of this site navigation menu item
	 */
	@Override
	public void setParentSiteNavigationMenuItemId(
		long parentSiteNavigationMenuItemId) {

		model.setParentSiteNavigationMenuItemId(parentSiteNavigationMenuItemId);
	}

	/**
	 * Sets the primary key of this site navigation menu item.
	 *
	 * @param primaryKey the primary key of this site navigation menu item
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the site navigation menu ID of this site navigation menu item.
	 *
	 * @param siteNavigationMenuId the site navigation menu ID of this site navigation menu item
	 */
	@Override
	public void setSiteNavigationMenuId(long siteNavigationMenuId) {
		model.setSiteNavigationMenuId(siteNavigationMenuId);
	}

	/**
	 * Sets the site navigation menu item ID of this site navigation menu item.
	 *
	 * @param siteNavigationMenuItemId the site navigation menu item ID of this site navigation menu item
	 */
	@Override
	public void setSiteNavigationMenuItemId(long siteNavigationMenuItemId) {
		model.setSiteNavigationMenuItemId(siteNavigationMenuItemId);
	}

	/**
	 * Sets the type of this site navigation menu item.
	 *
	 * @param type the type of this site navigation menu item
	 */
	@Override
	public void setType(String type) {
		model.setType(type);
	}

	/**
	 * Sets the type settings of this site navigation menu item.
	 *
	 * @param typeSettings the type settings of this site navigation menu item
	 */
	@Override
	public void setTypeSettings(String typeSettings) {
		model.setTypeSettings(typeSettings);
	}

	/**
	 * Sets the user ID of this site navigation menu item.
	 *
	 * @param userId the user ID of this site navigation menu item
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this site navigation menu item.
	 *
	 * @param userName the user name of this site navigation menu item
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this site navigation menu item.
	 *
	 * @param userUuid the user uuid of this site navigation menu item
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this site navigation menu item.
	 *
	 * @param uuid the uuid of this site navigation menu item
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
	protected SiteNavigationMenuItemWrapper wrap(
		SiteNavigationMenuItem siteNavigationMenuItem) {

		return new SiteNavigationMenuItemWrapper(siteNavigationMenuItem);
	}

}