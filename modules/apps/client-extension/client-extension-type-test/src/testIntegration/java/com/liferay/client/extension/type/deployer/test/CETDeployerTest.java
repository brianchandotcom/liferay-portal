/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.type.deployer.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.client.extension.model.ClientExtensionEntry;
import com.liferay.client.extension.service.ClientExtensionEntryLocalService;
import com.liferay.client.extension.type.deployer.CETDeployer;
import com.liferay.client.extension.type.factory.CETFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.ServiceRegistration;

/**
 * @author Anderson Luiz
 * @author Thiago Buarque
 */
@FeatureFlags({"LPD-10773", "LPD-15804", "LPS-186870"})
@RunWith(Arquillian.class)
public class CETDeployerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testDeploy() throws Exception {
		User user = UserTestUtil.addUser();

		_testDeploy(
			1, ClientExtensionEntryConstants.TYPE_COMMERCE_CHECKOUT_STEP,
			"checkoutStepOrder=" + RandomTestUtil.randomInt(), user);
		_testDeploy(
			1, ClientExtensionEntryConstants.TYPE_THEME_CSS, StringPool.BLANK,
			user);

		String randomURL = "http://" + RandomTestUtil.randomString() + ".com";

		_testDeploy(
			2, ClientExtensionEntryConstants.TYPE_CUSTOM_ELEMENT,
			"htmlElementName=valid-name\nurls=" + randomURL, user);
		_testDeploy(
			1, ClientExtensionEntryConstants.TYPE_EDITOR_CONFIG_CONTRIBUTOR,
			"url=" + randomURL, user);
		_testDeploy(
			2, ClientExtensionEntryConstants.TYPE_IFRAME, "url=" + randomURL,
			user);
		_testDeploy(
			1, ClientExtensionEntryConstants.TYPE_JS_IMPORT_MAPS_ENTRY,
			StringBundler.concat(
				"bareSpecifier=", RandomTestUtil.randomString(), "\nurl=",
				randomURL),
			user);
	}

	private void _testDeploy(
			int expectedServiceRegistrationsSize, String type,
			String typeSettings, User user)
		throws Exception {

		ClientExtensionEntry clientExtensionEntry =
			_clientExtensionEntryLocalService.addClientExtensionEntry(
				StringPool.BLANK, user.getUserId(), StringPool.BLANK,
				HashMapBuilder.put(
					LocaleUtil.getDefault(), RandomTestUtil.randomString()
				).build(),
				StringPool.BLANK, StringPool.BLANK, type, typeSettings);

		List<ServiceRegistration<?>> serviceRegistrations = new ArrayList<>();

		try {
			serviceRegistrations = _cetDeployer.deploy(
				_cetFactory.create(clientExtensionEntry, false));

			Assert.assertEquals(
				serviceRegistrations.toString(),
				expectedServiceRegistrationsSize, serviceRegistrations.size());
		}
		finally {
			_clientExtensionEntryLocalService.deleteClientExtensionEntry(
				clientExtensionEntry.getClientExtensionEntryId());

			serviceRegistrations.forEach(ServiceRegistration::unregister);
		}
	}

	@Inject
	private CETDeployer _cetDeployer;

	@Inject
	private CETFactory _cetFactory;

	@Inject
	private ClientExtensionEntryLocalService _clientExtensionEntryLocalService;

}