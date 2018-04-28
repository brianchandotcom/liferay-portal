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

package com.liferay.commerce.discount.model;

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
 * This class is a wrapper for {@link CommerceDiscountRel}.
 * </p>
 *
 * @author Marco Leo
 * @see CommerceDiscountRel
 * @generated
 */
@ProviderType
public class CommerceDiscountRelWrapper implements CommerceDiscountRel,
	ModelWrapper<CommerceDiscountRel> {
	public CommerceDiscountRelWrapper(CommerceDiscountRel commerceDiscountRel) {
		_commerceDiscountRel = commerceDiscountRel;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceDiscountRel.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceDiscountRel.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("commerceDiscountRelId", getCommerceDiscountRelId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commerceDiscountId", getCommerceDiscountId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceDiscountRelId = (Long)attributes.get(
				"commerceDiscountRelId");

		if (commerceDiscountRelId != null) {
			setCommerceDiscountRelId(commerceDiscountRelId);
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

		Long commerceDiscountId = (Long)attributes.get("commerceDiscountId");

		if (commerceDiscountId != null) {
			setCommerceDiscountId(commerceDiscountId);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}
	}

	@Override
	public Object clone() {
		return new CommerceDiscountRelWrapper((CommerceDiscountRel)_commerceDiscountRel.clone());
	}

	@Override
	public int compareTo(CommerceDiscountRel commerceDiscountRel) {
		return _commerceDiscountRel.compareTo(commerceDiscountRel);
	}

	/**
	* Returns the fully qualified class name of this commerce discount rel.
	*
	* @return the fully qualified class name of this commerce discount rel
	*/
	@Override
	public String getClassName() {
		return _commerceDiscountRel.getClassName();
	}

	/**
	* Returns the class name ID of this commerce discount rel.
	*
	* @return the class name ID of this commerce discount rel
	*/
	@Override
	public long getClassNameId() {
		return _commerceDiscountRel.getClassNameId();
	}

	/**
	* Returns the class pk of this commerce discount rel.
	*
	* @return the class pk of this commerce discount rel
	*/
	@Override
	public long getClassPK() {
		return _commerceDiscountRel.getClassPK();
	}

	/**
	* Returns the commerce discount ID of this commerce discount rel.
	*
	* @return the commerce discount ID of this commerce discount rel
	*/
	@Override
	public long getCommerceDiscountId() {
		return _commerceDiscountRel.getCommerceDiscountId();
	}

	/**
	* Returns the commerce discount rel ID of this commerce discount rel.
	*
	* @return the commerce discount rel ID of this commerce discount rel
	*/
	@Override
	public long getCommerceDiscountRelId() {
		return _commerceDiscountRel.getCommerceDiscountRelId();
	}

	/**
	* Returns the company ID of this commerce discount rel.
	*
	* @return the company ID of this commerce discount rel
	*/
	@Override
	public long getCompanyId() {
		return _commerceDiscountRel.getCompanyId();
	}

	/**
	* Returns the create date of this commerce discount rel.
	*
	* @return the create date of this commerce discount rel
	*/
	@Override
	public Date getCreateDate() {
		return _commerceDiscountRel.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceDiscountRel.getExpandoBridge();
	}

	/**
	* Returns the group ID of this commerce discount rel.
	*
	* @return the group ID of this commerce discount rel
	*/
	@Override
	public long getGroupId() {
		return _commerceDiscountRel.getGroupId();
	}

	/**
	* Returns the modified date of this commerce discount rel.
	*
	* @return the modified date of this commerce discount rel
	*/
	@Override
	public Date getModifiedDate() {
		return _commerceDiscountRel.getModifiedDate();
	}

	/**
	* Returns the primary key of this commerce discount rel.
	*
	* @return the primary key of this commerce discount rel
	*/
	@Override
	public long getPrimaryKey() {
		return _commerceDiscountRel.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceDiscountRel.getPrimaryKeyObj();
	}

	/**
	* Returns the user ID of this commerce discount rel.
	*
	* @return the user ID of this commerce discount rel
	*/
	@Override
	public long getUserId() {
		return _commerceDiscountRel.getUserId();
	}

	/**
	* Returns the user name of this commerce discount rel.
	*
	* @return the user name of this commerce discount rel
	*/
	@Override
	public String getUserName() {
		return _commerceDiscountRel.getUserName();
	}

	/**
	* Returns the user uuid of this commerce discount rel.
	*
	* @return the user uuid of this commerce discount rel
	*/
	@Override
	public String getUserUuid() {
		return _commerceDiscountRel.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _commerceDiscountRel.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceDiscountRel.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceDiscountRel.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceDiscountRel.isNew();
	}

	@Override
	public void persist() {
		_commerceDiscountRel.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceDiscountRel.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(String className) {
		_commerceDiscountRel.setClassName(className);
	}

	/**
	* Sets the class name ID of this commerce discount rel.
	*
	* @param classNameId the class name ID of this commerce discount rel
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_commerceDiscountRel.setClassNameId(classNameId);
	}

	/**
	* Sets the class pk of this commerce discount rel.
	*
	* @param classPK the class pk of this commerce discount rel
	*/
	@Override
	public void setClassPK(long classPK) {
		_commerceDiscountRel.setClassPK(classPK);
	}

	/**
	* Sets the commerce discount ID of this commerce discount rel.
	*
	* @param commerceDiscountId the commerce discount ID of this commerce discount rel
	*/
	@Override
	public void setCommerceDiscountId(long commerceDiscountId) {
		_commerceDiscountRel.setCommerceDiscountId(commerceDiscountId);
	}

	/**
	* Sets the commerce discount rel ID of this commerce discount rel.
	*
	* @param commerceDiscountRelId the commerce discount rel ID of this commerce discount rel
	*/
	@Override
	public void setCommerceDiscountRelId(long commerceDiscountRelId) {
		_commerceDiscountRel.setCommerceDiscountRelId(commerceDiscountRelId);
	}

	/**
	* Sets the company ID of this commerce discount rel.
	*
	* @param companyId the company ID of this commerce discount rel
	*/
	@Override
	public void setCompanyId(long companyId) {
		_commerceDiscountRel.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this commerce discount rel.
	*
	* @param createDate the create date of this commerce discount rel
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_commerceDiscountRel.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_commerceDiscountRel.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceDiscountRel.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceDiscountRel.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this commerce discount rel.
	*
	* @param groupId the group ID of this commerce discount rel
	*/
	@Override
	public void setGroupId(long groupId) {
		_commerceDiscountRel.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this commerce discount rel.
	*
	* @param modifiedDate the modified date of this commerce discount rel
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceDiscountRel.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_commerceDiscountRel.setNew(n);
	}

	/**
	* Sets the primary key of this commerce discount rel.
	*
	* @param primaryKey the primary key of this commerce discount rel
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceDiscountRel.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceDiscountRel.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this commerce discount rel.
	*
	* @param userId the user ID of this commerce discount rel
	*/
	@Override
	public void setUserId(long userId) {
		_commerceDiscountRel.setUserId(userId);
	}

	/**
	* Sets the user name of this commerce discount rel.
	*
	* @param userName the user name of this commerce discount rel
	*/
	@Override
	public void setUserName(String userName) {
		_commerceDiscountRel.setUserName(userName);
	}

	/**
	* Sets the user uuid of this commerce discount rel.
	*
	* @param userUuid the user uuid of this commerce discount rel
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_commerceDiscountRel.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceDiscountRel> toCacheModel() {
		return _commerceDiscountRel.toCacheModel();
	}

	@Override
	public CommerceDiscountRel toEscapedModel() {
		return new CommerceDiscountRelWrapper(_commerceDiscountRel.toEscapedModel());
	}

	@Override
	public String toString() {
		return _commerceDiscountRel.toString();
	}

	@Override
	public CommerceDiscountRel toUnescapedModel() {
		return new CommerceDiscountRelWrapper(_commerceDiscountRel.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _commerceDiscountRel.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceDiscountRelWrapper)) {
			return false;
		}

		CommerceDiscountRelWrapper commerceDiscountRelWrapper = (CommerceDiscountRelWrapper)obj;

		if (Objects.equals(_commerceDiscountRel,
					commerceDiscountRelWrapper._commerceDiscountRel)) {
			return true;
		}

		return false;
	}

	@Override
	public CommerceDiscountRel getWrappedModel() {
		return _commerceDiscountRel;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceDiscountRel.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceDiscountRel.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceDiscountRel.resetOriginalValues();
	}

	private final CommerceDiscountRel _commerceDiscountRel;
}