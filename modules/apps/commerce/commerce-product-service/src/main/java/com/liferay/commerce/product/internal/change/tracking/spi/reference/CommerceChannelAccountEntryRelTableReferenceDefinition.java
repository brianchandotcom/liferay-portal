/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.commerce.product.model.CommerceChannelAccountEntryRelTable;
import com.liferay.commerce.product.model.CommerceChannelTable;
import com.liferay.commerce.product.service.persistence.CommerceChannelAccountEntryRelPersistence;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = TableReferenceDefinition.class)
public class CommerceChannelAccountEntryRelTableReferenceDefinition
	implements TableReferenceDefinition<CommerceChannelAccountEntryRelTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<CommerceChannelAccountEntryRelTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<CommerceChannelAccountEntryRelTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.singleColumnReference(
			CommerceChannelAccountEntryRelTable.INSTANCE.commerceChannelId,
			CommerceChannelTable.INSTANCE.commerceChannelId
		).singleColumnReference(
			CommerceChannelAccountEntryRelTable.INSTANCE.companyId,
			CompanyTable.INSTANCE.companyId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _commerceChannelAccountEntryRelPersistence;
	}

	@Override
	public CommerceChannelAccountEntryRelTable getTable() {
		return CommerceChannelAccountEntryRelTable.INSTANCE;
	}

	@Reference
	private CommerceChannelAccountEntryRelPersistence
		_commerceChannelAccountEntryRelPersistence;

}