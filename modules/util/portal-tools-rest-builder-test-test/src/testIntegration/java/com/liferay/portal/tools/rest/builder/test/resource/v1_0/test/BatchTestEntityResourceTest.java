/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.rest.builder.test.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationSettingsMapFactoryUtil;
import com.liferay.exportimport.kernel.configuration.constants.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalService;
import com.liferay.exportimport.kernel.service.ExportImportLocalService;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.FeatureFlagTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.tools.rest.builder.test.client.dto.v1_0.BatchTestEntity;
import com.liferay.portal.tools.rest.builder.test.client.pagination.Page;
import com.liferay.portal.tools.rest.builder.test.resource.v1_0.test.portlet.BatchTestEntityPortlet;
import com.liferay.staging.StagingGroupHelper;

import jakarta.portlet.Portlet;

import java.io.File;

import java.util.Dictionary;
import java.util.Hashtable;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Alejandro Tardín
 */
@FeatureFlag("LPD-35914")
@RunWith(Arquillian.class)
public class BatchTestEntityResourceTest
	extends BaseBatchTestEntityResourceTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		_registerTestPortlet();

		FeatureFlagTestUtil.invokeFeatureFlagListeners(
			CompanyConstants.SYSTEM, true, "LPD-35914");
	}

	@AfterClass
	public static void tearDownClass() {
		FeatureFlagTestUtil.invokeFeatureFlagListeners(
			CompanyConstants.SYSTEM, false, "LPD-35914");

		_unregisterTestPortlet();
	}

	@Test
	public void testExportImport() throws Exception {
		Page<BatchTestEntity> batchTestEntitiesPage =
			batchTestEntityResource.getBatchTestEntitiesPage();

		long totalCount = batchTestEntitiesPage.getTotalCount();

		BatchTestEntity batchTestEntity1 =
			batchTestEntityResource.postBatchTestEntity(
				randomBatchTestEntity());
		BatchTestEntity batchTestEntity2 =
			batchTestEntityResource.postBatchTestEntity(
				randomBatchTestEntity());

		batchTestEntitiesPage =
			batchTestEntityResource.getBatchTestEntitiesPage();

		Assert.assertEquals(
			totalCount + 2, batchTestEntitiesPage.getTotalCount());

		Group group = _stagingGroupHelper.fetchCompanyGroup(
			TestPropsValues.getCompanyId());

		File larFile = _exportImportLocalService.exportLayoutsAsFile(
			_exportImportConfigurationLocalService.
				addDraftExportImportConfiguration(
					TestPropsValues.getUserId(),
					ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT,
					ExportImportConfigurationSettingsMapFactoryUtil.
						buildExportLayoutSettingsMap(
							TestPropsValues.getUser(), group.getGroupId(),
							false, new long[0],
							HashMapBuilder.put(
								PortletDataHandlerKeys.PORTLET_DATA,
								new String[] {Boolean.TRUE.toString()}
							).put(
								PortletDataHandlerKeys.PORTLET_DATA + "_" +
									"com_liferay_portal_tools_rest_builder_" +
										"test_portlet_BatchTestEntityPortlet",
								new String[] {Boolean.TRUE.toString()}
							).build())));

		batchTestEntityResource.deleteBatchTestEntityByExternalReferenceCode(
			batchTestEntity1.getExternalReferenceCode());
		batchTestEntityResource.deleteBatchTestEntityByExternalReferenceCode(
			batchTestEntity2.getExternalReferenceCode());

		batchTestEntitiesPage =
			batchTestEntityResource.getBatchTestEntitiesPage();

		Assert.assertEquals(totalCount, batchTestEntitiesPage.getTotalCount());

		_exportImportLocalService.importLayouts(
			_exportImportConfigurationLocalService.
				addDraftExportImportConfiguration(
					TestPropsValues.getUserId(),
					ExportImportConfigurationConstants.TYPE_IMPORT_LAYOUT,
					ExportImportConfigurationSettingsMapFactoryUtil.
						buildImportLayoutSettingsMap(
							TestPropsValues.getUser(), group.getGroupId(),
							false, new long[0],
							HashMapBuilder.put(
								PortletDataHandlerKeys.PORTLET_DATA,
								new String[] {Boolean.TRUE.toString()}
							).build())),
			larFile);

		batchTestEntitiesPage =
			batchTestEntityResource.getBatchTestEntitiesPage();

		Assert.assertEquals(
			totalCount + 2, batchTestEntitiesPage.getTotalCount());
	}

	@Override
	protected BatchTestEntity
			testDeleteBatchTestEntityByExternalReferenceCode_addBatchTestEntity()
		throws Exception {

		return batchTestEntityResource.postBatchTestEntity(
			randomBatchTestEntity());
	}

	@Override
	protected BatchTestEntity testGetBatchTestEntitiesPage_addBatchTestEntity(
			BatchTestEntity batchTestEntity)
		throws Exception {

		return batchTestEntityResource.postBatchTestEntity(batchTestEntity);
	}

	@Override
	protected BatchTestEntity testGetBatchTestEntity_addBatchTestEntity()
		throws Exception {

		return batchTestEntityResource.postBatchTestEntity(
			randomBatchTestEntity());
	}

	@Override
	protected BatchTestEntity
			testGetBatchTestEntityByExternalReferenceCode_addBatchTestEntity()
		throws Exception {

		return batchTestEntityResource.postBatchTestEntity(
			randomBatchTestEntity());
	}

	@Override
	protected BatchTestEntity testPostBatchTestEntity_addBatchTestEntity(
			BatchTestEntity batchTestEntity)
		throws Exception {

		return batchTestEntityResource.postBatchTestEntity(batchTestEntity);
	}

	@Override
	protected BatchTestEntity
			testPutBatchTestEntityByExternalReferenceCode_addBatchTestEntity()
		throws Exception {

		return batchTestEntityResource.postBatchTestEntity(
			randomBatchTestEntity());
	}

	private static void _registerTestPortlet() {
		Bundle bundle = FrameworkUtil.getBundle(
			BatchTestEntityResourceTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_batchTestEntityPortlet = new BatchTestEntityPortlet();

		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put(
			"com.liferay.portlet.display-category", "category.hidden");
		properties.put(
			"com.liferay.portlet.preferences-unique-per-layout", "false");
		properties.put("jakarta.portlet.display-name", "REST Builder");
		properties.put(
			"jakarta.portlet.name", BatchTestEntityPortlet.PORTLET_NAME);
		properties.put("jakarta.portlet.security-role-ref", "administrator");
		properties.put("jakarta.portlet.version", "4.0");

		_portletServiceRegistration = bundleContext.registerService(
			Portlet.class, _batchTestEntityPortlet, properties);
	}

	private static void _unregisterTestPortlet() {
		if (_portletServiceRegistration != null) {
			_portletServiceRegistration.unregister();

			_portletServiceRegistration = null;
		}
	}

	private static BatchTestEntityPortlet _batchTestEntityPortlet;
	private static ServiceRegistration<Portlet> _portletServiceRegistration;

	@Inject
	private ExportImportConfigurationLocalService
		_exportImportConfigurationLocalService;

	@Inject
	private ExportImportLocalService _exportImportLocalService;

	@Inject
	private StagingGroupHelper _stagingGroupHelper;

}