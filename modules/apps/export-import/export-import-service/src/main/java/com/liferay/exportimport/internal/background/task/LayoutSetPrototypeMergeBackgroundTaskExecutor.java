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
import com.liferay.exportimport.report.service.ExportImportReportEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskResult;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.LayoutSetPrototypeLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

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

		long layoutSetId = MapUtil.getLong(parameterMap, "layoutSetId");
		long layoutSetPrototypeId = MapUtil.getLong(
			parameterMap, "layoutSetPrototypeId");

		try {
			LayoutSet layoutSet = _layoutSetLocalService.getLayoutSet(
				layoutSetId);

			LayoutSetPrototype layoutSetPrototype =
				_layoutSetPrototypeLocalService.getLayoutSetPrototype(
					layoutSetPrototypeId);

			_mergeLayoutPrototypeLayouts(layoutSetPrototype);

			String cacheFileName = StringBundler.concat(
				_TEMP_DIR, layoutSetPrototype.getUuid(), ".v",
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

			Map<String, Serializable> importLayoutSettingsMap =
				ExportImportConfigurationSettingsMapFactoryUtil.
					buildImportLayoutSettingsMap(
						user.getUserId(), layoutSet.getGroupId(),
						layoutSet.isPrivateLayout(), null, parameterMap,
						user.getLocale(), user.getTimeZone());

			ExportImportConfiguration importExportImportConfiguration =
				_exportImportConfigurationLocalService.
					addExportImportConfiguration(
						user.getUserId(), layoutSet.getGroupId(),
						StringPool.BLANK, StringPool.BLANK,
						ExportImportConfigurationConstants.TYPE_IMPORT_LAYOUT,
						importLayoutSettingsMap, WorkflowConstants.STATUS_DRAFT,
						new ServiceContext());

			TransactionInvokerUtil.invoke(
				transactionConfig,
				new LayoutImportCallable(
					importExportImportConfiguration, cacheFile));

			int count =
				_exportImportReportEntryLocalService.
					getExportImportReportEntriesCount(
						importExportImportConfiguration.getCompanyId(),
						importExportImportConfiguration.
							getExportImportConfigurationId());

			if (count > 0) {
				return BackgroundTaskResult.COMPLETED_WITH_ERRORS;
			}

			return BackgroundTaskResult.SUCCESS;
		}
		catch (Throwable throwable) {
			_log.error(
				"The sync process failed for layout set prototype " +
					layoutSetPrototypeId,
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

	private void _mergeLayoutPrototypeLayouts(
			LayoutSetPrototype layoutSetPrototype)
		throws PortalException {

		Group layoutSetPrototypeGroup = layoutSetPrototype.getGroup();

		List<Layout> layouts = _layoutLocalService.getLayouts(
			layoutSetPrototypeGroup.getGroupId(), true);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(layoutSetPrototype.getCompanyId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			for (Layout layout : layouts) {
				if (Validator.isNull(
						layout.getPortletLayoutPageTemplateEntryERC())) {

					continue;
				}

				try {
					_sites.mergeLayoutPrototypeLayout(
						layout.getGroup(), layout);
				}
				catch (Exception exception) {
					_log.error(
						StringBundler.concat(
							"Unable to merge layout prototype into layout ",
							layout.getPlid(), " of layout set prototype ",
							layoutSetPrototype.getLayoutSetPrototypeId()),
						exception);
				}
			}
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	private static final String _TEMP_DIR =
		SystemProperties.get(SystemProperties.TMP_DIR) +
			"/liferay/layout_set_prototype/";

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutSetPrototypeMergeBackgroundTaskExecutor.class);

	@Reference
	private ExportImportConfigurationLocalService
		_exportImportConfigurationLocalService;

	@Reference
	private ExportImportLocalService _exportImportLocalService;

	@Reference
	private ExportImportReportEntryLocalService
		_exportImportReportEntryLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutSetLocalService _layoutSetLocalService;

	@Reference
	private LayoutSetPrototypeLocalService _layoutSetPrototypeLocalService;

	@Reference
	private UserLocalService _userLocalService;

	private class LayoutImportCallable implements Callable<Void> {

		public LayoutImportCallable(
			ExportImportConfiguration exportImportConfiguration, File file) {

			_exportImportConfiguration = exportImportConfiguration;
			_file = file;
		}

		@Override
		public Void call() throws PortalException {
			try {
				MergeLayoutPrototypesThreadLocal.setInProgress(true);

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

	}

}