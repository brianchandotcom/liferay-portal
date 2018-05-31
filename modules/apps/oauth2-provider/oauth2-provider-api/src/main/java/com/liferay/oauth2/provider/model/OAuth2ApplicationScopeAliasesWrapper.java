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
 * This class is a wrapper for {@link OAuth2ApplicationScopeAliases}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2ApplicationScopeAliases
 * @generated
 */
@ProviderType
public class OAuth2ApplicationScopeAliasesWrapper
	implements OAuth2ApplicationScopeAliases,
		ModelWrapper<OAuth2ApplicationScopeAliases> {
	public OAuth2ApplicationScopeAliasesWrapper(
		OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases) {
		_oAuth2ApplicationScopeAliases = oAuth2ApplicationScopeAliases;
	}

	@Override
	public Class<?> getModelClass() {
		return OAuth2ApplicationScopeAliases.class;
	}

	@Override
	public String getModelClassName() {
		return OAuth2ApplicationScopeAliases.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("oAuth2ApplicationScopeAliasesId",
			getOAuth2ApplicationScopeAliasesId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("oAuth2ApplicationId", getOAuth2ApplicationId());
		attributes.put("scopeAliases", getScopeAliases());
		attributes.put("scopeAliasesHash", getScopeAliasesHash());

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

		String scopeAliases = (String)attributes.get("scopeAliases");

		if (scopeAliases != null) {
			setScopeAliases(scopeAliases);
		}

		Long scopeAliasesHash = (Long)attributes.get("scopeAliasesHash");

		if (scopeAliasesHash != null) {
			setScopeAliasesHash(scopeAliasesHash);
		}
	}

	@Override
	public Object clone() {
		return new OAuth2ApplicationScopeAliasesWrapper((OAuth2ApplicationScopeAliases)_oAuth2ApplicationScopeAliases.clone());
	}

	@Override
	public int compareTo(
		OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases) {
		return _oAuth2ApplicationScopeAliases.compareTo(oAuth2ApplicationScopeAliases);
	}

	/**
	* Returns the company ID of this o auth2 application scope aliases.
	*
	* @return the company ID of this o auth2 application scope aliases
	*/
	@Override
	public long getCompanyId() {
		return _oAuth2ApplicationScopeAliases.getCompanyId();
	}

	/**
	* Returns the create date of this o auth2 application scope aliases.
	*
	* @return the create date of this o auth2 application scope aliases
	*/
	@Override
	public Date getCreateDate() {
		return _oAuth2ApplicationScopeAliases.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _oAuth2ApplicationScopeAliases.getExpandoBridge();
	}

	/**
	* Returns the o auth2 application ID of this o auth2 application scope aliases.
	*
	* @return the o auth2 application ID of this o auth2 application scope aliases
	*/
	@Override
	public long getOAuth2ApplicationId() {
		return _oAuth2ApplicationScopeAliases.getOAuth2ApplicationId();
	}

	/**
	* Returns the o auth2 application scope aliases ID of this o auth2 application scope aliases.
	*
	* @return the o auth2 application scope aliases ID of this o auth2 application scope aliases
	*/
	@Override
	public long getOAuth2ApplicationScopeAliasesId() {
		return _oAuth2ApplicationScopeAliases.getOAuth2ApplicationScopeAliasesId();
	}

	/**
	* Returns the primary key of this o auth2 application scope aliases.
	*
	* @return the primary key of this o auth2 application scope aliases
	*/
	@Override
	public long getPrimaryKey() {
		return _oAuth2ApplicationScopeAliases.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _oAuth2ApplicationScopeAliases.getPrimaryKeyObj();
	}

	/**
	* Returns the scope aliases of this o auth2 application scope aliases.
	*
	* @return the scope aliases of this o auth2 application scope aliases
	*/
	@Override
	public String getScopeAliases() {
		return _oAuth2ApplicationScopeAliases.getScopeAliases();
	}

	/**
	* Returns the scope aliases hash of this o auth2 application scope aliases.
	*
	* @return the scope aliases hash of this o auth2 application scope aliases
	*/
	@Override
	public long getScopeAliasesHash() {
		return _oAuth2ApplicationScopeAliases.getScopeAliasesHash();
	}

	@Override
	public java.util.List<String> getScopeAliasesList() {
		return _oAuth2ApplicationScopeAliases.getScopeAliasesList();
	}

	/**
	* Returns the user ID of this o auth2 application scope aliases.
	*
	* @return the user ID of this o auth2 application scope aliases
	*/
	@Override
	public long getUserId() {
		return _oAuth2ApplicationScopeAliases.getUserId();
	}

	/**
	* Returns the user name of this o auth2 application scope aliases.
	*
	* @return the user name of this o auth2 application scope aliases
	*/
	@Override
	public String getUserName() {
		return _oAuth2ApplicationScopeAliases.getUserName();
	}

	/**
	* Returns the user uuid of this o auth2 application scope aliases.
	*
	* @return the user uuid of this o auth2 application scope aliases
	*/
	@Override
	public String getUserUuid() {
		return _oAuth2ApplicationScopeAliases.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _oAuth2ApplicationScopeAliases.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _oAuth2ApplicationScopeAliases.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _oAuth2ApplicationScopeAliases.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _oAuth2ApplicationScopeAliases.isNew();
	}

	@Override
	public void persist() {
		_oAuth2ApplicationScopeAliases.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_oAuth2ApplicationScopeAliases.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this o auth2 application scope aliases.
	*
	* @param companyId the company ID of this o auth2 application scope aliases
	*/
	@Override
	public void setCompanyId(long companyId) {
		_oAuth2ApplicationScopeAliases.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this o auth2 application scope aliases.
	*
	* @param createDate the create date of this o auth2 application scope aliases
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_oAuth2ApplicationScopeAliases.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_oAuth2ApplicationScopeAliases.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_oAuth2ApplicationScopeAliases.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_oAuth2ApplicationScopeAliases.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public void setNew(boolean n) {
		_oAuth2ApplicationScopeAliases.setNew(n);
	}

	/**
	* Sets the o auth2 application ID of this o auth2 application scope aliases.
	*
	* @param oAuth2ApplicationId the o auth2 application ID of this o auth2 application scope aliases
	*/
	@Override
	public void setOAuth2ApplicationId(long oAuth2ApplicationId) {
		_oAuth2ApplicationScopeAliases.setOAuth2ApplicationId(oAuth2ApplicationId);
	}

	/**
	* Sets the o auth2 application scope aliases ID of this o auth2 application scope aliases.
	*
	* @param oAuth2ApplicationScopeAliasesId the o auth2 application scope aliases ID of this o auth2 application scope aliases
	*/
	@Override
	public void setOAuth2ApplicationScopeAliasesId(
		long oAuth2ApplicationScopeAliasesId) {
		_oAuth2ApplicationScopeAliases.setOAuth2ApplicationScopeAliasesId(oAuth2ApplicationScopeAliasesId);
	}

	/**
	* Sets the primary key of this o auth2 application scope aliases.
	*
	* @param primaryKey the primary key of this o auth2 application scope aliases
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_oAuth2ApplicationScopeAliases.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_oAuth2ApplicationScopeAliases.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the scope aliases of this o auth2 application scope aliases.
	*
	* @param scopeAliases the scope aliases of this o auth2 application scope aliases
	*/
	@Override
	public void setScopeAliases(String scopeAliases) {
		_oAuth2ApplicationScopeAliases.setScopeAliases(scopeAliases);
	}

	/**
	* Sets the scope aliases hash of this o auth2 application scope aliases.
	*
	* @param scopeAliasesHash the scope aliases hash of this o auth2 application scope aliases
	*/
	@Override
	public void setScopeAliasesHash(long scopeAliasesHash) {
		_oAuth2ApplicationScopeAliases.setScopeAliasesHash(scopeAliasesHash);
	}

	@Override
	public void setScopeAliasesList(java.util.List<String> scopeAliasesList) {
		_oAuth2ApplicationScopeAliases.setScopeAliasesList(scopeAliasesList);
	}

	/**
	* Sets the user ID of this o auth2 application scope aliases.
	*
	* @param userId the user ID of this o auth2 application scope aliases
	*/
	@Override
	public void setUserId(long userId) {
		_oAuth2ApplicationScopeAliases.setUserId(userId);
	}

	/**
	* Sets the user name of this o auth2 application scope aliases.
	*
	* @param userName the user name of this o auth2 application scope aliases
	*/
	@Override
	public void setUserName(String userName) {
		_oAuth2ApplicationScopeAliases.setUserName(userName);
	}

	/**
	* Sets the user uuid of this o auth2 application scope aliases.
	*
	* @param userUuid the user uuid of this o auth2 application scope aliases
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_oAuth2ApplicationScopeAliases.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<OAuth2ApplicationScopeAliases> toCacheModel() {
		return _oAuth2ApplicationScopeAliases.toCacheModel();
	}

	@Override
	public OAuth2ApplicationScopeAliases toEscapedModel() {
		return new OAuth2ApplicationScopeAliasesWrapper(_oAuth2ApplicationScopeAliases.toEscapedModel());
	}

	@Override
	public String toString() {
		return _oAuth2ApplicationScopeAliases.toString();
	}

	@Override
	public OAuth2ApplicationScopeAliases toUnescapedModel() {
		return new OAuth2ApplicationScopeAliasesWrapper(_oAuth2ApplicationScopeAliases.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _oAuth2ApplicationScopeAliases.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof OAuth2ApplicationScopeAliasesWrapper)) {
			return false;
		}

		OAuth2ApplicationScopeAliasesWrapper oAuth2ApplicationScopeAliasesWrapper =
			(OAuth2ApplicationScopeAliasesWrapper)obj;

		if (Objects.equals(_oAuth2ApplicationScopeAliases,
					oAuth2ApplicationScopeAliasesWrapper._oAuth2ApplicationScopeAliases)) {
			return true;
		}

		return false;
	}

	@Override
	public OAuth2ApplicationScopeAliases getWrappedModel() {
		return _oAuth2ApplicationScopeAliases;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _oAuth2ApplicationScopeAliases.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _oAuth2ApplicationScopeAliases.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_oAuth2ApplicationScopeAliases.resetOriginalValues();
	}

	private final OAuth2ApplicationScopeAliases _oAuth2ApplicationScopeAliases;
}