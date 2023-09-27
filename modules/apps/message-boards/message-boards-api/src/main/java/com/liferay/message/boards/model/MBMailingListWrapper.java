/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link MBMailingList}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MBMailingList
 * @generated
 */
public class MBMailingListWrapper
	extends BaseModelWrapper<MBMailingList>
	implements MBMailingList, ModelWrapper<MBMailingList> {

	public MBMailingListWrapper(MBMailingList mbMailingList) {
		super(mbMailingList);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
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
		attributes.put("inUseSSL", isInUseSSL());
		attributes.put("inUserName", getInUserName());
		attributes.put("inPassword", getInPassword());
		attributes.put("inReadInterval", getInReadInterval());
		attributes.put("outEmailAddress", getOutEmailAddress());
		attributes.put("outCustom", isOutCustom());
		attributes.put("outServerName", getOutServerName());
		attributes.put("outServerPort", getOutServerPort());
		attributes.put("outUseSSL", isOutUseSSL());
		attributes.put("outUserName", getOutUserName());
		attributes.put("outPassword", getOutPassword());
		attributes.put("allowAnonymous", isAllowAnonymous());
		attributes.put("active", isActive());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long ctCollectionId = (Long)attributes.get("ctCollectionId");

		if (ctCollectionId != null) {
			setCtCollectionId(ctCollectionId);
		}

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
	public MBMailingList cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the active of this message boards mailing list.
	 *
	 * @return the active of this message boards mailing list
	 */
	@Override
	public boolean getActive() {
		return model.getActive();
	}

	/**
	 * Returns the allow anonymous of this message boards mailing list.
	 *
	 * @return the allow anonymous of this message boards mailing list
	 */
	@Override
	public boolean getAllowAnonymous() {
		return model.getAllowAnonymous();
	}

	/**
	 * Returns the category ID of this message boards mailing list.
	 *
	 * @return the category ID of this message boards mailing list
	 */
	@Override
	public long getCategoryId() {
		return model.getCategoryId();
	}

	/**
	 * Returns the company ID of this message boards mailing list.
	 *
	 * @return the company ID of this message boards mailing list
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this message boards mailing list.
	 *
	 * @return the create date of this message boards mailing list
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this message boards mailing list.
	 *
	 * @return the ct collection ID of this message boards mailing list
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the email address of this message boards mailing list.
	 *
	 * @return the email address of this message boards mailing list
	 */
	@Override
	public String getEmailAddress() {
		return model.getEmailAddress();
	}

	/**
	 * Returns the group ID of this message boards mailing list.
	 *
	 * @return the group ID of this message boards mailing list
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the in password of this message boards mailing list.
	 *
	 * @return the in password of this message boards mailing list
	 */
	@Override
	public String getInPassword() {
		return model.getInPassword();
	}

	/**
	 * Returns the in protocol of this message boards mailing list.
	 *
	 * @return the in protocol of this message boards mailing list
	 */
	@Override
	public String getInProtocol() {
		return model.getInProtocol();
	}

	/**
	 * Returns the in read interval of this message boards mailing list.
	 *
	 * @return the in read interval of this message boards mailing list
	 */
	@Override
	public int getInReadInterval() {
		return model.getInReadInterval();
	}

	/**
	 * Returns the in server name of this message boards mailing list.
	 *
	 * @return the in server name of this message boards mailing list
	 */
	@Override
	public String getInServerName() {
		return model.getInServerName();
	}

	/**
	 * Returns the in server port of this message boards mailing list.
	 *
	 * @return the in server port of this message boards mailing list
	 */
	@Override
	public int getInServerPort() {
		return model.getInServerPort();
	}

	/**
	 * Returns the in user name of this message boards mailing list.
	 *
	 * @return the in user name of this message boards mailing list
	 */
	@Override
	public String getInUserName() {
		return model.getInUserName();
	}

	/**
	 * Returns the in use ssl of this message boards mailing list.
	 *
	 * @return the in use ssl of this message boards mailing list
	 */
	@Override
	public boolean getInUseSSL() {
		return model.getInUseSSL();
	}

	/**
	 * Returns the mailing list ID of this message boards mailing list.
	 *
	 * @return the mailing list ID of this message boards mailing list
	 */
	@Override
	public long getMailingListId() {
		return model.getMailingListId();
	}

	/**
	 * Returns the modified date of this message boards mailing list.
	 *
	 * @return the modified date of this message boards mailing list
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this message boards mailing list.
	 *
	 * @return the mvcc version of this message boards mailing list
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the out custom of this message boards mailing list.
	 *
	 * @return the out custom of this message boards mailing list
	 */
	@Override
	public boolean getOutCustom() {
		return model.getOutCustom();
	}

	/**
	 * Returns the out email address of this message boards mailing list.
	 *
	 * @return the out email address of this message boards mailing list
	 */
	@Override
	public String getOutEmailAddress() {
		return model.getOutEmailAddress();
	}

	/**
	 * Returns the out password of this message boards mailing list.
	 *
	 * @return the out password of this message boards mailing list
	 */
	@Override
	public String getOutPassword() {
		return model.getOutPassword();
	}

	/**
	 * Returns the out server name of this message boards mailing list.
	 *
	 * @return the out server name of this message boards mailing list
	 */
	@Override
	public String getOutServerName() {
		return model.getOutServerName();
	}

	/**
	 * Returns the out server port of this message boards mailing list.
	 *
	 * @return the out server port of this message boards mailing list
	 */
	@Override
	public int getOutServerPort() {
		return model.getOutServerPort();
	}

	/**
	 * Returns the out user name of this message boards mailing list.
	 *
	 * @return the out user name of this message boards mailing list
	 */
	@Override
	public String getOutUserName() {
		return model.getOutUserName();
	}

	/**
	 * Returns the out use ssl of this message boards mailing list.
	 *
	 * @return the out use ssl of this message boards mailing list
	 */
	@Override
	public boolean getOutUseSSL() {
		return model.getOutUseSSL();
	}

	/**
	 * Returns the primary key of this message boards mailing list.
	 *
	 * @return the primary key of this message boards mailing list
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this message boards mailing list.
	 *
	 * @return the user ID of this message boards mailing list
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this message boards mailing list.
	 *
	 * @return the user name of this message boards mailing list
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this message boards mailing list.
	 *
	 * @return the user uuid of this message boards mailing list
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this message boards mailing list.
	 *
	 * @return the uuid of this message boards mailing list
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns <code>true</code> if this message boards mailing list is active.
	 *
	 * @return <code>true</code> if this message boards mailing list is active; <code>false</code> otherwise
	 */
	@Override
	public boolean isActive() {
		return model.isActive();
	}

	/**
	 * Returns <code>true</code> if this message boards mailing list is allow anonymous.
	 *
	 * @return <code>true</code> if this message boards mailing list is allow anonymous; <code>false</code> otherwise
	 */
	@Override
	public boolean isAllowAnonymous() {
		return model.isAllowAnonymous();
	}

	/**
	 * Returns <code>true</code> if this message boards mailing list is in use ssl.
	 *
	 * @return <code>true</code> if this message boards mailing list is in use ssl; <code>false</code> otherwise
	 */
	@Override
	public boolean isInUseSSL() {
		return model.isInUseSSL();
	}

	/**
	 * Returns <code>true</code> if this message boards mailing list is out custom.
	 *
	 * @return <code>true</code> if this message boards mailing list is out custom; <code>false</code> otherwise
	 */
	@Override
	public boolean isOutCustom() {
		return model.isOutCustom();
	}

	/**
	 * Returns <code>true</code> if this message boards mailing list is out use ssl.
	 *
	 * @return <code>true</code> if this message boards mailing list is out use ssl; <code>false</code> otherwise
	 */
	@Override
	public boolean isOutUseSSL() {
		return model.isOutUseSSL();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets whether this message boards mailing list is active.
	 *
	 * @param active the active of this message boards mailing list
	 */
	@Override
	public void setActive(boolean active) {
		model.setActive(active);
	}

	/**
	 * Sets whether this message boards mailing list is allow anonymous.
	 *
	 * @param allowAnonymous the allow anonymous of this message boards mailing list
	 */
	@Override
	public void setAllowAnonymous(boolean allowAnonymous) {
		model.setAllowAnonymous(allowAnonymous);
	}

	/**
	 * Sets the category ID of this message boards mailing list.
	 *
	 * @param categoryId the category ID of this message boards mailing list
	 */
	@Override
	public void setCategoryId(long categoryId) {
		model.setCategoryId(categoryId);
	}

	/**
	 * Sets the company ID of this message boards mailing list.
	 *
	 * @param companyId the company ID of this message boards mailing list
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this message boards mailing list.
	 *
	 * @param createDate the create date of this message boards mailing list
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this message boards mailing list.
	 *
	 * @param ctCollectionId the ct collection ID of this message boards mailing list
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the email address of this message boards mailing list.
	 *
	 * @param emailAddress the email address of this message boards mailing list
	 */
	@Override
	public void setEmailAddress(String emailAddress) {
		model.setEmailAddress(emailAddress);
	}

	/**
	 * Sets the group ID of this message boards mailing list.
	 *
	 * @param groupId the group ID of this message boards mailing list
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the in password of this message boards mailing list.
	 *
	 * @param inPassword the in password of this message boards mailing list
	 */
	@Override
	public void setInPassword(String inPassword) {
		model.setInPassword(inPassword);
	}

	/**
	 * Sets the in protocol of this message boards mailing list.
	 *
	 * @param inProtocol the in protocol of this message boards mailing list
	 */
	@Override
	public void setInProtocol(String inProtocol) {
		model.setInProtocol(inProtocol);
	}

	/**
	 * Sets the in read interval of this message boards mailing list.
	 *
	 * @param inReadInterval the in read interval of this message boards mailing list
	 */
	@Override
	public void setInReadInterval(int inReadInterval) {
		model.setInReadInterval(inReadInterval);
	}

	/**
	 * Sets the in server name of this message boards mailing list.
	 *
	 * @param inServerName the in server name of this message boards mailing list
	 */
	@Override
	public void setInServerName(String inServerName) {
		model.setInServerName(inServerName);
	}

	/**
	 * Sets the in server port of this message boards mailing list.
	 *
	 * @param inServerPort the in server port of this message boards mailing list
	 */
	@Override
	public void setInServerPort(int inServerPort) {
		model.setInServerPort(inServerPort);
	}

	/**
	 * Sets the in user name of this message boards mailing list.
	 *
	 * @param inUserName the in user name of this message boards mailing list
	 */
	@Override
	public void setInUserName(String inUserName) {
		model.setInUserName(inUserName);
	}

	/**
	 * Sets whether this message boards mailing list is in use ssl.
	 *
	 * @param inUseSSL the in use ssl of this message boards mailing list
	 */
	@Override
	public void setInUseSSL(boolean inUseSSL) {
		model.setInUseSSL(inUseSSL);
	}

	/**
	 * Sets the mailing list ID of this message boards mailing list.
	 *
	 * @param mailingListId the mailing list ID of this message boards mailing list
	 */
	@Override
	public void setMailingListId(long mailingListId) {
		model.setMailingListId(mailingListId);
	}

	/**
	 * Sets the modified date of this message boards mailing list.
	 *
	 * @param modifiedDate the modified date of this message boards mailing list
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this message boards mailing list.
	 *
	 * @param mvccVersion the mvcc version of this message boards mailing list
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets whether this message boards mailing list is out custom.
	 *
	 * @param outCustom the out custom of this message boards mailing list
	 */
	@Override
	public void setOutCustom(boolean outCustom) {
		model.setOutCustom(outCustom);
	}

	/**
	 * Sets the out email address of this message boards mailing list.
	 *
	 * @param outEmailAddress the out email address of this message boards mailing list
	 */
	@Override
	public void setOutEmailAddress(String outEmailAddress) {
		model.setOutEmailAddress(outEmailAddress);
	}

	/**
	 * Sets the out password of this message boards mailing list.
	 *
	 * @param outPassword the out password of this message boards mailing list
	 */
	@Override
	public void setOutPassword(String outPassword) {
		model.setOutPassword(outPassword);
	}

	/**
	 * Sets the out server name of this message boards mailing list.
	 *
	 * @param outServerName the out server name of this message boards mailing list
	 */
	@Override
	public void setOutServerName(String outServerName) {
		model.setOutServerName(outServerName);
	}

	/**
	 * Sets the out server port of this message boards mailing list.
	 *
	 * @param outServerPort the out server port of this message boards mailing list
	 */
	@Override
	public void setOutServerPort(int outServerPort) {
		model.setOutServerPort(outServerPort);
	}

	/**
	 * Sets the out user name of this message boards mailing list.
	 *
	 * @param outUserName the out user name of this message boards mailing list
	 */
	@Override
	public void setOutUserName(String outUserName) {
		model.setOutUserName(outUserName);
	}

	/**
	 * Sets whether this message boards mailing list is out use ssl.
	 *
	 * @param outUseSSL the out use ssl of this message boards mailing list
	 */
	@Override
	public void setOutUseSSL(boolean outUseSSL) {
		model.setOutUseSSL(outUseSSL);
	}

	/**
	 * Sets the primary key of this message boards mailing list.
	 *
	 * @param primaryKey the primary key of this message boards mailing list
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this message boards mailing list.
	 *
	 * @param userId the user ID of this message boards mailing list
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this message boards mailing list.
	 *
	 * @param userName the user name of this message boards mailing list
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this message boards mailing list.
	 *
	 * @param userUuid the user uuid of this message boards mailing list
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this message boards mailing list.
	 *
	 * @param uuid the uuid of this message boards mailing list
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	public Map<String, Function<MBMailingList, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<MBMailingList, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected MBMailingListWrapper wrap(MBMailingList mbMailingList) {
		return new MBMailingListWrapper(mbMailingList);
	}

}