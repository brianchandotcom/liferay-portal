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

package com.liferay.commerce.model;

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
 * This class is a wrapper for {@link CommerceTaxCategoryRel}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTaxCategoryRel
 * @generated
 */
@ProviderType
public class CommerceTaxCategoryRelWrapper implements CommerceTaxCategoryRel,
	ModelWrapper<CommerceTaxCategoryRel> {
	public CommerceTaxCategoryRelWrapper(
		CommerceTaxCategoryRel commerceTaxCategoryRel) {
		_commerceTaxCategoryRel = commerceTaxCategoryRel;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceTaxCategoryRel.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceTaxCategoryRel.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("commerceTaxCategoryRelId", getCommerceTaxCategoryRelId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commerceTaxCategoryId", getCommerceTaxCategoryId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceTaxCategoryRelId = (Long)attributes.get(
				"commerceTaxCategoryRelId");

		if (commerceTaxCategoryRelId != null) {
			setCommerceTaxCategoryRelId(commerceTaxCategoryRelId);
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

		Long commerceTaxCategoryId = (Long)attributes.get(
				"commerceTaxCategoryId");

		if (commerceTaxCategoryId != null) {
			setCommerceTaxCategoryId(commerceTaxCategoryId);
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
	public java.lang.Object clone() {
		return new CommerceTaxCategoryRelWrapper((CommerceTaxCategoryRel)_commerceTaxCategoryRel.clone());
	}

	@Override
	public int compareTo(CommerceTaxCategoryRel commerceTaxCategoryRel) {
		return _commerceTaxCategoryRel.compareTo(commerceTaxCategoryRel);
	}

	/**
	* Returns the fully qualified class name of this commerce tax category rel.
	*
	* @return the fully qualified class name of this commerce tax category rel
	*/
	@Override
	public java.lang.String getClassName() {
		return _commerceTaxCategoryRel.getClassName();
	}

	/**
	* Returns the class name ID of this commerce tax category rel.
	*
	* @return the class name ID of this commerce tax category rel
	*/
	@Override
	public long getClassNameId() {
		return _commerceTaxCategoryRel.getClassNameId();
	}

	/**
	* Returns the class pk of this commerce tax category rel.
	*
	* @return the class pk of this commerce tax category rel
	*/
	@Override
	public long getClassPK() {
		return _commerceTaxCategoryRel.getClassPK();
	}

	@Override
	public CommerceTaxCategory getCommerceTaxCategory()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTaxCategoryRel.getCommerceTaxCategory();
	}

	/**
	* Returns the commerce tax category ID of this commerce tax category rel.
	*
	* @return the commerce tax category ID of this commerce tax category rel
	*/
	@Override
	public long getCommerceTaxCategoryId() {
		return _commerceTaxCategoryRel.getCommerceTaxCategoryId();
	}

	/**
	* Returns the commerce tax category rel ID of this commerce tax category rel.
	*
	* @return the commerce tax category rel ID of this commerce tax category rel
	*/
	@Override
	public long getCommerceTaxCategoryRelId() {
		return _commerceTaxCategoryRel.getCommerceTaxCategoryRelId();
	}

	/**
	* Returns the company ID of this commerce tax category rel.
	*
	* @return the company ID of this commerce tax category rel
	*/
	@Override
	public long getCompanyId() {
		return _commerceTaxCategoryRel.getCompanyId();
	}

	/**
	* Returns the create date of this commerce tax category rel.
	*
	* @return the create date of this commerce tax category rel
	*/
	@Override
	public Date getCreateDate() {
		return _commerceTaxCategoryRel.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceTaxCategoryRel.getExpandoBridge();
	}

	/**
	* Returns the group ID of this commerce tax category rel.
	*
	* @return the group ID of this commerce tax category rel
	*/
	@Override
	public long getGroupId() {
		return _commerceTaxCategoryRel.getGroupId();
	}

	/**
	* Returns the modified date of this commerce tax category rel.
	*
	* @return the modified date of this commerce tax category rel
	*/
	@Override
	public Date getModifiedDate() {
		return _commerceTaxCategoryRel.getModifiedDate();
	}

	/**
	* Returns the primary key of this commerce tax category rel.
	*
	* @return the primary key of this commerce tax category rel
	*/
	@Override
	public long getPrimaryKey() {
		return _commerceTaxCategoryRel.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceTaxCategoryRel.getPrimaryKeyObj();
	}

	/**
	* Returns the user ID of this commerce tax category rel.
	*
	* @return the user ID of this commerce tax category rel
	*/
	@Override
	public long getUserId() {
		return _commerceTaxCategoryRel.getUserId();
	}

	/**
	* Returns the user name of this commerce tax category rel.
	*
	* @return the user name of this commerce tax category rel
	*/
	@Override
	public java.lang.String getUserName() {
		return _commerceTaxCategoryRel.getUserName();
	}

	/**
	* Returns the user uuid of this commerce tax category rel.
	*
	* @return the user uuid of this commerce tax category rel
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _commerceTaxCategoryRel.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _commerceTaxCategoryRel.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceTaxCategoryRel.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceTaxCategoryRel.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceTaxCategoryRel.isNew();
	}

	@Override
	public void persist() {
		_commerceTaxCategoryRel.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceTaxCategoryRel.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(java.lang.String className) {
		_commerceTaxCategoryRel.setClassName(className);
	}

	/**
	* Sets the class name ID of this commerce tax category rel.
	*
	* @param classNameId the class name ID of this commerce tax category rel
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_commerceTaxCategoryRel.setClassNameId(classNameId);
	}

	/**
	* Sets the class pk of this commerce tax category rel.
	*
	* @param classPK the class pk of this commerce tax category rel
	*/
	@Override
	public void setClassPK(long classPK) {
		_commerceTaxCategoryRel.setClassPK(classPK);
	}

	/**
	* Sets the commerce tax category ID of this commerce tax category rel.
	*
	* @param commerceTaxCategoryId the commerce tax category ID of this commerce tax category rel
	*/
	@Override
	public void setCommerceTaxCategoryId(long commerceTaxCategoryId) {
		_commerceTaxCategoryRel.setCommerceTaxCategoryId(commerceTaxCategoryId);
	}

	/**
	* Sets the commerce tax category rel ID of this commerce tax category rel.
	*
	* @param commerceTaxCategoryRelId the commerce tax category rel ID of this commerce tax category rel
	*/
	@Override
	public void setCommerceTaxCategoryRelId(long commerceTaxCategoryRelId) {
		_commerceTaxCategoryRel.setCommerceTaxCategoryRelId(commerceTaxCategoryRelId);
	}

	/**
	* Sets the company ID of this commerce tax category rel.
	*
	* @param companyId the company ID of this commerce tax category rel
	*/
	@Override
	public void setCompanyId(long companyId) {
		_commerceTaxCategoryRel.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this commerce tax category rel.
	*
	* @param createDate the create date of this commerce tax category rel
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_commerceTaxCategoryRel.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_commerceTaxCategoryRel.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceTaxCategoryRel.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceTaxCategoryRel.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this commerce tax category rel.
	*
	* @param groupId the group ID of this commerce tax category rel
	*/
	@Override
	public void setGroupId(long groupId) {
		_commerceTaxCategoryRel.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this commerce tax category rel.
	*
	* @param modifiedDate the modified date of this commerce tax category rel
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceTaxCategoryRel.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_commerceTaxCategoryRel.setNew(n);
	}

	/**
	* Sets the primary key of this commerce tax category rel.
	*
	* @param primaryKey the primary key of this commerce tax category rel
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceTaxCategoryRel.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceTaxCategoryRel.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this commerce tax category rel.
	*
	* @param userId the user ID of this commerce tax category rel
	*/
	@Override
	public void setUserId(long userId) {
		_commerceTaxCategoryRel.setUserId(userId);
	}

	/**
	* Sets the user name of this commerce tax category rel.
	*
	* @param userName the user name of this commerce tax category rel
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_commerceTaxCategoryRel.setUserName(userName);
	}

	/**
	* Sets the user uuid of this commerce tax category rel.
	*
	* @param userUuid the user uuid of this commerce tax category rel
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_commerceTaxCategoryRel.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceTaxCategoryRel> toCacheModel() {
		return _commerceTaxCategoryRel.toCacheModel();
	}

	@Override
	public CommerceTaxCategoryRel toEscapedModel() {
		return new CommerceTaxCategoryRelWrapper(_commerceTaxCategoryRel.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _commerceTaxCategoryRel.toString();
	}

	@Override
	public CommerceTaxCategoryRel toUnescapedModel() {
		return new CommerceTaxCategoryRelWrapper(_commerceTaxCategoryRel.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _commerceTaxCategoryRel.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceTaxCategoryRelWrapper)) {
			return false;
		}

		CommerceTaxCategoryRelWrapper commerceTaxCategoryRelWrapper = (CommerceTaxCategoryRelWrapper)obj;

		if (Objects.equals(_commerceTaxCategoryRel,
					commerceTaxCategoryRelWrapper._commerceTaxCategoryRel)) {
			return true;
		}

		return false;
	}

	@Override
	public CommerceTaxCategoryRel getWrappedModel() {
		return _commerceTaxCategoryRel;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceTaxCategoryRel.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceTaxCategoryRel.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceTaxCategoryRel.resetOriginalValues();
	}

	private final CommerceTaxCategoryRel _commerceTaxCategoryRel;
}