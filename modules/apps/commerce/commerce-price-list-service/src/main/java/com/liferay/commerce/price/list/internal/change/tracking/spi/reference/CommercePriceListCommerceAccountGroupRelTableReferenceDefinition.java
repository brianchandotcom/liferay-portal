/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.price.list.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.commerce.price.list.model.CommercePriceListCommerceAccountGroupRelTable;
import com.liferay.commerce.price.list.model.CommercePriceListTable;
import com.liferay.commerce.price.list.service.persistence.CommercePriceListCommerceAccountGroupRelPersistence;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cheryl Tang
 */
@Component(service = TableReferenceDefinition.class)
public class CommercePriceListCommerceAccountGroupRelTableReferenceDefinition
	implements TableReferenceDefinition
		<CommercePriceListCommerceAccountGroupRelTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder
			<CommercePriceListCommerceAccountGroupRelTable>
				childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder
			<CommercePriceListCommerceAccountGroupRelTable>
				parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.singleColumnReference(
			CommercePriceListCommerceAccountGroupRelTable.INSTANCE.
				commercePriceListId,
			CommercePriceListTable.INSTANCE.commercePriceListId);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _commercePriceListCommerceAccountGroupRelPersistence;
	}

	@Override
	public CommercePriceListCommerceAccountGroupRelTable getTable() {
		return CommercePriceListCommerceAccountGroupRelTable.INSTANCE;
	}

	@Reference
	private CommercePriceListCommerceAccountGroupRelPersistence
		_commercePriceListCommerceAccountGroupRelPersistence;

}