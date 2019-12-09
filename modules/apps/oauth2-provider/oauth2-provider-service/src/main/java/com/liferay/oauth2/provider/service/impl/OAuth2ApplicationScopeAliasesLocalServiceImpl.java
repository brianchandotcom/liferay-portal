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

import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.model.OAuth2ApplicationScopeAliases;
import com.liferay.oauth2.provider.model.OAuth2ScopeGrant;
import com.liferay.oauth2.provider.scope.liferay.LiferayOAuth2Scope;
import com.liferay.oauth2.provider.scope.liferay.ScopeLocator;
import com.liferay.oauth2.provider.service.OAuth2ScopeGrantLocalService;
import com.liferay.oauth2.provider.service.base.OAuth2ApplicationScopeAliasesLocalServiceBaseImpl;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.framework.Bundle;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.oauth2.provider.model.OAuth2ApplicationScopeAliases",
	service = AopService.class
)
public class OAuth2ApplicationScopeAliasesLocalServiceImpl
	extends OAuth2ApplicationScopeAliasesLocalServiceBaseImpl {

	@Override
	public OAuth2ApplicationScopeAliases addOAuth2ApplicationScopeAliases(
			long companyId, long userId, String userName,
			long oAuth2ApplicationId, List<String> scopeAliasesList)
		throws PortalException {

		for (String scopeAlias : scopeAliasesList) {
			if (scopeAlias.contains(" ")) {
				throw new PortalException(
					"Scope aliases cannot contain spaces");
			}
		}

		Map<LiferayOAuth2Scope, List<String>> liferayOAuth2ScopesScopeAliases =
			_getLiferayOAuth2ScopesScopeAliases(companyId, scopeAliasesList);

		return _addOAuth2ApplicationScopeAliases(
			companyId, userId, userName, oAuth2ApplicationId,
			liferayOAuth2ScopesScopeAliases);
	}

	@Override
	public OAuth2ApplicationScopeAliases deleteOAuth2ApplicationScopeAliases(
			long oAuth2ApplicationScopeAliasesId)
		throws PortalException {

		Collection<OAuth2ScopeGrant> oAuth2ScopeGrants =
			_oAuth2ScopeGrantLocalService.getOAuth2ScopeGrants(
				oAuth2ApplicationScopeAliasesId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		for (OAuth2ScopeGrant oAuth2ScopeGrant : oAuth2ScopeGrants) {
			long[] oAuth2AuthorizationPrimaryKeys =
				_oAuth2ScopeGrantLocalService.getOAuth2AuthorizationPrimaryKeys(
					oAuth2ScopeGrant.getOAuth2ScopeGrantId());

			if (oAuth2AuthorizationPrimaryKeys.length > 0) {
				throw new PortalException(
					StringBundler.concat(
						"Cannot delete OAuth2ApplicationScopeAliases with ID ",
						oAuth2ApplicationScopeAliasesId,
						"because because there exists at least one ",
						"OAuth2Authorization which references its ",
						"OAuth2ScopeGrants"));
			}
		}

		for (OAuth2ScopeGrant oAuth2ScopeGrant : oAuth2ScopeGrants) {
			_oAuth2ScopeGrantLocalService.deleteOAuth2ScopeGrant(
				oAuth2ScopeGrant.getOAuth2ScopeGrantId());
		}

		return oAuth2ApplicationScopeAliasesPersistence.remove(
			oAuth2ApplicationScopeAliasesId);
	}

	@Override
	public OAuth2ApplicationScopeAliases fetchOAuth2ApplicationScopeAliases(
		long oAuth2ApplicationId, List<String> scopeAliasesList) {

		OAuth2Application oAuth2Application =
			oAuth2ApplicationPersistence.fetchByPrimaryKey(oAuth2ApplicationId);

		if (oAuth2Application == null) {
			return null;
		}

		Set<String> scopeAliases = new HashSet<>(scopeAliasesList);

		long oAuth2ApplicationScopeAliasesId =
			oAuth2Application.getOAuth2ApplicationScopeAliasesId();

		List<String> assignedScopeAliases = getScopeAliasesList(
			oAuth2ApplicationScopeAliasesId);

		if ((scopeAliases.size() == assignedScopeAliases.size()) &&
			assignedScopeAliases.containsAll(scopeAliases)) {

			return fetchOAuth2ApplicationScopeAliases(
				oAuth2ApplicationScopeAliasesId);
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

	@Override
	public List<String> getScopeAliasesList(
		long oAuth2ApplicationScopeAliasesId) {

		Collection<OAuth2ScopeGrant> oAuth2ScopeGrants =
			_oAuth2ScopeGrantLocalService.getOAuth2ScopeGrants(
				oAuth2ApplicationScopeAliasesId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		return _getScopeAliasesList(oAuth2ScopeGrants);
	}

	@Override
	public OAuth2ApplicationScopeAliases updateOAuth2ApplicationScopeAliases(
		OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases) {

		Collection<OAuth2ScopeGrant> oAuth2ScopeGrants =
			_oAuth2ScopeGrantLocalService.getOAuth2ScopeGrants(
				oAuth2ApplicationScopeAliases.
					getOAuth2ApplicationScopeAliasesId(),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		List<String> scopeAliasesList = _getScopeAliasesList(oAuth2ScopeGrants);

		Map<LiferayOAuth2Scope, List<String>> liferayOAuth2ScopesScopeAliases =
			_getLiferayOAuth2ScopesScopeAliases(
				oAuth2ApplicationScopeAliases.getCompanyId(), scopeAliasesList);

		if (_hasUpToDateScopeGrants(
				oAuth2ScopeGrants, liferayOAuth2ScopesScopeAliases)) {

			return oAuth2ApplicationScopeAliases;
		}

		try {
			return _addOAuth2ApplicationScopeAliases(
				oAuth2ApplicationScopeAliases.getCompanyId(),
				oAuth2ApplicationScopeAliases.getUserId(),
				oAuth2ApplicationScopeAliases.getUserName(),
				oAuth2ApplicationScopeAliases.getOAuth2ApplicationId(),
				liferayOAuth2ScopesScopeAliases);
		}
		catch (PortalException pe) {
			throw new IllegalArgumentException(pe);
		}
	}

	protected void createScopeGrants(
			long companyId, long oAuth2ApplicationScopeAliasesId,
			Map<LiferayOAuth2Scope, List<String>>
				liferayOAuth2ScopesScopeAliases)
		throws PortalException {

		for (Map.Entry<LiferayOAuth2Scope, List<String>> entry :
				liferayOAuth2ScopesScopeAliases.entrySet()) {

			LiferayOAuth2Scope liferayOAuth2Scope = entry.getKey();

			Bundle bundle = liferayOAuth2Scope.getBundle();

			_oAuth2ScopeGrantLocalService.createOAuth2ScopeGrant(
				companyId, oAuth2ApplicationScopeAliasesId,
				liferayOAuth2Scope.getApplicationName(),
				bundle.getSymbolicName(), liferayOAuth2Scope.getScope(),
				entry.getValue());
		}
	}

	private OAuth2ApplicationScopeAliases _addOAuth2ApplicationScopeAliases(
			long companyId, long userId, String userName,
			long oAuth2ApplicationId,
			Map<LiferayOAuth2Scope, List<String>>
				liferayOAuth2ScopesScopeAliases)
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

		oAuth2ApplicationScopeAliases =
			oAuth2ApplicationScopeAliasesPersistence.update(
				oAuth2ApplicationScopeAliases);

		createScopeGrants(
			companyId, oAuth2ApplicationScopeAliasesId,
			liferayOAuth2ScopesScopeAliases);

		return oAuth2ApplicationScopeAliases;
	}

	private Map<LiferayOAuth2Scope, List<String>>
		_getLiferayOAuth2ScopesScopeAliases(
			long companyId, List<String> scopeAliasesList) {

		Map<LiferayOAuth2Scope, List<String>> liferayOAuth2ScopesScopeAliases =
			new HashMap<>();

		for (String scopeAlias : ListUtil.sort(scopeAliasesList)) {
			for (LiferayOAuth2Scope liferayOAuth2Scope :
					_scopeLocator.getLiferayOAuth2Scopes(
						companyId, scopeAlias)) {

				List<String> scopeAliases =
					liferayOAuth2ScopesScopeAliases.computeIfAbsent(
						liferayOAuth2Scope, x -> new ArrayList<>());

				scopeAliases.add(scopeAlias);
			}
		}

		return liferayOAuth2ScopesScopeAliases;
	}

	private List<String> _getScopeAliasesList(
		Collection<OAuth2ScopeGrant> oAuth2ScopeGrants) {

		Stream<OAuth2ScopeGrant> stream = oAuth2ScopeGrants.stream();

		Set<String> scopeAliases = stream.flatMap(
			oa2sg -> oa2sg.getScopeAliasesList(
			).stream()
		).collect(
			Collectors.toSet()
		);

		return new ArrayList<>(scopeAliases);
	}

	private boolean _hasUpToDateScopeGrants(
		Collection<OAuth2ScopeGrant> oAuth2ScopeGrants,
		Map<LiferayOAuth2Scope, List<String>> liferayOAuth2ScopesScopeAliases) {

		if (liferayOAuth2ScopesScopeAliases.size() !=
				oAuth2ScopeGrants.size()) {

			return false;
		}

		oAuth2ScopeGrants = new ArrayList<>(oAuth2ScopeGrants);

		for (Map.Entry<LiferayOAuth2Scope, List<String>> entry :
				liferayOAuth2ScopesScopeAliases.entrySet()) {

			LiferayOAuth2Scope liferayOAuth2Scope = entry.getKey();
			List<String> scopeAliases = entry.getValue();
			Bundle bundle = liferayOAuth2Scope.getBundle();

			boolean found = oAuth2ScopeGrants.removeIf(
				oAuth2ScopeGrant -> {
					if (Objects.equals(
							liferayOAuth2Scope.getApplicationName(),
							oAuth2ScopeGrant.getApplicationName()) &&
						Objects.equals(
							bundle.getSymbolicName(),
							oAuth2ScopeGrant.getBundleSymbolicName()) &&
						Objects.equals(
							liferayOAuth2Scope.getScope(),
							oAuth2ScopeGrant.getScope())) {

						List<String> oAuth2ScopeGrantScopeAliases =
							oAuth2ScopeGrant.getScopeAliasesList();

						return oAuth2ScopeGrantScopeAliases.equals(
							scopeAliases);
					}

					return false;
				});

			if (!found) {
				return false;
			}
		}

		return true;
	}

	@Reference
	private OAuth2ScopeGrantLocalService _oAuth2ScopeGrantLocalService;

	@Reference
	private ScopeLocator _scopeLocator;

}