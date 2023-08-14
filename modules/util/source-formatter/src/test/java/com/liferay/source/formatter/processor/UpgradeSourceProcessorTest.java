/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.processor;

import com.liferay.petra.string.StringBundler;
import com.liferay.source.formatter.SourceFormatterArgs;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * @author Kevin Lee
 */
public class UpgradeSourceProcessorTest extends BaseSourceProcessorTestCase {

	@Test
	public void testGradleUpgradeReleaseDxpCheck() throws Exception {
		test("upgrade/GradleUpgradeReleaseDxpCheck.testgradle");
	}

	@Test
	public void testPropertiesUpgradeLiferayPluginPackageFileCheck()
		throws Exception {

		test("upgrade/liferay-plugin-package.testproperties");
	}

	@Test
	public void testUpgradeBNDIncludeResourceCheck() throws Exception {
		test("upgrade/upgrade-include-resource-check/bnd.testbnd");
	}

	@Test
	public void testUpgradeDLUtilCheck() throws Exception {
		test("upgrade/UpgradeJavaDLUtilCheck.testjava");
		test("upgrade/UpgradeJSPDLUtilCheck.testjsp");
	}

	@Test
	public void testUpgradeGetClassNamesMethodCheck() throws Exception {
		test("upgrade/UpgradeJavaGetClassNamesMethodCheck.testjava");
		test("upgrade/UpgradeJSPFGetClassNamesMethodCheck.testjspf");
	}

	@Test
	public void testUpgradeGetImagePreviewURLMethodCheck() throws Exception {
		test("upgrade/UpgradeJavaGetImagePreviewURLMethodCheck.testjava");
		test("upgrade/UpgradeJSPGetImagePreviewURLMethodCheck.testjsp");
	}

	@Test
	public void testUpgradeGetPortletGroupIdMethodCheck() throws Exception {
		test("upgrade/UpgradeFTLGetPortletGroupIdMethodCheck.testftl");
		test("upgrade/UpgradeJavaGetPortletGroupIdMethodCheck.testjava");
		test("upgrade/UpgradeJSPGetPortletGroupIdMethodCheck.testjsp");
	}

	@Test
	public void testUpgradeGradleIncludeResourceCheck() throws Exception {
		test(
			SourceProcessorTestParameters.create(
				"upgrade/upgrade-include-resource-check/build.testgradle"
			).addDependentFileName(
				"upgrade/upgrade-include-resource-check/bnd.testbnd"
			));
	}

	@Test
	public void testUpgradeJavaAddFDSTableSchemaFieldCheck() throws Exception {
		test("upgrade/UpgradeJavaAddFDSTableSchemaFieldCheck.testjava");
	}

	@Test
	public void testUpgradeJavaAddFolderParameterCheck() throws Exception {
		test("upgrade/UpgradeJavaAddFolderParameterCheck.testjava");
	}

	@Test
	public void testUpgradeJavaAssetEntryAssetCategoriesCheck()
		throws Exception {

		test("upgrade/UpgradeJavaAssetEntryAssetCategoriesCheck.testjava");
	}

	@Test
	public void testUpgradeJavaCheck() throws Exception {
		test("upgrade/UpgradeJavaCheck.testjava");
	}

	@Test
	public void testUpgradeJavaCommerceCountryCheck() throws Exception {
		test("upgrade/UpgradeJavaCommerceCountryCheck.testjava");
	}

	@Test
	public void testUpgradeJavaCommerceCountryServiceCheck() throws Exception {
		test("upgrade/UpgradeJavaCommerceCountryServiceCheck.testjava");
	}

	@Test
	public void testUpgradeJavaCommerceRegionCheck() throws Exception {
		test("upgrade/UpgradeJavaCommerceRegionCheck.testjava");
	}

	@Test
	public void testUpgradeJavaCommerceShippingOptionCheck() throws Exception {
		test("upgrade/UpgradeJavaCommerceShippingOptionCheck.testjava");
	}

	@Test
	public void testUpgradeJavaCookieKeysCheck() throws Exception {
		test("upgrade/UpgradeJavaCookieKeysCheck.testjava");
	}

	@Test
	public void testUpgradeJavaCookieUtilCheck() throws Exception {
		test("upgrade/UpgradeJavaCookieUtilCheck.testjava");
	}

	@Test
	public void testUpgradeJavaExtractTextMethodCheck() throws Exception {
		test("upgrade/UpgradeJavaExtractTextMethodCheck.testjava");
	}

	@Test
	public void testUpgradeJavaFacetedSearcherCheck() throws Exception {
		test("upgrade/UpgradeJavaFacetedSearcherCheck.testjava");
	}

	@Test
	public void testUpgradeJavaFDSActionProviderCheck() throws Exception {
		test("upgrade/UpgradeJavaFDSActionProviderCheck.testjava");
	}

	@Test
	public void testUpgradeJavaFDSDataProviderCheck() throws Exception {
		test("upgrade/UpgradeJavaFDSDataProviderCheck.testjava");
	}

	@Test
	public void testUpgradeJavaFetchCPDefinitionByCProductExternalReferenceCodeCheck()
		throws Exception {

		test(
			"upgrade/UpgradeJavaFetchCPDefinitionByCProductExternal" +
				"ReferenceCodeCheck.testjava");
	}

	@Test
	public void testUpgradeJavaGetFileMethodCheck() throws Exception {
		test("upgrade/UpgradeJavaGetFileMethodCheck.testjava");
	}

