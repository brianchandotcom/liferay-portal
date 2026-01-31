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
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.site.navigation.admin.constants.SiteNavigationAdminPortletKeys;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.service.SiteNavigationMenuLocalService;
import com.liferay.site.navigation.test.util.SiteNavigationMenuTestUtil;

import java.io.File;

import org.hamcrest.CoreMatchers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jonathan McCann
 */
@RunWith(Arquillian.class)
public class SiteNavigationAdminPortletDataHandlerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testExportImportSiteNavigationMenu() throws Exception {
		SiteNavigationMenu siteNavigationMenu =
			SiteNavigationMenuTestUtil.addSiteNavigationMenu(_group);

		File larFile = _exportLayouts(false, _group.getGroupId());

		_siteNavigationMenuLocalService.deleteSiteNavigationMenu(
			siteNavigationMenu);

		_importLayouts(false, _group.getGroupId(), larFile);

		Assert.assertNotNull(
			_siteNavigationMenuLocalService.
				fetchSiteNavigationMenuByExternalReferenceCode(
					siteNavigationMenu.getExternalReferenceCode(),
					_group.getGroupId()));
	}

	@Test
	public void testExportImportSiteNavigationMenuDeletions() throws Exception {
		SiteNavigationMenu siteNavigationMenu1 =
			SiteNavigationMenuTestUtil.addSiteNavigationMenu(_group);

		SiteNavigationMenu siteNavigationMenu2 =
			SiteNavigationMenuTestUtil.addSiteNavigationMenu(_group);

		File larFile = _exportLayouts(false, _group.getGroupId());

		Group importedGroup = GroupTestUtil.addGroup();

		_importLayouts(false, importedGroup.getGroupId(), larFile);

		Assert.assertNotNull(
			_siteNavigationMenuLocalService.
				fetchSiteNavigationMenuByExternalReferenceCode(
					siteNavigationMenu1.getExternalReferenceCode(),
					importedGroup.getGroupId()));
		Assert.assertNotNull(
			_siteNavigationMenuLocalService.
				fetchSiteNavigationMenuByExternalReferenceCode(
					siteNavigationMenu2.getExternalReferenceCode(),
					importedGroup.getGroupId()));

		_siteNavigationMenuLocalService.deleteSiteNavigationMenu(
			siteNavigationMenu2);

		larFile = _exportLayouts(true, _group.getGroupId());

		_importLayouts(true, importedGroup.getGroupId(), larFile);

		Assert.assertNotNull(
			_siteNavigationMenuLocalService.
				fetchSiteNavigationMenuByExternalReferenceCode(
					siteNavigationMenu1.getExternalReferenceCode(),
					importedGroup.getGroupId()));
		Assert.assertNull(
			_siteNavigationMenuLocalService.
				fetchSiteNavigationMenuByExternalReferenceCode(
					siteNavigationMenu2.getExternalReferenceCode(),
					importedGroup.getGroupId()));
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
			portletDataHandler.getPortletId());
	}

	private File _exportLayouts(boolean deletions, long groupId)
		throws Exception {

		return _exportImportLocalService.exportLayoutsAsFile(
			_exportImportConfigurationLocalService.
				addDraftExportImportConfiguration(
					TestPropsValues.getUserId(),
					ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT,
					ExportImportConfigurationSettingsMapFactoryUtil.
						buildExportLayoutSettingsMap(
							TestPropsValues.getUser(), groupId, false,
							new long[0],
							HashMapBuilder.put(
								PortletDataHandlerKeys.DELETIONS,
								new String[] {Boolean.toString(deletions)}
							).put(
								PortletDataHandlerKeys.PORTLET_DATA,
								new String[] {Boolean.TRUE.toString()}
							).put(
								PortletDataHandlerKeys.PORTLET_DATA + "_" +
									SiteNavigationAdminPortletKeys.
										SITE_NAVIGATION_ADMIN,
								new String[] {Boolean.TRUE.toString()}
							).build())));
	}

	private void _importLayouts(boolean deletions, long groupId, File larFile)
		throws Exception {

		ExportImportConfiguration exportImportConfiguration =
			_exportImportConfigurationLocalService.
				addDraftExportImportConfiguration(
					TestPropsValues.getUserId(),
					ExportImportConfigurationConstants.TYPE_IMPORT_LAYOUT,
					ExportImportConfigurationSettingsMapFactoryUtil.
						buildImportLayoutSettingsMap(
							TestPropsValues.getUser(), groupId, false,
							new long[0],
							HashMapBuilder.put(
								PortletDataHandlerKeys.DELETIONS,
								new String[] {Boolean.toString(deletions)}
							).put(
								PortletDataHandlerKeys.PORTLET_DATA,
								new String[] {Boolean.TRUE.toString()}
							).put(
								PortletDataHandlerKeys.PORTLET_DATA + "_" +
									SiteNavigationAdminPortletKeys.
										SITE_NAVIGATION_ADMIN,
								new String[] {Boolean.TRUE.toString()}
							).build()));

		if (deletions) {
			_exportImportLocalService.importLayoutsDataDeletions(
				exportImportConfiguration, larFile);
		}

		_exportImportLocalService.importLayouts(
			exportImportConfiguration, larFile);
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