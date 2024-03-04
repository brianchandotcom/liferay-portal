/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.announcements.web.internal.change.tracking.spi.reference;

import com.liferay.announcements.kernel.model.AnnouncementsDeliveryTable;
import com.liferay.announcements.kernel.service.persistence.AnnouncementsDeliveryPersistence;
import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brooke Dalton
 */
@Component(service = TableReferenceDefinition.class)
public class AnnouncementsDeliveryTableReferenceDefinition
	implements TableReferenceDefinition<AnnouncementsDeliveryTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<AnnouncementsDeliveryTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<AnnouncementsDeliveryTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.singleColumnReference(
			AnnouncementsDeliveryTable.INSTANCE.companyId,
			CompanyTable.INSTANCE.companyId);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _announcementsDeliveryPersistence;
	}

	@Override
	public AnnouncementsDeliveryTable getTable() {
		return AnnouncementsDeliveryTable.INSTANCE;
	}

	@Reference
	private AnnouncementsDeliveryPersistence _announcementsDeliveryPersistence;

}