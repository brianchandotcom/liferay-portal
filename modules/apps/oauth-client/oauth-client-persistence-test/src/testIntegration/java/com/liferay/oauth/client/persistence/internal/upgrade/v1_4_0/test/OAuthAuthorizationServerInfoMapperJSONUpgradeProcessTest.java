/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.persistence.internal.upgrade.v1_4_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.oauth.client.persistence.model.OAuthClientASLocalMetadata;
import com.liferay.oauth.client.persistence.service.OAuthClientASLocalMetadataLocalService;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import com.nimbusds.oauth2.sdk.id.Issuer;
import com.nimbusds.openid.connect.sdk.SubjectType;
import com.nimbusds.openid.connect.sdk.op.OIDCProviderMetadata;

import java.net.URI;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alvaro Saugar
 */
@RunWith(Arquillian.class)
public class OAuthAuthorizationServerInfoMapperJSONUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testUpgrade() throws Exception {
		OAuthClientASLocalMetadata oAuthClientASLocalMetadata1 =
			_oAuthClientASLocalMetadataLocalService.
				createOAuthClientASLocalMetadata(
					_counterLocalService.increment());

		oAuthClientASLocalMetadata1 =
			_oAuthClientASLocalMetadataLocalService.
				updateOAuthClientASLocalMetadata(oAuthClientASLocalMetadata1);

		OAuthClientASLocalMetadata oAuthClientASLocalMetadata2 =
			_oAuthClientASLocalMetadataLocalService.
				createOAuthClientASLocalMetadata(
					_counterLocalService.increment());

		String issuerString = RandomTestUtil.randomString();

		Issuer issuer = new Issuer(issuerString);

		OIDCProviderMetadata oidcProviderMetadata = new OIDCProviderMetadata(
			issuer, List.of(SubjectType.PUBLIC),
			new URI(RandomTestUtil.randomString()));

		oAuthClientASLocalMetadata2.setMetadataJSON(
			oidcProviderMetadata.toJSONObject(
			).toJSONString());

		oAuthClientASLocalMetadata2 =
			_oAuthClientASLocalMetadataLocalService.
				updateOAuthClientASLocalMetadata(oAuthClientASLocalMetadata2);

		_runUpgrade();

		OAuthClientASLocalMetadata oAuthClientASLocalMetadata1AfterUpgrade =
			_oAuthClientASLocalMetadataLocalService.
				getOAuthClientASLocalMetadata(
					oAuthClientASLocalMetadata1.
						getOAuthClientASLocalMetadataId());

		Assert.assertTrue(
			Validator.isNull(
				oAuthClientASLocalMetadata1AfterUpgrade.getIssuer()));

		OAuthClientASLocalMetadata oAuthClientASLocalMetadata2AfterUpgrade =
			_oAuthClientASLocalMetadataLocalService.
				getOAuthClientASLocalMetadata(
					oAuthClientASLocalMetadata2.
						getOAuthClientASLocalMetadataId());

		Assert.assertEquals(
			issuerString, oAuthClientASLocalMetadata2AfterUpgrade.getIssuer());
	}

	private void _runUpgrade() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_CLASS_NAME, LoggerTestUtil.ALL)) {

			UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
				_upgradeStepRegistrator, _CLASS_NAME);

			upgradeProcess.upgrade();

			_multiVMPool.clear();
		}
	}

	private static final String _CLASS_NAME =
		"com.liferay.oauth.client.persistence.internal.upgrade.v1_4_0." +
			"OAuthAuthorizationServerInfoMapperJSONUpgradeProcess";

	@Inject(
		filter = "(&(component.name=com.liferay.oauth.client.persistence.internal.upgrade.registry.OAuthClientPersistenceServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@Inject
	private CounterLocalService _counterLocalService;

	@Inject
	private MultiVMPool _multiVMPool;

	@Inject
	private OAuthClientASLocalMetadataLocalService
		_oAuthClientASLocalMetadataLocalService;

}