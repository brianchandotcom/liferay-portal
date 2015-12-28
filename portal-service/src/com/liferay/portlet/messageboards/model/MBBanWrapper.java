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

package com.liferay.portlet.messageboards.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import com.liferay.portlet.exportimport.lar.StagedModelType;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link MBBan}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MBBan
 * @generated
 */
@ProviderType
public class MBBanWrapper implements MBBan, ModelWrapper<MBBan> {
	public MBBanWrapper(MBBan mbBan) {
		_mbBan = mbBan;
	}

	@Override
	public Class<?> getModelClass() {
		return MBBan.class;
	}

	@Override
	public String getModelClassName() {
		return MBBan.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("banId", getBanId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("banUserId", getBanUserId());
		attributes.put("lastPublishDate", getLastPublishDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long banId = (Long)attributes.get("banId");

		if (banId != null) {
			setBanId(banId);
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

		Long banUserId = (Long)attributes.get("banUserId");

		if (banUserId != null) {
			setBanUserId(banUserId);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new MBBanWrapper((MBBan)_mbBan.clone());
	}

	@Override
	public int compareTo(com.liferay.portlet.messageboards.model.MBBan mbBan) {
		return _mbBan.compareTo(mbBan);
	}

	/**
	* Returns the ban ID of this message boards ban.
	*
	* @return the ban ID of this message boards ban
	*/
	@Override
	public long getBanId() {
		return _mbBan.getBanId();
	}

	/**
	* Returns the ban user ID of this message boards ban.
	*
	* @return the ban user ID of this message boards ban
	*/
	@Override
	public long getBanUserId() {
		return _mbBan.getBanUserId();
	}

	/**
	* Returns the ban user uuid of this message boards ban.
	*
	* @return the ban user uuid of this message boards ban
	*/
	@Override
	public java.lang.String getBanUserUuid() {
		return _mbBan.getBanUserUuid();
	}

	/**
	* Returns the company ID of this message boards ban.
	*
	* @return the company ID of this message boards ban
	*/
	@Override
	public long getCompanyId() {
		return _mbBan.getCompanyId();
	}

	/**
	* Returns the create date of this message boards ban.
	*
	* @return the create date of this message boards ban
	*/
	@Override
	public Date getCreateDate() {
		return _mbBan.getCreateDate();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _mbBan.getExpandoBridge();
	}

	/**
	* Returns the group ID of this message boards ban.
	*
	* @return the group ID of this message boards ban
	*/
	@Override
	public long getGroupId() {
		return _mbBan.getGroupId();
	}

	/**
	* Returns the last publish date of this message boards ban.
	*
	* @return the last publish date of this message boards ban
	*/
	@Override
	public Date getLastPublishDate() {
		return _mbBan.getLastPublishDate();
	}

	/**
	* Returns the modified date of this message boards ban.
	*
	* @return the modified date of this message boards ban
	*/
	@Override
	public Date getModifiedDate() {
		return _mbBan.getModifiedDate();
	}

	/**
	* Returns the primary key of this message boards ban.
	*
	* @return the primary key of this message boards ban
	*/
	@Override
	public long getPrimaryKey() {
		return _mbBan.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _mbBan.getPrimaryKeyObj();
	}

	/**
	* Returns the user ID of this message boards ban.
	*
	* @return the user ID of this message boards ban
	*/
	@Override
	public long getUserId() {
		return _mbBan.getUserId();
	}

	/**
	* Returns the user name of this message boards ban.
	*
	* @return the user name of this message boards ban
	*/
	@Override
	public java.lang.String getUserName() {
		return _mbBan.getUserName();
	}

	/**
	* Returns the user uuid of this message boards ban.
	*
	* @return the user uuid of this message boards ban
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _mbBan.getUserUuid();
	}

	/**
	* Returns the uuid of this message boards ban.
	*
	* @return the uuid of this message boards ban
	*/
	@Override
	public java.lang.String getUuid() {
		return _mbBan.getUuid();
	}

	@Override
	public int hashCode() {
		return _mbBan.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _mbBan.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _mbBan.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _mbBan.isNew();
	}

	@Override
	public void persist() {
		_mbBan.persist();
	}

	/**
	* Sets the ban ID of this message boards ban.
	*
	* @param banId the ban ID of this message boards ban
	*/
	@Override
	public void setBanId(long banId) {
		_mbBan.setBanId(banId);
	}

	/**
	* Sets the ban user ID of this message boards ban.
	*
	* @param banUserId the ban user ID of this message boards ban
	*/
	@Override
	public void setBanUserId(long banUserId) {
		_mbBan.setBanUserId(banUserId);
	}

	/**
	* Sets the ban user uuid of this message boards ban.
	*
	* @param banUserUuid the ban user uuid of this message boards ban
	*/
	@Override
	public void setBanUserUuid(java.lang.String banUserUuid) {
		_mbBan.setBanUserUuid(banUserUuid);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_mbBan.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this message boards ban.
	*
	* @param companyId the company ID of this message boards ban
	*/
	@Override
	public void setCompanyId(long companyId) {
		_mbBan.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this message boards ban.
	*
	* @param createDate the create date of this message boards ban
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_mbBan.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_mbBan.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_mbBan.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_mbBan.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this message boards ban.
	*
	* @param groupId the group ID of this message boards ban
	*/
	@Override
	public void setGroupId(long groupId) {
		_mbBan.setGroupId(groupId);
	}

	/**
	* Sets the last publish date of this message boards ban.
	*
	* @param lastPublishDate the last publish date of this message boards ban
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_mbBan.setLastPublishDate(lastPublishDate);
	}

	/**
	* Sets the modified date of this message boards ban.
	*
	* @param modifiedDate the modified date of this message boards ban
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_mbBan.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_mbBan.setNew(n);
	}

	/**
	* Sets the primary key of this message boards ban.
	*
	* @param primaryKey the primary key of this message boards ban
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_mbBan.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_mbBan.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this message boards ban.
	*
	* @param userId the user ID of this message boards ban
	*/
	@Override
	public void setUserId(long userId) {
		_mbBan.setUserId(userId);
	}

	/**
	* Sets the user name of this message boards ban.
	*
	* @param userName the user name of this message boards ban
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_mbBan.setUserName(userName);
	}

	/**
	* Sets the user uuid of this message boards ban.
	*
	* @param userUuid the user uuid of this message boards ban
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_mbBan.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this message boards ban.
	*
	* @param uuid the uuid of this message boards ban
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_mbBan.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.messageboards.model.MBBan> toCacheModel() {
		return _mbBan.toCacheModel();
	}

	@Override
	public com.liferay.portlet.messageboards.model.MBBan toEscapedModel() {
		return new MBBanWrapper(_mbBan.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _mbBan.toString();
	}

	@Override
	public com.liferay.portlet.messageboards.model.MBBan toUnescapedModel() {
		return new MBBanWrapper(_mbBan.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _mbBan.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof MBBanWrapper)) {
			return false;
		}

		MBBanWrapper mbBanWrapper = (MBBanWrapper)obj;

		if (Validator.equals(_mbBan, mbBanWrapper._mbBan)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _mbBan.getStagedModelType();
	}

	@Override
	public MBBan getWrappedModel() {
		return _mbBan;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _mbBan.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _mbBan.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_mbBan.resetOriginalValues();
	}

	private final MBBan _mbBan;
}