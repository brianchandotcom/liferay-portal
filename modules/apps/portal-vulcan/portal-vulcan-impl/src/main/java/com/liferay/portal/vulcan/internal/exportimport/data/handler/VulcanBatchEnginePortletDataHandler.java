/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.exportimport.data.handler;

import com.liferay.batch.engine.BatchEngineExportTaskExecutor;
import com.liferay.batch.engine.BatchEngineImportTaskExecutor;
import com.liferay.batch.engine.BatchEngineTaskExecuteStatus;
import com.liferay.batch.engine.BatchEngineTaskOperation;
import com.liferay.batch.engine.constants.BatchEngineImportTaskConstants;
import com.liferay.batch.engine.constants.CreateStrategy;
import com.liferay.batch.engine.model.BatchEngineExportTask;
import com.liferay.batch.engine.service.BatchEngineExportTaskService;
import com.liferay.batch.engine.service.BatchEngineImportTaskService;
import com.liferay.exportimport.kernel.lar.BasePortletDataHandler;
import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerControl;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.petra.io.StreamUtil;
import com.liferay.petra.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.MapUtil;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;

import java.util.Collections;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.portlet.PortletPreferences;

/**
 * @author Vendel Toreki
 * @author Alejandro Tardín
 */
public class VulcanBatchEnginePortletDataHandler
	extends BasePortletDataHandler {

	public static final String SCHEMA_VERSION = "4.0.0";

	public VulcanBatchEnginePortletDataHandler(
		BatchEngineExportTaskExecutor batchEngineExportTaskExecutor,
		BatchEngineExportTaskService batchEngineExportTaskService,
		BatchEngineImportTaskExecutor batchEngineImportTaskExecutor,
		BatchEngineImportTaskService batchEngineImportTaskService,
		String className, String taskItemDelegateName) {

		_batchEngineExportTaskExecutor = batchEngineExportTaskExecutor;
		_batchEngineExportTaskService = batchEngineExportTaskService;
		_batchEngineImportTaskExecutor = batchEngineImportTaskExecutor;
		_batchEngineImportTaskService = batchEngineImportTaskService;
		_className = className;
		_taskItemDelegateName = taskItemDelegateName;

		_fileName = taskItemDelegateName + ".json";

		setExportControls(
			new PortletDataHandlerBoolean(
				taskItemDelegateName, taskItemDelegateName, true, true, null,
				className));
	}

	@Override
	public String[] getClassNames() {
		return new String[] {_className};
	}

	@Override
	public String getName() {
		return _className + StringPool.POUND + _taskItemDelegateName;
	}

	@Override
	public String getSchemaVersion() {
		return SCHEMA_VERSION;
	}

	@Override
	public boolean isCompany() {
		return true;
	}

	@Override
	protected String doExportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		BatchEngineExportTask batchEngineExportTask =
			_batchEngineExportTaskService.addBatchEngineExportTask(
				null, portletDataContext.getCompanyId(), _getUserId(), null,
				_className, "JSON", BatchEngineTaskExecuteStatus.INITIAL.name(),
				Collections.emptyList(),
				HashMapBuilder.<String, Serializable>put(
					"batchNestedFields",
					() -> {
						if (MapUtil.getBoolean(
								portletDataContext.getParameterMap(),
								PortletDataHandlerKeys.PERMISSIONS)) {

							return "permissions";
						}

						return null;
					}
				).build(),
				_taskItemDelegateName);

		_batchEngineExportTaskExecutor.execute(batchEngineExportTask);

		portletDataContext.addZipEntry(
			_fileName, _getBytes(batchEngineExportTask));

		return getExportDataRootElementString(
			addExportDataRootElement(portletDataContext));
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		InputStream inputStream = portletDataContext.getZipEntryAsInputStream(
			_fileName);

		if (inputStream == null) {
			return portletPreferences;
		}

		_batchEngineImportTaskExecutor.execute(
			_batchEngineImportTaskService.addBatchEngineImportTask(
				null, portletDataContext.getCompanyId(), _getUserId(), 100,
				null, _className, _getBytes(_fileName, inputStream), "JSON",
				BatchEngineTaskExecuteStatus.INITIAL.name(),
				Collections.emptyMap(),
				BatchEngineImportTaskConstants.
					IMPORT_STRATEGY_ON_ERROR_CONTINUE,
				BatchEngineTaskOperation.CREATE.name(),
				HashMapBuilder.<String, Serializable>put(
					"batchRestrictFields",
					() -> {
						if (!MapUtil.getBoolean(
								portletDataContext.getParameterMap(),
								PortletDataHandlerKeys.PERMISSIONS)) {

							return "permissions";
						}

						return null;
					}
				).put(
					"createStrategy", CreateStrategy.UPSERT.getDBOperation()
				).build(),
				_taskItemDelegateName));

		return portletPreferences;
	}

	@Override
	protected long getExportModelCount(
		ManifestSummary manifestSummary,
		PortletDataHandlerControl[] portletDataHandlerControls) {

		// TODO LPD-45048

		return 1;
	}

	private byte[] _getBytes(BatchEngineExportTask batchEngineExportTask)
		throws Exception {

		try (InputStream inputStream =
				_batchEngineExportTaskService.openContentInputStream(
					batchEngineExportTask.getBatchEngineExportTaskId())) {

			// TODO LPD-45048

			File batchZipFile = FileUtil.createTempFile(inputStream);

			File tempFolder = FileUtil.createTempFolder();

			FileUtil.unzip(batchZipFile, tempFolder);

			return FileUtil.getBytes(new File(tempFolder, "export.json"));
		}
	}

	private byte[] _getBytes(String fileName, InputStream inputStream)
		throws Exception {

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		try (ZipOutputStream zipOutputStream = new ZipOutputStream(
				unsyncByteArrayOutputStream)) {

			ZipEntry zipEntry = new ZipEntry(fileName);

			zipOutputStream.putNextEntry(zipEntry);

			StreamUtil.transfer(inputStream, zipOutputStream, false);
		}

		return unsyncByteArrayOutputStream.toByteArray();
	}

	private long _getUserId() {
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		return permissionChecker.getUserId();
	}

	private final BatchEngineExportTaskExecutor _batchEngineExportTaskExecutor;
	private final BatchEngineExportTaskService _batchEngineExportTaskService;
	private final BatchEngineImportTaskExecutor _batchEngineImportTaskExecutor;
	private final BatchEngineImportTaskService _batchEngineImportTaskService;
	private final String _className;
	private final String _fileName;
	private final String _taskItemDelegateName;

}