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

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link SiteNavigationMenuItem}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SiteNavigationMenuItem
 * @generated
 */
@ProviderType
public class SiteNavigationMenuItemWrapper implements SiteNavigationMenuItem,
	ModelWrapper<SiteNavigationMenuItem> {
	public SiteNavigationMenuItemWrapper(
		SiteNavigationMenuItem siteNavigationMenuItem) {
		_siteNavigationMenuItem = siteNavigationMenuItem;
	}

	@Override
	public Class<?> getModelClass() {
		return SiteNavigationMenuItem.class;
	}

	@Override
	public String getModelClassName() {
		return SiteNavigationMenuItem.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("siteNavigationMenuItemId", getSiteNavigationMenuItemId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("siteNavigationMenuId", getSiteNavigationMenuId());
		attributes.put("parentSiteNavigationMenuItemId",
			getParentSiteNavigationMenuItemId());
		attributes.put("type", getType());
		attributes.put("typeSettings", getTypeSettings());
		attributes.put("order", getOrder());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
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

		Long siteNavigationMenuId = (Long)attributes.get("siteNavigationMenuId");

		if (siteNavigationMenuId != null) {
			setSiteNavigationMenuId(siteNavigationMenuId);
		}

		Long parentSiteNavigationMenuItemId = (Long)attributes.get(
				"parentSiteNavigationMenuItemId");

		if (parentSiteNavigationMenuItemId != null) {
			setParentSiteNavigationMenuItemId(parentSiteNavigationMenuItemId);
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
	}

	@Override
	public java.lang.Object clone() {
		return new SiteNavigationMenuItemWrapper((SiteNavigationMenuItem)_siteNavigationMenuItem.clone());
	}

	@Override
	public int compareTo(SiteNavigationMenuItem siteNavigationMenuItem) {
		return _siteNavigationMenuItem.compareTo(siteNavigationMenuItem);
	}

	/**
	* Returns the company ID of this site navigation menu item.
	*
	* @return the company ID of this site navigation menu item
	*/
	@Override
	public long getCompanyId() {
		return _siteNavigationMenuItem.getCompanyId();
	}

	/**
	* Returns the create date of this site navigation menu item.
	*
	* @return the create date of this site navigation menu item
	*/
	@Override
	public Date getCreateDate() {
		return _siteNavigationMenuItem.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _siteNavigationMenuItem.getExpandoBridge();
	}

	/**
	* Returns the group ID of this site navigation menu item.
	*
	* @return the group ID of this site navigation menu item
	*/
	@Override
	public long getGroupId() {
		return _siteNavigationMenuItem.getGroupId();
	}

	/**
	* Returns the modified date of this site navigation menu item.
	*
	* @return the modified date of this site navigation menu item
	*/
	@Override
	public Date getModifiedDate() {
		return _siteNavigationMenuItem.getModifiedDate();
	}

	/**
	* Returns the order of this site navigation menu item.
	*
	* @return the order of this site navigation menu item
	*/
	@Override
	public int getOrder() {
		return _siteNavigationMenuItem.getOrder();
	}

	/**
	* Returns the parent site navigation menu item ID of this site navigation menu item.
	*
	* @return the parent site navigation menu item ID of this site navigation menu item
	*/
	@Override
	public long getParentSiteNavigationMenuItemId() {
		return _siteNavigationMenuItem.getParentSiteNavigationMenuItemId();
	}

	/**
	* Returns the primary key of this site navigation menu item.
	*
	* @return the primary key of this site navigation menu item
	*/
	@Override
	public long getPrimaryKey() {
		return _siteNavigationMenuItem.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _siteNavigationMenuItem.getPrimaryKeyObj();
	}

	/**
	* Returns the site navigation menu ID of this site navigation menu item.
	*
	* @return the site navigation menu ID of this site navigation menu item
	*/
	@Override
	public long getSiteNavigationMenuId() {
		return _siteNavigationMenuItem.getSiteNavigationMenuId();
	}

	/**
	* Returns the site navigation menu item ID of this site navigation menu item.
	*
	* @return the site navigation menu item ID of this site navigation menu item
	*/
	@Override
	public long getSiteNavigationMenuItemId() {
		return _siteNavigationMenuItem.getSiteNavigationMenuItemId();
	}

	/**
	* Returns the type of this site navigation menu item.
	*
	* @return the type of this site navigation menu item
	*/
	@Override
	public java.lang.String getType() {
		return _siteNavigationMenuItem.getType();
	}

	/**
	* Returns the type settings of this site navigation menu item.
	*
	* @return the type settings of this site navigation menu item
	*/
	@Override
	public java.lang.String getTypeSettings() {
		return _siteNavigationMenuItem.getTypeSettings();
	}

	/**
	* Returns the user ID of this site navigation menu item.
	*
	* @return the user ID of this site navigation menu item
	*/
	@Override
	public long getUserId() {
		return _siteNavigationMenuItem.getUserId();
	}

	/**
	* Returns the user name of this site navigation menu item.
	*
	* @return the user name of this site navigation menu item
	*/
	@Override
	public java.lang.String getUserName() {
		return _siteNavigationMenuItem.getUserName();
	}

	/**
	* Returns the user uuid of this site navigation menu item.
	*
	* @return the user uuid of this site navigation menu item
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _siteNavigationMenuItem.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _siteNavigationMenuItem.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _siteNavigationMenuItem.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _siteNavigationMenuItem.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _siteNavigationMenuItem.isNew();
	}

	@Override
	public void persist() {
		_siteNavigationMenuItem.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_siteNavigationMenuItem.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this site navigation menu item.
	*
	* @param companyId the company ID of this site navigation menu item
	*/
	@Override
	public void setCompanyId(long companyId) {
		_siteNavigationMenuItem.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this site navigation menu item.
	*
	* @param createDate the create date of this site navigation menu item
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_siteNavigationMenuItem.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_siteNavigationMenuItem.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_siteNavigationMenuItem.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_siteNavigationMenuItem.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this site navigation menu item.
	*
	* @param groupId the group ID of this site navigation menu item
	*/
	@Override
	public void setGroupId(long groupId) {
		_siteNavigationMenuItem.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this site navigation menu item.
	*
	* @param modifiedDate the modified date of this site navigation menu item
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_siteNavigationMenuItem.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_siteNavigationMenuItem.setNew(n);
	}

	/**
	* Sets the order of this site navigation menu item.
	*
	* @param order the order of this site navigation menu item
	*/
	@Override
	public void setOrder(int order) {
		_siteNavigationMenuItem.setOrder(order);
	}

	/**
	* Sets the parent site navigation menu item ID of this site navigation menu item.
	*
	* @param parentSiteNavigationMenuItemId the parent site navigation menu item ID of this site navigation menu item
	*/
	@Override
	public void setParentSiteNavigationMenuItemId(
		long parentSiteNavigationMenuItemId) {
		_siteNavigationMenuItem.setParentSiteNavigationMenuItemId(parentSiteNavigationMenuItemId);
	}

	/**
	* Sets the primary key of this site navigation menu item.
	*
	* @param primaryKey the primary key of this site navigation menu item
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_siteNavigationMenuItem.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_siteNavigationMenuItem.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the site navigation menu ID of this site navigation menu item.
	*
	* @param siteNavigationMenuId the site navigation menu ID of this site navigation menu item
	*/
	@Override
	public void setSiteNavigationMenuId(long siteNavigationMenuId) {
		_siteNavigationMenuItem.setSiteNavigationMenuId(siteNavigationMenuId);
	}

	/**
	* Sets the site navigation menu item ID of this site navigation menu item.
	*
	* @param siteNavigationMenuItemId the site navigation menu item ID of this site navigation menu item
	*/
	@Override
	public void setSiteNavigationMenuItemId(long siteNavigationMenuItemId) {
		_siteNavigationMenuItem.setSiteNavigationMenuItemId(siteNavigationMenuItemId);
	}

	/**
	* Sets the type of this site navigation menu item.
	*
	* @param type the type of this site navigation menu item
	*/
	@Override
	public void setType(java.lang.String type) {
		_siteNavigationMenuItem.setType(type);
	}

	/**
	* Sets the type settings of this site navigation menu item.
	*
	* @param typeSettings the type settings of this site navigation menu item
	*/
	@Override
	public void setTypeSettings(java.lang.String typeSettings) {
		_siteNavigationMenuItem.setTypeSettings(typeSettings);
	}

	/**
	* Sets the user ID of this site navigation menu item.
	*
	* @param userId the user ID of this site navigation menu item
	*/
	@Override
	public void setUserId(long userId) {
		_siteNavigationMenuItem.setUserId(userId);
	}

	/**
	* Sets the user name of this site navigation menu item.
	*
	* @param userName the user name of this site navigation menu item
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_siteNavigationMenuItem.setUserName(userName);
	}

	/**
	* Sets the user uuid of this site navigation menu item.
	*
	* @param userUuid the user uuid of this site navigation menu item
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_siteNavigationMenuItem.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<SiteNavigationMenuItem> toCacheModel() {
		return _siteNavigationMenuItem.toCacheModel();
	}

	@Override
	public SiteNavigationMenuItem toEscapedModel() {
		return new SiteNavigationMenuItemWrapper(_siteNavigationMenuItem.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _siteNavigationMenuItem.toString();
	}

	@Override
	public SiteNavigationMenuItem toUnescapedModel() {
		return new SiteNavigationMenuItemWrapper(_siteNavigationMenuItem.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _siteNavigationMenuItem.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SiteNavigationMenuItemWrapper)) {
			return false;
		}

		SiteNavigationMenuItemWrapper siteNavigationMenuItemWrapper = (SiteNavigationMenuItemWrapper)obj;

		if (Objects.equals(_siteNavigationMenuItem,
					siteNavigationMenuItemWrapper._siteNavigationMenuItem)) {
			return true;
		}

		return false;
	}

	@Override
	public SiteNavigationMenuItem getWrappedModel() {
		return _siteNavigationMenuItem;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _siteNavigationMenuItem.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _siteNavigationMenuItem.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_siteNavigationMenuItem.resetOriginalValues();
	}

	private final SiteNavigationMenuItem _siteNavigationMenuItem;
}