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
 * This class is a wrapper for {@link MBBan}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBBan
 * @generated
 */
public class MBBanWrapper implements MBBan, ModelWrapper<MBBan> {
	public MBBanWrapper(MBBan mbBan) {
		_mbBan = mbBan;
	}

	public Class<?> getModelClass() {
		return MBBan.class;
	}

	public String getModelClassName() {
		return MBBan.class.getName();
	}

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

		return attributes;
	}

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
	}

	/**
	* Returns the primary key of this message boards ban.
	*
	* @return the primary key of this message boards ban
	*/
	public long getPrimaryKey() {
		return _mbBan.getPrimaryKey();
	}

	/**
	* Sets the primary key of this message boards ban.
	*
	* @param primaryKey the primary key of this message boards ban
	*/
	public void setPrimaryKey(long primaryKey) {
		_mbBan.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this message boards ban.
	*
	* @return the uuid of this message boards ban
	*/
	public java.lang.String getUuid() {
		return _mbBan.getUuid();
	}

	/**
	* Sets the uuid of this message boards ban.
	*
	* @param uuid the uuid of this message boards ban
	*/
	public void setUuid(java.lang.String uuid) {
		_mbBan.setUuid(uuid);
	}

	/**
	* Returns the ban ID of this message boards ban.
	*
	* @return the ban ID of this message boards ban
	*/
	public long getBanId() {
		return _mbBan.getBanId();
	}

	/**
	* Sets the ban ID of this message boards ban.
	*
	* @param banId the ban ID of this message boards ban
	*/
	public void setBanId(long banId) {
		_mbBan.setBanId(banId);
	}

	/**
	* Returns the group ID of this message boards ban.
	*
	* @return the group ID of this message boards ban
	*/
	public long getGroupId() {
		return _mbBan.getGroupId();
	}

	/**
	* Sets the group ID of this message boards ban.
	*
	* @param groupId the group ID of this message boards ban
	*/
	public void setGroupId(long groupId) {
		_mbBan.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this message boards ban.
	*
	* @return the company ID of this message boards ban
	*/
	public long getCompanyId() {
		return _mbBan.getCompanyId();
	}

	/**
	* Sets the company ID of this message boards ban.
	*
	* @param companyId the company ID of this message boards ban
	*/
	public void setCompanyId(long companyId) {
		_mbBan.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this message boards ban.
	*
	* @return the user ID of this message boards ban
	*/
	public long getUserId() {
		return _mbBan.getUserId();
	}

	/**
	* Sets the user ID of this message boards ban.
	*
	* @param userId the user ID of this message boards ban
	*/
	public void setUserId(long userId) {
		_mbBan.setUserId(userId);
	}

	/**
	* Returns the user uuid of this message boards ban.
	*
	* @return the user uuid of this message boards ban
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbBan.getUserUuid();
	}

	/**
	* Sets the user uuid of this message boards ban.
	*
	* @param userUuid the user uuid of this message boards ban
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_mbBan.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this message boards ban.
	*
	* @return the user name of this message boards ban
	*/
	public java.lang.String getUserName() {
		return _mbBan.getUserName();
	}

	/**
	* Sets the user name of this message boards ban.
	*
	* @param userName the user name of this message boards ban
	*/
	public void setUserName(java.lang.String userName) {
		_mbBan.setUserName(userName);
	}

	/**
	* Returns the create date of this message boards ban.
	*
	* @return the create date of this message boards ban
	*/
	public java.util.Date getCreateDate() {
		return _mbBan.getCreateDate();
	}

	/**
	* Sets the create date of this message boards ban.
	*
	* @param createDate the create date of this message boards ban
	*/
	public void setCreateDate(java.util.Date createDate) {
		_mbBan.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this message boards ban.
	*
	* @return the modified date of this message boards ban
	*/
	public java.util.Date getModifiedDate() {
		return _mbBan.getModifiedDate();
	}

	/**
	* Sets the modified date of this message boards ban.
	*
	* @param modifiedDate the modified date of this message boards ban
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_mbBan.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the ban user ID of this message boards ban.
	*
	* @return the ban user ID of this message boards ban
	*/
	public long getBanUserId() {
		return _mbBan.getBanUserId();
	}

	/**
	* Sets the ban user ID of this message boards ban.
	*
	* @param banUserId the ban user ID of this message boards ban
	*/
	public void setBanUserId(long banUserId) {
		_mbBan.setBanUserId(banUserId);
	}

	/**
	* Returns the ban user uuid of this message boards ban.
	*
	* @return the ban user uuid of this message boards ban
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getBanUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbBan.getBanUserUuid();
	}

	/**
	* Sets the ban user uuid of this message boards ban.
	*
	* @param banUserUuid the ban user uuid of this message boards ban
	*/
	public void setBanUserUuid(java.lang.String banUserUuid) {
		_mbBan.setBanUserUuid(banUserUuid);
	}

	public boolean isNew() {
		return _mbBan.isNew();
	}

	public void setNew(boolean n) {
		_mbBan.setNew(n);
	}

	public boolean isCachedModel() {
		return _mbBan.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_mbBan.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _mbBan.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _mbBan.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_mbBan.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _mbBan.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_mbBan.setExpandoBridgeAttributes(baseModel);
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_mbBan.setExpandoBridgeAttributes(expandoBridge);
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_mbBan.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new MBBanWrapper((MBBan)_mbBan.clone());
	}

	public int compareTo(com.liferay.portlet.messageboards.model.MBBan mbBan) {
		return _mbBan.compareTo(mbBan);
	}

	@Override
	public int hashCode() {
		return _mbBan.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portlet.messageboards.model.MBBan> toCacheModel() {
		return _mbBan.toCacheModel();
	}

	public com.liferay.portlet.messageboards.model.MBBan toEscapedModel() {
		return new MBBanWrapper(_mbBan.toEscapedModel());
	}

	public com.liferay.portlet.messageboards.model.MBBan toUnescapedModel() {
		return new MBBanWrapper(_mbBan.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _mbBan.toString();
	}

	public java.lang.String toXmlString() {
		return _mbBan.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_mbBan.persist();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public MBBan getWrappedMBBan() {
		return _mbBan;
	}

	public MBBan getWrappedModel() {
		return _mbBan;
	}

	public void resetOriginalValues() {
		_mbBan.resetOriginalValues();
	}

	private MBBan _mbBan;
}