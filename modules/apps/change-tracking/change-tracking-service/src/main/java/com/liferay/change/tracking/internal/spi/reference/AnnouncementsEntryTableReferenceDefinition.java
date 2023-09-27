/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.spi.reference;

import com.liferay.announcements.kernel.model.AnnouncementsEntry;
import com.liferay.announcements.kernel.model.AnnouncementsEntryTable;
import com.liferay.announcements.kernel.service.persistence.AnnouncementsEntryPersistence;
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
public class AnnouncementsEntryTableReferenceDefinition
	implements TableReferenceDefinition<AnnouncementsEntryTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<AnnouncementsEntryTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.resourcePermissionReference(
			AnnouncementsEntryTable.INSTANCE.entryId, AnnouncementsEntry.class);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<AnnouncementsEntryTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.singleColumnReference(
			AnnouncementsEntryTable.INSTANCE.companyId,
			CompanyTable.INSTANCE.companyId);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _announcementsEntryPersistence;
	}

	@Override
	public AnnouncementsEntryTable getTable() {
		return AnnouncementsEntryTable.INSTANCE;
	}

	@Reference
	private AnnouncementsEntryPersistence _announcementsEntryPersistence;

}