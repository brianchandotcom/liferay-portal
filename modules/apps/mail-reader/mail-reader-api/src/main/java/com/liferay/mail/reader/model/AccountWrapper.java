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

package com.liferay.mail.reader.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Account}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Account
 * @generated
 */
public class AccountWrapper
	extends BaseModelWrapper<Account>
	implements Account, ModelWrapper<Account> {

	public AccountWrapper(Account account) {
		super(account);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("accountId", getAccountId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("address", getAddress());
		attributes.put("personalName", getPersonalName());
		attributes.put("protocol", getProtocol());
		attributes.put("incomingHostName", getIncomingHostName());
		attributes.put("incomingPort", getIncomingPort());
		attributes.put("incomingSecure", isIncomingSecure());
		attributes.put("outgoingHostName", getOutgoingHostName());
		attributes.put("outgoingPort", getOutgoingPort());
		attributes.put("outgoingSecure", isOutgoingSecure());
		attributes.put("login", getLogin());
		attributes.put("password", getPassword());
		attributes.put("savePassword", isSavePassword());
		attributes.put("signature", getSignature());
		attributes.put("useSignature", isUseSignature());
		attributes.put("folderPrefix", getFolderPrefix());
		attributes.put("inboxFolderId", getInboxFolderId());
		attributes.put("draftFolderId", getDraftFolderId());
		attributes.put("sentFolderId", getSentFolderId());
		attributes.put("trashFolderId", getTrashFolderId());
		attributes.put("defaultSender", isDefaultSender());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long accountId = (Long)attributes.get("accountId");

		if (accountId != null) {
			setAccountId(accountId);
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

		String address = (String)attributes.get("address");

		if (address != null) {
			setAddress(address);
		}

		String personalName = (String)attributes.get("personalName");

		if (personalName != null) {
			setPersonalName(personalName);
		}

		String protocol = (String)attributes.get("protocol");

		if (protocol != null) {
			setProtocol(protocol);
		}

		String incomingHostName = (String)attributes.get("incomingHostName");

		if (incomingHostName != null) {
			setIncomingHostName(incomingHostName);
		}

		Integer incomingPort = (Integer)attributes.get("incomingPort");

		if (incomingPort != null) {
			setIncomingPort(incomingPort);
		}

		Boolean incomingSecure = (Boolean)attributes.get("incomingSecure");

		if (incomingSecure != null) {
			setIncomingSecure(incomingSecure);
		}

		String outgoingHostName = (String)attributes.get("outgoingHostName");

		if (outgoingHostName != null) {
			setOutgoingHostName(outgoingHostName);
		}

		Integer outgoingPort = (Integer)attributes.get("outgoingPort");

		if (outgoingPort != null) {
			setOutgoingPort(outgoingPort);
		}

		Boolean outgoingSecure = (Boolean)attributes.get("outgoingSecure");

		if (outgoingSecure != null) {
			setOutgoingSecure(outgoingSecure);
		}

		String login = (String)attributes.get("login");

		if (login != null) {
			setLogin(login);
		}

		String password = (String)attributes.get("password");

		if (password != null) {
			setPassword(password);
		}

		Boolean savePassword = (Boolean)attributes.get("savePassword");

		if (savePassword != null) {
			setSavePassword(savePassword);
		}

		String signature = (String)attributes.get("signature");

		if (signature != null) {
			setSignature(signature);
		}

		Boolean useSignature = (Boolean)attributes.get("useSignature");

		if (useSignature != null) {
			setUseSignature(useSignature);
		}

		String folderPrefix = (String)attributes.get("folderPrefix");

		if (folderPrefix != null) {
			setFolderPrefix(folderPrefix);
		}

		Long inboxFolderId = (Long)attributes.get("inboxFolderId");

		if (inboxFolderId != null) {
			setInboxFolderId(inboxFolderId);
		}

		Long draftFolderId = (Long)attributes.get("draftFolderId");

		if (draftFolderId != null) {
			setDraftFolderId(draftFolderId);
		}

		Long sentFolderId = (Long)attributes.get("sentFolderId");

		if (sentFolderId != null) {
			setSentFolderId(sentFolderId);
		}

		Long trashFolderId = (Long)attributes.get("trashFolderId");

		if (trashFolderId != null) {
			setTrashFolderId(trashFolderId);
		}

		Boolean defaultSender = (Boolean)attributes.get("defaultSender");

		if (defaultSender != null) {
			setDefaultSender(defaultSender);
		}
	}

	/**
	 * Returns the account ID of this account.
	 *
	 * @return the account ID of this account
	 */
	@Override
	public long getAccountId() {
		return model.getAccountId();
	}

	/**
	 * Returns the address of this account.
	 *
	 * @return the address of this account
	 */
	@Override
	public String getAddress() {
		return model.getAddress();
	}

	/**
	 * Returns the company ID of this account.
	 *
	 * @return the company ID of this account
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this account.
	 *
	 * @return the create date of this account
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the default sender of this account.
	 *
	 * @return the default sender of this account
	 */
	@Override
	public boolean getDefaultSender() {
		return model.getDefaultSender();
	}

	/**
	 * Returns the draft folder ID of this account.
	 *
	 * @return the draft folder ID of this account
	 */
	@Override
	public long getDraftFolderId() {
		return model.getDraftFolderId();
	}

	/**
	 * Returns the folder prefix of this account.
	 *
	 * @return the folder prefix of this account
	 */
	@Override
	public String getFolderPrefix() {
		return model.getFolderPrefix();
	}

	/**
	 * Returns the inbox folder ID of this account.
	 *
	 * @return the inbox folder ID of this account
	 */
	@Override
	public long getInboxFolderId() {
		return model.getInboxFolderId();
	}

	/**
	 * Returns the incoming host name of this account.
	 *
	 * @return the incoming host name of this account
	 */
	@Override
	public String getIncomingHostName() {
		return model.getIncomingHostName();
	}

	/**
	 * Returns the incoming port of this account.
	 *
	 * @return the incoming port of this account
	 */
	@Override
	public int getIncomingPort() {
		return model.getIncomingPort();
	}

	/**
	 * Returns the incoming secure of this account.
	 *
	 * @return the incoming secure of this account
	 */
	@Override
	public boolean getIncomingSecure() {
		return model.getIncomingSecure();
	}

	/**
	 * Returns the login of this account.
	 *
	 * @return the login of this account
	 */
	@Override
	public String getLogin() {
		return model.getLogin();
	}

	/**
	 * Returns the modified date of this account.
	 *
	 * @return the modified date of this account
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the outgoing host name of this account.
	 *
	 * @return the outgoing host name of this account
	 */
	@Override
	public String getOutgoingHostName() {
		return model.getOutgoingHostName();
	}

	/**
	 * Returns the outgoing port of this account.
	 *
	 * @return the outgoing port of this account
	 */
	@Override
	public int getOutgoingPort() {
		return model.getOutgoingPort();
	}

	/**
	 * Returns the outgoing secure of this account.
	 *
	 * @return the outgoing secure of this account
	 */
	@Override
	public boolean getOutgoingSecure() {
		return model.getOutgoingSecure();
	}

	/**
	 * Returns the password of this account.
	 *
	 * @return the password of this account
	 */
	@Override
	public String getPassword() {
		return model.getPassword();
	}

	@Override
	public String getPasswordDecrypted() {
		return model.getPasswordDecrypted();
	}

	/**
	 * Returns the personal name of this account.
	 *
	 * @return the personal name of this account
	 */
	@Override
	public String getPersonalName() {
		return model.getPersonalName();
	}

	/**
	 * Returns the primary key of this account.
	 *
	 * @return the primary key of this account
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the protocol of this account.
	 *
	 * @return the protocol of this account
	 */
	@Override
	public String getProtocol() {
		return model.getProtocol();
	}

	/**
	 * Returns the save password of this account.
	 *
	 * @return the save password of this account
	 */
	@Override
	public boolean getSavePassword() {
		return model.getSavePassword();
	}

	/**
	 * Returns the sent folder ID of this account.
	 *
	 * @return the sent folder ID of this account
	 */
	@Override
	public long getSentFolderId() {
		return model.getSentFolderId();
	}

	/**
	 * Returns the signature of this account.
	 *
	 * @return the signature of this account
	 */
	@Override
	public String getSignature() {
		return model.getSignature();
	}

	/**
	 * Returns the trash folder ID of this account.
	 *
	 * @return the trash folder ID of this account
	 */
	@Override
	public long getTrashFolderId() {
		return model.getTrashFolderId();
	}

	/**
	 * Returns the user ID of this account.
	 *
	 * @return the user ID of this account
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this account.
	 *
	 * @return the user name of this account
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this account.
	 *
	 * @return the user uuid of this account
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the use signature of this account.
	 *
	 * @return the use signature of this account
	 */
	@Override
	public boolean getUseSignature() {
		return model.getUseSignature();
	}

	/**
	 * Returns <code>true</code> if this account is default sender.
	 *
	 * @return <code>true</code> if this account is default sender; <code>false</code> otherwise
	 */
	@Override
	public boolean isDefaultSender() {
		return model.isDefaultSender();
	}

	/**
	 * Returns <code>true</code> if this account is incoming secure.
	 *
	 * @return <code>true</code> if this account is incoming secure; <code>false</code> otherwise
	 */
	@Override
	public boolean isIncomingSecure() {
		return model.isIncomingSecure();
	}

	/**
	 * Returns <code>true</code> if this account is outgoing secure.
	 *
	 * @return <code>true</code> if this account is outgoing secure; <code>false</code> otherwise
	 */
	@Override
	public boolean isOutgoingSecure() {
		return model.isOutgoingSecure();
	}

	/**
	 * Returns <code>true</code> if this account is save password.
	 *
	 * @return <code>true</code> if this account is save password; <code>false</code> otherwise
	 */
	@Override
	public boolean isSavePassword() {
		return model.isSavePassword();
	}

	/**
	 * Returns <code>true</code> if this account is use signature.
	 *
	 * @return <code>true</code> if this account is use signature; <code>false</code> otherwise
	 */
	@Override
	public boolean isUseSignature() {
		return model.isUseSignature();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the account ID of this account.
	 *
	 * @param accountId the account ID of this account
	 */
	@Override
	public void setAccountId(long accountId) {
		model.setAccountId(accountId);
	}

	/**
	 * Sets the address of this account.
	 *
	 * @param address the address of this account
	 */
	@Override
	public void setAddress(String address) {
		model.setAddress(address);
	}

	/**
	 * Sets the company ID of this account.
	 *
	 * @param companyId the company ID of this account
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this account.
	 *
	 * @param createDate the create date of this account
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets whether this account is default sender.
	 *
	 * @param defaultSender the default sender of this account
	 */
	@Override
	public void setDefaultSender(boolean defaultSender) {
		model.setDefaultSender(defaultSender);
	}

	/**
	 * Sets the draft folder ID of this account.
	 *
	 * @param draftFolderId the draft folder ID of this account
	 */
	@Override
	public void setDraftFolderId(long draftFolderId) {
		model.setDraftFolderId(draftFolderId);
	}

	/**
	 * Sets the folder prefix of this account.
	 *
	 * @param folderPrefix the folder prefix of this account
	 */
	@Override
	public void setFolderPrefix(String folderPrefix) {
		model.setFolderPrefix(folderPrefix);
	}

	/**
	 * Sets the inbox folder ID of this account.
	 *
	 * @param inboxFolderId the inbox folder ID of this account
	 */
	@Override
	public void setInboxFolderId(long inboxFolderId) {
		model.setInboxFolderId(inboxFolderId);
	}

	/**
	 * Sets the incoming host name of this account.
	 *
	 * @param incomingHostName the incoming host name of this account
	 */
	@Override
	public void setIncomingHostName(String incomingHostName) {
		model.setIncomingHostName(incomingHostName);
	}

	/**
	 * Sets the incoming port of this account.
	 *
	 * @param incomingPort the incoming port of this account
	 */
	@Override
	public void setIncomingPort(int incomingPort) {
		model.setIncomingPort(incomingPort);
	}

	/**
	 * Sets whether this account is incoming secure.
	 *
	 * @param incomingSecure the incoming secure of this account
	 */
	@Override
	public void setIncomingSecure(boolean incomingSecure) {
		model.setIncomingSecure(incomingSecure);
	}

	/**
	 * Sets the login of this account.
	 *
	 * @param login the login of this account
	 */
	@Override
	public void setLogin(String login) {
		model.setLogin(login);
	}

	/**
	 * Sets the modified date of this account.
	 *
	 * @param modifiedDate the modified date of this account
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the outgoing host name of this account.
	 *
	 * @param outgoingHostName the outgoing host name of this account
	 */
	@Override
	public void setOutgoingHostName(String outgoingHostName) {
		model.setOutgoingHostName(outgoingHostName);
	}

	/**
	 * Sets the outgoing port of this account.
	 *
	 * @param outgoingPort the outgoing port of this account
	 */
	@Override
	public void setOutgoingPort(int outgoingPort) {
		model.setOutgoingPort(outgoingPort);
	}

	/**
	 * Sets whether this account is outgoing secure.
	 *
	 * @param outgoingSecure the outgoing secure of this account
	 */
	@Override
	public void setOutgoingSecure(boolean outgoingSecure) {
		model.setOutgoingSecure(outgoingSecure);
	}

	/**
	 * Sets the password of this account.
	 *
	 * @param password the password of this account
	 */
	@Override
	public void setPassword(String password) {
		model.setPassword(password);
	}

	@Override
	public void setPasswordDecrypted(String unencryptedPassword) {
		model.setPasswordDecrypted(unencryptedPassword);
	}

	/**
	 * Sets the personal name of this account.
	 *
	 * @param personalName the personal name of this account
	 */
	@Override
	public void setPersonalName(String personalName) {
		model.setPersonalName(personalName);
	}

	/**
	 * Sets the primary key of this account.
	 *
	 * @param primaryKey the primary key of this account
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the protocol of this account.
	 *
	 * @param protocol the protocol of this account
	 */
	@Override
	public void setProtocol(String protocol) {
		model.setProtocol(protocol);
	}

	/**
	 * Sets whether this account is save password.
	 *
	 * @param savePassword the save password of this account
	 */
	@Override
	public void setSavePassword(boolean savePassword) {
		model.setSavePassword(savePassword);
	}

	/**
	 * Sets the sent folder ID of this account.
	 *
	 * @param sentFolderId the sent folder ID of this account
	 */
	@Override
	public void setSentFolderId(long sentFolderId) {
		model.setSentFolderId(sentFolderId);
	}

	/**
	 * Sets the signature of this account.
	 *
	 * @param signature the signature of this account
	 */
	@Override
	public void setSignature(String signature) {
		model.setSignature(signature);
	}

	/**
	 * Sets the trash folder ID of this account.
	 *
	 * @param trashFolderId the trash folder ID of this account
	 */
	@Override
	public void setTrashFolderId(long trashFolderId) {
		model.setTrashFolderId(trashFolderId);
	}

	/**
	 * Sets the user ID of this account.
	 *
	 * @param userId the user ID of this account
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this account.
	 *
	 * @param userName the user name of this account
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this account.
	 *
	 * @param userUuid the user uuid of this account
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets whether this account is use signature.
	 *
	 * @param useSignature the use signature of this account
	 */
	@Override
	public void setUseSignature(boolean useSignature) {
		model.setUseSignature(useSignature);
	}

	@Override
	protected AccountWrapper wrap(Account account) {
		return new AccountWrapper(account);
	}

}