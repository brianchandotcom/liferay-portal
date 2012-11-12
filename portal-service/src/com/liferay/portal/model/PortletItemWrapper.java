/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link PortletItem}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PortletItem
 * @generated
 */
public class PortletItemWrapper implements PortletItem,
	ModelWrapper<PortletItem> {
	public PortletItemWrapper(PortletItem portletItem) {
		_portletItem = portletItem;
	}

	public Class<?> getModelClass() {
		return PortletItem.class;
	}

	public String getModelClassName() {
		return PortletItem.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("portletItemId", getPortletItemId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("portletId", getPortletId());
		attributes.put("classNameId", getClassNameId());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		Long portletItemId = (Long)attributes.get("portletItemId");

		if (portletItemId != null) {
			setPortletItemId(portletItemId);
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

		String portletId = (String)attributes.get("portletId");

		if (portletId != null) {
			setPortletId(portletId);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}
	}

	/**
	* Returns the primary key of this portlet item.
	*
	* @return the primary key of this portlet item
	*/
	public long getPrimaryKey() {
		return _portletItem.getPrimaryKey();
	}

	/**
	* Sets the primary key of this portlet item.
	*
	* @param primaryKey the primary key of this portlet item
	*/
	public void setPrimaryKey(long primaryKey) {
		_portletItem.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the portlet item ID of this portlet item.
	*
	* @return the portlet item ID of this portlet item
	*/
	public long getPortletItemId() {
		return _portletItem.getPortletItemId();
	}

	/**
	* Sets the portlet item ID of this portlet item.
	*
	* @param portletItemId the portlet item ID of this portlet item
	*/
	public void setPortletItemId(long portletItemId) {
		_portletItem.setPortletItemId(portletItemId);
	}

	/**
	* Returns the group ID of this portlet item.
	*
	* @return the group ID of this portlet item
	*/
	public long getGroupId() {
		return _portletItem.getGroupId();
	}

	/**
	* Sets the group ID of this portlet item.
	*
	* @param groupId the group ID of this portlet item
	*/
	public void setGroupId(long groupId) {
		_portletItem.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this portlet item.
	*
	* @return the company ID of this portlet item
	*/
	public long getCompanyId() {
		return _portletItem.getCompanyId();
	}

	/**
	* Sets the company ID of this portlet item.
	*
	* @param companyId the company ID of this portlet item
	*/
	public void setCompanyId(long companyId) {
		_portletItem.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this portlet item.
	*
	* @return the user ID of this portlet item
	*/
	public long getUserId() {
		return _portletItem.getUserId();
	}

	/**
	* Sets the user ID of this portlet item.
	*
	* @param userId the user ID of this portlet item
	*/
	public void setUserId(long userId) {
		_portletItem.setUserId(userId);
	}

	/**
	* Returns the user uuid of this portlet item.
	*
	* @return the user uuid of this portlet item
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portletItem.getUserUuid();
	}

	/**
	* Sets the user uuid of this portlet item.
	*
	* @param userUuid the user uuid of this portlet item
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_portletItem.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this portlet item.
	*
	* @return the user name of this portlet item
	*/
	public java.lang.String getUserName() {
		return _portletItem.getUserName();
	}

	/**
	* Sets the user name of this portlet item.
	*
	* @param userName the user name of this portlet item
	*/
	public void setUserName(java.lang.String userName) {
		_portletItem.setUserName(userName);
	}

	/**
	* Returns the create date of this portlet item.
	*
	* @return the create date of this portlet item
	*/
	public java.util.Date getCreateDate() {
		return _portletItem.getCreateDate();
	}

	/**
	* Sets the create date of this portlet item.
	*
	* @param createDate the create date of this portlet item
	*/
	public void setCreateDate(java.util.Date createDate) {
		_portletItem.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this portlet item.
	*
	* @return the modified date of this portlet item
	*/
	public java.util.Date getModifiedDate() {
		return _portletItem.getModifiedDate();
	}

	/**
	* Sets the modified date of this portlet item.
	*
	* @param modifiedDate the modified date of this portlet item
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_portletItem.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the name of this portlet item.
	*
	* @return the name of this portlet item
	*/
	public java.lang.String getName() {
		return _portletItem.getName();
	}

	/**
	* Sets the name of this portlet item.
	*
	* @param name the name of this portlet item
	*/
	public void setName(java.lang.String name) {
		_portletItem.setName(name);
	}

	/**
	* Returns the portlet ID of this portlet item.
	*
	* @return the portlet ID of this portlet item
	*/
	public java.lang.String getPortletId() {
		return _portletItem.getPortletId();
	}

	/**
	* Sets the portlet ID of this portlet item.
	*
	* @param portletId the portlet ID of this portlet item
	*/
	public void setPortletId(java.lang.String portletId) {
		_portletItem.setPortletId(portletId);
	}

	/**
	* Returns the fully qualified class name of this portlet item.
	*
	* @return the fully qualified class name of this portlet item
	*/
	public java.lang.String getClassName() {
		return _portletItem.getClassName();
	}

	public void setClassName(java.lang.String className) {
		_portletItem.setClassName(className);
	}

	/**
	* Returns the class name ID of this portlet item.
	*
	* @return the class name ID of this portlet item
	*/
	public long getClassNameId() {
		return _portletItem.getClassNameId();
	}

	/**
	* Sets the class name ID of this portlet item.
	*
	* @param classNameId the class name ID of this portlet item
	*/
	public void setClassNameId(long classNameId) {
		_portletItem.setClassNameId(classNameId);
	}

	public boolean isNew() {
		return _portletItem.isNew();
	}

	public void setNew(boolean n) {
		_portletItem.setNew(n);
	}

	public boolean isCachedModel() {
		return _portletItem.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_portletItem.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _portletItem.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _portletItem.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_portletItem.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _portletItem.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_portletItem.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new PortletItemWrapper((PortletItem)_portletItem.clone());
	}

	public int compareTo(com.liferay.portal.model.PortletItem portletItem) {
		return _portletItem.compareTo(portletItem);
	}

	@Override
	public int hashCode() {
		return _portletItem.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.PortletItem> toCacheModel() {
		return _portletItem.toCacheModel();
	}

	public com.liferay.portal.model.PortletItem toEscapedModel() {
		return new PortletItemWrapper(_portletItem.toEscapedModel());
	}

	public com.liferay.portal.model.PortletItem toUnescapedModel() {
		return new PortletItemWrapper(_portletItem.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _portletItem.toString();
	}

	public java.lang.String toXmlString() {
		return _portletItem.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_portletItem.persist();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public PortletItem getWrappedPortletItem() {
		return _portletItem;
	}

	public PortletItem getWrappedModel() {
		return _portletItem;
	}

	public void resetOriginalValues() {
		_portletItem.resetOriginalValues();
	}

	private PortletItem _portletItem;
}