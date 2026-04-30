/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.asset;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.info.item.InfoItemServiceRegistryUtil;
import com.liferay.info.item.provider.InfoItemObjectVariationProvider;
import com.liferay.journal.model.JournalArticle;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Georgel Pop
 */
public class AssetAnalyticsAttributesProviderTest {

	@ClassRule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@After
	public void tearDown() {
		_featureFlagManagerUtilMockedStatic.close();
		_infoItemServiceRegistryUtilMockedStatic.close();
	}

	@Test
	@TestInfo("LPD-83537")
	public void testBuildAssetAnalyticsAttributes() {
		_featureFlagManagerUtilMockedStatic.when(
			() -> FeatureFlagManagerUtil.isEnabled(
				Mockito.anyLong(), Mockito.eq("LPD-81914"))
		).thenReturn(
			true
		);

		_testBuildAssetAnalyticsAttributesForJournalArticle();
		_testBuildAssetAnalyticsAttributesWhenAssetEntryIsNull();
		_testBuildAssetAnalyticsAttributesWhenViewModeIsFalse();
	}

	@Test
	@TestInfo("LPD-83537")
	public void testGetAssetAnalyticsAttributes() {
		_testGetAssetAnalyticsAttributesForBlogsEntry();
		_testGetAssetAnalyticsAttributesForDLFileEntry();
		_testGetAssetAnalyticsAttributesForJournalArticle();
		_testGetAssetAnalyticsAttributesForObjectEntry();
		_testGetAssetAnalyticsAttributesWhenDDMStructureKeyIsSet();
		_testGetAssetAnalyticsAttributesWhenInputIsNull();
	}

