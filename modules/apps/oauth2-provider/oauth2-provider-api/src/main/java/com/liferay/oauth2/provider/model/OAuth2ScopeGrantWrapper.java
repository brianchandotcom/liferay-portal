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

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

/**
 * <p>
 * This class is a wrapper for {@link OAuth2ScopeGrant}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2ScopeGrant
 * @generated
 */
@ProviderType
public class OAuth2ScopeGrantWrapper extends BaseModelWrapper<OAuth2ScopeGrant>
	implements OAuth2ScopeGrant, ModelWrapper<OAuth2ScopeGrant> {
	public OAuth2ScopeGrantWrapper(OAuth2ScopeGrant oAuth2ScopeGrant) {
		super(oAuth2ScopeGrant);
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
		model.setOAuth2ApplicationScopeAliasesId(oAuth2ApplicationScopeAliasesId);
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

	@Override
	protected OAuth2ScopeGrantWrapper wrap(OAuth2ScopeGrant oAuth2ScopeGrant) {
		return new OAuth2ScopeGrantWrapper(oAuth2ScopeGrant);
	}
}