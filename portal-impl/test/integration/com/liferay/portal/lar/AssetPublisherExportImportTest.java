/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.lar;

import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalCallbackAwareExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.LayoutTestUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil;
import com.liferay.portlet.assetpublisher.util.AssetPublisher;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.util.DLAppTestUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.util.DDMStructureTestUtil;
import com.liferay.portlet.journal.model.JournalArticle;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletPreferences;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.core.classloader.annotations.PrepareForTest;

/**
 * @author Julio Camarero
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		TransactionalCallbackAwareExecutionTestListener.class
	})
@PrepareForTest({PortletLocalServiceUtil.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class AssetPublisherExportImportTest
	extends BasePortletExportImportTestCase {

	@Test
	public void testAnyDLFileEntryType() throws Exception {
		Map<String, String[]> preferenceMap = new HashMap<String, String[]>();

		long dlFileEntryClassNameId = PortalUtil.getClassNameId(
			DLFileEntry.class);

		preferenceMap.put(
			"anyAssetType",
			new String[] {String.valueOf(dlFileEntryClassNameId)});
		preferenceMap.put(
			"anyClassTypeDLFileEntryAssetRendererFactory",
			new String[] {
				String.valueOf(Boolean.TRUE)
			});

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
		Map<String, String[]> preferenceMap = new HashMap<String, String[]>();

		long journalArticleClassNameId = PortalUtil.getClassNameId(
			JournalArticle.class);

		preferenceMap.put(
			"anyAssetType",
			new String[] {String.valueOf(journalArticleClassNameId)});
		preferenceMap.put(
			"anyClassTypeJournalArticleAssetRendererFactory",
			new String[] {
				String.valueOf(Boolean.TRUE)
			});

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
	public void testChildLayoutScopeIds() throws Exception {
		Map<String, String[]> preferenceMap = new HashMap<String, String[]>();

		Group childGroup = GroupTestUtil.addGroup(
			group.getGroupId(), ServiceTestUtil.randomString());

		preferenceMap.put(
			"scopeIds",
			new String[] {
				AssetPublisher.SCOPE_ID_CHILD_GROUP_PREFIX +
					childGroup.getGroupId()
			});

		PortletPreferences portletPreferences = getImportedPortletPreferences(
			preferenceMap);

		Assert.assertEquals(null, portletPreferences.getValue("scopeId", null));
		Assert.assertTrue(
			"The child group ID should have been filtered out on import",
			Validator.isNull(portletPreferences.getValues("scopeIds", null)));
	}

	@Override
	public void testExportImportAssetLinks() throws Exception {
		Assert.assertTrue("This test does not apply", true);
	}

	@Test
	public void testGlobalScopeId() throws Exception {
		Map<String, String[]> preferenceMap = new HashMap<String, String[]>();

		Company company = CompanyLocalServiceUtil.getCompany(
			layout.getCompanyId());

		Group companyGroup = company.getGroup();

		preferenceMap.put(
			"scopeIds",
			new String[] {
				AssetPublisher.SCOPE_ID_GROUP_PREFIX + companyGroup.getGroupId()
			});

		PortletPreferences portletPreferences = getImportedPortletPreferences(
			preferenceMap);

		Assert.assertEquals(
			AssetPublisher.SCOPE_ID_GROUP_PREFIX + companyGroup.getGroupId(),
			portletPreferences.getValue("scopeIds", null));
		Assert.assertEquals(null, portletPreferences.getValue("scopeId", null));
	}

	@Test
	public void testLayoutScopeId() throws Exception {
		Map<String, String[]> preferenceMap = new HashMap<String, String[]>();

		GroupTestUtil.addGroup(TestPropsValues.getUserId(), layout);

		preferenceMap.put(
			"scopeIds",
			new String[] {
				AssetPublisher.SCOPE_ID_LAYOUT_UUID_PREFIX + layout.getUuid()
			});

		PortletPreferences portletPreferences = getImportedPortletPreferences(
			preferenceMap);

		Assert.assertEquals(
			AssetPublisher.SCOPE_ID_LAYOUT_UUID_PREFIX +
			importedLayout.getUuid(),
			portletPreferences.getValue("scopeIds", null));
		Assert.assertEquals(null, portletPreferences.getValue("scopeId", null));
	}

	@Test
	public void testLegacyLayoutScopeId() throws Exception {
		Map<String, String[]> preferenceMap = new HashMap<String, String[]>();

		GroupTestUtil.addGroup(TestPropsValues.getUserId(), layout);

		preferenceMap.put(
			"scopeIds", new String[] {
				AssetPublisher.SCOPE_ID_LAYOUT_PREFIX + layout.getLayoutId()
			});

		PortletPreferences portletPreferences = getImportedPortletPreferences(
			preferenceMap);

		Assert.assertEquals(
			AssetPublisher.SCOPE_ID_LAYOUT_UUID_PREFIX +
				importedLayout.getUuid(),
			portletPreferences.getValue("scopeIds", null));
		Assert.assertEquals(null, portletPreferences.getValue("scopeId", null));
	}

	@Test
	public void testOneDLFileEntryType() throws Exception {
		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			group.getGroupId(), DLFileEntryType.class.getName());

		DLFileEntryType dlFileEntryType = DLAppTestUtil.addDLFileEntryType(
			group.getGroupId(), ddmStructure.getStructureId());

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		serviceContext.setUuid(ddmStructure.getUuid());

		DDMStructure importedDDMStructure = DDMStructureTestUtil.addStructure(
			importedGroup.getGroupId(), DLFileEntryType.class.getName(),
			ddmStructure.getXsd(), LocaleUtil.getDefault(), serviceContext);

		serviceContext.setUuid(dlFileEntryType.getUuid());

		DLFileEntryType importedDLFileEntryType =
			DLAppTestUtil.addDLFileEntryType(
				TestPropsValues.getUserId(), importedGroup.getGroupId(),
				ServiceTestUtil.randomString(), ServiceTestUtil.randomString(),
				new long[] {importedDDMStructure.getStructureId()},
				serviceContext);

		Map<String, String[]> preferenceMap = new HashMap<String, String[]>();

		long dlFileEntryClassNameId = PortalUtil.getClassNameId(
			DLFileEntry.class);

		preferenceMap.put(
			"anyAssetType",
			new String[] {String.valueOf(dlFileEntryClassNameId)});
		preferenceMap.put(
			"anyClassTypeDLFileEntryAssetRendererFactory",
			new String[] {
				String.valueOf(dlFileEntryType.getFileEntryTypeId())
			});
		preferenceMap.put(
			"classTypeIds",
			new String[] {
				String.valueOf(dlFileEntryType.getFileEntryTypeId())
			});

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

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		serviceContext.setUuid(ddmStructure.getUuid());

		DDMStructure importedDDMStructure = DDMStructureTestUtil.addStructure(
			importedGroup.getGroupId(), JournalArticle.class.getName(),
			ddmStructure.getXsd(), LocaleUtil.getDefault(), serviceContext);

		Map<String, String[]> preferenceMap = new HashMap<String, String[]>();

		long journalArticleClassNameId = PortalUtil.getClassNameId(
			JournalArticle.class);

		preferenceMap.put(
			"anyAssetType",
			new String[] {String.valueOf(journalArticleClassNameId)});
		preferenceMap.put(
			"anyClassTypeJournalArticleAssetRendererFactory",
			new String[] {
				String.valueOf(ddmStructure.getStructureId())
			});
		preferenceMap.put(
			"classTypeIds",
			new String[] {String.valueOf(ddmStructure.getStructureId())});

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
			group.getGroupId(), DLFileEntryType.class.getName());

		DLFileEntryType dlFileEntryType1 = DLAppTestUtil.addDLFileEntryType(
			group.getGroupId(), ddmStructure1.getStructureId());

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		serviceContext.setUuid(ddmStructure1.getUuid());

		DDMStructure importedDDMStructure1 = DDMStructureTestUtil.addStructure(
			importedGroup.getGroupId(), DLFileEntryType.class.getName(),
			ddmStructure1.getXsd(), LocaleUtil.getDefault(), serviceContext);

		serviceContext.setUuid(dlFileEntryType1.getUuid());

		DLFileEntryType importedDLFileEntryType1 =
			DLAppTestUtil.addDLFileEntryType(
				TestPropsValues.getUserId(), importedGroup.getGroupId(),
				ServiceTestUtil.randomString(), ServiceTestUtil.randomString(),
				new long[] {importedDDMStructure1.getStructureId()},
				serviceContext);

		DDMStructure ddmStructure2 = DDMStructureTestUtil.addStructure(
			group.getGroupId(), DLFileEntryType.class.getName());

		DLFileEntryType dlFileEntryType2 = DLAppTestUtil.addDLFileEntryType(
			group.getGroupId(), ddmStructure2.getStructureId());

		serviceContext.setUuid(ddmStructure2.getUuid());

		DDMStructure importedDDMStructure2 = DDMStructureTestUtil.addStructure(
			importedGroup.getGroupId(), DLFileEntryType.class.getName(),
			ddmStructure2.getXsd(), LocaleUtil.getDefault(), serviceContext);

		serviceContext.setUuid(dlFileEntryType2.getUuid());

		DLFileEntryType importedDLFileEntryType2 =
			DLAppTestUtil.addDLFileEntryType(
				TestPropsValues.getUserId(), importedGroup.getGroupId(),
				ServiceTestUtil.randomString(), ServiceTestUtil.randomString(),
				new long[] {importedDDMStructure2.getStructureId()},
				serviceContext);

		Map<String, String[]> preferenceMap = new HashMap<String, String[]>();

		preferenceMap.put(
			"anyClassTypeDLFileEntryAssetRendererFactory",
			new String[] {
				String.valueOf(Boolean.FALSE)
			});

		preferenceMap.put(
			"classTypeIdsDLFileEntryAssetRendererFactory",
			new String[] {
				String.valueOf(dlFileEntryType1.getFileEntryTypeId()),
				String.valueOf(dlFileEntryType2.getFileEntryTypeId())
			});

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

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		serviceContext.setUuid(ddmStructure1.getUuid());

		DDMStructure importedDDMStructure1 = DDMStructureTestUtil.addStructure(
			importedGroup.getGroupId(), JournalArticle.class.getName(),
			ddmStructure1.getXsd(), LocaleUtil.getDefault(), serviceContext);

		DDMStructure ddmStructure2 = DDMStructureTestUtil.addStructure(
			group.getGroupId(), JournalArticle.class.getName());

		serviceContext.setUuid(ddmStructure2.getUuid());

		DDMStructure importedDDMStructure2 = DDMStructureTestUtil.addStructure(
			importedGroup.getGroupId(), JournalArticle.class.getName(),
			ddmStructure1.getXsd(), LocaleUtil.getDefault(), serviceContext);

		Map<String, String[]> preferenceMap = new HashMap<String, String[]>();

		preferenceMap.put(
			"anyClassTypeJournalArticleAssetRendererFactory",
			new String[] {
				String.valueOf(Boolean.FALSE)
			});

		preferenceMap.put(
			"classTypeIdsJournalArticleAssetRendererFactory",
			new String[] {
				String.valueOf(ddmStructure1.getStructureId()),
				String.valueOf(ddmStructure2.getStructureId())
			});

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

		Layout secondLayout = LayoutTestUtil.addLayout(
			group.getGroupId(), ServiceTestUtil.randomString());

		GroupTestUtil.addGroup(TestPropsValues.getUserId(), secondLayout);

		Map<String, String[]> preferenceMap = new HashMap<String, String[]>();

		GroupTestUtil.addGroup(TestPropsValues.getUserId(), layout);

		Group companyGroup = company.getGroup();

		preferenceMap.put(
			"scopeIds",
			new String[] {
				AssetPublisher.SCOPE_ID_GROUP_PREFIX +
					companyGroup.getGroupId(),
				AssetPublisher.SCOPE_ID_LAYOUT_UUID_PREFIX + layout.getUuid(),
				AssetPublisher.SCOPE_ID_LAYOUT_UUID_PREFIX +
					secondLayout.getUuid()
			});

		PortletPreferences portletPreferences = getImportedPortletPreferences(
			preferenceMap);

		Layout importedSecondLayout =
			LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
				secondLayout.getUuid(), importedGroup.getGroupId(),
				importedLayout.isPrivateLayout());

		Assert.assertEquals(null, portletPreferences.getValue("scopeId", null));

		StringBundler sb = new StringBundler(8);

		sb.append(AssetPublisher.SCOPE_ID_GROUP_PREFIX);
		sb.append(companyGroup.getGroupId());
		sb.append(StringPool.COMMA);
		sb.append(AssetPublisher.SCOPE_ID_LAYOUT_UUID_PREFIX);
		sb.append(importedLayout.getUuid());
		sb.append(StringPool.COMMA);
		sb.append(AssetPublisher.SCOPE_ID_LAYOUT_UUID_PREFIX);
		sb.append(importedSecondLayout.getUuid());

		Assert.assertEquals(
			sb.toString(),
			StringUtil.merge(portletPreferences.getValues("scopeIds", null)));
	}

	@Test
	public void testSeveralLegacyLayoutScopeIds() throws Exception {
		Layout secondLayout = LayoutTestUtil.addLayout(
			group.getGroupId(), ServiceTestUtil.randomString());

		GroupTestUtil.addGroup(TestPropsValues.getUserId(), secondLayout);

		Map<String, String[]> preferenceMap = new HashMap<String, String[]>();

		GroupTestUtil.addGroup(TestPropsValues.getUserId(), layout);

		preferenceMap.put(
			"scopeIds",
			new String[] {
				AssetPublisher.SCOPE_ID_LAYOUT_PREFIX + layout.getLayoutId(),
				AssetPublisher.SCOPE_ID_LAYOUT_PREFIX +
					secondLayout.getLayoutId()
			});

		PortletPreferences portletPreferences = getImportedPortletPreferences(
			preferenceMap);

		Layout importedSecondLayout =
			LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
				secondLayout.getUuid(), importedGroup.getGroupId(),
				importedLayout.isPrivateLayout());

		Assert.assertEquals(null, portletPreferences.getValue("scopeId", null));

		StringBundler sb = new StringBundler(5);

		sb.append(AssetPublisher.SCOPE_ID_LAYOUT_UUID_PREFIX);
		sb.append(importedLayout.getUuid());
		sb.append(StringPool.COMMA);
		sb.append(AssetPublisher.SCOPE_ID_LAYOUT_UUID_PREFIX);
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

	@Override
	protected void doExportImportPortlet(String portletId) throws Exception {
		larFile = LayoutLocalServiceUtil.exportLayoutsAsFile(
			layout.getGroupId(), layout.isPrivateLayout(), null,
			getExportParameterMap(), null, null);

		// Import site LAR

		LayoutLocalServiceUtil.importLayouts(
			TestPropsValues.getUserId(), importedGroup.getGroupId(),
			layout.isPrivateLayout(), getImportParameterMap(), larFile);

		importedLayout = LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
			layout.getUuid(), importedGroup.getGroupId(),
			layout.isPrivateLayout());

		Assert.assertNotNull(importedLayout);
	}

	@Override
	protected Map<String, String[]> getExportParameterMap() throws Exception {
		Map<String, String[]> parameterMap =  new HashMap<String, String[]>();

		parameterMap.put(
			PortletDataHandlerKeys.CATEGORIES,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION_ALL,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_SETUP_ALL,
			new String[] {Boolean.TRUE.toString()});

		return parameterMap;
	}

	protected PortletPreferences getImportedPortletPreferences(
			Map<String, String[]> preferenceMap)
		throws Exception {

		// Export site LAR

		String assetPublisherPortletId = LayoutTestUtil.addPortletToLayout(
			TestPropsValues.getUserId(), this.layout,
			PortletKeys.ASSET_PUBLISHER, "column-1", preferenceMap);

		doExportImportPortlet(assetPublisherPortletId);

		return LayoutTestUtil.getPortletPreferences(
			importedLayout.getCompanyId(), importedLayout.getPlid(),
			assetPublisherPortletId);
	}

	@Override
	protected Map<String, String[]> getImportParameterMap() throws Exception {
		return getExportParameterMap();
	}

	protected void testSortByAssetVocabulary(boolean globalVocabulary)
		throws Exception {

		long groupId = group.getGroupId();

		if (globalVocabulary) {
			Company company = CompanyLocalServiceUtil.getCompany(
				layout.getCompanyId());

			Group companyGroup = company.getGroup();

			groupId = companyGroup.getGroupId();
		}

		AssetVocabulary assetVocabulary =
			AssetVocabularyLocalServiceUtil.addVocabulary(
				TestPropsValues.getUserId(), ServiceTestUtil.randomString(),
				ServiceTestUtil.getServiceContext(groupId));

		Map<String, String[]> preferenceMap = new HashMap<String, String[]>();

		preferenceMap.put(
			"assetVocabularyId",
			new String[] {String.valueOf(assetVocabulary.getVocabularyId())});

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
			"Vocabulary " + importedAssetVocabularyId +
				" does not belong to group " + expectedGroupId,
			expectedGroupId, importedVocabulary.getGroupId());

		AssetVocabularyLocalServiceUtil.deleteAssetVocabulary(assetVocabulary);
	}

}