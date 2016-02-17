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

package com.liferay.dynamic.data.mapping.background.task;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskResult;
import com.liferay.portal.kernel.backgroundtask.BaseBackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.display.BackgroundTaskDisplay;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.DDMStructureIndexer;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(
	immediate = true,
	property = {
		"background.task.executor.class.name=com.liferay.dynamic.data.mapping.background.task.DDMStructureIndexerBackgroundTaskExecutor"
	},
	service = BackgroundTaskExecutor.class
)
public class DDMStructureIndexerBackgroundTaskExecutor
	extends BaseBackgroundTaskExecutor {

	public static String getBackgroundTaskName(long structureId) {
		return _BACKGROUND_TASK_NAME_PREFIX.concat(String.valueOf(structureId));
	}

	@Override
	public BackgroundTaskExecutor clone() {
		return this;
	}

	@Override
	public BackgroundTaskResult execute(BackgroundTask backgroundTask)
		throws Exception {

		Map<String, Serializable> taskContextMap =
			backgroundTask.getTaskContextMap();

		long structureId = (long)taskContextMap.get("structureId");

		DDMStructureIndexer structureIndexer = getDDMStructureIndexer(
			structureId);

		List<Long> ddmStructureIds = getChildrenStructureIds(structureId);

		structureIndexer.reindexDDMStructures(ddmStructureIds);

		return BackgroundTaskResult.SUCCESS;
	}

	@Override
	public BackgroundTaskDisplay getBackgroundTaskDisplay(
		BackgroundTask backgroundTask) {

		return null;
	}

	@Override
	public int getIsolationLevel() {
		return BackgroundTaskConstants.ISOLATION_LEVEL_TASK_NAME;
	}

	@Override
	public boolean isSerial() {
		return true;
	}

	protected void getChildrenStructureIds(
			List<Long> structureIds, long parentStructureId)
		throws PortalException {

		List<DDMStructure> structures =
			_ddmStructureLocalService.getChildrenStructures(parentStructureId);

		for (DDMStructure structure : structures) {
			structureIds.add(structure.getStructureId());

			getChildrenStructureIds(structureIds, structure.getStructureId());
		}
	}

	protected List<Long> getChildrenStructureIds(long structureId)
		throws PortalException {

		List<Long> structureIds = new ArrayList<>();

		getChildrenStructureIds(structureIds, structureId);

		structureIds.add(0, structureId);

		return structureIds;
	}

	protected DDMStructureIndexer getDDMStructureIndexer(long structureId)
		throws PortalException {

		DDMStructure structure = _ddmStructureLocalService.getStructure(
			structureId);

		Indexer<?> indexer = _indexerRegistry.nullSafeGetIndexer(
			structure.getClassName());

		return (DDMStructureIndexer)indexer;
	}

	@Reference(unbind = "-")
	protected void setDDMStructureLocalService(
		DDMStructureLocalService ddmStructureLocalService) {

		_ddmStructureLocalService = ddmStructureLocalService;
	}

	@Reference(unbind = "-")
	protected void setIndexerRegistry(IndexerRegistry indexerRegistry) {
		_indexerRegistry = indexerRegistry;
	}

	private static final String _BACKGROUND_TASK_NAME_PREFIX =
		"DDMStructureIndexerBackgroundTaskExecutor-";

	private DDMStructureLocalService _ddmStructureLocalService;
	private IndexerRegistry _indexerRegistry;

}