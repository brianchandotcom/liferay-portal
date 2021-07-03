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

package com.liferay.calendar.internal.change.tracking.spi.reference;

import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.model.CalendarResourceTable;
import com.liferay.calendar.service.persistence.CalendarResourcePersistence;
import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cheryl Tang
 */
@Component(service = TableReferenceDefinition.class)
public class CalendarResourceTableReferenceDefinition
	implements TableReferenceDefinition<CalendarResourceTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<CalendarResourceTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.assetEntryReference(
			CalendarResourceTable.INSTANCE.calendarResourceId,
			CalendarResource.class
		).resourcePermissionReference(
			CalendarResourceTable.INSTANCE.calendarResourceId,
			CalendarResource.class
		);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<CalendarResourceTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			CalendarResourceTable.INSTANCE);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _calendarResourcePersistence;
	}

	@Override
	public CalendarResourceTable getTable() {
		return CalendarResourceTable.INSTANCE;
	}

	@Reference
	private CalendarResourcePersistence _calendarResourcePersistence;

}