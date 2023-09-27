/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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