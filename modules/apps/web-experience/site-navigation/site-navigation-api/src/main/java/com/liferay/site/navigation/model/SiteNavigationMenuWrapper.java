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
 * This class is a wrapper for {@link SiteNavigationMenu}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SiteNavigationMenu
 * @generated
 */
@ProviderType
public class SiteNavigationMenuWrapper implements SiteNavigationMenu,
	ModelWrapper<SiteNavigationMenu> {
	public SiteNavigationMenuWrapper(SiteNavigationMenu siteNavigationMenu) {
		_siteNavigationMenu = siteNavigationMenu;
	}

	@Override
	public Class<?> getModelClass() {
		return SiteNavigationMenu.class;
	}

	@Override
	public String getModelClassName() {
		return SiteNavigationMenu.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("siteNavigationMenuId", getSiteNavigationMenuId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("type", getType());
		attributes.put("auto", getAuto());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long siteNavigationMenuId = (Long)attributes.get("siteNavigationMenuId");

		if (siteNavigationMenuId != null) {
			setSiteNavigationMenuId(siteNavigationMenuId);
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

		Integer type = (Integer)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		Boolean auto = (Boolean)attributes.get("auto");

		if (auto != null) {
			setAuto(auto);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new SiteNavigationMenuWrapper((SiteNavigationMenu)_siteNavigationMenu.clone());
	}

	@Override
	public int compareTo(SiteNavigationMenu siteNavigationMenu) {
		return _siteNavigationMenu.compareTo(siteNavigationMenu);
	}

	/**
	* Returns the auto of this site navigation menu.
	*
	* @return the auto of this site navigation menu
	*/
	@Override
	public boolean getAuto() {
		return _siteNavigationMenu.getAuto();
	}

	/**
	* Returns the company ID of this site navigation menu.
	*
	* @return the company ID of this site navigation menu
	*/
	@Override
	public long getCompanyId() {
		return _siteNavigationMenu.getCompanyId();
	}

	/**
	* Returns the create date of this site navigation menu.
	*
	* @return the create date of this site navigation menu
	*/
	@Override
	public Date getCreateDate() {
		return _siteNavigationMenu.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _siteNavigationMenu.getExpandoBridge();
	}

	/**
	* Returns the group ID of this site navigation menu.
	*
	* @return the group ID of this site navigation menu
	*/
	@Override
	public long getGroupId() {
		return _siteNavigationMenu.getGroupId();
	}

	/**
	* Returns the modified date of this site navigation menu.
	*
	* @return the modified date of this site navigation menu
	*/
	@Override
	public Date getModifiedDate() {
		return _siteNavigationMenu.getModifiedDate();
	}

	/**
	* Returns the name of this site navigation menu.
	*
	* @return the name of this site navigation menu
	*/
	@Override
	public java.lang.String getName() {
		return _siteNavigationMenu.getName();
	}

	/**
	* Returns the primary key of this site navigation menu.
	*
	* @return the primary key of this site navigation menu
	*/
	@Override
	public long getPrimaryKey() {
		return _siteNavigationMenu.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _siteNavigationMenu.getPrimaryKeyObj();
	}

	/**
	* Returns the site navigation menu ID of this site navigation menu.
	*
	* @return the site navigation menu ID of this site navigation menu
	*/
	@Override
	public long getSiteNavigationMenuId() {
		return _siteNavigationMenu.getSiteNavigationMenuId();
	}

	/**
	* Returns the type of this site navigation menu.
	*
	* @return the type of this site navigation menu
	*/
	@Override
	public int getType() {
		return _siteNavigationMenu.getType();
	}

	@Override
	public java.lang.String getTypeKey() {
		return _siteNavigationMenu.getTypeKey();
	}

	/**
	* Returns the user ID of this site navigation menu.
	*
	* @return the user ID of this site navigation menu
	*/
	@Override
	public long getUserId() {
		return _siteNavigationMenu.getUserId();
	}

	/**
	* Returns the user name of this site navigation menu.
	*
	* @return the user name of this site navigation menu
	*/
	@Override
	public java.lang.String getUserName() {
		return _siteNavigationMenu.getUserName();
	}

	/**
	* Returns the user uuid of this site navigation menu.
	*
	* @return the user uuid of this site navigation menu
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _siteNavigationMenu.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _siteNavigationMenu.hashCode();
	}

	/**
	* Returns <code>true</code> if this site navigation menu is auto.
	*
	* @return <code>true</code> if this site navigation menu is auto; <code>false</code> otherwise
	*/
	@Override
	public boolean isAuto() {
		return _siteNavigationMenu.isAuto();
	}

	@Override
	public boolean isCachedModel() {
		return _siteNavigationMenu.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _siteNavigationMenu.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _siteNavigationMenu.isNew();
	}

	@Override
	public boolean isPrimary() {
		return _siteNavigationMenu.isPrimary();
	}

	@Override
	public void persist() {
		_siteNavigationMenu.persist();
	}

	/**
	* Sets whether this site navigation menu is auto.
	*
	* @param auto the auto of this site navigation menu
	*/
	@Override
	public void setAuto(boolean auto) {
		_siteNavigationMenu.setAuto(auto);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_siteNavigationMenu.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this site navigation menu.
	*
	* @param companyId the company ID of this site navigation menu
	*/
	@Override
	public void setCompanyId(long companyId) {
		_siteNavigationMenu.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this site navigation menu.
	*
	* @param createDate the create date of this site navigation menu
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_siteNavigationMenu.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_siteNavigationMenu.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_siteNavigationMenu.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_siteNavigationMenu.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this site navigation menu.
	*
	* @param groupId the group ID of this site navigation menu
	*/
	@Override
	public void setGroupId(long groupId) {
		_siteNavigationMenu.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this site navigation menu.
	*
	* @param modifiedDate the modified date of this site navigation menu
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_siteNavigationMenu.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this site navigation menu.
	*
	* @param name the name of this site navigation menu
	*/
	@Override
	public void setName(java.lang.String name) {
		_siteNavigationMenu.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_siteNavigationMenu.setNew(n);
	}

	/**
	* Sets the primary key of this site navigation menu.
	*
	* @param primaryKey the primary key of this site navigation menu
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_siteNavigationMenu.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_siteNavigationMenu.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the site navigation menu ID of this site navigation menu.
	*
	* @param siteNavigationMenuId the site navigation menu ID of this site navigation menu
	*/
	@Override
	public void setSiteNavigationMenuId(long siteNavigationMenuId) {
		_siteNavigationMenu.setSiteNavigationMenuId(siteNavigationMenuId);
	}

	/**
	* Sets the type of this site navigation menu.
	*
	* @param type the type of this site navigation menu
	*/
	@Override
	public void setType(int type) {
		_siteNavigationMenu.setType(type);
	}

	/**
	* Sets the user ID of this site navigation menu.
	*
	* @param userId the user ID of this site navigation menu
	*/
	@Override
	public void setUserId(long userId) {
		_siteNavigationMenu.setUserId(userId);
	}

	/**
	* Sets the user name of this site navigation menu.
	*
	* @param userName the user name of this site navigation menu
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_siteNavigationMenu.setUserName(userName);
	}

	/**
	* Sets the user uuid of this site navigation menu.
	*
	* @param userUuid the user uuid of this site navigation menu
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_siteNavigationMenu.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<SiteNavigationMenu> toCacheModel() {
		return _siteNavigationMenu.toCacheModel();
	}

	@Override
	public SiteNavigationMenu toEscapedModel() {
		return new SiteNavigationMenuWrapper(_siteNavigationMenu.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _siteNavigationMenu.toString();
	}

	@Override
	public SiteNavigationMenu toUnescapedModel() {
		return new SiteNavigationMenuWrapper(_siteNavigationMenu.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _siteNavigationMenu.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SiteNavigationMenuWrapper)) {
			return false;
		}

		SiteNavigationMenuWrapper siteNavigationMenuWrapper = (SiteNavigationMenuWrapper)obj;

		if (Objects.equals(_siteNavigationMenu,
					siteNavigationMenuWrapper._siteNavigationMenu)) {
			return true;
		}

		return false;
	}

	@Override
	public SiteNavigationMenu getWrappedModel() {
		return _siteNavigationMenu;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _siteNavigationMenu.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _siteNavigationMenu.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_siteNavigationMenu.resetOriginalValues();
	}

	private final SiteNavigationMenu _siteNavigationMenu;
}