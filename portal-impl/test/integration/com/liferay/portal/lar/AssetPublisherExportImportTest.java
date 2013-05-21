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
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalCallbackAwareExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.LayoutTestUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil;
import com.liferay.portlet.assetpublisher.util.AssetPublisher;

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

		importedGroup = GroupTestUtil.addGroup();

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
			PortletDataHandlerKeys.PORTLET_SETUP,
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