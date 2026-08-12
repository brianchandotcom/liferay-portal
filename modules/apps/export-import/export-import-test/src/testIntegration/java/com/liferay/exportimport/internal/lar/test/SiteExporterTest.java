/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.lar.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationSettingsMapFactoryUtil;
import com.liferay.exportimport.kernel.configuration.constants.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalService;
import com.liferay.exportimport.kernel.service.ExportImportLocalService;
import com.liferay.exportimport.site.ExportImportSiteProvider;
import com.liferay.exportimport.site.LARSite;
import com.liferay.exportimport.site.LARSiteReader;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipReaderFactory;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
public class SiteExporterTest {

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
	public void testExportSites() throws Exception {
		File file = _exportCompanyLayouts(_group);

		List<LARSite> larSites = _getLARSites(file);

		Assert.assertEquals(larSites.toString(), 1, larSites.size());

		LARSite larSite = larSites.get(0);

		Assert.assertEquals(
			_group.getExternalReferenceCode(),
			larSite.getExternalReferenceCode());
		Assert.assertEquals(_group.getGroupId(), larSite.getGroupId());
		Assert.assertEquals(
			_exportImportSiteProvider.getDescriptiveName(
				_group,
				LocaleUtil.fromLanguageId(_group.getDefaultLanguageId())),
			larSite.getDescriptiveName());

		Assert.assertNull(larSite.getParentExternalReferenceCode());

		Assert.assertTrue(
			larSite.getPath(),
			larSite.getPath(
			).endsWith(
				_exportImportSiteProvider.getDescriptiveName(
					_group,
					LocaleUtil.fromLanguageId(_group.getDefaultLanguageId()))
			));
	}

	@Test
	@TestInfo("LPD-85946")
	public void testExportSitesWithChildSite() throws Exception {
		Group childGroup = _addGroup(_group.getGroupId());

		List<LARSite> larSites = _getLARSites(
			_exportCompanyLayouts(_group, childGroup));

		LARSite childLARSite = _getLARSite(
			larSites, childGroup.getExternalReferenceCode());

		Assert.assertEquals(
			_group.getExternalReferenceCode(),
			childLARSite.getParentExternalReferenceCode());

		LARSite larSite = _getLARSite(
			larSites, _group.getExternalReferenceCode());

		Assert.assertEquals(1, larSite.getChildSiteCount());
	}

	@Test
	@TestInfo("LPD-85946")
	public void testExportSitesWithMultipleLayouts() throws Exception {
		Group otherGroup = _addGroup();

		LayoutTestUtil.addTypePortletLayout(_group);
		LayoutTestUtil.addTypePortletLayout(_group);

		File file = _exportCompanyLayouts(_group, otherGroup);

		List<LARSite> larSites = _getLARSites(file);

		Assert.assertEquals(larSites.toString(), 2, larSites.size());

		Assert.assertNotNull(
			larSites.toString(),
			_getLARSite(larSites, _group.getExternalReferenceCode()));
		Assert.assertNotNull(
			larSites.toString(),
			_getLARSite(larSites, otherGroup.getExternalReferenceCode()));
	}

	@Test
	@TestInfo("LPD-85946")
	public void testExportSitesWithoutSelectedSites() throws Exception {
		File file = _exportCompanyLayouts();

		try (ZipReader zipReader = _zipReaderFactory.getZipReader(file)) {
			Assert.assertFalse(
				_getManifest(
					zipReader
				).contains(
					"<sites>"
				));
		}
	}

	@Test
	@TestInfo("LPD-85946")
	public void testExportSitesWithSelectedSite() throws Exception {
		File file = _exportCompanyLayouts(_group);

		try (ZipReader zipReader = _zipReaderFactory.getZipReader(file)) {
			String manifest = _getManifest(zipReader);

			Assert.assertTrue(manifest.contains("<sites>"));
			Assert.assertTrue(
				manifest.contains(
					"external-reference-code=\"" +
						_group.getExternalReferenceCode() + "\""));

			Assert.assertNotNull(
				zipReader.getEntryAsInputStream(_getSiteManifestPath(_group)));
		}
	}

	@Test
	@TestInfo("LPD-85946")
	public void testExportSitesWithSiteManifest() throws Exception {
		File file = _exportCompanyLayouts(_group);

		try (ZipReader zipReader = _zipReaderFactory.getZipReader(file)) {
			Assert.assertFalse(
				_getManifest(
					zipReader, _getSiteManifestPath(_group)
				).contains(
					"<sites>"
				));
		}
	}

