/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
 * This class is a wrapper for {@link MBMailingList}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MBMailingList
 * @generated
 */
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

	/**
	* Returns the primary key of this message boards mailing list.
	*
	* @return the primary key of this message boards mailing list
	*/
	@Override
	public long getPrimaryKey() {
		return _mbMailingList.getPrimaryKey();
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

	/**
	* Returns the uuid of this message boards mailing list.
	*
	* @return the uuid of this message boards mailing list
	*/
	@Override
	public java.lang.String getUuid() {
		return _mbMailingList.getUuid();
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
	* Sets the mailing list ID of this message boards mailing list.
	*
	* @param mailingListId the mailing list ID of this message boards mailing list
	*/
	@Override
	public void setMailingListId(long mailingListId) {
		_mbMailingList.setMailingListId(mailingListId);
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
	* Sets the group ID of this message boards mailing list.
	*
	* @param groupId the group ID of this message boards mailing list
	*/
	@Override
	public void setGroupId(long groupId) {
		_mbMailingList.setGroupId(groupId);
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
	* Sets the company ID of this message boards mailing list.
	*
	* @param companyId the company ID of this message boards mailing list
	*/
	@Override
	public void setCompanyId(long companyId) {
		_mbMailingList.setCompanyId(companyId);
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
	* Sets the user ID of this message boards mailing list.
	*
	* @param userId the user ID of this message boards mailing list
	*/
	@Override
	public void setUserId(long userId) {
		_mbMailingList.setUserId(userId);
	}

	/**
	* Returns the user uuid of this message boards mailing list.
	*
	* @return the user uuid of this message boards mailing list
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbMailingList.getUserUuid();
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
	* Returns the user name of this message boards mailing list.
	*
	* @return the user name of this message boards mailing list
	*/
	@Override
	public java.lang.String getUserName() {
		return _mbMailingList.getUserName();
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
	* Returns the create date of this message boards mailing list.
	*
	* @return the create date of this message boards mailing list
	*/
	@Override
	public java.util.Date getCreateDate() {
		return _mbMailingList.getCreateDate();
	}

	/**
	* Sets the create date of this message boards mailing list.
	*
	* @param createDate the create date of this message boards mailing list
	*/
	@Override
	public void setCreateDate(java.util.Date createDate) {
		_mbMailingList.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this message boards mailing list.
	*
	* @return the modified date of this message boards mailing list
	*/
	@Override
	public java.util.Date getModifiedDate() {
		return _mbMailingList.getModifiedDate();
	}

	/**
	* Sets the modified date of this message boards mailing list.
	*
	* @param modifiedDate the modified date of this message boards mailing list
	*/
	@Override
	public void setModifiedDate(java.util.Date modifiedDate) {
		_mbMailingList.setModifiedDate(modifiedDate);
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
	* Sets the category ID of this message boards mailing list.
	*
	* @param categoryId the category ID of this message boards mailing list
	*/
	@Override
	public void setCategoryId(long categoryId) {
		_mbMailingList.setCategoryId(categoryId);
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

	/**
	* Sets the email address of this message boards mailing list.
	*
	* @param emailAddress the email address of this message boards mailing list
	*/
	@Override
	public void setEmailAddress(java.lang.String emailAddress) {
		_mbMailingList.setEmailAddress(emailAddress);
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
	* Sets the in protocol of this message boards mailing list.
	*
	* @param inProtocol the in protocol of this message boards mailing list
	*/
	@Override
	public void setInProtocol(java.lang.String inProtocol) {
		_mbMailingList.setInProtocol(inProtocol);
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
	* Sets the in server name of this message boards mailing list.
	*
	* @param inServerName the in server name of this message boards mailing list
	*/
	@Override
	public void setInServerName(java.lang.String inServerName) {
		_mbMailingList.setInServerName(inServerName);
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
	* Sets the in server port of this message boards mailing list.
	*
	* @param inServerPort the in server port of this message boards mailing list
	*/
	@Override
	public void setInServerPort(int inServerPort) {
		_mbMailingList.setInServerPort(inServerPort);
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
	* Returns <code>true</code> if this message boards mailing list is in use s s l.
	*
	* @return <code>true</code> if this message boards mailing list is in use s s l; <code>false</code> otherwise
	*/
	@Override
	public boolean isInUseSSL() {
		return _mbMailingList.isInUseSSL();
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
	* Returns the in user name of this message boards mailing list.
	*
	* @return the in user name of this message boards mailing list
	*/
	@Override
	public java.lang.String getInUserName() {
		return _mbMailingList.getInUserName();
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
	* Returns the in password of this message boards mailing list.
	*
	* @return the in password of this message boards mailing list
	*/
	@Override
	public java.lang.String getInPassword() {
		return _mbMailingList.getInPassword();
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
	* Returns the in read interval of this message boards mailing list.
	*
	* @return the in read interval of this message boards mailing list
	*/
	@Override
	public int getInReadInterval() {
		return _mbMailingList.getInReadInterval();
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
	* Returns the out email address of this message boards mailing list.
	*
	* @return the out email address of this message boards mailing list
	*/
	@Override
	public java.lang.String getOutEmailAddress() {
		return _mbMailingList.getOutEmailAddress();
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
	* Returns the out custom of this message boards mailing list.
	*
	* @return the out custom of this message boards mailing list
	*/
	@Override
	public boolean getOutCustom() {
		return _mbMailingList.getOutCustom();
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
	* Sets whether this message boards mailing list is out custom.
	*
	* @param outCustom the out custom of this message boards mailing list
	*/
	@Override
	public void setOutCustom(boolean outCustom) {
		_mbMailingList.setOutCustom(outCustom);
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
	* Sets the out server name of this message boards mailing list.
	*
	* @param outServerName the out server name of this message boards mailing list
	*/
	@Override
	public void setOutServerName(java.lang.String outServerName) {
		_mbMailingList.setOutServerName(outServerName);
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
	* Sets the out server port of this message boards mailing list.
	*
	* @param outServerPort the out server port of this message boards mailing list
	*/
	@Override
	public void setOutServerPort(int outServerPort) {
		_mbMailingList.setOutServerPort(outServerPort);
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
	* Returns <code>true</code> if this message boards mailing list is out use s s l.
	*
	* @return <code>true</code> if this message boards mailing list is out use s s l; <code>false</code> otherwise
	*/
	@Override
	public boolean isOutUseSSL() {
		return _mbMailingList.isOutUseSSL();
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
	* Returns the out user name of this message boards mailing list.
	*
	* @return the out user name of this message boards mailing list
	*/
	@Override
	public java.lang.String getOutUserName() {
		return _mbMailingList.getOutUserName();
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
	* Returns the out password of this message boards mailing list.
	*
	* @return the out password of this message boards mailing list
	*/
	@Override
	public java.lang.String getOutPassword() {
		return _mbMailingList.getOutPassword();
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
	* Returns the allow anonymous of this message boards mailing list.
	*
	* @return the allow anonymous of this message boards mailing list
	*/
	@Override
	public boolean getAllowAnonymous() {
		return _mbMailingList.getAllowAnonymous();
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

	/**
	* Sets whether this message boards mailing list is allow anonymous.
	*
	* @param allowAnonymous the allow anonymous of this message boards mailing list
	*/
	@Override
	public void setAllowAnonymous(boolean allowAnonymous) {
		_mbMailingList.setAllowAnonymous(allowAnonymous);
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
	* Returns <code>true</code> if this message boards mailing list is active.
	*
	* @return <code>true</code> if this message boards mailing list is active; <code>false</code> otherwise
	*/
	@Override
	public boolean isActive() {
		return _mbMailingList.isActive();
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

	@Override
	public boolean isNew() {
		return _mbMailingList.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_mbMailingList.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _mbMailingList.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_mbMailingList.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _mbMailingList.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _mbMailingList.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_mbMailingList.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _mbMailingList.getExpandoBridge();
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

	@Override
	public java.lang.Object clone() {
		return new MBMailingListWrapper((MBMailingList)_mbMailingList.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portlet.messageboards.model.MBMailingList mbMailingList) {
		return _mbMailingList.compareTo(mbMailingList);
	}

	@Override
	public int hashCode() {
		return _mbMailingList.hashCode();
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
	public com.liferay.portlet.messageboards.model.MBMailingList toUnescapedModel() {
		return new MBMailingListWrapper(_mbMailingList.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _mbMailingList.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _mbMailingList.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_mbMailingList.persist();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public MBMailingList getWrappedMBMailingList() {
		return _mbMailingList;
	}

	@Override
	public MBMailingList getWrappedModel() {
		return _mbMailingList;
	}

	@Override
	public void resetOriginalValues() {
		_mbMailingList.resetOriginalValues();
	}

	private MBMailingList _mbMailingList;
}