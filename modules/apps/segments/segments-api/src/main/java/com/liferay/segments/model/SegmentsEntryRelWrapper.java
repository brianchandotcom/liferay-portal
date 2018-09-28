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

package com.liferay.segments.model;

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
 * This class is a wrapper for {@link SegmentsEntryRel}.
 * </p>
 *
 * @author Eduardo Garcia
 * @see SegmentsEntryRel
 * @generated
 */
@ProviderType
public class SegmentsEntryRelWrapper implements SegmentsEntryRel,
	ModelWrapper<SegmentsEntryRel> {
	public SegmentsEntryRelWrapper(SegmentsEntryRel segmentsEntryRel) {
		_segmentsEntryRel = segmentsEntryRel;
	}

	@Override
	public Class<?> getModelClass() {
		return SegmentsEntryRel.class;
	}

	@Override
	public String getModelClassName() {
		return SegmentsEntryRel.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("segmentsEntryRelId", getSegmentsEntryRelId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("segmentsEntryId", getSegmentsEntryId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long segmentsEntryRelId = (Long)attributes.get("segmentsEntryRelId");

		if (segmentsEntryRelId != null) {
			setSegmentsEntryRelId(segmentsEntryRelId);
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

		Long segmentsEntryId = (Long)attributes.get("segmentsEntryId");

		if (segmentsEntryId != null) {
			setSegmentsEntryId(segmentsEntryId);
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
		return new SegmentsEntryRelWrapper((SegmentsEntryRel)_segmentsEntryRel.clone());
	}

	@Override
	public int compareTo(SegmentsEntryRel segmentsEntryRel) {
		return _segmentsEntryRel.compareTo(segmentsEntryRel);
	}

	/**
	* Returns the fully qualified class name of this segments entry rel.
	*
	* @return the fully qualified class name of this segments entry rel
	*/
	@Override
	public String getClassName() {
		return _segmentsEntryRel.getClassName();
	}

	/**
	* Returns the class name ID of this segments entry rel.
	*
	* @return the class name ID of this segments entry rel
	*/
	@Override
	public long getClassNameId() {
		return _segmentsEntryRel.getClassNameId();
	}

	/**
	* Returns the class pk of this segments entry rel.
	*
	* @return the class pk of this segments entry rel
	*/
	@Override
	public long getClassPK() {
		return _segmentsEntryRel.getClassPK();
	}

	/**
	* Returns the company ID of this segments entry rel.
	*
	* @return the company ID of this segments entry rel
	*/
	@Override
	public long getCompanyId() {
		return _segmentsEntryRel.getCompanyId();
	}

	/**
	* Returns the create date of this segments entry rel.
	*
	* @return the create date of this segments entry rel
	*/
	@Override
	public Date getCreateDate() {
		return _segmentsEntryRel.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _segmentsEntryRel.getExpandoBridge();
	}

	/**
	* Returns the group ID of this segments entry rel.
	*
	* @return the group ID of this segments entry rel
	*/
	@Override
	public long getGroupId() {
		return _segmentsEntryRel.getGroupId();
	}

	/**
	* Returns the modified date of this segments entry rel.
	*
	* @return the modified date of this segments entry rel
	*/
	@Override
	public Date getModifiedDate() {
		return _segmentsEntryRel.getModifiedDate();
	}

	/**
	* Returns the primary key of this segments entry rel.
	*
	* @return the primary key of this segments entry rel
	*/
	@Override
	public long getPrimaryKey() {
		return _segmentsEntryRel.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _segmentsEntryRel.getPrimaryKeyObj();
	}

	/**
	* Returns the segments entry ID of this segments entry rel.
	*
	* @return the segments entry ID of this segments entry rel
	*/
	@Override
	public long getSegmentsEntryId() {
		return _segmentsEntryRel.getSegmentsEntryId();
	}

	/**
	* Returns the segments entry rel ID of this segments entry rel.
	*
	* @return the segments entry rel ID of this segments entry rel
	*/
	@Override
	public long getSegmentsEntryRelId() {
		return _segmentsEntryRel.getSegmentsEntryRelId();
	}

	/**
	* Returns the user ID of this segments entry rel.
	*
	* @return the user ID of this segments entry rel
	*/
	@Override
	public long getUserId() {
		return _segmentsEntryRel.getUserId();
	}

	/**
	* Returns the user name of this segments entry rel.
	*
	* @return the user name of this segments entry rel
	*/
	@Override
	public String getUserName() {
		return _segmentsEntryRel.getUserName();
	}

	/**
	* Returns the user uuid of this segments entry rel.
	*
	* @return the user uuid of this segments entry rel
	*/
	@Override
	public String getUserUuid() {
		return _segmentsEntryRel.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _segmentsEntryRel.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _segmentsEntryRel.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _segmentsEntryRel.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _segmentsEntryRel.isNew();
	}

	@Override
	public void persist() {
		_segmentsEntryRel.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_segmentsEntryRel.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(String className) {
		_segmentsEntryRel.setClassName(className);
	}

	/**
	* Sets the class name ID of this segments entry rel.
	*
	* @param classNameId the class name ID of this segments entry rel
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_segmentsEntryRel.setClassNameId(classNameId);
	}

	/**
	* Sets the class pk of this segments entry rel.
	*
	* @param classPK the class pk of this segments entry rel
	*/
	@Override
	public void setClassPK(long classPK) {
		_segmentsEntryRel.setClassPK(classPK);
	}

	/**
	* Sets the company ID of this segments entry rel.
	*
	* @param companyId the company ID of this segments entry rel
	*/
	@Override
	public void setCompanyId(long companyId) {
		_segmentsEntryRel.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this segments entry rel.
	*
	* @param createDate the create date of this segments entry rel
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_segmentsEntryRel.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_segmentsEntryRel.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_segmentsEntryRel.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_segmentsEntryRel.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this segments entry rel.
	*
	* @param groupId the group ID of this segments entry rel
	*/
	@Override
	public void setGroupId(long groupId) {
		_segmentsEntryRel.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this segments entry rel.
	*
	* @param modifiedDate the modified date of this segments entry rel
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_segmentsEntryRel.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_segmentsEntryRel.setNew(n);
	}

	/**
	* Sets the primary key of this segments entry rel.
	*
	* @param primaryKey the primary key of this segments entry rel
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_segmentsEntryRel.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_segmentsEntryRel.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the segments entry ID of this segments entry rel.
	*
	* @param segmentsEntryId the segments entry ID of this segments entry rel
	*/
	@Override
	public void setSegmentsEntryId(long segmentsEntryId) {
		_segmentsEntryRel.setSegmentsEntryId(segmentsEntryId);
	}

	/**
	* Sets the segments entry rel ID of this segments entry rel.
	*
	* @param segmentsEntryRelId the segments entry rel ID of this segments entry rel
	*/
	@Override
	public void setSegmentsEntryRelId(long segmentsEntryRelId) {
		_segmentsEntryRel.setSegmentsEntryRelId(segmentsEntryRelId);
	}

	/**
	* Sets the user ID of this segments entry rel.
	*
	* @param userId the user ID of this segments entry rel
	*/
	@Override
	public void setUserId(long userId) {
		_segmentsEntryRel.setUserId(userId);
	}

	/**
	* Sets the user name of this segments entry rel.
	*
	* @param userName the user name of this segments entry rel
	*/
	@Override
	public void setUserName(String userName) {
		_segmentsEntryRel.setUserName(userName);
	}

	/**
	* Sets the user uuid of this segments entry rel.
	*
	* @param userUuid the user uuid of this segments entry rel
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_segmentsEntryRel.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<SegmentsEntryRel> toCacheModel() {
		return _segmentsEntryRel.toCacheModel();
	}

	@Override
	public SegmentsEntryRel toEscapedModel() {
		return new SegmentsEntryRelWrapper(_segmentsEntryRel.toEscapedModel());
	}

	@Override
	public String toString() {
		return _segmentsEntryRel.toString();
	}

	@Override
	public SegmentsEntryRel toUnescapedModel() {
		return new SegmentsEntryRelWrapper(_segmentsEntryRel.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _segmentsEntryRel.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SegmentsEntryRelWrapper)) {
			return false;
		}

		SegmentsEntryRelWrapper segmentsEntryRelWrapper = (SegmentsEntryRelWrapper)obj;

		if (Objects.equals(_segmentsEntryRel,
					segmentsEntryRelWrapper._segmentsEntryRel)) {
			return true;
		}

		return false;
	}

	@Override
	public SegmentsEntryRel getWrappedModel() {
		return _segmentsEntryRel;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _segmentsEntryRel.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _segmentsEntryRel.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_segmentsEntryRel.resetOriginalValues();
	}

	private final SegmentsEntryRel _segmentsEntryRel;
}