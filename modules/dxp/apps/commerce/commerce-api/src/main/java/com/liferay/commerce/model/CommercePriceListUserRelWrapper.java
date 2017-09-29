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
 * This class is a wrapper for {@link CommercePriceListUserRel}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListUserRel
 * @generated
 */
@ProviderType
public class CommercePriceListUserRelWrapper implements CommercePriceListUserRel,
	ModelWrapper<CommercePriceListUserRel> {
	public CommercePriceListUserRelWrapper(
		CommercePriceListUserRel commercePriceListUserRel) {
		_commercePriceListUserRel = commercePriceListUserRel;
	}

	@Override
	public Class<?> getModelClass() {
		return CommercePriceListUserRel.class;
	}

	@Override
	public String getModelClassName() {
		return CommercePriceListUserRel.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("commercePriceListUserRelId",
			getCommercePriceListUserRelId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commercePriceListId", getCommercePriceListId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("lastPublishDate", getLastPublishDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long commercePriceListUserRelId = (Long)attributes.get(
				"commercePriceListUserRelId");

		if (commercePriceListUserRelId != null) {
			setCommercePriceListUserRelId(commercePriceListUserRelId);
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

		Long commercePriceListId = (Long)attributes.get("commercePriceListId");

		if (commercePriceListId != null) {
			setCommercePriceListId(commercePriceListId);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new CommercePriceListUserRelWrapper((CommercePriceListUserRel)_commercePriceListUserRel.clone());
	}

	@Override
	public int compareTo(CommercePriceListUserRel commercePriceListUserRel) {
		return _commercePriceListUserRel.compareTo(commercePriceListUserRel);
	}

	/**
	* Returns the fully qualified class name of this commerce price list user rel.
	*
	* @return the fully qualified class name of this commerce price list user rel
	*/
	@Override
	public java.lang.String getClassName() {
		return _commercePriceListUserRel.getClassName();
	}

	/**
	* Returns the class name ID of this commerce price list user rel.
	*
	* @return the class name ID of this commerce price list user rel
	*/
	@Override
	public long getClassNameId() {
		return _commercePriceListUserRel.getClassNameId();
	}

	/**
	* Returns the class pk of this commerce price list user rel.
	*
	* @return the class pk of this commerce price list user rel
	*/
	@Override
	public long getClassPK() {
		return _commercePriceListUserRel.getClassPK();
	}

	/**
	* Returns the commerce price list ID of this commerce price list user rel.
	*
	* @return the commerce price list ID of this commerce price list user rel
	*/
	@Override
	public long getCommercePriceListId() {
		return _commercePriceListUserRel.getCommercePriceListId();
	}

	/**
	* Returns the commerce price list user rel ID of this commerce price list user rel.
	*
	* @return the commerce price list user rel ID of this commerce price list user rel
	*/
	@Override
	public long getCommercePriceListUserRelId() {
		return _commercePriceListUserRel.getCommercePriceListUserRelId();
	}

	/**
	* Returns the company ID of this commerce price list user rel.
	*
	* @return the company ID of this commerce price list user rel
	*/
	@Override
	public long getCompanyId() {
		return _commercePriceListUserRel.getCompanyId();
	}

	/**
	* Returns the create date of this commerce price list user rel.
	*
	* @return the create date of this commerce price list user rel
	*/
	@Override
	public Date getCreateDate() {
		return _commercePriceListUserRel.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commercePriceListUserRel.getExpandoBridge();
	}

	/**
	* Returns the group ID of this commerce price list user rel.
	*
	* @return the group ID of this commerce price list user rel
	*/
	@Override
	public long getGroupId() {
		return _commercePriceListUserRel.getGroupId();
	}

	/**
	* Returns the last publish date of this commerce price list user rel.
	*
	* @return the last publish date of this commerce price list user rel
	*/
	@Override
	public Date getLastPublishDate() {
		return _commercePriceListUserRel.getLastPublishDate();
	}

	/**
	* Returns the modified date of this commerce price list user rel.
	*
	* @return the modified date of this commerce price list user rel
	*/
	@Override
	public Date getModifiedDate() {
		return _commercePriceListUserRel.getModifiedDate();
	}

	/**
	* Returns the primary key of this commerce price list user rel.
	*
	* @return the primary key of this commerce price list user rel
	*/
	@Override
	public long getPrimaryKey() {
		return _commercePriceListUserRel.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commercePriceListUserRel.getPrimaryKeyObj();
	}

	/**
	* Returns the user ID of this commerce price list user rel.
	*
	* @return the user ID of this commerce price list user rel
	*/
	@Override
	public long getUserId() {
		return _commercePriceListUserRel.getUserId();
	}

	/**
	* Returns the user name of this commerce price list user rel.
	*
	* @return the user name of this commerce price list user rel
	*/
	@Override
	public java.lang.String getUserName() {
		return _commercePriceListUserRel.getUserName();
	}

	/**
	* Returns the user uuid of this commerce price list user rel.
	*
	* @return the user uuid of this commerce price list user rel
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _commercePriceListUserRel.getUserUuid();
	}

	/**
	* Returns the uuid of this commerce price list user rel.
	*
	* @return the uuid of this commerce price list user rel
	*/
	@Override
	public java.lang.String getUuid() {
		return _commercePriceListUserRel.getUuid();
	}

	@Override
	public int hashCode() {
		return _commercePriceListUserRel.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commercePriceListUserRel.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commercePriceListUserRel.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commercePriceListUserRel.isNew();
	}

	@Override
	public void persist() {
		_commercePriceListUserRel.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commercePriceListUserRel.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(java.lang.String className) {
		_commercePriceListUserRel.setClassName(className);
	}

	/**
	* Sets the class name ID of this commerce price list user rel.
	*
	* @param classNameId the class name ID of this commerce price list user rel
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_commercePriceListUserRel.setClassNameId(classNameId);
	}

	/**
	* Sets the class pk of this commerce price list user rel.
	*
	* @param classPK the class pk of this commerce price list user rel
	*/
	@Override
	public void setClassPK(long classPK) {
		_commercePriceListUserRel.setClassPK(classPK);
	}

	/**
	* Sets the commerce price list ID of this commerce price list user rel.
	*
	* @param commercePriceListId the commerce price list ID of this commerce price list user rel
	*/
	@Override
	public void setCommercePriceListId(long commercePriceListId) {
		_commercePriceListUserRel.setCommercePriceListId(commercePriceListId);
	}

	/**
	* Sets the commerce price list user rel ID of this commerce price list user rel.
	*
	* @param commercePriceListUserRelId the commerce price list user rel ID of this commerce price list user rel
	*/
	@Override
	public void setCommercePriceListUserRelId(long commercePriceListUserRelId) {
		_commercePriceListUserRel.setCommercePriceListUserRelId(commercePriceListUserRelId);
	}

	/**
	* Sets the company ID of this commerce price list user rel.
	*
	* @param companyId the company ID of this commerce price list user rel
	*/
	@Override
	public void setCompanyId(long companyId) {
		_commercePriceListUserRel.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this commerce price list user rel.
	*
	* @param createDate the create date of this commerce price list user rel
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_commercePriceListUserRel.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_commercePriceListUserRel.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commercePriceListUserRel.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commercePriceListUserRel.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this commerce price list user rel.
	*
	* @param groupId the group ID of this commerce price list user rel
	*/
	@Override
	public void setGroupId(long groupId) {
		_commercePriceListUserRel.setGroupId(groupId);
	}

	/**
	* Sets the last publish date of this commerce price list user rel.
	*
	* @param lastPublishDate the last publish date of this commerce price list user rel
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_commercePriceListUserRel.setLastPublishDate(lastPublishDate);
	}

	/**
	* Sets the modified date of this commerce price list user rel.
	*
	* @param modifiedDate the modified date of this commerce price list user rel
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commercePriceListUserRel.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_commercePriceListUserRel.setNew(n);
	}

	/**
	* Sets the primary key of this commerce price list user rel.
	*
	* @param primaryKey the primary key of this commerce price list user rel
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commercePriceListUserRel.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commercePriceListUserRel.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this commerce price list user rel.
	*
	* @param userId the user ID of this commerce price list user rel
	*/
	@Override
	public void setUserId(long userId) {
		_commercePriceListUserRel.setUserId(userId);
	}

	/**
	* Sets the user name of this commerce price list user rel.
	*
	* @param userName the user name of this commerce price list user rel
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_commercePriceListUserRel.setUserName(userName);
	}

	/**
	* Sets the user uuid of this commerce price list user rel.
	*
	* @param userUuid the user uuid of this commerce price list user rel
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_commercePriceListUserRel.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this commerce price list user rel.
	*
	* @param uuid the uuid of this commerce price list user rel
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_commercePriceListUserRel.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommercePriceListUserRel> toCacheModel() {
		return _commercePriceListUserRel.toCacheModel();
	}

	@Override
	public CommercePriceListUserRel toEscapedModel() {
		return new CommercePriceListUserRelWrapper(_commercePriceListUserRel.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _commercePriceListUserRel.toString();
	}

	@Override
	public CommercePriceListUserRel toUnescapedModel() {
		return new CommercePriceListUserRelWrapper(_commercePriceListUserRel.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _commercePriceListUserRel.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommercePriceListUserRelWrapper)) {
			return false;
		}

		CommercePriceListUserRelWrapper commercePriceListUserRelWrapper = (CommercePriceListUserRelWrapper)obj;

		if (Objects.equals(_commercePriceListUserRel,
					commercePriceListUserRelWrapper._commercePriceListUserRel)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _commercePriceListUserRel.getStagedModelType();
	}

	@Override
	public CommercePriceListUserRel getWrappedModel() {
		return _commercePriceListUserRel;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commercePriceListUserRel.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commercePriceListUserRel.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commercePriceListUserRel.resetOriginalValues();
	}

	private final CommercePriceListUserRel _commercePriceListUserRel;
}