/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.internal.change.tracking.spi.reference;

import com.liferay.calendar.model.CalendarNotificationTemplateTable;
import com.liferay.calendar.model.CalendarTable;
import com.liferay.calendar.service.persistence.CalendarNotificationTemplatePersistence;
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
public class CalendarNotificationTemplateTableReferenceDefinition
	implements TableReferenceDefinition<CalendarNotificationTemplateTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<CalendarNotificationTemplateTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<CalendarNotificationTemplateTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			CalendarNotificationTemplateTable.INSTANCE
		).singleColumnReference(
			CalendarNotificationTemplateTable.INSTANCE.calendarId,
			CalendarTable.INSTANCE.calendarId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _calendarNotificationTemplatePersistence;
	}

	@Override
	public CalendarNotificationTemplateTable getTable() {
		return CalendarNotificationTemplateTable.INSTANCE;
	}

	@Reference
	private CalendarNotificationTemplatePersistence
		_calendarNotificationTemplatePersistence;

}