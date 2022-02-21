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

import com.liferay.oauth2.provider.internal.upgrade.v2_0_0.OAuth2ApplicationScopeAliasesUpgradeProcess;
import com.liferay.oauth2.provider.internal.upgrade.v3_2_0.OAuth2ApplicationFeatureUpgradeProcess;
import com.liferay.oauth2.provider.internal.upgrade.v4_0_1.OAuth2ApplicationAllowedGrantTypesUpgradeProcess;
import com.liferay.oauth2.provider.scope.liferay.ScopeLocator;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.step.util.UpgradeStepFactory;

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
				OAuth2ScopeGrantUpgradeProcess());

		registry.register(
			"1.1.0", "1.2.0",
			UpgradeStepFactory.addColumns(
				"OAuth2Authorization", "remoteHostInfo VARCHAR(255) null"));

		registry.register(
			"1.2.0", "1.3.0",
			UpgradeStepFactory.addColumns(
				"OAuth2ScopeGrant", "scopeAliases TEXT null"));

		registry.register(
			"1.3.0", "2.0.0",
			new OAuth2ApplicationScopeAliasesUpgradeProcess(
				_companyLocalService, _scopeLocator),
			UpgradeStepFactory.dropColumns(
				"OAuth2ApplicationScopeAliases", "scopeAliases",
				"scopeAliasesHash"));

		registry.register(
			"2.0.0", "3.0.0",
			UpgradeStepFactory.addColumns(
				"OAuth2Application", "clientCredentialUserId LONG"),
			UpgradeStepFactory.addColumns(
				"OAuth2Application",
				"clientCredentialUserName VARCHAR(75) null"),
			UpgradeStepFactory.runSQL(
				"update OAuth2Application set clientCredentialUserId = " +
					"userId, clientCredentialUserName = userName"));

		registry.register(
			"3.0.0", "3.1.0",
			UpgradeStepFactory.addColumns(
				"OAuth2Application", "rememberDevice BOOLEAN"),
			UpgradeStepFactory.addColumns(
				"OAuth2Application", "trustedApplication BOOLEAN"),
			UpgradeStepFactory.addColumns(
				"OAuth2Authorization",
				"rememberDeviceContent VARCHAR(75) null"));

		registry.register(
			"3.1.0", "4.0.0", new OAuth2ApplicationFeatureUpgradeProcess());

		registry.register(
			"4.0.0", "4.0.1",
			new OAuth2ApplicationAllowedGrantTypesUpgradeProcess());
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private ScopeLocator _scopeLocator;

}