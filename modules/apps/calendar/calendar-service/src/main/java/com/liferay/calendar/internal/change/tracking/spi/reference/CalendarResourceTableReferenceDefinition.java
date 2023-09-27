/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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