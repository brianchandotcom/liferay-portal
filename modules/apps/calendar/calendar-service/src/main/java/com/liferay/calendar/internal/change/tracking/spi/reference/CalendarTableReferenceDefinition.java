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

import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarResourceTable;
import com.liferay.calendar.model.CalendarTable;
import com.liferay.calendar.service.persistence.CalendarPersistence;
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
public class CalendarTableReferenceDefinition
	implements TableReferenceDefinition<CalendarTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<CalendarTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.resourcePermissionReference(
			CalendarTable.INSTANCE.calendarId, Calendar.class);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<CalendarTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			CalendarTable.INSTANCE
		).singleColumnReference(
			CalendarTable.INSTANCE.calendarResourceId,
			CalendarResourceTable.INSTANCE.calendarResourceId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _calendarPersistence;
	}

	@Override
	public CalendarTable getTable() {
		return CalendarTable.INSTANCE;
	}

	@Reference
	private CalendarPersistence _calendarPersistence;

}