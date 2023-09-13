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
	public void testJSONUpgradeLiferayThemePackageJSONCheck() throws Exception {
		test(
			"upgrade/json-upgrade-liferay-theme-package-json-check/package." +
				"testjson");
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
	public void testUpgradeJavaAddAddressMethodCheck() throws Exception {
		test(
			"upgrade/UpgradeJavaAddAddressMethodCheck.testjava",
			StringBundler.concat(
				"Unable to format method addAddress from AddressLocalService, ",
				"AddressLocalServiceUtil, AddressService and ",
				"AddressServiceUtil. Fill the new parameters manually, see ",
				"LPS-193462"));
	}

	@Test
	public void testUpgradeJavaAddCategoryParameterCheck() throws Exception {
		test(
			"upgrade/UpgradeJavaAddCategoryParameterCheck.testjava",
			StringBundler.concat(
				"Unable to format method addCategory from ",
				"AssetCategoryLocalService and AssetCategoryLocalServiceUtil. ",
				"Fill the new parameters manually, see LPS-192320"));
	}

	@Test
	public void testUpgradeJavaAddFDSTableSchemaFieldCheck() throws Exception {
		test("upgrade/UpgradeJavaAddFDSTableSchemaFieldCheck.testjava");
	}

	@Test
	public void testUpgradeJavaAddFileEntryParameterCheck() throws Exception {
		test(
			"upgrade/UpgradeJavaAddFileEntryParameterCheck.testjava",
			StringBundler.concat(
				"Unable to format method addFileEntry from DLAppLocalService ",
				"and DLAppLocalServiceUtil. Fill the new parameters manually, ",
				"see LPS-194818"));
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
	public void testUpgradeJavaBasePanelAppExtendedClassesCheck()
		throws Exception {

		test("upgrade/UpgradeJavaBasePanelAppExtendedClassesCheck.testjava");
	}

	@Test
	public void testUpgradeJavaCaptchaUtilCheck() throws Exception {
		test("upgrade/UpgradeJavaCaptchaUtilCheck.testjava");
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
	public void testUpgradeJavaDLFolderMethodCheck() throws Exception {
		test(
			"upgrade/UpgradeJavaDLFolderMethodCheck.testjava",
			StringBundler.concat(
				"Unable to format method addFolder from DLFolderService, ",
				"DLFolderLocalService, DLFolderServiceUtil and ",
				"DLFolderLocalServiceUtil. Fill the new parameter manually, ",
				"see LPS-194001."));
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
	public void testUpgradeJavaLanguageUtilCheck() throws Exception {
		test("upgrade/UpgradeJavaLanguageUtilCheck.testjava");
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
	public void testUpgradeJavaPortletIdMethodCheck() throws Exception {
		test("upgrade/UpgradeJavaPortletIdMethodCheck.testjava");
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
	public void testUpgradeJavaUpdateFileEntryMethodCheck() throws Exception {
		test(
			"upgrade/UpgradeJavaUpdateFileEntryMethodCheck.testjava",
			StringBundler.concat(
				"Unable to format method updateFileEntry from ",
				"DLAppLocalService and DLAppLocalServiceUtil. Fill the new ",
				"parameters manually, see LPS-194134."));
	}

	@Test
	public void testUpgradeJavaUserLocalServiceUtilCheck() throws Exception {
		test(
			"upgrade/UpgradeJavaUserLocalServiceUtilCheck.testjava",
			StringBundler.concat(
				"Could not resolve types of updateStatus method. The method ",
				"signature has changed to updateStatus(long userId,",
				"int status, ServiceContext serviceContext). Fill the new ",
				"parameter manually."));
	}

	@Test
	public void testUpgradeJSPFieldSetGroupCheck() throws Exception {
		test("upgrade/UpgradeJSPFieldSetGroupCheck.testjsp");
	}

	@Test
	public void testUpgradePortletFTLCheck() throws Exception {
		test("upgrade/UpgradeFTLPortletFTLCheck.testftl");
	}

	@Test
	public void testUpgradeRejectedExecutionHandlerCheck() throws Exception {
		test("upgrade/UpgradeRejectedExecutionHandlerCheck.testjava");
	}

	@Test
	public void testUpgradeSCSSImportsCheck() throws Exception {
		test("upgrade/UpgradeSCSSImportsCheck.testscss");
	}

	@Test
	public void testUpgradeSCSSMixinsCheck() throws Exception {
		test(
			"upgrade/UpgradeSCSSMixinsCheck.testscss",
			StringBundler.concat(
				"Do not use 'media-query' mixing, replace with its equivalent ",
				"(e.g., media-breakpoint-up, media-breakpoint-only, ",
				"media-breakpoint-down, etc.), see LPS-194507."));
	}

	@Test
	public void testUpgradeSCSSNodeSassPatternsCheck() throws Exception {
		test("upgrade/UpgradeSCSSNodeSassPatternsCheck.testscss");
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
	public void testXMLUpgradeCompatibilityVersionCheck() throws Exception {
		test("upgrade/XMLUpgradeCompatibilityVersionCheck.testxml");
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