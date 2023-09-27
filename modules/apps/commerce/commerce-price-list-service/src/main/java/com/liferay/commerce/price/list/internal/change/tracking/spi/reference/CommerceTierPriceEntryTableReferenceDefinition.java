/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.price.list.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.commerce.price.list.model.CommercePriceEntryTable;
import com.liferay.commerce.price.list.model.CommerceTierPriceEntryTable;
import com.liferay.commerce.price.list.service.persistence.CommerceTierPriceEntryPersistence;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cheryl Tang
 */
@Component(service = TableReferenceDefinition.class)
public class CommerceTierPriceEntryTableReferenceDefinition
	implements TableReferenceDefinition<CommerceTierPriceEntryTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<CommerceTierPriceEntryTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<CommerceTierPriceEntryTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.singleColumnReference(
			CommerceTierPriceEntryTable.INSTANCE.commercePriceEntryId,
			CommercePriceEntryTable.INSTANCE.commercePriceEntryId);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _commerceTierPriceEntryPersistence;
	}

	@Override
	public CommerceTierPriceEntryTable getTable() {
		return CommerceTierPriceEntryTable.INSTANCE;
	}

	@Reference
	private CommerceTierPriceEntryPersistence
		_commerceTierPriceEntryPersistence;

}