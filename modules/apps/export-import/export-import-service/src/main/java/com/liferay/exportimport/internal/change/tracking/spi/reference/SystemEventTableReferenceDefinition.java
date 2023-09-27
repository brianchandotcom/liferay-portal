/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.portal.kernel.model.SystemEventTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.SystemEventPersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class SystemEventTableReferenceDefinition
	implements TableReferenceDefinition<SystemEventTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<SystemEventTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<SystemEventTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			SystemEventTable.INSTANCE
		).parentColumnReference(
			SystemEventTable.INSTANCE.systemEventId,
			SystemEventTable.INSTANCE.parentSystemEventId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _systemEventPersistence;
	}

	@Override
	public SystemEventTable getTable() {
		return SystemEventTable.INSTANCE;
	}

	@Reference
	private SystemEventPersistence _systemEventPersistence;

}