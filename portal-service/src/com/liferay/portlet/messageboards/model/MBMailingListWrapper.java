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
 * This class is a wrapper for {@link MBMailingList}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MBMailingList
 * @generated
 */
@ProviderType
public class MBMailingListWrapper implements MBMailingList,
	ModelWrapper<MBMailingList> {
	public MBMailingListWrapper(MBMailingList mbMailingList) {
		_mbMailingList = mbMailingList;
	}

	@Override
	public Class<?> getModelClass() {
		return MBMailingList.class;
	}

	@Override
	public String getModelClassName() {
		return MBMailingList.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("mailingListId", getMailingListId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("categoryId", getCategoryId());
		attributes.put("emailAddress", getEmailAddress());
		attributes.put("inProtocol", getInProtocol());
		attributes.put("inServerName", getInServerName());
		attributes.put("inServerPort", getInServerPort());
		attributes.put("inUseSSL", getInUseSSL());
		attributes.put("inUserName", getInUserName());
		attributes.put("inPassword", getInPassword());
		attributes.put("inReadInterval", getInReadInterval());
		attributes.put("outEmailAddress", getOutEmailAddress());
		attributes.put("outCustom", getOutCustom());
		attributes.put("outServerName", getOutServerName());
		attributes.put("outServerPort", getOutServerPort());
		attributes.put("outUseSSL", getOutUseSSL());
		attributes.put("outUserName", getOutUserName());
		attributes.put("outPassword", getOutPassword());
		attributes.put("allowAnonymous", getAllowAnonymous());
		attributes.put("active", getActive());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long mailingListId = (Long)attributes.get("mailingListId");

		if (mailingListId != null) {
			setMailingListId(mailingListId);
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

		Long categoryId = (Long)attributes.get("categoryId");

		if (categoryId != null) {
			setCategoryId(categoryId);
		}

		String emailAddress = (String)attributes.get("emailAddress");

		if (emailAddress != null) {
			setEmailAddress(emailAddress);
		}

		String inProtocol = (String)attributes.get("inProtocol");

		if (inProtocol != null) {
			setInProtocol(inProtocol);
		}

		String inServerName = (String)attributes.get("inServerName");

		if (inServerName != null) {
			setInServerName(inServerName);
		}

		Integer inServerPort = (Integer)attributes.get("inServerPort");

		if (inServerPort != null) {
			setInServerPort(inServerPort);
		}

		Boolean inUseSSL = (Boolean)attributes.get("inUseSSL");

		if (inUseSSL != null) {
			setInUseSSL(inUseSSL);
		}

		String inUserName = (String)attributes.get("inUserName");

		if (inUserName != null) {
			setInUserName(inUserName);
		}

		String inPassword = (String)attributes.get("inPassword");

		if (inPassword != null) {
			setInPassword(inPassword);
		}

		Integer inReadInterval = (Integer)attributes.get("inReadInterval");

		if (inReadInterval != null) {
			setInReadInterval(inReadInterval);
		}

		String outEmailAddress = (String)attributes.get("outEmailAddress");

		if (outEmailAddress != null) {
			setOutEmailAddress(outEmailAddress);
		}

		Boolean outCustom = (Boolean)attributes.get("outCustom");

		if (outCustom != null) {
			setOutCustom(outCustom);
		}

		String outServerName = (String)attributes.get("outServerName");

		if (outServerName != null) {
			setOutServerName(outServerName);
		}

		Integer outServerPort = (Integer)attributes.get("outServerPort");

		if (outServerPort != null) {
			setOutServerPort(outServerPort);
		}

		Boolean outUseSSL = (Boolean)attributes.get("outUseSSL");

		if (outUseSSL != null) {
			setOutUseSSL(outUseSSL);
		}

		String outUserName = (String)attributes.get("outUserName");

		if (outUserName != null) {
			setOutUserName(outUserName);
		}

		String outPassword = (String)attributes.get("outPassword");

		if (outPassword != null) {
			setOutPassword(outPassword);
		}

		Boolean allowAnonymous = (Boolean)attributes.get("allowAnonymous");

		if (allowAnonymous != null) {
			setAllowAnonymous(allowAnonymous);
		}

		Boolean active = (Boolean)attributes.get("active");

		if (active != null) {
			setActive(active);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new MBMailingListWrapper((MBMailingList)_mbMailingList.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portlet.messageboards.model.MBMailingList mbMailingList) {
		return _mbMailingList.compareTo(mbMailingList);
	}

	/**
	* Returns the active of this message boards mailing list.
	*
	* @return the active of this message boards mailing list
	*/
	@Override
	public boolean getActive() {
		return _mbMailingList.getActive();
	}

	/**
	* Returns the allow anonymous of this message boards mailing list.
	*
	* @return the allow anonymous of this message boards mailing list
	*/
	@Override
	public boolean getAllowAnonymous() {
		return _mbMailingList.getAllowAnonymous();
	}

	/**
	* Returns the category ID of this message boards mailing list.
	*
	* @return the category ID of this message boards mailing list
	*/
	@Override
	public long getCategoryId() {
		return _mbMailingList.getCategoryId();
	}

	/**
	* Returns the company ID of this message boards mailing list.
	*
	* @return the company ID of this message boards mailing list
	*/
	@Override
	public long getCompanyId() {
		return _mbMailingList.getCompanyId();
	}

	/**
	* Returns the create date of this message boards mailing list.
	*
	* @return the create date of this message boards mailing list
	*/
	@Override
	public Date getCreateDate() {
		return _mbMailingList.getCreateDate();
	}

	/**
	* Returns the email address of this message boards mailing list.
	*
	* @return the email address of this message boards mailing list
	*/
	@Override
	public java.lang.String getEmailAddress() {
		return _mbMailingList.getEmailAddress();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _mbMailingList.getExpandoBridge();
	}

	/**
	* Returns the group ID of this message boards mailing list.
	*
	* @return the group ID of this message boards mailing list
	*/
	@Override
	public long getGroupId() {
		return _mbMailingList.getGroupId();
	}

	/**
	* Returns the in password of this message boards mailing list.
	*
	* @return the in password of this message boards mailing list
	*/
	@Override
	public java.lang.String getInPassword() {
		return _mbMailingList.getInPassword();
	}

	/**
	* Returns the in protocol of this message boards mailing list.
	*
	* @return the in protocol of this message boards mailing list
	*/
	@Override
	public java.lang.String getInProtocol() {
		return _mbMailingList.getInProtocol();
	}

	/**
	* Returns the in read interval of this message boards mailing list.
	*
	* @return the in read interval of this message boards mailing list
	*/
	@Override
	public int getInReadInterval() {
		return _mbMailingList.getInReadInterval();
	}

	/**
	* Returns the in server name of this message boards mailing list.
	*
	* @return the in server name of this message boards mailing list
	*/
	@Override
	public java.lang.String getInServerName() {
		return _mbMailingList.getInServerName();
	}

	/**
	* Returns the in server port of this message boards mailing list.
	*
	* @return the in server port of this message boards mailing list
	*/
	@Override
	public int getInServerPort() {
		return _mbMailingList.getInServerPort();
	}

	/**
	* Returns the in use s s l of this message boards mailing list.
	*
	* @return the in use s s l of this message boards mailing list
	*/
	@Override
	public boolean getInUseSSL() {
		return _mbMailingList.getInUseSSL();
	}

	/**
	* Returns the in user name of this message boards mailing list.
	*
	* @return the in user name of this message boards mailing list
	*/
	@Override
	public java.lang.String getInUserName() {
		return _mbMailingList.getInUserName();
	}

	/**
	* Returns the mailing list ID of this message boards mailing list.
	*
	* @return the mailing list ID of this message boards mailing list
	*/
	@Override
	public long getMailingListId() {
		return _mbMailingList.getMailingListId();
	}

	/**
	* Returns the modified date of this message boards mailing list.
	*
	* @return the modified date of this message boards mailing list
	*/
	@Override
	public Date getModifiedDate() {
		return _mbMailingList.getModifiedDate();
	}

	/**
	* Returns the out custom of this message boards mailing list.
	*
	* @return the out custom of this message boards mailing list
	*/
	@Override
	public boolean getOutCustom() {
		return _mbMailingList.getOutCustom();
	}

	/**
	* Returns the out email address of this message boards mailing list.
	*
	* @return the out email address of this message boards mailing list
	*/
	@Override
	public java.lang.String getOutEmailAddress() {
		return _mbMailingList.getOutEmailAddress();
	}

	/**
	* Returns the out password of this message boards mailing list.
	*
	* @return the out password of this message boards mailing list
	*/
	@Override
	public java.lang.String getOutPassword() {
		return _mbMailingList.getOutPassword();
	}

	/**
	* Returns the out server name of this message boards mailing list.
	*
	* @return the out server name of this message boards mailing list
	*/
	@Override
	public java.lang.String getOutServerName() {
		return _mbMailingList.getOutServerName();
	}

	/**
	* Returns the out server port of this message boards mailing list.
	*
	* @return the out server port of this message boards mailing list
	*/
	@Override
	public int getOutServerPort() {
		return _mbMailingList.getOutServerPort();
	}

	/**
	* Returns the out use s s l of this message boards mailing list.
	*
	* @return the out use s s l of this message boards mailing list
	*/
	@Override
	public boolean getOutUseSSL() {
		return _mbMailingList.getOutUseSSL();
	}

	/**
	* Returns the out user name of this message boards mailing list.
	*
	* @return the out user name of this message boards mailing list
	*/
	@Override
	public java.lang.String getOutUserName() {
		return _mbMailingList.getOutUserName();
	}

	/**
	* Returns the primary key of this message boards mailing list.
	*
	* @return the primary key of this message boards mailing list
	*/
	@Override
	public long getPrimaryKey() {
		return _mbMailingList.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _mbMailingList.getPrimaryKeyObj();
	}

	/**
	* Returns the user ID of this message boards mailing list.
	*
	* @return the user ID of this message boards mailing list
	*/
	@Override
	public long getUserId() {
		return _mbMailingList.getUserId();
	}

	/**
	* Returns the user name of this message boards mailing list.
	*
	* @return the user name of this message boards mailing list
	*/
	@Override
	public java.lang.String getUserName() {
		return _mbMailingList.getUserName();
	}

	/**
	* Returns the user uuid of this message boards mailing list.
	*
	* @return the user uuid of this message boards mailing list
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _mbMailingList.getUserUuid();
	}

	/**
	* Returns the uuid of this message boards mailing list.
	*
	* @return the uuid of this message boards mailing list
	*/
	@Override
	public java.lang.String getUuid() {
		return _mbMailingList.getUuid();
	}

	@Override
	public int hashCode() {
		return _mbMailingList.hashCode();
	}

	/**
	* Returns <code>true</code> if this message boards mailing list is active.
	*
	* @return <code>true</code> if this message boards mailing list is active; <code>false</code> otherwise
	*/
	@Override
	public boolean isActive() {
		return _mbMailingList.isActive();
	}

	/**
	* Returns <code>true</code> if this message boards mailing list is allow anonymous.
	*
	* @return <code>true</code> if this message boards mailing list is allow anonymous; <code>false</code> otherwise
	*/
	@Override
	public boolean isAllowAnonymous() {
		return _mbMailingList.isAllowAnonymous();
	}

	@Override
	public boolean isCachedModel() {
		return _mbMailingList.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _mbMailingList.isEscapedModel();
	}

	/**
	* Returns <code>true</code> if this message boards mailing list is in use s s l.
	*
	* @return <code>true</code> if this message boards mailing list is in use s s l; <code>false</code> otherwise
	*/
	@Override
	public boolean isInUseSSL() {
		return _mbMailingList.isInUseSSL();
	}

	@Override
	public boolean isNew() {
		return _mbMailingList.isNew();
	}

	/**
	* Returns <code>true</code> if this message boards mailing list is out custom.
	*
	* @return <code>true</code> if this message boards mailing list is out custom; <code>false</code> otherwise
	*/
	@Override
	public boolean isOutCustom() {
		return _mbMailingList.isOutCustom();
	}

	/**
	* Returns <code>true</code> if this message boards mailing list is out use s s l.
	*
	* @return <code>true</code> if this message boards mailing list is out use s s l; <code>false</code> otherwise
	*/
	@Override
	public boolean isOutUseSSL() {
		return _mbMailingList.isOutUseSSL();
	}

	@Override
	public void persist() {
		_mbMailingList.persist();
	}

	/**
	* Sets whether this message boards mailing list is active.
	*
	* @param active the active of this message boards mailing list
	*/
	@Override
	public void setActive(boolean active) {
		_mbMailingList.setActive(active);
	}

	/**
	* Sets whether this message boards mailing list is allow anonymous.
	*
	* @param allowAnonymous the allow anonymous of this message boards mailing list
	*/
	@Override
	public void setAllowAnonymous(boolean allowAnonymous) {
		_mbMailingList.setAllowAnonymous(allowAnonymous);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_mbMailingList.setCachedModel(cachedModel);
	}

	/**
	* Sets the category ID of this message boards mailing list.
	*
	* @param categoryId the category ID of this message boards mailing list
	*/
	@Override
	public void setCategoryId(long categoryId) {
		_mbMailingList.setCategoryId(categoryId);
	}

	/**
	* Sets the company ID of this message boards mailing list.
	*
	* @param companyId the company ID of this message boards mailing list
	*/
	@Override
	public void setCompanyId(long companyId) {
		_mbMailingList.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this message boards mailing list.
	*
	* @param createDate the create date of this message boards mailing list
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_mbMailingList.setCreateDate(createDate);
	}

	/**
	* Sets the email address of this message boards mailing list.
	*
	* @param emailAddress the email address of this message boards mailing list
	*/
	@Override
	public void setEmailAddress(java.lang.String emailAddress) {
		_mbMailingList.setEmailAddress(emailAddress);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_mbMailingList.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_mbMailingList.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_mbMailingList.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this message boards mailing list.
	*
	* @param groupId the group ID of this message boards mailing list
	*/
	@Override
	public void setGroupId(long groupId) {
		_mbMailingList.setGroupId(groupId);
	}

	/**
	* Sets the in password of this message boards mailing list.
	*
	* @param inPassword the in password of this message boards mailing list
	*/
	@Override
	public void setInPassword(java.lang.String inPassword) {
		_mbMailingList.setInPassword(inPassword);
	}

	/**
	* Sets the in protocol of this message boards mailing list.
	*
	* @param inProtocol the in protocol of this message boards mailing list
	*/
	@Override
	public void setInProtocol(java.lang.String inProtocol) {
		_mbMailingList.setInProtocol(inProtocol);
	}

	/**
	* Sets the in read interval of this message boards mailing list.
	*
	* @param inReadInterval the in read interval of this message boards mailing list
	*/
	@Override
	public void setInReadInterval(int inReadInterval) {
		_mbMailingList.setInReadInterval(inReadInterval);
	}

	/**
	* Sets the in server name of this message boards mailing list.
	*
	* @param inServerName the in server name of this message boards mailing list
	*/
	@Override
	public void setInServerName(java.lang.String inServerName) {
		_mbMailingList.setInServerName(inServerName);
	}

	/**
	* Sets the in server port of this message boards mailing list.
	*
	* @param inServerPort the in server port of this message boards mailing list
	*/
	@Override
	public void setInServerPort(int inServerPort) {
		_mbMailingList.setInServerPort(inServerPort);
	}

	/**
	* Sets whether this message boards mailing list is in use s s l.
	*
	* @param inUseSSL the in use s s l of this message boards mailing list
	*/
	@Override
	public void setInUseSSL(boolean inUseSSL) {
		_mbMailingList.setInUseSSL(inUseSSL);
	}

	/**
	* Sets the in user name of this message boards mailing list.
	*
	* @param inUserName the in user name of this message boards mailing list
	*/
	@Override
	public void setInUserName(java.lang.String inUserName) {
		_mbMailingList.setInUserName(inUserName);
	}

	/**
	* Sets the mailing list ID of this message boards mailing list.
	*
	* @param mailingListId the mailing list ID of this message boards mailing list
	*/
	@Override
	public void setMailingListId(long mailingListId) {
		_mbMailingList.setMailingListId(mailingListId);
	}

	/**
	* Sets the modified date of this message boards mailing list.
	*
	* @param modifiedDate the modified date of this message boards mailing list
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_mbMailingList.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_mbMailingList.setNew(n);
	}

	/**
	* Sets whether this message boards mailing list is out custom.
	*
	* @param outCustom the out custom of this message boards mailing list
	*/
	@Override
	public void setOutCustom(boolean outCustom) {
		_mbMailingList.setOutCustom(outCustom);
	}

	/**
	* Sets the out email address of this message boards mailing list.
	*
	* @param outEmailAddress the out email address of this message boards mailing list
	*/
	@Override
	public void setOutEmailAddress(java.lang.String outEmailAddress) {
		_mbMailingList.setOutEmailAddress(outEmailAddress);
	}

	/**
	* Sets the out password of this message boards mailing list.
	*
	* @param outPassword the out password of this message boards mailing list
	*/
	@Override
	public void setOutPassword(java.lang.String outPassword) {
		_mbMailingList.setOutPassword(outPassword);
	}

	/**
	* Sets the out server name of this message boards mailing list.
	*
	* @param outServerName the out server name of this message boards mailing list
	*/
	@Override
	public void setOutServerName(java.lang.String outServerName) {
		_mbMailingList.setOutServerName(outServerName);
	}

	/**
	* Sets the out server port of this message boards mailing list.
	*
	* @param outServerPort the out server port of this message boards mailing list
	*/
	@Override
	public void setOutServerPort(int outServerPort) {
		_mbMailingList.setOutServerPort(outServerPort);
	}

	/**
	* Sets whether this message boards mailing list is out use s s l.
	*
	* @param outUseSSL the out use s s l of this message boards mailing list
	*/
	@Override
	public void setOutUseSSL(boolean outUseSSL) {
		_mbMailingList.setOutUseSSL(outUseSSL);
	}

	/**
	* Sets the out user name of this message boards mailing list.
	*
	* @param outUserName the out user name of this message boards mailing list
	*/
	@Override
	public void setOutUserName(java.lang.String outUserName) {
		_mbMailingList.setOutUserName(outUserName);
	}

	/**
	* Sets the primary key of this message boards mailing list.
	*
	* @param primaryKey the primary key of this message boards mailing list
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_mbMailingList.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_mbMailingList.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this message boards mailing list.
	*
	* @param userId the user ID of this message boards mailing list
	*/
	@Override
	public void setUserId(long userId) {
		_mbMailingList.setUserId(userId);
	}

	/**
	* Sets the user name of this message boards mailing list.
	*
	* @param userName the user name of this message boards mailing list
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_mbMailingList.setUserName(userName);
	}

	/**
	* Sets the user uuid of this message boards mailing list.
	*
	* @param userUuid the user uuid of this message boards mailing list
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_mbMailingList.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this message boards mailing list.
	*
	* @param uuid the uuid of this message boards mailing list
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_mbMailingList.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.messageboards.model.MBMailingList> toCacheModel() {
		return _mbMailingList.toCacheModel();
	}

	@Override
	public com.liferay.portlet.messageboards.model.MBMailingList toEscapedModel() {
		return new MBMailingListWrapper(_mbMailingList.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _mbMailingList.toString();
	}

	@Override
	public com.liferay.portlet.messageboards.model.MBMailingList toUnescapedModel() {
		return new MBMailingListWrapper(_mbMailingList.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _mbMailingList.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof MBMailingListWrapper)) {
			return false;
		}

		MBMailingListWrapper mbMailingListWrapper = (MBMailingListWrapper)obj;

		if (Validator.equals(_mbMailingList, mbMailingListWrapper._mbMailingList)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _mbMailingList.getStagedModelType();
	}

	@Override
	public MBMailingList getWrappedModel() {
		return _mbMailingList;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _mbMailingList.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _mbMailingList.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_mbMailingList.resetOriginalValues();
	}

	private final MBMailingList _mbMailingList;
}