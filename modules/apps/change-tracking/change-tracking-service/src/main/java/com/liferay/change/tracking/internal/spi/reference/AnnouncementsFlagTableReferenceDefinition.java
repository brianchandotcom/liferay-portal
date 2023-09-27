/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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