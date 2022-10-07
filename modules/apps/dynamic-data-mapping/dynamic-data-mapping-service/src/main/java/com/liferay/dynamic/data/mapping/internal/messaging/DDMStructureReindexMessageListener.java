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

package com.liferay.dynamic.data.mapping.internal.messaging;

import com.liferay.dynamic.data.mapping.internal.background.task.DDMStructureIndexerTracker;
import com.liferay.dynamic.data.mapping.internal.constants.DDMDestinationNames;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.search.DDMStructureIndexer;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(
	property = "destination.name=" + DDMDestinationNames.DDM_STRUCTURE_REINDEX,
	service = MessageListener.class
)
public class DDMStructureReindexMessageListener extends BaseMessageListener {

	@Override
	protected void doReceive(Message message) throws Exception {
		long structureId = message.getLong("structureId");

		DDMStructure structure = _ddmStructureLocalService.getStructure(
			structureId);

		DDMStructureIndexer structureIndexer =
			_ddmStructureIndexerTracker.getDDMStructureIndexer(
				structure.getClassName());

		List<Long> ddmStructureIds = new ArrayList<>();

		ddmStructureIds.add(structureId);

		_collectChildrenStructureIds(ddmStructureIds, structureId);

		structureIndexer.reindexDDMStructures(ddmStructureIds);
	}

	private void _collectChildrenStructureIds(
		List<Long> structureIds, long parentStructureId) {

		List<DDMStructure> structures =
			_ddmStructureLocalService.getChildrenStructures(parentStructureId);

		for (DDMStructure structure : structures) {
			structureIds.add(structure.getStructureId());

			_collectChildrenStructureIds(
				structureIds, structure.getStructureId());
		}
	}

	@Reference
	private DDMStructureIndexerTracker _ddmStructureIndexerTracker;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

}