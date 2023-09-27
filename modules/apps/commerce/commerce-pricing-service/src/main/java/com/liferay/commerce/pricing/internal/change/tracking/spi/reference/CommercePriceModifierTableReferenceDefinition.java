/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.pricing.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.commerce.price.list.model.CommercePriceListTable;
import com.liferay.commerce.pricing.model.CommercePriceModifierTable;
import com.liferay.commerce.pricing.service.persistence.CommercePriceModifierPersistence;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cheryl Tang
 */
@Component(service = TableReferenceDefinition.class)
public class CommercePriceModifierTableReferenceDefinition
	implements TableReferenceDefinition<CommercePriceModifierTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<CommercePriceModifierTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.singleColumnReference(
			CommercePriceModifierTable.INSTANCE.commercePriceListId,
			CommercePriceListTable.INSTANCE.commercePriceListId);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<CommercePriceModifierTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			CommercePriceModifierTable.INSTANCE
		).singleColumnReference(
			CommercePriceModifierTable.INSTANCE.commercePriceListId,
			CommercePriceListTable.INSTANCE.commercePriceListId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _commercePriceModifierPersistence;
	}

	@Override
	public CommercePriceModifierTable getTable() {
		return CommercePriceModifierTable.INSTANCE;
	}

	@Reference
	private CommercePriceModifierPersistence _commercePriceModifierPersistence;

}