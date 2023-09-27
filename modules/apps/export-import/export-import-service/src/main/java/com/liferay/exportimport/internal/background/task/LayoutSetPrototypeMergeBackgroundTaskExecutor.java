/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.background.task;

import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationSettingsMapFactoryUtil;
import com.liferay.exportimport.kernel.configuration.constants.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalService;
import com.liferay.exportimport.kernel.service.ExportImportLocalService;
import com.liferay.exportimport.kernel.staging.MergeLayoutPrototypesThreadLocal;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
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
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.LayoutSetPrototypeLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.sites.kernel.util.Sites;

import java.io.File;
import java.io.Serializable;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.Callable;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tamas Molnar
 */
@Component(
	property = "background.task.executor.class.name=com.liferay.exportimport.internal.background.task.LayoutSetPrototypeMergeBackgroundTaskExecutor",
	service = BackgroundTaskExecutor.class
)
public class LayoutSetPrototypeMergeBackgroundTaskExecutor
	extends BaseExportImportBackgroundTaskExecutor {

	@Override
	public BackgroundTaskExecutor clone() {
		return this;
	}

	@Override
	public BackgroundTaskResult execute(BackgroundTask backgroundTask)
		throws Exception {

		ExportImportConfiguration exportImportConfiguration =
			getExportImportConfiguration(backgroundTask);

		Map<String, Serializable> settingsMap =
			exportImportConfiguration.getSettingsMap();

		Map<String, String[]> parameterMap =
			(Map<String, String[]>)settingsMap.get("parameterMap");

		boolean anyFailedLayoutModifiedSinceLastMerge = MapUtil.getBoolean(
			parameterMap, "anyFailedLayoutModifiedSinceLastMerge");
		long layoutSetId = MapUtil.getLong(parameterMap, "layoutSetId");
		long layoutSetPrototypeId = MapUtil.getLong(
			parameterMap, "layoutSetPrototypeId");

		try {
			LayoutSet layoutSet = _layoutSetLocalService.getLayoutSet(
				layoutSetId);

			LayoutSetPrototype layoutSetPrototype =
				_layoutSetPrototypeLocalService.getLayoutSetPrototype(
					layoutSetPrototypeId);

			UnicodeProperties settingsUnicodeProperties =
				layoutSet.getSettingsProperties();

			long lastMergeTime = GetterUtil.getLong(
				settingsUnicodeProperties.getProperty(Sites.LAST_MERGE_TIME));
			long lastMergeVersion = GetterUtil.getLong(
				settingsUnicodeProperties.getProperty(
					Sites.LAST_MERGE_VERSION));

			Date layoutSetPrototypeModifiedDate =
				layoutSetPrototype.getModifiedDate();

			if ((lastMergeVersion == layoutSetPrototype.getMvccVersion()) &&
				(lastMergeTime >= layoutSetPrototypeModifiedDate.getTime()) &&
				!anyFailedLayoutModifiedSinceLastMerge) {

				if (_log.isDebugEnabled()) {
					StringBundler sb = new StringBundler(5);

					sb.append("Skipping background task ");
					sb.append(backgroundTask.getBackgroundTaskId());
					sb.append(", layoutSet ");
					sb.append(layoutSetId);
					sb.append(" is already up to date");

					_log.debug(sb.toString());
				}

				return new BackgroundTaskResult(
					BackgroundTaskConstants.STATUS_SUCCESSFUL);
			}

			boolean importData = MapUtil.getBoolean(parameterMap, "importData");

			String cacheFileName = StringBundler.concat(
				_TEMP_DIR, layoutSetPrototype.getUuid(), importData, ".v",
				layoutSetPrototype.getMvccVersion(), ".lar");

			File cacheFile = new File(cacheFileName);

			if (cacheFile.exists()) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Using cached layout set prototype LAR file " +
							cacheFile.getAbsolutePath());
				}
			}
			else {
				File larFile = _exportImportLocalService.exportLayoutsAsFile(
					exportImportConfiguration);

				try {
					FileUtil.copyFile(larFile, cacheFile);

					if (_log.isDebugEnabled()) {
						_log.debug(
							StringBundler.concat(
								"Copied ", larFile.getAbsolutePath(), " to ",
								cacheFile.getAbsolutePath()));
					}
				}
				catch (Exception exception) {
					_log.error(
						StringBundler.concat(
							"Unable to copy file ", larFile.getAbsolutePath(),
							" to ", cacheFile.getAbsolutePath()),
						exception);

					cacheFile = larFile;
				}
			}

			User user = _userLocalService.getDefaultUser(
				layoutSet.getCompanyId());

			parameterMap.put(
				"lastMergeVersion",
				new String[] {
					String.valueOf(layoutSetPrototype.getMvccVersion())
				});

			Map<String, Serializable> importLayoutSettingsMap =
				ExportImportConfigurationSettingsMapFactoryUtil.
					buildImportLayoutSettingsMap(
						user.getUserId(), layoutSet.getGroupId(),
						layoutSet.isPrivateLayout(), null, parameterMap,
						user.getLocale(), user.getTimeZone());

			TransactionInvokerUtil.invoke(
				transactionConfig,
				new LayoutImportCallable(
					_exportImportConfigurationLocalService.
						addExportImportConfiguration(
							user.getUserId(), layoutSet.getGroupId(),
							StringPool.BLANK, StringPool.BLANK,
							ExportImportConfigurationConstants.
								TYPE_IMPORT_LAYOUT,
							importLayoutSettingsMap,
							WorkflowConstants.STATUS_DRAFT,
							new ServiceContext()),
					cacheFile, layoutSet));

			return BackgroundTaskResult.SUCCESS;
		}
		catch (Throwable throwable) {
			LayoutSetPrototype layoutSetPrototype =
				_layoutSetPrototypeLocalService.getLayoutSetPrototype(
					layoutSetPrototypeId);

			LayoutSet layoutSetPrototypeLayoutSet =
				layoutSetPrototype.getLayoutSet();

			UnicodeProperties layoutSetPrototypeSettingsUnicodeProperties =
				layoutSetPrototypeLayoutSet.getSettingsProperties();

			int mergeFailCount = GetterUtil.getInteger(
				layoutSetPrototypeSettingsUnicodeProperties.getProperty(
					Sites.MERGE_FAIL_COUNT));

			mergeFailCount++;

			layoutSetPrototypeSettingsUnicodeProperties.setProperty(
				Sites.MERGE_FAIL_COUNT, String.valueOf(mergeFailCount));

			_layoutSetLocalService.updateLayoutSet(layoutSetPrototypeLayoutSet);

			_log.error(
				StringBundler.concat(
					"Merge fail count increased to ", mergeFailCount,
					" for layout set prototype ",
					layoutSetPrototype.getLayoutSetPrototypeId()),
				throwable);

			throw new SystemException(throwable);
		}
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		setBackgroundTaskStatusMessageTranslator(
			new LayoutExportImportBackgroundTaskStatusMessageTranslator());

		setIsolationLevel(BackgroundTaskConstants.ISOLATION_LEVEL_COMPANY);
	}

	private static final String _TEMP_DIR =
		SystemProperties.get(SystemProperties.TMP_DIR) +
			"/liferay/layout_set_prototype/";

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutSetPrototypeMergeBackgroundTaskExecutor.class);

	@Reference
	private BackgroundTaskManager _backgroundTaskManager;

	@Reference
	private ExportImportConfigurationLocalService
		_exportImportConfigurationLocalService;

	@Reference
	private ExportImportLocalService _exportImportLocalService;

	@Reference
	private LayoutSetLocalService _layoutSetLocalService;

	@Reference
	private LayoutSetPrototypeLocalService _layoutSetPrototypeLocalService;

	@Reference
	private Sites _sites;

	@Reference
	private UserLocalService _userLocalService;

	private class LayoutImportCallable implements Callable<Void> {

		public LayoutImportCallable(
			ExportImportConfiguration exportImportConfiguration, File file,
			LayoutSet layoutSet) {

			_exportImportConfiguration = exportImportConfiguration;
			_file = file;
			_layoutSet = layoutSet;
		}

		@Override
		public Void call() throws PortalException {
			try {
				MergeLayoutPrototypesThreadLocal.setInProgress(true);

				_sites.removeMergeFailFriendlyURLLayouts(_layoutSet);

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

		private final ExportImportConfiguration _exportImportConfiguration;
		private final File _file;
		private final LayoutSet _layoutSet;

	}

}