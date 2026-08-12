/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.lar.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationSettingsMapFactoryUtil;
import com.liferay.exportimport.kernel.configuration.constants.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalService;
import com.liferay.exportimport.kernel.service.ExportImportLocalService;
import com.liferay.exportimport.report.constants.ExportImportReportEntryConstants;
import com.liferay.exportimport.report.model.ExportImportReportEntry;
import com.liferay.exportimport.report.service.ExportImportReportEntryLocalService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.File;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Petteri Karttunen
 */
@FeatureFlags(
	featureFlags = {
		@FeatureFlag(value = "LPD-57655"), @FeatureFlag("LPD-85946")
	}
)
@RunWith(Arquillian.class)
public class SiteImporterTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_companyGroup = _groupLocalService.getCompanyGroup(
			TestPropsValues.getCompanyId());

		_group = _addGroup();
	}

	@After
	public void tearDown() {
		FileUtil.delete(_file);
	}

	@Test
	@TestInfo("LPD-85946")
	public void testImportSites() throws Exception {
		Layout layout = LayoutTestUtil.addTypePortletLayout(_group);

		File file = _exportLayouts(_group);

		_deleteLayout(layout);

		_importLayouts(file, _getExternalReferenceCodes(_group));

		Assert.assertNotNull(
			_layoutLocalService.fetchLayoutByExternalReferenceCode(
				layout.getExternalReferenceCode(), _group.getGroupId()));
	}

	@Test
	@TestInfo("LPD-85946")
	public void testImportSitesTwice() throws Exception {
		LayoutTestUtil.addTypePortletLayout(_group);

		int layoutsCount = _layoutLocalService.getLayoutsCount(_group, false);

		File file = _exportLayouts(_group);

		_importLayouts(file, _getExternalReferenceCodes(_group));
		_importLayouts(file, _getExternalReferenceCodes(_group));

		Assert.assertEquals(
			layoutsCount, _layoutLocalService.getLayoutsCount(_group, false));
	}

	@Test
	@TestInfo("LPD-85946")
	public void testImportSitesWithChildSite() throws Exception {
		Group childGroup = _addGroup(_group.getGroupId());

		File file = _exportLayouts(_group, childGroup);

		_updateParentGroupId(childGroup, 0);

		_importLayouts(file, _getExternalReferenceCodes(_group, childGroup));

		Group importedChildGroup = _groupLocalService.getGroup(
			childGroup.getGroupId());

		Assert.assertEquals(
			_group.getGroupId(), importedChildGroup.getParentGroupId());
	}

	@Test
	@TestInfo("LPD-85946")
	public void testImportSitesWithMissingSite() throws Exception {
		String externalReferenceCode = RandomTestUtil.randomString();

		File file = _exportLayouts(_group);

		ExportImportConfiguration exportImportConfiguration = _importLayouts(
			file, _group.getExternalReferenceCode(), externalReferenceCode);

		List<ExportImportReportEntry> exportImportReportEntries =
			_exportImportReportEntryLocalService.getExportImportReportEntries(
				TestPropsValues.getCompanyId(),
				exportImportConfiguration.getExportImportConfigurationId());

		Assert.assertTrue(
			exportImportReportEntries.toString(),
			ListUtil.exists(
				exportImportReportEntries,
				exportImportReportEntry -> {
					if (Objects.equals(
							externalReferenceCode,
							exportImportReportEntry.
								getClassExternalReferenceCode()) &&
						(exportImportReportEntry.getType() ==
							ExportImportReportEntryConstants.TYPE_ERROR)) {

						return true;
					}

					return false;
				}));
	}

	@Test
	@TestInfo("LPD-85946")
	public void testImportSitesWithUnselectedSite() throws Exception {
		Group otherGroup = _addGroup();

		Layout layout = LayoutTestUtil.addTypePortletLayout(_group);
		Layout otherLayout = LayoutTestUtil.addTypePortletLayout(otherGroup);

		File file = _exportLayouts(_group, otherGroup);

		_deleteLayout(layout);
		_deleteLayout(otherLayout);

		_importLayouts(file, _getExternalReferenceCodes(_group));

		Assert.assertNotNull(
			_layoutLocalService.fetchLayoutByExternalReferenceCode(
				layout.getExternalReferenceCode(), _group.getGroupId()));

		Assert.assertNull(
			_layoutLocalService.fetchLayoutByExternalReferenceCode(
				otherLayout.getExternalReferenceCode(),
				otherGroup.getGroupId()));
	}

	private Group _addGroup() throws Exception {
		return _addGroup(GroupConstants.DEFAULT_PARENT_GROUP_ID);
	}

	private Group _addGroup(long parentGroupId) throws Exception {
		Group group = GroupTestUtil.addGroup(parentGroupId);

		_groups.add(0, group);

		return group;
	}

	private void _deleteLayout(Layout layout) throws Exception {
		_layoutLocalService.deleteLayout(
			layout,
			ServiceContextTestUtil.getServiceContext(
				layout.getGroupId(), TestPropsValues.getUserId()));
	}

	private File _exportLayouts(Group... groups) throws Exception {
		_file = _exportImportLocalService.exportLayoutsAsFile(
			_exportImportConfigurationLocalService.
				addDraftExportImportConfiguration(
					TestPropsValues.getUserId(),
					ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT,
					ExportImportConfigurationSettingsMapFactoryUtil.
						buildExportLayoutSettingsMap(
							TestPropsValues.getUser(),
							_companyGroup.getGroupId(), false, null,
							_getParameterMap(
								_getExternalReferenceCodes(groups)))));

		return _file;
	}

	private String[] _getExternalReferenceCodes(Group... groups) {
		String[] externalReferenceCodes = new String[groups.length];

		for (int i = 0; i < groups.length; i++) {
			externalReferenceCodes[i] = groups[i].getExternalReferenceCode();
		}

		return externalReferenceCodes;
	}

	private Map<String, String[]> _getParameterMap(
		String... siteExternalReferenceCodes) {

		Map<String, String[]> parameterMap = HashMapBuilder.put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_DATA,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_DATA_ALL,
			new String[] {Boolean.TRUE.toString()}
		).build();

		if (siteExternalReferenceCodes.length == 0) {
			return parameterMap;
		}

		parameterMap.put(
			PortletDataHandlerKeys.SITE_EXTERNAL_REFERENCE_CODES,
			siteExternalReferenceCodes);

		return parameterMap;
	}

	private ExportImportConfiguration _importLayouts(
			File file, String... siteExternalReferenceCodes)
		throws Exception {

		ExportImportConfiguration exportImportConfiguration =
			_exportImportConfigurationLocalService.
				addDraftExportImportConfiguration(
					TestPropsValues.getUserId(),
					ExportImportConfigurationConstants.TYPE_IMPORT_LAYOUT,
					ExportImportConfigurationSettingsMapFactoryUtil.
						buildImportLayoutSettingsMap(
							TestPropsValues.getUser(),
							_companyGroup.getGroupId(), false, null,
							_getParameterMap(siteExternalReferenceCodes)));

		_exportImportLocalService.importLayouts(
			exportImportConfiguration, file);

		return exportImportConfiguration;
	}

	private void _updateParentGroupId(Group group, long parentGroupId)
		throws Exception {

		_groupLocalService.updateGroup(
			group.getGroupId(), parentGroupId, group.getNameMap(),
			group.getDescriptionMap(), group.getType(), group.getTypeSettings(),
			group.isManualMembership(), group.getMembershipRestriction(),
			group.getFriendlyURL(), group.isInheritContent(), group.isActive(),
			null);
	}

	private Group _companyGroup;

	@Inject
	private ExportImportConfigurationLocalService
		_exportImportConfigurationLocalService;

	@Inject
	private ExportImportLocalService _exportImportLocalService;

	@Inject
	private ExportImportReportEntryLocalService
		_exportImportReportEntryLocalService;

	private File _file;
	private Group _group;

	@Inject
	private GroupLocalService _groupLocalService;

	@DeleteAfterTestRun
	private final List<Group> _groups = new ArrayList<>();

	@Inject
	private LayoutLocalService _layoutLocalService;

}