	@Test
	@TestInfo("LPD-85946")
	public void testExportSitesWithUnselectedSite() throws Exception {
		Group otherGroup = _addGroup();

		File file = _exportCompanyLayouts(_group);

		try (ZipReader zipReader = _zipReaderFactory.getZipReader(file)) {
			String manifest = _getManifest(zipReader);

			Assert.assertFalse(
				manifest.contains(
					"external-reference-code=\"" +
						otherGroup.getExternalReferenceCode() + "\""));

			Assert.assertNull(
				zipReader.getEntryAsInputStream(
					_getSiteManifestPath(otherGroup)));
		}
	}

	private Group _addGroup() throws Exception {
		return _addGroup(GroupConstants.DEFAULT_PARENT_GROUP_ID);
	}

	private Group _addGroup(long parentGroupId) throws Exception {
		Group group = GroupTestUtil.addGroup(parentGroupId);

		_groups.add(0, group);

		return group;
	}

	private File _exportCompanyLayouts(Group... groups) throws Exception {
		_file = _exportImportLocalService.exportLayoutsAsFile(
			_exportImportConfigurationLocalService.
				addDraftExportImportConfiguration(
					TestPropsValues.getUserId(),
					ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT,
					ExportImportConfigurationSettingsMapFactoryUtil.
						buildExportLayoutSettingsMap(
							TestPropsValues.getUser(),
							_companyGroup.getGroupId(), false, null,
							_getExportParameterMap(groups))));

		return _file;
	}

	private Map<String, String[]> _getExportParameterMap(Group... groups) {
		Map<String, String[]> parameterMap = _getParameterMap();

		if (groups.length == 0) {
			return parameterMap;
		}

		String[] siteExternalReferenceCodes = new String[groups.length];

		for (int i = 0; i < groups.length; i++) {
			siteExternalReferenceCodes[i] =
				groups[i].getExternalReferenceCode();
		}

		parameterMap.put(
			PortletDataHandlerKeys.SITE_EXTERNAL_REFERENCE_CODES,
			siteExternalReferenceCodes);

		return parameterMap;
	}

	private LARSite _getLARSite(
		List<LARSite> larSites, String externalReferenceCode) {

		for (LARSite larSite : larSites) {
			if (externalReferenceCode.equals(
					larSite.getExternalReferenceCode())) {

				return larSite;
			}
		}

		return null;
	}

	private List<LARSite> _getLARSites(File file) throws Exception {
		FileEntry fileEntry = null;

		try (InputStream inputStream = new FileInputStream(file)) {
			fileEntry = _layoutService.addTempFileEntry(
				_companyGroup.getGroupId(), SiteExporterTest.class.getName(),
				RandomTestUtil.randomString() + ".lar", inputStream,
				ContentTypes.APPLICATION_ZIP);
		}

		try {
			return _larSiteReader.getLARSites(fileEntry);
		}
		finally {
			_layoutService.deleteTempFileEntry(
				_companyGroup.getGroupId(), SiteExporterTest.class.getName(),
				fileEntry.getFileName());
		}
	}

	private String _getManifest(ZipReader zipReader) throws Exception {
		return _getManifest(zipReader, "/manifest.xml");
	}

	private String _getManifest(ZipReader zipReader, String path)
		throws Exception {

		try (InputStream inputStream = zipReader.getEntryAsInputStream(path)) {
			Assert.assertNotNull(inputStream);

			return new String(inputStream.readAllBytes());
		}
	}

	private Map<String, String[]> _getParameterMap() {
		return HashMapBuilder.put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_DATA,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_DATA_ALL,
			new String[] {Boolean.TRUE.toString()}
		).build();
	}

	private String _getSiteManifestPath(Group group) {
		return StringBundler.concat(
			"/group/", group.getGroupId(), "/manifest.xml");
	}

	private Group _companyGroup;

	@Inject
	private ExportImportConfigurationLocalService
		_exportImportConfigurationLocalService;

	@Inject
	private ExportImportLocalService _exportImportLocalService;

	@Inject
	private ExportImportSiteProvider _exportImportSiteProvider;

	private File _file;
	private Group _group;

	@Inject
	private GroupLocalService _groupLocalService;

	@DeleteAfterTestRun
	private final List<Group> _groups = new ArrayList<>();

	@Inject
	private LARSiteReader _larSiteReader;

	@Inject
	private LayoutService _layoutService;

	@Inject
	private ZipReaderFactory _zipReaderFactory;

}