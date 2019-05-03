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

package com.liferay.oauth2.provider.internal.upgrade;

import com.liferay.oauth2.provider.internal.upgrade.v1_2_0.util.OAuth2AuthorizationTable;
import com.liferay.oauth2.provider.internal.upgrade.v2_0_0.UpgradeOAuth2ApplicationScopeAliases;
import com.liferay.oauth2.provider.internal.upgrade.v2_0_0.util.OAuth2ApplicationScopeAliasesTable;
import com.liferay.oauth2.provider.internal.upgrade.v2_0_0.util.OAuth2ApplicationTable;
import com.liferay.oauth2.provider.internal.upgrade.v2_0_0.util.OAuth2ScopeGrantTable;
import com.liferay.oauth2.provider.scope.liferay.ScopeLocator;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import java.util.Arrays;
import java.util.function.Function;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Sierra Andrés
 */
@Component(service = UpgradeStepRegistrator.class)
public class OAuth2ServiceUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"1.0.0", "1.1.0",
			new com.liferay.oauth2.provider.internal.upgrade.v1_1_0.
				UpgradeOAuth2ScopeGrant());

		registry.register(
			"1.1.0", "1.2.0",
			getAddColumnsUpgradeProcess(
				OAuth2AuthorizationTable.class,
				"remoteHostInfo VARCHAR(255) null"));

		registry.register(
			"1.2.0", "1.3.0",
			getAddColumnsUpgradeProcess(
				OAuth2ScopeGrantTable.class, "scopeAliases TEXT null"));

		registry.register(
			"1.3.0", "2.0.0",
			new UpgradeOAuth2ApplicationScopeAliases(
				_companyLocalService, _scopeLocator),
			getDropColumnsUpgradeProcess(
				OAuth2ApplicationScopeAliasesTable.class, "scopeAliases",
				"scopeAliasesHash"));

		registry.register(
			"2.0.0", "3.0.0",
			getAddColumnsUpgradeProcess(
				OAuth2ApplicationTable.class, "clientCredentialUserId long"),
			getAddColumnsUpgradeProcess(
				OAuth2ApplicationTable.class,
				"clientCredentialUserName VARCHAR(255) null"),
			getRunSQLUpgradeProcess(
				StringBundler.concat(
					"update OAuth2Application set ",
					"clientCredentialUserId=userId, clientCredentialUserName=",
					"userName")));
	}

	protected UpgradeProcess getAddColumnsUpgradeProcess(
		Class<?> tableClass, String... columnDefinitions) {

		return new UpgradeProcess() {

			@Override
			protected void doUpgrade() throws Exception {
				alter(
					tableClass,
					_getAlterables(
						AlterTableAddColumn::new, columnDefinitions));
			}

		};
	}

	protected UpgradeProcess getDropColumnsUpgradeProcess(
		Class<?> tableClass, String... tableNames) {

		return new UpgradeProcess() {

			@Override
			protected void doUpgrade() throws Exception {
				alter(
					tableClass,
					_getAlterables(AlterTableDropColumn::new, tableNames));
			}

		};
	}

	protected UpgradeProcess getRunSQLUpgradeProcess(String sql) {
		return new UpgradeProcess() {

			@Override
			protected void doUpgrade() throws Exception {
				runSQL(sql);
			}

		};
	}

	private UpgradeProcess.Alterable[] _getAlterables(
		Function<String, UpgradeProcess.Alterable> alterableFunction,
		String... alterableStrings) {

		return Arrays.stream(
			alterableStrings
		).map(
			alterableFunction
		).toArray(
			UpgradeProcess.Alterable[]::new
		);
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private ScopeLocator _scopeLocator;

}