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
 * This class is a wrapper for {@link LayoutPageTemplateCollection}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateCollection
 * @generated
 */
@ProviderType
public class LayoutPageTemplateCollectionWrapper
	implements LayoutPageTemplateCollection,
		ModelWrapper<LayoutPageTemplateCollection> {
	public LayoutPageTemplateCollectionWrapper(
		LayoutPageTemplateCollection layoutPageTemplateCollection) {
		_layoutPageTemplateCollection = layoutPageTemplateCollection;
	}

	@Override
	public Class<?> getModelClass() {
		return LayoutPageTemplateCollection.class;
	}

	@Override
	public String getModelClassName() {
		return LayoutPageTemplateCollection.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("layoutPageTemplateCollectionId",
			getLayoutPageTemplateCollectionId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("type", getType());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long layoutPageTemplateCollectionId = (Long)attributes.get(
				"layoutPageTemplateCollectionId");

		if (layoutPageTemplateCollectionId != null) {
			setLayoutPageTemplateCollectionId(layoutPageTemplateCollectionId);
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

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		Integer type = (Integer)attributes.get("type");

		if (type != null) {
			setType(type);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new LayoutPageTemplateCollectionWrapper((LayoutPageTemplateCollection)_layoutPageTemplateCollection.clone());
	}

	@Override
	public int compareTo(
		LayoutPageTemplateCollection layoutPageTemplateCollection) {
		return _layoutPageTemplateCollection.compareTo(layoutPageTemplateCollection);
	}

	/**
	* Returns the company ID of this layout page template collection.
	*
	* @return the company ID of this layout page template collection
	*/
	@Override
	public long getCompanyId() {
		return _layoutPageTemplateCollection.getCompanyId();
	}

	/**
	* Returns the create date of this layout page template collection.
	*
	* @return the create date of this layout page template collection
	*/
	@Override
	public Date getCreateDate() {
		return _layoutPageTemplateCollection.getCreateDate();
	}

	/**
	* Returns the description of this layout page template collection.
	*
	* @return the description of this layout page template collection
	*/
	@Override
	public java.lang.String getDescription() {
		return _layoutPageTemplateCollection.getDescription();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _layoutPageTemplateCollection.getExpandoBridge();
	}

	/**
	* Returns the group ID of this layout page template collection.
	*
	* @return the group ID of this layout page template collection
	*/
	@Override
	public long getGroupId() {
		return _layoutPageTemplateCollection.getGroupId();
	}

	/**
	* Returns the layout page template collection ID of this layout page template collection.
	*
	* @return the layout page template collection ID of this layout page template collection
	*/
	@Override
	public long getLayoutPageTemplateCollectionId() {
		return _layoutPageTemplateCollection.getLayoutPageTemplateCollectionId();
	}

	/**
	* Returns the modified date of this layout page template collection.
	*
	* @return the modified date of this layout page template collection
	*/
	@Override
	public Date getModifiedDate() {
		return _layoutPageTemplateCollection.getModifiedDate();
	}

	/**
	* Returns the name of this layout page template collection.
	*
	* @return the name of this layout page template collection
	*/
	@Override
	public java.lang.String getName() {
		return _layoutPageTemplateCollection.getName();
	}

	/**
	* Returns the primary key of this layout page template collection.
	*
	* @return the primary key of this layout page template collection
	*/
	@Override
	public long getPrimaryKey() {
		return _layoutPageTemplateCollection.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _layoutPageTemplateCollection.getPrimaryKeyObj();
	}

	/**
	* Returns the type of this layout page template collection.
	*
	* @return the type of this layout page template collection
	*/
	@Override
	public int getType() {
		return _layoutPageTemplateCollection.getType();
	}

	/**
	* Returns the user ID of this layout page template collection.
	*
	* @return the user ID of this layout page template collection
	*/
	@Override
	public long getUserId() {
		return _layoutPageTemplateCollection.getUserId();
	}

	/**
	* Returns the user name of this layout page template collection.
	*
	* @return the user name of this layout page template collection
	*/
	@Override
	public java.lang.String getUserName() {
		return _layoutPageTemplateCollection.getUserName();
	}

	/**
	* Returns the user uuid of this layout page template collection.
	*
	* @return the user uuid of this layout page template collection
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _layoutPageTemplateCollection.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _layoutPageTemplateCollection.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _layoutPageTemplateCollection.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _layoutPageTemplateCollection.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _layoutPageTemplateCollection.isNew();
	}

	@Override
	public void persist() {
		_layoutPageTemplateCollection.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_layoutPageTemplateCollection.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this layout page template collection.
	*
	* @param companyId the company ID of this layout page template collection
	*/
	@Override
	public void setCompanyId(long companyId) {
		_layoutPageTemplateCollection.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this layout page template collection.
	*
	* @param createDate the create date of this layout page template collection
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_layoutPageTemplateCollection.setCreateDate(createDate);
	}

	/**
	* Sets the description of this layout page template collection.
	*
	* @param description the description of this layout page template collection
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_layoutPageTemplateCollection.setDescription(description);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_layoutPageTemplateCollection.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_layoutPageTemplateCollection.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_layoutPageTemplateCollection.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this layout page template collection.
	*
	* @param groupId the group ID of this layout page template collection
	*/
	@Override
	public void setGroupId(long groupId) {
		_layoutPageTemplateCollection.setGroupId(groupId);
	}

	/**
	* Sets the layout page template collection ID of this layout page template collection.
	*
	* @param layoutPageTemplateCollectionId the layout page template collection ID of this layout page template collection
	*/
	@Override
	public void setLayoutPageTemplateCollectionId(
		long layoutPageTemplateCollectionId) {
		_layoutPageTemplateCollection.setLayoutPageTemplateCollectionId(layoutPageTemplateCollectionId);
	}

	/**
	* Sets the modified date of this layout page template collection.
	*
	* @param modifiedDate the modified date of this layout page template collection
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_layoutPageTemplateCollection.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this layout page template collection.
	*
	* @param name the name of this layout page template collection
	*/
	@Override
	public void setName(java.lang.String name) {
		_layoutPageTemplateCollection.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_layoutPageTemplateCollection.setNew(n);
	}

	/**
	* Sets the primary key of this layout page template collection.
	*
	* @param primaryKey the primary key of this layout page template collection
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_layoutPageTemplateCollection.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_layoutPageTemplateCollection.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the type of this layout page template collection.
	*
	* @param type the type of this layout page template collection
	*/
	@Override
	public void setType(int type) {
		_layoutPageTemplateCollection.setType(type);
	}

	/**
	* Sets the user ID of this layout page template collection.
	*
	* @param userId the user ID of this layout page template collection
	*/
	@Override
	public void setUserId(long userId) {
		_layoutPageTemplateCollection.setUserId(userId);
	}

	/**
	* Sets the user name of this layout page template collection.
	*
	* @param userName the user name of this layout page template collection
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_layoutPageTemplateCollection.setUserName(userName);
	}

	/**
	* Sets the user uuid of this layout page template collection.
	*
	* @param userUuid the user uuid of this layout page template collection
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_layoutPageTemplateCollection.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<LayoutPageTemplateCollection> toCacheModel() {
		return _layoutPageTemplateCollection.toCacheModel();
	}

	@Override
	public LayoutPageTemplateCollection toEscapedModel() {
		return new LayoutPageTemplateCollectionWrapper(_layoutPageTemplateCollection.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _layoutPageTemplateCollection.toString();
	}

	@Override
	public LayoutPageTemplateCollection toUnescapedModel() {
		return new LayoutPageTemplateCollectionWrapper(_layoutPageTemplateCollection.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _layoutPageTemplateCollection.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof LayoutPageTemplateCollectionWrapper)) {
			return false;
		}

		LayoutPageTemplateCollectionWrapper layoutPageTemplateCollectionWrapper = (LayoutPageTemplateCollectionWrapper)obj;

		if (Objects.equals(_layoutPageTemplateCollection,
					layoutPageTemplateCollectionWrapper._layoutPageTemplateCollection)) {
			return true;
		}

		return false;
	}

	@Override
	public LayoutPageTemplateCollection getWrappedModel() {
		return _layoutPageTemplateCollection;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _layoutPageTemplateCollection.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _layoutPageTemplateCollection.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_layoutPageTemplateCollection.resetOriginalValues();
	}

	private final LayoutPageTemplateCollection _layoutPageTemplateCollection;
}