/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.asset.publisher.lar.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyLocalServiceUtil;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.asset.publisher.constants.AssetPublisherPortletKeys;
import com.liferay.asset.publisher.test.util.AssetPublisherTestUtil;
import com.liferay.asset.publisher.util.AssetEntryResult;
import com.liferay.asset.publisher.util.AssetPublisherHelper;
import com.liferay.asset.test.util.AssetTestUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationSettingsMapFactoryUtil;
import com.liferay.exportimport.kernel.lar.ExportImportHelperUtil;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalServiceUtil;
import com.liferay.exportimport.kernel.service.ExportImportLocalServiceUtil;
import com.liferay.exportimport.test.util.lar.BasePortletExportImportTestCase;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletPreferences;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.portlet.MockPortletRequest;

/**
 * Tests the export and import behavior of the Asset Publisher bundle with
 * different types of assets.
 *
 * @author Julio Camarero
 */
@RunWith(Arquillian.class)
public class AssetPublisherExportImportTest
	extends BasePortletExportImportTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	public String getPortletId() throws Exception {
		return PortletIdCodec.encode(
			AssetPublisherPortletKeys.ASSET_PUBLISHER,
			RandomTestUtil.randomString());
	}

	@Before
	@Override
	public void setUp() throws Exception {
		ServiceTestUtil.setUser(TestPropsValues.getUser());

		super.setUp();

		_permissionChecker = PermissionCheckerFactoryUtil.create(
			TestPropsValues.getUser());
	}

	@Test
	public void testAnyDLFileEntryType() throws Exception {
		long dlFileEntryClassNameId = PortalUtil.getClassNameId(
			DLFileEntry.class);

		Map<String, String[]> preferenceMap = HashMapBuilder.put(
			"anyAssetType",
			new String[] {String.valueOf(dlFileEntryClassNameId)}
		).put(
			"anyClassTypeDLFileEntryAssetRendererFactory",
			new String[] {String.valueOf(Boolean.TRUE)}
		).build();

		PortletPreferences portletPreferences = getImportedPortletPreferences(
			preferenceMap);

		long anyAssetType = GetterUtil.getLong(
			portletPreferences.getValue("anyAssetType", null));

		Assert.assertEquals(dlFileEntryClassNameId, anyAssetType);

		String anyClassTypeDLFileEntryAssetRendererFactory =
			portletPreferences.getValue(
				"anyClassTypeDLFileEntryAssetRendererFactory", null);

		Assert.assertEquals(
			anyClassTypeDLFileEntryAssetRendererFactory,
			String.valueOf(Boolean.TRUE));
	}

	@Test
	public void testAnyJournalStructure() throws Exception {
		long journalArticleClassNameId = PortalUtil.getClassNameId(
			JournalArticle.class);

		Map<String, String[]> preferenceMap = HashMapBuilder.put(
			"anyAssetType",
			new String[] {String.valueOf(journalArticleClassNameId)}
		).put(
			"anyClassTypeJournalArticleAssetRendererFactory",
			new String[] {String.valueOf(Boolean.TRUE)}
		).build();

		PortletPreferences portletPreferences = getImportedPortletPreferences(
			preferenceMap);

		long anyAssetType = GetterUtil.getLong(
			portletPreferences.getValue("anyAssetType", null));

		Assert.assertEquals(journalArticleClassNameId, anyAssetType);

		String anyClassTypeDLFileEntryAssetRendererFactory =
			portletPreferences.getValue(
				"anyClassTypeJournalArticleAssetRendererFactory", null);

		Assert.assertEquals(
			anyClassTypeDLFileEntryAssetRendererFactory,
			String.valueOf(Boolean.TRUE));
	}

	@Test
	public void testAssetCategories() throws Exception {
		AssetVocabulary assetVocabulary = AssetTestUtil.addVocabulary(
			group.getGroupId());

		AssetCategory assetCategory = AssetTestUtil.addCategory(
			group.getGroupId(), assetVocabulary.getVocabularyId());

		Map<String, String[]> preferenceMap = HashMapBuilder.put(
			"queryName0", new String[] {"assetCategories"}
		).put(
			"queryValues0",
			new String[] {String.valueOf(assetCategory.getCategoryId())}
		).build();

		PortletPreferences portletPreferences = getImportedPortletPreferences(
			preferenceMap);

		long importedAssetCategoryId = GetterUtil.getLong(
			portletPreferences.getValue("queryValues0", null));

		Assert.assertNotEquals(importedAssetCategoryId, 0L);

		Assert.assertNotEquals(
			assetCategory.getCategoryId(), importedAssetCategoryId);

		AssetCategory importedAssetCategory =
			AssetCategoryLocalServiceUtil.fetchAssetCategory(
				importedAssetCategoryId);

		Assert.assertNotNull(importedAssetCategory);
		Assert.assertEquals(
			assetCategory.getUuid(), importedAssetCategory.getUuid());
	}

	@Test
	public void testChildLayoutScopeIds() throws Exception {
		Group childGroup = GroupTestUtil.addGroup(group.getGroupId());

		Map<String, String[]> preferenceMap = HashMapBuilder.put(
			"scopeIds",
			new String[] {
				AssetPublisherHelper.SCOPE_ID_CHILD_GROUP_PREFIX +
					childGroup.getGroupId()
			}
		).build();

		try {
			PortletPreferences portletPreferences =
				getImportedPortletPreferences(preferenceMap);

			Assert.assertEquals(
				null, portletPreferences.getValue("scopeId", null));
			Assert.assertEquals(
				AssetPublisherHelper.SCOPE_ID_GROUP_PREFIX +
					childGroup.getGroupId(),
				portletPreferences.getValue("scopeIds", null));
		}
		finally {
			GroupLocalServiceUtil.deleteGroup(childGroup);
		}
	}

	@Test
	public void testDisplayStyle() throws Exception {
		String displayStyle = RandomTestUtil.randomString();

		Map<String, String[]> preferenceMap = HashMapBuilder.put(
			"displayStyle", new String[] {displayStyle}
		).build();

		PortletPreferences portletPreferences = getImportedPortletPreferences(
			preferenceMap);

		Assert.assertEquals(
			displayStyle, portletPreferences.getValue("displayStyle", null));
		Assert.assertTrue(
			"The display style should not be null",
			Validator.isNotNull(
				portletPreferences.getValue("displayStyle", null)));
	}

	@Test
	public void testDynamicExportImportAssetCategoryFiltering()
		throws Exception {

		AssetVocabulary assetVocabulary = AssetTestUtil.addVocabulary(
			group.getGroupId());

		AssetCategory assetCategory = AssetTestUtil.addCategory(
			group.getGroupId(), assetVocabulary.getVocabularyId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		serviceContext.setAssetCategoryIds(
			new long[] {assetCategory.getCategoryId()});

		List<AssetEntry> expectedAssetEntries = addAssetEntries(
			group, 2, new ArrayList<AssetEntry>(), serviceContext);

		Map<String, String[]> preferenceMap = HashMapBuilder.put(
			"queryContains0", new String[] {"true"}
		).put(
			"queryName0", new String[] {"assetCategories"}
		).put(
			"queryValues0",
			new String[] {String.valueOf(assetCategory.getCategoryId())}
		).build();

		testDynamicExportImport(preferenceMap, expectedAssetEntries, true);
	}

	@Test
	public void testDynamicExportImportAssetTagFiltering() throws Exception {
		AssetTag assetTag = AssetTestUtil.addTag(group.getGroupId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		serviceContext.setAssetTagNames(new String[] {assetTag.getName()});

		List<AssetEntry> expectedAssetEntries = addAssetEntries(
			group, 2, new ArrayList<AssetEntry>(), serviceContext);

		Map<String, String[]> preferenceMap = HashMapBuilder.put(
			"queryContains0", new String[] {Boolean.TRUE.toString()}
		).put(
			"queryValues0", new String[] {assetTag.getName()}
		).build();

		testDynamicExportImport(preferenceMap, expectedAssetEntries, true);
	}

	@Test
	public void testDynamicExportImportAssetVocabularyFiltering()
		throws Exception {

		AssetVocabulary assetVocabulary = AssetTestUtil.addVocabulary(
			group.getGroupId());

		AssetCategory assetCategory1 = AssetTestUtil.addCategory(
			group.getGroupId(), assetVocabulary.getVocabularyId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		serviceContext.setAssetCategoryIds(
			new long[] {assetCategory1.getCategoryId()});

		List<AssetEntry> expectedAssetEntries = addAssetEntries(
			group, 1, new ArrayList<AssetEntry>(), serviceContext);

		AssetCategory assetCategory2 = AssetTestUtil.addCategory(
			group.getGroupId(), assetVocabulary.getVocabularyId());

		serviceContext.setAssetCategoryIds(
			new long[] {assetCategory2.getCategoryId()});

		expectedAssetEntries = addAssetEntries(
			group, 1, expectedAssetEntries, serviceContext);

		Map<String, String[]> preferenceMap = HashMapBuilder.put(
			"assetVocabularyId",
			new String[] {String.valueOf(assetVocabulary.getVocabularyId())}
		).build();

		testDynamicExportImport(preferenceMap, expectedAssetEntries, true);
	}

	@Test
	public void testDynamicExportImportClassTypeFiltering() throws Exception {
		List<AssetEntry> expectedAssetEntries = new ArrayList<>();

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			group.getGroupId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(100),
			ServiceContextTestUtil.getServiceContext());

		expectedAssetEntries.add(getAssetEntry(journalArticle));

		Map<String, String[]> preferenceMap = HashMapBuilder.put(
			"anyAssetType",
			new String[] {
				String.valueOf(PortalUtil.getClassNameId(JournalArticle.class))
			}
		).put(
			"classTypeIds",
			() -> {
				DDMStructure ddmStructure = journalArticle.getDDMStructure();

				return new String[] {
					String.valueOf(ddmStructure.getStructureId())
				};
			}
		).build();

		testDynamicExportImport(preferenceMap, expectedAssetEntries, true);
	}

	@Test
	public void testDynamicExportImportLayoutFiltering() throws Exception {
		List<AssetEntry> expectedAssetEntries = new ArrayList<>();

		Map<Locale, String> titleMap = HashMapBuilder.put(
			LocaleUtil.getDefault(), RandomTestUtil.randomString()
		).build();

		Map<Locale, String> contentMap = HashMapBuilder.put(
			LocaleUtil.getDefault(), RandomTestUtil.randomString(100)
		).build();

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			group.getGroupId(), JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, titleMap, titleMap,
			contentMap, layout.getUuid(), LocaleUtil.getDefault(), null, false,
			false, ServiceContextTestUtil.getServiceContext());

		expectedAssetEntries.add(getAssetEntry(journalArticle));

		Map<String, String[]> preferenceMap = HashMapBuilder.put(
			"showOnlyLayoutAssets", new String[] {Boolean.TRUE.toString()}
		).build();

		testDynamicExportImport(preferenceMap, expectedAssetEntries, true);
	}

	@Test
	public void testDynamicExportImportOtherClassNameFiltering()
		throws Exception {

		Map<String, String[]> preferenceMap = HashMapBuilder.put(
			"anyAssetType",
			new String[] {
				String.valueOf(PortalUtil.getClassNameId(DLFileEntry.class))
			}
		).build();

		testDynamicExportImport(
			preferenceMap, new ArrayList<AssetEntry>(), true);
	}

	@Test
	public void testDynamicExportImportWithNoFiltering() throws Exception {
		List<AssetEntry> expectedAssetEntries = addAssetEntries(
			group, 2, new ArrayList<AssetEntry>(),
			ServiceContextTestUtil.getServiceContext());

		testDynamicExportImport(
			new HashMap<String, String[]>(), expectedAssetEntries, false);
	}

	@Test
	public void testExportImportAssetEntries() throws Exception {
		testExportImportAssetEntries(group);
	}

	@Ignore
	@Override
	@Test
	public void testExportImportAssetLinks() throws Exception {
	}

	@Test
	public void testExportImportLayoutScopedAssetEntries() throws Exception {
		Group layoutGroup = GroupTestUtil.addGroup(
			TestPropsValues.getUserId(), layout);

		testExportImportAssetEntries(layoutGroup);
	}

	@Test
	public void testExportImportSeveralScopedAssetEntries() throws Exception {
		List<Group> groups = new ArrayList<>();

		Company company = CompanyLocalServiceUtil.getCompany(
			layout.getCompanyId());

		Group companyGroup = company.getGroup();

		groups.add(companyGroup);

		groups.add(group);

		Group group2 = GroupTestUtil.addGroup();

		groups.add(group2);

		Group layoutGroup1 = GroupTestUtil.addGroup(
			TestPropsValues.getUserId(), layout);

		groups.add(layoutGroup1);

		Layout layout2 = LayoutTestUtil.addLayout(group);

		Group layoutGroup2 = GroupTestUtil.addGroup(
			TestPropsValues.getUserId(), layout2);

		groups.add(layoutGroup2);

		testExportImportAssetEntries(groups);
	}

	@Test
	public void testGlobalScopeId() throws Exception {
		Company company = CompanyLocalServiceUtil.getCompany(
			layout.getCompanyId());

		Group companyGroup = company.getGroup();

		Map<String, String[]> preferenceMap = HashMapBuilder.put(
			"scopeIds",
			new String[] {
				AssetPublisherHelper.SCOPE_ID_GROUP_PREFIX +
					companyGroup.getGroupId()
			}
		).build();

		PortletPreferences portletPreferences = getImportedPortletPreferences(
			preferenceMap);

		Assert.assertEquals(
			AssetPublisherHelper.SCOPE_ID_GROUP_PREFIX +
				companyGroup.getGroupId(),
			portletPreferences.getValue("scopeIds", null));
		Assert.assertEquals(null, portletPreferences.getValue("scopeId", null));
	}

	@Test
	public void testLayoutScopeId() throws Exception {
		GroupTestUtil.addGroup(TestPropsValues.getUserId(), layout);

		Map<String, String[]> preferenceMap = HashMapBuilder.put(
			"scopeIds",
			new String[] {
				AssetPublisherHelper.SCOPE_ID_LAYOUT_UUID_PREFIX +
					layout.getUuid()
			}
		).build();

		PortletPreferences portletPreferences = getImportedPortletPreferences(
			preferenceMap);

		Assert.assertEquals(
			AssetPublisherHelper.SCOPE_ID_LAYOUT_UUID_PREFIX +
				importedLayout.getUuid(),
			portletPreferences.getValue("scopeIds", null));
		Assert.assertEquals(null, portletPreferences.getValue("scopeId", null));
	}

	@Test
	public void testLegacyLayoutScopeId() throws Exception {
		GroupTestUtil.addGroup(TestPropsValues.getUserId(), layout);

		Map<String, String[]> preferenceMap = HashMapBuilder.put(
			"scopeIds",
			new String[] {
				AssetPublisherHelper.SCOPE_ID_LAYOUT_PREFIX +
					layout.getLayoutId()
			}
		).build();

		PortletPreferences portletPreferences = getImportedPortletPreferences(
			preferenceMap);

		Assert.assertEquals(
			AssetPublisherHelper.SCOPE_ID_LAYOUT_UUID_PREFIX +
				importedLayout.getUuid(),
			portletPreferences.getValue("scopeIds", null));
		Assert.assertEquals(null, portletPreferences.getValue("scopeId", null));
	}

	@Test
	public void testOneDLFileEntryType() throws Exception {
		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			group.getGroupId(), DLFileEntryMetadata.class.getName());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		DLFileEntryType dlFileEntryType = addDLFileEntryType(
			group.getGroupId(), ddmStructure.getStructureId(), serviceContext);

		serviceContext.setUuid(ddmStructure.getUuid());

		DDMStructure importedDDMStructure = DDMStructureTestUtil.addStructure(
			importedGroup.getGroupId(), DLFileEntryMetadata.class.getName(), 0,
			ddmStructure.getDDMForm(), LocaleUtil.getDefault(), serviceContext);

		serviceContext.setUuid(dlFileEntryType.getUuid());

		DLFileEntryType importedDLFileEntryType = addDLFileEntryType(
			importedGroup.getGroupId(), importedDDMStructure.getStructureId(),
			serviceContext);

		long dlFileEntryClassNameId = PortalUtil.getClassNameId(
			DLFileEntry.class);

		Map<String, String[]> preferenceMap = HashMapBuilder.put(
			"anyAssetType",
			new String[] {String.valueOf(dlFileEntryClassNameId)}
		).put(
			"anyClassTypeDLFileEntryAssetRendererFactory",
			new String[] {String.valueOf(dlFileEntryType.getFileEntryTypeId())}
		).put(
			"classTypeIds",
			new String[] {String.valueOf(dlFileEntryType.getFileEntryTypeId())}
		).build();

		PortletPreferences portletPreferences = getImportedPortletPreferences(
			preferenceMap);

		long anyClassTypeDLFileEntryAssetRendererFactory = GetterUtil.getLong(
			portletPreferences.getValue(
				"anyClassTypeDLFileEntryAssetRendererFactory", null));

		Assert.assertEquals(
			anyClassTypeDLFileEntryAssetRendererFactory,
			importedDLFileEntryType.getFileEntryTypeId());

		long anyAssetType = GetterUtil.getLong(
			portletPreferences.getValue("anyAssetType", null));

		Assert.assertEquals(dlFileEntryClassNameId, anyAssetType);

		long classTypeIds = GetterUtil.getLong(
			portletPreferences.getValue("classTypeIds", null));

		Assert.assertEquals(
			importedDLFileEntryType.getFileEntryTypeId(), classTypeIds);
	}

	@Test
	public void testOneJournalStructure() throws Exception {
		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			group.getGroupId(), JournalArticle.class.getName());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		serviceContext.setUuid(ddmStructure.getUuid());

		DDMStructure importedDDMStructure = DDMStructureTestUtil.addStructure(
			importedGroup.getGroupId(), JournalArticle.class.getName(), 0,
			ddmStructure.getDDMForm(), LocaleUtil.getDefault(), serviceContext);

		long journalArticleClassNameId = PortalUtil.getClassNameId(
			JournalArticle.class);

		Map<String, String[]> preferenceMap = HashMapBuilder.put(
			"anyAssetType",
			new String[] {String.valueOf(journalArticleClassNameId)}
		).put(
			"anyClassTypeJournalArticleAssetRendererFactory",
			new String[] {String.valueOf(ddmStructure.getStructureId())}
		).put(
			"classTypeIds",
			new String[] {String.valueOf(ddmStructure.getStructureId())}
		).build();

		PortletPreferences portletPreferences = getImportedPortletPreferences(
			preferenceMap);

		long anyClassTypeJournalArticleAssetRendererFactory =
			GetterUtil.getLong(
				portletPreferences.getValue(
					"anyClassTypeJournalArticleAssetRendererFactory", null));

		Assert.assertEquals(
			anyClassTypeJournalArticleAssetRendererFactory,
			importedDDMStructure.getStructureId());

		long anyAssetType = GetterUtil.getLong(
			portletPreferences.getValue("anyAssetType", null));

		Assert.assertEquals(journalArticleClassNameId, anyAssetType);

		long classTypeIds = GetterUtil.getLong(
			portletPreferences.getValue("classTypeIds", null));

		Assert.assertEquals(
			importedDDMStructure.getStructureId(), classTypeIds);
	}

	@Test
	public void testSeveralDLFileEntryTypes() throws Exception {
		DDMStructure ddmStructure1 = DDMStructureTestUtil.addStructure(
			group.getGroupId(), DLFileEntryMetadata.class.getName());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		DLFileEntryType dlFileEntryType1 = addDLFileEntryType(
			group.getGroupId(), ddmStructure1.getStructureId(), serviceContext);

		serviceContext.setUuid(ddmStructure1.getUuid());

		DDMStructure importedDDMStructure1 = DDMStructureTestUtil.addStructure(
			importedGroup.getGroupId(), DLFileEntryMetadata.class.getName(), 0,
			ddmStructure1.getDDMForm(), LocaleUtil.getDefault(),
			serviceContext);

		serviceContext.setUuid(dlFileEntryType1.getUuid());

		DLFileEntryType importedDLFileEntryType1 = addDLFileEntryType(
			importedGroup.getGroupId(), importedDDMStructure1.getStructureId(),
			serviceContext);

		DDMStructure ddmStructure2 = DDMStructureTestUtil.addStructure(
			group.getGroupId(), DLFileEntryMetadata.class.getName());

		serviceContext.setUuid(null);

		DLFileEntryType dlFileEntryType2 = addDLFileEntryType(
			group.getGroupId(), ddmStructure2.getStructureId(), serviceContext);

		serviceContext.setUuid(ddmStructure2.getUuid());

		DDMStructure importedDDMStructure2 = DDMStructureTestUtil.addStructure(
			importedGroup.getGroupId(), DLFileEntryMetadata.class.getName(), 0,
			ddmStructure2.getDDMForm(), LocaleUtil.getDefault(),
			serviceContext);

		serviceContext.setUuid(dlFileEntryType2.getUuid());

		DLFileEntryType importedDLFileEntryType2 = addDLFileEntryType(
			importedGroup.getGroupId(), importedDDMStructure2.getStructureId(),
			serviceContext);

		Map<String, String[]> preferenceMap = HashMapBuilder.put(
			"anyAssetType",
			new String[] {
				String.valueOf(PortalUtil.getClassNameId(DLFileEntry.class))
			}
		).put(
			"anyClassTypeDLFileEntryAssetRendererFactory",
			new String[] {String.valueOf(Boolean.FALSE)}
		).put(
			"classTypeIdsDLFileEntryAssetRendererFactory",
			new String[] {
				String.valueOf(dlFileEntryType1.getFileEntryTypeId()),
				String.valueOf(dlFileEntryType2.getFileEntryTypeId())
			}
		).build();

		PortletPreferences portletPreferences = getImportedPortletPreferences(
			preferenceMap);

		Assert.assertEquals(
			importedDLFileEntryType1.getFileEntryTypeId() + StringPool.COMMA +
				importedDLFileEntryType2.getFileEntryTypeId(),
			StringUtil.merge(
				portletPreferences.getValues(
					"classTypeIdsDLFileEntryAssetRendererFactory", null)));
	}

	@Test
	public void testSeveralJournalStructures() throws Exception {
		DDMStructure ddmStructure1 = DDMStructureTestUtil.addStructure(
			group.getGroupId(), JournalArticle.class.getName());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		serviceContext.setUuid(ddmStructure1.getUuid());

		DDMStructure importedDDMStructure1 = DDMStructureTestUtil.addStructure(
			importedGroup.getGroupId(), JournalArticle.class.getName(), 0,
			ddmStructure1.getDDMForm(), LocaleUtil.getDefault(),
			serviceContext);

		DDMStructure ddmStructure2 = DDMStructureTestUtil.addStructure(
			group.getGroupId(), JournalArticle.class.getName());

		serviceContext.setUuid(ddmStructure2.getUuid());

		DDMStructure importedDDMStructure2 = DDMStructureTestUtil.addStructure(
			importedGroup.getGroupId(), JournalArticle.class.getName(), 0,
			ddmStructure1.getDDMForm(), LocaleUtil.getDefault(),
			serviceContext);

		Map<String, String[]> preferenceMap = HashMapBuilder.put(
			"anyAssetType",
			new String[] {
				String.valueOf(PortalUtil.getClassNameId(JournalArticle.class))
			}
		).put(
			"anyClassTypeJournalArticleAssetRendererFactory",
			new String[] {String.valueOf(Boolean.FALSE)}
		).put(
			"classTypeIdsJournalArticleAssetRendererFactory",
			new String[] {
				String.valueOf(ddmStructure1.getStructureId()),
				String.valueOf(ddmStructure2.getStructureId())
			}
		).build();

		PortletPreferences portletPreferences = getImportedPortletPreferences(
			preferenceMap);

		Assert.assertEquals(
			importedDDMStructure1.getStructureId() + StringPool.COMMA +
				importedDDMStructure2.getStructureId(),
			StringUtil.merge(
				portletPreferences.getValues(
					"classTypeIdsJournalArticleAssetRendererFactory", null)));
	}

	@Test
	public void testSeveralLayoutScopeIds() throws Exception {
		Company company = CompanyLocalServiceUtil.getCompany(
			layout.getCompanyId());

		Layout secondLayout = LayoutTestUtil.addLayout(group);

		GroupTestUtil.addGroup(TestPropsValues.getUserId(), secondLayout);

		GroupTestUtil.addGroup(TestPropsValues.getUserId(), layout);

		Group companyGroup = company.getGroup();

		Map<String, String[]> preferenceMap = HashMapBuilder.put(
			"scopeIds",
			new String[] {
				AssetPublisherHelper.SCOPE_ID_GROUP_PREFIX +
					companyGroup.getGroupId(),
				AssetPublisherHelper.SCOPE_ID_LAYOUT_UUID_PREFIX +
					layout.getUuid(),
				AssetPublisherHelper.SCOPE_ID_LAYOUT_UUID_PREFIX +
					secondLayout.getUuid()
			}
		).build();

		PortletPreferences portletPreferences = getImportedPortletPreferences(
			preferenceMap);

		Layout importedSecondLayout =
			LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
				secondLayout.getUuid(), importedGroup.getGroupId(),
				importedLayout.isPrivateLayout());

		Assert.assertEquals(null, portletPreferences.getValue("scopeId", null));

		StringBundler sb = new StringBundler(8);

		sb.append(AssetPublisherHelper.SCOPE_ID_GROUP_PREFIX);
		sb.append(companyGroup.getGroupId());
		sb.append(StringPool.COMMA);
		sb.append(AssetPublisherHelper.SCOPE_ID_LAYOUT_UUID_PREFIX);
		sb.append(importedLayout.getUuid());
		sb.append(StringPool.COMMA);
		sb.append(AssetPublisherHelper.SCOPE_ID_LAYOUT_UUID_PREFIX);
		sb.append(importedSecondLayout.getUuid());

		Assert.assertEquals(
			sb.toString(),
			StringUtil.merge(portletPreferences.getValues("scopeIds", null)));
	}

	@Test
	public void testSeveralLegacyLayoutScopeIds() throws Exception {
		Layout secondLayout = LayoutTestUtil.addLayout(group);

		GroupTestUtil.addGroup(TestPropsValues.getUserId(), secondLayout);

		GroupTestUtil.addGroup(TestPropsValues.getUserId(), layout);

		Map<String, String[]> preferenceMap = HashMapBuilder.put(
			"scopeIds",
			new String[] {
				AssetPublisherHelper.SCOPE_ID_LAYOUT_PREFIX +
					layout.getLayoutId(),
				AssetPublisherHelper.SCOPE_ID_LAYOUT_PREFIX +
					secondLayout.getLayoutId()
			}
		).build();

		PortletPreferences portletPreferences = getImportedPortletPreferences(
			preferenceMap);

		Layout importedSecondLayout =
			LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
				secondLayout.getUuid(), importedGroup.getGroupId(),
				importedLayout.isPrivateLayout());

		Assert.assertEquals(null, portletPreferences.getValue("scopeId", null));

		StringBundler sb = new StringBundler(5);

		sb.append(AssetPublisherHelper.SCOPE_ID_LAYOUT_UUID_PREFIX);
		sb.append(importedLayout.getUuid());
		sb.append(StringPool.COMMA);
		sb.append(AssetPublisherHelper.SCOPE_ID_LAYOUT_UUID_PREFIX);
		sb.append(importedSecondLayout.getUuid());

		Assert.assertEquals(
			sb.toString(),
			StringUtil.merge(portletPreferences.getValues("scopeIds", null)));
	}

	@Test
	public void testSortByAssetVocabulary() throws Exception {
		testSortByAssetVocabulary(false);
	}

	@Test
	public void testSortByGlobalAssetVocabulary() throws Exception {
		testSortByAssetVocabulary(true);
	}

	protected List<AssetEntry> addAssetEntries(
			Group group, int count, List<AssetEntry> assetEntries,
			ServiceContext serviceContext)
		throws Exception {

		for (int i = 0; i < count; i++) {
			JournalArticle journalArticle = JournalTestUtil.addArticle(
				group.getGroupId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(100), serviceContext);

			assetEntries.add(getAssetEntry(journalArticle));
		}

		return assetEntries;
	}

	protected DLFileEntryType addDLFileEntryType(
			long groupId, long ddmStructureId, ServiceContext serviceContext)
		throws Exception {

		return DLFileEntryTypeLocalServiceUtil.addFileEntryType(
			serviceContext.getUserId(), groupId, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), new long[] {ddmStructureId},
			serviceContext);
	}

	protected void assertAssetEntries(
		List<AssetEntry> expectedAssetEntries,
		List<AssetEntry> actualAssetEntries) {

		Assert.assertEquals(
			actualAssetEntries.toString(), expectedAssetEntries.size(),
			actualAssetEntries.size());

		Iterator<AssetEntry> expectedAssetEntriesIterator =
			expectedAssetEntries.iterator();
		Iterator<AssetEntry> actualAssetEntriesIterator =
			expectedAssetEntries.iterator();

		while (expectedAssetEntriesIterator.hasNext() &&
			   actualAssetEntriesIterator.hasNext()) {

			AssetEntry expectedAssetEntry = expectedAssetEntriesIterator.next();
			AssetEntry actualAssetEntry = actualAssetEntriesIterator.next();

			Assert.assertEquals(
				expectedAssetEntry.getClassName(),
				actualAssetEntry.getClassName());
			Assert.assertEquals(
				expectedAssetEntry.getClassUuid(),
				actualAssetEntry.getClassUuid());
		}
	}

	@Override
	protected void exportImportPortlet(String portletId) throws Exception {
		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
			layout.getGroupId(), layout.isPrivateLayout());

		User user = TestPropsValues.getUser();

		Map<String, Serializable> exportLayoutSettingsMap =
			ExportImportConfigurationSettingsMapFactoryUtil.
				buildExportLayoutSettingsMap(
					user, layout.getGroupId(), layout.isPrivateLayout(),
					ExportImportHelperUtil.getLayoutIds(layouts),
					getExportParameterMap());

		ExportImportConfiguration exportConfiguration =
			ExportImportConfigurationLocalServiceUtil.
				addDraftExportImportConfiguration(
					user.getUserId(),
					ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT,
					exportLayoutSettingsMap);

		larFile = ExportImportLocalServiceUtil.exportLayoutsAsFile(
			exportConfiguration);

		// Import site LAR

		Map<String, Serializable> importLayoutSettingsMap =
			ExportImportConfigurationSettingsMapFactoryUtil.
				buildImportLayoutSettingsMap(
					user, importedGroup.getGroupId(), layout.isPrivateLayout(),
					null, getImportParameterMap());

		ExportImportConfiguration importConfiguration =
			ExportImportConfigurationLocalServiceUtil.
				addDraftExportImportConfiguration(
					user.getUserId(),
					ExportImportConfigurationConstants.TYPE_IMPORT_LAYOUT,
					importLayoutSettingsMap);

		ExportImportLocalServiceUtil.importLayouts(
			importConfiguration, larFile);

		importedLayout = LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
			layout.getUuid(), importedGroup.getGroupId(),
			layout.isPrivateLayout());

		Assert.assertNotNull(importedLayout);
	}

	protected String[] getAssetEntriesXmls(List<AssetEntry> assetEntries) {
		String[] assetEntriesXmls = new String[assetEntries.size()];

		for (int i = 0; i < assetEntries.size(); i++) {
			assetEntriesXmls[i] = AssetPublisherTestUtil.getAssetEntryXml(
				assetEntries.get(i));
		}

		return assetEntriesXmls;
	}

	@Override
	protected Map<String, String[]> getExportParameterMap() throws Exception {
		return HashMapBuilder.put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION_ALL,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_DATA,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_SETUP_ALL,
			new String[] {Boolean.TRUE.toString()}
		).build();
	}

	protected long[] getGroupIdsFromScopeIds(String[] scopeIds, Layout layout)
		throws Exception {

		long[] groupIds = new long[scopeIds.length];

		for (int i = 0; i < scopeIds.length; i++) {
			groupIds[i] = _assetPublisherHelper.getGroupIdFromScopeId(
				scopeIds[i], layout.getGroupId(), layout.isPrivateLayout());
		}

		return groupIds;
	}

	@Override
	protected Map<String, String[]> getImportParameterMap() throws Exception {
		return getExportParameterMap();
	}

	protected void testDynamicExportImport(
			Map<String, String[]> preferenceMap,
			List<AssetEntry> expectedAssetEntries, boolean filtering)
		throws Exception {

		if (filtering) {

			// Creating entries to validate filtering

			addAssetEntries(
				group, 2, new ArrayList<AssetEntry>(),
				ServiceContextTestUtil.getServiceContext());
		}

		String scopeId = _assetPublisherHelper.getScopeId(
			group, group.getGroupId());

		preferenceMap.put("scopeIds", new String[] {scopeId});

		preferenceMap.put("selectionStyle", new String[] {"dynamic"});

		PortletPreferences portletPreferences = getImportedPortletPreferences(
			preferenceMap);

		Company company = CompanyLocalServiceUtil.getCompany(
			TestPropsValues.getCompanyId());

		AssetEntryQuery assetEntryQuery =
			_assetPublisherHelper.getAssetEntryQuery(
				portletPreferences, importedGroup.getGroupId(), layout, null,
				null);

		SearchContainer<AssetEntry> searchContainer = new SearchContainer<>();

		searchContainer.setTotal(10);

		List<AssetEntryResult> actualAssetEntryResults =
			_assetPublisherHelper.getAssetEntryResults(
				searchContainer, assetEntryQuery, layout, portletPreferences,
				StringPool.BLANK, null, null, company.getCompanyId(),
				importedGroup.getGroupId(), TestPropsValues.getUserId(),
				assetEntryQuery.getClassNameIds(), null);

		List<AssetEntry> actualAssetEntries = new ArrayList<>();

		for (AssetEntryResult assetEntryResult : actualAssetEntryResults) {
			actualAssetEntries.addAll(assetEntryResult.getAssetEntries());
		}

		assertAssetEntries(expectedAssetEntries, actualAssetEntries);
	}

	protected void testExportImportAssetEntries(Group scopeGroup)
		throws Exception {

		testExportImportAssetEntries(ListUtil.fromArray(scopeGroup));
	}

	protected void testExportImportAssetEntries(List<Group> scopeGroups)
		throws Exception {

		List<AssetEntry> assetEntries = new ArrayList<>();
		String[] scopeIds = new String[0];

		for (Group scopeGroup : scopeGroups) {
			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext();

			if (scopeGroup.isLayout()) {

				// Creating structures and templates in layout scope group is
				// not possible

				Company company = CompanyLocalServiceUtil.getCompany(
					layout.getCompanyId());

				serviceContext.setAttribute("ddmGroupId", company.getGroupId());
			}

			assetEntries = addAssetEntries(
				scopeGroup, 3, assetEntries, serviceContext);

			String scopeId = _assetPublisherHelper.getScopeId(
				scopeGroup, group.getGroupId());

			scopeIds = ArrayUtil.append(scopeIds, scopeId);
		}

		Map<String, String[]> preferenceMap = HashMapBuilder.put(
			"assetEntryXml", getAssetEntriesXmls(assetEntries)
		).put(
			"scopeIds", scopeIds
		).build();

		PortletPreferences importedPortletPreferences =
			getImportedPortletPreferences(preferenceMap);

		String[] importedScopeIds = importedPortletPreferences.getValues(
			"scopeIds", null);

		long[] selectedGroupIds = getGroupIdsFromScopeIds(
			importedScopeIds, importedLayout);

		List<AssetEntry> actualAssetEntries =
			_assetPublisherHelper.getAssetEntries(
				new MockPortletRequest(), importedPortletPreferences,
				_permissionChecker, selectedGroupIds, false, false);

		assertAssetEntries(assetEntries, actualAssetEntries);
	}

	protected void testSortByAssetVocabulary(boolean globalVocabulary)
		throws Exception {

		long groupId = group.getGroupId();

		if (globalVocabulary) {
			Company company = CompanyLocalServiceUtil.getCompany(
				layout.getCompanyId());

			groupId = company.getGroupId();
		}

		AssetVocabulary assetVocabulary =
			AssetVocabularyLocalServiceUtil.addVocabulary(
				TestPropsValues.getUserId(), groupId,
				RandomTestUtil.randomString(),
				ServiceContextTestUtil.getServiceContext(groupId));

		Map<String, String[]> preferenceMap = HashMapBuilder.put(
			"assetVocabularyId",
			new String[] {String.valueOf(assetVocabulary.getVocabularyId())}
		).build();

		PortletPreferences portletPreferences = getImportedPortletPreferences(
			preferenceMap);

		Assert.assertNotNull(
			"Portlet preference \"assetVocabularyId\" is null",
			portletPreferences.getValue("assetVocabularyId", null));

		long importedAssetVocabularyId = GetterUtil.getLong(
			portletPreferences.getValue("assetVocabularyId", null));

		AssetVocabulary importedVocabulary =
			AssetVocabularyLocalServiceUtil.fetchAssetVocabulary(
				importedAssetVocabularyId);

		Assert.assertNotNull(
			"Vocabulary " + importedAssetVocabularyId + " does not exist",
			importedVocabulary);

		long expectedGroupId = groupId;

		if (!globalVocabulary) {
			expectedGroupId = importedGroup.getGroupId();
		}

		Assert.assertEquals(
			StringBundler.concat(
				"Vocabulary ", importedAssetVocabularyId,
				" does not belong to group ", expectedGroupId),
			expectedGroupId, importedVocabulary.getGroupId());

		AssetVocabularyLocalServiceUtil.deleteAssetVocabulary(assetVocabulary);
	}

	@Inject
	private AssetPublisherHelper _assetPublisherHelper;

	private PermissionChecker _permissionChecker;

}