/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalCallbackAwareExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.LayoutTestUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.assetpublisher.util.AssetPublisherUtil;

import java.io.File;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletPreferences;

import org.junit.Assert;
import org.junit.Before;
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
public class AssetPublisherExportImportTest extends BaseExportImportTestCase {

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_layout = LayoutTestUtil.addLayout(
			_group.getGroupId(), ServiceTestUtil.randomString());

		// Delete and readd to ensure a different layout ID (not ID or UUID).
		// See LPS-32132.

		LayoutLocalServiceUtil.deleteLayout(
			_layout, true, new ServiceContext());

		_layout = LayoutTestUtil.addLayout(
			_group.getGroupId(), ServiceTestUtil.randomString());
	}

	@Test
	public void testChildLayoutScopeIds() throws Exception {
		Map<String, String[]> preferenceMap = new HashMap<String, String[]>();

		Group childGroup = GroupTestUtil.addGroup(
			_group.getGroupId(), ServiceTestUtil.randomString());

		preferenceMap.put(
			"scopeIds",
			new String[] {
				AssetPublisherUtil.SCOPE_ID_CHILD_GROUP_PREFIX +
					childGroup.getGroupId()
			});

		PortletPreferences portletPreferences = getImportedPortletPreferences(
			_layout, preferenceMap);

		Assert.assertEquals(null, portletPreferences.getValue("scopeId", null));
		Assert.assertTrue(
			"The child group ID should have been filtered out on import",
			Validator.isNull(portletPreferences.getValues("scopeIds", null)));
	}

	@Test
	public void testGlobalScopeId() throws Exception {
		Map<String, String[]> preferenceMap = new HashMap<String, String[]>();

		Company company = CompanyLocalServiceUtil.getCompany(
			_layout.getCompanyId());

		Group companyGroup = company.getGroup();

		preferenceMap.put(
			"scopeIds",
			new String[] {
				AssetPublisherUtil.SCOPE_ID_GROUP_PREFIX +
					companyGroup.getGroupId()
			});

		PortletPreferences portletPreferences = getImportedPortletPreferences(
			_layout, preferenceMap);

		Assert.assertEquals(
			AssetPublisherUtil.SCOPE_ID_GROUP_PREFIX +
				companyGroup.getGroupId(),
			portletPreferences.getValue("scopeIds", null));
		Assert.assertEquals(null, portletPreferences.getValue("scopeId", null));
	}

	@Test
	public void testLayoutScopeId() throws Exception {
		Map<String, String[]> preferenceMap = new HashMap<String, String[]>();

		GroupTestUtil.addGroup(TestPropsValues.getUserId(), _layout);

		preferenceMap.put(
			"scopeIds",
			new String[] {
				AssetPublisherUtil.SCOPE_ID_LAYOUT_UUID_PREFIX +
					_layout.getUuid()
			});

		PortletPreferences portletPreferences = getImportedPortletPreferences(
			_layout, preferenceMap);

		Assert.assertEquals(
			AssetPublisherUtil.SCOPE_ID_LAYOUT_UUID_PREFIX +
				_importedLayout.getUuid(),
			portletPreferences.getValue("scopeIds", null));
		Assert.assertEquals(null, portletPreferences.getValue("scopeId", null));
	}

	@Test
	public void testLegacyLayoutScopeId() throws Exception {
		Map<String, String[]> preferenceMap = new HashMap<String, String[]>();

		GroupTestUtil.addGroup(TestPropsValues.getUserId(), _layout);

		preferenceMap.put(
			"scopeIds", new String[] {
				AssetPublisherUtil.SCOPE_ID_LAYOUT_PREFIX +
					_layout.getLayoutId()
			});

		PortletPreferences portletPreferences = getImportedPortletPreferences(
			_layout, preferenceMap);

		Assert.assertEquals(
			AssetPublisherUtil.SCOPE_ID_LAYOUT_UUID_PREFIX +
				_importedLayout.getUuid(),
			portletPreferences.getValue("scopeIds", null));
		Assert.assertEquals(null, portletPreferences.getValue("scopeId", null));
	}

	@Test
	public void testSeveralLayoutScopeIds() throws Exception {
		Company company = CompanyLocalServiceUtil.getCompany(
			_layout.getCompanyId());

		Layout secondLayout = LayoutTestUtil.addLayout(
			_group.getGroupId(), ServiceTestUtil.randomString());

		GroupTestUtil.addGroup(TestPropsValues.getUserId(), secondLayout);

		Map<String, String[]> preferenceMap = new HashMap<String, String[]>();

		GroupTestUtil.addGroup(TestPropsValues.getUserId(), _layout);

		Group companyGroup = company.getGroup();

		preferenceMap.put(
			"scopeIds",
			new String[] {
				AssetPublisherUtil.SCOPE_ID_GROUP_PREFIX +
					companyGroup.getGroupId(),
				AssetPublisherUtil.SCOPE_ID_LAYOUT_UUID_PREFIX +
					_layout.getUuid(),
				AssetPublisherUtil.SCOPE_ID_LAYOUT_UUID_PREFIX +
					secondLayout.getUuid()
			});

		PortletPreferences portletPreferences = getImportedPortletPreferences(
			_layout, preferenceMap);

		Layout importedSecondLayout =
			LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
				secondLayout.getUuid(), _importedGroup.getGroupId(),
				_importedLayout.isPrivateLayout());

		Assert.assertEquals(null, portletPreferences.getValue("scopeId", null));

		StringBundler sb = new StringBundler(8);

		sb.append(AssetPublisherUtil.SCOPE_ID_GROUP_PREFIX);
		sb.append(companyGroup.getGroupId());
		sb.append(StringPool.COMMA);
		sb.append(AssetPublisherUtil.SCOPE_ID_LAYOUT_UUID_PREFIX);
		sb.append(_importedLayout.getUuid());
		sb.append(StringPool.COMMA);
		sb.append(AssetPublisherUtil.SCOPE_ID_LAYOUT_UUID_PREFIX);
		sb.append(importedSecondLayout.getUuid());

		Assert.assertEquals(
			sb.toString(),
			StringUtil.merge(portletPreferences.getValues("scopeIds", null)));
	}

	@Test
	public void testSeveralLegacyLayoutScopeIds() throws Exception {
		Layout secondLayout = LayoutTestUtil.addLayout(
			_group.getGroupId(), ServiceTestUtil.randomString());

		GroupTestUtil.addGroup(TestPropsValues.getUserId(), secondLayout);

		Map<String, String[]> preferenceMap = new HashMap<String, String[]>();

		GroupTestUtil.addGroup(TestPropsValues.getUserId(), _layout);

		preferenceMap.put(
			"scopeIds",
			new String[] {
				AssetPublisherUtil.SCOPE_ID_LAYOUT_PREFIX +
					_layout.getLayoutId(),
				AssetPublisherUtil.SCOPE_ID_LAYOUT_PREFIX +
					secondLayout.getLayoutId()
			});

		PortletPreferences portletPreferences = getImportedPortletPreferences(
			_layout, preferenceMap);

		Layout importedSecondLayout =
			LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
				secondLayout.getUuid(), _importedGroup.getGroupId(),
				_importedLayout.isPrivateLayout());

		Assert.assertEquals(null, portletPreferences.getValue("scopeId", null));

		StringBundler sb = new StringBundler(5);

		sb.append(AssetPublisherUtil.SCOPE_ID_LAYOUT_UUID_PREFIX);
		sb.append(_importedLayout.getUuid());
		sb.append(StringPool.COMMA);
		sb.append(AssetPublisherUtil.SCOPE_ID_LAYOUT_UUID_PREFIX);
		sb.append(importedSecondLayout.getUuid());

		Assert.assertEquals(
			sb.toString(),
			StringUtil.merge(portletPreferences.getValues("scopeIds", null)));
	}

	protected String addAssetPublisherPortletToLayout(
			long userId, Layout layout, String columnId,
			Map<String, String[]> preferenceMap)
		throws Exception {

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		String assetPublisherPortletId = layoutTypePortlet.addPortletId(
			userId, PortletKeys.ASSET_PUBLISHER, columnId, -1);

		LayoutLocalServiceUtil.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			layout.getTypeSettings());

		PortletPreferences portletPreferences = getPortletPreferences(
			layout.getCompanyId(), layout.getPlid(), assetPublisherPortletId);

		for (String key : preferenceMap.keySet()) {
			portletPreferences.setValues(key, preferenceMap.get(key));
		}

		updatePortletPreferences(
			layout.getPlid(), assetPublisherPortletId, portletPreferences);

		return assetPublisherPortletId;
	}

	protected PortletPreferences getImportedPortletPreferences(
			Layout layout, Map<String, String[]> preferenceMap)
		throws Exception {

		// Export site LAR

		String assetPublisherPortletId = addAssetPublisherPortletToLayout(
			TestPropsValues.getUserId(), _layout, "column-1", preferenceMap);

		Map<String, String[]> parameterMap =  new HashMap<String, String[]>();

		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_SETUP,
			new String[] {Boolean.TRUE.toString()});

		File file = LayoutLocalServiceUtil.exportLayoutsAsFile(
			layout.getGroupId(), layout.isPrivateLayout(), null, parameterMap,
			null, null);

		_importedGroup = GroupTestUtil.addGroup();

		// Import site LAR

		LayoutLocalServiceUtil.importLayouts(
			TestPropsValues.getUserId(), _importedGroup.getGroupId(),
			layout.isPrivateLayout(), parameterMap, file);

		_importedLayout = LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
			layout.getUuid(), _importedGroup.getGroupId(),
			layout.isPrivateLayout());

		Assert.assertNotNull(_importedLayout);

		return getPortletPreferences(
			_importedLayout.getCompanyId(), _importedLayout.getPlid(),
			assetPublisherPortletId);
	}

	protected PortletPreferences getPortletPreferences(
			long companyId, long plid, String portletId)
		throws Exception {

		return PortletPreferencesLocalServiceUtil.getPreferences(
			companyId, PortletKeys.PREFS_OWNER_ID_DEFAULT,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, plid, portletId);
	}

	protected void updatePortletPreferences(
			long plid, String portletId, PortletPreferences portletPreferences)
		throws Exception {

		PortletPreferencesLocalServiceUtil.updatePreferences(
			PortletKeys.PREFS_OWNER_ID_DEFAULT,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, plid, portletId,
			portletPreferences);
	}

	private Group _group;
	private Group _importedGroup;
	private Layout _importedLayout;
	private Layout _layout;

}