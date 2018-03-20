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

package com.liferay.oauth2.provider.service.impl;

import com.liferay.oauth2.provider.exception.DuplicateOAuth2ScopeGrantException;
import com.liferay.oauth2.provider.exception.NoSuchOAuth2AccessTokenException;
import com.liferay.oauth2.provider.model.OAuth2AccessToken;
import com.liferay.oauth2.provider.model.OAuth2ScopeGrant;
import com.liferay.oauth2.provider.scope.liferay.LiferayOAuth2Scope;
import com.liferay.oauth2.provider.service.base.OAuth2ScopeGrantLocalServiceBaseImpl;
import com.liferay.portal.kernel.util.StringBundler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.osgi.framework.Bundle;

/**
 * @author Brian Wing Shun Chan
 */
public class OAuth2ScopeGrantLocalServiceImpl
	extends OAuth2ScopeGrantLocalServiceBaseImpl {

	@Override
	public Collection<OAuth2ScopeGrant> getOAuth2ScopeGrants(
		long oAuth2AccessTokenId) {

		return oAuth2ScopeGrantPersistence.findByOAuth2AccessTokenId(
			oAuth2AccessTokenId);
	}

	@Override
	public Collection<OAuth2ScopeGrant> getOAuth2ScopeGrants(
		long companyId, String applicationName, String bundleSymbolicName,
		String tokenContent) {

		return oAuth2ScopeGrantFinder.findByC_A_B_T(
			companyId, applicationName, bundleSymbolicName, tokenContent);
	}

	@Override
	public Collection<OAuth2ScopeGrant> grantScopesToToken(
			String oAuth2AccessTokenContent,
			Collection<LiferayOAuth2Scope> scopes)
		throws DuplicateOAuth2ScopeGrantException,
			   NoSuchOAuth2AccessTokenException {

		if (scopes.isEmpty()) {
			return Collections.emptyList();
		}

		OAuth2AccessToken oAuth2AccessToken =
			oAuth2AccessTokenPersistence.fetchByTokenContent(
				oAuth2AccessTokenContent);

		if (oAuth2AccessToken == null) {
			throw new NoSuchOAuth2AccessTokenException(
				oAuth2AccessTokenContent);
		}

		long companyId = oAuth2AccessToken.getCompanyId();
		long oAuth2AccessTokenId = oAuth2AccessToken.getOAuth2AccessTokenId();

		Collection<OAuth2ScopeGrant> oAuth2ScopeGrants = new ArrayList<>(
			scopes.size());

		for (LiferayOAuth2Scope scope : scopes) {
			String applicationName = scope.getApplicationName();
			Bundle bundle = scope.getBundle();

			String bundleSymbolicName = bundle.getSymbolicName();

			String scopeString = scope.getScope();

			if (oAuth2ScopeGrantPersistence.countByC_O_A_B_S(
					companyId, oAuth2AccessTokenId, applicationName,
					bundleSymbolicName, scopeString) > 0) {

				StringBundler sb = new StringBundler(10);

				sb.append("Scope ");
				sb.append(scopeString);
				sb.append(" for application ");
				sb.append(applicationName);
				sb.append(" from bundle ");
				sb.append(bundleSymbolicName);
				sb.append(" in company ");
				sb.append(companyId);
				sb.append(" was already granted for token ");
				sb.append(oAuth2AccessTokenId);

				throw new DuplicateOAuth2ScopeGrantException(sb.toString());
			}

			long oAuth2ScopeGrantId = counterLocalService.increment(
				OAuth2ScopeGrant.class.getName());

			OAuth2ScopeGrant oAuth2ScopeGrant = createOAuth2ScopeGrant(
				oAuth2ScopeGrantId);

			oAuth2ScopeGrant.setApplicationName(applicationName);
			oAuth2ScopeGrant.setBundleSymbolicName(bundleSymbolicName);
			oAuth2ScopeGrant.setCompanyId(companyId);
			oAuth2ScopeGrant.setOAuth2AccessTokenId(oAuth2AccessTokenId);
			oAuth2ScopeGrant.setScope(scopeString);

			oAuth2ScopeGrant = updateOAuth2ScopeGrant(oAuth2ScopeGrant);

			oAuth2ScopeGrants.add(oAuth2ScopeGrant);
		}

		return oAuth2ScopeGrants;
	}

}