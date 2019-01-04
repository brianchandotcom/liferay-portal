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

package com.liferay.portal.kernel.model;

import aQute.bnd.annotation.ProviderType;

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
@ProviderType
public class AccountWrapper extends BaseModelWrapper<Account> implements Account,
	ModelWrapper<Account> {
	public AccountWrapper(Account account) {
		super(account);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("accountId", getAccountId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("parentAccountId", getParentAccountId());
		attributes.put("name", getName());
		attributes.put("legalName", getLegalName());
		attributes.put("legalId", getLegalId());
		attributes.put("legalType", getLegalType());
		attributes.put("sicCode", getSicCode());
		attributes.put("tickerSymbol", getTickerSymbol());
		attributes.put("industry", getIndustry());
		attributes.put("type", getType());
		attributes.put("size", getSize());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

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

		Long parentAccountId = (Long)attributes.get("parentAccountId");

		if (parentAccountId != null) {
			setParentAccountId(parentAccountId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String legalName = (String)attributes.get("legalName");

		if (legalName != null) {
			setLegalName(legalName);
		}

		String legalId = (String)attributes.get("legalId");

		if (legalId != null) {
			setLegalId(legalId);
		}

		String legalType = (String)attributes.get("legalType");

		if (legalType != null) {
			setLegalType(legalType);
		}

		String sicCode = (String)attributes.get("sicCode");

		if (sicCode != null) {
			setSicCode(sicCode);
		}

		String tickerSymbol = (String)attributes.get("tickerSymbol");

		if (tickerSymbol != null) {
			setTickerSymbol(tickerSymbol);
		}

		String industry = (String)attributes.get("industry");

		if (industry != null) {
			setIndustry(industry);
		}

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		String size = (String)attributes.get("size");

		if (size != null) {
			setSize(size);
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
	* Returns the industry of this account.
	*
	* @return the industry of this account
	*/
	@Override
	public String getIndustry() {
		return model.getIndustry();
	}

	/**
	* Returns the legal ID of this account.
	*
	* @return the legal ID of this account
	*/
	@Override
	public String getLegalId() {
		return model.getLegalId();
	}

	/**
	* Returns the legal name of this account.
	*
	* @return the legal name of this account
	*/
	@Override
	public String getLegalName() {
		return model.getLegalName();
	}

	/**
	* Returns the legal type of this account.
	*
	* @return the legal type of this account
	*/
	@Override
	public String getLegalType() {
		return model.getLegalType();
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
	* Returns the mvcc version of this account.
	*
	* @return the mvcc version of this account
	*/
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	* Returns the name of this account.
	*
	* @return the name of this account
	*/
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	* Returns the parent account ID of this account.
	*
	* @return the parent account ID of this account
	*/
	@Override
	public long getParentAccountId() {
		return model.getParentAccountId();
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
	* Returns the sic code of this account.
	*
	* @return the sic code of this account
	*/
	@Override
	public String getSicCode() {
		return model.getSicCode();
	}

	/**
	* Returns the size of this account.
	*
	* @return the size of this account
	*/
	@Override
	public String getSize() {
		return model.getSize();
	}

	/**
	* Returns the ticker symbol of this account.
	*
	* @return the ticker symbol of this account
	*/
	@Override
	public String getTickerSymbol() {
		return model.getTickerSymbol();
	}

	/**
	* Returns the type of this account.
	*
	* @return the type of this account
	*/
	@Override
	public String getType() {
		return model.getType();
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
	* Sets the industry of this account.
	*
	* @param industry the industry of this account
	*/
	@Override
	public void setIndustry(String industry) {
		model.setIndustry(industry);
	}

	/**
	* Sets the legal ID of this account.
	*
	* @param legalId the legal ID of this account
	*/
	@Override
	public void setLegalId(String legalId) {
		model.setLegalId(legalId);
	}

	/**
	* Sets the legal name of this account.
	*
	* @param legalName the legal name of this account
	*/
	@Override
	public void setLegalName(String legalName) {
		model.setLegalName(legalName);
	}

	/**
	* Sets the legal type of this account.
	*
	* @param legalType the legal type of this account
	*/
	@Override
	public void setLegalType(String legalType) {
		model.setLegalType(legalType);
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
	* Sets the mvcc version of this account.
	*
	* @param mvccVersion the mvcc version of this account
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	* Sets the name of this account.
	*
	* @param name the name of this account
	*/
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	* Sets the parent account ID of this account.
	*
	* @param parentAccountId the parent account ID of this account
	*/
	@Override
	public void setParentAccountId(long parentAccountId) {
		model.setParentAccountId(parentAccountId);
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
	* Sets the sic code of this account.
	*
	* @param sicCode the sic code of this account
	*/
	@Override
	public void setSicCode(String sicCode) {
		model.setSicCode(sicCode);
	}

	/**
	* Sets the size of this account.
	*
	* @param size the size of this account
	*/
	@Override
	public void setSize(String size) {
		model.setSize(size);
	}

	/**
	* Sets the ticker symbol of this account.
	*
	* @param tickerSymbol the ticker symbol of this account
	*/
	@Override
	public void setTickerSymbol(String tickerSymbol) {
		model.setTickerSymbol(tickerSymbol);
	}

	/**
	* Sets the type of this account.
	*
	* @param type the type of this account
	*/
	@Override
	public void setType(String type) {
		model.setType(type);
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

	@Override
	protected AccountWrapper wrap(Account account) {
		return new AccountWrapper(account);
	}
}