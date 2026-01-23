/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.categories.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.categories.admin.web.constants.AssetCategoriesAdminPortletKeys;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.model.AssetVocabularyGroupRel;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyGroupRelLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.depot.service.DepotEntryLocalServiceUtil;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationSettingsMapFactoryUtil;
import com.liferay.exportimport.kernel.configuration.constants.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.lar.DataLevel;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalService;
import com.liferay.exportimport.kernel.service.ExportImportLocalService;
import com.liferay.exportimport.kernel.service.StagingLocalServiceUtil;
import com.liferay.exportimport.kernel.staging.constants.StagingConstants;
import com.liferay.exportimport.report.constants.ExportImportReportEntryConstants;
import com.liferay.exportimport.report.model.ExportImportReportEntry;
import com.liferay.exportimport.report.service.ExportImportReportEntryLocalService;
import com.liferay.exportimport.test.util.lar.BasePortletDataHandlerTestCase;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.GroupServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.FeatureFlagTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.File;

import java.util.List;
import java.util.Objects;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Zoltan Csaszi
 */
@RunWith(Arquillian.class)
public class AssetCategoryPortletDataHandlerTest
	extends BasePortletDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@FeatureFlags(
		featureFlags = {
			@FeatureFlag("LPD-17564"), @FeatureFlag("LPD-35443"),
			@FeatureFlag("LPD-35914")
		}
	)
	@Test
	public void testAssetCategoryExportImportReportEntriesDuplicateExternalReferenceCode()
		throws Exception {

		FeatureFlagTestUtil.invokeFeatureFlagListeners(
			TestPropsValues.getCompanyId(), true, "LPD-35914");

		AssetVocabulary assetVocabulary = _addAssetVocabulary();

		AssetCategory assetCategory = _addAssetCategory(assetVocabulary);

		String originalExternalReferenceCode =
			assetCategory.getExternalReferenceCode();

		File larFile = _exportLayoutsAsFile();

		assetCategory.setExternalReferenceCode(RandomTestUtil.randomString());

		_assetCategoryLocalService.updateAssetCategory(assetCategory);

		ExportImportConfiguration exportImportConfiguration =
			_setUpExportImportConfiguration();

		_exportImportLocalService.importLayouts(
			exportImportConfiguration, larFile);

		List<ExportImportReportEntry> exportImportReportEntries =
			_exportImportReportEntryLocalService.getExportImportReportEntries(
				TestPropsValues.getCompanyId(),
				exportImportConfiguration.getExportImportConfigurationId());

		Assert.assertEquals(
			exportImportReportEntries.toString(), 1,
			exportImportReportEntries.size());
		Assert.assertTrue(
			ListUtil.exists(
				exportImportReportEntries,
				exportImportReportEntry ->
					Objects.equals(
						exportImportReportEntry.getClassExternalReferenceCode(),
						originalExternalReferenceCode) &&
					(exportImportReportEntry.getType() ==
						ExportImportReportEntryConstants.TYPE_ERROR)));
	}

	@FeatureFlags(
		featureFlags = {
			@FeatureFlag("LPD-17564"), @FeatureFlag("LPD-35443"),
			@FeatureFlag("LPD-35914")
		}
	)
	@Test
	public void testAssetVocabularyExportImportReportEntriesDuplicateTitle()
		throws Exception {

		FeatureFlagTestUtil.invokeFeatureFlagListeners(
			TestPropsValues.getCompanyId(), true, "LPD-35914");

		AssetVocabulary assetVocabulary = _addAssetVocabulary();

		String originalExternalReferenceCode =
			assetVocabulary.getExternalReferenceCode();

		File larFile = _exportLayoutsAsFile();

		_assetVocabularyLocalService.deleteVocabulary(assetVocabulary);

		_addAssetVocabulary();

		ExportImportConfiguration exportImportConfiguration =
			_setUpExportImportConfiguration();

		_exportImportLocalService.importLayouts(
			exportImportConfiguration, larFile);

		List<ExportImportReportEntry> exportImportReportEntries =
			_exportImportReportEntryLocalService.getExportImportReportEntries(
				TestPropsValues.getCompanyId(),
				exportImportConfiguration.getExportImportConfigurationId());

		Assert.assertEquals(
			exportImportReportEntries.toString(), 1,
			exportImportReportEntries.size());
		Assert.assertTrue(
			ListUtil.exists(
				exportImportReportEntries,
				exportImportReportEntry ->
					Objects.equals(
						exportImportReportEntry.getClassExternalReferenceCode(),
						originalExternalReferenceCode) &&
					(exportImportReportEntry.getType() ==
						ExportImportReportEntryConstants.TYPE_ERROR)));
	}

	@FeatureFlags(
		featureFlags = {
			@FeatureFlag("LPD-17564"), @FeatureFlag("LPD-35443"),
			@FeatureFlag("LPD-35914")
		}
	)
	@Test
	public void testExportImportAssetCategory() throws Exception {
		FeatureFlagTestUtil.invokeFeatureFlagListeners(
			TestPropsValues.getCompanyId(), true, "LPD-35914");

		AssetVocabulary assetVocabulary = _addAssetVocabulary();

		AssetCategory assetCategory = _addAssetCategory(assetVocabulary);

		File larFile = _exportLayoutsAsFile();

		_assetCategoryLocalService.deleteCategory(assetCategory);

		_assetVocabularyLocalService.deleteVocabulary(assetVocabulary);

		ExportImportConfiguration exportImportConfiguration =
			_setUpExportImportConfiguration();

		_exportImportLocalService.importLayouts(
			exportImportConfiguration, larFile);

		Assert.assertNotNull(
			_assetCategoryLocalService.
				fetchAssetCategoryByExternalReferenceCode(
					assetCategory.getExternalReferenceCode(),
					stagingGroup.getGroupId()));

		FeatureFlagTestUtil.invokeFeatureFlagListeners(
			TestPropsValues.getCompanyId(), false, "LPD-35914");
	}

	@FeatureFlags(
		featureFlags = {
			@FeatureFlag("LPD-17564"), @FeatureFlag("LPD-35443"),
			@FeatureFlag("LPD-35914")
		}
	)
	@Test
	public void testExportImportAssetVocabulary() throws Exception {
		FeatureFlagTestUtil.invokeFeatureFlagListeners(
			TestPropsValues.getCompanyId(), true, "LPD-35914");

		AssetVocabulary assetVocabulary = _addAssetVocabulary();

		File larFile = _exportLayoutsAsFile();

		_assetVocabularyLocalService.deleteVocabulary(assetVocabulary);

		ExportImportConfiguration exportImportConfiguration =
			_setUpExportImportConfiguration();

		_exportImportLocalService.importLayouts(
			exportImportConfiguration, larFile);

		Assert.assertNotNull(
			_assetVocabularyLocalService.
				fetchAssetVocabularyByExternalReferenceCode(
					assetVocabulary.getExternalReferenceCode(),
					stagingGroup.getGroupId()));

		FeatureFlagTestUtil.invokeFeatureFlagListeners(
			TestPropsValues.getCompanyId(), false, "LPD-35914");
	}

	@FeatureFlags(
		featureFlags = {
			@FeatureFlag(value = "LPD-11235"),
			@FeatureFlag(value = "LPD-17564"), @FeatureFlag(value = "LPD-34594")
		}
	)
	@Test
	public void testExportImportCMSAssetVocabulary() throws Exception {
		FeatureFlagTestUtil.invokeFeatureFlagListeners(
			TestPropsValues.getCompanyId(), true, "LPD-17564");

		Group originalStagingGroup = stagingGroup;

		try {
			Group cmsGroup = GroupServiceUtil.getGroup(
				TestPropsValues.getCompanyId(), GroupConstants.CMS);

			stagingGroup = cmsGroup;

			AssetVocabulary assetVocabulary = _addAssetVocabulary();

			DepotEntry depotEntry = _addStagedDepotEntry();

			Group depotGroup = depotEntry.getGroup();

			_assetVocabularyGroupRelLocalService.setAssetVocabularyGroupRels(
				assetVocabulary.getVocabularyId(),
				new long[] {depotGroup.getGroupId()});

			File larFile = _exportLayoutsAsFile();

			_assetVocabularyLocalService.deleteVocabulary(assetVocabulary);

			ExportImportConfiguration exportImportConfiguration =
				_setUpExportImportConfiguration();

			_exportImportLocalService.importLayouts(
				exportImportConfiguration, larFile);

			AssetVocabulary importedVocabulary =
				_assetVocabularyLocalService.
					fetchAssetVocabularyByExternalReferenceCode(
						assetVocabulary.getExternalReferenceCode(),
						cmsGroup.getGroupId());

			Assert.assertNotNull(importedVocabulary);

			List<AssetVocabularyGroupRel> assetVocabularyGroupRels =
				_assetVocabularyGroupRelLocalService.
					getAssetVocabularyGroupRelsByVocabularyId(
						importedVocabulary.getVocabularyId());

			Assert.assertTrue(
				ListUtil.exists(
					assetVocabularyGroupRels,
					assetVocabularyGroupRel ->
						assetVocabularyGroupRel.getGroupId() ==
							depotGroup.getGroupId()));
		}
		finally {
			stagingGroup = originalStagingGroup;
		}

		FeatureFlagTestUtil.invokeFeatureFlagListeners(
			TestPropsValues.getCompanyId(), false, "LPD-17564");
	}

	@Override
	protected void addStagedModels() throws Exception {
	}

	@Override
	protected DataLevel getDataLevel() {
		return DataLevel.SITE;
	}

	@Override
	protected String getPortletId() {
		return AssetCategoriesAdminPortletKeys.ASSET_CATEGORIES_ADMIN;
	}

	@Override
	protected boolean isDataPortalLevel() {
		return false;
	}

	@Override
	protected boolean isDataPortletInstanceLevel() {
		return false;
	}

	@Override
	protected boolean isDataSiteLevel() {
		return true;
	}

	private AssetCategory _addAssetCategory(AssetVocabulary assetVocabulary)
		throws Exception {

		return _assetCategoryLocalService.addCategory(
			TestPropsValues.getUserId(), stagingGroup.getGroupId(),
			RandomTestUtil.randomString(), assetVocabulary.getVocabularyId(),
			ServiceContextTestUtil.getServiceContext());
	}

	private AssetVocabulary _addAssetVocabulary() throws Exception {
		return _assetVocabularyLocalService.addVocabulary(
			TestPropsValues.getUserId(), stagingGroup.getGroupId(),
			"vocabulary", ServiceContextTestUtil.getServiceContext());
	}

	private DepotEntry _addStagedDepotEntry() throws Exception {
		DepotEntry depotEntry = _depotEntryLocalService.addDepotEntry(
			HashMapBuilder.put(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()
			).build(),
			HashMapBuilder.put(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()
			).build(),
			DepotConstants.TYPE_ASSET_LIBRARY,
			ServiceContextTestUtil.getServiceContext());

		return _enableLocalStaging(depotEntry);
	}

	private DepotEntry _enableLocalStaging(DepotEntry depotEntry)
		throws Exception {

		Group stagingGroup = _enableLocalStaging(depotEntry.getGroup());

		return DepotEntryLocalServiceUtil.fetchGroupDepotEntry(
			stagingGroup.getGroupId());
	}

	private Group _enableLocalStaging(Group group) throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		_setStagingAttributes(serviceContext);

		serviceContext.setAttribute("staging", Boolean.TRUE);

		StagingLocalServiceUtil.enableLocalStaging(
			TestPropsValues.getUserId(), group, false, false, serviceContext);

		return group.getStagingGroup();
	}

	private File _exportLayoutsAsFile() throws Exception {
		return _exportImportLocalService.exportLayoutsAsFile(
			_exportImportConfigurationLocalService.
				addDraftExportImportConfiguration(
					TestPropsValues.getUserId(),
					ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT,
					ExportImportConfigurationSettingsMapFactoryUtil.
						buildExportLayoutSettingsMap(
							TestPropsValues.getUser(),
							stagingGroup.getGroupId(), false, new long[0],
							HashMapBuilder.put(
								PortletDataHandlerKeys.PORTLET_DATA,
								new String[] {Boolean.TRUE.toString()}
							).put(
								PortletDataHandlerKeys.PORTLET_DATA + "_" +
									AssetCategoriesAdminPortletKeys.
										ASSET_CATEGORIES_ADMIN,
								new String[] {Boolean.TRUE.toString()}
							).build())));
	}

	private void _setStagingAttribute(
		ServiceContext serviceContext, String key) {

		serviceContext.setAttribute(
			StagingConstants.STAGED_PREFIX + key + StringPool.DOUBLE_DASH,
			String.valueOf(Boolean.TRUE));
	}

	private void _setStagingAttributes(ServiceContext serviceContext) {
		_setStagingAttribute(
			serviceContext, PortletDataHandlerKeys.DATA_STRATEGY_MIRROR);
		_setStagingAttribute(
			serviceContext, PortletDataHandlerKeys.PORTLET_CONFIGURATION_ALL);
		_setStagingAttribute(
			serviceContext, PortletDataHandlerKeys.PORTLET_DATA_ALL);
		_setStagingAttribute(
			serviceContext, PortletDataHandlerKeys.PORTLET_SETUP_ALL);
	}

	private ExportImportConfiguration _setUpExportImportConfiguration()
		throws Exception {

		return _exportImportConfigurationLocalService.
			addDraftExportImportConfiguration(
				TestPropsValues.getUserId(),
				ExportImportConfigurationConstants.TYPE_IMPORT_LAYOUT,
				ExportImportConfigurationSettingsMapFactoryUtil.
					buildImportLayoutSettingsMap(
						TestPropsValues.getUser(), stagingGroup.getGroupId(),
						false, new long[0],
						HashMapBuilder.put(
							PortletDataHandlerKeys.PORTLET_DATA,
							new String[] {Boolean.TRUE.toString()}
						).put(
							PortletDataHandlerKeys.PORTLET_DATA + "_" +
								AssetCategoriesAdminPortletKeys.
									ASSET_CATEGORIES_ADMIN,
							new String[] {Boolean.TRUE.toString()}
						).build()));
	}

	@Inject
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Inject
	private AssetVocabularyGroupRelLocalService
		_assetVocabularyGroupRelLocalService;

	@Inject
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

	@Inject
	private ExportImportConfigurationLocalService
		_exportImportConfigurationLocalService;

	@Inject
	private ExportImportLocalService _exportImportLocalService;

	@Inject
	private ExportImportReportEntryLocalService
		_exportImportReportEntryLocalService;

}