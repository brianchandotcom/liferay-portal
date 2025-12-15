/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.internal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationSettingsMapFactoryUtil;
import com.liferay.exportimport.kernel.configuration.constants.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalService;
import com.liferay.exportimport.kernel.service.ExportImportLocalService;
import com.liferay.exportimport.portlet.data.handler.provider.PortletDataHandlerProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.FeatureFlagTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.site.navigation.admin.constants.SiteNavigationAdminPortletKeys;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.service.SiteNavigationMenuLocalService;
import com.liferay.site.navigation.test.util.SiteNavigationMenuTestUtil;

import java.io.File;

import org.hamcrest.CoreMatchers;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jonathan McCann
 */
@FeatureFlags(
	featureFlags = {
		@FeatureFlag("LPD-35443"), @FeatureFlag("LPD-35914"),
		@FeatureFlag("LPD-66179")
	}
)
@RunWith(Arquillian.class)
public class SiteNavigationAdminPortletDataHandlerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws PortalException {
		FeatureFlagTestUtil.invokeFeatureFlagListeners(
			TestPropsValues.getCompanyId(), true, "LPD-35914");
	}

	@AfterClass
	public static void tearDownClass() throws PortalException {
		FeatureFlagTestUtil.invokeFeatureFlagListeners(
			TestPropsValues.getCompanyId(), false, "LPD-35914");
	}

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testExportImportSiteNavigationMenu() throws Exception {
		SiteNavigationMenu siteNavigationMenu =
			SiteNavigationMenuTestUtil.addSiteNavigationMenu(_group);

		File larFile = _exportImportLocalService.exportLayoutsAsFile(
			_exportImportConfigurationLocalService.
				addDraftExportImportConfiguration(
					TestPropsValues.getUserId(),
					ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT,
					ExportImportConfigurationSettingsMapFactoryUtil.
						buildExportLayoutSettingsMap(
							TestPropsValues.getUser(), _group.getGroupId(),
							false, new long[0],
							HashMapBuilder.put(
								PortletDataHandlerKeys.PORTLET_DATA,
								new String[] {Boolean.TRUE.toString()}
							).put(
								PortletDataHandlerKeys.PORTLET_DATA + "_" +
									SiteNavigationAdminPortletKeys.
										SITE_NAVIGATION_ADMIN,
								new String[] {Boolean.TRUE.toString()}
							).build())));

		_siteNavigationMenuLocalService.deleteSiteNavigationMenu(
			siteNavigationMenu);

		ExportImportConfiguration exportImportConfiguration =
			_exportImportConfigurationLocalService.
				addDraftExportImportConfiguration(
					TestPropsValues.getUserId(),
					ExportImportConfigurationConstants.TYPE_IMPORT_LAYOUT,
					ExportImportConfigurationSettingsMapFactoryUtil.
						buildImportLayoutSettingsMap(
							TestPropsValues.getUser(), _group.getGroupId(),
							false, new long[0],
							HashMapBuilder.put(
								PortletDataHandlerKeys.PORTLET_DATA,
								new String[] {Boolean.TRUE.toString()}
							).put(
								PortletDataHandlerKeys.PORTLET_DATA + "_" +
									SiteNavigationAdminPortletKeys.
										SITE_NAVIGATION_ADMIN,
								new String[] {Boolean.TRUE.toString()}
							).build()));

		_exportImportLocalService.importLayouts(
			exportImportConfiguration, larFile);

		Assert.assertNotNull(
			_siteNavigationMenuLocalService.
				fetchSiteNavigationMenuByExternalReferenceCode(
					siteNavigationMenu.getExternalReferenceCode(),
					_group.getGroupId()));
	}

	@Test
	public void testPortletDataHandlerRegistration() throws Exception {
		PortletDataHandler portletDataHandler =
			_portletDataHandlerProvider.provide(
				TestPropsValues.getCompanyId(),
				SiteNavigationAdminPortletKeys.SITE_NAVIGATION_ADMIN);

		Assert.assertThat(
			ClassUtil.getClassName(portletDataHandler),
			CoreMatchers.containsString("BatchEnginePortletDataHandler"));
		Assert.assertEquals(
			SiteNavigationMenu.class.getName(),
			portletDataHandler.getClassNames()[0]);
		Assert.assertEquals(
			SiteNavigationAdminPortletKeys.SITE_NAVIGATION_ADMIN,
			portletDataHandler.getName());
	}

	@Inject
	private ExportImportConfigurationLocalService
		_exportImportConfigurationLocalService;

	@Inject
	private ExportImportLocalService _exportImportLocalService;

	private Group _group;

	@Inject
	private PortletDataHandlerProvider _portletDataHandlerProvider;

	@Inject
	private SiteNavigationMenuLocalService _siteNavigationMenuLocalService;

}