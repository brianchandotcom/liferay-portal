/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.messaging;

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
		DDMStructureIndexer ddmStructureIndexer =
			(DDMStructureIndexer)message.get("ddmStructureIndexer");

		long structureId = message.getLong("structureId");

		List<Long> ddmStructureIds = new ArrayList<>();

		ddmStructureIds.add(structureId);

		_collectChildrenStructureIds(ddmStructureIds, structureId);

		ddmStructureIndexer.reindexDDMStructures(ddmStructureIds);
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
	private DDMStructureLocalService _ddmStructureLocalService;

}