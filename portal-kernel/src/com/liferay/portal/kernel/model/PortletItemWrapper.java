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

package com.liferay.portal.kernel.model;

import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link PortletItem}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PortletItem
 * @generated
 */
public class PortletItemWrapper
	extends BaseModelWrapper<PortletItem>
	implements ModelWrapper<PortletItem>, PortletItem {

	public PortletItemWrapper(PortletItem portletItem) {
		super(portletItem);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
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

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

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
	 * Returns the fully qualified class name of this portlet item.
	 *
	 * @return the fully qualified class name of this portlet item
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this portlet item.
	 *
	 * @return the class name ID of this portlet item
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the company ID of this portlet item.
	 *
	 * @return the company ID of this portlet item
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this portlet item.
	 *
	 * @return the create date of this portlet item
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the group ID of this portlet item.
	 *
	 * @return the group ID of this portlet item
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this portlet item.
	 *
	 * @return the modified date of this portlet item
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this portlet item.
	 *
	 * @return the mvcc version of this portlet item
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this portlet item.
	 *
	 * @return the name of this portlet item
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the portlet ID of this portlet item.
	 *
	 * @return the portlet ID of this portlet item
	 */
	@Override
	public String getPortletId() {
		return model.getPortletId();
	}

	/**
	 * Returns the portlet item ID of this portlet item.
	 *
	 * @return the portlet item ID of this portlet item
	 */
	@Override
	public long getPortletItemId() {
		return model.getPortletItemId();
	}

	/**
	 * Returns the primary key of this portlet item.
	 *
	 * @return the primary key of this portlet item
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this portlet item.
	 *
	 * @return the user ID of this portlet item
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this portlet item.
	 *
	 * @return the user name of this portlet item
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this portlet item.
	 *
	 * @return the user uuid of this portlet item
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a portlet item model instance should use the <code>PortletItem</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	 * Sets the class name ID of this portlet item.
	 *
	 * @param classNameId the class name ID of this portlet item
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the company ID of this portlet item.
	 *
	 * @param companyId the company ID of this portlet item
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this portlet item.
	 *
	 * @param createDate the create date of this portlet item
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the group ID of this portlet item.
	 *
	 * @param groupId the group ID of this portlet item
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this portlet item.
	 *
	 * @param modifiedDate the modified date of this portlet item
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this portlet item.
	 *
	 * @param mvccVersion the mvcc version of this portlet item
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this portlet item.
	 *
	 * @param name the name of this portlet item
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the portlet ID of this portlet item.
	 *
	 * @param portletId the portlet ID of this portlet item
	 */
	@Override
	public void setPortletId(String portletId) {
		model.setPortletId(portletId);
	}

	/**
	 * Sets the portlet item ID of this portlet item.
	 *
	 * @param portletItemId the portlet item ID of this portlet item
	 */
	@Override
	public void setPortletItemId(long portletItemId) {
		model.setPortletItemId(portletItemId);
	}

	/**
	 * Sets the primary key of this portlet item.
	 *
	 * @param primaryKey the primary key of this portlet item
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this portlet item.
	 *
	 * @param userId the user ID of this portlet item
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this portlet item.
	 *
	 * @param userName the user name of this portlet item
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this portlet item.
	 *
	 * @param userUuid the user uuid of this portlet item
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected PortletItemWrapper wrap(PortletItem portletItem) {
		return new PortletItemWrapper(portletItem);
	}

}