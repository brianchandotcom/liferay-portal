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
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.test.util.FeatureFlagTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.tools.rest.builder.test.client.dto.v1_0.BatchTestEntity;
import com.liferay.portal.tools.rest.builder.test.client.pagination.Page;
import com.liferay.staging.StagingGroupHelper;

import jakarta.portlet.Portlet;

import java.io.File;

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
		Bundle bundle = FrameworkUtil.getBundle(
			BatchTestEntityResourceTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_serviceRegistration = bundleContext.registerService(
			Portlet.class, new BatchTestEntityPortlet(),
			HashMapDictionaryBuilder.<String, Object>put(
				"com.liferay.portlet.display-category", "category.hidden"
			).put(
				"com.liferay.portlet.preferences-unique-per-layout", "false"
			).put(
				"jakarta.portlet.display-name", "REST Builder"
			).put(
				"jakarta.portlet.name", BatchTestEntityPortlet.PORTLET_NAME
			).put(
				"jakarta.portlet.security-role-ref", "administrator"
			).put(
				"jakarta.portlet.version", "4.0"
			).build());

		FeatureFlagTestUtil.invokeFeatureFlagListeners(
			CompanyConstants.SYSTEM, true, "LPD-35914");
	}

	@AfterClass
	public static void tearDownClass() {
		FeatureFlagTestUtil.invokeFeatureFlagListeners(
			CompanyConstants.SYSTEM, false, "LPD-35914");

		_serviceRegistration.unregister();
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
									BatchTestEntityPortlet.PORTLET_NAME,
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

	public static class BatchTestEntityPortlet extends MVCPortlet {

		public static final String PORTLET_NAME =
			"com_liferay_portal_tools_rest_builder_test_portlet_" +
				"BatchTestEntityPortlet";

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

	private static ServiceRegistration<Portlet> _serviceRegistration;

	@Inject
	private ExportImportConfigurationLocalService
		_exportImportConfigurationLocalService;

	@Inject
	private ExportImportLocalService _exportImportLocalService;

	@Inject
	private StagingGroupHelper _stagingGroupHelper;

}