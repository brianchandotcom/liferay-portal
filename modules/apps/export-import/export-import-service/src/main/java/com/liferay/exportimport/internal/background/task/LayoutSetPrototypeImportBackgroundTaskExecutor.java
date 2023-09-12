/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.background.task;

import com.liferay.exportimport.kernel.background.task.BackgroundTaskExecutorNames;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportLocalService;
import com.liferay.exportimport.kernel.staging.MergeLayoutPrototypesThreadLocal;
import com.liferay.layout.set.prototype.configuration.LayoutSetPrototypeConfiguration;
import com.liferay.layout.set.prototype.configuration.LayoutSetPrototypeSystemConfiguration;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManager;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskResult;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.LayoutSetPrototypeLocalService;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.sites.kernel.util.Sites;

import java.io.File;
import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tamas Molnar
 */
@Component(
	configurationPid = "com.liferay.layout.set.prototype.configuration.LayoutSetPrototypeSystemConfiguration",
	property = "background.task.executor.class.name=com.liferay.exportimport.internal.background.task.LayoutSetPrototypeImportBackgroundTaskExecutor",
	service = BackgroundTaskExecutor.class
)
public class LayoutSetPrototypeImportBackgroundTaskExecutor
	extends BaseExportImportBackgroundTaskExecutor {

	@Override
	public BackgroundTaskExecutor clone() {
		return this;
	}

	@Override
	public BackgroundTaskResult execute(BackgroundTask backgroundTask)
		throws Exception {

		ExportImportThreadLocal.setLayoutImportInProcess(true);

		try {
			if (isCancelPropagationImportTask()) {
				List<BackgroundTask> newBackgroundTasks =
					_backgroundTaskManager.getBackgroundTasks(
						backgroundTask.getGroupId(),
						LayoutSetPrototypeImportBackgroundTaskExecutor.class.
							getName(),
						BackgroundTaskConstants.STATUS_NEW);

				List<BackgroundTask> queuedBackgroundTasks =
					_backgroundTaskManager.getBackgroundTasks(
						backgroundTask.getGroupId(),
						LayoutSetPrototypeImportBackgroundTaskExecutor.class.
							getName(),
						BackgroundTaskConstants.STATUS_QUEUED);

				if (!newBackgroundTasks.isEmpty() ||
					!queuedBackgroundTasks.isEmpty()) {

					if (_log.isDebugEnabled()) {
						StringBundler sb = new StringBundler(7);

						sb.append("Cancelling background task ");
						sb.append(backgroundTask.getBackgroundTaskId());
						sb.append(", found ");
						sb.append(newBackgroundTasks.size());
						sb.append(" new and ");
						sb.append(queuedBackgroundTasks.size());
						sb.append(" queued tasks");

						_log.debug(sb.toString());
					}

					return new BackgroundTaskResult(
						BackgroundTaskConstants.STATUS_CANCELLED);
				}
			}

			ExportImportConfiguration exportImportConfiguration =
				getExportImportConfiguration(backgroundTask);

			List<FileEntry> attachmentsFileEntries =
				backgroundTask.getAttachmentsFileEntries();

			File file = null;

			for (FileEntry attachmentsFileEntry : attachmentsFileEntries) {
				try {
					file = _file.createTempFile("lar");

					_file.write(file, attachmentsFileEntry.getContentStream());

					TransactionInvokerUtil.invoke(
						transactionConfig,
						new LayoutImportCallable(
							exportImportConfiguration, file));
				}
				catch (Throwable throwable) {
					Map<String, Serializable> settingsMap =
						exportImportConfiguration.getSettingsMap();

					Map<String, String[]> parameterMap =
						(Map<String, String[]>)settingsMap.get("parameterMap");

					long layoutSetPrototypeId = MapUtil.getLong(
						parameterMap, "layoutSetPrototypeId");

					LayoutSetPrototype layoutSetPrototype =
						_layoutSetPrototypeLocalService.getLayoutSetPrototype(
							layoutSetPrototypeId);

					LayoutSet layoutSetPrototypeLayoutSet =
						layoutSetPrototype.getLayoutSet();

					UnicodeProperties
						layoutSetPrototypeSettingsUnicodeProperties =
							layoutSetPrototypeLayoutSet.getSettingsProperties();

					int mergeFailCount = GetterUtil.getInteger(
						layoutSetPrototypeSettingsUnicodeProperties.getProperty(
							Sites.MERGE_FAIL_COUNT));

					mergeFailCount++;

					layoutSetPrototypeSettingsUnicodeProperties.setProperty(
						Sites.MERGE_FAIL_COUNT, String.valueOf(mergeFailCount));

					_layoutSetLocalService.updateLayoutSet(
						layoutSetPrototypeLayoutSet);

					_log.error(
						StringBundler.concat(
							"Merge fail count increased to ", mergeFailCount,
							" for layout set prototype ",
							layoutSetPrototype.getLayoutSetPrototypeId()),
						throwable);

					throw new SystemException(throwable);
				}
				finally {
					MergeLayoutPrototypesThreadLocal.setInProgress(false);

					_file.delete(file);
				}
			}

			return BackgroundTaskResult.SUCCESS;
		}
		finally {
			ExportImportThreadLocal.setLayoutValidationInProcess(false);
		}
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		setBackgroundTaskStatusMessageTranslator(
			new LayoutExportImportBackgroundTaskStatusMessageTranslator());

		LayoutSetPrototypeSystemConfiguration
			layoutSetPrototypeSystemConfiguration =
				ConfigurableUtil.createConfigurable(
					LayoutSetPrototypeSystemConfiguration.class, properties);

		String importTaskIsolation =
			layoutSetPrototypeSystemConfiguration.importTaskIsolation();

		if (Validator.isNotNull(importTaskIsolation) &&
			importTaskIsolation.equals("company")) {

			setIsolationLevel(BackgroundTaskConstants.ISOLATION_LEVEL_COMPANY);
		}
		else {
			setIsolationLevel(BackgroundTaskConstants.ISOLATION_LEVEL_GROUP);
		}
	}

	protected boolean isCancelPropagationImportTask() {
		try {
			LayoutSetPrototypeConfiguration layoutSetPrototypeConfiguration =
				_getLayoutSetPrototypeConfiguration();

			if ((layoutSetPrototypeConfiguration != null) &&
				layoutSetPrototypeConfiguration.cancelPropagationImportTask()) {

				return true;
			}

			return false;
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return false;
	}

	private LayoutSetPrototypeConfiguration
		_getLayoutSetPrototypeConfiguration() {

		try {
			return _configurationProvider.getCompanyConfiguration(
				LayoutSetPrototypeConfiguration.class,
				CompanyThreadLocal.getCompanyId());
		}
		catch (ConfigurationException configurationException) {
			_log.error(
				"Unable to load layout set configuration",
				configurationException);
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutSetPrototypeImportBackgroundTaskExecutor.class);

	@Reference
	private BackgroundTaskManager _backgroundTaskManager;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private ExportImportLocalService _exportImportLocalService;

	@Reference
	private com.liferay.portal.kernel.util.File _file;

	@Reference
	private LayoutSetLocalService _layoutSetLocalService;

	@Reference
	private LayoutSetPrototypeLocalService _layoutSetPrototypeLocalService;

	private class LayoutImportCallable implements Callable<Void> {

		public LayoutImportCallable(
			ExportImportConfiguration exportImportConfiguration, File file) {

			_exportImportConfiguration = exportImportConfiguration;
			_file = file;
		}

		@Override
		public Void call() throws PortalException {
			try {
				_cleanUpPreviousBackgroundTasks();

				MergeLayoutPrototypesThreadLocal.setInProgress(true);

				_exportImportLocalService.importLayoutsDataDeletions(
					_exportImportConfiguration, _file);

				_exportImportLocalService.importLayouts(
					_exportImportConfiguration, _file);

				return null;
			}
			finally {
				MergeLayoutPrototypesThreadLocal.setInProgress(false);
			}
		}

		private void _cleanUpPreviousBackgroundTasks() {
			try {
				List<BackgroundTask> backgroundTasks =
					_backgroundTaskManager.getBackgroundTasks(
						_exportImportConfiguration.getGroupId(),
						BackgroundTaskExecutorNames.
							LAYOUT_SET_PROTOTYPE_IMPORT_BACKGROUND_TASK_EXECUTOR);

				for (BackgroundTask backgroundTask : backgroundTasks) {
					int status = backgroundTask.getStatus();

					if ((status ==
							BackgroundTaskConstants.STATUS_IN_PROGRESS) ||
						(status == BackgroundTaskConstants.STATUS_NEW) ||
						(status == BackgroundTaskConstants.STATUS_QUEUED)) {

						continue;
					}

					_backgroundTaskManager.deleteBackgroundTask(
						backgroundTask.getBackgroundTaskId());
				}
			}
			catch (PortalException portalException) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to clean up previous background tasks",
						portalException);
				}
			}
		}

		private final ExportImportConfiguration _exportImportConfiguration;
		private final File _file;

	}

}