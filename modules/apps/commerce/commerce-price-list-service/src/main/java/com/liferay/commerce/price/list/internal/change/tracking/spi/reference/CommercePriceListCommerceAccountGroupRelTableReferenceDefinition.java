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
@Component(enabled = false, service = TableReferenceDefinition.class)
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