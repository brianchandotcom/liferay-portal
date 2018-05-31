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

import com.liferay.oauth2.provider.model.OAuth2ApplicationScopeAliases;
import com.liferay.oauth2.provider.model.OAuth2ScopeGrant;
import com.liferay.oauth2.provider.scope.liferay.LiferayOAuth2Scope;
import com.liferay.oauth2.provider.scope.liferay.ScopeLocator;
import com.liferay.oauth2.provider.service.base.OAuth2ApplicationScopeAliasesLocalServiceBaseImpl;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.osgi.framework.Bundle;

/**
 * @author Brian Wing Shun Chan
 */
public class OAuth2ApplicationScopeAliasesLocalServiceImpl
	extends OAuth2ApplicationScopeAliasesLocalServiceBaseImpl {

	@Override
	public OAuth2ApplicationScopeAliases addOAuth2ApplicationScopeAliases(
			long companyId, long userId, String userName,
			long oAuth2ApplicationId, List<String> scopeAliasesList)
		throws PortalException {

		long oAuth2ApplicationScopeAliasesId = counterLocalService.increment(
			OAuth2ApplicationScopeAliases.class.getName());

		OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases =
			createOAuth2ApplicationScopeAliases(
				oAuth2ApplicationScopeAliasesId);

		oAuth2ApplicationScopeAliases.setCompanyId(companyId);
		oAuth2ApplicationScopeAliases.setUserId(userId);
		oAuth2ApplicationScopeAliases.setUserName(userName);
		oAuth2ApplicationScopeAliases.setCreateDate(new Date());
		oAuth2ApplicationScopeAliases.setOAuth2ApplicationId(
			oAuth2ApplicationId);
		oAuth2ApplicationScopeAliases.setScopeAliasesList(scopeAliasesList);

		oAuth2ApplicationScopeAliases =
			oAuth2ApplicationScopeAliasesPersistence.update(
				oAuth2ApplicationScopeAliases);

		createScopeGrants(
			companyId, oAuth2ApplicationScopeAliasesId, scopeAliasesList);

		return oAuth2ApplicationScopeAliases;
	}

	@Override
	public OAuth2ApplicationScopeAliases deleteOAuth2ApplicationScopeAliases(
			long oAuth2ApplicationScopeAliasesId)
		throws PortalException {

		Collection<OAuth2ScopeGrant> oAuth2ScopeGrants =
			oAuth2ScopeGrantLocalService.getOAuth2ScopeGrants(
				oAuth2ApplicationScopeAliasesId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		for (OAuth2ScopeGrant oAuth2ScopeGrant : oAuth2ScopeGrants) {
			oAuth2ScopeGrantLocalService.deleteOAuth2ScopeGrant(
				oAuth2ScopeGrant.getOAuth2ScopeGrantId());
		}

		return oAuth2ApplicationScopeAliasesPersistence.remove(
			oAuth2ApplicationScopeAliasesId);
	}

	@Override
	public OAuth2ApplicationScopeAliases fetchOAuth2ApplicationScopeAliases(
		long oAuth2ApplicationId, List<String> scopeAliasesList) {

		scopeAliasesList = new ArrayList<>(scopeAliasesList);

		scopeAliasesList.sort(null);

		String scopeAliases = StringUtil.merge(
			scopeAliasesList, StringPool.SPACE);

		List<OAuth2ApplicationScopeAliases> oAuth2ApplicationScopeAliasesList =
			oAuth2ApplicationScopeAliasesPersistence.findByO_S(
				oAuth2ApplicationId, scopeAliases.hashCode());

		for (OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases :
				oAuth2ApplicationScopeAliasesList) {

			if (scopeAliases.equals(
					oAuth2ApplicationScopeAliases.getScopeAliases())) {

				return oAuth2ApplicationScopeAliases;
			}
		}

		return null;
	}

	@Override
	public List<OAuth2ApplicationScopeAliases>
		getOAuth2ApplicationScopeAliaseses(
			long oAuth2ApplicationId, int start, int end,
			OrderByComparator<OAuth2ApplicationScopeAliases>
				orderByComparator) {

		return oAuth2ApplicationScopeAliasesPersistence.
			findByOAuth2ApplicationId(
				oAuth2ApplicationId, start, end, orderByComparator);
	}

	protected void createScopeGrants(
			long companyId, long oAuth2ApplicationScopeAliasesId,
			List<String> scopeAliasesList)
		throws PortalException {

		Set<LiferayOAuth2Scope> liferayOAuth2Scopes = new HashSet<>();

		for (String scopeAlias : scopeAliasesList) {
			liferayOAuth2Scopes.addAll(
				_scopeLocator.getLiferayOAuth2Scopes(companyId, scopeAlias));
		}

		for (LiferayOAuth2Scope liferayOAuth2Scope : liferayOAuth2Scopes) {
			Bundle bundle = liferayOAuth2Scope.getBundle();

			oAuth2ScopeGrantLocalService.createOAuth2ScopeGrant(
				companyId, oAuth2ApplicationScopeAliasesId,
				liferayOAuth2Scope.getApplicationName(),
				bundle.getSymbolicName(), liferayOAuth2Scope.getScope());
		}
	}

	@ServiceReference(type = ScopeLocator.class)
	private ScopeLocator _scopeLocator;

}