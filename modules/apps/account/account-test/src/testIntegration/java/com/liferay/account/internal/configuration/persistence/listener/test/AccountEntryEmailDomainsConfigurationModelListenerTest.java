/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.configuration.persistence.listener.test;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.test.util.AccountEntryArgs;
import com.liferay.account.service.test.util.AccountEntryTestUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Dictionary;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Drew Brokke
 */
@RunWith(Arquillian.class)
public class AccountEntryEmailDomainsConfigurationModelListenerTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_companyId = TestPropsValues.getCompanyId();
	}

	@Test
	public void testOnAfterSave() throws Exception {
		String blockedDomain = "blocked.com";
		String validDomain = "valid.com";

		AccountEntry accountEntry = AccountEntryTestUtil.addAccountEntry(
			AccountEntryArgs.withDomains(blockedDomain, validDomain));

		Assert.assertTrue(
			ArrayUtil.contains(accountEntry.getDomainsArray(), blockedDomain));
		Assert.assertTrue(
			ArrayUtil.contains(accountEntry.getDomainsArray(), validDomain));

		_configurationModelListener.onAfterSave(
			StringPool.BLANK,
			_getProperties(new String[] {blockedDomain}, new String[0]));

		accountEntry = _accountEntryLocalService.getAccountEntry(
			accountEntry.getAccountEntryId());

		Assert.assertFalse(
			ArrayUtil.contains(accountEntry.getDomainsArray(), blockedDomain));
		Assert.assertTrue(
			ArrayUtil.contains(accountEntry.getDomainsArray(), validDomain));
	}

	@Test
	public void testOnBeforeSave() throws Exception {
		String[] blockedEmailAddressDomains = {
			"  blocked1.com  ", " blocked2.com", "BLOCKED3.COM", "blocked4.com"
		};

		Dictionary<String, Object> properties = _getProperties(
			blockedEmailAddressDomains, new String[0]);

		_configurationModelListener.onBeforeSave(StringPool.BLANK, properties);

		Assert.assertArrayEquals(
			new String[] {
				"blocked1.com", "blocked2.com", "blocked3.com", "blocked4.com"
			},
			_getBlockedEmailAddressDomains(
				GetterUtil.getString(properties.get("blockedEmailDomains"))));

		String[] invalidDomains = {"invalid", "foo.invalid"};

		properties = _getProperties(
			ArrayUtil.append(invalidDomains, "bar.valid"),
			new String[] {"valid"});

		try {
			_configurationModelListener.onBeforeSave(
				StringPool.BLANK, properties);

			Assert.fail();
		}
		catch (ConfigurationModelListenerException
					configurationModelListenerException) {

			Dictionary<String, Object> exceptionProperties =
				configurationModelListenerException.properties;

			Assert.assertArrayEquals(
				invalidDomains,
				GetterUtil.getStringValues(
					exceptionProperties.get("invalidDomains")));
		}
	}

	private String[] _getBlockedEmailAddressDomains(
		String blockedEmailAddressDomainsString) {

		return StringUtil.split(
			blockedEmailAddressDomainsString, StringPool.NEW_LINE);
	}

	private Dictionary<String, Object> _getProperties(
		String[] blockedEmailAddressDomains, String[] customTLDs) {

		return HashMapDictionaryBuilder.<String, Object>put(
			"blockedEmailDomains",
			StringUtil.merge(blockedEmailAddressDomains, StringPool.NEW_LINE)
		).put(
			"companyId", _companyId
		).put(
			"customTLDs", customTLDs
		).build();
	}

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	private long _companyId;

	@Inject(
		filter = "model.class.name=com.liferay.account.configuration.AccountEntryEmailDomainsConfiguration"
	)
	private ConfigurationModelListener _configurationModelListener;

}