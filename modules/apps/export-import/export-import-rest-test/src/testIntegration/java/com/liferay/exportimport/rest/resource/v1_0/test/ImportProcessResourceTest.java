/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.exportimport.kernel.background.task.BackgroundTaskExecutorNames;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationSettingsMapFactoryUtil;
import com.liferay.exportimport.kernel.configuration.constants.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalServiceUtil;
import com.liferay.exportimport.kernel.service.ExportImportLocalServiceUtil;
import com.liferay.exportimport.rest.client.dto.v1_0.ImportProcess;
import com.liferay.exportimport.rest.client.dto.v1_0.ValidationResponse;
import com.liferay.portal.background.task.model.BackgroundTask;
import com.liferay.portal.background.task.service.BackgroundTaskLocalService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.vulcan.util.GroupUtil;
import com.liferay.staging.StagingGroupHelper;

import java.io.File;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Petteri Karttunen
 */
@RunWith(Arquillian.class)
public class ImportProcessResourceTest
	extends BaseImportProcessResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();
	}

	@Override
	@Test
	public void testPostScopeScopeKeyValidate() throws Exception {
		super.testPostScopeScopeKeyValidate();

		_testPostScopeScopeKeyValidateWithInstanceLar();
		_testPostScopeScopeKeyValidateWithSiteLar();
	}

	@Override
	@Test
	public void testPostValidate() throws Exception {
		super.testPostValidate();

		_testPostValidateWithInstanceLar();
		_testPostValidateWithSiteLar();
	}

	@Override
	protected ImportProcess
			testGetAssetLibraryImportProcessesPage_addImportProcess(
				Long assetLibraryId, ImportProcess importProcess)
		throws Exception {

		return _addImportProcess(
			GroupUtil.getDepotGroupId(
				String.valueOf(assetLibraryId), TestPropsValues.getCompanyId(),
				_depotEntryLocalService, _groupLocalService),
			randomImportProcess());
	}

	@Override
	protected ImportProcess testGetImportProcess_addImportProcess()
		throws Exception {

		return _addImportProcess(_getCompanyGroupId(), randomImportProcess());
	}

	@Override
	protected ImportProcess testGetImportProcessesPage_addImportProcess(
			ImportProcess importProcess)
		throws Exception {

		return _addImportProcess(_getCompanyGroupId(), randomImportProcess());
	}

	@Override
	protected ImportProcess testGetSiteImportProcessesPage_addImportProcess(
			Long siteId, ImportProcess importProcess)
		throws Exception {

		return _addImportProcess(siteId, randomImportProcess());
	}

	private ImportProcess _addImportProcess(
			long groupId, ImportProcess importProcess)
		throws Exception {

		BackgroundTask backgroundTask =
			_backgroundTaskLocalService.addBackgroundTask(
				TestPropsValues.getUserId(), groupId, importProcess.getTitle(),
				BackgroundTaskExecutorNames.
					LAYOUT_IMPORT_BACKGROUND_TASK_EXECUTOR,
				HashMapBuilder.<String, Serializable>put(
					"exportImportConfigurationId", RandomTestUtil.randomLong()
				).build(),
				null);

		_backgroundTasks.add(backgroundTask);

		return new ImportProcess() {
			{
				setDateCreated(backgroundTask.getCreateDate());
				setDateModified(backgroundTask.getModifiedDate());
				setId(backgroundTask.getBackgroundTaskId());
				setTitle(backgroundTask.getName());
			}
		};
	}

	private File _exportGroupToLar(long groupId) throws Exception {
		Map<String, Serializable> parameterMap =
			ExportImportConfigurationSettingsMapFactoryUtil.
				buildExportLayoutSettingsMap(
					TestPropsValues.getUser(), groupId, false, null,
					new HashMap<String, String[]>());

		ExportImportConfiguration exportImportConfiguration =
			ExportImportConfigurationLocalServiceUtil.
				addExportImportConfiguration(
					TestPropsValues.getUserId(), groupId,
					"Site Export Test - " + groupId,
					"Description of site export",
					ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT,
					parameterMap, new ServiceContext());

		return ExportImportLocalServiceUtil.exportLayoutsAsFile(
			exportImportConfiguration);
	}

	private File _exportInstanceToLar() throws Exception {
		long companyGroupId = _getCompanyGroupId();

		Map<String, Serializable> parameterMap =
			ExportImportConfigurationSettingsMapFactoryUtil.
				buildExportLayoutSettingsMap(
					TestPropsValues.getUser(), companyGroupId, false, null,
					new HashMap<String, String[]>());

		ExportImportConfiguration exportImportConfiguration =
			ExportImportConfigurationLocalServiceUtil.
				addExportImportConfiguration(
					TestPropsValues.getUserId(), companyGroupId,
					"Instance Export Test", "Description", 333, parameterMap,
					new ServiceContext());

		return ExportImportLocalServiceUtil.exportLayoutsAsFile(
			exportImportConfiguration);
	}

	private long _getCompanyGroupId() throws Exception {
		Group group = _stagingGroupHelper.fetchCompanyGroup(
			TestPropsValues.getCompanyId());

		return group.getGroupId();
	}

	private void _testPostScopeScopeKeyValidateWithInstanceLar()
		throws Exception {

		File larFile = _exportInstanceToLar();

		try {
			ValidationResponse validationResponse =
				importProcessResource.postScopeScopeKeyValidate(
					String.valueOf(testGroup.getGroupId()), null,
					HashMapBuilder.<String, File>put(
						"file", larFile
					).build());

			Assert.assertNotNull(validationResponse);
			Assert.assertFalse(validationResponse.getSuccess());

			Assert.assertTrue(validationResponse.getErrorMessages().length > 0);
		}
		finally {
			FileUtil.delete(larFile);
		}
	}

	private void _testPostScopeScopeKeyValidateWithSiteLar() throws Exception {
		File larFile = _exportGroupToLar(testGroup.getGroupId());

		try {
			ValidationResponse validationResponse =
				importProcessResource.postScopeScopeKeyValidate(
					String.valueOf(testGroup.getGroupId()), null,
					HashMapBuilder.<String, File>put(
						"file", larFile
					).build());

			Assert.assertNotNull(validationResponse);
			Assert.assertTrue(validationResponse.getSuccess());
			Assert.assertNotNull(validationResponse.getFileEntryId());
		}
		finally {
			FileUtil.delete(larFile);
		}
	}

	private void _testPostValidateWithInstanceLar() throws Exception {
		File larFile = _exportInstanceToLar();

		try {
			ValidationResponse validationResponse =
				importProcessResource.postValidate(
					null,
					HashMapBuilder.<String, File>put(
						"file", larFile
					).build());

			Assert.assertNotNull(validationResponse);
			Assert.assertTrue(validationResponse.getSuccess());
			Assert.assertNotNull(validationResponse.getFileEntryId());
		}
		finally {
			FileUtil.delete(larFile);
		}
	}

	private void _testPostValidateWithSiteLar() throws Exception {
		File larFile = _exportGroupToLar(testGroup.getGroupId());

		try {
			ValidationResponse validationResponse =
				importProcessResource.postValidate(
					null,
					HashMapBuilder.<String, File>put(
						"file", larFile
					).build());

			Assert.assertNotNull(validationResponse);
			Assert.assertFalse(validationResponse.getSuccess());
		}
		finally {
			FileUtil.delete(larFile);
		}
	}

	@Inject
	private BackgroundTaskLocalService _backgroundTaskLocalService;

	@DeleteAfterTestRun
	private final List<BackgroundTask> _backgroundTasks = new ArrayList<>();

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private StagingGroupHelper _stagingGroupHelper;

}