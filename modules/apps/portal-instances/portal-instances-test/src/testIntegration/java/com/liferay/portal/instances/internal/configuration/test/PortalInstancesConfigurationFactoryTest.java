/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.instances.internal.configuration.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SynchronousMailTestRule;

import java.util.Dictionary;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Raymond Augé
 */
@RunWith(Arquillian.class)
public class PortalInstancesConfigurationFactoryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), SynchronousMailTestRule.INSTANCE);

	public void checkAdminUserCreation(Dictionary<String, Object> properties)
		throws Exception {

		ConfigurationTestUtil.saveConfiguration(_configuration, properties);

		String adminEmailAddress = (String)properties.get("adminEmailAddress");

		if (adminEmailAddress == null) {
			adminEmailAddress =
				PropsUtil.get(PropsKeys.DEFAULT_ADMIN_EMAIL_ADDRESS_PREFIX) +
					"@" + _domain;
		}

		String adminScreenName = (String)properties.get("adminScreenName");

		if (adminScreenName == null) {
			adminScreenName = PropsUtil.get(
				PropsKeys.DEFAULT_ADMIN_SCREEN_NAME);
		}

		String adminFirstName = (String)properties.get("adminFirstName");

		if (adminFirstName == null) {
			adminFirstName = PropsUtil.get(PropsKeys.DEFAULT_ADMIN_FIRST_NAME);
		}

		String adminMiddleName = (String)properties.get("adminMiddleName");

		if (adminMiddleName == null) {
			adminMiddleName = PropsUtil.get(
				PropsKeys.DEFAULT_ADMIN_MIDDLE_NAME);
		}

		String adminLastName = (String)properties.get("adminLastName");

		if (adminLastName == null) {
			adminLastName = PropsUtil.get(PropsKeys.DEFAULT_ADMIN_LAST_NAME);
		}

		_company = _companyLocalService.getCompanyByWebId(_webId);

		Assert.assertNotNull(_company);

		_adminUser = _userLocalService.getUserByEmailAddress(
			_company.getCompanyId(), adminEmailAddress);

		Assert.assertNotNull(_adminUser);

		Assert.assertEquals(
			StringUtil.toLowerCase(adminEmailAddress),
			_adminUser.getEmailAddress());

		Assert.assertEquals(
			StringUtil.toLowerCase(adminScreenName),
			_adminUser.getScreenName());

		Assert.assertEquals(adminFirstName, _adminUser.getFirstName());

		Assert.assertEquals(adminMiddleName, _adminUser.getMiddleName());

		Assert.assertEquals(adminLastName, _adminUser.getLastName());
	}

	@Before
	public void setUp() throws Exception {
		_webId = RandomTestUtil.randomString();

		_domain = _webId.concat(".foo.bar");

		_configuration = _configurationAdmin.getFactoryConfiguration(
			"com.liferay.portal.instances.internal.configuration." +
				"PortalInstancesConfiguration",
			_webId, StringPool.QUESTION);
	}

	@After
	public void tearDown() throws Exception {
		ConfigurationTestUtil.deleteConfiguration(_configuration);
	}

	@Test
	public void testAdminUserCreationWithAllProperties() throws Exception {
		checkAdminUserCreation(
			HashMapDictionaryBuilder.<String, Object>put(
				"adminEmailAddress", "testAdminEmailAddress@" + _domain
			).put(
				"adminFirstName", "testAdminFirstName"
			).put(
				"adminLastName", "testAdminLastName"
			).put(
				"adminMiddleName", "testAdminMiddleName"
			).put(
				"adminPassword", RandomTestUtil.randomString()
			).put(
				"adminScreenName", "testAdminScreenName"
			).put(
				"mx", _domain
			).put(
				"virtualHostname", _domain
			).build());
	}

	@Test
	public void testAdminUserCreationWithDefaultProperties() throws Exception {
		checkAdminUserCreation(
			HashMapDictionaryBuilder.<String, Object>put(
				"mx", _domain
			).put(
				"virtualHostname", _domain
			).build());
	}

	@Test
	public void testAdminUserCreationWithPartialProperties() throws Exception {
		checkAdminUserCreation(
			HashMapDictionaryBuilder.<String, Object>put(
				"adminFirstName", "testAdminFirstName"
			).put(
				"adminLastName", "testAdminLastName"
			).put(
				"adminPassword", RandomTestUtil.randomString()
			).put(
				"mx", _domain
			).put(
				"virtualHostname", _domain
			).build());
	}

	@Test
	public void testVirtualInstanceCreation() throws Exception {
		ConfigurationTestUtil.saveConfiguration(
			_configuration,
			HashMapDictionaryBuilder.<String, Object>put(
				"mx", _domain
			).put(
				"virtualHostname", _domain
			).build());

		_company = _companyLocalService.getCompanyByWebId(_webId);

		Assert.assertNotNull(_company);
	}

	@DeleteAfterTestRun
	private User _adminUser;

	@DeleteAfterTestRun
	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	private Configuration _configuration;

	@Inject
	private ConfigurationAdmin _configurationAdmin;

	private String _domain;

	@Inject
	private UserLocalService _userLocalService;

	private String _webId;

}