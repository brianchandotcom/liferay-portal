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

package com.liferay.commerce.product.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.commerce.product.model.CommerceChannelRelTable;
import com.liferay.commerce.product.model.CommerceChannelTable;
import com.liferay.commerce.product.service.persistence.CommerceChannelRelPersistence;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cheryl Tang
 */
@Component(enabled = false, service = TableReferenceDefinition.class)
public class CommerceChannelRelTableReferenceDefinition
	implements TableReferenceDefinition<CommerceChannelRelTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<CommerceChannelRelTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.singleColumnReference(
			CommerceChannelRelTable.INSTANCE.commerceChannelId,
			CommerceChannelTable.INSTANCE.commerceChannelId);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<CommerceChannelRelTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.singleColumnReference(
			CommerceChannelRelTable.INSTANCE.commerceChannelId,
			CommerceChannelTable.INSTANCE.commerceChannelId
		).singleColumnReference(
			CommerceChannelRelTable.INSTANCE.companyId,
			CompanyTable.INSTANCE.companyId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _commerceChannelRelPersistence;
	}

	@Override
	public CommerceChannelRelTable getTable() {
		return CommerceChannelRelTable.INSTANCE;
	}

	@Reference
	private CommerceChannelRelPersistence _commerceChannelRelPersistence;

}