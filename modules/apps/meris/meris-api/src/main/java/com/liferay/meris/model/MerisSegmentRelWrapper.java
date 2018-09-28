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

package com.liferay.meris.model;

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
 * This class is a wrapper for {@link MerisSegmentRel}.
 * </p>
 *
 * @author Eduardo Garcia
 * @see MerisSegmentRel
 * @generated
 */
@ProviderType
public class MerisSegmentRelWrapper implements MerisSegmentRel,
	ModelWrapper<MerisSegmentRel> {
	public MerisSegmentRelWrapper(MerisSegmentRel merisSegmentRel) {
		_merisSegmentRel = merisSegmentRel;
	}

	@Override
	public Class<?> getModelClass() {
		return MerisSegmentRel.class;
	}

	@Override
	public String getModelClassName() {
		return MerisSegmentRel.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("merisSegmentRelId", getMerisSegmentRelId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("merisSegmentId", getMerisSegmentId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long merisSegmentRelId = (Long)attributes.get("merisSegmentRelId");

		if (merisSegmentRelId != null) {
			setMerisSegmentRelId(merisSegmentRelId);
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

		Long merisSegmentId = (Long)attributes.get("merisSegmentId");

		if (merisSegmentId != null) {
			setMerisSegmentId(merisSegmentId);
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
		return new MerisSegmentRelWrapper((MerisSegmentRel)_merisSegmentRel.clone());
	}

	@Override
	public int compareTo(MerisSegmentRel merisSegmentRel) {
		return _merisSegmentRel.compareTo(merisSegmentRel);
	}

	/**
	* Returns the fully qualified class name of this meris segment rel.
	*
	* @return the fully qualified class name of this meris segment rel
	*/
	@Override
	public String getClassName() {
		return _merisSegmentRel.getClassName();
	}

	/**
	* Returns the class name ID of this meris segment rel.
	*
	* @return the class name ID of this meris segment rel
	*/
	@Override
	public long getClassNameId() {
		return _merisSegmentRel.getClassNameId();
	}

	/**
	* Returns the class pk of this meris segment rel.
	*
	* @return the class pk of this meris segment rel
	*/
	@Override
	public long getClassPK() {
		return _merisSegmentRel.getClassPK();
	}

	/**
	* Returns the company ID of this meris segment rel.
	*
	* @return the company ID of this meris segment rel
	*/
	@Override
	public long getCompanyId() {
		return _merisSegmentRel.getCompanyId();
	}

	/**
	* Returns the create date of this meris segment rel.
	*
	* @return the create date of this meris segment rel
	*/
	@Override
	public Date getCreateDate() {
		return _merisSegmentRel.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _merisSegmentRel.getExpandoBridge();
	}

	/**
	* Returns the group ID of this meris segment rel.
	*
	* @return the group ID of this meris segment rel
	*/
	@Override
	public long getGroupId() {
		return _merisSegmentRel.getGroupId();
	}

	/**
	* Returns the meris segment ID of this meris segment rel.
	*
	* @return the meris segment ID of this meris segment rel
	*/
	@Override
	public long getMerisSegmentId() {
		return _merisSegmentRel.getMerisSegmentId();
	}

	/**
	* Returns the meris segment rel ID of this meris segment rel.
	*
	* @return the meris segment rel ID of this meris segment rel
	*/
	@Override
	public long getMerisSegmentRelId() {
		return _merisSegmentRel.getMerisSegmentRelId();
	}

	/**
	* Returns the modified date of this meris segment rel.
	*
	* @return the modified date of this meris segment rel
	*/
	@Override
	public Date getModifiedDate() {
		return _merisSegmentRel.getModifiedDate();
	}

	/**
	* Returns the primary key of this meris segment rel.
	*
	* @return the primary key of this meris segment rel
	*/
	@Override
	public long getPrimaryKey() {
		return _merisSegmentRel.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _merisSegmentRel.getPrimaryKeyObj();
	}

	/**
	* Returns the user ID of this meris segment rel.
	*
	* @return the user ID of this meris segment rel
	*/
	@Override
	public long getUserId() {
		return _merisSegmentRel.getUserId();
	}

	/**
	* Returns the user name of this meris segment rel.
	*
	* @return the user name of this meris segment rel
	*/
	@Override
	public String getUserName() {
		return _merisSegmentRel.getUserName();
	}

	/**
	* Returns the user uuid of this meris segment rel.
	*
	* @return the user uuid of this meris segment rel
	*/
	@Override
	public String getUserUuid() {
		return _merisSegmentRel.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _merisSegmentRel.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _merisSegmentRel.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _merisSegmentRel.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _merisSegmentRel.isNew();
	}

	@Override
	public void persist() {
		_merisSegmentRel.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_merisSegmentRel.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(String className) {
		_merisSegmentRel.setClassName(className);
	}

	/**
	* Sets the class name ID of this meris segment rel.
	*
	* @param classNameId the class name ID of this meris segment rel
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_merisSegmentRel.setClassNameId(classNameId);
	}

	/**
	* Sets the class pk of this meris segment rel.
	*
	* @param classPK the class pk of this meris segment rel
	*/
	@Override
	public void setClassPK(long classPK) {
		_merisSegmentRel.setClassPK(classPK);
	}

	/**
	* Sets the company ID of this meris segment rel.
	*
	* @param companyId the company ID of this meris segment rel
	*/
	@Override
	public void setCompanyId(long companyId) {
		_merisSegmentRel.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this meris segment rel.
	*
	* @param createDate the create date of this meris segment rel
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_merisSegmentRel.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_merisSegmentRel.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_merisSegmentRel.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_merisSegmentRel.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this meris segment rel.
	*
	* @param groupId the group ID of this meris segment rel
	*/
	@Override
	public void setGroupId(long groupId) {
		_merisSegmentRel.setGroupId(groupId);
	}

	/**
	* Sets the meris segment ID of this meris segment rel.
	*
	* @param merisSegmentId the meris segment ID of this meris segment rel
	*/
	@Override
	public void setMerisSegmentId(long merisSegmentId) {
		_merisSegmentRel.setMerisSegmentId(merisSegmentId);
	}

	/**
	* Sets the meris segment rel ID of this meris segment rel.
	*
	* @param merisSegmentRelId the meris segment rel ID of this meris segment rel
	*/
	@Override
	public void setMerisSegmentRelId(long merisSegmentRelId) {
		_merisSegmentRel.setMerisSegmentRelId(merisSegmentRelId);
	}

	/**
	* Sets the modified date of this meris segment rel.
	*
	* @param modifiedDate the modified date of this meris segment rel
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_merisSegmentRel.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_merisSegmentRel.setNew(n);
	}

	/**
	* Sets the primary key of this meris segment rel.
	*
	* @param primaryKey the primary key of this meris segment rel
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_merisSegmentRel.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_merisSegmentRel.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this meris segment rel.
	*
	* @param userId the user ID of this meris segment rel
	*/
	@Override
	public void setUserId(long userId) {
		_merisSegmentRel.setUserId(userId);
	}

	/**
	* Sets the user name of this meris segment rel.
	*
	* @param userName the user name of this meris segment rel
	*/
	@Override
	public void setUserName(String userName) {
		_merisSegmentRel.setUserName(userName);
	}

	/**
	* Sets the user uuid of this meris segment rel.
	*
	* @param userUuid the user uuid of this meris segment rel
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_merisSegmentRel.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<MerisSegmentRel> toCacheModel() {
		return _merisSegmentRel.toCacheModel();
	}

	@Override
	public MerisSegmentRel toEscapedModel() {
		return new MerisSegmentRelWrapper(_merisSegmentRel.toEscapedModel());
	}

	@Override
	public String toString() {
		return _merisSegmentRel.toString();
	}

	@Override
	public MerisSegmentRel toUnescapedModel() {
		return new MerisSegmentRelWrapper(_merisSegmentRel.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _merisSegmentRel.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof MerisSegmentRelWrapper)) {
			return false;
		}

		MerisSegmentRelWrapper merisSegmentRelWrapper = (MerisSegmentRelWrapper)obj;

		if (Objects.equals(_merisSegmentRel,
					merisSegmentRelWrapper._merisSegmentRel)) {
			return true;
		}

		return false;
	}

	@Override
	public MerisSegmentRel getWrappedModel() {
		return _merisSegmentRel;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _merisSegmentRel.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _merisSegmentRel.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_merisSegmentRel.resetOriginalValues();
	}

	private final MerisSegmentRel _merisSegmentRel;
}