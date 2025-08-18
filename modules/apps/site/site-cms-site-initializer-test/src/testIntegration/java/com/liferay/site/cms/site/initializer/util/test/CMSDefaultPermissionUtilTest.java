/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.batch.engine.unit.BatchEngineUnitProcessor;
import com.liferay.batch.engine.unit.BatchEngineUnitReader;
import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.object.constants.ObjectFolderConstants;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectFolder;
import com.liferay.object.service.ObjectFolderLocalService;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.site.cms.site.initializer.util.CMSDefaultPermissionUtil;

import java.io.File;
import java.io.Serializable;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Stefano Motta
 */
@FeatureFlag("LPD-17564")
@RunWith(Arquillian.class)
public class CMSDefaultPermissionUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_depotEntry = _depotEntryLocalService.addDepotEntry(
			HashMapBuilder.put(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()
			).build(),
			HashMapBuilder.put(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()
			).build(),
			DepotConstants.TYPE_SPACE,
			ServiceContextTestUtil.getServiceContext());

		if (_isCMSSiteInitialized()) {
			return;
		}

		// These tests require the instance to be created with the feature
		// flag LPD-17564 enabled. On CI, feature flags are enabled on
		// demand for each test, but not during instance initialization.
		// Until the feature flag LPD-17564 is removed, run the batch
		// engine unit processor manually so that the object definitions
		// are created.

		Bundle testBundle = FrameworkUtil.getBundle(
			CMSDefaultPermissionUtilTest.class);

		BundleContext bundleContext = testBundle.getBundleContext();

		for (Bundle bundle : bundleContext.getBundles()) {
			if (!Objects.equals(
					bundle.getSymbolicName(),
					"com.liferay.site.initializer.cms")) {

				continue;
			}

			_setUpProcessedFile(bundle, "01.object.folder");
			_setUpProcessedFile(bundle, "02.object.definition");

			CompletableFuture<Void> completableFuture =
				_batchEngineUnitProcessor.processBatchEngineUnits(
					_batchEngineUnitReader.getBatchEngineUnits(bundle));

			completableFuture.join();
		}
	}

	@Test
	public void testAddOrUpdateDefaultsPermission() throws Exception {
		Group group = _depotEntry.getGroup();

		ObjectEntry objectEntry1 =
			CMSDefaultPermissionUtil.addOrUpdateCMSDefaultsPermission(
				group.getExternalReferenceCode(), group.getGroupId(),
				TestPropsValues.getUserId(), group.getExternalReferenceCode(),
				_depotEntry.getModelClassName(),
				JSONUtil.put(
					"L_BASIC_WEB_CONTENT", JSONUtil.putAll(ActionKeys.VIEW)));

		Map<String, Serializable> values = objectEntry1.getValues();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			String.valueOf(values.getOrDefault("permissions", "{}")));

		JSONArray jsonArray = jsonObject.getJSONArray("L_BASIC_WEB_CONTENT");

		Assert.assertEquals(1, jsonArray.length());
		Assert.assertEquals(ActionKeys.VIEW, jsonArray.getString(0));

		ObjectEntry objectEntry2 =
			CMSDefaultPermissionUtil.addOrUpdateCMSDefaultsPermission(
				group.getExternalReferenceCode(), group.getGroupId(),
				TestPropsValues.getUserId(), group.getExternalReferenceCode(),
				_depotEntry.getModelClassName(),
				JSONUtil.put(
					"L_BASIC_WEB_CONTENT",
					JSONUtil.putAll(ActionKeys.UPDATE, ActionKeys.VIEW)));

		Assert.assertEquals(
			objectEntry1.getObjectEntryId(), objectEntry2.getObjectEntryId());

		values = objectEntry2.getValues();

		jsonObject = JSONFactoryUtil.createJSONObject(
			String.valueOf(values.getOrDefault("permissions", "{}")));

		jsonArray = jsonObject.getJSONArray("L_BASIC_WEB_CONTENT");

		Assert.assertEquals(2, jsonArray.length());
		Assert.assertEquals(ActionKeys.UPDATE, jsonArray.getString(0));
		Assert.assertEquals(ActionKeys.VIEW, jsonArray.getString(1));
	}

	@Test
	public void testGetCMSDefaultPermissionPermissions() throws Exception {
		Group group = _depotEntry.getGroup();

		CMSDefaultPermissionUtil.addOrUpdateCMSDefaultsPermission(
			group.getExternalReferenceCode(), group.getGroupId(),
			TestPropsValues.getUserId(), group.getExternalReferenceCode(),
			_depotEntry.getModelClassName(),
			JSONUtil.put(
				"L_BASIC_WEB_CONTENT",
				JSONUtil.putAll(ActionKeys.UPDATE, ActionKeys.VIEW)));

		JSONObject jsonObject =
			CMSDefaultPermissionUtil.getCMSDefaultPermissionPermissions(
				group.getExternalReferenceCode(), group.getGroupId());

		JSONArray jsonArray = jsonObject.getJSONArray("L_BASIC_WEB_CONTENT");

		Assert.assertEquals(2, jsonArray.length());
		Assert.assertEquals(ActionKeys.UPDATE, jsonArray.getString(0));
		Assert.assertEquals(ActionKeys.VIEW, jsonArray.getString(1));
	}

	private boolean _isCMSSiteInitialized() throws Exception {
		ObjectFolder objectFolder =
			_objectFolderLocalService.fetchObjectFolderByExternalReferenceCode(
				ObjectFolderConstants.EXTERNAL_REFERENCE_CODE_FILE_TYPES,
				TestPropsValues.getCompanyId());

		if (objectFolder != null) {
			return true;
		}

		return false;
	}

	private void _setUpProcessedFile(Bundle bundle, String fileName) {
		File file = bundle.getDataFile(
			".com.liferay.site.initializer.cms.internal.batch." + fileName +
				".batch.engine.data.json.0.processed");

		if ((file != null) && file.exists()) {
			file.delete();
		}
	}

	@Inject
	private BatchEngineUnitProcessor _batchEngineUnitProcessor;

	@Inject
	private BatchEngineUnitReader _batchEngineUnitReader;

	private DepotEntry _depotEntry;

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

	@Inject
	private ObjectFolderLocalService _objectFolderLocalService;

}