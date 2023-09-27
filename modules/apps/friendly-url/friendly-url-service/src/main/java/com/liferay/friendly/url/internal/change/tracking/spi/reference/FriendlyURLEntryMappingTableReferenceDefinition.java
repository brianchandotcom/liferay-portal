/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.friendly.url.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.friendly.url.model.FriendlyURLEntryMappingTable;
import com.liferay.friendly.url.model.FriendlyURLEntryTable;
import com.liferay.friendly.url.service.persistence.FriendlyURLEntryMappingPersistence;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class FriendlyURLEntryMappingTableReferenceDefinition
	implements TableReferenceDefinition<FriendlyURLEntryMappingTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<FriendlyURLEntryMappingTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<FriendlyURLEntryMappingTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.singleColumnReference(
			FriendlyURLEntryMappingTable.INSTANCE.companyId,
			CompanyTable.INSTANCE.companyId
		).singleColumnReference(
			FriendlyURLEntryMappingTable.INSTANCE.friendlyURLEntryId,
			FriendlyURLEntryTable.INSTANCE.friendlyURLEntryId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _friendlyURLEntryMappingPersistence;
	}

	@Override
	public FriendlyURLEntryMappingTable getTable() {
		return FriendlyURLEntryMappingTable.INSTANCE;
	}

	@Reference
	private FriendlyURLEntryMappingPersistence
		_friendlyURLEntryMappingPersistence;

}