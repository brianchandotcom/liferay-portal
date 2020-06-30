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

package com.liferay.commerce.pricing.model;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link CommercePricingClass}.
 * </p>
 *
 * @author Riccardo Alberti
 * @see CommercePricingClass
 * @generated
 */
public class CommercePricingClassWrapper
	implements CommercePricingClass, ModelWrapper<CommercePricingClass> {

	public CommercePricingClassWrapper(
		CommercePricingClass commercePricingClass) {

		_commercePricingClass = commercePricingClass;
	}

	@Override
	public Class<?> getModelClass() {
		return CommercePricingClass.class;
	}

	@Override
	public String getModelClassName() {
		return CommercePricingClass.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("externalReferenceCode", getExternalReferenceCode());
		attributes.put("commercePricingClassId", getCommercePricingClassId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("title", getTitle());
		attributes.put("description", getDescription());
		attributes.put("lastPublishDate", getLastPublishDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		String externalReferenceCode = (String)attributes.get(
			"externalReferenceCode");

		if (externalReferenceCode != null) {
			setExternalReferenceCode(externalReferenceCode);
		}

		Long commercePricingClassId = (Long)attributes.get(
			"commercePricingClassId");

		if (commercePricingClassId != null) {
			setCommercePricingClassId(commercePricingClassId);
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

		String title = (String)attributes.get("title");

		if (title != null) {
			setTitle(title);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	@Override
	public Object clone() {
		return new CommercePricingClassWrapper(
			(CommercePricingClass)_commercePricingClass.clone());
	}

	@Override
	public int compareTo(CommercePricingClass commercePricingClass) {
		return _commercePricingClass.compareTo(commercePricingClass);
	}

	/**
	 * Returns the commerce pricing class ID of this commerce pricing class.
	 *
	 * @return the commerce pricing class ID of this commerce pricing class
	 */
	@Override
	public long getCommercePricingClassId() {
		return _commercePricingClass.getCommercePricingClassId();
	}

	/**
	 * Returns the company ID of this commerce pricing class.
	 *
	 * @return the company ID of this commerce pricing class
	 */
	@Override
	public long getCompanyId() {
		return _commercePricingClass.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce pricing class.
	 *
	 * @return the create date of this commerce pricing class
	 */
	@Override
	public Date getCreateDate() {
		return _commercePricingClass.getCreateDate();
	}

	/**
	 * Returns the description of this commerce pricing class.
	 *
	 * @return the description of this commerce pricing class
	 */
	@Override
	public String getDescription() {
		return _commercePricingClass.getDescription();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commercePricingClass.getExpandoBridge();
	}

	/**
	 * Returns the external reference code of this commerce pricing class.
	 *
	 * @return the external reference code of this commerce pricing class
	 */
	@Override
	public String getExternalReferenceCode() {
		return _commercePricingClass.getExternalReferenceCode();
	}

	/**
	 * Returns the group ID of this commerce pricing class.
	 *
	 * @return the group ID of this commerce pricing class
	 */
	@Override
	public long getGroupId() {
		return _commercePricingClass.getGroupId();
	}

	/**
	 * Returns the last publish date of this commerce pricing class.
	 *
	 * @return the last publish date of this commerce pricing class
	 */
	@Override
	public Date getLastPublishDate() {
		return _commercePricingClass.getLastPublishDate();
	}

	/**
	 * Returns the modified date of this commerce pricing class.
	 *
	 * @return the modified date of this commerce pricing class
	 */
	@Override
	public Date getModifiedDate() {
		return _commercePricingClass.getModifiedDate();
	}

	/**
	 * Returns the primary key of this commerce pricing class.
	 *
	 * @return the primary key of this commerce pricing class
	 */
	@Override
	public long getPrimaryKey() {
		return _commercePricingClass.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commercePricingClass.getPrimaryKeyObj();
	}

	/**
	 * Returns the title of this commerce pricing class.
	 *
	 * @return the title of this commerce pricing class
	 */
	@Override
	public String getTitle() {
		return _commercePricingClass.getTitle();
	}

	/**
	 * Returns the user ID of this commerce pricing class.
	 *
	 * @return the user ID of this commerce pricing class
	 */
	@Override
	public long getUserId() {
		return _commercePricingClass.getUserId();
	}

	/**
	 * Returns the user name of this commerce pricing class.
	 *
	 * @return the user name of this commerce pricing class
	 */
	@Override
	public String getUserName() {
		return _commercePricingClass.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce pricing class.
	 *
	 * @return the user uuid of this commerce pricing class
	 */
	@Override
	public String getUserUuid() {
		return _commercePricingClass.getUserUuid();
	}

	/**
	 * Returns the uuid of this commerce pricing class.
	 *
	 * @return the uuid of this commerce pricing class
	 */
	@Override
	public String getUuid() {
		return _commercePricingClass.getUuid();
	}

	@Override
	public int hashCode() {
		return _commercePricingClass.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commercePricingClass.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commercePricingClass.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commercePricingClass.isNew();
	}

	@Override
	public void persist() {
		_commercePricingClass.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commercePricingClass.setCachedModel(cachedModel);
	}

	/**
	 * Sets the commerce pricing class ID of this commerce pricing class.
	 *
	 * @param commercePricingClassId the commerce pricing class ID of this commerce pricing class
	 */
	@Override
	public void setCommercePricingClassId(long commercePricingClassId) {
		_commercePricingClass.setCommercePricingClassId(commercePricingClassId);
	}

	/**
	 * Sets the company ID of this commerce pricing class.
	 *
	 * @param companyId the company ID of this commerce pricing class
	 */
	@Override
	public void setCompanyId(long companyId) {
		_commercePricingClass.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce pricing class.
	 *
	 * @param createDate the create date of this commerce pricing class
	 */
	@Override
	public void setCreateDate(Date createDate) {
		_commercePricingClass.setCreateDate(createDate);
	}

	/**
	 * Sets the description of this commerce pricing class.
	 *
	 * @param description the description of this commerce pricing class
	 */
	@Override
	public void setDescription(String description) {
		_commercePricingClass.setDescription(description);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_commercePricingClass.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commercePricingClass.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commercePricingClass.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the external reference code of this commerce pricing class.
	 *
	 * @param externalReferenceCode the external reference code of this commerce pricing class
	 */
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		_commercePricingClass.setExternalReferenceCode(externalReferenceCode);
	}

	/**
	 * Sets the group ID of this commerce pricing class.
	 *
	 * @param groupId the group ID of this commerce pricing class
	 */
	@Override
	public void setGroupId(long groupId) {
		_commercePricingClass.setGroupId(groupId);
	}

	/**
	 * Sets the last publish date of this commerce pricing class.
	 *
	 * @param lastPublishDate the last publish date of this commerce pricing class
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_commercePricingClass.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the modified date of this commerce pricing class.
	 *
	 * @param modifiedDate the modified date of this commerce pricing class
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commercePricingClass.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_commercePricingClass.setNew(n);
	}

	/**
	 * Sets the primary key of this commerce pricing class.
	 *
	 * @param primaryKey the primary key of this commerce pricing class
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commercePricingClass.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commercePricingClass.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the title of this commerce pricing class.
	 *
	 * @param title the title of this commerce pricing class
	 */
	@Override
	public void setTitle(String title) {
		_commercePricingClass.setTitle(title);
	}

	/**
	 * Sets the user ID of this commerce pricing class.
	 *
	 * @param userId the user ID of this commerce pricing class
	 */
	@Override
	public void setUserId(long userId) {
		_commercePricingClass.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce pricing class.
	 *
	 * @param userName the user name of this commerce pricing class
	 */
	@Override
	public void setUserName(String userName) {
		_commercePricingClass.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce pricing class.
	 *
	 * @param userUuid the user uuid of this commerce pricing class
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_commercePricingClass.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this commerce pricing class.
	 *
	 * @param uuid the uuid of this commerce pricing class
	 */
	@Override
	public void setUuid(String uuid) {
		_commercePricingClass.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommercePricingClass>
		toCacheModel() {

		return _commercePricingClass.toCacheModel();
	}

	@Override
	public CommercePricingClass toEscapedModel() {
		return new CommercePricingClassWrapper(
			_commercePricingClass.toEscapedModel());
	}

	@Override
	public String toString() {
		return _commercePricingClass.toString();
	}

	@Override
	public CommercePricingClass toUnescapedModel() {
		return new CommercePricingClassWrapper(
			_commercePricingClass.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _commercePricingClass.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommercePricingClassWrapper)) {
			return false;
		}

		CommercePricingClassWrapper commercePricingClassWrapper =
			(CommercePricingClassWrapper)obj;

		if (Objects.equals(
				_commercePricingClass,
				commercePricingClassWrapper._commercePricingClass)) {

			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _commercePricingClass.getStagedModelType();
	}

	@Override
	public CommercePricingClass getWrappedModel() {
		return _commercePricingClass;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commercePricingClass.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commercePricingClass.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commercePricingClass.resetOriginalValues();
	}

	private final CommercePricingClass _commercePricingClass;

}