	@Test
	public void testUpgradeJavaGetLayoutDisplayPageObjectProviderCheck()
		throws Exception {

		test(
			"upgrade/UpgradeJavaGetLayoutDisplayPageObjectProviderCheck." +
				"testjava",
			StringBundler.concat(
				"Could not resolve variable className for new ",
				"InfoItemReference(). Replace 'TO_BE_REPLACED_FOR_CLASSNAME' ",
				"with the correct type"));
	}

	@Test
	public void testUpgradeJavaGetLayoutDisplayPageProviderCheck()
		throws Exception {

		test("upgrade/UpgradeJavaGetLayoutDisplayPageProviderCheck.testjava");
	}

	@Test
	public void testUpgradeJavaIndexerCheck() throws Exception {
		test("upgrade/UpgradeJavaIndexerCheck.testjava");
	}

	@Test
	public void testUpgradeJavaLayoutServicesCheck() throws Exception {
		test(
			"upgrade/UpgradeJavaLayoutServicesCheck.testjava",
			StringBundler.concat(
				"Unable to format methods addLayout and updateLayout from ",
				"LayoutService, LayoutLocalService, LayoutServiceUtil and ",
				"LayoutLocalServiceUtil. Fill the new parameters manually, ",
				"see LPS-188828 and LPS-190401"));
	}

	@Test
	public void testUpgradeJavaModelPermissionsCheck() throws Exception {
		test("upgrade/UpgradeJavaModelPermissionsCheck.testjava");
	}

	@Test
	public void testUpgradeJavaMultiVMPoolUtilCheck() throws Exception {
		test(
			"upgrade/UpgradeJavaMultiVMPoolUtilCheck.testjava",
			"Could not resolve types for MultiVMPool.getPortalCache(). " +
				"Replace 'TO_BE_REPLACED' with the correct type");
	}

	@Test
	public void testUpgradeJavaOnAfterUpdateParameterCheck() throws Exception {
		test("upgrade/UpgradeJavaOnAfterUpdateParameterCheck.testjava");
	}

	@Test
	public void testUpgradeJavaPhoneLocalServiceUtilCheck() throws Exception {
		test("upgrade/UpgradeJavaPhoneLocalServiceUtilCheck.testjava");
	}

	@Test
	public void testUpgradeJavaPortletSharedSearchSettingsCheck()
		throws Exception {

		test("upgrade/UpgradeJavaPortletSharedSearchSettingsCheck.testjava");
	}

	@Test
	public void testUpgradeJavaSchedulerEntryImplConstructorCheck()
		throws Exception {

		test("upgrade/UpgradeJavaSchedulerEntryImplConstructorCheck.testjava");
	}

	@Test
	public void testUpgradeJavaSearchVocabulariesMethodCheck()
		throws Exception {

		test(
			"upgrade/UpgradeJavaSearchVocabulariesMethodCheck.testjava",
			StringBundler.concat(
				"Unable to format searchVocabularies method from ",
				"AssetVocabularyService and AssetVocabularyLocalService. Fill ",
				"the new parameters manually, see LPS-189866"));
	}

	@Test
	public void testUpgradeJavaServiceReferenceAnnotationCheck()
		throws Exception {

		test("upgrade/UpgradeJavaServiceReferenceAnnotationCheck.testjava");
	}

	@Test
	public void testUpgradeJavaServiceTrackerListCheck() throws Exception {
		test("upgrade/UpgradeJavaServiceTrackerListCheck.testjava");
	}

	@Test
	public void testUpgradeJavaUpdateCommerceAddressCheck() throws Exception {
		test("upgrade/UpgradeJavaUpdateCommerceAddressCheck.testjava");
	}

	@Test
	public void testUpgradeRejectedExecutionHandlerCheck() throws Exception {
		test("upgrade/UpgradeRejectedExecutionHandlerCheck.testjava");
	}

	@Test
	public void testUpgradeSetResultsSetTotalMethodCheck() throws Exception {
		test("upgrade/UpgradeJavaSetResultsSetTotalMethodCheck.testjava");
		test("upgrade/UpgradeJSPSetResultsSetTotalMethodCheck.testjsp");
		test("upgrade/UpgradeJSPFSetResultsSetTotalMethodCheck.testjspf");
	}

	@Test
	public void testUpgradeVelocityMigrationCheck() throws Exception {
		test(
			SourceProcessorTestParameters.create(
				"upgrade/UpgradeVelocityMigrationCheck.testvm"
			).setExpectedFileName(
				"upgrade/migrated/UpgradeVelocityMigrationCheck.testftl"
			));
	}

	@Test
	public void testXMLUpgradeDTDVersionCheck() throws Exception {
		test("upgrade/XMLUpgradeDTDVersionCheck.testxml");
	}

	@Override
	protected SourceFormatterArgs getSourceFormatterArgs() {
		List<String> checkCategoryNames = new ArrayList<>();

		checkCategoryNames.add("Upgrade");

		List<String> sourceFormatterProperties = new ArrayList<>();

		sourceFormatterProperties.add(
			"upgrade.to.version=" + _UPGRADE_TO_VERSION);

		SourceFormatterArgs sourceFormatterArgs =
			super.getSourceFormatterArgs();

		sourceFormatterArgs.setCheckCategoryNames(checkCategoryNames);
		sourceFormatterArgs.setJavaParserEnabled(false);
		sourceFormatterArgs.setSourceFormatterProperties(
			sourceFormatterProperties);

		return sourceFormatterArgs;
	}

	private static final String _UPGRADE_TO_VERSION = "7.4.13.u27";

}