	@Test
	@TestInfo("LPD-83537")
	public void testGetAssetAnalyticsCMSVersion() {
		long companyId = RandomTestUtil.randomLong();

		_testGetAssetAnalyticsCMSVersionForJournalArticle(companyId);

		String objectDefinitionClassName =
			"com.liferay.object.model.ObjectDefinition#42";

		AssetEntry objectEntry = _mockAssetEntry(
			objectDefinitionClassName, RandomTestUtil.randomLong(),
			RandomTestUtil.randomLong(), companyId);

		AssetAnalyticsAttributesProvider provider =
			new AssetAnalyticsAttributesProvider(
				objectEntry, null, null, false);

		Snapshot<ObjectDefinitionLocalService> originalSnapshot =
			(Snapshot<ObjectDefinitionLocalService>)
				ReflectionTestUtil.getFieldValue(
					AssetAnalyticsAttributesProvider.class,
					"_objectDefinitionLocalServiceSnapshot");

		try {
			@SuppressWarnings("unchecked")
			Snapshot<ObjectDefinitionLocalService> mockSnapshot = Mockito.mock(
				Snapshot.class);

			ReflectionTestUtil.setFieldValue(
				AssetAnalyticsAttributesProvider.class,
				"_objectDefinitionLocalServiceSnapshot", mockSnapshot);

			ObjectDefinitionLocalService objectDefinitionLocalService =
				Mockito.mock(ObjectDefinitionLocalService.class);
			ObjectDefinition objectDefinition = Mockito.mock(
				ObjectDefinition.class);

			_testGetAssetAnalyticsCMSVersionWhenObjectDefinitionIsCMS(
				provider, mockSnapshot, objectDefinitionLocalService,
				objectDefinition, companyId, objectDefinitionClassName);
			_testGetAssetAnalyticsCMSVersionWhenObjectDefinitionIsNotCMS(
				provider, mockSnapshot, objectDefinitionLocalService,
				objectDefinition, companyId, objectDefinitionClassName);

			_testGetAssetAnalyticsCMSVersionWhenObjectDefinitionIsNull(
				provider, mockSnapshot, objectDefinitionLocalService, companyId,
				objectDefinitionClassName);
			_testGetAssetAnalyticsCMSVersionWhenSnapshotReturnsNull(
				provider, mockSnapshot);
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				AssetAnalyticsAttributesProvider.class,
				"_objectDefinitionLocalServiceSnapshot", originalSnapshot);
		}
	}

	@Test
	@TestInfo("LPD-83537")
	public void testGetAssetAnalyticsTypeForObjectEntry() {
		long companyId = RandomTestUtil.randomLong();
		String objectDefinitionClassName =
			"com.liferay.object.model.ObjectDefinition#42";

		AssetEntry objectEntry = _mockAssetEntry(
			objectDefinitionClassName, RandomTestUtil.randomLong(),
			RandomTestUtil.randomLong(), companyId);

		AssetAnalyticsAttributesProvider provider =
			new AssetAnalyticsAttributesProvider(
				objectEntry, null, null, false);

		Snapshot<ObjectDefinitionLocalService> originalSnapshot =
			(Snapshot<ObjectDefinitionLocalService>)
				ReflectionTestUtil.getFieldValue(
					AssetAnalyticsAttributesProvider.class,
					"_objectDefinitionLocalServiceSnapshot");

		try {
			@SuppressWarnings("unchecked")
			Snapshot<ObjectDefinitionLocalService> mockSnapshot = Mockito.mock(
				Snapshot.class);

			ReflectionTestUtil.setFieldValue(
				AssetAnalyticsAttributesProvider.class,
				"_objectDefinitionLocalServiceSnapshot", mockSnapshot);

			ObjectDefinitionLocalService objectDefinitionLocalService =
				Mockito.mock(ObjectDefinitionLocalService.class);

			Mockito.when(
				mockSnapshot.get()
			).thenReturn(
				objectDefinitionLocalService
			);

			ObjectDefinition objectDefinition = Mockito.mock(
				ObjectDefinition.class);

			Mockito.when(
				objectDefinitionLocalService.fetchObjectDefinitionByClassName(
					companyId, objectDefinitionClassName)
			).thenReturn(
				objectDefinition
			);

			_testGetAssetAnalyticsTypeForObjectEntryWhenObjectDefinitionHasName(
				provider, objectDefinition);
			_testGetAssetAnalyticsTypeForObjectEntryWhenObjectDefinitionNameIsNull(
				provider, objectDefinition);
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				AssetAnalyticsAttributesProvider.class,
				"_objectDefinitionLocalServiceSnapshot", originalSnapshot);
		}
	}

	private AssetEntry _mockAssetEntry(
		String className, long classPK, long groupId) {

		AssetEntry assetEntry = Mockito.mock(AssetEntry.class);

		Mockito.when(
			assetEntry.getClassName()
		).thenReturn(
			className
		);

		Mockito.when(
			assetEntry.getClassPK()
		).thenReturn(
			classPK
		);

		Mockito.when(
			assetEntry.getGroupId()
		).thenReturn(
			groupId
		);

		return assetEntry;
	}

	private AssetEntry _mockAssetEntry(
		String className, long classPK, long groupId, long companyId) {

		AssetEntry assetEntry = _mockAssetEntry(className, classPK, groupId);

		Mockito.when(
			assetEntry.getCompanyId()
		).thenReturn(
			companyId
		);

		return assetEntry;
	}

	private AssetRenderer<?> _mockAssetRenderer(String title) {
		AssetRenderer<?> assetRenderer = Mockito.mock(AssetRenderer.class);

		Mockito.when(
			assetRenderer.getTitle(LocaleUtil.US)
		).thenReturn(
			title
		);

		return assetRenderer;
	}

	private void _testBuildAssetAnalyticsAttributesForJournalArticle() {
		AssetAnalyticsAttributesProvider provider =
			new AssetAnalyticsAttributesProvider(
				_mockAssetEntry(
					_CLASS_NAME_JOURNAL_ARTICLE, RandomTestUtil.randomLong(),
					RandomTestUtil.randomLong()),
				_mockAssetRenderer("Quoted \" and <tag>"), LocaleUtil.US, true);

		String attributes = provider.buildAttributes(
			AssetAnalyticsAttributesProvider.ACTION_IMPRESSION,
			AssetAnalyticsAttributesProvider.FIELD_TITLE);

		Assert.assertFalse(attributes.contains("\"Quoted \""));
		Assert.assertTrue(attributes.contains("data-analytics-asset-title=\""));
		Assert.assertTrue(
			attributes.contains(
				"data-analytics-asset-field=\"" +
					AssetAnalyticsAttributesProvider.FIELD_TITLE + "\""));
	}

	private void _testBuildAssetAnalyticsAttributesWhenAssetEntryIsNull() {
		AssetAnalyticsAttributesProvider provider =
			new AssetAnalyticsAttributesProvider(null, null, null, true);

		Assert.assertEquals(
			StringPool.BLANK,
			provider.buildAttributes(
				AssetAnalyticsAttributesProvider.ACTION_IMPRESSION,
				AssetAnalyticsAttributesProvider.FIELD_TITLE));
	}

	private void _testBuildAssetAnalyticsAttributesWhenViewModeIsFalse() {
		AssetAnalyticsAttributesProvider provider =
			new AssetAnalyticsAttributesProvider(
				_mockAssetEntry(
					_CLASS_NAME_JOURNAL_ARTICLE, RandomTestUtil.randomLong(),
					RandomTestUtil.randomLong()),
				_mockAssetRenderer(RandomTestUtil.randomString()),
				LocaleUtil.US, false);

		Assert.assertEquals(
			StringPool.BLANK,
			provider.buildAttributes(
				AssetAnalyticsAttributesProvider.ACTION_IMPRESSION,
				AssetAnalyticsAttributesProvider.FIELD_TITLE));
	}

	private void _testGetAssetAnalyticsAttributesForBlogsEntry() {
		AssetAnalyticsAttributesProvider provider =
			new AssetAnalyticsAttributesProvider(
				_mockAssetEntry(
					"com.liferay.blogs.model.BlogsEntry",
					RandomTestUtil.randomLong(), RandomTestUtil.randomLong()),
				null, null, false);

		Map<String, String> analyticsAttributes = provider.getAttributes(
			AssetAnalyticsAttributesProvider.ACTION_IMPRESSION,
			AssetAnalyticsAttributesProvider.FIELD_TITLE);

		Assert.assertNull(
			analyticsAttributes.get("data-analytics-asset-subtype"));
		Assert.assertEquals(
			"blog", analyticsAttributes.get("data-analytics-asset-type"));
	}

	private void _testGetAssetAnalyticsAttributesForDLFileEntry() {
		AssetAnalyticsAttributesProvider provider =
			new AssetAnalyticsAttributesProvider(
				_mockAssetEntry(
					"com.liferay.document.library.kernel.model.DLFileEntry",
					RandomTestUtil.randomLong(), RandomTestUtil.randomLong()),
				null, null, false);

		Map<String, String> analyticsAttributes = provider.getAttributes(
			AssetAnalyticsAttributesProvider.ACTION_IMPRESSION,
			AssetAnalyticsAttributesProvider.FIELD_TITLE);

		Assert.assertEquals(
			"document", analyticsAttributes.get("data-analytics-asset-type"));
	}

	private void _testGetAssetAnalyticsAttributesForJournalArticle() {
		long journalArticleClassPK = RandomTestUtil.randomLong();
		String title = RandomTestUtil.randomString();
		String structureId = RandomTestUtil.randomString();

		InfoItemObjectVariationProvider<Object>
			infoItemObjectVariationProvider = Mockito.mock(
				InfoItemObjectVariationProvider.class);

		Mockito.when(
			infoItemObjectVariationProvider.getInfoItemFormVariationKey(
				Mockito.any())
		).thenReturn(
			structureId
		);

		_infoItemServiceRegistryUtilMockedStatic.when(
			() -> InfoItemServiceRegistryUtil.getFirstInfoItemService(
				InfoItemObjectVariationProvider.class,
				_CLASS_NAME_JOURNAL_ARTICLE)
		).thenReturn(
			infoItemObjectVariationProvider
		);

		AssetAnalyticsAttributesProvider provider =
			new AssetAnalyticsAttributesProvider(
				_mockAssetEntry(
					_CLASS_NAME_JOURNAL_ARTICLE, journalArticleClassPK,
					RandomTestUtil.randomLong()),
				_mockAssetRenderer(title), LocaleUtil.US, false);

		Map<String, String> analyticsAttributes = provider.getAttributes(
			AssetAnalyticsAttributesProvider.ACTION_VIEW,
			AssetAnalyticsAttributesProvider.FIELD_CONTENT);

		Assert.assertEquals(
			AssetAnalyticsAttributesProvider.ACTION_VIEW,
			analyticsAttributes.get("data-analytics-asset-action"));
		Assert.assertEquals(
			"1.0", analyticsAttributes.get("data-analytics-asset-cmsversion"));
		Assert.assertEquals(
			AssetAnalyticsAttributesProvider.FIELD_CONTENT,
			analyticsAttributes.get("data-analytics-asset-field"));
		Assert.assertEquals(
			String.valueOf(journalArticleClassPK),
			analyticsAttributes.get("data-analytics-asset-id"));
		Assert.assertEquals(
			structureId,
			analyticsAttributes.get("data-analytics-asset-subtype"));
		Assert.assertEquals(
			title, analyticsAttributes.get("data-analytics-asset-title"));
		Assert.assertEquals(
			"web-content",
			analyticsAttributes.get("data-analytics-asset-type"));
	}

	private void _testGetAssetAnalyticsAttributesForObjectEntry() {
		AssetAnalyticsAttributesProvider provider =
			new AssetAnalyticsAttributesProvider(
				_mockAssetEntry(
					"com.liferay.object.model.ObjectDefinition#99",
					RandomTestUtil.randomLong(), RandomTestUtil.randomLong()),
				null, null, false);

		Map<String, String> analyticsAttributes = provider.getAttributes(
			AssetAnalyticsAttributesProvider.ACTION_IMPRESSION,
			AssetAnalyticsAttributesProvider.FIELD_TITLE);

		Assert.assertEquals(
			"object-entry",
			analyticsAttributes.get("data-analytics-asset-type"));
	}

	private void _testGetAssetAnalyticsAttributesWhenDDMStructureKeyIsSet() {
		String ddmStructureKey = "MY-STRUCTURE-KEY";

		JournalArticle journalArticle = Mockito.mock(JournalArticle.class);

		Mockito.when(
			journalArticle.getDDMStructureKey()
		).thenReturn(
			ddmStructureKey
		);

		AssetRenderer<?> assetRenderer = Mockito.mock(AssetRenderer.class);

		Mockito.when(
			assetRenderer.getAssetObject()
		).thenReturn(
			journalArticle
		);

		AssetAnalyticsAttributesProvider provider =
			new AssetAnalyticsAttributesProvider(
				_mockAssetEntry(
					RandomTestUtil.randomString(), RandomTestUtil.randomLong(),
					RandomTestUtil.randomLong()),
				assetRenderer, LocaleUtil.US, false);

		Map<String, String> analyticsAttributes = provider.getAttributes(
			AssetAnalyticsAttributesProvider.ACTION_IMPRESSION,
			AssetAnalyticsAttributesProvider.FIELD_TITLE);

		Assert.assertEquals(
			StringUtil.toLowerCase(ddmStructureKey),
			analyticsAttributes.get("data-analytics-asset-subtype"));
	}

	private void _testGetAssetAnalyticsAttributesWhenInputIsNull() {
		AssetAnalyticsAttributesProvider provider1 =
			new AssetAnalyticsAttributesProvider(null, null, null, false);

		Map<String, String> analyticsAttributes1 = provider1.getAttributes(
			AssetAnalyticsAttributesProvider.ACTION_IMPRESSION,
			AssetAnalyticsAttributesProvider.FIELD_TITLE);

		Assert.assertTrue(analyticsAttributes1.isEmpty());

		AssetEntry journalArticle = _mockAssetEntry(
			_CLASS_NAME_JOURNAL_ARTICLE, RandomTestUtil.randomLong(),
			RandomTestUtil.randomLong());

		AssetAnalyticsAttributesProvider provider2 =
			new AssetAnalyticsAttributesProvider(
				journalArticle, null, null, false);

		Map<String, String> analyticsAttributes2 = provider2.getAttributes(
			AssetAnalyticsAttributesProvider.ACTION_IMPRESSION,
			AssetAnalyticsAttributesProvider.FIELD_TITLE);

		Assert.assertNull(
			analyticsAttributes2.get("data-analytics-asset-title"));

		AssetRenderer<?> assetRenderer = _mockAssetRenderer(
			RandomTestUtil.randomString());

		AssetAnalyticsAttributesProvider provider3 =
			new AssetAnalyticsAttributesProvider(
				journalArticle, assetRenderer, null, false);

		Map<String, String> analyticsAttributes3 = provider3.getAttributes(
			AssetAnalyticsAttributesProvider.ACTION_IMPRESSION,
			AssetAnalyticsAttributesProvider.FIELD_TITLE);

		Assert.assertNull(
			analyticsAttributes3.get("data-analytics-asset-title"));

		AssetAnalyticsAttributesProvider provider4 =
			new AssetAnalyticsAttributesProvider(
				journalArticle, assetRenderer, LocaleUtil.US, false);

		Map<String, String> analyticsAttributes4 = provider4.getAttributes(
			AssetAnalyticsAttributesProvider.ACTION_IMPRESSION, null);

		Assert.assertNull(
			analyticsAttributes4.get("data-analytics-asset-field"));
	}

	private void _testGetAssetAnalyticsCMSVersionForJournalArticle(
		long companyId) {

		AssetAnalyticsAttributesProvider provider =
			new AssetAnalyticsAttributesProvider(
				_mockAssetEntry(
					_CLASS_NAME_JOURNAL_ARTICLE, RandomTestUtil.randomLong(),
					RandomTestUtil.randomLong(), companyId),
				null, null, false);

		Map<String, String> analyticsAttributes = provider.getAttributes(
			AssetAnalyticsAttributesProvider.ACTION_VIEW,
			AssetAnalyticsAttributesProvider.FIELD_CONTENT);

		Assert.assertEquals(
			"1.0", analyticsAttributes.get("data-analytics-asset-cmsversion"));
	}

	private void _testGetAssetAnalyticsCMSVersionWhenObjectDefinitionIsCMS(
		AssetAnalyticsAttributesProvider provider,
		Snapshot<ObjectDefinitionLocalService> mockSnapshot,
		ObjectDefinitionLocalService objectDefinitionLocalService,
		ObjectDefinition objectDefinition, long companyId,
		String objectDefinitionClassName) {

		Mockito.when(
			mockSnapshot.get()
		).thenReturn(
			objectDefinitionLocalService
		);

		Mockito.when(
			objectDefinitionLocalService.fetchObjectDefinitionByClassName(
				companyId, objectDefinitionClassName)
		).thenReturn(
			objectDefinition
		);

		Mockito.when(
			objectDefinition.isCMS()
		).thenReturn(
			true
		);

		Map<String, String> analyticsAttributes = provider.getAttributes(
			AssetAnalyticsAttributesProvider.ACTION_VIEW,
			AssetAnalyticsAttributesProvider.FIELD_CONTENT);

		Assert.assertEquals(
			"2.0", analyticsAttributes.get("data-analytics-asset-cmsversion"));
	}

	private void _testGetAssetAnalyticsCMSVersionWhenObjectDefinitionIsNotCMS(
		AssetAnalyticsAttributesProvider provider,
		Snapshot<ObjectDefinitionLocalService> mockSnapshot,
		ObjectDefinitionLocalService objectDefinitionLocalService,
		ObjectDefinition objectDefinition, long companyId,
		String objectDefinitionClassName) {

		Mockito.when(
			mockSnapshot.get()
		).thenReturn(
			objectDefinitionLocalService
		);

		Mockito.when(
			objectDefinitionLocalService.fetchObjectDefinitionByClassName(
				companyId, objectDefinitionClassName)
		).thenReturn(
			objectDefinition
		);

		Mockito.when(
			objectDefinition.isCMS()
		).thenReturn(
			false
		);

		Map<String, String> analyticsAttributes = provider.getAttributes(
			AssetAnalyticsAttributesProvider.ACTION_VIEW,
			AssetAnalyticsAttributesProvider.FIELD_CONTENT);

		Assert.assertEquals(
			"1.0", analyticsAttributes.get("data-analytics-asset-cmsversion"));
	}

	private void _testGetAssetAnalyticsCMSVersionWhenObjectDefinitionIsNull(
		AssetAnalyticsAttributesProvider provider,
		Snapshot<ObjectDefinitionLocalService> mockSnapshot,
		ObjectDefinitionLocalService objectDefinitionLocalService,
		long companyId, String objectDefinitionClassName) {

		Mockito.when(
			mockSnapshot.get()
		).thenReturn(
			objectDefinitionLocalService
		);

		Mockito.when(
			objectDefinitionLocalService.fetchObjectDefinitionByClassName(
				companyId, objectDefinitionClassName)
		).thenReturn(
			null
		);

		Map<String, String> analyticsAttributes = provider.getAttributes(
			AssetAnalyticsAttributesProvider.ACTION_VIEW,
			AssetAnalyticsAttributesProvider.FIELD_CONTENT);

		Assert.assertEquals(
			"1.0", analyticsAttributes.get("data-analytics-asset-cmsversion"));
	}

	private void _testGetAssetAnalyticsCMSVersionWhenSnapshotReturnsNull(
		AssetAnalyticsAttributesProvider provider,
		Snapshot<ObjectDefinitionLocalService> mockSnapshot) {

		Mockito.when(
			mockSnapshot.get()
		).thenReturn(
			null
		);

		Map<String, String> analyticsAttributes = provider.getAttributes(
			AssetAnalyticsAttributesProvider.ACTION_VIEW,
			AssetAnalyticsAttributesProvider.FIELD_CONTENT);

		Assert.assertEquals(
			"1.0", analyticsAttributes.get("data-analytics-asset-cmsversion"));
	}

	private void
		_testGetAssetAnalyticsTypeForObjectEntryWhenObjectDefinitionHasName(
			AssetAnalyticsAttributesProvider provider,
			ObjectDefinition objectDefinition) {

		Mockito.when(
			objectDefinition.getName()
		).thenReturn(
			"MyCMSType"
		);

		Map<String, String> analyticsAttributes = provider.getAttributes(
			AssetAnalyticsAttributesProvider.ACTION_VIEW,
			AssetAnalyticsAttributesProvider.FIELD_CONTENT);

		Assert.assertEquals(
			"my-cms-type",
			analyticsAttributes.get("data-analytics-asset-type"));
	}

	private void
		_testGetAssetAnalyticsTypeForObjectEntryWhenObjectDefinitionNameIsNull(
			AssetAnalyticsAttributesProvider provider,
			ObjectDefinition objectDefinition) {

		Mockito.when(
			objectDefinition.getName()
		).thenReturn(
			null
		);

		Map<String, String> analyticsAttributes = provider.getAttributes(
			AssetAnalyticsAttributesProvider.ACTION_VIEW,
			AssetAnalyticsAttributesProvider.FIELD_CONTENT);

		Assert.assertEquals(
			"object-entry",
			analyticsAttributes.get("data-analytics-asset-type"));
	}

	private static final String _CLASS_NAME_JOURNAL_ARTICLE =
		"com.liferay.journal.model.JournalArticle";

	private final MockedStatic<FeatureFlagManagerUtil>
		_featureFlagManagerUtilMockedStatic = Mockito.mockStatic(
			FeatureFlagManagerUtil.class);
	private final MockedStatic<InfoItemServiceRegistryUtil>
		_infoItemServiceRegistryUtilMockedStatic = Mockito.mockStatic(
			InfoItemServiceRegistryUtil.class);

}