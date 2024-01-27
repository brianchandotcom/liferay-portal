/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.type.deployer.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.client.extension.model.ClientExtensionEntry;
import com.liferay.client.extension.service.ClientExtensionEntryLocalService;
import com.liferay.client.extension.type.CET;
import com.liferay.client.extension.type.deployer.CETDeployer;
import com.liferay.client.extension.type.factory.CETFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.ServiceRegistration;

/**
 * @author Anderson Luiz
 * @author Thiago Buarque
 */
@FeatureFlags("LPD-10773")
@RunWith(Arquillian.class)
public class CETDeployerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		User user = UserTestUtil.addUser();

		_clientExtensionEntry =
			_clientExtensionEntryLocalService.addClientExtensionEntry(
				"", user.getUserId(), "",
				HashMapBuilder.put(
					LocaleUtil.getDefault(), "Client Extension Name"
				).build(),
				"", "", ClientExtensionEntryConstants.TYPE_THEME_CSS, "");
	}

	@After
	public void tearDown() throws PortalException {
		_clientExtensionEntryLocalService.deleteClientExtensionEntry(
			_clientExtensionEntry.getClientExtensionEntryId());
	}

	@Test
	public void testThemeCSSCETServiceRegistration() throws PortalException {
		CET cet = _cetFactory.create(_clientExtensionEntry, false);

		List<ServiceRegistration<?>> serviceRegistrations = _cetDeployer.deploy(
			cet);

		Assert.assertEquals(
			serviceRegistrations.toString(), 1, serviceRegistrations.size());
	}

	@Inject
	private CETDeployer _cetDeployer;

	@Inject
	private CETFactory _cetFactory;

	private ClientExtensionEntry _clientExtensionEntry;

	@Inject
	private ClientExtensionEntryLocalService _clientExtensionEntryLocalService;

}