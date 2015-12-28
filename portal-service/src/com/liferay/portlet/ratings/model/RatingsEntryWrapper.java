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

package com.liferay.portlet.ratings.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import com.liferay.portlet.exportimport.lar.StagedModelType;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link RatingsEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RatingsEntry
 * @generated
 */
@ProviderType
public class RatingsEntryWrapper implements RatingsEntry,
	ModelWrapper<RatingsEntry> {
	public RatingsEntryWrapper(RatingsEntry ratingsEntry) {
		_ratingsEntry = ratingsEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return RatingsEntry.class;
	}

	@Override
	public String getModelClassName() {
		return RatingsEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("entryId", getEntryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("score", getScore());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long entryId = (Long)attributes.get("entryId");

		if (entryId != null) {
			setEntryId(entryId);
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

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		Double score = (Double)attributes.get("score");

		if (score != null) {
			setScore(score);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new RatingsEntryWrapper((RatingsEntry)_ratingsEntry.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portlet.ratings.model.RatingsEntry ratingsEntry) {
		return _ratingsEntry.compareTo(ratingsEntry);
	}

	/**
	* Returns the fully qualified class name of this ratings entry.
	*
	* @return the fully qualified class name of this ratings entry
	*/
	@Override
	public java.lang.String getClassName() {
		return _ratingsEntry.getClassName();
	}

	/**
	* Returns the class name ID of this ratings entry.
	*
	* @return the class name ID of this ratings entry
	*/
	@Override
	public long getClassNameId() {
		return _ratingsEntry.getClassNameId();
	}

	/**
	* Returns the class p k of this ratings entry.
	*
	* @return the class p k of this ratings entry
	*/
	@Override
	public long getClassPK() {
		return _ratingsEntry.getClassPK();
	}

	/**
	* Returns the company ID of this ratings entry.
	*
	* @return the company ID of this ratings entry
	*/
	@Override
	public long getCompanyId() {
		return _ratingsEntry.getCompanyId();
	}

	/**
	* Returns the create date of this ratings entry.
	*
	* @return the create date of this ratings entry
	*/
	@Override
	public Date getCreateDate() {
		return _ratingsEntry.getCreateDate();
	}

	/**
	* Returns the entry ID of this ratings entry.
	*
	* @return the entry ID of this ratings entry
	*/
	@Override
	public long getEntryId() {
		return _ratingsEntry.getEntryId();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _ratingsEntry.getExpandoBridge();
	}

	/**
	* Returns the modified date of this ratings entry.
	*
	* @return the modified date of this ratings entry
	*/
	@Override
	public Date getModifiedDate() {
		return _ratingsEntry.getModifiedDate();
	}

	/**
	* Returns the primary key of this ratings entry.
	*
	* @return the primary key of this ratings entry
	*/
	@Override
	public long getPrimaryKey() {
		return _ratingsEntry.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _ratingsEntry.getPrimaryKeyObj();
	}

	/**
	* Returns the score of this ratings entry.
	*
	* @return the score of this ratings entry
	*/
	@Override
	public double getScore() {
		return _ratingsEntry.getScore();
	}

	/**
	* Returns the user ID of this ratings entry.
	*
	* @return the user ID of this ratings entry
	*/
	@Override
	public long getUserId() {
		return _ratingsEntry.getUserId();
	}

	/**
	* Returns the user name of this ratings entry.
	*
	* @return the user name of this ratings entry
	*/
	@Override
	public java.lang.String getUserName() {
		return _ratingsEntry.getUserName();
	}

	/**
	* Returns the user uuid of this ratings entry.
	*
	* @return the user uuid of this ratings entry
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _ratingsEntry.getUserUuid();
	}

	/**
	* Returns the uuid of this ratings entry.
	*
	* @return the uuid of this ratings entry
	*/
	@Override
	public java.lang.String getUuid() {
		return _ratingsEntry.getUuid();
	}

	@Override
	public int hashCode() {
		return _ratingsEntry.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _ratingsEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _ratingsEntry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _ratingsEntry.isNew();
	}

	@Override
	public void persist() {
		_ratingsEntry.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_ratingsEntry.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(java.lang.String className) {
		_ratingsEntry.setClassName(className);
	}

	/**
	* Sets the class name ID of this ratings entry.
	*
	* @param classNameId the class name ID of this ratings entry
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_ratingsEntry.setClassNameId(classNameId);
	}

	/**
	* Sets the class p k of this ratings entry.
	*
	* @param classPK the class p k of this ratings entry
	*/
	@Override
	public void setClassPK(long classPK) {
		_ratingsEntry.setClassPK(classPK);
	}

	/**
	* Sets the company ID of this ratings entry.
	*
	* @param companyId the company ID of this ratings entry
	*/
	@Override
	public void setCompanyId(long companyId) {
		_ratingsEntry.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this ratings entry.
	*
	* @param createDate the create date of this ratings entry
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_ratingsEntry.setCreateDate(createDate);
	}

	/**
	* Sets the entry ID of this ratings entry.
	*
	* @param entryId the entry ID of this ratings entry
	*/
	@Override
	public void setEntryId(long entryId) {
		_ratingsEntry.setEntryId(entryId);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_ratingsEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_ratingsEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_ratingsEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the modified date of this ratings entry.
	*
	* @param modifiedDate the modified date of this ratings entry
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_ratingsEntry.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_ratingsEntry.setNew(n);
	}

	/**
	* Sets the primary key of this ratings entry.
	*
	* @param primaryKey the primary key of this ratings entry
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_ratingsEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_ratingsEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the score of this ratings entry.
	*
	* @param score the score of this ratings entry
	*/
	@Override
	public void setScore(double score) {
		_ratingsEntry.setScore(score);
	}

	/**
	* Sets the user ID of this ratings entry.
	*
	* @param userId the user ID of this ratings entry
	*/
	@Override
	public void setUserId(long userId) {
		_ratingsEntry.setUserId(userId);
	}

	/**
	* Sets the user name of this ratings entry.
	*
	* @param userName the user name of this ratings entry
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_ratingsEntry.setUserName(userName);
	}

	/**
	* Sets the user uuid of this ratings entry.
	*
	* @param userUuid the user uuid of this ratings entry
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_ratingsEntry.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this ratings entry.
	*
	* @param uuid the uuid of this ratings entry
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_ratingsEntry.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.ratings.model.RatingsEntry> toCacheModel() {
		return _ratingsEntry.toCacheModel();
	}

	@Override
	public com.liferay.portlet.ratings.model.RatingsEntry toEscapedModel() {
		return new RatingsEntryWrapper(_ratingsEntry.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _ratingsEntry.toString();
	}

	@Override
	public com.liferay.portlet.ratings.model.RatingsEntry toUnescapedModel() {
		return new RatingsEntryWrapper(_ratingsEntry.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _ratingsEntry.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof RatingsEntryWrapper)) {
			return false;
		}

		RatingsEntryWrapper ratingsEntryWrapper = (RatingsEntryWrapper)obj;

		if (Validator.equals(_ratingsEntry, ratingsEntryWrapper._ratingsEntry)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _ratingsEntry.getStagedModelType();
	}

	@Override
	public RatingsEntry getWrappedModel() {
		return _ratingsEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _ratingsEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _ratingsEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_ratingsEntry.resetOriginalValues();
	}

	private final RatingsEntry _ratingsEntry;
}