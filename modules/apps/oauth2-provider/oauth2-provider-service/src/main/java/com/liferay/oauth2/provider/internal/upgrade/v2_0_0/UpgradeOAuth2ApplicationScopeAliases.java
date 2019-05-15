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

package com.liferay.oauth2.provider.internal.upgrade.v2_0_0;

import com.liferay.oauth2.provider.scope.liferay.LiferayOAuth2Scope;
import com.liferay.oauth2.provider.scope.liferay.ScopeLocator;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Stian Sigvartsen
 */
public class UpgradeOAuth2ApplicationScopeAliases extends UpgradeProcess {

	public UpgradeOAuth2ApplicationScopeAliases(
		CompanyLocalService companyLocalService, ScopeLocator scopeLocator) {

		_companyLocalService = companyLocalService;
		_scopeLocator = scopeLocator;
	}

	@Override
	protected void doUpgrade() throws Exception {
		for (Company company : _companyLocalService.getCompanies()) {
			upgradeCompany(company.getCompanyId());
		}
	}

	protected void upgradeCompany(long companyId) throws Exception {
		Map<LiferayOAuth2Scope, Set<String>> liferayOAuth2ScopeScopeAliases =
			new HashMap<>();

		List<String> scopeAliases = new ArrayList<>(
			_scopeLocator.getScopeAliases(companyId));

		for (String scopeAlias : scopeAliases) {
			for (LiferayOAuth2Scope liferayOAuth2Scope :
					_scopeLocator.getLiferayOAuth2Scopes(
						companyId, scopeAlias)) {

				Set<String> set =
					liferayOAuth2ScopeScopeAliases.computeIfAbsent(
						liferayOAuth2Scope, x -> new HashSet<>());

				set.add(scopeAlias);
			}
		}

		try (LoggingTimer loggingTimer = new LoggingTimer();
			ResultSet applicationScopeAliasesResultSet =
				_getApplicationScopeAliases(companyId)) {

			while (applicationScopeAliasesResultSet.next()) {
				String scopeAliasesString =
					applicationScopeAliasesResultSet.getString("scopeAliases");

				if (Validator.isNull(scopeAliasesString)) {
					continue;
				}

				long oAuth2ApplicationScopeAliasesId =
					applicationScopeAliasesResultSet.getLong(
						"oA2AScopeAliasesId");

				Set<String> assignedScopeAliases = SetUtil.fromArray(
					StringUtil.split(scopeAliasesString, StringPool.SPACE));

				_upgradeOAuth2ScopeGrants(
					oAuth2ApplicationScopeAliasesId, companyId,
					liferayOAuth2ScopeScopeAliases, assignedScopeAliases);
			}
		}
	}

	private ResultSet _getApplicationScopeAliases(long companyId)
		throws SQLException {

		String sql = StringBundler.concat(
			"select oa2asa.oA2AScopeAliasesId, oa2asa.scopeAliases from ",
			"OAuth2Application oa2a inner join OAuth2ApplicationScopeAliases ",
			"oa2asa on oa2a.oA2AScopeAliasesId = oa2asa.oA2AScopeAliasesId ",
			"where oa2a.companyId = ?");

		PreparedStatement preparedStatement = connection.prepareStatement(sql);

		preparedStatement.setLong(1, companyId);

		return preparedStatement.executeQuery();
	}

	private ResultSet _getOAuth2ScopeGrantResultSet(
			long oAuth2ApplicationScopeAliasesId)
		throws SQLException {

		String sql =
			"select * from OAuth2ScopeGrant where oA2AScopeAliasesId = ?";

		PreparedStatement preparedStatement = connection.prepareStatement(sql);

		preparedStatement.setLong(1, oAuth2ApplicationScopeAliasesId);

		return preparedStatement.executeQuery();
	}

	private void _updateOAuth2ScopeGrant(
			long oAuth2ScopeGrantId, Set<String> scopeAliases)
		throws SQLException {

		String sql =
			"update OAuth2ScopeGrant set scopeAliases = ? where " +
				"oAuth2ScopeGrantId = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				sql)) {

			preparedStatement.setString(
				1,
				StringUtil.merge(
					ListUtil.sort(new ArrayList<>(scopeAliases)),
					StringPool.SPACE));
			preparedStatement.setLong(2, oAuth2ScopeGrantId);

			preparedStatement.execute();
		}
	}

	private void _upgradeOAuth2ScopeGrants(
			long oAuth2ApplicationScopeAliasesId, long companyId,
			Map<LiferayOAuth2Scope, Set<String>>
				liferayOAuth2ScopesScopeAliases,
			Set<String> assignedScopeAliases)
		throws SQLException {

		try (ResultSet resultSet = _getOAuth2ScopeGrantResultSet(
				oAuth2ApplicationScopeAliasesId)) {

			while (resultSet.next()) {
				long oAuth2ScopeGrantId = resultSet.getLong(
					"oAuth2ScopeGrantId");

				String applicationName = resultSet.getString("applicationName");
				String scope = resultSet.getString("scope");

				LiferayOAuth2Scope liferayOAuth2Scope =
					_scopeLocator.getLiferayOAuth2Scope(
						companyId, applicationName, scope);

				if (liferayOAuth2Scope == null) {
					_updateOAuth2ScopeGrant(
						oAuth2ScopeGrantId, Collections.singleton("legacy"));

					continue;
				}

				Set<String> availableScopeAliases =
					liferayOAuth2ScopesScopeAliases.get(liferayOAuth2Scope);

				Set<String> assignedAvailableScopeAliases = new HashSet<>(
					assignedScopeAliases);

				assignedAvailableScopeAliases.retainAll(availableScopeAliases);

				if (assignedAvailableScopeAliases.isEmpty()) {
					_updateOAuth2ScopeGrant(
						oAuth2ScopeGrantId, Collections.singleton("legacy"));
				}
				else {
					_updateOAuth2ScopeGrant(
						oAuth2ScopeGrantId, assignedAvailableScopeAliases);
				}
			}
		}
	}

	private final CompanyLocalService _companyLocalService;
	private final ScopeLocator _scopeLocator;

}