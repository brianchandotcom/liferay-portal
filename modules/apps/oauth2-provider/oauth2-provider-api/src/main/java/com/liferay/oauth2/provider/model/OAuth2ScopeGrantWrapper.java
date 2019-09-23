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

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link OAuth2ScopeGrant}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2ScopeGrant
 * @generated
 */
public class OAuth2ScopeGrantWrapper
	extends BaseModelWrapper<OAuth2ScopeGrant>
	implements ModelWrapper<OAuth2ScopeGrant>, OAuth2ScopeGrant {

	public OAuth2ScopeGrantWrapper(OAuth2ScopeGrant oAuth2ScopeGrant) {
		super(oAuth2ScopeGrant);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("oAuth2ScopeGrantId", getOAuth2ScopeGrantId());
		attributes.put("companyId", getCompanyId());
		attributes.put(
			"oAuth2ApplicationScopeAliasesId",
			getOAuth2ApplicationScopeAliasesId());
		attributes.put("applicationName", getApplicationName());
		attributes.put("bundleSymbolicName", getBundleSymbolicName());
		attributes.put("scope", getScope());
		attributes.put("scopeAliases", getScopeAliases());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long oAuth2ScopeGrantId = (Long)attributes.get("oAuth2ScopeGrantId");

		if (oAuth2ScopeGrantId != null) {
			setOAuth2ScopeGrantId(oAuth2ScopeGrantId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long oAuth2ApplicationScopeAliasesId = (Long)attributes.get(
			"oAuth2ApplicationScopeAliasesId");

		if (oAuth2ApplicationScopeAliasesId != null) {
			setOAuth2ApplicationScopeAliasesId(oAuth2ApplicationScopeAliasesId);
		}

		String applicationName = (String)attributes.get("applicationName");

		if (applicationName != null) {
			setApplicationName(applicationName);
		}

		String bundleSymbolicName = (String)attributes.get(
			"bundleSymbolicName");

		if (bundleSymbolicName != null) {
			setBundleSymbolicName(bundleSymbolicName);
		}

		String scope = (String)attributes.get("scope");

		if (scope != null) {
			setScope(scope);
		}

		String scopeAliases = (String)attributes.get("scopeAliases");

		if (scopeAliases != null) {
			setScopeAliases(scopeAliases);
		}
	}

	/**
	 * Returns the application name of this o auth2 scope grant.
	 *
	 * @return the application name of this o auth2 scope grant
	 */
	@Override
	public String getApplicationName() {
		return model.getApplicationName();
	}

	/**
	 * Returns the bundle symbolic name of this o auth2 scope grant.
	 *
	 * @return the bundle symbolic name of this o auth2 scope grant
	 */
	@Override
	public String getBundleSymbolicName() {
		return model.getBundleSymbolicName();
	}

	/**
	 * Returns the company ID of this o auth2 scope grant.
	 *
	 * @return the company ID of this o auth2 scope grant
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the o auth2 application scope aliases ID of this o auth2 scope grant.
	 *
	 * @return the o auth2 application scope aliases ID of this o auth2 scope grant
	 */
	@Override
	public long getOAuth2ApplicationScopeAliasesId() {
		return model.getOAuth2ApplicationScopeAliasesId();
	}

	/**
	 * Returns the o auth2 scope grant ID of this o auth2 scope grant.
	 *
	 * @return the o auth2 scope grant ID of this o auth2 scope grant
	 */
	@Override
	public long getOAuth2ScopeGrantId() {
		return model.getOAuth2ScopeGrantId();
	}

	/**
	 * Returns the primary key of this o auth2 scope grant.
	 *
	 * @return the primary key of this o auth2 scope grant
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the scope of this o auth2 scope grant.
	 *
	 * @return the scope of this o auth2 scope grant
	 */
	@Override
	public String getScope() {
		return model.getScope();
	}

	/**
	 * Returns the scope aliases of this o auth2 scope grant.
	 *
	 * @return the scope aliases of this o auth2 scope grant
	 */
	@Override
	public String getScopeAliases() {
		return model.getScopeAliases();
	}

	@Override
	public java.util.List<String> getScopeAliasesList() {
		return model.getScopeAliasesList();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a o auth2 scope grant model instance should use the <code>OAuth2ScopeGrant</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the application name of this o auth2 scope grant.
	 *
	 * @param applicationName the application name of this o auth2 scope grant
	 */
	@Override
	public void setApplicationName(String applicationName) {
		model.setApplicationName(applicationName);
	}

	/**
	 * Sets the bundle symbolic name of this o auth2 scope grant.
	 *
	 * @param bundleSymbolicName the bundle symbolic name of this o auth2 scope grant
	 */
	@Override
	public void setBundleSymbolicName(String bundleSymbolicName) {
		model.setBundleSymbolicName(bundleSymbolicName);
	}

	/**
	 * Sets the company ID of this o auth2 scope grant.
	 *
	 * @param companyId the company ID of this o auth2 scope grant
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the o auth2 application scope aliases ID of this o auth2 scope grant.
	 *
	 * @param oAuth2ApplicationScopeAliasesId the o auth2 application scope aliases ID of this o auth2 scope grant
	 */
	@Override
	public void setOAuth2ApplicationScopeAliasesId(
		long oAuth2ApplicationScopeAliasesId) {

		model.setOAuth2ApplicationScopeAliasesId(
			oAuth2ApplicationScopeAliasesId);
	}

	/**
	 * Sets the o auth2 scope grant ID of this o auth2 scope grant.
	 *
	 * @param oAuth2ScopeGrantId the o auth2 scope grant ID of this o auth2 scope grant
	 */
	@Override
	public void setOAuth2ScopeGrantId(long oAuth2ScopeGrantId) {
		model.setOAuth2ScopeGrantId(oAuth2ScopeGrantId);
	}

	/**
	 * Sets the primary key of this o auth2 scope grant.
	 *
	 * @param primaryKey the primary key of this o auth2 scope grant
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the scope of this o auth2 scope grant.
	 *
	 * @param scope the scope of this o auth2 scope grant
	 */
	@Override
	public void setScope(String scope) {
		model.setScope(scope);
	}

	/**
	 * Sets the scope aliases of this o auth2 scope grant.
	 *
	 * @param scopeAliases the scope aliases of this o auth2 scope grant
	 */
	@Override
	public void setScopeAliases(String scopeAliases) {
		model.setScopeAliases(scopeAliases);
	}

	@Override
	public void setScopeAliasesList(java.util.List<String> scopeAliasesList) {
		model.setScopeAliasesList(scopeAliasesList);
	}

	@Override
	protected OAuth2ScopeGrantWrapper wrap(OAuth2ScopeGrant oAuth2ScopeGrant) {
		return new OAuth2ScopeGrantWrapper(oAuth2ScopeGrant);
	}

}