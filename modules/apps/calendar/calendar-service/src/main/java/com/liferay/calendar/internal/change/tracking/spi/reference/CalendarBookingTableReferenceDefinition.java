/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.internal.change.tracking.spi.reference;

import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.model.CalendarBookingTable;
import com.liferay.calendar.model.CalendarTable;
import com.liferay.calendar.service.persistence.CalendarBookingPersistence;
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
public class CalendarBookingTableReferenceDefinition
	implements TableReferenceDefinition<CalendarBookingTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<CalendarBookingTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.assetEntryReference(
			CalendarBookingTable.INSTANCE.calendarBookingId,
			CalendarBooking.class
		).resourcePermissionReference(
			CalendarBookingTable.INSTANCE.calendarBookingId,
			CalendarBooking.class
		);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<CalendarBookingTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			CalendarBookingTable.INSTANCE
		).parentColumnReference(
			CalendarBookingTable.INSTANCE.calendarBookingId,
			CalendarBookingTable.INSTANCE.parentCalendarBookingId
		).singleColumnReference(
			CalendarBookingTable.INSTANCE.calendarId,
			CalendarTable.INSTANCE.calendarId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _calendarBookingPersistence;
	}

	@Override
	public CalendarBookingTable getTable() {
		return CalendarBookingTable.INSTANCE;
	}

	@Reference
	private CalendarBookingPersistence _calendarBookingPersistence;

}