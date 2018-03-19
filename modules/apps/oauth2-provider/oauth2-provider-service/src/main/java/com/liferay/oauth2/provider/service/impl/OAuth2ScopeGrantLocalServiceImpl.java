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
 * The implementation of the o auth2 scope grant local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.oauth2.provider.service.OAuth2ScopeGrantLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2ScopeGrantLocalServiceBaseImpl
 * @see com.liferay.oauth2.provider.service.OAuth2ScopeGrantLocalServiceUtil
 */
public class OAuth2ScopeGrantLocalServiceImpl
	extends OAuth2ScopeGrantLocalServiceBaseImpl {

	@Override
	public Collection<OAuth2ScopeGrant> findByA_BSN_C_T(
		String applicationName, String bundleSymbolicName, Long companyId,
		String tokenContent) {

		return oAuth2ScopeGrantFinder.findByA_BSN_C_T(
			applicationName, bundleSymbolicName, companyId, tokenContent);
	}

	public Collection<OAuth2ScopeGrant> findByToken(long tokenId) {
		return oAuth2ScopeGrantPersistence.findByToken(tokenId);
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Always use {@link com.liferay.oauth2.provider.service.OAuth2ScopeGrantLocalServiceUtil} to access the o auth2 scope grant local service.
	 */
	public Collection<OAuth2ScopeGrant> grantScopesToToken(
			String tokenContent, Collection<LiferayOAuth2Scope> scopes)
		throws DuplicateOAuth2ScopeGrantException,
			   NoSuchOAuth2AccessTokenException {

		if (scopes.isEmpty()) {
			return Collections.emptyList();
		}

		OAuth2AccessToken oAuth2AccessToken =
			oAuth2AccessTokenPersistence.fetchByTokenContent(tokenContent);

		if (oAuth2AccessToken == null) {
			throw new NoSuchOAuth2AccessTokenException(tokenContent);
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

			if (oAuth2ScopeGrantPersistence.countByA_B_C_T_S(
					applicationName, bundleSymbolicName, companyId,
					oAuth2AccessTokenId, scopeString) > 0) {

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