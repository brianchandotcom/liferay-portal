/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.pricing.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.commerce.pricing.model.CommercePriceModifierRelTable;
import com.liferay.commerce.pricing.model.CommercePriceModifierTable;
import com.liferay.commerce.pricing.service.persistence.CommercePriceModifierRelPersistence;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cheryl Tang
 */
@Component(service = TableReferenceDefinition.class)
public class CommercePriceModifierRelTableReferenceDefinition
	implements TableReferenceDefinition<CommercePriceModifierRelTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<CommercePriceModifierRelTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.singleColumnReference(
			CommercePriceModifierRelTable.INSTANCE.commercePriceModifierId,
			CommercePriceModifierTable.INSTANCE.commercePriceModifierId);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<CommercePriceModifierRelTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.singleColumnReference(
			CommercePriceModifierRelTable.INSTANCE.commercePriceModifierId,
			CommercePriceModifierTable.INSTANCE.commercePriceModifierId);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _commercePriceModifierRelPersistence;
	}

	@Override
	public CommercePriceModifierRelTable getTable() {
		return CommercePriceModifierRelTable.INSTANCE;
	}

	@Reference
	private CommercePriceModifierRelPersistence
		_commercePriceModifierRelPersistence;

}