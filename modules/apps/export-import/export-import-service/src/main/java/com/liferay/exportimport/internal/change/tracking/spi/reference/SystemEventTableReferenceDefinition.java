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