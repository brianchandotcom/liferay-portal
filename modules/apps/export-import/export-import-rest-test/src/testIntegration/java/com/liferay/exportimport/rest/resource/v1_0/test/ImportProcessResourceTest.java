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
import com.liferay.exportimport.rest.client.http.HttpInvoker;
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

		Group companyGroup = _stagingGroupHelper.fetchCompanyGroup(
			TestPropsValues.getCompanyId());

		_testPostScopeScopeKeyValidateError(companyGroup, testGroup);
		_testPostScopeScopeKeyValidateError(testGroup, companyGroup);
		_testPostScopeScopeKeyValidateSuccess(companyGroup);

		_testPostScopeScopeKeyValidateSuccess(testGroup);
		_testPostScopeScopeKeyValidateWithInvalidScope();
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

	private File _exportLayoutAsFile(long groupId) throws Exception {
		Map<String, Serializable> parameterMap =
			ExportImportConfigurationSettingsMapFactoryUtil.
				buildExportLayoutSettingsMap(
					TestPropsValues.getUser(), groupId, false, null,
					new HashMap<String, String[]>());

		ExportImportConfiguration exportImportConfiguration =
			ExportImportConfigurationLocalServiceUtil.
				addExportImportConfiguration(
					TestPropsValues.getUserId(), groupId,
					RandomTestUtil.randomString(),
					RandomTestUtil.randomString(),
					ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT,
					parameterMap, new ServiceContext());

		return ExportImportLocalServiceUtil.exportLayoutsAsFile(
			exportImportConfiguration);
	}

	private long _getCompanyGroupId() throws Exception {
		Group group = _stagingGroupHelper.fetchCompanyGroup(
			TestPropsValues.getCompanyId());

		return group.getGroupId();
	}

	private void _testPostScopeScopeKeyValidateError(
			Group scopeGroup, Group fileGroup)
		throws Exception {

		File file = _exportLayoutAsFile(fileGroup.getGroupId());

		try {
			ValidationResponse validationResponse =
				importProcessResource.postScopeScopeKeyValidate(
					String.valueOf(scopeGroup.getGroupId()), null,
					HashMapBuilder.<String, File>put(
						"file", file
					).build());

			String[] errorMessages = validationResponse.getErrorMessages();

			Assert.assertTrue(errorMessages.length > 0);

			Assert.assertFalse(validationResponse.getSuccess());
		}
		finally {
			FileUtil.delete(file);
		}
	}

	private void _testPostScopeScopeKeyValidateSuccess(Group group)
		throws Exception {

		for (String scopeKey :
				new String[] {
					group.getExternalReferenceCode(),
					String.valueOf(group.getGroupId()), group.getGroupKey()
				}) {

			File file = _exportLayoutAsFile(group.getGroupId());

			try {
				ValidationResponse validationResponse =
					importProcessResource.postScopeScopeKeyValidate(
						scopeKey, null,
						HashMapBuilder.<String, File>put(
							"file", file
						).build());

				Assert.assertNotNull(validationResponse.getFileEntryId());
				Assert.assertTrue(validationResponse.getSuccess());
			}
			finally {
				FileUtil.delete(file);
			}
		}
	}

	private void _testPostScopeScopeKeyValidateWithInvalidScope()
		throws Exception {

		File file = _exportLayoutAsFile(testGroup.getGroupId());

		try {
			HttpInvoker.HttpResponse httpResponse =
				importProcessResource.postScopeScopeKeyValidateHttpResponse(
					RandomTestUtil.randomString(), null,
					HashMapBuilder.<String, File>put(
						"file", file
					).build());

			Assert.assertEquals(404, httpResponse.getStatusCode());
		}
		finally {
			FileUtil.delete(file);
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