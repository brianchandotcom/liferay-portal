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

package com.liferay.oauth2.provider.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link OAuth2ApplicationScopeAliases}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2ApplicationScopeAliases
 * @generated
 */
public class OAuth2ApplicationScopeAliasesWrapper
	extends BaseModelWrapper<OAuth2ApplicationScopeAliases>
	implements ModelWrapper<OAuth2ApplicationScopeAliases>,
			   OAuth2ApplicationScopeAliases {

	public OAuth2ApplicationScopeAliasesWrapper(
		OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases) {

		super(oAuth2ApplicationScopeAliases);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put(
			"oAuth2ApplicationScopeAliasesId",
			getOAuth2ApplicationScopeAliasesId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("oAuth2ApplicationId", getOAuth2ApplicationId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long oAuth2ApplicationScopeAliasesId = (Long)attributes.get(
			"oAuth2ApplicationScopeAliasesId");

		if (oAuth2ApplicationScopeAliasesId != null) {
			setOAuth2ApplicationScopeAliasesId(oAuth2ApplicationScopeAliasesId);
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

		Long oAuth2ApplicationId = (Long)attributes.get("oAuth2ApplicationId");

		if (oAuth2ApplicationId != null) {
			setOAuth2ApplicationId(oAuth2ApplicationId);
		}
	}

	/**
	 * Returns the company ID of this o auth2 application scope aliases.
	 *
	 * @return the company ID of this o auth2 application scope aliases
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this o auth2 application scope aliases.
	 *
	 * @return the create date of this o auth2 application scope aliases
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the o auth2 application ID of this o auth2 application scope aliases.
	 *
	 * @return the o auth2 application ID of this o auth2 application scope aliases
	 */
	@Override
	public long getOAuth2ApplicationId() {
		return model.getOAuth2ApplicationId();
	}

	/**
	 * Returns the o auth2 application scope aliases ID of this o auth2 application scope aliases.
	 *
	 * @return the o auth2 application scope aliases ID of this o auth2 application scope aliases
	 */
	@Override
	public long getOAuth2ApplicationScopeAliasesId() {
		return model.getOAuth2ApplicationScopeAliasesId();
	}

	/**
	 * Returns the primary key of this o auth2 application scope aliases.
	 *
	 * @return the primary key of this o auth2 application scope aliases
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this o auth2 application scope aliases.
	 *
	 * @return the user ID of this o auth2 application scope aliases
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this o auth2 application scope aliases.
	 *
	 * @return the user name of this o auth2 application scope aliases
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this o auth2 application scope aliases.
	 *
	 * @return the user uuid of this o auth2 application scope aliases
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a o auth2 application scope aliases model instance should use the <code>OAuth2ApplicationScopeAliases</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this o auth2 application scope aliases.
	 *
	 * @param companyId the company ID of this o auth2 application scope aliases
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this o auth2 application scope aliases.
	 *
	 * @param createDate the create date of this o auth2 application scope aliases
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the o auth2 application ID of this o auth2 application scope aliases.
	 *
	 * @param oAuth2ApplicationId the o auth2 application ID of this o auth2 application scope aliases
	 */
	@Override
	public void setOAuth2ApplicationId(long oAuth2ApplicationId) {
		model.setOAuth2ApplicationId(oAuth2ApplicationId);
	}

	/**
	 * Sets the o auth2 application scope aliases ID of this o auth2 application scope aliases.
	 *
	 * @param oAuth2ApplicationScopeAliasesId the o auth2 application scope aliases ID of this o auth2 application scope aliases
	 */
	@Override
	public void setOAuth2ApplicationScopeAliasesId(
		long oAuth2ApplicationScopeAliasesId) {

		model.setOAuth2ApplicationScopeAliasesId(
			oAuth2ApplicationScopeAliasesId);
	}

	/**
	 * Sets the primary key of this o auth2 application scope aliases.
	 *
	 * @param primaryKey the primary key of this o auth2 application scope aliases
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this o auth2 application scope aliases.
	 *
	 * @param userId the user ID of this o auth2 application scope aliases
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this o auth2 application scope aliases.
	 *
	 * @param userName the user name of this o auth2 application scope aliases
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this o auth2 application scope aliases.
	 *
	 * @param userUuid the user uuid of this o auth2 application scope aliases
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected OAuth2ApplicationScopeAliasesWrapper wrap(
		OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases) {

		return new OAuth2ApplicationScopeAliasesWrapper(
			oAuth2ApplicationScopeAliases);
	}

}