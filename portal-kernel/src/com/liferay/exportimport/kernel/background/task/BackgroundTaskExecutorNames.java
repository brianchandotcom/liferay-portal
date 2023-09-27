/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.kernel.background.task;

/**
 * @author Michael C. Han
 */
public class BackgroundTaskExecutorNames {

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static final String DDM_STRUCTURE_INDEXER_BACKGROUND_TASK_EXECUTOR =
		"com.liferay.dynamic.data.mapping.internal.background.task." +
			"DDMStructureIndexerBackgroundTaskExecutor";

	public static final String LAYOUT_EXPORT_BACKGROUND_TASK_EXECUTOR =
		"com.liferay.exportimport.internal.background.task." +
			"LayoutExportBackgroundTaskExecutor";

	public static final String LAYOUT_IMPORT_BACKGROUND_TASK_EXECUTOR =
		"com.liferay.exportimport.internal.background.task." +
			"LayoutImportBackgroundTaskExecutor";

	public static final String LAYOUT_REMOTE_STAGING_BACKGROUND_TASK_EXECUTOR =
		"com.liferay.exportimport.internal.background.task." +
			"LayoutRemoteStagingBackgroundTaskExecutor";

	public static final String
		LAYOUT_SET_PROTOTYPE_IMPORT_BACKGROUND_TASK_EXECUTOR =
			"com.liferay.exportimport.internal.background.task." +
				"LayoutSetPrototypeImportBackgroundTaskExecutor";

	public static final String
		LAYOUT_SET_PROTOTYPE_MERGE_BACKGROUND_TASK_EXECUTOR =
			"com.liferay.exportimport.internal.background.task." +
				"LayoutSetPrototypeMergeBackgroundTaskExecutor";

	public static final String LAYOUT_STAGING_BACKGROUND_TASK_EXECUTOR =
		"com.liferay.exportimport.internal.background.task." +
			"LayoutStagingBackgroundTaskExecutor";

	public static final String PORTLET_EXPORT_BACKGROUND_TASK_EXECUTOR =
		"com.liferay.exportimport.internal.background.task." +
			"PortletExportBackgroundTaskExecutor";

	public static final String PORTLET_IMPORT_BACKGROUND_TASK_EXECUTOR =
		"com.liferay.exportimport.internal.background.task." +
			"PortletImportBackgroundTaskExecutor";

	public static final String PORTLET_REMOTE_STAGING_BACKGROUND_TASK_EXECUTOR =
		"com.liferay.exportimport.internal.background.task." +
			"PortletRemoteStagingBackgroundTaskExecutor";

	public static final String PORTLET_STAGING_BACKGROUND_TASK_EXECUTOR =
		"com.liferay.exportimport.internal.background.task." +
			"PortletStagingBackgroundTaskExecutor";

}