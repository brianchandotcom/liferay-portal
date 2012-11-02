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

package com.liferay.portlet.messageboards.model;

import com.liferay.portal.model.ModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link MBCategory}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBCategory
 * @generated
 */
public class MBCategoryWrapper implements MBCategory, ModelWrapper<MBCategory> {
	public MBCategoryWrapper(MBCategory mbCategory) {
		_mbCategory = mbCategory;
	}

	public Class<?> getModelClass() {
		return MBCategory.class;
	}

	public String getModelClassName() {
		return MBCategory.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("categoryId", getCategoryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("parentCategoryId", getParentCategoryId());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("displayStyle", getDisplayStyle());
		attributes.put("threadCount", getThreadCount());
		attributes.put("messageCount", getMessageCount());
		attributes.put("lastPostDate", getLastPostDate());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long categoryId = (Long)attributes.get("categoryId");

		if (categoryId != null) {
			setCategoryId(categoryId);
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

		Long parentCategoryId = (Long)attributes.get("parentCategoryId");

		if (parentCategoryId != null) {
			setParentCategoryId(parentCategoryId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String displayStyle = (String)attributes.get("displayStyle");

		if (displayStyle != null) {
			setDisplayStyle(displayStyle);
		}

		Integer threadCount = (Integer)attributes.get("threadCount");

		if (threadCount != null) {
			setThreadCount(threadCount);
		}

		Integer messageCount = (Integer)attributes.get("messageCount");

		if (messageCount != null) {
			setMessageCount(messageCount);
		}

		Date lastPostDate = (Date)attributes.get("lastPostDate");

		if (lastPostDate != null) {
			setLastPostDate(lastPostDate);
		}
	}

	/**
	* Returns the primary key of this message boards category.
	*
	* @return the primary key of this message boards category
	*/
	public long getPrimaryKey() {
		return _mbCategory.getPrimaryKey();
	}

	/**
	* Sets the primary key of this message boards category.
	*
	* @param primaryKey the primary key of this message boards category
	*/
	public void setPrimaryKey(long primaryKey) {
		_mbCategory.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this message boards category.
	*
	* @return the uuid of this message boards category
	*/
	public java.lang.String getUuid() {
		return _mbCategory.getUuid();
	}

	/**
	* Sets the uuid of this message boards category.
	*
	* @param uuid the uuid of this message boards category
	*/
	public void setUuid(java.lang.String uuid) {
		_mbCategory.setUuid(uuid);
	}

	/**
	* Returns the category ID of this message boards category.
	*
	* @return the category ID of this message boards category
	*/
	public long getCategoryId() {
		return _mbCategory.getCategoryId();
	}

	/**
	* Sets the category ID of this message boards category.
	*
	* @param categoryId the category ID of this message boards category
	*/
	public void setCategoryId(long categoryId) {
		_mbCategory.setCategoryId(categoryId);
	}

	/**
	* Returns the group ID of this message boards category.
	*
	* @return the group ID of this message boards category
	*/
	public long getGroupId() {
		return _mbCategory.getGroupId();
	}

	/**
	* Sets the group ID of this message boards category.
	*
	* @param groupId the group ID of this message boards category
	*/
	public void setGroupId(long groupId) {
		_mbCategory.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this message boards category.
	*
	* @return the company ID of this message boards category
	*/
	public long getCompanyId() {
		return _mbCategory.getCompanyId();
	}

	/**
	* Sets the company ID of this message boards category.
	*
	* @param companyId the company ID of this message boards category
	*/
	public void setCompanyId(long companyId) {
		_mbCategory.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this message boards category.
	*
	* @return the user ID of this message boards category
	*/
	public long getUserId() {
		return _mbCategory.getUserId();
	}

	/**
	* Sets the user ID of this message boards category.
	*
	* @param userId the user ID of this message boards category
	*/
	public void setUserId(long userId) {
		_mbCategory.setUserId(userId);
	}

	/**
	* Returns the user uuid of this message boards category.
	*
	* @return the user uuid of this message boards category
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbCategory.getUserUuid();
	}

	/**
	* Sets the user uuid of this message boards category.
	*
	* @param userUuid the user uuid of this message boards category
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_mbCategory.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this message boards category.
	*
	* @return the user name of this message boards category
	*/
	public java.lang.String getUserName() {
		return _mbCategory.getUserName();
	}

	/**
	* Sets the user name of this message boards category.
	*
	* @param userName the user name of this message boards category
	*/
	public void setUserName(java.lang.String userName) {
		_mbCategory.setUserName(userName);
	}

	/**
	* Returns the create date of this message boards category.
	*
	* @return the create date of this message boards category
	*/
	public java.util.Date getCreateDate() {
		return _mbCategory.getCreateDate();
	}

	/**
	* Sets the create date of this message boards category.
	*
	* @param createDate the create date of this message boards category
	*/
	public void setCreateDate(java.util.Date createDate) {
		_mbCategory.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this message boards category.
	*
	* @return the modified date of this message boards category
	*/
	public java.util.Date getModifiedDate() {
		return _mbCategory.getModifiedDate();
	}

	/**
	* Sets the modified date of this message boards category.
	*
	* @param modifiedDate the modified date of this message boards category
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_mbCategory.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the parent category ID of this message boards category.
	*
	* @return the parent category ID of this message boards category
	*/
	public long getParentCategoryId() {
		return _mbCategory.getParentCategoryId();
	}

	/**
	* Sets the parent category ID of this message boards category.
	*
	* @param parentCategoryId the parent category ID of this message boards category
	*/
	public void setParentCategoryId(long parentCategoryId) {
		_mbCategory.setParentCategoryId(parentCategoryId);
	}

	/**
	* Returns the name of this message boards category.
	*
	* @return the name of this message boards category
	*/
	public java.lang.String getName() {
		return _mbCategory.getName();
	}

	/**
	* Sets the name of this message boards category.
	*
	* @param name the name of this message boards category
	*/
	public void setName(java.lang.String name) {
		_mbCategory.setName(name);
	}

	/**
	* Returns the description of this message boards category.
	*
	* @return the description of this message boards category
	*/
	public java.lang.String getDescription() {
		return _mbCategory.getDescription();
	}

	/**
	* Sets the description of this message boards category.
	*
	* @param description the description of this message boards category
	*/
	public void setDescription(java.lang.String description) {
		_mbCategory.setDescription(description);
	}

	/**
	* Returns the display style of this message boards category.
	*
	* @return the display style of this message boards category
	*/
	public java.lang.String getDisplayStyle() {
		return _mbCategory.getDisplayStyle();
	}

	/**
	* Sets the display style of this message boards category.
	*
	* @param displayStyle the display style of this message boards category
	*/
	public void setDisplayStyle(java.lang.String displayStyle) {
		_mbCategory.setDisplayStyle(displayStyle);
	}

	/**
	* Returns the thread count of this message boards category.
	*
	* @return the thread count of this message boards category
	*/
	public int getThreadCount() {
		return _mbCategory.getThreadCount();
	}

	/**
	* Sets the thread count of this message boards category.
	*
	* @param threadCount the thread count of this message boards category
	*/
	public void setThreadCount(int threadCount) {
		_mbCategory.setThreadCount(threadCount);
	}

	/**
	* Returns the message count of this message boards category.
	*
	* @return the message count of this message boards category
	*/
	public int getMessageCount() {
		return _mbCategory.getMessageCount();
	}

	/**
	* Sets the message count of this message boards category.
	*
	* @param messageCount the message count of this message boards category
	*/
	public void setMessageCount(int messageCount) {
		_mbCategory.setMessageCount(messageCount);
	}

	/**
	* Returns the last post date of this message boards category.
	*
	* @return the last post date of this message boards category
	*/
	public java.util.Date getLastPostDate() {
		return _mbCategory.getLastPostDate();
	}

	/**
	* Sets the last post date of this message boards category.
	*
	* @param lastPostDate the last post date of this message boards category
	*/
	public void setLastPostDate(java.util.Date lastPostDate) {
		_mbCategory.setLastPostDate(lastPostDate);
	}

	public boolean isNew() {
		return _mbCategory.isNew();
	}

	public void setNew(boolean n) {
		_mbCategory.setNew(n);
	}

	public boolean isCachedModel() {
		return _mbCategory.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_mbCategory.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _mbCategory.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _mbCategory.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_mbCategory.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _mbCategory.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_mbCategory.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new MBCategoryWrapper((MBCategory)_mbCategory.clone());
	}

	public int compareTo(
		com.liferay.portlet.messageboards.model.MBCategory mbCategory) {
		return _mbCategory.compareTo(mbCategory);
	}

	@Override
	public int hashCode() {
		return _mbCategory.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portlet.messageboards.model.MBCategory> toCacheModel() {
		return _mbCategory.toCacheModel();
	}

	public com.liferay.portlet.messageboards.model.MBCategory toEscapedModel() {
		return new MBCategoryWrapper(_mbCategory.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _mbCategory.toString();
	}

	public java.lang.String toXmlString() {
		return _mbCategory.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_mbCategory.persist();
	}

	public java.util.List<java.lang.Long> getAncestorCategoryIds()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbCategory.getAncestorCategoryIds();
	}

	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> getAncestors()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbCategory.getAncestors();
	}

	public com.liferay.portlet.messageboards.model.MBCategory getParentCategory()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbCategory.getParentCategory();
	}

	public boolean isRoot() {
		return _mbCategory.isRoot();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public MBCategory getWrappedMBCategory() {
		return _mbCategory;
	}

	public MBCategory getWrappedModel() {
		return _mbCategory;
	}

	public void resetOriginalValues() {
		_mbCategory.resetOriginalValues();
	}

	private MBCategory _mbCategory;
}