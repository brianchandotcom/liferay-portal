/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.exportimport.kernel.background.task;

/**
 * @author Michael C. Han
 */
public class BackgroundTaskExecutorNames {

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