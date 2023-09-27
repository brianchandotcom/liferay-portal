/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.user.associated.data.anonymizer.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.user.associated.data.anonymizer.UADAnonymousUserProvider;

import java.util.Dictionary;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Erick Monteiro
 */
@RunWith(Arquillian.class)
public class UADAnonymousUserProviderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testIsAnonymousUser() throws Exception {
		User user = UserTestUtil.addUser();

		Assert.assertFalse(_uadAnonymousUserProvider.isAnonymousUser(user));

		Configuration configuration = _getConfiguration(
			TestPropsValues.getCompanyId());

		Assert.assertNotNull(configuration);

		Dictionary<String, Object> properties = configuration.getProperties();

		properties.put("userId", user.getUserId());

		configuration.update(properties);

		Assert.assertTrue(_uadAnonymousUserProvider.isAnonymousUser(user));
	}

	private Configuration _getConfiguration(long companyId) throws Exception {
		String filterString = String.format(
			"(&(service.factoryPid=com.liferay.user.associated.data.web." +
				"internal.configuration.AnonymousUserConfiguration.scoped)" +
					"(companyId=%s))",
			companyId);

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			filterString);

		if (configurations != null) {
			return configurations[0];
		}

		return null;
	}

	@Inject
	private ConfigurationAdmin _configurationAdmin;

	@Inject
	private UADAnonymousUserProvider _uadAnonymousUserProvider;

}