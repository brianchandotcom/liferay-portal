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

package com.liferay.change.tracking.internal.spi.reference;

import com.liferay.announcements.kernel.model.AnnouncementsEntryTable;
import com.liferay.announcements.kernel.model.AnnouncementsFlagTable;
import com.liferay.announcements.kernel.service.persistence.AnnouncementsFlagPersistence;
import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class AnnouncementsFlagTableReferenceDefinition
	implements TableReferenceDefinition<AnnouncementsFlagTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<AnnouncementsFlagTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<AnnouncementsFlagTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.singleColumnReference(
			AnnouncementsFlagTable.INSTANCE.companyId,
			CompanyTable.INSTANCE.companyId
		).singleColumnReference(
			AnnouncementsFlagTable.INSTANCE.entryId,
			AnnouncementsEntryTable.INSTANCE.entryId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _announcementsFlagPersistence;
	}

	@Override
	public AnnouncementsFlagTable getTable() {
		return AnnouncementsFlagTable.INSTANCE;
	}

	@Reference
	private AnnouncementsFlagPersistence _announcementsFlagPersistence;

}