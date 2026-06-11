/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.validator.vies.web.internal.portlet.action.test;

import com.liferay.account.validator.vies.configuration.VIESAccountEntryValidatorConfiguration;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.CompanyConfigurationTemporarySwapper;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.portlet.MockPortletSession;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.IOException;

import java.util.Dictionary;
import java.util.Hashtable;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Crescenzo Rega
 */
@RunWith(Arquillian.class)
@Sync
public class EditVIESAccountEntryValidatorConfigurationMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_company = _companyLocalService.getCompany(_group.getCompanyId());
		_layout = LayoutTestUtil.addTypePortletLayout(_group);
	}

	@Test
	public void testProcessAction() throws Exception {
		try (CompanyConfigurationTemporarySwapper
				companyConfigurationTemporarySwapper =
					new CompanyConfigurationTemporarySwapper(
						_company.getCompanyId(),
						VIESAccountEntryValidatorConfiguration.class.getName(),
						new Hashtable<>())) {

			MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
				new MockLiferayPortletActionRequest();

			int checkInterval = RandomTestUtil.randomInt();
			String[] countries = {
				RandomTestUtil.randomString(), RandomTestUtil.randomString()
			};
			String viesEndpointURL =
				"https://" + RandomTestUtil.randomString() + ".com";

			mockLiferayPortletActionRequest.setAttribute(
				WebKeys.PORTLET_ID,
				ConfigurationAdminPortletKeys.INSTANCE_SETTINGS);
			mockLiferayPortletActionRequest.setAttribute(
				WebKeys.THEME_DISPLAY, _getThemeDisplay());
			mockLiferayPortletActionRequest.setParameter(
				"checkInterval", String.valueOf(checkInterval));
			mockLiferayPortletActionRequest.setParameter(
				"countries", StringUtil.merge(countries, StringPool.COMMA));
			mockLiferayPortletActionRequest.setParameter("enabled", "true");
			mockLiferayPortletActionRequest.setParameter(
				"viesEndpointURL", viesEndpointURL);
			mockLiferayPortletActionRequest.setPortletSession(
				new MockPortletSession());

			_mvcActionCommand.processAction(
				mockLiferayPortletActionRequest,
				new MockLiferayPortletActionResponse());

			Configuration configuration = _getConfiguration(
				_company.getCompanyId());

			Dictionary<String, Object> properties =
				configuration.getProperties();

			Assert.assertEquals(
				checkInterval,
				GetterUtil.getInteger(properties.get("checkInterval")));
			Assert.assertArrayEquals(
				countries,
				GetterUtil.getStringValues(properties.get("countries")));
			Assert.assertTrue(GetterUtil.getBoolean(properties.get("enabled")));
			Assert.assertEquals(
				viesEndpointURL,
				GetterUtil.getString(properties.get("viesEndpointURL")));
		}
	}

	private Configuration _getConfiguration(long companyId) throws Exception {
		try {
			Configuration[] configurations =
				_configurationAdmin.listConfigurations(
					StringBundler.concat(
						"(&(", ConfigurationAdmin.SERVICE_FACTORYPID,
						StringPool.EQUAL,
						VIESAccountEntryValidatorConfiguration.class.getName(),
						".scoped)(companyId=", companyId, "))"));

			if (ArrayUtil.isNotEmpty(configurations)) {
				return configurations[0];
			}

			return null;
		}
		catch (InvalidSyntaxException | IOException exception) {
			throw new ConfigurationException(exception);
		}
	}

	private ThemeDisplay _getThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(_company);
		themeDisplay.setLayout(_layout);
		themeDisplay.setLayoutSet(_layout.getLayoutSet());
		themeDisplay.setLayoutTypePortlet(
			(LayoutTypePortlet)_layout.getLayoutType());
		themeDisplay.setLocale(LocaleUtil.US);
		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());
		themeDisplay.setPlid(_layout.getPlid());
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setSiteGroupId(_group.getGroupId());

		return themeDisplay;
	}

	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private ConfigurationAdmin _configurationAdmin;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

	@Inject(
		filter = "mvc.command.name=/instance_settings/edit_vies_account_entry_validator_configuration",
		type = MVCActionCommand.class
	)
	private MVCActionCommand _mvcActionCommand;

}