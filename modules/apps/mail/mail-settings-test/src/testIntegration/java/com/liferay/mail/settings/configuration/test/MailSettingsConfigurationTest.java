/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mail.settings.configuration.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.mail.kernel.service.MailService;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import jakarta.mail.Session;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jiefeng Wu
 */
@RunWith(Arquillian.class)
public class MailSettingsConfigurationTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testGetSession() throws Exception {
		long companyId = RandomTestUtil.randomLong();

		_testGetSession(companyId, 1111);
		_testGetSession(companyId, 2222);

		_mailService.clearSession();
	}

	private void _testGetSession(long companyId, int port) throws Exception {
		ConfigurationTestUtil.saveConfiguration(
			"com.liferay.mail.settings.configuration." +
				"MailSettingCompanyConfiguration",
			HashMapDictionaryBuilder.<String, Object>put(
				"outgoingSMTPPort", port
			).build());

		Session session = _mailService.getSession(companyId);

		Assert.assertEquals(
			String.valueOf(port), session.getProperty("mail.smtp.port"));
	}

	@Inject
	private MailService _mailService;

}